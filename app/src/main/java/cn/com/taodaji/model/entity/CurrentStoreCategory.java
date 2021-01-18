package cn.com.taodaji.model.entity;

import java.util.List;

public class CurrentStoreCategory {

    /**
     * err : 0
     * data : {"storeCategoryList":[{"categoryId":13,"categoryName":"调味","checkStatus":1,"commodityLimit":0,"createdAt":"2018-12-07 09:41:05.0","entityId":129,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":274,"categoryName":"酒水饮料","checkStatus":1,"commodityLimit":0,"createdAt":"2018-12-07 09:41:12.0","entityId":131,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":146,"categoryName":"酒店用品","checkStatus":1,"commodityLimit":0,"createdAt":"2019-02-09 21:20:23.0","entityId":746,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":11,"categoryName":"禽蛋肉类","checkStatus":1,"commodityLimit":0,"createdAt":"2019-02-09 21:20:45.0","entityId":747,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":10,"categoryName":"新鲜蔬菜","checkStatus":1,"commodityLimit":0,"createdAt":"2019-12-25 15:58:41.0","entityId":1855,"isActive":1,"storeId":12,"updatedAt":"2019-12-25 15:58:41.0","websiteId":2}],"total":5,"supplierSaleType":1,"storeCommodityList":[{"categoryName":"叶菜类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"大白菜","createTime":1577070257000,"entityId":2255,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"},{"categoryName":"叶菜类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"豆苗","createTime":1577070257000,"entityId":2257,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"},{"categoryName":"根茎类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"白萝卜","createTime":1577070257000,"entityId":2258,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"}]}
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
         * storeCategoryList : [{"categoryId":13,"categoryName":"调味","checkStatus":1,"commodityLimit":0,"createdAt":"2018-12-07 09:41:05.0","entityId":129,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":274,"categoryName":"酒水饮料","checkStatus":1,"commodityLimit":0,"createdAt":"2018-12-07 09:41:12.0","entityId":131,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":146,"categoryName":"酒店用品","checkStatus":1,"commodityLimit":0,"createdAt":"2019-02-09 21:20:23.0","entityId":746,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":11,"categoryName":"禽蛋肉类","checkStatus":1,"commodityLimit":0,"createdAt":"2019-02-09 21:20:45.0","entityId":747,"isActive":1,"storeId":12,"updatedAt":"2019-10-10 10:13:06.0","websiteId":2},{"categoryId":10,"categoryName":"新鲜蔬菜","checkStatus":1,"commodityLimit":0,"createdAt":"2019-12-25 15:58:41.0","entityId":1855,"isActive":1,"storeId":12,"updatedAt":"2019-12-25 15:58:41.0","websiteId":2}]
         * total : 5
         * supplierSaleType : 1
         * storeCommodityList : [{"categoryName":"叶菜类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"大白菜","createTime":1577070257000,"entityId":2255,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"},{"categoryName":"叶菜类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"豆苗","createTime":1577070257000,"entityId":2257,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"},{"categoryName":"根茎类","checkStatus":1,"checkStatusRemark":"审核通过!","checker":"超级管理员","commodityName":"白萝卜","createTime":1577070257000,"entityId":2258,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"}]
         */

        private int total;
        private int supplierSaleType;
        private List<StoreCategoryListBean> storeCategoryList;
        private List<StoreCommodityListBean> storeCommodityList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSupplierSaleType() {
            return supplierSaleType;
        }

        public void setSupplierSaleType(int supplierSaleType) {
            this.supplierSaleType = supplierSaleType;
        }

        public List<StoreCategoryListBean> getStoreCategoryList() {
            return storeCategoryList;
        }

        public void setStoreCategoryList(List<StoreCategoryListBean> storeCategoryList) {
            this.storeCategoryList = storeCategoryList;
        }

        public List<StoreCommodityListBean> getStoreCommodityList() {
            return storeCommodityList;
        }

        public void setStoreCommodityList(List<StoreCommodityListBean> storeCommodityList) {
            this.storeCommodityList = storeCommodityList;
        }

        public static class StoreCategoryListBean {
            /**
             * categoryId : 13
             * categoryName : 调味
             * checkStatus : 1
             * commodityLimit : 0
             * createdAt : 2018-12-07 09:41:05.0
             * entityId : 129
             * isActive : 1
             * storeId : 12
             * updatedAt : 2019-10-10 10:13:06.0
             * websiteId : 2
             */

            private int categoryId;
            private String categoryName;
            private int checkStatus;
            private int commodityLimit;
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

            public int getCommodityLimit() {
                return commodityLimit;
            }

            public void setCommodityLimit(int commodityLimit) {
                this.commodityLimit = commodityLimit;
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

        public static class StoreCommodityListBean {
            /**
             * categoryName : 叶菜类
             * checkStatus : 1
             * checkStatusRemark : 审核通过!
             * checker : 超级管理员
             * commodityName : 大白菜
             * createTime : 1577070257000
             * entityId : 2255
             * parentCategoryName : 新鲜蔬菜
             * phone : 13697118502
             * remark :
             * role : 1
             * storeName : 李奎调料
             * username : 李奎
             */

            private String categoryName;
            private int checkStatus;
            private String checkStatusRemark;
            private String checker;
            private String commodityName;
            private long createTime;
            private int entityId;
            private String parentCategoryName;
            private String phone;
            private String remark;
            private int role;
            private String storeName;
            private String username;

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

            public String getCheckStatusRemark() {
                return checkStatusRemark;
            }

            public void setCheckStatusRemark(String checkStatusRemark) {
                this.checkStatusRemark = checkStatusRemark;
            }

            public String getChecker() {
                return checker;
            }

            public void setChecker(String checker) {
                this.checker = checker;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public String getParentCategoryName() {
                return parentCategoryName;
            }

            public void setParentCategoryName(String parentCategoryName) {
                this.parentCategoryName = parentCategoryName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
