package cn.com.taodaji.model.entity;

public class AdvOrderDetailParam {
    private String putTime;
    private int advertisementPackageId;
    private String productIds;
    private String userRemark;
    private String days;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPutTime() {
        return putTime;
    }

    public void setPutTime(String putTime) {
        this.putTime = putTime;
    }

    public int getAdvertisementPackageId() {
        return advertisementPackageId;
    }

    public void setAdvertisementPackageId(int advertisementPackageId) {
        this.advertisementPackageId = advertisementPackageId;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }
}
