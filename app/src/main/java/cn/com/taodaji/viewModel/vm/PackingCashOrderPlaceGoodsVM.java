package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.base.utils.JavaMethod;
import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

public class PackingCashOrderPlaceGoodsVM extends OrderPlaceGoodsDetailVM {
    @Override
    public void setValues(@NonNull View view, Object value){
        super.setValues(view,value);
        if (view.getId()==R.id.ll_cash_pledge) {
            view.setVisibility(View.VISIBLE);
        }
        if (view.getId()==R.id.v_line) {
            view.setVisibility(View.GONE);
        }
        if (view.getId()==R.id.line_spec) {
            view.setVisibility(View.GONE);
        }
        if (view.getId()==R.id.tv_money_sign) {
            view.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public <T> void setValues(BaseViewHolder viewHolder, @NonNull T bean) {
        super.setValues(viewHolder, bean);
        if (bean == null) return;
        if (bean instanceof OrderDetail.ItemsBean) {
            OrderDetail.ItemsBean goodsBean = (OrderDetail.ItemsBean) bean;

            if (PublicCache.loginPurchase != null) {
                viewHolder.setText(R.id.tv_supply_name, goodsBean.getStoreName());
            } else {
              //  viewHolder.setText(R.id.tv_supply_name, goodsBean.getCustomerName());
            }
        }
        viewHolder.setVisibility(R.id.v_line,View.GONE);
        viewHolder.setVisibility(R.id.line_spec,View.GONE);
        viewHolder.setVisibility(R.id.tv_money_sign,View.VISIBLE);
        viewHolder.setVisibility(R.id.tv_back_day,View.GONE);

    }
    @Override
    public void setValue(@NonNull TextView textView, @NonNull Object value) {
        if (textView.getId() == R.id.tv_textView1) {
            int currentStatus = JavaMethod.transformClass(value, int.class);
            switch(currentStatus){  // String[] title = {"处理中押金", "未退押金","已支付押金", "已退押金"}; 1 2 3 4
                case 1 : //当前押金列表请求状态  0-未退，1-处理中，2-已退，3-已支付
                    textView.setText("退:");
                    break;
                case 0 :
                    textView.setText("未退:");
                    break;
                case 3 :
                    textView.setText("已支付:");
                    break;
                case 2 :
                    textView.setText("已退:");
                    break;

            }

        } else super.setValue(textView, value);
    }



    @Override
    protected void dataBinding() {
        putRelation(R.id.goods_image, "image");
//        putRelation(R.id.goods_name, "name");
        putRelation(R.id.goods_unit, "unit");
        putRelation(R.id.unit, "unit");
        putRelation(R.id.goods_price, "price");
        putRelation(R.id.cart_price, "totalPrice");
        putRelation(R.id.order_no, "qrCodeId");
        putRelation(R.id.level2Value, "level2Value");
        putRelation(R.id.level2Unit, "level2Unit");
        putRelation(R.id.level3Value, "level3Value");
        putRelation(R.id.level3Unit, "level3Unit");


        putRelation(R.id.tv_isP, "isP");
        putRelation("amount", R.id.goods_count_x);

        putRelation("avgUnit", R.id.goods_unit2);
        putRelation_more("levelType");
        putRelation_more("name", "nickName");
        putRelation_more("productCriteria", "productType");


        putRelation("packageName", R.id.tv_cash_pledge_name);
//        putRelation("packageStatus", R.id.tv_textView1); //押金状态
        putRelation("foregift", R.id.tv_cash_pledge_price);//押金单价
//        putRelation("packageNum", R.id.tv_cash_pledge_count);//包装数量
//        putRelation("packageFee", R.id.tv_cash_pledge_sum);//订购或未退押金金额
        putRelation("isForegift", R.id.ll_cash_pledge); // 是否收取押金 0-不收，1-收


//        putRelation("storeName",R.id.tv_supply_name);
        putRelation("currentNum", R.id.tv_cash_pledge_count);
        putRelation("currentFee", R.id.tv_cash_pledge_sum);
        putRelation("currentStatus", R.id.tv_textView1);
    }


    @Override
    protected void addOnclick() {
        super.addOnclick();
        putViewOnClick(R.id.tv_go_back_money);
    }


}
