package cn.com.taodaji.model.event;

import cn.com.taodaji.model.entity.EvaluationList;

/**
 * 评论发布更改成功事件
 */
public class EvaluateListSucEvent {

    private boolean isNew;
    private EvaluationList.DataBean.ItemsBean evaluationBean;

    public EvaluateListSucEvent(EvaluationList.DataBean.ItemsBean evaluationBean) {
        this.evaluationBean = evaluationBean;
    }


    public EvaluateListSucEvent(boolean isNew) {
        this.isNew = isNew;
    }


    public EvaluationList.DataBean.ItemsBean getEvaluationBean() {
        return evaluationBean;
    }

    public void setEvaluationBean(EvaluationList.DataBean.ItemsBean evaluationBean) {
        this.evaluationBean = evaluationBean;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
