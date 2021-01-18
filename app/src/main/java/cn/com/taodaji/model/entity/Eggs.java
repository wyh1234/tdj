package cn.com.taodaji.model.entity;

public class Eggs {

    /**
     * err : 0
     * data : {"has":0}
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
         * has : 0
         */

        private int has;

        public int getHas() {
            return has;
        }

        public void setHas(int has) {
            this.has = has;
        }
    }
}
