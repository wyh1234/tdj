package cn.com.taodaji.ui.activity.purchaseBill;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CustomerFinanceRecordOrderDetail;

import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class PurchaseOrderFormDetailActivity extends SimpleToolbarActivity implements View.OnClickListener {
    TextView paymentAmount;
    TextView payContext;
    TextView paymentMethod, paymentMethod_text, cancel_text;
    TextView deliveryDate;
    TextView receiverName;
    TextView receiverAddress;
    TextView orderNo;
    TextView orderCreate;
    TextView payTime;

    LinearLayout cancel_order;
    TextView cancel_time;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("账单详情");
    }

    private View mainView;
    private int id;

    private boolean isOrderCancel = false;//是否订单取消

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_purchase_order_form_detail);
        body_other.addView(mainView);

        paymentAmount = ViewUtils.findViewById(mainView, R.id.paymentAmount);
        payContext = ViewUtils.findViewById(mainView, R.id.payContext);
        paymentMethod = ViewUtils.findViewById(mainView, R.id.paymentMethod);
        paymentMethod_text = mainView.findViewById(R.id.paymentMethod_text);
        cancel_order = mainView.findViewById(R.id.cancel_order);
        cancel_time = mainView.findViewById(R.id.cancel_time);


        deliveryDate = ViewUtils.findViewById(mainView, R.id.delivery_date);
        receiverName = ViewUtils.findViewById(mainView, R.id.receiver_name);
        receiverAddress = ViewUtils.findViewById(mainView, R.id.receiver_address);
        orderNo = ViewUtils.findViewById(mainView, R.id.order_no);
        orderCreate = ViewUtils.findViewById(mainView, R.id.order_create);
        payTime = ViewUtils.findViewById(mainView, R.id.pay_time);
        ViewUtils.findViewById(mainView, R.id.order).setOnClickListener(this);

        id = getIntent().getIntExtra("data", -1);
        String text = getIntent().getStringExtra("text");

        if ("取消订单".equals(text)) {
            isOrderCancel = true;
            cancel_text = mainView.findViewById(R.id.cancel_text);
            cancel_text.setText(text);
        }
        payContext.setText(text);
        if (id == -1) return;
        onStartLoading();
        getData(id);
    }

    public static void startActivity(Context context, int id, String text) {
        Intent intent = new Intent(context, PurchaseOrderFormDetailActivity.class);
        intent.putExtra("data", id);
        intent.putExtra("text", text);
        context.startActivity(intent);
    }

    public void getData(int id) {
//        loading();
        getRequestPresenter().customerFinanceRecordOrderDetail(id, new RequestCallback<CustomerFinanceRecordOrderDetail>(this) {
            @Override
            public void onSuc(CustomerFinanceRecordOrderDetail body) {
//                loadingDimss();
                CustomerFinanceRecordOrderDetail.DataBean bean = body.getData();
                if (bean == null) return;
                String tex = isOrderCancel ? "+" : "-";
                paymentAmount.setText(tex + bean.getPaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "");
                //paymentMethod	num	0、退款  1、支付宝 2、微信支付 3、余额支付 4、微信+余额支付 5、支付宝+余额支付 6、提现
                BigDecimal balance = bean.getPaymentAmount().subtract(bean.getOnlinePaymentAmount());
                String payText = "";
                switch (bean.getPaymentMethod()) {
                    case 1:
                        payText = "支付宝" + bean.getPaymentAmount().toString() + "元";
                        break;
                    case 2:
                        payText = "微信" + bean.getPaymentAmount().toString() + "元";
                        break;
                    case 3:
                        payText = "余额" + bean.getPaymentAmount().toString() + "元";
                        if (payContext.getText().toString().contains("余额")) {
                            paymentAmount.setText("-" + balance.toString() + "");
                        }
                        break;
                    case 7:
                        payText = "花呗" + bean.getPaymentAmount().toString() + "元";
                        break;
                    case 4:
                        payText = "微信" + bean.getOnlinePaymentAmount() + "元   余额" + balance.toString() + "元";
                        if (payContext.getText().toString().contains("余额")) {
                            paymentAmount.setText("-" + balance.toString() + "");
                        }
                        break;
                    case 8:
                        payText = "花呗" + bean.getOnlinePaymentAmount()+ "元   余额支付" + balance.toString() + "元";
                        if (payContext.getText().toString().contains("余额")) {
                            paymentAmount.setText("-" + balance.toString() + "");
                        }
                        break;
                    case 5:
                        payText = "支付宝" + bean.getOnlinePaymentAmount() + "元   余额" + balance.toString() + "元";
                        if (payContext.getText().toString().contains("余额")) {
                            paymentAmount.setText("-" + balance.toString() + "");
                        }
                        break;
                }
                deliveryDate.setText(bean.getExpectDeliveredDate() + "  " + bean.getExpectDeliveredEarliestTime() + "至" + bean.getExpectDeliveredLatestTime());
                receiverName.setText(bean.getReceiveName() + "   " + bean.getReceiveTel());
                receiverAddress.setText(bean.getReceiveAddress());
                orderNo.setText(bean.getExtOrderId());
                orderCreate.setText(bean.getOrderCreateTime());
                payTime.setText(bean.getOrderPayTime());

                if (isOrderCancel) {
                    paymentMethod_text.setText("取消原因:");
                    paymentMethod.setText(bean.getReason().equals("1")?"不想要了":"其他原因");
                    cancel_order.setVisibility(View.VISIBLE);
                    cancel_time.setText(bean.getCreateTime());
                } else paymentMethod.setText(payText);


            }

            @Override
            public void onFailed(int customerFinanceRecordOrderDetail, String msg) {
//                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


    public void onClick(View view) {
        if (id == -1) return;
        PurchaseOrderFormItemDetailActivity.startActivity(this, id);
    }
}
