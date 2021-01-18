package com.base.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ClickCheckedUtil;
import com.base.utils.StatusBarUtil;


public class FloatingDragView extends FloatingActionButton implements View.OnTouchListener {

    private int screenWidth;
    private int screenHeight;
    private Context mContext;
    private int lastX, lastY;
    private int left, top;
    private ViewGroup.MarginLayoutParams layoutParams;
    private int startX;
    private int endX;
    private boolean isMoved = false;

    private int toolBarSize;

    private int marginBottom;

    private OnClickListener mLister;
    Paint paint;
    Canvas canvas;

    private static final int borderWidth = 1;    //边框的宽度

    public void setOnClickListener(OnClickListener listener) {
        this.mLister = listener;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        init();
    }

    public FloatingDragView(Context context) {
        this(context, null);
    }

    public FloatingDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
//        initView();
    }

    private void initView() {
        canvas = new Canvas();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#c0c3c5"));
        paint.setStrokeWidth((float) borderWidth);
        paint.setStyle(Paint.Style.STROKE);
    }



    public void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        toolBarSize = StatusBarUtil.getActionBarSize(mContext);
        screenHeight = displayMetrics.heightPixels - getStatusBarHeight() - toolBarSize - marginBottom;

        setOnTouchListener(this);
        post(new Runnable() {
            @Override
            public void run() {
                layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                layoutParams.topMargin = screenHeight - getHeight();
                layoutParams.leftMargin = screenWidth - getWidth();
                setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                startX = lastX;
                break;
            case MotionEvent.ACTION_MOVE:
                isMoved = true;
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                left = v.getLeft() + dx;
                top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                // 设置不能出界
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (top < appBarSize()) {
                    top = appBarSize();
                    bottom = top + v.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                //只有滑动改变上边距时，抬起才进行设置
                if (isMoved) {
                    layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                    layoutParams.topMargin = top;
                    setLayoutParams(layoutParams);
                }
                endX = (int) event.getRawX();
                //滑动距离比较小，当作点击事件处理
                if (Math.abs(startX - endX) < 6) {
                    return false;
                }
                if (left + v.getWidth() / 2 < screenWidth / 2) {
                    startScroll(left, screenWidth / 2, true);
                } else {
                    startScroll(left, screenWidth / 2, false);
                }
                break;
        }
        return true;
    }

    //在此处理点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ClickCheckedUtil.onClickChecked(2000) && mLister != null) mLister.onClick(this);
        return super.onTouchEvent(event);
    }

    public void startScroll(final int start, int end, final boolean isLeft) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end).setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isLeft) {
                    layoutParams.leftMargin = (int) (start * (1 - animation.getAnimatedFraction()));
                } else {
                    layoutParams.leftMargin = (int) (start + (screenWidth - start - getWidth()) * (animation.getAnimatedFraction()));
                }
                setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    /**
     * 获取状态栏的高度   * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }


    private int appBarSize() {
        if (getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                if (viewGroup.getChildAt(i) instanceof AppBarLayout) {
                    AppBarLayout appBarLayout = (AppBarLayout) viewGroup.getChildAt(i);
                    return appBarLayout.getBottom();
                }
            }

        }
        return 0;
    }


}
