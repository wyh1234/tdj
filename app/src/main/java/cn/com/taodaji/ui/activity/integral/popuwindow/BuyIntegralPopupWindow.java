package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class BuyIntegralPopupWindow extends BasePopupWindow {
    private View popupView;
    private Button btn;
    private ImageView iv;

    private BuyIntegralPopupWindowListener listener;

    public void setBuyIntegralPopupWindowListener(BuyIntegralPopupWindowListener listener) {
        this.listener = listener;
    }


    public interface BuyIntegralPopupWindowListener {
        void buy_integra();

    }


    public BuyIntegralPopupWindow(Context context) {
        super(context);
        iv=popupView.findViewById(R.id.iv);
        btn=popupView.findViewById(R.id.btn);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.buy_integra();
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
        popupView = createPopupById(R.layout.buy_integral_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
       return findViewById(R.id.popup_anima);
    }
}
