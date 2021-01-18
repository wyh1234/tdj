package cn.com.taodaji.viewModel.vm.goods;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.ui.activity.homepage.ContrastPriceActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.shopManagement.LookQualificationsActivity;
import tools.extend.SimpleStringUtils;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.UIUtils;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

public class GoodsInformationVM extends BaseVM {
    private  boolean b;

    public  GoodsInformationVM(){

    }

    public  GoodsInformationVM(boolean b){
        this.b=b;

    }
    @Override
    protected void dataBinding() {
        putRelation(R.id.goods_image, "image");
        // putRelation(R.id.goods_name, "name");

        putRelation(R.id.goods_unit, "unit");

        putRelation(R.id.unit, "unit");
        putRelation(R.id.goods_max_unit, "unit");
        putRelation(R.id.goods_price, "minPrice");
        putRelation(R.id.salesNumber, "monthSaleNumbers");
        //  putRelation(R.id.goods_max_price,"maxPrice");

        putRelation(R.id.avgPrice, "avgPrice");
        putRelation(R.id.avgUnit, "avgUnit");

//        putRelation(R.id.goods_nickName, "nickName");
//        putRelation(R.id.goods_content, "description");
        // putRelation(R.id.goods_count, "productQty");
        putRelation(R.id.goods_saleCount, "monthSaleNumbers");
        putRelation(R.id.shop_name, "storeName");

        putRelation(R.id.tv_isP, "isP");

        putRelation_more("name", "nickName","isP","productType","productCriteria");

        putRelation_more("productCriteria", "productType");

        putRelation_more("packageName");
    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.goods_image);
        putViewOnClick(R.id.tv_spec_help);
        putViewOnClick(R.id.look_document);
        putViewOnClick(R.id.contrast_price);
        putViewOnClick(R.id.ll_shop);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object dataBean) {
        if (onclickType == 0) {
            GoodsInformation bean = (GoodsInformation) dataBean;
            if (bean == null) return true;
            switch (view.getId()) {
                case R.id.ll_shop:
                    GoodsDetailActivity.startActivity(view.getContext(), bean.getEntityId());
                    break;
                case R.id.goods_image:
                    GoodsDetailActivity.startActivity(view.getContext(), bean.getEntityId());
                    break;
                case R.id.look_document:

                    if (TextUtils.isEmpty(bean.getCredentialsImage())) {
                        UIUtils.showToastSafesShort("商家还没有上传资质");
                        break;
                    }
                    Intent intent = new Intent(view.getContext(), LookQualificationsActivity.class);
                    intent.putExtra("productId", bean.getEntityId());
                    intent.putExtra("hide", true);
                    view.getContext().startActivity(intent);
                    break;
                case R.id.contrast_price:
                    ContrastPriceActivity.startActivity(view.getContext(), bean);
                    break;
            }
            return true;
        }
        return super.onItemClick(view, onclickType, position, dataBean);
    }

    @Override
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {
        GoodsInformation dataBean = (GoodsInformation) bean;
        switch (pamstr) {
            case "packageName":
                if (TextUtils.isEmpty(dataBean.getPackageName())) {
                    viewHolder.setText(R.id.tv_cash, "");
                } else
                    viewHolder.setText(R.id.tv_cash, "(另收押金" + dataBean.getForegift() + "元)");
                break;
            case "name":

                TextView goods_name_txt = viewHolder.findViewById(R.id.goods_name);
                //(String name,String nickName,int productType,int productCriteria,int isP)
                SpecialStringBuilder sb;
                if (b){
                     sb=SimpleStringUtils.getTitleNames(dataBean.getName(),dataBean.getNickName(),dataBean.getProductType(),dataBean.getProductCriteria(),dataBean.getIsP());

                }else {
                     sb=SimpleStringUtils.getTitleName(dataBean.getName(),dataBean.getNickName(),dataBean.getProductType(),dataBean.getProductCriteria(),dataBean.getIsP());

                }
                goods_name_txt.setText(sb.getCharSequence());

                viewHolder.setVisibility(R.id.textView_21, View.GONE);
                viewHolder.setVisibility(R.id.goods_nickName, View.GONE);
                viewHolder.setVisibility(R.id.textView_22, View.GONE);
                break;
            case "productCriteria":
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

            /*
                //是否是热销
                View img_hot_sale = viewHolder.findViewById(R.id.img_hot_sale);
                if (dataBean.getProductType() == 3) {
                    img_hot_sale.setVisibility(View.VISIBLE);
                } else {
                    img_hot_sale.setVisibility(View.GONE);
                }


                //商品标准“1”：通货商品 “2”：精品商品 '
                if (dataBean.getProductCriteria() == 2) {
                    viewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_jin_red);
                } else if (dataBean.getProductCriteria() == 1) {
                    viewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_tong_blue);
                } else {
                    viewHolder.setVisibility(R.id.jinpin, View.GONE);
                }*/

                break;
        }

    }

    @Override
    public void setValues(@NonNull View view, Object value) {
        if (view.getId() == R.id.tv_isP) {
            view.setVisibility(View.GONE);
          /*  if (value != null && !"".equals(value.toString())) {
                int isp = JavaMethod.transformClass(int.class, value);
                if (isp == 0) view.setVisibility(View.GONE);
                else view.setVisibility(View.VISIBLE);
            }*/
        } else super.setValues(view, value);
    }

    @Override
    public <T> void setValues(BaseViewHolder viewHolder, T bean) {
        GoodsInformation gc = (GoodsInformation) bean;
        if (gc == null) return;
        //   int productId = gc.getEntityId();
        if (TextUtils.isEmpty(gc.getCredentialsImage())) {
            viewHolder.setVisibility(R.id.look_document, View.GONE);
        } else viewHolder.setVisibility(R.id.look_document, View.VISIBLE);

        //   JavaMethod.setFieldValue(bean, getFieldName(R.id.goods_count), CartModel.getInstance().getCount(productId));
        super.setValues(viewHolder, bean);
    }


}
