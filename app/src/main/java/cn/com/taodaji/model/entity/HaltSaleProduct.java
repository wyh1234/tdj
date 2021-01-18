package cn.com.taodaji.model.entity;

import java.util.List;

public class HaltSaleProduct {

    /**
     * data : {"list":[{"afterSaleRate":20,"categoryName":"叶菜类","commissionerName":"","createTime":"2020-01-06 19:00:25.0","endTime":"2020-01-08 08:00:00.0","haltDay":2,"haltNum":1,"mergeCategoryName":"新鲜蔬菜-叶菜类","name":"大芹菜","nickName":"","operator":"","parentCategoryName":"新鲜蔬菜","productCriteria":2,"productId":69448,"rulerName":"规则26","saleDay":3,"status":0,"supplierName":"测试WL","supplierTel":"12345678009"}],"pn":1,"ps":20,"total":1}
     * err : 0
     * msg : 查询停售商品成功
     */

    private DataBean data;
    private int err;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * list : [{"afterSaleRate":20,"categoryName":"叶菜类","commissionerName":"","createTime":"2020-01-06 19:00:25.0","endTime":"2020-01-08 08:00:00.0","haltDay":2,"haltNum":1,"mergeCategoryName":"新鲜蔬菜-叶菜类","name":"大芹菜","nickName":"","operator":"","parentCategoryName":"新鲜蔬菜","productCriteria":2,"productId":69448,"rulerName":"规则26","saleDay":3,"status":0,"supplierName":"测试WL","supplierTel":"12345678009"}]
         * pn : 1
         * ps : 20
         * total : 1
         */

        private int pn;
        private int ps;
        private int total;
        private List<ListBean> list;

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
             * afterSaleRate : 20
             * categoryName : 叶菜类
             * commissionerName :
             * createTime : 2020-01-06 19:00:25.0
             * endTime : 2020-01-08 08:00:00.0
             * haltDay : 2
             * haltNum : 1
             * mergeCategoryName : 新鲜蔬菜-叶菜类
             * name : 大芹菜
             * nickName :
             * operator :
             * parentCategoryName : 新鲜蔬菜
             * productCriteria : 2
             * productId : 69448
             * rulerName : 规则26
             * saleDay : 3
             * status : 0
             * supplierName : 测试WL
             * supplierTel : 12345678009
             */

            private int afterSaleRate;
            private String categoryName;
            private String commissionerName;
            private String createTime;
            private String endTime;
            private int haltDay;
            private int haltNum;
            private String mergeCategoryName;
            private String name;
            private String nickName;
            private String operator;
            private String parentCategoryName;
            private int productCriteria;
            private int productId;
            private String rulerName;
            private int saleDay;
            private int status;
            private String supplierName;
            private String supplierTel;
            private String productName;

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getAfterSaleRate() {
                return afterSaleRate;
            }

            public void setAfterSaleRate(int afterSaleRate) {
                this.afterSaleRate = afterSaleRate;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getCommissionerName() {
                return commissionerName;
            }

            public void setCommissionerName(String commissionerName) {
                this.commissionerName = commissionerName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getHaltDay() {
                return haltDay;
            }

            public void setHaltDay(int haltDay) {
                this.haltDay = haltDay;
            }

            public int getHaltNum() {
                return haltNum;
            }

            public void setHaltNum(int haltNum) {
                this.haltNum = haltNum;
            }

            public String getMergeCategoryName() {
                return mergeCategoryName;
            }

            public void setMergeCategoryName(String mergeCategoryName) {
                this.mergeCategoryName = mergeCategoryName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getParentCategoryName() {
                return parentCategoryName;
            }

            public void setParentCategoryName(String parentCategoryName) {
                this.parentCategoryName = parentCategoryName;
            }

            public int getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(int productCriteria) {
                this.productCriteria = productCriteria;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getRulerName() {
                return rulerName;
            }

            public void setRulerName(String rulerName) {
                this.rulerName = rulerName;
            }

            public int getSaleDay() {
                return saleDay;
            }

            public void setSaleDay(int saleDay) {
                this.saleDay = saleDay;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
            }

            public String getSupplierTel() {
                return supplierTel;
            }

            public void setSupplierTel(String supplierTel) {
                this.supplierTel = supplierTel;
            }
        }
    }
}
