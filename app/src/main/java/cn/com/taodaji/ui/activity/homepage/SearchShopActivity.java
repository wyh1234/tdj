package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.FavoriteRefreshEvent;
import cn.com.taodaji.viewModel.adapter.SimpleShopListAdapter;
import cn.com.taodaji.model.entity.GoodsInformation;

import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import cn.com.taodaji.model.entity.SearchShop;
import cn.com.taodaji.model.entity.ShopDetail;

import com.base.retrofit.ResultInfoCallback;

import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SearchShopActivity extends SearchBaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnGetDataListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        super.titleSetting(toolbar);
        search_edit.setBackgroundResource(R.drawable.z_round_rect_gray_6a);

        search_edit.removeTextChangedListener(search_edit);
        search_edit.removeDrawableRightListener(search_edit);
        search_edit.setCompoundDrawables(null, null, null, null);

        setSearchText("店铺");
        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput(search_edit.getWindowToken());
                finish();
                // String ss = search_edit.getText().toString();
                //   if (ss.length() == 0) return;
                //  onClicked(ss);
            }
        });
    }

    private SimpleShopListAdapter shopView_adapter;

    private int pageNo;//当前页码
    private RecyclerView search_recyclerView, goods_recycle;//搜索结果的列表
    private SwipeToLoadLayout swipeToLoadLayout;
    private LinearLayout shop_view;//店铺的view
    private LoadMoreUtil loadMoreUtils;

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_search_shop);
        body_other.addView(mainView);
        shop_view = ViewUtils.findViewById(mainView, R.id.shop_view);
        search_recyclerView = ViewUtils.findViewById(mainView, R.id.search_recyclerView);
        search_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_recyclerView.addItemDecoration(new DividerItemDecoration(this));
        search_recyclerView.setAdapter(simpleSearchResultAdapter);
        simpleSearchResultAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    Object obj = JavaMethod.getValueFromBean(bean, "name");
                    if (obj != null) {
                        search_edit.removeTextChangedListener(SearchShopActivity.this);
                        search_edit.setText(obj.toString());
                        search_edit.setSelection(obj.toString().length());
                        search_edit.addTextChangedListener(SearchShopActivity.this);
                        onClicked(obj.toString());
                    }
                    return true;
                }
                return false;
            }
        });

        //商品显示的view
        swipeToLoadLayout = ViewUtils.findViewById(shop_view, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        goods_recycle = ViewUtils.findViewById(swipeToLoadLayout, R.id.swipe_target);
        loadMoreUtils = new LoadMoreUtil(this, goods_recycle);
        goods_recycle.setLayoutManager(new LinearLayoutManager(this));
        goods_recycle.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(8), R.color.gray_f2));
        shopView_adapter = new SimpleShopListAdapter() {
            @Override
            public List<GoodsInformation> getGoodsList(int position) {
                if (getListBean(position) != null && ((ShopDetail) getListBean(position)).getProductsList() != null)
                    return ((ShopDetail) getListBean(position)).getProductsList().getItems();
                return null;
            }
        };
        goods_recycle.setAdapter(shopView_adapter);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    private void onClicked(String str) {
        if (TextUtils.isEmpty(str)) return;
        shop_view.setVisibility(View.VISIBLE);
        search_recyclerView.setVisibility(View.GONE);
        simpleSearchResultAdapter.clear();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    protected void initData() {
        String ss = getIntent().getStringExtra("data");
        if (ss == null) ss = "";
        search_edit.removeTextChangedListener(this);
        search_edit.setText(ss);
        search_edit.setSelection(ss.length());
        search_edit.addTextChangedListener(this);
        swipeToLoadLayout.setRefreshing(true);
    }


    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(FavoriteRefreshEvent event) {
        if (goods_recycle != null) {
            View view = goods_recycle.getChildAt(0);
            if (view != null) {
                int position = goods_recycle.getChildAdapterPosition(view);
                loadMoreUtils.refreshData(position, 8);
            }
        }

    }

    //获取商铺的数据
    private void getShopViewData(String name, int pn) {
        if (TextUtils.isEmpty(name)) {
            stop();
            return;
        }
        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        getRequestPresenter().getSearchShop(name, userType, pn, 5, new ResultInfoCallback<SearchShop>() {
            @Override
            public void onSuccess(SearchShop body) {
                loadMoreUtils.setData(body.getList(), body.getPn(), body.getPs());
                stop();
            }

            @Override
            public void onFailed(int searchShopResultInfo, String msg) {
                stop();
            }
        });
    }


    @Override
    public void onRefresh() {
        getData(1);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String ss = editable.toString();
        if (ss.length() == 0) {
            shop_view.setVisibility(View.VISIBLE);
            search_recyclerView.setVisibility(View.GONE);
            simpleSearchResultAdapter.clear();
        } else {
            shop_view.setVisibility(View.GONE);
            search_recyclerView.setVisibility(View.VISIBLE);
            getSearchResult(ss,getIntent().getIntExtra("isCanteen",0));
        }
    }

    public void stop() {
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        } else if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    @Override
    public void getData(int pn) {
        String str = search_edit.getText().toString();
        getShopViewData(str, pn);
    }
}
