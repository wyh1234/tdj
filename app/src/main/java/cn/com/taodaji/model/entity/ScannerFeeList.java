package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class ScannerFeeList {

    /**
     * err : 0
     * msg : success
     * data : {"total":5,"ps":10,"currIndex":0,"startTime":"2019-12-20 10:10:10","endTime":"2019-12-25 10:10:10","storeId":2725,"items":[{"entityId":65,"qrCodeId":"9191223000050","type":4,"price":0.02,"createTime":1577088257000,"itemId":null,"inTime":1577088257000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":66,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088257000,"itemId":null,"inTime":1577088257000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":67,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088304000,"itemId":null,"inTime":1577088304000,"settingId":null,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":68,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088304000,"itemId":null,"inTime":1577088304000,"settingId":null,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":69,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577089566000,"itemId":null,"inTime":1577089566000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null}],"pn":1,"scannerType":1}
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
         * total : 5
         * ps : 10
         * currIndex : 0
         * startTime : 2019-12-20 10:10:10
         * endTime : 2019-12-25 10:10:10
         * storeId : 2725
         * items : [{"entityId":65,"qrCodeId":"9191223000050","type":4,"price":0.02,"createTime":1577088257000,"itemId":null,"inTime":1577088257000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":66,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088257000,"itemId":null,"inTime":1577088257000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":67,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088304000,"itemId":null,"inTime":1577088304000,"settingId":null,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":68,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577088304000,"itemId":null,"inTime":1577088304000,"settingId":null,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null},{"entityId":69,"qrCodeId":"9191223000050","type":4,"price":0,"createTime":1577089566000,"itemId":null,"inTime":1577089566000,"settingId":15,"driverId":487,"storeId":2725,"status":1,"storeName":"汉口北销售店","storePhone":"13995566101","storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":"2019-12-24 14:49:29.0","productName":"黄瓜","productId":69419,"productPrice":1,"productUnit":"袋","productUnitType":"斤","lineCode":"E99区1","customerLineCode":"1","customerId":25652,"rowSize":null}]
         * pn : 1
         * scannerType : 1
         */

        private int total;
        private int ps;
        private int currIndex;
        private String startTime;
        private String endTime;
        private int storeId;
        private int pn;
        private int scannerType;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getCurrIndex() {
            return currIndex;
        }

        public void setCurrIndex(int currIndex) {
            this.currIndex = currIndex;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getScannerType() {
            return scannerType;
        }

        public void setScannerType(int scannerType) {
            this.scannerType = scannerType;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
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

            private int entityId;
            private String qrCodeId;
            private int type;
            private BigDecimal price;
            private long createTime;
            private Object itemId;
            private long inTime;
            private int settingId;
            private int driverId;
            private int storeId;
            private int status;
            private String storeName;
            private String storePhone;
            private Object storeUser;
            private Object storeNick;
            private String scannerHouse;
            private String updateTime;
            private String productName;
            private int productId;
            private double productPrice;
            private String productUnit;
            private String productUnitType;
            private String lineCode;
            private String customerLineCode;
            private int customerId;
            private Object rowSize;

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public String getQrCodeId() {
                return qrCodeId;
            }

            public void setQrCodeId(String qrCodeId) {
                this.qrCodeId = qrCodeId;
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

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public Object getItemId() {
                return itemId;
            }

            public void setItemId(Object itemId) {
                this.itemId = itemId;
            }

            public long getInTime() {
                return inTime;
            }

            public void setInTime(long inTime) {
                this.inTime = inTime;
            }

            public int getSettingId() {
                return settingId;
            }

            public void setSettingId(int settingId) {
                this.settingId = settingId;
            }

            public int getDriverId() {
                return driverId;
            }

            public void setDriverId(int driverId) {
                this.driverId = driverId;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getStorePhone() {
                return storePhone;
            }

            public void setStorePhone(String storePhone) {
                this.storePhone = storePhone;
            }

            public Object getStoreUser() {
                return storeUser;
            }

            public void setStoreUser(Object storeUser) {
                this.storeUser = storeUser;
            }

            public Object getStoreNick() {
                return storeNick;
            }

            public void setStoreNick(Object storeNick) {
                this.storeNick = storeNick;
            }

            public String getScannerHouse() {
                return scannerHouse;
            }

            public void setScannerHouse(String scannerHouse) {
                this.scannerHouse = scannerHouse;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductUnit() {
                return productUnit;
            }

            public void setProductUnit(String productUnit) {
                this.productUnit = productUnit;
            }

            public String getProductUnitType() {
                return productUnitType;
            }

            public void setProductUnitType(String productUnitType) {
                this.productUnitType = productUnitType;
            }

            public String getLineCode() {
                return lineCode;
            }

            public void setLineCode(String lineCode) {
                this.lineCode = lineCode;
            }

            public String getCustomerLineCode() {
                return customerLineCode;
            }

            public void setCustomerLineCode(String customerLineCode) {
                this.customerLineCode = customerLineCode;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public Object getRowSize() {
                return rowSize;
            }

            public void setRowSize(Object rowSize) {
                this.rowSize = rowSize;
            }
        }
    }
}
