package cn.com.taodaji.ui.activity.advertisement.popuwindow;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class RemarkPopuWindow extends BasePopupWindow {
    View popupView;
    ImageView iv_qx;
    TextView tv_ok;
    EditText ed_remark;
    TextView tv_num;


    private RemarkPopuWindowListener listener;

    public void setRemarkPopuWindowListener(RemarkPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface RemarkPopuWindowListener {
        void onCancel();
        void onOk(String remark);
    }
    public RemarkPopuWindow(Context context) {
        super(context);
        iv_qx=popupView.findViewById(R.id.iv_qx);
        tv_ok=popupView.findViewById(R.id.tv_ok);
        ed_remark=popupView.findViewById(R.id.ed_remark);
        tv_num=popupView.findViewById(R.id.tv_num);
        iv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onCancel();
                }
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    if (ed_remark.getText().toString().length()>0){
                        listener.onOk(ed_remark.getText().toString());
                    }else {
                        UIUtils.showToastSafe("备注不能为空");
                    }

                }
            }
        });
        ed_remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_num.setText(editable.toString().length()+"/50字");

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
        popupView = createPopupById(R.layout.remark_popu_layout);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
