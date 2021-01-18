package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class SupplyMonthlyBillBean {


    /**
     * err : 0
     * msg : 处理成功
     * data : {"receiveMoneyList":[{"year":2018,"month":9,"freezeMoney":12},{"year":2018,"month":8,"freezeMoney":252},{"year":2018,"month":7,"freezeMoney":523},{"year":2018,"month":6,"freezeMoney":43},{"year":2018,"month":5,"freezeMoney":0.64}],"currentMonthAfterSaleMoney":0,"currentMonthFreezeMoney":12,"afterSalersMoneyList":[{"year":2018,"month":9,"afterSaleMoney":0},{"year":2018,"month":8,"afterSaleMoney":0},{"year":2018,"month":7,"afterSaleMoney":0},{"year":2018,"month":6,"afterSaleMoney":0},{"year":2018,"month":5,"afterSaleMoney":0}]}
     */

    private int err;
    private String msg = "";
    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * receiveMoneyList : [{"year":2018,"month":9,"freezeMoney":12},{"year":2018,"month":8,"freezeMoney":252},{"year":2018,"month":7,"freezeMoney":523},{"year":2018,"month":6,"freezeMoney":43},{"year":2018,"month":5,"freezeMoney":0.64}]
         * currentMonthAfterSaleMoney : 0
         * currentMonthFreezeMoney : 12
         * afterSalersMoneyList : [{"year":2018,"month":9,"afterSaleMoney":0},{"year":2018,"month":8,"afterSaleMoney":0},{"year":2018,"month":7,"afterSaleMoney":0},{"year":2018,"month":6,"afterSaleMoney":0},{"year":2018,"month":5,"afterSaleMoney":0}]
         */

        private BigDecimal currentMonthAfterSaleMoney = BigDecimal.ZERO;
        private BigDecimal currentMonthFreezeMoney = BigDecimal.ZERO;
        private List<ReceiveMoneyListBean> receiveMoneyList;
        private List<AfterSalersMoneyListBean> afterSalersMoneyList;

        public BigDecimal getCurrentMonthAfterSaleMoney() {
            return currentMonthAfterSaleMoney;
        }

        public void setCurrentMonthAfterSaleMoney(BigDecimal currentMonthAfterSaleMoney) {
            this.currentMonthAfterSaleMoney = currentMonthAfterSaleMoney;
        }

        public BigDecimal getCurrentMonthFreezeMoney() {
            return currentMonthFreezeMoney;
        }

        public void setCurrentMonthFreezeMoney(BigDecimal currentMonthFreezeMoney) {
            this.currentMonthFreezeMoney = currentMonthFreezeMoney;
        }

        public List<ReceiveMoneyListBean> getReceiveMoneyList() {
            return receiveMoneyList;
        }

        public void setReceiveMoneyList(List<ReceiveMoneyListBean> receiveMoneyList) {
            this.receiveMoneyList = receiveMoneyList;
        }

        public List<AfterSalersMoneyListBean> getAfterSalersMoneyList() {
            return afterSalersMoneyList;
        }

        public void setAfterSalersMoneyList(List<AfterSalersMoneyListBean> afterSalersMoneyList) {
            this.afterSalersMoneyList = afterSalersMoneyList;
        }

        public static class ReceiveMoneyListBean {
            /**
             * year : 2018
             * month : 9
             * freezeMoney : 12
             */

            private int year;
            private int month;
            private BigDecimal freezeMoney;

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public BigDecimal getFreezeMoney() {
                return freezeMoney;
            }

            public void setFreezeMoney(BigDecimal freezeMoney) {
                this.freezeMoney = freezeMoney;
            }
        }

        public static class AfterSalersMoneyListBean {
            /**
             * year : 2018
             * month : 9
             * afterSaleMoney : 0
             */

            private int year;
            private int month;
            private BigDecimal afterSaleMoney = BigDecimal.ZERO;

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public BigDecimal getAfterSaleMoney() {
                return afterSaleMoney;
            }

            public void setAfterSaleMoney(BigDecimal afterSaleMoney) {
                this.afterSaleMoney = afterSaleMoney;
            }
        }
    }
}
