package com.base.cycleViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * 自定义高度的viewpapger
 */
public class BaseViewPager extends ViewPager {

    public float hightState = 0;
    private boolean scrollable = true;
    private ScrollerCustomDuration mScroller = null;//滑动时间

    public BaseViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }


    public void setWidthDividedByHigh(float n) {
        hightState = n;
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @ enable
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    //自适应高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (hightState != 0) heightMeasureSpec = setHight_state(widthMeasureSpec, hightState);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int setHight_state(int widthMeasureSpec, float n) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int w = child.getMeasuredWidth();
            float h = (1.0f * w) / n;
            if (h > height) height = (int) h;
        }
        return MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return scrollable && super.onInterceptTouchEvent(event);
    }


    private void postInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }
}