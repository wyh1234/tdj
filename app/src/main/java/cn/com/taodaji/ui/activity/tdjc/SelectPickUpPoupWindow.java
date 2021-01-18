package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class SelectPickUpPoupWindow  extends BasePopupWindow {
    private View popupView;
    private TextView tv_name,tv_cancel,tv_ok;
    private SelectPickUpPoupWindowListener listener;



    public void setSelectPickUpPoupWindowListener(SelectPickUpPoupWindowListener listener) {
        this.listener = listener;
    }
    public interface SelectPickUpPoupWindowListener {
        void ok();
        void cancle();
    }
    public SelectPickUpPoupWindow(String currentCommunityName,String str,Context context) {
        super(context);
        tv_name=popupView.findViewById(R.id.tv_name);
        tv_cancel=popupView.findViewById(R.id.tv_cancel);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv_name.setText("您当前的提货点为"+currentCommunityName+",确认更改为"+str+"吗?");
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener. cancle();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener. ok();
            }
        });
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
        popupView = createPopupById(R.layout.select_pick_up_poupwindow);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
