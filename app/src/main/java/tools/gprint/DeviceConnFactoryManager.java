package tools.gprint;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.base.application.App;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;
import com.gprinter.io.BluetoothPort;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.taodaji.R;

/**
 * Created by Administrator
 *
 * @author 猿史森林
 * Time 2017/8/2
 */
public class DeviceConnFactoryManager {

    private BluetoothPort mPort;

    private static final String TAG = DeviceConnFactoryManager.class.getSimpleName();

    private String macAddress;
    private String name;

    private int id;

    private static DeviceConnFactoryManager[] deviceConnFactoryManagers = new DeviceConnFactoryManager[4];

    private boolean isOpenPort;
    /**
     * ESC查询打印机实时状态指令
     */
    private byte[] esc = {0x10, 0x04, 0x02};
    /**
     * ESC查询打印机实时状态 缺纸状态
     */
    private static final int ESC_STATE_PAPER_ERR = 0x20;

    /**
     * ESC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int ESC_STATE_COVER_OPEN = 0x04;

    /**
     * ESC指令查询打印机实时状态 打印机报错状态
     */
    private static final int ESC_STATE_ERR_OCCURS = 0x40;

    /**
     * TSC查询打印机状态指令
     */
    private byte[] tsc = {0x1b, '!', '?'};

    /**
     * TSC指令查询打印机实时状态 打印机缺纸状态
     */
    private static final int TSC_STATE_PAPER_ERR = 0x04;

    /**
     * TSC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int TSC_STATE_COVER_OPEN = 0x01;



    /**
     * TSC指令查询打印机实时状态 打印机出错状态
     */
    private static final int TSC_STATE_ERR_OCCURS = 0x80;


    public static final byte FLAG = 0x10;
    public static final String ACTION_CONN_STATE = "action_connect_state";
    public static final String ACTION_QUERY_PRINTER_STATE = "action_query_printer_state";
    public static final String STATE = "state";
    public static final String DEVICE_ID = "id";
    public static final int CONN_STATE_DISCONNECT = 0x90;
    public static final int CONN_STATE_CONNECTING = CONN_STATE_DISCONNECT << 1;
    public static final int CONN_STATE_FAILED = CONN_STATE_DISCONNECT << 2;
    public static final int CONN_STATE_CONNECTED = CONN_STATE_DISCONNECT << 3;

    public static final int PRINT_STATE_SUC = CONN_STATE_DISCONNECT << 4;
    public static final int PRINT_STATE_ERR = CONN_STATE_DISCONNECT << 5;

    /**
     * 判断打印机所使用指令是否是ESC指令
     */
    private PrinterCommand currentPrinterCommand = PrinterCommand.TSC;

    private boolean isQuery = false;//正在查询打印机状态？

    private boolean isReader = false;


    public enum CONN_METHOD {
        //蓝牙连接
        BLUETOOTH("BLUETOOTH"), //USB连接
        USB("USB"), //wifi连接
        WIFI("WIFI"), //串口连接
        SERIAL_PORT("SERIAL_PORT");

        private String name;

