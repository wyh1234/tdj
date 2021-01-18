package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateTfAdvManage  implements Serializable {
    private ArrayList<SelGoods.DataBean.ItemsBean> goods;
    private boolean b;
    private boolean f;
    private String day;//输入的天数
    private String time;//投放时间
    private int advPackageId;//广告套餐id
    private String advPackageName;
    private BigDecimal advPackagePice;
    private String jsDay;
    private String remark;//备注；
    private int limit_days;

    public int getLimit_days() {
        return limit_days;
    }

    public void setLimit_days(int limit_days) {
        this.limit_days = limit_days;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAdvPackagePice() {
        return advPackagePice;
    }

    public void setAdvPackagePice(BigDecimal advPackagePice) {
        this.advPackagePice = advPackagePice;
    }

    public String getJsDay() {
        return jsDay;
    }

    public void setJsDay(String jsDay) {
        this.jsDay = jsDay;
    }

    public int getAdvPackageId() {
        return advPackageId;
    }

    public void setAdvPackageId(int advPackageId) {
        this.advPackageId = advPackageId;
    }

    public String getAdvPackageName() {
        return advPackageName;
    }

    public void setAdvPackageName(String advPackageName) {
        this.advPackageName = advPackageName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public ArrayList<SelGoods.DataBean.ItemsBean> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<SelGoods.DataBean.ItemsBean> goods) {
        this.goods = goods;
    }

}
