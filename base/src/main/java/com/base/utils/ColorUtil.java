package com.base.utils;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

public class ColorUtil {

    /**
     * 获取颜色
     */
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(UIUtils.getContext(), resId);
    }

    /**
     * 获取颜色选择
     */
    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return ContextCompat.getColorStateList(UIUtils.getContext(), resId);
    }

    /**
     * 获取颜色透明度
     */
    public static int getAlphaRes(@ColorRes int resId) {
        return getAlphaInt(getColor(resId));
    }

    /**
     * 获取颜色透明度
     */
    public static int getAlphaInt(@ColorInt int colorInt) {
        return Color.alpha(colorInt);
    }

    /**
     * 设置颜色透明度
     */
    @ColorInt
    public static int setColorAlpha(@ColorInt int colorInt, int alpha) {
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        return Color.argb(alpha, Color.red(colorInt), Color.green(colorInt), Color.blue(colorInt));
    }

    /**
     * 添加或减少颜色透明度
     */
    public static int changeAlpha(@ColorInt int colorInt, int alphaChanges) {
        return setColorAlpha(colorInt, Color.alpha(colorInt) + alphaChanges);
    }

    /**
     * 添加或减少颜色透明度
     */
    public static int changeAlphaRes(@ColorRes int colorInt, int alphaChanges) {
        return setColorAlpha(getColor(colorInt), Color.alpha(colorInt) + alphaChanges);
    }

    /**
     * 当前颜色透明度 的百分比  比如，蓝色30%透明度
     */
    public static int percentageAlpha(@ColorInt int colorInt, float percentage) {
        return setColorAlpha(colorInt, (int) (Color.alpha(colorInt) * percentage));
    }

    /**
     * 当前颜色透明度 的百分比  比如，蓝色30%透明度
     */
    public static int percentageAlphaRes(@ColorRes int colorRes, float percentage) {
        return setColorAlpha(getColor(colorRes), (int) (Color.alpha(getColor(colorRes)) * percentage));
    }


    // 合成新的颜色值
    public static int getNewColorByStartEndColor(Context context, float fraction, int startValue, int endValue) {
        return evaluate(fraction, context.getResources().getColor(startValue), context.getResources().getColor(endValue));
    }

    /**
     * 合成新的颜色值
     *
     * @param fraction   颜色取值的级别 (0.0f ~ 1.0f)
     * @param startValue 开始显示的颜色
     * @param endValue   结束显示的颜色
     * @return 返回生成新的颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) | ((startR + (int) (fraction * (endR - startR))) << 16) | ((startG + (int) (fraction * (endG - startG))) << 8) | ((startB + (int) (fraction * (endB - startB))));
    }
}
