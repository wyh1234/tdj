package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-11-24.
 */

public class CheckBoxPositionEvent {
    int position;

    public CheckBoxPositionEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
