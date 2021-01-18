package cn.com.taodaji.ui.activity.shopManagement;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;

import org.hamcrest.Matchers;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.test.TestUtil;

import static org.hamcrest.core.IsNull.notNullValue;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by yangkuo on 2018-07-30.
 */
public class SpecificationActivityTest {
    public TestUtil testUtil = new TestUtil();

    public void create(List<GoodsSpecification> specs) {
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        if (specs != null) {
            //等待规格列表加载完毕
//            testUtil.waitForDialog("正在努力加载中...");

            for (GoodsSpecification spec : specs) {
                onView(withId(R.id.recycle)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.bt_ok), withText("添加规格"))), click()));
                new SpecificationActivityAddTest().add(spec);
            }
            //填写完成后，返回发布商品页面
            Espresso.pressBack();
        }

    }

    public void update() {

    }


}