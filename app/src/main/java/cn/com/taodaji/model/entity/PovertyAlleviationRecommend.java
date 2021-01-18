package cn.com.taodaji.model.entity;

import java.util.List;

public class PovertyAlleviationRecommend {

    /**
     * err : 0
     * data : {"total":4,"items":[{"entityId":68685,"productCriteria":"1","name":"黄瓜","commodityId":null,"commodityName":"","nickName":"食堂专用有点弯有点租","credentialsImage":"","updateTime":1561950461000,"createTime":1561531149000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"袋","price":0,"saleNum":17,"totalSellNum":17,"totalSellAmount":385,"isRecommend":0,"status":1,"description":"","store":194,"storeStatus":0,"storeName":"薛氏蔬菜","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1906281805555e774993.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":26,"maxPrice":-1,"specs":[{"specId":114866,"productId":68685,"salesNumber":17,"avgPrice":0.65,"level3Value":0,"level3Unit":"","level1Unit":"袋","level2Value":40,"price":26,"levelType":2,"siteId":2,"avgUnit":"斤","stock":10,"level2Unit":"斤"}],"monthSaleNumbers":14,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":68686,"productCriteria":"1","name":"黄瓜","commodityId":null,"commodityName":"","nickName":"本地黄瓜有点弯","credentialsImage":"","updateTime":1561950461000,"createTime":1561531263000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"袋","price":0,"saleNum":4,"totalSellNum":4,"totalSellAmount":110,"isRecommend":0,"status":1,"description":"","store":194,"storeStatus":0,"storeName":"薛氏蔬菜","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190627204800df7a3ddd.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":29,"maxPrice":-1,"specs":[{"specId":114867,"productId":68686,"salesNumber":4,"avgPrice":0.73,"level3Value":0,"level3Unit":"","level1Unit":"袋","level2Value":40,"price":29,"levelType":2,"siteId":2,"avgUnit":"斤","stock":146,"level2Unit":"斤"}],"monthSaleNumbers":4,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":42401,"productCriteria":"2","name":"意大利生菜","commodityId":null,"commodityName":"","nickName":"比如我看统高速发展速度很快事情","credentialsImage":"","updateTime":1547708923000,"createTime":1547708923000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"斤","price":0,"saleNum":0,"totalSellNum":0,"totalSellAmount":0,"isRecommend":0,"status":1,"description":"","store":1314,"storeStatus":0,"storeName":"侦测店铺001","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1901171507142ed87b26.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":1,"maxPrice":-1,"specs":[{"specId":88577,"productId":42401,"salesNumber":0,"avgPrice":1,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":1,"levelType":1,"siteId":2,"avgUnit":"斤","stock":1,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"的","foregift":2,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":36285,"productCriteria":"1","name":"印象老家豆腐乳","commodityId":null,"commodityName":"","nickName":"测试商品不发货，勿拍","credentialsImage":"","updateTime":1544575542000,"createTime":1542608624000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"斤","price":0,"saleNum":0,"totalSellNum":0,"totalSellAmount":0,"isRecommend":0,"status":1,"description":"","store":1314,"storeStatus":0,"storeName":"侦测店铺001","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/181119142253fceae736.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":5,"maxPrice":-1,"specs":[{"specId":82476,"productId":36285,"salesNumber":0,"avgPrice":5,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":5,"levelType":1,"siteId":2,"avgUnit":"斤","stock":5555555,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0}]}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * total : 4
         * items : [{"entityId":68685,"productCriteria":"1","name":"黄瓜","commodityId":null,"commodityName":"","nickName":"食堂专用有点弯有点租","credentialsImage":"","updateTime":1561950461000,"createTime":1561531149000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"袋","price":0,"saleNum":17,"totalSellNum":17,"totalSellAmount":385,"isRecommend":0,"status":1,"description":"","store":194,"storeStatus":0,"storeName":"薛氏蔬菜","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1906281805555e774993.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":26,"maxPrice":-1,"specs":[{"specId":114866,"productId":68685,"salesNumber":17,"avgPrice":0.65,"level3Value":0,"level3Unit":"","level1Unit":"袋","level2Value":40,"price":26,"levelType":2,"siteId":2,"avgUnit":"斤","stock":10,"level2Unit":"斤"}],"monthSaleNumbers":14,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":68686,"productCriteria":"1","name":"黄瓜","commodityId":null,"commodityName":"","nickName":"本地黄瓜有点弯","credentialsImage":"","updateTime":1561950461000,"createTime":1561531263000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"袋","price":0,"saleNum":4,"totalSellNum":4,"totalSellAmount":110,"isRecommend":0,"status":1,"description":"","store":194,"storeStatus":0,"storeName":"薛氏蔬菜","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190627204800df7a3ddd.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":29,"maxPrice":-1,"specs":[{"specId":114867,"productId":68686,"salesNumber":4,"avgPrice":0.73,"level3Value":0,"level3Unit":"","level1Unit":"袋","level2Value":40,"price":29,"levelType":2,"siteId":2,"avgUnit":"斤","stock":146,"level2Unit":"斤"}],"monthSaleNumbers":4,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":42401,"productCriteria":"2","name":"意大利生菜","commodityId":null,"commodityName":"","nickName":"比如我看统高速发展速度很快事情","credentialsImage":"","updateTime":1547708923000,"createTime":1547708923000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"斤","price":0,"saleNum":0,"totalSellNum":0,"totalSellAmount":0,"isRecommend":0,"status":1,"description":"","store":1314,"storeStatus":0,"storeName":"侦测店铺001","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1901171507142ed87b26.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":1,"maxPrice":-1,"specs":[{"specId":88577,"productId":42401,"salesNumber":0,"avgPrice":1,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":1,"levelType":1,"siteId":2,"avgUnit":"斤","stock":1,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"的","foregift":2,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0},{"entityId":36285,"productCriteria":"1","name":"印象老家豆腐乳","commodityId":null,"commodityName":"","nickName":"测试商品不发货，勿拍","credentialsImage":"","updateTime":1544575542000,"createTime":1542608624000,"authStatus":2,"verifyInfo":"","gallery":[],"stock":0,"unit":"斤","price":0,"saleNum":0,"totalSellNum":0,"totalSellAmount":0,"isRecommend":0,"status":1,"description":"","store":1314,"storeStatus":0,"storeName":"侦测店铺001","sku":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/181119142253fceae736.jpg","smallImage":"","thumbnail":"","parentCategoryName":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":5,"maxPrice":-1,"specs":[{"specId":82476,"productId":36285,"salesNumber":0,"avgPrice":5,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":5,"levelType":1,"siteId":2,"avgUnit":"斤","stock":5555555,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"","foregift":-1,"isF":null,"storeStation":0,"storeType":2,"memberPrice":0}]
         */

        private int total;
        private List<GoodsInformation> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<GoodsInformation> getItems() {
            return items;
        }

        public void setItems(List<GoodsInformation> items) {
            this.items = items;
        }
    }
}