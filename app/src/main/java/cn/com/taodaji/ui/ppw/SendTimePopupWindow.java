package cn.com.taodaji.ui.ppw;

import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;

public abstract class SendTimePopupWindow extends BasePopupWindow implements View.OnClickListener, PopupResultInterface {


    public SendTimePopupWindow(View mainView) {
        super(mainView);
    }

    public void setTimeState(int time_state) {
        //初始化送达时间
/*        String time_now = DateUtils.parseTime(System.currentTimeMillis(), "HH:mm");
        if (UIUtils.getString(R.string.service_time_select_2).equals(time_now) > 0 && UIUtils.getString(R.string.service_time_select_1).equals(time_now) < 0)
            today.setVisibility(View.VISIBLE);
        initState(time_state);*/
    }

    private void initState(int time_state) {
        if (time_state == -1) return;
        switch (time_state) {
            case 0:
                today_x.setSelected(true);
                time_s.setSelected(false);
                time_x.setSelected(false);
                break;
            case 1:
                today_x.setSelected(false);
                time_s.setSelected(true);
                time_x.setSelected(false);
                break;
            case 2:
                today_x.setSelected(false);
                time_s.setSelected(false);
                time_x.setSelected(true);
                break;
        }
    }

    private View time_s, time_x, today_x, today;
    private TextView date_tody, date_tomorrow;

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_send_time);
        ViewUtils.findViewById(view, R.id.cancel).setOnClickListener(this);

        today = ViewUtils.findViewById(view, R.id.today);

        time_s = ViewUtils.findViewById(view, R.id.time_s);
        time_s.setOnClickListener(this);
        time_x = ViewUtils.findViewById(view, R.id.time_x);
        time_x.setOnClickListener(this);
        today_x = ViewUtils.findViewById(view, R.id.today_x);
        today_x.setOnClickListener(this);

        date_tody = ViewUtils.findViewById(view, R.id.date_tody);
        date_tody.setText(DateUtils.getDay("(MM月dd日)", 0));
        date_tomorrow = ViewUtils.findViewById(view, R.id.date_tomorrow);
        date_tomorrow.setText(DateUtils.getDayNext(0, "(MM月dd日)"));

        return view;
    }


    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.time_s:
                initState(1);
                setResult(1);
                break;
            case R.id.time_x:
                initState(2);
                setResult(2);
                break;
            case R.id.today_x:
                initState(0);
                setResult(0);
                break;
        }
        dismiss();
    }

}
