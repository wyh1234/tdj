package cn.com.taodaji.model.event;


public class OrderDetailEvent {
    private int orderId;
    private String orderNO;
    private int couponAmount;

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public OrderDetailEvent(int orderId, String orderNO,int couponAmount) {
        this.orderId = orderId;
        this.orderNO = orderNO;
        this.couponAmount = couponAmount;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }
}
