package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;

/**
 * Created by yangkuo on 2018/10/25.
 */
public class ScanQRCode {

    /**
     * err : 0
     * data : {"buyNumber":0,"createTime":1540176545000,"customerId":5152,"expectDeliveredDate":1540224000000,"expectDeliveredEarliestTime":"08:00","expectDeliveredLatestTime":"11:00","extOrderAmount":0,"extOrderId":"6459968642007179264","extOrderItemCount":0,"extTaxAmount":0,"itemCount":3,"items":[{"amount":2,"avgPrice":0.8,"avgUnit":"斤","commission":0,"description":"色泽好，品质佳芽长7到9公分","discount":1.6,"discountAvgPrice":0.8,"discountPrice":0.8,"discountTotalPrice":1.6,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171113121412808f78b1.jpg","itemId":1883710,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"绿豆芽","nickName":"","orderId":1504347,"price":0.8,"productId":5419,"qrCodeId":"9181022000047","realTotal":0,"returnedAmount":0,"sku":"xtb_1510546532930","specId":2296,"status":0,"storeId":24,"storeName":"金谦宝豆制品","totalPrice":1.6,"transactionRate":0,"unit":"斤"},{"amount":2,"avgPrice":1.8,"avgUnit":"斤","commission":0.9,"description":"脆嫩、水分充足、色泽亮丽、","discount":3.6,"discountAvgPrice":1.8,"discountPrice":1.8,"discountTotalPrice":3.6,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180727194922bf0a754f.jpg","itemId":1883711,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"莲藕","nickName":"精品莲藕、菡萏、莲菜、芙蕖","orderId":1504348,"price":1.8,"productId":1595,"qrCodeId":"9181022000054","realTotal":0,"returnedAmount":0,"sku":"xtb_1496998043350","specId":755,"status":0,"storeId":76,"storeName":"宋鹏莲藕批发部","totalPrice":3.6,"transactionRate":25,"unit":"斤"},{"amount":3,"avgPrice":1.26,"avgUnit":"斤","commission":0,"description":"8公分左右，色泽鲜艳","discount":3.78,"discountAvgPrice":1.26,"discountPrice":1.26,"discountTotalPrice":3.78,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170711081752a331572d.jpg","itemId":1883712,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"黄豆芽","nickName":"东津豆芽8公分","orderId":1504349,"price":1.26,"productId":2002,"qrCodeId":"9181022000061","realTotal":0,"returnedAmount":0,"sku":"xtb_1499732348446","specId":952,"status":0,"storeId":124,"storeName":"翔翔豆制品","totalPrice":3.78,"transactionRate":0,"unit":"斤"}],"orderId":0,"orderIds":"1504349,1504348,1504347","orderNo":"6459968642007179264","stationName":"竹叶山","statusCode":"wait_seller_confirm_goods","subtotalCost":8.98,"taxCost":0,"totalCommission":0,"totalCost":8.98,"transactionNumber":"20181022104918","updateTime":1540176558000}
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

    public static class DataBean implements Serializable {
        /**
         * buyNumber : 0
         * createTime : 1540176545000
         * customerId : 5152
         * expectDeliveredDate : 1540224000000
         * expectDeliveredEarliestTime : 08:00
         * expectDeliveredLatestTime : 11:00
         * extOrderAmount : 0
         * extOrderId : 6459968642007179264
         * extOrderItemCount : 0
         * extTaxAmount : 0
         * itemCount : 3
         * items : [{"amount":2,"avgPrice":0.8,"avgUnit":"斤","commission":0,"description":"色泽好，品质佳芽长7到9公分","discount":1.6,"discountAvgPrice":0.8,"discountPrice":0.8,"discountTotalPrice":1.6,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171113121412808f78b1.jpg","itemId":1883710,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"绿豆芽","nickName":"","orderId":1504347,"price":0.8,"productId":5419,"qrCodeId":"9181022000047","realTotal":0,"returnedAmount":0,"sku":"xtb_1510546532930","specId":2296,"status":0,"storeId":24,"storeName":"金谦宝豆制品","totalPrice":1.6,"transactionRate":0,"unit":"斤"},{"amount":2,"avgPrice":1.8,"avgUnit":"斤","commission":0.9,"description":"脆嫩、水分充足、色泽亮丽、","discount":3.6,"discountAvgPrice":1.8,"discountPrice":1.8,"discountTotalPrice":3.6,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180727194922bf0a754f.jpg","itemId":1883711,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"莲藕","nickName":"精品莲藕、菡萏、莲菜、芙蕖","orderId":1504348,"price":1.8,"productId":1595,"qrCodeId":"9181022000054","realTotal":0,"returnedAmount":0,"sku":"xtb_1496998043350","specId":755,"status":0,"storeId":76,"storeName":"宋鹏莲藕批发部","totalPrice":3.6,"transactionRate":25,"unit":"斤"},{"amount":3,"avgPrice":1.26,"avgUnit":"斤","commission":0,"description":"8公分左右，色泽鲜艳","discount":3.78,"discountAvgPrice":1.26,"discountPrice":1.26,"discountTotalPrice":3.78,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170711081752a331572d.jpg","itemId":1883712,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"黄豆芽","nickName":"东津豆芽8公分","orderId":1504349,"price":1.26,"productId":2002,"qrCodeId":"9181022000061","realTotal":0,"returnedAmount":0,"sku":"xtb_1499732348446","specId":952,"status":0,"storeId":124,"storeName":"翔翔豆制品","totalPrice":3.78,"transactionRate":0,"unit":"斤"}]
         * orderId : 0
         * orderIds : 1504349,1504348,1504347
         * orderNo : 6459968642007179264
         * stationName : 竹叶山
         * statusCode : wait_seller_confirm_goods
         * subtotalCost : 8.98
         * taxCost : 0
         * totalCommission : 0
         * totalCost : 8.98
         * transactionNumber : 20181022104918
         * updateTime : 1540176558000
         */

