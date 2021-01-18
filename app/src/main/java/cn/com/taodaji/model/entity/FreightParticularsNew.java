package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-10-12.
 */

public class FreightParticularsNew {

    /**
     * err : 0
     * data : [{"fee":15,"title":"平台配送"},{"fee":15,"title":"平台配送"}]
     * msg : success
     */

    private int err;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fee : 15
         * title : 平台配送
         */

        private BigDecimal fee;
        private String title;

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
