package cn.com.taodaji.model.event;

public class SupplyAskNewSaleTypeEvent {
    private boolean sucess;

    public SupplyAskNewSaleTypeEvent(boolean sucess) {
        this.sucess = sucess;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
}
