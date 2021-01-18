package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import razerdp.basepopup.BasePopupWindow;

public class RemarkPopuWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_gr,tv_qy;
    private ImageView iv_gb;

    public RemarkPopuWindow(Context context) {
        super(context);
        tv_gr=popupView.findViewById(R.id.tv_gr);
        tv_qy=popupView.findViewById(R.id.tv_qy);
        iv_gb=popupView.findViewById(R.id.iv_gb);
        iv_gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        String strMsg = "<font color=\"#FD0404\">企业用户：</font>"+ PublicCache.initializtionData.getB_freight();
        String strMsg1 = "<font color=\"#FD0404\">个人用户：</font>"+PublicCache.initializtionData.getC_freight();
        tv_gr.setText(Html.fromHtml(strMsg1));
        tv_qy.setText(Html.fromHtml(strMsg));
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
        popupView = createPopupById(R.layout.remark_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
