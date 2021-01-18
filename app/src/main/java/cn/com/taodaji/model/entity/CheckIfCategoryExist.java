package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class CheckIfCategoryExist {
    private int err;
    private boolean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
