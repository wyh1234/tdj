package cn.com.taodaji.model.entity;

public class EidtEmployeeInfo {

    /**
     * err : 0
     * data : {"customerEntityId":7888,"empFlag":1,"employeesEntityId":383,"enterpriseCode":"包子","flag":1,"parentCustomerEntityId":46,"phone":"13871622414","role":0,"workName":"陈玉婷"}
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
         * customerEntityId : 7888
         * empFlag : 1
         * employeesEntityId : 383
         * enterpriseCode : 包子
         * flag : 1
         * parentCustomerEntityId : 46
         * phone : 13871622414
         * role : 0
         * workName : 陈玉婷
         */

        private int customerEntityId;
        private int empFlag;
        private int employeesEntityId;
        private String enterpriseCode;
        private int flag;
        private int parentCustomerEntityId;
        private String phone;
        private int role;
        private String workName;

        public int getCustomerEntityId() {
            return customerEntityId;
        }

        public void setCustomerEntityId(int customerEntityId) {
            this.customerEntityId = customerEntityId;
        }

        public int getEmpFlag() {
            return empFlag;
        }

        public void setEmpFlag(int empFlag) {
            this.empFlag = empFlag;
        }

        public int getEmployeesEntityId() {
            return employeesEntityId;
        }

        public void setEmployeesEntityId(int employeesEntityId) {
            this.employeesEntityId = employeesEntityId;
        }

        public String getEnterpriseCode() {
            return enterpriseCode;
        }

        public void setEnterpriseCode(String enterpriseCode) {
            this.enterpriseCode = enterpriseCode;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getParentCustomerEntityId() {
            return parentCustomerEntityId;
        }

        public void setParentCustomerEntityId(int parentCustomerEntityId) {
            this.parentCustomerEntityId = parentCustomerEntityId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }
    }
}
