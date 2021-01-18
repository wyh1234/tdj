package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class IntegralOrder {
    private int err;
    private String msg;
    private IntegralOrderData data;

    public IntegralOrderData getData() {
        return data;
    }

    public void setData(IntegralOrderData data) {
        this.data = data;
    }

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
    public static class  IntegralOrderData implements Serializable{
        private String orderId;
        private String totalFee;
        private String total_amount;
        private String trade_status;
        private String trade_state_desc;
        private String remark;
        private String sub_msg;
        private String goodsName;
        private String payWay;

        public String getSub_msg() {
            return sub_msg;
        }

        public void setSub_msg(String sub_msg) {
            this.sub_msg = sub_msg;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(String trade_status) {
            this.trade_status = trade_status;
        }

        public String getTrade_state_desc() {
            return trade_state_desc;
        }

        public void setTrade_state_desc(String trade_state_desc) {
            this.trade_state_desc = trade_state_desc;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }
    }
}
