package tools.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class PaymentCalssifyPouwindow extends BasePopupWindow {
    private View popupView;
    private PaymentCalssifyPouwindowListener listener;
    private TextView tv_penalty,tv_advertisement,tv_year_money,tv_t_money,tv_qx;

    public void setPaymentCalssifyPouwindowListener(PaymentCalssifyPouwindowListener listener) {
        this.listener = listener;
    }
    public interface PaymentCalssifyPouwindowListener {
        void onCancel();
        void onSel(int type);
    }
    public PaymentCalssifyPouwindow(Context context) {
        super(context);
        tv_penalty=popupView.findViewById(R.id.tv_penalty);
        tv_advertisement=popupView.findViewById(R.id.tv_advertisement);
        tv_year_money=popupView.findViewById(R.id.tv_year_money);
        tv_t_money=popupView.findViewById(R.id.tv_t_money);
        tv_qx=popupView.findViewById(R.id.tv_qx);
        tv_penalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSel(1);
                tv_penalty.setSelected(true);
                tv_advertisement.setSelected(false);
                tv_year_money.setSelected(false);
                tv_t_money.setSelected(false);
            }
        });
        tv_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSel(2);
                tv_penalty.setSelected(false);
                tv_advertisement.setSelected(true);
                tv_year_money.setSelected(false);
                tv_t_money.setSelected(false);
            }
        });
        tv_year_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSel(3);
                tv_penalty.setSelected(false);
                tv_advertisement.setSelected(false);
                tv_year_money.setSelected(true);
                tv_t_money.setSelected(false);
            }
        });
        tv_t_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSel(4);
                tv_penalty.setSelected(false);
                tv_advertisement.setSelected(false);
                tv_year_money.setSelected(false);
                tv_t_money.setSelected(true);
            }
        });
        tv_qx.setOnClickListener(new View.OnClickListener() {
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
         return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.pay_ment_clssify_layout);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
