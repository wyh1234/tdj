package cn.com.taodaji.model.entity;

import java.util.List;

public class DeliverTime {

    /**
     * err : 0
     * data : {"timeList":[{"startTime":"10:00","time":"10:00--12:00","isDefault":"1","timeEntityId":1,"endTime":"12:00"},{"startTime":"12:00","time":"12:00--15:00","isDefault":"0","timeEntityId":2,"endTime":"15:00"}]}
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
        private List<TimeListBean> timeList;

        public List<TimeListBean> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<TimeListBean> timeList) {
            this.timeList = timeList;
        }

        public static class TimeListBean {
            /**
             * startTime : 10:00
             * time : 10:00--12:00
             * isDefault : 1
             * timeEntityId : 1
             * endTime : 12:00
             */

            private String startTime;
            private String time;
            private int isDefault;
            private int timeEntityId;
            private String endTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public int getTimeEntityId() {
                return timeEntityId;
            }

            public void setTimeEntityId(int timeEntityId) {
                this.timeEntityId = timeEntityId;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }
    }
}
