package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.base.viewModel.BaseViewHolder;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;


import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SupplyMoneyDetailBean;
import cn.com.taodaji.viewModel.vm.SupplierOrderDetailVM;
import tools.activity.SimpleToolbarActivity;

/**
 * 供应商账单售后详情
 */
public class SupplierOrderAfterSaleActivity extends SimpleToolbarActivity {
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
        mainView = getLayoutView(R.layout.activity_supplier_after_sale);
        body_scroll.addView(mainView);
        viewHolder = new BaseViewHolder(mainView, new SupplierOrderDetailVM(), null);

        String remarks=getIntent().getStringExtra("remarks");
        getData( remarks);
    }


    public static void startActivity(Context context, String remarks) {
        Intent intent = new Intent(context, SupplierOrderAfterSaleActivity.class);
        intent.putExtra("remarks", remarks);
        context.startActivity(intent);
    }
    public void getData(String remarks) {
        //http://test.51taodj.com:3001/supplierFinanceRecord/afterSale/info?afterSaleApplicationId=1054
        loading();
        getRequestPresenter().getSupplierOrderAfterSale( remarks,new RequestCallback<SupplyMoneyDetailBean>(){

            @Override
            protected void onSuc(SupplyMoneyDetailBean body) {
                loadingDimss();
                if (body != null&&body.getData()!=null){
                    if (!TextUtils.isEmpty(body.getData().getNick_name())) {
                        body.getData().setNick_name("("+body.getData().getNick_name()+")");
                    }

                    viewHolder.setValues(body.getData());

                    BigDecimal afterSalePay=body.getData().getTotal_price().subtract(body.getData().getCommission()).add(body.getData().getCoupon_amount());
                    viewHolder.setText(R.id.txt_after_total_price, afterSalePay.toString());
                    viewHolder.setText(R.id.txt_back_price, body.getData().getTotal_price().toString());

                    String countText =  body.getData().getUnit();
                    if (body.getData().getLevel_type() == 2) {
                        countText += "(" + String.valueOf(body.getData().getLevel_2_value()) + body.getData().getLevel_2_unit() + ")";
                    } else if (body.getData().getLevel_type() == 3) {
                        countText += "(" + String.valueOf(body.getData().getLevel_2_value()) + body.getData().getLevel_2_unit() + "*" + String.valueOf(body.getData().getLevel_3_value() + body.getData().getLevel_3_unit()) + ")";
                    }


                    // putRelation(R.id.txt_single_unit_1, "avg_unit");
                    viewHolder.setText(R.id.txt_single_unit_1, countText);
                    if (body.getData().getReceiveAddr()!=null) {
                        viewHolder.setText(R.id.txt_receive_name, body.getData().getReceiveAddr().getName());
                        viewHolder.setText(R.id.txt_receive_phone, body.getData().getReceiveAddr().getTelephone());
                        viewHolder.setText(R.id.txt_receive_address, body.getData().getReceiveAddr().getAddress());
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
