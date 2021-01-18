package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2017-08-18.
 */

public class FiltrateTransaction {
    private boolean selected;
    private String text;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
