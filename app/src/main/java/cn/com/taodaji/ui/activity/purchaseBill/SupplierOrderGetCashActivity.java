package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.viewModel.BaseViewHolder;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SupplyMoneyDetailBean;
import cn.com.taodaji.viewModel.vm.SupplierOrderDetailVM;
import tools.activity.SimpleToolbarActivity;

/**
 * 供应商账单提现详情
 */
public class SupplierOrderGetCashActivity extends SimpleToolbarActivity {
    private View mainView;
    private BaseViewHolder viewHolder;


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("明细详情");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_supplier_order_get_cash);
        body_scroll.addView(mainView);
        viewHolder = new BaseViewHolder(mainView, new SupplierOrderDetailVM(), null);

        int withdrawalStatus=getIntent().getIntExtra("withdrawalStatus",0);
        String remarks = getIntent().getStringExtra("remarks");
        getData(remarks,withdrawalStatus);
    }

    public static void startActivity(Context context, String remarks,int withdrawalStatus) {
        Intent intent = new Intent(context, SupplierOrderGetCashActivity.class);
        intent.putExtra("remarks", remarks);
        intent.putExtra("withdrawalStatus", withdrawalStatus);
        context.startActivity(intent);
    }



    public void getData(String remarks,int withdrawalStatus) {
         loading();

        // http://test.51taodj.com:3001/supplierFinanceRecord/withdrawal/info?cashWithdrawlEntityId=2258
        getRequestPresenter().getSupplierOrderGetCash(withdrawalStatus,remarks, new RequestCallback<SupplyMoneyDetailBean>(this) {


            @Override
            protected void onSuc(SupplyMoneyDetailBean body) {
                loadingDimss();
                if (body != null && body.getData() != null) {
                    // "status": 0, //提现状态   0：待提现  1：提现成功  2： 拒绝提现
                    viewHolder.setValues(body.getData());

                    switch (body.getData().getStatus()) {
                        case 0:
                            viewHolder.setText(R.id.txt_type, "-");
                            viewHolder.setText(R.id.txt_get_cash_state, "处理中");
                            viewHolder.setVisibility(R.id.line_finish_time, View.GONE);
                            break;
                        case 1:
                            viewHolder.setText(R.id.txt_type, "-");
                            viewHolder.setText(R.id.txt_get_cash_state, "提现成功");
                            viewHolder.setVisibility(R.id.line_finish_time, View.VISIBLE);
                            break;
                        case 2:
                            viewHolder.setText(R.id.txt_type, "+");
                            viewHolder.setText(R.id.txt_get_cash_state, "驳回申请");
                            viewHolder.setVisibility(R.id.line_finish_time, View.VISIBLE);
                            break;
                        case 3:
                            viewHolder.setText(R.id.txt_type, "-");
                            viewHolder.setText(R.id.txt_get_cash_state, "处理中(已驳回)");
                            viewHolder.setVisibility(R.id.line_finish_time, View.VISIBLE);
                            break;

                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


}
