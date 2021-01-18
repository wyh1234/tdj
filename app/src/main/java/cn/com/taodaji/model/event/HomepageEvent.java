package cn.com.taodaji.model.event;

public class HomepageEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HomepageEvent(int position) {
        this.position = position;
    }
}
