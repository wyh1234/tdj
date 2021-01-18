package cn.com.taodaji.model.entity;

import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
public class SupplierBean {

    /**
     * err : 0
     * data : {"code":858163}
     * msg : 短信发送成功
     */

    private int err;
    private LoginSupplierBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public LoginSupplierBean getData() {
        return data;
    }

    public void setData(LoginSupplierBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
