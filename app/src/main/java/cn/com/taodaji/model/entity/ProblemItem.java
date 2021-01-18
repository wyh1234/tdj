package cn.com.taodaji.model.entity;

import java.io.Serializable;

public class ProblemItem implements Serializable {
    private int id;
    private String text;
    private boolean checked = false;
    private String nickname;
    private String num;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ProblemItem() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public ProblemItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public ProblemItem(int id, String text, boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
