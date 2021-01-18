package cn.com.taodaji.model.entity;

import java.util.List;

public class DeliveryCode {


    /**
     * err : 0
     * data : {"list":[{"site":2,"lon":"112.240480","deliveryCode":"AJFNV2","communityShortName":"测试123","deliveryType":1,"street":"古驿镇","city":"","masterName":"","expectDeliveredEarliestTime":"08:00","area":"襄州区","address":"襄州区古驿镇","masterPhone":"","customerId":3787,"expectDeliveredLatestTime":"11:00","communityName":"测试","lat":"32.256910","deliveryStatus":1,"expectDeliveredDate":"05-14","communityId":1},{"site":2,"lon":"112.240480","deliveryCode":"SHKFY6","communityShortName":"测试123","deliveryType":2,"street":"古驿镇","city":"","masterName":"","expectDeliveredEarliestTime":"08:00","area":"襄州区","address":"襄州区古驿镇","masterPhone":"","customerId":3787,"expectDeliveredLatestTime":"11:00","communityName":"测试","lat":"32.256910","deliveryStatus":1,"expectDeliveredDate":"12-13","communityId":1}],"total":2}
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
         * list : [{"site":2,"lon":"112.240480","deliveryCode":"AJFNV2","communityShortName":"测试123","deliveryType":1,"street":"古驿镇","city":"","masterName":"","expectDeliveredEarliestTime":"08:00","area":"襄州区","address":"襄州区古驿镇","masterPhone":"","customerId":3787,"expectDeliveredLatestTime":"11:00","communityName":"测试","lat":"32.256910","deliveryStatus":1,"expectDeliveredDate":"05-14","communityId":1},{"site":2,"lon":"112.240480","deliveryCode":"SHKFY6","communityShortName":"测试123","deliveryType":2,"street":"古驿镇","city":"","masterName":"","expectDeliveredEarliestTime":"08:00","area":"襄州区","address":"襄州区古驿镇","masterPhone":"","customerId":3787,"expectDeliveredLatestTime":"11:00","communityName":"测试","lat":"32.256910","deliveryStatus":1,"expectDeliveredDate":"12-13","communityId":1}]
         * total : 2
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * site : 2
             * lon : 112.240480
             * deliveryCode : AJFNV2
             * communityShortName : 测试123
             * deliveryType : 1
             * street : 古驿镇
             * city :
             * masterName :
             * expectDeliveredEarliestTime : 08:00
             * area : 襄州区
             * address : 襄州区古驿镇
             * masterPhone :
             * customerId : 3787
             * expectDeliveredLatestTime : 11:00
             * communityName : 测试
             * lat : 32.256910
             * deliveryStatus : 1
             * expectDeliveredDate : 05-14
             * communityId : 1
             */

            private int site;
            private String lon;
            private String deliveryCode;
            private String communityShortName;
            private int deliveryType;
            private String street;
            private String city;
            private String masterName;
            private String expectDeliveredEarliestTime;
            private String area;
            private String address;
            private String masterPhone;
            private int customerId;
            private String expectDeliveredLatestTime;
            private String communityName;
            private String lat;
            private int deliveryStatus;
            private String expectDeliveredDate;
            private int communityId;

            public int getSite() {
                return site;
            }

            public void setSite(int site) {
                this.site = site;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getDeliveryCode() {
                return deliveryCode;
            }

            public void setDeliveryCode(String deliveryCode) {
                this.deliveryCode = deliveryCode;
            }

            public String getCommunityShortName() {
                return communityShortName;
            }

            public void setCommunityShortName(String communityShortName) {
                this.communityShortName = communityShortName;
            }

            public int getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(int deliveryType) {
                this.deliveryType = deliveryType;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getMasterName() {
                return masterName;
            }

            public void setMasterName(String masterName) {
                this.masterName = masterName;
            }

            public String getExpectDeliveredEarliestTime() {
                return expectDeliveredEarliestTime;
            }

            public void setExpectDeliveredEarliestTime(String expectDeliveredEarliestTime) {
                this.expectDeliveredEarliestTime = expectDeliveredEarliestTime;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMasterPhone() {
                return masterPhone;
            }

            public void setMasterPhone(String masterPhone) {
                this.masterPhone = masterPhone;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getExpectDeliveredLatestTime() {
                return expectDeliveredLatestTime;
            }

            public void setExpectDeliveredLatestTime(String expectDeliveredLatestTime) {
                this.expectDeliveredLatestTime = expectDeliveredLatestTime;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public int getDeliveryStatus() {
                return deliveryStatus;
            }

            public void setDeliveryStatus(int deliveryStatus) {
                this.deliveryStatus = deliveryStatus;
            }

            public String getExpectDeliveredDate() {
                return expectDeliveredDate;
            }

            public void setExpectDeliveredDate(String expectDeliveredDate) {
                this.expectDeliveredDate = expectDeliveredDate;
            }

            public int getCommunityId() {
                return communityId;
            }

            public void setCommunityId(int communityId) {
                this.communityId = communityId;
            }
        }
    }
}
