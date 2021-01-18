package cn.com.taodaji.model.entity;

import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class PurchaseBean {

    /**
     * err : 0
     * data : {"code":858163}
     * msg : 短信发送成功
     */

    private int err;
    private LoginPurchaseBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public LoginPurchaseBean getData() {
        return data;
    }

    public void setData(LoginPurchaseBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
