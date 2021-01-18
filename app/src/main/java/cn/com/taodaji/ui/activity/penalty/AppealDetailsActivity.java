package cn.com.taodaji.ui.activity.penalty;

import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class AppealDetailsActivity extends SimpleToolbarActivity {
    View mainView;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("申诉");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.appeal_details_layout);
        body_other.addView(mainView);
    }
}
