package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class QualificationUpload {

    /**
     * err : 0
     * data : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032114313757fd4dd3.png
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
