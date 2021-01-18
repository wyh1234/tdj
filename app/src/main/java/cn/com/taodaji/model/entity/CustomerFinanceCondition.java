package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-08-17.
 */

public class CustomerFinanceCondition {

    /**
     * err : 0
     * data : {"total":2,"items":[{"entityId":4,"createTime":"2017-07-29 15:51","updateTime":"2017-06-29 15:51","status":1,"customerId":1,"outTradeNo":"58ff20e0319611e78bfe4f960d1051ff","paymentMethod":4,"paymentAmount":12.24,"moneyAmount":0,"rechargeAmount":0,"refundAmount":0,"onlinePaymentAmount":0,"accountPaymentAmount":0,"remarks":"资金提现","isHotel":1,"role":"0","hotelName":"湖北黄牛庄","subUserId":"-1","customerName":"又又","transactionNumber":"","extOrderId":"4500000041","productId":0,"refundTotalMoney":0,"refundQty":0,"refundMoney":0,"productImg":"","productName":"","productNickName":"","productDesc":"","productPrice":0,"productUnit":"","originalQty":0,"originalTotalMoney":0,"orderCreateTime":"0000-00-00 00:00:00","orderPayTime":null,"afterSaleApplyTime":null,"afterSaleHandleTime":null,"afterSaleApplicationId":0,"afterSaleApplicationNo":"0","storeId":0,"withdrawalRechargeAmount":0,"withdrawalRechargeAmountFee":0,"withdrawalAfterSaleAmount":12.24,"withdrawalAfterSaleAmountFee":2,"withdrawalAmount":12.24,"withdrawalFeeAmount":2,"customerCashWithdrawalId":11,"deliveryFee":0,"cashCoupon":0,"flag":"2017-07"},{"entityId":3,"createTime":"2017-07-29 15:39","updateTime":"2017-06-29 15:39","status":2,"customerId":1,"outTradeNo":"58ff20e0319611e78bfe4f960d1051ff","paymentMethod":2,"paymentAmount":26.23,"moneyAmount":0,"rechargeAmount":0,"refundAmount":0,"onlinePaymentAmount":12,"accountPaymentAmount":24.23,"remarks":"资金提现","isHotel":1,"role":"0","hotelName":"湖北黄牛庄","subUserId":"-1","customerName":"又又","transactionNumber":"","extOrderId":"4500000041","productId":0,"refundTotalMoney":0,"refundQty":0,"refundMoney":0,"productImg":"","productName":"","productNickName":"","productDesc":"","productPrice":0,"productUnit":"","originalQty":0,"originalTotalMoney":0,"orderCreateTime":"0000-00-00 00:00:00","orderPayTime":null,"afterSaleApplyTime":null,"afterSaleHandleTime":null,"afterSaleApplicationId":0,"afterSaleApplicationNo":"0","storeId":0,"withdrawalRechargeAmount":3.23,"withdrawalRechargeAmountFee":0.03,"withdrawalAfterSaleAmount":23,"withdrawalAfterSaleAmountFee":2,"withdrawalAmount":26.23,"withdrawalFeeAmount":2.03,"customerCashWithdrawalId":10,"deliveryFee":0,"cashCoupon":0,"flag":"2017-07"}],"pn":1,"ps":10,"count":{"paymentRechargeMoney":12.24,"paymentConsumeMoney":26.23,"paymentAfterSaleMoney":0,"paymentWithdrawalMoney":0,"totalSumMoney":-13.99}}
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
         * total : 2
         * items : [{"entityId":4,"createTime":"2017-07-29 15:51","updateTime":"2017-06-29 15:51","status":1,"customerId":1,"outTradeNo":"58ff20e0319611e78bfe4f960d1051ff","paymentMethod":4,"paymentAmount":12.24,"moneyAmount":0,"rechargeAmount":0,"refundAmount":0,"onlinePaymentAmount":0,"accountPaymentAmount":0,"remarks":"资金提现","isHotel":1,"role":"0","hotelName":"湖北黄牛庄","subUserId":"-1","customerName":"又又","transactionNumber":"","extOrderId":"4500000041","productId":0,"refundTotalMoney":0,"refundQty":0,"refundMoney":0,"productImg":"","productName":"","productNickName":"","productDesc":"","productPrice":0,"productUnit":"","originalQty":0,"originalTotalMoney":0,"orderCreateTime":"0000-00-00 00:00:00","orderPayTime":null,"afterSaleApplyTime":null,"afterSaleHandleTime":null,"afterSaleApplicationId":0,"afterSaleApplicationNo":"0","storeId":0,"withdrawalRechargeAmount":0,"withdrawalRechargeAmountFee":0,"withdrawalAfterSaleAmount":12.24,"withdrawalAfterSaleAmountFee":2,"withdrawalAmount":12.24,"withdrawalFeeAmount":2,"customerCashWithdrawalId":11,"deliveryFee":0,"cashCoupon":0,"flag":"2017-07"},{"entityId":3,"createTime":"2017-07-29 15:39","updateTime":"2017-06-29 15:39","status":2,"customerId":1,"outTradeNo":"58ff20e0319611e78bfe4f960d1051ff","paymentMethod":2,"paymentAmount":26.23,"moneyAmount":0,"rechargeAmount":0,"refundAmount":0,"onlinePaymentAmount":12,"accountPaymentAmount":24.23,"remarks":"资金提现","isHotel":1,"role":"0","hotelName":"湖北黄牛庄","subUserId":"-1","customerName":"又又","transactionNumber":"","extOrderId":"4500000041","productId":0,"refundTotalMoney":0,"refundQty":0,"refundMoney":0,"productImg":"","productName":"","productNickName":"","productDesc":"","productPrice":0,"productUnit":"","originalQty":0,"originalTotalMoney":0,"orderCreateTime":"0000-00-00 00:00:00","orderPayTime":null,"afterSaleApplyTime":null,"afterSaleHandleTime":null,"afterSaleApplicationId":0,"afterSaleApplicationNo":"0","storeId":0,"withdrawalRechargeAmount":3.23,"withdrawalRechargeAmountFee":0.03,"withdrawalAfterSaleAmount":23,"withdrawalAfterSaleAmountFee":2,"withdrawalAmount":26.23,"withdrawalFeeAmount":2.03,"customerCashWithdrawalId":10,"deliveryFee":0,"cashCoupon":0,"flag":"2017-07"}]
         * pn : 1
         * ps : 10
         * count : {"paymentRechargeMoney":12.24,"paymentConsumeMoney":26.23,"paymentAfterSaleMoney":0,"paymentWithdrawalMoney":0,"totalSumMoney":-13.99}
         */

