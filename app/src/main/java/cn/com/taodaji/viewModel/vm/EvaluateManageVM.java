package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;
import tools.extend.SimpleStringUtils;

public class EvaluateManageVM extends BaseVM {
    @Override
    protected void dataBinding() {
//        putRelation(R.id.customerLogo, "customerLogo");
//        putViewTag(R.id.customerLogo, ROUND);
//        putRelation(R.id.customerName, "customerName");
        putRelation(R.id.evaluate_time, "createTime");
        putRelation(R.id.evaluate_text, "creditContent");
        putRelation(R.id.evaluate_response_time, "replyTime");
        putRelation(R.id.evaluate_response_text, "replyContent");

        //putRelation(R.id.goods_unit2,);
        putRelation(R.id.level2Value, "level_2_value");
        putRelation(R.id.level2Unit, "level_2_unit");
        putRelation(R.id.level3Value, "level_3_value");
        putRelation(R.id.level3Unit, "level_3_unit");

        putRelation(R.id.goods_image, "productImg");
//        putRelation(R.id.goods_name, "productName");
        putRelation(R.id.goods_unit, "unit");
        putRelation(R.id.goods_price, "price");
//        putRelation(R.id.goods_nickName, "nickName");
        putRelation(R.id.goods_count, "qty");
        putRelation(R.id.cart_price, "totalPrice");

        putRelation(R.id.order_num, "orderNo");

        putRelation_more("productName","nickName","isP","productType","productCriteria");
        putRelation_more("productCriteria","productType");
        putRelation(R.id.tv_isP, "isP");

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.reply);
        putViewOnClick(R.id.txt_go_update_evaluate);
    }

    @Override
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {
        if (bean instanceof EvaluationList.DataBean.ItemsBean) {
            EvaluationList.DataBean.ItemsBean abean = (EvaluationList.DataBean.ItemsBean) bean;
            viewHolder.setVisibility(R.id.ll_cash_pledge, View.GONE);

            if (pamstr.equals("productName")){
                TextView goods_name_txt = viewHolder.findViewById(R.id.goods_name);
                //(String name,String nickName,int productType,int productCriteria,int isP)
                SpecialStringBuilder sb=SimpleStringUtils.getTitleName(abean.getProductName(),abean.getNickName(),abean.getProductType(),abean.getProductCriteria(),abean.getIsP());
                goods_name_txt.setText(sb.getCharSequence());

                viewHolder.setVisibility(R.id.textView_21, View.GONE);
                viewHolder.setVisibility(R.id.goods_nickName, View.GONE);
                viewHolder.setVisibility(R.id.textView_22, View.GONE);
            }else  if (pamstr.equals("productCriteria")){
                //促销
                if (abean.getProductType() == 1) {
                    viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
                } else {
                    viewHolder.setVisibility(R.id.special_offer, View.GONE);
                }
                viewHolder.setVisibility(R.id.img_hot_sale, View.GONE);
                viewHolder.setVisibility(R.id.jinpin, View.GONE);

                if (abean.getProductType() == 1||abean.getProductType() == 3) {
                    viewHolder.setVisibility(R.id.img_ad, View.VISIBLE);
                }else{
                    viewHolder.setVisibility(R.id.img_ad, View.GONE);
                }

               /* //促销
                if (abean.getProductType() == 1) {
                    viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
                } else {
                    viewHolder.setVisibility(R.id.special_offer, View.GONE);
                }
                //是否是热销
                View img_hot_sale = viewHolder.findViewById(R.id.img_hot_sale);
                if (abean.getProductType() == 3) {0
                    img_hot_sale.setVisibility(View.VISIBLE);
                } else {
                    img_hot_sale.setVisibility(View.GONE);
                }
                viewHolder.setVisibility(R.id.jinpin, View.VISIBLE);

                //商品标准“1”：通货商品 “2”：精品商品 '
                if (abean.getProductCriteria()==2 ) {
                    viewHolder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
                }else if (abean.getProductCriteria()==1 ) {
                    viewHolder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
                }else{
                    viewHolder.setVisibility(R.id.jinpin, View.GONE);
                }*/
            }


        }

    }
    @Override
    public void setValues(@NonNull View view, Object value) {
        if (view == null) return;

        if (R.id.evaluate_time == view.getId() && value != null) {
            String ss = value.toString();
            if (ss.length() >= 10) {
                value = ss.substring(0, 10);
            }
        }
        if (view.getId() == R.id.tv_isP) {
            view.setVisibility(View.GONE);
          /*  if (value != null && !"".equals(value.toString())) {
                int isp = JavaMethod.transformClass(int.class, value);
                if (isp == 0) view.setVisibility(View.GONE);
                else view.setVisibility(View.VISIBLE);
            }*/
        }

        super.setValues(view, value);
    }


    @Override
    public <T> void setValues(BaseViewHolder holder, T abean) {
        super.setValues(holder, abean);


        if (abean instanceof EvaluationList.DataBean.ItemsBean) {
            EvaluationList.DataBean.ItemsBean bean = (EvaluationList.DataBean.ItemsBean) abean;

            boolean isHide = true;
            /** isVirtual 0匿名1正常*/
            if (bean != null) {
                if (bean.getIsVirtual() == 1) {
                    isHide = false;
                } else {
                    isHide = true;
                }
                if (PublicCache.loginPurchase != null && bean.getCustomerId() == PublicCache.loginPurchase.getEntityId()) {//采购商的登录数据
                    isHide = false;
                }
                if (PublicCache.loginSupplier != null && bean.getCustomerId() == PublicCache.loginSupplier.getEntityId()) {//供应商的登录数据
                    isHide = false;
                }
            }
            if (isHide) {
                holder.setImageRes(R.id.customerLogo, R.mipmap.hide_portrait);
                holder.setText(R.id.customerName, "匿名用户");
            } else {
                holder.setImageRes(R.id.customerLogo, bean.getCustomerLogo());
                holder.setText(R.id.customerName, bean.getCustomerName());
            }

        } else return;

       /* //多规格
        if (Evbean.getLevel_type() == 1) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.level2Value, View.GONE);
            holder.setVisibility(R.id.level2Unit, View.GONE);

            holder.setVisibility(R.id.textView1, View.GONE);
            holder.setVisibility(R.id.textView2, View.GONE);
        } else if (Evbean.getLevel_type() == 2) {
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
        }*/
    }
}
