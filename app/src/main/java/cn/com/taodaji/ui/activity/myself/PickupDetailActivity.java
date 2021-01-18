package cn.com.taodaji.ui.activity.myself;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.LoadMoreUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PickupFeeList;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.viewModel.adapter.PickupDetailAdapter;
import tools.activity.SimpleToolbarActivity;

public class PickupDetailActivity extends SimpleToolbarActivity implements OnGetDataListener, SwipeRefreshLayout.OnRefreshListener {

    private List<PickupFeeList.DataBean.PageBeanBean.RecordListBean> list = new ArrayList<>();
    private SwipeToLoadLayout swipeToLoadLayout;
    private LoadMoreUtil loadMoreUt;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("淘钱包费用明细");
    }

    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_pickup_detail);
        body_other.addView(view);
        swipeToLoadLayout = ViewUtils.findViewById(view, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshEnabled(false);
        RecyclerView recyclerView = ViewUtils.findViewById(swipeToLoadLayout, R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        PickupDetailAdapter adapter=new PickupDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        loadMoreUt = new LoadMoreUtil(this, recyclerView);
        swipeToLoadLayout.setRefreshing(true);

        onRefresh();
    }

    @Override
    public void getData(int pn) {
        if (PublicCache.loginSupplier == null) return;
        RequestPresenter2.getInstance().getPickupFeeList(PublicCache.loginSupplier.getStore(), pn, 10, new RequestCallback<PickupFeeList>() {
            @Override
            protected void onSuc(PickupFeeList body) {
                if (body.getData()!=null) {
                    list=body.getData().getPageBean().getRecordList();
                    if (list != null) {
                        if (swipeToLoadLayout.isLoadingMore())swipeToLoadLayout.setLoadingMore(false);
                            loadMoreUt.setData(body.getData().getPageBean().getRecordList(), body.getData().getPageBean().getPageNo(), body.getData().getPageBean().getLength());
                    }
                }
            }

            @Override
            public void onFailed(int couponsFindreciveList, String msg) {
                if (swipeToLoadLayout.isLoadingMore()) swipeToLoadLayout.setLoadingMore(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        getData(1);
    }
}
