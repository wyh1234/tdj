package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

/**
 * Created by Administrator on 2017/2/6 0006.
 */
public class PickFoodViewModel extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.group_text, "categoryName");
        putRelation(R.id.image_name, "name");
        putRelation(R.id.group_icon, "selected");

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.group_item);
        putViewOnClick(R.id.image_name);
        putViewOnClick(R.id.item_view);
    }
}
