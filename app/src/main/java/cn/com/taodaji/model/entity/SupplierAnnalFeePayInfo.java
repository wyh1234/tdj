package cn.com.taodaji.model.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SupplierAnnalFeePayInfo implements Serializable {


    /**
     * err : 0
     * data : {"appid":"wx602b75dea9fde1b2","noncestr":"4wbUhjA53DMM6OAF","package":"Sign=WXPay","partnerid":"1530961881","prepayid":"wx171127069469305ee28269d11325961100","sign":"D5CA4D5FB261C889592D08A04171EAB5","timestamp":"1560742026"}
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
         * appid : wx602b75dea9fde1b2
         * noncestr : 4wbUhjA53DMM6OAF
         * package : Sign=WXPay
         * partnerid : 1530961881
         * prepayid : wx171127069469305ee28269d11325961100
         * sign : D5CA4D5FB261C889592D08A04171EAB5
         * timestamp : 1560742026
         */

        private String appid;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String outTradeNo;
        @JsonProperty(value = "package")
        private String packageX;

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }


        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
