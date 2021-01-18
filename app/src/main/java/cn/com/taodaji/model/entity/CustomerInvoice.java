package cn.com.taodaji.model.entity;

import java.io.Serializable;

/**
 * Created by yangkuo on 2018-08-14.
 */
public class CustomerInvoice {


    /**
     * err : 0
     * data : {"address":"address","bankAccount":"1234567090","bankName":"农业银行","createdAt":"2018-08-08 16:08:12.0","customerId":1,"entityId":1,"invoiceTitle":"测试测试法测试fap测试法测试发票tat","invoiceType":1,"isActive":1,"taxNumber":"123456","telephone":"15071515151","updatedAt":"2018-08-08 16:34:57.0"}
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

    public static class DataBean implements Serializable{
        /**
         * address : address
         * bankAccount : 1234567090
         * bankName : 农业银行
         * createdAt : 2018-08-08 16:08:12.0
         * customerId : 1
         * entityId : 1
         * invoiceTitle : 测试测试法测试fap测试法测试发票tat
         * invoiceType : 1
         * isActive : 1
         * taxNumber : 123456
         * telephone : 15071515151
         * updatedAt : 2018-08-08 16:34:57.0
         */

        private String address;
        private String bankAccount;
        private String bankName;
        private String createdAt;
        private int customerId;
        private int entityId;
        private String invoiceTitle;
        private int invoiceType;
        private int isActive;
        private String taxNumber;
        private String telephone;
        private String updatedAt;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }

        public int getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(int invoiceType) {
            this.invoiceType = invoiceType;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
