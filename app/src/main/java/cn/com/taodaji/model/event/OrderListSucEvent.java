package cn.com.taodaji.model.event;

/**
 * 订单支付成功事件
 */
public class OrderListSucEvent {


    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    private String outTradeNo;


    public OrderListSucEvent(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
