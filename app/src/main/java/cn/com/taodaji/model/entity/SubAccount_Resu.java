package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class SubAccount_Resu {

    /**
     * err : 0
     * data : {"code":858163}
     * msg : 短信发送成功
     */

    private int err;
    private List<SubAccount> data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<SubAccount> getData() {
        return data;
    }

    public void setData(List<SubAccount> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
