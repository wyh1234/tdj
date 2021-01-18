package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IntegralShopCart {


    /**
     * err : 0
     * data : [{"guid":"3eb00b96-3228-42d1-8a87-766e43a585b1","userId":"b","createTime":"2019-08-14 14:50:26","goodsId":"14","quantity":1,"name":"M4A1","moneyPrice":120.283,"integralPrice":0,"salesPrice":80.123,"salesIntegral":131,"repertory":2,"status":1,"picUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png"}]
     * error : null
     * msg : Success
     * errorCode : null
     */

    private int err;
    private Object error;
    private String msg;
    private Object errorCode;
    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable {
        /**
         * guid : 3eb00b96-3228-42d1-8a87-766e43a585b1
         * userId : b
         * createTime : 2019-08-14 14:50:26
         * goodsId : 14
         * quantity : 1
         * name : M4A1
         * moneyPrice : 120.283
         * integralPrice : 0
         * salesPrice : 80.123
         * salesIntegral : 131
         * repertory : 2
         * status : 1
         * picUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190805132049f7117f84.png
         */
        private boolean b;
        private String guid;
        private String userId;
        private String createTime;
        private String goodsId;
        private int quantity;
        private String name;
        private BigDecimal moneyPrice;
        private int integralPrice;
        private BigDecimal salesPrice;
        private int salesIntegral;
        private int repertory;
        private int status;
        private String picUrl;
        private int sellType;

        public int getSellType() {
            return sellType;
        }

        public void setSellType(int sellType) {
            this.sellType = sellType;
        }

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getMoneyPrice() {
            return moneyPrice;
        }

        public void setMoneyPrice(BigDecimal moneyPrice) {
            this.moneyPrice = moneyPrice;
        }

        public int getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(int integralPrice) {
            this.integralPrice = integralPrice;
        }

        public BigDecimal getSalesPrice() {
            return salesPrice;
        }

        public void setSalesPrice(BigDecimal salesPrice) {
            this.salesPrice = salesPrice;
        }

        public int getSalesIntegral() {
            return salesIntegral;
        }

        public void setSalesIntegral(int salesIntegral) {
            this.salesIntegral = salesIntegral;
        }

        public int getRepertory() {
            return repertory;
        }

        public void setRepertory(int repertory) {
            this.repertory = repertory;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
