package com.base.event;

/**
 * Created by yangkuo on 2018-10-22.
 */
public class VersionEvent {
    boolean isTips;

    public VersionEvent(boolean isTips) {
        this.isTips = isTips;
    }

    public boolean isTips() {
        return isTips;
    }

    public void setTips(boolean tips) {
        isTips = tips;
    }
}
