package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by zhaowenlong on 2019/3/15.
 */
public class HotelList {

    /**
     * err : 0
     * data : {"list":[{"isActive":1,"customerId":7829,"enterpriseCode":"香喷喷","account":"15000000000","markCode":"25f531a06f9f11e9ae843b56bf2ad3fe_15000000000","authStatus":1,"enterpriseMsg":"汉江创投"},{"isActive":1,"customerId":7838,"enterpriseCode":"阿香米线","account":"15222222222","markCode":"1a75a5506fa711e9ae843b56bf2ad3fe_15222222222","authStatus":1,"enterpriseMsg":"春园东路汉江水云间小区5号楼"},{"isActive":1,"customerId":7896,"enterpriseCode":"香四方卤肉","account":"15444444444","markCode":"dea2c550715c11e985fa4397c3ac0c10_15444444444","authStatus":1,"enterpriseMsg":"清河北路1号"}]}
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
             * isActive : 1
             * customerId : 7829
             * enterpriseCode : 香喷喷
             * account : 15000000000
             * markCode : 25f531a06f9f11e9ae843b56bf2ad3fe_15000000000
             * authStatus : 1
             * enterpriseMsg : 汉江创投
             */

            private int isActive;
            private int customerId;
            private String enterpriseCode;
            private String account;
            private String markCode;
            private int authStatus;
            private String enterpriseMsg;

            public int getIsActive() {
                return isActive;
            }

            public void setIsActive(int isActive) {
                this.isActive = isActive;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getEnterpriseCode() {
                return enterpriseCode;
            }

            public void setEnterpriseCode(String enterpriseCode) {
                this.enterpriseCode = enterpriseCode;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getMarkCode() {
                return markCode;
            }

            public void setMarkCode(String markCode) {
                this.markCode = markCode;
            }

            public int getAuthStatus() {
                return authStatus;
            }

            public void setAuthStatus(int authStatus) {
                this.authStatus = authStatus;
            }

            public String getEnterpriseMsg() {
                return enterpriseMsg;
            }

            public void setEnterpriseMsg(String enterpriseMsg) {
                this.enterpriseMsg = enterpriseMsg;
            }
        }
    }
}
