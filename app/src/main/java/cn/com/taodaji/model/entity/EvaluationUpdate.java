package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-06-22.
 */

public class EvaluationUpdate {

    /**
     * entityId : 2
     * replyContent : 谢谢好评
     *  "entityId":29473,   "productId":1489,   "creditLevel":1,   "evaluationContent":"评价错了",   "evalImg":""
     */

    private int entityId;
    private String replyContent;


    private int productId;
    private int creditLevel;
    private String evaluationContent;
    private String evalImg;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(int creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public String getEvalImg() {
        return evalImg;
    }

    public void setEvalImg(String evalImg) {
        this.evalImg = evalImg;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
