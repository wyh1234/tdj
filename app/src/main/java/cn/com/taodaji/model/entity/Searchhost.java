package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

public class Searchhost implements Serializable {


    private int err;
    private List<DataBean> data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static class DataBean implements Serializable {
        /**
         * name : 白菜
         * hit_number : 16
         */

        private String name;
        private int hit_number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHit_number() {
            return hit_number;
        }

        public void setHit_number(int hit_number) {
            this.hit_number = hit_number;
        }
    }


}
