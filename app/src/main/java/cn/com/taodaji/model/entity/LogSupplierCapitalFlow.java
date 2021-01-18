package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

public class LogSupplierCapitalFlow {

    /**
     * total : 4
     * items : [{"entity_id":51,"create_time":"2017-03-06 00:37","customer_id":42,"product_img":"http://llwer.pi.com","store_id":22,"capital_change":"430","capital_change_reason":"订单退款","order_id":545,"order_no":"32423434243","out_trade_no":"f79e5520ed0f11e6807e4b081815219e","order_item_id":1913,"type":2,"product_name":"菠菜","product_price":43,"product_desc":"AAAAAAAAAAAAAAA","product_qty":10,"total_price":430,"unit":"斤","hotel_name":"测试酒店3","receive_address":"襄阳盛大酒3店","create_order_time":"2017-03-07 20:25"},{"entity_id":50,"create_time":"2017-03-06 00:22","customer_id":42,"product_img":"http://llwer.pi.com","store_id":22,"capital_change":"430","capital_change_reason":"订单退款","order_id":545,"order_no":"32423434243","out_trade_no":"f79e5520ed0f11e6807e4b081815219e","order_item_id":1913,"type":2,"product_name":"菠菜","product_price":43,"product_desc":null,"product_qty":10,"total_price":430,"unit":"斤","hotel_name":"测试酒店3","receive_address":"襄阳盛大酒3店","create_order_time":"2017-03-06 00:28"},{"entity_id":47,"create_time":"2017-03-05 22:28","customer_id":42,"product_img":"http://www.baidu.com/sj1.jpg","store_id":22,"capital_change":"278.76","capital_change_reason":"订单支付","order_id":545,"order_no":"32423434243","out_trade_no":"f79e5520ed0f11e6807e4b081815219e","order_item_id":1913,"type":1,"product_name":"东北大米1167","product_price":23.23,"product_desc":null,"product_qty":12,"total_price":278.76,"unit":"斤","hotel_name":"天圣酒店","receive_address":"湖北_襄阳_中国_胜利街","create_order_time":"2017-03-06 00:28"},{"entity_id":48,"create_time":"2017-03-05 22:28","customer_id":42,"product_img":"http://www.baidu.com/sj1.jpg","store_id":22,"capital_change":"69.69","capital_change_reason":"订单支付","order_id":545,"order_no":"32423434243","out_trade_no":"f79e5520ed0f11e6807e4b081815219e","order_item_id":1914,"type":1,"product_name":"东北大米999","product_price":23.23,"product_desc":null,"product_qty":3,"total_price":69.69,"unit":"斤","hotel_name":"天圣酒店","receive_address":"湖北_襄阳_中国_胜利街","create_order_time":"2017-03-06 00:28"}]
     * pn : 1
     * ps : 5
     */

    private int total;
    private int pn;
    private int ps;
    @OnlyField
    private String month;
    private List<ItemsBean> items;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * entity_id : 51
         * create_time : 2017-03-06 00:37
         * customer_id : 42
         * product_img : http://llwer.pi.com
         * store_id : 22
         * capital_change : 430
         * capital_change_reason : 订单退款
         * order_id : 545
         * order_no : 32423434243
         * out_trade_no : f79e5520ed0f11e6807e4b081815219e
         * order_item_id : 1913
         * type : 2
         * product_name : 菠菜
         * product_price : 43
         * product_desc : AAAAAAAAAAAAAAA
         * product_qty : 10
         * total_price : 430
         * unit : 斤
         * hotel_name : 测试酒店3
         * receive_address : 襄阳盛大酒3店
         * create_order_time : 2017-03-07 20:25
         */

        private int entity_id;
        private String create_time;
        private int customer_id;
        private String product_img;
        private int store_id;
        private String capital_change;
        private String capital_change_reason;
        private int order_id;
        private String order_no;
        private String out_trade_no;
        private int order_item_id;
        private int type;
        private String product_name;
        private BigDecimal product_price;
        private String product_desc;
        private BigDecimal product_qty;
        private BigDecimal total_price;
        private String customer_img;
        private String unit;
        private String hotel_name;
        private String receive_address;
        private String create_order_time;

        private String avg_unit;
        private BigDecimal avg_price;
        private BigDecimal discount_avg_price;
        private String level_2_unit;
        private BigDecimal level_2_value;
        private String level_3_unit;
        private BigDecimal level_3_value;
        private int level_type;


        public String getAvg_unit() {
            return avg_unit;
        }

        public void setAvg_unit(String avg_unit) {
            this.avg_unit = avg_unit;
        }

        public BigDecimal getAvg_price() {
            return avg_price;
        }

        public void setAvg_price(BigDecimal avg_price) {
            this.avg_price = avg_price;
        }

        public BigDecimal getDiscount_avg_price() {
            return discount_avg_price;
        }

        public void setDiscount_avg_price(BigDecimal discount_avg_price) {
            this.discount_avg_price = discount_avg_price;
        }

        public String getLevel_2_unit() {
            return level_2_unit;
        }

        public void setLevel_2_unit(String level_2_unit) {
            this.level_2_unit = level_2_unit;
        }

        public BigDecimal getLevel_2_value() {
            return level_2_value;
        }

        public void setLevel_2_value(BigDecimal level_2_value) {
            this.level_2_value = level_2_value;
        }

        public String getLevel_3_unit() {
            return level_3_unit;
        }

        public void setLevel_3_unit(String level_3_unit) {
            this.level_3_unit = level_3_unit;
        }

        public BigDecimal getLevel_3_value() {
            return level_3_value;
        }

        public void setLevel_3_value(BigDecimal level_3_value) {
            this.level_3_value = level_3_value;
        }

        public int getLevel_type() {
            return level_type;
        }

        public void setLevel_type(int level_type) {
            this.level_type = level_type;
        }

        public String getCustomer_img() {
            return customer_img;
        }

        public void setCustomer_img(String customer_img) {
            this.customer_img = customer_img;
        }

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

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getCapital_change() {
            return capital_change;
        }

        public void setCapital_change(String capital_change) {
            this.capital_change = capital_change;
        }

        public String getCapital_change_reason() {
            return capital_change_reason;
        }

        public void setCapital_change_reason(String capital_change_reason) {
            this.capital_change_reason = capital_change_reason;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public int getOrder_item_id() {
            return order_item_id;
        }

        public void setOrder_item_id(int order_item_id) {
            this.order_item_id = order_item_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public BigDecimal getProduct_price() {
            return product_price;
        }

        public void setProduct_price(BigDecimal product_price) {
            this.product_price = product_price;
        }

        public String getProduct_desc() {
            return product_desc;
        }

        public void setProduct_desc(String product_desc) {
            this.product_desc = product_desc;
        }

        public BigDecimal getProduct_qty() {
            return product_qty;
        }

        public void setProduct_qty(BigDecimal product_qty) {
            this.product_qty = product_qty;
        }

        public BigDecimal getTotal_price() {
            return total_price;
        }

        public void setTotal_price(BigDecimal total_price) {
            this.total_price = total_price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public String getReceive_address() {
            return receive_address;
        }

        public void setReceive_address(String receive_address) {
            this.receive_address = receive_address;
        }

        public String getCreate_order_time() {
            return create_order_time;
        }

        public void setCreate_order_time(String create_order_time) {
            this.create_order_time = create_order_time;
        }
    }
}



