package cn.com.taodaji.model.event;

public class PickupEvent {
    int position;
    int type;
    int flag;

    public PickupEvent(int position, int type, int flag) {
        this.position = position;
        this.type = type;
        this.flag = flag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
