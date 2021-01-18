package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class WaitNoticeResultBean {
    /**
     * err : 0
     * msg : 处理成功
     * data : {"total":8,"items":[{"entityId":1,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":58.35,"createOrderTime":"2019-03-11 16:28:45","paymentSuccessTime":null,"extOrderId":"6510788425392590848"},{"entityId":32,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":28.4,"createOrderTime":"2019-03-17 13:03:51","paymentSuccessTime":null,"extOrderId":"6512911187909283840"},{"entityId":33,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":152.9,"createOrderTime":"2019-03-17 13:04:12","paymentSuccessTime":null,"extOrderId":"6512911274689433600"},{"entityId":34,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":88.4,"createOrderTime":"2019-03-17 13:07:59","paymentSuccessTime":null,"extOrderId":"6512912227719188480"},{"entityId":35,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":102.5,"createOrderTime":"2019-03-17 13:08:19","paymentSuccessTime":null,"extOrderId":"6512912312662233088"},{"entityId":57,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":122.05,"createOrderTime":"2019-03-25 11:17:57","paymentSuccessTime":null,"extOrderId":"6515783637592444928"},{"entityId":59,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":73.9,"createOrderTime":"2019-03-25 11:45:52","paymentSuccessTime":null,"extOrderId":"6515790964508856320"},{"entityId":60,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":36.95,"createOrderTime":"2019-03-25 11:48:02","paymentSuccessTime":null,"extOrderId":"6515791210206990336"}]}
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
         * total : 8
         * items : [{"entityId":1,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":58.35,"createOrderTime":"2019-03-11 16:28:45","paymentSuccessTime":null,"extOrderId":"6510788425392590848"},{"entityId":32,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":28.4,"createOrderTime":"2019-03-17 13:03:51","paymentSuccessTime":null,"extOrderId":"6512911187909283840"},{"entityId":33,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":152.9,"createOrderTime":"2019-03-17 13:04:12","paymentSuccessTime":null,"extOrderId":"6512911274689433600"},{"entityId":34,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":88.4,"createOrderTime":"2019-03-17 13:07:59","paymentSuccessTime":null,"extOrderId":"6512912227719188480"},{"entityId":35,"affairType":1,"affairTitle":"待付款（需你付款）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":102.5,"createOrderTime":"2019-03-17 13:08:19","paymentSuccessTime":null,"extOrderId":"6512912312662233088"},{"entityId":57,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":122.05,"createOrderTime":"2019-03-25 11:17:57","paymentSuccessTime":null,"extOrderId":"6515783637592444928"},{"entityId":59,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":73.9,"createOrderTime":"2019-03-25 11:45:52","paymentSuccessTime":null,"extOrderId":"6515790964508856320"},{"entityId":60,"affairType":2,"affairTitle":"待付款（已关闭）","customerName":"淘大集 李坤18727120758","customerId":6331,"originatorName":"开心酒店","originatorRole":0,"capitalAmount":36.95,"createOrderTime":"2019-03-25 11:48:02","paymentSuccessTime":null,"extOrderId":"6515791210206990336"}]
         */
        private int count;

        private int total;
        private List<ItemsBean> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean  implements Serializable {
            /**
             * entityId : 1
             * affairType : 2
             * affairTitle : 待付款（已关闭）
             * customerName : 淘大集 李坤18727120758
             * customerId : 6331
             * originatorName : 开心酒店
             * originatorRole : 0
             * capitalAmount : 58.35
             * createOrderTime : 2019-03-11 16:28:45
             * paymentSuccessTime : null
             * extOrderId : 6510788425392590848
             */

            private int entityId;
            private int affairType;
            private String affairTitle;
            private String customerName;
            private int customerId;
            private String originatorName;
            private int originatorRole;
            private BigDecimal capitalAmount = BigDecimal.ZERO;
            private String createOrderTime;
            private String paymentSuccessTime;
            private String extOrderId;

            private String startNameAndRole="";
            private String payNameAndRole="";

            private String  handleCreateTime="";
            private String outTradeNo="";
            private String orderIds="";
            /**
             * messageType : 1
             * messageTitle : 付款超时订单关闭
             * capitalAmount : 5.6
             * originatorCustomerId : 6331
             * paymentCustomerId : 6331
             * paymentCustomerName : 淘大集 李坤18727120758
             * paymentCustomerRole : 0
             * paymentSuccessTime : null
             * handleWithdrawalTime : null
             * afterSalesApplicationNo : null
             * afterSalesApplicationCreateTime : null
             * afterSalesApplicationFinishTime : null
             */
//1.订单/付款成功
//2.订单/付款超时订单关闭
//3.提现/提现成功
//4.售后申请/售后申请成功
//5.售后完成
            private int messageType;
            private String messageTitle;

            private int originatorCustomerId;
            private int paymentCustomerId;
            private String paymentCustomerName;
            private int paymentCustomerRole;

            private String handleWithdrawalTime;
            private String afterSalesApplicationNo;
            private String afterSalesApplicationCreateTime;
            private String afterSalesApplicationFinishTime;

            private String orderClosedTime;

            private int   isRead;

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(String outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public String getOrderIds() {
                return orderIds;
            }

            public void setOrderIds(String orderIds) {
                this.orderIds = orderIds;
            }

            public String getHandleCreateTime() {
                return handleCreateTime;
            }

            public void setHandleCreateTime(String handleCreateTime) {
                this.handleCreateTime = handleCreateTime;
            }

            public String getOrderClosedTime() {
                return orderClosedTime;
            }

            public void setOrderClosedTime(String orderClosedTime) {
                this.orderClosedTime = orderClosedTime;
            }

            public String getStartNameAndRole() {
                return startNameAndRole;
            }

            public void setStartNameAndRole(String startNameAndRole) {
                this.startNameAndRole = startNameAndRole;
            }

            public String getPayNameAndRole() {
                return payNameAndRole;
            }

            public void setPayNameAndRole(String payNameAndRole) {
                this.payNameAndRole = payNameAndRole;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getAffairType() {
                return affairType;
            }

            public void setAffairType(int affairType) {
                this.affairType = affairType;
            }

            public String getAffairTitle() {
                return affairTitle;
            }

            public void setAffairTitle(String affairTitle) {
                this.affairTitle = affairTitle;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getOriginatorName() {
                return originatorName;
            }

            public void setOriginatorName(String originatorName) {
                this.originatorName = originatorName;
            }

            public int getOriginatorRole() {
                return originatorRole;
            }

            public void setOriginatorRole(int originatorRole) {
                this.originatorRole = originatorRole;
            }

            public BigDecimal getCapitalAmount() {
                return capitalAmount;
            }

            public void setCapitalAmount(BigDecimal capitalAmount) {
                this.capitalAmount = capitalAmount;
            }

            public String getCreateOrderTime() {
                return createOrderTime;
            }

            public void setCreateOrderTime(String createOrderTime) {
                this.createOrderTime = createOrderTime;
            }

            public String getPaymentSuccessTime() {
                return paymentSuccessTime;
            }

            public void setPaymentSuccessTime(String paymentSuccessTime) {
                this.paymentSuccessTime = paymentSuccessTime;
            }

            public String getExtOrderId() {
                return extOrderId;
            }

            public void setExtOrderId(String extOrderId) {
                this.extOrderId = extOrderId;
            }

            public int getMessageType() {
                return messageType;
            }

            public void setMessageType(int messageType) {
                this.messageType = messageType;
            }

            public String getMessageTitle() {
                return messageTitle;
            }

            public void setMessageTitle(String messageTitle) {
                this.messageTitle = messageTitle;
            }


            public int getOriginatorCustomerId() {
                return originatorCustomerId;
            }

            public void setOriginatorCustomerId(int originatorCustomerId) {
                this.originatorCustomerId = originatorCustomerId;
            }

            public int getPaymentCustomerId() {
                return paymentCustomerId;
            }

            public void setPaymentCustomerId(int paymentCustomerId) {
                this.paymentCustomerId = paymentCustomerId;
            }

            public String getPaymentCustomerName() {
                return paymentCustomerName;
            }

            public void setPaymentCustomerName(String paymentCustomerName) {
                this.paymentCustomerName = paymentCustomerName;
            }

            public int getPaymentCustomerRole() {
                return paymentCustomerRole;
            }

            public void setPaymentCustomerRole(int paymentCustomerRole) {
                this.paymentCustomerRole = paymentCustomerRole;
            }


            public String getHandleWithdrawalTime() {
                return handleWithdrawalTime;
            }

            public void setHandleWithdrawalTime(String handleWithdrawalTime) {
                this.handleWithdrawalTime = handleWithdrawalTime;
            }

            public String getAfterSalesApplicationNo() {
                return afterSalesApplicationNo;
            }

            public void setAfterSalesApplicationNo(String afterSalesApplicationNo) {
                this.afterSalesApplicationNo = afterSalesApplicationNo;
            }

            public String getAfterSalesApplicationCreateTime() {
                return afterSalesApplicationCreateTime;
            }

            public void setAfterSalesApplicationCreateTime(String afterSalesApplicationCreateTime) {
                this.afterSalesApplicationCreateTime = afterSalesApplicationCreateTime;
            }

            public String getAfterSalesApplicationFinishTime() {
                return afterSalesApplicationFinishTime;
            }

            public void setAfterSalesApplicationFinishTime(String afterSalesApplicationFinishTime) {
                this.afterSalesApplicationFinishTime = afterSalesApplicationFinishTime;
            }
        }
    }
}
