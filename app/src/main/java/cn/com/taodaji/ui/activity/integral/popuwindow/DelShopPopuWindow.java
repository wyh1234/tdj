package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class DelShopPopuWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_del, tv_cancle, tv_ok;
    private DelPopuWindowListener listener;
    public void setDelShopPopuWindowListener(DelPopuWindowListener listener) {
        this.listener = listener;
    }

    public interface DelPopuWindowListener {
        void del_shop(String type,int pos);

    }

    public DelShopPopuWindow(Context context,String type,int pos) {
        super(context);
        tv_ok = popupView.findViewById(R.id.tv_ok);
        tv_cancle = popupView.findViewById(R.id.tv_cancle);
        tv_del = popupView.findViewById(R.id.tv_del);
        tv_del.setText("是否删除该商品");
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.del_shop(type,pos);
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
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
        popupView = createPopupById(R.layout.del_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
