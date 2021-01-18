package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-09-27.
 */

public class FreightParticulars {

    /**
     * err : 0
     * data : {"totalFraeight":15,"item":[{"categoryIds":"10,11,12,13,14,110,118","purchseAmount":200,"fee":15,"title":"生鲜配送","entityId":1,"platform":1,"website":null,"supplier":null}]}
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
         * totalFreight : 15
         * item : [{"categoryIds":"10,11,12,13,14,110,118","purchaseAmount":200,"fee":15,"title":"生鲜配送","entityId":1,"platform":1,"website":null,"supplier":null}]
         */

        private BigDecimal totalFreight;
        private List<ItemBean> item;
        private int isInvoice;

        public int getIsInvoice() {
            return isInvoice;
        }

        public void setIsInvoice(int isInvoice) {
            this.isInvoice = isInvoice;
        }
        public BigDecimal getTotalFreight() {
            return totalFreight;
        }

        public void setTotalFreight(BigDecimal totalFreight) {
            this.totalFreight = totalFreight;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * categoryIds : 10,11,12,13,14,110,118
             * purchaseAmount : 200
             * fee : 15
             * title : 生鲜配送
             * entityId : 1
             * platform : 1
             * website : null
             * supplier : null
             */

            private String categoryIds;
            private int purchaseAmount;
            private BigDecimal fee;
            private String title;
            private int entityId;
            private BigDecimal platform;
            private Object website;
            private Object supplier;



            public String getCategoryIds() {
                return categoryIds;
            }

            public void setCategoryIds(String categoryIds) {
                this.categoryIds = categoryIds;
            }

            public int getPurchaseAmount() {
                return purchaseAmount;
            }

            public void setPurchaseAmount(int purchaseAmount) {
                this.purchaseAmount = purchaseAmount;
            }

            public BigDecimal getFee() {
                return fee;
            }

            public void setFee(BigDecimal fee) {
                this.fee = fee;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public BigDecimal getPlatform() {
                return platform;
            }

            public void setPlatform(BigDecimal platform) {
                this.platform = platform;
            }

            public Object getWebsite() {
                return website;
            }

            public void setWebsite(Object website) {
                this.website = website;
            }

            public Object getSupplier() {
                return supplier;
            }

            public void setSupplier(Object supplier) {
                this.supplier = supplier;
            }
        }
    }
}
