package cn.com.taodaji.ui.activity.homepage;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.orm.SugarRecord;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import cn.com.taodaji.ui.activity.login.LoginActivityTest;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivityTest;
import cn.com.taodaji.ui.activity.shopManagement.ShopManagementActivityTest;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static cn.com.taodaji.common.Constants.PURCHASER;
import static cn.com.taodaji.common.Constants.SUPPLIER;
import static junit.framework.Assert.assertEquals;

/**
 * Created by yangkuo on 2018-07-28.
 */
@RunWith(AndroidJUnit4.class)
public class ManageActivityTest {
    //        onData(Matchers.allOf()).inAdapterView(withId(R.id.recyclerView)).atPosition(1).onChildView(withId(R.id.ok)).check(matches(not(isDisplayed()))).perform(click());
//                .inAdapterView(withId(R.id.photo_gridview)) // listview的id
//                .atPosition(1)                              // 所在位置
//                .onChildView(withId(R.id.imageview_photo))  // item中子控件id
//                .perform(click());

    @Rule
    public ActivityTestRule<ManageActivity> activityTestRule = new ActivityTestRule<>(ManageActivity.class, true, false);


    //isLogin 是否登陆，单元测试是未登录需要初始化数据，集成测试是跳转过来，
    public void open(String str, boolean isPurchase) {
        initData(isPurchase);
        gotoMyself();
        onView(withText(str)).perform(click());
/*        switch (str) {
            case "商品管理":
                onView(Matchers.allOf(withId(R.id.item_view), withChild(Matchers.allOf(withId(R.id.image_name), withText(str))))).perform(click());
                break;

        }*/
    }


    //发布商品->去发布商品
    public void releaseGoods(CommodityCategoryActivityTest.GoodsTestInformation... goods) throws Exception {
        //初始化数据
        initData(false);
        //跳转到个人中心
        gotoMyself();

        //点击发布商品
        onView(Matchers.allOf(withId(R.id.item_view), withChild(Matchers.allOf(withId(R.id.image_name), withText("发布商品"))))).perform(click());
        new CommodityCategoryActivityTest().releaseGoods(goods);
    }

    //商品管理->检查发布商品是否正确
    public void releaseGoodsChecked(CommodityCategoryActivityTest.GoodsTestInformation... goods) {
        //初始化数据
        initData(false);
        //跳转到个人中心
        gotoMyself();
        //点击商品管理
        onView(Matchers.allOf(withId(R.id.item_view), withChild(Matchers.allOf(withId(R.id.image_name), withText("商品管理"))))).perform(click());
        new ShopManagementActivityTest().releaseGoodsChecked(goods);
    }

    //跳转到个人中心
    private void gotoMyself() {
        onView(Matchers.allOf(withId(R.id.tab_itemView), withChild(Matchers.allOf(withId(R.id.tab_text), withText("我"))))).perform(click());
    }

    public void initData(boolean isPurchase) {
        if (LoginMethod.notLoginChecked()) {
            if (isPurchase) {
                LoginPurchaseBean loginPurchase = SugarRecord.first(LoginPurchaseBean.class);
                //已登录  初始化数据
                if (loginPurchase != null) {
                    PublicCache.loginPurchase = loginPurchase;
                    PublicCache.loginSupplier = null;
                    PublicCache.login_mode = PURCHASER;
                    PublicCache.site = PublicCache.loginPurchase.getSite();
                    PublicCache.site_name = PublicCache.loginPurchase.getSiteName();
                    PublicCache.site_login = PublicCache.site;
                    PublicCache.site_name_login = PublicCache.site_name;
                    int en;
                    if (loginPurchase.getEmpRole() == 0) en = loginPurchase.getEntityId();
                    else en = loginPurchase.getSubUserId();
                    PublicCache.userNameId = en;
                    activityTestRule.launchActivity(new Intent());
                } else new LoginActivityTest().purchaserText();

            } else {
                //初始化登陆数据
                LoginSupplierBean loginSupplier = LoginSupplierBean.first(LoginSupplierBean.class);
                if (loginSupplier != null) {
                    //已登录  初始化数据
                    PublicCache.loginSupplier = loginSupplier;
                    PublicCache.loginPurchase = null;
                    PublicCache.login_mode = SUPPLIER;
                    PublicCache.site = PublicCache.loginSupplier.getSite();
                    PublicCache.site_name = PublicCache.loginSupplier.getSiteName();
                    PublicCache.site_login = PublicCache.site;
                    PublicCache.site_name_login = PublicCache.site_name;
                    PublicCache.userNameId = loginSupplier.getEntityId();
                    activityTestRule.launchActivity(new Intent());
                } else new LoginActivityTest().supplierText();
            }
        }


    }
}
