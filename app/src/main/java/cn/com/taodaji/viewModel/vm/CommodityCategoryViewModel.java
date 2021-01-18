package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;
import cn.com.taodaji.R;

/**
 * Created by Administrator on 2017/2/4 0004.
 */
public class CommodityCategoryViewModel extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.imageUrl, "imageUrl");
        putRelation(R.id.categoryName, "categoryName");

        putRelation(R.id.item_view, "selected");
    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.imageUrl);
        putViewOnClick(R.id.item_view);
    }
}
