package cn.com.taodaji.model.entity;

public class DeleteCommodity {

    /**
     * err : 0
     * data : {"commodityId":3,"cszNum":0,"dshNum":0,"storeId":12,"yxjNum":0}
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
         * commodityId : 3
         * cszNum : 0
         * dshNum : 0
         * storeId : 12
         * yxjNum : 0
         */

        private int commodityId;
        private int cszNum;
        private int dshNum;
        private int storeId;
        private int yxjNum;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getCszNum() {
            return cszNum;
        }

        public void setCszNum(int cszNum) {
            this.cszNum = cszNum;
        }

        public int getDshNum() {
            return dshNum;
        }

        public void setDshNum(int dshNum) {
            this.dshNum = dshNum;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getYxjNum() {
            return yxjNum;
        }

        public void setYxjNum(int yxjNum) {
            this.yxjNum = yxjNum;
        }
    }
}
