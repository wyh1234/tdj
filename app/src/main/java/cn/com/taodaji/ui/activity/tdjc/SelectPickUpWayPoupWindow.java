package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DeliverFee;
import razerdp.basepopup.BasePopupWindow;

public class SelectPickUpWayPoupWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_way,tv_way1;

    private SelectPickUpWayPoupWindowListener listener;
    public void setSelectPickUpWayPoupWindowListener(SelectPickUpWayPoupWindowListener listener) {
        this.listener = listener;
    }
    public interface SelectPickUpWayPoupWindowListener {
        void select_way(int i,String delivery);
    }
    public SelectPickUpWayPoupWindow(Context context, DeliverFee mbody) {
        super(context);
        tv_way=popupView.findViewById(R.id.tv_way);
        tv_way1=popupView.findViewById(R.id.tv_way1);
        tv_way.setText(mbody.getData().getServiceFee().get(0).getServiceName());
        tv_way1.setText(mbody.getData().getServiceFee().get(1).getServiceName());
        tv_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.select_way(mbody.getData().getServiceFee().get(0).getDeliveryType(),tv_way.getText().toString());
                    dismiss();
                }
            }
        });
        tv_way1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.select_way(mbody.getData().getServiceFee().get(1).getDeliveryType(),tv_way1.getText().toString());
                    dismiss();
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
        popupView = createPopupById(R.layout.select_pick_up_way_poupwindow);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
