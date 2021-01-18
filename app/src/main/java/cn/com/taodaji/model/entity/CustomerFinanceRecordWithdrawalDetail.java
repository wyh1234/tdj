package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-08-19.
 */

public class CustomerFinanceRecordWithdrawalDetail {

    /**
     * err : 0
     * data : {"entity_id":10,"create_time":"2017-06-29 15:38","update_time":"2017-07-01 10:06","customer_id":1,"province_name":"湖北","city_name":"襄阳","bank_name":"建设银行","account_no":"23423453245324","bank_address":"樊城区23号","after_sale_amount":23,"recharge_amount":3.23,"capital_withdrawal":26.23,"recharge_amount_fee":0.03,"after_sale_amount_fee":2,"fee":2.03,"money_type":3,"customer_name":"又又","customer_tel":"18571161280","hotel_name":"湖北黄牛庄","is_withdrawal":"N","status":0,"remark":""}
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
         * entity_id : 10
         * create_time : 2017-06-29 15:38
         * update_time : 2017-07-01 10:06
         * customer_id : 1
         * province_name : 湖北
         * city_name : 襄阳
         * bank_name : 建设银行
         * account_no : 23423453245324
         * bank_address : 樊城区23号
         * after_sale_amount : 23
         * recharge_amount : 3.23
         * capital_withdrawal : 26.23
         * recharge_amount_fee : 0.03
         * after_sale_amount_fee : 2
         * fee : 2.03
         * money_type : 3
         * customer_name : 又又
         * customer_tel : 18571161280
         * hotel_name : 湖北黄牛庄
         * is_withdrawal : N
         * status : 0
         * remark :
         */

        private int entity_id;
        private String create_time;
        private String update_time;
        private int customer_id;
        private String province_name;
        private String city_name;
        private String bank_name;
        private String account_no;
        private String bank_address;
        private BigDecimal after_sale_amount;
        private BigDecimal recharge_amount;
        private BigDecimal capital_withdrawal=BigDecimal.ZERO;
        private BigDecimal recharge_amount_fee;
        private BigDecimal after_sale_amount_fee;
        private BigDecimal fee;
        private int money_type;
        private String customer_name;
        private String customer_tel;
        private String hotel_name;
        private String is_withdrawal;
        private int status;
        private String remark;

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public BigDecimal getAfter_sale_amount() {
            return after_sale_amount;
        }

        public void setAfter_sale_amount(BigDecimal after_sale_amount) {
            this.after_sale_amount = after_sale_amount;
        }

        public BigDecimal getRecharge_amount() {
            return recharge_amount;
        }

        public void setRecharge_amount(BigDecimal recharge_amount) {
            this.recharge_amount = recharge_amount;
        }

        public BigDecimal getCapital_withdrawal() {
            return capital_withdrawal;
        }

        public void setCapital_withdrawal(BigDecimal capital_withdrawal) {
            this.capital_withdrawal = capital_withdrawal;
        }

        public BigDecimal getRecharge_amount_fee() {
            return recharge_amount_fee;
        }

        public void setRecharge_amount_fee(BigDecimal recharge_amount_fee) {
            this.recharge_amount_fee = recharge_amount_fee;
        }

        public BigDecimal getAfter_sale_amount_fee() {
            return after_sale_amount_fee;
        }

        public void setAfter_sale_amount_fee(BigDecimal after_sale_amount_fee) {
            this.after_sale_amount_fee = after_sale_amount_fee;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }

        public int getMoney_type() {
            return money_type;
        }

        public void setMoney_type(int money_type) {
            this.money_type = money_type;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getCustomer_tel() {
            return customer_tel;
        }

        public void setCustomer_tel(String customer_tel) {
            this.customer_tel = customer_tel;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public String getIs_withdrawal() {
            return is_withdrawal;
        }

        public void setIs_withdrawal(String is_withdrawal) {
            this.is_withdrawal = is_withdrawal;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
