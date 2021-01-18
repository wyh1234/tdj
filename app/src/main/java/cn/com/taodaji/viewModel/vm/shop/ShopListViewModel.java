package cn.com.taodaji.viewModel.vm.shop;


import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;

import com.base.viewModel.BaseVM;
import com.base.utils.JavaMethod;

public class ShopListViewModel extends BaseVM {


    @Override
    protected void dataBinding() {
        putRelation(R.id.shop_logo, "logoImageUrl");
        putRelation(R.id.shop_name, "name");
        putRelation(R.id.orderNumber, "orderNumber");
        putRelation(R.id.productNumber, "productNumber");
        putRelation(R.id.shop_market, "marketName");
        putRelation(R.id.shop_market_id, "marketNo");

        putRelation("storeScore", R.id.shop_evaluate_value);
        putRelation("favoriteCount",R.id.tv_enshrine_count);
    }

    protected void addOnclick() {
        putViewOnClick(R.id.shop_logo);
        putViewOnClick(R.id.stroll_shop);
        putViewOnClick(R.id.shop_name_group);
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            //店铺的id
            Object value_store = JavaMethod.getValueFromBean(bean, "store");
            switch (view.getId()) {
                case R.id.shop_logo:
                case R.id.stroll_shop:
                case R.id.shop_name_group:
                    shopDeatailShow(view, value_store);
                    break;
            }
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }

    private void shopDeatailShow(View view, Object value_store) {
        if (value_store != null && view != null) {
            ShopDetailInformationActivity.startActivity(view.getContext(), (int) value_store);
        }
    }
}
