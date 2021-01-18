package cn.com.taodaji.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class GoodsReceiptAddressBean implements Serializable {
    /**
     * addressId : 20
     * isActive : 0
     * createTime : 2016-12-28 09:29
     * updateTime : 2016-12-28 09:29
     * consignee : 张峰奇
     * phoneNumber : 18789098765
     * provinceId : 13
     * provinceName : 湖北
     * cityId : 193
     * cityName : 襄阳
     * countyId : 1
     * countyName : 中国
     * hotelName : 天圣酒店
     * street : 胜利街
     * customerId : 29
     * gender : 0
     */

    private int addressId;
    private int isActive;
    private String createTime;
    private String updateTime;
    private String consignee;
    private String phoneNumber;
    private int provinceId;
    private String provinceName;
    private int cityId;
    private String cityName;
    private String siteName;
    private int countyId;
    private String countyName;
    private String hotelName;
    private String street;
    private int customerId;
    private int gender;
    private int isDefault;
    private String deliveredTime;
    private String deliveredTimeEnd;
    private String street_number="";

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public String getDeliveredTimeEnd() {
        return deliveredTimeEnd;
    }

    public void setDeliveredTimeEnd(String deliveredTimeEnd) {
        this.deliveredTimeEnd = deliveredTimeEnd;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
