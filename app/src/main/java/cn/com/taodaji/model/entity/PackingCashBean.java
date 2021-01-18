package cn.com.taodaji.model.entity;

import com.orm.dsl.Ignore;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;

public class PackingCashBean {
    /**
     * err : 0
     * data : {"packList":[{"afterSaleId":0,"currentFee":10,"currentNum":2,"customerName":"淘大集 宁倩15071519512","foregift":5,"isP":0,"level1Unit":"斤","level2Unit":"","level2Value":"0.00","level3Unit":"","level3Value":"0.00","levelType":1,"nickName":"宜昌蜜桔","orderItemId":2634950,"orderStatus":"trade_finished","packOrderId":1,"packageFee":10,"packageImg":"","packageName":"泡沫箱","packageNum":2,"payTime":"2018-12-17 17:25:55","productId":27769,"productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18101816390276b14344.jpg","productName":"桔子","productPrice":"1.50","productQty":2,"productSpec":"","specId":67672,"storeName":"宋氏水果行"}],"applyMoney":0,"payMoney":10,"pn":0,"ps":0,"returnMoney":6,"total":0,"unReturnMoney":0}
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
         * packList : [{"afterSaleId":0,"currentFee":10,"currentNum":2,"customerName":"淘大集 宁倩15071519512","foregift":5,"isP":0,"level1Unit":"斤","level2Unit":"","level2Value":"0.00","level3Unit":"","level3Value":"0.00","levelType":1,"nickName":"宜昌蜜桔","orderItemId":2634950,"orderStatus":"trade_finished","packOrderId":1,"packageFee":10,"packageImg":"","packageName":"泡沫箱","packageNum":2,"payTime":"2018-12-17 17:25:55","productId":27769,"productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18101816390276b14344.jpg","productName":"桔子","productPrice":"1.50","productQty":2,"productSpec":"","specId":67672,"storeName":"宋氏水果行"}]
         * applyMoney : 0
         * payMoney : 10
         * pn : 0
         * ps : 0
         * returnMoney : 6
         * total : 0
         * unReturnMoney : 0
         */

        private BigDecimal applyMoney; //处理中总金额
        private BigDecimal payMoney; //已支付总金额
        private int pn;
        private int ps;
        private BigDecimal returnMoney;//已退总金额
        private int total;
        private BigDecimal unReturnMoney;//未退总金额
        private List<CartGoodsBean> packList;


        public BigDecimal getApplyMoney() {
            return applyMoney;
        }

        public void setApplyMoney(BigDecimal applyMoney) {
            this.applyMoney = applyMoney;
        }

        public BigDecimal getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(BigDecimal payMoney) {
            this.payMoney = payMoney;
        }

        public BigDecimal getReturnMoney() {
            return returnMoney;
        }

        public void setReturnMoney(BigDecimal returnMoney) {
            this.returnMoney = returnMoney;
        }

        public BigDecimal getUnReturnMoney() {
            return unReturnMoney;
        }

        public void setUnReturnMoney(BigDecimal unReturnMoney) {
            this.unReturnMoney = unReturnMoney;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }


        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }



        public List<CartGoodsBean> getPackList() {
            return packList;
        }

        public void setPackList(List<CartGoodsBean> packList) {
            this.packList = packList;
        }


    }
}
