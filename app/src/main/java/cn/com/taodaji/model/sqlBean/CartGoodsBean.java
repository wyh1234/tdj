package cn.com.taodaji.model.sqlBean;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.math.BigDecimal;

import com.base.annotation.OnlyField;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

public class CartGoodsBean extends SugarRecord implements Serializable {

    @OnlyField
    @Unique
    private int specId;
    private int entityId;

    private int productId;

    private String productImage;
    private int storeId;
    private int productQty;
    private String productUnit;
    private String nickName = "";
    private String productName = "";
    private BigDecimal productPrice = BigDecimal.ZERO;
    private BigDecimal priceSum;
    @OnlyField
    private int itemId;
    private int orderId;
    private String sku;
    private int stock;
    private int countXg;
    private int typeXg;
    private boolean selected;
    private int itemStatus;
    private int status;//1表示上架 2,下架,3,审核中
    private int storeStatus;//0店铺正在营业
    private BigDecimal totalPrice;

    private int isP;// 0-零售 1-批发
    private int categoryId;
    private int commodityId;



    private String level2Unit;
    private BigDecimal level3Value = BigDecimal.ZERO;
    private int levelType;
    private BigDecimal level2Value = BigDecimal.ZERO;
    private String level3Unit;
    private String avgUnit;
    private BigDecimal avgPrice = BigDecimal.ZERO;


    private int creditLevel;
    @Ignore
    private String creditImgs = "";
    @Ignore
    private String creditContent = "";
    @Ignore
    private int isUseCoupon;//是否使用代金券    这个字段后台并没有加，目前不起作用

    private int productCriteria;//商品标准“1”：通货商品 “2”：精品商品 '

    private int productType;


    private String packageName;  //包装名称
    private BigDecimal foregift = BigDecimal.ZERO; //押金费用(单个)

    //订单列表押金使用
    @Ignore
    private BigDecimal orderForegift = BigDecimal.ZERO; //押金总金额
    @Ignore
    private int isForegift;// 是否收取押金 0-不收，1-收
    @Ignore
    private int packageStatus = -1;//押金状态  -1 订购，0 - 未退, 1-已退完
    @Ignore
    private int packageNum;  //押金订购数量
    @Ignore
    private BigDecimal packageFee = BigDecimal.ZERO;//订购或未退押金金额
    @Ignore
    private String remark;
    @Ignore
    private long afterSaleId;
    /**
     * 未退/处理中/已退/已支付 金额
     */
    @Ignore
    private BigDecimal currentFee = BigDecimal.ZERO;
    @Ignore
    private int currentNum; // 未退/处理中/已退/已支付 押金数量
    @Ignore
    private String customerName; //采购商名称
    @Ignore
    private String discountAvgPrice;
    @Ignore
    private long orderItemId;
    @Ignore
    private String orderStatus;
    @Ignore
    private long packOrderId;  //订单押金关联id
    @Ignore
    private String packageImg;   //退押包装物图片
    @Ignore
    private String payTime;
    @Ignore
    private String storeName;
    @Ignore
    private String applyTime;

    @Ignore
    private String qrCodeId;  //商品单号
    @Ignore
    private int type;//自定义区分押金列表分类
    @Ignore
    private int dayDiff;

    @Ignore
    private int applyStatus; //处理中押金状态  1-申请退押 ,2-取货中, 3-等待认领,4-已退, 5-拒绝退,6-撤销
    @Ignore
    private int orderStatusNum;//订单数字状态  0 不可退押，1-可点击退押
    @Ignore
    private int currentStatus;  //当前押金列表请求状态  0-未退，1-处理中，2-已退，3-已支付

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public int getOrderStatusNum() {
        return orderStatusNum;
    }

    public void setOrderStatusNum(int orderStatusNum) {
        this.orderStatusNum = orderStatusNum;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getDayDiff() {
        return dayDiff;
    }

    public void setDayDiff(int dayDiff) {
        this.dayDiff = dayDiff;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public long getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public BigDecimal getCurrentFee() {
        return currentFee;
    }

    public void setCurrentFee(BigDecimal currentFee) {
        this.currentFee = currentFee;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDiscountAvgPrice() {
        return discountAvgPrice;
    }

    public void setDiscountAvgPrice(String discountAvgPrice) {
        this.discountAvgPrice = discountAvgPrice;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPackOrderId() {
        return packOrderId;
    }

    public void setPackOrderId(long packOrderId) {
        this.packOrderId = packOrderId;
    }

    public String getPackageImg() {
        return packageImg;
    }

    public void setPackageImg(String packageImg) {
        this.packageImg = packageImg;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public int getIsForegift() {
        return isForegift;
    }

    public void setIsForegift(int isForegift) {
        this.isForegift = isForegift;
    }

    public int getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(int packageStatus) {
        this.packageStatus = packageStatus;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public BigDecimal getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(BigDecimal packageFee) {
        this.packageFee = packageFee;
    }

    public BigDecimal getOrderForegift() {
        return orderForegift;
    }

    public void setOrderForegift(BigDecimal orderForegift) {
        this.orderForegift = orderForegift;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getForegift() {
        return foregift;
    }

    public void setForegift(BigDecimal foregift) {
        this.foregift = foregift;
    }

    public int getProductCriteria() {
        return productCriteria;
    }

    public void setProductCriteria(int productCriteria) {
        this.productCriteria = productCriteria;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getIsP() {
        return isP;
    }

    public void setIsP(int isP) {
        this.isP = isP;
    }

    public String getCreditContent() {
        return creditContent;
    }

    public void setCreditContent(String creditContent) {
        this.creditContent = creditContent;
    }

    public String getCreditImgs() {
        return creditImgs;
    }

    public void setCreditImgs(String creditImgs) {
        this.creditImgs = creditImgs;
    }

    public int getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(int creditLevel) {
        this.creditLevel = creditLevel;
    }


    public int getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(int isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
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

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public BigDecimal getLevel2Value() {
        return level2Value;
    }

    public void setLevel2Value(BigDecimal level2Value) {
        this.level2Value = level2Value;
    }

    public String getLevel3Unit() {
        return level3Unit;
    }

    public void setLevel3Unit(String level3Unit) {
        this.level3Unit = level3Unit;
    }


    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public int getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(int storeStatus) {
        this.storeStatus = storeStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCountXg() {
        return countXg;
    }

    public void setCountXg(int countXg) {
        this.countXg = countXg;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getTypeXg() {
        return typeXg;
    }

    public void setTypeXg(int typeXg) {
        this.typeXg = typeXg;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
