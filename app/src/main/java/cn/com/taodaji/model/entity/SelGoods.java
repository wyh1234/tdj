package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SelGoods {


    /**
     * err : 0
     * data : {"total":1,"items":[{"productCriteria":"1","entityId":69374,"createTime":1564450706000,"updateTime":1564450706000,"name":"冰草","commodityId":1,"isP":0,"store":2717,"storeName":"测试店铺09","saleNum":0,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg","categoryId":"15","description":"","bannerImage":"","authStatus":2,"catalogCategoryName":"","categories":"15","nickName":"测试","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg"],"phone":"12345678909","realName":"测试09","verifyInfo":"","credentialsImage":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":0.01,"maxPrice":-1,"unit":"斤","specs":[{"specId":115555,"productId":69374,"salesNumber":0,"avgPrice":0.01,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":0.01,"levelType":1,"siteId":3,"avgUnit":"斤","stock":1000,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"","foregift":-1,"isF":null,"storeStation":-1,"storeType":-1}],"pn":1,"ps":10}
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
        /**
         * total : 1
         * items : [{"productCriteria":"1","entityId":69374,"createTime":1564450706000,"updateTime":1564450706000,"name":"冰草","commodityId":1,"isP":0,"store":2717,"storeName":"测试店铺09","saleNum":0,"specialPrice":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg","categoryId":"15","description":"","bannerImage":"","authStatus":2,"catalogCategoryName":"","categories":"15","nickName":"测试","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg"],"phone":"12345678909","realName":"测试09","verifyInfo":"","credentialsImage":"","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"minPrice":0.01,"maxPrice":-1,"unit":"斤","specs":[{"specId":115555,"productId":69374,"salesNumber":0,"avgPrice":0.01,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":0.01,"levelType":1,"siteId":3,"avgUnit":"斤","stock":1000,"level2Unit":""}],"monthSaleNumbers":0,"packageName":"","foregift":-1,"isF":null,"storeStation":-1,"storeType":-1}]
         * pn : 1
         * ps : 10
         */

        private int total;
        private int pn;
        private int ps;
        private ArrayList<ItemsBean> items;

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

        public ArrayList<ItemsBean> getItems() {
            return items;
        }

        public void setItems(ArrayList<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * productCriteria : 1
             * entityId : 69374
             * createTime : 1564450706000
             * updateTime : 1564450706000
             * name : 冰草
             * commodityId : 1
             * isP : 0
             * store : 2717
             * storeName : 测试店铺09
             * saleNum : 0
             * specialPrice : 0
             * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg
             * categoryId : 15
             * description :
             * bannerImage :
             * authStatus : 2
             * catalogCategoryName :
             * categories : 15
             * nickName : 测试
             * status : 1
             * gallery : ["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19073009381137241133.jpg"]
             * phone : 12345678909
             * realName : 测试09
             * verifyInfo :
             * credentialsImage :
             * productType : 0
             * specialId : 0
             * allowPurchase : 0
             * alreadyPurchase : 0
             * minPrice : 0.01
             * maxPrice : -1
             * unit : 斤
             * specs : [{"specId":115555,"productId":69374,"salesNumber":0,"avgPrice":0.01,"level3Value":0,"level3Unit":"","level1Unit":"斤","level2Value":0,"price":0.01,"levelType":1,"siteId":3,"avgUnit":"斤","stock":1000,"level2Unit":""}]
             * monthSaleNumbers : 0
             * packageName :
             * foregift : -1
             * isF : null
             * storeStation : -1
             * storeType : -1
             */

            private String storeName;
            private String packageName;
            private Object isF;
            private String image;
            private String nickName;
            private int entityId;
            private long updateTime;
            private String unit;
            private long createTime;
            private String phone;
            private BigDecimal minPrice;
            private String name;
            private BigDecimal maxPrice;
            private int status;
            private List<SpecsBean> specs;
            private boolean flag;
            private int productCriteria;

            public int getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(int productCriteria) {
                this.productCriteria = productCriteria;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public Object getIsF() {
                return isF;
            }

            public void setIsF(Object isF) {
                this.isF = isF;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public BigDecimal getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(BigDecimal minPrice) {
                this.minPrice = minPrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BigDecimal getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(BigDecimal maxPrice) {
                this.maxPrice = maxPrice;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<SpecsBean> getSpecs() {
                return specs;
            }

            public void setSpecs(List<SpecsBean> specs) {
                this.specs = specs;
            }

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public static class SpecsBean implements Serializable {
                /**
                 * specId : 115555
                 * productId : 69374
                 * salesNumber : 0
                 * avgPrice : 0.01
                 * level3Value : 0
                 * level3Unit :
                 * level1Unit : 斤
                 * level2Value : 0
                 * price : 0.01
                 * levelType : 1
                 * siteId : 3
                 * avgUnit : 斤
                 * stock : 1000
                 * level2Unit :
                 */

                private int level3Value;
                private String level3Unit;
                private double level2Value;
                private int levelType;
                private String level2Unit;

                public int getLevel3Value() {
                    return level3Value;
                }

                public void setLevel3Value(int level3Value) {
                    this.level3Value = level3Value;
                }

                public String getLevel3Unit() {
                    return level3Unit;
                }

                public void setLevel3Unit(String level3Unit) {
                    this.level3Unit = level3Unit;
                }

                public double getLevel2Value() {
                    return level2Value;
                }

                public void setLevel2Value(double level2Value) {
                    this.level2Value = level2Value;
                }

                public int getLevelType() {
                    return levelType;
                }

                public void setLevelType(int levelType) {
                    this.levelType = levelType;
                }

                public String getLevel2Unit() {
                    return level2Unit;
                }

                public void setLevel2Unit(String level2Unit) {
                    this.level2Unit = level2Unit;
                }
            }
        }
    }
}
