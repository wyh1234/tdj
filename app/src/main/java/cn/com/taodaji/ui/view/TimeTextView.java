package cn.com.taodaji.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.base.utils.BitmapUtil;
import com.base.utils.DateUtils;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import cn.com.taodaji.R;
import cn.com.taodaji.model.event.WaitEvent;


public class TimeTextView extends AppCompatTextView {
private TextView go_text;
private int position;
    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 在控件被销毁时移除消息
        handler.removeMessages(0);
    }

    long Time;
    private boolean run = true; // 是否启动了


    @SuppressLint("NewApi")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        long mTime = Time;
                        if (mTime > 0) {
                            //String day = "";
                           String mm= DateUtils.parseTime("mm", Time);
                            String ss= DateUtils.parseTime("ss", Time);
                            TimeTextView.this.setText("付款倒计时: " + mm+":"+ss);
                            Time = Time - 1000;
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    } else {
                        TimeTextView.this.setText("订单已超时关闭");
                        TimeTextView.this.setTextColor(UIUtils.getColor(R.color.red_dark));
                  /*      Drawable drawable = BitmapUtil.getDrawable(R.mipmap.icon_over_time);
                        // 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        TimeTextView.this.setCompoundDrawables(drawable, null, null, null);
                        TimeTextView.this.setCompoundDrawablePadding(5);
                        go_text.setText("联系发起人重新下单");*/
                        go_text.setVisibility(GONE);

                        EventBus.getDefault().post(new WaitEvent(position));
                    }
                    break;
            }

        }
    };





    @SuppressLint("NewApi")
    public void setTimes(long mT,int position,TextView text_go_pay) {
        this.position=position;
        this.go_text=text_go_pay;
        // 标示已经启动
//        Date date = new Date();
//        long t2 = date.getTime();
        Time = mT ;//- t2
      //  date = null;

        if (Time > 0) {
            handler.removeMessages(0);
            handler.sendEmptyMessage(0);
        } else {
            TimeTextView.this.setText("订单已超时关闭");
            TimeTextView.this.setTextColor(UIUtils.getColor(R.color.red_dark));
          /*  Drawable drawable = BitmapUtil.getDrawable(R.mipmap.icon_over_time);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            TimeTextView.this.setCompoundDrawables(drawable, null, null, null);
            TimeTextView.this.setCompoundDrawablePadding(5);*/
            go_text.setVisibility(GONE);

            EventBus.getDefault().post(new WaitEvent(position));

        }
    }

    public void stop() {
        run = false;
    }
}
