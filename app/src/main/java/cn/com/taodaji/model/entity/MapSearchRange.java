package cn.com.taodaji.model.entity;

public class MapSearchRange {

    /**
     * err : 0
     * data : {"customer_withdrawal_fee":"0.006","count_down_time":"1","is_start_limited_time":"1","order_start_time":"9","order_end_time":"0","customer_delivery_time":"08:00-11:00","supplier_delivery_time":"5:00","recharge_min_amount":"0.01","customer_pay_time_limit":"5","coupon_send_sms":"1","server_environment":"0","tax_fee_rate":"0.12","ext_prohibit_content":"淘大集《襄阳站》放假时间为：2月3日\u2014\u20142月7日（农历腊月29\u2014\u2014正月初三），2月3日（腊月29）暂停配送，2月7日（正月初三）恢复下单，2月8日（正月初四）恢复正常配送。","ext_prohibit_siteid":"2","ext_prohibit_order":"1","early_truck_time":"4:00","city_join_url":"http://ywyn.taodaji.com.cn/platform/toApplyJoinCity.html?applySource=1","c_deposit_explanation":"http://www.51taodj.com/h5/c.html","s_deposit_explanation":"http://www.51taodj.com/h5/s.html","map_search_range":"2000"}
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
         * customer_withdrawal_fee : 0.006
         * count_down_time : 1
         * is_start_limited_time : 1
         * order_start_time : 9
         * order_end_time : 0
         * customer_delivery_time : 08:00-11:00
         * supplier_delivery_time : 5:00
         * recharge_min_amount : 0.01
         * customer_pay_time_limit : 5
         * coupon_send_sms : 1
         * server_environment : 0
         * tax_fee_rate : 0.12
         * ext_prohibit_content : 淘大集《襄阳站》放假时间为：2月3日——2月7日（农历腊月29——正月初三），2月3日（腊月29）暂停配送，2月7日（正月初三）恢复下单，2月8日（正月初四）恢复正常配送。
         * ext_prohibit_siteid : 2
         * ext_prohibit_order : 1
         * early_truck_time : 4:00
         * city_join_url : http://ywyn.taodaji.com.cn/platform/toApplyJoinCity.html?applySource=1
         * c_deposit_explanation : http://www.51taodj.com/h5/c.html
         * s_deposit_explanation : http://www.51taodj.com/h5/s.html
         * map_search_range : 2000
         */

        private String customer_withdrawal_fee;
        private String count_down_time;
        private String is_start_limited_time;
        private String order_start_time;
        private String order_end_time;
        private String customer_delivery_time;
        private String supplier_delivery_time;
        private String recharge_min_amount;
        private String customer_pay_time_limit;
        private String coupon_send_sms;
        private String server_environment;
        private String tax_fee_rate;
        private String ext_prohibit_content;
        private String ext_prohibit_siteid;
        private String ext_prohibit_order;
        private String early_truck_time;
        private String city_join_url;
        private String c_deposit_explanation;
        private String s_deposit_explanation;
        private String map_search_range;

        public String getCustomer_withdrawal_fee() {
            return customer_withdrawal_fee;
        }

        public void setCustomer_withdrawal_fee(String customer_withdrawal_fee) {
            this.customer_withdrawal_fee = customer_withdrawal_fee;
        }

        public String getCount_down_time() {
            return count_down_time;
        }

        public void setCount_down_time(String count_down_time) {
            this.count_down_time = count_down_time;
        }

        public String getIs_start_limited_time() {
            return is_start_limited_time;
        }

        public void setIs_start_limited_time(String is_start_limited_time) {
            this.is_start_limited_time = is_start_limited_time;
        }

        public String getOrder_start_time() {
            return order_start_time;
        }

        public void setOrder_start_time(String order_start_time) {
            this.order_start_time = order_start_time;
        }

        public String getOrder_end_time() {
            return order_end_time;
        }

        public void setOrder_end_time(String order_end_time) {
            this.order_end_time = order_end_time;
        }

        public String getCustomer_delivery_time() {
            return customer_delivery_time;
        }

        public void setCustomer_delivery_time(String customer_delivery_time) {
            this.customer_delivery_time = customer_delivery_time;
        }

        public String getSupplier_delivery_time() {
            return supplier_delivery_time;
        }

        public void setSupplier_delivery_time(String supplier_delivery_time) {
            this.supplier_delivery_time = supplier_delivery_time;
        }

        public String getRecharge_min_amount() {
            return recharge_min_amount;
        }

        public void setRecharge_min_amount(String recharge_min_amount) {
            this.recharge_min_amount = recharge_min_amount;
        }

        public String getCustomer_pay_time_limit() {
            return customer_pay_time_limit;
        }

        public void setCustomer_pay_time_limit(String customer_pay_time_limit) {
            this.customer_pay_time_limit = customer_pay_time_limit;
        }

        public String getCoupon_send_sms() {
            return coupon_send_sms;
        }

        public void setCoupon_send_sms(String coupon_send_sms) {
            this.coupon_send_sms = coupon_send_sms;
        }

        public String getServer_environment() {
            return server_environment;
        }

        public void setServer_environment(String server_environment) {
            this.server_environment = server_environment;
        }

        public String getTax_fee_rate() {
            return tax_fee_rate;
        }

        public void setTax_fee_rate(String tax_fee_rate) {
            this.tax_fee_rate = tax_fee_rate;
        }

        public String getExt_prohibit_content() {
            return ext_prohibit_content;
        }

        public void setExt_prohibit_content(String ext_prohibit_content) {
            this.ext_prohibit_content = ext_prohibit_content;
        }

        public String getExt_prohibit_siteid() {
            return ext_prohibit_siteid;
        }

        public void setExt_prohibit_siteid(String ext_prohibit_siteid) {
            this.ext_prohibit_siteid = ext_prohibit_siteid;
        }

        public String getExt_prohibit_order() {
            return ext_prohibit_order;
        }

        public void setExt_prohibit_order(String ext_prohibit_order) {
            this.ext_prohibit_order = ext_prohibit_order;
        }

        public String getEarly_truck_time() {
            return early_truck_time;
        }

        public void setEarly_truck_time(String early_truck_time) {
            this.early_truck_time = early_truck_time;
        }

        public String getCity_join_url() {
            return city_join_url;
        }

        public void setCity_join_url(String city_join_url) {
            this.city_join_url = city_join_url;
        }

        public String getC_deposit_explanation() {
            return c_deposit_explanation;
        }

        public void setC_deposit_explanation(String c_deposit_explanation) {
            this.c_deposit_explanation = c_deposit_explanation;
        }

        public String getS_deposit_explanation() {
            return s_deposit_explanation;
        }

        public void setS_deposit_explanation(String s_deposit_explanation) {
            this.s_deposit_explanation = s_deposit_explanation;
        }

        public String getMap_search_range() {
            return map_search_range;
        }

        public void setMap_search_range(String map_search_range) {
            this.map_search_range = map_search_range;
        }
    }
}
