package cn.com.taodaji.ui.activity.advertisement.popuwindow;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class DelAdvPopuWindow extends BasePopupWindow {
    View popupView;
    Button btn_next,btn_qx;
    private DelAdvPopuWindowListener listener;
    private TextView tv_title;

    public void setDelAdvPopuWindowListener(DelAdvPopuWindowListener listener) {
        this.listener = listener;

    }
    public interface DelAdvPopuWindowListener {
        void onCancel();
        void onOk();
    }
    public DelAdvPopuWindow(Context context,String str) {
        super(context);
        tv_title=popupView.findViewById(R.id.tv_title);
        btn_next=popupView.findViewById(R.id.btn_next);
        btn_qx=popupView.findViewById(R.id.btn_qx);
        String name = "\""+str + "\"";
        String strMsg = "是否要删除"+name;
        tv_title.setText(Html.fromHtml(strMsg));
        btn_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onOk();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onCancel();
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
        return null;
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.del_adv_popu_layout);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
