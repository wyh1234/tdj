package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class AddressInfo {

    /**
     * err : 0
     * data : {"id":251838,"userId":"11","province":null,"city":"2","area":null,"detailAddress":"汉江创投产业园测试新增11","status":1,"isDefault":1,"lon":"111.644015","lat":"32.392676","gid":"0f9af4f1-44a4-4bc0-8384-870474e2fa87","recevingPersion":"胡杰1","recevingMobile":"15271967861","updateTime":"2019-08-15 09:58:16","createTime":"2019-08-15 09:58:16"}
     * error : null
     * msg : Success
     * errorCode : null
     */

    private int err;
    private DataBean data;
    private Object error;
    private String msg;
    private Object errorCode;

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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 251838
         * userId : 11
         * province : null
         * city : 2
         * area : null
         * detailAddress : 汉江创投产业园测试新增11
         * status : 1
         * isDefault : 1
         * lon : 111.644015
         * lat : 32.392676
         * gid : 0f9af4f1-44a4-4bc0-8384-870474e2fa87
         * recevingPersion : 胡杰1
         * recevingMobile : 15271967861
         * updateTime : 2019-08-15 09:58:16
         * createTime : 2019-08-15 09:58:16
         */

        private int id;
        private String userId;
        private Object province;
        private String city;
        private Object area;
        private String detailAddress;
        private int status;
        private int isDefault;
        private String lon;
        private String lat;
        private String gid;
        private String recevingPersion;
        private String recevingMobile;
        private String updateTime;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getRecevingPersion() {
            return recevingPersion;
        }

        public void setRecevingPersion(String recevingPersion) {
            this.recevingPersion = recevingPersion;
        }

        public String getRecevingMobile() {
            return recevingMobile;
        }

        public void setRecevingMobile(String recevingMobile) {
            this.recevingMobile = recevingMobile;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
