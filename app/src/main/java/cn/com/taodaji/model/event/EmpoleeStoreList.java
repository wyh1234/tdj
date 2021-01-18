package cn.com.taodaji.model.event;

import java.util.List;

public class EmpoleeStoreList {

    /**
     * err : 0
     * data : {"list":[{"createTime":"2019-04-22 10:30:45.0","customerEntityId":3483,"empFlag":1,"employeesEntityId":46,"enterpriseCode":"zzzz","flag":1,"isCreater":"Y","isLeader":"Y","markCode":"4137dc50752e11e88a4b4501da9ff6a0_13422067707","nickName":"路大郎","parentCustomerEntityId":5,"phone":"13422067707","role":0,"siteName":"襄阳"},{"createTime":"2019-02-26 13:43:10.0","customerEntityId":3483,"empFlag":0,"employeesEntityId":1,"enterpriseCode":"草帽海贼团","flag":1,"isCreater":"Y","isLeader":"Y","markCode":"4137dc50752e11e88a4b4501da9ff6a0_13422067707","nickName":"路大郎","parentCustomerEntityId":3483,"phone":"13422067707","role":0,"siteName":"襄阳"},{"createTime":"2019-03-08 10:31:57.0","customerEntityId":3483,"empFlag":0,"employeesEntityId":6,"enterpriseCode":"草帽海贼二团","flag":1,"isCreater":"Y","isLeader":"N","markCode":"4137dc50752e11e88a4b4501da9ff6a0_13422067708","nickName":"路二郎","parentCustomerEntityId":7631,"phone":"13422067708","role":0,"siteName":"襄阳"},{"createTime":"2019-04-19 15:33:36.0","customerEntityId":3483,"empFlag":0,"employeesEntityId":41,"enterpriseCode":"淘大集 宁倩15071519512","flag":1,"isCreater":"Y","isLeader":"Y","markCode":"0f34f650835811e88efc41b0bbec39e3_15071519512","nickName":"宁","parentCustomerEntityId":3787,"phone":"15071519512","role":0,"siteName":"襄阳"},{"createTime":"2019-04-20 15:45:53.0","customerEntityId":3483,"empFlag":0,"employeesEntityId":44,"enterpriseCode":"测试2","flag":1,"isCreater":"N","isLeader":"N","markCode":"b998e8705feb11e9992c5d5b2ac5abd2_12345672222","nickName":"陈瑞","parentCustomerEntityId":7678,"phone":"12345672222","role":0,"siteName":"襄阳"}]}
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
             * createTime : 2019-04-22 10:30:45.0
             * customerEntityId : 3483
             * empFlag : 1
             * employeesEntityId : 46
             * enterpriseCode : zzzz
             * flag : 1
             * isCreater : Y
             * isLeader : Y
             * markCode : 4137dc50752e11e88a4b4501da9ff6a0_13422067707
             * nickName : 路大郎
             * parentCustomerEntityId : 5
             * phone : 13422067707
             * role : 0
             * siteName : 襄阳
             */

            private String createTime;
            private int customerEntityId;
            private int empFlag;
            private int employeesEntityId;
            private String enterpriseCode;
            private int flag;
            private String isCreater;
            private String isLeader;
            private String markCode;
            private String nickName;
            private int parentCustomerEntityId;
            private String phone;
            private int role;
            private String siteName;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

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

            public String getIsCreater() {
                return isCreater;
            }

            public void setIsCreater(String isCreater) {
                this.isCreater = isCreater;
            }

            public String getIsLeader() {
                return isLeader;
            }

            public void setIsLeader(String isLeader) {
                this.isLeader = isLeader;
            }

            public String getMarkCode() {
                return markCode;
            }

            public void setMarkCode(String markCode) {
                this.markCode = markCode;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
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

            public String getSiteName() {
                return siteName;
            }

            public void setSiteName(String siteName) {
                this.siteName = siteName;
            }
        }
    }
}
