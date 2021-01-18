package cn.com.taodaji.model.entity;

public class ShareInfoBean {
    /**
     * err : 0
     * msg : 处理成功
     * data : {"path":"http://tdjtest.51taodj.com:8080/fund/static/images/qrcode/269772.png","sharePaht":"http://m.51taodj.com/tdjh5/register/page?verifyCode=269772"}
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
         * path : http://tdjtest.51taodj.com:8080/fund/static/images/qrcode/269772.png
         * sharePaht : http://m.51taodj.com/tdjh5/register/page?verifyCode=269772
         */

        private String path;
        private String sharePaht;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSharePaht() {
            return sharePaht;
        }

        public void setSharePaht(String sharePaht) {
            this.sharePaht = sharePaht;
        }
    }
}
