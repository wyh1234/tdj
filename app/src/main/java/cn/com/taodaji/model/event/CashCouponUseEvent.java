package cn.com.taodaji.model.event;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;

public class CashCouponUseEvent {
    private int cash_coupon_count;//代金券使用数量
    private BigDecimal cash_coupon_money;//代金券金额
    private BigDecimal cash_coupon_used_money = BigDecimal.ZERO;//代金券使用金额
    private String couponItemInfo;//json字符
    private int staste;

    private ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list;

    public ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list) {
        this.list = list;
    }

    public int getStaste() {
        return staste;
    }

    public void setStaste(int staste) {
        this.staste = staste;
    }

    public String getCouponItemInfo() {
        return couponItemInfo;
    }

    public void setCouponItemInfo(String couponItemInfo) {
        this.couponItemInfo = couponItemInfo;
    }

    public int getCash_coupon_count() {
        return cash_coupon_count;
    }

    public void setCash_coupon_count(int cash_coupon_count) {
        this.cash_coupon_count = cash_coupon_count;
    }

    public BigDecimal getCash_coupon_money() {
        return cash_coupon_money;
    }

    public void setCash_coupon_money(BigDecimal cash_coupon_money) {
        this.cash_coupon_money = cash_coupon_money;
    }

    public BigDecimal getCash_coupon_used_money() {
        return cash_coupon_used_money;
    }

    public void setCash_coupon_used_money(BigDecimal cash_coupon_used_money) {
        this.cash_coupon_used_money = cash_coupon_used_money;
    }
}