        private CONN_METHOD(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static DeviceConnFactoryManager[] getDeviceConnFactoryManagers() {
        return deviceConnFactoryManagers;
    }

    /**
     * 打开端口
     *
     * @return
     */
    public void openPort() {
        isOpenPort = false;
        if (mPort != null) {
            mPort.closePort();
            mPort = null;
        }

        sendStateBroadcast(CONN_STATE_CONNECTING);
        mPort = new BluetoothPort(macAddress);

        ThreadManager.getDownloadPool().execute(() -> {
            isOpenPort =mPort.openPort();
            if (isOpenPort) {
                startReadState();
                btnPrinterState(true);
            } else {
                sendStateBroadcast(CONN_STATE_FAILED);
            }
        });
    }


    public void btnPrinterState(boolean isQue) {
        this.isQuery = isQue;
        if (currentPrinterCommand == PrinterCommand.ESC) {
            Vector<Byte> data = new Vector<>(esc.length);
            for (int i = 0; i < esc.length; i++) {
                data.add(esc[i]);
            }
            sendDataImmediately(data); //发送esc数据
        } else if (currentPrinterCommand == PrinterCommand.TSC) {
            Vector<Byte> data = new Vector<>(tsc.length);
            for (int i = 0; i < tsc.length; i++) {
                data.add(tsc[i]);
            }
            sendDataImmediately(data); //发送tsc数据
        }

    }


    /**
     * 获取端口打开状态（true 打开，false 未打开）
     *
     * @return
     */
    public boolean getConnState() {
        return isOpenPort;
    }

    /**
     * 获取连接蓝牙的物理地址
     *
     * @return
     */
    public String getMacAddress() {
        return macAddress;
    }


    /**
     * 关闭端口
     */
    public void closePort() {
        if (this.mPort != null) {
            System.out.println("id -> " + id);
            this.mPort.closePort();
            stopReadState();
            isOpenPort = false;
        }
        sendStateBroadcast(CONN_STATE_DISCONNECT);
    }


    public static void closeAllPort() {
        for (DeviceConnFactoryManager deviceConnFactoryManager : deviceConnFactoryManagers) {
            if (deviceConnFactoryManager != null) {
                deviceConnFactoryManager.closePort();
//                deviceConnFactoryManagers[deviceConnFactoryManager.id] = null;
            }
        }
    }

    private DeviceConnFactoryManager(Build build) {
        this.macAddress = build.macAddress;
        this.id = build.id;
        this.name = build.name;
        deviceConnFactoryManagers[id] = this;
    }


    Runnable runnable = new Runnable() {

        //读取打印机返回信息
        private byte[] buffer = new byte[100];

        @Override
        public void run() {
            while (isReader) {
                int len = readDataImmediately(buffer);
                    int result = judgeResponseType(buffer[0]); //数据右移

                    String status = "";

                    if (currentPrinterCommand == PrinterCommand.ESC) {

                        if (len == -3) {
                            if (deviceConnFactoryManagers[id] != null) {
                                closePort();
                            }
                        }
                        //查询打印机状态
                        else if (result == 0) {//打印机状态查询
                            Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                            intent.putExtra(DEVICE_ID, id);
                            UIUtils.getContext().sendBroadcast(intent);
                        } else if (result == 1) {

                            //查询打印机实时状态
                            if ((buffer[0] & ESC_STATE_PAPER_ERR) > 0) {
                                status = UIUtils.getString(R.string.str_printer_out_of_paper);
                            }
                            if ((buffer[0] & ESC_STATE_COVER_OPEN) > 0) {
                                status = UIUtils.getString(R.string.str_printer_open_cover);
                            }
                            if ((buffer[0] & ESC_STATE_ERR_OCCURS) > 0) {
                                status = UIUtils.getString(R.string.str_printer_error);
                            }
                            if (!TextUtils.isEmpty(status)) {
                                sendStateBroadcast(PRINT_STATE_ERR);
                                UIUtils.showToastSafesShort(status);
                            } else {
                                if (isQuery) {
                                    isQuery = false;
                                    sendStateBroadcast(CONN_STATE_CONNECTED);
                                } else {


                                }
                            }
                        }

                    } else if (currentPrinterCommand == PrinterCommand.TSC) {
                        //打印机状态查询
                        if (len == 1) {
                            if ((buffer[0] & TSC_STATE_PAPER_ERR) > 0) {//缺纸
                                status = UIUtils.getString(R.string.str_printer_out_of_paper);
                            } else if ((buffer[0] & TSC_STATE_COVER_OPEN) > 0) {//开盖
                                status = UIUtils.getString(R.string.str_printer_open_cover);
                            } else if ((buffer[0] & TSC_STATE_ERR_OCCURS) > 0) {//打印机报错
                                status = UIUtils.getString(R.string.str_printer_error);
                            }
                            if (!TextUtils.isEmpty(status)) {
                                sendStateBroadcast(PRINT_STATE_ERR, status);
                                UIUtils.showToastSafesShort(status);
                            } else {
                                if (isQuery) {
                                    isQuery = false;
                                    sendStateBroadcast(CONN_STATE_CONNECTED);
                                }

//                            else {
//                                sendStateBroadcast(PRINT_STATE_SUC);
//                            }
                            }
                        }
                        //无法连接
                        else if (len == -3) {
                            if (deviceConnFactoryManagers[id] != null) {
                                closePort();
                            }
                        }
                        //打印机查询
                        else {
                            if (len>0){

                                LogUtils.e(len);
                                Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                                intent.putExtra(DEVICE_ID, id);
                                UIUtils.getContext().sendBroadcast(intent);

                            }

                        }
                    }
                }


        }

    };


    public void sendDataImmediately(final Vector<Byte> data) {
        if (this.mPort == null) {
            return;
        }
        //  Log.e(TAG, "data -> " + new String(com.gprinter.command.GpUtils.convertVectorByteTobytes(data), "gb2312"));
        this.mPort.writeDataImmediately(data, 0, data.size());
    }

    public int readDataImmediately(byte[] buffer) {
        try {
            return this.mPort.readData(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return -3;
        }
    }

    /**
     * 开始读取打印机状态
     */
    public void startReadState() {
        if (!isReader) {
            isReader = true;
            ThreadManager.getLongPool().execute(runnable);
        }
    }


    public void stopReadState() {
        isReader = false;
        ThreadManager.getLongPool().removeRunning(runnable);
    }


    private void sendStateBroadcast(int state, String... msg) {
        Intent intent = new Intent(ACTION_CONN_STATE);
        if (msg.length > 0) {
            intent.putExtra("msg", msg[0]);
        }
        intent.putExtra(STATE, state);
        intent.putExtra(DEVICE_ID, id);
        App.getApplication().sendBroadcast(intent);
    }

    /**
     * 判断是实时状态（10 04 02）还是查询状态（1D 72 01）
     */
    private int judgeResponseType(byte r) {
        return (byte) ((r & FLAG) >> 4);
    }

    public static final class Build {
        private String macAddress;
        private String name;
        private int id;


        public DeviceConnFactoryManager.Build setMacAddress(String macAddress) {
            this.macAddress = macAddress;
            return this;
        }


        public DeviceConnFactoryManager.Build setName(String name) {
            this.name = name;
            return this;
        }

        public DeviceConnFactoryManager.Build setId(int id) {
            this.id = id;
            return this;
        }


        public DeviceConnFactoryManager build() {
            return new DeviceConnFactoryManager(this);
        }
    }
}