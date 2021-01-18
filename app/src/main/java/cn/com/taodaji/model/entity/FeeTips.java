package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class FeeTips {


    /**
     * err : 0
     * data : {"info":{"annalFeeStatus":2,"endTime":"2020-07-17 09:59:50","id":17,"isDiscount":0,"lessDay":395,"openCycle":13,"reminrType":0,"servStatus":2,"startTime":"2019-06-17 09:59:50","storeId":1473,"storeType":2}}
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
         * info : {"annalFeeStatus":2,"endTime":"2020-07-17 09:59:50","id":17,"isDiscount":0,"lessDay":395,"openCycle":13,"reminrType":0,"servStatus":2,"startTime":"2019-06-17 09:59:50","storeId":1473,"storeType":2}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }



        public static class InfoBean implements Serializable {
            /**
             * annalFeeStatus : 2
             * endTime : 2020-07-17 09:59:50
             * id : 17
             * isDiscount : 0
             * lessDay : 395
             * openCycle : 13
             * reminrType : 0
             * servStatus : 2
             * startTime : 2019-06-17 09:59:50
             * storeId : 1473
             * storeType : 2
             */

            private int annalFeeStatus;
            private String endTime;
            private int id;
            private int isDiscount;
            private int lessDay;
            private int openCycle;
            private int reminrType;
            private int servStatus;
            private String startTime;
            private int storeId;
            private int storeType;
            private int isSelected;

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }

            public int getAnnalFeeStatus() {
                return annalFeeStatus;
            }

            public void setAnnalFeeStatus(int annalFeeStatus) {
                this.annalFeeStatus = annalFeeStatus;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(int isDiscount) {
                this.isDiscount = isDiscount;
            }

            public int getLessDay() {
                return lessDay;
            }

            public void setLessDay(int lessDay) {
                this.lessDay = lessDay;
            }

            public int getOpenCycle() {
                return openCycle;
            }

            public void setOpenCycle(int openCycle) {
                this.openCycle = openCycle;
            }

            public int getReminrType() {
                return reminrType;
            }

            public void setReminrType(int reminrType) {
                this.reminrType = reminrType;
            }

            public int getServStatus() {
                return servStatus;
            }

            public void setServStatus(int servStatus) {
                this.servStatus = servStatus;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getStoreType() {
                return storeType;
            }

            public void setStoreType(int storeType) {
                this.storeType = storeType;
            }
            public String toString(){
                return "annalFeeStatus  "+annalFeeStatus+"   openCycle  "+openCycle+"    endTime  "+endTime+"   id  "+id+"  isDiscount  "+isDiscount+"  lessDay  "+lessDay+"  reminrType  "+reminrType
                        +"  servStatus  "+servStatus+"  startTime  "+startTime+"  storeId  "+storeId+"  storeType  "+storeType;
            }
        }
    }
}
