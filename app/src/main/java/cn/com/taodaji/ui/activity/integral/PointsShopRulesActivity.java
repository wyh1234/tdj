package cn.com.taodaji.ui.activity.integral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class PointsShopRulesActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.red_f0);
        setToolBarColor(R.color.red_f0);
        simple_title.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_points_shop_rules);
        body_other.addView(mainView);
    }
}
