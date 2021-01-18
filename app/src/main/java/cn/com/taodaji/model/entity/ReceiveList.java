package cn.com.taodaji.model.entity;

import java.util.List;

public class ReceiveList {

    /**
     * err : 0
     * data : {"list":[{"totalCount":0,"wareHouse":{"receiveWarehouseId":1,"warehouseName":"接1仓12","phone":"3532326","receiveTimeStart":"3:00","address":"万达广场","warehouseShortName":"接1","stationId":1,"lng":112.133136,"receiveTimeEnd":"5:00","shortName":"竹1","stationName":"竹叶山","lat":32.062453}},{"totalCount":0,"wareHouse":{"receiveWarehouseId":1,"warehouseName":"接1仓12","phone":"3532326","receiveTimeStart":"3:00","address":"万达广场","warehouseShortName":"接1","stationId":2,"lng":112.133136,"receiveTimeEnd":"5:00","shortName":"汉1","stationName":"汉江路","lat":32.062453}}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * totalCount : 0
             * wareHouse : {"receiveWarehouseId":1,"warehouseName":"接1仓12","phone":"3532326","receiveTimeStart":"3:00","address":"万达广场","warehouseShortName":"接1","stationId":1,"lng":112.133136,"receiveTimeEnd":"5:00","shortName":"竹1","stationName":"竹叶山","lat":32.062453}
             */

            private int totalCount;
            private WareHouseBean wareHouse;

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public WareHouseBean getWareHouse() {
                return wareHouse;
            }

            public void setWareHouse(WareHouseBean wareHouse) {
                this.wareHouse = wareHouse;
            }

            public static class WareHouseBean {
                /**
                 * receiveWarehouseId : 1
                 * warehouseName : 接1仓12
                 * phone : 3532326
                 * receiveTimeStart : 3:00
                 * address : 万达广场
                 * warehouseShortName : 接1
                 * stationId : 1
                 * lng : 112.133136
                 * receiveTimeEnd : 5:00
                 * shortName : 竹1
                 * stationName : 竹叶山
                 * lat : 32.062453
                 */

                private int receiveWarehouseId;
                private String warehouseName;
                private String phone;
                private String receiveTimeStart;
                private String address;
                private String warehouseShortName;
                private int stationId;
                private double lng;
                private String receiveTimeEnd;
                private String shortName;
                private String stationName;
                private double lat;

                public int getReceiveWarehouseId() {
                    return receiveWarehouseId;
                }

                public void setReceiveWarehouseId(int receiveWarehouseId) {
                    this.receiveWarehouseId = receiveWarehouseId;
                }

                public String getWarehouseName() {
                    return warehouseName;
                }

                public void setWarehouseName(String warehouseName) {
                    this.warehouseName = warehouseName;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getReceiveTimeStart() {
                    return receiveTimeStart;
                }

                public void setReceiveTimeStart(String receiveTimeStart) {
                    this.receiveTimeStart = receiveTimeStart;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getWarehouseShortName() {
                    return warehouseShortName;
                }

                public void setWarehouseShortName(String warehouseShortName) {
                    this.warehouseShortName = warehouseShortName;
                }

                public int getStationId() {
                    return stationId;
                }

                public void setStationId(int stationId) {
                    this.stationId = stationId;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public String getReceiveTimeEnd() {
                    return receiveTimeEnd;
                }

                public void setReceiveTimeEnd(String receiveTimeEnd) {
                    this.receiveTimeEnd = receiveTimeEnd;
                }

                public String getShortName() {
                    return shortName;
                }

                public void setShortName(String shortName) {
                    this.shortName = shortName;
                }

                public String getStationName() {
                    return stationName;
                }

                public void setStationName(String stationName) {
                    this.stationName = stationName;
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }
            }
        }
    }
}
