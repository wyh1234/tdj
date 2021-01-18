package com.base.viewModel.adapter.splite_line;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import com.base.R;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
 * DividerItemDecoration.VERTICAL_LIST));
 */
@SuppressLint("DuplicateDivider")
public class DividerItemDecoration extends FixedItemDecoration {

//    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private Paint mPaint = null;
    private View mView;

    private int mDividerHeight = 1;//分割线高度，默认为1px


    private boolean showLast = false;//是否显示最后一个分界线

    public boolean isShowLast() {
        return showLast;
    }

    public void setShowLast(boolean showLast) {
        this.showLast = showLast;
    }

    public DividerItemDecoration(Context context) {
        super();
//        final TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
//        a.recycle();

        mDivider = ContextCompat.getDrawable(context, R.color.gray_db);
    }


    /**
     * 自定义分割线
     *
     * @param context
     * @param drawableId 分割线图片
     */
    public DividerItemDecoration(Context context, @DrawableRes int drawableId) {
        super();
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public DividerItemDecoration(int dividerHeight, @ColorRes int dividerColor) {
        super();
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(UIUtils.getColor(dividerColor));
        mPaint.setStyle(Paint.Style.FILL);
    }

    public DividerItemDecoration(Context context, int dividerHeight, @LayoutRes int layRes) {
        mView = ViewUtils.getLayoutView(context, layRes);
        mDividerHeight = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        if (isHorizontal(parent)) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        if (parent.getAdapter() == null) return;
        int lastPosition = parent.getAdapter().getItemCount() - 1;
        if (lastPosition == -1) return;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else if (mView != null) {
                // c.clipRect(left, top, right, bottom);
                mView.setLeft(left);
                mView.setRight(right);
                UIUtils.setViewSize(mView, right, bottom);
                mView.draw(c);
            }
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        int lastPosition = parent.getAdapter().getItemCount() - 1;
        if (lastPosition == -1) return;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerHeight;

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else if (mView != null) {

                mView.setLeft(left);
                mView.setRight(right);
                UIUtils.setViewSize(mView, right, bottom);
                mView.draw(c);
            }

        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (!showLast) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            int pos = layoutManager.getPosition(view);
            int lastPosition = parent.getAdapter().getItemCount() - 1;
            if (pos == lastPosition) return;
        }

        if (isHorizontal(parent)) {
            outRect.set(0, 0, mDividerHeight, 0);
        } else {
            outRect.set(0, 0, 0, mDividerHeight);
        }


    }
}
