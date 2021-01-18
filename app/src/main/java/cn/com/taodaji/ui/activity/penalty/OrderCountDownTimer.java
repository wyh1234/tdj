package cn.com.taodaji.ui.activity.penalty;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class OrderCountDownTimer extends CountDownTimer {
    private TextView textView;

    public OrderCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public OrderCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        int ses = (int) (millisUntilFinished / 1000);
        int day = ses / (60 * 60 * 24);//换成天
        int hour = (ses-day*60 * 60 * 24) / 3600;//总秒数-换算成天的秒数=剩余的秒数    剩余的秒数换算为小时
        int minute = (ses  - day*60 * 60 * 24-3600 * hour) / 60;//总秒数-换算成天的秒数-换算成小时的秒数=剩余的秒数    剩余的秒数换算为分
        int second = ses  - day*60 * 60 * 24-3600 * hour - 60 * minute;//总秒数-换算成天的秒数-换算成小时的秒数-换算为分的秒数=剩余的秒数
        textView.setText("缴费倒计时:" + day+"天"+hour + ":" + minute + ":" + second);

    }

    @Override
    public void onFinish() {
        textView.setVisibility(View.GONE);
    }
}
