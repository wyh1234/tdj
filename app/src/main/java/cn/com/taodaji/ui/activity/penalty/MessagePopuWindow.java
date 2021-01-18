package cn.com.taodaji.ui.activity.penalty;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class MessagePopuWindow extends BasePopupWindow {
    private View popupView;
    private TextView tv_content,tv_title;
    private Button btn_qx,btn_next;

    private MessagePopuWindowListener listener;

    public void setMessagePopuWindowListener(MessagePopuWindowListener listener) {
        this.listener = listener;
    }
    public interface MessagePopuWindowListener {
        void onCancel();
        void onOk();
    }
    public void setFineCount(int fineCount){
        tv_content.setText("你有" + fineCount + "条违规处罚通知");

    }
    public MessagePopuWindow(Context context,String title) {
        super(context);
        tv_content=popupView.findViewById(R.id.tv_content);
        btn_qx=popupView.findViewById(R.id.btn_qx);
        tv_title=popupView.findViewById(R.id.tv_title);
        btn_next=popupView.findViewById(R.id.btn_next);

        tv_title.setText(title);
        if (!title.equals("支付失败")){
            btn_next.setText("去查看");

        }else {
            btn_next.setText("继续支付");
            tv_content.setText("如未完成支付请重新支付");
        }
        btn_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOk();
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
        popupView = createPopupById(R.layout.pay_err_layout);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
