package cn.com.taodaji.model.entity;

import java.util.List;

public class GmoEditStore {

    /**
     * err : 0
     * data : {"list":[{"customerEntityId":7763,"enterpriseCode":"tao1","flag":1,"parentCustomerEntityId":7763},{"customerEntityId":7764,"enterpriseCode":"tao2","flag":1,"parentCustomerEntityId":7764},{"customerEntityId":7772,"enterpriseCode":"tao5","flag":0,"parentCustomerEntityId":7772}]}
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
             * customerEntityId : 7763
             * enterpriseCode : tao1
             * flag : 1
             * parentCustomerEntityId : 7763
             */

            private int customerEntityId;
            private String enterpriseCode;
            private int flag;
            private int parentCustomerEntityId;

            public int getCustomerEntityId() {
                return customerEntityId;
            }

            public void setCustomerEntityId(int customerEntityId) {
                this.customerEntityId = customerEntityId;
            }

            public String getEnterpriseCode() {
                return enterpriseCode;
            }

            public void setEnterpriseCode(String enterpriseCode) {
                this.enterpriseCode = enterpriseCode;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getParentCustomerEntityId() {
                return parentCustomerEntityId;
            }

            public void setParentCustomerEntityId(int parentCustomerEntityId) {
                this.parentCustomerEntityId = parentCustomerEntityId;
            }
        }
    }
}
