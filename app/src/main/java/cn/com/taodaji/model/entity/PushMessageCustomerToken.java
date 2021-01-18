package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2018-01-19.
 */

public class PushMessageCustomerToken {

    /**
     * err : 0
     * data : {"entityId":1,"createTime":"2018-01-08 17:04","updateTime":"2018-01-08 17:04","status":1,"type":2,"customerId":234,"supplierId":-1,"telephone":"18789876766","customerFlag":0,"customerSubuserId":234,"customerRole":0,"token":"TOKENDSFSDFJLSSKDJFISDFJJLSSLL"}
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
         * createTime : 2018-01-08 17:04
         * updateTime : 2018-01-08 17:04
         * status : 1
         * type : 2
         * customerId : 234
         * supplierId : -1
         * telephone : 18789876766
         * customerFlag : 0
         * customerSubuserId : 234
         * customerRole : 0
         * token : TOKENDSFSDFJLSSKDJFISDFJJLSSLL
         */

        private int entityId;
        private String createTime;
        private String updateTime;
        private int status;
        private int type;
        private int customerId;
        private int supplierId;
        private String telephone;
        private int customerFlag;
        private int customerSubuserId;
        private int customerRole;
        private String token;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getCustomerFlag() {
            return customerFlag;
        }

        public void setCustomerFlag(int customerFlag) {
            this.customerFlag = customerFlag;
        }

        public int getCustomerSubuserId() {
            return customerSubuserId;
        }

        public void setCustomerSubuserId(int customerSubuserId) {
            this.customerSubuserId = customerSubuserId;
        }

        public int getCustomerRole() {
            return customerRole;
        }

        public void setCustomerRole(int customerRole) {
            this.customerRole = customerRole;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
