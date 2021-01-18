package cn.com.taodaji.model.sqlBean;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.math.BigDecimal;

import cn.com.taodaji.model.entity.OrderStatusCount;

/**
 * Created by Administrator on 2016/12/12 0012.
 */
public class LoginSupplierBean extends SugarRecord {
    /**
     * "entityId": 36,
     * "createTime": "2016-12-06 10:33",
     * "username": "18571161280",
     * "phoneNumber": "18571161280",
     * "realname": "YOYO",
     * "site": 2,
     * "store": 6,
     * "storeName": "格子铺",
     * "storePics": "店招图片",
     * "storeAddress": "人民路西",
     * "storeNumber": "007",
     * "provinceId": 13,
     * "cityId": 193,
     * "isActive": 0,
     * "markCode": "6e214bc0bb5c11e68939ebe897c4bafe_18571161280",
     * "flag": 1,
     * "remarks": "",
     * "userType": 1,
     * "pushToken": "",
     * "sourceType": "ios",
     * "marketId": 市场id【int型】,"
     * "marketName": 市场名称,
     * "idcardNumber": "身份证号【为空时状态是未认证】",
     * "idcardPics": "身份证图片多张以，分割",
     * "storeStatus": "店铺状态0正常，1今日休息，2歇业一周,3歇业一月,4关闭商铺【int型】",
     * "storeStatusRemark": "店铺状态说明",
     * "closeStoreDatetime": "店铺休息日期【storeStatus状态为2，3时】",
     * "closeStoreEndDatetime": "店铺歇业结束时间【storeStatus状态为2，3时】",
     * "authStatus": "0 已经提交（待审核）,1 已认证（审核通过），2 驳回  （xxxxx?） （无效：0、-1、2，  有效：1）",
     * "authStatusRemark": "认证状态说明",
     * "licencePics":"营业执照多张以，分割",
     * "foodQualityPics": "食品资格证多张以，分割"
     */

    @Unique
    private int entityId;
    private String createTime;
    private String username;
    private String phoneNumber;
    private String realname;
    private int site;
    private String siteName;
    private int store;
    private String storeName;
    private String storePics;
    private String storeAddress;
    private String storeNumber;
    private int provinceId;
    private int cityId;
    private int isActive;
    private String markCode;
    private int flag;
    private String remarks;
    private int userType;
    private String idcardNumber;
    private String idcardPics;
    private int storeStatus;
    private String storeStatusRemark;
    private String closeStoreDatetime;
    private String closeStoreEndDatetime;
    private int authStatus;
    private String authStatusRemark;
    private String foodQualityPics;
    private String licencePics;
    private int marketId;
    private String marketName;
    private String pushToken;
    private String sourceType;
    @Ignore
    private OrderStatusCount orderNumber;

    private String qqNumber;
    private String stationAddress;
    private String stationId;
    private String stationName;
    private String stationContactName;
    private String stationContactPhone;
    private String stationOfficePhone;

    private String address;
    private String gender;
    private String frontageIdcardImageUrl;
    private String backIdcardImageUrl;
    private String birthday;
    private String expirationDate;
    private String isAuth;

    private String storeCategory="";

    private int automaticRenewalFee;
    private int isAutomaticRenewal;
    private BigDecimal receiveMoney;
    private int isAllOpen;
    private String withdrawalMoney;
    private int supplierSaleType;

    public int getSupplierSaleType() {
        return supplierSaleType;
    }

    public void setSupplierSaleType(int supplierSaleType) {
        this.supplierSaleType = supplierSaleType;
    }

    public String getWithdrawalMoney() {
        return withdrawalMoney;
    }

    public void setWithdrawalMoney(String withdrawalMoney) {
        this.withdrawalMoney = withdrawalMoney;
    }

    public int getAutomaticRenewalFee() {
        return automaticRenewalFee;
    }

    public void setAutomaticRenewalFee(int automaticRenewalFee) {
        this.automaticRenewalFee = automaticRenewalFee;
    }

    public int getIsAutomaticRenewal() {
        return isAutomaticRenewal;
    }

    public void setIsAutomaticRenewal(int isAutomaticRenewal) {
        this.isAutomaticRenewal = isAutomaticRenewal;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public int getIsAllOpen() {
        return isAllOpen;
    }

    public void setIsAllOpen(int isAllOpen) {
        this.isAllOpen = isAllOpen;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFrontageIdcardImageUrl() {
        return frontageIdcardImageUrl;
    }

    public void setFrontageIdcardImageUrl(String frontageIdcardImageUrl) {
        this.frontageIdcardImageUrl = frontageIdcardImageUrl;
    }

    public String getBackIdcardImageUrl() {
        return backIdcardImageUrl;
    }

    public void setBackIdcardImageUrl(String backIdcardImageUrl) {
        this.backIdcardImageUrl = backIdcardImageUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationContactName() {
        return stationContactName;
    }

    public void setStationContactName(String stationContactName) {
        this.stationContactName = stationContactName;
    }

    public String getStationContactPhone() {
        return stationContactPhone;
    }

    public void setStationContactPhone(String stationContactPhone) {
        this.stationContactPhone = stationContactPhone;
    }

    public String getStationOfficePhone() {
        return stationOfficePhone;
    }

    public void setStationOfficePhone(String stationOfficePhone) {
        this.stationOfficePhone = stationOfficePhone;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }


    public OrderStatusCount getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(OrderStatusCount orderNumber) {
        this.orderNumber = orderNumber;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
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

    public String getStorePics() {
        return storePics;
    }

    public void setStorePics(String storePics) {
        this.storePics = storePics;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String getIdcardPics() {
        return idcardPics;
    }

    public void setIdcardPics(String idcardPics) {
        this.idcardPics = idcardPics;
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

    public String getCloseStoreDatetime() {
        return closeStoreDatetime;
    }

    public void setCloseStoreDatetime(String closeStoreDatetime) {
        this.closeStoreDatetime = closeStoreDatetime;
    }

    public String getCloseStoreEndDatetime() {
        return closeStoreEndDatetime;
    }

    public void setCloseStoreEndDatetime(String closeStoreEndDatetime) {
        this.closeStoreEndDatetime = closeStoreEndDatetime;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthStatusRemark() {
        return authStatusRemark;
    }

    public void setAuthStatusRemark(String authStatusRemark) {
        this.authStatusRemark = authStatusRemark;
    }

    public String getFoodQualityPics() {
        return foodQualityPics;
    }

    public void setFoodQualityPics(String foodQualityPics) {
        this.foodQualityPics = foodQualityPics;
    }

    public String getLicencePics() {
        return licencePics;
    }

    public void setLicencePics(String licencePics) {
        this.licencePics = licencePics;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }


    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
