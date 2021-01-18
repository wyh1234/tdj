package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class GetUnitList {
    /**
     * 获取发布规格单位
     * err : 0
     * data : {"baseUnit":["斤","克","毫升"],"level1Unit":["斤","千克","箱","筐","盒","袋","个","壶","件","瓶","捆","条","桶"],"listUnit":["斤","克","毫升","千克","箱","筐","盒","袋","个","壶","件","瓶","捆","条","桶"]}
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
        private List<String> baseUnit;
        private List<String> level1Unit;
        private List<String> listUnit;

        public List<String> getBaseUnit() {
            return baseUnit;
        }

        public void setBaseUnit(List<String> baseUnit) {
            this.baseUnit = baseUnit;
        }

        public List<String> getLevel1Unit() {
            return level1Unit;
        }

        public void setLevel1Unit(List<String> level1Unit) {
            this.level1Unit = level1Unit;
        }

        public List<String> getListUnit() {
            return listUnit;
        }

        public void setListUnit(List<String> listUnit) {
            this.listUnit = listUnit;
        }
    }


}
