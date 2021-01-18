package cn.com.taodaji.ui.pay;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.RechargeCreate;

import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.model.event.RechargeMoneyPayEvent;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import tools.activity.SimpleToolbarActivity;
import tools.jni.JniMethod;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class RechargeMoneyPayActivity extends SimpleToolbarActivity implements View.OnClickListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("充值");
    }

    private float rechargeMoney;
    TextView money;
    private static final String payment_wechat = "wechat_payment";
    private static final String payment_alipay = "alipay_payment";
    private static final String payment_lian = "lian_payment";
    private static final String huabei_payment = "huabei_payment";


    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_recharge_money_pay);
        body_other.addView(mainView);
        money = ViewUtils.findViewById(mainView, R.id.money);
        ViewUtils.findViewById(mainView, R.id.alipay).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.wxpay).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.alipay_hb).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.tdj).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        money.setText(String.valueOf(rechargeMoney));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onEvent(RechargeMoneyPayEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        rechargeMoney = event.getMoney();
    }


    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        setResult(RESULT_OK);
        finish();
    }

    public void onClick(View view) {
        String paymentMethod = null;
        loading();
        switch (view.getId()) {
            case R.id.alipay:
                paymentMethod = payment_alipay;
                break;
            case R.id.wxpay:
                paymentMethod = payment_wechat;
                break;
            case R.id.alipay_hb:
                paymentMethod = huabei_payment;
                break;
            case R.id.tdj:
                paymentMethod = payment_lian;
                break;
        }
        getRequestPresenter().recharge_create(PublicCache.loginPurchase.getEntityId(), PublicCache.loginPurchase.getSubUserId(), PublicCache.loginPurchase.getHotelName(), paymentMethod, "padding", rechargeMoney, new RequestCallback<RechargeCreate>(this) {
            @Override
            public void onSuc(RechargeCreate body) {

                switch (body.getData().getPaymentMethod()) {
                    case payment_alipay:
                        loadingDimss();
                            new Alipay(getBaseActivity(), "充值-淘大集供应链", "充值",
                                    String.valueOf(body.getData().getCapital()), body.getData().getOutTradeNo(),
                                    PublicCache.getROOT_URL().get(0) + JniMethod.alipay_recharge(),false).payIsHb();

                        break;
                    case payment_wechat:
                        getRequestPresenter().wxpay_recharge(body.getData().getOutTradeNo(), body.getData().getCapital(), new ResultInfoCallback<WXPay>() {
                            @Override
                            public void onSuccess(WXPay body) {
                                loadingDimss();
                                new Wxpay(RechargeMoneyPayActivity.this).pay(body);
                            }

                            @Override
                            public void onFailed(int wxPayResultInfo, String msg) {
                                loadingDimss();
                            }
                        });


                        break;
                    case payment_lian:
                        loadingDimss();
                        UIUtils.showToastSafe("暂不支持第三方");
                        break;
                    case huabei_payment:
                        loadingDimss();
                        new Alipay(getBaseActivity(), "充值-淘大集供应链", "充值",
                                String.valueOf(body.getData().getCapital()), body.getData().getOutTradeNo(),
                                PublicCache.getROOT_URL().get(0) + JniMethod.alipay_rechargehb(),true).payIsHb();
                        break;
                }
            }

            @Override
            public void onFailed(int rechargeCreate, String msg) {
                UIUtils.showToastSafe(msg);
                loadingDimss();
            }
        });
    }


}
