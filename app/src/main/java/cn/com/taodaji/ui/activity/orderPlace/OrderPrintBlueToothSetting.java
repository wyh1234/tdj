package cn.com.taodaji.ui.activity.orderPlace;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.base.viewModel.adapter.OnItemClickListener;

import java.util.Set;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimplePrintBlueAdapter;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;

import com.base.listener.OnPermissionListener;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

public class OrderPrintBlueToothSetting extends SimpleToolbarActivity implements OnItemClickListener, OnPermissionListener {
    private SimplePrintBlueAdapter adapter1;
    private SimplePrintBlueAdapter adapter2;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("设置蓝牙打印机");
    }

    private View mainView;
    private View progressBar;

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutView(this, R.layout.activity_order_print_sysem_seting);
        body_other.addView(mainView);
        progressBar = ViewUtils.findViewById(mainView, R.id.progressBar);
        RecyclerView recyclerView1 = ViewUtils.findViewById(mainView, R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.addItemDecoration(new SpacesItemDecoration(UIUtils.dip2px(2)));
        RecyclerView recyclerView2 = ViewUtils.findViewById(mainView, R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.addItemDecoration(new SpacesItemDecoration(UIUtils.dip2px(2)));
        adapter1 = new SimplePrintBlueAdapter();
        adapter1.setItemClickListener(this);
        adapter2 = new SimplePrintBlueAdapter();
        adapter2.setItemClickListener(this);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);


    }

    @Override
    public void permissionSuccess(int requestCode) {
        discoveryDevice();//扫描
    }

    @Override
    public void permissionFail(int requestCode) {
//        finish();
    }

    @Override
    protected void initData() {
        getDeviceList();
        addPermissionListen(1035, this);
        permissionRequest(1035, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object data) {
        if (onclickType == 0) {
            // Cancel discovery because it's costly and we're about to connect
//            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].closePort();
            mBluetoothAdapter.cancelDiscovery();
            BluetoothDevice bean = (BluetoothDevice) data;
            finish();
            EventBus.getDefault().post(bean);
            return true;
        }
        return false;
    }

    // changes the title when discovery is finished
    private final BroadcastReceiver mFindBlueToothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed
                // already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    String ddd = device.getName();
                    if (!TextUtils.isEmpty(device.getName())) adapter2.loadMore(device);
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressBar.setVisibility(View.GONE);
                if (adapter2.getRealCount() == 0) {
//                    UIUtils.showToastSafesShort("未扫描到蓝牙设备");
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        unregisterReceiver(mFindBlueToothReceiver);
        // Make sure we're not doing discovery anymore
        super.onDestroy();
    }

    protected void getDeviceList() {

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        // Get the local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (!TextUtils.isEmpty(device.getName())) adapter1.loadMore(device);
            }
        } else UIUtils.showToastSafesShort("没有配对的蓝牙设备");
    }

    private void discoveryDevice() {

        if (mBluetoothAdapter == null) return;
        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    public static void startActivity(Context cont) {
        Intent intent = new Intent(cont, OrderPrintBlueToothSetting.class);
        cont.startActivity(intent);
    }


}
