package cn.com.taodaji.ui.activity.myself;


import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.base.utils.NotificationsUtils;
import com.base.utils.ViewUtils;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;
import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by Administrator on 2018/1/11.
 * 设置里的消息通知
 */

public class SelfNewsActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("新消息提醒");
    }

    private View mainView;
    private Switch switch1;
    private Switch switch2;
    private Boolean is_open_news;
    private SharedPreferences sp;
    private PushAgent mPushAgent;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_self_news);
        body_scroll.addView(mainView);
        mPushAgent = PushAgent.getInstance(this);
        switch2 = ViewUtils.findViewById(mainView, R.id.switch2);
        sp = getSharedPreferences("notification_message", 0);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("message", isChecked);
                //提交edit
                edit.apply();
                if (isChecked && !NotificationsUtils.isNotificationOpen(getBaseActivity())) {
                    //打开通知权限
                    NotificationsUtils.startNotificationSetting(getBaseActivity());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean bb = NotificationsUtils.isNotificationOpen(getBaseActivity());
        boolean isOpen = sp.getBoolean("message", bb);
        //如果系统给了通知权限，
        if (bb && isOpen) {
            switch2.setChecked(true);
            //打开友盟推送
            mPushAgent.enable(null);
        } else {
            //关闭友盟推送
            switch2.setChecked(false);
            //mPushAgent.disable(null);
            mPushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        }
    }
}
