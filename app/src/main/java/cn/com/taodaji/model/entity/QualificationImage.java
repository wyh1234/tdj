package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class QualificationImage {

    /**
     * err : 0
     * data : 1
     * msg : success
     */

    private int err;
    private int data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
