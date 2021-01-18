package cn.com.taodaji.model.entity;

public class PickupDetailItem {
    private String price;
    private String info;
    private String date;
    private String balance;
    private int flag;

    public PickupDetailItem() {
    }

    public PickupDetailItem(String price, String info, String date, String balance, int flag) {
        this.price = price;
        this.info = info;
        this.date = date;
        this.balance = balance;
        this.flag = flag;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
