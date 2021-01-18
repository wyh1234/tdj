package cn.com.taodaji.viewModel.vm.goods;


import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;

import com.base.viewModel.BaseVM;
import com.base.utils.JavaMethod;

public class GoodsSimpleVM extends BaseVM {

    protected void dataBinding() {
        putRelation(R.id.goods_image, "image");
        putRelation(R.id.goods_name, "name");
        putRelation(R.id.goods_unit, "unit");
        putRelation(R.id.goods_price, "minPrice");
    }

    protected void addOnclick() {
        putViewOnClick(R.id.goods_image);
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.goods_image:
                    int productId = (int) JavaMethod.getValueFromBean(bean, "entityId");
                    GoodsDetailActivity.startActivity(view.getContext(), productId);
                    return true;
            }
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
