package cn.com.taodaji.ui.activity.shopManagement;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;

import org.junit.Before;

import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by yangkuo on 2018-07-30.
 */
public class CommodityCategoryNextActivityTest {


    public void test(CommodityCategoryActivityTest.GoodsTestInformation good) throws Exception {
        onView(withText(good.categoryThree)).perform(click());
        new ReleaseCommodityGoodsCreateActivityTest().releaseGoodsTest(good);
        //商品发布成功后，返回商品分类页面，以便发布下一个商品
        Espresso.pressBack();
    }
}