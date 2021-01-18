package cn.com.taodaji.model.entity;

import java.util.List;

public class RegisterSaleTypeBean {
    /**
     * err : 0
     * data : {"list":[{"categoryName":"新鲜蔬菜","categoryId":10},{"categoryName":"禽蛋肉类","categoryId":11},{"categoryName":"米面粮油","categoryId":12},{"categoryName":"调味","categoryId":13},{"categoryName":"水果","categoryId":14},{"categoryName":"水产海鲜","categoryId":122},{"categoryName":"特色食材","categoryId":129},{"categoryName":"酒店用品","categoryId":146},{"categoryName":"冻品食材","categoryId":188},{"categoryName":"酒水饮料","categoryId":274}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * categoryName : 新鲜蔬菜
             * categoryId : 10
             */

            private String categoryName;
            private int categoryId;
            private int  isApply;//0-可申请，1-不可申请
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

            public int getIsApply() {
                return isApply;
            }

            public void setIsApply(int isApply) {
                this.isApply = isApply;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }
        }
    }
}
