package cn.com.taodaji.model.entity;

import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


public class OrderDetail implements Serializable{


    /**
     * buyNumber : 0
     * createTime : 2017-02-27 09:29
     * customerId : 36
     * expectDeliveredDate : 2017-02-27
     * expectDeliveredEarliestTime : 16:30
     * expectDeliveredLatestTime : 18:00
     * extOrderId : 3600000029
     * history : [{"createdAt":"2017-02-27 09:29","status":"wait_buyer_pay"},{"createdAt":"2017-02-27 09:30","status":"wait_seller_confirm_goods"},{"createdAt":"2017-02-27 10:25","status":"wait_seller_send_goods"}]
     * itemCount : 4
     * items : [{"amount":10,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170112160531653442e7.jpg","itemId":133,"name":"油麦菜","nickName":"淘大集天气","orderId":80,"price":12,"productId":618,"qrCodeId":"","realTotal":120,"returnedAmount":0,"sku":"xtb_1484208374794","status":3,"totalPrice":120,"unit":"斤"},{"amount":3,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170113144112a6473600.jpg","itemId":134,"name":"广上海青","nickName":"淘打李","orderId":80,"price":2868,"productId":620,"qrCodeId":"","realTotal":8604,"returnedAmount":0,"sku":"xtb_1484289693440","status":3,"totalPrice":8604,"unit":"斤"},{"amount":10,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170113170319a198d848.jpg","itemId":135,"name":"紫贝菜","nickName":"一哈巴黎","orderId":80,"price":21,"productId":621,"qrCodeId":"","realTotal":210,"returnedAmount":0,"sku":"xtb_1484298225997","status":3,"totalPrice":210,"unit":"斤"},{"amount":5,"discount":0,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17011415393692a70e04.jpg","itemId":136,"name":"西生菜","nickName":"星期","orderId":80,"price":1115,"productId":624,"qrCodeId":"","realTotal":5575,"returnedAmount":0,"sku":"xtb_1484379610072","status":3,"totalPrice":5575,"unit":"斤"}]
     * orderId : 0
     * orderIds : 80
     * orderNo : 3500000001
     * paymentMethod : {"code":"online_payment","label":"在线支付"}
     * receiveAddr : {"address":"襄阳_七里河南岗写字楼","hotelName":"淘大集","name":"杨阔","postcode":"","telephone":"13986397100"}
     * shipCost : 0
     * statusCode : wait_seller_send_goods
     * storeinfo : {"siteId":2,"siteName":"淘大集","storeId":35,"storeName":"淘大集"}
     * subtotalCost : 14509
     * totalCost : 14509
     * updateTime : 2017-02-27 09:29
     * outTradeNo : 2a9026d0fc8c11e6863b0dc951515bef
     */

    private int buyNumber;
    private String createTime;
    private int customerAddrId;
    private int customerId;
    private String expectDeliveredDate;
    private String expectDeliveredEarliestTime;
    private String expectDeliveredLatestTime;
    private String extOrderId;
    private int itemCount;
    private int orderId;
    private String orderIds;
    private String orderNo;
    private String phone;
    private PaymentMethodBean paymentMethod;
    private ReceiveAddrBean receiveAddr;
    private int shipCost;
    private String statusCode;
    private StoreinfoBean storeinfo;
    private BigDecimal subtotalCost = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private String updateTime;
    private String outTradeNo;
    private List<HistoryBean> history;
    private List<ItemsBean> items;
    private String customerLineCode;
    private String customerLogo;
    private String customerName;
    private int isC;
    private String customerMobile;
    private String address;
    private int deliveryType;
    private String streetNumber;
    private String deliverAddress;
    private String communityShortName;
    private int tzServiceAmount;
    private String ccLat;
    private String ccLon;

    public String getCcLat() {
        return ccLat;
    }

    public void setCcLat(String ccLat) {
        this.ccLat = ccLat;
    }

    public String getCcLon() {
        return ccLon;
    }

