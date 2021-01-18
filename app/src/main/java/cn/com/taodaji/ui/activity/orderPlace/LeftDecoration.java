package cn.com.taodaji.ui.activity.orderPlace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.R;
import com.base.utils.BitmapUtil;
import com.base.utils.UIUtils;


public class LeftDecoration extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint();
    private int offsetY;//竖直方向上的偏移量
    private float w;
    private float h;


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        final int left = 0;


        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            offsetY = child.getPaddingTop();
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop();
            final int right = parent.getPaddingStart() + child.getPaddingStart();
            final int bottom = child.getBottom() + params.bottomMargin;
            draw(c, i, left, top, right, bottom);
        }

    }

    private void draw(Canvas c, int position, float left, float top, float right, float bottom) {
        float startY = top + offsetY;
        float startx = left + (right - left) / 2;
        drawCircle(c, position, startx, startY, top, bottom);
    }


    //画圆点
    private void drawCircle(Canvas c, int position, float cx, float cy, float top, float bottom) {
        Bitmap bitmap = null;

        paint.setAntiAlias(true);
        if (position == 0) {
            bitmap = BitmapUtil.getBitmap(R.mipmap.icon_exchange);
            w = bitmap.getWidth() / 2;
            h = bitmap.getHeight() / 2;
            c.drawBitmap(bitmap, cx - w, cy + h, paint);
            drawLine(c, cx, cy + h * 3, cx, bottom);
        } else {
            bitmap = BitmapUtil.getBitmap(R.mipmap.icon_exchange_gray);
            int x = bitmap.getWidth() / 2;
            int y = bitmap.getHeight() / 2;
            drawLine(c, cx, top, cx, cy + h);
            c.drawBitmap(bitmap, cx - x, cy + h, paint);
            drawLine(c, cx, cy + y * 2 + h, cx, bottom);
        }
    }

    //画灰色的线
    private void drawLine(Canvas c, float startX, float startY, float endX, float endY) {
        paint.setStrokeWidth(1);
        paint.setColor(UIUtils.getColor(R.color.gray_69));
        c.drawLine(startX, startY, endX, endY, paint);
    }


}
