package cn.com.taodaji.ui.activity.integral.tools;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

public abstract class OrderstateCountDownTimer extends CountDownTimer {
    private TextView textView, textView1, textView2,textViewd;


    public OrderstateCountDownTimer(long millisInFuture, long countDownInterval, TextView textView,TextView textView1,TextView textView2,TextView textViewd) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.textView2=textView2;
        this.textView1=textView1;
        this.textViewd=textViewd;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        int totalTime = (int) (millisUntilFinished / 1000);//秒
        int day =0; int hour = 0, minute = 0, second = 0;
        if (60 * 60 * 24<=totalTime){
            day = totalTime / (60 * 60 * 24);//换成天
            totalTime=totalTime-60 * 60 * 24 * day;
        }

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
            if (day<10){
            textViewd.setText("0" + day);
        }else {
            textViewd.setText("0" + day);

            }
        if (hour < 10) {
            textView.setText("0" + hour);
        } else {
            textView.setText("" + hour);
        }
        if (minute < 10) {
            textView1.setText("0" + minute);
        } else {
            textView1.setText("" + minute);
        }
        if (second < 10) {
            textView2.setText("0" + second);
        } else {
            textView2.setText("" + second);
        }
    }

    @Override
    public abstract void onFinish();
}
