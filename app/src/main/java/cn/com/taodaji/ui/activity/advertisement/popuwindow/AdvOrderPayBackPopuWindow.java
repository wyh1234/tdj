package cn.com.taodaji.ui.activity.advertisement.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class AdvOrderPayBackPopuWindow extends BasePopupWindow {
    Button btn_qx,btn_next;

    View popupView;
    private AdvOrderPayBackPopuWindowListener listener;

    public void setAdvOrderPayBackWindowListener(AdvOrderPayBackPopuWindowListener listener) {
        this.listener = listener;

    }
    public interface AdvOrderPayBackPopuWindowListener {
        void onCancel();
        void onOk();
    }
    public AdvOrderPayBackPopuWindow(Context context) {
        super(context);
        btn_qx=popupView.findViewById(R.id.btn_qx);
        btn_next=popupView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOk();
            }
        });
        btn_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });
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
        popupView = createPopupById(R.layout.adv_order_pay_back_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
