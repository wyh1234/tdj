package cn.com.taodaji.model.entity;

public class YearPayWayInfo {
    private int num;
    private String y_money;
    private String x_money;
    private boolean b;

    public YearPayWayInfo(int num, String y_money, String x_money, boolean b) {
        this.num = num;
        this.y_money = y_money;
        this.x_money = x_money;
        this.b = b;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getY_money() {
        return y_money;
    }

    public void setY_money(String y_money) {
        this.y_money = y_money;
    }

    public String getX_money() {
        return x_money;
    }

    public void setX_money(String x_money) {
        this.x_money = x_money;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
