package cn.com.taodaji.model.event;

import cn.com.taodaji.model.entity.ShopTypeBean;

public class ShopTypeSelectEvent {
    private ShopTypeBean bean ;


    public ShopTypeSelectEvent(ShopTypeBean bean) {
        this.bean = bean;
    }



    public ShopTypeBean getBean() {
        return bean;
    }

    public void setBean(ShopTypeBean bean) {
        this.bean = bean;
    }


}
