package cn.com.taodaji.ui.activity.homepage;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018/10/24.
 */
public class RichScanHintActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setTitle("淘大集扫码提示");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_rich_scan_hint);
        body_other.addView(mainView);
        TextView tv_mess = findViewById(R.id.tv_mess);
        String ss = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(ss)) {
            tv_mess.setText(ss);
        }
    }
}
