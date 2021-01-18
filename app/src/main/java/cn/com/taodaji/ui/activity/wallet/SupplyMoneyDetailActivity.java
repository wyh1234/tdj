package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.LogSupplierCapitalFlow;
import tools.activity.SimpleToolbarActivity;

public class SupplyMoneyDetailActivity extends SimpleToolbarActivity {
    GlideImageView shopLogo;
    TextView shopName;
    TextView type;
    TextView totalPrice;
    TextView state;
    TextView name;
    TextView description;
    TextView price;
    TextView unit1;
    TextView unit2;
    TextView address;
    TextView orderNo;
    TextView orderTime;
    TextView count;
    LinearLayout cancel_order;
    TextView cancel_time;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("明细详情");
    }

    @Override
    protected void initMainView() {

        View mainView = getLayoutView(R.layout.activity_supply_money_deatil);
        body_other.addView(mainView);
        shopLogo = ViewUtils.findViewById(mainView, R.id.shop_logo);
        shopName = ViewUtils.findViewById(mainView, R.id.shop_name);
        type = ViewUtils.findViewById(mainView, R.id.type);
        totalPrice = ViewUtils.findViewById(mainView, R.id.total_price);
        state = ViewUtils.findViewById(mainView, R.id.state);
        name = ViewUtils.findViewById(mainView, R.id.name);
        description = ViewUtils.findViewById(mainView, R.id.description);
        price = ViewUtils.findViewById(mainView, R.id.price);
        unit1 = ViewUtils.findViewById(mainView, R.id.unit_1);
        unit2 = ViewUtils.findViewById(mainView, R.id.unit_2);
        address = ViewUtils.findViewById(mainView, R.id.address);
        orderNo = ViewUtils.findViewById(mainView, R.id.order_no);
        orderTime = ViewUtils.findViewById(mainView, R.id.order_time);
        count = ViewUtils.findViewById(mainView, R.id.count);

        cancel_order = mainView.findViewById(R.id.cancel_order);
        cancel_time = mainView.findViewById(R.id.cancel_time);


        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(LogSupplierCapitalFlow.ItemsBean ben) {
        EventBus.getDefault().removeStickyEvent(ben);
        ImageLoaderUtils.loadImage(shopLogo,  ben.getCustomer_img());
        shopName.setText(ben.getHotel_name());
        String value;
        if (PublicCache.loginSupplier != null) value = 2 == ben.getType() ? "-" : "+";
        else value = 2 == ben.getType() ? "+" : "-";
        // type.setText(ben.getType() == 2 ? "-" : "+");
        type.setText(value);
        if (ben.getCapital_change_reason().contains("取消")) {
            type.setText("-");
            cancel_order.setVisibility(View.VISIBLE);
            cancel_time.setText(ben.getCreate_time());
        }
        totalPrice.setText(String.valueOf(ben.getTotal_price()));
        state.setText(ben.getCapital_change_reason());


        name.setText(ben.getProduct_name());
        description.setText(ben.getProduct_desc());
        price.setText(String.valueOf(ben.getProduct_price()));


        String price = ben.getUnit();
        if (ben.getLevel_type() == 2) {
            price += "(" + String.valueOf(ben.getLevel_2_value()) + ben.getLevel_2_unit() + ")";

        } else if (ben.getLevel_type() == 3) {
            price += "(" + String.valueOf(ben.getLevel_2_value()) + ben.getLevel_2_unit() + "*" + String.valueOf(ben.getLevel_3_value() + ben.getLevel_3_unit()) + ")";
        }

        unit1.setText(price);


        unit2.setText(ben.getUnit());
        count.setText(String.valueOf(ben.getProduct_qty()));
        address.setText(ben.getReceive_address());
        orderNo.setText(ben.getOrder_no());
        orderTime.setText(ben.getCreate_order_time());
    }

    public static void startActivity(Context context, LogSupplierCapitalFlow.ItemsBean bean) {
        EventBus.getDefault().postSticky(bean);
        context.startActivity(new Intent(context, SupplyMoneyDetailActivity.class));
    }


}
