package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-08-17.
 */

public class CustomerFinanceItem {
    /**
     * entityId : 3825
     * createTime : 2017-06-23 10:40
     * updateTime : 2017-06-23 10:40
     * status : 1
     * customerId : 2
     * outTradeNo : 8bcc8230572d11e78c3cb1eabacf0d29
     * paymentMethod : 0
     * paymentAmount : 0.07
     * moneyAmount : 0.07
     * rechargeAmount : 0
     * refundAmount : 0.14
     * onlinePaymentAmount : 0
     * accountPaymentAmount : 0
     * remarks : 退款
     * isHotel : 1
     * role : 管理员
     * hotelName : 全聚德
     * subUserId : -1
     * customerName : 全聚德
     * transactionNumber : 4009302001201706226913184170
     * extOrderId : 200000071
     * productId : 930
     * refundTotalMoney : 0.07
     * refundQty : 1
     * refundMoney : 0.07
     * productImg : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170622162416ffd8d944.jpg
     * productName : 荷兰芹
     * productNickName : 荷兰芹
     * productDesc : 好
     * productPrice : 0.07
     * productUnit : 斤
     * originalQty : 1
     * originalTotalMoney : 0.07
     * orderCreateTime : 2017-06-22 17:31
     * orderPayTime : 2017-06-22 17:32
     * afterSaleApplyTime : 2017-06-23 10:37
     * afterSaleHandleTime : 2017-08-15 16:09
     * afterSaleApplicationId : 126
     * afterSaleApplicationNo : 1498185447745
     * storeId : 11
     * withdrawalRechargeAmount : 0
     * withdrawalRechargeAmountFee : 0
     * withdrawalAfterSaleAmount : 0
     * withdrawalAfterSaleAmountFee : 0
     * withdrawalAmount : 0
     * withdrawalFeeAmount : 0
     * customerCashWithdrawalId : 0
     * deliveryFee : 0
     * cashCoupon : 0
     * year : 2017
     * month : 6
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
    private BigDecimal rechargeAmount;
    private BigDecimal refundAmount;
    private BigDecimal onlinePaymentAmount;
    private BigDecimal accountPaymentAmount;
    private String remarks;
    private int isHotel;
    private String role;
    private String hotelName;
    private String subUserId;
    private String customerName;
    private String transactionNumber;
    private String extOrderId;
    private int productId;
    private BigDecimal refundTotalMoney;
    private BigDecimal refundQty;
    private BigDecimal refundMoney;
    private String productImg;
    private String productName;
    private String productNickName;
    private String productDesc;
    private BigDecimal productPrice;
    private String productUnit;
    private BigDecimal originalQty;
    private BigDecimal originalTotalMoney;
    private String orderCreateTime;
    private String orderPayTime;
    private String afterSaleApplyTime;
    private String afterSaleHandleTime;
    private int afterSaleApplicationId;
    private String afterSaleApplicationNo;
    private int storeId;
    private BigDecimal withdrawalRechargeAmount;
    private BigDecimal withdrawalRechargeAmountFee;
    private BigDecimal withdrawalAfterSaleAmount;
    private BigDecimal withdrawalAfterSaleAmountFee;
    private BigDecimal withdrawalAmount;
    private BigDecimal withdrawalFeeAmount;
    private int customerCashWithdrawalId;
    private int deliveryFee;
    private int cashCoupon;
    private int year;
    private int month;

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

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getOnlinePaymentAmount() {
        return onlinePaymentAmount;
    }

    public void setOnlinePaymentAmount(BigDecimal onlinePaymentAmount) {
        this.onlinePaymentAmount = onlinePaymentAmount;
    }

    public BigDecimal getAccountPaymentAmount() {
        return accountPaymentAmount;
    }

    public void setAccountPaymentAmount(BigDecimal accountPaymentAmount) {
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

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
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

    public BigDecimal getRefundQty() {
        return refundQty;
    }

    public void setRefundQty(BigDecimal refundQty) {
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

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
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

    public BigDecimal getWithdrawalRechargeAmount() {
        return withdrawalRechargeAmount;
    }

    public void setWithdrawalRechargeAmount(BigDecimal withdrawalRechargeAmount) {
        this.withdrawalRechargeAmount = withdrawalRechargeAmount;
    }

    public BigDecimal getWithdrawalRechargeAmountFee() {
        return withdrawalRechargeAmountFee;
    }

    public void setWithdrawalRechargeAmountFee(BigDecimal withdrawalRechargeAmountFee) {
        this.withdrawalRechargeAmountFee = withdrawalRechargeAmountFee;
    }

    public BigDecimal getWithdrawalAfterSaleAmount() {
        return withdrawalAfterSaleAmount;
    }

    public void setWithdrawalAfterSaleAmount(BigDecimal withdrawalAfterSaleAmount) {
        this.withdrawalAfterSaleAmount = withdrawalAfterSaleAmount;
    }

    public BigDecimal getWithdrawalAfterSaleAmountFee() {
        return withdrawalAfterSaleAmountFee;
    }

    public void setWithdrawalAfterSaleAmountFee(BigDecimal withdrawalAfterSaleAmountFee) {
        this.withdrawalAfterSaleAmountFee = withdrawalAfterSaleAmountFee;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public BigDecimal getWithdrawalFeeAmount() {
        return withdrawalFeeAmount;
    }

    public void setWithdrawalFeeAmount(BigDecimal withdrawalFeeAmount) {
        this.withdrawalFeeAmount = withdrawalFeeAmount;
    }

    public int getCustomerCashWithdrawalId() {
        return customerCashWithdrawalId;
    }

    public void setCustomerCashWithdrawalId(int customerCashWithdrawalId) {
        this.customerCashWithdrawalId = customerCashWithdrawalId;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getCashCoupon() {
        return cashCoupon;
    }

    public void setCashCoupon(int cashCoupon) {
        this.cashCoupon = cashCoupon;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}
