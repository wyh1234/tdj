package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by yangkuo on 2018/12/4.
 */
public class FavoriteProducts {

    /**
     * total : 2
     * items : [{"entityId":2,"createTime":1490584970000,"updateTime":1519483258000,"name":"腐竹","commodityId":255,"isP":0,"store":12,"storeName":"李奎调料","saleNum":43,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327112106b821e8e0.jpg","categoryId":"31","description":"原汁精制，久煮不糊。","bannerImage":"","authStatus":2,"catalogCategoryName":"","categories":"31","nickName":"远洋腐竹","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327112106b821e8e0.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327112113af643aea.jpg"],"phone":"13697118502","realName":"李奎","verifyInfo":"","sku":"xtb_1490584970748","credentialsImage":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":9,"maxPrice":-1,"unit":"斤","specs":[{"level2Value":-1,"level2Unit":"","level3Unit":"","level1Unit":"斤","level3Value":-1,"productId":2,"levelType":1,"price":9,"stock":10063,"siteId":2,"avgPrice":9,"salesNumber":43,"specId":2,"avgUnit":"斤"}],"monthSaleNumbers":0},{"entityId":1,"createTime":1490584656000,"updateTime":1519908658000,"name":"大红枣","commodityId":251,"isP":0,"store":12,"storeName":"李奎调料","saleNum":1769,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327111202bfb8d24d.jpg","categoryId":"31","description":"黄河滩大红枣，枣中富含钙和铁","bannerImage":"","authStatus":2,"catalogCategoryName":"","categories":"31","nickName":"黄河滩大红枣","status":2,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327111202bfb8d24d.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327111207eb612e66.jpg"],"phone":"13697118502","realName":"李奎","verifyInfo":"","sku":"xtb_1490584656645","credentialsImage":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":5,"maxPrice":-1,"unit":"斤","specs":[{"level2Value":0,"level2Unit":"","level3Unit":"","level1Unit":"斤","level3Value":0,"productId":1,"levelType":1,"price":5,"stock":200,"siteId":2,"avgPrice":5,"salesNumber":0,"specId":17682,"avgUnit":"斤"}],"monthSaleNumbers":0}]
     * pn : 1
     * ps : 2
     */

    private int total;
    private int pn;
    private int ps;
    private List<GoodsInformation> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public List<GoodsInformation> getItems() {
        return items;
    }

    public void setItems(List<GoodsInformation> items) {
        this.items = items;
    }


}

