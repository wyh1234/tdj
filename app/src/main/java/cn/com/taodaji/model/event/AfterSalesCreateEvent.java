package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-06-02.
 */
public class AfterSalesCreateEvent {


    private int orderItemId;

    public AfterSalesCreateEvent(int orderId) {
        this.orderItemId = orderId;
    }


    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }
}
