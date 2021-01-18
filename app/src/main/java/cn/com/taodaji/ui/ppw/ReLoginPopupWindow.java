package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.utils.ViewUtils;


public class ReLoginPopupWindow extends PopupWindow implements View.OnClickListener {
    private TextView popupWindow_message;
    private Button button_right;
    private int position;
    private SimpleButtonPopupWindow.ButtonOnclickInterface buttonOnclickInterface;
    private View showCountView;

    public ReLoginPopupWindow(int position, View showCountView) {
        this.position = position;
        this.showCountView = showCountView;

        final View view = ViewUtils.getLayoutViewMatch(showCountView.getContext(),R.layout.popup_window_relogin);
        button_right = (Button) view.findViewById(R.id.button_right);
        Button popupWindow_close = (Button) view.findViewById(R.id.popupWindow_close);

        popupWindow_close.setOnClickListener(this);

        button_right.setOnClickListener(this);
        popupWindow_message = (TextView) view.findViewById(R.id.popupWindow_message);

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottomPopupWindow);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                View popu = v.findViewById(R.id.simple_popup_window);
                int top = popu.getTop();
                int bottom = popu.getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < top || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setButtonOnclickInterface(SimpleButtonPopupWindow.ButtonOnclickInterface buttonOnclickInterface) {
        this.buttonOnclickInterface = buttonOnclickInterface;
    }
    public ReLoginPopupWindow setMessage(String message_text) {
        popupWindow_message.setText(message_text);
        return this;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right://确认
                buttonOnclickInterface.buttonRightOnclick(position, showCountView);
                break;
        }
    }
}
