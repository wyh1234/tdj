package cn.com.taodaji.model.entity;

/**
 * Created by yangkuo on 2018-10-23.
 */
public class Inforeaded {

    /**
     * code : 0
     * userid : null
     * message : 信息请求成功，用户未登录
     * data : 143
     */

    private int err;
    private int code;
    private String userid;
    private String message;
    private String data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
