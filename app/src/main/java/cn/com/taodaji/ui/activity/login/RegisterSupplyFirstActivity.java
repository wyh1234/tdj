package cn.com.taodaji.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.BitmapUtil;
import com.base.utils.MD5AndSHA;
import com.base.utils.SerializableMap;
import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.InterfaceUtils.UserNameChangeListener;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.MarketLocal;
import cn.com.taodaji.model.entity.Register;
import cn.com.taodaji.model.entity.SmsCode;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.view.UserNameSupplyEditText;
import tools.activity.DataBaseActivity;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

public class RegisterSupplyFirstActivity extends DataBaseActivity implements View.OnClickListener , UserNameChangeListener {
    private String register_flag;
    private View mainView;
//  private Button register_OK;
    private UserNameSupplyEditText username_edit;
    private TextView get_verificationCode;
    private EditText password_edit1;
    private EditText password_edit2;
    private EditText editText3;
    private ImageView back,service;
    private Button go_register_next;
    private boolean phone_input = false;

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_register_supply_first);
    }


    protected void initView() {
        register_flag = getIntent().getStringExtra(FLAG);
       // ViewUtils.findViewById(mainView, R.id.agreement_content).setOnClickListener(this);

        back = ViewUtils.findViewById(this,R.id.img_back);
        back.setOnClickListener(this);
        service = ViewUtils.findViewById(this,R.id.ic_customer_service);
        service.setOnClickListener(this);

        go_register_next = ViewUtils.findViewById(this, R.id.go_register_next);
        go_register_next.setOnClickListener(this);

        editText3 = ViewUtils.findViewById(this, R.id.code_edit);
        Drawable drawable = BitmapUtil.getDrawable(R.mipmap.yzm_bg);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        editText3.setCompoundDrawables(drawable, null, null, null);




        password_edit1 = ViewUtils.findViewById(this, R.id.password_edit);
        password_edit2 = ViewUtils.findViewById(this, R.id.repeat_password_edit);
        Drawable drawable1 = BitmapUtil.getDrawable(R.mipmap.xz_bg);
        // 这一步必须要做,否则不会显示.
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        password_edit2.setCompoundDrawables(drawable1, null, null, null);
        password_edit2.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码框改成验证码，需要显示
        get_verificationCode = ViewUtils.findViewById(this, R.id.get_verificationCode);
        get_verificationCode.setOnClickListener(this);

        username_edit = ViewUtils.findViewById(this, R.id.username_edit);
        username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!UserNameUtill.isPhoneNO(username_edit.getText().toString()))
                        username_edit.setError("手机号码格式不正确");
                }
            }
        });
        username_edit.setUserNameChangeListener(this);
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
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01s);
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
    private Map<String, Object> values_map;


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.go_register_next:
                /*      注册前验证*/
                if (!UserNameUtill.isPhoneNO(username_edit.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式错误");
                    return;
                }
                if (TextUtils.isEmpty(editText3.getText().toString())) {
                    UIUtils.showToastSafesShort("请输入验证码");
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

                /* 获取页面的值*/
                values_map = new HashMap<>();
                values_map.put("account", username_edit.getText().toString().trim());
                values_map.put("phoneNumber", username_edit.getText().toString().trim());
                values_map.put("smsCode", editText3.getText().toString().trim());
                values_map.put("password", MD5AndSHA.md5Encode(password_edit1.getText().toString().trim()));

                //传递map
                Bundle bundle1 = new Bundle();
                SerializableMap tepmap1 = new SerializableMap(values_map);
                bundle1.putSerializable("tepmap", tepmap1);

                Intent intent1 = new Intent(RegisterSupplyFirstActivity.this, RegisterSupplySecondActivity.class);
                intent1.putExtras(bundle1);
                intent1.putExtra(FLAG, register_flag);
                startActivity(intent1);


              break;
            case R.id.get_verificationCode:
                if (!UserNameUtill.isPhoneNO(username_edit.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }
                String phoneNO = username_edit.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phoneNO);
                map.put("smsType", 1);//1注册，2登陆，3忘记密码

                getRequestPresenter().smsCode(map, new RequestCallback<SmsCode>(this) {
                    @Override
                    public void onSuc(SmsCode body) {
                        handler.post(runable);
                        get_verificationCode.setText("已发送(" + datetime + "s)");
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01s));
                        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });

                break;
            case R.id.ic_customer_service:
                /** 拨打电话*/
                SystemUtils.callPhone(this, PhoneUtils.getPhoneService());
                break;
            case R.id.img_back:
                //关闭按扭后跳转到主页
                hideSoftInput(view.getWindowToken());//隐藏键盘
                finish();
                break;
        }
    }

    @Override
    public void onUserNameChangeListener(String s) {
        phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
        if (phone_input) {
            if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.white)) {
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01s);
            }
        }else{
            if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.orange_yellow_ff7d01s)) {
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01s));
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
