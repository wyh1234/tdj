package cn.com.taodaji.ui.activity.myself;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.base.activity.BaseFragment;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.event.WaitCountEvent;
import cn.com.taodaji.model.event.WaitEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.fragment.FavoriteFragment;
import cn.com.taodaji.ui.fragment.NoticeFragment;
import cn.com.taodaji.ui.fragment.WaitFragment;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.DataBaseFragment;

public class NoticeWaitManageActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<DataBaseFragment> list = new ArrayList<>();
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_wait);
    }*/

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
            case PURCHASER: //采购商
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

        WaitFragment waitFragment= new WaitFragment();
        waitFragment.setTitle("待办事件");
        NoticeFragment noticeFragment=new NoticeFragment();
        noticeFragment.setTitle("消息");
        list.add(waitFragment);
        list.add(noticeFragment);
        return list;
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        if (PublicCache.loginPurchase == null) {
            return;
        }
        getNoticeCount();

    }

    private void getNoticeCount(){


        addRequest(ModelRequest.getInstance(1).getNoticeUnReadCount(PublicCache.loginPurchase.getEmpRole(),PublicCache.loginPurchase.getLoginUserId(), PublicCache.site_login), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                int count= body.getData().getCount();
                tabLayout.getTabAt(1).setText("消息（"+count+"）");
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(WaitCountEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        int count=event.getCount();
        tabLayout.getTabAt(0).setText("待办事件（"+count+"）");

    }
    @Subscribe(sticky = true)
    public void onEvent(NoticeCountEvent event) {
       // getNoticeCount();
        tabLayout.getTabAt(1).setText("消息（"+0+"）");
    }
}
