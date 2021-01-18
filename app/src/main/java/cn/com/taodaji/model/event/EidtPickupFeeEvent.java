package cn.com.taodaji.model.event;

public class EidtPickupFeeEvent {
    private int fee;

    public EidtPickupFeeEvent(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
