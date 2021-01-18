package cn.com.taodaji.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CouponsFindreciveList;
import cn.com.taodaji.model.event.CashCouponTabCountEvent;
import cn.com.taodaji.model.event.ShopManagementTabLayoutTextEvent;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.market.ShopGoodsDetailActivity;
import cn.com.taodaji.viewModel.adapter.CashCouponAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CouponsFindByUser;

import com.base.retrofit.RequestCallback;

import tools.activity.MenuToolbarActivity;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;


public class CashCouponListFragment extends LoadMoreRecyclerViewFragment {

    //status：0未使用，1已使用，2已过期，
    private int status;

    private CashCouponAdapter couponAdapter;

    private ImageView iv2,iv1;
    private LinearLayout ll_time,ll_money,ll;
    private int createTimelsAsc;
    private int amountlsAsc;
    private int time_coun = 1;//时间排序的点击次数
    private int money_coun = 1;//金额排序的点击次数

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void initData() {

        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.setPadding(DensityUtil.dp2px(7.5f), DensityUtil.dp2px(7.5f), DensityUtil.dp2px(10), 0);
        recycler_targetView.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(10), R.color.transparent));
        couponAdapter = new CashCouponAdapter();
        couponAdapter.setStatus(status);
        recycler_targetView.setAdapter(couponAdapter);
            View head_view = ViewUtils.getLayoutView(getContext(), R.layout.sort_layout);
            ll_time= ViewUtils.findViewById(head_view, R.id.ll_time);
            ll_money= ViewUtils.findViewById(head_view, R.id.ll_money);
            ll= ViewUtils.findViewById(head_view, R.id.ll);
            iv1= ViewUtils.findViewById(head_view, R.id.iv1);
            iv2= ViewUtils.findViewById(head_view, R.id.iv2);
        top_view.setPadding(DensityUtil.dp2px(10f), DensityUtil.dp2px(10f), DensityUtil.dp2px(10f), DensityUtil.dp2px(10f));
                 top_view.addView(head_view);
            if (status==-1){
                iv2.setImageResource(R.mipmap.quanhui_bottom);
                createTimelsAsc=0;


                iv1.setImageResource(R.mipmap.quanhui_bottom);
                amountlsAsc=0;
                top_view.setVisibility(View.VISIBLE);
            }else {
                top_view.setVisibility(View.GONE);
            }

            ll_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time_coun++;
                    if (time_coun % 2 == 1) {
                        iv1.setImageResource(R.mipmap.quanhui_bottom);
                        createTimelsAsc=0;
                    }else {
                        iv1.setImageResource(R.mipmap.quanhui_top);
                        createTimelsAsc=1;
                    }
                    swipeToLoadLayout.setRefreshing(true);

                }
            });
            ll_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    money_coun++;
                    if (money_coun % 2 == 1) {
                        iv2.setImageResource(R.mipmap.quanhui_bottom);
                        amountlsAsc=0;
                    }else {
                        iv2.setImageResource(R.mipmap.quanhui_top);
                        amountlsAsc=1;
                    }
                    swipeToLoadLayout.setRefreshing(true);
                }
            });




        loadMoreUtil.setZeroDataView(R.mipmap.no_djq, "当前暂无代金券");
        swipeToLoadLayout.setRefreshing(true);
        // onRefresh();

        couponAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, final int position, Object bean) {
                if (onclickType == 0) {
                    if (PublicCache.loginPurchase == null) return true;
                    CouponsFindByUser.DataBean.ItemsBean itemsBean = (CouponsFindByUser.DataBean.ItemsBean) bean;
                    if (status==0){
                        if ((Integer)itemsBean.getStoreId()>0) {
                            if (itemsBean.getType()==1){
                                ShopDetailInformationActivity.startActivity(view.getContext(), (Integer)itemsBean.getStoreId());

                            }else {
                                ShopGoodsDetailActivity.startActivity(view.getContext(),itemsBean.getProductIds());
                            }
                        }else {
                            MenuToolbarActivity.goToPage(0);
                        }

                    }else {


                    if (itemsBean.getCanCollect()==0)return true;
                    ShowLoadingDialog.showLoadingDialog(getContext());
                    if (itemsBean == null) return true;
                    Map<String, Object> map = new HashMap<>();
                    map.put("couponId", itemsBean.getEntityId());
                    map.put("account", PublicCache.loginPurchase.getPhoneNumber());
                    map.put("accountName", PublicCache.loginPurchase.getHotelName());
                    map.put("startTime", itemsBean.getStartTime());
                    map.put("endTime", itemsBean.getEndTime());

                    map.put("userType", 0);
                    map.put("userId", PublicCache.loginPurchase.getEntityId());
                    map.put("expireTime", itemsBean.getExpireTime());
                    map.put("expiryTimeUnit", itemsBean.getExpiryTimeUnit());
                    getRequestPresenter().coupons_received(map, new ResultInfoCallback() {
                        @Override
                        public void onFailed(int o, String msg) {
                            ShowLoadingDialog.close();


                        }

                        @Override
                        public void onSuccess(Object body) {
                            ShowLoadingDialog.close();
                            UIUtils.showToastSafesShort("领取成功");
                            swipeToLoadLayout.setRefreshing(true);
//                            loadMoreUtil.refreshData(position, 9);
                        }
                    });
                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onUserVisible() {
        if (recycler_targetView.getChildCount() == 0) super.onUserVisible();
    }
    @Subscribe
    public void onTabText(CashCouponTabCountEvent event) {
    }
    @Override
    public void getData(int pn) {
        if (PublicCache.loginPurchase == null) return;
        if (status>=0){
            getRequestPresenter().coupons_findByUser(PublicCache.loginPurchase.getEntityId(), status, pn, 9, new RequestCallback<CouponsFindByUser>() {
                @Override
                protected void onSuc(CouponsFindByUser body) {
                    EventBus.getDefault().post(new ShopManagementTabLayoutTextEvent(body.getData().getTotal(), getTitle(), status));
                    loadMoreUtil.setData(body.getData().getItems(), body.getData().getPn(), body.getData().getPs());
                    stop();
                }

                @Override
                public void onFailed(int couponsFindByUser, String msg) {
                    UIUtils.showToastSafesShort(msg);
                    stop();
                }
            });

        }else {
            getRequestPresenter().coupons_findVoucher(PublicCache.loginPurchase.getEntityId(), amountlsAsc,createTimelsAsc,pn, 9, new RequestCallback<CouponsFindByUser>() {
                @Override
                protected void onSuc(CouponsFindByUser body) {
                    loadMoreUtil.setData(body.getData().getItems(), body.getData().getPn(), body.getData().getPs());
                    stop();
                }

                @Override
                public void onFailed(int couponsFindByUser, String msg) {
                    UIUtils.showToastSafesShort(msg);
                    stop();
                }
            });
        }


    }
}
