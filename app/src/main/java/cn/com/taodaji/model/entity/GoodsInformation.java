package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

public class GoodsInformation implements Serializable {
    /**
     * entityId : 364
     * createTime : 1486349767000
     * name : 黑胡椒
     * commodityId : 100
     * store : 13
     * storeName : 窝窝蔬菜
     * unit : 千克
     * price : 12
     * specialPrice : 0
     * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg
     * stock : 2000
     * qrCodeId :
     * categoryId : 25
     * description : 他咯哦
     * saleNum : 0
     * authStatus : 2
     * bannerImage :
     * catalogCategoryName : 调味品-椒类
     * categories : 25
     * nickName : 小黑胡椒
     * status : 1
     * gallery : ["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061055408c92ff1e.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061054492468ff70.jpg"]
     * phone : 18571161280
     * realName : 格子
     * verifyInfo :
     * sku : xtb_1486349767730
     * credentialsImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170206105527cb28e3c1.jpg
     * memberPrice : 14.399999999999999
     * monthSaleNumbers  月销
     */

    @OnlyField
    private String productProperty;
    private int varietyEntityId;
    private String varietyName;
    private int entityId;
    private String endTime;
    private long createTime;
    private long updateTime;
    private String name;
    private int commodityId;
    private int store;
    private String storeName;
    private String unit;
    private BigDecimal price;
    private BigDecimal specialPrice;
    private String image;
    private int stock;
    private int productQty;
    private String qrCodeId;
    private int categoryId;
    private String description;
    private int saleNum;
    private int totalSellNum;
    private int authStatus;//1待审核  2审核成功 3审核驳回
    private String bannerImage;
    private String catalogCategoryName;
    private String categories;
    private String nickName = "";
    private int status;
    private String phone;
    private String realName;
    private String verifyInfo;
    private String sku;
    private String credentialsImage;
    private BigDecimal memberPrice;
    private List<String> gallery;
    private int productType;//0、普通的商品 1、Banner图的特价产品 2、供应商报名参加特价活动
    private int specialId;
    private int allowPurchase;//限购数量
    private int alreadyPurchase;//已购数量
    private BigDecimal minPrice;
    private BigDecimal maxPrice = BigDecimal.valueOf(-1);
    private int isDrainageArea;
    private int priceEditable;

    public int getPriceEditable() {
        return priceEditable;
    }

    public void setPriceEditable(int priceEditable) {
        this.priceEditable = priceEditable;
    }

    public int getIsDrainageArea() {
        return isDrainageArea;
    }

    public void setIsDrainageArea(int isDrainageArea) {
        this.isDrainageArea = isDrainageArea;
    }

    private int monthSaleNumbers;
    private int isP;
    private int productCriteria;//商品标准“1”：通货商品 “2”：精品商品 '

    private String packageName;  //包装名称
    private BigDecimal foregift = BigDecimal.ZERO; //押金费用(单个)

    private int isF;//是否有押金0：无，1：有
    private boolean isFavor = false;//是否收藏
    private List<GoodsSpecification> specs;
    private int storeType;
    private int shopCouponsCount;
    private String eavluationLevelOneNum;
    private int eavluationRatioNum;
    private int isEdit;
    private int parentCategoryId;
    private String customerExhibitionImage;
    private int isCanteen;
    private int isForceTemplate;

    public int getShopCouponsCount() {
        return shopCouponsCount;
    }

    public void setShopCouponsCount(int shopCouponsCount) {
        this.shopCouponsCount = shopCouponsCount;
    }

    public int getIsForceTemplate() {
        return isForceTemplate;
    }

    public void setIsForceTemplate(int isForceTemplate) {
        this.isForceTemplate = isForceTemplate;
    }

    public int getIsCanteen() {
        return isCanteen;
    }

    public void setIsCanteen(int isCanteen) {
        this.isCanteen = isCanteen;
    }

    public String getCustomerExhibitionImage() {
        return customerExhibitionImage;
    }

    public void setCustomerExhibitionImage(String customerExhibitionImage) {
        this.customerExhibitionImage = customerExhibitionImage;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public String getEavluationLevelOneNum() {
        return eavluationLevelOneNum;
    }

    public void setEavluationLevelOneNum(String eavluationLevelOneNum) {
        this.eavluationLevelOneNum = eavluationLevelOneNum;
    }

    public int getEavluationRatioNum() {
        return eavluationRatioNum;
    }

    public void setEavluationRatioNum(int eavluationRatioNum) {
        this.eavluationRatioNum = eavluationRatioNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(String productProperty) {
        this.productProperty = productProperty;
    }

    public int getVarietyEntityId() {
        return varietyEntityId;
    }

    public void setVarietyEntityId(int varietyEntityId) {
        this.varietyEntityId = varietyEntityId;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getIsF() {
        return isF;
    }

    public void setIsF(int isF) {
        this.isF = isF;
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


    public boolean isFavor() {
        return isFavor;
    }

    public void setIsFavor(boolean favorite) {
        isFavor = favorite;
    }

    public int getIsP() {
        return isP;
    }

    public void setIsP(int isP) {
        this.isP = isP;
    }

    public int getMonthSaleNumbers() {
        return monthSaleNumbers;
    }

    public void setMonthSaleNumbers(int monthSaleNumbers) {
        this.monthSaleNumbers = monthSaleNumbers;
    }


    public int getTotalSellNum() {
        return totalSellNum;
    }

    public void setTotalSellNum(int totalSellNum) {
        this.totalSellNum = totalSellNum;
    }

    public List<GoodsSpecification> getSpecs() {
        return specs;
    }

    public void setSpecs(List<GoodsSpecification> specs) {
        this.specs = specs;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public int getAllowPurchase() {
        return allowPurchase;
    }

    public void setAllowPurchase(int allowPurchase) {
        this.allowPurchase = allowPurchase;
    }

    public int getAlreadyPurchase() {
        return alreadyPurchase;
    }

    public void setAlreadyPurchase(int alreadyPurchase) {
        this.alreadyPurchase = alreadyPurchase;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getCatalogCategoryName() {
        return catalogCategoryName;
    }

    public void setCatalogCategoryName(String catalogCategoryName) {
        this.catalogCategoryName = catalogCategoryName;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getVerifyInfo() {
        return verifyInfo;
    }

    public void setVerifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCredentialsImage() {
        return credentialsImage;
    }

    public void setCredentialsImage(String credentialsImage) {
        this.credentialsImage = credentialsImage;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }
}



