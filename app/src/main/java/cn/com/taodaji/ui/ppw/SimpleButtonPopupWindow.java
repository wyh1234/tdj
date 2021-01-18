package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class SimpleButtonPopupWindow extends PopupWindow implements OnClickListener {

    private int position;
    private ButtonOnclickInterface buttonOnclickInterface;
    private View showCountView, close;

    private Button button_left;
    private Button button_right;
    private TextView popupWindow_message;
    private boolean isClose = true;
    private boolean isTransparent = false;

    public SimpleButtonPopupWindow(int position, View showCountView) {
        this.position = position;
        this.showCountView = showCountView;
        final View view = ViewUtils.getLayoutViewMatch(showCountView.getContext(), R.layout.popup_window_simple_button);

        button_left = (Button) view.findViewById(R.id.button_left);
        button_right = (Button) view.findViewById(R.id.button_right);
        close = view.findViewById(R.id.close);
        Button popupWindow_close = (Button) view.findViewById(R.id.popupWindow_close);

        popupWindow_close.setOnClickListener(this);
        button_left.setOnClickListener(this);
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
        ColorDrawable dw;
        if (isTransparent) {
            // 实例化一个ColorDrawable颜色为透明
            dw = new ColorDrawable(UIUtils.getColor(android.R.color.transparent));
        } else {
            // 实例化一个ColorDrawable颜色为半透明
            dw = new ColorDrawable(0xb0000000);
        }


        // 设置SelectPicPopupWindow弹出窗体的背景
        ////必须要有这句否则弹出popupWindow后监听不到Back键
        this.setBackgroundDrawable(dw);
        view.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (isClose) {
                    View popu = view.findViewById(R.id.simple_popup_window);
                    int top = popu.getTop();
                    int bottom = popu.getBottom();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < top || y > bottom) {
                            dismiss();
                        }
                    }
                }

                return true;
            }
        });
    }




    public SimpleButtonPopupWindow setCloseHide(boolean isHide) {
        if (isHide) {
            close.setVisibility(View.GONE);
        } else close.setVisibility(View.VISIBLE);
        return this;
    }

    public SimpleButtonPopupWindow isClose(boolean isClose) {
        this.isClose = isClose;
        return this;
    }

    public SimpleButtonPopupWindow isTransparent(boolean isTransparent) {
        this.isTransparent = isTransparent;
        return this;
    }

    public SimpleButtonPopupWindow setMessage(String message_text) {
        popupWindow_message.setText(message_text);
        return this;
    }

    public SimpleButtonPopupWindow setButton_left_text(String button_left_text) {
        button_left.setText(button_left_text);
        return this;
    }

    public SimpleButtonPopupWindow setButton_left_backgroundResource(@DrawableRes int resid) {
        button_left.setBackgroundResource(resid);
        return this;
    }

    public SimpleButtonPopupWindow setButton_left_TextColor(@ColorRes int resid) {
        button_left.setTextColor(UIUtils.getColor(resid));
        return this;
    }


    public SimpleButtonPopupWindow setButton_right_text(String button_right_text) {
        button_right.setText(button_right_text);
        return this;
    }

    public SimpleButtonPopupWindow setButton_right_backgroundResource(@DrawableRes int resid) {
        button_right.setBackgroundResource(resid);
        return this;
    }

    public SimpleButtonPopupWindow setButton_right_TextColor(@ColorRes int resid) {
        button_right.setTextColor(UIUtils.getColor(resid));
        return this;
    }

    public void setButtonOnclickInterface(ButtonOnclickInterface buttonOnclickInterface) {
        this.buttonOnclickInterface = buttonOnclickInterface;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left://取消
                buttonOnclickInterface.buttonLeftOnclick();
                break;

            case R.id.button_right://确认
                buttonOnclickInterface.buttonRightOnclick(position, showCountView);
                break;
            case R.id.popupWindow_close:
                dismiss();
                break;
        }
    }

    public interface ButtonOnclickInterface {
        void buttonLeftOnclick();

        void buttonRightOnclick(int position, View showCountView);
    }

}
