package cn.com.taodaji.model.entity;


public class CouponsStatistics {

    /**
     * err : 0
     * data : {"unused":2,"used":0,"expired":0}
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
         * unused : 2
         * used : 0
         * expired : 0
         * 说明：unused：未使用，used：已使用，expired：已过期
         */

        private int unused;
        private int used;
        private int expired;

        public int getUnused() {
            return unused;
        }

        public void setUnused(int unused) {
            this.unused = unused;
        }

        public int getUsed() {
            return used;
        }

        public void setUsed(int used) {
            this.used = used;
        }

        public int getExpired() {
            return expired;
        }

        public void setExpired(int expired) {
            this.expired = expired;
        }
    }
}
