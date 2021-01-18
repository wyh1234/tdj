package cn.com.taodaji.model.event;
/**
 * 待评论数量改变事件
 */
public class EvaluateWaitCountEvent {

    private int waitCount;

    public EvaluateWaitCountEvent(int waitCount) {
        this.waitCount = waitCount;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }
}
