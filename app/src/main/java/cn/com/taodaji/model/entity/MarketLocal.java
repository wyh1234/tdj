package cn.com.taodaji.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MarketLocal {

    private int err;
    private List<DataBean> data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
         * entityId : 1
         * createTime : 2016-07-08 17:31
         * updateTime : 2016-07-08 17:31
         * status : 1
         * name : 洪沟蔬菜批发市场
         * address : 洪沟蔬菜批发市场
         * provinceId : 13
         * provinceName : 湖北
         * cityId : 193
         * cityName : 襄阳
         * image : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3047022550,750796636&fm=23&gp=0.jpg
         * xPosition : 112.149783
         * yPosition : 32.076414
         * proccentId : 1
         */

        private int entityId;
        private String createTime;
        private String updateTime;
        private int status;
        private String name;
        private String address;
        private int provinceId;
        private String provinceName;
        private int cityId;
        private String cityName;
        private String image;
        @JsonProperty("xPosition")
        private double xPos;
        @JsonProperty("yPosition")
        private double yPos;
        private int proccentId;

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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getXPos() {
            return xPos;
        }

        public void setXPos(double xPosition) {
            this.xPos = xPosition;
        }

        public double getYPos() {
            return yPos;
        }

        public void setYPos(double yPosition) {
            this.yPos = yPosition;
        }

        public int getProccentId() {
            return proccentId;
        }

        public void setProccentId(int proccentId) {
            this.proccentId = proccentId;
        }
    }


}

