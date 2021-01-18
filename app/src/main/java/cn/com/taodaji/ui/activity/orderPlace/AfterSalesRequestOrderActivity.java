package cn.com.taodaji.ui.activity.orderPlace;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.viewModel.BaseViewModel;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.AfterSalesRequestOrderViewModel;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018/10/25.
 */
public class AfterSalesRequestOrderActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("当前送达商品");
    }

    @Override
    public BaseViewModel setViewModel() {
        return new AfterSalesRequestOrderViewModel(this);
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_after_sales_request_order);
        body_other.addView(mainView);
    }

}
