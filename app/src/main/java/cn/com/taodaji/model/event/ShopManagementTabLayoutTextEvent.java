package cn.com.taodaji.model.event;

/**
 * Created by yangkuo on 2018/11/12.
 */
public class ShopManagementTabLayoutTextEvent {
    private int count;
    private String title;
    private int index;

    public ShopManagementTabLayoutTextEvent(int count, String title, int index) {
        this.count = count;
        this.title = title;
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
