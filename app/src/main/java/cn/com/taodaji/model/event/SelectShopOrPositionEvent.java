package cn.com.taodaji.model.event;

/**
 * Created by zhaowenlong on 2019/3/7.
 */
public class SelectShopOrPositionEvent {
    private String currentSelected;
    private boolean flag; //True is shop ,False is position
    private int position;
    private String markCode;


    public SelectShopOrPositionEvent() {
    }

    public SelectShopOrPositionEvent(String currentSelected) {
        this.currentSelected = currentSelected;
    }

    public SelectShopOrPositionEvent(String currentSelected, boolean flag) {
        this.currentSelected = currentSelected;
        this.flag = flag;
    }

    public SelectShopOrPositionEvent(String currentSelected, boolean flag, int position) {
        this.currentSelected = currentSelected;
        this.flag = flag;
        this.position = position;
    }

    public String getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(String currentSelected) {
        this.currentSelected = currentSelected;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }
}
