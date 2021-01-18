package com.base.widget.my_edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.base.listener.DrawableLeftListener;
import com.base.listener.DrawableRightListener;

/**
 * 加强版的EditText,可以响应DrawableLeft 和 DrawableRight的点击事件
 * 要实现响应点击,先设置setDrawableListener
 */
public class MyEditText extends EditText {

    private DrawableLeftListener mLeftListener;
    private DrawableRightListener mRightListener;

    public final int DRAWABLE_LEFT = 0;
    public final int DRAWABLE_TOP = 1;
    public final int DRAWABLE_RIGHT = 2;
    public final int DRAWABLE_BOTTOM = 3;

    @SuppressLint("NewApi")
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context) {
        super(context);
    }


    public void setDrawableLeftListener(DrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setDrawableRightListener(DrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public void removeDrawableRightListener(DrawableRightListener listener) {
        this.mRightListener = null;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    // if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                    // if (drawableRight != null && event.getX() >= (getRight() - drawableRight.getBounds().width())) {
                    boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                    if (drawableRight != null && touchable) {
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }

                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public void insertDrawable(int drawableId, String str) {
        final SpannableString ss = new SpannableString(str);
        //得到drawable对象，即所要插入的图片
        Drawable d = ContextCompat.getDrawable(getContext(), drawableId);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //用这个drawable对象代替字符串easy
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        //包括0但是不包括str.length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
    }
}
