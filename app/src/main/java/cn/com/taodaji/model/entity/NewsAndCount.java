package cn.com.taodaji.model.entity;

public class NewsAndCount {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"notReadPunishmentRes":1,"notReadPushMessageRes":0,"newRes":1}
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
         * notReadPunishmentRes : 1
         * notReadPushMessageRes : 0
         * newRes : 1
         */

        private int notReadPunishmentRes;
        private int notReadPushMessageRes;
        private int newRes;

        public int getNotReadPunishmentRes() {
            return notReadPunishmentRes;
        }

        public void setNotReadPunishmentRes(int notReadPunishmentRes) {
            this.notReadPunishmentRes = notReadPunishmentRes;
        }

        public int getNotReadPushMessageRes() {
            return notReadPushMessageRes;
        }

        public void setNotReadPushMessageRes(int notReadPushMessageRes) {
            this.notReadPushMessageRes = notReadPushMessageRes;
        }

        public int getNewRes() {
            return newRes;
        }

        public void setNewRes(int newRes) {
            this.newRes = newRes;
        }
    }
}
