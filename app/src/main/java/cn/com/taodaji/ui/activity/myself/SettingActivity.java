package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.CacheUtils;
import com.base.utils.SystemUtils;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.LoginOutEvent;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.ui.activity.login.UpdatePassWordActivity;
import cn.com.taodaji.ui.activity.openTicket.TicketStatusActivity;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;


public class SettingActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private Button login_out;
    private TextView txt_service_phone;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("设置");

    }

    private TextView clean;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_myself_setting);
        body_scroll.addView(mainView);

        LinearLayout ll_news = ViewUtils.findViewById(mainView, R.id.ll_news);
        ll_news.setOnClickListener(this);

        login_out = ViewUtils.findViewById(mainView, R.id.login_out);
        if (LoginMethod.notLoginChecked()) login_out.setVisibility(View.GONE);
        login_out.setOnClickListener(this);

        LinearLayout ll_open_ticket = ViewUtils.findViewById(mainView, R.id.ll_open_ticket);
        ll_open_ticket.setOnClickListener(this);
//        if (PublicCache.loginSupplier != null) {
        ll_open_ticket.setVisibility(View.GONE);
//        }

        LinearLayout ll_change_psw = ViewUtils.findViewById(mainView, R.id.ll_change_psw);
        ll_change_psw.setOnClickListener(this);

        LinearLayout ll_new_product = ViewUtils.findViewById(mainView, R.id.ll_new_product);
        ll_new_product.setOnClickListener(this);

        LinearLayout ll_mean_post = ViewUtils.findViewById(mainView, R.id.ll_mean_post);
        ll_mean_post.setOnClickListener(this);

        LinearLayout ll_service_phone = ViewUtils.findViewById(mainView, R.id.ll_service_phone);
        ll_service_phone.setOnClickListener(this);

        txt_service_phone=ViewUtils.findViewById(mainView, R.id.txt_service_phone);
        txt_service_phone.setText(PhoneUtils.getPhoneService());

        LinearLayout ll_clean = ViewUtils.findViewById(mainView, R.id.ll_clean);
        ll_clean.setOnClickListener(this);
        clean = ViewUtils.findViewById(mainView, R.id.clean);
        try {
            clean.setText("当前缓存" + CacheUtils.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LinearLayout ll_about_us = ViewUtils.findViewById(mainView, R.id.ll_about_us);
        ll_about_us.setOnClickListener(this);
        setViewBackColor(login_out);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }

    //选择提现银行卡入口
    @Subscribe(sticky = true)
    public void onEvent(LoginOutEvent event) {
        //ben==true,点击事件变为 设置默认银行卡
        EventBus.getDefault().removeStickyEvent(event);
        login_out.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_news:
                intent = new Intent(getBaseActivity(), SelfNewsActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_change_psw:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), UpdatePassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_open_ticket:
//                intent = new Intent(getBaseActivity(), TicketStatusActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_new_product:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), GoodsNeedActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_mean_post:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), MeaningPostActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_service_phone:
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
            case R.id.ll_clean:
                clean.setText("当前缓存" + "0k");
                ThreadManager.getShortPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        CacheUtils.clearAllCache(getApplicationContext());
                        Glide.get(getApplicationContext()).clearDiskCache();
                    }
                });
                UIUtils.showToastSafesShort("缓存已清理");
                break;

            case R.id.ll_about_us:
                intent = new Intent(getBaseActivity(), AboutMyselfActivity.class);
                startActivity(intent);
                break;

            case R.id.login_out:
                //TODO:点击退出设置界面，返回到我的中心出现退出模式
                UIUtils.showToastSafesShort("账号已退出");
                PublicCache.login_mode = PURCHASER;
                LoginMethod.loginOut();
                this.finish();

                break;
        }

    }


}
