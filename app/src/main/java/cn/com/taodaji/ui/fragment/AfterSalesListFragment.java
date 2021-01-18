package cn.com.taodaji.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PageByCSIdList;

import com.base.retrofit.RequestCallback;

import tools.fragment.LoadMoreRecyclerViewFragment;


public class AfterSalesListFragment extends LoadMoreRecyclerViewFragment {


    private SimpleAfterSalesAdapter simpleAfterSalesAdapter;




    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        simpleAfterSalesAdapter = new SimpleAfterSalesAdapter();
        recycler_targetView.setAdapter(simpleAfterSalesAdapter);
        swipeToLoadLayout.setRefreshing(true);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        if (PublicCache.loginPurchase != null) {
            loadMoreUtil.setZeroDataView(R.mipmap.no_order, "暂无退换货商品");
        } else if (PublicCache.loginSupplier != null) {
            loadMoreUtil.setZeroDataView(R.mipmap.no_order_supplier, "暂无退换货商品");
        }
    }

    @Override
    public void getData(int pn) {
        int type = -1;
        int id = -1;
        if (PublicCache.loginPurchase != null) {
            type = 0;
            id = PublicCache.loginPurchase.getEntityId();
        } else if (PublicCache.loginSupplier != null) {
            type = 1;
            id = PublicCache.loginSupplier.getStore();
        }
        if (type == -1 || id == -1) return;
        getRequestPresenter().findPageByCSIdList(type, id, pn, 10, new RequestCallback<PageByCSIdList>() {
            @Override
            public void onSuc(PageByCSIdList event) {
                loadMoreUtil.setData(event.getData().getItems(), event.getData().getPn(), event.getData().getPs());
                stop();
            }

            @Override
            public void onFailed(int pageByCSIdList, String msg) {
                stop();
            }
        });
    }

}
