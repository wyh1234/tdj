package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by yangkuo on 2019/1/9.
 */
public class FindByCustomerRecordId {

    /**
     * err : 0
     * data : {"entityId":227286,"createTime":"2019-01-08 11:13","updateTime":"2019-01-08 11:13","status":6,"customerId":4967,"outTradeNo":"0","paymentMethod":0,"paymentAmount":11.6,"moneyAmount":5659.61,"rechargeAmount":0,"refundAmount":11.6,"onlinePaymentAmount":0,"accountPaymentAmount":0,"remarks":"退押金","isHotel":1,"role":"管理员","hotelName":"真实测试酒店001","customerTel":"","subUserId":null,"customerName":"真实测试酒店001","transactionNumber":"20190104133813","extOrderId":"6486827862841102336","productId":39396,"refundTotalMoney":11.6,"refundQty":2,"refundMoney":11.6,"productImg":null,"productName":"菜心","productNickName":"龙玩游戏","productDesc":null,"productPrice":55,"productUnit":"斤","originalQty":3,"originalTotalMoney":165,"orderCreateTime":"2019-01-04 13:38","orderPayTime":"2019-01-04 13:38","afterSaleApplyTime":"2019-01-08 10:56","afterSaleHandleTime":null,"afterSaleApplicationId":27,"afterSaleApplicationNo":"侦测店铺001","storeId":1314,"withdrawalRechargeAmount":0,"withdrawalRechargeAmountFee":0,"withdrawalAfterSaleAmount":0,"withdrawalAfterSaleAmountFee":0,"withdrawalAmount":0,"withdrawalFeeAmount":0,"customerCashWithdrawalId":0,"deliveryFee":5.8,"cashCoupon":0,"taxAmount":0,"avgUnit":null,"avgPrice":null,"discountAvgPrice":null,"specificationId":85571,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":1,"websiteId":2,"cityName":null,"applicant":"别人公司","reason":0,"commission":0,"packageFee":0}
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
         * entityId : 227286
         * createTime : 2019-01-08 11:13
         * updateTime : 2019-01-08 11:13
         * status : 6
         * customerId : 4967
         * outTradeNo : 0
         * paymentMethod : 0
         * paymentAmount : 11.6
         * moneyAmount : 5659.61
         * rechargeAmount : 0
         * refundAmount : 11.6
         * onlinePaymentAmount : 0
         * accountPaymentAmount : 0
         * remarks : 退押金
         * isHotel : 1
         * role : 管理员
         * hotelName : 真实测试酒店001
         * customerTel :
         * subUserId : null
         * customerName : 真实测试酒店001
         * transactionNumber : 20190104133813
         * extOrderId : 6486827862841102336
         * productId : 39396
         * refundTotalMoney : 11.6
         * refundQty : 2
         * refundMoney : 11.6
         * productImg : null
         * productName : 菜心
         * productNickName : 龙玩游戏
         * productDesc : null
         * productPrice : 55
         * productUnit : 斤
         * originalQty : 3
         * originalTotalMoney : 165
         * orderCreateTime : 2019-01-04 13:38
         * orderPayTime : 2019-01-04 13:38
         * afterSaleApplyTime : 2019-01-08 10:56
         * afterSaleHandleTime : null
         * afterSaleApplicationId : 27
         * afterSaleApplicationNo : 侦测店铺001
         * storeId : 1314
         * withdrawalRechargeAmount : 0
         * withdrawalRechargeAmountFee : 0
         * withdrawalAfterSaleAmount : 0
         * withdrawalAfterSaleAmountFee : 0
         * withdrawalAmount : 0
         * withdrawalFeeAmount : 0
         * customerCashWithdrawalId : 0
         * deliveryFee : 5.8
         * cashCoupon : 0
         * taxAmount : 0
         * avgUnit : null
         * avgPrice : null
         * discountAvgPrice : null
         * specificationId : 85571
         * level2Value : null
         * level2Unit : null
         * level3Value : null
         * level3Unit : null
         * levelType : 1
         * websiteId : 2
         * cityName : null
         * applicant : 别人公司
         * reason : 0
         * commission : 0
         * packageFee : 0
         */

