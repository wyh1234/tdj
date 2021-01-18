package cn.com.taodaji.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class OrderStatusCount implements Serializable {

    /**
     * seller_print_goods : 0
     * wait_buyer_confirm_goods : 0   待收货
     * wait_seller_send_goods : 2     待发货
     * wait_buyer_pay : 0             代付款
     * trade_success : 4              //交易成功
     * wait_seller_confirm_goods : 0  //待确认
     * "wait_buyer_evaluate": 0,       //待买家评价
     * "wait_seller_evaluate": 0,      //待卖家评价
     * "trade_finished": 8             //交易完成
     */

    private int seller_print_goods;
    private int wait_buyer_confirm_goods;
    private int wait_seller_send_goods;
    private int wait_buyer_pay;
    private int trade_success;
    private int wait_seller_confirm_goods;

    private int wait_buyer_evaluate;
    private int wait_seller_evaluate;
    private int trade_finished;
    private int afterSalesCount;
    private int trade_canceled;


    public int getTrade_canceled() {
        return trade_canceled;
    }

    public void setTrade_canceled(int trade_canceled) {
        this.trade_canceled = trade_canceled;
    }

    public int getWait_buyer_evaluate() {
        return wait_buyer_evaluate;
    }

    public void setWait_buyer_evaluate(int wait_buyer_evaluate) {
        this.wait_buyer_evaluate = wait_buyer_evaluate;
    }

    public int getWait_seller_evaluate() {
        return wait_seller_evaluate;
    }

    public void setWait_seller_evaluate(int wait_seller_evaluate) {
        this.wait_seller_evaluate = wait_seller_evaluate;
    }

    public int getTrade_finished() {
        return trade_finished;
    }

    public void setTrade_finished(int trade_finished) {
        this.trade_finished = trade_finished;
    }

    public int getSeller_print_goods() {
        return seller_print_goods;
    }

    public void setSeller_print_goods(int seller_print_goods) {
        this.seller_print_goods = seller_print_goods;
    }

    public int getWait_buyer_confirm_goods() {
        return wait_buyer_confirm_goods;
    }

    public void setWait_buyer_confirm_goods(int wait_buyer_confirm_goods) {
        this.wait_buyer_confirm_goods = wait_buyer_confirm_goods;
    }

    public int getWait_seller_send_goods() {
        return wait_seller_send_goods;
    }

    public void setWait_seller_send_goods(int wait_seller_send_goods) {
        this.wait_seller_send_goods = wait_seller_send_goods;
    }

    public int getWait_buyer_pay() {
        return wait_buyer_pay;
    }

    public void setWait_buyer_pay(int wait_buyer_pay) {
        this.wait_buyer_pay = wait_buyer_pay;
    }

    public int getTrade_success() {
        return trade_success;
    }

    public void setTrade_success(int trade_success) {
        this.trade_success = trade_success;
    }

    public int getWait_seller_confirm_goods() {
        return wait_seller_confirm_goods;
    }

    public void setWait_seller_confirm_goods(int wait_seller_confirm_goods) {
        this.wait_seller_confirm_goods = wait_seller_confirm_goods;
    }

    public int getAfterSalesCount() {
        return afterSalesCount;
    }

    public void setAfterSalesCount(int afterSalesCount) {
        this.afterSalesCount = afterSalesCount;
    }
}
