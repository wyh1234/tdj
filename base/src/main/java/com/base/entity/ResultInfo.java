package com.base.entity;

public class ResultInfo<T> {

    /**
     * err : 0
     * data : {"code":858163}
     * msg : 短信发送成功
     */

    private int err;
    private T data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
