package cn.com.taodaji.ui.activity.shopManagement;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.retrofit.HttpRequestManage;
import com.base.retrofit.RequestCallback;

import org.hamcrest.Matchers;
import org.junit.Before;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.test.TestUtil;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by yangkuo on 2018-07-30.
 * 不能单独测试
 * 要从登陆->个人中心->发布商品  依次点击过来测试
 */
public class CommodityCategoryActivityTest {
    public TestUtil testUtil = new TestUtil();

    public void releaseGoods(CommodityCategoryActivityTest.GoodsTestInformation... goods) throws Exception {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        testUtil.waitForRequestBack(baseActivity);
        for (GoodsTestInformation good : goods) {
            onView(withText(good.categoryOne)).perform(click());
            onView(Matchers.allOf(withId(R.id.item_view), withChild(withText(good.categoryTwo)))).perform(click());
            new CommodityCategoryNextActivityTest().test(good);
        }
        //发布完商品后返回个人中心
        Espresso.pressBack();

    }


    public static class GoodsTestInformation {
        public String categoryOne;
        public String categoryTwo;
        public String categoryThree;

        public String nickName = "";//别名
        public String desc = "";

        public String[] goodsPic;//商品图片

        public List<GoodsSpecification> specs;

    }


}