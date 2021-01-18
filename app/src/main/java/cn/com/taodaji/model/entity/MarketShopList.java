package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class MarketShopList {

    /**
     * list : [{"code":"sijiqing","contactName":"","contactPhone":"15271967861","createTime":1471048877000,"groupId":10,"isActive":0,"marketNo":"","name":"兴发米面店","orderNumber":6,"productNumber":42,"site":2,"siteName":"淘大集","store":10,"storeStatus":0,"storeStatusRemark":"","logoImageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608151038061ae4e049.jpg","marketName":"四季青米面市场","items":[{"entityId":464,"createTime":1472197320000,"name":"德林面粉","commodityId":265,"store":10,"storeName":"兴发米面店","unit":"斤","price":1.9,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"49","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-面","categories":"49","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608261541595d64c5da.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472197320086","credentialsImage":""},{"entityId":467,"createTime":1472197500000,"name":"德林面条","commodityId":266,"store":10,"storeName":"兴发米面店","unit":"斤","price":2,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"49","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-面","categories":"49","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160826154500803dee70.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472197500767","credentialsImage":""},{"entityId":472,"createTime":1472199084000,"name":"东北长粒香米","commodityId":259,"store":10,"storeName":"兴发米面店","unit":"斤","price":3.25,"specialPrice":0,"image":"","stock":999998,"qrCodeId":"","categoryId":"48","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-米","categories":"48","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082616112382f0f3fa.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472199084206","credentialsImage":""}]},{"code":"mimian1","contactName":"","contactPhone":"13372775567","createTime":1471853929000,"groupId":16,"isActive":1,"marketNo":"","name":"东方粮油","orderNumber":0,"productNumber":9,"site":2,"siteName":"淘大集","store":16,"storeStatus":0,"storeStatusRemark":"","logoImageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160822164452276618cc.jpg","marketName":"四季青米面市场","items":[{"entityId":430,"createTime":1471941929000,"name":"菜籽油","commodityId":279,"store":16,"storeName":"东方粮油","unit":"斤","price":6.5,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"50","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-油","categories":"50","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823164529ee05b4b6.jpg"],"phone":"13372775567","realName":"陈泽","verifyInfo":"","sku":"xtb_1471941929852","credentialsImage":""},{"entityId":425,"createTime":1471941728000,"name":"大米","commodityId":263,"store":16,"storeName":"东方粮油","unit":"斤","price":2,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"48","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-米","categories":"48","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823164207591f8d65.jpg"],"phone":"13372775567","realName":"陈泽","verifyInfo":"","sku":"xtb_1471941728004","credentialsImage":""},{"entityId":426,"createTime":1471941763000,"name":"东北大米","commodityId":263,"store":16,"storeName":"东方粮油","unit":"斤","price":2.4,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"48","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-米","categories":"48","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823164243d8eeaae2.jpg"],"phone":"13372775567","realName":"陈泽","verifyInfo":"","sku":"xtb_1471941763361","credentialsImage":""}]}]
     * pn : 1
     * ps : 10
     * total : 2
     */

    private int pn;
    private int ps;
    private int total;
    private List<ShopInformation> list;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ShopInformation> getList() {
        return list;
    }

    public void setList(List<ShopInformation> list) {
        this.list = list;
    }

}



