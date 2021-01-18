package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class NewCouponsChooseCouponList {
    /**
     * err : 0
     * data : {"unlimit":[{"entityId":3,"title":"dadada","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":20,"purchaseAmount":200,"couponDesc":"仅限新鲜蔬菜使用","createTime":"2017-09-07 10:30","receiveTime":"2017-09-07 10:30","couponId":2,"productIds":"1,2","categoryId":"10"}],"limit":[{"entityId":5,"title":"优惠大酬宾","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":10,"purchaseAmount":0,"couponDesc":"全场通用","createTime":"2017-09-07 10:41","receiveTime":"2017-09-07 10:41","couponId":1,"productIds":"368","categoryId":"-1"}],"disable":[{"entityId":4,"title":"11111","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":50,"purchaseAmount":300,"couponDesc":"仅限特色食材","createTime":"2017-09-07 10:30","receiveTime":"2017-09-07 10:30","couponId":3,"productIds":"","categoryId":"129"}],"disableCount":1,"usableCount":2}
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
         * unlimit : [{"entityId":3,"title":"dadada","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":20,"purchaseAmount":200,"couponDesc":"仅限新鲜蔬菜使用","createTime":"2017-09-07 10:30","receiveTime":"2017-09-07 10:30","couponId":2,"productIds":"1,2","categoryId":"10"}]
         * limit : [{"entityId":5,"title":"优惠大酬宾","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":10,"purchaseAmount":0,"couponDesc":"全场通用","createTime":"2017-09-07 10:41","receiveTime":"2017-09-07 10:41","couponId":1,"productIds":"368","categoryId":"-1"}]
         * disable : [{"entityId":4,"title":"11111","startTime":"2017-09-01 00:00","endTime":"2017-10-01 00:00","status":0,"amount":50,"purchaseAmount":300,"couponDesc":"仅限特色食材","createTime":"2017-09-07 10:30","receiveTime":"2017-09-07 10:30","couponId":3,"productIds":"","categoryId":"129"}]
         * disableCount : 1
         * usableCount : 2
         * <p>
         * * 说明：
         * unlimit 可使用代金券中的无限金额数组，
         * limit 可使用代金券中的限制金额的数组，
         * disable 不可用代金券列表，
         * disableCount 不可用代金券数量
         * usableCount 可用代金券数量
         */

        private int disableCount;
        private int usableCount;


        private ShopItemBean shop;
        private PlatformItemBean platform;

        public ShopItemBean getShop() {
            return shop;
        }

        public void setShop(ShopItemBean shop) {
            this.shop = shop;
        }

        public PlatformItemBean getPlatform() {
            return platform;
        }

        public void setPlatform(PlatformItemBean platform) {
            this.platform = platform;
        }

        public int getDisableCount() {
            return disableCount;
        }

        public void setDisableCount(int disableCount) {
            this.disableCount = disableCount;
        }

        public int getUsableCount() {
            return usableCount;
        }

        public void setUsableCount(int usableCount) {
            this.usableCount = usableCount;
        }

        public static  class  PlatformItemBean {
            private List<ItemBean> unlimit;
            private List<ItemBean> limit;
            private List<ItemBean> disable;

            public List<ItemBean> getUnlimit() {
                return unlimit;
            }

            public void setUnlimit(List<ItemBean> unlimit) {
                this.unlimit = unlimit;
            }

            public List<ItemBean> getLimit() {
                return limit;
            }

            public void setLimit(List<ItemBean> limit) {
                this.limit = limit;
            }

            public List<ItemBean> getDisable() {
                return disable;
            }

            public void setDisable(List<ItemBean> disable) {
                this.disable = disable;
            }

        }

        public static class ShopItemBean {
                private List<ItemBean> unlimit;
                private List<ItemBean> limit;
                private List<ItemBean> disable;

                public List<ItemBean> getUnlimit() {
                    return unlimit;
                }

                public void setUnlimit(List<ItemBean> unlimit) {
                    this.unlimit = unlimit;
                }

                public List<ItemBean> getLimit() {
                    return limit;
                }

                public void setLimit(List<ItemBean> limit) {
                    this.limit = limit;
                }

                public List<ItemBean> getDisable() {
                    return disable;
                }

                public void setDisable(List<ItemBean> disable) {
                    this.disable = disable;
                }

            }
                public static class ItemBean implements Serializable {
                    /**
                     * entityId : 3
                     * title : dadada
                     * startTime : 2017-09-01 00:00
                     * endTime : 2017-10-01 00:00
                     * status : 0
                     * amount : 20
                     * purchaseAmount : 200
                     * couponDesc : 仅限新鲜蔬菜使用
                     * createTime : 2017-09-07 10:30
                     * receiveTime : 2017-09-07 10:30
                     * couponId : 2
                     * productIds : 1,2
                     * categoryId : 10
                     * ----详情中：categoryId等于“-1”时表示全场通用
                     */

                    @OnlyField
                    private int entityId;
                    private String title;
                    private String startTime;
                    private String endTime;
                    private int status;
                    private BigDecimal amount;
                    private BigDecimal purchaseAmount;
                    private String couponDesc;
                    private String createTime;
                    private String receiveTime;
                    private int couponId;
                    private String productIds;
                    private String categoryId;//分类id
                    private boolean selected;
                    private String storeName;
                    private String logoImageUrl;
                    private int storeId;//淘券，还是店券
                    private String couponsProductIds;//商品id
                    private int type;//2是商品，1是商铺


                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public String getCouponsProductIds() {
                        return couponsProductIds;
                    }

                    public void setCouponsProductIds(String couponsProductIds) {
                        this.couponsProductIds = couponsProductIds;
                    }


                    public String getStoreName() {
                        return storeName;
                    }

                    public void setStoreName(String storeName) {
                        this.storeName = storeName;
                    }

                    public String getLogoImageUrl() {
                        return logoImageUrl;
                    }

                    public void setLogoImageUrl(String logoImageUrl) {
                        this.logoImageUrl = logoImageUrl;
                    }

                    public int getStoreId() {
                        return storeId;
                    }

                    public void setStoreId(int storeId) {
                        this.storeId = storeId;
                    }

                    public boolean getSelected() {
                        return selected;
                    }

                    public void setSelected(boolean selected) {
                        this.selected = selected;
                    }

                    public int getEntityId() {
                        return entityId;
                    }

                    public void setEntityId(int entityId) {
                        this.entityId = entityId;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getStartTime() {
                        return startTime;
                    }

                    public void setStartTime(String startTime) {
                        this.startTime = startTime;
                    }

                    public String getEndTime() {
                        return endTime;
                    }

                    public void setEndTime(String endTime) {
                        this.endTime = endTime;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public BigDecimal getAmount() {
                        return amount;
                    }

                    public void setAmount(BigDecimal amount) {
                        this.amount = amount;
                    }

                    public BigDecimal getPurchaseAmount() {
                        return purchaseAmount;
                    }

                    public void setPurchaseAmount(BigDecimal purchaseAmount) {
                        this.purchaseAmount = purchaseAmount;
                    }

                    public String getCouponDesc() {
                        return couponDesc;
                    }

                    public void setCouponDesc(String couponDesc) {
                        this.couponDesc = couponDesc;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getReceiveTime() {
                        return receiveTime;
                    }

                    public void setReceiveTime(String receiveTime) {
                        this.receiveTime = receiveTime;
                    }

                    public int getCouponId() {
                        return couponId;
                    }

                    public void setCouponId(int couponId) {
                        this.couponId = couponId;
                    }

                    public String getProductIds() {
                        return productIds;
                    }

                    public void setProductIds(String productIds) {
                        this.productIds = productIds;
                    }

                    public String getCategoryId() {
                        return categoryId;
                    }

                    public void setCategoryId(String categoryId) {
                        this.categoryId = categoryId;
                    }
                }


            }
            }
