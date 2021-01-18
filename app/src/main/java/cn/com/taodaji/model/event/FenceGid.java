package cn.com.taodaji.model.event;

public class FenceGid {

    /**
     * data : {"clientStatus":"in","fenceGid":"516b"}
     * err : 0
     * msg : success
     */

    private DataBean data;
    private int err;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * clientStatus : in
         * fenceGid : 516b
         */

        private String clientStatus;
        private String fenceGid;

        public String getClientStatus() {
            return clientStatus;
        }

        public void setClientStatus(String clientStatus) {
            this.clientStatus = clientStatus;
        }

        public String getFenceGid() {
            return fenceGid;
        }

        public void setFenceGid(String fenceGid) {
            this.fenceGid = fenceGid;
        }
    }
}
