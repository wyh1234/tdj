package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.HomepageGridDatas;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.ui.fragment.CommendListFragment;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;

public class CommendActivity extends SimpleToolbarActivity {

    private CommendListFragment commendListFragment;
    private TextView tv_shopping_count;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);


    }

    @Override
    protected void initMainView() {
        View shopping_floating_button = ViewUtils.findViewById(this, R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        shopping_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuToolbarActivity.goToPage(3);
            }
        });
        View mShoppingCart = ViewUtils.findViewById(shopping_floating_button, R.id.iv_shopping_cart);
        tv_shopping_count = ViewUtils.findViewById(shopping_floating_button, R.id.tv_shopping_count);
        tv_shopping_count.setText(String.valueOf(CartModel.getInstance().getCount()));

        if (commendListFragment == null) {
            commendListFragment = new CommendListFragment();
            commendListFragment.setmShoppingCart(mShoppingCart);
            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, commendListFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    //接收商品数量变化，改变悬浮按扭的数值
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        tv_shopping_count.setText(String.valueOf(CartModel.getInstance().getCount()));
    }

    @Subscribe(sticky = true)
    public void onEvent(HomepageGridDatas datas) {
        EventBus.getDefault().removeStickyEvent(datas);
        commendListFragment.setData(datas, datas.getIsShowIndex());
        simple_title.setText("店长推荐" + datas.getList().get(datas.getIsShowIndex()).getName() + "专区");
    }

    public static void startActivity(Context context, HomepageGridDatas datas) {
        EventBus.getDefault().postSticky(datas);
        context.startActivity(new Intent(context, CommendActivity.class));
    }
}
