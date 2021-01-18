package cn.com.taodaji.ui.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.model.entity.Float_String;

import com.base.utils.DensityUtil;
import com.base.utils.MathUtil;
import com.base.utils.UIUtils;


public class PieChartViewUitl {
    //需要显示的数据集合
    //  private List<Float> mValues = new ArrayList<>();
    //需要显示的数据描述集合
    //  private List<String> mDescription = new ArrayList<>();

    //边框颜色和标注颜色
    // private int[] mColor = new int[]{0xFFE57373, 0xFF4FC3F7, 0xFFF06292, 0xFFFFF176, 0xFF9575CD, 0xFF81C784};


    //需要显示的数据、描述、边框颜色和标注颜色集合
    private List<Float_String> datas = new ArrayList<>();


    private int mScreenWidth;
    private int mScreenHeight;

    //-------------画笔相关-------------
    //圆环的画笔
    private Paint cyclePaint;
    //文字的画笔
    private Paint textPaint;
    //标注的画笔
    private Paint labelPaint;


    //文字颜色
    private int[] textColor = {0xFF00000, 0xFF00000};

    //分配比例大小，总比例大小为100
    private List<Float> strPercent = new ArrayList<>();

    //比例如的总值
    private float valuesMax = 0f;

    //圆的直径
    private float mRadius = 100;
    //圆的粗细
    private float mStrokeWidth = 40;
    //文字大小
    private int textSize = 20;

    //向上偏移量
    private int skewingTop;

    //记录上次分隔线的y坐标
    private float y_split = 0;

    private int last_posi;

    public void setTextColor(int... textColor) {
        this.textColor = textColor;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public void setmStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setSize(int width, int hight) {
        mScreenWidth = width;
        //向上偏移，30dp用于画标签
        mScreenHeight = hight - skewingTop;
    }

    //在setSize方法之前调用
    public void setSkewingTop(int skewingTop) {
        this.skewingTop = skewingTop;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(mStrokeWidth);
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor[1]);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
        //标注画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setStrokeWidth(2);
    }

    /**
     * 计算绘制比例
     */

    public void initData() {
        strPercent.clear();
        valuesMax = 0f;
        for (Float_String data : datas) {
            valuesMax += data.getValue();
        }
        for (Float_String data : datas) {
            float ss = data.getValue() / valuesMax;
            if (ss < 0.03f && ss > 0) ss = 0.03f;
            strPercent.add(ss);
        }
        //初始化画笔
        initPaint();
    }

    public void setValues(List<Float_String> data) {
        if (data.isEmpty()) return;
        datas = data;
        initData();
    }

    //画圆环
    public void drawCycle(Canvas canvas) {

        float startPercent = 0;
        float sweepPercent = 0;
        y_split = 0;
        last_posi = 0;
        float left = mScreenWidth / 2 - mRadius / 2;
        float top = mScreenHeight / 2 - mRadius / 2;
        RectF rectF = new RectF(left, top, left + mRadius, top + mRadius);

        Path path = new Path();
        path.addArc(rectF, 270, 359.999f);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();

        for (int i = 0; i < strPercent.size(); i++) {
            cyclePaint.setColor(UIUtils.getColor(datas.get(i).getColorInt()));


            //截取的初始位置
            startPercent = sweepPercent + startPercent;
            //每一段划过的距离
            sweepPercent = strPercent.get(i) * length;

            if (sweepPercent == 0) continue;

            Path partPath = new Path();
            if (i == strPercent.size() - 1) {
                sweepPercent += 0.2;
            }
            pathMeasure.getSegment(startPercent, startPercent + sweepPercent, partPath, true);
            canvas.drawPath(partPath, cyclePaint);


            //画标签
            labelPaint.setStyle(Paint.Style.FILL);
            labelPaint.setColor(UIUtils.getColor(datas.get(i).getColorInt()));
            labelPaint.setStrokeWidth(textSize);

            float start = mScreenWidth / strPercent.size();
//            float textS = textSize * datas.get(i).getDescription().length();

            float x_p = start * last_posi + textSize;
            last_posi++;//记录当前画了几个

            float y_p = mScreenHeight + skewingTop / 2;

            canvas.drawPoint(x_p, y_p - textSize / 2 + 5, labelPaint);

            canvas.drawText(datas.get(i).getDescription(), x_p + textSize, y_p, textPaint);


            //画分隔线和文字c
            //计算出弧线中间的坐标
            float midd_length = startPercent + sweepPercent / 2;
            float pos[] = new float[2];//路经的坐标
            float tan[] = new float[2];//路经的斜率
            pathMeasure.getPosTan(midd_length, pos, tan);

            drawTextAndLabel(canvas, datas.get(i).getSymbol() + String.valueOf(datas.get(i).getValue()), datas.get(i).getDescription(), UIUtils.getColor(datas.get(i).getColorInt()), pos, tan);
        }


    }

