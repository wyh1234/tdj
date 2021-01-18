package cn.com.taodaji.model.sqlBean;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.math.BigDecimal;

import cn.com.taodaji.model.entity.OrderStatusCount;

public class LoginPurchaseBean extends SugarRecord {

    /**
     * entityId : 22
     * createTime : 2016-12-09 11:40
     * username : 19999999999
     * realname : 斩风
     * hotelName : 盛大V酒店
     * phoneNumber : 19999999999
     * provinceId : 13
     * cityId : 193
     * hotelAddress : 襄阳盛大酒店
     * isActive : 0
     * userType : 0
     * markCode : 2b19fcc0bdc111e684797fcaf10e7ce3_18727120758
     * flag : 1
     * remarks :
     * role : 0
     * "authStatus": "0 已经提交（待审核）,1 已认证（审核通过），2 驳回  （xxxxx?）",
     * imageUrl : http://www.sina.com/cc.jpg
     * verifyInfo :
     * bzlicenceUrl : http://www.sina.com/b.jpg
     * identificationCard : 429001198808088008
     * identificationImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16070818195024ba1967.jpeg,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16070818195024ba1967.jpeg
     * pushToken :
     * sourceType : ios
     */

    @Unique
    private int entityId;
    private String createTime;
    private String username;
    private String realname;
    private String hotelName;
    private int isC;
    private String phoneNumber;
    private int provinceId;
    private int cityId;
    private String hotelAddress;
    private int isActive;
    private int userType;
    private String markCode;
    private int flag;
    private String remarks;
//    private int role;//只区分主账号子账号
    private int authStatus;
    private String imageUrl;
    private String headUrl;
    private String verifyInfo;
    private int subUserId;
    private String bzlicenceUrl;
    private String identificationCard;
    private String identificationImage;
    private BigDecimal money = BigDecimal.ZERO;
    private String pushToken;
    private String sourceType;
    @Ignore
    private OrderStatusCount orderNumber;
    @Ignore//这个注解强调这个属性在表中不要创建对应的字段
    private int hasVerfify;//0未激活
    private String address;
    private String gender;
    private String frontageIdcardImageUrl;
    private String backIdcardImageUrl;
    private String birthday;
    private String expirationDate;
    private String isAuth;
    private int site;
    private String siteName;

    private int imgCheckStatus;  //门店形象照审核状态  //审核状态：0-待审核，1-审核成功，2-审核失败
    private String imgRefuseInfo;  //驳回原因
    private int loginUserId;//第一次登陆ID
    private String alias;  //昵称
    private String verifyCode;
    private int empRole;//所属门店角色
    private String fenceGid;
    private int communityId;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getFenceGid() {
        return fenceGid;
    }

    public void setFenceGid(String fenceGid) {
        this.fenceGid = fenceGid;
    }

    public int getIsC() {
        return isC;
    }

    public void setIsC(int isC) {
        this.isC = isC;
    }

    public int getEmpRole() {
        return empRole;
    }

    public void setEmpRole(int empRole) {
        this.empRole = empRole;
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

    public int getHasVerfify() {
        return hasVerfify;
    }

    public void setHasVerfify(int hasVerfify) {
        this.hasVerfify = hasVerfify;
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

/*    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }*/

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
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
