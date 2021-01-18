package cn.com.taodaji.model.entity;

import com.amap.api.services.core.LatLonPoint;

/**
 * Created by zhaowenlong on 2019/3/2.
 */
public class ShopAddressItem {
    private String id;
    private LatLonPoint point;
    private String title;
    private String snippet;
    private boolean current;
    private int authStatus;
    private int checked;
    private int shopId;
    private String account;
    private int distance;

    public ShopAddressItem() {
    }

    public ShopAddressItem(String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
    }

    public ShopAddressItem(String title, String snippet, boolean current) {
        this.title = title;
        this.snippet = snippet;
        this.current = current;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatLonPoint getPoint() {
        return point;
    }

    public void setPoint(LatLonPoint point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
