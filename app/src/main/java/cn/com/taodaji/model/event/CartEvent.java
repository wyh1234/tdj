package cn.com.taodaji.model.event;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;

public class CartEvent {

    public CartGoodsBean data;
    public boolean isUpdate = false;
    public int position = -1;

    public boolean isUpdate() {
        return isUpdate;
    }

    public CartEvent setUpdate(boolean update) {
        isUpdate = update;
        return this;
    }

    public CartGoodsBean getData() {
        return data;
    }

    public void setData(CartGoodsBean data) {
        this.data = data;
    }

    public CartEvent(CartGoodsBean bean) {
        data = bean;
    }


}
