package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class TfAdvertisement {


    /**
     * err : 0
     * data : {"total":11,"items":[{"advertisementDetailId":256,"advertisementOrderId":121,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017070269420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 17:07:02.0","days":-1,"entityId":256,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170702","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":257,"advertisementOrderId":121,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017070269369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:07:02.0","days":-1,"entityId":257,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170702","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":258,"advertisementOrderId":122,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017091469420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 17:09:14.0","days":-1,"entityId":258,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170914","packageDays":3,"payCount":5,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":1,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":248,"advertisementOrderId":117,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113016573969378","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 16:57:39.0","days":5,"entityId":248,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130165739","packageDays":1,"payCount":20,"payType":0,"price":4,"productId":69378,"productName":"包菜","productNickName":"1","productNum":1,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":251,"advertisementOrderId":119,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113017014869379","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:01:48.0","days":5,"entityId":251,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130170148","packageDays":1,"payCount":40,"payType":0,"price":4,"productId":69379,"productName":"包菜","productNickName":"2","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":252,"advertisementOrderId":119,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113017014869380","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:01:48.0","days":5,"entityId":252,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130170148","packageDays":1,"payCount":40,"payType":0,"price":4,"productId":69380,"productName":"包菜","productNickName":"3","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":249,"advertisementOrderId":118,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113016585269420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 16:58:52.0","days":-1,"entityId":249,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130165852","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":250,"advertisementOrderId":118,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113016585269369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 16:58:52.0","days":-1,"entityId":250,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130165852","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":253,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":253,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":254,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469372","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":254,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69372,"productName":"包菜","productNickName":"","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":255,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469373","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":255,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69373,"productName":"包菜","productNickName":"","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""}],"pn":1,"ps":10}
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
         * total : 11
         * items : [{"advertisementDetailId":256,"advertisementOrderId":121,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017070269420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 17:07:02.0","days":-1,"entityId":256,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170702","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":257,"advertisementOrderId":121,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017070269369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:07:02.0","days":-1,"entityId":257,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170702","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":258,"advertisementOrderId":122,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017091469420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 17:09:14.0","days":-1,"entityId":258,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170914","packageDays":3,"payCount":5,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":1,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":248,"advertisementOrderId":117,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113016573969378","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 16:57:39.0","days":5,"entityId":248,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130165739","packageDays":1,"payCount":20,"payType":0,"price":4,"productId":69378,"productName":"包菜","productNickName":"1","productNum":1,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":251,"advertisementOrderId":119,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113017014869379","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:01:48.0","days":5,"entityId":251,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130170148","packageDays":1,"payCount":40,"payType":0,"price":4,"productId":69379,"productName":"包菜","productNickName":"2","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":252,"advertisementOrderId":119,"advertisementPackageId":61,"advertisementPlanCode":"2716-2019113017014869380","advertisementTypeId":14,"advertisementTypeName":"弹屏+顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:01:48.0","days":5,"entityId":252,"giftDays":0,"isFinishd":1,"orderCode":"gg2716-20191130170148","packageDays":1,"payCount":40,"payType":0,"price":4,"productId":69380,"productName":"包菜","productNickName":"3","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":20,"stage":1,"stageType":2,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":249,"advertisementOrderId":118,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113016585269420","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"根茎类","createTime":"2019-11-30 16:58:52.0","days":-1,"entityId":249,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130165852","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69420,"productName":"白萝卜","productNickName":"竹笋 整件批测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":250,"advertisementOrderId":118,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113016585269369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 16:58:52.0","days":-1,"entityId":250,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130165852","packageDays":3,"payCount":10,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":2,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":253,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469369","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":253,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69369,"productName":"包菜","productNickName":"测试测试测试","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":254,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469372","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":254,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69372,"productName":"包菜","productNickName":"","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""},{"advertisementDetailId":255,"advertisementOrderId":120,"advertisementPackageId":62,"advertisementPlanCode":"2716-2019113017031469373","advertisementTypeId":15,"advertisementTypeName":"顶部banner","categoryName":"叶菜类","createTime":"2019-11-30 17:03:14.0","days":-1,"entityId":255,"giftDays":1,"isFinishd":1,"orderCode":"gg2716-20191130170314","packageDays":3,"payCount":15,"payType":0,"price":5,"productId":69373,"productName":"包菜","productNickName":"","productNum":3,"putTime":"2019-11-30","refuseRemark":"","remark":"","rowValue":5,"stage":1,"stageType":1,"status":1,"storeId":2716,"storeName":"测试环境店铺","storePhone":"12345678009","transactionNo":"0","userRemark":""}]
         * pn : 1
         * ps : 10
         */

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

        public static class ItemsBean {
            /**
             * advertisementDetailId : 256
             * advertisementOrderId : 121
             * advertisementPackageId : 62
             * advertisementPlanCode : 2716-2019113017070269420
             * advertisementTypeId : 15
             * advertisementTypeName : 顶部banner
             * categoryName : 根茎类
             * createTime : 2019-11-30 17:07:02.0
             * days : -1
             * entityId : 256
             * giftDays : 1
             * isFinishd : 1
             * orderCode : gg2716-20191130170702
             * packageDays : 3
             * payCount : 10
             * payType : 0
             * price : 5
             * productId : 69420
             * productName : 白萝卜
             * productNickName : 竹笋 整件批测试
             * productNum : 2
             * putTime : 2019-11-30
             * refuseRemark :
             * remark :
             * rowValue : 5
             * stage : 1
             * stageType : 1
             * status : 1
             * storeId : 2716
             * storeName : 测试环境店铺
             * storePhone : 12345678009
             * transactionNo : 0
             * userRemark :
             */

            private int advertisementDetailId;
            private int advertisementOrderId;
            private int advertisementPackageId;
            private String advertisementPlanCode;
            private int advertisementTypeId;
            private String advertisementTypeName;
            private int combination;
            private String categoryName;
            private String createTime;
            private int days;
            private int entityId;
            private int giftDays;
            private int isFinishd;
            private String orderCode;
            private int packageDays;
            private int payCount;
            private int payType;
            private BigDecimal price;
            private int productId;
            private String productName;
            private String productNickName;
            private int productNum;
            private String putTime;
            private String refuseRemark;
            private String remark;
            private int rowValue;
            private int stage;
            private int stageType;
            private int status;
            private int storeId;
            private String storeName;
            private String storePhone;
            private String transactionNo;
            private String productImage;
            private String userRemark;
            private BigDecimal productMaxPrice;
            private BigDecimal productMinPrice;
            private String productUnit;
            private String advertisementBgUrl;
            private String endTime;
            private String startTime;
            private String suspendTime;
            private List<Specs> specs;
            private int limitDays;

            public int getLimitDays() {
                return limitDays;
            }

            public void setLimitDays(int limitDays) {
                this.limitDays = limitDays;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public int getCombination() {
                return combination;
            }

            public void setCombination(int combination) {
                this.combination = combination;
            }

            public String getProductUnit() {
                return productUnit;
            }

            public void setProductUnit(String productUnit) {
                this.productUnit = productUnit;
            }

            public BigDecimal getProductMinPrice() {
                return productMinPrice;
            }

            public void setProductMinPrice(BigDecimal productMinPrice) {
                this.productMinPrice = productMinPrice;
            }

            public List<Specs> getSpecs() {
                return specs;
            }

            public void setSpecs(List<Specs> specs) {
                this.specs = specs;
            }

            public BigDecimal getProductMaxPrice() {
                return productMaxPrice;
            }

            public void setProductMaxPrice(BigDecimal productMaxPrice) {
                this.productMaxPrice = productMaxPrice;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getSuspendTime() {
                return suspendTime;
            }

            public void setSuspendTime(String suspendTime) {
                this.suspendTime = suspendTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getAdvertisementBgUrl() {
                return advertisementBgUrl;
            }

            public void setAdvertisementBgUrl(String advertisementBgUrl) {
                this.advertisementBgUrl = advertisementBgUrl;
            }

            public int getAdvertisementDetailId() {
                return advertisementDetailId;
            }

            public void setAdvertisementDetailId(int advertisementDetailId) {
                this.advertisementDetailId = advertisementDetailId;
            }

            public int getAdvertisementOrderId() {
                return advertisementOrderId;
            }

            public void setAdvertisementOrderId(int advertisementOrderId) {
                this.advertisementOrderId = advertisementOrderId;
            }

            public int getAdvertisementPackageId() {
                return advertisementPackageId;
            }

            public void setAdvertisementPackageId(int advertisementPackageId) {
                this.advertisementPackageId = advertisementPackageId;
            }

            public String getAdvertisementPlanCode() {
                return advertisementPlanCode;
            }

            public void setAdvertisementPlanCode(String advertisementPlanCode) {
                this.advertisementPlanCode = advertisementPlanCode;
            }

            public int getAdvertisementTypeId() {
                return advertisementTypeId;
            }

            public void setAdvertisementTypeId(int advertisementTypeId) {
                this.advertisementTypeId = advertisementTypeId;
            }

            public String getAdvertisementTypeName() {
                return advertisementTypeName;
            }

            public void setAdvertisementTypeName(String advertisementTypeName) {
                this.advertisementTypeName = advertisementTypeName;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
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

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getGiftDays() {
                return giftDays;
            }

            public void setGiftDays(int giftDays) {
                this.giftDays = giftDays;
            }

            public int getIsFinishd() {
                return isFinishd;
            }

            public void setIsFinishd(int isFinishd) {
                this.isFinishd = isFinishd;
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

            public int getPayCount() {
                return payCount;
            }

            public void setPayCount(int payCount) {
                this.payCount = payCount;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
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

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getPutTime() {
                return putTime;
            }

            public void setPutTime(String putTime) {
                this.putTime = putTime;
            }

            public String getRefuseRemark() {
                return refuseRemark;
            }

            public void setRefuseRemark(String refuseRemark) {
                this.refuseRemark = refuseRemark;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getRowValue() {
                return rowValue;
            }

            public void setRowValue(int rowValue) {
                this.rowValue = rowValue;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public String getStorePhone() {
                return storePhone;
            }

            public void setStorePhone(String storePhone) {
                this.storePhone = storePhone;
            }

            public String getTransactionNo() {
                return transactionNo;
            }

            public void setTransactionNo(String transactionNo) {
                this.transactionNo = transactionNo;
            }

            public String getUserRemark() {
                return userRemark;
            }

            public void setUserRemark(String userRemark) {
                this.userRemark = userRemark;
            }

            public static class  Specs {
                private int level3Value;
                private String level3Unit;
                private double level2Value;
                private int levelType;
                private String level2Unit;

                public int getLevel3Value() {
                    return level3Value;
                }

                public void setLevel3Value(int level3Value) {
                    this.level3Value = level3Value;
                }

                public String getLevel3Unit() {
                    return level3Unit;
                }

                public void setLevel3Unit(String level3Unit) {
                    this.level3Unit = level3Unit;
                }

                public double getLevel2Value() {
                    return level2Value;
                }

                public void setLevel2Value(double level2Value) {
                    this.level2Value = level2Value;
                }

                public int getLevelType() {
                    return levelType;
                }

                public void setLevelType(int levelType) {
                    this.levelType = levelType;
                }

                public String getLevel2Unit() {
                    return level2Unit;
                }

                public void setLevel2Unit(String level2Unit) {
                    this.level2Unit = level2Unit;
                }
            }
        }
    }
}
