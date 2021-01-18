package cn.com.taodaji.model.entity;

public class AfterSales {

    /**
     * entityId : 2
     * createTime : 2017-03-02 16:39
     * afterSalesNo : 1488443994866
     * supplierName : 供应商姓名
     * supplierTel : 18987654366
     * customerName : 采购商
     * customerId : 32
     * storeId : 4
     * orderId : 323
     * orderItemId : 432
     * sku : 32434343223
     * unit : 斤
     * name : 菠菜
     * nickName : 四季青
     * price : 432
     * amount : 32
     * problemDescription : 问题描述
     * certificatePhotos : http://www.sina.com.cn
     * applyType : 1
     * status : 1
     */

    private int entityId;
    private String createTime;
    private String afterSalesNo;
    private String supplierName;
    private String supplierTel;
    private String customerName;
    private int customerId;
    private int storeId;
    private int orderId;
    private int orderItemId;
    private String sku;
    private String unit;
    private String name;
    private String nickName;
    private float price;
    private float amount;
    private String problemDescription;
    private String certificatePhotos;
    private int applyType;
    private int status;

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

    public String getAfterSalesNo() {
        return afterSalesNo;
    }

    public void setAfterSalesNo(String afterSalesNo) {
        this.afterSalesNo = afterSalesNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getCertificatePhotos() {
        return certificatePhotos;
    }

    public void setCertificatePhotos(String certificatePhotos) {
        this.certificatePhotos = certificatePhotos;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

