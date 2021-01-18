package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.utils.BitmapUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-07-05.
 */

public class SearchToolBarPopupWindow extends PopupWindow implements View.OnClickListener {

    public SearchToolBarPopupWindow(Context context, int width) {
        View view = ViewUtils.getLayoutView(context, R.layout.popup_window_search_toolbar);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setBackgroundDrawable(BitmapUtil.getColorDrawable(R.color.transparent));

        ViewUtils.findViewById(view, R.id.goods).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.shop).setOnClickListener(this);

    }

    private PopupResultInterface resultInterface;


    public void setResultInterface(PopupResultInterface resultInterface) {
        this.resultInterface = resultInterface;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.goods:
                TextView textView = (TextView) view;
                resultInterface.setResult(textView.getText().toString());
                dismiss();
                break;
            case R.id.shop:
                TextView textView1 = (TextView) view;
                resultInterface.setResult(textView1.getText().toString());
                dismiss();
                break;
        }
    }
}
