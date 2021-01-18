package cn.com.taodaji.ui.activity.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class ShareRewardRuleActivity extends SimpleToolbarActivity {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("分享奖励规则");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_share_reward_rule);
        body_other.addView(mainView);
    }
}
