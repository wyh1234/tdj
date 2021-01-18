package cn.com.taodaji.model.event;

import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

/**
 * Created by Administrator on 2017-06-02.
 */
public class PackingCashReturnCarbeanOrOrderBeanEvent {


    private CartGoodsBean cartGoodsBean;
    private OrderDetail.ItemsBean orderBean;

    public PackingCashReturnCarbeanOrOrderBeanEvent(CartGoodsBean cartGoodsBean, OrderDetail.ItemsBean orderBean) {
        this.cartGoodsBean = cartGoodsBean;
        this.orderBean = orderBean;
    }

    public CartGoodsBean getCartGoodsBean() {
        return cartGoodsBean;
    }

    public void setCartGoodsBean(CartGoodsBean cartGoodsBean) {
        this.cartGoodsBean = cartGoodsBean;
    }

    public OrderDetail.ItemsBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderDetail.ItemsBean orderBean) {
        this.orderBean = orderBean;
    }
}
