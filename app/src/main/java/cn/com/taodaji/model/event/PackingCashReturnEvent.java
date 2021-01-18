package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-06-02.
 */
public class PackingCashReturnEvent {


    private long afterSaleId;

    public PackingCashReturnEvent(long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public long getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }
}
