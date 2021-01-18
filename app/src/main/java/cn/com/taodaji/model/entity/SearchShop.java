package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class SearchShop {

    private int pn;
    private int ps;
    private int total;
    private List<ShopDetail> list;

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

    public List<ShopDetail> getList() {
        return list;
    }

    public void setList(List<ShopDetail> list) {
        this.list = list;
    }

}
