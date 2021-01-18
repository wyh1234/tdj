package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class StoreDiyFee {


    /**
     * err : 0
     * msg : success
     * data : {"freeDays":38,"countMonry":0,"freeEndTime":1580525509000,"rowSize":0}
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
         * freeDays : 38
         * countMonry : 0.0
         * freeEndTime : 1580525509000
         * rowSize : 0
         */

        private int freeDays;
        private BigDecimal countMonry;
        private long freeEndTime;
        private int rowSize;
        private String freeText;

        public String getFreeText() {
            return freeText;
        }

        public void setFreeText(String freeText) {
            this.freeText = freeText;
        }

        public int getFreeDays() {
            return freeDays;
        }

        public void setFreeDays(int freeDays) {
            this.freeDays = freeDays;
        }

        public BigDecimal getCountMonry() {
            return countMonry;
        }

        public void setCountMonry(BigDecimal countMonry) {
            this.countMonry = countMonry;
        }

        public long getFreeEndTime() {
            return freeEndTime;
        }

        public void setFreeEndTime(long freeEndTime) {
            this.freeEndTime = freeEndTime;
        }

        public int getRowSize() {
            return rowSize;
        }

        public void setRowSize(int rowSize) {
            this.rowSize = rowSize;
        }
    }
}
