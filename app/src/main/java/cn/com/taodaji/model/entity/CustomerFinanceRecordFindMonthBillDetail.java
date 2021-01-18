package cn.com.taodaji.model.entity;


import java.math.BigDecimal;

public class CustomerFinanceRecordFindMonthBillDetail {

    /**
     * err : 0
     * data : {"item":{"afterAmount":0,"afterNums":0,"balanceAmount":663.88,"cashAmount":0,"couponAmount":0,"deliveryAmount":320,"orderEndDate":"11-06","orderNums":16,"orderStartDate":"11-01","packageAmount":0.1,"packageNums":1,"productAmount":343.78,"realPayAmount":663.88,"returenPackageAmount":0,"returnPackageNums":0,"taxAmount":0,"totalAmount":663.88}}
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
         * item : {"afterAmount":0,"afterNums":0,"balanceAmount":663.88,"cashAmount":0,"couponAmount":0,"deliveryAmount":320,"orderEndDate":"11-06","orderNums":16,"orderStartDate":"11-01","packageAmount":0.1,"packageNums":1,"productAmount":343.78,"realPayAmount":663.88,"returenPackageAmount":0,"returnPackageNums":0,"taxAmount":0,"totalAmount":663.88}
         */

        private ItemBean item;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * afterAmount : 0
             * afterNums : 0
             * balanceAmount : 663.88
             * cashAmount : 0
             * couponAmount : 0
             * deliveryAmount : 320
             * orderEndDate : 11-06
             * orderNums : 16
             * orderStartDate : 11-01
             * packageAmount : 0.1
             * packageNums : 1
             * productAmount : 343.78
             * realPayAmount : 663.88
             * returenPackageAmount : 0
             * returnPackageNums : 0
             * taxAmount : 0
             * totalAmount : 663.88
             */

            private BigDecimal afterAmount;
            private int afterNums;
            private BigDecimal balanceAmount;
            private BigDecimal cashAmount;
            private BigDecimal couponAmount;
            private BigDecimal deliveryAmount;
            private String orderEndDate;
            private int orderNums;
            private String orderStartDate;
            private BigDecimal packageAmount;
            private int packageNums;
            private BigDecimal productAmount;
            private BigDecimal realPayAmount;
            private BigDecimal returenPackageAmount;
            private int returnPackageNums;
            private BigDecimal taxAmount;
            private BigDecimal totalAmount;

            public BigDecimal getAfterAmount() {
                return afterAmount;
            }

            public void setAfterAmount(BigDecimal afterAmount) {
                this.afterAmount = afterAmount;
            }

            public int getAfterNums() {
                return afterNums;
            }

            public void setAfterNums(int afterNums) {
                this.afterNums = afterNums;
            }

            public BigDecimal getBalanceAmount() {
                return balanceAmount;
            }

            public void setBalanceAmount(BigDecimal balanceAmount) {
                this.balanceAmount = balanceAmount;
            }

            public BigDecimal getCashAmount() {
                return cashAmount;
            }

            public void setCashAmount(BigDecimal cashAmount) {
                this.cashAmount = cashAmount;
            }

            public BigDecimal getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(BigDecimal couponAmount) {
                this.couponAmount = couponAmount;
            }

            public BigDecimal getDeliveryAmount() {
                return deliveryAmount;
            }

            public void setDeliveryAmount(BigDecimal deliveryAmount) {
                this.deliveryAmount = deliveryAmount;
            }

            public String getOrderEndDate() {
                return orderEndDate;
            }

            public void setOrderEndDate(String orderEndDate) {
                this.orderEndDate = orderEndDate;
            }

            public int getOrderNums() {
                return orderNums;
            }

            public void setOrderNums(int orderNums) {
                this.orderNums = orderNums;
            }

            public String getOrderStartDate() {
                return orderStartDate;
            }

            public void setOrderStartDate(String orderStartDate) {
                this.orderStartDate = orderStartDate;
            }

            public BigDecimal getPackageAmount() {
                return packageAmount;
            }

            public void setPackageAmount(BigDecimal packageAmount) {
                this.packageAmount = packageAmount;
            }

            public int getPackageNums() {
                return packageNums;
            }

            public void setPackageNums(int packageNums) {
                this.packageNums = packageNums;
            }

            public BigDecimal getProductAmount() {
                return productAmount;
            }

            public void setProductAmount(BigDecimal productAmount) {
                this.productAmount = productAmount;
            }

            public BigDecimal getRealPayAmount() {
                return realPayAmount;
            }

            public void setRealPayAmount(BigDecimal realPayAmount) {
                this.realPayAmount = realPayAmount;
            }

            public BigDecimal getReturenPackageAmount() {
                return returenPackageAmount;
            }

            public void setReturenPackageAmount(BigDecimal returenPackageAmount) {
                this.returenPackageAmount = returenPackageAmount;
            }

            public int getReturnPackageNums() {
                return returnPackageNums;
            }

            public void setReturnPackageNums(int returnPackageNums) {
                this.returnPackageNums = returnPackageNums;
            }

            public BigDecimal getTaxAmount() {
                return taxAmount;
            }

            public void setTaxAmount(BigDecimal taxAmount) {
                this.taxAmount = taxAmount;
            }

            public BigDecimal getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(BigDecimal totalAmount) {
                this.totalAmount = totalAmount;
            }
        }
    }
}
