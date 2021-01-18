package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class TodayDeliverGoodsOrderBean {

    /**
     * err : 0
     * data : {"expectDeliveredDate":"2019-10-28","orderItems":[{"itemId":1112,"receiveWarehouseId":12,"receiveWarehouseName":"竹叶山接货仓","receiveWarehouseShortName":"竹1","productCriteria":"1","printStatus":1,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1812291027212885d6c4.jpg","shippingTel":"12345671001","shippingCity":"","totalPrice":0.1,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"开水器","level3Unit":"","level2Value":0.8,"price":0.1,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000190","productId":40552,"shippingName":"测试酒店001","nickName":"测试商品不发货，勿拍","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"袋","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"},{"shippingTel":"12345671001","shippingCity":"","totalPrice":0.1,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"使用二级","level3Unit":"","level2Value":10,"price":0.1,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000183","productId":36287,"shippingName":"测试酒店001","nickName":"测试商品不发货，勿拍","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"箱","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"},{"shippingTel":"12345671001","shippingCity":"","totalPrice":2,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"樟树港辣椒","level3Unit":"","level2Value":1,"price":2,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000206","productId":28961,"shippingName":"测试酒店001","nickName":"测试商品不发","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"筐","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"}]}
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
         * expectDeliveredDate : 2019-10-28
         * orderItems : [{"itemId":1112,"receiveWarehouseId":12,"receiveWarehouseName":"竹叶山接货仓","receiveWarehouseShortName":"竹1","productCriteria":"1","printStatus":1,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1812291027212885d6c4.jpg","shippingTel":"12345671001","shippingCity":"","totalPrice":0.1,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"开水器","level3Unit":"","level2Value":0.8,"price":0.1,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000190","productId":40552,"shippingName":"测试酒店001","nickName":"测试商品不发货，勿拍","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"袋","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"},{"shippingTel":"12345671001","shippingCity":"","totalPrice":0.1,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"使用二级","level3Unit":"","level2Value":10,"price":0.1,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000183","productId":36287,"shippingName":"测试酒店001","nickName":"测试商品不发货，勿拍","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"箱","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"},{"shippingTel":"12345671001","shippingCity":"","totalPrice":2,"payTime":"2019-10-22 13:42:29","shippingStreet":"双沟镇","customerLineCode":"C26区18-89","remark":"","productName":"樟树港辣椒","level3Unit":"","level2Value":1,"price":2,"levelType":2,"itemStatus":1,"storeName":"侦测店铺002","avgUnit":"斤","level2Unit":"斤","driverNo":"18号","qrCodeId":"9191022000206","productId":28961,"shippingName":"测试酒店001","nickName":"测试商品不发","level3Value":0,"stationShortName":"竹1","truckTime":"1","unit":"筐","qty":1,"shippingAddress":"_双沟镇","shippingHotel":"真*********"}]
         */

        private String expectDeliveredDate;
        private List<OrderItemsBean> orderItems;

        public String getExpectDeliveredDate() {
            return expectDeliveredDate;
        }

        public void setExpectDeliveredDate(String expectDeliveredDate) {
            this.expectDeliveredDate = expectDeliveredDate;
        }

        public List<OrderItemsBean> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsBean> orderItems) {
            this.orderItems = orderItems;
        }

        public static class OrderItemsBean {
            /**
             * itemId : 1112
             * receiveWarehouseId : 12
             * receiveWarehouseName : 竹叶山接货仓
             * receiveWarehouseShortName : 竹1
             * productCriteria : 1
             * printStatus : 1
             * productImg : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1812291027212885d6c4.jpg
             * shippingTel : 12345671001
             * shippingCity :
             * totalPrice : 0.1
             * payTime : 2019-10-22 13:42:29
             * shippingStreet : 双沟镇
             * customerLineCode : C26区18-89
             * remark :
             * productName : 开水器
             * level3Unit :
             * level2Value : 0.8
             * price : 0.1
             * levelType : 2
             * itemStatus : 1
             * storeName : 侦测店铺002
             * avgUnit : 斤
             * level2Unit : 斤
             * driverNo : 18号
             * qrCodeId : 9191022000190
             * productId : 40552
             * shippingName : 测试酒店001
             * nickName : 测试商品不发货，勿拍
             * level3Value : 0
             * stationShortName : 竹1
             * truckTime : 1
             * unit : 袋
             * qty : 1
             * shippingAddress : _双沟镇
             * shippingHotel : 真*********
             */

            private int itemId;
            private int receiveWarehouseId;
            private String receiveWarehouseName;
            private String receiveWarehouseShortName;
            private String productCriteria;
            private int printStatus;
            private String productImg;
            private String shippingTel;
            private String shippingCity;
            private BigDecimal totalPrice;
            private String payTime;
            private String shippingStreet;
            private String customerLineCode;
            private String remark;
            private String productName;
            private String level3Unit;
            private BigDecimal level2Value;
            private BigDecimal price;
            private int levelType;
            private int itemStatus;
            private String storeName;
            private String avgUnit;
            private String level2Unit;
            private String driverNo;
            private String qrCodeId;
            private int productId;
            private String shippingName;
            private String nickName;
            private BigDecimal level3Value;
            private String stationShortName;
            private String truckTime;
            private String unit;
            private int qty;
            private String shippingAddress;
            private String shippingHotel;
            private String customerName;
            private int isC;
            private String customerMobile;

            public String getCustomerMobile() {
                return customerMobile;
            }

            public void setCustomerMobile(String customerMobile) {
                this.customerMobile = customerMobile;
            }

            public int getIsC() {
                return isC;
            }

            public void setIsC(int isC) {
                this.isC = isC;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public int getReceiveWarehouseId() {
                return receiveWarehouseId;
            }

            public void setReceiveWarehouseId(int receiveWarehouseId) {
                this.receiveWarehouseId = receiveWarehouseId;
            }

            public String getReceiveWarehouseName() {
                return receiveWarehouseName;
            }

            public void setReceiveWarehouseName(String receiveWarehouseName) {
                this.receiveWarehouseName = receiveWarehouseName;
            }

            public String getReceiveWarehouseShortName() {
                return receiveWarehouseShortName;
            }

            public void setReceiveWarehouseShortName(String receiveWarehouseShortName) {
                this.receiveWarehouseShortName = receiveWarehouseShortName;
            }

            public String getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(String productCriteria) {
                this.productCriteria = productCriteria;
            }

            public int getPrintStatus() {
                return printStatus;
            }

            public void setPrintStatus(int printStatus) {
                this.printStatus = printStatus;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public String getShippingTel() {
                return shippingTel;
            }

            public void setShippingTel(String shippingTel) {
                this.shippingTel = shippingTel;
            }

            public String getShippingCity() {
                return shippingCity;
            }

            public void setShippingCity(String shippingCity) {
                this.shippingCity = shippingCity;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getShippingStreet() {
                return shippingStreet;
            }

            public void setShippingStreet(String shippingStreet) {
                this.shippingStreet = shippingStreet;
            }

            public String getCustomerLineCode() {
                return customerLineCode;
            }

            public void setCustomerLineCode(String customerLineCode) {
                this.customerLineCode = customerLineCode;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getLevel3Unit() {
                return level3Unit;
            }

            public void setLevel3Unit(String level3Unit) {
                this.level3Unit = level3Unit;
            }

            public BigDecimal getLevel2Value() {
                return level2Value;
            }

            public void setLevel2Value(BigDecimal level2Value) {
                this.level2Value = level2Value;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public int getLevelType() {
                return levelType;
            }

            public void setLevelType(int levelType) {
                this.levelType = levelType;
            }

            public int getItemStatus() {
                return itemStatus;
            }

            public void setItemStatus(int itemStatus) {
                this.itemStatus = itemStatus;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getAvgUnit() {
                return avgUnit;
            }

            public void setAvgUnit(String avgUnit) {
                this.avgUnit = avgUnit;
            }

            public String getLevel2Unit() {
                return level2Unit;
            }

            public void setLevel2Unit(String level2Unit) {
                this.level2Unit = level2Unit;
            }

            public String getDriverNo() {
                return driverNo;
            }

            public void setDriverNo(String driverNo) {
                this.driverNo = driverNo;
            }

            public String getQrCodeId() {
                return qrCodeId;
            }

            public void setQrCodeId(String qrCodeId) {
                this.qrCodeId = qrCodeId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getShippingName() {
                return shippingName;
            }

            public void setShippingName(String shippingName) {
                this.shippingName = shippingName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public BigDecimal getLevel3Value() {
                return level3Value;
            }

            public void setLevel3Value(BigDecimal level3Value) {
                this.level3Value = level3Value;
            }

            public String getStationShortName() {
                return stationShortName;
            }

            public void setStationShortName(String stationShortName) {
                this.stationShortName = stationShortName;
            }

            public String getTruckTime() {
                return truckTime;
            }

            public void setTruckTime(String truckTime) {
                this.truckTime = truckTime;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public String getShippingAddress() {
                return shippingAddress;
            }

            public void setShippingAddress(String shippingAddress) {
                this.shippingAddress = shippingAddress;
            }

            public String getShippingHotel() {
                return shippingHotel;
            }

            public void setShippingHotel(String shippingHotel) {
                this.shippingHotel = shippingHotel;
            }
        }
    }
}
