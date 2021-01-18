package cn.com.taodaji.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;


import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.Float_String;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;

public class MonthlyBillLineView extends View {

    public MonthlyBillLineView(Context context) {
        super(context);
    }
    public MonthlyBillLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthlyBillLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthlyBillLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private int mScreenWidth;
    private int mScreenHeight;

    private BezierUtil bezierUtil = new BezierUtil();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mScreenWidth = getMeasuredWidth();
            mScreenHeight = getMeasuredHeight();
        bezierUtil.setSize(mScreenWidth, mScreenHeight);
    }

    public void setValues(List<Float_String> data) {
        bezierUtil.setValues(data);
        bezierUtil.init();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = bezierUtil.getPaint();

        // 画原始点
        paint.setColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(UIUtils.dip2px(1));
        bezierUtil.drawCirclePoints(canvas, 4, UIUtils.dip2px(3));


        // 画穿越原始点的折线
        paint.setColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UIUtils.dip2px(2));
        bezierUtil.drawCrossPointsBrokenLine(canvas);

        // 画数字和描述
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(DensityUtil.sp2px(12));
        paint.setColor(UIUtils.getColor(R.color.gray_66));
        bezierUtil.drawDescText(canvas);
        paint.setColor(UIUtils.getColor(R.color.gray_69));
        bezierUtil.drawValuesText(canvas, 4, UIUtils.getColor(R.color.orange_yellow_ff7d01));

        // 画中间点
        //  drawMidPoints(canvas);
        // 画中间点的中间点
        //  drawMidMidPoints(canvas);
        // 画控制点
        //   drawControlPoints(canvas);
        // 画贝塞尔曲线
        //  paint.setStyle(Paint.Style.STROKE);
        //  bezierUtil.drawBezier(canvas);
    }

}
