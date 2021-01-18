package cn.com.taodaji.model.entity;


import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;

public class PurchaseFinanceRecord {
    @OnlyField
    private String month;
    private List<ItemBean> itemList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemBean {
        @OnlyField
        private int entityId;
        private int status;
        private int paymentMethod;
        private String extOrderId;
        private String paySymbol;//正负的符号
        private String paymentAmount;
        private Object image;
        private String description;
        private String weekDay;
        private String yearMonth;
        private BigDecimal balance;

        private int packOrderId;

        public int getPackOrderId() {
            return packOrderId;
        }

        public void setPackOrderId(int packOrderId) {
            this.packOrderId = packOrderId;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public String getPaySymbol() {
            return paySymbol;
        }

        public void setPaySymbol(String paySymbol) {
            this.paySymbol = paySymbol;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public String getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(String paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(String yearMonth) {
            this.yearMonth = yearMonth;
        }
    }
}
