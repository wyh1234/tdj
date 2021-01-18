package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimplePPWAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CustomerFinanceRecordFindMonthBillDetail;
import cn.com.taodaji.model.entity.Float_String;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.ppw.RecyclerViewPPW;
import cn.com.taodaji.ui.view.MonthlyBillLineView;
import cn.com.taodaji.ui.view.PieChartView;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

public class MonthlyBillActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private TextView date,orderDate,orderNums,realPay,payBalance,payCash,totalPrice,totalPriceAmount,productAmount,deliveryAmount,taxAmount,packagePrice,packagePriceAmount,couponAmount,afterSalePrice,afterSalePriceAmount,returnPackagePrice,returnPackagePriceAmount;
    private LinearLayout date_par;

    @Override
    protected void titleSetting(Toolbar toolbar) {

        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("月账单");
    }

    private SimplePPWAdapter adapter;


    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_monthly_bill);
        body_scroll.addView(mainView);
        date = ViewUtils.findViewById(mainView, R.id.date);
        orderDate = ViewUtils.findViewById(mainView, R.id.tv_order_date);
        orderNums = ViewUtils.findViewById(mainView, R.id.tv_order_nums);
        realPay = ViewUtils.findViewById(mainView, R.id.tv_real_pay);
        payBalance = ViewUtils.findViewById(mainView, R.id.tv_pay_balance);
        payCash = ViewUtils.findViewById(mainView, R.id.tv_pay_cash);
        totalPrice = ViewUtils.findViewById(mainView, R.id.tv_total_price);
        totalPriceAmount = ViewUtils.findViewById(mainView, R.id.tv_total_price_amount);
        productAmount = ViewUtils.findViewById(mainView, R.id.tv_product_amount);
        deliveryAmount = ViewUtils.findViewById(mainView, R.id.tv_delivery_amount);
        taxAmount = ViewUtils.findViewById(mainView, R.id.tv_tax_amount);
        packagePrice = ViewUtils.findViewById(mainView, R.id.tv_package_price);
        packagePriceAmount = ViewUtils.findViewById(mainView, R.id.tv_package_price_amount);
        couponAmount = ViewUtils.findViewById(mainView, R.id.tv_coupon_amount);
        afterSalePrice = ViewUtils.findViewById(mainView, R.id.tv_after_sale_price);
        afterSalePriceAmount = ViewUtils.findViewById(mainView, R.id.tv_after_sale_price_amount);
        returnPackagePrice = ViewUtils.findViewById(mainView, R.id.tv_return_package_price);
        returnPackagePriceAmount = ViewUtils.findViewById(mainView, R.id.tv_return_package_price_amount);
        date_par = ViewUtils.findViewById(mainView, R.id.date_par);
        ViewUtils.findViewById(mainView, R.id.date_group).setOnClickListener(this);

        String dateStr = getIntent().getStringExtra("data");
        getDate(dateStr);
    }


    public void getDate(String date) {
        if (PublicCache.loginPurchase == null) return;
        if (TextUtils.isEmpty(date) || date.length() < 7) return;
        int year = Integer.valueOf(date.substring(0, 4));
        final int month = Integer.valueOf(date.substring(5, 7));
        this.date.setText(date);
        loading();
        getRequestPresenter().customerFinanceRecordFindMonthBillDetail(PublicCache.loginPurchase.getEntityId(), year, month, new RequestCallback<CustomerFinanceRecordFindMonthBillDetail>() {
            @Override
            public void onSuc(CustomerFinanceRecordFindMonthBillDetail body) {
                loadingDimss();
                CustomerFinanceRecordFindMonthBillDetail.DataBean.ItemBean bean = body.getData().getItem();
                orderDate.setText(bean.getOrderStartDate()+"至"+bean.getOrderEndDate());
                orderNums.setText(bean.getOrderNums()+"笔");
                realPay.setText("￥"+bean.getRealPayAmount());
                payBalance.setText("余额支付：￥"+bean.getBalanceAmount());
                payCash.setText("现金支付：￥"+bean.getCashAmount());
                totalPrice.setText("共付款金额("+bean.getOrderNums()+"笔)");
                totalPriceAmount.setText("￥"+bean.getTotalAmount());
                productAmount.setText("+￥"+bean.getProductAmount());
                deliveryAmount.setText("+￥"+bean.getDeliveryAmount());
                taxAmount.setText("+￥"+bean.getTaxAmount());
                packagePrice.setText("包装物押金(共"+bean.getPackageNums()+"个)");
                packagePriceAmount.setText("+￥"+bean.getPackageAmount());
                couponAmount.setText("-￥"+bean.getCouponAmount());
                afterSalePrice.setText("售后退款(共"+bean.getAfterNums()+"个)");
                afterSalePriceAmount.setText("-￥"+bean.getAfterAmount());
                returnPackagePrice.setText("退包装物押金(共"+bean.getReturnPackageNums()+"个)");
                returnPackagePriceAmount.setText("-￥"+bean.getReturenPackageAmount());
            }

            @Override
            public void onFailed(int customerFinanceRecordFindMonthBillDetail, String msg) {
                loadingDimss();
            }
        });
    }


    public static void startActivity(Context context, String date) {
        Intent intent = new Intent(context, MonthlyBillActivity.class);
        intent.putExtra("data", date);
        context.startActivity(intent);
    }

    private RecyclerViewPPW debug_listView;


    public void onClick(View view) {
        String dates = date.getText().toString();
        if (adapter == null) {
            adapter = new SimplePPWAdapter();
            adapter.setItemClickListener(new OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                    if (onclickType == 0) {
                        getDate(JavaMethod.getFieldValue(bean, "imageName"));
                        debug_listView.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            debug_listView = new RecyclerViewPPW(this, date_par, adapter);
        }


        List<ADInfo> list = new ArrayList<>();
        ADInfo adInfo = new ADInfo();
        adInfo.setImageName(dates);
        list.add(adInfo);

        long now = DateUtils.dateStringToLong(dates, "yyyy年MM月");
        for (int i = 1; i <= 4; i++) {
            String str1 = DateUtils.getMonth("yyyy年MM月", -i, now);
            ADInfo adInfo1 = new ADInfo();
            adInfo1.setImageName(str1);
            list.add(adInfo1);
        }
        adapter.notifyDataSetChanged(list);

        if (!debug_listView.isShowing()) debug_listView.showAsDropDown(date_par);

    }
}
