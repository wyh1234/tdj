package cn.com.taodaji.ui.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.base.retrofit.RequestCallback;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DeliveryWarehouseItem;
import cn.com.taodaji.model.entity.ReceiveList;
import cn.com.taodaji.model.entity.ReceiveWarehouseItem;
import cn.com.taodaji.model.entity.StationBean;
import cn.com.taodaji.viewModel.adapter.DeliveryWarehouseAdapter;
import cn.com.taodaji.viewModel.adapter.ReceiveWarehouseAdapter;
import tools.activity.SimpleToolbarActivity;

public class SelectDeliveryWarehouseActivity extends SimpleToolbarActivity {
    private RecyclerView recyclerView,receiveWarehouse;
    private RelativeLayout empty;
    private DeliveryWarehouseAdapter adapter;
    private ReceiveWarehouseAdapter receiveWarehouseAdapter;
    private List<DeliveryWarehouseItem> itemList = new ArrayList<>();
    private List<ReceiveWarehouseItem> receiveWarehouseItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("选择配送仓库");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_select_delivery_warehouse);
        body_other.addView(mainView);
        adapter = new DeliveryWarehouseAdapter(itemList,this);
        receiveWarehouseAdapter = new ReceiveWarehouseAdapter(receiveWarehouseItems,this);
        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_delivery_warehouse);
        receiveWarehouse = ViewUtils.findViewById(mainView,R.id.rv_receive_warehouse);
        empty = ViewUtils.findViewById(mainView,R.id.ll_list_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()){
                    case R.id.btn_start_distribution:
                        Intent intent = new Intent(SelectDeliveryWarehouseActivity.this,TodayDeliverGoodsOrderManageActivity.class);
                        intent.putExtra("orderId1",getIntent().getIntExtra("orderId1",0));
                        intent.putExtra("stationId",itemList.get(position).getStationId());
                        intent.putExtra("stationName",itemList.get(position).getStationName()+" (简称："+itemList.get(position).getShortName()+"仓)");
                        intent.putExtra("carTime","早班车"+itemList.get(position).getEarlyBus()+"普通车"+itemList.get(position).getNormalBus()+"前送达配送中心。");
                        startActivity(intent);
                        break;
                    case R.id.iv_navi_icon:
                        if (isHaveGaodeMap())
                        {
                            openGaoDeMap(SelectDeliveryWarehouseActivity.this, PublicCache.loginSupplier.getAddress(),itemList.get(position).getAddress(),itemList.get(position).getLat(),itemList.get(position).getLon());
                        }else {
                            UIUtils.showToastSafe("高德地图未安装");
                        }
                        break;
                }
            }
        });

        receiveWarehouse.setLayoutManager(new LinearLayoutManager(this));
        receiveWarehouse.setHasFixedSize(true);
        receiveWarehouse.setAdapter(receiveWarehouseAdapter);
        receiveWarehouseAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()){
                    case R.id.btn_start_distribution:
                        Intent intent = new Intent(SelectDeliveryWarehouseActivity.this,TodayDeliverGoodsOrderManageActivity.class);
                        intent.putExtra("orderId1",getIntent().getIntExtra("orderId1",0));
                        intent.putExtra("stationId",receiveWarehouseItems.get(position).getStationId());
                        intent.putExtra("rwId",receiveWarehouseItems.get(position).getReceiveWarehouseId());
                        intent.putExtra("stationName",receiveWarehouseItems.get(position).getWarehouseName()+" (简称："+receiveWarehouseItems.get(position).getWarehouseShortName()+"仓)");
                        intent.putExtra("carTime","早班车"+receiveWarehouseItems.get(position).getReceiveTimeStart()+"普通车"+receiveWarehouseItems.get(position).getReceiveTimeEnd()+"前送达配送中心。");
                        startActivity(intent);
                        break;
                    case R.id.iv_navi_icon:
                        if (isHaveGaodeMap())
                        {
                            openGaoDeMap(SelectDeliveryWarehouseActivity.this, PublicCache.loginSupplier.getAddress(),itemList.get(position).getAddress(),itemList.get(position).getLat(),itemList.get(position).getLon());
                        }else {
                            UIUtils.showToastSafe("高德地图未安装");
                        }
                        break;
                    case R.id.ll_call_pickup:
                        if (UserNameUtill.isPhoneNO(receiveWarehouseItems.get(position).getPhone())){
                            SystemUtils.callPhone(SelectDeliveryWarehouseActivity.this,receiveWarehouseItems.get(position).getPhone());
                        }else {
                            UIUtils.showToastSafe("电话号码不正确！");
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        receiveWarehouseItems.clear();
        getRequestPresenter().getReceiveList(PublicCache.loginSupplier.getStore(), new RequestCallback<ReceiveList>() {
            @Override
            protected void onSuc(ReceiveList body) {
                if (body.getData().getList().size()!=0){
                    for (ReceiveList.DataBean.ListBean bean : body.getData().getList()){
                        ReceiveWarehouseItem item = new ReceiveWarehouseItem();
                        item.setTotalCount(bean.getTotalCount());
                        item.setAddress(bean.getWareHouse().getAddress());
                        item.setLat(bean.getWareHouse().getLat());
                        item.setLng(bean.getWareHouse().getLng());
                        item.setPhone(bean.getWareHouse().getPhone());
                        item.setReceiveTimeEnd(bean.getWareHouse().getReceiveTimeEnd());
                        item.setReceiveTimeStart(bean.getWareHouse().getReceiveTimeStart());
                        item.setReceiveWarehouseId(bean.getWareHouse().getReceiveWarehouseId());
                        item.setShortName(bean.getWareHouse().getShortName());
                        item.setStationId(bean.getWareHouse().getStationId());
                        item.setStationName(bean.getWareHouse().getStationName());
                        item.setWarehouseName(bean.getWareHouse().getWarehouseName());
                        item.setWarehouseShortName(bean.getWareHouse().getWarehouseShortName());
                        receiveWarehouseItems.add(item);
                    }
                    empty.setVisibility(View.GONE);
                    receiveWarehouseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });

        itemList.clear();
        getRequestPresenter().getStationList(getIntent().getIntExtra("orderId1", 0), new RequestCallback<StationBean>() {
            @Override
            protected void onSuc(StationBean body) {
                if (body.getData().getList().size()!=0) {
                    for (StationBean.DataBean.ListBean bean : body.getData().getList()) {
                        DeliveryWarehouseItem item = new DeliveryWarehouseItem();
                        item.setAddress(bean.getAddress());
                        item.setEarlyBus(bean.getEarlyBus());
                        item.setNormalBus(bean.getNormalBus());
                        item.setLat(bean.getLat());
                        item.setLon(bean.getLon());
                        item.setShortName(bean.getShortName());
                        item.setStationId(bean.getStationId());
                        item.setTotalCount(bean.getTotalCount());
                        item.setTotalEarlyBusCount(bean.getTotalEarlyBusCount());
                        item.setTotalNormalBusCount(bean.getTotalNormalBusCount());
                        item.setStationName(bean.getStationName());
                        itemList.add(item);
                    }
                    empty.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    /**
     * 是否安装高德地图
     *
     * @return
     */
    public boolean isHaveGaodeMap() {
        try {
            if (!new File("/data/data/" + "com.autonavi.minimap").exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * openGaoDeMap void 调用高德地图apk
     * http://lbs.amap.com/api/uri-api/guide/travel/route
     */
    public void openGaoDeMap(Context context, String dqAddress, String gotoAddress, String gotoLatitude, String gotoLongitude) {
        try {
            if (context != null && !TextUtils.isEmpty(gotoAddress) && !TextUtils.isEmpty(gotoLatitude) && !TextUtils.isEmpty(gotoLongitude)) {
                //double[] gotoLang=AMAPUtils.getInstance().bdToGaoDe(Double.parseDouble(gotoLatitude),Double.parseDouble(gotoLongitude));
                //gotoLatitude=String.valueOf(gotoLang[0]);gotoLongitude=String.valueOf(gotoLang[1]);
                String url = "androidamap://navi?sourceApplication=" + dqAddress + "&poiname=" + gotoAddress + "&lat=" + gotoLatitude + "&lon=" + gotoLongitude + "&dev=0&style=1";
                Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(url));
                intent.setPackage("com.autonavi.minimap");
                context.startActivity(intent);
            }
        } catch (Exception e) {

        }
    }
}
