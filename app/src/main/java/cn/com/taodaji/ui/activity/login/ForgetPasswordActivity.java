package cn.com.taodaji.ui.activity.login;


import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.taodaji.InterfaceUtils.UserNameChangeListener;
import cn.com.taodaji.R;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.model.entity.ForgetPassword;
import cn.com.taodaji.model.entity.SmsCode;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;

import com.base.activity.ActivityManage;

import cn.com.taodaji.ui.view.UserNameEditText;
import tools.activity.DataBaseActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.activity.BaseActivity;

import cn.com.taodaji.model.LoginMethod;
import tools.statusbar.Eyes;

import com.base.utils.MD5AndSHA;
import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

public class ForgetPasswordActivity extends DataBaseActivity implements View.OnClickListener, UserNameChangeListener {
    private String forget_flag;
    private View mainView;
    private TextView get_verificationCode;
    private EditText password_edit1;
    private EditText password_edit2,ed_smsCode;
    private UserNameEditText username_edit;
    private Button forget_password_OK;
    private RelativeLayout rl_bg;
    private ImageView img_back;
    private boolean phone_input = false;
/*    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.white);
        toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);//设置导航栏图标
        simple_title.setText("找回密码");
        simple_title.setTextColor(UIUtils.getColor(R.color.gray_66));

    }*/

/*    @Override
    protected void initMainView() {
        forget_flag = getIntent().getStringExtra(FLAG);
//        setStatusBarColor();//设置通知栏颜色

//        mainView = getLayoutView(R.layout.activity_login_forgetpassword);
//        body_scroll.addView(mainView);





    }*/

    @Override
    protected void initView() {
        super.initView();
//        setStatusBarColor();//设置通知栏颜色
        Eyes.translucentStatusBar(this,true);
        forget_flag = getIntent().getStringExtra(FLAG);
        password_edit1 = ViewUtils.findViewById(this, R.id.password_edit1);
        password_edit2 = ViewUtils.findViewById(this, R.id.password_edit2);
        rl_bg=ViewUtils.findViewById(this,R.id.rl_bg);
        username_edit = ViewUtils.findViewById(this, R.id.username_edit);
        ed_smsCode= ViewUtils.findViewById(this, R.id.ed_smsCode);
        img_back= ViewUtils.findViewById(this, R.id.img_back);
        img_back.setOnClickListener(this);
        username_edit.setUserNameChangeListener(this);
        username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                    if (!phone_input)
                        username_edit.setError("手机号码格式不正确");
                }
            }
        });
        get_verificationCode = ViewUtils.findViewById(this, R.id.get_verificationCode);
        get_verificationCode.setOnClickListener(this);
        forget_password_OK = ViewUtils.findViewById(this, R.id.forget_password_OK);
        forget_password_OK.setOnClickListener(this);

        forget_password_mode(forget_flag);
    }

    @Override
    protected View getContentLayout() {
         return getLayoutView(R.layout.activity_login_forgetpassword);
    }

    @Override
    protected void initData() {

    }

    private void forget_password_mode(String forget_flag) {
        if (forget_flag == null) return;
        switch (forget_flag) {
            case PURCHASER:
                forget_password_OK.setBackgroundResource(R.mipmap.mjdl_bg);
                rl_bg.setBackgroundResource(R.mipmap.wsmj_bg);
                break;
            case SUPPLIER:
                forget_password_OK.setBackgroundResource(R.mipmap.mjdlone_bg);
                rl_bg.setBackgroundResource(R.mipmap.xss_bg);
                break;
        }
    }


    private Handler handler = UIUtils.getHandler();
    // 更新重新获取按扭
    int datetime = 60;
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            // TODO 自动生成的方法存根
            if (datetime == 0) {
                get_verificationCode.setText("重新获取");

                get_verificationCode.setEnabled(true);
                datetime = 60;
                return;
            } else {
                get_verificationCode.setEnabled(false);
            }
            get_verificationCode.setText("已发送(" + datetime + "s)");
            get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
            handler.postDelayed(runable, 1000);
            datetime--;
        }
    };

    private Map<String, Object> map_pamms;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_verificationCode:
                phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                if (!phone_input) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }


                String phoneNO = username_edit.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phoneNO);
                map.put("smsType", 3);//1注册，2登陆，3忘记密码

                getRequestPresenter().smsCode(map, new RequestCallback<SmsCode>(this) {
                    @Override
                    public void onSuc(SmsCode body) {
                        handler.post(runable);
                        get_verificationCode.setText("已发送(" + datetime + "s)");

                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        loadingDimss();
                    }
                });
                break;

            case R.id.forget_password_OK:
                phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                if (!phone_input) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }

                if (password_edit1.getText().toString().compareTo(password_edit2.getText().toString()) != 0) {
                    UIUtils.showToastSafesShort("两次密码输入不一致");
                    return;
                }
                if (password_edit1.getText().toString().length() < 6) {
                    UIUtils.showToastSafesShort("密码最少需要6位");
                    return;
                }
                map_pamms = new HashMap<>();
