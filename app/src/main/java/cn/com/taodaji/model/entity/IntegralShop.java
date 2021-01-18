package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class IntegralShop {


    /**
     * err : 0
     * data : {"total":2,"obj":[{"id":14,"commodityTypeId":-1,"brandId":null,"commodityCode":"20190805100757","siteId":3,"name":"M4A1","intro":null,"unit":"架","desp":null,"moneyPrice":120.283,"preferentialPrice":80.123,"integralPrice":0,"preferentialIntegral":131,"sellNum":null,"repertory":2,"isHot":null,"isNew":null,"isEntity":null,"auditStatus":null,"status":1,"sellType":3,"sellObj":1,"sellLevel":2,"startTime":"2019-06-10 00:00:00","endTime":"2019-09-20 00:00:00","author":1,"updateTime":null,"createTime":null,"commodityPic":[{"id":2,"picUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","isMaster":1,"order":null,"commodityId":14,"status":1,"imageWidth":null,"imageHeight":null,"fileSize":null,"updateTime":null,"createTime":null}],"pics":null,"masterPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png"},{"id":17,"commodityTypeId":-1,"brandId":null,"commodityCode":"20190805115209","siteId":2,"name":"213","intro":null,"unit":"架","desp":"132","moneyPrice":123,"preferentialPrice":123,"integralPrice":12312,"preferentialIntegral":0,"sellNum":null,"repertory":12,"isHot":null,"isNew":null,"isEntity":null,"auditStatus":null,"status":1,"sellType":3,"sellObj":1,"sellLevel":2,"startTime":"2019-07-04 00:00:00","endTime":"2019-08-18 14:02:32","author":1,"updateTime":null,"createTime":null,"commodityPic":[],"pics":null,"masterPic":null}]}
     * error : null
     * msg : Success
     * errorCode : null
     */

    private int err;
    private DataBean data;
    private Object error;
    private String msg;
    private Object errorCode;

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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public static class DataBean {
        /**
         * total : 2
         * obj : [{"id":14,"commodityTypeId":-1,"brandId":null,"commodityCode":"20190805100757","siteId":3,"name":"M4A1","intro":null,"unit":"架","desp":null,"moneyPrice":120.283,"preferentialPrice":80.123,"integralPrice":0,"preferentialIntegral":131,"sellNum":null,"repertory":2,"isHot":null,"isNew":null,"isEntity":null,"auditStatus":null,"status":1,"sellType":3,"sellObj":1,"sellLevel":2,"startTime":"2019-06-10 00:00:00","endTime":"2019-09-20 00:00:00","author":1,"updateTime":null,"createTime":null,"commodityPic":[{"id":2,"picUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","isMaster":1,"order":null,"commodityId":14,"status":1,"imageWidth":null,"imageHeight":null,"fileSize":null,"updateTime":null,"createTime":null}],"pics":null,"masterPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png"},{"id":17,"commodityTypeId":-1,"brandId":null,"commodityCode":"20190805115209","siteId":2,"name":"213","intro":null,"unit":"架","desp":"132","moneyPrice":123,"preferentialPrice":123,"integralPrice":12312,"preferentialIntegral":0,"sellNum":null,"repertory":12,"isHot":null,"isNew":null,"isEntity":null,"auditStatus":null,"status":1,"sellType":3,"sellObj":1,"sellLevel":2,"startTime":"2019-07-04 00:00:00","endTime":"2019-08-18 14:02:32","author":1,"updateTime":null,"createTime":null,"commodityPic":[],"pics":null,"masterPic":null}]
         */

        private int total;
        private List<ObjBean> obj;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ObjBean> getObj() {
            return obj;
        }

        public void setObj(List<ObjBean> obj) {
            this.obj = obj;
        }

        public static class ObjBean {
            /**
             * id : 14
             * commodityTypeId : -1
             * brandId : null
             * commodityCode : 20190805100757
             * siteId : 3
             * name : M4A1
             * intro : null
             * unit : 架
             * desp : null
             * moneyPrice : 120.283
             * preferentialPrice : 80.123
             * integralPrice : 0
             * preferentialIntegral : 131
             * sellNum : null
             * repertory : 2
             * isHot : null
             * isNew : null
             * isEntity : null
             * auditStatus : null
             * status : 1
             * sellType : 3
             * sellObj : 1
             * sellLevel : 2
             * startTime : 2019-06-10 00:00:00
             * endTime : 2019-09-20 00:00:00
             * author : 1
             * updateTime : null
             * createTime : null
             * commodityPic : [{"id":2,"picUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png","isMaster":1,"order":null,"commodityId":14,"status":1,"imageWidth":null,"imageHeight":null,"fileSize":null,"updateTime":null,"createTime":null}]
             * pics : null
             * masterPic : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png
             */

            private int id;
            private int commodityTypeId;
            private Object brandId;
            private String commodityCode;
            private int siteId;
            private String name;
            private Object intro;
            private String unit;
            private Object desp;
            private BigDecimal moneyPrice;
            private BigDecimal preferentialPrice;
            private int integralPrice;
            private int preferentialIntegral;
            private Object sellNum;
            private int repertory;
            private Object isHot;
            private Object isNew;
            private Object isEntity;
            private Object auditStatus;
            private int status;
            private int sellType;
            private int sellObj;
            private int sellLevel;
            private String startTime;
            private String endTime;
            private int author;
            private Object updateTime;
            private Object createTime;
            private Object pics;
            private String masterPic;
            private List<CommodityPicBean> commodityPic;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommodityTypeId() {
                return commodityTypeId;
            }

            public void setCommodityTypeId(int commodityTypeId) {
                this.commodityTypeId = commodityTypeId;
            }

            public Object getBrandId() {
                return brandId;
            }

            public void setBrandId(Object brandId) {
                this.brandId = brandId;
            }

            public String getCommodityCode() {
                return commodityCode;
            }

            public void setCommodityCode(String commodityCode) {
                this.commodityCode = commodityCode;
            }

            public int getSiteId() {
                return siteId;
            }

            public void setSiteId(int siteId) {
                this.siteId = siteId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getIntro() {
                return intro;
            }

            public void setIntro(Object intro) {
                this.intro = intro;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public Object getDesp() {
                return desp;
            }

            public void setDesp(Object desp) {
                this.desp = desp;
            }

            public BigDecimal getMoneyPrice() {
                return moneyPrice;
            }

            public void setMoneyPrice(BigDecimal moneyPrice) {
                this.moneyPrice = moneyPrice;
            }

            public BigDecimal getPreferentialPrice() {
                return preferentialPrice;
            }

            public void setPreferentialPrice(BigDecimal preferentialPrice) {
                this.preferentialPrice = preferentialPrice;
            }

            public int getIntegralPrice() {
                return integralPrice;
            }

            public void setIntegralPrice(int integralPrice) {
                this.integralPrice = integralPrice;
            }

            public int getPreferentialIntegral() {
                return preferentialIntegral;
            }

            public void setPreferentialIntegral(int preferentialIntegral) {
                this.preferentialIntegral = preferentialIntegral;
            }

            public Object getSellNum() {
                return sellNum;
            }

            public void setSellNum(Object sellNum) {
                this.sellNum = sellNum;
            }

            public int getRepertory() {
                return repertory;
            }

            public void setRepertory(int repertory) {
                this.repertory = repertory;
            }

            public Object getIsHot() {
                return isHot;
            }

            public void setIsHot(Object isHot) {
                this.isHot = isHot;
            }

            public Object getIsNew() {
                return isNew;
            }

            public void setIsNew(Object isNew) {
                this.isNew = isNew;
            }

            public Object getIsEntity() {
                return isEntity;
            }

            public void setIsEntity(Object isEntity) {
                this.isEntity = isEntity;
            }

            public Object getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(Object auditStatus) {
                this.auditStatus = auditStatus;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSellType() {
                return sellType;
            }

            public void setSellType(int sellType) {
                this.sellType = sellType;
            }

            public int getSellObj() {
                return sellObj;
            }

            public void setSellObj(int sellObj) {
                this.sellObj = sellObj;
            }

            public int getSellLevel() {
                return sellLevel;
            }

            public void setSellLevel(int sellLevel) {
                this.sellLevel = sellLevel;
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

            public int getAuthor() {
                return author;
            }

            public void setAuthor(int author) {
                this.author = author;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getPics() {
                return pics;
            }

            public void setPics(Object pics) {
                this.pics = pics;
            }

            public String getMasterPic() {
                return masterPic;
            }

            public void setMasterPic(String masterPic) {
                this.masterPic = masterPic;
            }

            public List<CommodityPicBean> getCommodityPic() {
                return commodityPic;
            }

            public void setCommodityPic(List<CommodityPicBean> commodityPic) {
                this.commodityPic = commodityPic;
            }

            public static class CommodityPicBean {
                /**
                 * id : 2
                 * picUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png
                 * isMaster : 1
                 * order : null
                 * commodityId : 14
                 * status : 1
                 * imageWidth : null
                 * imageHeight : null
                 * fileSize : null
                 * updateTime : null
                 * createTime : null
                 */

                private int id;
                private String picUrl;
                private int isMaster;
                private Object order;
                private int commodityId;
                private int status;
                private Object imageWidth;
                private Object imageHeight;
                private Object fileSize;
                private Object updateTime;
                private Object createTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public int getIsMaster() {
                    return isMaster;
                }

                public void setIsMaster(int isMaster) {
                    this.isMaster = isMaster;
                }

                public Object getOrder() {
                    return order;
                }

                public void setOrder(Object order) {
                    this.order = order;
                }

                public int getCommodityId() {
                    return commodityId;
                }

                public void setCommodityId(int commodityId) {
                    this.commodityId = commodityId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public Object getImageWidth() {
                    return imageWidth;
                }

                public void setImageWidth(Object imageWidth) {
                    this.imageWidth = imageWidth;
                }

                public Object getImageHeight() {
                    return imageHeight;
                }

                public void setImageHeight(Object imageHeight) {
                    this.imageHeight = imageHeight;
                }

                public Object getFileSize() {
                    return fileSize;
                }

                public void setFileSize(Object fileSize) {
                    this.fileSize = fileSize;
                }

                public Object getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(Object updateTime) {
                    this.updateTime = updateTime;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }
            }
        }
    }
}
