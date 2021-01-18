package cn.com.taodaji.model.entity;

public class DeliveryWarehouseItem {

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
