package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CheckSmsCode;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.SmsCode;

import java.util.HashMap;
import java.util.Map;

import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class PhoneReBindFirstActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("换绑手机号");
    }

    private View mainView;
    private Button verification_code_send;
    private EditText verification_code;
    private TextView verification_message;
    private String phone = "";


    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_phone_rebind_first);
        body_other.addView(mainView);
        verification_code_send = ViewUtils.findViewById(mainView, R.id.verification_code_send);
        verification_code = ViewUtils.findViewById(mainView, R.id.verification_code);
        verification_message = ViewUtils.findViewById(mainView, R.id.verification_message);
        setViewBackColor(verification_code_send);
        setViewBackColor(ViewUtils.findViewById(mainView, R.id.verification));
    }

    @Override
    protected void initData() {

        switch (PublicCache.login_mode) {
            case PURCHASER:
                MyselftUpdateP.DataBean bean=(MyselftUpdateP.DataBean)getIntent().getExtras().getSerializable("bean");
                if (bean == null) return;{
                phone = bean.getPhoneNumber();
                }
                break;
            case SUPPLIER:
                if (PublicCache.loginSupplier == null) return;
                phone = PublicCache.loginSupplier.getPhoneNumber();
                break;
        }
        String ss = phone.substring(0, 4) + "****" + phone.substring(phone.length() - 3, phone.length());
        verification_message.setText("请输入" + ss + "收到的短信验证码：");
    }

    private Handler handler = UIUtils.getHandler();

    // 更新重新获取按扭
    int datetime = 60;
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            // TODO 自动生成的方法存根
            if (datetime == 0) {
                verification_code_send.setText("重新获取");
                verification_code_send.setEnabled(true);
                datetime = 60;
                return;
            } else {
                verification_code_send.setEnabled(false);
            }
            verification_code_send.setText("已发送(" + datetime + "s)");
            handler.postDelayed(runable, 1000);
            datetime--;
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verification_code_send:
                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phone);
                map.put("smsType", 4);//1注册，2登陆，3忘记密码 4换绑
                getRequestPresenter().smsCode(map, new RequestCallback<SmsCode>(this) {
                    @Override
                    public void onSuc(SmsCode body) {
                        handler.post(runable);
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
            case R.id.verification:
                String sstr = verification_code.getText().toString();
                if (sstr.length() < 4) {
                    UIUtils.showToastSafesShort("请输入正确的验证码");
                    return;
                }
                loading();
                getRequestPresenter().checkSmsCode(phone, sstr, new RequestCallback<CheckSmsCode>() {
                    @Override
                    public void onSuc(CheckSmsCode body) {
                        loadingDimss();
                        Intent intent = new Intent(getBaseActivity(), PhoneReBindSecondActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(int checkSmsCode, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
        }

    }
}
