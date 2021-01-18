package cn.com.taodaji.model.event;

public class GoodsDetailEvent {
    private int code;

    public GoodsDetailEvent(int message) {
        code = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