        private int total;
        private int pn;
        private int ps;
        private CountBean count;
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

        public CountBean getCount() {
            return count;
        }

        public void setCount(CountBean count) {
            this.count = count;
        }

        public List<CustomerFinanceItem> getItems() {
            return items;
        }

        public void setItems(List<CustomerFinanceItem> items) {
            this.items = items;
        }

        public static class CountBean {
            /**
             * paymentRechargeMoney : 12.24
             * paymentConsumeMoney : 26.23
             * paymentAfterSaleMoney : 0
             * paymentWithdrawalMoney : 0
             * totalSumMoney : -13.99
             */

            private BigDecimal paymentRechargeMoney;
            private BigDecimal paymentConsumeMoney;
            private BigDecimal paymentAfterSaleMoney;
            private BigDecimal paymentWithdrawalMoney;
            private BigDecimal totalSumMoney;
            private BigDecimal paymentCancelOrderMoney;

            public BigDecimal getPaymentCancelOrderMoney() {
                return paymentCancelOrderMoney;
            }

            public void setPaymentCancelOrderMoney(BigDecimal paymentCancelOrderMoney) {
                this.paymentCancelOrderMoney = paymentCancelOrderMoney;
            }

            public BigDecimal getPaymentRechargeMoney() {
                return paymentRechargeMoney;
            }

            public void setPaymentRechargeMoney(BigDecimal paymentRechargeMoney) {
                this.paymentRechargeMoney = paymentRechargeMoney;
            }

            public BigDecimal getPaymentConsumeMoney() {
                return paymentConsumeMoney;
            }

            public void setPaymentConsumeMoney(BigDecimal paymentConsumeMoney) {
                this.paymentConsumeMoney = paymentConsumeMoney;
            }

            public BigDecimal getPaymentAfterSaleMoney() {
                return paymentAfterSaleMoney;
            }

            public void setPaymentAfterSaleMoney(BigDecimal paymentAfterSaleMoney) {
                this.paymentAfterSaleMoney = paymentAfterSaleMoney;
            }

            public BigDecimal getPaymentWithdrawalMoney() {
                return paymentWithdrawalMoney;
            }

            public void setPaymentWithdrawalMoney(BigDecimal paymentWithdrawalMoney) {
                this.paymentWithdrawalMoney = paymentWithdrawalMoney;
            }

            public BigDecimal getTotalSumMoney() {
                return totalSumMoney;
            }

            public void setTotalSumMoney(BigDecimal totalSumMoney) {
                this.totalSumMoney = totalSumMoney;
            }
        }

    }
}
