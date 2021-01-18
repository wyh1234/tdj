package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class RefreshPickupFee {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"automatic_renewal_fee":0,"is_automatic_renewal":0,"receive_money":0,"withdrawal_money":19641.41}
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
         * automatic_renewal_fee : 0
         * is_automatic_renewal : 0
         * receive_money : 0
         * withdrawal_money : 19641.41
         */

        private BigDecimal automatic_renewal_fee;
        private int is_automatic_renewal;
        private BigDecimal receive_money;
        private BigDecimal withdrawal_money;

        public BigDecimal getAutomatic_renewal_fee() {
            return automatic_renewal_fee;
        }

        public void setAutomatic_renewal_fee(BigDecimal automatic_renewal_fee) {
            this.automatic_renewal_fee = automatic_renewal_fee;
        }

        public int getIs_automatic_renewal() {
            return is_automatic_renewal;
        }

        public void setIs_automatic_renewal(int is_automatic_renewal) {
            this.is_automatic_renewal = is_automatic_renewal;
        }

        public BigDecimal getReceive_money() {
            return receive_money;
        }

        public void setReceive_money(BigDecimal receive_money) {
            this.receive_money = receive_money;
        }

        public BigDecimal getWithdrawal_money() {
            return withdrawal_money;
        }

        public void setWithdrawal_money(BigDecimal withdrawal_money) {
            this.withdrawal_money = withdrawal_money;
        }
    }
}
