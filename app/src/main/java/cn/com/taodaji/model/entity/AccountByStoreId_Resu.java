package cn.com.taodaji.model.entity;

import java.util.List;


public class AccountByStoreId_Resu {

    /**
     * err : 0
     * data : {"code":858163}
     * msg : 短信发送成功
     */

    private int err;
    private List<AccountByStoreId> data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<AccountByStoreId> getData() {
        return data;
    }

    public void setData(List<AccountByStoreId> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
