package cn.com.taodaji.model.entity;

import java.util.List;

public class StationBean {

    /**
     * err : 0
     * data : {"list":[{"address":"汉江路配送中心","earlyBus":"5:00","lat":"34.085543","lon":"114.833908","normalBus":"6:00","shortName":"配A","stationId":2,"stationName":"汉江路","totalCount":0,"totalEarlyBusCount":0,"totalNormalBusCount":0},{"address":"大吕沟创投园","earlyBus":"1：00","lat":"41.483564","lon":"118.680566","normalBus":"2：00","shortName":"简1","stationId":10,"stationName":"创业园","totalCount":0,"totalEarlyBusCount":0,"totalNormalBusCount":0}]}
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
             * address : 汉江路配送中心
             * earlyBus : 5:00
             * lat : 34.085543
             * lon : 114.833908
             * normalBus : 6:00
             * shortName : 配A
             * stationId : 2
             * stationName : 汉江路
             * totalCount : 0
             * totalEarlyBusCount : 0
             * totalNormalBusCount : 0
             */

            private String address;
            private String earlyBus;
            private String lat;
            private String lon;
            private String normalBus;
            private String shortName;
            private int stationId;
            private String stationName;
            private int totalCount;
            private int totalEarlyBusCount;
            private int totalNormalBusCount;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getEarlyBus() {
                return earlyBus;
            }

            public void setEarlyBus(String earlyBus) {
                this.earlyBus = earlyBus;
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

            public String getNormalBus() {
                return normalBus;
            }

            public void setNormalBus(String normalBus) {
                this.normalBus = normalBus;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public int getStationId() {
                return stationId;
            }

            public void setStationId(int stationId) {
                this.stationId = stationId;
            }

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getTotalEarlyBusCount() {
                return totalEarlyBusCount;
            }

            public void setTotalEarlyBusCount(int totalEarlyBusCount) {
                this.totalEarlyBusCount = totalEarlyBusCount;
            }

            public int getTotalNormalBusCount() {
                return totalNormalBusCount;
            }

            public void setTotalNormalBusCount(int totalNormalBusCount) {
                this.totalNormalBusCount = totalNormalBusCount;
            }
        }
    }
}
