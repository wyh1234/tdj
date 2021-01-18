package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class PaymentList {


    /**
     * err : 0
     * data : {"list":[{"store_id":1,"order_no":"fk001","money":10,"create_time":"2019-11-20 15:52:35","payment":1,"payment_name":"余额","id":1,"type":1,"type_name":"罚款","content":"迟到"}],"pn":0,"ps":0,"total":1,"totalMoney":10}
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
         * list : [{"store_id":1,"order_no":"fk001","money":10,"create_time":"2019-11-20 15:52:35","payment":1,"payment_name":"余额","id":1,"type":1,"type_name":"罚款","content":"迟到"}]
         * pn : 0
         * ps : 0
         * total : 1
         * totalMoney : 10
         */

        private int pn;
        private int ps;
        private int total;
        private BigDecimal totalMoney;
        private List<ListBean> list;

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

        public BigDecimal getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(BigDecimal totalMoney) {
            this.totalMoney = totalMoney;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * store_id : 1
             * order_no : fk001
             * money : 10
             * create_time : 2019-11-20 15:52:35
             * payment : 1
             * payment_name : 余额
             * id : 1
             * type : 1
             * type_name : 罚款
             * content : 迟到
             */

            private int store_id;
            private String order_no;
            private BigDecimal money;
            private String create_time;
            private int payment;
            private String payment_name;
            private int id;
            private int type;
            private String type_name;
            private String content;
            private String pic_url;

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public BigDecimal getMoney() {
                return money;
            }

            public void setMoney(BigDecimal money) {
                this.money = money;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getPayment() {
                return payment;
            }

            public void setPayment(int payment) {
                this.payment = payment;
            }

            public String getPayment_name() {
                return payment_name;
            }

            public void setPayment_name(String payment_name) {
                this.payment_name = payment_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
