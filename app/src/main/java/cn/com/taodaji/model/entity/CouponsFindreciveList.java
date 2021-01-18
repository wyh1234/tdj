package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

public class CouponsFindreciveList {

    /**
     * err : 0
     * data : {"total":3,"items":[{"entityId":3,"title":"满1500使用","startTime":"2017-09-01 09:43","endTime":"2017-10-01 09:43","amount":400,"collecteLimit":3,"purchaseAmount":1500,"couponDesc":"满1500-400","alreadyCollect":1,"canCollect":2},{"entityId":2,"title":"满1000使用","startTime":"2017-09-01 09:43","endTime":"2017-10-01 09:43","amount":250,"collecteLimit":3,"purchaseAmount":1000,"couponDesc":"仅适用新鲜蔬菜","alreadyCollect":1,"canCollect":2},{"entityId":1,"title":"满500使用","startTime":"2017-09-01 16:02","endTime":"2017-09-30 16:02","amount":100,"collecteLimit":3,"purchaseAmount":500,"couponDesc":"全场通用","alreadyCollect":1,"canCollect":2}],"pn":1,"ps":10}
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
         * total : 3
         * items : [{"entityId":3,"title":"满1500使用","startTime":"2017-09-01 09:43","endTime":"2017-10-01 09:43","amount":400,"collecteLimit":3,"purchaseAmount":1500,"couponDesc":"满1500-400","alreadyCollect":1,"canCollect":2},{"entityId":2,"title":"满1000使用","startTime":"2017-09-01 09:43","endTime":"2017-10-01 09:43","amount":250,"collecteLimit":3,"purchaseAmount":1000,"couponDesc":"仅适用新鲜蔬菜","alreadyCollect":1,"canCollect":2},{"entityId":1,"title":"满500使用","startTime":"2017-09-01 16:02","endTime":"2017-09-30 16:02","amount":100,"collecteLimit":3,"purchaseAmount":500,"couponDesc":"全场通用","alreadyCollect":1,"canCollect":2}]
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
             * entityId : 3
             * title : 满1500使用
             * startTime : 2017-09-01 09:43
             * endTime : 2017-10-01 09:43
             * amount : 400
             * collecteLimit : 3
             * purchaseAmount : 1500
             * couponDesc : 满1500-400
             * alreadyCollect : 1
             * canCollect : 2
             *
             * 说明：
             amount代金券金额，
             purchaseAmount使用消费金额为0时无金额限制，
             collecteLimit限领张数，
             alreadyCollect已经领取张数，
             canCollect可领取张数
             */

            @OnlyField
            private int entityId;
            private String title;
            private String startTime;
            private String endTime;
            private BigDecimal amount;
            private int collecteLimit;
            private BigDecimal purchaseAmount;
            private String couponDesc;
            private int alreadyCollect;
            private int canCollect;
            private String storeName;
            private String logoImageUrl;
            private int storeId;

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
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

            public BigDecimal getAmount() {
                return amount;
            }

            public void setAmount(BigDecimal amount) {
                this.amount = amount;
            }

            public int getCollecteLimit() {
                return collecteLimit;
            }

            public void setCollecteLimit(int collecteLimit) {
                this.collecteLimit = collecteLimit;
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

            public int getAlreadyCollect() {
                return alreadyCollect;
            }

            public void setAlreadyCollect(int alreadyCollect) {
                this.alreadyCollect = alreadyCollect;
            }

            public int getCanCollect() {
                return canCollect;
            }

            public void setCanCollect(int canCollect) {
                this.canCollect = canCollect;
            }
        }
    }
}
