package com.base.swipetoloadlayout;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.R;
import com.base.activity.BaseActivity;
import com.base.activity.CustomerFragment;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.swipetoloadlayout.listener.OnGetDataListener;

import java.util.List;

/**
 * Created by yangkuo on 2018-08-29.
 */
public class SwipeToLoadViewUtils implements SwipeRefreshLayout.OnRefreshListener {


    public SwipeToLoadLayout swipeToLoadLayout;
    public RecyclerView recycler_targetView;
    private OnGetDataListener onGetDataListener;
    private LoadMoreUtil loadMoreUtil;

    public SwipeToLoadViewUtils(BaseActivity activity) {
        checked(activity);
        init(activity.getRootView());
    }

    public SwipeToLoadViewUtils(CustomerFragment fragment) {
        checked(fragment);
        init(fragment.getRootView());
    }

    /**
     * 检查是否实现了 OnGetDataListener 接口
     *
     * @param obj
     */
    private <T> void checked(T obj) {
        if (obj instanceof OnGetDataListener) onGetDataListener = (OnGetDataListener) obj;
        else throw new IllegalStateException(obj.getClass() + " 类，必须实现 OnGetDataListener 接口");
    }

    /**
     * 控件的初始化
     *
     * @param mainView
     */
    private void init(View mainView) {
        swipeToLoadLayout = mainView.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);

        recycler_targetView = mainView.findViewById(R.id.swipe_target);
        loadMoreUtil = new LoadMoreUtil(onGetDataListener, recycler_targetView);
    }

    public RecyclerView getRecyclerView() {
        return recycler_targetView;
    }

    public void setData(List list, int ppn, int ps) {
        loadMoreUtil.setData(list, ppn, ps);
    }

    @Override
    public void onRefresh() {
        onGetDataListener.getData(1);
    }
}
