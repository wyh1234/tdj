package cn.com.taodaji.ui.activity.penalty;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplierAnnalFeePayInfo;
import cn.com.taodaji.model.entity.SupplyMoney;
import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.ui.activity.advertisement.AdvOrderCommitActivity;
import cn.com.taodaji.ui.activity.advertisement.AdvOrderPayDetailsActivity;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;
import tools.activity.DataBaseActivity;
import tools.activity.SimpleToolbarActivity;
import tools.jni.JniMethod;
import tools.statusbar.Eyes;

public class PunishPayActivity extends DataBaseActivity implements View.OnClickListener, MessagePopuWindow.MessagePopuWindowListener {
    @BindView(R.id.post_ok)
    Button post_ok;

    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_type)
    TextView tv_type;
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
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.yu_e_tv)
    TextView yu_e_tv;
    private BigDecimal withdrawalMoney;

    private MessagePopuWindow messagePopuWindow;
    private int payType=0;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @OnClick({R.id.btn_back,R.id.post_ok,R.id.yu_e,R.id.alipay,R.id.wxpay})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                if (messagePopuWindow!=null){
                    if (messagePopuWindow.isShowing()){
                        return;
                    }
                    messagePopuWindow.showPopupWindow();
                }else {

                    messagePopuWindow = new MessagePopuWindow(this,"支付失败");
                    messagePopuWindow.setDismissWhenTouchOutside(false);
                    messagePopuWindow.setInterceptTouchEvent(false);
                    messagePopuWindow.setPopupWindowFullScreen(true);//铺满
                    messagePopuWindow.setMessagePopuWindowListener(this);
                    messagePopuWindow.showPopupWindow();
                }
            break;
            case R.id.post_ok:
                if (getPayType()==0){
                    UIUtils.showToastSafe("请选择支付方式");
                    return;
                }
                if (iv_y_e.isSelected()){
                    if (withdrawalMoney!=null){
                        if (withdrawalMoney.compareTo(new BigDecimal(getIntent().getStringExtra("payCount")))==-1){
                            Toast.makeText(this,"账户余额不足",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                }
                getData();
                break;
            case R.id.yu_e:
                iv_y_e.setSelected(true);
                iv_alipay.setSelected(false);
                iv_wxpay.setSelected(false);
                setPayType(3);
                break;
            case R.id.alipay:
                iv_y_e.setSelected(false);
                iv_alipay.setSelected(true);
                iv_wxpay.setSelected(false);
                setPayType(1);
                break;
            case R.id.wxpay:
                iv_y_e.setSelected(false);
                iv_alipay.setSelected(false);
                iv_wxpay.setSelected(true);
                setPayType(2);
                break;
        }

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
        tv_title.setText("订单支付");
        tv_money.setText("￥"+getIntent().getStringExtra("payCount")+"元");
        tv_type.setText("缴费类目："+getIntent().getStringExtra("item"));
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getSupplyMoney();
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
    public void onCancel() {
        messagePopuWindow.dismiss();
        finish();

    }
    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        LogUtils.i("event");
        Intent intent=new Intent(PunishPayActivity.this,PunishPaySuccessActivity.class);
        intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
        intent.putExtra("payCount",getIntent().getStringExtra("payCount"));
        startActivity(intent);
        finish();
    }
    public void getData(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("type",12);
        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("fineId",getIntent().getStringExtra("Id"));
        map.put("payType",getPayType());
        map.put("platform",0);
        map.put("payCount",getIntent().getStringExtra("payCount"));
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
                    new Wxpay(PunishPayActivity.this).pay(wxpay);
                }else if (getPayType()==1){
                    new Alipay(getBaseActivity(), getIntent().getStringExtra("item")+"罚款-淘大集供应链", "罚款",
                            getIntent().getStringExtra("payCount"), body.getData().getOutTradeNo(), PublicCache.getROOT_URL().get(0) + JniMethod.supplierAnnalFee_alipay()).newPay();


                }else {
                    Intent intent=new Intent(PunishPayActivity.this, PunishPaySuccessActivity.class);
                    intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                    intent.putExtra("payCount",getIntent().getStringExtra("payCount"));
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

    @Override
    public void onOk() {
        if (getPayType()!=0){
            getData();
        }
        messagePopuWindow.dismiss();
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.punish_pay_layout);
    }
}
