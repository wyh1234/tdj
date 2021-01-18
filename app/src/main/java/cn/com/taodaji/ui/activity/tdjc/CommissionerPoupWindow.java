package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class CommissionerPoupWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_name,tv_cancel,tv_ok;
    public CommissionerPoupWindow( String commissionerName,String commissionerTelephone, Context context) {
        super(context);
        tv_cancel=popupView.findViewById(R.id.tv_cancel);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        tv_name=popupView.findViewById(R.id.tv_name);
        tv_name.setText(commissionerName+" "+commissionerTelephone);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + commissionerTelephone);
                intent.setData(data);
                context. startActivity(intent);
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
        popupView = createPopupById(R.layout.commissioner_poupwindow);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
