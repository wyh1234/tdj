package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class ModifyInventory {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"level_3_unit":"斤","created_at":"2019-09-18 09:32:14","level_1_unit":"箱","avg_price":0.2,"entity_id":115616,"level_type":3,"level_2_unit":"盒","price":10,"product_id":69435,"avg_unit":"斤","level_2_value":10,"level_3_value":5,"stock":196}
     */

    private int err;
    private String msg;
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
         * level_3_unit : 斤
         * created_at : 2019-09-18 09:32:14
         * level_1_unit : 箱
         * avg_price : 0.2
         * entity_id : 115616
         * level_type : 3
         * level_2_unit : 盒
         * price : 10
         * product_id : 69435
         * avg_unit : 斤
         * level_2_value : 10
         * level_3_value : 5
         * stock : 196
         */

        private String level_3_unit;
        private String created_at;
        private String level_1_unit;
        private BigDecimal avg_price;
        private int entity_id;
        private int level_type;
        private String level_2_unit;
        private BigDecimal price;
        private int product_id;
        private String avg_unit;
        private BigDecimal level_2_value;
        private BigDecimal level_3_value;
        private int stock;

        public String getLevel_3_unit() {
            return level_3_unit;
        }

        public void setLevel_3_unit(String level_3_unit) {
            this.level_3_unit = level_3_unit;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getLevel_1_unit() {
            return level_1_unit;
        }

        public void setLevel_1_unit(String level_1_unit) {
            this.level_1_unit = level_1_unit;
        }

        public BigDecimal getAvg_price() {
            return avg_price;
        }

        public void setAvg_price(BigDecimal avg_price) {
            this.avg_price = avg_price;
        }

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public int getLevel_type() {
            return level_type;
        }

        public void setLevel_type(int level_type) {
            this.level_type = level_type;
        }

        public String getLevel_2_unit() {
            return level_2_unit;
        }

        public void setLevel_2_unit(String level_2_unit) {
            this.level_2_unit = level_2_unit;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getAvg_unit() {
            return avg_unit;
        }

        public void setAvg_unit(String avg_unit) {
            this.avg_unit = avg_unit;
        }

        public BigDecimal getLevel_2_value() {
            return level_2_value;
        }

        public void setLevel_2_value(BigDecimal level_2_value) {
            this.level_2_value = level_2_value;
        }

        public BigDecimal getLevel_3_value() {
            return level_3_value;
        }

        public void setLevel_3_value(BigDecimal level_3_value) {
            this.level_3_value = level_3_value;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
