package cn.com.taodaji.model.entity;

import java.math.BigDecimal;

public class PackingReturnMaxBean {
    /**
     * applyMaxNum : 2
     * foregift : 5
     * packOrderId : 1
     */

    private int applyMaxNum;
    private BigDecimal foregift;
    private long packOrderId;

    public int getApplyMaxNum() {
        return applyMaxNum;
    }

    public void setApplyMaxNum(int applyMaxNum) {
        this.applyMaxNum = applyMaxNum;
    }

    public BigDecimal getForegift() {
        return foregift;
    }

    public void setForegift(BigDecimal foregift) {
        this.foregift = foregift;
    }

    public long getPackOrderId() {
        return packOrderId;
    }

    public void setPackOrderId(long packOrderId) {
        this.packOrderId = packOrderId;
    }
}
