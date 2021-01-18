package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import razerdp.basepopup.BasePopupWindow;

public class AgreementPoupWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_cannel,tv_ok,tv,tv1,tv2;
    private AgreementPoupWindowListener listener;
    public void setAgreementPoupWindowListener(AgreementPoupWindowListener listener) {
        this.listener = listener;
    }
    public interface AgreementPoupWindowListener {
        void ok();
        void cancle();
    }

    public AgreementPoupWindow(Context context) {
        super(context);
        tv_cannel=popupView.findViewById(R.id.tv_cannel);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv=popupView.findViewById(R.id.tv);
        tv1=popupView.findViewById(R.id.tv1);
        tv2=popupView.findViewById(R.id.tv2);
        tv.setText(PublicCache.initializtionData.getAfter_sale_intro_title());
        tv1.setText(PublicCache.initializtionData.getAfter_sale_intro_content().replace("\\n", "\n"));
        tv2.setText(PublicCache.initializtionData.getAfter_sale_intro_end());
        tv_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.cancle();
                }

            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.ok();
                }
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
        popupView = createPopupById(R.layout.agreement_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
