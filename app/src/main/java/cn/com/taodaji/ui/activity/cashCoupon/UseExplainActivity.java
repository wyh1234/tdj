package cn.com.taodaji.ui.activity.cashCoupon;

import android.support.v7.widget.Toolbar;
import android.view.View;


import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.ViewUtils;


public class UseExplainActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setTitle("代金券使用说明");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_use_explain);
        body_other.addView(mainView);

    }


}
