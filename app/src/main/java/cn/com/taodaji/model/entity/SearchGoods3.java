package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017-04-17.
 */
public class SearchGoods3 {

    /**
     * total : 4
     * items : [{"entityId":356,"createTime":1491898371000,"updateTime":1492177820000,"name":"土豆","commodityId":51,"store":34,"storeName":"潘老大土豆洋葱","unit":"斤","price":0.8,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170411161226ce475f93.jpg","stock":9968,"qrCodeId":"","categoryId":"16","description":"营养丰富  绿色健康","saleNum":31,"authStatus":2,"bannerImage":"","catalogCategoryName":"新鲜蔬菜-根茎类","categories":"16","nickName":"黄心中土豆","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170411161226ce475f93.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170411215644f58a1a8e.jpg"],"phone":"15872275985","realName":"潘会","verifyInfo":"","sku":"xtb_1491898370787","credentialsImage":"","memberPrice":1.04},{"entityId":357,"createTime":1491898404000,"updateTime":1492349514000,"name":"土豆","commodityId":51,"store":34,"storeName":"潘老大土豆洋葱","unit":"斤","price":1.2,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704111613012b2d0e81.jpg","stock":9958,"qrCodeId":"","categoryId":"16","description":"营养丰富  绿色健康","saleNum":51,"authStatus":2,"bannerImage":"","catalogCategoryName":"新鲜蔬菜-根茎类","categories":"16","nickName":"黄心大土豆","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704111613012b2d0e81.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170411161304a823cdd7.jpg"],"phone":"15872275985","realName":"潘会","verifyInfo":"","sku":"xtb_1491898404136","credentialsImage":"","memberPrice":1.56},{"entityId":355,"createTime":1491898335000,"updateTime":1492347604000,"name":"小土豆","commodityId":58,"store":34,"storeName":"潘老大土豆洋葱","unit":"斤","price":1.2,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17041116110311862bbc.jpg","stock":9948,"qrCodeId":"","categoryId":"16","description":"营养丰富 绿色健康适合油炸干锅","saleNum":51,"authStatus":2,"bannerImage":"","catalogCategoryName":"新鲜蔬菜-根茎类","categories":"16","nickName":"黄心小土豆","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17041116110311862bbc.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17041122142516816847.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17041122143482ea185d.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704112214437d6648c0.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170411221448946e1a10.jpg"],"phone":"15872275985","realName":"潘会","verifyInfo":"","sku":"xtb_1491898334814","credentialsImage":"","memberPrice":1.56},{"entityId":431,"createTime":1492213014000,"updateTime":1492225916000,"name":"土豆","commodityId":51,"store":34,"storeName":"潘老大土豆洋葱","unit":"袋","price":100,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704150734041c152997.jpg","stock":2000,"qrCodeId":"","categoryId":"16","description":"需提前一天预定，每袋50斤","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"新鲜蔬菜-根茎类","categories":"16","nickName":"去皮黄心小土豆","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704150734041c152997.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704150734139291679c.jpg"],"phone":"15872275985","realName":"潘会","verifyInfo":"","sku":"xtb_1492213013817","credentialsImage":"","memberPrice":130}]
     * pn : 1
     * ps : 10
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

