package cn.com.taodaji.ui.activity.myself;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.FavoriteEvent;
import cn.com.taodaji.ui.fragment.FavoriteFragment;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018/12/5.
 */
public class FavoriteActivity extends SimpleToolbarActivity {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的收藏");
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<FavoriteFragment> list = new ArrayList<>();

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.tablayout_tabs_top);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(FavoriteEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (viewPager != null) {
            int index = 0;
            if (event.type == 1) {
                index = 1;
            }
            viewPager.setCurrentItem(index, false);
        }
    }

    private void initViewPager(View mainView) {
        viewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);
        ManageActivityPagerAdapter activityAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(activityAdapter);
        activityAdapter.setFragments(list);

        tabLayout.setupWithViewPager(viewPager);
    }

    private List<FavoriteFragment> initFragment() {
        list.clear();
        String[] title = {"关注的店铺", "收藏的商品"};
        for (int i = 2; i > 0; i--) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            favoriteFragment.setType(i);
            favoriteFragment.setTitle(title[i - 1]);
            list.add(favoriteFragment);
        }
        return list;
    }
}
