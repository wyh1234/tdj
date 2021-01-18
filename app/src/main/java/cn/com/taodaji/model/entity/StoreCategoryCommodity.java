package cn.com.taodaji.model.entity;

import java.util.List;

public class StoreCategoryCommodity {

    /**
     * err : 0
     * data : {"list":[{"categoryName":"叶菜类","checkStatus":0,"checkStatusRemark":"","checker":"","commodityName":"芹菜","createTime":1577070257000,"entityId":2256,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"}],"total":1}
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
         * list : [{"categoryName":"叶菜类","checkStatus":0,"checkStatusRemark":"","checker":"","commodityName":"芹菜","createTime":1577070257000,"entityId":2256,"parentCategoryName":"新鲜蔬菜","phone":"13697118502","remark":"","role":1,"storeName":"李奎调料","username":"李奎"}]
         * total : 1
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * categoryName : 叶菜类
             * checkStatus : 0
             * checkStatusRemark :
             * checker :
             * commodityName : 芹菜
             * createTime : 1577070257000
             * entityId : 2256
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
