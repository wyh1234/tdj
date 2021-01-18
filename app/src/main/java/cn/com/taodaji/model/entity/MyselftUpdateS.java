package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class MyselftUpdateS {

    /**
     * err : 0
     * data : {"entityId":2,"createTime":"2017-03-15 15:19","username":"18571161280","phoneNumber":"18571161280","realname":"米粒","site":2,"store":3,"storeName":"福记蔬菜","storePics":"","storeAddress":"樊城区长虹北路120号","storeNumber":"007","provinceId":13,"cityId":193,"isActive":1,"markCode":"ca3bc2f0094f11e783e1ef7ba1d1d6d0_18571161280","flag":1,"remarks":"","userType":1,"idcardNumber":"","idcardPics":"","storeStatus":1,"storeStatusRemark":"","closeStoreDatetime":"1970-01-01 08:00","closeStoreEndDatetime":"1970-01-01 08:00","authStatus":1,"authStatusRemark":"审核通过!","foodQualityPics":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170315152737d18349e5.jpg","licencePics":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17031515272255da44b5.jpg","freezeMoney":0,"withdrawalMoney":0,"marketId":1,"marketName":"洪沟蔬菜批发市场","orderNumber":{"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}}
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
         * entityId : 2
         * createTime : 2017-03-15 15:19
         * username : 18571161280
         * phoneNumber : 18571161280
         * realname : 米粒
         * site : 2
         * store : 3
         * storeName : 福记蔬菜
         * storePics :
         * storeAddress : 樊城区长虹北路120号
         * storeNumber : 007
         * provinceId : 13
         * cityId : 193
         * isActive : 1
         * markCode : ca3bc2f0094f11e783e1ef7ba1d1d6d0_18571161280
         * flag : 1
         * remarks :
         * userType : 1
         * idcardNumber :
         * idcardPics :
         * storeStatus : 1
         * storeStatusRemark :
         * closeStoreDatetime : 1970-01-01 08:00
         * closeStoreEndDatetime : 1970-01-01 08:00
         * authStatus : 1
         * authStatusRemark : 审核通过!
         * foodQualityPics : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170315152737d18349e5.jpg
         * licencePics : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17031515272255da44b5.jpg
         * freezeMoney : 0
         * withdrawalMoney : 0
         * marketId : 1
         * marketName : 洪沟蔬菜批发市场
         * orderNumber : {"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}
         */

        private int entityId;
        private int fineCount;
        private String createTime;
        private String username;
        private String phoneNumber;
        private String realname;
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
        private BigDecimal freezeMoney;
        private BigDecimal withdrawalMoney;
        private int marketId;
        private String marketName;
        private OrderStatusCount orderNumber;
        private BigDecimal storeScore;
        private int isWarning;
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
        private int pushMessageNotReadCount;  //获取通知数

        private int site;
        private String siteName;
        private String storeCategory = "";

        private int favoriteStoreCount;
        private int favoriteProdCount;
        private int couponItemsCount;
        private int isAllOpen;

        public int getFineCount() {
            return fineCount;
        }

        public void setFineCount(int fineCount) {
            this.fineCount = fineCount;
        }

        public int getIsAllOpen() {
            return isAllOpen;
        }

        public void setIsAllOpen(int isAllOpen) {
            this.isAllOpen = isAllOpen;
        }

        public int getCouponItemsCount() {
            return couponItemsCount;
        }

        public void setCouponItemsCount(int couponItemsCount) {
            this.couponItemsCount = couponItemsCount;
        }

        public int getFavoriteStoreCount() {
            return favoriteStoreCount;
        }

        public void setFavoriteStoreCount(int favoriteStoreCount) {
            this.favoriteStoreCount = favoriteStoreCount;
        }

        public int getFavoriteProdCount() {
            return favoriteProdCount;
        }

        public void setFavoriteProdCount(int favoriteProdCount) {
            this.favoriteProdCount = favoriteProdCount;
        }

        public String getStoreCategory() {
            return storeCategory;
        }

        public void setStoreCategory(String storeCategory) {
            this.storeCategory = storeCategory;
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


        public int getPushMessageNotReadCount() {
            return pushMessageNotReadCount;
        }

        public void setPushMessageNotReadCount(int pushMessageNotReadCount) {
            this.pushMessageNotReadCount = pushMessageNotReadCount;
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

        public int getIsWarning() {
            return isWarning;
        }

        public void setIsWarning(int isWarning) {
            this.isWarning = isWarning;
        }

        public BigDecimal getStoreScore() {
            return storeScore;
        }

        public void setStoreScore(BigDecimal storeScore) {
            this.storeScore = storeScore;
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

        public BigDecimal getFreezeMoney() {
            return freezeMoney;
        }

        public void setFreezeMoney(BigDecimal freezeMoney) {
            this.freezeMoney = freezeMoney;
        }

        public BigDecimal getWithdrawalMoney() {
            return withdrawalMoney;
        }

        public void setWithdrawalMoney(BigDecimal withdrawalMoney) {
            this.withdrawalMoney = withdrawalMoney;
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

        public OrderStatusCount getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(OrderStatusCount orderNumber) {
            this.orderNumber = orderNumber;
        }

    }
}
