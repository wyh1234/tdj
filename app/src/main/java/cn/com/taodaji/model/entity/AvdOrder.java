package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AvdOrder implements Serializable {


    /**
     * err : 0
     * data : {"id":71,"payCount":596}
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

    public static class
    DataBean implements Serializable {
        /**
         * id : 71
         * payCount : 596
         */

        private int id;
        private BigDecimal payCount;
        private String orderCode;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BigDecimal getPayCount() {
            return payCount;
        }

        public void setPayCount(BigDecimal payCount) {
            this.payCount = payCount;
        }
    }
}
