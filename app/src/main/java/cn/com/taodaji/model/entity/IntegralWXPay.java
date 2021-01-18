package cn.com.taodaji.model.entity;

public class IntegralWXPay {


    /**
     * status : 200
     * data : {"nonce_str":"I4EGwfYB0U6AHdbX","appid":"wx602b75dea9fde1b2","sign":"480D9F83C880236D00258C871AAB87A4","trade_type":"APP","return_msg":"OK","result_code":"SUCCESS","mch_id":"1442568802","return_code":"SUCCESS","prepay_id":"wx081639504780209ba73242461549718700"}
     * totalCount : null
     * error : null
     * message : null
     * errorCode : null
     */

    private int err;
    private DataBean data;
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
         * nonce_str : I4EGwfYB0U6AHdbX
         * appid : wx602b75dea9fde1b2
         * sign : 480D9F83C880236D00258C871AAB87A4
         * trade_type : APP
         * return_msg : OK
         * result_code : SUCCESS
         * mch_id : 1442568802
         * return_code : SUCCESS
         * prepay_id : wx081639504780209ba73242461549718700
         */

        private String noncestr;
        private String appid;
        private String sign;
        private String trade_type;
        private String return_msg;
        private String result_code;
        private String partnerid;
        private String return_code;
        private String prepayid;
        private String timestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }
    }
}
