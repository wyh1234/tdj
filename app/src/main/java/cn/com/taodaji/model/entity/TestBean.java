package cn.com.taodaji.model.entity;

import java.util.List;

public class TestBean {

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


        private PageBeanBean pageBean;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public static class PageBeanBean {

            private int length;
            private int pageNo;
            private int totalRow;
            private int from;
            private int to;
            private boolean needCount;
            private int totalPage;
            private List<RecordListBean> recordList;

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
                 * entityId : 46
                 * siteId : 2
                 * createTime : 2018-08-11 10:33:15
                 * updateTime : 2018-09-11 10:33:15
                 * type : 1
                 * capitalChangeAmount : 0.64
                 * capitalChangeReason : 供应商资金冻结
                 * orderId : 224805
                 * transactionNo : 20180911103310
                 * orderCommissionAmount : 0
                 * extOrderId : 6445106673383116800
                 * orderFreezeStatus : UNFREEZE
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
                 * supplierUserId : 220
                 * storeId : 23
                 * storeName : 薛氏蔬菜
                 * mobile : 15926888592
                 * supplierFreezeAmount : 6466.8
                 * supplierWithdrawalAmount : 3856.95
                 * supplierTotalAmount : 10323.75
                 * remarks : null
                 * status : 0
                 * year : 2018
                 * month : 08
                 * flag : 2018-08
                 * tokenId : 511355f6-be0b-429c-aiujuucf-4da2aded63cb
                 */

                private int entityId;
                private int siteId;
                private String createTime;
                private String updateTime;
                private int type;
                private double capitalChangeAmount;
                private String capitalChangeReason;
                private int orderId;
                private String transactionNo;
                private int orderCommissionAmount;
                private String extOrderId;
                private String orderFreezeStatus;
                private String orderStatus;
                private Object withdrawalTotalAmount;
                private Object withdrawalFeeAmount;
                private Object withdrawalActuralAmount;
                private Object withdrawalStatus;
                private Object afterSalesCustomerId;
                private Object afterSalesCustomerImg;
                private Object afterSalesTotalAmount;
                private Object afterSalesCommissionAmount;
                private Object afterSalesOrderItemId;
                private Object afterSalesQrCodeId;
                private Object afterSalesProductId;
                private Object productImg;
                private Object productName;
                private Object productNickName;
                private Object productDesc;
                private Object productPrice;
                private Object productUnit;
                private Object productAvgUnit;
                private Object productAvgPrice;
                private Object productDiscountAvgPrice;
                private Object specificationId;
                private Object level2Value;
                private Object level2Unit;
                private Object level3Value;
                private Object level3Unit;
                private Object levelType;
                private int supplierUserId;
                private int storeId;
                private String storeName;
                private String mobile;
                private double supplierFreezeAmount;
                private double supplierWithdrawalAmount;
                private double supplierTotalAmount;
                private Object remarks;
                private int status;
                private String year;
                private String month;
                private String flag;
                private String tokenId;

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

                public double getCapitalChangeAmount() {
                    return capitalChangeAmount;
                }

                public void setCapitalChangeAmount(double capitalChangeAmount) {
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

                public int getOrderCommissionAmount() {
                    return orderCommissionAmount;
                }

                public void setOrderCommissionAmount(int orderCommissionAmount) {
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

                public Object getWithdrawalTotalAmount() {
                    return withdrawalTotalAmount;
                }

                public void setWithdrawalTotalAmount(Object withdrawalTotalAmount) {
                    this.withdrawalTotalAmount = withdrawalTotalAmount;
                }

                public Object getWithdrawalFeeAmount() {
                    return withdrawalFeeAmount;
                }

                public void setWithdrawalFeeAmount(Object withdrawalFeeAmount) {
                    this.withdrawalFeeAmount = withdrawalFeeAmount;
                }

                public Object getWithdrawalActuralAmount() {
                    return withdrawalActuralAmount;
                }

                public void setWithdrawalActuralAmount(Object withdrawalActuralAmount) {
                    this.withdrawalActuralAmount = withdrawalActuralAmount;
                }

                public Object getWithdrawalStatus() {
                    return withdrawalStatus;
                }

                public void setWithdrawalStatus(Object withdrawalStatus) {
                    this.withdrawalStatus = withdrawalStatus;
                }

                public Object getAfterSalesCustomerId() {
                    return afterSalesCustomerId;
                }

                public void setAfterSalesCustomerId(Object afterSalesCustomerId) {
                    this.afterSalesCustomerId = afterSalesCustomerId;
                }

                public Object getAfterSalesCustomerImg() {
                    return afterSalesCustomerImg;
                }

                public void setAfterSalesCustomerImg(Object afterSalesCustomerImg) {
                    this.afterSalesCustomerImg = afterSalesCustomerImg;
                }

                public Object getAfterSalesTotalAmount() {
                    return afterSalesTotalAmount;
                }

                public void setAfterSalesTotalAmount(Object afterSalesTotalAmount) {
                    this.afterSalesTotalAmount = afterSalesTotalAmount;
                }

                public Object getAfterSalesCommissionAmount() {
                    return afterSalesCommissionAmount;
                }

                public void setAfterSalesCommissionAmount(Object afterSalesCommissionAmount) {
                    this.afterSalesCommissionAmount = afterSalesCommissionAmount;
                }

                public Object getAfterSalesOrderItemId() {
                    return afterSalesOrderItemId;
                }

                public void setAfterSalesOrderItemId(Object afterSalesOrderItemId) {
                    this.afterSalesOrderItemId = afterSalesOrderItemId;
                }

                public Object getAfterSalesQrCodeId() {
                    return afterSalesQrCodeId;
                }

                public void setAfterSalesQrCodeId(Object afterSalesQrCodeId) {
                    this.afterSalesQrCodeId = afterSalesQrCodeId;
                }

                public Object getAfterSalesProductId() {
                    return afterSalesProductId;
                }

                public void setAfterSalesProductId(Object afterSalesProductId) {
                    this.afterSalesProductId = afterSalesProductId;
                }

                public Object getProductImg() {
                    return productImg;
                }

                public void setProductImg(Object productImg) {
                    this.productImg = productImg;
                }

                public Object getProductName() {
                    return productName;
                }

                public void setProductName(Object productName) {
                    this.productName = productName;
                }

                public Object getProductNickName() {
                    return productNickName;
                }

                public void setProductNickName(Object productNickName) {
                    this.productNickName = productNickName;
                }

                public Object getProductDesc() {
                    return productDesc;
                }

                public void setProductDesc(Object productDesc) {
                    this.productDesc = productDesc;
                }

                public Object getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(Object productPrice) {
                    this.productPrice = productPrice;
                }

                public Object getProductUnit() {
                    return productUnit;
                }

                public void setProductUnit(Object productUnit) {
                    this.productUnit = productUnit;
                }

                public Object getProductAvgUnit() {
                    return productAvgUnit;
                }

                public void setProductAvgUnit(Object productAvgUnit) {
                    this.productAvgUnit = productAvgUnit;
                }

                public Object getProductAvgPrice() {
                    return productAvgPrice;
                }

                public void setProductAvgPrice(Object productAvgPrice) {
                    this.productAvgPrice = productAvgPrice;
                }

                public Object getProductDiscountAvgPrice() {
                    return productDiscountAvgPrice;
                }

                public void setProductDiscountAvgPrice(Object productDiscountAvgPrice) {
                    this.productDiscountAvgPrice = productDiscountAvgPrice;
                }

                public Object getSpecificationId() {
                    return specificationId;
                }

                public void setSpecificationId(Object specificationId) {
                    this.specificationId = specificationId;
                }

                public Object getLevel2Value() {
                    return level2Value;
                }

                public void setLevel2Value(Object level2Value) {
                    this.level2Value = level2Value;
                }

                public Object getLevel2Unit() {
                    return level2Unit;
                }

                public void setLevel2Unit(Object level2Unit) {
                    this.level2Unit = level2Unit;
                }

                public Object getLevel3Value() {
                    return level3Value;
                }

                public void setLevel3Value(Object level3Value) {
                    this.level3Value = level3Value;
                }

                public Object getLevel3Unit() {
                    return level3Unit;
                }

                public void setLevel3Unit(Object level3Unit) {
                    this.level3Unit = level3Unit;
                }

                public Object getLevelType() {
                    return levelType;
                }

                public void setLevelType(Object levelType) {
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

                public double getSupplierFreezeAmount() {
                    return supplierFreezeAmount;
                }

                public void setSupplierFreezeAmount(double supplierFreezeAmount) {
                    this.supplierFreezeAmount = supplierFreezeAmount;
                }

                public double getSupplierWithdrawalAmount() {
                    return supplierWithdrawalAmount;
                }

                public void setSupplierWithdrawalAmount(double supplierWithdrawalAmount) {
                    this.supplierWithdrawalAmount = supplierWithdrawalAmount;
                }

                public double getSupplierTotalAmount() {
                    return supplierTotalAmount;
                }

                public void setSupplierTotalAmount(double supplierTotalAmount) {
                    this.supplierTotalAmount = supplierTotalAmount;
                }

                public Object getRemarks() {
                    return remarks;
                }

                public void setRemarks(Object remarks) {
                    this.remarks = remarks;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public String getTokenId() {
                    return tokenId;
                }

                public void setTokenId(String tokenId) {
                    this.tokenId = tokenId;
                }
            }
        }
    }
}
