package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DeliverTime;
import razerdp.basepopup.BasePopupWindow;

public class SelectPickUpTimePoupWindow extends BasePopupWindow {
    private View popupView;
    private LinearLayout ll_bottom;


    private SelectPickUpTimePoupWindowListener listener;
    public void setSelectPickUpTimePoupWindowListener(SelectPickUpTimePoupWindowListener listener) {
        this.listener = listener;
    }
    public interface SelectPickUpTimePoupWindowListener {
        void select_time(int i,String time);
    }
    public SelectPickUpTimePoupWindow(Context context, DeliverTime body) {
        super(context);
        ll_bottom=popupView.findViewById(R.id.ll_bottom);

        LayoutInflater from = LayoutInflater.from(context);
        for (int i=0;i<body.getData().getTimeList().size();i++){
            View view = from.inflate(R.layout.select_time_textview, null, false);
            ll_bottom.addView(view);
            TextView tv_time=view.findViewById(R.id.tv_time);
            tv_time.setText(body.getData().getTimeList().get(i).getStartTime()+"-"+body.getData().getTimeList().get(i).getEndTime());
            int finalI = i;
            tv_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.select_time(body.getData().getTimeList().get(finalI).getTimeEntityId(),tv_time.getText().toString());
                    dismiss();
                }
            });

        }


    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.select_pick_up_time_poupwindow);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
