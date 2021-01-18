package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2016/12/31 0031.
 */
public class SubAccount {
    /**
     * entityId : 40
     * createTime : 2016-12-31 18:43
     * username : 13986397101
     * realname : 杨阔
     * hotelName : 淘大集
     * phoneNumber : 13986397101
     * provinceId : 13
     * cityId : 193
     * hotelAddress : 南岗
     * isActive : 1
     * userType : 0
     * markCode : 1499db70cd6411e6bf0381235d0ef082_13986397100
     * flag : 2
     * remarks :
     * role : 1
     * imageUrl :
     * bzlicenceUrl :
     * identificationCard :
     * identificationImage :
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
    private String bzlicenceUrl;
    private String identificationCard;
    private String identificationImage;
    private int hasVerfify;//0未激活

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

}
