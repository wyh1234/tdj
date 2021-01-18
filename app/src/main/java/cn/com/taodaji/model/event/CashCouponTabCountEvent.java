package cn.com.taodaji.model.event;

import java.util.ArrayList;

import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;

/**
 * Created by yangkuo on 2018/12/7.
 */
public class CashCouponTabCountEvent {
    private int count;
    private String title;
    private int index;

    public CashCouponTabCountEvent(int count, String title, int index) {
        this.count = count;
        this.title = title;
        this.index = index;
    }
    private ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list;

    public CashCouponTabCountEvent(ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list) {
        this.list = list;
    }

    public ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list) {
        this.list = list;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
