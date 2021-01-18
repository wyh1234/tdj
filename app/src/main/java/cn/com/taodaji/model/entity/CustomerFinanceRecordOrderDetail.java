package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-08-19.
 */

public class CustomerFinanceRecordOrderDetail {

    /**
     * err : 0
     * data : {"outTradeNo":"58ff20e0319611e78bfe4f960d1051ff","extOrderId":0,"paymentAmount":26.23,"onlinePaymentAmount":0,"orderCreateTime":"0000-00-00 00:00:00","orderPayTime":"0000-00-00 00:00:00","expectDeliveredDate":"2017-05-06","expectDeliveredEarliestTime":"09:30","expectDeliveredLatestTime":"11:30","receiveAddress":"襄阳_大庆西路黄牛庄","receiveHotelName":"黄牛庄大庆店","receiveName":"马丰","receiveTel":"15392774567"}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * outTradeNo : 58ff20e0319611e78bfe4f960d1051ff
         * extOrderId : 0
         * paymentAmount : 26.23
         * onlinePaymentAmount : 0
         * orderCreateTime : 0000-00-00 00:00:00
         * orderPayTime : 0000-00-00 00:00:00
         * expectDeliveredDate : 2017-05-06
         * expectDeliveredEarliestTime : 09:30
         * expectDeliveredLatestTime : 11:30
         * receiveAddress : 襄阳_大庆西路黄牛庄
         * receiveHotelName : 黄牛庄大庆店
         * receiveName : 马丰
         * receiveTel : 15392774567
         */

        private String outTradeNo;
        private String extOrderId;
        private BigDecimal paymentAmount;
        private BigDecimal onlinePaymentAmount;
        private String orderCreateTime;
        private String orderPayTime;
        private String expectDeliveredDate;
        private String expectDeliveredEarliestTime;
        private String expectDeliveredLatestTime;
        private String receiveAddress;
        private String receiveHotelName;
        private String receiveName;
        private String receiveTel;
        private int paymentMethod;

        private String reason;
        private String createTime;


        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public BigDecimal getOnlinePaymentAmount() {
            return onlinePaymentAmount;
        }

        public void setOnlinePaymentAmount(BigDecimal onlinePaymentAmount) {
            this.onlinePaymentAmount = onlinePaymentAmount;
        }

        public String getOrderCreateTime() {
            return orderCreateTime;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
        }

        public String getOrderPayTime() {
            return orderPayTime;
        }

        public void setOrderPayTime(String orderPayTime) {
            this.orderPayTime = orderPayTime;
        }

        public String getExpectDeliveredDate() {
            return expectDeliveredDate;
        }

        public void setExpectDeliveredDate(String expectDeliveredDate) {
            this.expectDeliveredDate = expectDeliveredDate;
        }

        public String getExpectDeliveredEarliestTime() {
            return expectDeliveredEarliestTime;
        }

        public void setExpectDeliveredEarliestTime(String expectDeliveredEarliestTime) {
            this.expectDeliveredEarliestTime = expectDeliveredEarliestTime;
        }

        public String getExpectDeliveredLatestTime() {
            return expectDeliveredLatestTime;
        }

        public void setExpectDeliveredLatestTime(String expectDeliveredLatestTime) {
            this.expectDeliveredLatestTime = expectDeliveredLatestTime;
        }

        public String getReceiveAddress() {
            return receiveAddress;
        }

        public void setReceiveAddress(String receiveAddress) {
            this.receiveAddress = receiveAddress;
        }

        public String getReceiveHotelName() {
            return receiveHotelName;
        }

        public void setReceiveHotelName(String receiveHotelName) {
            this.receiveHotelName = receiveHotelName;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceiveTel() {
            return receiveTel;
        }

        public void setReceiveTel(String receiveTel) {
            this.receiveTel = receiveTel;
        }
    }
}
