package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-07-03.
 */

public class EvaluateReplyEvent {
    private int evaluateId;

    public int getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    public EvaluateReplyEvent(int evaluateId) {
        this.evaluateId = evaluateId;
    }

}
