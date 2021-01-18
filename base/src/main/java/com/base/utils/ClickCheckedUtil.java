package com.base.utils;


public class ClickCheckedUtil {
    /**
     * 双击退出检测, 阈值 threShold  ms
     */
    public static long mLastClick = 0L;

    public static boolean doubleChecked(int threShold) {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < threShold;
        if (b) mLastClick = now;
        return b;
    }


    public static boolean onClickChecked(int threShold) {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick > threShold;
        if (b) mLastClick = now;
        return b;
    }


}
