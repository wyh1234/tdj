package cn.com.taodaji.ui.activity.market;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SimpleShopListAdapter;

import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.MarketShopList;
import cn.com.taodaji.model.entity.ShopInformation;

import java.util.List;

import com.base.retrofit.ResultInfoCallback;
import cn.com.taodaji.ui.activity.homepage.SearchNewActivity;
import retrofit2.Call;
import tools.activity.SimpleToolbarActivity;

import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ViewUtils;

public class MarketStrollShopListActivity extends SimpleToolbarActivity implements OnGetDataListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        search_text.setVisibility(View.VISIBLE);
        search_text.setOnClickListener(this);
    }

    private SimpleShopListAdapter simpleShopListAdapter;
    private int marketId;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private LoadMoreUtil loadMoreUtils;

    @Override
    protected void initMainView() {
        swipeToLoadLayout = getLayoutView(R.layout.activity_market_shop_list);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        body_other.addView(swipeToLoadLayout);
        recyclerView = ViewUtils.findViewById(swipeToLoadLayout, R.id.swipe_target);
        loadMoreUtils = new LoadMoreUtil(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), R.color.gray_f2));
        simpleShopListAdapter = new SimpleShopListAdapter() {
            @Override
            public List<GoodsInformation> getGoodsList(int position) {
                return ((ShopInformation) getListBean(position)).getItems();
            }
        };
        recyclerView.setAdapter(simpleShopListAdapter);



    }

    @Override
    protected void initData() {
        marketId = getIntent().getIntExtra("marketId", 0);
        swipeToLoadLayout.setRefreshing(true);
    }


    public void getData(final int pn) {
        getRequestPresenter().getMarket_shopInformation(marketId, pn, 5, new ResultInfoCallback<MarketShopList>() {
            @Override
            public void onSuccess(MarketShopList body) {
                if (body != null) {
                    loadMoreUtils.setData(body.getList(), body.getPn(), body.getPs());
                }
                swipeOnClost();
            }

            @Override
            public void onFailed(int marketShopListResultInfo, String msg) {
                swipeOnClost();
            }
        });
    }

    private void swipeOnClost() {
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }


    @Override
    public void onRefresh() {
        getData(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_text:
                Intent intent = new Intent(this, SearchNewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