    /**
     * 画文字和标注
     *
     * @param canvas
     */
    private void drawTextAndLabel(Canvas canvas, String value, String description, @ColorInt int Lablecolor, float pos[], float tan[]) {
        labelPaint.setColor(Lablecolor);
        labelPaint.setStrokeWidth(2);
        float textSize = textPaint.getTextSize();//文字的大小
        int text_xx = 3;//文字的上下间距
        int offset_x = 0;//圆点偏移量
        int offset_y = 0;//偏移量
        int offset = 30;
        int xx = 0;//边距
        int vx = 0; //数字的开始坐标
        int dx = 0; //描述的开始坐标


        float[] coord = MathUtil.getCoordinate(tan, mStrokeWidth / 2 + offset);

        float x = pos[0] + coord[0];
        float y = pos[1] + coord[1];

        if (coord[0] < 0) {
            xx = 60;
            offset_x = -offset;
            vx = xx;
            dx = xx;
        } else {
            offset_x = offset;
            xx = mScreenWidth - 60;
            vx = (int) (xx - textSize * value.length() / 2);
            dx = (int) (xx - textSize * description.length());
        }

        if (coord[1] < 0) {
            offset_y = -offset;
        } else {
            offset_y = offset;
        }

        //解决数值过小，文字重叠
        float a = Math.abs(y + offset_y - y_split);
        float b = textSize * 2 + text_xx * 2 + 20;
        //y_split > 0  判断是否是第一个点
        if (a < b && y_split > 0) {
            //左边,顺时针，向上移
            if (coord[0] < 0) {
                offset_y -= b - a;
            } else {
                offset_y += b - a;
            }
        }
        y_split = y + offset_y;

        //解决折线点与圆相交 折线的点 (x + offset_x, y_split)
        float rx = mScreenWidth / 2;
        float ry = mScreenHeight / 2;
        //计算折线点到圆心的距离
        double rr = Math.sqrt((x + offset_x - rx) * (x + offset_x - rx) + (y_split - ry) * (y_split - ry));
        double mss = mRadius / 2 + mStrokeWidth / 2 + offset;
        if (rr < mss) {
            offset_x = (int) (Math.sqrt(mss * mss - (y_split - ry) * (y_split - ry)) + rx - x);
        }


        //画出指示器
        //1,先画一个小圆点
        labelPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, 8, labelPaint);
        //2,画出偏移的折线和分隔的水平线
        Path path = new Path();
        labelPaint.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        path.lineTo(x + offset_x, y_split);
        path.lineTo(xx, y_split);
        canvas.drawPath(path, labelPaint);

        //写出数字和描述
        textPaint.setColor(textColor[0]);
        canvas.drawText(value, vx, y_split - textSize / 4, textPaint);
        textPaint.setColor(textColor[1]);
        canvas.drawText(description, dx, y_split + text_xx + textSize, textPaint);

    }
}
