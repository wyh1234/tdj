package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CustomerFinanceRecordWithdrawalDetail;
import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class PurchaseWithDepositeDetailActivity extends SimpleToolbarActivity {
    TextView withdrawalAmount;
    TextView payContext;
    TextView withdrawalMoney;
    TextView goodsName;
    TextView applyName;
    TextView applyTime;
    TextView disposeTime;
    TextView bankName;
    TextView bankCardNo;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("账单详情");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_purchase_withdraw_deposite_detail);
        body_other.addView(mainView);
        withdrawalAmount=ViewUtils.findViewById(mainView,R.id.withdrawalAmount);
        payContext=ViewUtils.findViewById(mainView,R.id.payContext);
        withdrawalMoney=ViewUtils.findViewById(mainView,R.id.withdrawalMoney);
        goodsName=ViewUtils.findViewById(mainView,R.id.goods_name);
        applyName=ViewUtils.findViewById(mainView,R.id.applyName);
        applyTime=ViewUtils.findViewById(mainView,R.id.applyTime);
        disposeTime=ViewUtils.findViewById(mainView,R.id.disposeTime);
        bankName=ViewUtils.findViewById(mainView,R.id.bank_name);
        bankCardNo=ViewUtils.findViewById(mainView,R.id.bankCard_no);

        int id = getIntent().getIntExtra("data", -1);
        if (id == -1) return;
        getData(id);
    }

    public void getData(int id) {
        loading();
        getRequestPresenter().customerFinanceRecordWithdrawalDetail(id, new RequestCallback<CustomerFinanceRecordWithdrawalDetail>() {
            @Override
            public void onSuc(CustomerFinanceRecordWithdrawalDetail body) {
                loadingDimss();
                if (body.getData().getCapital_withdrawal()!=null)
                withdrawalAmount.setText("-" + body.getData().getCapital_withdrawal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                withdrawalMoney.setText(body.getData().getCapital_withdrawal().toString() + "元");
                applyName.setText(body.getData().getCustomer_name() + "   " + body.getData().getCustomer_tel());
                applyTime.setText(body.getData().getCreate_time());
                disposeTime.setText(body.getData().getUpdate_time());
                bankName.setText(body.getData().getBank_name());
                bankCardNo.setText(body.getData().getAccount_no());
            }

            @Override
            public void onFailed(int customerFinanceRecordWithdrawalDetail, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, PurchaseWithDepositeDetailActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

}
