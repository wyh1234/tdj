package cn.com.taodaji.integrationTest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.ui.activity.homepage.ManageActivityTest;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivityTest;

/**
 * Created by yangkuo on 2018-07-28.
 */
@RunWith(AndroidJUnit4.class)
public class SupplierTest {
    //获取需要测试的数据
    CommodityCategoryActivityTest.GoodsTestInformation[] goodsTestInformations = DataInit.getReleaseGoodsData();

    //从登陆到发布商品
    @Test
    public void loging_releaseCommodities() throws Exception {

        //跳转到个人中心并弹出发布商品页面
        ManageActivityTest manageActivityTest = new ManageActivityTest();

        //点击个人中心的发布商品开始发布商品测试
        manageActivityTest.releaseGoods(goodsTestInformations);


    }

    @Test
    public void releaseGoodsChecked() {

        //去检查发布商品的正确性
//        new ManageActivityTest().releaseGoodsChecked(goodsTestInformations);
    }

}
