package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class MyselftUpdateP {

    /**
     * err : 0
     * data : {"entityId":1,"createTime":"2017-03-21 11:20","username":"18571161290","realname":"又又","hotelName":"MOMO酒店","phoneNumber":"18571161290","provinceId":13,"cityId":193,"hotelAddress":"樊城区人民路72号","isActive":1,"userType":0,"markCode":"8fa55eb0095111e7a85ecb189ce58509_18571161280","flag":2,"remarks":"","role":1,"imageUrl":"","verifyInfo":"审核通过!","bzlicenceUrl":"","identificationCard":"","identificationImage":"","authStatus":1,"money":0,"subUserId":5,"orderNumber":{"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}}
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
         * entityId : 1
         * createTime : 2017-03-21 11:20
         * username : 18571161290
         * realname : 又又
         * hotelName : MOMO酒店
         * phoneNumber : 18571161290
         * provinceId : 13
         * cityId : 193
         * hotelAddress : 樊城区人民路72号
         * isActive : 1
         * userType : 0
         * markCode : 8fa55eb0095111e7a85ecb189ce58509_18571161280
         * flag : 2
         * remarks :
         * role : 1
         * imageUrl :
         * verifyInfo : 审核通过!
         * bzlicenceUrl :
         * identificationCard :
         * identificationImage :
         * authStatus : 1
         * money : 0
         * subUserId : 5
         * orderNumber : {"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}
         */

        private int entityId;
        private String createTime;
        private String username;
        private String realname;
        private String hotelName;
        private String phoneNumber;
        private int provinceId;
        private int cityId;
        private String hotelAddress;
        private int isActive;
        private int userType;
        private String markCode;
        private int flag;
        private String remarks;
        private int role;
        private String imageUrl;
        private String verifyInfo;
        private String bzlicenceUrl;
        private String identificationCard;
        private String identificationImage;
        private int authStatus;
        private BigDecimal money;
        private int subUserId;
        private int hasVerfify;
        private OrderStatusCount orderNumber;
        private BigDecimal refundMoney;
        private BigDecimal rechargeMoney;
        private int pushMessageNotReadCount;  //获取通知数


        private String address;
        private String gender;
        private String frontageIdcardImageUrl;
        private String backIdcardImageUrl;
        private String birthday;
        private String expirationDate;
        private String isAuth;
        private int site;
        private String siteName;

        private int isInvoice;

        private int favoriteStoreCount;
        private int favoriteProdCount;
        private int couponItemsCount;
        private int integral;
        private int level;
        private int isSign;

        private String headUrl;
        private int imgCheckStatus;  //门店形象照审核状态  //审核状态：0-待审核，1-审核成功，2-审核失败
        private String imgRefuseInfo;  //驳回原因
        private int loginUserId;//第一次登陆ID
        private String alias;  //昵称
        private String verifyCode;
        private int communityId;
        private String commissionerTelephone;
        private String commissionerName;

        public String getCommissionerTelephone() {
            return commissionerTelephone;
        }

        public void setCommissionerTelephone(String commissionerTelephone) {
            this.commissionerTelephone = commissionerTelephone;
        }

        public String getCommissionerName() {
            return commissionerName;
        }

        public void setCommissionerName(String commissionerName) {
            this.commissionerName = commissionerName;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIsSign() {
            return isSign;
        }

        public void setIsSign(int isSign) {
            this.isSign = isSign;
        }

        public int getImgCheckStatus() {
            return imgCheckStatus;
        }

        public void setImgCheckStatus(int imgCheckStatus) {
            this.imgCheckStatus = imgCheckStatus;
        }

        public String getImgRefuseInfo() {
            return imgRefuseInfo;
        }

        public void setImgRefuseInfo(String imgRefuseInfo) {
            this.imgRefuseInfo = imgRefuseInfo;
        }

        public int getLoginUserId() {
            return loginUserId;
        }

        public void setLoginUserId(int loginUserId) {
            this.loginUserId = loginUserId;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
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

        public int getIsInvoice() {
            return isInvoice;
        }

        public void setIsInvoice(int isInvoice) {
            this.isInvoice = isInvoice;
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

        public BigDecimal getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(BigDecimal refundMoney) {
            this.refundMoney = refundMoney;
        }

        public BigDecimal getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(BigDecimal rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }


        public int getHasVerfify() {
            return hasVerfify;
        }

        public void setHasVerfify(int hasVerfify) {
            this.hasVerfify = hasVerfify;
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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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

        public String getHotelAddress() {
            return hotelAddress;
        }

        public void setHotelAddress(String hotelAddress) {
            this.hotelAddress = hotelAddress;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
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

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVerifyInfo() {
            return verifyInfo;
        }

        public void setVerifyInfo(String verifyInfo) {
            this.verifyInfo = verifyInfo;
        }

        public String getBzlicenceUrl() {
            return bzlicenceUrl;
        }

        public void setBzlicenceUrl(String bzlicenceUrl) {
            this.bzlicenceUrl = bzlicenceUrl;
        }

        public String getIdentificationCard() {
            return identificationCard;
        }

        public void setIdentificationCard(String identificationCard) {
            this.identificationCard = identificationCard;
        }

        public String getIdentificationImage() {
            return identificationImage;
        }

        public void setIdentificationImage(String identificationImage) {
            this.identificationImage = identificationImage;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public int getSubUserId() {
            return subUserId;
        }

        public void setSubUserId(int subUserId) {
            this.subUserId = subUserId;
        }

        public OrderStatusCount getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(OrderStatusCount orderNumber) {
            this.orderNumber = orderNumber;
        }

    }
}
