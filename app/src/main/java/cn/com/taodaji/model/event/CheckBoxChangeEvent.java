package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-11-24.
 */

public class CheckBoxChangeEvent {
    Boolean isChecked;
    int position;


    public CheckBoxChangeEvent(Boolean isChecked, int position) {
        this.isChecked = isChecked;
        this.position = position;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
