package tools.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.ui.activity.myself.YearMoneyRenewActivity;
import razerdp.basepopup.BasePopupWindow;

public class ApplyUsePopuwindow extends BasePopupWindow implements View.OnClickListener  {
    private View popupView;
    private Button btn;
    public ApplyUsePopuwindow(Context context) {
        super(context);
        btn = popupView.findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                dismiss();

                break;

        }

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
        popupView = createPopupById(R.layout.notify_employ_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
