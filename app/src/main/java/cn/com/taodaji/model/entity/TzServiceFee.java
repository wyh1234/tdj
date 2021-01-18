package cn.com.taodaji.model.entity;

public class TzServiceFee {

    /**
     * err : 0
     * data : {"tzServiceFee":5}
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
         * tzServiceFee : 5
         */

        private int tzServiceFee;

        public int getTzServiceFee() {
            return tzServiceFee;
        }

        public void setTzServiceFee(int tzServiceFee) {
            this.tzServiceFee = tzServiceFee;
        }
    }
}
