package cn.com.taodaji.ui.activity.shopManagement;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matchers;

import cn.com.taodaji.R;

import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by yangkuo on 2018-07-30.
 */
public class ShopManagementActivityTest {


    //审核中测试
    public void releaseGoodsChecked(CommodityCategoryActivityTest.GoodsTestInformation... goods) {
        //跳转到审核中
        onView(withText("审核中")).perform(click());

        for (CommodityCategoryActivityTest.GoodsTestInformation good : goods) {
            //输入搜索内容
            onView(Matchers.allOf(withId(R.id.swipe_twitter_swipe_toload_recyclerview_edit), isDisplayed())).perform(replaceText(good.categoryThree));

//            onView(Matchers.allOf(withId(R.id.swipe_target),isDisplayed()))
//                    .perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId()))));

        }


    }


}