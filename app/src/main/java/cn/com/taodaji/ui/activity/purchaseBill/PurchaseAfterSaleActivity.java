package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CustomerFinanceRecordRefundDetail;
import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.ViewUtils;

public class PurchaseAfterSaleActivity extends SimpleToolbarActivity {
    TextView paymentAmount;
    TextView payContext;
    TextView paymentMethod;
    TextView goodsName;
    TextView goodsPrice;
    TextView buyCount;
    TextView orderNum;
    TextView afterNum;
    TextView orderCreateTime;
    TextView afterSaleTime;
    TextView help;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("账单详情");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_purchase_after_sale_detail);
        body_other.addView(mainView);
        paymentAmount = ViewUtils.findViewById(mainView, R.id.paymentAmount);
        payContext = ViewUtils.findViewById(mainView, R.id.payContext);
        paymentMethod = ViewUtils.findViewById(mainView, R.id.paymentMethod);
        goodsName = ViewUtils.findViewById(mainView, R.id.goods_name);
        goodsPrice = ViewUtils.findViewById(mainView, R.id.goods_price);
        buyCount = ViewUtils.findViewById(mainView, R.id.buy_count);
        orderNum = ViewUtils.findViewById(mainView, R.id.order_num);
        afterNum = ViewUtils.findViewById(mainView, R.id.after_num);
        orderCreateTime = ViewUtils.findViewById(mainView, R.id.order_create_time);
        afterSaleTime = ViewUtils.findViewById(mainView, R.id.after_sale_time);
        help = ViewUtils.findViewById(mainView, R.id.help);

        int id = getIntent().getIntExtra("data", -1);
        getData(id);
    }

    public void getData(int id) {
        if (id == -1) return;
        getRequestPresenter().customerFinanceRecordRefundDetail(id, new RequestCallback<CustomerFinanceRecordRefundDetail>() {
            @Override
            public void onSuc(CustomerFinanceRecordRefundDetail body) {
                CustomerFinanceRecordRefundDetail.DataBean bean = body.getData();
                if (bean == null) return;

                paymentAmount.setText("+" + bean.getRefundTotalMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                paymentMethod.setText(bean.getRefundQty().toString() + bean.getAvgUnit() + "   退款金额(扣除代金券后实付款)" + bean.getRefundTotalMoney().toString() + "元");

              /*  String name = bean.getProductName();
                int nameL = name == null ? 0 : name.length();
                String ss = bean.getProductNickName();
                if (ss == null) ss = "";
                int niceName = ss.length();

                if (nameL >= goodsNameLength) ss = "";
                else if (nameL + niceName > goodsNameLength) {
                    ss = ss.substring(0, goodsNameLength - nameL) + "..";
                }
                if (ss.length() == 0) {
                    ss = "(" + ss + ")";
                }*/
                String name = bean.getProductName();
                if (!TextUtils.isEmpty(bean.getProductNickName())) {
                    name = bean.getProductName()+"("+bean.getProductNickName()+")";
                }
                goodsName.setText(name);
                String price = String.valueOf(bean.getProductPrice()) + "元/" + bean.getProductUnit();
                if (bean.getLevelType() == 2) {
                    price += "(" + String.valueOf(bean.getLevel2Value()) + bean.getLevel2Unit() + ")";

                } else if (bean.getLevelType() == 3) {
                    price += "(" + String.valueOf(bean.getLevel2Value()) + bean.getLevel2Unit() + "*" + String.valueOf(bean.getLevel3Value() + bean.getLevel3Unit()) + ")";
                }

                goodsPrice.setText(price);


                buyCount.setText(bean.getOriginalQty().toString() + bean.getProductUnit() + "   金额" + String.valueOf(bean.getOriginalQty().multiply(bean.getProductPrice())) + "元");


                orderNum.setText(bean.getExtOrderId());
                afterNum.setText(bean.getAfterSaleApplicationNo());
                orderCreateTime.setText(bean.getOrderCreateTime());
                afterSaleTime.setText(bean.getAfterSaleApplyTime());
            }

            @Override
            public void onFailed(int customerFinanceRecordRefundDetail, String msg) {

            }
        });
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, PurchaseAfterSaleActivity.class);
        intent.putExtra("data", id);
        context.startActivity(intent);
    }

}
