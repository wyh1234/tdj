package cn.com.taodaji.model.entity;

public class ShareRecord {
    private String name;
    private String phoneNum;
    private String action;
    private String date;
    private String points;

    public ShareRecord(String name, String phoneNum, String action, String date, String points) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.action = action;
        this.date = date;
        this.points = points;
    }

    public ShareRecord() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
