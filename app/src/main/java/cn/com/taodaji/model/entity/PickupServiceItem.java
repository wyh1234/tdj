package cn.com.taodaji.model.entity;

public class PickupServiceItem {
    private int flag;
    private int status;
    private String title;
    private String rate;
    private String goods;
    private String time;
    private String phone;
    private String address;
    private int receiveWarehouseId;
    private int stationId;
    private String feeByDay;
    private String openDate;
    private Double lat,lon;
    private int isApplyClose;
    private String applyTime;
    private String rejectTime;


    public PickupServiceItem(int flag, int status, String title, String rate, String goods, String time, String phone, String address) {
        this.flag = flag;
        this.status = status;
        this.title = title;
        this.rate = rate;
        this.goods = goods;
        this.time = time;
        this.phone = phone;
        this.address = address;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public PickupServiceItem() {
    }

    public int getReceiveWarehouseId() {
        return receiveWarehouseId;
    }

    public void setReceiveWarehouseId(int receiveWarehouseId) {
        this.receiveWarehouseId = receiveWarehouseId;
    }

    public String getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
