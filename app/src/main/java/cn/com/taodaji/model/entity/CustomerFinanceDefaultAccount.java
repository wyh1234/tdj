package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-05-11.
 */
public class CustomerFinanceDefaultAccount {

    /**
     * err : 0
     * data : {"entityId":2,"createTime":"2017-05-10 17:39","updateTime":"2017-05-10 17:39","status":1,"provinceId":13,"provinceName":"湖北","cityId":193,"cityName":"襄阳","customerId":10,"hotelName":"襄阳黄牛庄","bankType":1,"isDefault":1,"bankName":"支付宝","accountNo":"8621 **** **** .com","ownerName":"XXX","bankAddress":"襄阳樊城区"}
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
         * entityId : 2
         * createTime : 2017-05-10 17:39
         * updateTime : 2017-05-10 17:39
         * status : 1
         * provinceId : 13
         * provinceName : 湖北
         * cityId : 193
         * cityName : 襄阳
         * customerId : 10
         * hotelName : 襄阳黄牛庄
         * bankType : 1
         * isDefault : 1
         * bankName : 支付宝
         * accountNo : 8621 **** **** .com
         * ownerName : XXX
         * bankAddress : 襄阳樊城区
         */

        private int entityId;
        private String createTime;
        private String updateTime;
        private int status;
        private int provinceId;
        private String provinceName;
        private int cityId;
        private String cityName;
        private int customerId;
        private String hotelName;
        private int bankType;
        private int isDefault;
        private String bankName;
        private String accountNo;
        private String ownerName;
        private String bankAddress;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public int getBankType() {
            return bankType;
        }

        public void setBankType(int bankType) {
            this.bankType = bankType;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }
    }
}
