package cn.com.taodaji.model.event;

public class DeleteCustomeEvent {
    int flag; //0失败 1成功
    String msg;

    public DeleteCustomeEvent(int flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
