package tools.gprint;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.UIUtils;
import com.gprinter.command.EscCommand;
import com.gprinter.command.LabelCommand;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.event.PrintComplete;
import cn.com.taodaji.model.event.PrintStatus;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.orderPlace.OrderPrintBlueToothSetting;

import static tools.gprint.DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE;
import static tools.gprint.DeviceConnFactoryManager.CONN_STATE_FAILED;
import static tools.gprint.DeviceConnFactoryManager.PRINT_STATE_ERR;
import static tools.gprint.DeviceConnFactoryManager.PRINT_STATE_SUC;

/**
 * Created by Administrator on 2017-12-28.
 */

public class PrintUtils {
    private int print_state = 0;//0设置打印机,1已连接，2连接失败
    private Context mContext;
    public boolean isPrint = false;//正在打印？
    private Handler   handler = new Handler();
    private StringBuffer sb = new StringBuffer();
    //打印队列
    private List<OrderDetail.ItemsBean> list = new ArrayList<>();

    private String isC_name,isC_phone;

    //是否打印完成
    public boolean isPrintDone() {
        return list.size() == 0;
    }

    public String getIsC_name() {
        return isC_name;
    }

    public void setIsC_name(String isC_name) {
        this.isC_name = isC_name;
    }

    public String getIsC_phone() {
        return isC_phone;
    }

    public void setIsC_phone(String isC_phone) {
        this.isC_phone = isC_phone;
    }

