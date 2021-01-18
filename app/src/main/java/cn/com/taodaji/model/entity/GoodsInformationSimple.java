package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

public class GoodsInformationSimple implements Serializable {

    /**
     * entityId : 221
     * name : 朝天椒
     * commodityId : 304
     * commodityName : 红杭椒
     * nickName : 昵称
     * credentialsImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081832227c901639.jpeg
     * categoryId : 15
     * categoryName : 根茎类
     * updateTime : 1482480428000
     * gallery : ["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081832227c901639.jpeg"]
     * stock : 999926
     * unit : KG
     * price : 234
     * status : 2
     * description : default desc
     * store : 3
     * storeName : 周记蔬菜店
     * sku : xtb_1467973942988
     * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081832227c901639.jpeg
     * smallImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081832227c901639.jpeg
     * thumbnail : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1607081832227c901639.jpeg
     * parentCategoryId : 10
     */

    private int entityId;
    private String name;
    private int commodityId;
    private String commodityName;
    private String nickName;
    private String credentialsImage;
    private int categoryId;
    private String categoryName;
    private long updateTime;
    private int stock;
    private String unit;
    private float price;

    private int status;
    private String description;
    private int store;
    private String storeName;
    private String sku;
    private String image;
    private String smallImage;
    private String thumbnail;
    private int parentCategoryId;
    private List<String> gallery;

    private float memberPrice;

    public float getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(float memberPrice) {
        this.memberPrice = memberPrice;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCredentialsImage() {
        return credentialsImage;
    }

    public void setCredentialsImage(String credentialsImage) {
        this.credentialsImage = credentialsImage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }
}

