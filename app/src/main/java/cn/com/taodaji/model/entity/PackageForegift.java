package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by yangkuo on 2019/1/9.
 */
public class PackageForegift {

    /**
     * err : 0
     * data : {"payNum":2,"hotel_name":"淘大集 宁倩15071519512","customer_img":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1809182233142d283756.jpg","name":"桔子","nick_name":"宜昌蜜桔","amount":2,"price":1.5,"unit":"斤","avg_price":1.5,"avg_unit":"斤","discount_avg_price":0,"specification_id":67672,"level_2_value":0,"level_2_unit":"","level_3_value":0,"level_3_unit":"","level_type":1,"total_price":3,"foregift":1.5,"order_no":"9181217007110","order_pay_time":"2018-12-17 17:25:55"}
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
         * payNum : 2
         * hotel_name : 淘大集 宁倩15071519512
         * customer_img : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1809182233142d283756.jpg
         * name : 桔子
         * nick_name : 宜昌蜜桔
         * amount : 2
         * price : 1.5
         * unit : 斤
         * avg_price : 1.5
         * avg_unit : 斤
         * discount_avg_price : 0
         * specification_id : 67672
         * level_2_value : 0
         * level_2_unit :
         * level_3_value : 0
         * level_3_unit :
         * level_type : 1
         * total_price : 3
         * foregift : 1.5
         * order_no : 9181217007110
         * order_pay_time : 2018-12-17 17:25:55
         */

        private int payNum;
        private String hotel_name;
        private String customer_img;
        private String name;
        private String nick_name;
        private int amount;
        private BigDecimal price;
        private String unit;
        private BigDecimal avg_price;
        private String avg_unit;
        private BigDecimal discount_avg_price;
        private int specification_id;
        private BigDecimal level_2_value;
        private String level_2_unit;
        private BigDecimal level_3_value;
        private String level_3_unit;
        private int level_type;
        private BigDecimal total_price;
        private BigDecimal foregift=BigDecimal.ZERO;
        private String order_no;
        private String order_pay_time;

        public int getPayNum() {
            return payNum;
        }

        public void setPayNum(int payNum) {
            this.payNum = payNum;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public String getCustomer_img() {
            return customer_img;
        }

        public void setCustomer_img(String customer_img) {
            this.customer_img = customer_img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public BigDecimal getAvg_price() {
            return avg_price;
        }

        public void setAvg_price(BigDecimal avg_price) {
            this.avg_price = avg_price;
        }

        public String getAvg_unit() {
            return avg_unit;
        }

        public void setAvg_unit(String avg_unit) {
            this.avg_unit = avg_unit;
        }

        public BigDecimal getDiscount_avg_price() {
            return discount_avg_price;
        }

        public void setDiscount_avg_price(BigDecimal discount_avg_price) {
            this.discount_avg_price = discount_avg_price;
        }

        public int getSpecification_id() {
            return specification_id;
        }

        public void setSpecification_id(int specification_id) {
            this.specification_id = specification_id;
        }

        public BigDecimal getLevel_2_value() {
            return level_2_value;
        }

        public void setLevel_2_value(BigDecimal level_2_value) {
            this.level_2_value = level_2_value;
        }

        public String getLevel_2_unit() {
            return level_2_unit;
        }

        public void setLevel_2_unit(String level_2_unit) {
            this.level_2_unit = level_2_unit;
        }

        public BigDecimal getLevel_3_value() {
            return level_3_value;
        }

        public void setLevel_3_value(BigDecimal level_3_value) {
            this.level_3_value = level_3_value;
        }

        public String getLevel_3_unit() {
            return level_3_unit;
        }

        public void setLevel_3_unit(String level_3_unit) {
            this.level_3_unit = level_3_unit;
        }

        public int getLevel_type() {
            return level_type;
        }

        public void setLevel_type(int level_type) {
            this.level_type = level_type;
        }

        public BigDecimal getTotal_price() {
            return total_price;
        }

        public void setTotal_price(BigDecimal total_price) {
            this.total_price = total_price;
        }

        public BigDecimal getForegift() {
            return foregift;
        }

        public void setForegift(BigDecimal foregift) {
            this.foregift = foregift;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOrder_pay_time() {
            return order_pay_time;
        }

        public void setOrder_pay_time(String order_pay_time) {
            this.order_pay_time = order_pay_time;
        }
    }
}