    public PrintUtils(Context mContext, TextView mTextView) {
        this.mContext = mContext;
        this.mTextView = mTextView;
        mTextView.setOnClickListener(v -> {
            if (blueToothChecked()) {
                OrderPrintBlueToothSetting.startActivity(mContext);
            }
        });
        printerRegister();
        initBluetooth();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 单个打印
     */
    public void printOne(OrderDetail.ItemsBean bean) {
        if (bean == null) return;

        if (!isPrint) {
            list.clear();
        } else {
            UIUtils.showToastSafesShort("正在打印");
        }

        //加入打印队列
        list.add(0, bean);
        printLabelClicked();
    }

    /**
     * 批量打印
     */
    public void printList(List<OrderDetail.ItemsBean> itemsBeanList) {
        LogUtils.i(list);
        LogUtils.i(isPrint);
        if (itemsBeanList == null || itemsBeanList.isEmpty()) {
            UIUtils.showToastSafesShort("未找到需要发货的商品");
            return;
        }
        if (!isPrint) {
            list.clear();
        } else {
            UIUtils.showToastSafesShort("正在打印");
        }
        //反转序列
        Collections.reverse(itemsBeanList);
        //加入打印队列
        list.addAll(0, itemsBeanList);
        LogUtils.i(list.size());
        printLabelClicked();

    }

    public void clear() {
        list.clear();
    }

    /**
     * 继续打印
     */
    public void printList() {
        if (!isPrintDone()) printLabelClicked();
    }

    private boolean blueToothChecked() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivity(enableIntent);
            return false;
        }
        return true;
    }

    private void initBluetooth() {
        SharedPreferences sharedPreferences = UIUtils.getSharedPreferences("blueTooth");
        deviceName = sharedPreferences.getString("deviceName", "");
        bluetoothAddr = sharedPreferences.getString("bluetoothAddr", null);

        if (BluetoothAdapter.getDefaultAdapter() != null && !BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            mTextView.setText(UIUtils.getString(R.string.str_bluetooth_connect));
            print_state = 0;
            return;
        }
        if (bluetoothAddr != null) {
            print_state = 0;
            connect();
        } else {
            print_state = 0;
            mTextView.setText("设置打印机");
        }

    }

    private void printLabelClicked() {
        if (print_state != 1) {
            UIUtils.showToastSafesShort("打印机未连接");
            return;
        }
//        || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
            UIUtils.showToastSafesShort(UIUtils.getString(R.string.str_cann_printer));
            return;
        }

        sendLabelWithResponse();
    }

    private void sendLabelWithResponse() {
        if (isPrintDone()) return;
        isPrint = true;
        OrderDetail.ItemsBean bean = list.get(list.size() - 1);
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(70, 60); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(2); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        tsc.addDensity(LabelCommand.DENSITY.DNESITY15);//设置打印浓度
        tsc.addSpeed(LabelCommand.SPEED.SPEED4);//设置打印速度


        // 开启带Response的打印，用于连续打印
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);

        tsc.addReference(13, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区
        OrderDetail.ReceiveAddrBean receiveAddrBean = bean.getReceiveAddr();//productCriteria = 1;//  商品标准， 1：通货商品 2 ： 精品商品 '
        String hotelName=receiveAddrBean.getHotelName();
          if (!TextUtils.isEmpty(hotelName)&&hotelName.length()>16) {
            hotelName = hotelName.substring(0,13)+"...";
        }
        // 绘制简体中文
        if (bean.getIsC()==1){
//            tsc.addText(0, 27, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, "淘大集(家用) ");
            tsc.addText(13, 27, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2,  bean.getCustomerName() );

        }else {
            tsc.addText(13, 27, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, "淘大集订单 ");


        }

        String str=UIUtils.isNullOrZeroLenght(bean.getRemark())?"":bean.getRemark();

        if (!TextUtils.isEmpty(str)) {
            str="备注："+str;
            if (bean.getIsC()!=1){

            if (str.length()<12) {
                // 绘制简体中文
                tsc.addText(260, 27, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, str );
            }else {
                // 绘制简体中文
                tsc.addText(260, 27, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, str.substring(0,12) );
                tsc.addText(260, 57, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, str.substring(12,str.length()) );
            }

            }else {
                if (str.length() < 11) {
                    tsc.addText(325, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, str );

                } else {
                    tsc.addText(325, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, str.substring(0,10) );

                }

            }
        }

        tsc.addBar(0, 90, 600, 1);
        // 绘制简体中文
//        if (bean.getDriverNo().length()==0){
//            tsc.addText(0, 100, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, bean.getStationShortName()+" "+bean.getDriverNo());
//        }else {
//            tsc.addText(0, 100, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, bean.getStationShortName()+" "+bean.getDriverNo().substring(0,bean.getDriverNo().length()-1));
//        }
                tsc.addText(13, 100, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, bean.getCustomerLineCode());

        tsc.addBar(325, 90, 1, 65);

        // 绘制简体中文
//        tsc.addText(255, 100, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, bean.getCustomerLineCode());
        tsc.addBar(0, 155, 600, 1);

//        OrderDetail.ReceiveAddrBean receiveAddrBean = bean.getReceiveAddr();//productCriteria = 1;//  商品标准， 1：通货商品 2 ： 精品商品 '

        // 绘制图片
       /* if (bean.getIsC()!=1){
            Bitmap b = BitmapFactory.decodeResource(UIUtils.getResources(), R.mipmap.cai4);
            tsc.addBitmap(0, 159, LabelCommand.BITMAP_MODE.OVERWRITE, 40, b);
        }*/


//        String hotelName=receiveAddrBean.getHotelName();
//        if (!TextUtils.isEmpty(hotelName)&&hotelName.length()>16) {
//            hotelName = hotelName.substring(0,13)+"...";
//        }

        String storeName=bean.getStoreName();
        if (!TextUtils.isEmpty(storeName)&&storeName.length()>12) {
            storeName = storeName.substring(0,12);
        }
         if (bean.getIsC()==1){
             tsc.addText(13, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,storeName);
            }else {
             tsc.addText(13, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, hotelName);
             tsc.addText(325, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,storeName);

        }


//        if (bean.getIsC()==1){
//            tsc.addText(0, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, bean.getCustomerName() );
//
//        }else {
//            tsc.addText(0, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, hotelName );
//
//        }
        // 绘制简体中文
     /*   if (bean.getIsC()==1){
            tsc.addText(45, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, bean.getCustomerName() );

        }else {
            tsc.addText(45, 167, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, hotelName );

        }*/

        if (bean.getDriverNo().length()==0){
            tsc.addText(330, 90, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, bean.getStationShortName()+" "+bean.getDriverNo());
        }else {
            tsc.addText(330, 90, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, bean.getStationShortName()+" "+bean.getDriverNo().substring(0,bean.getDriverNo().length()-1));
        }
        if (bean.getIsC()==1){
            tsc.addText(330, 130, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    (ListUtils.isNullOrZeroLenght(getIsC_phone())?bean.getReceiveAddr().getTelephone():getIsC_phone())+(ListUtils.isNullOrZeroLenght(getIsC_name())? "   "+bean.getReceiveAddr().getName():"   "+getIsC_name()));
//        tsc.addText(330, 130, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
//           bean.getCustomerMobile());
        }


        // 绘制图片
//        Bitmap b1 = BitmapFactory.decodeResource(UIUtils.getResources(), R.mipmap.xiao);
//        tsc.addBitmap(280, 159, LabelCommand.BITMAP_MODE.OVERWRITE, 40, b1);



        tsc.addBar(0, 202, 600, 1);

        tsc.addText(13, 210, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "单号:" + bean.getQrCodeId() + "");

        String sign="";
        if (bean.getProductCriteria()==1) {
            sign="(通)";
        }else  if (bean.getProductCriteria()==2) {
            sign="(精)";
        }else {
            sign="";
        }

        String nickName = bean.getNickName();
        if (nickName.length() > 0) nickName = "(" + nickName + ")";

        String name="商品:" +sign+ bean.getName() + nickName;
//        if (!TextUtils.isEmpty(name)&&name.length()>19) {
//            name = name.substring(0,16)+"...";
//        }

        String receiveHouse = bean.getReceiveWarehouseShortName();

        tsc.addText(13, 250, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,name);

        tsc.addBar(350, 202, 1, 68);
        // 绘制简体中文
        tsc.addText(355, 200, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, receiveHouse);

        //tsc.addBar(0, 270, 600, 1);

        //数量
        BigDecimal count = bean.getAmount();
        if (bean.getLevelType() == 2) {
            //如果均价单位是最小单
            if (Constants.specification_unit_base.contains(bean.getAvgUnit())) {
                count = bean.getLevel2Value().multiply(bean.getAmount());
            }
        }

        BigDecimal mat = (count).setScale(2, BigDecimal.ROUND_HALF_UP);
        String good_num = mat.stripTrailingZeros().toPlainString();

        String countText = good_num + bean.getUnit();
        if (bean.getLevelType() == 2) {
            //如果均价单位是最小单
            if (Constants.specification_unit_base.contains(bean.getAvgUnit())) {
                countText = good_num + bean.getAvgUnit();
            }
            countText += "(每" + bean.getUnit() + String.valueOf(bean.getLevel2Value()) + bean.getLevel2Unit() + "*" + bean.getAmount() + ")";
        } else if (bean.getLevelType() == 3) {
            countText += "(" + bean.getAmount() + "*" + String.valueOf(bean.getLevel2Value()) + bean.getLevel2Unit() + "*" + String.valueOf(bean.getLevel3Value() + bean.getLevel3Unit()) + ")";
        }

        tsc.addText(13, 280, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_2, LabelCommand.FONTMUL.MUL_2, "共订:" + countText);

        // 绘制一维条`码
        tsc.add1DBarcode(40, 335, LabelCommand.BARCODETYPE.EAN13, 90, LabelCommand.READABEL.EANBEL, LabelCommand.ROTATION.ROTATION_0, 5, 5, bean.getQrCodeId());
        tsc.addPrint(1, 1); // 打印标签
        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);

        Vector<Byte> datas = tsc.getCommand(); // 发送数据
        // 发送数据
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(datas);

        //移除已经打印过的数据
        if (list.size() > 0) {
            sb.append(list.get(list.size()-1).getOrderId()+",");
            list.remove(list.size() - 1);
        }

        if (deviceName!=null&&deviceName.contains("Printer")){
            if (!isPrintDone()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         *要执行的操作
                         */
                        sendLabelWithResponse();
                    }
                }, 3000);//3秒后执行Runnable中的run方法

            }else {
                EventBus.getDefault().post(new PrintComplete(sb.substring(0,sb.length()-1)));
            }
        }
    }


    private int id = 0;
    private String deviceName, bluetoothAddr;
    private TextView mTextView;

    /**
     * 注册广播
     */
    private void printerRegister() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_QUERY_PRINTER_STATE);
        filter.addAction(DeviceConnFactoryManager.ACTION_CONN_STATE);
        mContext.registerReceiver(receiver, filter);
    }

    /**
     *
     */
    public void recycler() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mContext.unregisterReceiver(receiver);
        DeviceConnFactoryManager.closeAllPort();
    }

    //打印机设置返回
    @Subscribe
    public void onEvent(BluetoothDevice device) {
        deviceName = device.getName();
        bluetoothAddr = device.getAddress();
        connect();
    }

    /**
     * 连接蓝牙mac地址
     */
    private void connect() {
        if (bluetoothAddr != null) {
            //初始化话DeviceConnFactoryManager
            new DeviceConnFactoryManager.Build().setId(id)
                    //设置连接的蓝牙mac地址
                    .setMacAddress(bluetoothAddr).build();
            //打开端口
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();

        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            LogUtils.e(action);
            switch (action) {
                //蓝牙连接断开广播
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    LogUtils.i(-1);
                    if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null) {
                        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].closePort();
                    }
                    break;
                case DeviceConnFactoryManager.ACTION_CONN_STATE:
                    int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
                    int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
                    switch (state) {
                        case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
                            if (id == deviceId) {
                                mTextView.setText(UIUtils.getString(R.string.str_conn_state_disconnect));
                                print_state = 2;
//                                isPrint = false;
                            }
                            break;
                        //连接中...
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTING:
                            mTextView.setText(UIUtils.getString(R.string.connecting));
                            print_state = 0;
                            break;
                        //连接成功
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
                            SharedPreferences.Editor sharedPreferences = UIUtils.getSharedPreferences("blueTooth").edit();
                            sharedPreferences.putString("deviceName", deviceName);
                            sharedPreferences.putString("bluetoothAddr", bluetoothAddr);
                            sharedPreferences.apply();
                            mTextView.setText(deviceName);
                            print_state = 1;
                            EventBus.getDefault().post(new PrintStatus(print_state));
                            break;
                        //连接失败
                        case CONN_STATE_FAILED:
                            mTextView.setText(UIUtils.getString(R.string.str_conn_state_disconnect));
                            print_state = 2;
                            //清楚缓存的址
                            if (!TextUtils.isEmpty(bluetoothAddr)) {
                                SharedPreferences sharedPreferences1 = UIUtils.getSharedPreferences("blueTooth");
                                if (sharedPreferences1.getString("bluetoothAddr", "").equals(bluetoothAddr)) {
                                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                                    editor.clear();
                                    editor.apply();
                                }
                            }
                            EventBus.getDefault().post(new PrintStatus(print_state));
                            break;
                        case PRINT_STATE_ERR://打印出错
//                            String msg = intent.getStringExtra("msg");
//                            if (!TextUtils.isEmpty(msg)) {
//                                mTextView.setText(msg);
//                            }
                            //移除已经打印过的数据
//                            if (list.size() > 0) list.remove(list.size() - 1);
                            //检查是否还有未打印数据
                            if (isPrintDone()) {
                                //发送完成打印事件
//                                EventBus.getDefault().post(new PrintComplete());
                            } else {
//                                DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].btnPrinterState(false);
                            }
                            break;
                        //打印机状态正常,可以打印
                        case PRINT_STATE_SUC:
//                            mTextView.setText(deviceName);
//                            sendLabelWithResponse();
                            break;
                        default:
                            break;
                    }
                    break;
                //继续打印
                case ACTION_QUERY_PRINTER_STATE:
                    //刚打印结束
//                    if (isPrint) {
//                        isPrint = false;
//                        int last = list.size() - 1;
//
//                        //发送结束打印事件
//                        EventBus.getDefault().post(new PrintEnd(list.get(last).getQrCodeId()));
//                        //移除已经打印过的数据
//                        list.remove(last);
//                    }

                    if (!isPrintDone()) {
                        if (!deviceName.contains("Printer")) {
                            sendLabelWithResponse();
                        }
                    } else {
                        isPrint = false;
                        EventBus.getDefault().post(new PrintComplete(sb.substring(0,sb.length()-1)));
                    }
                    break;
            }
        }
    };


//    private boolean continuePrint = false;

}
