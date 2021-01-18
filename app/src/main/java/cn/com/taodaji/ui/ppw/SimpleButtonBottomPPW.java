package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;

/**
 * Created by yangkuo on 2018/12/11.
 */
public class SimpleButtonBottomPPW extends PopupWindow {

    private boolean isClose = true;
    private View mainView;

    public SimpleButtonBottomPPW(View mainView) {
        this.mainView = mainView;
        if (mainView == null) return;
        // 设置SelectPicPopupWindow的View
        setContentView(mainView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopupWindow);
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

        mainView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (isClose) {
                    View popu = mainView.findViewById(R.id.popup_window_context);
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

    public boolean isTransparent() {
        return false;
    }


    public void setCancelButton(@IdRes int idRes, View.OnClickListener clickListener) {
        View bt_cancel = mainView.findViewById(idRes);
        if (bt_cancel != null) bt_cancel.setOnClickListener(clickListener);
    }

    public void setPostButton(@IdRes int idRes, View.OnClickListener clickListener) {
        if (mainView == null) return;
        View bt_ok = mainView.findViewById(idRes);
        if (bt_ok != null) bt_ok.setOnClickListener(clickListener);
    }

}
