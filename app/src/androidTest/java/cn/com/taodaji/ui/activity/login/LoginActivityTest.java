package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.com.taodaji.test.Config;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.test.TestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.assertEquals;


import static cn.com.taodaji.common.Constants.PURCHASER;
import static cn.com.taodaji.common.Constants.SUPPLIER;


/**
 * Created by yangkuo on 2018-07-25.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    public TestUtil testUtil = new TestUtil();

    /**
     * 集成测试
     * 1,先注销去掉 Config.purchaserUserName ,Config.supplierUserName
     * 2，用密码登陆，失败-> 验证码登陆 失败->注册 该账号
     */

    //采购商密码登录测试
    @Test
    public void purchaserText() {
        purchaserLogin();
        loginText(Config.purchaserUserName, Config.purchaserPassword, true);
    }

    //供应商密码登录测试
    @Test
    public void supplierText() {
        supplierLogin();
        loginText(Config.supplierUserName, Config.supplierPassword, false);
    }


    /**
     * 以上为单元测试
     */
    //采购商验证码登录测试
    private void purchaserSmsCodeText() {
        purchaserLogin();
        loginSmsCodeText(Config.purchaserUserName, true);
    }

    //供应商验证码登录测试
    private void supplierSmsCodeText() {
        supplierLogin();
        loginSmsCodeText(Config.supplierUserName, false);
    }


    //采购商登录
    private void purchaserLogin() {
        Intent intent = new Intent();
        intent.putExtra("flag", PURCHASER);
        PublicCache.login_mode = PURCHASER;
        activityTestRule.launchActivity(intent);


    }


    //供应商登录
    private void supplierLogin() {
        Intent intent = new Intent();
        intent.putExtra("flag", SUPPLIER);
        PublicCache.login_mode = SUPPLIER;
        activityTestRule.launchActivity(intent);
    }

    //密码登陆测试
    private void loginText(String username, String pass, boolean isPurchaser) {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        //手机号和密码置空
//        onView(withId(R.id.username_edit)).perform(clearText());
//        onView(withId(R.id.password_edit)).perform(clearText());

        onView(withId(R.id.username_edit)).perform(replaceText(username), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(replaceText(pass), closeSoftKeyboard());

        //通过id找到button，执行点击事件
        onView(withId(R.id.login_button)).perform(click());
        testUtil.waitForRequestBack(baseActivity);
        // 如果登录失败则用验证码登陆
        if (testUtil.lastCallback.testData.errCode != 0) {
            loginSmsCodeText(username, isPurchaser);
        }
    }

    //点击 手机验证码登陆 测试
    private void loginSmsCodeText(String username, boolean isPurchaser) {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        //手机号和密码置空
        onView(withId(R.id.username_edit)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(clearText(), closeSoftKeyboard());

        //切换验证码登陆
        onView(withId(R.id.login_type)).check(matches(withText(R.string.verificationCode_login))).perform(click());
        //验证切换状态
        onView(withId(R.id.login_type)).check(matches(withText(R.string.password_login)));

        //输入手机号
        onView(withId(R.id.username_edit)).perform(typeText(username), closeSoftKeyboard());
        //获取验证码
        onView(withId(R.id.get_verificationCode)).check(matches(withText(R.string.get_verificationCode))).perform(click());
        //输入验证码
        onView(withId(R.id.password_edit)).perform(typeText(Config.smscode), closeSoftKeyboard());

        //通过id找到button，执行点击事件
        onView(withId(R.id.login_button)).perform(click());
        testUtil.waitForRequestBack(baseActivity);
        //如果验证码登陆失败，则可认为未注册，跳转到注册页面注册
//        if (idlingresource.lastCallback.testData.errStr.contains("未注册")) {

        if (testUtil.lastCallback.testData.errCode != 0) {
            registerOnclickText(isPurchaser);
        }
    }

    //注册点击测试
    private void registerOnclickText(boolean isPurchaser) {

        onView(withId(R.id.username_edit)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.register)).perform(click());

        if (isPurchaser) new RegisterActivityTest().purchaserRegisterText();
        else new RegisterActivityTest().supplierRegisterText();
    }

}