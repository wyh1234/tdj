package cn.com.taodaji.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class BuyPackageFee {

    /**
     * err : 0
     * data : {"appid":"wxf1c361fc73d52a29","noncestr":"qSlePuYyOvkUYpoN","package":"Sign=WXPay","partnerid":"1530961881","prepayid":"wx25115206805693d68e63fde61123014500","sign":"71A629EAAEE3B66BE17E039066896AD9","timestamp":"1569383523","outTradeNo":"rec_0002_cwsFpF20190930104937961","payMoney":"80","notify_url":"http://test.51taodj.com:3001/receiveStationFeeInfo/wxPaySuccessNotify"}
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
         * appid : wxf1c361fc73d52a29
         * noncestr : qSlePuYyOvkUYpoN
         * package : Sign=WXPay
         * partnerid : 1530961881
         * prepayid : wx25115206805693d68e63fde61123014500
         * sign : 71A629EAAEE3B66BE17E039066896AD9
         * timestamp : 1569383523
         * outTradeNo : rec_0002_cwsFpF20190930104937961
         * payMoney : 80
         * notify_url : http://test.51taodj.com:3001/receiveStationFeeInfo/wxPaySuccessNotify
         */

        private String appid;
        private String noncestr;
        @JsonProperty(value = "package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String outTradeNo;
        private String payMoney;
        private String notify_url;

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

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
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

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }
    }
}
