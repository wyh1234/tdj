package cn.com.taodaji.ui.ppw;

import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.common.Constants;
import com.base.utils.ViewUtils;

public abstract class ShopStateRemarkPopupWindow extends BasePopupWindow implements View.OnClickListener {

    public ShopStateRemarkPopupWindow(View mainView) {
        super(mainView);
    }

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_shop_state_remark);
        if (PublicCache.loginSupplier == null) return view;
        int state = PublicCache.loginSupplier.getStoreStatus();
        TextView shop_state = ViewUtils.findViewById(view, R.id.shop_state);
        shop_state.setText(Constants.STORE_STATUS.get(state));
        TextView date_start = ViewUtils.findViewById(view, R.id.date_start);
        TextView date_end = ViewUtils.findViewById(view, R.id.date_end);
        TextView state_remark = ViewUtils.findViewById(view, R.id.state_remark);
        ViewUtils.findViewById(view, R.id.business).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.rest).setOnClickListener(this);
        if (state == 4) {
            shop_state.setText(Constants.STORE_STATUS.get(state) + ",当前店铺已被关闭");
            date_start.setText("(" + PublicCache.loginSupplier.getCloseStoreDatetime());
            date_end.setText(PublicCache.loginSupplier.getCloseStoreEndDatetime() + ")");
            state_remark.setText("原因：" + PublicCache.loginSupplier.getStoreStatusRemark());
        } else if (state == 2 || state == 3) {

            date_start.setText("(" + PublicCache.loginSupplier.getCloseStoreDatetime());
            date_end.setText(PublicCache.loginSupplier.getCloseStoreEndDatetime() + ")");
            state_remark.setText("原因：" + PublicCache.loginSupplier.getStoreStatusRemark());
        } else {
            ViewUtils.findViewById(view, R.id.textView).setVisibility(View.GONE);
            state_remark.setVisibility(View.GONE);
        }
        ViewUtils.findViewById(view, R.id.popupWindow_close).setOnClickListener(this);
        return view;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    public abstract void leftOnclick();

    public abstract void rightOnclick();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popupWindow_close:
                dismiss();
                break;
            case R.id.rest:
                leftOnclick();
                dismiss();
                break;
            case R.id.business:
                rightOnclick();
                dismiss();
                break;
        }
    }
}
