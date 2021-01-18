package cn.com.taodaji.model.event;

import java.util.List;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;

/**
 * Created by Administrator on 2017/2/22 0022.
 */
public class OrderPlaceEvent {
    private List<CartGoodsBean> list;

    public OrderPlaceEvent(List<CartGoodsBean> list) {
        this.list = list;
    }

    public List<CartGoodsBean> getList() {
        return list;
    }

    public void setList(List<CartGoodsBean> list) {
        this.list = list;
    }
}
