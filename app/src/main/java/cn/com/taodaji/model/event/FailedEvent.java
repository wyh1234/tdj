package cn.com.taodaji.model.event;

public class FailedEvent {

    private Class cls;
    private String msg;

    public FailedEvent(Class cls) {
        this.cls = cls;
    }

    public FailedEvent(Class cls, String msg) {
        this.cls = cls;
        this.msg = msg;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
