package cn.com.taodaji.ui.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressUpdate;
import cn.com.taodaji.model.entity.PickupServiceItem;
import cn.com.taodaji.model.entity.ReceiveWarehouseRecommendList;
import cn.com.taodaji.model.entity.RefreshPickupFee;
import cn.com.taodaji.model.event.PickupEvent;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.ppw.SwitchPickupServicePopupWindow;
import cn.com.taodaji.viewModel.adapter.PickUpServiceAdapter;
import tools.activity.SimpleToolbarActivity;

public class PickupServiceActivity extends SimpleToolbarActivity {

    private View mainView;
    private TextView pickupExpenseTips,restFee;
    private RecyclerView recyclerView;
    private List<PickupServiceItem> itemList = new ArrayList<>();
    private PickUpServiceAdapter adapter;
    private SwitchPickupServicePopupWindow popupWindow;
    private LinearLayout myPickupPackage;
    private ImageView ads,rechargeTip;
    private int temp;
    private BigDecimal fee;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("接货仓服务");
        right_icon_text.setText("常见问题");
        right_icon.setImageResource(R.mipmap.ic_question_mark_white);
        right_icon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickupServiceActivity.this, UsuallyProblemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPickupFee();
    }

    protected void initMainView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_pickup_service);
        body_scroll.addView(mainView);
        ads = mainView.findViewById(R.id.iv_pickup_ad);
        ads.setFocusable(true);
        ads.setFocusableInTouchMode(true);
        ads.requestFocus();
        restFee = mainView.findViewById(R.id.tv_rest_fee);
        rechargeTip = mainView.findViewById(R.id.iv_recharge_tip);
        myPickupPackage = mainView.findViewById(R.id.ll_my_pickupPackage);
        myPickupPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickupServiceActivity.this,PickupFeeActivity.class);
                intent.putExtra("fee",fee+"");
                intent.putExtra("balance",PublicCache.loginSupplier.getAutomaticRenewalFee());
                startActivity(intent);
            }
        });
        pickupExpenseTips = mainView.findViewById(R.id.tv_pickup_cost_tip);
        Spanned spanned = Html.fromHtml("当天开通，后天生效。例：7月15日开通的接货仓服务，于7月17日可直接将7月16日的订单送至接货仓，享受该服务。接货费将从"+"<font color='#2898eb'>"+"\"我的淘钱包\""+"</font>"+"中扣除。当前余额少于200元时，服务将自动关闭。再次使用该服务，需要重新开通。");
        pickupExpenseTips.setText(spanned);
        pickupExpenseTips.setVisibility(View.GONE);
        recyclerView = mainView.findViewById(R.id.rv_pickup_recommend);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PickUpServiceAdapter(itemList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId()){
                    case R.id.btn_turn_on_service:
                        switch (itemList.get(position).getStatus()){
                            case 0:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",0,position);
                                break;
                            case 1:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",1,position);
                                break;
                            case 2:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",2,position);
                                break;
                            case 3:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",2,position);
                                break;
                            case 4:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),itemList.get(position).getRejectTime(),4,position);
                                break;
                            case 5:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getRejectTime(),"",6,position);
                                break;
                            case 6:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getRejectTime(),"",6,position);
                                break;
                            case 7:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),itemList.get(position).getRejectTime(),7,position);
                                break;
                            case 8:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",9,position);
                                break;
                            case 9:
                                popupWindow = new SwitchPickupServicePopupWindow(PickupServiceActivity.this,itemList.get(position).getTitle(),itemList.get(position).getRate(),itemList.get(position).getGoods(),itemList.get(position).getTime(),itemList.get(position).getApplyTime(),"",9,position);
                                break;
                                default:break;
                        }
                        popupWindow.setDismissWhenTouchOutside(false);
                        popupWindow.setInterceptTouchEvent(false);
                        popupWindow.setPopupWindowFullScreen(true);
                        popupWindow.showPopupWindow();
                        break;
                    case R.id.iv_navi_icon:
                        if (isHaveGaodeMap()) {
                            openGaoDeMap(PickupServiceActivity.this, PublicCache.loginSupplier.getAddress(),itemList.get(position).getAddress(),itemList.get(position).getLat().toString(),itemList.get(position).getLon().toString());
                        }else {
                            UIUtils.showToastSafe("高德地图未安装");
                        }
                        break;
                    case R.id.tv_item_call:
                        if (!UserNameUtill.isPhoneNO(itemList.get(position).getPhone())){
                            SystemUtils.callPhone(PickupServiceActivity.this,itemList.get(position).getPhone());
                        }else {
                            UIUtils.showToastSafe("电话号码不正确！");
                        }
                        break;
                        default:break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public void refreshPickupFee(){
        RequestPresenter2.getInstance().refreshPickupFee(PublicCache.loginSupplier.getStore(), new RequestCallback<RefreshPickupFee>() {
            @Override
            protected void onSuc(RefreshPickupFee body) {
                restFee.setText("剩余"+body.getData().getReceive_money()+"元");
                fee = body.getData().getReceive_money();
                if (body.getData().getReceive_money().intValue()<200){
                    rechargeTip.setVisibility(View.VISIBLE);
                }else {
                    rechargeTip.setVisibility(View.INVISIBLE);
                }
                PublicCache.loginSupplier.setAutomaticRenewalFee(body.getData().getAutomatic_renewal_fee().intValue());
                PublicCache.loginSupplier.setWithdrawalMoney(body.getData().getWithdrawal_money().toString());
                PublicCache.loginSupplier.setIsAutomaticRenewal(body.getData().getIs_automatic_renewal());
                PublicCache.loginSupplier.setReceiveMoney(body.getData().getReceive_money());
                PublicCache.loginSupplier.update();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void initData(){
        itemList.clear();
        getRequestPresenter().getReceiveWarehouseRecommendList(PublicCache.loginSupplier.getStore(), new RequestCallback<ReceiveWarehouseRecommendList>() {
            @Override
            protected void onSuc(ReceiveWarehouseRecommendList body) {
                if (body.getErr()==0){
                    temp = body.getData().getIsStoreReceive();
                    if (body.getData().getReceiveMoney().intValue()<200){
                        rechargeTip.setVisibility(View.VISIBLE);
                    }else {
                        rechargeTip.setVisibility(View.INVISIBLE);
                    }
                    if (body.getData().getList()!=null){
                        for (ReceiveWarehouseRecommendList.DataBean.ListBean bean:body.getData().getList()){
                            PickupServiceItem item = new PickupServiceItem();
                            item.setTitle("推荐接货仓："+bean.getWarehouseNameAbbr());
                            item.setFlag(bean.getReceiveType());
                            item.setStatus(bean.getIsOpen());
                            item.setAddress("接货地点："+bean.getAddress());
                            item.setGoods(bean.getShortName()+"("+bean.getStationName()+")");
                            item.setFeeByDay(bean.getFeeByDay());
                            item.setRate(bean.getFeePercent());
                            item.setPhone("负责人电话："+bean.getPhone());
                            item.setOpenDate(bean.getOpenTime());
                            item.setStationId(bean.getStationId());
                            item.setTime("接货时间："+bean.getReceiveTimeStart()+"—"+bean.getReceiveTimeEnd());
                            item.setReceiveWarehouseId(bean.getReceiveWarehouseId());
                            item.setLat(bean.getLat());
                            item.setLon(bean.getLon());
                            item.setIsApplyClose(bean.getIsApplyClose());
                            item.setApplyTime(bean.getApplyTime());
                            item.setRejectTime(bean.getRejectTime());
                            itemList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    //接收开通或者关闭接货仓
    @Subscribe
    public void onEvent(PickupEvent event) {
        PickupServiceItem item = itemList.get(event.getPosition());
        if (event.getFlag()==0){//开通
            getRequestPresenter().openReceiveWarehouse(PublicCache.loginSupplier.getStore(), item.getStationId(),item.getReceiveWarehouseId(), 1, temp, event.getType(),new RequestCallback<AddressUpdate>() {
                @Override
                protected void onSuc(AddressUpdate body) {
                    initData();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    UIUtils.showToastSafe(msg);
                }
            });
        }else {
            getRequestPresenter().closeReceiveWarehouse(PublicCache.loginSupplier.getStore(), item.getStationId(), item.getReceiveWarehouseId(),event.getType(),1, new RequestCallback<AddressUpdate>() {
                @Override
                protected void onSuc(AddressUpdate body) {
                    initData();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    UIUtils.showToastSafe(msg);
                }
            });
        }
    }
}
