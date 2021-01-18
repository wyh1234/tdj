package cn.com.taodaji.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleStoreRecommendAdapter;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import cn.com.taodaji.model.entity.StoreRecommend;
import com.base.retrofit.ResultInfoCallback;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.utils.UIUtils;


public class StoreRecommendListFragment extends LoadMoreRecyclerViewFragment {
    private SimpleStoreRecommendAdapter storeRecommendAdapter;

    private final static int LINEARN = 100;//优秀店铺展示
    private final static int LIN = 102;//实力商家
    private int type;

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(8), R.color.gray_f2));
        storeRecommendAdapter = new SimpleStoreRecommendAdapter();
        if (type == 1) storeRecommendAdapter.setType(LINEARN);
        else storeRecommendAdapter.setType(LIN);
        recycler_targetView.setAdapter(storeRecommendAdapter);
        swipeToLoadLayout.setRefreshing(true);
        return view;
    }


    @Override
    public void getData(final int pn) {
        getRequestPresenter().store_recommend(type, pn, 9, new ResultInfoCallback<StoreRecommend>() {
            @Override
            public void onSuccess(StoreRecommend body) {
                loadMoreUtil.setData(body.getList(), pn, 9);
                stop();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
            }
        });
    }
}
