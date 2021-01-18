package cn.com.taodaji.ui.activity.cashCoupon;


import android.content.Context;
import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.event.CashCouponTabCountEvent;
import cn.com.taodaji.model.event.ShopManagementTabLayoutTextEvent;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CouponsChooseCouponList;
import cn.com.taodaji.model.entity.CouponsStatistics;
import cn.com.taodaji.model.event.CashCouponUseEvent;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.fragment.CashCouponListFragment;
import cn.com.taodaji.ui.fragment.CashCouponUsedFragment;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.DataBaseFragment;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class CashCouponActivity extends SimpleToolbarActivity {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
    }


    private TabLayout tabLayout;

    private ViewPager viewPager;
    private List<DataBaseFragment> fragments;


    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutViewMatch(this, R.layout.tablayout_tabs_top_cashcoupon);
        body_other.addView(view);

        tabLayout = ViewUtils.findViewById(view, R.id.tabLayout);
        viewPager = ViewUtils.findViewById(view, R.id.tabLayout_viewpager);
        ViewUtils.findViewById(view, R.id.view).setVisibility(View.GONE);
        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));

        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setupWithViewPager(viewPager);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);


    }


    @Override
    protected void initData() {
        super.initData();
        onStartLoading();
        int state = getIntent().getIntExtra("data", -1);
        if (state == -1) return;

        if (state == 0) {
            setTitle("代金券");
            right_textView.setText("卡密券");
            fragments = initFragments("代金券中心","未使用", "使用记录", "已过期");
            title_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CashCouponCardPwdActivity.startActivity(getBaseActivity());
                }
            });
            getCashCouponData();
        } else {
            setTitle("使用代金券");
            getCashCouponUsedData();
            right_textView.setText("使用说明");

            title_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseActivity(), UseExplainActivity.class));
                }
            });
        }


    }

    //代金券数量
    private void getCashCouponData() {
        if (PublicCache.loginPurchase == null) return;
//        loading();
        getRequestPresenter().coupons_statistics(PublicCache.loginPurchase.getEntityId(), new RequestCallback<CouponsStatistics>(this) {
            @Override
            protected void onSuc(CouponsStatistics body) {
//                loadingDimss();
                ManageActivityPagerAdapter mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(mAdapter);
                mAdapter.setFragments(fragments);
                setTabCount(1, String.valueOf(body.getData().getUnused()));
                setTabCount(2, String.valueOf(body.getData().getUsed()));
                setTabCount(3, String.valueOf(body.getData().getExpired()));
            }

            @Override
            public void onFailed(int couponsStatistics, String msg) {
                UIUtils.showToastSafesShort(msg);
//                loadingDimss();
            }
        });

    }

    //使用代金券
    private void getCashCouponUsedData() {
        if (PublicCache.loginPurchase == null) return;
//        loading();
        List<CartGoodsBean> list = CartModel.getInstance().getCartList();
        String productInfo = "[";
        for (CartGoodsBean a : list) {
            if (a.getSelected())
                productInfo += "{\"productId\":" + a.getProductId() + ",\"price\":" + a.getProductPrice().toString() + ",\"qty\":" + a.getProductQty() + "},";
        }
        productInfo = productInfo.substring(0, productInfo.length() - 1) + "]";

        getRequestPresenter().coupons_chooseCouponList(PublicCache.loginPurchase.getEntityId(), productInfo, new RequestCallback<CouponsChooseCouponList>(this) {
            @Override
            protected void onSuc(CouponsChooseCouponList body) {
//                loadingDimss();
                if (fragments != null) return;
                List<CouponsChooseCouponList.DataBean.ItemBean> usedList = new ArrayList<>();
                usedList.addAll(body.getData().getUnlimit());
                usedList.addAll(body.getData().getLimit());

                fragments = initFragments1(usedList, body.getData().getDisable());
                ManageActivityPagerAdapter mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(mAdapter);
                mAdapter.setFragments(fragments);

                setTabCount(0, String.valueOf(body.getData().getUsableCount()));
                setTabCount(1, String.valueOf(body.getData().getDisableCount()));
            }

            @Override
            public void onFailed(int couponsFindReceiveList, String msg) {
//                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    private void setTabCount(int index, String text) {
        if (tabLayout != null && fragments.size() > index)
            tabLayout.getTabAt(index).setText(fragments.get(index).getTitle() + " (" + text + ")");
    }

    @Subscribe
    public void onTabText(CashCouponTabCountEvent event) {
        setTabCount(event.getIndex(), String.valueOf(event.getCount()));
    }


    private List<DataBaseFragment> initFragments(String... titles) {
        List<DataBaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            CashCouponListFragment fragment = new CashCouponListFragment();
            fragment.setTitle(titles[i]);
            fragment.setStatus(i-1);
            fragments.add(fragment);
        }
        return fragments;
    }

    /**
     * 将选中的代金券，发送到{@link cn.com.taodaji.ui.activity.orderPlace.SubmitOrderActivity#onEvent(CashCouponUseEvent)}
     */
    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        if (viewPager.getCurrentItem() == 0) {
            for (Fragment fragment : getFirstVisibleFragments()) {
                if (fragment instanceof CashCouponUsedFragment) {
                    CashCouponUsedFragment frag = (CashCouponUsedFragment) fragment;

                    EventBus.getDefault().post(frag.getCouponItemInfo());
                }
            }
        }
        super.onDestroy();
    }

    private List<DataBaseFragment> initFragments1(List<CouponsChooseCouponList.DataBean.ItemBean>... list) {
        String[] titles = {"可用优惠券", "不可用优惠券"};
        List<DataBaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            CashCouponUsedFragment fragment = new CashCouponUsedFragment();
            fragment.setTitle(titles[i]);
            fragment.setStatus(i + 4);
            fragment.setListData(list[i]);
            fragments.add(fragment);
        }
        return fragments;
    }

    public static void startActivity(Context context, int state) {
        //state 0 代金券  1使用代金券
        Intent intent = new Intent(context, CashCouponActivity.class);
        intent.putExtra("data", state);
        context.startActivity(intent);
    }


}
