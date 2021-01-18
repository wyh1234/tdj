package cn.com.taodaji.ui.activity.packingCash;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.activity.login.PackingCashExplainActivity;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyActivity;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;

public class PackingCashCurrentActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<PackingCashFragment> list = new ArrayList<>();
    private TextView txt_look_tips;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("包装物押金");
    }

    @Override
    protected void initMainView() {

        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_packing_cash_current);
        body_other.addView(mainView); //toolbar 添加view
        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);

        right_icon.setImageResource(R.mipmap.ic_back_money_tips);
        right_icon_text.setText("说明");
        right_onclick_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackingCashExplainActivity.startActivity(getBaseActivity());
            }
        });

        View tipsView= LayoutInflater.from(this).inflate(R.layout.top_tips_txt,null);
        txt_look_tips = ViewUtils.findViewById(tipsView, R.id.txt_look_tips);
        line_top_tips.setVisibility(View.VISIBLE);
        line_top_tips.addView(tipsView);


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
        String[] title = {"处理中押金", "未申请退押","已支付押金", "已退押金"};
        for (int i = 2; i > 0; i--) {
            PackingCashFragment packingCashFragment = new PackingCashFragment();
            packingCashFragment.setType(i);
            packingCashFragment.setTablayoutView(tabLayout);
            packingCashFragment.setTextTips(txt_look_tips);
            packingCashFragment.setTitle(title[i - 1]);
            list.add(packingCashFragment);
        }

      /*  for (int i = 4; i > 1; i--) {
            PackingCashFragment packingCashFragment = new PackingCashFragment();
            packingCashFragment.setType(i);
            packingCashFragment.setTitle(title[i - 1]);
            list.add(packingCashFragment);
        }*/
        return list;
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PackingCashCurrentActivity.class);
        context.startActivity(intent);
    }
}
