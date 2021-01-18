package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.ui.activity.myself.IdentityManageActivity;
import cn.com.taodaji.ui.activity.myself.PickupServiceActivity;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.fragment.OrderListFragment;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * 我的订单activity
 **/
public class OrderListActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView pickupAD;
    private CustomerData<String, String, String> order_state = PublicCache.getOrderState();

    private List<OrderListFragment> list = new ArrayList<>();


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的订单");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        switch (PublicCache.login_mode){
            case PURCHASER:     //采购商
                pickupAD.setVisibility(View.GONE);
                break;
            case SUPPLIER:  //供货商
                if (PublicCache.loginSupplier.getIsAllOpen()==1){
                    pickupAD.setVisibility(View.GONE);
                }
                break;
        }
    }


    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(OrderEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        viewPager.setCurrentItem(event.code, false);
    }


    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.tablayout_tabs_top);
        body_other.addView(mainView); //toolbar 添加view
        pickupAD = ViewUtils.findViewById(mainView,R.id.iv_pickup_ad);
        pickupAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderListActivity.this, PickupServiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);
        switch (PublicCache.login_mode) {//登录模式  ：默认  ，采购商，供货商
            case PURCHASER:     //采购商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                pickupAD.setVisibility(View.GONE);
                break;
            case SUPPLIER:  //供货商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_blue_2898eb));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.blue_2898eb));
                if (PublicCache.loginSupplier.getIsAllOpen()!=1){
                    pickupAD.setVisibility(View.VISIBLE);
                }
                break;
        }

        initFragment();
        initViewPager(mainView);
    }

    private void initViewPager(View mainView) {
        viewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);
        ManageActivityPagerAdapter activityAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(activityAdapter);
        activityAdapter.setFragments(list);

        tabLayout.setupWithViewPager(viewPager);
    }

    private List<OrderListFragment> initFragment() {
        list.clear();
        int[] arrays = null;

        switch (PublicCache.login_mode) {
            case PURCHASER:
                arrays = new int[]{-1, 0, 4, 1, 2};
                break;
            case SUPPLIER:
                arrays = new int[]{-1, 3, 11, 5,9};
                break;
        }
        for (int i : arrays) {
            OrderListFragment orderListFragment = new OrderListFragment();
            orderListFragment.setStatus_code(i);
            if (i == -1) {
                orderListFragment.setTitle("全部");
                orderListFragment.isCreateAfterSale = true;
            } else {
                orderListFragment.setTitle(order_state.getValueOfId(i));
                orderListFragment.isCreateAfterSale = false;
            }
            list.add(orderListFragment);
        }
        return list;
    }

    @Override
    protected void initData() {

    }
}
