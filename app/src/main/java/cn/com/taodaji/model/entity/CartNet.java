package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-05-19.
 */
public class CartNet {

    /**
     * err : 0
     * data : {"items":[{"alreadyPurchase":0,"status":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170327115138cdc1c294.jpg","specialId":8,"productType":1,"id":4,"allowPurchase":10,"unit":"袋","price":120,"stock":9999,"nickName":"东北大米","name":"东北长粒香米","authStatus":2},{"alreadyPurchase":0,"status":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170329164025bfa19d85.jpg","specialId":0,"productType":0,"id":189,"allowPurchase":0,"unit":"斤","price":0.4,"stock":99894,"nickName":"白萝卜","name":"萝卜","authStatus":2},{"alreadyPurchase":0,"status":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170330102928ab283331.jpg","specialId":0,"productType":0,"id":238,"allowPurchase":0,"unit":"斤","price":1.3,"stock":9817,"nickName":"","name":"黄瓜","authStatus":2}]}
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
        private List<ItemsBean> items;

        private String deleteSpecIds;

        public String getDeleteSpecIds() {
            return deleteSpecIds;
        }

        public void setDeleteSpecIds(String deleteSpecIds) {
            this.deleteSpecIds = deleteSpecIds;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * level2Unit : 斤
             * specialId : 0
             * level3Value : 0
             * productType : 0
             * allowPurchase : 0
             * id : 7816
             * levelType : 2
             * stock : 99933
             * nickName : 111把
             * name : 大葱
             * avgUnit : 斤
             * level2Value : 5
             * alreadyPurchase : 0
             * status : 1
             * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1706251324115baab1a6.jpg
             * level3Unit :
             * level1Unit : 袋
             * productId : 7816
             * isActive : 1
             * storeStatus : 0
             * price : 50
             * avgPrice : 10
             * salesNumber : 0
             * specId : 7544
             * storeAuthStatus : 1
             * authStatus : 2
             */
            private int specialId;


            private String level2Unit;
            private BigDecimal level3Value = BigDecimal.ZERO;
            private int levelType;
            private BigDecimal level2Value = BigDecimal.ZERO;
            private String level3Unit;
            private String level1Unit;

            private int productType;
            private int allowPurchase;
            private int alreadyPurchase;

            private int stock;
            private String nickName;
            private String name;
            private String avgUnit;


            private int status;
            private String image;

            private int productId;
            private int isActive;
            private int storeStatus;
            private BigDecimal price = BigDecimal.ZERO;
            private BigDecimal avgPrice = BigDecimal.ZERO;
            private int salesNumber;
            private int specId;
            private int storeAuthStatus;
            private int authStatus;

            private int siteId;


            private int isP;// 0-零售 1-批发
            private int categoryId;
            private int commodityId;
            private int productCriteria;//商品标准“1”：通货商品 “2”：精品商品 '


            private String packageName;  //包装名称
            private BigDecimal foregift = BigDecimal.ZERO; //押金费用(单个)


            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public BigDecimal getForegift() {
                return foregift;
            }

            public void setForegift(BigDecimal foregift) {
                this.foregift = foregift;
            }


            public int getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(int productCriteria) {
                this.productCriteria = productCriteria;
            }

            public int getIsP() {
                return isP;
            }

            public void setIsP(int isP) {
                this.isP = isP;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public int getSiteId() {
                return siteId;
            }

            public void setSiteId(int siteId) {
                this.siteId = siteId;
            }

            public String getLevel2Unit() {
                return level2Unit;
            }

            public void setLevel2Unit(String level2Unit) {
                this.level2Unit = level2Unit;
            }

            public int getSpecialId() {
                return specialId;
            }

            public void setSpecialId(int specialId) {
                this.specialId = specialId;
            }

            public BigDecimal getLevel3Value() {
                return level3Value;
            }

            public void setLevel3Value(BigDecimal level3Value) {
                this.level3Value = level3Value;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public int getAllowPurchase() {
                return allowPurchase;
            }

            public void setAllowPurchase(int allowPurchase) {
                this.allowPurchase = allowPurchase;
            }

            public int getLevelType() {
                return levelType;
            }

            public void setLevelType(int levelType) {
                this.levelType = levelType;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvgUnit() {
                return avgUnit;
            }

            public void setAvgUnit(String avgUnit) {
                this.avgUnit = avgUnit;
            }

            public BigDecimal getLevel2Value() {
                return level2Value;
            }

            public void setLevel2Value(BigDecimal level2Value) {
                this.level2Value = level2Value;
            }

            public int getAlreadyPurchase() {
                return alreadyPurchase;
            }

            public void setAlreadyPurchase(int alreadyPurchase) {
                this.alreadyPurchase = alreadyPurchase;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLevel3Unit() {
                return level3Unit;
            }

            public void setLevel3Unit(String level3Unit) {
                this.level3Unit = level3Unit;
            }

            public String getLevel1Unit() {
                return level1Unit;
            }

            public void setLevel1Unit(String level1Unit) {
                this.level1Unit = level1Unit;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getIsActive() {
                return isActive;
            }

            public void setIsActive(int isActive) {
                this.isActive = isActive;
            }

            public int getStoreStatus() {
                return storeStatus;
            }

            public void setStoreStatus(int storeStatus) {
                this.storeStatus = storeStatus;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public BigDecimal getAvgPrice() {
                return avgPrice;
            }

            public void setAvgPrice(BigDecimal avgPrice) {
                this.avgPrice = avgPrice;
            }

            public int getSalesNumber() {
                return salesNumber;
            }

            public void setSalesNumber(int salesNumber) {
                this.salesNumber = salesNumber;
            }

            public int getSpecId() {
                return specId;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public int getStoreAuthStatus() {
                return storeAuthStatus;
            }

            public void setStoreAuthStatus(int storeAuthStatus) {
                this.storeAuthStatus = storeAuthStatus;
            }

            public int getAuthStatus() {
                return authStatus;
            }

            public void setAuthStatus(int authStatus) {
                this.authStatus = authStatus;
            }
        }
    }
}
