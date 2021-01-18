package cn.com.taodaji.ui.activity.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.UpdatePassword;
import java.util.HashMap;
import java.util.Map;
import com.base.retrofit.RequestCallback;

import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.LoginOutEvent;
import tools.activity.SimpleToolbarActivity;
import cn.com.taodaji.model.LoginMethod;
import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

public class UpdatePassWordActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("修改密码");
    }

    private View mainView;
    private EditText password_edit1;
    private EditText password_edit2;
    private Map<String, Object> map;


    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_login_update_password);
        setViewBackColor(ViewUtils.findViewById(mainView, R.id.update_password_OK));
        body_other.addView(mainView);
        password_edit1 = ViewUtils.findViewById(mainView, R.id.password_edit1);
        password_edit2 = ViewUtils.findViewById(mainView, R.id.password_edit2);


    }


    public void onClick(View view) {
        if (view.getId() == R.id.update_password_OK) {
            hideSoftInput(view.getWindowToken());//隐藏键盘
            if (password_edit1.getText().toString().compareTo(password_edit2.getText().toString()) != 0) {
                UIUtils.showToastSafesShort("两次密码输入不一致");
                return;
            }

            if (password_edit1.getText().toString().length() < 6) {
                UIUtils.showToastSafesShort("密码最少需要6位");
                return;
            }
            map = new HashMap<>();
            map.put("uniqueId", SystemUtils.getAndroidId());
            if (!UIDataProcessing.getChildControlsValue(mainView, map)) return;
            String path = null;
            if (PublicCache.loginPurchase != null) {
                MyselftUpdateP.DataBean bean=(MyselftUpdateP.DataBean)getIntent().getExtras().getSerializable("bean");
                map.put("account", bean.getPhoneNumber());
                path = "customer";
            } else if (PublicCache.loginSupplier != null) {
                map.put("account", PublicCache.loginSupplier.getPhoneNumber());
                path = "supplier";
            } else {
                UIUtils.showToastSafesShort("请登录");
            }

            loading("密码修改中...");
            getRequestPresenter().update_password(path, map, new RequestCallback<UpdatePassword>() {
                @Override
                public void onSuc(UpdatePassword body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("密码修改成功,请重新登录");
                    UIUtils.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new LoginOutEvent(true));
                            LoginMethod.getInstance(UpdatePassWordActivity.this).toChooseLoginActivity();
                        }
                    }, 500);
                    finish();
                }

                @Override
                public void onFailed(int updatePassword, String msg) {
                    UIUtils.showToastSafesShort(msg);
                    loadingDimss();
                }
            });
        }

    }

    @Override
    protected void initData() {
    }
}
