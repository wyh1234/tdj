package cn.com.taodaji.model.entity;

import java.util.List;

public class StandardList {

    /**
     * err : 0
     * data : {"criteriaList":[{"productCriteria":1,"productCriteriaName":"通货商品"},{"productCriteria":2,"productCriteriaName":"精品商品"}],"isShow":0}
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
         * criteriaList : [{"productCriteria":1,"productCriteriaName":"通货商品"},{"productCriteria":2,"productCriteriaName":"精品商品"}]
         * isShow : 0
         */

        private int isShow;
        private List<CriteriaListBean> criteriaList;

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public List<CriteriaListBean> getCriteriaList() {
            return criteriaList;
        }

        public void setCriteriaList(List<CriteriaListBean> criteriaList) {
            this.criteriaList = criteriaList;
        }

        public static class CriteriaListBean {
            /**
             * productCriteria : 1
             * productCriteriaName : 通货商品
             */

            private int productCriteria;
            private String productCriteriaName;

            public int getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(int productCriteria) {
                this.productCriteria = productCriteria;
            }

            public String getProductCriteriaName() {
                return productCriteriaName;
            }

            public void setProductCriteriaName(String productCriteriaName) {
                this.productCriteriaName = productCriteriaName;
            }
        }
    }
}
