package cn.com.taodaji.model.event;

import cn.com.taodaji.model.entity.MyselftUpdateP;

public class PersonInfoChangeEvent {
    private MyselftUpdateP.DataBean bean;

    public PersonInfoChangeEvent(MyselftUpdateP.DataBean bean) {
        this.bean = bean;
    }

    public MyselftUpdateP.DataBean getBean() {
        return bean;
    }

    public void setBean(MyselftUpdateP.DataBean bean) {
        this.bean = bean;
    }
}
