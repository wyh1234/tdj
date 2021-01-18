package cn.com.taodaji.model.entity;

public class StoreRoleBean {
    /**
     * err : 0
     * data : {"empRole":0,"customerEntityId":3483,"parentCustomerEntityId":3483}
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
         * empRole : 0
         * customerEntityId : 3483
         * parentCustomerEntityId : 3483
         */

        private int empRole;
        private int customerEntityId;
        private int parentCustomerEntityId;

        public int getEmpRole() {
            return empRole;
        }

        public void setEmpRole(int empRole) {
            this.empRole = empRole;
        }

        public int getCustomerEntityId() {
            return customerEntityId;
        }

        public void setCustomerEntityId(int customerEntityId) {
            this.customerEntityId = customerEntityId;
        }

        public int getParentCustomerEntityId() {
            return parentCustomerEntityId;
        }

        public void setParentCustomerEntityId(int parentCustomerEntityId) {
            this.parentCustomerEntityId = parentCustomerEntityId;
        }
    }
}
