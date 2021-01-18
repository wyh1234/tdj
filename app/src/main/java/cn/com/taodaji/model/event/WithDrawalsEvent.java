package cn.com.taodaji.model.event;

import java.math.BigDecimal;

public class WithDrawalsEvent {
    private BigDecimal money;
    private int type;

    public WithDrawalsEvent(int type, BigDecimal money) {
        this.type = type;
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public int getType() {
        return type;
    }
}
