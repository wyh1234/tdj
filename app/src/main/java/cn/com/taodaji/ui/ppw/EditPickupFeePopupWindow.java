package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.EidtPickupFeeEvent;
import cn.com.taodaji.model.event.PickupEvent;
import razerdp.basepopup.BasePopupWindow;

public class EditPickupFeePopupWindow extends BasePopupWindow {
    private View popupView;
    private TextView confirm;
    private EditText fee;
    private int flag;
    private BigDecimal restFee;

    public EditPickupFeePopupWindow(Context context) {
        super(context);
    }

    public BigDecimal getRestFee() {
        return restFee;
    }

    public void setRestFee(BigDecimal restFee) {
        this.restFee = restFee;
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView().findViewById(R.id.tv_cancel);
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.ppw_edit_fee);
        fee = popupView.findViewById(R.id.et_edit_fee);
        fee.setText(PublicCache.loginSupplier.getAutomaticRenewalFee()+"");
        confirm = popupView.findViewById(R.id.tv_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fee.getText().toString().trim().isEmpty()){
                    UIUtils.showToastSafe("请输入金额");
                    return;
                }
                try {
                    flag=Integer.parseInt(fee.getText().toString().trim());
                }catch (Exception e){
                    e.printStackTrace();
                    UIUtils.showToastSafe("请输入数字");
                }
                if (flag<200){
                    UIUtils.showToastSafe("续费金额必须大于200");
                    return;
                }
                if (flag>restFee.intValue()){
                    UIUtils.showToastSafe("当前余额不足");
                }else {
                    EventBus.getDefault().post(new EidtPickupFeeEvent(flag));
                    dismiss();
                }
            }
        });


        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}