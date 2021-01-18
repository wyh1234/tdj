package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2018-01-19.
 */

public class UmengTokenEvent {
    private String token;

    public UmengTokenEvent(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
