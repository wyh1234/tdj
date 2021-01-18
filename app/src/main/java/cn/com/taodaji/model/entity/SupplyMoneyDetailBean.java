package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class SupplyMoneyDetailBean {
    /**
     * err : 0
     * data : {"buyNumber":6,"createTime":"2018-08-18 13:07","customerId":3449,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180903192910db78c315.png","customerName":"淘大集","expectDeliveredDate":"2018-08-19","expectDeliveredEarliestTime":"08:00","expectDeliveredLatestTime":"11:00","extOrderAmount":0,"extOrderId":"6436448192065183744","extOrderItemCount":0,"extTaxAmount":0,"history":[{"createdAt":"2018-08-18 13:07","status":"wait_buyer_pay"},{"createdAt":"2018-08-18 13:07","status":"wait_seller_confirm_goods"},{"createdAt":"2018-08-18 13:10","status":"wait_seller_send_goods"},{"createdAt":"2018-08-18 13:15","status":"wait_buyer_confirm_goods"},{"createdAt":"2018-08-18 13:16","status":"wait_buyer_confirm_goods"},{"createdAt":"2018-08-18 13:30","status":"trade_success"},{"createdAt":"2018-08-26 00:00","status":"trade_finished"},{"createdAt":"2018-08-26 00:00","status":"trade_finished"}],"itemCount":3,"items":[{"amount":4,"avgPrice":0.02,"avgUnit":"斤","description":"了","discount":0.08,"discountAvgPrice":0.02,"discountPrice":0.02,"discountTotalPrice":0.08,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171117090815f179d77e.png","itemId":1414269,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"苹果","nickName":"不不不","orderId":1104525,"price":0.02,"productId":5738,"qrCodeId":"9180818001237","realTotal":0,"returnedAmount":0,"sku":"xtb_1510880904455","specId":64877,"status":5,"storeId":11,"storeName":"咪咪虾条2","totalPrice":0.08,"unit":"斤"},{"amount":3,"avgPrice":1,"avgUnit":"卷","description":"","discount":3,"discountAvgPrice":1,"discountPrice":1,"discountTotalPrice":3,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101153164ca4aecf.jpg","itemId":1414270,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"菱角","nickName":"","orderId":1104525,"price":1,"productId":27411,"qrCodeId":"9180818001244","realTotal":0,"returnedAmount":0,"sku":"","specId":66186,"status":5,"storeId":11,"storeName":"咪咪虾条2","totalPrice":3,"unit":"卷"},{"amount":4,"avgPrice":5,"avgUnit":"斤","description":"cs测试下","discount":20,"discountAvgPrice":5,"discountPrice":5,"discountTotalPrice":20,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18042615150208f66698.png","itemId":1414271,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"板蓝根","nickName":"cs","orderId":1104525,"price":5,"productId":19201,"qrCodeId":"9180818001251","realTotal":0,"returnedAmount":0,"sku":"","specId":48205,"status":9,"storeId":11,"storeName":"咪咪虾条2","totalPrice":20,"unit":"斤"}],"orderId":0,"orderIds":"1104525","orderNo":"6436448192065183744","paymentMethod":{"code":"online_payment","label":"在线支付"},"receiveAddr":{"address":"_大李沟路3号汉江创业创新产业园二楼","hotelName":"淘大集","name":"谭明明","postcode":"","telephone":"18696448397"},"statusCode":"trade_finished","storeinfo":{"phone":"18571161280","siteId":2,"siteName":"襄阳","storeId":11,"storeName":"咪咪虾条2"},"subtotalCost":23.08,"taxCost":0,"totalCost":23.08,"transactionNumber":"20180818130721","updateTime":"2018-08-18 13:07","outTradeNo":"90ec9480a2a411e8a2999b1b7a36a88b","couponAmount":0,"totalFreight":0,"actualTotalCost":23.08}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg = "";

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
         * buyNumber : 6
         * createTime : 2018-08-18 13:07
         * customerId : 3449
         * customerLogo : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180903192910db78c315.png
         * customerName : 淘大集
         * expectDeliveredDate : 2018-08-19
         * expectDeliveredEarliestTime : 08:00
         * expectDeliveredLatestTime : 11:00
         * extOrderAmount : 0
         * extOrderId : 6436448192065183744
         * extOrderItemCount : 0
         * extTaxAmount : 0
         * history : [{"createdAt":"2018-08-18 13:07","status":"wait_buyer_pay"},{"createdAt":"2018-08-18 13:07","status":"wait_seller_confirm_goods"},{"createdAt":"2018-08-18 13:10","status":"wait_seller_send_goods"},{"createdAt":"2018-08-18 13:15","status":"wait_buyer_confirm_goods"},{"createdAt":"2018-08-18 13:16","status":"wait_buyer_confirm_goods"},{"createdAt":"2018-08-18 13:30","status":"trade_success"},{"createdAt":"2018-08-26 00:00","status":"trade_finished"},{"createdAt":"2018-08-26 00:00","status":"trade_finished"}]
         * itemCount : 3
         * items : [{"amount":4,"avgPrice":0.02,"avgUnit":"斤","description":"了","discount":0.08,"discountAvgPrice":0.02,"discountPrice":0.02,"discountTotalPrice":0.08,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/171117090815f179d77e.png","itemId":1414269,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"苹果","nickName":"不不不","orderId":1104525,"price":0.02,"productId":5738,"qrCodeId":"9180818001237","realTotal":0,"returnedAmount":0,"sku":"xtb_1510880904455","specId":64877,"status":5,"storeId":11,"storeName":"咪咪虾条2","totalPrice":0.08,"unit":"斤"},{"amount":3,"avgPrice":1,"avgUnit":"卷","description":"","discount":3,"discountAvgPrice":1,"discountPrice":1,"discountTotalPrice":3,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101153164ca4aecf.jpg","itemId":1414270,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"菱角","nickName":"","orderId":1104525,"price":1,"productId":27411,"qrCodeId":"9180818001244","realTotal":0,"returnedAmount":0,"sku":"","specId":66186,"status":5,"storeId":11,"storeName":"咪咪虾条2","totalPrice":3,"unit":"卷"},{"amount":4,"avgPrice":5,"avgUnit":"斤","description":"cs测试下","discount":20,"discountAvgPrice":5,"discountPrice":5,"discountTotalPrice":20,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18042615150208f66698.png","itemId":1414271,"level2Unit":"","level2Value":0,"level3Unit":"","level3Value":0,"levelType":1,"name":"板蓝根","nickName":"cs","orderId":1104525,"price":5,"productId":19201,"qrCodeId":"9180818001251","realTotal":0,"returnedAmount":0,"sku":"","specId":48205,"status":9,"storeId":11,"storeName":"咪咪虾条2","totalPrice":20,"unit":"斤"}]
         * orderId : 0
         * orderIds : 1104525
         * orderNo : 6436448192065183744
         * paymentMethod : {"code":"online_payment","label":"在线支付"}
         * receiveAddr : {"address":"_大李沟路3号汉江创业创新产业园二楼","hotelName":"淘大集","name":"谭明明","postcode":"","telephone":"18696448397"}
         * statusCode : trade_finished
         * storeinfo : {"phone":"18571161280","siteId":2,"siteName":"襄阳","storeId":11,"storeName":"咪咪虾条2"}
         * subtotalCost : 23.08
         * taxCost : 0
         * totalCost : 23.08
         * transactionNumber : 20180818130721
         * updateTime : 2018-08-18 13:07
         * outTradeNo : 90ec9480a2a411e8a2999b1b7a36a88b
         * couponAmount : 0
         * totalFreight : 0
         * actualTotalCost : 23.08
         *
         */
        private BigDecimal commission=BigDecimal.ZERO;//订单佣金
        private String   paymentTime="";//订单支付时间

        private int entity_id;
        private String create_time="";
        private String update_time="";
        private int supplier_id;
        private String province_name="";
        private String city_name="";
        private String bank_name="";
        private String account_no="";
        private String bank_address="";
        private BigDecimal capital_withdrawal=BigDecimal.ZERO;//提现金额
        private String supplier_name="";
        private String supplier_tel="";
        private String store_name="";
        private String is_withdrawal="";//是否提现
        private String  remark="";
        private int status;//提现状态   0：待提现  1：提现成功  2： 拒绝提现
        private String  reason="";
        private int website_id;
        private int store_id;
        private int business_id;

        private int buyNumber;
        private String createTime = "";
        private int customerId;
        private String customerLogo = "";
        private String customerName = "";
        private String expectDeliveredDate = "";//送达时间
        private String expectDeliveredEarliestTime = "";
        private String expectDeliveredLatestTime = "";
        private int extOrderAmount;
        private String extOrderId = "";
        private int extOrderItemCount;
        private int extTaxAmount;
        private int itemCount;
        private int orderId;
        private String orderIds = "";
        private String orderNo = "";
        private PaymentMethodBean paymentMethod;
        private ReceiveAddrBean receiveAddr;
        private String statusCode = "";

        private BigDecimal subtotalCost =BigDecimal.ZERO;//商品金额
        private BigDecimal taxCost =BigDecimal.ZERO;//税费
        private BigDecimal totalCost =BigDecimal.ZERO;
        private String transactionNumber = "";
        private String updateTime = "";
        private String outTradeNo = "";
        private int couponAmount;//代金券金额
        private int totalFreight;//总运费
        private BigDecimal actualTotalCost =BigDecimal.ZERO;//实付款


        private String hotel_name="";
        private String customer_img="";
        private BigDecimal total_price=BigDecimal.ZERO;//,//售后金额
        private BigDecimal amount=BigDecimal.ZERO;//售后数量
        private String name="";
        private String nick_name="";
        private BigDecimal price=BigDecimal.ZERO;
        private String unit="";
        private String  avg_unit="";
        private BigDecimal avg_price=BigDecimal.ZERO;
        private BigDecimal discount_avg_price=BigDecimal.ZERO;
        private int specification_id;//规格ID
        private BigDecimal level_2_value=BigDecimal.ZERO;
        private String level_2_unit="";
        private BigDecimal level_3_value=BigDecimal.ZERO;
        private String level_3_unit="";
        private int level_type;
        private int original_amount;//采购数量
        private BigDecimal original_total_price=BigDecimal.ZERO;//采购金额
        private String required_delivery_time="";//送达时间
        private String order_no="";//订单编号
        private String after_sales_no="";//售后编号
        private String order_pay_time="";//订单支付时间
        private String complete_time="";//售后完成时间

        private BigDecimal coupon_amount=BigDecimal.ZERO;//订单佣金

        public BigDecimal getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(BigDecimal coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getAvg_unit() {
            return avg_unit;
        }

        public void setAvg_unit(String avg_unit) {
            this.avg_unit = avg_unit;
        }

        public BigDecimal getAvg_price() {
            return avg_price;
        }

        public void setAvg_price(BigDecimal avg_price) {
            this.avg_price = avg_price;
        }

        public BigDecimal getDiscount_avg_price() {
            return discount_avg_price;
        }

        public void setDiscount_avg_price(BigDecimal discount_avg_price) {
            this.discount_avg_price = discount_avg_price;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public String getCustomer_img() {
            return customer_img;
        }

        public void setCustomer_img(String customer_img) {
            this.customer_img = customer_img;
        }

        public BigDecimal getTotal_price() {
            return total_price;
        }

        public void setTotal_price(BigDecimal total_price) {
            this.total_price = total_price;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getSpecification_id() {
            return specification_id;
        }

        public void setSpecification_id(int specification_id) {
            this.specification_id = specification_id;
        }

        public BigDecimal getLevel_2_value() {
            return level_2_value;
        }

        public void setLevel_2_value(BigDecimal level_2_value) {
            this.level_2_value = level_2_value;
        }

        public String getLevel_2_unit() {
            return level_2_unit;
        }

        public void setLevel_2_unit(String level_2_unit) {
            this.level_2_unit = level_2_unit;
        }

        public BigDecimal getLevel_3_value() {
            return level_3_value;
        }

        public void setLevel_3_value(BigDecimal level_3_value) {
            this.level_3_value = level_3_value;
        }

        public String getLevel_3_unit() {
            return level_3_unit;
        }

        public void setLevel_3_unit(String level_3_unit) {
            this.level_3_unit = level_3_unit;
        }

        public int getLevel_type() {
            return level_type;
        }

        public void setLevel_type(int level_type) {
            this.level_type = level_type;
        }

        public int getOriginal_amount() {
            return original_amount;
        }

        public void setOriginal_amount(int original_amount) {
            this.original_amount = original_amount;
        }

        public BigDecimal getOriginal_total_price() {
            return original_total_price;
        }

        public void setOriginal_total_price(BigDecimal original_total_price) {
            this.original_total_price = original_total_price;
        }

        public String getRequired_delivery_time() {
            return required_delivery_time;
        }

        public void setRequired_delivery_time(String required_delivery_time) {
            this.required_delivery_time = required_delivery_time;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getAfter_sales_no() {
            return after_sales_no;
        }

        public void setAfter_sales_no(String after_sales_no) {
            this.after_sales_no = after_sales_no;
        }

        public String getOrder_pay_time() {
            return order_pay_time;
        }

        public void setOrder_pay_time(String order_pay_time) {
            this.order_pay_time = order_pay_time;
        }

        public String getComplete_time() {
            return complete_time;
        }

        public void setComplete_time(String complete_time) {
            this.complete_time = complete_time;
        }

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public BigDecimal getCapital_withdrawal() {
            return capital_withdrawal;
        }

        public void setCapital_withdrawal(BigDecimal capital_withdrawal) {
            this.capital_withdrawal = capital_withdrawal;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }

        public String getSupplier_tel() {
            return supplier_tel;
        }

        public void setSupplier_tel(String supplier_tel) {
            this.supplier_tel = supplier_tel;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getIs_withdrawal() {
            return is_withdrawal;
        }

        public void setIs_withdrawal(String is_withdrawal) {
            this.is_withdrawal = is_withdrawal;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getWebsite_id() {
            return website_id;
        }

        public void setWebsite_id(int website_id) {
            this.website_id = website_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(int business_id) {
            this.business_id = business_id;
        }

        public BigDecimal getCommission() {
            return commission;
        }

        public void setCommission(BigDecimal commission) {
            this.commission = commission;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
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

        public int getExtOrderAmount() {
            return extOrderAmount;
        }

        public void setExtOrderAmount(int extOrderAmount) {
            this.extOrderAmount = extOrderAmount;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public int getExtOrderItemCount() {
            return extOrderItemCount;
        }

        public void setExtOrderItemCount(int extOrderItemCount) {
            this.extOrderItemCount = extOrderItemCount;
        }

        public int getExtTaxAmount() {
            return extTaxAmount;
        }

        public void setExtTaxAmount(int extTaxAmount) {
            this.extTaxAmount = extTaxAmount;
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

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public BigDecimal getSubtotalCost() {
            return subtotalCost;
        }

        public void setSubtotalCost(BigDecimal subtotalCost) {
            this.subtotalCost = subtotalCost;
        }

        public BigDecimal getTaxCost() {
            return taxCost;
        }

        public void setTaxCost(BigDecimal taxCost) {
            this.taxCost = taxCost;
        }

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
        }

        public void setActualTotalCost(BigDecimal actualTotalCost) {
            this.actualTotalCost = actualTotalCost;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
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

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public int getTotalFreight() {
            return totalFreight;
        }

        public void setTotalFreight(int totalFreight) {
            this.totalFreight = totalFreight;
        }

        public BigDecimal getActualTotalCost() {
            return actualTotalCost;
        }




        public static class PaymentMethodBean {
            /**
             * code : online_payment
             * label : 在线支付
             */

            private String code = "";
            private String label = "";

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

        public static class ReceiveAddrBean {
            /**
             * address : _大李沟路3号汉江创业创新产业园二楼
             * hotelName : 淘大集
             * name : 谭明明
             * postcode :
             * telephone : 18696448397
             */

            private String address = "";
            private String hotelName = "";
            private String name = "";
            private String postcode = "";
            private String telephone = "";

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

        public static class StoreinfoBean {
            /**
             * phone : 18571161280
             * siteId : 2
             * siteName : 襄阳
             * storeId : 11
             * storeName : 咪咪虾条2
             */

            private String phone = "";
            private int siteId;
            private String siteName = "";
            private int storeId;
            private String storeName = "";

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

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



    }
}
