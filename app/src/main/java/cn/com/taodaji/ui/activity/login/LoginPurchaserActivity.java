package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.utils.BitmapUtil;
import com.base.utils.MD5AndSHA;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.InterfaceUtils.UserNameChangeListener;
import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.SmsCode;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.view.PasswordPurchaserEditText;
import cn.com.taodaji.ui.view.UserNamePurchaserEditText;
import tools.activity.DataBaseActivity;

public class LoginPurchaserActivity extends DataBaseActivity implements View.OnClickListener, Constants,UserNameChangeListener {
    private String login_flag;//采购商或者供应商的标志
    private String login_mode = PASSWORD_LOGIN;//手机验证码或密码登录
    private UserNamePurchaserEditText username_edit;
    private PasswordPurchaserEditText password_edit;

    private ImageView img_back;
    //private LinearLayout verificationCode_layout;

    //    private TextView password_text;
    private TextView forget_password;
    private Button login_button;
    //    private TextView login_info;
    private TextView login_type,tv_fw,tv_ys;
    private TextView register;
    private TextView get_verificationCode;
    private boolean phone_input = false;
    private ImageView login_close;

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_login_purchaser);
    }

    protected void initView() {
        super.initView();
        // setStatusBarForeColorColor(false);
        //setStatusBarDrawableRes(R.drawable.bg_purchaser_top);
        login_flag = getIntent().getStringExtra(FLAG);

        login_close = ViewUtils.findViewById(this, R.id.login_close);
        login_close.setOnClickListener(this);

        tv_fw= ViewUtils.findViewById(this,R.id.tv_fw);
        tv_fw.setOnClickListener(this);
        tv_ys= ViewUtils.findViewById(this,R.id.tv_ys);
        tv_ys.setOnClickListener(this);

        img_back=ViewUtils.findViewById(this, R.id.img_back);
        img_back.setOnClickListener(this);

        login_button = ViewUtils.findViewById(this, R.id.login_button);
        login_button.setOnClickListener(this);
//        login_info = ViewUtils.findViewById(this, R.id.login_info);
        login_type = ViewUtils.findViewById(this, R.id.login_type);
        login_type.setOnClickListener(this);
        register = ViewUtils.findViewById(this, R.id.register);
        register.setOnClickListener(this);
        forget_password = ViewUtils.findViewById(this, R.id.forget_password);
        forget_password.setOnClickListener(this);

//        password_text = ViewUtils.findViewById(this, R.id.password_text);

        //需要显示 ,获取验证码区域，
        //verificationCode_layout = ViewUtils.findViewById(this, R.id.verificationCode_layout);


        //获取验证码文本控件
        get_verificationCode = ViewUtils.findViewById(this, R.id.get_verificationCode);
        get_verificationCode.setOnClickListener(this);

        username_edit = ViewUtils.findViewById(this, R.id.username_edit);
        username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                    if (!phone_input) username_edit.setError("手机号码格式不正确");
                }
            }
        });
        username_edit.setUserNameChangeListener(this);
        password_edit = ViewUtils.findViewById(this, R.id.password_edit);
        password_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    login_button.callOnClick();
                    return true;
                }
                return false;
            }
        });
        hideSoftInput(getRootView().getWindowToken());//隐藏键盘
        loginFlagSwitch(login_flag);//登录用户类别切换
    }

    @Override
    public void onUserNameChangeListener(String s) {
        if (login_mode.equals("1")) {
            phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
            if (phone_input) {
                if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.white)) {
                    get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                    get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                }
            }else{
                if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.orange_yellow_ff7d01)) {
                    get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                    get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                }

            }
        }


    }

    @Override
    public void onUserNameCloseListener() {
        get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_69));
        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
    }


    @Override
    protected void initData() {
//        View currentFocus = getCurrentFocus();
//        if (currentFocus != null) {
//            currentFocus.clearFocus();
//        }
    }

    private void loginFlagSwitch(String login_flag) {
        if (login_flag == null) return;
        switch (login_flag) {
            case SUPPLIER://供应商

                // login_info.setText(UIUtils.getString(R.string.supplier_login_info));
                login_button.setText("卖家登录");
                login_button.setBackgroundResource(R.mipmap.mjdl_bg);
                login_type.setTextColor(UIUtils.getColor(R.color.blue_light));
                register.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                break;
            case PURCHASER://采购商
                //  login_info.setText(UIUtils.getString(R.string.purchaser_login_info));
                login_button.setText("买家登录");
                login_button.setBackgroundResource(R.mipmap.mjdl_bg);
                login_type.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                //register.setTextColor(UIUtils.getColor(R.color.blue_light));
                break;
        }
    }

    private void loginModeSwitch(String type) {
        switch (type) {
            case VER_CODE_LOGIN:{
                get_verificationCode.setVisibility(View.VISIBLE);

                get_verificationCode.setCompoundDrawablePadding(UIUtils.dip2px(10));
                Drawable drawable = BitmapUtil.getDrawable(R.mipmap.yzm_bg);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

                password_edit.setCompoundDrawables(drawable, null, null, null);
                // password_text.setText("验证码：");
                forget_password.setVisibility(View.GONE);
                password_edit.setHint(UIUtils.getString(R.string.verificationCode_hint));
                login_type.setText(UIUtils.getString(R.string.password_login));
                password_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码框改成验证码，需要显示

                break;}
            case PASSWORD_LOGIN:
                get_verificationCode.setVisibility(View.GONE);
                // password_text.setText("密码：");
                forget_password.setVisibility(View.VISIBLE);
                password_edit.setHint(UIUtils.getString(R.string.password_hint));
                login_type.setText(UIUtils.getString(R.string.verificationCode_login));
                password_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码框要隐藏密码

                get_verificationCode.setCompoundDrawablePadding(UIUtils.dip2px(10));
                Drawable drawable = BitmapUtil.getDrawable(R.mipmap.suo_bg);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

                password_edit.setCompoundDrawables(drawable, null,null , null);


                break;
        }
        password_edit.setText("");
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
                switch (login_flag) {
                    case PURCHASER:
                        // get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                        break;
                    case SUPPLIER:
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.blue_light));
                        break;
                }
                get_verificationCode.setEnabled(true);
                datetime = 60;
                return;
            } else {
                get_verificationCode.setEnabled(false);
//                get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
//                get_verificationCode.setBackgroundResource(R.drawable.z_round_rect_solid_gray66);
            }
            get_verificationCode.setText("已发送(" + datetime + "s)");
            // get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
            handler.postDelayed(runable, 1000);
            datetime--;
        }
    };


    private boolean login_type_b = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button: {
                phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                if (!phone_input) {
                    UIUtils.showToastSafesShort("手机号码格式错误");
                    return;
                }
                String pws = password_edit.getText().toString();
                String username = username_edit.getText().toString();
                String password = MD5AndSHA.md5Encode(pws);
                Map<String, Object> map_login = new HashMap<>();
                map_login.put("account", username);
                map_login.put("loginType", login_mode);
                //登录模式不同，调整参数
                switch (login_mode) {
                    case VER_CODE_LOGIN:
                        if (TextUtils.isEmpty(pws)) {
                            //UIUtils.showToastSafesShort("请输入验证码");
                            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_LONG).show();
                            return;
                        }
                        map_login.put("smsCode", pws);
                        break;

                    case PASSWORD_LOGIN:
                        if (pws.length() < 6) {
                            //UIUtils.showToastSafesShort("密码最少需要6位");
                            Toast.makeText(getApplicationContext(), "密码最少需要6位", Toast.LENGTH_LONG).show();
                            UIUtils.showToastSafe("密码最少需要6位");
                            return;
                        }
                        map_login.put("password", password);
                        break;
                }
                hideSoftInput(view.getWindowToken());//隐藏键盘
                loading("正在登录...");
                //登录
                LoginMethod.getInstance(this).login(map_login, login_flag);
                break;
            }

            case R.id.login_type:
                login_type_b = !login_type_b;
                if (login_type_b) login_mode = PASSWORD_LOGIN;
                else login_mode = VER_CODE_LOGIN;
                loginModeSwitch(login_mode);
                break;
            case R.id.forget_password:
                Intent forget = new Intent(this, ForgetPasswordActivity.class);
                forget.putExtra(FLAG, login_flag);
                startActivity(forget);
                break;
            case R.id.register:
                if (login_flag == null) return;
                switch (login_flag) {
                    case PURCHASER: {
                        Intent register1 = new Intent(this, RegisterPurchaserFirstActivity.class);
                        register1.putExtra(FLAG, login_flag);
                        register1.putExtra("type",2);
                        startActivity(register1);
//                        Intent register = new Intent(this, SelectRegisterActivity.class);
//                        register.putExtra(FLAG, login_flag);
//                        startActivity(register);
                        break;
                    }
                    case SUPPLIER: {
                        Intent register = new Intent(this, RegisterSupplyFirstActivity.class);
                        register.putExtra(FLAG, login_flag);
                        startActivity(register);
                        break;
                    }
                }

                break;

            case R.id.get_verificationCode:
                phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                if (!phone_input) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }
                String phoneNO = username_edit.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phoneNO);
                map.put("smsType", 2);//1注册，2登陆，3忘记密码
                RequestPresenter.getInstance().smsCode(map, new RequestCallback<SmsCode>(this) {
                    @Override
                    public void onSuc(SmsCode body) {
                        handler.post(runable);
                        get_verificationCode.setText("已发送(" + datetime + "s)");
                        //get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        UIUtils.showToastSafesShort(msg);
                    }
                });

                break;
            case R.id.login_close:
                //关闭按扭后跳转到主页
                hideSoftInput(view.getWindowToken());//隐藏键盘
                if (ActivityManage.isActivityExist(ManageActivity.class)) {
                    finish();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(this, ManageActivity.class);
                PublicCache.login_mode = PURCHASER;
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_fw:
                startActivity(new Intent(this, AgreementActivity.class));
                break;

            case R.id.tv_ys:
                startActivity(new Intent(this, AgreementActivity.class));
              /*  Intent intent_tv_right=new Intent(this, SpecialActivitiesActivity.class);
                intent_tv_right.putExtra("url","http://siji.51taodj.com:8060/scanner");
                intent_tv_right.putExtra("name","隐私协议");
                startActivity(intent_tv_right);*/
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            login_close.callOnClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
