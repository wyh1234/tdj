package cn.com.taodaji.ui.activity.orderPlace;

import android.support.v7.widget.Toolbar;

import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018-03-17.
 */

public class OrderPostActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("提交订单");
    }

    @Override
    protected void initMainView() {

    }
}