//                UIDataProcessing.getChildControlsValue(mainView, map_pamms);
                map_pamms.put("smsCode",ed_smsCode.getText().toString().trim());
                map_pamms.put("account",username_edit.getText().toString().trim());
                map_pamms.put("newPassword", MD5AndSHA.md5Encode(password_edit1.getText().toString().trim()));
                map_pamms.put("uniqueId", SystemUtils.getAndroidId());
                String path = null;
                switch (forget_flag) {
                    case PURCHASER:
                        path = "customer";
                        break;
                    case SUPPLIER:
                        path = "supplier";
                        break;
                }
                LogUtils.e(map_pamms);
                getRequestPresenter().forget_pwd(path, map_pamms, new RequestCallback<ForgetPassword>() {
                    @Override
                    public void onSuc(ForgetPassword body) {
                        Map<String, Object> map_login = new HashMap<>();
                        map_login.put("account", map_pamms.get("account"));
                        map_login.put("loginType", PASSWORD_LOGIN);
                        map_login.put("password", map_pamms.get("newPassword").toString());
                        UIUtils.showToastSafesShort("密码找回成功,请牢记您的密码.");

                        switch (forget_flag) {
                            case PURCHASER:{
                                BaseActivity loginActivity = ActivityManage.getActivity(LoginPurchaserActivity.class);
                                if (loginActivity != null) loginActivity.finish();
                                LoginMethod.loginOut();
                                LoginMethod.getInstance(ForgetPasswordActivity.this).login(map_login, forget_flag);
                                break;}
                            case SUPPLIER:{
                                BaseActivity loginActivity = ActivityManage.getActivity(LoginActivity.class);
                                if (loginActivity != null) loginActivity.finish();
                                LoginMethod.loginOut();
                                LoginMethod.getInstance(ForgetPasswordActivity.this).login(map_login, forget_flag);
                                break;}
                        }


                    }

                    @Override
                    public void onFailed(int forgetPassword, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        loadingDimss();
                    }
                });


                break;
            case R.id.img_back:
                finish();
                break;
        }

    }

    @Override
    public void onUserNameChangeListener(String s) {
            phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
            if (phone_input) {
                if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.white)) {
                    switch (forget_flag) {
                        case PURCHASER:
                            // get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                            get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                            get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                            break;
                        case SUPPLIER:
                            get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                            get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01s);
                            break;
                    }

                }
            }else{
                if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.orange_yellow_ff7d01)) {

                    switch (forget_flag) {
                        case PURCHASER:
                            // get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                            get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                            break;
                        case SUPPLIER:
                            get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01s));//
                            break;
                    }

                    get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                }

            }

    }

    @Override
    public void onUserNameCloseListener() {
        get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_69));
        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
    }
}
