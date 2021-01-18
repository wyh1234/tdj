package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.zxing.qrcode.encoder.QRCode;
import com.tdj.zxinglibrary.bean.ZxingConfig;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DeliveryCode;
import razerdp.basepopup.BasePopupWindow;
import tools.share.ShareUtils;

public class MyPickupCodePoupWindow extends BasePopupWindow {
    View popupView;
    private ImageView iv_code,iv_close;

    public MyPickupCodePoupWindow(Context context, DeliveryCode.DataBean.ListBean deliveryCode) {
        super(context);
        iv_code=popupView.findViewById(R.id.iv_code);
        iv_close=popupView.findViewById(R.id.iv_close);
        iv_code.setImageBitmap(ShareUtils.createQRCode(deliveryCode.getSite()+","+deliveryCode.getCustomerId()+","+deliveryCode.getDeliveryCode(),210));
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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
        popupView = createPopupById(R.layout.my_pick_up_code_poupwindow);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
