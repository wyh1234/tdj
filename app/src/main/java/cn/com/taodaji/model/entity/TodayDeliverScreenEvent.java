package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class TodayDeliverScreenEvent implements Serializable {
    private int productId;
    private int areaId;
    private String time;
    private String product;
    private String area;
    private String productNickName;
    private int productPosition;
    private int areaPosition;
    private int isc=-1;
    private String ps_time="1";

    public String getPs_time() {
        return ps_time;
    }

    public void setPs_time(String ps_time) {
        this.ps_time = ps_time;
    }

    public int getIsc() {
        return isc;
    }

    public void setIsc(int isc) {
        this.isc = isc;
    }

    public int getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(int productPosition) {
        this.productPosition = productPosition;
    }

    public int getAreaPosition() {
        return areaPosition;
    }

    public void setAreaPosition(int areaPosition) {
        this.areaPosition = areaPosition;
    }

    public TodayDeliverScreenEvent() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductNickName() {
        return productNickName;
    }

    public void setProductNickName(String productNickName) {
        this.productNickName = productNickName;
    }
}
