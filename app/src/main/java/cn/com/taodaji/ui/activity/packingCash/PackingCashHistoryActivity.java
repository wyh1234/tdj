package cn.com.taodaji.ui.activity.packingCash;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;

public class PackingCashHistoryActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<PackingCashFragment> list = new ArrayList<>();

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("历史押金");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_packing_cash_history);
        body_other.addView(mainView); //toolbar 添加view
        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);
        switch (PublicCache.login_mode) {//登录模式  ：默认  ，采购商，供货商
            case PURCHASER:     //采购商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                break;
            case SUPPLIER:  //供货商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_blue_2898eb));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.blue_2898eb));
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

    private List<PackingCashFragment> initFragment() {
        list.clear();
        String[] title = {"处理中押金", "未退押金","已支付押金", "已退押金"};
      /*  for (int i = 2; i > 0; i--) {
            PackingCashFragment packingCashFragment = new PackingCashFragment();
            packingCashFragment.setType(i);
            packingCashFragment.setTitle(title[i - 1]);
            list.add(packingCashFragment);
        }*/

        for (int i = 4; i > 2; i--) {
            PackingCashFragment packingCashFragment = new PackingCashFragment();
            packingCashFragment.setType(i);
            //packingCashFragment.setTablayoutView(tabLayout);
            packingCashFragment.setTitle(title[i - 1]);
            list.add(packingCashFragment);
        }
        return list;
    }
}
