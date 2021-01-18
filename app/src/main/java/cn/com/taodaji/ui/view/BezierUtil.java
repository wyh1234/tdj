package cn.com.taodaji.ui.view;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.Float_String;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;

public class BezierUtil {

    private static final String TAG = "BIZIER";

    //需要显示的数据集合
    private List<Float> mValues = new ArrayList<>();
    //需要显示的数据描述集合
    private List<String> mDescription = new ArrayList<>();

    //圆点半经
    private List<Integer> mRadius = new ArrayList<>();


    public void setValues(List<Float_String> data) {
        if (data.isEmpty()) return;
        mValues.clear();
        mDescription.clear();
        for (Float_String float_string : data) {
            mValues.add(float_string.getValue());
            mDescription.add(float_string.getDescription());
        }
    }

    /**
     * 即将要穿越的点集合
     */
    private List<Point> mPoints = new ArrayList<>();
    /**
     * 中点集合
     */
    private List<Point> mMidPoints = new ArrayList<>();
    /**
     * 中点的中点集合
     */
    private List<Point> mMidMidPoints = new ArrayList<>();
    /**
     * 移动后的点集合(控制点)
     */
    private List<Point> mControlPoints = new ArrayList<>();

    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;
    private Path mPath;


    public void setSize(int width, int hight) {
        mScreenWidth = width;
        mScreenHeight = hight;
        Log.i("111111111111",":"+mScreenWidth);
        Log.i("111111111112",":"+mScreenHeight);
    }


