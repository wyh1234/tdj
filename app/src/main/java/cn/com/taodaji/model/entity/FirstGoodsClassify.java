package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-10-21.
 */

public class FirstGoodsClassify {
    private String expectDeliveredDate;
    private String expectDeliveredETime;
    private String expectDeliveredLTime;
    private BigDecimal totalPrice;
    private String nickName;
    private BigDecimal orderAmount;
    private int orderCount;
    private int orderQty;
    private BigDecimal price;
    private int productId;
    private String productImg;
    private String productName;
    private String unit;
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpectDeliveredDate() {
        return expectDeliveredDate;
    }

    public void setExpectDeliveredDate(String expectDeliveredDate) {
        this.expectDeliveredDate = expectDeliveredDate;
    }

    public String getExpectDeliveredETime() {
        return expectDeliveredETime;
    }

    public void setExpectDeliveredETime(String expectDeliveredETime) {
        this.expectDeliveredETime = expectDeliveredETime;
    }

    public String getExpectDeliveredLTime() {
        return expectDeliveredLTime;
    }

    public void setExpectDeliveredLTime(String expectDeliveredLTime) {
        this.expectDeliveredLTime = expectDeliveredLTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
