package cn.com.taodaji.model.event;


public class AfterSalesCancleEvent {


    private int orderItemId;

    public AfterSalesCancleEvent(int orderId) {
        this.orderItemId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }
}
