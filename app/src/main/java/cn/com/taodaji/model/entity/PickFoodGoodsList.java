package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/5 0005.
 */
public class PickFoodGoodsList {
    /**
     * total : 468
     * items : [{"entityId":490,"createTime":1472283855000,"name":"380克盐","commodityId":154,"store":5,"storeName":"老郑调料店","unit":"袋","price":2,"specialPrice":0,"image":"","stock":999998,"qrCodeId":"","categoryId":"27","description":"便宜啊静静","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"调味品-调味粉粒","categories":"27","nickName":"要打击","status":1,"gallery":[""],"phone":"18656004515","realName":"童振华","verifyInfo":"","sku":"xtb_1472283855028","credentialsImage":""}]
     * pn : 1
     * ps : 1
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
