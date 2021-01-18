package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-06-03.
 */
public class CustomerCash {

    /**
     * err : 0
     * data : {"entityId":10,"createTime":"2017-06-02 21:35","customerId":76,"provinceName":"湖北","cityName":"襄阳","bankName":"工商银行","accountNo":"6228480759741921473","bankAddress":"三元路支行","capitalWithdrawal":100,"moneyType":2,"fee":0.8,"customerName":"张三","customerTel":"18727120758","hotelName":"淘大集","status":0,"updateTime":"2017-06-02 21:35","isWithdrawal":"N"}
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
         * entityId : 10
         * createTime : 2017-06-02 21:35
         * customerId : 76
         * provinceName : 湖北
         * cityName : 襄阳
         * bankName : 工商银行
         * accountNo : 6228480759741921473
         * bankAddress : 三元路支行
         * capitalWithdrawal : 100
         * moneyType : 2
         * fee : 0.8
         * customerName : 张三
         * customerTel : 18727120758
         * hotelName : 淘大集
         * status : 0
         * updateTime : 2017-06-02 21:35
         * isWithdrawal : N
         */

        private int entityId;
        private String createTime;
        private int customerId;
        private String provinceName;
        private String cityName;
        private String bankName;
        private String accountNo;
        private String bankAddress;
        private float capitalWithdrawal;
        private int moneyType;
        private double fee;
        private String customerName;
        private String customerTel;
        private String hotelName;
        private int status;
        private String updateTime;
        private String isWithdrawal;

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

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
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

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public float getCapitalWithdrawal() {
            return capitalWithdrawal;
        }

        public void setCapitalWithdrawal(float capitalWithdrawal) {
            this.capitalWithdrawal = capitalWithdrawal;
        }

        public int getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(int moneyType) {
            this.moneyType = moneyType;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerTel() {
            return customerTel;
        }

        public void setCustomerTel(String customerTel) {
            this.customerTel = customerTel;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getIsWithdrawal() {
            return isWithdrawal;
        }

        public void setIsWithdrawal(String isWithdrawal) {
            this.isWithdrawal = isWithdrawal;
        }
    }
}
