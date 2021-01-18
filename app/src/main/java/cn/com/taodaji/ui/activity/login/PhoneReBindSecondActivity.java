package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ChangeTelUrl;
import cn.com.taodaji.model.entity.SmsCode;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.R;

import com.base.entity.ResultInfo;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.ui.ppw.ReLoginPopupWindow;
import cn.com.taodaji.ui.ppw.SimpleButtonPopupWindow;
import retrofit2.Call;
import tools.activity.SimpleToolbarActivity;


import cn.com.taodaji.model.LoginMethod;

import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;


public class PhoneReBindSecondActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("换绑手机号");
    }


    private View mainView;
    private Button verification_code_send;
    private EditText phone_new;
    private EditText verification_code;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_phone_rebind_second);
        body_other.addView(mainView);
        verification_code_send = ViewUtils.findViewById(mainView, R.id.verification_code_send);
        verification_code = ViewUtils.findViewById(mainView, R.id.verification_code);
        phone_new = ViewUtils.findViewById(mainView, R.id.phone_new);
        phone_new.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    boolean phone_input = UserNameUtill.isPhoneNO(phone_new.getText().toString());
                    if (!phone_input) phone_new.setError("手机号码格式不正确");
                }
            }
        });
        setViewBackColor(verification_code_send);
        setViewBackColor(ViewUtils.findViewById(mainView, R.id.verification));
    }

    @Override
    protected void initData() {

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
        Call<ResultInfo<Object>> call = null;
        switch (view.getId()) {
            case R.id.verification_code_send:
                if (!UserNameUtill.isPhoneNO(phone_new.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式错误");
                    return;
                }
                String phone = phone_new.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phone);
                map.put("smsType", 5);//1注册，2登陆，3忘记密码 4手机号换绑，5新手机绑定"
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
                Map<String, Object> map1 = new HashMap<>();
                map1.put("uniqueId", SystemUtils.getAndroidId());
                UIDataProcessing.getChildControlsValue(mainView, map1);
                String path = null;

                if (PublicCache.loginPurchase != null) {
                    map1.put("entityId", PublicCache.loginPurchase.getLoginUserId());
                    path = "customer";
                } else if (PublicCache.loginSupplier != null) {
                    map1.put("store", PublicCache.loginSupplier.getStore());
                    map1.put("entityId", PublicCache.loginSupplier.getEntityId());
                    path = "supplier";
                } else return;

                loading();
                getRequestPresenter().changeTelUrl(path, map1, new RequestCallback<ChangeTelUrl>() {
                    @Override
                    public void onSuc(ChangeTelUrl body) {
                        loadingDimss();
                        LoginMethod.loginOut();
                        final ReLoginPopupWindow reLoginPopupWindow = new ReLoginPopupWindow(0, mainView).setMessage("新账号为 " + phone_new.getText().toString() + "。");
                        reLoginPopupWindow.setButtonOnclickInterface(new SimpleButtonPopupWindow.ButtonOnclickInterface() {
                            @Override
                            public void buttonLeftOnclick() {

                            }

                            @Override
                            public void buttonRightOnclick(int position, View showCountView) {

                                switch (PublicCache.login_mode) {
                                    case PURCHASER:{
                                        Intent intent = new Intent();
                                        intent.setClass(getBaseActivity(), LoginPurchaserActivity.class);
                                        intent.putExtra(FLAG, PublicCache.login_mode);
                                        startActivity(intent);
                                        reLoginPopupWindow.dismiss();
                                        break;}
                                    case SUPPLIER:{
                                        Intent intent = new Intent();
                                        intent.setClass(getBaseActivity(), LoginActivity.class);
                                        intent.putExtra(FLAG, PublicCache.login_mode);
                                        startActivity(intent);
                                        reLoginPopupWindow.dismiss();
                                        break;}
                                }
                            }
                        });
                        reLoginPopupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onFailed(int changeTelUrl, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        loadingDimss();
                    }
                });
                break;
        }

    }

}
