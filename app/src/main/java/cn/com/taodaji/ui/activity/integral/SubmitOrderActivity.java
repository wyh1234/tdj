package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.ui.activity.integral.adapter.FiltrateServiceAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopCartAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.SubmitOrderAdapter;
import cn.com.taodaji.ui.activity.integral.popuwindow.FriendlyPopuWindow;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.extend.MyRecyclerViews;
import tools.statusbar.Eyes;

public class SubmitOrderActivity extends DataBaseActivity implements FriendlyPopuWindow.FriendlyPopuWindowListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.shop_order_list)
    MyRecyclerViews shop_order_list;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_address_info)
    TextView tv_address_info;
    @BindView(R.id.tv_ps_address)
    TextView tv_ps_address;
    @BindView(R.id.iv_ps)
    ImageView iv_ps;
    @BindView(R.id.iv_zt)
    ImageView iv_zt;
    @BindView(R.id.tv_moeny)
    TextView tv_moeny;
    @BindView(R.id.tv_status)
    TextView tv_status;

    @BindView(R.id.tv_integral)
    TextView tv_integral;
    @BindView(R.id.tv_total)
    TextView tv_total;
    private List<IntegralShopCart.DataBean> list;
    private SubmitOrderAdapter submitOrderAdapter;
    private AddressInfo.DataBean body;
    private int distributionType=1;
    private FriendlyPopuWindow friendlyPopuWindow;

    public int getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(int distributionType) {
        this.distributionType = distributionType;
    }

    public AddressInfo.DataBean getBody() {
        return body;
    }

    public void setBody(AddressInfo.DataBean body) {
        this.body = body;
    }

    @OnClick({R.id.rl_address,R.id.btn,R.id.btn_back,R.id.iv_ps,R.id.iv_zt})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_address:
           /*     Intent intent=new Intent(this,RedactAddressActivity.class);
//                intent.putExtra("addinfo",getBody());
                startActivity(intent);*/

                break;
            case R.id.btn:
                if (getBody()==null){
                    UIUtils.showToastSafe("请选择收货地址");
                    return;
                }
                if (Double.parseDouble(getIntent().getStringExtra("money"))>0){
                    order_create();
                }else {
                    if (friendlyPopuWindow!=null){
                        if (friendlyPopuWindow.isShowing()){
                            return;
                        }

                    }
                    friendlyPopuWindow = new FriendlyPopuWindow(this);
                    friendlyPopuWindow.setFriendlyPopuWindowListener(this);
                    friendlyPopuWindow.setDismissWhenTouchOutside(false);
                    friendlyPopuWindow.setInterceptTouchEvent(false);
                    friendlyPopuWindow.setPopupWindowFullScreen(true);//铺满
                    friendlyPopuWindow.showPopupWindow();
                }





                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_ps:
                iv_zt.setSelected(false);
                iv_ps.setSelected(true);
                setDistributionType(1);
                break;
            case R.id.iv_zt:
                setDistributionType(0);
                iv_zt.setSelected(true);
                iv_ps.setSelected(false);
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.submit_order_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        tv_title.setText("确认订单");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        list= (List<IntegralShopCart.DataBean>) getIntent().getSerializableExtra("item");
        LogUtils.i(list);
        ScrollLinearLayoutManager layoutManager=   new ScrollLinearLayoutManager(this, 1);
        shop_order_list.setLayoutManager(layoutManager);
        layoutManager.setScrollEnable(false);
        submitOrderAdapter=new SubmitOrderAdapter(this,list);
        shop_order_list.setAdapter(submitOrderAdapter);
        tv_moeny.setText("￥"+getIntent().getStringExtra("money")+"元");
        tv_integral.setText(getIntent().getStringExtra("intergral")+"积分");
        tv_total.setText(getIntent().getStringExtra("money")+"元+"+getIntent().getStringExtra("intergral")+"积分");
        iv_ps.setSelected(true);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getDefaultAddress(map, new RequestCallback<AddressInfo>(this) {
            @Override
            public void onSuc(AddressInfo body) {
                ShowLoadingDialog.close();
                if (body.getData()!=null){
                    setBody(body.getData());
                    setAddressInfo(body.getData());
                }else {
                    tv_address.setText("请选择收货地址");
                }
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                    UIUtils.showToastSafe(msg);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get_AddressInfo(AddressInfo event) {
            setBody(event.getData());

            setAddressInfo(event.getData());



    }

    public void setAddressInfo(AddressInfo.DataBean body){
        if (body!=null){
            tv_address.setText(body.getDetailAddress());
            tv_ps_address.setText("配送地址："+body.getDetailAddress());
            tv_address_info.setText(body.getRecevingPersion()+"\t\t\t\t"+body.getRecevingMobile());
                if (body.getIsDefault()==1){
                    tv_status.setVisibility(View.VISIBLE);
                    tv_status.setText("默认");
                }else if (body.getIsDefault()==2){
                    tv_status.setVisibility(View.VISIBLE);
                    tv_status.setText("家");
                }else if (body.getIsDefault()==3){
                    tv_status.setVisibility(View.VISIBLE);
                    tv_status.setText("公司");
                }else {
                    tv_status.setVisibility(View.GONE);
                }
        }else {
            tv_address.setText("请选择收货地址");
            tv_ps_address.setText("");
            tv_address_info.setText("");
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("默认");
        }


    }

    public void order_create(){
        ShowLoadingDialog.showLoadingDialog(this);
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<list.size();i++){
            if (list.get(i).isB()){
                builder.append(list.get(i).getGoodsId()+",");
            }

        }
        builder.deleteCharAt(builder.length() - 1);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("orderAddressId", getBody().getId());
        map.put("goodsId", builder.toString());
        map.put("orderType", "1");
        map.put("distributionType", getDistributionType());

        getIntegralRequestPresenter().order_create(map, new RequestCallback<IntegralOrder>(this) {
            @Override
            public void onSuc(IntegralOrder body) {
                ShowLoadingDialog.close();
                EventBus.getDefault().post(new IntegralShopCart());
                if (Double.parseDouble(body.getData().getTotalFee())==0){
                    integral_pay(body.getData().getOrderId());



                }else {
                    Intent intent1=new Intent(SubmitOrderActivity.this,IntegralShopPayActivity.class);
                    intent1.putExtra("IntegralOrder",body.getData());
                    startActivity(intent1);
                    finish();
                }



            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    public void integral_pay(String orderId){
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", orderId);
        getIntegralRequestPresenter().integral_pay(map, new RequestCallback<IntegralOrder>(this) {
            @Override
            public void onSuc(IntegralOrder body) {
                    Intent intent1=new Intent(SubmitOrderActivity.this,IntegralShopPaySuccessActivity.class);
                    intent1.putExtra("orderId",orderId);
                    intent1.putExtra("integral",getIntent().getStringExtra("intergral"));
                    startActivity(intent1);
                    finish();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                UIUtils.showToastSafe(msg);

            }
        });


    }

    @Override
    public void ok() {
        order_create();
    }
}
