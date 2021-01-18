package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class ShopInformation {
    /**
     * code : sijiqing
     * contactName :
     * contactPhone : 15271967861
     * createTime : 1471048877000
     * groupId : 10
     * isActive : 0
     * marketNo :
     * name : 兴发米面店
     * orderNumber : 6
     * productNumber : 42
     * site : 2
     * siteName : 淘大集
     * store : 10
     * storeStatus : 0
     * storeStatusRemark :
     * logoImageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608151038061ae4e049.jpg
     * marketName : 四季青米面市场
     * monthSaleNumbers  月销
     * items : [{"entityId":464,"createTime":1472197320000,"name":"德林面粉","commodityId":265,"store":10,"storeName":"兴发米面店","unit":"斤","price":1.9,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"49","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-面","categories":"49","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608261541595d64c5da.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472197320086","credentialsImage":""},{"entityId":467,"createTime":1472197500000,"name":"德林面条","commodityId":266,"store":10,"storeName":"兴发米面店","unit":"斤","price":2,"specialPrice":0,"image":"","stock":999999,"qrCodeId":"","categoryId":"49","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-面","categories":"49","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160826154500803dee70.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472197500767","credentialsImage":""},{"entityId":472,"createTime":1472199084000,"name":"东北长粒香米","commodityId":259,"store":10,"storeName":"兴发米面店","unit":"斤","price":3.25,"specialPrice":0,"image":"","stock":999998,"qrCodeId":"","categoryId":"48","description":"default desc","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"米面油-米","categories":"48","nickName":"","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082616112382f0f3fa.jpg"],"phone":"15271967861","realName":"汪波","verifyInfo":"","sku":"xtb_1472199084206","credentialsImage":""}]
     */

    private String code;
    private String contactName;
    private int storeType;
    private String contactPhone;
    private long createTime;
    private int groupId;
    private int isActive;
    private String marketNo;
    private String name;
    private int orderNumber;
    private int productNumber;
    private int site;
    private String siteName;
    private int store;
    private int storeStatus;
    private String storeStatusRemark;
    private String logoImageUrl;
    private String marketName;
    private List<GoodsInformation> items;
    private BigDecimal storeScore;
    private int monthSaleNumbers;
    private int favoriteCount;

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getMonthSaleNumbers() {
        return monthSaleNumbers;
    }

    public void setMonthSaleNumbers(int monthSaleNumbers) {
        this.monthSaleNumbers = monthSaleNumbers;
    }

    public BigDecimal getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(BigDecimal storeScore) {
        this.storeScore = storeScore;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getMarketNo() {
        return marketNo;
    }

    public void setMarketNo(String marketNo) {
        this.marketNo = marketNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(int storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getStoreStatusRemark() {
        return storeStatusRemark;
    }

    public void setStoreStatusRemark(String storeStatusRemark) {
        this.storeStatusRemark = storeStatusRemark;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public List<GoodsInformation> getItems() {
        return items;
    }

    public void setItems(List<GoodsInformation> items) {
        this.items = items;
    }
}
