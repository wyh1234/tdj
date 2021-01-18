package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-07-15.
 */

public class DictFindAll {

    /**
     * err : 0
     * data : {"customer_withdrawal_fee":"0.008","count_down_time":"1","is_start_limited_time":"1","order_start_time":"6","order_end_time":"0","customer_delivery_time":"08:00-11:00","supplier_delivery_time":"6"}
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
         * customer_withdrawal_fee	采购商提现费用比例
         * count_down_time	倒计时间
         * is_start_limited_time	是否启用限制下单
         * order_start_time	当天开始下单时间 6:00
         * order_end_time	当天截止下单时间24:00
         * customer_delivery_time	采购商送达时间
         * supplier_delivery_time	供应商送达时间
         * recharge_min_amount 充值最少充多少
         * ext_prohibit_content    禁止下单原因
         * ext_prohibit_siteid     禁止下单的城市SiteID
         * ext_prohibit_order      是否禁止下单
         */
        /**
         * customer_withdrawal_fee : 0.008
         * count_down_time : 1
         * is_start_limited_time : 1
         * order_start_time : 6
         * order_end_time : 0
         * customer_delivery_time : 08:00-11:00
         * supplier_delivery_time : 6
         * recharge_min_amount
         */

        private String customer_withdrawal_fee;
        private int count_down_time;
        private String is_start_limited_time;
        private String order_start_time;
        private String order_end_time;
        private String customer_delivery_time;
        private String supplier_delivery_time;
        private String recharge_min_amount;
        private String early_truck_time;

        private String tax_fee_rate;//开发票的税率

        private int ext_prohibit_siteid;

        private int ext_prohibit_order;

        private String ext_prohibit_content;

        private String city_join_url;

        private String c_deposit_explanation ;  // 采购商押金说明;

        private String s_deposit_explanation ;  // 供应商押金说明;

        private String customer_help_html ;  // 采购商帮助地址;;

        private String supplier_help_html ;  // 供应商帮助地址;
        private String b_freight;
        private String c_freight;
        private String after_sale_intro_title;
        private String  after_sale_intro_content;
        private String  after_sale_intro_end;
        private String copywriting;

        public String getCopywriting() {
            return copywriting;
        }

        public void setCopywriting(String copywriting) {
            this.copywriting = copywriting;
        }

        public String getAfter_sale_intro_title() {
            return after_sale_intro_title;
        }

        public void setAfter_sale_intro_title(String after_sale_intro_title) {
            this.after_sale_intro_title = after_sale_intro_title;
        }

        public String getAfter_sale_intro_content() {
            return after_sale_intro_content;
        }

        public void setAfter_sale_intro_content(String after_sale_intro_content) {
            this.after_sale_intro_content = after_sale_intro_content;
        }

        public String getAfter_sale_intro_end() {
            return after_sale_intro_end;
        }

        public void setAfter_sale_intro_end(String after_sale_intro_end) {
            this.after_sale_intro_end = after_sale_intro_end;
        }

        public String getB_freight() {
            return b_freight;
        }

        public void setB_freight(String b_freight) {
            this.b_freight = b_freight;
        }

        public String getC_freight() {
            return c_freight;
        }

        public void setC_freight(String c_freight) {
            this.c_freight = c_freight;
        }

        public String getCustomer_help_html() {
            return customer_help_html;
        }

        public void setCustomer_help_html(String customer_help_html) {
            this.customer_help_html = customer_help_html;
        }

        public String getSupplier_help_html() {
            return supplier_help_html;
        }

        public void setSupplier_help_html(String supplier_help_html) {
            this.supplier_help_html = supplier_help_html;
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

        public String getCity_join_url() {
            return city_join_url;
        }

        public void setCity_join_url(String city_join_url) {
            this.city_join_url = city_join_url;
        }

        public int getExt_prohibit_siteid() {
            return ext_prohibit_siteid;
        }

        public String getEarly_truck_time() {
            return early_truck_time;
        }

        public void setEarly_truck_time(String early_truck_time) {
            this.early_truck_time = early_truck_time;
        }

        public void setExt_prohibit_siteid(int ext_prohibit_siteid) {
            this.ext_prohibit_siteid = ext_prohibit_siteid;
        }

        public int getExt_prohibit_order() {
            return ext_prohibit_order;
        }

        public void setExt_prohibit_order(int ext_prohibit_order) {
            this.ext_prohibit_order = ext_prohibit_order;
        }

        public String getExt_prohibit_content() {
            return ext_prohibit_content;
        }

        public void setExt_prohibit_content(String ext_prohibit_content) {
            this.ext_prohibit_content = ext_prohibit_content;
        }

        public String getTax_fee_rate() {
            return tax_fee_rate;
        }

        public void setTax_fee_rate(String tax_fee_rate) {
            this.tax_fee_rate = tax_fee_rate;
        }

        public String getRecharge_min_amount() {
            return recharge_min_amount;
        }

        public void setRecharge_min_amount(String recharge_min_amount) {
            this.recharge_min_amount = recharge_min_amount;
        }

        public String getCustomer_withdrawal_fee() {
            return customer_withdrawal_fee;
        }

        public void setCustomer_withdrawal_fee(String customer_withdrawal_fee) {
            this.customer_withdrawal_fee = customer_withdrawal_fee;
        }

        public int getCount_down_time() {
            return count_down_time;
        }

        public void setCount_down_time(int count_down_time) {
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
    }
}
