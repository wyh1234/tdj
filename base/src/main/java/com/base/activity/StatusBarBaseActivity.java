package com.base.activity;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.base.utils.JavaMethod;
import com.stylingandroid.prism.Prism;
import com.base.utils.UIUtils;
import com.stylingandroid.prism.Setter;
import com.stylingandroid.prism.Trigger;
import com.stylingandroid.prism.filter.Filter;
import com.stylingandroid.prism.filter.SystemChromeFilter;
import com.stylingandroid.prism.filter.TintFilter;
import com.stylingandroid.prism.setter.BaseSetter;
import com.stylingandroid.prism.viewpager.ViewPagerTrigger;

import java.util.ArrayList;

/**
 * 继承该类的子类，可以设置statusBar的颜色
 */
public abstract class StatusBarBaseActivity extends BaseActivity {


    protected Prism prism;

    protected void initPrism() {
//        Trigger trigger = ViewPagerTrigger.newInstance(mViewPager, mAdapter);
//        prism = Prism.Builder.newInstance().add(trigger).background(getWindow(),tint).background(mViewPager, tint).build();
        prism = Prism.Builder.newInstance().background(getWindow()).build();
    }


    @Override
    protected void initView() {
        initPrism();
        hookPrism();
    }

    public Prism getPrism() {
        if (prism == null) initPrism();
        return prism;
    }

    //prism设置setStatusBarColor   和自动设置的在5.X版本是一样的
    public void setStatusBarColor(@ColorRes int colour) {
        if (prism != null) {
            prism.setColour(UIUtils.getColor(colour));
        }
    }

    public void setStatusBarColorInt(@ColorInt int colour) {
        if (prism != null) {
            prism.setColour(colour);
        }
    }

    private boolean isLoadingsucc = false;

    public void setStatusBarDrawableRes(@DrawableRes int drawableRes) {
        int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
        View statusBarView = getWindow().findViewById(identifier);
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(drawableRes);
        } else {
            if (!isLoadingsucc) {
                isLoadingsucc = true;
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                setStatusBarDrawableRes(drawableRes);
                            }
                        });
                        return false;
                    }
                });
            }
        }
    }


    private void setSystemColor(){
        ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        View statusBarView = new View(getApplicationContext());
        int statusBarHeight = getStatusBarHeight(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.parseColor("#2ecc71"));
        decorViewGroup.addView(statusBarView);
    }

    private int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * android6.0以后可以对状态栏文字颜色和图标进行修改
     * StatusBar 字体的颜色
     *
     * @param isTint 是否是浅色
     */

    public void setStatusBarForeColorColor(boolean isTint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isTint) {
                //设置状态栏文字颜色及图标为浅色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                //设置状态栏文字颜色及图标为浅色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    /**
     * 去掉更改导航栏的颜色
     */
    public void hookPrism() {
        ArrayList<Setter> setters = JavaMethod.getFieldValue(prism, "setters");
        if (setters != null) {
            for (int i = setters.size() - 1; i >= 0; i--) {
                if (setters.get(i).getClass().getSimpleName().equals("SystemChromeSetter")) {
                    setters.set(i, new SystemChromeSetterProxy(setters.get(i)));
                }
            }

        }
    }


    private static class SystemChromeSetterProxy extends BaseSetter {

        private Object SystemChromeSetter;

        protected SystemChromeSetterProxy(Filter filter) {
            super(filter);
        }

        protected SystemChromeSetterProxy(Filter filter, boolean transientChanges) {
            super(filter, transientChanges);
        }

        public SystemChromeSetterProxy(Object SystemChromeSetter) {
//            this(new SystemChromeFilter());
            this(new TintFilter(0f));
            this.SystemChromeSetter = SystemChromeSetter;
        }

        @Override
        public void onSetColour(int colour) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JavaMethod.getMethodValue(SystemChromeSetter, "setStatusBarColour", int.class, colour);
            }
        }
    }


}

