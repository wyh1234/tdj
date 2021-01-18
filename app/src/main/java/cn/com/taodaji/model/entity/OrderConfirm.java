package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class OrderConfirm {

    /**
     * orderIds : 12
     * totalPrice : 190.0
     * outTradeNo : b34fd7a0ee9911e68b285f1f84260ea9
     */

    private String orderIds;
    private String totalPrice;
    private String outTradeNo;
    private String order_no;
    private long createTime;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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
