package cn.com.taodaji.model.entity;

import java.util.List;

public class SupplierAnnalFeeInfo {


    /**
     * err : 0
     * data : {"disFlag":false,"list":[{"cityLevel":"3","openCycle":"3","specAmount":3600,"specDisAmount":3240,"vipAmount":9000,"vipDisAmount":8100},{"cityLevel":"3","openCycle":"6","specAmount":6600,"specDisAmount":5280,"vipAmount":16500,"vipDisAmount":13200},{"cityLevel":"3","openCycle":"12","specAmount":12000,"specDisAmount":8400,"vipAmount":30000,"vipDisAmount":21000}],"storeId":1315}
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
         * disFlag : false
         * list : [{"cityLevel":"3","openCycle":"3","specAmount":3600,"specDisAmount":3240,"vipAmount":9000,"vipDisAmount":8100},{"cityLevel":"3","openCycle":"6","specAmount":6600,"specDisAmount":5280,"vipAmount":16500,"vipDisAmount":13200},{"cityLevel":"3","openCycle":"12","specAmount":12000,"specDisAmount":8400,"vipAmount":30000,"vipDisAmount":21000}]
         * storeId : 1315
         */

        private boolean disFlag;
        private int storeId;
        private List<ListBean> list;

        public boolean isDisFlag() {
            return disFlag;
        }

        public void setDisFlag(boolean disFlag) {
            this.disFlag = disFlag;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * cityLevel : 3
             * openCycle : 3
             * specAmount : 3600
             * specDisAmount : 3240
             * vipAmount : 9000
             * vipDisAmount : 8100
             */

            private String cityLevel;
            private String openCycle;
            private double specAmount;
            private double specDisAmount;
            private double vipAmount;
            private double vipDisAmount;
            private boolean b;

            public boolean isB() {
                return b;
            }

            public void setB(boolean b) {
                this.b = b;
            }

            public String getCityLevel() {
                return cityLevel;
            }

            public void setCityLevel(String cityLevel) {
                this.cityLevel = cityLevel;
            }

            public String getOpenCycle() {
                return openCycle;
            }

            public void setOpenCycle(String openCycle) {
                this.openCycle = openCycle;
            }

            public double getSpecAmount() {
                return specAmount;
            }

            public void setSpecAmount(double specAmount) {
                this.specAmount = specAmount;
            }

            public double getSpecDisAmount() {
                return specDisAmount;
            }

            public void setSpecDisAmount(double specDisAmount) {
                this.specDisAmount = specDisAmount;
            }

            public double getVipAmount() {
                return vipAmount;
            }

            public void setVipAmount(double vipAmount) {
                this.vipAmount = vipAmount;
            }

            public double getVipDisAmount() {
                return vipDisAmount;
            }

            public void setVipDisAmount(double vipDisAmount) {
                this.vipDisAmount = vipDisAmount;
            }
        }
    }
}
