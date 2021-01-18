package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-07-28.
 */

public class BalanceQuery {

    /**
     * err : 0
     * data : {"entityId":1,"createTime":"2017-07-28 09:17","updateTime":"2017-07-28 09:17","status":1,"customerId":2,"customerTel":"13986397100","hotelName":"张家酒店","enterTime":"2017-07-28 09:17","extOrderId":"200000123","remark":"padding","paymentMethod":3,"isSupportBalancePayment":1,"totalPaymentAmount":89.2,"onlinePaymentAmount":0,"balancePaymentAmount":89.2}
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
         * entityId : 1
         * createTime : 2017-07-28 09:17
         * updateTime : 2017-07-28 09:17
         * status : 1
         * customerId : 2
         * customerTel : 13986397100
         * hotelName : 张家酒店
         * enterTime : 2017-07-28 09:17
         * extOrderId : 200000123
         * remark : padding
         * paymentMethod : 3
         * isSupportBalancePayment : 1
         * totalPaymentAmount : 89.2
         * onlinePaymentAmount : 0
         * balancePaymentAmount : 89.2
         */
        /**
         * isSupportBalancePayment	num	1	0、不支持余额支持 1、支持余额支持
         * totalPaymentAmount	num	1	支付的总金额
         * onlinePaymentAmount	num	1	支付宝或微信支付的总金额
         * balancePaymentAmount	num	1	余额支付的总金额
         */
        private int entityId;
        private String createTime;
        private String updateTime;
        private int status;
        private int customerId;
        private String customerTel;
        private String hotelName;
        private String enterTime;
        private String extOrderId;
        private String remark;
        private int paymentMethod;
        private int isSupportBalancePayment;
        private BigDecimal totalPaymentAmount;
        private BigDecimal onlinePaymentAmount;
        private BigDecimal balancePaymentAmount;

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerTel() {
            return customerTel;
        }

        public void setCustomerTel(String customerTel) {
            this.customerTel = customerTel;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getEnterTime() {
            return enterTime;
        }

        public void setEnterTime(String enterTime) {
            this.enterTime = enterTime;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public int getIsSupportBalancePayment() {
            return isSupportBalancePayment;
        }

        public void setIsSupportBalancePayment(int isSupportBalancePayment) {
            this.isSupportBalancePayment = isSupportBalancePayment;
        }

        public BigDecimal getTotalPaymentAmount() {
            return totalPaymentAmount;
        }

        public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
            this.totalPaymentAmount = totalPaymentAmount;
        }

        public BigDecimal getOnlinePaymentAmount() {
            return onlinePaymentAmount;
        }

        public void setOnlinePaymentAmount(BigDecimal onlinePaymentAmount) {
            this.onlinePaymentAmount = onlinePaymentAmount;
        }

        public BigDecimal getBalancePaymentAmount() {
            return balancePaymentAmount;
        }

        public void setBalancePaymentAmount(BigDecimal balancePaymentAmount) {
            this.balancePaymentAmount = balancePaymentAmount;
        }
    }
}
