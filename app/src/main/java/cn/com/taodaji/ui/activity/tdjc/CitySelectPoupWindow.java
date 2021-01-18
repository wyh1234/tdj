package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DeliverFee;
import razerdp.basepopup.BasePopupWindow;

public class CitySelectPoupWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_way,tv_way1;

    private CitySelectPoupWindow.CitySelectPoupWindowListener listener;
    public void setCitySelectPoupWindowListener(CitySelectPoupWindow.CitySelectPoupWindowListener listener) {
        this.listener = listener;
    }
    public interface CitySelectPoupWindowListener {
        void select_city();
        void select_city_one();
    }
    public CitySelectPoupWindow(Context context) {
        super(context);
        tv_way=popupView.findViewById(R.id.tv_way);
        tv_way1=popupView.findViewById(R.id.tv_way1);
        tv_way.setText("武汉");
        tv_way.setText("襄阳");
        tv_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.select_city();
                    dismiss();
                }
            }
        });
        tv_way1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.select_city_one();
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

