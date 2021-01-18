package cn.com.taodaji.model.entity;

import java.util.List;

public class HomepageGridData {

    /**
     * total : 240
     * items : [{"entityId":490,"createTime":1472283855000,"name":"380克盐","commodityId":154,"store":5,"storeName":"老郑调料店","unit":"袋","price":2,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082715441443c16501.jpg","stock":999998,"qrCodeId":"","categoryId":"27","description":"default desc","saleNum":0,"authStatus":1,"bannerImage":"","catalogCategoryName":"调味粉粒","categories":"27","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082715441443c16501.jpg"],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1472283855028","credentialsImage":""},{"entityId":155,"createTime":1467973291000,"name":"760克泰国鸡酱","commodityId":70,"store":5,"storeName":"老郑调料店","unit":"瓶","price":7.5,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081821317f4351c8.jpeg","stock":999986,"qrCodeId":"0b5ad7665b9611e6aa68a0999b04e4c7","categoryId":"24","description":"default desc","saleNum":0,"authStatus":1,"bannerImage":"","catalogCategoryName":"调味酱","categories":"24","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081821317f4351c8.jpeg"],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1467973291859","credentialsImage":""},{"entityId":356,"createTime":1467976514000,"name":"白醋((保宁牌)","commodityId":124,"store":5,"storeName":"老郑调料店","unit":"瓶","price":1.8,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708191514e90b5275.jpeg","stock":999997,"qrCodeId":"","categoryId":"26","description":"default desc","saleNum":0,"authStatus":1,"bannerImage":"","catalogCategoryName":"酱油料酒醋汁","categories":"26","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708191514e90b5275.jpeg"],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1467976514487","credentialsImage":""},{"entityId":281,"createTime":1467975188000,"name":"白胡椒","commodityId":96,"store":5,"storeName":"老郑调料店","unit":"斤","price":45,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708185308c876ca36.jpeg","stock":999999,"qrCodeId":"","categoryId":"25","description":"default desc","saleNum":0,"authStatus":1,"bannerImage":"","catalogCategoryName":"椒类","categories":"25","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708185308c876ca36.jpeg"],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1467975188968","credentialsImage":""},{"entityId":283,"createTime":1467975219000,"name":"白胡椒粉","commodityId":97,"store":5,"storeName":"老郑调料店","unit":"斤","price":45,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708185339d7984c66.jpeg","stock":999996,"qrCodeId":"","categoryId":"25","description":"default desc","saleNum":0,"authStatus":1,"bannerImage":"","catalogCategoryName":"椒类","categories":"25","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708185339d7984c66.jpeg"],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1467975219644","credentialsImage":""}]
     * pn : 1
     * ps : 5
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




