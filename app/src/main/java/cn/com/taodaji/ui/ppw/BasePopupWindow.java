package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;

public abstract class BasePopupWindow extends PopupWindow {
    private boolean isClose = true;

    public BasePopupWindow(View mainView) {

        final View view = createContextView(mainView);
        if (view == null) return;
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopupWindow);

        setTouchable(true);
        ColorDrawable dw;
        if (isTransparent()) {
            // 实例化一个ColorDrawable颜色为透明
            dw = new ColorDrawable(UIUtils.getColor(android.R.color.transparent));
        } else {
            // 实例化一个ColorDrawable颜色为半透明
            dw = new ColorDrawable(0xa0000000);
        }
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        View bt_cancel = mainView.findViewById(R.id.bt_cancel);
        if (bt_cancel != null) {
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (isClose) {
                    View popu = view.findViewById(R.id.popup_window_context);
                    if (popu == null) return false;
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

    public abstract View createContextView(View mainView);

    public abstract boolean isTransparent();
}
