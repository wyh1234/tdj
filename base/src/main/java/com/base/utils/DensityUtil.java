package com.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DensityUtil {

    private static Resources sRes = Resources.getSystem();
    private static int sDensityDpi = sRes.getDisplayMetrics().densityDpi;
    private static float sScaledDensity = sRes.getDisplayMetrics().scaledDensity;

    public static int dp2px(float dip) {
        final float scale = sDensityDpi;
        return (int) (dip * (scale / 160) + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = sDensityDpi;
        return (int) ((px * 160) / scale + 0.5f);
    }

    public static int sp2px(float sp) {
        float spValue = sp * sScaledDensity;
        return (int) (spValue + 0.5f);
    }

    public static int px2sp(float px) {
        final float scale = sScaledDensity;
        return (int) (px / scale + 0.5f);
    }

    public static int dimenPixelSize(@DimenRes int id) {
        return UIUtils.getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * getLocationInWindow   getLocationOnScreen得到绝对位置的方法只在有弹出窗时会有区别。
     *
     * @param view
     * @return
     */
    public static Rect getViewRectInWindow(View view) {
        int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);
        int viewX = viewLocation[0]; // x 坐标
        int viewY = viewLocation[1]; // y 坐标
        return new Rect(viewX, viewY, viewX + view.getWidth(), viewY + view.getHeight());
    }

    public static Rect getViewRectOnScreen(View view) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int viewX = viewLocation[0]; // x 坐标
        int viewY = viewLocation[1]; // y 坐标
        return new Rect(viewX, viewY, viewX + view.getWidth(), viewY + view.getHeight());
    }


    public static int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeightPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    public static boolean checkIsVisible(Context context, View view) {
        Point point = getScreenMetrics(context);
        // 如果已经加载了，判断view是否显示出来，然后曝光
        Rect rect = new Rect(0, 0, point.x, point.y);
//        int[] location = new int[2];
//        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            //view已不在屏幕可见区域;
            return false;
        }
    }


    public static float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

    public static void setViewSize(View view, int x, int y) {
        ViewGroup.LayoutParams laParams = view.getLayoutParams();
        if (laParams != null) {
            laParams.width = x;
            laParams.height = y;
        } else {
            laParams = new ViewGroup.LayoutParams(x, y);
        }
        view.setLayoutParams(laParams);
    }

    public static void setViewHight(View view, int y) {
        ViewGroup.LayoutParams laParams = view.getLayoutParams();
        if (laParams != null) {
            laParams.height = y;
        } else {
            laParams = new ViewGroup.LayoutParams(view.getMinimumWidth(), y);
        }
        view.setLayoutParams(laParams);
    }

}