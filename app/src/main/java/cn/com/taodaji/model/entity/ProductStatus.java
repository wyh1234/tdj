package cn.com.taodaji.model.entity;

/**
 * Created by yangkuo on 2018/11/23.
 */
public class ProductStatus {

    /**
     * err : 0
     * data : {"map":{"total":26,"limitTotalNum":0,"limitSalesNum":0,"inReview":11,"pullOff":8,"putOn":7}}
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
         * map : {"total":26,"limitTotalNum":0,"limitSalesNum":0,"inReview":11,"pullOff":8,"putOn":7}
         */

        private MapBean map;

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public static class MapBean {
            /**
             * total : 26
             * limitTotalNum : 0
             * limitSalesNum : 0
             * inReview : 11
             * pullOff : 8
             * putOn : 7
             */

            private int total;
            private int limitTotalNum;
            private int limitSalesNum;
            private int inReview;
            private int pullOff;
            private int putOn;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getLimitTotalNum() {
                return limitTotalNum;
            }

            public void setLimitTotalNum(int limitTotalNum) {
                this.limitTotalNum = limitTotalNum;
            }

            public int getLimitSalesNum() {
                return limitSalesNum;
            }

            public void setLimitSalesNum(int limitSalesNum) {
                this.limitSalesNum = limitSalesNum;
            }

            public int getInReview() {
                return inReview;
            }

            public void setInReview(int inReview) {
                this.inReview = inReview;
            }

            public int getPullOff() {
                return pullOff;
            }

            public void setPullOff(int pullOff) {
                this.pullOff = pullOff;
            }

            public int getPutOn() {
                return putOn;
            }

            public void setPutOn(int putOn) {
                this.putOn = putOn;
            }
        }
    }
}
