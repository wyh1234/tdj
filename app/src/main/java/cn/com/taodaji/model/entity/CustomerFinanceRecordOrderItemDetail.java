package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-08-19.
 */

public class CustomerFinanceRecordOrderItemDetail {

    /**
     * err : 0
     * data : {"paymentAmount":26.23,"deliveryFee":0,"cashCoupon":0,"extraAmount":0,"items":[{"amount":8,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1705030916146a1b8ace.jpeg","itemId":2976,"name":"大葱","nickName":"外地钢葱","orderId":1005,"price":1,"productId":853,"qrCodeId":"9170505000810","realTotal":8,"returnedAmount":0,"sku":"xtb_1493774312951","status":5,"storeId":16,"storeName":"杨氏蔬菜","totalPrice":8,"unit":"斤"},{"amount":3,"discount":0,"image":"http://tdj-res.oss-cn-hangzhou.aliyuncs.com/images/%E7%8C%AA%E8%A1%80.jpg","itemId":2977,"name":"猪血","nickName":"","orderId":1005,"price":1.5,"productId":314,"qrCodeId":"9170505000827","realTotal":4.5,"returnedAmount":0,"sku":"xtb_1491790963168","status":5,"storeId":16,"storeName":"杨氏蔬菜","totalPrice":4.5,"unit":"斤"}],"size":28}
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
         * paymentAmount : 26.23
         * deliveryFee : 0
         * cashCoupon : 0
         * extraAmount : 0
         * items : [{"amount":8,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1705030916146a1b8ace.jpeg","itemId":2976,"name":"大葱","nickName":"外地钢葱","orderId":1005,"price":1,"productId":853,"qrCodeId":"9170505000810","realTotal":8,"returnedAmount":0,"sku":"xtb_1493774312951","status":5,"storeId":16,"storeName":"杨氏蔬菜","totalPrice":8,"unit":"斤"},{"amount":3,"discount":0,"image":"http://tdj-res.oss-cn-hangzhou.aliyuncs.com/images/%E7%8C%AA%E8%A1%80.jpg","itemId":2977,"name":"猪血","nickName":"","orderId":1005,"price":1.5,"productId":314,"qrCodeId":"9170505000827","realTotal":4.5,"returnedAmount":0,"sku":"xtb_1491790963168","status":5,"storeId":16,"storeName":"杨氏蔬菜","totalPrice":4.5,"unit":"斤"}]
         * size : 28
         */

        private BigDecimal paymentAmount;
        private BigDecimal deliveryFee = BigDecimal.ZERO;
        private BigDecimal cashCoupon;
        private BigDecimal extraAmount;
        private int size;
        private List<OrderDetail.ItemsBean> items;

        private BigDecimal actualPaymentAmount = BigDecimal.ZERO;
        private BigDecimal couponAmount = BigDecimal.ZERO;
        private BigDecimal orderTotalAmount = BigDecimal.ZERO;

        private BigDecimal taxCost = BigDecimal.ZERO;


        private BigDecimal packageFee = BigDecimal.ZERO; //押金总金额


        public BigDecimal getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(BigDecimal packageFee) {
            this.packageFee = packageFee;
        }

        public BigDecimal getTaxCost() {
            return taxCost;
        }

        public void setTaxCost(BigDecimal taxCost) {
            this.taxCost = taxCost;
        }

        public BigDecimal getOrderTotalAmount() {
            return orderTotalAmount;
        }

        public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
            this.orderTotalAmount = orderTotalAmount;
        }


        public BigDecimal getActualPaymentAmount() {
            return actualPaymentAmount;
        }

        public void setActualPaymentAmount(BigDecimal actualPaymentAmount) {
            this.actualPaymentAmount = actualPaymentAmount;
        }

        public BigDecimal getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(BigDecimal couponAmount) {
            this.couponAmount = couponAmount;
        }

        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public BigDecimal getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(BigDecimal deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public BigDecimal getCashCoupon() {
            return cashCoupon;
        }

        public void setCashCoupon(BigDecimal cashCoupon) {
            this.cashCoupon = cashCoupon;
        }

        public BigDecimal getExtraAmount() {
            return extraAmount;
        }

        public void setExtraAmount(BigDecimal extraAmount) {
            this.extraAmount = extraAmount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<OrderDetail.ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<OrderDetail.ItemsBean> items) {
            this.items = items;
        }


    }
}
