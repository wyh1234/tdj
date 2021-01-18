package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by Administrator on 2017-12-27.
 */

public class ShippingAddressActivity extends SimpleToolbarActivity {


    TextView shoppingAddressNameTv;
    TextView shoppingAddressPhoneTv;
    TextView shoppingAddressTv;

    private View mainView;

    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("配送地址");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_shopping_address);
        body_other.addView(mainView);
        shoppingAddressNameTv = ViewUtils.findViewById(mainView, R.id.shopping_address_name_tv);
        shoppingAddressPhoneTv = ViewUtils.findViewById(mainView, R.id.shopping_address_phone_tv);
        shoppingAddressTv = ViewUtils.findViewById(mainView, R.id.shopping_address_tv);
    }

    @Override
    protected void initData() {
        if (PublicCache.loginSupplier != null) {
            shoppingAddressNameTv.setText(PublicCache.loginSupplier.getStationName());
            shoppingAddressPhoneTv.setText(PublicCache.loginSupplier.getStationOfficePhone());
            shoppingAddressTv.setText(PublicCache.loginSupplier.getStationAddress());
        }

    }


}
