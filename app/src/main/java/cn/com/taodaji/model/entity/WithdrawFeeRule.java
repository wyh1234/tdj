package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class WithdrawFeeRule {


    /**
     * err : 0
     * data : {"entity_id":2,"create_time":"2020-10-13 14:25","update_time":null,"site":3,"standard_amount":1000,"collection_rate":0.7,"transfer_amount":3}
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
         * entity_id : 2
         * create_time : 2020-10-13 14:25
         * update_time : null
         * site : 3
         * standard_amount : 1000
         * collection_rate : 0.7
         * transfer_amount : 3
         */

        private int entity_id;
        private String create_time;
        private Object update_time;
        private int site;
        private BigDecimal standard_amount;
        private BigDecimal collection_rate;
        private BigDecimal transfer_amount;

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

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public BigDecimal getStandard_amount() {
            return standard_amount;
        }

        public void setStandard_amount(BigDecimal standard_amount) {
            this.standard_amount = standard_amount;
        }

        public BigDecimal getCollection_rate() {
            return collection_rate;
        }

        public void setCollection_rate(BigDecimal collection_rate) {
            this.collection_rate = collection_rate;
        }

        public BigDecimal getTransfer_amount() {
            return transfer_amount;
        }

        public void setTransfer_amount(BigDecimal transfer_amount) {
            this.transfer_amount = transfer_amount;
        }
    }
}
