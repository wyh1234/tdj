package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-08-19.
 */

public class CustomerFinanceRecordRechargeDetail {

    /**
     * err : 0
     * data : {"paymentAmount":26.23,"paymentMethod":4,"payTime":"2017-06-29 15:39","role":"0","customerName":"又又","transactionNumber":""}
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
         * paymentAmount : 26.23
         * paymentMethod : 4
         * payTime : 2017-06-29 15:39
         * role : 0
         * customerName : 又又
         * transactionNumber :
         */

        private BigDecimal paymentAmount;
        private int paymentMethod;
        private String payTime;
        private String role;
        private String customerName;
        private String transactionNumber;

        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }
    }
}
