package cn.com.taodaji.model.entity;

public class PrivilegesInfo {


    /**
     * err : 0
     * data : {"sotreType":2,"startTime":"2019-06-17","endTime":"2019-08-17","test1":"2019-06-17","test2":"2019-08-16T16:00:00.000Z"}
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
         * sotreType : 2
         * startTime : 2019-06-17
         * endTime : 2019-08-17
         * test1 : 2019-06-17
         * test2 : 2019-08-16T16:00:00.000Z
         */

        private int sotreType;
        private String startTime;
        private String endTime;
        private String test1;
        private String test2;

        public int getSotreType() {
            return sotreType;
        }

        public void setSotreType(int sotreType) {
            this.sotreType = sotreType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTest1() {
            return test1;
        }

        public void setTest1(String test1) {
            this.test1 = test1;
        }

        public String getTest2() {
            return test2;
        }

        public void setTest2(String test2) {
            this.test2 = test2;
        }
    }
}
