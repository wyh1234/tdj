package cn.com.taodaji.model.event;

import cn.com.taodaji.model.entity.GoodsReceiptAddressBean;

public class UpdateAddressEvent {
    GoodsReceiptAddressBean bean;

    public UpdateAddressEvent(GoodsReceiptAddressBean bean) {
        this.bean = bean;
    }

    public GoodsReceiptAddressBean getBean() {
        return bean;
    }

    public void setBean(GoodsReceiptAddressBean bean) {
        this.bean = bean;
    }
}
