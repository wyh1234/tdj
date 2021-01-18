package cn.com.taodaji.ui.activity.shopManagement;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.utils.UIUtils;


import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.test.TestUtil;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by yangkuo on 2018-07-30.
 */
public class ReleaseCommodityGoodsCreateActivityTest {
    public TestUtil testUtil = new TestUtil();

    public void releaseGoodsTest(CommodityCategoryActivityTest.GoodsTestInformation good) throws Exception {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        onView(withId(R.id.goods_nickName)).perform(replaceText(good.nickName));
        onView(withId(R.id.goods_content)).perform(replaceText(good.desc));

        //填写规格
        specificationWrite(good.specs);
        //选择图片
        imageSwitcher(good.goodsPic);

        //提交
        onView(withId(R.id.goods_create_ok)).perform(click());
        testUtil.waitForRequestBack(baseActivity);
        if (testUtil.lastCallback.testData.errCode == 0) {
            //如果发布成功会弹出图文详情页面，这里不测试这个，直接返回
            Espresso.pressBack();
            //关闭发布商品页面
            Espresso.pressBack();
        }
    }

    //填写规格
    private void specificationWrite(List<GoodsSpecification> specs) {
//        onView(withId(R.id.specififcation)).perform(swipeUp(), click());
        new SpecificationActivityTest().create(specs);
    }

    //选择图片
    private void imageSwitcher(String... goodsPic) throws Exception {
        if (goodsPic != null && goodsPic.length > 0) {
            int i = 0;
            for (String s : goodsPic) {
                String[] str = s.split(",");
                if (str.length == 2) {
                    //打开图片选择
                    //检查是否显示了压缩对话框
                    testUtil.waitForDialog(UIUtils.getString(com.base.R.string.tip_compress));

                    onView(withId(R.id.added_image_group)).perform(RecyclerViewActions.actionOnItemAtPosition(i++, click()));

                    //初始化一个UiDevice对象
                    UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
                    // 点击home键，回到home界面
                    // mDevice.pressHome();

                    //选择相册图片
                    UiScrollable gridView = new UiScrollable(new UiSelector().className(GridView.class).resourceId("cn.com.taodaji:id/grid_view_album_select"));
                    //滑动到底部
                    gridView.flingForward();
                    //滑动到顶部
                    gridView.flingBackward();
                    UiObject item = gridView.getChild(new UiSelector().index(Integer.valueOf(str[0])));
                    item.click();

                    //选择图片
                    UiScrollable gridView2 = new UiScrollable(new UiSelector().className(GridView.class).resourceId("cn.com.taodaji:id/grid_view_image_select"));
                    //滑动到底部
                    gridView2.flingForward();
                    //滑动到顶部
                    gridView2.flingBackward();
                    UiObject item2 = gridView2.getChild(new UiSelector().index(Integer.valueOf(str[1])));
                    item2.click();

                    UiObject ok = mDevice.findObject(new UiSelector().text("确定").className(TextView.class));
                    ok.click();

                    //剪切
                    UiObject okkk = mDevice.findObject(new UiSelector().resourceId("com.miui.gallery:id/ok").className(Button.class));
                    okkk.click();
                }
            }
        }

    }


}