package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;


import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SupplyMoneyDetailBean;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.viewModel.vm.SupplierOrderDetailVM;
import tools.activity.SimpleToolbarActivity;

/**
 * 供应商账单已收款（取消）详情
 */
public class SupplierOrderPaymentActivity extends SimpleToolbarActivity implements OnItemClickListener {
    private View mainView;
    private BaseViewHolder viewHolder;

    private String order_status = "";
    private String orderNO = "";
    private int orderid;
    private int storeId;
    private TextView tv_cash_pledge_price_sum;
    private String shopCouponAmount="";

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("明细详情");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_supplier_order_payment);
        body_scroll.addView(mainView);
        viewHolder = new BaseViewHolder(mainView, new SupplierOrderDetailVM(), this);

        orderid = getIntent().getIntExtra("orderid", 0);
        orderNO = getIntent().getStringExtra("orderNO");
        // order_status订单的状态:trade_finished|trade_canceled|trade_closed
        order_status = getIntent().getStringExtra("order_status");
        storeId = getIntent().getIntExtra("storeId", 0);
        order_status = getIntent().getStringExtra("order_status");
        shopCouponAmount= getIntent().getStringExtra("shopCouponAmount");
        if ("trade_canceled".equals(order_status)) {
            viewHolder.setVisibility(R.id.txt_state, View.VISIBLE);
            viewHolder.setVisibility(R.id.line_cancel_time, View.VISIBLE);


            viewHolder.setText(R.id.txt_type, "-");
            viewHolder.setText(R.id.txt_order_symbol, "-");
            viewHolder.setText(R.id.txt_commission_symbol, "+");
            viewHolder.setText(R.id.txt_commission_symbol, "+");


        } else {
            viewHolder.setVisibility(R.id.txt_state, View.GONE);
            viewHolder.setVisibility(R.id.line_cancel_time, View.GONE);

            viewHolder.setText(R.id.txt_type, "+");
            viewHolder.setText(R.id.txt_order_symbol, "+");
            viewHolder.setText(R.id.txt_commission_symbol, "-");
        }


        getData(orderid, orderNO);
    }

    public static void startActivity(Context context, int orderid, String orderNO, String order_status, int storeId,String shopCouponAmount) {
        Intent intent = new Intent(context, SupplierOrderPaymentActivity.class);
        intent.putExtra("orderid", orderid);
        intent.putExtra("orderNO", orderNO);
        intent.putExtra("order_status", order_status);
        intent.putExtra("storeId", storeId);
        intent.putExtra("shopCouponAmount", shopCouponAmount);
        context.startActivity(intent);
    }

    public void getData(int orderid, String orderNO) {
        // http://test.51taodj.com:3001//supplierFinanceRecord/order/findOne
        //  orderid  orderNO
        loading();
        getRequestPresenter().getSupplierOrderPayment(orderid, orderNO, new RequestCallback<SupplyMoneyDetailBean>() {

            @Override
            protected void onSuc(SupplyMoneyDetailBean body) {
                loadingDimss();
                if (body != null && body.getData() != null) {
                    viewHolder.setValues(body.getData());

                    // putRelation(R.id.txt_order_payment_price, "subtotalCost");
                    // BigDecimal orderPay=body.getData().getSubtotalCost().add(body.getData().getCommission());
                    viewHolder.setText(R.id.txt_order_payment_price, body.getData().getSubtotalCost().toString());

                    BigDecimal realPrice = body.getData().getSubtotalCost().subtract(body.getData().getCommission());
                    viewHolder.setText(R.id.txt_payment_price, realPrice.toString());
                    //        putRelation(R.id.txt_payment_price, "subtotalCost");


                    if (body.getData().getReceiveAddr() != null) {
                        viewHolder.setText(R.id.txt_receive_name, body.getData().getReceiveAddr().getName());
                        viewHolder.setText(R.id.txt_receive_phone, body.getData().getReceiveAddr().getTelephone());
                        viewHolder.setText(R.id.txt_receive_address, body.getData().getReceiveAddr().getAddress());

                    }

                    if (!ListUtils.isNullOrZeroLenght(shopCouponAmount)){
                        if (body.getData().getCouponAmount()>0){
                            viewHolder.setVisibility(R.id.ll_djq, View.VISIBLE);
                            viewHolder.setText(R.id.txt_payment_djq, ""+shopCouponAmount);
                            viewHolder.setText(R.id.txt_payment_price, realPrice.subtract(new BigDecimal(shopCouponAmount)));
                        }else {
                            viewHolder.setVisibility(R.id.ll_djq, View.GONE);
                        }
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

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.txt_go_order_detail:
                    SupplierOrderFormItemDetailActivity.startActivity(view.getContext(), orderid, orderNO, storeId);
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }
}
