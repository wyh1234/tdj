package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SupplierOrderItemAdapter;
import cn.com.taodaji.model.entity.SupplierOrderFormItemBean;
import tools.activity.SimpleToolbarActivity;

/**
 * 供应商从账单详情跳转到的订单详情页面
 */
public class SupplierOrderFormItemDetailActivity extends SimpleToolbarActivity {
    private SupplierOrderItemAdapter adapter;
//    private TextView goodsCount;
    private RecyclerView recyclerView;


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        setTitle("订单详情");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_supplier_order_form_item_detail);
        body_other.addView(mainView);
        recyclerView=mainView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        adapter = new SupplierOrderItemAdapter();
        recyclerView.setAdapter(adapter);

        int orderid=getIntent().getIntExtra("orderid",0);
        int storeId=getIntent().getIntExtra("storeId",0);
        String orderNO=getIntent().getStringExtra("orderNO");

        getData(orderid, orderNO, storeId);

    }

    private void getData(int orderid,String orderNO,int storeId) {
        // http://test.51taodj.com:3001//supplierFinanceRecord/order/findOne
        //  orderid  orderNO
        getRequestPresenter().getSupplierOrderFormItemDetailList(storeId, orderid, orderNO,new RequestCallback<SupplierOrderFormItemBean>(){

            @Override
            protected void onSuc(SupplierOrderFormItemBean body) {
                if (body != null&&body.getData()!=null){

                    TextView  goods_count=findViewById(R.id.goods_count);
                    TextView  total=findViewById(R.id.total);
                    TextView  paymentAmount=findViewById(R.id.paymentAmount);//实收款
                     TextView  deliveryFee=findViewById(R.id.deliveryFee);//佣金

                    goods_count.setText(body.getData().getBuyNumber()+"");
                    total.setText("+"+body.getData().getSubtotalCost()+"元");//totalCost

                    BigDecimal realPrice=body.getData().getSubtotalCost().subtract(body.getData().getCommission());

                    paymentAmount.setText("+"+realPrice.toString()+"元");//totalCost
                     deliveryFee.setText("-"+body.getData().getCommission()+"元");//totalCost

                    if (body.getData().getItems()!=null){
                        adapter.setList(body.getData().getItems());
                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });

    }

    public static void startActivity(Context context, int orderid,String orderNO,int storeId) {
        Intent intent = new Intent(context, SupplierOrderFormItemDetailActivity.class);
        intent.putExtra("orderid", orderid);
        intent.putExtra("orderNO", orderNO);
        intent.putExtra("storeId", storeId);
        context.startActivity(intent);
    }
}
