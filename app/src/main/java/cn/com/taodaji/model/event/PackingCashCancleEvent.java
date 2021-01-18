package cn.com.taodaji.model.event;


public class PackingCashCancleEvent {


    private long afterSaleId;

    public PackingCashCancleEvent(long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public long getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }
}
