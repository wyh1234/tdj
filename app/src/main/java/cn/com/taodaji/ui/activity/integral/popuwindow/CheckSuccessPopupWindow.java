package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class CheckSuccessPopupWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_message_content;
    public CheckSuccessPopupWindow(Context context,String integral) {
        super(context);
        tv_message_content=popupView.findViewById(R.id.tv_message_content);
        tv_message_content.setText(integral);
    }
    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView().findViewById(R.id.btn_close_ppw);
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.ppw_check_success);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
