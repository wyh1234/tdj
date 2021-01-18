package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;

public class PickUpOrder {
    /**
     * total : 32
     * items : [{"baseTotalRefund":0,"buyNumber":0,"confirmReceiveTime":0,"createTime":"2017-10-13 09:52","customerContactName":"又又","customerId":1,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg   ","customerName":"湖北黄牛庄","customerTel":"18571161280","expectDeliveredDate":"2017-10-14","expectDeliveredEarliestTime":"08:00","expectDeliveredLatestTime":"11:00","extraField":[{"stock":999980,"productQty":1,"nickName":"兰州鲜百合","productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170723072717e6bae0c1.jpg   ","itemStatus":0,"qrCodeId":"9171013000170","sku":"xtb_1500766111162","itemId":104989,"storeId":81,"productUnit":"袋","productName":"百合","productPrice":5,"orderId":54310,"productId":2179}],"itemCount":1,"lastName":"淘大集","orderId":0,"orderIds":"54310","orderNo":"100000133","shipCost":0,"shipStatus":0,"statusCode":"trade_closed","subtotalCost":5,"totalCost":5,"updateTime":0,"outTradeNo":"3bdb0730afb911e7b931578df62c79c9","couponAmount":0,"totalFreight":20,"actualTotalCost":25}]
     * pn : 1
     * ps : 1
     */
    private DataObject data;
    private int err;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataObject getData() {
        return data;
    }

    public void setData(DataObject data) {
        this.data = data;
    }

    public static class DataObject{
        private int total;
        private int pn;
        private int ps;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * baseTotalRefund : 0
             * buyNumber : 0
             * confirmReceiveTime : 0
             * createTime : 2017-10-13 09:52
             * customerContactName : 又又
             * customerId : 1
             * customerLogo : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg
             * customerName : 湖北黄牛庄
             * customerTel : 18571161280
             * expectDeliveredDate : 2017-10-14
             * expectDeliveredEarliestTime : 08:00
             * expectDeliveredLatestTime : 11:00
             * extraField : [{"stock":999980,"productQty":1,"nickName":"兰州鲜百合","productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170723072717e6bae0c1.jpg   ","itemStatus":0,"qrCodeId":"9171013000170","sku":"xtb_1500766111162","itemId":104989,"storeId":81,"productUnit":"袋","productName":"百合","productPrice":5,"orderId":54310,"productId":2179}]
             * itemCount : 1
             * lastName : 淘大集
             * orderId : 0
             * orderIds : 54310
             * orderNo : 100000133
             * shipCost : 0
             * shipStatus : 0
             * statusCode : trade_closed
             * subtotalCost : 5
             * totalCost : 5
             * updateTime : 0
             * outTradeNo : 3bdb0730afb911e7b931578df62c79c9
             * couponAmount : 0
             * totalFreight : 20
             * actualTotalCost : 25
             */

            private BigDecimal baseTotalRefund;
            private int buyNumber;
            private long confirmReceiveTime;
            private String createTime;
            private String customerContactName;
            private int customerId;
            private String customerLogo;
            private String customerName;
            private String customerTel;
            private String expectDeliveredDate;
            private String expectDeliveredEarliestTime;
            private String expectDeliveredLatestTime;
            private int itemCount;
            private String lastName;
            private int orderId;
            private String orderIds;
            @OnlyField
            private String orderNo;
            private int shipCost;
            private int shipStatus;
            private String statusCode;
            private BigDecimal subtotalCost;
            private BigDecimal totalCost;
            private int updateTime;
            private String outTradeNo;
            private int couponAmount;
            private int totalFreight;
            private BigDecimal actualTotalCost;
            private List<CartGoodsBean> extraField;
            private boolean isFold;
            private int deliveryType;
            private String communityName;
            private String communityShortName;

            private int originalorCustomerId;//创建订单ID

            public String getCommunityShortName() {
                return communityShortName;
            }

            public void setCommunityShortName(String communityShortName) {
                this.communityShortName = communityShortName;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }

