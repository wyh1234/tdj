package cn.com.taodaji.ui.ppw;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public abstract class PickFoodSortPopupWindow extends BasePopupWindow implements View.OnClickListener, PopupResultInterface {

    private View textView;

    public PickFoodSortPopupWindow(View mainView) {
        super(mainView);
        Rect rectF = DensityUtil.getViewRectInWindow((View) mainView.getParent());
        setHeight(UIUtils.getScreenHeightPixels() - rectF.bottom - 3);
        textView = mainView;
    }

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_pick_food_sort);
        ViewUtils.findViewById(view, R.id.sort_sale).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.sort_price).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.sort_popularity).setOnClickListener(this);
        return view;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_sale:
                setResult("{qty:1}");
                break;
            case R.id.sort_price:
                setResult("{price:0}");
                break;
            case R.id.sort_popularity:
                setResult("{popularity:1}");
                break;
        }
        TextView tview = (TextView) textView;
        TextView tview_click = (TextView) view;
        tview.setText(tview_click.getText().toString().substring(0,2));
        dismiss();
    }
}
