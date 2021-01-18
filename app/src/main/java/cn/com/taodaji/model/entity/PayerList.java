package cn.com.taodaji.model.entity;

import java.util.List;

public class PayerList {

    /**
     * err : 0
     * data : {"list":[{"isCreater":"N","employeesEntityId":3,"phone":"13422067701","flag":2,"customerEntityId":7630,"nickName":"宋大财","role":2,"isLeader":"N","parentCustomerEntityId":3483,"empFlag":0},{"isCreater":"N","employeesEntityId":11,"phone":"13400000001","flag":2,"customerEntityId":7635,"nickName":"店老大","role":4,"isLeader":"N","parentCustomerEntityId":3483,"empFlag":0}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * isCreater : N
             * employeesEntityId : 3
             * phone : 13422067701
             * flag : 2
             * customerEntityId : 7630
             * nickName : 宋大财
             * role : 2
             * isLeader : N
             * parentCustomerEntityId : 3483
             * empFlag : 0
             */

            private String isCreater;
            private int employeesEntityId;
            private String phone;
            private int flag;
            private int customerEntityId;
            private String nickName;
            private int role;
            private String isLeader;
            private int parentCustomerEntityId;
            private int empFlag;

            public String getIsCreater() {
                return isCreater;
            }

            public void setIsCreater(String isCreater) {
                this.isCreater = isCreater;
            }

            public int getEmployeesEntityId() {
                return employeesEntityId;
            }

            public void setEmployeesEntityId(int employeesEntityId) {
                this.employeesEntityId = employeesEntityId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getCustomerEntityId() {
                return customerEntityId;
            }

            public void setCustomerEntityId(int customerEntityId) {
                this.customerEntityId = customerEntityId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getIsLeader() {
                return isLeader;
            }

            public void setIsLeader(String isLeader) {
                this.isLeader = isLeader;
            }

            public int getParentCustomerEntityId() {
                return parentCustomerEntityId;
            }

            public void setParentCustomerEntityId(int parentCustomerEntityId) {
                this.parentCustomerEntityId = parentCustomerEntityId;
            }

            public int getEmpFlag() {
                return empFlag;
            }

            public void setEmpFlag(int empFlag) {
                this.empFlag = empFlag;
            }
        }
    }
}
