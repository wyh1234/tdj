package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-06-22.
 */

public class EvaluationStatics {

    /**
     * err : 0
     * data : {"allEvaluateCount":4,"avgScore":4.5,"badEvaluateCount":0,"goodEvaluateCount":3,"hasImgEvaluateCount":1,"mediumEvaluateCount":1}
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
         * allEvaluateCount : 4
         * avgScore : 4.5
         * badEvaluateCount : 0
         * goodEvaluateCount : 3
         * hasImgEvaluateCount : 1
         * mediumEvaluateCount : 1
         */

        private int allEvaluateCount;
        private BigDecimal avgScore;
        private int badEvaluateCount;
        private int goodEvaluateCount;
        private int hasImgEvaluateCount;
        private int mediumEvaluateCount;

        public int getAllEvaluateCount() {
            return allEvaluateCount;
        }

        public void setAllEvaluateCount(int allEvaluateCount) {
            this.allEvaluateCount = allEvaluateCount;
        }

        public BigDecimal getAvgScore() {
            return avgScore;
        }

        public void setAvgScore(BigDecimal avgScore) {
            this.avgScore = avgScore;
        }

        public int getBadEvaluateCount() {
            return badEvaluateCount;
        }

        public void setBadEvaluateCount(int badEvaluateCount) {
            this.badEvaluateCount = badEvaluateCount;
        }

        public int getGoodEvaluateCount() {
            return goodEvaluateCount;
        }

        public void setGoodEvaluateCount(int goodEvaluateCount) {
            this.goodEvaluateCount = goodEvaluateCount;
        }

        public int getHasImgEvaluateCount() {
            return hasImgEvaluateCount;
        }

        public void setHasImgEvaluateCount(int hasImgEvaluateCount) {
            this.hasImgEvaluateCount = hasImgEvaluateCount;
        }

        public int getMediumEvaluateCount() {
            return mediumEvaluateCount;
        }

        public void setMediumEvaluateCount(int mediumEvaluateCount) {
            this.mediumEvaluateCount = mediumEvaluateCount;
        }
    }
}
