package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-05-12.
 */
public class RechargeMoneyPayEvent {
    private float money;

    public RechargeMoneyPayEvent(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
