package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-05-13.
 */
public class RechargeCreate {

    /**
     * err : 0
     * data : {"entityId":12,"createTime":"2017-05-11 15:05","customerId":10,"customerName":"张三","capital":23,"paymentMethod":"alipay_payment","outTradeNo":"33cb3580361811e78930d95ff60e5bb1","remarks":"padding","isPay":0}
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
         * entityId : 12
         * createTime : 2017-05-11 15:05
         * customerId : 10
         * customerName : 张三
         * capital : 23
         * paymentMethod : alipay_payment
         * outTradeNo : 33cb3580361811e78930d95ff60e5bb1
         * remarks : padding
         * isPay : 0
         */

        private int entityId;
        private String createTime;
        private int customerId;
        private String customerName;
        private float capital;
        private String paymentMethod;
        private String outTradeNo;
        private String remarks;
        private int isPay;

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

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public float getCapital() {
            return capital;
        }

        public void setCapital(float capital) {
            this.capital = capital;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }
    }
}
