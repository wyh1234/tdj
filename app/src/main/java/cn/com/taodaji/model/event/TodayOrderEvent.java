package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-10-23.
 */

public class TodayOrderEvent{

    private int orderId;

    public TodayOrderEvent(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
