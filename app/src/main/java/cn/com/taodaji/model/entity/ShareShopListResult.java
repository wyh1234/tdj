package cn.com.taodaji.model.entity;

import java.util.List;

public class ShareShopListResult {


    /**
     * err : 0
     * data : {"applyCount":1121,"list":[{"customerId":162,"enterpriseCode":"南漳食堂自提","nickName":"何照兵","account":"15071505968"},{"customerId":257,"enterpriseCode":"特警支队食堂","nickName":"钟宇洲","account":"13476300202"},{"customerId":295,"enterpriseCode":"万达甜甜馆","nickName":"李海敏","account":"13339806408"},{"customerId":312,"enterpriseCode":"喵喵烤肉饭","nickName":"张莉薇","account":"13687286622"},{"customerId":583,"enterpriseCode":"汽车职业技术学院","nickName":"孙品","account":"13697100608"},{"customerId":588,"enterpriseCode":"襄阳汽车职业技术学院食堂","nickName":"魏祖山","account":"18327522225"},{"customerId":756,"enterpriseCode":"贵园酒轩","nickName":"寇红江","account":"13871616949"},{"customerId":917,"enterpriseCode":"三丰阁酒店","nickName":"李文彬","account":"18671007118"},{"customerId":1004,"enterpriseCode":"尹集理工学院二楼程大个火锅","nickName":"汪长杰","account":"13094113530"},{"customerId":1018,"enterpriseCode":"理工学院学生食堂二楼","nickName":"吕海平","account":"15571174481"}],"noOrderCount":453,"orderCount":668,"pn":1,"ps":10,"total":1121}
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
         * applyCount : 1121
         * list : [{"customerId":162,"enterpriseCode":"南漳食堂自提","nickName":"何照兵","account":"15071505968"},{"customerId":257,"enterpriseCode":"特警支队食堂","nickName":"钟宇洲","account":"13476300202"},{"customerId":295,"enterpriseCode":"万达甜甜馆","nickName":"李海敏","account":"13339806408"},{"customerId":312,"enterpriseCode":"喵喵烤肉饭","nickName":"张莉薇","account":"13687286622"},{"customerId":583,"enterpriseCode":"汽车职业技术学院","nickName":"孙品","account":"13697100608"},{"customerId":588,"enterpriseCode":"襄阳汽车职业技术学院食堂","nickName":"魏祖山","account":"18327522225"},{"customerId":756,"enterpriseCode":"贵园酒轩","nickName":"寇红江","account":"13871616949"},{"customerId":917,"enterpriseCode":"三丰阁酒店","nickName":"李文彬","account":"18671007118"},{"customerId":1004,"enterpriseCode":"尹集理工学院二楼程大个火锅","nickName":"汪长杰","account":"13094113530"},{"customerId":1018,"enterpriseCode":"理工学院学生食堂二楼","nickName":"吕海平","account":"15571174481"}]
         * noOrderCount : 453
         * orderCount : 668
         * pn : 1
         * ps : 10
         * total : 1121
         */

        private int applyCount;
        private int noOrderCount;
        private int orderCount;
        private int pn;
        private int ps;
        private int total;
        private List<ListBean> list;

        public int getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(int applyCount) {
            this.applyCount = applyCount;
        }

        public int getNoOrderCount() {
            return noOrderCount;
        }

        public void setNoOrderCount(int noOrderCount) {
            this.noOrderCount = noOrderCount;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * customerId : 162
             * enterpriseCode : 南漳食堂自提
             * nickName : 何照兵
             * account : 15071505968
             */

            private int customerId;
            private String enterpriseCode;
            private String nickName;
            private String account;

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getEnterpriseCode() {
                return enterpriseCode;
            }

            public void setEnterpriseCode(String enterpriseCode) {
                this.enterpriseCode = enterpriseCode;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }
        }
    }
}
