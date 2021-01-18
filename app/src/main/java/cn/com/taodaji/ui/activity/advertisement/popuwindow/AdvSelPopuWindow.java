package cn.com.taodaji.ui.activity.advertisement.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class AdvSelPopuWindow extends BasePopupWindow {
    View popupView;
    private AdvSelPopuWindowListener listener;

    public void setAdvSelPopuWindowListener(AdvSelPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface AdvSelPopuWindowListener {
        void onCancel();
        void onOk();
    }
    public AdvSelPopuWindow(Context context) {
        super(context);
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.adv_sel_popu_layout);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
