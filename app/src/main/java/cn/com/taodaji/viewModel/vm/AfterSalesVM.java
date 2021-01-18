package cn.com.taodaji.viewModel.vm;


import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.utils.JavaMethod;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.RefundDetail;

public class AfterSalesVM extends BaseVM {

    @Override
    protected void dataBinding() {
        putRelation(R.id.order_num, "receive_hotel_name");
        putRelation(R.id.customerLogo, "customer_img");
        putRelation(R.id.customerName, "receive_hotel_name");
        //  putAssignmentString(R.id.order_state, "status");
        putRelation(R.id.goods_image, "product_img");
//        putRelation(R.id.goods_name, "name");
//        putRelation(R.id.goods_nickName, "nick_name");
        putRelation(R.id.goods_price, "price");


        putRelation(R.id.goods_unit, "unit");
        putRelation(R.id.goods_unit2, "unit");


        putRelation(R.id.unit, "avg_unit");

        putRelation(R.id.level2Value, "level_2_value");
        putRelation(R.id.level2Unit, "level_2_unit");
        putRelation(R.id.level3Value, "level_3_value");
        putRelation(R.id.level3Unit, "level_3_unit");

        putRelation(R.id.goods_count, "original_amount");
        putRelation(R.id.cart_price, "original_total_price");
        putRelation(R.id.refund_price, "total_price");
        putRelation(R.id.order_time, "order_pay_time");
        putRelation(R.id.count_image, "original_total_price");
        putRelation(R.id.count_sum, "amount");
        putRelation(R.id.after_sales_type, "apply_type");

        putRelation_more("name", "nick_name");

        putRelation("isForegift", R.id.ll_cash_pledge); // 是否收取押金 0-不收，1-收
        putRelation("packageName", R.id.tv_cash_pledge_name);
        putRelation("foregift", R.id.tv_cash_pledge_price);//押金单价
    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.order_ok);
    }

    private static SparseArrayCompat<String> stringSparseArray() {
        SparseArrayCompat<String> sparseArrayCompat = new SparseArrayCompat<>();
        sparseArrayCompat.put(1, "售后申请");
        sparseArrayCompat.put(2, "退款中");
        sparseArrayCompat.put(3, "换货中");
        sparseArrayCompat.put(4, "拒绝退款");
        sparseArrayCompat.put(5, "拒绝换货");
        sparseArrayCompat.put(6, "售后完成");
        sparseArrayCompat.put(7, "补货中");
        sparseArrayCompat.put(8, "拒绝补货");
        return sparseArrayCompat;
    }

    @Override
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {
        RefundDetail goodsBean;
        if (bean instanceof RefundDetail) goodsBean = (RefundDetail) bean;
        else return;
        if (pamstr.equals("name")) {
            String goods_name = goodsBean.getName();
            if (!TextUtils.isEmpty(goodsBean.getNick_name())) {
                goods_name = goodsBean.getName() + "(" + goodsBean.getNick_name() + ")";
            }
            viewHolder.setText(R.id.goods_name, goods_name);

            viewHolder.setVisibility(R.id.textView_21, View.GONE);
            viewHolder.setVisibility(R.id.goods_nickName, View.GONE);
            viewHolder.setVisibility(R.id.textView_22, View.GONE);
        }

    }


    @Override
    public <T> void setValues(BaseViewHolder holder, T bean) {
        super.setValues(holder, bean);
        RefundDetail goodsBean;
        if (bean instanceof RefundDetail) goodsBean = (RefundDetail) bean;
        else return;

        if (PublicCache.loginPurchase != null) {
            holder.setImageRes(R.id.iv_cash_pledge, R.mipmap.ic_cash_pledge_pur);
        } else holder.setImageRes(R.id.iv_cash_pledge, R.mipmap.ic_cash_pledge_sup);
        //多规格
        if (goodsBean.getLevel_type() == 1) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.level2Value, View.GONE);
            holder.setVisibility(R.id.level2Unit, View.GONE);

            holder.setVisibility(R.id.textView1, View.GONE);
            holder.setVisibility(R.id.textView2, View.GONE);
        } else if (goodsBean.getLevel_type() == 2) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.level3Value, View.VISIBLE);
            holder.setVisibility(R.id.specification_split, View.VISIBLE);
            holder.setVisibility(R.id.level3Unit, View.VISIBLE);

            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);
        }
    }

    @Override
    public void setValues(@NonNull View view, Object value) {
        switch (view.getId()) {
            case R.id.ll_cash_pledge:
                int ss = JavaMethod.transformClass(value, int.class);
                // 是否收取押金 0-不收，1-收
                if (ss == 0) view.setVisibility(View.GONE);
                else view.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_isP:
                if (!"".equals(value.toString())) {
                    int isp = JavaMethod.transformClass(int.class, value);
                    if (isp == 0) view.setVisibility(View.GONE);
                    else view.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.after_sales_type:
                int type = Integer.parseInt(value.toString());
                String string = PublicCache.getAfterSaleType().getValueOfKey(type);
                if (string == null) string = "";
                if (string.length() > 2) string = string.substring(0, 2);
                super.setValues(view, string + "：");
                break;
            default:
                super.setValues(view, value);
        }
    }

    @Override
    public void setValue(@NonNull TextView textView, @NonNull Object value) {
        if (textView.getId() == R.id.tv_textView1) {
            int packageStatus = JavaMethod.transformClass(value, int.class);
            //押金状态  -1 订购，0 - 未退, 1-已退完
            switch (packageStatus) {
                case -1:
                    super.setValue(textView, "订购");
                    break;
                case 0:
                    super.setValue(textView, "未退");
                    break;
                default:
                    super.setValue(textView, "已退完");
                    break;
            }

        } else super.setValue(textView, value);
    }

}
