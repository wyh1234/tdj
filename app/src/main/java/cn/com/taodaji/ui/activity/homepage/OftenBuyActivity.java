package cn.com.taodaji.ui.activity.homepage;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.fragment.OffenBuyFragment;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class OftenBuyActivity extends SimpleToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        if (getIntent().getStringExtra("type").equals("2")) {
            setTitle("经常买");
        }else {
            right_icon.setImageResource(R.mipmap.ic_customer_service_white);
            right_icon.setOnClickListener(this);
            search_text.setVisibility(View.VISIBLE);
            search_text.setOnClickListener(this);
        }

    }

    private ViewPager mViewPager;
    private List<OffenBuyFragment> fragments;


    private View iv_shopping_cart;
    private TextView tv_shopping_count;

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.tablayout_tabs_top);
        body_other.addView(mainView);


        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        iv_shopping_cart = findViewById(R.id.iv_shopping_cart);
        tv_shopping_count = findViewById(R.id.tv_shopping_count);


        TabLayout tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);
        mViewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @Override
    protected void initData() {
        getGoodsCategoryList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getGoodsCategoryList() {
        if (fragments != null) return;
        loading();
        getRequestPresenter().findCategoryList(PublicCache.site_login, PublicCache.refreshId,new RequestCallback<GoodsCategoryList_Resu>() {
            @Override
            public void onSuc(GoodsCategoryList_Resu body) {
                if (!body.getData().isEmpty()) {
                    fragments = initFragments(body.getData());
                    ManageActivityPagerAdapter mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mAdapter);
                    mAdapter.setFragments(fragments);
                    //updateChildFragment();
                    loadingDimss();
                }
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                loadingDimss();
            }
        });
    }

    private List<OffenBuyFragment> initFragments(List<GoodsCategoryList> body) {
        List<OffenBuyFragment> fragments = new ArrayList<>();
        for (GoodsCategoryList gcl : body) {
            OffenBuyFragment sale = new OffenBuyFragment();
            sale.setTitle(gcl.getCategoryName());
            sale.setCatalogId(gcl.getCategoryId());//一级分类
            sale.setIv_shopping_cart(iv_shopping_cart);
            sale.setTv_shopping_count(tv_shopping_count);
            fragments.add(sale);
        }
        return fragments;
    }

    //更新内容
    private void updateChildFragment() {
        List<Fragment> fragments = getFirstVisibleFragments();
        if (fragments != null) for (Fragment fragment : fragments) {
            if (fragment instanceof OffenBuyFragment) {
                OffenBuyFragment fn = (OffenBuyFragment) fragment;
                fn.update();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_icon:
                /** 拨打电话*/
                SystemUtils.callPhone(this, PhoneUtils.getPhoneService());
                break;
            case R.id.search_text:
                Intent intent = new Intent(this, SearchNewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
