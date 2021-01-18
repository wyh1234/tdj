package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class ScannerFeeListDetail {

    /**
     * err : 0
     * msg : success
     * data : {"entityId":65,"qrCodeId":"9191223000050","type":4,"price":0.02,"createTime":1577088257000,"itemId":null,"inTime":1577088257000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null}
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
         * entityId : 65
         * qrCodeId : 9191223000050
         * type : 4
         * price : 0.02
         * createTime : 1577088257000
         * itemId : null
         * inTime : 1577088257000
         * settingId : 15
         * driverId : 487
         * storeId : 2725
         * status : 1
         * storeName : 汉口北销售店
         * storePhone : 13995566101
         * storeUser : null
         * storeNick : null
         * scannerHouse : 武泰闸配送中心
         * updateTime : 2019-12-24 14:49:29.0
         * productName : 黄瓜
         * productId : 69419
         * productPrice : 1.0
         * productUnit : 袋
         * productUnitType : 斤
         * lineCode : E99区1
         * customerLineCode : 1
         * customerId : 25652
         * rowSize : null
         */

        private int type;
        private BigDecimal price;
        private long createTime;
        private String scannerHouse;
        private String driverName;
        private String driverPhone;
        private OrderInfo orderInfo;

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getScannerHouse() {
            return scannerHouse;
        }

        public void setScannerHouse(String scannerHouse) {
            this.scannerHouse = scannerHouse;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public static class OrderInfo{
            private String productName;
            private String nickName;
            private String productImage;
            private int qty;
            private String avgUnit;
            private BigDecimal totalPrice;
            private String level2Unit;
            private int level2Value;
            private String level3Unit;
            private int level3Value;
            private int levelType;
            private BigDecimal  price;
            private String unit;
            private String sku;

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getLevel3Unit() {
                return level3Unit;
            }

            public void setLevel3Unit(String level3Unit) {
                this.level3Unit = level3Unit;
            }

            public int getLevel3Value() {
                return level3Value;
            }

            public void setLevel3Value(int level3Value) {
                this.level3Value = level3Value;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public String getLevel2Unit() {
                return level2Unit;
            }

            public void setLevel2Unit(String level2Unit) {
                this.level2Unit = level2Unit;
            }

            public int getLevel2Value() {
                return level2Value;
            }

            public void setLevel2Value(int level2Value) {
                this.level2Value = level2Value;
            }

            public int getLevelType() {
                return levelType;
            }

            public void setLevelType(int levelType) {
                this.levelType = levelType;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getAvgUnit() {
                return avgUnit;
            }

            public void setAvgUnit(String avgUnit) {
                this.avgUnit = avgUnit;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

        }

    }

}
