package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class PunishData {


    /**
     * err : 0
     * data : {"list":[{"order_no":"1574138280377","explain":"严重迟到","create_user_id":0,"item":"迟到","create_time":"2019-11-19 00:00:00","close_time":"2019-11-22 00:00:00","product_code":"","type":0,"store_phone":"18907185644","appeal_status":3,"pay_status":0,"money":89,"appeal_time":"2019-11-20 11:26:19","punish":"缴纳罚款后持续关店整改2天","appeal_cause":"基材莱城酸楚夏雨来需要再","id":2,"detail":"2:00前50单以上未送货完成","pic_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","status":2},{"order_no":"1574140787732","explain":"单月累计迟到","create_user_id":1,"item":"迟到","create_time":"2019-11-19 00:00:00","close_time":"2019-11-22 00:00:00","product_code":"","type":0,"store_phone":"18907185644","appeal_status":3,"pay_status":0,"money":70,"appeal_time":"2019-11-20 12:17:11","punish":"关店1周，并在群里通报批评","appeal_cause":"基材莱城酸楚夏雨来需要再","id":5,"detail":"当月累计迟到5次","pic_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","status":2}],"pn":0,"ps":0,"total":2}
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
         * list : [{"order_no":"1574138280377","explain":"严重迟到","create_user_id":0,"item":"迟到","create_time":"2019-11-19 00:00:00","close_time":"2019-11-22 00:00:00","product_code":"","type":0,"store_phone":"18907185644","appeal_status":3,"pay_status":0,"money":89,"appeal_time":"2019-11-20 11:26:19","punish":"缴纳罚款后持续关店整改2天","appeal_cause":"基材莱城酸楚夏雨来需要再","id":2,"detail":"2:00前50单以上未送货完成","pic_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","status":2},{"order_no":"1574140787732","explain":"单月累计迟到","create_user_id":1,"item":"迟到","create_time":"2019-11-19 00:00:00","close_time":"2019-11-22 00:00:00","product_code":"","type":0,"store_phone":"18907185644","appeal_status":3,"pay_status":0,"money":70,"appeal_time":"2019-11-20 12:17:11","punish":"关店1周，并在群里通报批评","appeal_cause":"基材莱城酸楚夏雨来需要再","id":5,"detail":"当月累计迟到5次","pic_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","status":2}]
         * pn : 0
         * ps : 0
         * total : 2
         */

        private int pn;
        private int ps;
        private int total;
        private String currentTime;
        private List<ListBean> list;

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
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
             * order_no : 1574138280377
             * explain : 严重迟到
             * create_user_id : 0
             * item : 迟到
             * create_time : 2019-11-19 00:00:00
             * close_time : 2019-11-22 00:00:00
             * product_code :
             * type : 0
             * store_phone : 18907185644
             * appeal_status : 3
             * pay_status : 0
             * money : 89
             * appeal_time : 2019-11-20 11:26:19
             * punish : 缴纳罚款后持续关店整改2天
             * appeal_cause : 基材莱城酸楚夏雨来需要再
             * id : 2
             * detail : 2:00前50单以上未送货完成
             * pic_url : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png
             * status : 2
             */

            private String order_no;
            private String explain;
            private int create_user_id;
            private String item;
            private String create_time;
            private List<String> voucher;
            private String close_time;
            private String product_code;
            private int type;
            private String store_phone;
            private int appeal_status;
            private int ut_status;
            private BigDecimal money;
            private String appeal_time;
            private String punish;
            private String appeal_cause;
            private int id;
            private String detail;
            private String pic_url;
            private int status;

            public List<String> getVoucher() {
                return voucher;
            }

            public void setVoucher(List<String> voucher) {
                this.voucher = voucher;
            }

            public int getUt_status() {
                return ut_status;
            }

            public void setUt_status(int ut_status) {
                this.ut_status = ut_status;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public int getCreate_user_id() {
                return create_user_id;
            }

            public void setCreate_user_id(int create_user_id) {
                this.create_user_id = create_user_id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getClose_time() {
                return close_time;
            }

            public void setClose_time(String close_time) {
                this.close_time = close_time;
            }

            public String getProduct_code() {
                return product_code;
            }

            public void setProduct_code(String product_code) {
                this.product_code = product_code;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }

            public int getAppeal_status() {
                return appeal_status;
            }

            public void setAppeal_status(int appeal_status) {
                this.appeal_status = appeal_status;
            }



            public BigDecimal getMoney() {
                return money;
            }

            public void setMoney(BigDecimal money) {
                this.money = money;
            }

            public String getAppeal_time() {
                return appeal_time;
            }

            public void setAppeal_time(String appeal_time) {
                this.appeal_time = appeal_time;
            }

            public String getPunish() {
                return punish;
            }

            public void setPunish(String punish) {
                this.punish = punish;
            }

            public String getAppeal_cause() {
                return appeal_cause;
            }

            public void setAppeal_cause(String appeal_cause) {
                this.appeal_cause = appeal_cause;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
