package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class AdvMoneyDetails {

    /**
     * err : 0
     * data : {"advertisementBgUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1912061105472fe1d458.png","advertisementDetailId":352,"advertisementOrderId":195,"advertisementPackageId":63,"advertisementPlanCode":"195693721","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","categoryParentName":"叶菜类","combination":2,"createTime":"2019-12-06 09:18:09.0","days":4,"endTime":"","entityId":352,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191206091809","packageDays":3,"payCount":15,"payType":3,"price":5,"productId":69372,"productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190730092617d0660310.jpg","productMaxPrice":-1,"productMinPrice":1,"productName":"包菜","productNickName":"","productNum":3,"productUnit":"","putTime":"2019-12-06","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"startTime":"","status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","suspendTime":"","transactionNo":"gg_0003_8jZNFP20191206091812997","typeDetail":"","updateTime":"","userRemark":""}
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
         * advertisementBgUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1912061105472fe1d458.png
         * advertisementDetailId : 352
         * advertisementOrderId : 195
         * advertisementPackageId : 63
         * advertisementPlanCode : 195693721
         * advertisementTypeId : 15
         * advertisementTypeName : 顶部banner
         * categoryName : 叶菜类
         * categoryParentName : 叶菜类
         * combination : 2
         * createTime : 2019-12-06 09:18:09.0
         * days : 4
         * endTime :
         * entityId : 352
         * giftDays : 1
         * isFinishd : 1
         * orderCode : gg2716-20191206091809
         * packageDays : 3
         * payCount : 15
         * payType : 3
         * price : 5
         * productId : 69372
         * productImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190730092617d0660310.jpg
         * productMaxPrice : -1
         * productMinPrice : 1
         * productName : 包菜
         * productNickName :
         * productNum : 3
         * productUnit :
         * putTime : 2019-12-06
         * refuseRemark :
         * remark :
         * rowValue : 5
         * stage : 1
         * stageType : 1
         * startTime :
         * status : 1
         * storeId : 2716
         * storeName : 测试环境店铺
         * storePhone : 12345678009
         * suspendTime :
         * transactionNo : gg_0003_8jZNFP20191206091812997
         * typeDetail :
         * updateTime :
         * userRemark :
         */
        private String advertisementTypeName;
        private String advertisementPlanCode;
        private String createTime;
        private int days;
        private int giftDays;
        private String orderCode;
        private int packageDays;
        private BigDecimal payCount;
        private String putTime;
        private int stage;
        private int stageType;
        private FKInfo info;
        private BigDecimal money;

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public String getAdvertisementPlanCode() {
            return advertisementPlanCode;
        }

        public void setAdvertisementPlanCode(String advertisementPlanCode) {
            this.advertisementPlanCode = advertisementPlanCode;
        }

        public FKInfo getInfo() {
            return info;
        }

        public void setInfo(FKInfo info) {
            this.info = info;
        }

        public String getAdvertisementTypeName() {
            return advertisementTypeName;
        }

        public void setAdvertisementTypeName(String advertisementTypeName) {
            this.advertisementTypeName = advertisementTypeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getGiftDays() {
            return giftDays;
        }

        public void setGiftDays(int giftDays) {
            this.giftDays = giftDays;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getPackageDays() {
            return packageDays;
        }

        public void setPackageDays(int packageDays) {
            this.packageDays = packageDays;
        }

        public BigDecimal getPayCount() {
            return payCount;
        }

        public void setPayCount(BigDecimal payCount) {
            this.payCount = payCount;
        }

        public String getPutTime() {
            return putTime;
        }

        public void setPutTime(String putTime) {
            this.putTime = putTime;
        }

        public int getStage() {
            return stage;
        }

        public void setStage(int stage) {
            this.stage = stage;
        }

        public int getStageType() {
            return stageType;
        }

        public void setStageType(int stageType) {
            this.stageType = stageType;
        }

      public   static class FKInfo{

            /**
             * closeTime : 2019-12-08 23:59:59
             * createTime : 2019-12-05 13:44:52
             * createUserId : 0
             * id : 13
             * item : 迟到
             * money : 70
             * orderNo : 1575524692492
             * punish : 关店1周，并在群里通报批评
             * storeId : 20
             */

            private String closeTime;
            private String createTime;
            private int createUserId;
            private int id;
            private String item;
            private BigDecimal money;
            private String orderNo;
            private String punish;
            private int storeId;

            public String getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(String closeTime) {
                this.closeTime = closeTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public BigDecimal getMoney() {
                return money;
            }

            public void setMoney(BigDecimal money) {
                this.money = money;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getPunish() {
                return punish;
            }

            public void setPunish(String punish) {
                this.punish = punish;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }
        }
    }

}