        private int entityId;
        private String createTime;
        private String updateTime;
        private int status;
        private int customerId;
        private String outTradeNo;
        private int paymentMethod;
        private BigDecimal paymentAmount;
        private BigDecimal moneyAmount;
        private int rechargeAmount;
        private BigDecimal refundAmount;
        private int onlinePaymentAmount;
        private int accountPaymentAmount;
        private String remarks;
        private int isHotel;
        private String role;
        private String hotelName;
        private String customerTel;
        private Object subUserId;
        private String customerName;
        private String transactionNumber;
        private String extOrderId;
        private int productId;
        private BigDecimal refundTotalMoney;
        private int refundQty;
        private BigDecimal refundMoney;
        private String productImg;
        private String productName;
        private String productNickName;
        private String productDesc;
        private BigDecimal productPrice;
        private String productUnit;
        private int originalQty;
        private BigDecimal originalTotalMoney;
        private String orderCreateTime;
        private String orderPayTime;
        private String afterSaleApplyTime;
        private String afterSaleHandleTime;
        private int afterSaleApplicationId;
        private String afterSaleApplicationNo;
        private int storeId;
        private int withdrawalRechargeAmount;
        private int withdrawalRechargeAmountFee;
        private int withdrawalAfterSaleAmount;
        private int withdrawalAfterSaleAmountFee;
        private int withdrawalAmount;
        private int withdrawalFeeAmount;
        private int customerCashWithdrawalId;
        private BigDecimal deliveryFee;
        private BigDecimal cashCoupon;
        private BigDecimal taxAmount;
        private String avgUnit;
        private BigDecimal avgPrice;
        private BigDecimal discountAvgPrice;
        private int specificationId;
        private BigDecimal level2Value;
        private String level2Unit;
        private BigDecimal level3Value;
        private String level3Unit;
        private int levelType;
        private int websiteId;
        private String cityName;
        private String applicant;
        private int reason;
        private BigDecimal commission;
        private BigDecimal packageFee;

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public BigDecimal getMoneyAmount() {
            return moneyAmount;
        }

        public void setMoneyAmount(BigDecimal moneyAmount) {
            this.moneyAmount = moneyAmount;
        }

