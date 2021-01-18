package cn.com.taodaji.model.entity;

import java.util.List;

public class SupplySaleTypeBean {
    /**
     * err : 0
     * data : {"storeCategoryList":[{"categoryId":14,"categoryName":"水果","checkStatus":1,"createdAt":"2018-11-09 11:37:23.0","entityId":3,"isActive":1,"storeId":11,"updatedAt":"2018-11-15 15:09:17.0","websiteId":2},{"categoryId":129,"categoryName":"特色食材","checkStatus":1,"createdAt":"2018-11-16 10:03:47.0","entityId":4,"isActive":1,"storeId":11,"updatedAt":"2018-11-16 10:03:47.0","websiteId":2}],"total":2}
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
         * storeCategoryList : [{"categoryId":14,"categoryName":"水果","checkStatus":1,"createdAt":"2018-11-09 11:37:23.0","entityId":3,"isActive":1,"storeId":11,"updatedAt":"2018-11-15 15:09:17.0","websiteId":2},{"categoryId":129,"categoryName":"特色食材","checkStatus":1,"createdAt":"2018-11-16 10:03:47.0","entityId":4,"isActive":1,"storeId":11,"updatedAt":"2018-11-16 10:03:47.0","websiteId":2}]
         * total : 2
         */

        private int total;
        private List<StoreCategoryListBean> storeCategoryList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<StoreCategoryListBean> getStoreCategoryList() {
            return storeCategoryList;
        }

        public void setStoreCategoryList(List<StoreCategoryListBean> storeCategoryList) {
            this.storeCategoryList = storeCategoryList;
        }

        public static class StoreCategoryListBean {
            /**
             * categoryId : 14
             * categoryName : 水果
             * checkStatus : 1
             * createdAt : 2018-11-09 11:37:23.0
             * entityId : 3
             * isActive : 1
             * storeId : 11
             * updatedAt : 2018-11-15 15:09:17.0
             * websiteId : 2
             */

            private int categoryId;
            private String categoryName;
            private int checkStatus;
            private String createdAt;
            private int entityId;
            private int isActive;
            private int storeId;
            private String updatedAt;
            private int websiteId;

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

            public int getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(int checkStatus) {
                this.checkStatus = checkStatus;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getIsActive() {
                return isActive;
            }

            public void setIsActive(int isActive) {
                this.isActive = isActive;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int getWebsiteId() {
                return websiteId;
            }

            public void setWebsiteId(int websiteId) {
                this.websiteId = websiteId;
            }
        }
    }
}
