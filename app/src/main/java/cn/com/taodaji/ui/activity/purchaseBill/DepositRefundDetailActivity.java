package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindByCustomerRecordId;
import cn.com.taodaji.model.entity.PackageForegift;
import cn.com.taodaji.model.presenter.ModelRequest;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2019/1/8.
 */
public class DepositRefundDetailActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("明细详情");
    }

    private View view;

    @Override
    protected void initMainView() {
        view = getLayoutView(R.layout.activity_deposit_refund_detail);
        body_other.addView(view);

        TextView payContext = view.findViewById(R.id.payContext);
        TextView tv_payContext = view.findViewById(R.id.tv_payContext);


        if (PublicCache.loginSupplier != null) {
            view.findViewById(R.id.ll_supp_logo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ll_pur_supName).setVisibility(View.GONE);
            view.findViewById(R.id.ll_pageName).setVisibility(View.GONE);
            payContext.setText("已支付包装物押金(元)");
            tv_payContext.setText("已支付包装物押金");
        } else {
            payContext.setText("包装物退押金(元)");
            tv_payContext.setText("包装物退押金");
        }


    }

    @Override
    protected void initData() {
        super.initData();

        TextView paymentAmount = view.findViewById(R.id.paymentAmount);//总金额
        TextView paymentMethod = view.findViewById(R.id.paymentMethod);//退押数量
        TextView tv_cash_pledge_price_sum = view.findViewById(R.id.tv_cash_pledge_price_sum);//押金金额
        TextView goods_name = view.findViewById(R.id.goods_name);//商品名称
        TextView goods_price = view.findViewById(R.id.goods_price);//商品单价
        TextView buy_count = view.findViewById(R.id.buy_count);//购买数量
        TextView tv_cash_pledge_price = view.findViewById(R.id.tv_cash_pledge_price);//押金单价

        TextView order_num = view.findViewById(R.id.order_num);//订单编号
        TextView order_create_time = view.findViewById(R.id.order_create_time);//下单时间
        TextView tv_afterSaleApplyTime = view.findViewById(R.id.tv_afterSaleApplyTime);//tv_afterSaleApplyTime
        TextView tv_after_sale_time_wc = view.findViewById(R.id.tv_after_sale_time_wc);//退押完成时间


        if (PublicCache.loginPurchase != null) {
            onStartLoading();
            int entityId = getIntent().getIntExtra("entityId", 0);
            ModelRequest.getInstance().findByCustomerRecordId(entityId, PublicCache.site_login).enqueue(new RequestCallback<FindByCustomerRecordId>(this) {
                @Override
                protected void onSuc(FindByCustomerRecordId body) {
                    paymentAmount.setText("+" + body.getData().getRefundTotalMoney());

                    paymentMethod.setText(body.getData().getRefundQty() + "个");
                    tv_cash_pledge_price_sum.setText(String.valueOf(body.getData().getRefundTotalMoney()) + "元");


                    String nickName = "";
                    if (!TextUtils.isEmpty(body.getData().getProductNickName())) {
                        nickName = "(" + body.getData().getProductNickName() + ")";
                    }
                    goods_name.setText(body.getData().getProductName() + nickName);
                    goods_price.setText(String.valueOf(body.getData().getProductPrice()) + "元/" + body.getData().getProductUnit());
                    buy_count.setText(body.getData().getOriginalQty() + body.getData().getProductUnit() + "     金额" + body.getData().getOriginalTotalMoney() + "元");
                    tv_cash_pledge_price.setText(String.valueOf(body.getData().getDeliveryFee()) + "元/个    订购数量" + body.getData().getOriginalQty() + "个");

                    TextView tv_pageName = view.findViewById(R.id.tv_pageName);//包装物
                    tv_pageName.setText(body.getData().getApplicant());
                    TextView tv_supply_name = view.findViewById(R.id.tv_supply_name);//供应商
                    tv_supply_name.setText(body.getData().getAfterSaleApplicationNo());


                    order_num.setText(body.getData().getExtOrderId());
                    order_create_time.setText(body.getData().getOrderCreateTime());
                    if (TextUtils.isEmpty(body.getData().getAfterSaleApplyTime())) {
                        view.findViewById(R.id.ll_afterSaleApplyTime).setVisibility(View.GONE);
                    } else tv_afterSaleApplyTime.setText(body.getData().getAfterSaleApplyTime());
                    tv_after_sale_time_wc.setText(body.getData().getCreateTime());

                }
            });
        } else if (PublicCache.loginSupplier != null) {
            onStartLoading();
            int packOrderId = getIntent().getIntExtra("packOrderId", 0);
            String applyTime = getIntent().getStringExtra("applyTime");
            ModelRequest.getInstance().packageForegift(packOrderId, PublicCache.site_login).enqueue(new RequestCallback<PackageForegift>(this) {
                @Override
                protected void onSuc(PackageForegift body) {
                    GlideImageView imageView = view.findViewById(R.id.img_shop_logo);
                    imageView.loadImage(body.getData().getCustomer_img());
                    TextView tv_shop_name = view.findViewById(R.id.tv_shop_name);
                    tv_shop_name.setText(body.getData().getHotel_name());
                    paymentAmount.setText("+" + body.getData().getForegift().multiply(BigDecimal.valueOf(body.getData().getPayNum())).toString());

                    paymentMethod.setText(body.getData().getPayNum() + "个");
                    tv_cash_pledge_price_sum.setText(String.valueOf(body.getData().getForegift().multiply(BigDecimal.valueOf(body.getData().getPayNum()))) + "元");

                    String nickName = "";
                    if (!TextUtils.isEmpty(body.getData().getNick_name())) {
                        nickName = "(" + body.getData().getNick_name() + ")";
                    }
                    goods_name.setText(body.getData().getName() + nickName);

                    goods_price.setText(String.valueOf(body.getData().getPrice()) + "元/" + body.getData().getUnit());
                    buy_count.setText(body.getData().getAmount() + body.getData().getUnit() + "     金额" + body.getData().getTotal_price() + "元");
                    tv_cash_pledge_price.setText(String.valueOf(body.getData().getForegift()) + "元/个    订购数量" + body.getData().getAmount() + "个");


                    order_num.setText(body.getData().getOrder_no());
                    order_create_time.setText(body.getData().getOrder_pay_time());
                    view.findViewById(R.id.ll_afterSaleApplyTime).setVisibility(View.GONE);
                    tv_after_sale_time_wc.setText(applyTime);
                }
            });
        }

    }

    public static void startActivity(Context context, int entityId) {
        Intent intent = new Intent(context, DepositRefundDetailActivity.class);
        intent.putExtra("entityId", entityId);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int packOrderId, String applyTime) {
        Intent intent = new Intent(context, DepositRefundDetailActivity.class);
        intent.putExtra("packOrderId", packOrderId);
        intent.putExtra("applyTime", applyTime);
        context.startActivity(intent);
    }
}
