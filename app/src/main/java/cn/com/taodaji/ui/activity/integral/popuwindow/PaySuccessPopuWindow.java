package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.IntegralOrder;
import razerdp.basepopup.BasePopupWindow;

public class PaySuccessPopuWindow extends BasePopupWindow {
   private View popupView;
   private Button tv_cancle,tv_ok;
   private TextView tv_name,tv_pay_way;
   private PaySuccessPopuWindowListene listener;
    public void setPaySuccessPopuWindowListene(PaySuccessPopuWindowListene listener) {
        this.listener = listener;
    }
    public interface PaySuccessPopuWindowListene {
        void back_my();
        void back_cart();
    }


    public PaySuccessPopuWindow(Context context, IntegralOrder.IntegralOrderData data) {
        super(context);
        tv_cancle=popupView.findViewById(R.id.tv_cancle);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv_pay_way=popupView.findViewById(R.id.tv_pay_way);
        tv_pay_way.setText(data.getPayWay()+data.getTotalFee());
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener. back_my();

            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener. back_cart();
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
        popupView = createPopupById(R.layout.pay_success_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
