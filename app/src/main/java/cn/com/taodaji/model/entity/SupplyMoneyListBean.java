package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.math.BigDecimal;
import java.util.List;

public class SupplyMoneyListBean {


    /**
     * err : 0
     * msg : 处理成功
     * data : {"pageBean":{"length":10,"pageNo":1,"totalRow":3,"from":0,"to":10,"recordList":[{"entityId":6,"siteId":2,"createTime":"2018-09-06 15:43:29","updateTime":"2018-09-06 15:43:29","type":1,"capitalChangeAmount":2.4,"capitalChangeReason":"供应商资金冻结","orderId":224747,"transactionNo":"20180906154323","orderCommissionAmount":0,"extOrderId":"6443372769232031744","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8681.02,"supplierWithdrawalAmount":91,"supplierTotalAmount":8772.02,"remarks":null,"status":0,"tokenId":"d328596a-fd9d-4968-8134-dd5b7d1cd37f","year":"2018","month":"09","flag":"2018-09"},{"entityId":16,"siteId":2,"createTime":"2018-09-06 15:45:41","updateTime":"2018-09-06 15:45:41","type":1,"capitalChangeAmount":4.7,"capitalChangeReason":"供应商资金冻结","orderId":224757,"transactionNo":"20180906154525","orderCommissionAmount":0,"extOrderId":"6443373306316853248","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8685.72,"supplierWithdrawalAmount":91,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"f0305976-d107-41be-a7d9-81d3b07cec42","year":"2018","month":"09","flag":"2018-09"},{"entityId":17,"siteId":2,"createTime":"2018-09-06 17:53:09","updateTime":"2018-09-06 17:53:09","type":1,"capitalChangeAmount":4.8,"capitalChangeReason":"供应商资金解冻","orderId":223849,"transactionNo":"20180719104634","orderCommissionAmount":0,"extOrderId":"6425541155965898752","orderFreezeStatus":"UNFREEZE","orderStatus":"trade_finished","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"","supplierFreezeAmount":8680.92,"supplierWithdrawalAmount":95.8,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"804c5999-f3b2-438e-a5be-047dc98589d0","year":"2018","month":"09","flag":"2018-09"}],"needCount":true,"totalPage":1}}
     */

