package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShopDetail  implements Serializable {

    /**
     * authStatus : 1
     * authStatusRemark : fdasdfadsf
     * closeStoreDatetime : 0
     * closeStoreEndDatetime : 0
     * code : jiaheshucai
     * companyAddress :
     * companyName :
     * contactEmail :
     * contactName :
     * contactPhone : 18656004515
     * createTime : 1467970649000
     * description : xiaotaobao
     * extraFields : {"foodQualiynoImageUrl":"","logoImageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16081909512779b76ab7.jpg"}
     * foodQualiynoImageUrl :
     * idcardImageUrl :
     * idcardNumber :
     * isActive : 1
     * licenceAddress :
     * licenceDocurl :
     * licenceExpiration :
     * licenceNo :
     * marketNo :
     * name : 周记蔬菜店
     * orgDocurl :
     * orgNo :
     * phone : 18656004515
     * realName : 淘大集
     * rootCategoryId : 32
     * site : 2
     * siteName : 淘大集
     * store : 3
     * storeStatus : 0
     * storeStatusRemark :
     * logoImageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16081909512779b76ab7.jpg
     * marketName : 洪沟蔬菜批发市场
     * marketAddress : 洪沟蔬菜批发市场
     */

    private int authStatus;
    private int storeType;
    private String authStatusRemark;
    private long closeStoreDatetime;
    private long closeStoreEndDatetime;
    private String code;
    private String companyAddress;
    private String companyName;
    private String contactEmail;
    private String contactName;
    private String contactPhone;
    private long createTime;
    private String description;
    private ExtraFieldsBean extraFields;
    private String foodQualiynoImageUrl;
    private String idcardImageUrl;
    private String idcardNumber;
    private int isActive;
    private String licenceAddress;
    private String licenceDocurl;
    private String licenceExpiration;
    private String licenceNo;
    private String marketNo;
    private String name;
    private String orgDocurl;
    private String orgNo;
    private String phone;
    private String realName;
    private int rootCategoryId;
    private int site;
    private String siteName;
    private int store;
    private int storeStatus;
    private String storeStatusRemark;
    private String logoImageUrl;
    private String marketName;
    private String marketAddress;
    private ProductsListBean productsList;
    private BigDecimal storeScore;
    private String qqNumber;
    private int orderNumber;
    private int productNumber;
    private int favoriteCount;
    private String mainCommodity;
    private boolean isFavor = false;

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getMainCommodity() {
        return mainCommodity;
    }

    public void setMainCommodity(String mainCommodity) {
        this.mainCommodity = mainCommodity;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public void setIsFavor(boolean favorite) {
        isFavor = favorite;
    }
    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public BigDecimal getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(BigDecimal storeScore) {
        this.storeScore = storeScore;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthStatusRemark() {
        return authStatusRemark;
    }

    public void setAuthStatusRemark(String authStatusRemark) {
        this.authStatusRemark = authStatusRemark;
    }

    public long getCloseStoreDatetime() {
        return closeStoreDatetime;
    }

    public void setCloseStoreDatetime(long closeStoreDatetime) {
        this.closeStoreDatetime = closeStoreDatetime;
    }

    public long getCloseStoreEndDatetime() {
        return closeStoreEndDatetime;
    }

    public void setCloseStoreEndDatetime(long closeStoreEndDatetime) {
        this.closeStoreEndDatetime = closeStoreEndDatetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExtraFieldsBean getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(ExtraFieldsBean extraFields) {
        this.extraFields = extraFields;
    }

    public String getFoodQualiynoImageUrl() {
        return foodQualiynoImageUrl;
    }

    public void setFoodQualiynoImageUrl(String foodQualiynoImageUrl) {
        this.foodQualiynoImageUrl = foodQualiynoImageUrl;
    }

    public String getIdcardImageUrl() {
        return idcardImageUrl;
    }

    public void setIdcardImageUrl(String idcardImageUrl) {
        this.idcardImageUrl = idcardImageUrl;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getLicenceAddress() {
        return licenceAddress;
    }

    public void setLicenceAddress(String licenceAddress) {
        this.licenceAddress = licenceAddress;
    }

    public String getLicenceDocurl() {
        return licenceDocurl;
    }

    public void setLicenceDocurl(String licenceDocurl) {
        this.licenceDocurl = licenceDocurl;
    }

    public String getLicenceExpiration() {
        return licenceExpiration;
    }

    public void setLicenceExpiration(String licenceExpiration) {
        this.licenceExpiration = licenceExpiration;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getMarketNo() {
        return marketNo;
    }

    public void setMarketNo(String marketNo) {
        this.marketNo = marketNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgDocurl() {
        return orgDocurl;
    }

    public void setOrgDocurl(String orgDocurl) {
        this.orgDocurl = orgDocurl;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(int rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(int storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getStoreStatusRemark() {
        return storeStatusRemark;
    }

    public void setStoreStatusRemark(String storeStatusRemark) {
        this.storeStatusRemark = storeStatusRemark;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public static class ExtraFieldsBean implements Serializable{
        /**
         * foodQualiynoImageUrl :
         * logoImageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16081909512779b76ab7.jpg
         */

        private String foodQualiynoImageUrl;
        private String logoImageUrl;

        public String getFoodQualiynoImageUrl() {
            return foodQualiynoImageUrl;
        }

        public void setFoodQualiynoImageUrl(String foodQualiynoImageUrl) {
            this.foodQualiynoImageUrl = foodQualiynoImageUrl;
        }

        public String getLogoImageUrl() {
            return logoImageUrl;
        }

        public void setLogoImageUrl(String logoImageUrl) {
            this.logoImageUrl = logoImageUrl;
        }
    }

    public ProductsListBean getProductsList() {
        return productsList;
    }

    public void setProductsList(ProductsListBean productsList) {
        this.productsList = productsList;
    }

    public static class ProductsListBean implements Serializable{
        /**
         * total : 12
         * items : [{"entityId":444,"createTime":1471943940000,"name":"冰糖","commodityId":156,"store":15,"storeName":"诚信粮油调料店","unit":"斤","price":4,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823171900dbdff95c.jpg","stock":999999,"qrCodeId":"","categoryId":"27"},{"entityId":437,"createTime":1471943486000,"name":"花椒粉","commodityId":95,"store":15,"storeName":"诚信粮油调料店","unit":"斤","price":46,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082317112697b09853.jpg","stock":999999,"qrCodeId":"","categoryId":"25"},{"entityId":436,"createTime":1471943447000,"name":"胡椒粉","commodityId":97,"store":15,"storeName":"诚信粮油调料店","unit":"斤","price":45,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608231710467c20c6de.jpg","stock":999992,"qrCodeId":"","categoryId":"25"},{"entityId":434,"createTime":1471943340000,"name":"鸡精","commodityId":157,"store":15,"storeName":"诚信粮油调料店","unit":"袋","price":36,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608231709000b94ad61.jpg","stock":999998,"qrCodeId":"","categoryId":"27"},{"entityId":441,"createTime":1471943668000,"name":"老抽","commodityId":122,"store":15,"storeName":"诚信粮油调料店","unit":"瓶","price":16,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823171428bd180927.jpg","stock":999999,"qrCodeId":"","categoryId":"26"},{"entityId":438,"createTime":1471943530000,"name":"皮蛋","commodityId":242,"store":15,"storeName":"诚信粮油调料店","unit":"斤","price":0.8,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823171209f67240ac.jpg","stock":999759,"qrCodeId":"0da620f05b9811e69776a0999b04e4c7","categoryId":"54"},{"entityId":440,"createTime":1471943635000,"name":"生抽","commodityId":116,"store":15,"storeName":"诚信粮油调料店","unit":"壶","price":13,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823171355112922d4.jpg","stock":999999,"qrCodeId":"","categoryId":"26"},{"entityId":433,"createTime":1471943304000,"name":"十三香","commodityId":171,"store":15,"storeName":"诚信粮油调料店","unit":"盒","price":2.1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/16082317082423e02f8c.jpg","stock":999999,"qrCodeId":"","categoryId":"27"},{"entityId":435,"createTime":1471943377000,"name":"味精","commodityId":158,"store":15,"storeName":"诚信粮油调料店","unit":"袋","price":10,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160823170937277d57d6.jpg","stock":999999,"qrCodeId":"","categoryId":"27"},{"entityId":439,"createTime":1471943572000,"name":"咸蛋","commodityId":243,"store":15,"storeName":"诚信粮油调料店","unit":"斤","price":10,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1608231712528efbb450.jpg","stock":999999,"qrCodeId":"","categoryId":"54"}]
         * pn : 1
         * ps : 10
         */

        private int total;
        private int pn;
        private int ps;
        private List<GoodsInformation> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public List<GoodsInformation> getItems() {
            return items;
        }

        public void setItems(List<GoodsInformation> items) {
            this.items = items;
        }


    }
}

