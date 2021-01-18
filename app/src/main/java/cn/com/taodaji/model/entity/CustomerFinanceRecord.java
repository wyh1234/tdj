package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;



public class CustomerFinanceRecord {

    /**
     * err : 0
     * data : {"total":1,"items":[{"entityId":3825,"createTime":"2017-06-23 10:40","updateTime":"2017-06-23 10:40","status":1,"customerId":2,"outTradeNo":"8bcc8230572d11e78c3cb1eabacf0d29","paymentMethod":0,"paymentAmount":0.07,"moneyAmount":0.07,"rechargeAmount":0,"refundAmount":0.14,"onlinePaymentAmount":0,"accountPaymentAmount":0,"remarks":"退款","isHotel":1,"role":"管理员","hotelName":"全聚德","subUserId":"-1","customerName":"全聚德","transactionNumber":"4009302001201706226913184170","extOrderId":"200000071","productId":930,"refundTotalMoney":0.07,"refundQty":1,"refundMoney":0.07,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170622162416ffd8d944.jpg","productName":"荷兰芹","productNickName":"荷兰芹","productDesc":"好","productPrice":0.07,"productUnit":"斤","originalQty":1,"originalTotalMoney":0.07,"orderCreateTime":"2017-06-22 17:31","orderPayTime":"2017-06-22 17:32","afterSaleApplyTime":"2017-06-23 10:37","afterSaleHandleTime":"2017-08-15 16:09","afterSaleApplicationId":126,"afterSaleApplicationNo":"1498185447745","storeId":11,"withdrawalRechargeAmount":0,"withdrawalRechargeAmountFee":0,"withdrawalAfterSaleAmount":0,"withdrawalAfterSaleAmountFee":0,"withdrawalAmount":0,"withdrawalFeeAmount":0,"customerCashWithdrawalId":0,"deliveryFee":0,"cashCoupon":0,"year":2017,"month":6}],"pn":1,"ps":10}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * total : 1
         * items : [{"entityId":3825,"createTime":"2017-06-23 10:40","updateTime":"2017-06-23 10:40","status":1,"customerId":2,"outTradeNo":"8bcc8230572d11e78c3cb1eabacf0d29","paymentMethod":0,"paymentAmount":0.07,"moneyAmount":0.07,"rechargeAmount":0,"refundAmount":0.14,"onlinePaymentAmount":0,"accountPaymentAmount":0,"remarks":"退款","isHotel":1,"role":"管理员","hotelName":"全聚德","subUserId":"-1","customerName":"全聚德","transactionNumber":"4009302001201706226913184170","extOrderId":"200000071","productId":930,"refundTotalMoney":0.07,"refundQty":1,"refundMoney":0.07,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170622162416ffd8d944.jpg","productName":"荷兰芹","productNickName":"荷兰芹","productDesc":"好","productPrice":0.07,"productUnit":"斤","originalQty":1,"originalTotalMoney":0.07,"orderCreateTime":"2017-06-22 17:31","orderPayTime":"2017-06-22 17:32","afterSaleApplyTime":"2017-06-23 10:37","afterSaleHandleTime":"2017-08-15 16:09","afterSaleApplicationId":126,"afterSaleApplicationNo":"1498185447745","storeId":11,"withdrawalRechargeAmount":0,"withdrawalRechargeAmountFee":0,"withdrawalAfterSaleAmount":0,"withdrawalAfterSaleAmountFee":0,"withdrawalAmount":0,"withdrawalFeeAmount":0,"customerCashWithdrawalId":0,"deliveryFee":0,"cashCoupon":0,"year":2017,"month":6}]
         * pn : 1
         * ps : 10
         */

        private int total;
        private int pn;
        private int ps;
        private List<CustomerFinanceItem> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public List<CustomerFinanceItem> getItems() {
            return items;
        }

        public void setItems(List<CustomerFinanceItem> items) {
            this.items = items;
        }


    }
}
