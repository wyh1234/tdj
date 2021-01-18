package cn.com.taodaji.model.event;

import java.io.Serializable;
import java.util.ArrayList;

import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;

public class CashCouponListEvent implements Serializable {
    private ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list;

    public CashCouponListEvent(ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list) {
        this.list = list;
    }

    public ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list) {
        this.list = list;
    }

    }



