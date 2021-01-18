package cn.com.taodaji.model.event;

public class BuyPackageEvent {
    int price;

    public BuyPackageEvent(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
