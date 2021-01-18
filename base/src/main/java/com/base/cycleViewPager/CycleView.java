package com.base.cycleViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.base.R;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.listener.OnCustomerItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * 实现可循环，可轮播的viewpager
 */
public class CycleView implements OnPageChangeListener, View.OnTouchListener {

    private List<ImageView> imageViews = new ArrayList<>();
    private ImageView[] indicators;
    private LinearLayout indicatorLayout; // 指示�?
    private BaseViewPager viewPager;
    private BaseViewPager parentViewPager;
    private ViewPagerAdapter adapter;
    private Handler handler;
    private int time = 5000; // 默认轮播时间
    private int currentPosition = 0; // 轮播当前位置
    private boolean isScrolling = false; // 滚动框是否滚动着
    private boolean isCycle = false; // 是否循环
    private boolean isWheel = false; // 是否轮播
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松�?后短时间进行切换
    private int WHEEL = 100; // 转动
    private int WHEEL_WAIT = 101; // 等待
    private List<ADInfo> infos;
    private int mLastPosition;
    private OnCustomerItemClickListener<ADInfo> listener;
    public float hightState = 1;
    public GlideImageView right_icon;
    private Context mContext;

    public CycleView(Context context) {
        mContext = context;
    }

    protected View mainView;

