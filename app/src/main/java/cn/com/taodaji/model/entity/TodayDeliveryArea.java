package cn.com.taodaji.model.entity;

import java.util.List;

public class TodayDeliveryArea {

    /**
     * err : 0
     * data : {"orderRegionToBeStoreds":[{"regionCollNo":"区域","regionId":-1,"regionNo":"区域"},{"regionCollNo":"C","regionId":91,"regionNo":"26"}],"total":1}
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
         * orderRegionToBeStoreds : [{"regionCollNo":"区域","regionId":-1,"regionNo":"区域"},{"regionCollNo":"C","regionId":91,"regionNo":"26"}]
         * total : 1
         */

        private int total;
        private List<OrderRegionToBeStoredsBean> orderRegionToBeStoreds;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OrderRegionToBeStoredsBean> getOrderRegionToBeStoreds() {
            return orderRegionToBeStoreds;
        }

        public void setOrderRegionToBeStoreds(List<OrderRegionToBeStoredsBean> orderRegionToBeStoreds) {
            this.orderRegionToBeStoreds = orderRegionToBeStoreds;
        }

        public static class OrderRegionToBeStoredsBean {
            /**
             * regionCollNo : 区域
             * regionId : -1
             * regionNo : 区域
             */

            private String regionCollNo;
            private int regionId;
            private String regionNo;
            private int regionCollectionId;

            public String getRegionCollNo() {
                return regionCollNo;
            }

            public void setRegionCollNo(String regionCollNo) {
                this.regionCollNo = regionCollNo;
            }

            public int getRegionId() {
                return regionId;
            }

            public void setRegionId(int regionId) {
                this.regionId = regionId;
            }

            public String getRegionNo() {
                return regionNo;
            }

            public void setRegionNo(String regionNo) {
                this.regionNo = regionNo;
            }

            public int getRegionCollectionId() {
                return regionCollectionId;
            }

            public void setRegionCollectionId(int regionCollectionId) {
                this.regionCollectionId = regionCollectionId;
            }
        }
    }
}
