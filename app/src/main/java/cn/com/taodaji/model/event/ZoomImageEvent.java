package cn.com.taodaji.model.event;


public class ZoomImageEvent {
    private Object imageUrl;

    public ZoomImageEvent(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }
}
