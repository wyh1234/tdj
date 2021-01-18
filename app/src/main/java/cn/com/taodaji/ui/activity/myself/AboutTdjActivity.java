package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;

import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

/**
 * Created by Administrator on 2016/12/24 0024.
 */
public class AboutTdjActivity extends SimpleToolbarActivity {
   private TextView txt_service_phone;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("关于淘大集");
    }

    private View mainView;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_about_tdj);
        txt_service_phone=mainView.findViewById(R.id.txt_service_phone);
        body_other.addView(mainView);
    }

    @Override
    protected void initData() {
        txt_service_phone.setText(PhoneUtils.getPhoneService());
    }
}
