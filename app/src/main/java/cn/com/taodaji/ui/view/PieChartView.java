package cn.com.taodaji.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.Float_String;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;

public class PieChartView extends View {


    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private int mScreenWidth;
    private int mScreenHeight;
    //圆的直径
    private float mRadius = 300;
    private PieChartViewUitl pieChartViewUitl = new PieChartViewUitl();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScreenWidth = getMeasuredWidth();
        mScreenHeight = getMeasuredHeight();
        mRadius = mScreenHeight / 3;

        //初始化画笔
        pieChartViewUitl.setSkewingTop(DensityUtil.dp2px(30));
        pieChartViewUitl.setSize(mScreenWidth, mScreenHeight);
        pieChartViewUitl.setmRadius(mRadius);
        pieChartViewUitl.setmStrokeWidth(mRadius / 2);
        pieChartViewUitl.setTextSize(DensityUtil.sp2px(12));
        pieChartViewUitl.setTextColor(UIUtils.getColor(R.color.gray_69), UIUtils.getColor(R.color.gray_66));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // pieChartViewUitl.initData();
        pieChartViewUitl.drawCycle(canvas);
    }

    public void setValues(List<Float_String> data) {
        pieChartViewUitl.setValues(JavaMethod.sortAsc(data,"value"));

    }


}
