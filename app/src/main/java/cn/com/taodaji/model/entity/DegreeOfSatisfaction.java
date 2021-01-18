package cn.com.taodaji.model.entity;

public class DegreeOfSatisfaction {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"isC":1,"degreeOfSatisfaction":"60%","isE":1}
     */

    private int err;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * isC : 1
         * degreeOfSatisfaction : 60%
         * isE : 1
         */

        private int isC;
        private String degreeOfSatisfaction;
        private int isE;

        public int getIsC() {
            return isC;
        }

        public void setIsC(int isC) {
            this.isC = isC;
        }

        public String getDegreeOfSatisfaction() {
            return degreeOfSatisfaction;
        }

        public void setDegreeOfSatisfaction(String degreeOfSatisfaction) {
            this.degreeOfSatisfaction = degreeOfSatisfaction;
        }

        public int getIsE() {
            return isE;
        }

        public void setIsE(int isE) {
            this.isE = isE;
        }
    }
}
