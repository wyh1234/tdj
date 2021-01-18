package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class DeleteSalesAppByEntityId {

    /**
     * err : 0
     * data : 1
     * msg : success
     */

    private int err;
    private Object data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
