package cn.com.taodaji.ui.activity.cashCoupon;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.CashCouponAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CouponsFindreciveList;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import tools.activity.SimpleToolbarActivity;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class CashCouponsGetActivity extends SimpleToolbarActivity implements OnGetDataListener, SwipeRefreshLayout.OnRefreshListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setTitle("领取代金券");
    }

    private SwipeToLoadLayout swipeToLoadLayout;
    private LoadMoreUtil loadMoreUt;
    private LinearLayout mLinearLayoutBg;

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.swipe_twitter_swipe_toload_recyclerview);
        body_other.addView(view);
        swipeToLoadLayout = ViewUtils.findViewById(view, R.id.swipeToLoadLayout);
//        mLinearLayoutBg = ViewUtils.findViewById(view,R.id.swipe_twitter_swipe_toload_recyclerview_bg_linearlayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        RecyclerView recyclerView = ViewUtils.findViewById(view, R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setPadding(DensityUtil.dp2px(7.5f), DensityUtil.dp2px(10), DensityUtil.dp2px(10), 0);
        recyclerView.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(10), R.color.transparent));
        loadMoreUt = new LoadMoreUtil(this, recyclerView);
        CashCouponAdapter couponAdapter = new CashCouponAdapter();
        couponAdapter.setStatus(3);
        // couponAdapter.setSideslip(true);
        recyclerView.setAdapter(couponAdapter);
        swipeToLoadLayout.setRefreshing(true);

        couponAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, final int position, Object bean) {
                if (onclickType == 0) {
                    if (PublicCache.loginPurchase == null) return true;
                    CouponsFindreciveList.DataBean.ItemsBean itemsBean = (CouponsFindreciveList.DataBean.ItemsBean) bean;
                    if (itemsBean == null) return true;
                    loading();
                    Map<String, Object> map = new HashMap<>();
                    map.put("couponId", itemsBean.getEntityId());
                    map.put("account", PublicCache.loginPurchase.getPhoneNumber());
                    map.put("accountName", PublicCache.loginPurchase.getHotelName());
                    map.put("startTime", itemsBean.getStartTime());
                    map.put("endTime", itemsBean.getEndTime());

                    map.put("userType", 0);
                    map.put("userId", PublicCache.loginPurchase.getEntityId());

                    getRequestPresenter().coupons_received(map, new ResultInfoCallback() {
                        @Override
                        public void onFailed(int o, String msg) {
                            loadingDimss();
                            UIUtils.showToastSafesShort(msg);
                        }

                        @Override
                        public void onSuccess(Object body) {
                            loadingDimss();
                            loadMoreUt.refreshData(position, 9);
                        }
                    });

                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void getData(int pn) {
        if (PublicCache.loginPurchase == null) return;

        getRequestPresenter().coupons_chooseCouponList(PublicCache.loginPurchase.getEntityId(),PublicCache.loginPurchase.getIsC(), pn, 9, new RequestCallback<CouponsFindreciveList>() {
            @Override
            protected void onSuc(CouponsFindreciveList body) {
                if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                loadMoreUt.setData(body.getData().getItems(), body.getData().getPn(), body.getData().getPs());
            }

            @Override
            public void onFailed(int couponsFindreciveList, String msg) {
                if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        getData(1);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CashCouponsGetActivity.class));
    }

}
