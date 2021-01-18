package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017/2/25 0025.
 */
public class OrderDeleteEvent {
    /**
     * err : 0
     * msg : success
     */

    private int err;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