        private int buyNumber;
        private long createTime;
        private int customerId;
        private long expectDeliveredDate;
        private String expectDeliveredEarliestTime;
        private String expectDeliveredLatestTime;
        private int extOrderAmount;
        private String extOrderId;
        private int extOrderItemCount;
        private int extTaxAmount;
        private int itemCount;
        private int orderId;
        private String orderIds;
        private String orderNo;
        private String stationName;
        private String statusCode;
        private double subtotalCost;
        private int taxCost;
        private String statusLabel;//"statusLabel": "该订单已售后入口已关闭，如需售后请联系客服。",
        private int totalCommission;
        private double totalCost;
        private String transactionNumber;
        private long updateTime;
        private List<OrderDetail.ItemsBean> items;
        private int couponAmount;//代金券

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getStatusLabel() {
            return statusLabel;
        }

        public void setStatusLabel(String statusLabel) {
            this.statusLabel = statusLabel;
        }

        public int getBuyNumber() {
            return buyNumber;
        }

        public void setBuyNumber(int buyNumber) {
            this.buyNumber = buyNumber;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public long getExpectDeliveredDate() {
            return expectDeliveredDate;
        }

        public void setExpectDeliveredDate(long expectDeliveredDate) {
            this.expectDeliveredDate = expectDeliveredDate;
        }

        public String getExpectDeliveredEarliestTime() {
            return expectDeliveredEarliestTime;
        }

        public void setExpectDeliveredEarliestTime(String expectDeliveredEarliestTime) {
            this.expectDeliveredEarliestTime = expectDeliveredEarliestTime;
        }

        public String getExpectDeliveredLatestTime() {
            return expectDeliveredLatestTime;
        }

        public void setExpectDeliveredLatestTime(String expectDeliveredLatestTime) {
            this.expectDeliveredLatestTime = expectDeliveredLatestTime;
        }

        public int getExtOrderAmount() {
            return extOrderAmount;
        }

        public void setExtOrderAmount(int extOrderAmount) {
            this.extOrderAmount = extOrderAmount;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public int getExtOrderItemCount() {
            return extOrderItemCount;
        }

        public void setExtOrderItemCount(int extOrderItemCount) {
            this.extOrderItemCount = extOrderItemCount;
        }

        public int getExtTaxAmount() {
            return extTaxAmount;
        }

        public void setExtTaxAmount(int extTaxAmount) {
            this.extTaxAmount = extTaxAmount;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderIds() {
            return orderIds;
        }

        public void setOrderIds(String orderIds) {
            this.orderIds = orderIds;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public double getSubtotalCost() {
            return subtotalCost;
        }

        public void setSubtotalCost(double subtotalCost) {
            this.subtotalCost = subtotalCost;
        }

        public int getTaxCost() {
            return taxCost;
        }

        public void setTaxCost(int taxCost) {
            this.taxCost = taxCost;
        }

        public int getTotalCommission() {
            return totalCommission;
        }

        public void setTotalCommission(int totalCommission) {
            this.totalCommission = totalCommission;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public List<OrderDetail.ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<OrderDetail.ItemsBean> items) {
            this.items = items;
        }


    }
}
