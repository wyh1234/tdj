package cn.com.taodaji.model.event;

public class LoginOutEvent {
    private   boolean loginOut;

    public LoginOutEvent(boolean loginOut) {
        this.loginOut = loginOut;
    }

    public boolean isLoginOut() {
        return loginOut;
    }

    public void setLoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }
}
