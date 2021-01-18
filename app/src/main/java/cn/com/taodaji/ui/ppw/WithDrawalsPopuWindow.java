package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.WithdrawFeeRule;
import razerdp.basepopup.BasePopupWindow;

public class WithDrawalsPopuWindow extends BasePopupWindow {
    private View popupView;
    private Button btn;
    private ImageView iv;
    private TextView d_money_tv,z_money_tv,tv_1;
    private RelativeLayout rl_3;

    private WithDrawalsPopuWindowListener listener;

    public void setWithDrawalsPopuWindowListener(WithDrawalsPopuWindowListener listener) {
        this.listener = listener;
    }


    public interface WithDrawalsPopuWindowListener {
        void button(BigDecimal m);

    }


    public WithDrawalsPopuWindow(Context context, WithdrawFeeRule mbody, BigDecimal m) {
        super(context);
        iv = popupView.findViewById(R.id.iv);
        btn = popupView.findViewById(R.id.btn);
        d_money_tv=popupView.findViewById(R.id.d_money_tv);
        z_money_tv=popupView.findViewById(R.id.z_money_tv);
        tv_1=popupView.findViewById(R.id.tv_1);
        rl_3=popupView.findViewById(R.id.rl_3);
        if (m.compareTo(mbody.getData().getStandard_amount())>=0){
            rl_3.setVisibility(View.GONE);
            d_money_tv.setText("￥"+m.multiply(mbody.getData().getCollection_rate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            tv_1.setText("￥"+m.subtract(m.multiply(mbody.getData().getCollection_rate()).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP));

        }else if (m.compareTo(mbody.getData().getStandard_amount())==-1){
            rl_3.setVisibility(View.VISIBLE);
            d_money_tv.setText("￥"+m.multiply(mbody.getData().getCollection_rate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            z_money_tv.setText("￥"+mbody.getData().getTransfer_amount());
            tv_1.setText("￥"+m.subtract(m.multiply(mbody.getData().getCollection_rate()).divide(new BigDecimal(100))).subtract(mbody.getData().getTransfer_amount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }

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
                listener.button(m);
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
        popupView = createPopupById(R.layout.withdrawals_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}