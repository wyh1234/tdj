package cn.com.taodaji.model.entity;

public class CommodityLimit {

    /**
     * err : 0
     * data : {"isDefault":0,"storeType":2,"commodityLimit":2,"flagCommodityLimit":7}
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
         * isDefault : 0
         * storeType : 2
         * commodityLimit : 2
         * flagCommodityLimit : 7
         */

        private int isDefault;
        private int storeType;
        private int commodityLimit;
        private int flagCommodityLimit;

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public int getStoreType() {
            return storeType;
        }

        public void setStoreType(int storeType) {
            this.storeType = storeType;
        }

        public int getCommodityLimit() {
            return commodityLimit;
        }

        public void setCommodityLimit(int commodityLimit) {
            this.commodityLimit = commodityLimit;
        }

        public int getFlagCommodityLimit() {
            return flagCommodityLimit;
        }

        public void setFlagCommodityLimit(int flagCommodityLimit) {
            this.flagCommodityLimit = flagCommodityLimit;
        }
    }
}
