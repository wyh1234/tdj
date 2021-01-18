package cn.com.taodaji.model.entity;

public class OrderPlaceBack {

    /**
     * orderIds : 12
     * totalPrice : 190.0
     * outTradeNo : b34fd7a0ee9911e68b285f1f84260ea9
     */

    private String orderIds;
    private String totalPrice;
    private String outTradeNo;
    private long createTime;
    private String extOrderId;
    private String actualTotalPrice;

    public String getActualTotalPrice() {
        return actualTotalPrice;
    }

    public void setActualTotalPrice(String actualTotalPrice) {
        this.actualTotalPrice = actualTotalPrice;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public void setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}

