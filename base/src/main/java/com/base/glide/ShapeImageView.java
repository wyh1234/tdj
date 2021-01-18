package com.base.glide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.base.R;
import com.base.annotation.ShapeType;

@SuppressWarnings("deprecation")
public class ShapeImageView extends AppCompatImageView {

    // 图片的宽高
    private int width;
    private int height;

    private int borderColor = 0x1A000000; // 边框颜色
    private int borderWidth = 0; // 边框宽度
    private int radius = 0; // 圆角弧度
    private int shapeType = ShapeType.RECTANGLE; // 图片类型（ 矩形）

    private Paint pressedPaint; // 按下的画笔
    private float pressedAlpha = 0.1f; // 按下的透明度
    private int pressedColor = 0x1A000000; // 按下的颜色


    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView);
            borderWidth = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_border_width, borderWidth);
            borderColor = array.getColor(R.styleable.ShapeImageView_siv_border_color, borderColor);
            radius = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius, radius);
            pressedAlpha = array.getFloat(R.styleable.ShapeImageView_siv_pressed_alpha, pressedAlpha);
            if (pressedAlpha > 1) pressedAlpha = 1;
            pressedColor = array.getColor(R.styleable.ShapeImageView_siv_pressed_color, pressedColor);
            shapeType = array.getInteger(R.styleable.ShapeImageView_siv_shape_type, shapeType);
            array.recycle();
        }

        initPressedPaint();
//        setClickable(true);
//        setDrawingCacheEnabled(true);
//        setWillNotDraw(false);
    }

    // 初始化按下的画笔
    private void initPressedPaint() {
        pressedPaint = new Paint();
        pressedPaint.setAntiAlias(true);
        pressedPaint.setStyle(Paint.Style.FILL);
        pressedPaint.setColor(pressedColor);
        pressedPaint.setAlpha(0);
        pressedPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        drawBorder(canvas);

        if (isClickable()) drawPressed(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    // 绘制边框
    private void drawBorder(Canvas canvas) {
        if (borderWidth > 0) {
            Paint paint = new Paint();
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setAntiAlias(true);

            switch (shapeType) {
                case ShapeType.CIRCLE:
                    canvas.drawCircle(width / 2, height / 2, (width - borderWidth) / 2, paint);
                    break;
                case ShapeType.ROUNDRECT:
                    RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
                    canvas.drawRoundRect(rectf, radius, radius, paint);
                    break;
                default:
                    RectF rect = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
                    canvas.drawRect(rect, paint);
                    break;
            }
        }
    }

    // 绘制按下效果
    private void drawPressed(Canvas canvas) {

        switch (shapeType) {
            case ShapeType.CIRCLE:
                canvas.drawCircle(width / 2, height / 2, width / 2, pressedPaint);
                break;
            case ShapeType.ROUNDRECT:
                RectF rectf = new RectF(1, 1, width - 1, height - 1);
                canvas.drawRoundRect(rectf, radius, radius, pressedPaint);
                break;
            default:
                RectF rect = new RectF(1, 1, width - 1, height - 1);
                canvas.drawRect(rect, pressedPaint);
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedPaint.setAlpha((int) (pressedAlpha * 255));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                pressedPaint.setAlpha(0);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                pressedPaint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    // 设置边框颜色
    public void setBorderColor(@ColorRes int id) {
        this.borderColor = getResources().getColor(id);
        invalidate();
    }

    // 设置边框宽度
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    // 设置图片按下颜色透明度
    public void setPressedAlpha(float pressAlpha) {
        this.pressedAlpha = pressAlpha;
    }

    // 设置图片按下的颜色
    public void setPressedColor(@ColorRes int id) {
        this.pressedColor = getResources().getColor(id);
        pressedPaint.setColor(pressedColor);
        pressedPaint.setAlpha(0);
        invalidate();
    }

    // 设置圆角半径
    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public int getRadius() {
        return radius;
    }


    // 设置形状类型
    public void setShapeType(@ShapeType int shapeType) {
        this.shapeType = shapeType;
        invalidate();
    }


    public int getShapeType() {
        return shapeType;
    }

}
