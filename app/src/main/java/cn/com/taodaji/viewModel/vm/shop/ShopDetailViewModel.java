package cn.com.taodaji.viewModel.vm.shop;

import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.utils.JavaMethod;


public class ShopDetailViewModel extends BaseVM {

    @Override
    protected void dataBinding() {
        putRelation(R.id.market_address, "marketAddress");
        // putRelation(R.id.goods_memberPrice, "memberPrice");
        putRelation(R.id.shop_logo, "logoImageUrl");
        putRelation(R.id.shop_name, "name");
        putRelation(R.id.orderNumber, "orderNumber");
        putRelation(R.id.productNumber, "productNumber");
        putRelation(R.id.shop_market, "marketName");
        putRelation(R.id.shop_market_id, "marketNo");

        putRelation("storeScore", R.id.shop_evaluate_value);
        putRelation("favoriteCount",R.id.tv_enshrine_count);

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.shop_logo);
        putViewOnClick(R.id.stroll_shop);
        putViewOnClick(R.id.shop_name_group);
        putViewOnClick(R.id.foodQuality);
        putViewOnClick(R.id.licenceDoc);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            if (bean == null) return true;
            //店铺的id
            Object value_store = JavaMethod.getFieldValue(bean, "store");
            Object url;
            switch (view.getId()) {
                case R.id.realName_check:
                    url = JavaMethod.getFieldValue(bean, "idcardImageUrl");

                    if (url == null || "".equals(url.toString())) break;
//                    ZoomImageActivity.startActivity(view.getContext(), url);

                    ImagesActivity.startActivity(view, url);
                    break;
                case R.id.foodQuality:
                    url = JavaMethod.getFieldValue(bean, "foodQualiynoImageUrl");
                    if (url == null || "".equals(url.toString())) break;
//                    ZoomImageActivity.startActivity(view.getContext(), url);
                    ImagesActivity.startActivity(view, url);
                    break;
                case R.id.licenceDoc:
                    url = JavaMethod.getFieldValue(bean, "licenceDocurl");
                    if (url == null || "".equals(url.toString())) break;
//                    ZoomImageActivity.startActivity(view.getContext(), url);
                    ImagesActivity.startActivity(view, url);
                    break;
                case R.id.shop_logo:
                case R.id.stroll_shop:
                case R.id.shop_name_group:
                    shopDeatailShow(view, value_store);
                    break;
            }
            return true;
        } else return super.onItemClick(view, onclickType, position, bean);
    }

    private void shopDeatailShow(View view, Object value_store) {
        if (value_store != null && view != null) {
            ShopDetailInformationActivity.startActivity(view.getContext(), (int) value_store);
        }
    }

    //初始化时控制显示
    @Override
    public <T> void setValues(BaseViewHolder viewHolder, T bean) {
        super.setValues(viewHolder, bean);
        initRealName_check(viewHolder, bean);
        initFoodQuality(viewHolder, bean);
        initCertification(viewHolder, bean);
    }

    private void initRealName_check(BaseViewHolder viewHolder, Object bean) {
        Object idcardNumber = JavaMethod.getValueFromBean(bean, "idcardNumber");
        Object idcardImageUrl = JavaMethod.getValueFromBean(bean, "idcardImageUrl");
        if (idcardNumber == null || "".equals(idcardNumber) || idcardImageUrl == null || "".equals(idcardImageUrl))
            viewHolder.setViewSelected(R.id.realName_check, false);
        else viewHolder.setViewSelected(R.id.realName_check, true);
    }

    private void initFoodQuality(BaseViewHolder viewHolder, Object bean) {
        Object foodQualiynoImageUrl = JavaMethod.getValueFromBean(bean, "foodQualiynoImageUrl");
        if (foodQualiynoImageUrl == null || "".equals(foodQualiynoImageUrl))
            viewHolder.setViewSelected(R.id.foodQuality, false);
        else viewHolder.setViewSelected(R.id.foodQuality, true);
    }

    private void initCertification(BaseViewHolder viewHolder, Object bean) {
        Object licenceDocurl = JavaMethod.getValueFromBean(bean, "licenceDocurl");
        if (licenceDocurl == null || "".equals(licenceDocurl))
            viewHolder.setViewSelected(R.id.licenceDoc, false);
        else viewHolder.setViewSelected(R.id.licenceDoc, true);
    }
}
