package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class MarketListViewModel extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.market_image, "image");
        putRelation(R.id.market_name, "name");
    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.market_image);
    }
}