    public void setCcLon(String ccLon) {
        this.ccLon = ccLon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTzServiceAmount() {
        return tzServiceAmount;
    }

    public void setTzServiceAmount(int tzServiceAmount) {
        this.tzServiceAmount = tzServiceAmount;
    }

    public String getCommunityShortName() {
        return communityShortName;
    }

    public void setCommunityShortName(String communityShortName) {
        this.communityShortName = communityShortName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

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

    private BigDecimal couponAmount = BigDecimal.ZERO;
    private BigDecimal actualTotalCost = BigDecimal.ZERO;

    private BigDecimal taxCost = BigDecimal.ZERO;

    public BigDecimal totalFreight = BigDecimal.ZERO;

    private BigDecimal orderForegift = BigDecimal.ZERO;
    private int originalorCustomerId;//创建订单ID

    public int getOriginalorCustomerId() {
        return originalorCustomerId;
    }

    public void setOriginalorCustomerId(int originalorCustomerId) {
        this.originalorCustomerId = originalorCustomerId;
    }


    public int getCustomerAddrId() {
        return customerAddrId;
    }

    public void setCustomerAddrId(int customerAddrId) {
        this.customerAddrId = customerAddrId;
    }

    public BigDecimal getOrderForegift() {
        return orderForegift;
    }

    public void setOrderForegift(BigDecimal orderForegift) {
        this.orderForegift = orderForegift;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public String getCustomerLineCode() {
        return customerLineCode;
    }

    public void setCustomerLineCode(String customerLineCode) {
        this.customerLineCode = customerLineCode;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }


    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getActualTotalCost() {
        return actualTotalCost;
    }

    public void setActualTotalCost(BigDecimal actualTotalCost) {
        this.actualTotalCost = actualTotalCost;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
    }


    public String getCustomerLogo() {
        return customerLogo;
    }

    public void setCustomerLogo(String customerLogo) {
        this.customerLogo = customerLogo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getExpectDeliveredDate() {
        return expectDeliveredDate;
    }

    public void setExpectDeliveredDate(String expectDeliveredDate) {
        this.expectDeliveredDate = expectDeliveredDate;
    }

    public String getExpectDeliveredEarliestTime() {
        return expectDeliveredEarliestTime;
    }

    public void setExpectDeliveredEarliestTime(String expectDeliveredEarliestTime) {
        this.expectDeliveredEarliestTime = expectDeliveredEarliestTime;
    }

    public String getExpectDeliveredLatestTime() {
        return expectDeliveredLatestTime;
    }

    public void setExpectDeliveredLatestTime(String expectDeliveredLatestTime) {
        this.expectDeliveredLatestTime = expectDeliveredLatestTime;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public void setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public PaymentMethodBean getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodBean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ReceiveAddrBean getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(ReceiveAddrBean receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public int getShipCost() {
        return shipCost;
    }

    public void setShipCost(int shipCost) {
        this.shipCost = shipCost;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public StoreinfoBean getStoreinfo() {
        return storeinfo;
    }

    public void setStoreinfo(StoreinfoBean storeinfo) {
        this.storeinfo = storeinfo;
    }

    public BigDecimal getSubtotalCost() {
        return subtotalCost;
    }

    public void setSubtotalCost(BigDecimal subtotalCost) {
        this.subtotalCost = subtotalCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class PaymentMethodBean implements Serializable{
        /**
         * code : online_payment
         * label : 在线支付
         */

        private String code;
        private String label;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public static class ReceiveAddrBean implements Serializable{
        /**
         * address : 襄阳_七里河南岗写字楼
         * hotelName : 淘大集
         * name : 杨阔
         * postcode :
         * telephone : 13986397100
         */

        private String address;
        private String hotelName;
        private String name;
        private String postcode;
        private String telephone;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }

    public static class StoreinfoBean implements Serializable{
        /**
         * siteId : 2
         * siteName : 淘大集
         * storeId : 35
         * storeName : 淘大集
         */

        private int siteId;
        private String siteName;
        private int storeId;
        private String storeName;

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }

    public static class HistoryBean implements Serializable{
        /**
         * createdAt : 2017-02-27 09:29
         * status : wait_buyer_pay
         */

        private String createdAt;
        private String status;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ItemsBean implements Serializable {
        /**
         * amount : 10
         * discount : 0
         * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170112160531653442e7.jpg
         * itemId : 133
         * name : 油麦菜
         * nickName : 淘大集天气
         * orderId : 80
         * price : 12
         * productId : 618
         * qrCodeId :
         * realTotal : 120
         * returnedAmount : 0
         * sku : xtb_1484208374794
         * status : 3
         * totalPrice : 120
         * unit : 斤
         */

        private String level2Unit;
        private String remark;
        private BigDecimal level3Value = BigDecimal.ZERO;
        private int levelType;
        private BigDecimal level2Value = BigDecimal.ZERO;
        private String level3Unit;

        private String avgUnit;
        private BigDecimal avgPrice = BigDecimal.ZERO;


        private BigDecimal amount;
        private BigDecimal discount;
        private String image;
        private int itemId;
        private String name;
        private String nickName;
        private int orderId;
        private BigDecimal price;
        private int productId;
        private String qrCodeId;
        private BigDecimal realTotal;
        private int returnedAmount;
        private int storeId;
        private String sku;
        private int status;
        private BigDecimal totalPrice;
        private String unit;
        private String storeName;
        private ReceiveAddrBean receiveAddr;
        //        private List<HistoryBean> history;
        private String customerLineCode;
        private int specId;
        private int isUseCoupon;//是否使用代金券
        private int printState;//0 未打印，1正在打印，2已打印，3打印错误

        private int isP;

        private int categoryId;
        private int commodityId;
        private int productCriteria;//商品标准“1”：通货商品 “2”：精品商品 '
        private int productType;

        private String stationShortName; // "家1",
        private String driverNo;
        private String driverTel;
        private String driverName;
        private int driverId;
        private String receiveWarehouseName;
        private String receiveWarehouseShortName;
        private String customerName;
        private int isC;
        private String customerMobile;

        public String getCustomerMobile() {
            return customerMobile;
        }

        public void setCustomerMobile(String customerMobile) {
            this.customerMobile = customerMobile;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public int getIsC() {
            return isC;
        }

        public void setIsC(int isC) {
            this.isC = isC;
        }

        public int getDriverId() {
            return driverId;
        }

        public void setDriverId(int driverId) {
            this.driverId = driverId;
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

        private String packageName;  //包装名称
        private BigDecimal foregift = BigDecimal.ZERO; //押金费用(单个)


        //订单列表押金使用
        @Ignore
        private BigDecimal orderForegift = BigDecimal.ZERO; //押金总金额
        @Ignore
        private int isForegift;// 是否收取押金 0-不收，1-收
        @Ignore
        private int packageStatus = -1;//押金状态  -1 订购，0 - 未退, 1-已退完
        @Ignore
        private int packageNum;  //押金订购数量
        @Ignore
        private BigDecimal packageFee = BigDecimal.ZERO;//订购或未退押金金额

        @Ignore
        private BigDecimal currentFee = BigDecimal.ZERO;
        @Ignore
        private int currentNum; // 未退/处理中/已退/已支付 押金数量

        @Ignore
        private int currentStatus;  //当前押金列表请求状态  0-未退，1-处理中，2-已退，3-已支付

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(int currentNum) {
            this.currentNum = currentNum;
        }

        public int getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(int currentStatus) {
            this.currentStatus = currentStatus;
        }

        public BigDecimal getCurrentFee() {
            return currentFee;
        }

        public void setCurrentFee(BigDecimal currentFee) {
            this.currentFee = currentFee;
        }

        private long packOrderId;  //订单押金关联id

        public long getPackOrderId() {
            return packOrderId;
        }

        public void setPackOrderId(long packOrderId) {
            this.packOrderId = packOrderId;
        }

        public BigDecimal getOrderForegift() {
            return orderForegift;
        }

        public void setOrderForegift(BigDecimal orderForegift) {
            this.orderForegift = orderForegift;
        }

        public String getStationShortName() {
            return stationShortName;
        }

        public void setStationShortName(String stationShortName) {
            this.stationShortName = stationShortName;
        }

        public String getDriverNo() {
            return driverNo;
        }

        public void setDriverNo(String driverNo) {
            this.driverNo = driverNo;
        }

        public String getDriverTel() {
            return driverTel;
        }

        public void setDriverTel(String driverTel) {
            this.driverTel = driverTel;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public int getIsForegift() {
            return isForegift;
        }

        public void setIsForegift(int isForegift) {
            this.isForegift = isForegift;
        }

        public int getPackageStatus() {
            return packageStatus;
        }

        public void setPackageStatus(int packageStatus) {
            this.packageStatus = packageStatus;
        }

        public int getPackageNum() {
            return packageNum;
        }

        public void setPackageNum(int packageNum) {
            this.packageNum = packageNum;
        }

        public BigDecimal getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(BigDecimal packageFee) {
            this.packageFee = packageFee;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public BigDecimal getForegift() {
            return foregift;
        }

        public void setForegift(BigDecimal foregift) {
            this.foregift = foregift;
        }


        public int getProductCriteria() {
            return productCriteria;
        }

        public void setProductCriteria(int productCriteria) {
            this.productCriteria = productCriteria;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getIsP() {
            return isP;
        }

        public void setIsP(int isP) {
            this.isP = isP;
        }

        public int getPrintState() {
            return printState;
        }

        public void setPrintState(int printState) {
            this.printState = printState;
        }

        public int getIsUseCoupon() {
            return isUseCoupon;
        }

        public void setIsUseCoupon(int isUseCoupon) {
            this.isUseCoupon = isUseCoupon;
        }

//        public List<HistoryBean> getHistory() {
//            return history;
//        }
//
//        public void setHistory(List<HistoryBean> history) {
//            this.history = history;
//        }

        public String getAvgUnit() {
            return avgUnit;
        }

        public void setAvgUnit(String avgUnit) {
            this.avgUnit = avgUnit;
        }

        public BigDecimal getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(BigDecimal avgPrice) {
            this.avgPrice = avgPrice;
        }

        public String getLevel2Unit() {
            return level2Unit;
        }

        public void setLevel2Unit(String level2Unit) {
            this.level2Unit = level2Unit;
        }

        public BigDecimal getLevel3Value() {
            return level3Value;
        }

        public void setLevel3Value(BigDecimal level3Value) {
            this.level3Value = level3Value;
        }

        public int getLevelType() {
            return levelType;
        }

        public void setLevelType(int levelType) {
            this.levelType = levelType;
        }

        public BigDecimal getLevel2Value() {
            return level2Value;
        }

        public void setLevel2Value(BigDecimal level2Value) {
            this.level2Value = level2Value;
        }

        public String getLevel3Unit() {
            return level3Unit;
        }

        public void setLevel3Unit(String level3Unit) {
            this.level3Unit = level3Unit;
        }


        public int getSpecId() {
            return specId;
        }

        public void setSpecId(int specId) {
            this.specId = specId;
        }

        public String getCustomerLineCode() {
            return customerLineCode;
        }

        public void setCustomerLineCode(String customerLineCode) {
            this.customerLineCode = customerLineCode;
        }

        private String payTime;

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public ReceiveAddrBean getReceiveAddr() {
            return receiveAddr;
        }

        public void setReceiveAddr(ReceiveAddrBean receiveAddr) {
            this.receiveAddr = receiveAddr;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getQrCodeId() {
            return qrCodeId;
        }

        public void setQrCodeId(String qrCodeId) {
            this.qrCodeId = qrCodeId;
        }

        public BigDecimal getRealTotal() {
            return realTotal;
        }

        public void setRealTotal(BigDecimal realTotal) {
            this.realTotal = realTotal;
        }

        public int getReturnedAmount() {
            return returnedAmount;
        }

        public void setReturnedAmount(int returnedAmount) {
            this.returnedAmount = returnedAmount;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}