    private int err;
    private String msg = "";
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
         * pageBean : {"length":10,"pageNo":1,"totalRow":3,"from":0,"to":10,"recordList":[{"entityId":6,"siteId":2,"createTime":"2018-09-06 15:43:29","updateTime":"2018-09-06 15:43:29","type":1,"capitalChangeAmount":2.4,"capitalChangeReason":"供应商资金冻结","orderId":224747,"transactionNo":"20180906154323","orderCommissionAmount":0,"extOrderId":"6443372769232031744","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8681.02,"supplierWithdrawalAmount":91,"supplierTotalAmount":8772.02,"remarks":null,"status":0,"tokenId":"d328596a-fd9d-4968-8134-dd5b7d1cd37f","year":"2018","month":"09","flag":"2018-09"},{"entityId":16,"siteId":2,"createTime":"2018-09-06 15:45:41","updateTime":"2018-09-06 15:45:41","type":1,"capitalChangeAmount":4.7,"capitalChangeReason":"供应商资金冻结","orderId":224757,"transactionNo":"20180906154525","orderCommissionAmount":0,"extOrderId":"6443373306316853248","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8685.72,"supplierWithdrawalAmount":91,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"f0305976-d107-41be-a7d9-81d3b07cec42","year":"2018","month":"09","flag":"2018-09"},{"entityId":17,"siteId":2,"createTime":"2018-09-06 17:53:09","updateTime":"2018-09-06 17:53:09","type":1,"capitalChangeAmount":4.8,"capitalChangeReason":"供应商资金解冻","orderId":223849,"transactionNo":"20180719104634","orderCommissionAmount":0,"extOrderId":"6425541155965898752","orderFreezeStatus":"UNFREEZE","orderStatus":"trade_finished","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"","supplierFreezeAmount":8680.92,"supplierWithdrawalAmount":95.8,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"804c5999-f3b2-438e-a5be-047dc98589d0","year":"2018","month":"09","flag":"2018-09"}],"needCount":true,"totalPage":1}
         */

        private BigDecimal orderFreezeMoney=BigDecimal.ZERO;
        private BigDecimal afterSaleMoney=BigDecimal.ZERO;
        private BigDecimal withdrawalMoney=BigDecimal.ZERO;//提现
        private BigDecimal orderUNFreezeMoney=BigDecimal.ZERO;
        private BigDecimal orderCanceledMoney=BigDecimal.ZERO;

        public BigDecimal getOrderCanceledMoney() {
            return orderCanceledMoney;
        }

        public void setOrderCanceledMoney(BigDecimal orderCanceledMoney) {
            this.orderCanceledMoney = orderCanceledMoney;
        }

        public BigDecimal getOrderFreezeMoney() {
            return orderFreezeMoney;
        }

        public void setOrderFreezeMoney(BigDecimal orderFreezeMoney) {
            this.orderFreezeMoney = orderFreezeMoney;
        }

        public BigDecimal getAfterSaleMoney() {
            return afterSaleMoney;
        }

        public void setAfterSaleMoney(BigDecimal afterSaleMoney) {
            this.afterSaleMoney = afterSaleMoney;
        }

        public BigDecimal getWithdrawalMoney() {
            return withdrawalMoney;
        }

        public void setWithdrawalMoney(BigDecimal withdrawalMoney) {
            this.withdrawalMoney = withdrawalMoney;
        }

        public BigDecimal getOrderUNFreezeMoney() {
            return orderUNFreezeMoney;
        }

        public void setOrderUNFreezeMoney(BigDecimal orderUNFreezeMoney) {
            this.orderUNFreezeMoney = orderUNFreezeMoney;
        }

        private PageBeanBean pageBean;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public static class PageBeanBean {
            /**
             * length : 10
             * pageNo : 1
             * totalRow : 3
             * from : 0
             * to : 10
             * recordList : [{"entityId":6,"siteId":2,"createTime":"2018-09-06 15:43:29","updateTime":"2018-09-06 15:43:29","type":1,"capitalChangeAmount":2.4,"capitalChangeReason":"供应商资金冻结","orderId":224747,"transactionNo":"20180906154323","orderCommissionAmount":0,"extOrderId":"6443372769232031744","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8681.02,"supplierWithdrawalAmount":91,"supplierTotalAmount":8772.02,"remarks":null,"status":0,"tokenId":"d328596a-fd9d-4968-8134-dd5b7d1cd37f","year":"2018","month":"09","flag":"2018-09"},{"entityId":16,"siteId":2,"createTime":"2018-09-06 15:45:41","updateTime":"2018-09-06 15:45:41","type":1,"capitalChangeAmount":4.7,"capitalChangeReason":"供应商资金冻结","orderId":224757,"transactionNo":"20180906154525","orderCommissionAmount":0,"extOrderId":"6443373306316853248","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8685.72,"supplierWithdrawalAmount":91,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"f0305976-d107-41be-a7d9-81d3b07cec42","year":"2018","month":"09","flag":"2018-09"},{"entityId":17,"siteId":2,"createTime":"2018-09-06 17:53:09","updateTime":"2018-09-06 17:53:09","type":1,"capitalChangeAmount":4.8,"capitalChangeReason":"供应商资金解冻","orderId":223849,"transactionNo":"20180719104634","orderCommissionAmount":0,"extOrderId":"6425541155965898752","orderFreezeStatus":"UNFREEZE","orderStatus":"trade_finished","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"","supplierFreezeAmount":8680.92,"supplierWithdrawalAmount":95.8,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"804c5999-f3b2-438e-a5be-047dc98589d0","year":"2018","month":"09","flag":"2018-09"}]
             * needCount : true
             * totalPage : 1
             */

            private int length;
            private int pageNo;
            private int totalRow;
            private int from;
            private int to;
            private boolean needCount;
            private int totalPage;
            private List<RecordListBean> recordList;
            @OnlyField
            private String month;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getTotalRow() {
                return totalRow;
            }

            public void setTotalRow(int totalRow) {
                this.totalRow = totalRow;
            }

            public int getFrom() {
                return from;
            }

            public void setFrom(int from) {
                this.from = from;
            }

            public int getTo() {
                return to;
            }

            public void setTo(int to) {
                this.to = to;
            }

            public boolean isNeedCount() {
                return needCount;
            }

            public void setNeedCount(boolean needCount) {
                this.needCount = needCount;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public List<RecordListBean> getRecordList() {
                return recordList;
            }

            public void setRecordList(List<RecordListBean> recordList) {
                this.recordList = recordList;
            }

            public static class RecordListBean {
                /**
                 * entityId : 6
                 * siteId : 2
                 * createTime : 2018-09-06 15:43:29
                 * updateTime : 2018-09-06 15:43:29
                 * type : 1
                 * capitalChangeAmount : 2.4
                 * capitalChangeReason : 供应商资金冻结
                 * orderId : 224747
                 * transactionNo : 20180906154323
                 * orderCommissionAmount : 0
                 * extOrderId : 6443372769232031744
                 * orderFreezeStatus : FREEZE
                 * orderStatus : wait_seller_confirm_goods
                 * withdrawalTotalAmount : null
                 * withdrawalFeeAmount : null
                 * withdrawalActuralAmount : null
                 * withdrawalStatus : null
                 * afterSalesCustomerId : null
                 * afterSalesCustomerImg : null
                 * afterSalesTotalAmount : null
                 * afterSalesCommissionAmount : null
                 * afterSalesOrderItemId : null
                 * afterSalesQrCodeId : null
                 * afterSalesProductId : null
                 * productImg : null
                 * productName : null
                 * productNickName : null
                 * productDesc : null
                 * productPrice : null
                 * productUnit : null
                 * productAvgUnit : null
                 * productAvgPrice : null
                 * productDiscountAvgPrice : null
                 * specificationId : null
                 * level2Value : null
                 * level2Unit : null
                 * level3Value : null
                 * level3Unit : null
                 * levelType : null
                 * supplierUserId : 44
                 * storeId : 23
                 * storeName : 姐弟生姜大蒜批发
                 * mobile : 18986399038
                 * supplierFreezeAmount : 8681.02
                 * supplierWithdrawalAmount : 91
                 * supplierTotalAmount : 8772.02
                 * remarks : null
                 * status : 0
                 * tokenId : d328596a-fd9d-4968-8134-dd5b7d1cd37f
                 * year : 2018
                 * month : 09
                 * flag : 2018-09
                 */

                private int entityId;
                private int siteId;
                private String createTime = "";
                private String updateTime = "";
                private int type;
                private BigDecimal capitalChangeAmount = BigDecimal.ZERO;
                private String capitalChangeReason = "";
                private int orderId;
                private String transactionNo = "";
                private BigDecimal orderCommissionAmount = BigDecimal.ZERO;
                private String extOrderId = "";
                private String orderFreezeStatus = "";
                private String orderStatus = "";
                private BigDecimal withdrawalTotalAmount = BigDecimal.ZERO;
                private BigDecimal withdrawalFeeAmount = BigDecimal.ZERO;
                private BigDecimal withdrawalActuralAmount = BigDecimal.ZERO;

               /** 供应商提现状态：0：处理中|1：处理完成*/
                private int withdrawalStatus;
                private int afterSalesCustomerId;
                private String afterSalesCustomerImg = "";
                private BigDecimal afterSalesTotalAmount = BigDecimal.ZERO;
                private BigDecimal afterSalesCommissionAmount = BigDecimal.ZERO;
                private int afterSalesOrderItemId;
                private String afterSalesQrCodeId = "";
                private int afterSalesProductId;
                private String productImg = "";
                private String productName = "";
                private String productNickName = "";
                private String productDesc = "";
                private BigDecimal productPrice = BigDecimal.ZERO;
                private String productUnit = "";
                private String productAvgUnit = "";
                private BigDecimal productAvgPrice = BigDecimal.ZERO;
                private BigDecimal productDiscountAvgPrice = BigDecimal.ZERO;
                private int specificationId;
                private BigDecimal level2Value = BigDecimal.ZERO;
                private String level2Unit = "";
                private BigDecimal level3Value = BigDecimal.ZERO;
                private String level3Unit = "";
                private int levelType;
                private int supplierUserId;
                private int storeId;
                private String storeName = "";
                private String mobile = "";
                private BigDecimal supplierFreezeAmount = BigDecimal.ZERO;
                private BigDecimal supplierWithdrawalAmount = BigDecimal.ZERO;
                private BigDecimal supplierTotalAmount = BigDecimal.ZERO;
                private String remarks="";
                private int status;
                private String tokenId = "";
                private String year = "";
                private String month = "";
                private String flag = "";
                private BigDecimal shopCouponAmount = BigDecimal.ZERO;


                private int packOrderId;

                public BigDecimal getShopCouponAmount() {
                    return shopCouponAmount;
                }

                public void setShopCouponAmount(BigDecimal shopCouponAmount) {
                    this.shopCouponAmount = shopCouponAmount;
                }

                public int getPackOrderId() {
                    return packOrderId;
                }

                public void setPackOrderId(int packOrderId) {
                    this.packOrderId = packOrderId;
                }

                public int getEntityId() {
                    return entityId;
                }

                public void setEntityId(int entityId) {
                    this.entityId = entityId;
                }

                public int getSiteId() {
                    return siteId;
                }

                public void setSiteId(int siteId) {
                    this.siteId = siteId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public BigDecimal getCapitalChangeAmount() {
                    return capitalChangeAmount;
                }

                public void setCapitalChangeAmount(BigDecimal capitalChangeAmount) {
                    this.capitalChangeAmount = capitalChangeAmount;
                }

                public String getCapitalChangeReason() {
                    return capitalChangeReason;
                }

                public void setCapitalChangeReason(String capitalChangeReason) {
                    this.capitalChangeReason = capitalChangeReason;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public String getTransactionNo() {
                    return transactionNo;
                }

                public void setTransactionNo(String transactionNo) {
                    this.transactionNo = transactionNo;
                }

                public BigDecimal getOrderCommissionAmount() {
                    return orderCommissionAmount;
                }

                public void setOrderCommissionAmount(BigDecimal orderCommissionAmount) {
                    this.orderCommissionAmount = orderCommissionAmount;
                }

                public String getExtOrderId() {
                    return extOrderId;
                }

                public void setExtOrderId(String extOrderId) {
                    this.extOrderId = extOrderId;
                }

                public String getOrderFreezeStatus() {
                    return orderFreezeStatus;
                }

                public void setOrderFreezeStatus(String orderFreezeStatus) {
                    this.orderFreezeStatus = orderFreezeStatus;
                }

                public String getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(String orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public BigDecimal getWithdrawalTotalAmount() {
                    return withdrawalTotalAmount;
                }

                public void setWithdrawalTotalAmount(BigDecimal withdrawalTotalAmount) {
                    this.withdrawalTotalAmount = withdrawalTotalAmount;
                }

                public BigDecimal getWithdrawalFeeAmount() {
                    return withdrawalFeeAmount;
                }

                public void setWithdrawalFeeAmount(BigDecimal withdrawalFeeAmount) {
                    this.withdrawalFeeAmount = withdrawalFeeAmount;
                }

                public BigDecimal getWithdrawalActuralAmount() {
                    return withdrawalActuralAmount;
                }

                public void setWithdrawalActuralAmount(BigDecimal withdrawalActuralAmount) {
                    this.withdrawalActuralAmount = withdrawalActuralAmount;
                }

                public int getWithdrawalStatus() {
                    return withdrawalStatus;
                }

                public void setWithdrawalStatus(int withdrawalStatus) {
                    this.withdrawalStatus = withdrawalStatus;
                }

                public int getAfterSalesCustomerId() {
                    return afterSalesCustomerId;
                }

                public void setAfterSalesCustomerId(int afterSalesCustomerId) {
                    this.afterSalesCustomerId = afterSalesCustomerId;
                }

                public String getAfterSalesCustomerImg() {
                    return afterSalesCustomerImg;
                }

                public void setAfterSalesCustomerImg(String afterSalesCustomerImg) {
                    this.afterSalesCustomerImg = afterSalesCustomerImg;
                }

                public BigDecimal getAfterSalesTotalAmount() {
                    return afterSalesTotalAmount;
                }

                public void setAfterSalesTotalAmount(BigDecimal afterSalesTotalAmount) {
                    this.afterSalesTotalAmount = afterSalesTotalAmount;
                }

                public BigDecimal getAfterSalesCommissionAmount() {
                    return afterSalesCommissionAmount;
                }

                public void setAfterSalesCommissionAmount(BigDecimal afterSalesCommissionAmount) {
                    this.afterSalesCommissionAmount = afterSalesCommissionAmount;
                }

                public int getAfterSalesOrderItemId() {
                    return afterSalesOrderItemId;
                }

                public void setAfterSalesOrderItemId(int afterSalesOrderItemId) {
                    this.afterSalesOrderItemId = afterSalesOrderItemId;
                }

                public String getAfterSalesQrCodeId() {
                    return afterSalesQrCodeId;
                }

                public void setAfterSalesQrCodeId(String afterSalesQrCodeId) {
                    this.afterSalesQrCodeId = afterSalesQrCodeId;
                }

                public int getAfterSalesProductId() {
                    return afterSalesProductId;
                }

                public void setAfterSalesProductId(int afterSalesProductId) {
                    this.afterSalesProductId = afterSalesProductId;
                }

                public String getProductImg() {
                    return productImg;
                }

                public void setProductImg(String productImg) {
                    this.productImg = productImg;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductNickName() {
                    return productNickName;
                }

                public void setProductNickName(String productNickName) {
                    this.productNickName = productNickName;
                }

                public String getProductDesc() {
                    return productDesc;
                }

                public void setProductDesc(String productDesc) {
                    this.productDesc = productDesc;
                }

                public BigDecimal getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(BigDecimal productPrice) {
                    this.productPrice = productPrice;
                }

                public String getProductUnit() {
                    return productUnit;
                }

                public void setProductUnit(String productUnit) {
                    this.productUnit = productUnit;
                }

                public String getProductAvgUnit() {
                    return productAvgUnit;
                }

                public void setProductAvgUnit(String productAvgUnit) {
                    this.productAvgUnit = productAvgUnit;
                }

                public BigDecimal getProductAvgPrice() {
                    return productAvgPrice;
                }

                public void setProductAvgPrice(BigDecimal productAvgPrice) {
                    this.productAvgPrice = productAvgPrice;
                }

                public BigDecimal getProductDiscountAvgPrice() {
                    return productDiscountAvgPrice;
                }

                public void setProductDiscountAvgPrice(BigDecimal productDiscountAvgPrice) {
                    this.productDiscountAvgPrice = productDiscountAvgPrice;
                }

                public int getSpecificationId() {
                    return specificationId;
                }

                public void setSpecificationId(int specificationId) {
                    this.specificationId = specificationId;
                }

                public BigDecimal getLevel2Value() {
                    return level2Value;
                }

                public void setLevel2Value(BigDecimal level2Value) {
                    this.level2Value = level2Value;
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

                public String getLevel3Unit() {
                    return level3Unit;
                }

                public void setLevel3Unit(String level3Unit) {
                    this.level3Unit = level3Unit;
                }

                public int getLevelType() {
                    return levelType;
                }

                public void setLevelType(int levelType) {
                    this.levelType = levelType;
                }

                public int getSupplierUserId() {
                    return supplierUserId;
                }

                public void setSupplierUserId(int supplierUserId) {
                    this.supplierUserId = supplierUserId;
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

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public BigDecimal getSupplierFreezeAmount() {
                    return supplierFreezeAmount;
                }

                public void setSupplierFreezeAmount(BigDecimal supplierFreezeAmount) {
                    this.supplierFreezeAmount = supplierFreezeAmount;
                }

                public BigDecimal getSupplierWithdrawalAmount() {
                    return supplierWithdrawalAmount;
                }

                public void setSupplierWithdrawalAmount(BigDecimal supplierWithdrawalAmount) {
                    this.supplierWithdrawalAmount = supplierWithdrawalAmount;
                }

                public BigDecimal getSupplierTotalAmount() {
                    return supplierTotalAmount;
                }

                public void setSupplierTotalAmount(BigDecimal supplierTotalAmount) {
                    this.supplierTotalAmount = supplierTotalAmount;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getTokenId() {
                    return tokenId;
                }

                public void setTokenId(String tokenId) {
                    this.tokenId = tokenId;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }
            }
        }
    }
}
