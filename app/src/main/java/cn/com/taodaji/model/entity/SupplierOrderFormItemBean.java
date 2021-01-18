package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class SupplierOrderFormItemBean {
    /**
     * err : 0
     * data : {"buyNumber":20,"createTime":"2018-09-10 11:47","totalCost":2.62,"subtotalCost":2.62,"commission":0,"actualTotalCost":22.62,"extOrderId":"6444763032059908096","items":[{"amount":1,"avgPrice":0.32,"avgUnit":"斤","description":"黄心矮帮白菜","discount":0.32,"discountAvgPrice":0.32,"discountPrice":0.32,"discountTotalPrice":0.32,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171007101727ad79d0bb.jpg","itemId":358197,"level2Unit":"","level2Value":-1,"level3Unit":"","level3Value":-1,"levelType":1,"name":"大白菜","nickName":"","orderId":224779,"price":0.32,"productId":3437,"qrCodeId":"9180910000053","realTotal":0,"returnedAmount":0,"sku":"xtb_1507342674605","specId":3173,"status":0,"storeId":194,"storeName":"薛氏蔬菜","totalPrice":0.32,"unit":"斤"},{"amount":1,"avgPrice":2.3,"avgUnit":"斤","description":"新鲜当日生产请5～5斤以上下单","discount":2.3,"discountAvgPrice":2.3,"discountPrice":2.3,"discountTotalPrice":2.3,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1703291435042e967c11.jpg","itemId":358198,"level2Unit":"","level2Value":-1,"level3Unit":"","level3Value":-1,"levelType":1,"name":"蒜米","nickName":"5-5斤以上批发","orderId":224780,"price":2.3,"productId":180,"qrCodeId":"9180910000060","realTotal":0,"returnedAmount":0,"sku":"xtb_1490769402611","specId":146,"status":0,"storeId":23,"storeName":"姐弟生姜大蒜批发","totalPrice":2.3,"unit":"斤"}]}
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
         * buyNumber : 20
         * createTime : 2018-09-10 11:47
         * totalCost : 2.62
         * subtotalCost : 2.62
         * commission : 0
         * actualTotalCost : 22.62
         * extOrderId : 6444763032059908096
         * items : [{"amount":1,"avgPrice":0.32,"avgUnit":"斤","description":"黄心矮帮白菜","discount":0.32,"discountAvgPrice":0.32,"discountPrice":0.32,"discountTotalPrice":0.32,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171007101727ad79d0bb.jpg","itemId":358197,"level2Unit":"","level2Value":-1,"level3Unit":"","level3Value":-1,"levelType":1,"name":"大白菜","nickName":"","orderId":224779,"price":0.32,"productId":3437,"qrCodeId":"9180910000053","realTotal":0,"returnedAmount":0,"sku":"xtb_1507342674605","specId":3173,"status":0,"storeId":194,"storeName":"薛氏蔬菜","totalPrice":0.32,"unit":"斤"},{"amount":1,"avgPrice":2.3,"avgUnit":"斤","description":"新鲜当日生产请5～5斤以上下单","discount":2.3,"discountAvgPrice":2.3,"discountPrice":2.3,"discountTotalPrice":2.3,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1703291435042e967c11.jpg","itemId":358198,"level2Unit":"","level2Value":-1,"level3Unit":"","level3Value":-1,"levelType":1,"name":"蒜米","nickName":"5-5斤以上批发","orderId":224780,"price":2.3,"productId":180,"qrCodeId":"9180910000060","realTotal":0,"returnedAmount":0,"sku":"xtb_1490769402611","specId":146,"status":0,"storeId":23,"storeName":"姐弟生姜大蒜批发","totalPrice":2.3,"unit":"斤"}]
         */

        private int buyNumber;//数量
        private String createTime;
        private BigDecimal totalCost;//订单金额
        private BigDecimal subtotalCost;
        private BigDecimal commission;//佣金
        private BigDecimal actualTotalCost;//加上运费后的金额
        private String extOrderId;//订单编号
        private List<OrderDetail.ItemsBean> items;

        public int getBuyNumber() {
            return buyNumber;
        }

        public void setBuyNumber(int buyNumber) {
            this.buyNumber = buyNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
        }

        public BigDecimal getSubtotalCost() {
            return subtotalCost;
        }

        public void setSubtotalCost(BigDecimal subtotalCost) {
            this.subtotalCost = subtotalCost;
        }

        public BigDecimal getCommission() {
            return commission;
        }

        public void setCommission(BigDecimal commission) {
            this.commission = commission;
        }

        public BigDecimal getActualTotalCost() {
            return actualTotalCost;
        }

        public void setActualTotalCost(BigDecimal actualTotalCost) {
            this.actualTotalCost = actualTotalCost;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public List<OrderDetail.ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<OrderDetail.ItemsBean> items) {
            this.items = items;
        }

    }
}
