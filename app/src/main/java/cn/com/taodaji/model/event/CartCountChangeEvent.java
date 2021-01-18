package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-10-31.
 */

public class CartCountChangeEvent {
    int count;

    public CartCountChangeEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
