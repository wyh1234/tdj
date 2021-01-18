package cn.com.taodaji.model.entity;

import java.util.List;

public class GoodsReceiptAddress {

    /**
     * total : 1
     * items : [{"addressId":20,"isActive":0,"createTime":"2016-12-28 09:29","updateTime":"2016-12-28 09:29","consignee":"张峰奇","phoneNumber":"18789098765","provinceId":13,"provinceName":"湖北","cityId":193,"cityName":"襄阳","countyId":1,"countyName":"中国","hotelName":"天圣酒店","street":"胜利街","customerId":29,"gender":0}]
     * pn : 1
     * ps : 10
     */

    private int total;
    private int pn;
    private int ps;
    private List<GoodsReceiptAddressBean> items;

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

    public List<GoodsReceiptAddressBean> getItems() {
        return items;
    }

    public void setItems(List<GoodsReceiptAddressBean> items) {
        this.items = items;
    }


}

