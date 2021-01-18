package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by zhaowenlong on 2019/3/20.
 */
public class EmployeesListBean {

    /**
     * err : 0
     * data : {"gmoList":[{"customerEntityId":3483,"empCount":7,"empFlag":1,"employeesEntityId":2,"enterpriseCode":"草帽饮食管理有限公司","flag":0,"ownedStores":"3483,7631","parentCustomerEntityId":2,"role":0}],"list":[{"address":"大李沟路3号汉江创业创新产业园二楼","createTime":"2019-03-16 09:58:51.0","customerEntityId":3787,"empCount":4,"empFlag":0,"employeesEntityId":8,"enterpriseCode":"淘大集 宁倩15071519512","flag":1,"isCreater":"Y","isLeader":"N","nickName":"宁","parentCustomerEntityId":3787,"phone":"15071519512","role":0,"siteName":"襄阳","subInfoList":[{"isCreater":"Y","employeesEntityId":1,"phone":"13422067707","flag":1,"enterpriseCode":"草帽海贼团","customerEntityId":3483,"nickName":"路大郎","workName":"路11","role":0,"isLeader":"Y","markCode":"4137dc50752e11e88a4b4501da9ff6a0_13422067707","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"Y","employeesEntityId":8,"phone":"15071519512","flag":1,"enterpriseCode":"淘大集 宁倩15071519512","customerEntityId":3787,"nickName":"宁","workName":"aa11","role":0,"isLeader":"N","markCode":"0f34f650835811e88efc41b0bbec39e3_15071519512","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"N","employeesEntityId":9,"phone":"13476102622","flag":1,"enterpriseCode":"淘大集","customerEntityId":7627,"nickName":"刘梦","workName":"bbb11","role":0,"isLeader":"N","markCode":"5aeb619033ea11e9b8f13be120405d0e_13476102622","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"N","employeesEntityId":10,"phone":"18727120999","flag":1,"enterpriseCode":"嘿嘿嘿","customerEntityId":7593,"nickName":"测试淘大集","workName":"ccc11","role":0,"isLeader":"Y","markCode":"508a5010123111e9a506957a7e447d44_18727120999","parentCustomerEntityId":3787,"empFlag":0}]}]}
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
        private List<GmoListBean> gmoList;
        private List<ListBean> list;

        public List<GmoListBean> getGmoList() {
            return gmoList;
        }

        public void setGmoList(List<GmoListBean> gmoList) {
            this.gmoList = gmoList;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class GmoListBean {
            /**
             * customerEntityId : 3483
             * empCount : 7
             * empFlag : 1
             * employeesEntityId : 2
             * enterpriseCode : 草帽饮食管理有限公司
             * flag : 0
             * ownedStores : 3483,7631
             * parentCustomerEntityId : 2
             * role : 0
             */

            private int customerEntityId;
            private int empCount;
            private int empFlag;
            private int employeesEntityId;
            private String enterpriseCode;
            private int flag;
            private String ownedStores;
            private int parentCustomerEntityId;
            private int role;

            public int getCustomerEntityId() {
                return customerEntityId;
            }

            public void setCustomerEntityId(int customerEntityId) {
                this.customerEntityId = customerEntityId;
            }

            public int getEmpCount() {
                return empCount;
            }

            public void setEmpCount(int empCount) {
                this.empCount = empCount;
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

            public String getOwnedStores() {
                return ownedStores;
            }

            public void setOwnedStores(String ownedStores) {
                this.ownedStores = ownedStores;
            }

            public int getParentCustomerEntityId() {
                return parentCustomerEntityId;
            }

            public void setParentCustomerEntityId(int parentCustomerEntityId) {
                this.parentCustomerEntityId = parentCustomerEntityId;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }
        }

        public static class ListBean {
            /**
             * address : 大李沟路3号汉江创业创新产业园二楼
             * createTime : 2019-03-16 09:58:51.0
             * customerEntityId : 3787
             * empCount : 4
             * empFlag : 0
             * employeesEntityId : 8
             * enterpriseCode : 淘大集 宁倩15071519512
             * flag : 1
             * isCreater : Y
             * isLeader : N
             * nickName : 宁
             * parentCustomerEntityId : 3787
             * phone : 15071519512
             * role : 0
             * siteName : 襄阳
             * subInfoList : [{"isCreater":"Y","employeesEntityId":1,"phone":"13422067707","flag":1,"enterpriseCode":"草帽海贼团","customerEntityId":3483,"nickName":"路大郎","workName":"路11","role":0,"isLeader":"Y","markCode":"4137dc50752e11e88a4b4501da9ff6a0_13422067707","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"Y","employeesEntityId":8,"phone":"15071519512","flag":1,"enterpriseCode":"淘大集 宁倩15071519512","customerEntityId":3787,"nickName":"宁","workName":"aa11","role":0,"isLeader":"N","markCode":"0f34f650835811e88efc41b0bbec39e3_15071519512","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"N","employeesEntityId":9,"phone":"13476102622","flag":1,"enterpriseCode":"淘大集","customerEntityId":7627,"nickName":"刘梦","workName":"bbb11","role":0,"isLeader":"N","markCode":"5aeb619033ea11e9b8f13be120405d0e_13476102622","parentCustomerEntityId":3787,"empFlag":0},{"isCreater":"N","employeesEntityId":10,"phone":"18727120999","flag":1,"enterpriseCode":"嘿嘿嘿","customerEntityId":7593,"nickName":"测试淘大集","workName":"ccc11","role":0,"isLeader":"Y","markCode":"508a5010123111e9a506957a7e447d44_18727120999","parentCustomerEntityId":3787,"empFlag":0}]
             */

            private String address;
            private String createTime;
            private int customerEntityId;
            private int empCount;
            private int empFlag;
            private int employeesEntityId;
            private String enterpriseCode;
            private int flag;
            private String isCreater;
            private String isLeader;
            private String nickName;
            private int parentCustomerEntityId;
            private String phone;
            private int role;
            private String siteName;
            private List<SubInfoListBean> subInfoList;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

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

            public int getEmpCount() {
                return empCount;
            }

            public void setEmpCount(int empCount) {
                this.empCount = empCount;
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

            public List<SubInfoListBean> getSubInfoList() {
                return subInfoList;
            }

            public void setSubInfoList(List<SubInfoListBean> subInfoList) {
                this.subInfoList = subInfoList;
            }

            public static class SubInfoListBean {
                /**
                 * isCreater : Y
                 * employeesEntityId : 1
                 * phone : 13422067707
                 * flag : 1
                 * enterpriseCode : 草帽海贼团
                 * customerEntityId : 3483
                 * nickName : 路大郎
                 * workName : 路11
                 * role : 0
                 * isLeader : Y
                 * markCode : 4137dc50752e11e88a4b4501da9ff6a0_13422067707
                 * parentCustomerEntityId : 3787
                 * empFlag : 0
                 */

                private String isCreater;
                private int employeesEntityId;
                private String phone;
                private int flag;
                private String enterpriseCode;
                private int customerEntityId;
                private String nickName;
                private String workName;
                private int role;
                private String isLeader;
                private String markCode;
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

                public String getEnterpriseCode() {
                    return enterpriseCode;
                }

                public void setEnterpriseCode(String enterpriseCode) {
                    this.enterpriseCode = enterpriseCode;
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

                public String getWorkName() {
                    return workName;
                }

                public void setWorkName(String workName) {
                    this.workName = workName;
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

                public String getMarkCode() {
                    return markCode;
                }

                public void setMarkCode(String markCode) {
                    this.markCode = markCode;
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
}
