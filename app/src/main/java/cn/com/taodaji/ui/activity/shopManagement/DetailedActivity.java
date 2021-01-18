package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

/**
 * 发布商品中的详细说明
 * Created by Administrator on 2018/4/19.
 */

public class DetailedActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("详细说明");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_sp_detailed);
        body_scroll.addView(mainView);
    }
}
