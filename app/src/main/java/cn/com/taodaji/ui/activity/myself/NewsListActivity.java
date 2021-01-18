package cn.com.taodaji.ui.activity.myself;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.NewsAndCount;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.event.WaitCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.fragment.EvaluateManageFragment;
import cn.com.taodaji.ui.fragment.NewsListFragment;
import cn.com.taodaji.ui.fragment.NoticeFragment;
import cn.com.taodaji.ui.fragment.PunishmentFragment;
import cn.com.taodaji.ui.fragment.WaitFragment;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.common.PublicCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import tools.activity.SimpleToolbarActivity;
import tools.fragment.DataBaseFragment;


/**
 * Created by Administrator on 2018/1/18.
 * 通知
 */

public class NewsListActivity extends SimpleToolbarActivity  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<DataBaseFragment> list = new ArrayList<>();

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("通知");
    }

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

    private void initViewPager(View mainView) {
        viewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);
        ManageActivityPagerAdapter activityAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(activityAdapter);
        activityAdapter.setFragments(list);

        tabLayout.setupWithViewPager(viewPager);
    }

    private List<DataBaseFragment> initFragment() {
        list.clear();

        NewsListFragment newsListFragment=new NewsListFragment();
        newsListFragment.setTitle("消息");
        PunishmentFragment punishmentFragment= new PunishmentFragment();
        punishmentFragment.setTitle("处罚公告");
        list.add(newsListFragment);
        list.add(punishmentFragment);
        return list;
    }

    @Override
    protected void initData() {
        if (PublicCache.loginSupplier == null) {
            return;
        }
        getNoticeCount();
    }

    private void getNoticeCount(){
        getRequestPresenter().getInstance().getNewAndCount(PublicCache.loginSupplier.getEntityId(), new RequestCallback<NewsAndCount>() {
            @Override
            protected void onSuc(NewsAndCount body) {
                tabLayout.getTabAt(0).setText("消息（"+body.getData().getNotReadPushMessageRes()+"）");
                tabLayout.getTabAt(1).setText("处罚公告（"+body.getData().getNotReadPunishmentRes()+"）");
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
