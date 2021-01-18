package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2018-02-05.
 */

public class NotificationStartActivityEvent {
    public Class activityClass;

    public NotificationStartActivityEvent(Class activityClass) {
        this.activityClass = activityClass;
    }
}
