package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-08-21.
 */

public class CustomerFinanceRecordRefundDetail {

    /**
     * err : 0
     * data : {"refundTotalMoney":25,"refundQty":25,"productPrice":1,"productName":"2评价图片测试","productUnit":"斤","originalQty":30,"extOrderId":"200000214","orderCreateTime":"2017-08-19 15:52","afterSaleApplyTime":"2017-08-19 16:00","afterSaleHandleTime":null}
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
         * refundTotalMoney : 25
         * refundQty : 25
         * productPrice : 1
         * productName : 2评价图片测试
         * productUnit : 斤
         * originalQty : 30
         * extOrderId : 200000214
         * orderCreateTime : 2017-08-19 15:52
         * afterSaleApplyTime : 2017-08-19 16:00
         * afterSaleHandleTime : null
         */

        private BigDecimal refundTotalMoney;
        private BigDecimal refundQty;
        private BigDecimal productPrice;
        private String productName;
        private String productNickName;
        private String productUnit;
        private BigDecimal originalQty;
        private String extOrderId;
        private String orderCreateTime;
        private String afterSaleApplyTime;
        private Object afterSaleHandleTime;
        private String afterSaleApplicationNo;

        private String avgUnit;
        private BigDecimal avgPrice;
        private BigDecimal discountAvgPrice;
        private BigDecimal level2Value;
        private String level2Unit;
        private BigDecimal level3Value;
        private String level3Unit;
        private int levelType;


        public String getAvgUnit() {
            return avgUnit;
        }

        public void setAvgUnit(String avgUnit) {
            this.avgUnit = avgUnit;
        }

        public BigDecimal getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(BigDecimal avgPrice) {
            this.avgPrice = avgPrice;
        }

        public BigDecimal getDiscountAvgPrice() {
            return discountAvgPrice;
        }

        public void setDiscountAvgPrice(BigDecimal discountAvgPrice) {
            this.discountAvgPrice = discountAvgPrice;
        }

        public BigDecimal getLevel2Value() {
            return level2Value;
        }

        public void setLevel2Value(BigDecimal level2Value) {
            this.level2Value = level2Value;
        }

        public String getLevel2Unit() {
            return level2Unit;
        }

        public void setLevel2Unit(String level2Unit) {
            this.level2Unit = level2Unit;
        }

        public BigDecimal getLevel3Value() {
            return level3Value;
        }

        public void setLevel3Value(BigDecimal level3Value) {
            this.level3Value = level3Value;
        }

        public String getLevel3Unit() {
            return level3Unit;
        }

        public void setLevel3Unit(String level3Unit) {
            this.level3Unit = level3Unit;
        }

        public int getLevelType() {
            return levelType;
        }

        public void setLevelType(int levelType) {
            this.levelType = levelType;
        }

        public String getAfterSaleApplicationNo() {
            return afterSaleApplicationNo;
        }

        public void setAfterSaleApplicationNo(String afterSaleApplicationNo) {
            this.afterSaleApplicationNo = afterSaleApplicationNo;
        }

        public String getProductNickName() {
            return productNickName;
        }

        public void setProductNickName(String productNickName) {
            this.productNickName = productNickName;
        }

        public BigDecimal getRefundTotalMoney() {
            return refundTotalMoney;
        }

        public void setRefundTotalMoney(BigDecimal refundTotalMoney) {
            this.refundTotalMoney = refundTotalMoney;
        }

        public BigDecimal getRefundQty() {
            return refundQty;
        }

        public void setRefundQty(BigDecimal refundQty) {
            this.refundQty = refundQty;
        }

        public BigDecimal getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(BigDecimal productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public BigDecimal getOriginalQty() {
            return originalQty;
        }

        public void setOriginalQty(BigDecimal originalQty) {
            this.originalQty = originalQty;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public String getOrderCreateTime() {
            return orderCreateTime;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
        }

        public String getAfterSaleApplyTime() {
            return afterSaleApplyTime;
        }

        public void setAfterSaleApplyTime(String afterSaleApplyTime) {
            this.afterSaleApplyTime = afterSaleApplyTime;
        }

        public Object getAfterSaleHandleTime() {
            return afterSaleHandleTime;
        }

        public void setAfterSaleHandleTime(Object afterSaleHandleTime) {
            this.afterSaleHandleTime = afterSaleHandleTime;
        }
    }
}
