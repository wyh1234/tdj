package cn.com.taodaji.ui.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.BalanceQuery;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.OrderConfirm;

import com.base.entity.ResultInfo;

import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.OrderListSucEvent;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import tools.activity.SimpleToolbarActivity;
import tools.jni.JniMethod;

import com.base.utils.DateUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class OrderConfirmActivity extends SimpleToolbarActivity implements View.OnClickListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
    }

    private View mainView;
    private TextView pay_price, time_mm, time_HH, time_ss, time_hint, tdj_name, tdj_description,alipay_description;
    private Button pay_ok;
    private View alipay, wxpay, tdjpay;
    private long time_end;
    private final String[] paymethods = {"alipay", "wxpay"};
    private String paymethod;
    private String order_no;
    private String outTradeNo;

    private OrderConfirm orderConfirm;
    private Switch switch1;
    private BigDecimal money_ye, money_cz, money_sh, money_pay;

    //private BigDecimal limited_hour;   //支付的倒计时

    @Override
    protected void initMainView() {
        if (PublicCache.initializtionData == null) return;
        mainView = ViewUtils.getLayoutView(this, R.layout.activity_order_confirm);
        body_scroll.addView(mainView);

        tdj_description = ViewUtils.findViewById(mainView, R.id.tdj_description);

        pay_ok = ViewUtils.findViewById(mainView, R.id.pay_ok);
        pay_ok.setOnClickListener(this);
        pay_ok.setEnabled(false);

        pay_price = ViewUtils.findViewById(mainView, R.id.pay_price);
        time_HH = ViewUtils.findViewById(mainView, R.id.time_HH);
        time_mm = ViewUtils.findViewById(mainView, R.id.time_mm);
        time_ss = ViewUtils.findViewById(mainView, R.id.time_ss);
        time_hint = ViewUtils.findViewById(mainView, R.id.time_hint);


        alipay = ViewUtils.findViewById(mainView, R.id.alipay);
        alipay.setSelected(true);
        alipay.setEnabled(false);

        paymethod = paymethods[0];
        alipay.setOnClickListener(this);
        wxpay = ViewUtils.findViewById(mainView, R.id.wxpay);
        wxpay.setOnClickListener(this);
        wxpay.setEnabled(false);

        tdjpay = ViewUtils.findViewById(mainView, R.id.tdj);
        tdjpay.setEnabled(false);

        tdj_name = ViewUtils.findViewById(mainView, R.id.tdj_name);
        switch1 = ViewUtils.findViewById(mainView, R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tdjpay.setVisibility(View.VISIBLE);
                    getMoney();
                } else {
                    tdjpay.setVisibility(View.GONE);
                    setViewText();
                    alipay.setEnabled(true);
                    wxpay.setEnabled(true);
                    alipay.callOnClick();
                }
            }
        });

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PublicCache.loginPurchase != null && PublicCache.loginPurchase.getEmpRole() != 1&& PublicCache.loginPurchase.getEmpRole() != 5)
            getMoney();
    }


    private Handler handler = UIUtils.getHandler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time_end < 0) {
                pay_ok.setEnabled(false);
                pay_ok.setBackgroundResource(R.drawable.z_round_rect_solid_gray_db_3p);
                pay_ok.setText("结算超时请重新下单");
                time_hint.setText("该订单已超时，请重新下单");
                simple_title.setText("订单超时");
            } else {
                String ss = String.valueOf((int) time_end / (60 * 60 * 1000));

                time_HH.setText(ss.length() < 2 ? "0" + ss : ss);
                time_mm.setText(DateUtils.parseTime("mm", time_end));
                time_ss.setText(DateUtils.parseTime("ss", time_end));
                handler.postDelayed(runnable, 1000);
            }
            time_end -= 1000;
        }
    };


    @Subscribe(sticky = true)
    public void onEvent(OrderConfirm orderPlaceBack) {
        EventBus.getDefault().removeStickyEvent(orderPlaceBack);
        orderConfirm = orderPlaceBack;
        order_no = orderPlaceBack.getOrder_no();
        outTradeNo = orderPlaceBack.getOutTradeNo();
        money_pay = new BigDecimal(orderPlaceBack.getTotalPrice());

        if (PublicCache.loginPurchase.getEmpRole() == 1||PublicCache.loginPurchase.getEmpRole() == 5) {
            ViewUtils.findViewById(mainView, R.id.pay_item).setVisibility(View.GONE);
            simple_title.setText("确认订单");
            pay_ok.setText("已通知财务付款");
            pay_ok.setEnabled(true);
        } else {
            getMoney();
            simple_title.setText("确认支付");
            pay_ok.setText("确认支付 " + orderPlaceBack.getTotalPrice() + " 元");
        }
        pay_price.setText(orderPlaceBack.getTotalPrice() + " 元");
        //支付的倒计时
       // limited_hour = new BigDecimal(PublicCache.initializtionData.getCount_down_time());

        int minute = PublicCache.initializtionData.getCount_down_time();
        long next_hour = DateUtils.getMinute(orderPlaceBack.getCreateTime(), minute);

        time_end = next_hour - System.currentTimeMillis();
        time_hint.setText("该笔订单超过" + DateUtils.getMinute(orderPlaceBack.getCreateTime(), "HH:mm", minute) + "未支付，将自动关闭，请及时结算");
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public static void startActivity(Context context, OrderConfirm orderPlaceBack) {
        EventBus.getDefault().postSticky(orderPlaceBack);
        Intent intent = new Intent(context, OrderConfirmActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alipay:
                alipay.setSelected(true);
                wxpay.setSelected(false);
                paymethod = paymethods[0];
                setViewText();
                break;
            case R.id.wxpay:
                alipay.setSelected(false);
                wxpay.setSelected(true);
                paymethod = paymethods[1];
                setViewText();
                break;
            case R.id.pay_ok:
                if (PublicCache.loginPurchase.getEmpRole() == 1||PublicCache.loginPurchase.getEmpRole()==5) {
                    finish();
                } else {
                    if (pay_ok.isEnabled()) {
                        payMethodChecked();
                    }
                }
                break;
        }
    }


    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        //通知订单列表和详情支付成功
        EventBus.getDefault().post(new OrderListSucEvent(orderConfirm.getOutTradeNo()));
        finish();
    }


    private void alipay(BigDecimal money) {
        new Alipay(this, "淘大集供应链", "淘大集供应链", money.toString(), outTradeNo, PublicCache.getROOT_URL().get(0) + JniMethod.alipay_order()).pay();
    }

    private void wxpay(BigDecimal money) {
        loading("正在调起微信");
        getRequestPresenter().wxpay_repayId(outTradeNo, money.toString(), new ResultInfoCallback<WXPay>() {
            @Override
            public void onSuccess(WXPay wxPay) {
                loadingDimss();
                new Wxpay(OrderConfirmActivity.this).pay(wxPay);
            }

            @Override
            public void onFailed(int wxPayResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
                loadingDimss();
            }
        });
    }

    private void tdjpay(final BigDecimal money) {
        loading("正在使用余额支付");
        getRequestPresenter().balancePay(outTradeNo, money, PublicCache.loginPurchase.getEntityId(), new RequestCallback<ResultInfo>() {
            @Override
            public void onSuc(ResultInfo body) {
                loadingDimss();
                UIUtils.showToastSafe("支付成功");
                //通知提交页面支付成功
                EventBus.getDefault().post(new PaySuccessEvent());
                finish();
            }

            @Override
            public void onFailed(int resultInfo, String msg) {
                UIUtils.showToastSafesShort(msg);
                loadingDimss();
            }
        });
    }

    //获取余额
    public void getMoney() {
        if (PublicCache.loginPurchase == null) return;//flag //0-刷新门店信息 1-获取当前登陆用户信息
        getRequestPresenter().customer_refreshInfo(PublicCache.loginPurchase.getEntityId(),0,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<MyselftUpdateP>() {
            @Override
            public void onSuc(MyselftUpdateP body) {
                money_ye = body.getData().getMoney();
                money_cz = body.getData().getRechargeMoney();
                money_sh = body.getData().getRefundMoney();
                if (time_end >= 0) {
                    pay_ok.setEnabled(true);
                }
                if (money_ye == null || money_pay == null) return;
                //如果余额够支付
                if (money_ye.compareTo(money_pay) >= 0 && switch1.isChecked()) {
                    alipay.setSelected(false);
                    wxpay.setSelected(false);
                    alipay.setEnabled(false);
                    wxpay.setEnabled(false);
                    setViewText();
                } else {
                    alipay.setEnabled(true);
                    wxpay.setEnabled(true);
                    alipay.callOnClick();
                }

            }

            @Override
            public void onFailed(int myselftUpdateP, String msg) {

            }
        });
    }

    private String getPayString(String paymethod) {
        if (paymethod.equals(paymethods[0])) return "支付宝";
        else return "微信";
    }

    private void setViewText() {
        if (money_cz == null || money_pay == null || money_ye == null) return;
        if (switch1.isChecked()) {
            tdj_name.setText("我的余额：" + money_ye.toString() + " 元");

            String text1 = "";
            String text2 = "";
            pay_ok.setEnabled(true);

            if (money_pay.compareTo(money_cz) <= 0) {
                text1 = "充值余额 " + money_pay + " 元";
                pay_ok.setText("确认使用余额支付 " + money_pay.toString() + "元");
            } else if (money_pay.compareTo(money_ye) <= 0) {

                if (money_cz.compareTo(BigDecimal.ZERO) > 0)
                    text1 = "充值余额 " + money_cz.toString() + " 元，";

                text2 = "售后余额" + money_pay.subtract(money_cz).toString() + "元";

                pay_ok.setText("确认使用余额支付 " + money_pay.toString() + "元");
            } else {
                if (money_cz.compareTo(BigDecimal.ZERO) > 0)
                    text1 = "充值余额 " + money_cz.toString() + " 元，";
                text2 = "售后余额" + money_sh.toString() + "元";

                pay_ok.setText("使用" + getPayString(paymethod) + "支付 " + money_pay.subtract(money_ye).toString() + " 元");
            }
            tdj_description.setText("使用" + text1 + text2);
        } else {
            pay_ok.setText("使用" + getPayString(paymethod) + "支付 " + money_pay.toString() + " 元");
        }


    }

    /**
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * customerId	num	1	采购商的customerId
     * customerTel	str	1	采购商的电话号码
     * hotelName	str	1	酒店名称
     * extOrderId	str	1	订单的extOrderId
     * outTradeNo	str	1	订单的outTradeNo
     * status	num	1	1、支付中 2、支付结束
     * isSupportBalancePayment	num	1	0、不支持余额支持 1、支持余额支持
     * totalPaymentAmount	num	1	支付的总金额
     * onlinePaymentAmount	num	1	支付宝或微信支付的总金额
     * balancePaymentAmount	num	1	余额支付的总金额
     * paymentMethod	num	1	1、支付宝 2、微信支付 3、余额支付 4、支付宝+余额 5、微信+余额
     */

    private void payMethodChecked() {
        loading();
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", PublicCache.loginPurchase.getEntityId());
        map.put("customerTel", PublicCache.loginPurchase.getPhoneNumber());
        map.put("hotelName", PublicCache.loginPurchase.getHotelName());
        map.put("extOrderId", order_no);
        map.put("outTradeNo", outTradeNo);
        map.put("status", 1);
        map.put("divNo", SystemUtils.getDeviceId(this));

        //不使用余额
        if (!switch1.isChecked() || money_ye.compareTo(BigDecimal.ZERO) == 0) {
            map.put("isSupportBalancePayment", 0);
            map.put("paymentMethod", paymethod.equals(paymethods[0]) ? 1 : 2);
            map.put("totalPaymentAmount", money_pay);
            map.put("onlinePaymentAmount", money_pay);
            map.put("balancePaymentAmount", 0);
        }
        //只使用余额
        else if (money_ye.compareTo(money_pay) >= 0) {
            map.put("isSupportBalancePayment", 1);
            map.put("paymentMethod", 3);
            map.put("totalPaymentAmount", money_pay);
            map.put("onlinePaymentAmount", 0);
            map.put("balancePaymentAmount", money_pay);
        }
        //使用余额和第三方
        else {
            map.put("isSupportBalancePayment", 1);
            map.put("paymentMethod", paymethod.equals(paymethods[0]) ? 4 : 5);
            map.put("totalPaymentAmount", money_pay);
            map.put("onlinePaymentAmount", money_pay.subtract(money_ye));
            map.put("balancePaymentAmount", money_ye);
        }

        getRequestPresenter().balanceQuery(map, new RequestCallback<BalanceQuery>() {
            @Override
            public void onSuc(BalanceQuery body) {
                loadingDimss();
                if (body.getData().getPaymentMethod() == 3) {
                    tdjpay(body.getData().getBalancePaymentAmount());
                } else if (body.getData().getPaymentMethod() == 1 || body.getData().getPaymentMethod() == 4) {
                    alipay(body.getData().getOnlinePaymentAmount());
                } else wxpay(body.getData().getOnlinePaymentAmount());
            }

            @Override
            public void onFailed(int balanceQuery, String msg) {
                loadingDimss();
                if (balanceQuery == 1) {
                    UIUtils.showToastSafe(msg);
                }
            }
        });
    }
}
