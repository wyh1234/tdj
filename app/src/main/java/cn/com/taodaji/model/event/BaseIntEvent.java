package cn.com.taodaji.model.event;

/**
 * 一次性事件，用过后清除，免干扰其他类使用
 */
public class BaseIntEvent {
    private int code;

    public BaseIntEvent(int message) {
        code = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
