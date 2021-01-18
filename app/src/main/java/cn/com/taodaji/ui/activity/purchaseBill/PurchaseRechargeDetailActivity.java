package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CustomerFinanceRecordRechargeDetail;
import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.ViewUtils;


public class PurchaseRechargeDetailActivity extends SimpleToolbarActivity {
    TextView rechargeAmount;
    TextView rechargeContext;
    TextView rechargeMethod;
    TextView rechargeTime;
    TextView textView8;
    TextView rechargeName;
    TextView rechargeNo;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("账单详情");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_purchase_recharge_detail);
        body_other.addView(mainView);
        rechargeAmount = ViewUtils.findViewById(mainView, R.id.rechargeAmount);
        rechargeContext = ViewUtils.findViewById(mainView, R.id.rechargeContext);
        rechargeMethod = ViewUtils.findViewById(mainView, R.id.rechargeMethod);
        rechargeTime = ViewUtils.findViewById(mainView, R.id.rechargeTime);
        textView8 = ViewUtils.findViewById(mainView, R.id.textView8);
        rechargeName = ViewUtils.findViewById(mainView, R.id.recharge_name);
        rechargeNo = ViewUtils.findViewById(mainView, R.id.recharge_no);

        int id = getIntent().getIntExtra("data", -1);
        if (id == -1) return;
        getData(id);
    }

    public void getData(int id) {
        getRequestPresenter().customerFinanceRecordRechargeDetail(id, new RequestCallback<CustomerFinanceRecordRechargeDetail>() {
            @Override
            public void onSuc(CustomerFinanceRecordRechargeDetail body) {
                rechargeAmount.setText("+" + body.getData().getPaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                int rec = body.getData().getPaymentMethod();
                String text = "";
                if (rec == 1) {
                    text = "支付宝";
                }
                else if (rec == 2){
                    text = "微信";
                }else if (rec == 7){
                    text = "花呗";
                }else if (rec==9){
                    text = "线下缴费";
                }
                rechargeMethod.setText(text + body.getData().getPaymentAmount().toString() + "元");
                rechargeTime.setText(body.getData().getPayTime());
                rechargeName.setText(body.getData().getRole() + "   " + body.getData().getCustomerName());
                rechargeNo.setText(body.getData().getTransactionNumber());
            }

            @Override
            public void onFailed(int customerFinanceRecordRechargeDetail, String msg) {

            }
        });
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, PurchaseRechargeDetailActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

}
