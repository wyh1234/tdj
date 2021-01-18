package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class SupplierCash {

    /**
     * entityId : 1
     * createTime : 2017-03-01 16:12
     * supplierId : 52
     * provinceName : 湖北
     * cityName : 襄阳
     * bankName : 建设银行
     * accountNo : 46787624763276423432
     * bankAddress : 春运路23号支行
     * capitalWithdrawal : 23
     */

    private int entityId;
    private String createTime;
    private int supplierId;
    private String provinceName;
    private String cityName;
    private String bankName;
    private String accountNo;
    private String bankAddress;
    private float capitalWithdrawal;

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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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
}

