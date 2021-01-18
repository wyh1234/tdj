package cn.com.taodaji.ui.activity.advertisement;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AvdOrder;
import cn.com.taodaji.model.entity.CreateTfAdvManage;
import cn.com.taodaji.model.entity.SelGoods;
import cn.com.taodaji.model.entity.SupplierAnnalFeePayInfo;
import cn.com.taodaji.model.entity.SupplyMoney;
import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.ui.activity.advertisement.adapter.AdvOrderAdapter;
import cn.com.taodaji.ui.activity.advertisement.popuwindow.AdvOrderPayBackPopuWindow;
import cn.com.taodaji.ui.activity.advertisement.popuwindow.DelAdvPopuWindow;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.myself.MyEquitiesActivity;
import cn.com.taodaji.ui.pay.YearMoneyPayActivity;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.extend.MyRecyclerViews;
import tools.jni.JniMethod;
import tools.statusbar.Eyes;

public class AdvOrderCommitActivity extends DataBaseActivity implements AdvOrderAdapter.OnItemClickListener,AdvOrderPayBackPopuWindow.AdvOrderPayBackPopuWindowListener{
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.recyclerview)
    MyRecyclerViews myRecyclerViews;
    @BindView(R.id.yu_e_tv)
    TextView yu_e_tv;
    @BindView(R.id.yu_e)
    RelativeLayout yu_e;
    @BindView(R.id.alipay)
    RelativeLayout alipay;
    @BindView(R.id.wxpay)
    RelativeLayout wxpay;
    @BindView(R.id.iv_y_e)
    ImageView iv_y_e;
    @BindView(R.id.iv_alipay)
    ImageView iv_alipay;
    @BindView(R.id.iv_wxpay)
    ImageView iv_wxpay;
    private AdvOrderAdapter advOrderAdapter;
    private AdvOrderPayBackPopuWindow advOrderPayBackPopuWindow;
    private List<CreateTfAdvManage> list;
    private AvdOrder avdOrder;
    private int payType=0;
    private BigDecimal withdrawalMoney;
    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @OnClick({R.id.btn_back,R.id.tv_pay,R.id.yu_e,R.id.alipay,R.id.wxpay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if (advOrderPayBackPopuWindow!=null){
                    if (advOrderPayBackPopuWindow.isShowing()){
                        return;
                    }
                    advOrderPayBackPopuWindow.showPopupWindow();
                }else {

                    advOrderPayBackPopuWindow = new AdvOrderPayBackPopuWindow(this);
                    advOrderPayBackPopuWindow.setDismissWhenTouchOutside(false);
                    advOrderPayBackPopuWindow.setInterceptTouchEvent(false);
                    advOrderPayBackPopuWindow.setPopupWindowFullScreen(true);//铺满
                    advOrderPayBackPopuWindow.setAdvOrderPayBackWindowListener(this);
                    advOrderPayBackPopuWindow.showPopupWindow();
                }
                break;
            case R.id.tv_pay:
                if (getPayType()==0){
                    UIUtils.showToastSafe("请选择支付方式");
                    return;
                }
                if (iv_y_e.isSelected()){
                    if (withdrawalMoney!=null){
                        if (withdrawalMoney.compareTo(avdOrder.getData().getPayCount())==-1){
                            Toast.makeText(this,"账户余额不足",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                }
                getData();


                break;
            case R.id.wxpay:
                iv_y_e.setSelected(false);
                iv_alipay.setSelected(false);
                iv_wxpay.setSelected(true);
                setPayType(2);
                break;
            case R.id.alipay:
                iv_y_e.setSelected(false);
                iv_alipay.setSelected(true);
                iv_wxpay.setSelected(false);
                setPayType(1);
                break;

            case R.id.yu_e:
                iv_y_e.setSelected(true);
                iv_alipay.setSelected(false);
                iv_wxpay.setSelected(false);
                setPayType(3);
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.adv_order_commit_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("支付订单");
        ScrollLinearLayoutManager layoutManager = new ScrollLinearLayoutManager(this, 1);
        layoutManager.setScrollEnable(false);
       list = (ArrayList<CreateTfAdvManage>) getIntent().getSerializableExtra("list");
        LogUtils.e(list);
        myRecyclerViews.setLayoutManager(layoutManager);
        advOrderAdapter = new AdvOrderAdapter(this, list);
        advOrderAdapter.setOnItemClickListener(this);
        myRecyclerViews.setAdapter(advOrderAdapter);;
        avdOrder= (AvdOrder) getIntent().getSerializableExtra("avdOrder");
        String strMsg = "支付<font color=\"#FD0404\">"+"￥"+avdOrder.getData().getPayCount()+"</font>"+"元";
        tv_money.setText(Html.fromHtml(strMsg));
        getSupplyMoney();
    }

    public void getSupplyMoney(){
        getRequestPresenter().getSupplyMoney(PublicCache.loginSupplier.getEntityId(), new ResultInfoCallback<SupplyMoney>(this) {
            @Override
            public void onSuccess(SupplyMoney event) {
                yu_e_tv.setText("余额抵扣（可用余额:"+event.getWithdrawalMoney().toString() + "元)");
                withdrawalMoney=event.getWithdrawalMoney();
            }

            @Override
            public void onFailed(int supplyMoneyResultInfo, String msg) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (list.get(position).isF()){
            list.get(position).setF(false);
        }else {
            list.get(position).setF(true);
        }
        advOrderAdapter.notifyDataSetChanged();

    }

    public void getData(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("type",13);
        map.put("storeId",PublicCache.loginSupplier.getStore());
        map.put("orderId",avdOrder.getData().getId());
        map.put("payType",getPayType());
        map.put("platform",0);
        map.put("payCount",avdOrder.getData().getPayCount());
        LogUtils.e(map);
        getRequestPresenter().adv_fee_pay(map, new RequestCallback<SupplierAnnalFeePayInfo>() {
            @Override
            protected void onSuc(SupplierAnnalFeePayInfo body) {
                ShowLoadingDialog.close();
                if (getPayType()==2){
                    WXPay wxpay=new WXPay();
                    wxpay.setAppid(body.getData().getAppid());
                    wxpay.setNoncestr(body.getData().getNoncestr());
                    wxpay.setPackageX(body.getData().getPackageX());
                    wxpay.setPartnerid(body.getData().getPartnerid());
                    wxpay.setPrepayid(body.getData().getPrepayid());
                    wxpay.setSign(body.getData().getSign());
                    wxpay.setTimestamp(body.getData().getTimestamp());
                    LogUtils.i(wxpay);
                        new Wxpay(AdvOrderCommitActivity.this).pay(wxpay);
                }else if (getPayType()==1){
                    new Alipay(getBaseActivity(), "广告收费-淘大集供应链", "收费",
                            avdOrder.getData().getPayCount().toString(), body.getData().getOutTradeNo(), PublicCache.getROOT_URL().get(0) + JniMethod.supplierAnnalFee_alipay()).newPay();


                }else {
                    Intent intent=new Intent(AdvOrderCommitActivity.this,AdvOrderPayDetailsActivity.class);
                    intent.putExtra("avdOrder",avdOrder);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }

        });

    }
    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        LogUtils.i("event");
        Intent intent=new Intent(AdvOrderCommitActivity.this,AdvOrderPayDetailsActivity.class);
        intent.putExtra("avdOrder",avdOrder);
        startActivity(intent);
        finish();
    }
    @Override
    public void onCancel() {
        advOrderPayBackPopuWindow.dismiss();
        finish();
    }

    @Override
    public void onOk() {
        advOrderPayBackPopuWindow.dismiss();
        if (getPayType()!=0){
            getData();
        }


    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
