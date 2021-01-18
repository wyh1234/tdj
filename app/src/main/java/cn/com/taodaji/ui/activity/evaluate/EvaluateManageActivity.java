package cn.com.taodaji.ui.activity.evaluate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.EvaluateWaitCountEvent;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.ui.fragment.EvaluateManageFragment;
import tools.activity.SimpleToolbarActivity;


public class EvaluateManageActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<EvaluateManageFragment> list = new ArrayList<>();
    private static CustomerData<Integer, String, String> evaluateType = PublicCache.getEvaluateType();
    public TextView text_evaluate_or_reply_tips;
    public TextView txt_go_evaluate_or_reply;
    public LinearLayout line_evaluate_tips;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("评价管理");
    }

    // private EvaluateManageFragment evaluateManageFragment;

    @Override
    protected void initMainView() {

//        if (evaluateManageFragment == null) {
//            evaluateManageFragment = new EvaluateManageFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.other_body, evaluateManageFragment).commit();
//        }

        View mainView = ViewUtils.getLayoutView(this, R.layout.tablayout_tabs_top);
        body_other.addView(mainView); //toolbar 添加view
        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);

        View tipsView= LayoutInflater.from(this).inflate(R.layout.layout_evaluate_tips,null);

        line_evaluate_tips = ViewUtils.findViewById(tipsView, R.id.line_evaluate_tips);
        text_evaluate_or_reply_tips = ViewUtils.findViewById(tipsView, R.id.text_evaluate_or_reply_tips);
        txt_go_evaluate_or_reply = ViewUtils.findViewById(tipsView, R.id.txt_go_evaluate_or_reply);

        line_top_tips.setVisibility(View.VISIBLE);
        line_top_tips.addView(tipsView);

        switch (PublicCache.login_mode) {//登录模式  ：默认  ，采购商，供货商
            case PURCHASER:     //采购商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));

                int wait = getIntent().getIntExtra("wait_evaluate", 0);
                getNewStutas(wait);
                break;
            case SUPPLIER:  //供货商
                tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_blue_2898eb));
                tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.blue_2898eb));
                break;
        }

        initFragment();
        initViewPager(mainView);
    }

    @Subscribe
    public void onEvent(EvaluateWaitCountEvent event) {
        getNewStutas(event.getWaitCount());
    }

    private void getNewStutas(int count) {
        if (count > 0) {
            if (PublicCache.login_mode.equals(PURCHASER)) {
                line_evaluate_tips.setVisibility(View.VISIBLE);
                line_group.setVisibility(View.VISIBLE);
                text_evaluate_or_reply_tips.setText(UIUtils.getString(R.string.evaluate_customer));
                txt_go_evaluate_or_reply.setVisibility(View.VISIBLE);
                txt_go_evaluate_or_reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ActivityManage.isActivityExist(OrderListActivity.class)) {
                            EventBus.getDefault().postSticky(new OrderEvent(4));
                            Intent intent = new Intent(EvaluateManageActivity.this, OrderListActivity.class);
                            startActivity(intent);
                        }
                        // onBackPressed();
                        //finish();
                    }
                });
            } else if (PublicCache.login_mode.equals(SUPPLIER)) {
                line_evaluate_tips.setVisibility(View.VISIBLE);
                line_group.setVisibility(View.VISIBLE);
                text_evaluate_or_reply_tips.setText(UIUtils.getString(R.string.evaluate_supplier));
                txt_go_evaluate_or_reply.setVisibility(View.GONE);
            }

        } else {
            line_evaluate_tips.setVisibility(View.GONE);
            line_group.setVisibility(View.GONE);
        }
    }

    private void initViewPager(View mainView) {
        viewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);
        ManageActivityPagerAdapter activityAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(activityAdapter);
        activityAdapter.setFragments(list);

        tabLayout.setupWithViewPager(viewPager);
    }

    private List<EvaluateManageFragment> initFragment() {
        list.clear();
        int[] arrays = null;

        switch (PublicCache.login_mode) {
            case PURCHASER:
                arrays = new int[]{0, 1, 2};
                break;
            case SUPPLIER:
                arrays = new int[]{0, 1, 3, 4};
                break;
        }
        for (int id : arrays) {
            EvaluateManageFragment evaluateManageFragment = new EvaluateManageFragment();
            evaluateManageFragment.setStatus_code(id);

            evaluateManageFragment.setTitle(evaluateType.getValueOfId(id));
            list.add(evaluateManageFragment);
        }
        return list;
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
