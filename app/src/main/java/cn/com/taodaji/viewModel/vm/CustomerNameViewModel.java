package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

/**
 * Created by Administrator on 2017/2/6 0006.
 */
public class CustomerNameViewModel extends BaseVM {

    @Override
    protected void dataBinding() {
        putRelation(R.id.goods_name, "name");
        putRelation(R.id.goods_description, "description");
    }

    @Override
    protected void addOnclick() {

    }
}
