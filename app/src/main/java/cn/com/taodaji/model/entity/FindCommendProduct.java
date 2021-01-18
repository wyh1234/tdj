package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class FindCommendProduct {

    /**
     * total : 1
     * items : [{"entityId":364,"createTime":1486349767000,"name":"黑胡椒","commodityId":100,"store":13,"storeName":"窝窝蔬菜","unit":"千克","price":12,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","stock":2000,"qrCodeId":"","categoryId":"25","description":"他咯哦","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"调味品-椒类","categories":"25","nickName":"小黑胡椒","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061055408c92ff1e.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061054492468ff70.jpg"],"phone":"18571161280","realName":"格子","verifyInfo":"","sku":"xtb_1486349767730","credentialsImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170206105527cb28e3c1.jpg","memberPrice":14.399999999999999}]
     * pn : 1
     * ps : 20
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

