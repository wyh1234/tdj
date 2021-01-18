package cn.com.taodaji.model.event;

public class WaitEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public WaitEvent(int position) {
        this.position = position;
    }
}