            public int getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(int deliveryType) {
                this.deliveryType = deliveryType;
            }

            public int getOriginalorCustomerId() {
                return originalorCustomerId;
            }

            public void setOriginalorCustomerId(int originalorCustomerId) {
                this.originalorCustomerId = originalorCustomerId;
            }

            public boolean isFold() {
                return isFold;
            }

            public void setFold(boolean fold) {
                isFold = fold;
            }

            public BigDecimal getBaseTotalRefund() {
                return baseTotalRefund;
            }

            public void setBaseTotalRefund(BigDecimal baseTotalRefund) {
                this.baseTotalRefund = baseTotalRefund;
            }

            public int getBuyNumber() {
                return buyNumber;
            }

            public void setBuyNumber(int buyNumber) {
                this.buyNumber = buyNumber;
            }

            public long getConfirmReceiveTime() {
                return confirmReceiveTime;
            }

            public void setConfirmReceiveTime(long confirmReceiveTime) {
                this.confirmReceiveTime = confirmReceiveTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCustomerContactName() {
                return customerContactName;
            }

            public void setCustomerContactName(String customerContactName) {
                this.customerContactName = customerContactName;
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

            public String getCustomerTel() {
                return customerTel;
            }

            public void setCustomerTel(String customerTel) {
                this.customerTel = customerTel;
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

            public int getItemCount() {
                return itemCount;
            }

            public void setItemCount(int itemCount) {
                this.itemCount = itemCount;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
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

            public int getShipCost() {
                return shipCost;
            }

            public void setShipCost(int shipCost) {
                this.shipCost = shipCost;
            }

            public int getShipStatus() {
                return shipStatus;
            }

            public void setShipStatus(int shipStatus) {
                this.shipStatus = shipStatus;
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

            public BigDecimal getTotalCost() {
                return totalCost;
            }

            public void setTotalCost(BigDecimal totalCost) {
                this.totalCost = totalCost;
            }

            public int getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(int updateTime) {
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

            public void setActualTotalCost(BigDecimal actualTotalCost) {
                this.actualTotalCost = actualTotalCost;
            }

            public List<CartGoodsBean> getExtraField() {
                return extraField;
            }

            public void setExtraField(List<CartGoodsBean> extraField) {
                this.extraField = extraField;
            }

            public static class ExtraFieldBean {
                /**
                 * stock : 999980
                 * productQty : 1
                 * nickName : 兰州鲜百合
                 * productImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170723072717e6bae0c1.jpg
                 * itemStatus : 0
                 * qrCodeId : 9171013000170
                 * sku : xtb_1500766111162
                 * itemId : 104989
                 * storeId : 81
                 * productUnit : 袋
                 * productName : 百合
                 * productPrice : 5
                 * orderId : 54310
                 * productId : 2179
                 */

                private int stock;
                private int productQty;
                private String nickName;
                private String productImage;
                private int itemStatus;
                private String qrCodeId;
                private String sku;
                private int itemId;
                private int storeId;
                private String productUnit;
                private String productName;
                private int productPrice;
                private int orderId;
                private int productId;

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public int getProductQty() {
                    return productQty;
                }

                public void setProductQty(int productQty) {
                    this.productQty = productQty;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public int getItemStatus() {
                    return itemStatus;
                }

                public void setItemStatus(int itemStatus) {
                    this.itemStatus = itemStatus;
                }

                public String getQrCodeId() {
                    return qrCodeId;
                }

                public void setQrCodeId(String qrCodeId) {
                    this.qrCodeId = qrCodeId;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public int getItemId() {
                    return itemId;
                }

                public void setItemId(int itemId) {
                    this.itemId = itemId;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public String getProductUnit() {
                    return productUnit;
                }

                public void setProductUnit(String productUnit) {
                    this.productUnit = productUnit;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public int getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(int productPrice) {
                    this.productPrice = productPrice;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }
            }
        }
    }
}
