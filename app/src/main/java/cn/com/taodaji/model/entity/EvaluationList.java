package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

/**
 * Created by Administrator on 2017-06-22.
 */

public class EvaluationList {

    /**
     * err : 0
     * data : {"total":8,"items":[{"createTime":"2017-06-14 17:01","creditContent":"风格和风格和风格很烦好风格和发挥发挥","creditImgs":"","creditLevel":3,"customerId":1,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg","customerName":"湖北黄牛庄","entityId":4,"nickName":"平包","orderId":3907,"orderItemId":9851,"orderNo":"100000072","price":0.4,"productId":591,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420144722c31b322d.jpeg","productName":"包菜","qty":10,"replyContent":"","replyTime":"","storeId":16,"totalPrice":4,"unit":"斤","isReply":1,"type":1},{"createTime":"2017-06-14 17:01","creditContent":"中评","creditImgs":"","creditLevel":2,"customerId":1,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg","customerName":"湖北黄牛庄","entityId":5,"nickName":"本地波菜","orderId":3907,"orderItemId":9852,"orderNo":"100000072","price":3.5,"productId":122,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032817141184bdcec1.jpg","productName":"菠菜","qty":10,"replyContent":"","replyTime":"","storeId":16,"totalPrice":35,"unit":"斤","isReply":0,"type":1}],"pn":1,"ps":2}
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
         * total : 8
         * items : [{"createTime":"2017-06-14 17:01","creditContent":"风格和风格和风格很烦好风格和发挥发挥","creditImgs":"","creditLevel":3,"customerId":1,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg","customerName":"湖北黄牛庄","entityId":4,"nickName":"平包","orderId":3907,"orderItemId":9851,"orderNo":"100000072","price":0.4,"productId":591,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420144722c31b322d.jpeg","productName":"包菜","qty":10,"replyContent":"","replyTime":"","storeId":16,"totalPrice":4,"unit":"斤","isReply":1,"type":1},{"createTime":"2017-06-14 17:01","creditContent":"中评","creditImgs":"","creditLevel":2,"customerId":1,"customerLogo":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg","customerName":"湖北黄牛庄","entityId":5,"nickName":"本地波菜","orderId":3907,"orderItemId":9852,"orderNo":"100000072","price":3.5,"productId":122,"productImg":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032817141184bdcec1.jpg","productName":"菠菜","qty":10,"replyContent":"","replyTime":"","storeId":16,"totalPrice":35,"unit":"斤","isReply":0,"type":1}]
         * pn : 1
         * ps : 2
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

        public static class ItemsBean implements Serializable{
            /**
             * createTime : 2017-06-14 17:01
             * creditContent : 风格和风格和风格很烦好风格和发挥发挥
             * creditImgs :
             * creditLevel : 3
             * customerId : 1
             * customerLogo : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170331101725484c8ec6.jpeg
             * customerName : 湖北黄牛庄
             * entityId : 4
             * nickName : 平包
             * orderId : 3907
             * orderItemId : 9851
             * orderNo : 100000072
             * price : 0.4
             * productId : 591
             * productImg : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420144722c31b322d.jpeg
             * productName : 包菜
             * qty : 10
             * replyContent :
             * replyTime :
             * storeId : 16
             * totalPrice : 4
             * unit : 斤
             * isReply : 1   //isReply : 0未回复，1已回复， 2超时不能回复
             * type : 1
             * isVirtual 0匿名1正常
             */

            private String createTime;
            private String creditContent="";
            private String creditImgs;
            private int creditLevel;   //1好评
            private int customerId;
            private String customerLogo;
            private String customerName;
            @OnlyField
            private int entityId;
            private String nickName;
            private int orderId;
            private int orderItemId;
            private String orderNo;
            private BigDecimal price;
            private int productId;
            private String productImg;
            private String productName;
            private int qty;
            private String replyContent;
            private String replyTime;
            private int storeId;
            private BigDecimal totalPrice;
            private String unit;
            private int isReply;
            private int type;
           /** isVirtual 0匿名1正常*/
            private int isVirtual;
            /** 修改次数*/
            private int modifyCount;

            private int productType;//0、普通的商品 1、Banner图的特价产品 2、供应商报名参加特价活动
            private int isP;
            private int productCriteria;//商品标准“1”：通货商品 “2”：精品商品 '

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public int getIsP() {
                return isP;
            }

            public void setIsP(int isP) {
                this.isP = isP;
            }

            public int getProductCriteria() {
                return productCriteria;
            }

            public void setProductCriteria(int productCriteria) {
                this.productCriteria = productCriteria;
            }

            public int getModifyCount() {
                return modifyCount;
            }

            public void setModifyCount(int modifyCount) {
                this.modifyCount = modifyCount;
            }

            public int getIsVirtual() {
                return isVirtual;
            }

            public void setIsVirtual(int isVirtual) {
                this.isVirtual = isVirtual;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreditContent() {
                return creditContent;
            }

            public void setCreditContent(String creditContent) {
                this.creditContent = creditContent;
            }

            public String getCreditImgs() {
                return creditImgs;
            }

            public void setCreditImgs(String creditImgs) {
                this.creditImgs = creditImgs;
            }

            public int getCreditLevel() {
                return creditLevel;
            }

            public void setCreditLevel(int creditLevel) {
                this.creditLevel = creditLevel;
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

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
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

            public int getOrderItemId() {
                return orderItemId;
            }

            public void setOrderItemId(int orderItemId) {
                this.orderItemId = orderItemId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
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

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
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

            public int getIsReply() {
                return isReply;
            }

            public void setIsReply(int isReply) {
                this.isReply = isReply;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
