package cn.com.taodaji.model.event;


public class DownLoadEvent {
    public String message;

    public DownLoadEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
