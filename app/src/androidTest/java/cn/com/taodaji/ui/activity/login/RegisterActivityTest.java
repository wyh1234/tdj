package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;

import org.junit.Rule;
import org.junit.Test;

import cn.com.taodaji.test.Config;
import cn.com.taodaji.R;
import cn.com.taodaji.test.TestUtil;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.assertEquals;


import static cn.com.taodaji.common.Constants.PURCHASER;
import static cn.com.taodaji.common.Constants.SUPPLIER;

/**
 * Created by yangkuo on 2018-07-26.
 */
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(RegisterActivity.class, true, false);

    public TestUtil testUtil = new TestUtil();

    //采购商
    @Test
    public void purchaserText() {
        Intent intent = new Intent();
        intent.putExtra("flag", PURCHASER);
        activityTestRule.launchActivity(intent);
        purchaserRegisterText();
    }

    //供应商
    @Test
    public void supplierText() {
        Intent intent = new Intent();
        intent.putExtra("flag", SUPPLIER);
        activityTestRule.launchActivity(intent);
        supplierRegisterText();
    }


    //采购商注册测试
    public void purchaserRegisterText() {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
//        assertEquals(ActivityManage.getTopActivity().getClass().getSimpleName(), RegisterActivity.class.getSimpleName());
        //输入手机号
        onView(withId(R.id.username_edit)).perform(typeText(Config.purchaserUserName), closeSoftKeyboard());

        //验获取验证码
        onView(withId(R.id.get_verificationCode)).check(matches(withText(R.string.get_verificationCode))).perform(click());
        //输入验证码
        onView(withId(R.id.editText3)).perform(typeText(Config.smscode), closeSoftKeyboard());
        //输入密码
        onView(withId(R.id.password_edit1)).perform(typeText(Config.purchaserPassword), closeSoftKeyboard());
        onView(withId(R.id.password_edit2)).perform(typeText(Config.purchaserPassword), closeSoftKeyboard());


        //真实姓名
        onView(withId(R.id.et_realName)).perform(replaceText("杨测试"), closeSoftKeyboard());
        //单位名称
        onView(withId(R.id.shop_name_hint)).perform(replaceText("淘大集测试用"), closeSoftKeyboard());
        //详细地址
        onView(withId(R.id.address_detail)).perform(replaceText("南岗写字楼测试地址"), closeSoftKeyboard(), swipeDown());

        //确定注册

        onView(withId(R.id.register_OK)).perform(click());
        //调用Activity中我们已经设置好的getIdlingresource()方法，获取Idlingresource对象
        testUtil.waitForRequestBack(baseActivity);
        if (testUtil.lastCallback.testData.errCode != 0) {
            Log.e("test", testUtil.lastCallback.testData.errStr);
        } else {
            Log.e("test", "采购商" + Config.purchaserUserName + "注册成功");
        }
    }


    //供应商注册测试
    public void supplierRegisterText() {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        assertEquals(ActivityManage.getTopActivity().getClass().getSimpleName(), RegisterActivity.class.getSimpleName());
        //输入手机号
        onView(withId(R.id.username_edit)).perform(typeText(Config.supplierUserName), closeSoftKeyboard());
        //验获取验证码
        onView(withId(R.id.get_verificationCode)).check(matches(withText(R.string.get_verificationCode))).perform(click());
        //输入验证码
        onView(withId(R.id.editText3)).perform(typeText(Config.smscode), closeSoftKeyboard());
        //输入密码
        onView(withId(R.id.password_edit1)).perform(typeText(Config.supplierPassword), closeSoftKeyboard());
        onView(withId(R.id.password_edit2)).perform(typeText(Config.supplierPassword), closeSoftKeyboard());


        onView(isRoot()).perform(swipeUp());
        //真实姓名
        onView(withId(R.id.et_realName)).perform(replaceText("供应测试"), closeSoftKeyboard());
        //店铺名称
        onView(withId(R.id.shop_name_hint)).perform(replaceText("淘大集测试用"), closeSoftKeyboard());
        //详细地址
        onView(withId(R.id.address_detail)).perform(replaceText("南岗写字楼测试地址"), closeSoftKeyboard());

        //市场编号
        onView(withId(R.id.market_NO)).perform(typeText("18"), closeSoftKeyboard());

        //确定注册
        //调用Activity中我们已经设置好的getIdlingresource()方法，获取Idlingresource对象

        onView(withId(R.id.register_OK)).perform(click());

        testUtil.waitForRequestBack(baseActivity);
        if (testUtil.lastCallback.testData.errCode != 0) {
            Log.e("test", testUtil.lastCallback.testData.errStr);
        } else {
            Log.e("test", "供应商" + Config.purchaserUserName + "注册成功");
        }
    }


}