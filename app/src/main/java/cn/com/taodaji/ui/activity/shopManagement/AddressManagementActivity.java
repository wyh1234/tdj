package cn.com.taodaji.ui.activity.shopManagement;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CommunityAddress;
import cn.com.taodaji.model.entity.DeliverFee;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.ShopAddressActivity;
import cn.com.taodaji.ui.activity.tdjc.SelectPickUpActivity;
import io.reactivex.functions.Consumer;
import tools.activity.SimpleToolbarActivity;
import tools.location.LocationUtils;

public class AddressManagementActivity extends SimpleToolbarActivity implements View.OnClickListener{
    private View mainView;
    private RelativeLayout relativeLayout;
    private RelativeLayout rl_select_pick;
    private ImageView iv_right;
//    private TextView currentShopTitle,currentShopAddress,currentExpired,tv_regionCode;
    private CommunityAddress body;
    private TextView tv_name,tv_address,tv_shop_title,tv_jl,tv_shop_address,tv_masterPhone;
    private String address,currentCommunityName;
    private String distance;
    private DeliverFee mbody;
    private RxPermissions rxPermissions;
    public CommunityAddress getBody() {
        return body;
    }

    public void setBody(CommunityAddress body) {
        this.body = body;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_current_shop:
                if (getIntent().getStringExtra("EditAddress")!=null){
                    Intent intent1 = new Intent(this, EditAddressManagementActivity.class);
                    if (mbody!=null){
                        intent1.putExtra("address",mbody.getData().getDeliverInfo().getDeliverAddress());
                        intent1.putExtra("streetNumber",mbody.getData().getDeliverInfo().getStreetNumber());
                        intent1.putExtra("customerName",mbody.getData().getDeliverInfo().getCustomerName());
                        intent1.putExtra("customerTel",mbody.getData().getDeliverInfo().getCustomerTel());
                        intent1.putExtra("deliverScope",getIntent().getIntExtra("deliverScope",0));
                        intent1.putExtra("addressId",getIntent().getIntExtra("addressId",0));

                    }
//                    intent1.putExtra("body",getBody().getData());
                    startActivityForResult(intent1,2);
                }

                break;
            case R.id.rl_select_pick:
                rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean b) throws Exception {
                        if (b){
                            LocationUtils.getInstance().startLocalService("");
                            Intent intent2 = new Intent(AddressManagementActivity.this, SelectPickUpActivity.class);
                            intent2.putExtra("currentCommunityName",currentCommunityName);
                            intent2.putExtra("currentCommunityaddress",address);
                            intent2.putExtra("distance",distance);
                            startActivity(intent2);

                        }

                    }
                });

                break;
            default:break;
        }
    }
    @Override
    protected void initData() {
        super.initData();
        registerEventBus(this);
    }
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("收货地址管理");
        right_onclick_line.setOnClickListener(this);
    }

    @Override
    protected void initMainView() {
        rxPermissions = new RxPermissions(this);
        mainView = getLayoutView(R.layout.activity_address_management);
        body_other.addView(mainView);
//        currentShopTitle = ViewUtils.findViewById(mainView,R.id.tv_current_shop_title);
//        currentShopAddress = ViewUtils.findViewById(mainView,R.id.tv_current_shop_address);
//        tv_regionCode= ViewUtils.findViewById(mainView,R.id.tv_regionCode);
//        currentExpired = ViewUtils.findViewById(mainView,R.id.tv_expired_address);
        tv_name= ViewUtils.findViewById(mainView,R.id.tv_name);
        tv_address = ViewUtils.findViewById(mainView,R.id.tv_address);
        relativeLayout = ViewUtils.findViewById(mainView,R.id.rl_current_shop);
        rl_select_pick= ViewUtils.findViewById(mainView, R.id.rl_select_pick);
        tv_shop_title= ViewUtils.findViewById(mainView, R.id.tv_shop_title);
        tv_masterPhone= ViewUtils.findViewById(mainView, R.id.tv_masterPhone);
        tv_jl= ViewUtils.findViewById(mainView, R.id.tv_jl);
        iv_right=ViewUtils.findViewById(mainView, R.id.iv_right);
        tv_shop_address= ViewUtils.findViewById(mainView, R.id.tv_shop_address);
        rl_select_pick.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        if (getIntent().getStringExtra("EditAddress")!=null){
            iv_right.setVisibility(View.VISIBLE);
        }else {
            iv_right.setVisibility(View.GONE);
        }


        getAddress();
        currentCommunityName();
    }

    private void getAddress(){
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",PublicCache.loginPurchase.getEntityId());
        getRequestPresenter().getDeliverFee(map, new RequestCallback<DeliverFee>() {
            @Override
            protected void onSuc(DeliverFee body) {
                mbody=body;
                tv_name.setText("收货人："+body.getData().getDeliverInfo().getCustomerName()+" "+body.getData().getDeliverInfo().getCustomerTel());
                tv_address.setText("收货地址："+body.getData().getDeliverInfo().getDeliverAddress()+"("+body.getData().getDeliverInfo().getStreetNumber()+")");

            }


        });
    }

    private void initList() {
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",getIntent().getIntExtra("id",-1));
        LogUtils.e(map);
        getRequestPresenter().customerCommunity_find_addresss(map, new RequestCallback<CommunityAddress>() {
            @Override
            protected void onSuc(CommunityAddress body) {
                ShowLoadingDialog.close();
                if (body.getData()!=null){
                    setBody(body);

//                    currentShopTitle.setText(body.getData().getMiddlename()+"("+body.getData().getTelephone()+")");
//                    currentShopAddress.setText(body.getData().getCommunityName()+"("+body.getData().getStreetNumber()+")");
//                    tv_regionCode.setText(body.getData().getRegionNumber());


                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });

    }
    public void currentCommunityName(){
        Map<String,Object> map=new HashMap<>();
        map.put("page",0);
        map.put("ps",20);
        map.put("pn",1);
        map.put("lon",PublicCache.longtitude);
        map.put("lat",PublicCache.latitude);
        map.put("communityName","");
        map.put("customerId",PublicCache.loginPurchase.getEntityId());

        LogUtils.e(map);
        getRequestPresenter().customerCommunity_find( PublicCache.site,map, new RequestCallback<XiaoQuAddressItem>() {
            @Override
            protected void onSuc(XiaoQuAddressItem body) {
                address=body.getData().getDataList().getItems().get(0).getCity()+body.getData().getDataList().getItems().get(0).getArea()+body.getData().getDataList().getItems().get(0).getAddress();
                distance=body.getData().getDataList().getItems().get(0).getDistance()+"";
                currentCommunityName=body.getData().getDataList().getItems().get(0).getCommunityName();
                tv_shop_title.setText("提货点："+body.getData().getDataList().getItems().get(0).getCommunityName());
                tv_shop_address.setText("提货地址："+body.getData().getDataList().getItems().get(0).getCity()+body.getData().getDataList().getItems().get(0).getArea()+body.getData().getDataList().getItems().get(0).getAddress());
                tv_masterPhone.setText("团长："+body.getData().getDataList().getItems().get(0).getMasterPhone());
            }

            @Override
            public void onFailed(int errCode, String msg) {
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            getAddress();

        }

    }


    @Subscribe
    public void updaterCommunityName(XiaoQuAddressItem.DataBean.DataListBean.ItemsBean itemsBean) {//切换提货点刷新数据
        LogUtils.e(itemsBean);
        tv_shop_title.setText("提货点："+itemsBean.getCommunityName());
        tv_shop_address.setText("提货地址："+itemsBean.getAddress());
    }

//    public void onRefresh(){
//
//        getRequestPresenter().customer_refreshInfo(PublicCache.loginPurchase.getEntityId(),0,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<MyselftUpdateP>(this) {
//            @Override
//            public void onSuc(MyselftUpdateP event) {
//                currentCommunityName(event.getData().getCommunityId());
//
//            }
//
//            @Override
//            public void onFailed(int myselftUpdateP, String msg) {
//            }
//        });
//    }

}
