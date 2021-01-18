package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.TakeDown;
import cn.com.taodaji.model.entity.TakeUp;
import cn.com.taodaji.model.event.ShopManagementTabLayoutTextEvent;
import cn.com.taodaji.ui.fragment.MyselfShopManagement;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;

public class ShopManagementActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ManageActivityPagerAdapter mAdapter;
    private List<MyselfShopManagement> fragments;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("商品管理");
        right_icon.setImageResource(R.mipmap.ic_question_mark_white);
        right_textView.setText("橱窗说明");
        title_right.setOnClickListener(v -> startActivity(new Intent(getBaseActivity(), ShopWindowDeclareActivity.class)));
    }

    @Override
    protected void initMainView() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View mainView = getLayoutView(R.layout.tablayout_tabs_top);
        body_other.addView(mainView);

        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);
        ViewPager mViewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);

        fragments = initFragments();
        mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setFragments(fragments);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_blue_2898eb));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.blue_2898eb));
        tabLayout.setSelectedTabIndicatorHeight(5);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onTabText(ShopManagementTabLayoutTextEvent event) {
        if (tabLayout != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(event.getIndex());
            if (tab != null) tab.setText(event.getTitle() + "(" + event.getCount() + ")");
        }
    }

    @Subscribe
    public void onEvent(TakeUp takeUp) {
        if (fragments != null && fragments.size() > 0) {
            fragments.get(0).onRefresh();
        }
    }

    @Subscribe
    public void onEvent(TakeDown takeDown) {
        if (fragments != null && fragments.size() > 1) {
            fragments.get(1).onRefresh();
        }
    }

    private List<MyselfShopManagement> initFragments() {
        List<MyselfShopManagement> fragments = new ArrayList<>();
        for (int i = 0; i < SHOP_MANAGEMENT_LIST.size(); i++) {
            MyselfShopManagement sale = new MyselfShopManagement();
            sale.setTitle(SHOP_MANAGEMENT_LIST.get(i));
            sale.setStatus(i + 1);
            fragments.add(sale);
        }
        return fragments;
    }

    @Override
    protected void initData() {}
}