    public Paint getPaint() {
        //设置画虚线，如果之后不再使用虚线，调用paint.setPathEffect(null);
/*        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mPaint.setPathEffect(effects);*/
        mPaint = new Paint();
        mPath = new Path();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);
        return mPaint;
    }


    public void init() {
        initPoints();
        initMidPoints(this.mPoints);
        initMidMidPoints(this.mMidPoints);
        initControlPoints(this.mPoints, this.mMidPoints, this.mMidMidPoints);
    }

    /**
     * 添加即将要穿越的点
     */
    private void initPoints() {
        Log.i("111111111113",":"+mScreenWidth);
        Log.i("111111111112",":"+mScreenHeight);
        if (mScreenHeight == 0 || mScreenWidth == 0 ){

        }
        mPoints.clear();
        int count = mValues.size();
        if (count == 0) return;
        int pointWidthSpace = mScreenWidth / count;
        //取出最大的数值
        float max = JavaMethod.getMaxValue(mValues);
        if (max == 0f) max = 100f;
        //计算出数值和高度的比  h=xs*value   底部空出0.25 绘制描述，顶部控出0.25,绘制数值
        float xs = mScreenHeight * 0.75f / (max * 1.5f);

        for (int i = 0; i < count; i++) {
            Point point;
            //最开始的点空出半个间隔
            point = new Point((int) (pointWidthSpace * (i + 0.5)), (int) (mScreenHeight * 0.75f - mValues.get(i) * xs));
            mPoints.add(point);
        }
    }

    /**
     * 初始化中点集合
     */
    private void initMidPoints(List<Point> points) {
        mMidPoints.clear();
        for (int i = 0; i < points.size(); i++) {
            Point midPoint = null;
            if (i == points.size() - 1) {
                return;
            } else {
                midPoint = new Point((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
            }
            mMidPoints.add(midPoint);
        }
    }

    /**
     * 初始化中点的中点集合
     */
    private void initMidMidPoints(List<Point> midPoints) {
        mMidMidPoints.clear();
        for (int i = 0; i < midPoints.size(); i++) {
            Point midMidPoint = null;
            if (i == midPoints.size() - 1) {
                return;
            } else {
                midMidPoint = new Point((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
            }
            mMidMidPoints.add(midMidPoint);
        }
    }

    /**
     * 初始化控制点集合
     */
    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midMidPoints) {
        mControlPoints.clear();
        for (int i = 0; i < points.size(); i++) {
            if (i != 0 && i != points.size() - 1) {
                Point before = new Point();
                Point after = new Point();
                before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
                before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
                after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
                after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
                mControlPoints.add(before);
                mControlPoints.add(after);
            }
        }
    }


    protected void onDraw(Canvas canvas) {
        //   super.onDraw(canvas);
        // ***********************************************************
        // ************* 贝塞尔进阶--曲滑穿越已知点 **********************
        // ***********************************************************

        // 画原始点
        // drawPoints(canvas);
        // 画穿越原始点的折线
        // drawCrossPointsBrokenLine(canvas);
        // 画中间点
        //  drawMidPoints(canvas);
        // 画中间点的中间点
        //  drawMidMidPoints(canvas);
        // 画控制点
        //   drawControlPoints(canvas);
        // 画贝塞尔曲线
        //   drawBezier(canvas);

    }

    /**
     * 画贝塞尔曲线
     */
    public void drawBezier(Canvas canvas) {
        //  mPaint.setStrokeWidth(LINEWIDTH);
        //  mPaint.setColor(Color.BLACK);
        // 重置路径
        mPath.reset();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {// 第一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);
            } else if (i < mPoints.size() - 2) {// 三阶贝塞尔
                mPath.cubicTo(mControlPoints.get(2 * i - 1).x, mControlPoints.get(2 * i - 1).y,// 控制点
                        mControlPoints.get(2 * i).x, mControlPoints.get(2 * i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            } else if (i == mPoints.size() - 2) {// 最后一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(mControlPoints.size() - 1).x, mControlPoints.get(mControlPoints.size() - 1).y,
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            }
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画原始点
     */
    public void drawPoints(Canvas canvas) {
        for (int i = 0; i < mPoints.size(); i++) {
            drawPoint(canvas, mPoints.get(i).x, mPoints.get(i).y);
        }
    }

    //最后一个圆点空心   position放大
    public void drawCirclePoints(Canvas canvas, int position, @NonNull int radius) {

        int r;
        for (int i = 0; i < mPoints.size(); i++) {

            if (i == position)
                r = (int) (radius * 1.25f);
            else if (i == mPoints.size() - 1) {
                r = (int) (radius * 0.5f);
            } else r = radius;


 /*           if (i == mPoints.size() - 1) {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(UIUtils.getColor(R.color.yellow_ffefe3));
            }*/
            mRadius.add(r);//记录圆点的半径
            drawCirclePoint(canvas, mPoints.get(i).x, mPoints.get(i).y, r);
        }
    }

    /**
     * 画点
     */
    public void drawPoint(Canvas canvas, int x, int y) {
        canvas.drawPoint(x, y, mPaint);
    }

    /**
     * 画圆
     */
    public void drawCirclePoint(Canvas canvas, int x, int y, int radius) {
        canvas.drawCircle(x, y, radius, mPaint);
    }

    /**
     * 画穿越原始点的折线
     */
    public void drawCrossPointsBrokenLine(Canvas canvas) {
        // 重置路径
        mPath.reset();

        int alpha = mPaint.getAlpha();
        float width = mPaint.getStrokeWidth();

        if (mPoints.size() == 0) return;
        // 画穿越原始点的折线
        //第一个点之前的虚线
        mPath.moveTo(0, mScreenHeight * 0.75f);
        mPath.lineTo(mPoints.get(0).x, mPoints.get(0).y);
        mPaint.setStrokeWidth(width * 0.6f);
        mPaint.setAlpha((int) (alpha * 0.5f));
        canvas.drawPath(mPath, mPaint);

        //第一个到最后一个
        mPath.reset();
        mPaint.setAlpha(alpha);
        mPaint.setStrokeWidth(width);
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        for (int i = 1; i < mPoints.size(); i++) {
            mPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        canvas.drawPath(mPath, mPaint);

        //虚线连接最后一个
        //设置画虚线，如果之后不再使用虚线，调用paint.setPathEffect(null);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mPaint.setPathEffect(effects);
        mPaint.setColor(UIUtils.getColor(R.color.yellow_ffefe3));
        mPaint.setStrokeWidth(width * 0.75f);
        mPath.reset();
        int last = mPoints.size() - 1;
        //  mPath.moveTo(mPoints.get(last - 1).x + mRadius.get(last - 1), mPoints.get(last - 1).y + mRadius.get(last - 1));
        // mPath.lineTo(mPoints.get(last).x - mRadius.get(last), mPoints.get(last).y - mRadius.get(last));
        mPath.moveTo(mPoints.get(last).x + mRadius.get(last), mPoints.get(last).y + mRadius.get(last) / 2);
        mPath.lineTo(mScreenWidth, mScreenHeight * 0.75f);
        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 数字和描术
     */
    public void drawValuesText(Canvas canvas, int position, @ColorInt int colorInt) {
        float textsize = mPaint.getTextSize();
        int textColor = mPaint.getColor();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == position) {
                mPaint.setTextSize(textsize * 1.25f);
                mPaint.setColor(colorInt);
            } else {
                mPaint.setTextSize(textsize);
                mPaint.setColor(textColor);
            }

            String text = String.valueOf(mValues.get(i));
            drawText(canvas, text, mPoints.get(i).x - textsize * text.length() / 4, mPoints.get(i).y - textsize);
        }
    }

    public void drawDescText(Canvas canvas) {
        float textsize = mPaint.getTextSize();
        for (int i = 0; i < mPoints.size(); i++) {
            String text = String.valueOf(mDescription.get(i));
            drawText(canvas, text, mPoints.get(i).x - textsize * text.length() / 2, mScreenHeight * 0.875f);
        }
    }

    public void drawText(Canvas canvas, String text, float x, float y) {
        canvas.drawText(text, x, y, mPaint);
    }


    /**
     * 画中间点
     */
    public void drawMidPoints(Canvas canvas) {
        for (int i = 0; i < mMidPoints.size(); i++) {
            canvas.drawPoint(mMidPoints.get(i).x, mMidPoints.get(i).y, mPaint);
        }
    }

    /**
     * 画中间点的中间点
     */
    public void drawMidMidPoints(Canvas canvas) {
        // mPaint.setColor(Color.YELLOW);
        for (int i = 0; i < mMidMidPoints.size(); i++) {
            canvas.drawPoint(mMidMidPoints.get(i).x, mMidMidPoints.get(i).y, mPaint);
        }

    }

    /**
     * 画控制点
     */
    public void drawControlPoints(Canvas canvas) {
        //  mPaint.setColor(Color.GRAY);
        // 画控制点
        for (int i = 0; i < mControlPoints.size(); i++) {
            canvas.drawPoint(mControlPoints.get(i).x, mControlPoints.get(i).y, mPaint);
        }
    }


}
