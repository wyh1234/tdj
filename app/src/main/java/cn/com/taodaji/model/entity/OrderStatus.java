package cn.com.taodaji.model.entity;

public class OrderStatus {

    /**
     * status : {"wait_buyer_pay":3,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":0,"wait_seller_confirm_goods":0,"trade_success":0}
     */

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class StatusBean {
        /**
         * "wait_buyer_pay": 3,  //待付款
         * "wait_buyer_confirm_goods": 0,  //待确认【采购商“个人中心”中的“待发货”包括待确认和待发货两项的统计和】
         * "wait_seller_send_goods": 0, //待发货
         * "wait_seller_confirm_goods": 0 //待收货
         * "trade_success": 0, //已收货
         */

        private int wait_buyer_pay;
        private int wait_buyer_confirm_goods;
        private int wait_seller_send_goods;
        private int wait_seller_confirm_goods;
        private int trade_success;

        public int getWait_buyer_pay() {
            return wait_buyer_pay;
        }

        public void setWait_buyer_pay(int wait_buyer_pay) {
            this.wait_buyer_pay = wait_buyer_pay;
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

        public int getWait_seller_confirm_goods() {
            return wait_seller_confirm_goods;
        }

        public void setWait_seller_confirm_goods(int wait_seller_confirm_goods) {
            this.wait_seller_confirm_goods = wait_seller_confirm_goods;
        }

        public int getTrade_success() {
            return trade_success;
        }

        public void setTrade_success(int trade_success) {
            this.trade_success = trade_success;
        }
    }
}

