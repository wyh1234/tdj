package cn.com.taodaji.model.event;

public class WaitCountEvent {
    private int count;

    public WaitCountEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
