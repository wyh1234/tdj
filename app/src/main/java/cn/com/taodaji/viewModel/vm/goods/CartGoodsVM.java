package cn.com.taodaji.viewModel.vm.goods;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.viewModel.BaseVM;
import com.base.utils.JavaMethod;
import com.base.viewModel.BaseViewHolder;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import tools.extend.SimpleStringUtils;

public class CartGoodsVM extends BaseVM {


    protected void dataBinding() {
        putRelation(R.id.goods_image, "productImage");
//        putRelation(R.id.goods_name, "productName");

        putRelation(R.id.goods_price, "productPrice");
//        putRelation(R.id.goods_nickName, "nickName");
        putRelation(R.id.item_select, "selected");
        putRelation(R.id.unit, "productUnit");
        putRelation(R.id.goods_unit, "productUnit");

        putRelation(R.id.level1Unit, "level1Unit");
        putRelation(R.id.level2Value, "level2Value");
        putRelation(R.id.level2Unit, "level2Unit");
        putRelation(R.id.level3Value, "level3Value");
        putRelation(R.id.level3Unit, "level3Unit");

//        putRelation(R.id.tv_isP, "isP");

        putRelation("countXg", R.id.purchase_restrictions);
        //putRelation_more("productName", "nickName","isP","productType","productCriteria");

        putRelation_more("productCriteria", "typeXg");

        putRelation_more("productQty", "foregift");
        putRelation_more("packageName");

    }


//    @Override
//    public void setValues(@NonNull View view, Object value) {
//        if (view.getId() == R.id.tv_isP) {
//           /* if (value != null && !"".equals(value.toString())) {
//                int isp = JavaMethod.transformClass(int.class, value);
//                if (isp == 0) view.setVisibility(View.GONE);
//                else view.setVisibility(View.VISIBLE);
//            }*/
//            view.setVisibility(View.GONE);
//        } else super.setValues(view, value);
//    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.item_select);
        //  putViewOnClick(R.id.item_view);
        putViewOnClick(R.id.after_sales);
        putViewOnClick(R.id.goods_image);
    }

    @Override
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {
        CartGoodsBean dataBean = (CartGoodsBean) bean;
  /*      if (pamstr.equals("productName")) {

            TextView goods_name_txt = viewHolder.findViewById(R.id.goods_name);
            //(String name,String nickName,int productType,int productCriteria,int isP)
            SpecialStringBuilder sb=SimpleStringUtils.getTitleName(dataBean.getProductName(),dataBean.getNickName(),dataBean.getProductType(),dataBean.getProductCriteria(),dataBean.getIsP());
            goods_name_txt.setText(sb.getCharSequence());
            viewHolder.setVisibility(R.id.textView_21, View.GONE);
            viewHolder.setVisibility(R.id.goods_nickName, View.GONE);
            viewHolder.setVisibility(R.id.textView_22, View.GONE);

        } else*/ if (pamstr.equals("productCriteria")) {
            //促销
            if (dataBean.getProductType() == 1) {
                viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
                if (dataBean.getCountXg() > 100) {
                    viewHolder.setVisibility(R.id.line_xq, View.GONE);
                } else {
                    viewHolder.setVisibility(R.id.line_xq, View.VISIBLE);
                }
            } else {
                viewHolder.setVisibility(R.id.line_xq, View.GONE);
                viewHolder.setVisibility(R.id.special_offer, View.GONE);
            }
            viewHolder.setVisibility(R.id.img_hot_sale, View.GONE);
            viewHolder.setVisibility(R.id.jinpin, View.GONE);

            if (dataBean.getProductType() == 1||dataBean.getProductType() == 3) {
                viewHolder.setVisibility(R.id.img_ad, View.VISIBLE);
            }else{
                viewHolder.setVisibility(R.id.img_ad, View.GONE);
            }
           /* //促销
            if (dataBean.getCountXg() == 1) {
                viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
                if (dataBean.getCountXg() > 100) {
                    viewHolder.setVisibility(R.id.line_xq, View.GONE);
                } else {
                    viewHolder.setVisibility(R.id.line_xq, View.VISIBLE);
                }

            } else {
                viewHolder.setVisibility(R.id.line_xq, View.GONE);
                viewHolder.setVisibility(R.id.special_offer, View.GONE);
            }
            //精品
            View img_hot_sale = viewHolder.findViewById(R.id.img_hot_sale);
            if (dataBean.getTypeXg() == 3) {
                img_hot_sale.setVisibility(View.VISIBLE);
            } else {
                img_hot_sale.setVisibility(View.GONE);
            }
            viewHolder.setVisibility(R.id.jinpin, View.VISIBLE);

            //商品标准“1”：通货商品 “2”：精品商品 '
            if (dataBean.getProductCriteria() == 2) {
                viewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_jin_red);
            } else if (dataBean.getProductCriteria() == 1) {
                viewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_tong_blue);
            } else {
                viewHolder.setVisibility(R.id.jinpin, View.GONE);
            }*/
        } else if (pamstr.equals("packageName")) {
            if (TextUtils.isEmpty(dataBean.getPackageName())) {
                viewHolder.setVisibility(R.id.ll_cash_pledge, View.GONE);
            } else {
                viewHolder.setVisibility(R.id.ll_cash_pledge, View.VISIBLE);
                viewHolder.setText(R.id.tv_cash_pledge_name, dataBean.getPackageName());
            }
        } else if (pamstr.equals("productQty")) {
            viewHolder.setText(R.id.tv_cash_pledge_sum, dataBean.getForegift().multiply(BigDecimal.valueOf(dataBean.getProductQty())));
            viewHolder.setText(R.id.tv_cash_pledge_count, dataBean.getProductQty());
            viewHolder.setText(R.id.tv_cash_pledge_price, dataBean.getForegift());
        }


    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {

            switch (view.getId()) {
                case R.id.goods_image:
                    CartGoodsBean data = (CartGoodsBean) bean;
                    if (data == null) return true;
                    if (data.getStatus() != 1 || data.getStoreStatus() != 0) return true;
                    GoodsDetailActivity.startActivity(view.getContext(), data.getProductId());
                    break;
            }

            return true;
        } else return super.onItemClick(view, onclickType, position, bean);
    }
}
