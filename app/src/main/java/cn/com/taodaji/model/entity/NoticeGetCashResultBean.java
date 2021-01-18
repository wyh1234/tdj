package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class NoticeGetCashResultBean {
    /**
     * err : 0
     * data : {"create_time":"2019-03-06 13:33","handle_time":"2019-03-06 15:11","customer_id":6331,"role":0,"customer_name":"开心酒店","hotel_name":"淘大集 李坤18727120758","capital_withdrawal":3334.72,"fee":20.81}
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
         * create_time : 2019-03-06 13:33
         * handle_time : 2019-03-06 15:11
         * customer_id : 6331
         * role : 0
         * customer_name : 开心酒店
         * hotel_name : 淘大集 李坤18727120758
         * capital_withdrawal : 3334.72
         * fee : 20.81
         */

        private String create_time;
        private String handle_time;
        private int customer_id;
        private int role;
        private String customer_name;
        private String hotel_name;
        private BigDecimal capital_withdrawal;
        private BigDecimal fee;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getHandle_time() {
            return handle_time;
        }

        public void setHandle_time(String handle_time) {
            this.handle_time = handle_time;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public BigDecimal getCapital_withdrawal() {
            return capital_withdrawal;
        }

        public void setCapital_withdrawal(BigDecimal capital_withdrawal) {
            this.capital_withdrawal = capital_withdrawal;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }
    }
}
