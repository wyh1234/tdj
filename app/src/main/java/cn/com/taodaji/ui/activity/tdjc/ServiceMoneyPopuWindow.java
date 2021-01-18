package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DeliverFee;
import razerdp.basepopup.BasePopupWindow;

public class ServiceMoneyPopuWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_gr, tv_qy,tv_info;
    private ImageView iv_gb;

    public ServiceMoneyPopuWindow(DeliverFee mbody,Context context) {
        super(context);
        tv_gr = popupView.findViewById(R.id.tv_gr);
        tv_qy = popupView.findViewById(R.id.tv_qy);
        iv_gb = popupView.findViewById(R.id.iv_gb);
        tv_info=popupView.findViewById(R.id.tv_info);
        iv_gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        String strMsg = "<font color=\"#FF7E01\">客户自提：</font>" + mbody.getData().getTips().getInfo1();
        String strMsg1 = "<font color=\"#FF7E01\">送货上门：</font>" +mbody.getData().getTips().getInfo2();
        tv_gr.setText(Html.fromHtml(strMsg1));
        tv_qy.setText(Html.fromHtml(strMsg));
        tv_info.setText(mbody.getData().getTips().getInfo3());
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
        popupView = createPopupById(R.layout.service_money_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}