package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class PickupFeeList {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"pageBean":{"length":3,"pageNo":1,"totalRow":19,"from":0,"to":3,"recordList":[{"entityId":19,"createTime":"2019-09-19 09:55:02","updateTime":"2019-09-19 09:55:02","websiteId":2,"receiveStationId":1,"stationId":2,"storeId":2,"type":4,"rechargeType":1,"feePercent":0.05,"status":2,"orderTotalFee":500.8,"payMoney":50,"payTime":"2019-09-19 09:55:02","receiveDriverId":1,"storeReceiveMoney":500,"outTradeNo":"12334444","transactionNumber":"343453453535","buyMoney":299.8,"discountRate":0.8,"startTime":"2019-09-19 09:55:02","endTime":"2019-09-19 09:55:02","receiveStationName":"接1仓","stationName":"竹叶山","storeName":"张三蔬菜批发","storeTel":"","receiveFees":100,"receiveDriverName":"","receiveDriverTel":"","site":2,"receiveStationNameAbbr":"","stationNameAbbr":"","receiveTime":"2019-09-19 09:55:02","expectDeliveredDate":"2019-09-19 09:55:02"}],"needCount":true,"totalPage":7}}
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
         * pageBean : {"length":3,"pageNo":1,"totalRow":19,"from":0,"to":3,"recordList":[{"entityId":19,"createTime":"2019-09-19 09:55:02","updateTime":"2019-09-19 09:55:02","websiteId":2,"receiveStationId":1,"stationId":2,"storeId":2,"type":4,"rechargeType":1,"feePercent":0.05,"status":2,"orderTotalFee":500.8,"payMoney":50,"payTime":"2019-09-19 09:55:02","receiveDriverId":1,"storeReceiveMoney":500,"outTradeNo":"12334444","transactionNumber":"343453453535","buyMoney":299.8,"discountRate":0.8,"startTime":"2019-09-19 09:55:02","endTime":"2019-09-19 09:55:02","receiveStationName":"接1仓","stationName":"竹叶山","storeName":"张三蔬菜批发","storeTel":"","receiveFees":100,"receiveDriverName":"","receiveDriverTel":"","site":2,"receiveStationNameAbbr":"","stationNameAbbr":"","receiveTime":"2019-09-19 09:55:02","expectDeliveredDate":"2019-09-19 09:55:02"}],"needCount":true,"totalPage":7}
         */

        private PageBeanBean pageBean;

        public PageBeanBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBeanBean pageBean) {
            this.pageBean = pageBean;
        }

        public static class PageBeanBean {
            /**
             * length : 3
             * pageNo : 1
             * totalRow : 19
             * from : 0
             * to : 3
             * recordList : [{"entityId":19,"createTime":"2019-09-19 09:55:02","updateTime":"2019-09-19 09:55:02","websiteId":2,"receiveStationId":1,"stationId":2,"storeId":2,"type":4,"rechargeType":1,"feePercent":0.05,"status":2,"orderTotalFee":500.8,"payMoney":50,"payTime":"2019-09-19 09:55:02","receiveDriverId":1,"storeReceiveMoney":500,"outTradeNo":"12334444","transactionNumber":"343453453535","buyMoney":299.8,"discountRate":0.8,"startTime":"2019-09-19 09:55:02","endTime":"2019-09-19 09:55:02","receiveStationName":"接1仓","stationName":"竹叶山","storeName":"张三蔬菜批发","storeTel":"","receiveFees":100,"receiveDriverName":"","receiveDriverTel":"","site":2,"receiveStationNameAbbr":"","stationNameAbbr":"","receiveTime":"2019-09-19 09:55:02","expectDeliveredDate":"2019-09-19 09:55:02"}]
             * needCount : true
             * totalPage : 7
             */

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
                 * entityId : 19
                 * createTime : 2019-09-19 09:55:02
                 * updateTime : 2019-09-19 09:55:02
                 * websiteId : 2
                 * receiveStationId : 1
                 * stationId : 2
                 * storeId : 2
                 * type : 4
                 * rechargeType : 1
                 * feePercent : 0.05
                 * status : 2
                 * orderTotalFee : 500.8
                 * payMoney : 50
                 * payTime : 2019-09-19 09:55:02
                 * receiveDriverId : 1
                 * storeReceiveMoney : 500
                 * outTradeNo : 12334444
                 * transactionNumber : 343453453535
                 * buyMoney : 299.8
                 * discountRate : 0.8
                 * startTime : 2019-09-19 09:55:02
                 * endTime : 2019-09-19 09:55:02
                 * receiveStationName : 接1仓
                 * stationName : 竹叶山
                 * storeName : 张三蔬菜批发
                 * storeTel :
                 * receiveFees : 100
                 * receiveDriverName :
                 * receiveDriverTel :
                 * site : 2
                 * receiveStationNameAbbr :
                 * stationNameAbbr :
                 * receiveTime : 2019-09-19 09:55:02
                 * expectDeliveredDate : 2019-09-19 09:55:02
                 */

                private int entityId;
                private String createTime;
                private String updateTime;
                private int websiteId;
                private int receiveStationId;
                private int stationId;
                private int storeId;
                private int type;
                private int rechargeType;
                private BigDecimal feePercent;
                private int status;
                private BigDecimal orderTotalFee;
                private BigDecimal payMoney;
                private String payTime;
                private int receiveDriverId;
                private BigDecimal storeReceiveMoney;
                private String outTradeNo;
                private String transactionNumber;
                private BigDecimal buyMoney;
                private BigDecimal discountRate;
                private String startTime;
                private String endTime;
                private String receiveStationName;
                private String stationName;
                private String storeName;
                private String storeTel;
                private BigDecimal receiveFees;
                private String receiveDriverName;
                private String receiveDriverTel;
                private int site;
                private String receiveStationNameAbbr;
                private String stationNameAbbr;
                private String receiveTime;
                private String expectDeliveredDate;
                private int receiveType;

                public int getEntityId() {
                    return entityId;
                }

                public void setEntityId(int entityId) {
                    this.entityId = entityId;
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

                public int getWebsiteId() {
                    return websiteId;
                }

                public void setWebsiteId(int websiteId) {
                    this.websiteId = websiteId;
                }

                public int getReceiveStationId() {
                    return receiveStationId;
                }

                public void setReceiveStationId(int receiveStationId) {
                    this.receiveStationId = receiveStationId;
                }

                public int getStationId() {
                    return stationId;
                }

                public void setStationId(int stationId) {
                    this.stationId = stationId;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getRechargeType() {
                    return rechargeType;
                }

                public void setRechargeType(int rechargeType) {
                    this.rechargeType = rechargeType;
                }

                public BigDecimal getFeePercent() {
                    return feePercent;
                }

                public void setFeePercent(BigDecimal feePercent) {
                    this.feePercent = feePercent;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public BigDecimal getOrderTotalFee() {
                    return orderTotalFee;
                }

                public void setOrderTotalFee(BigDecimal orderTotalFee) {
                    this.orderTotalFee = orderTotalFee;
                }

                public BigDecimal getPayMoney() {
                    return payMoney;
                }

                public void setPayMoney(BigDecimal payMoney) {
                    this.payMoney = payMoney;
                }

                public String getPayTime() {
                    return payTime;
                }

                public void setPayTime(String payTime) {
                    this.payTime = payTime;
                }

                public int getReceiveDriverId() {
                    return receiveDriverId;
                }

                public void setReceiveDriverId(int receiveDriverId) {
                    this.receiveDriverId = receiveDriverId;
                }

                public BigDecimal getStoreReceiveMoney() {
                    return storeReceiveMoney;
                }

                public void setStoreReceiveMoney(BigDecimal storeReceiveMoney) {
                    this.storeReceiveMoney = storeReceiveMoney;
                }

                public String getOutTradeNo() {
                    return outTradeNo;
                }

                public void setOutTradeNo(String outTradeNo) {
                    this.outTradeNo = outTradeNo;
                }

                public String getTransactionNumber() {
                    return transactionNumber;
                }

                public void setTransactionNumber(String transactionNumber) {
                    this.transactionNumber = transactionNumber;
                }

                public BigDecimal getBuyMoney() {
                    return buyMoney;
                }

                public void setBuyMoney(BigDecimal buyMoney) {
                    this.buyMoney = buyMoney;
                }

                public BigDecimal getDiscountRate() {
                    return discountRate;
                }

                public void setDiscountRate(BigDecimal discountRate) {
                    this.discountRate = discountRate;
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

                public String getReceiveStationName() {
                    return receiveStationName;
                }

                public void setReceiveStationName(String receiveStationName) {
                    this.receiveStationName = receiveStationName;
                }

                public String getStationName() {
                    return stationName;
                }

                public void setStationName(String stationName) {
                    this.stationName = stationName;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
                }

                public String getStoreTel() {
                    return storeTel;
                }

                public void setStoreTel(String storeTel) {
                    this.storeTel = storeTel;
                }

                public BigDecimal getReceiveFees() {
                    return receiveFees;
                }

                public void setReceiveFees(BigDecimal receiveFees) {
                    this.receiveFees = receiveFees;
                }

                public String getReceiveDriverName() {
                    return receiveDriverName;
                }

                public void setReceiveDriverName(String receiveDriverName) {
                    this.receiveDriverName = receiveDriverName;
                }

                public String getReceiveDriverTel() {
                    return receiveDriverTel;
                }

                public void setReceiveDriverTel(String receiveDriverTel) {
                    this.receiveDriverTel = receiveDriverTel;
                }

                public int getSite() {
                    return site;
                }

                public void setSite(int site) {
                    this.site = site;
                }

                public String getReceiveStationNameAbbr() {
                    return receiveStationNameAbbr;
                }

                public void setReceiveStationNameAbbr(String receiveStationNameAbbr) {
                    this.receiveStationNameAbbr = receiveStationNameAbbr;
                }

                public String getStationNameAbbr() {
                    return stationNameAbbr;
                }

                public void setStationNameAbbr(String stationNameAbbr) {
                    this.stationNameAbbr = stationNameAbbr;
                }

                public String getReceiveTime() {
                    return receiveTime;
                }

                public void setReceiveTime(String receiveTime) {
                    this.receiveTime = receiveTime;
                }

                public String getExpectDeliveredDate() {
                    return expectDeliveredDate;
                }

                public void setExpectDeliveredDate(String expectDeliveredDate) {
                    this.expectDeliveredDate = expectDeliveredDate;
                }

                public int getReceiveType() {
                    return receiveType;
                }

                public void setReceiveType(int receiveType) {
                    this.receiveType = receiveType;
                }
            }
        }
    }
}
