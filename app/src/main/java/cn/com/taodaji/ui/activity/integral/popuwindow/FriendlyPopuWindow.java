package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class FriendlyPopuWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_del,tv_cancle,tv_ok;
    private FriendlyPopuWindowListener listener;
    public void setFriendlyPopuWindowListener(FriendlyPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface FriendlyPopuWindowListener {
        void ok();

    }

    public FriendlyPopuWindow(Context context) {
        super(context);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv_cancle=popupView.findViewById(R.id.tv_cancle);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ok();
                dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        popupView = createPopupById(R.layout.friendly_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
