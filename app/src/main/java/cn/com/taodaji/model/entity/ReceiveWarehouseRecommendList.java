package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class ReceiveWarehouseRecommendList {

    /**
     * err : 0
     * data : {"isStoreReceive":1,"list":[{"receiveTimeEnd":"5:00","warehouseNameAbbr":"接1","address":"万达广场","feePercent":"20%","receiveWarehouseId":1,"receiveTimeStart":"3:00","lon":112.133136,"warehouseName":"接1仓12","isOpen":1,"phone":"3532326","stationName":"竹叶山","shortName":"竹1","openTime":"2019-09-23 13:13:37","receiveType":1,"lat":32.062453,"stationId":1,"feeByDay":"20"},{"receiveTimeEnd":"5:00","warehouseNameAbbr":"接1","address":"万达广场","feePercent":"11%","receiveWarehouseId":1,"receiveTimeStart":"3:00","lon":112.133136,"warehouseName":"接1仓12","isOpen":1,"phone":"3532326","stationName":"汉江路","shortName":"汉1","openTime":"2019-09-19 13:21:40","receiveType":1,"lat":32.062453,"stationId":2,"feeByDay":"10"},{"receiveTimeEnd":"6:00","warehouseNameAbbr":"汉江创","address":"汉江创投产业园","feePercent":"10%","receiveWarehouseId":2,"receiveTimeStart":"2:00","lon":112.147125,"warehouseName":"汉江创投接货仓","isOpen":1,"phone":"3530988","stationName":"汉江创投","shortName":"汉1仓","openTime":"2019-09-23 11:30:59","receiveType":1,"lat":32.07056,"stationId":23,"feeByDay":"20"}],"receiveMoney":2290}
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
         * isStoreReceive : 1
         * list : [{"receiveTimeEnd":"5:00","warehouseNameAbbr":"接1","address":"万达广场","feePercent":"20%","receiveWarehouseId":1,"receiveTimeStart":"3:00","lon":112.133136,"warehouseName":"接1仓12","isOpen":1,"phone":"3532326","stationName":"竹叶山","shortName":"竹1","openTime":"2019-09-23 13:13:37","receiveType":1,"lat":32.062453,"stationId":1,"feeByDay":"20"},{"receiveTimeEnd":"5:00","warehouseNameAbbr":"接1","address":"万达广场","feePercent":"11%","receiveWarehouseId":1,"receiveTimeStart":"3:00","lon":112.133136,"warehouseName":"接1仓12","isOpen":1,"phone":"3532326","stationName":"汉江路","shortName":"汉1","openTime":"2019-09-19 13:21:40","receiveType":1,"lat":32.062453,"stationId":2,"feeByDay":"10"},{"receiveTimeEnd":"6:00","warehouseNameAbbr":"汉江创","address":"汉江创投产业园","feePercent":"10%","receiveWarehouseId":2,"receiveTimeStart":"2:00","lon":112.147125,"warehouseName":"汉江创投接货仓","isOpen":1,"phone":"3530988","stationName":"汉江创投","shortName":"汉1仓","openTime":"2019-09-23 11:30:59","receiveType":1,"lat":32.07056,"stationId":23,"feeByDay":"20"}]
         * receiveMoney : 2290
         */

        private int isStoreReceive;
        private BigDecimal receiveMoney;
        private List<ListBean> list;

        public int getIsStoreReceive() {
            return isStoreReceive;
        }

        public void setIsStoreReceive(int isStoreReceive) {
            this.isStoreReceive = isStoreReceive;
        }

        public BigDecimal getReceiveMoney() {
            return receiveMoney;
        }

        public void setReceiveMoney(BigDecimal receiveMoney) {
            this.receiveMoney = receiveMoney;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * receiveTimeEnd : 5:00
             * warehouseNameAbbr : 接1
             * address : 万达广场
             * feePercent : 20%
             * receiveWarehouseId : 1
             * receiveTimeStart : 3:00
             * lon : 112.133136
             * warehouseName : 接1仓12
             * isOpen : 1
             * phone : 3532326
             * stationName : 竹叶山
             * shortName : 竹1
             * openTime : 2019-09-23 13:13:37
             * receiveType : 1
             * lat : 32.062453
             * stationId : 1
             * feeByDay : 20
             */

            private String receiveTimeEnd;
            private String warehouseNameAbbr;
            private String address;
            private String feePercent;
            private int receiveWarehouseId;
            private String receiveTimeStart;
            private double lon;
            private String warehouseName;
            private int isOpen;
            private String phone;
            private String stationName;
            private String shortName;
            private String openTime;
            private int receiveType;
            private double lat;
            private int stationId;
            private String feeByDay;
            private int isApplyClose;
            private String applyTime;
            private String rejectTime;

            public String getRejectTime() {
                return rejectTime;
            }

            public void setRejectTime(String rejectTime) {
                this.rejectTime = rejectTime;
            }

            public String getReceiveTimeEnd() {
                return receiveTimeEnd;
            }

            public void setReceiveTimeEnd(String receiveTimeEnd) {
                this.receiveTimeEnd = receiveTimeEnd;
            }

            public String getWarehouseNameAbbr() {
                return warehouseNameAbbr;
            }

            public void setWarehouseNameAbbr(String warehouseNameAbbr) {
                this.warehouseNameAbbr = warehouseNameAbbr;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getFeePercent() {
                return feePercent;
            }

            public void setFeePercent(String feePercent) {
                this.feePercent = feePercent;
            }

            public int getReceiveWarehouseId() {
                return receiveWarehouseId;
            }

            public void setReceiveWarehouseId(int receiveWarehouseId) {
                this.receiveWarehouseId = receiveWarehouseId;
            }

            public String getReceiveTimeStart() {
                return receiveTimeStart;
            }

            public void setReceiveTimeStart(String receiveTimeStart) {
                this.receiveTimeStart = receiveTimeStart;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public String getWarehouseName() {
                return warehouseName;
            }

            public void setWarehouseName(String warehouseName) {
                this.warehouseName = warehouseName;
            }

            public int getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(int isOpen) {
                this.isOpen = isOpen;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public int getReceiveType() {
                return receiveType;
            }

            public void setReceiveType(int receiveType) {
                this.receiveType = receiveType;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public int getStationId() {
                return stationId;
            }

            public void setStationId(int stationId) {
                this.stationId = stationId;
            }

            public String getFeeByDay() {
                return feeByDay;
            }

            public void setFeeByDay(String feeByDay) {
                this.feeByDay = feeByDay;
            }

            public int getIsApplyClose() {
                return isApplyClose;
            }

            public void setIsApplyClose(int isApplyClose) {
                this.isApplyClose = isApplyClose;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }
        }
    }
}
