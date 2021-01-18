package cn.com.taodaji.model.entity;

public class GetPointsItem {
    private int icon;
    private String title;
    private String info;
    private String action;

    public GetPointsItem(int icon, String title, String info, String action) {
        this.icon = icon;
        this.title = title;
        this.info = info;
        this.action = action;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
