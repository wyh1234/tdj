package cn.com.taodaji.model.entity;

import java.util.List;

public class ChainShopList {
    /**
     * err : 0
     * data : {"list":[{"address":"春园东路15号","createTime":"2019-04-26 16:00:14.0","customerEntityId":7763,"empCount":1,"empFlag":1,"employeesEntityId":176,"enterpriseCode":"总部","flag":1,"isCreater":"Y","isLeader":"Y","nickName":"taotao","parentCustomerEntityId":24,"phone":"15700000008","role":0,"siteName":"襄阳","subInfoList":[{"isCreater":"Y","employeesEntityId":176,"phone":"15700000008","flag":1,"enterpriseCode":"tao1","customerEntityId":7763,"nickName":"taotao","workName":"fd","role":0,"isLeader":"Y","markCode":"cbf7b63067f811e9a13fdfdc7c2bd769_15700000008","parentCustomerEntityId":24,"empFlag":1}]},{"address":"春园东路15号","createTime":"2019-04-26 15:56:28.0","customerEntityId":7763,"empCount":2,"empFlag":0,"employeesEntityId":170,"enterpriseCode":"tao1","flag":1,"isCreater":"Y","isLeader":"Y","nickName":"taotao","parentCustomerEntityId":7763,"phone":"15700000008","role":0,"siteName":"襄阳","subInfoList":[{"isCreater":"Y","employeesEntityId":170,"phone":"15700000008","flag":1,"enterpriseCode":"tao1","customerEntityId":7763,"nickName":"taotao","workName":"小甜甜","role":0,"isLeader":"Y","markCode":"cbf7b63067f811e9a13fdfdc7c2bd769_15700000008","parentCustomerEntityId":7763,"empFlag":0},{"isCreater":"N","employeesEntityId":231,"phone":"15700000031","flag":1,"enterpriseCode":"tao5","customerEntityId":7772,"nickName":"拉了","workName":"赫本","role":2,"isLeader":"N","markCode":"094c9f2068b011e9a7a2b355fa5ad0f8_15700000031","parentCustomerEntityId":7763,"empFlag":0}]},{"address":"高新区大李沟路3号汉江创业创新产业园","createTime":"2019-04-26 15:57:50.0","customerEntityId":7764,"empCount":5,"empFlag":0,"employeesEntityId":172,"enterpriseCode":"tao2","flag":1,"isCreater":"N","isLeader":"Y","nickName":"来","parentCustomerEntityId":7764,"phone":"15700000014","role":0,"siteName":"襄阳","subInfoList":[{"isCreater":"N","employeesEntityId":172,"phone":"15700000014","flag":1,"enterpriseCode":"tao2","customerEntityId":7764,"nickName":"来","workName":"未设置姓名","role":0,"isLeader":"Y","markCode":"fc9cde0067f811e9a13fdfdc7c2bd769_15700000014","parentCustomerEntityId":7764,"empFlag":0},{"isCreater":"Y","employeesEntityId":173,"phone":"15700000008","flag":1,"enterpriseCode":"tao1","customerEntityId":7763,"nickName":"taotao","workName":"ddd","role":0,"isLeader":"N","markCode":"cbf7b63067f811e9a13fdfdc7c2bd769_15700000008","parentCustomerEntityId":7764,"empFlag":0},{"isCreater":"N","employeesEntityId":226,"phone":"15700000030","flag":1,"enterpriseCode":"tao4","customerEntityId":7770,"nickName":"明","workName":"未设置姓名","role":1,"isLeader":"N","markCode":"df0c94e068af11e9a7a2b355fa5ad0f8_15700000030","parentCustomerEntityId":7764,"empFlag":0},{"isCreater":"N","employeesEntityId":227,"phone":"15700000031","flag":1,"enterpriseCode":"tao5","customerEntityId":7772,"nickName":"拉了","workName":"未设置姓名","role":1,"isLeader":"N","markCode":"094c9f2068b011e9a7a2b355fa5ad0f8_15700000031","parentCustomerEntityId":7764,"empFlag":0},{"isCreater":"N","employeesEntityId":229,"phone":"15710000012","flag":2,"enterpriseCode":"tao2","customerEntityId":7789,"nickName":"你好","workName":"未设置姓名","role":2,"isLeader":"N","markCode":"fc9cde0067f811e9a13fdfdc7c2bd769_15700000014","parentCustomerEntityId":7764,"empFlag":0}]}]}
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
             * address : 春园东路15号
             * createTime : 2019-04-26 16:00:14.0
             * customerEntityId : 7763
             * empCount : 1
             * empFlag : 1
             * employeesEntityId : 176
             * enterpriseCode : 总部
             * flag : 1
             * isCreater : Y
             * isLeader : Y
             * nickName : taotao
             * parentCustomerEntityId : 24
             * phone : 15700000008
             * role : 0
             * siteName : 襄阳
             * subInfoList : [{"isCreater":"Y","employeesEntityId":176,"phone":"15700000008","flag":1,"enterpriseCode":"tao1","customerEntityId":7763,"nickName":"taotao","workName":"fd","role":0,"isLeader":"Y","markCode":"cbf7b63067f811e9a13fdfdc7c2bd769_15700000008","parentCustomerEntityId":24,"empFlag":1}]
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
                 * employeesEntityId : 176
                 * phone : 15700000008
                 * flag : 1
                 * enterpriseCode : tao1
                 * customerEntityId : 7763
                 * nickName : taotao
                 * workName : fd
                 * role : 0
                 * isLeader : Y
                 * markCode : cbf7b63067f811e9a13fdfdc7c2bd769_15700000008
                 * parentCustomerEntityId : 24
                 * empFlag : 1
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