        public int getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(int rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public BigDecimal getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(BigDecimal refundAmount) {
            this.refundAmount = refundAmount;
        }

        public int getOnlinePaymentAmount() {
            return onlinePaymentAmount;
        }

        public void setOnlinePaymentAmount(int onlinePaymentAmount) {
            this.onlinePaymentAmount = onlinePaymentAmount;
        }

        public int getAccountPaymentAmount() {
            return accountPaymentAmount;
        }

        public void setAccountPaymentAmount(int accountPaymentAmount) {
            this.accountPaymentAmount = accountPaymentAmount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getIsHotel() {
            return isHotel;
        }

        public void setIsHotel(int isHotel) {
            this.isHotel = isHotel;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getCustomerTel() {
            return customerTel;
        }

        public void setCustomerTel(String customerTel) {
            this.customerTel = customerTel;
        }

        public Object getSubUserId() {
            return subUserId;
        }

        public void setSubUserId(Object subUserId) {
            this.subUserId = subUserId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public BigDecimal getRefundTotalMoney() {
            return refundTotalMoney;
        }

        public void setRefundTotalMoney(BigDecimal refundTotalMoney) {
            this.refundTotalMoney = refundTotalMoney;
        }

        public int getRefundQty() {
            return refundQty;
        }

        public void setRefundQty(int refundQty) {
            this.refundQty = refundQty;
        }

        public BigDecimal getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(BigDecimal refundMoney) {
            this.refundMoney = refundMoney;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNickName() {
            return productNickName;
        }

        public void setProductNickName(String productNickName) {
            this.productNickName = productNickName;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public BigDecimal getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(BigDecimal productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public int getOriginalQty() {
            return originalQty;
        }

        public void setOriginalQty(int originalQty) {
            this.originalQty = originalQty;
        }

        public BigDecimal getOriginalTotalMoney() {
            return originalTotalMoney;
        }

        public void setOriginalTotalMoney(BigDecimal originalTotalMoney) {
            this.originalTotalMoney = originalTotalMoney;
        }

        public String getOrderCreateTime() {
            return orderCreateTime;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
        }

        public String getOrderPayTime() {
            return orderPayTime;
        }

        public void setOrderPayTime(String orderPayTime) {
            this.orderPayTime = orderPayTime;
        }

        public String getAfterSaleApplyTime() {
            return afterSaleApplyTime;
        }

        public void setAfterSaleApplyTime(String afterSaleApplyTime) {
            this.afterSaleApplyTime = afterSaleApplyTime;
        }

        public String getAfterSaleHandleTime() {
            return afterSaleHandleTime;
        }

        public void setAfterSaleHandleTime(String afterSaleHandleTime) {
            this.afterSaleHandleTime = afterSaleHandleTime;
        }

        public int getAfterSaleApplicationId() {
            return afterSaleApplicationId;
        }

        public void setAfterSaleApplicationId(int afterSaleApplicationId) {
            this.afterSaleApplicationId = afterSaleApplicationId;
        }

        public String getAfterSaleApplicationNo() {
            return afterSaleApplicationNo;
        }

        public void setAfterSaleApplicationNo(String afterSaleApplicationNo) {
            this.afterSaleApplicationNo = afterSaleApplicationNo;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getWithdrawalRechargeAmount() {
            return withdrawalRechargeAmount;
        }

        public void setWithdrawalRechargeAmount(int withdrawalRechargeAmount) {
            this.withdrawalRechargeAmount = withdrawalRechargeAmount;
        }

        public int getWithdrawalRechargeAmountFee() {
            return withdrawalRechargeAmountFee;
        }

        public void setWithdrawalRechargeAmountFee(int withdrawalRechargeAmountFee) {
            this.withdrawalRechargeAmountFee = withdrawalRechargeAmountFee;
        }

        public int getWithdrawalAfterSaleAmount() {
            return withdrawalAfterSaleAmount;
        }

        public void setWithdrawalAfterSaleAmount(int withdrawalAfterSaleAmount) {
            this.withdrawalAfterSaleAmount = withdrawalAfterSaleAmount;
        }

        public int getWithdrawalAfterSaleAmountFee() {
            return withdrawalAfterSaleAmountFee;
        }

        public void setWithdrawalAfterSaleAmountFee(int withdrawalAfterSaleAmountFee) {
            this.withdrawalAfterSaleAmountFee = withdrawalAfterSaleAmountFee;
        }

        public int getWithdrawalAmount() {
            return withdrawalAmount;
        }

        public void setWithdrawalAmount(int withdrawalAmount) {
            this.withdrawalAmount = withdrawalAmount;
        }

        public int getWithdrawalFeeAmount() {
            return withdrawalFeeAmount;
        }

        public void setWithdrawalFeeAmount(int withdrawalFeeAmount) {
            this.withdrawalFeeAmount = withdrawalFeeAmount;
        }

        public int getCustomerCashWithdrawalId() {
            return customerCashWithdrawalId;
        }

        public void setCustomerCashWithdrawalId(int customerCashWithdrawalId) {
            this.customerCashWithdrawalId = customerCashWithdrawalId;
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

        public BigDecimal getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(BigDecimal taxAmount) {
            this.taxAmount = taxAmount;
        }

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

        public int getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(int specificationId) {
            this.specificationId = specificationId;
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

        public int getWebsiteId() {
            return websiteId;
        }

        public void setWebsiteId(int websiteId) {
            this.websiteId = websiteId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public int getReason() {
            return reason;
        }

        public void setReason(int reason) {
            this.reason = reason;
        }

        public BigDecimal getCommission() {
            return commission;
        }

        public void setCommission(BigDecimal commission) {
            this.commission = commission;
        }

        public BigDecimal getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(BigDecimal packageFee) {
            this.packageFee = packageFee;
        }
    }
}
