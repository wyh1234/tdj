package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.base.event.IntegralLogin;
import com.base.event.LoginLoseEfficacyEvent;
import com.base.utils.ClickCheckedUtil;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class SubmitOrderErrPoupWindow  extends BasePopupWindow {
    private View popupView;
    private TextView tv_name,tv_cancel,tv_ok;
    public SubmitOrderErrPoupWindow( String str, Context context) {
        super(context);
        tv_name=popupView.findViewById(R.id.tv_name);
        tv_cancel=popupView.findViewById(R.id.tv_cancel);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv_name.setText(str);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickCheckedUtil.onClickChecked(1000)){
                    EventBus.getDefault().post(new LoginLoseEfficacyEvent());
                }
                dismiss();
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
