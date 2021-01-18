package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class PickupOrderDetail {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"productCount":1,"pageBean":{"length":3,"pageNo":1,"totalRow":1,"from":0,"to":3,"recordList":[{"productItemPrice":2,"productId":69390,"nickName":"大青芒","pictureUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1908100908343f67aad6.jpg","productPayMoney":500.88,"orderItems":[{"region_no":"99","amount":1,"qrCodeId":"9190918000016","regionCollNo":"E","customerLineCode":"1","leve1Value":1,"level3Value":0,"level3Unit":"","lineCode":"3","level1Unit":"斤","level2Value":0,"rowTotal":1,"leve2Unit":"","levelType":"3"}],"productName":"大青芒","itemCount":2}],"needCount":true,"totalPage":1}}
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
         * productCount : 1
         * pageBean : {"length":3,"pageNo":1,"totalRow":1,"from":0,"to":3,"recordList":[{"productItemPrice":2,"productId":69390,"nickName":"大青芒","pictureUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1908100908343f67aad6.jpg","productPayMoney":500.88,"orderItems":[{"region_no":"99","amount":1,"qrCodeId":"9190918000016","regionCollNo":"E","customerLineCode":"1","leve1Value":1,"level3Value":0,"level3Unit":"","lineCode":"3","level1Unit":"斤","level2Value":0,"rowTotal":1,"leve2Unit":"","levelType":"3"}],"productName":"大青芒","itemCount":2}],"needCount":true,"totalPage":1}
         */

        private int productCount;
        private PageBeanBean pageBean;

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

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
             * totalRow : 1
             * from : 0
             * to : 3
             * recordList : [{"productItemPrice":2,"productId":69390,"nickName":"大青芒","pictureUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1908100908343f67aad6.jpg","productPayMoney":500.88,"orderItems":[{"region_no":"99","amount":1,"qrCodeId":"9190918000016","regionCollNo":"E","customerLineCode":"1","leve1Value":1,"level3Value":0,"level3Unit":"","lineCode":"3","level1Unit":"斤","level2Value":0,"rowTotal":1,"leve2Unit":"","levelType":"3"}],"productName":"大青芒","itemCount":2}]
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
                 * productItemPrice : 2
                 * productId : 69390
                 * nickName : 大青芒
                 * pictureUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1908100908343f67aad6.jpg
                 * productPayMoney : 500.88
                 * orderItems : [{"region_no":"99","amount":1,"qrCodeId":"9190918000016","regionCollNo":"E","customerLineCode":"1","leve1Value":1,"level3Value":0,"level3Unit":"","lineCode":"3","level1Unit":"斤","level2Value":0,"rowTotal":1,"leve2Unit":"","levelType":"3"}]
                 * productName : 大青芒
                 * itemCount : 2
                 */

                private BigDecimal productItemPrice;
                private int productId;
                private String nickName;
                private String pictureUrl;
                private BigDecimal productPayMoney;
                private String productName;
                private int itemCount;
                private List<OrderItemsBean> orderItems;

                public BigDecimal getProductItemPrice() {
                    return productItemPrice;
                }

                public void setProductItemPrice(BigDecimal productItemPrice) {
                    this.productItemPrice = productItemPrice;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getPictureUrl() {
                    return pictureUrl;
                }

                public void setPictureUrl(String pictureUrl) {
                    this.pictureUrl = pictureUrl;
                }

                public BigDecimal getProductPayMoney() {
                    return productPayMoney;
                }

                public void setProductPayMoney(BigDecimal productPayMoney) {
                    this.productPayMoney = productPayMoney;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public int getItemCount() {
                    return itemCount;
                }

                public void setItemCount(int itemCount) {
                    this.itemCount = itemCount;
                }

                public List<OrderItemsBean> getOrderItems() {
                    return orderItems;
                }

                public void setOrderItems(List<OrderItemsBean> orderItems) {
                    this.orderItems = orderItems;
                }

                public static class OrderItemsBean {
                    /**
                     * region_no : 99
                     * amount : 1
                     * qrCodeId : 9190918000016
                     * regionCollNo : E
                     * customerLineCode : 1
                     * leve1Value : 1
                     * level3Value : 0
                     * level3Unit :
                     * lineCode : 3
                     * level1Unit : 斤
                     * level2Value : 0
                     * rowTotal : 1
                     * leve2Unit :
                     * levelType : 3
                     */

                    private String region_no;
                    private int amount;
                    private String qrCodeId;
                    private String regionCollNo;
                    private String hotelName;
                    private String customerLineCode;
                    private BigDecimal leve1Value;
                    private BigDecimal level3Value;
                    private String level3Unit;
                    private String lineCode;
                    private String level1Unit;
                    private BigDecimal level2Value;
                    private BigDecimal rowTotal;
                    private String leve2Unit;
                    private int levelType;
                    private BigDecimal avgPrice;
                    private String avgUnit;

                    public BigDecimal getAvgPrice() {
                        return avgPrice;
                    }

                    public void setAvgPrice(BigDecimal avgPrice) {
                        this.avgPrice = avgPrice;
                    }

                    public String getAvgUnit() {
                        return avgUnit;
                    }

                    public void setAvgUnit(String avgUnit) {
                        this.avgUnit = avgUnit;
                    }

                    public String getHotelName() {
                        return hotelName;
                    }

                    public void setHotelName(String hotelName) {
                        this.hotelName = hotelName;
                    }

                    public String getRegion_no() {
                        return region_no;
                    }

                    public void setRegion_no(String region_no) {
                        this.region_no = region_no;
                    }

                    public int getAmount() {
                        return amount;
                    }

                    public void setAmount(int amount) {
                        this.amount = amount;
                    }

                    public String getQrCodeId() {
                        return qrCodeId;
                    }

                    public void setQrCodeId(String qrCodeId) {
                        this.qrCodeId = qrCodeId;
                    }

                    public String getRegionCollNo() {
                        return regionCollNo;
                    }

                    public void setRegionCollNo(String regionCollNo) {
                        this.regionCollNo = regionCollNo;
                    }

                    public String getCustomerLineCode() {
                        return customerLineCode;
                    }

                    public void setCustomerLineCode(String customerLineCode) {
                        this.customerLineCode = customerLineCode;
                    }

                    public BigDecimal getLeve1Value() {
                        return leve1Value;
                    }

                    public void setLeve1Value(BigDecimal leve1Value) {
                        this.leve1Value = leve1Value;
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

                    public String getLineCode() {
                        return lineCode;
                    }

                    public void setLineCode(String lineCode) {
                        this.lineCode = lineCode;
                    }

                    public String getLevel1Unit() {
                        return level1Unit;
                    }

                    public void setLevel1Unit(String level1Unit) {
                        this.level1Unit = level1Unit;
                    }

                    public BigDecimal getLevel2Value() {
                        return level2Value;
                    }

                    public void setLevel2Value(BigDecimal level2Value) {
                        this.level2Value = level2Value;
                    }

                    public BigDecimal getRowTotal() {
                        return rowTotal;
                    }

                    public void setRowTotal(BigDecimal rowTotal) {
                        this.rowTotal = rowTotal;
                    }

                    public String getLeve2Unit() {
                        return leve2Unit;
                    }

                    public void setLeve2Unit(String leve2Unit) {
                        this.leve2Unit = leve2Unit;
                    }

                    public int getLevelType() {
                        return levelType;
                    }

                    public void setLevelType(int levelType) {
                        this.levelType = levelType;
                    }
                }
            }
        }
    }
}
