package cn.com.taodaji.model.entity;

public class ReceiveWarehouseItem {

    /**
     * totalCount : 0
     * wareHouse : {"receiveWarehouseId":1,"warehouseName":"接1仓12","phone":"3532326","receiveTimeStart":"3:00","address":"万达广场","warehouseShortName":"接1","stationId":2,"lng":112.133136,"receiveTimeEnd":"5:00","shortName":"汉1","stationName":"汉江路","lat":32.062453}
     */

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


        /**
         * receiveWarehouseId : 1
         * warehouseName : 接1仓12
         * phone : 3532326
         * receiveTimeStart : 3:00
         * address : 万达广场
         * warehouseShortName : 接1
         * stationId : 2
         * lng : 112.133136
         * receiveTimeEnd : 5:00
         * shortName : 汉1
         * stationName : 汉江路
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
