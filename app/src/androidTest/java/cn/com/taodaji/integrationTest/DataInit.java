package cn.com.taodaji.integrationTest;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivityTest;

/**
 * Created by yangkuo on 2018-08-03.
 */
public class DataInit {

    //发布商品的数据
    public static CommodityCategoryActivityTest.GoodsTestInformation[] getReleaseGoodsData() {
        //第一个
        CommodityCategoryActivityTest.GoodsTestInformation goodsTestInformation1 = new CommodityCategoryActivityTest.GoodsTestInformation();
        goodsTestInformation1.categoryOne = "水果";
        goodsTestInformation1.categoryTwo = "苹果梨";
        goodsTestInformation1.categoryThree = "苹果";
        goodsTestInformation1.nickName = "测试苹果";
        goodsTestInformation1.desc = "这是一个用来测试的苹果";
        //2,4 表示相册中第2个文件夹，第4张图片
        goodsTestInformation1.goodsPic = new String[]{"0,1", "0,2"};

        goodsTestInformation1.specs = new ArrayList<>();


        //一级规格
        GoodsSpecification goodsSpecification1 = new GoodsSpecification();
        goodsSpecification1.setLevelType(1);
        goodsSpecification1.setLevel1Unit("卷");
        goodsSpecification1.setPrice(BigDecimal.valueOf(35.58));
        goodsSpecification1.setStock(95);
        goodsTestInformation1.specs.add(goodsSpecification1);

        //二级规格
        GoodsSpecification goodsSpecification2 = new GoodsSpecification();
        goodsSpecification2.setLevelType(2);
        goodsSpecification2.setLevel2Unit("扇");
        goodsSpecification2.setLevel2Value(BigDecimal.valueOf(5));
        goodsSpecification2.setLevel1Unit("卷");
        goodsSpecification2.setPrice(BigDecimal.valueOf(288));
        goodsSpecification2.setStock(188);
        goodsTestInformation1.specs.add(goodsSpecification2);

        //三级规格
        GoodsSpecification goodsSpecification3 = new GoodsSpecification();
        goodsSpecification3.setLevelType(3);
        goodsSpecification3.setLevel3Unit("件");
        goodsSpecification3.setLevel3Value(BigDecimal.valueOf(5));
        goodsSpecification3.setLevel2Unit("扇");
        goodsSpecification3.setLevel2Value(BigDecimal.valueOf(10));
        goodsSpecification3.setLevel1Unit("卷");
        goodsSpecification3.setPrice(BigDecimal.valueOf(288));
        goodsSpecification3.setStock(23);
        goodsTestInformation1.specs.add(goodsSpecification3);


        //第二个
        CommodityCategoryActivityTest.GoodsTestInformation goodsTestInformation2 = new CommodityCategoryActivityTest.GoodsTestInformation();
        goodsTestInformation2.categoryOne = "水产海鲜";
        goodsTestInformation2.categoryTwo = "鱼类";
        goodsTestInformation2.categoryThree = "草鱼";
        goodsTestInformation2.nickName = "测试水产海鲜";
        goodsTestInformation2.desc = "这是一条用来测试的鱼";
        //0,4 表示相册中第1个文件夹，第5张图片
        goodsTestInformation2.goodsPic = new String[]{"1,0", "1,2"};

        goodsTestInformation2.specs = new ArrayList<>();

        //二级规格
        GoodsSpecification goodsSpecification4 = new GoodsSpecification();
        goodsSpecification4.setLevelType(2);
        goodsSpecification4.setLevel2Unit("条");
        goodsSpecification4.setLevel2Value(BigDecimal.valueOf(5));
        goodsSpecification4.setLevel1Unit("斤");
        goodsSpecification4.setPrice(BigDecimal.valueOf(288));
        goodsSpecification4.setStock(188);
        goodsTestInformation2.specs.add(goodsSpecification4);

        //一级规格
        GoodsSpecification goodsSpecification5 = new GoodsSpecification();
        goodsSpecification5.setLevelType(1);
        goodsSpecification5.setLevel1Unit("斤");
        goodsSpecification5.setPrice(BigDecimal.valueOf(38));
        goodsSpecification5.setStock(95);
        goodsTestInformation2.specs.add(goodsSpecification5);


        //三级规格
        GoodsSpecification goodsSpecification6 = new GoodsSpecification();
        goodsSpecification6.setLevelType(3);
        goodsSpecification6.setLevel3Unit("箱");
        goodsSpecification6.setLevel3Value(BigDecimal.valueOf(5));
        goodsSpecification6.setLevel2Unit("条");
        goodsSpecification6.setLevel2Value(BigDecimal.valueOf(10));
        goodsSpecification6.setLevel1Unit("斤");
        goodsSpecification6.setPrice(BigDecimal.valueOf(288));
        goodsSpecification6.setStock(23);
        goodsTestInformation2.specs.add(goodsSpecification6);


        return new CommodityCategoryActivityTest.GoodsTestInformation[]{goodsTestInformation1, goodsTestInformation2};
    }

}
