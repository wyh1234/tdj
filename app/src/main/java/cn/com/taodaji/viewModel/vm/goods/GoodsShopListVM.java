package cn.com.taodaji.viewModel.vm.goods;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.JavaMethod;
import com.base.viewModel.BaseVM;
import com.base.utils.DateUtils;
import com.base.viewModel.BaseViewHolder;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsInformation;
import tools.extend.SimpleStringUtils;

public class GoodsShopListVM extends BaseVM {
    protected void dataBinding() {
        // putRelation(R.id.stock, "stock");
        putRelation(R.id.update_time, "updateTime");
//        putRelation(R.id.goods_nickName, "nickName");
//        putRelation(R.id.goods_content, "description");
        putRelation(R.id.goods_image, "image");
//        putRelation(R.id.goods_name, "name");
        putRelation(R.id.goods_unit, "unit");
        putRelation_more("name","nickName","isP","productType","productCriteria");
        putRelation(R.id.goods_max_price, "maxPrice");
        putRelation(R.id.goods_max_unit, "unit");
        putRelation(R.id.goods_price, "minPrice");

        putRelation(R.id.tv_isP, "isP");

        putRelation_more("productCriteria","productType");

    }

    protected void addOnclick() {
        putViewOnClick(R.id.shelves_up);
        putViewOnClick(R.id.goods_edit);
        putViewOnClick(R.id.goods_delete);
        putViewOnClick(R.id.tv_inventory_edit);
        putViewOnClick(R.id.tv_price_edit);
        putViewOnClick(R.id.view_reason);
    }

    @Override
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {
        GoodsInformation dataBean = (GoodsInformation) bean;
        if (pamstr.equals("name")){
            TextView goods_name_txt = viewHolder.findViewById(R.id.goods_name);
            //(String name,String nickName,int productType,int productCriteria,int isP)
            LogUtils.e(dataBean.getEntityId()+","+dataBean.getName()+","+dataBean.getNickName());
            SpecialStringBuilder sb=SimpleStringUtils.getTitleName(dataBean.getName(),dataBean.getNickName(),dataBean.getProductType(),dataBean.getProductCriteria(),dataBean.getIsP());
            goods_name_txt.setText(sb.getCharSequence());

            viewHolder.setVisibility(R.id.textView_21, View.GONE);
            viewHolder.setVisibility(R.id.goods_nickName, View.GONE);
            viewHolder.setVisibility(R.id.textView_22, View.GONE);
        }else  if (pamstr.equals("productCriteria")){
            //促销
            if (dataBean.getProductType() == 1) {
                viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
            } else {
                viewHolder.setVisibility(R.id.special_offer, View.GONE);
            }
            viewHolder.setVisibility(R.id.img_hot_sale, View.GONE);
            viewHolder.setVisibility(R.id.jinpin, View.GONE);

            if (dataBean.getProductType() == 1||dataBean.getProductType() == 3) {
                viewHolder.setVisibility(R.id.img_ad, View.VISIBLE);
            }else{
                viewHolder.setVisibility(R.id.img_ad, View.GONE);
            }
          /*  //促销
            if (dataBean.getProductType() == 1) {
                viewHolder.setVisibility(R.id.special_offer, View.VISIBLE);
            } else {
                viewHolder.setVisibility(R.id.special_offer, View.GONE);
            }
            //是否是热销
            View img_hot_sale = viewHolder.findViewById(R.id.img_hot_sale);
            if (dataBean.getProductType() == 3) {
                img_hot_sale.setVisibility(View.VISIBLE);
            } else {
                img_hot_sale.setVisibility(View.GONE);
            }
            viewHolder.setVisibility(R.id.jinpin, View.VISIBLE);

            //商品标准“1”：通货商品 “2”：精品商品 '
            if (dataBean.getProductCriteria()==2 ) {
                viewHolder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
            }else if (dataBean.getProductCriteria()==1 ){
                viewHolder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
            }else{
                viewHolder.setVisibility(R.id.jinpin, View.GONE);
            }*/
        }

    }

    @Override
    public void setValues(@NonNull View view, Object value) {
        if (view != null && view.getId() == R.id.update_time) {
            value = DateUtils.dateLongToString(Long.valueOf(value.toString()));
        }if (view.getId() == R.id.tv_isP) {
            view.setVisibility(View.GONE);
        }  else super.setValues(view, value);

    }
}
