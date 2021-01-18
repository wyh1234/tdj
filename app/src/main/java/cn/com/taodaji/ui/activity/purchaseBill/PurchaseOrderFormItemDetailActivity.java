package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.PurchaseOrderItemAdapter;
import cn.com.taodaji.model.entity.CustomerFinanceRecordOrderItemDetail;

import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import java.math.BigDecimal;


public class PurchaseOrderFormItemDetailActivity extends SimpleToolbarActivity {
    TextView total;
    TextView deliveryFee, tv_invoice;
    TextView paymentAmount;

    TextView cashCoupon;
    RecyclerView recyclerView;
    LinearLayout ll_invoice;

    private TextView tv_orderForegift, tv_size;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("订单明细");
    }

    private PurchaseOrderItemAdapter adapter;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_purchase_bill_order_form_detail);
        body_other.addView(mainView);


        View hearView = ViewUtils.getLayoutView(this, R.layout.activity_purchase_bill_order_form_detail_heard);
        total = ViewUtils.findViewById(hearView, R.id.total);
        deliveryFee = ViewUtils.findViewById(hearView, R.id.deliveryFee);
        paymentAmount = ViewUtils.findViewById(hearView, R.id.paymentAmount);
        cashCoupon = ViewUtils.findViewById(hearView, R.id.cash_coupon);
        ll_invoice = hearView.findViewById(R.id.ll_invoice);
        tv_invoice = hearView.findViewById(R.id.tv_invoice);

        tv_orderForegift = hearView.findViewById(R.id.tv_orderForegift);
        tv_size = hearView.findViewById(R.id.tv_size);


        recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        adapter = new PurchaseOrderItemAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(hearView);

        int id = getIntent().getIntExtra("data", -1);
        if (id == -1) return;
        onStartLoading();
        getData(id);
    }

    public void getData(int id) {
        getRequestPresenter().customerFinanceRecordOrderItemDetail(id, new RequestCallback<CustomerFinanceRecordOrderItemDetail>(this) {
            @Override
            public void onSuc(CustomerFinanceRecordOrderItemDetail body) {
                if (body.getData() != null) {
                    tv_size.setText("共" + body.getData().getSize() + "件商品");
                    tv_orderForegift.setText("+" + body.getData().getPackageFee().toString() + "元");
                    deliveryFee.setText(String.valueOf(body.getData().getDeliveryFee()) + "元");
                    total.setText("+" + String.valueOf(body.getData().getOrderTotalAmount()) + "元");
                    paymentAmount.setText(String.valueOf(body.getData().getPaymentAmount()) + "元");
                    cashCoupon.setText(String.valueOf(body.getData().getCashCoupon()) + "元");

                    if (body.getData().getItems() != null)
                        adapter.setList(body.getData().getItems());

                    if (body.getData().getTaxCost().compareTo(BigDecimal.ZERO) > 0) {
                        ll_invoice.setVisibility(View.VISIBLE);
                        tv_invoice.setText(body.getData().getTaxCost().toString() + "元");
                    } else {
                        ll_invoice.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailed(int customerFinanceRecordOrderItemDetail, String msg) {

            }
        });
    }


    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, PurchaseOrderFormItemDetailActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

}