    //创建窗口
    @SuppressLint("HandlerLeak")
    public View initView() {
        mainView = ViewUtils.getLayoutView(mContext, R.layout.view_cycle_viewpager_contet);

        int w = UIUtils.getScreenWidthPixels();
        int y = (int) (w / hightState);
        UIUtils.setViewSize(mainView, w, y);

        right_icon = ViewUtils.findViewById(mainView, R.id.right_icon);
        viewPager = (BaseViewPager) mainView.findViewById(R.id.viewPager);
        // viewPager.setPageTransformer(true, new DepthPageTransformer());//设滑动动画
        viewPager.setScrollDurationFactor(3);//设滑动时间
        viewPager.setOnTouchListener(this);
        indicatorLayout = (LinearLayout) mainView.findViewById(R.id.viewpager_indicator);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == WHEEL && imageViews.size() != 0) {
                    if (!isScrolling) {
                        viewPager.setCurrentItem(currentPosition + 1, true);
                        start();
                    }
                    return;
                }
                if (msg.what == WHEEL_WAIT && imageViews.size() != 0) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                }
            }
        };

        return mainView;
    }

    public void setindicatorGone() {
        indicatorLayout.setVisibility(View.GONE);
    }

    public void setData(List<ImageView> views, List<ADInfo> list) {
        setData(views, list, 0);
    }

    public void setOnCustomerItemClickListener(OnCustomerItemClickListener<ADInfo> listener) {
        this.listener = listener;
    }

    public void setRight_icon(Object obj) {
        ImageLoaderUtils.loadImage(right_icon, obj,false);

    }

    public ImageView getRight_icon() {
        return right_icon;
    }

    /**
     * 初始化viewpager
     *
     * @param views        要显示的views
     * @param showPosition 默认显示位置
     */
    public void setData(List<ImageView> views, List<ADInfo> list, int showPosition) {
        viewPager.hightState = hightState;
        infos = list;
        this.imageViews.clear();

        if (views.size() == 0) {
            mainView.setVisibility(View.GONE);
            return;
        } else mainView.setVisibility(View.VISIBLE);

        mLastPosition = list.size() - 1;
        this.imageViews.addAll(views);

        int ivSize = views.size();

        // 设置指示�?
        if (isCycle) indicators = new ImageView[ivSize - 2];
        else indicators = new ImageView[ivSize];

        indicatorLayout.removeAllViews();
        int margin = UIUtils.getDimens(R.dimen.indicator_margin);
        int size = UIUtils.getDimens(R.dimen.indicator_size);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
        lp.setMarginStart(margin);

        for (int i = 0; i < indicators.length; i++) {
            GlideImageView image = ViewUtils.getImageView(mContext);
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.s_indicators);
            image.setImageDrawable(drawable);
            image.setLayoutParams(lp);
            indicators[i] = image;
            indicatorLayout.addView(image);
        }

        adapter = new ViewPagerAdapter();

        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);

        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        if (showPosition < 0 || showPosition >= views.size()) showPosition = 0;
        if (isCycle) {
            showPosition = showPosition + 1;
        }
        viewPager.setCurrentItem(showPosition);
        currentPosition = showPosition;
    }

    /**
     * 设置指示器居中，默认指示器在右方
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorLayout.setLayoutParams(params);
    }

    /**
     * 是否循环，默认不�?启，�?启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public void setHightstate(float hightstate) {
        this.hightState = hightstate;
    }

    public void start() {
        releaseTime = System.currentTimeMillis();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }

    /**
     * 是否处于循环状�??
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * 设置是否轮播，默认不轮播,轮播�?定是循环�?
     *
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isCycle = isWheel;
        if (isWheel) {
            handler.postDelayed(runnable, time);
        }
    }

    /**
     * 是否处于轮播状�??
     *
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Activity activity = (Activity) mContext;
            if (activity != null && !activity.isFinishing() && isWheel) {
                long now = System.currentTimeMillis();
                // �?测上�?次滑动时间与本次之间是否有触�?(手滑�?)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
    public void releaseHeight() {
        mainView.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        refreshData();
    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下�?张视�?.默认5000ms
     *
     * @param time 毫秒为单�?
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    /**
     * 隐藏CycleViewPager
     */
    public void hide() {
        mainView.setVisibility(View.GONE);
    }

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    public BaseViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            stop();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            start();
        }

        return false;
    }

    /**
     * 页面适配�? 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            ImageView v = imageViews.get(position);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (infos.size() == 0) return;
                    int po;
                    if (isCycle) po = position - 1;
                    else po = position;

                    if (po == infos.size()) return;
                    if (listener != null && getListBean(po) != null)
                        listener.onCustomerItemClick(v, po, getListBean(po));
                }
            });
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public ADInfo getListBean(int position) {
        if (position > -1 && position < infos.size()) return infos.get(position);
        return null;
    }

    public List<ADInfo> getList() {
        return infos;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) {
            // viewPager在滚�?
            isScrolling = true;
            return;
        } else if (arg0 == 0) {
            // viewPager滚动结束
            if (parentViewPager != null) parentViewPager.setScrollable(true);
            currentPosition = viewPager.getCurrentItem();
            releaseTime = System.currentTimeMillis();

            if (imageViews.size() > 3 && isCycle()) {
                if (currentPosition == 0) {
                    viewPager.setCurrentItem(imageViews.size() - 2, false);
                    currentPosition = imageViews.size() - 2;
                } else if (currentPosition == imageViews.size() - 1) {
                    viewPager.setCurrentItem(1, false);
                    currentPosition = 1;
                }
            }
        }
        isScrolling = false;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        if (isCycle) {
            if (arg0 > 0 && arg0 < imageViews.size() - 1) {
                setIndicator(arg0 - 1);
            }

        } else setIndicator(arg0);
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        viewPager.setScrollable(enable);
    }

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views�?前方与最后方加入的视图，即当前页面试图在views集合的位�?
     *
     * @return
     */
    public int getCurrentPostion() {
        return currentPosition;
    }

    /**
     * 设置指示�?
     *
     * @param selectedPosition 默认指示器位�?
     */
    private void setIndicator(int selectedPosition) {
        indicators[mLastPosition].setActivated(false);
        indicators[selectedPosition].setActivated(true);
        mLastPosition = selectedPosition;
    }

    /**
     * 如果当前页面嵌套在另g个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可�? 阻止父ViewPager滑动事件
     * 父ViewPager�?要实现ParentViewPager中的setScrollable方法
     */
    public void disableParentViewPagerTouchEvent(BaseViewPager parentViewPager) {
        this.parentViewPager = parentViewPager;
        if (parentViewPager != null) parentViewPager.setScrollable(false);
    }

}