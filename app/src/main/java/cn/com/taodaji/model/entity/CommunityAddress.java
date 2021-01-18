package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class CommunityAddress {

    /**
     * err : 0
     * data : {"address":"东津世纪城营销中心","area":"樊城区","authStatus":0,"city":"","communityAbbreviation":"世纪-1期","communityId":4,"communityName":"东津世纪城观澜郡","createTime":"2020-02-15 10:51:51.0","customerId":-1,"entityId":258172,"invitationName":"","invitationPhone":"","lat":"32.081503","lon":"112.255457","markCode":"1d90aff04f9e11eaab0b5d72e4435ebf_13618618437","middlename":"真实姓名","nickName":"真实姓名","phone":"13618618437","province":"","remarks":"","street":"高新区高新技术产业园","streetNumber":"6-1008","telephone":"13618618437"}
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
         * address : 东津世纪城营销中心
         * area : 樊城区
         * authStatus : 0
         * city :
         * communityAbbreviation : 世纪-1期
         * communityId : 4
         * communityName : 东津世纪城观澜郡
         * createTime : 2020-02-15 10:51:51.0
         * customerId : -1
         * entityId : 258172
         * invitationName :
         * invitationPhone :
         * lat : 32.081503
         * lon : 112.255457
         * markCode : 1d90aff04f9e11eaab0b5d72e4435ebf_13618618437
         * middlename : 真实姓名
         * nickName : 真实姓名
         * phone : 13618618437
         * province :
         * remarks :
         * street : 高新区高新技术产业园
         * streetNumber : 6-1008
         * telephone : 13618618437
         */

        private String address;
        private String area;
        private int authStatus;
        private String city;
        private String communityAbbreviation;
        private int communityId;
        private String communityName;
        private String createTime;
        private int customerId;
        private int entityId;
        private String invitationName;
        private String invitationPhone;
        private String lat;
        private String lon;
        private String markCode;
        private String middlename;
        private String nickName;
        private String phone;
        private String province;
        private String remarks;
        private String street;
        private String streetNumber;
        private String telephone;
        private String  regionNumber;

        public String getRegionNumber() {
            return regionNumber;
        }

        public void setRegionNumber(String regionNumber) {
            this.regionNumber = regionNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCommunityAbbreviation() {
            return communityAbbreviation;
        }

        public void setCommunityAbbreviation(String communityAbbreviation) {
            this.communityAbbreviation = communityAbbreviation;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getInvitationName() {
            return invitationName;
        }

        public void setInvitationName(String invitationName) {
            this.invitationName = invitationName;
        }

        public String getInvitationPhone() {
            return invitationPhone;
        }

        public void setInvitationPhone(String invitationPhone) {
            this.invitationPhone = invitationPhone;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getMarkCode() {
            return markCode;
        }

        public void setMarkCode(String markCode) {
            this.markCode = markCode;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
