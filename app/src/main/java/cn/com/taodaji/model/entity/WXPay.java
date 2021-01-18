package cn.com.taodaji.model.entity;

//import com.google.gson.annotations.SerializedName;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WXPay {

    /**
     * appid : wxcc18a2df17b81415
     * noncestr : hMyP5yXrT6n1kEgX
     * package : Sign=WXPay
     * partnerid : 1357962502
     * prepayid : wx20170222110712833aac45570546771203
     * timestamp : 1487732855
     */

    private String appid;
    private String noncestr;
//    @SerializedName("package")
    @JsonProperty(value = "package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
