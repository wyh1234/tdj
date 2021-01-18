package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

/**
 * Created by Administrator on 2017-09-05.
 */

public class CouponsFindByUser {

    /**
     * err : 0
     * data : {"total":2,"items":[{"entityId":2,"title":"满1000使用","startTime":"2017-09-04 15:11","endTime":"2017-10-04 15:11","status":0,"amount":250,"
     * purchaseAmount":1000,"couponDesc":"仅适用新鲜蔬菜","createTime":"2017-09-04 15:11","receiveTime":"2017-09-04 15:09"},
     * {"entityId":1,"title":"满500使用","startTime":"2017-09-04 15:09","endTime":"2017-10-04 15:10","status":0,"amount":100,"purchaseAmount":500,"couponDesc":"全场通用","createTime
     * ":"2017-09-04 15:09","receiveTime":"2017-09-04 15:09"}],"pn":1,"ps":10}
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
         * total : 2
         * items : [{"entityId":2,"title":"满1000使用","startTime":"2017-09-04 15:11","endTime":"2017-10-04 15:11","status":0,"amount":250,"purchaseAmount":1000,"couponDesc":"仅适用新鲜蔬菜","createTime":"2017-09-04 15:11","receiveTime":"2017-09-04 15:09"},{"entityId":1,"title":"满500使用","startTime":"2017-09-04 15:09","endTime":"2017-10-04 15:10","status":0,"amount":100,"purchaseAmount":500,"couponDesc":"全场通用","createTime":"2017-09-04 15:09","receiveTime":"2017-09-04 15:09"}]
         * pn : 1
         * ps : 10
         */


        private int total;
        private int pn;
        private int ps;
        private List<ItemsBean> items;

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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * entityId : 2
             * title : 满1000使用
             * startTime : 2017-09-04 15:11
             * endTime : 2017-10-04 15:11
             * status : 0
             * amount : 250
             * purchaseAmount : 1000
             * couponDesc : 仅适用新鲜蔬菜
             * createTime : 2017-09-04 15:11
             * receiveTime : 2017-09-04 15:09
             */

            @OnlyField
            private int entityId;
            private String title;
            private String startTime;
            private String endTime;
            private int status;
            private BigDecimal amount = BigDecimal.ZERO;
            private BigDecimal purchaseAmount = BigDecimal.ZERO;
            private String couponDesc;
            private String createTime;
            private String receiveTime;
            private String storeName;
            private int canCollect;
            private String logoImageUrl;
            private int storeId;
            private String expireTime;
            private String expiryTimeUnit;
            private int type;
            private int productIds;

            public int getProductIds() {
                return productIds;
            }

            public void setProductIds(int productIds) {
                this.productIds = productIds;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getExpiryTimeUnit() {
                return expiryTimeUnit;
            }

            public void setExpiryTimeUnit(String expiryTimeUnit) {
                this.expiryTimeUnit = expiryTimeUnit;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getLogoImageUrl() {
                return logoImageUrl;
            }

            public void setLogoImageUrl(String logoImageUrl) {
                this.logoImageUrl = logoImageUrl;
            }

            public int getCanCollect() {
                return canCollect;
            }

            public void setCanCollect(int canCollect) {
                this.canCollect = canCollect;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
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
        }
    }
}
