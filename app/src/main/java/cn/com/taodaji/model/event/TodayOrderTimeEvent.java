package cn.com.taodaji.model.event;

public class TodayOrderTimeEvent {
    private String sendTime;
    private boolean hasMore;

    public TodayOrderTimeEvent(String sendTime, boolean hasMore) {
        this.sendTime = sendTime;
        this.hasMore = hasMore;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
