package cn.com.taodaji.ui.activity.login;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class SystemFixActivity extends SimpleToolbarActivity implements View.OnClickListener{
    private View view;

    @Override
    protected void titleSetting(Toolbar toolbar) {

    }

    @Override
    protected void initMainView() {
        view = getLayoutView(R.layout.activity_system_fix);
        ViewUtils.findViewById(view, R.id.refresh_load).setOnClickListener(this);
        body_other.addView(view);
    }

    @Override
    public void onClick(View v) {
        UIUtils.showToastSafe("重新加载中");
    }
}
