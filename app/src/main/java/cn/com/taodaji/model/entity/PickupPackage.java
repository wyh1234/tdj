package cn.com.taodaji.model.entity;

import java.util.List;

public class PickupPackage {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"total":3,"items":[{"entityId":4,"createTime":"2019-09-09 09:03:34","updateTime":"2019-09-12 16:29:19","websiteId":2,"rechargeAmount":100,"discountAmount":80,"discountRate":0.8,"packageTitle":"222","remarks":""},{"entityId":5,"createTime":"2019-09-09 10:28:35","updateTime":"2019-09-09 14:46:33","websiteId":2,"rechargeAmount":20000,"discountAmount":18000,"discountRate":0.9,"packageTitle":"sda ","remarks":""},{"entityId":6,"createTime":"2019-09-09 15:00:48","updateTime":null,"websiteId":2,"rechargeAmount":20000,"discountAmount":18000,"discountRate":0.9,"packageTitle":"sda ","remarks":""}]}
     */

    private int err;
    private String msg;
    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 3
         * items : [{"entityId":4,"createTime":"2019-09-09 09:03:34","updateTime":"2019-09-12 16:29:19","websiteId":2,"rechargeAmount":100,"discountAmount":80,"discountRate":0.8,"packageTitle":"222","remarks":""},{"entityId":5,"createTime":"2019-09-09 10:28:35","updateTime":"2019-09-09 14:46:33","websiteId":2,"rechargeAmount":20000,"discountAmount":18000,"discountRate":0.9,"packageTitle":"sda ","remarks":""},{"entityId":6,"createTime":"2019-09-09 15:00:48","updateTime":null,"websiteId":2,"rechargeAmount":20000,"discountAmount":18000,"discountRate":0.9,"packageTitle":"sda ","remarks":""}]
         */

        private int total;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * entityId : 4
             * createTime : 2019-09-09 09:03:34
             * updateTime : 2019-09-12 16:29:19
             * websiteId : 2
             * rechargeAmount : 100
             * discountAmount : 80
             * discountRate : 0.8
             * packageTitle : 222
             * remarks :
             */

            private int entityId;
            private String createTime;
            private String updateTime;
            private int websiteId;
            private int rechargeAmount;
            private int discountAmount;
            private double discountRate;
            private String packageTitle;
            private String remarks;

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getWebsiteId() {
                return websiteId;
            }

            public void setWebsiteId(int websiteId) {
                this.websiteId = websiteId;
            }

            public int getRechargeAmount() {
                return rechargeAmount;
            }

            public void setRechargeAmount(int rechargeAmount) {
                this.rechargeAmount = rechargeAmount;
            }

            public int getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(int discountAmount) {
                this.discountAmount = discountAmount;
            }

            public double getDiscountRate() {
                return discountRate;
            }

            public void setDiscountRate(double discountRate) {
                this.discountRate = discountRate;
            }

            public String getPackageTitle() {
                return packageTitle;
            }

            public void setPackageTitle(String packageTitle) {
                this.packageTitle = packageTitle;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }
    }
}
