package tools.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.MyRecyclerView;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.swipetoloadlayout.listener.OnLoadMoreListener;

import cn.com.taodaji.R;

import com.base.utils.ViewUtils;

/**
 * 带加载更多的fragment
 **/
public abstract class LoadMoreRecyclerViewFragment extends DataBaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, OnGetDataListener {

    public SwipeToLoadLayout swipeToLoadLayout;
    public int pageNo = 1;//翻页计数器
    public MyRecyclerView recycler_targetView;
    public LinearLayout bottom_view,down_view;
    public LinearLayout top_view;
    public LinearLayout mLinearLayoutSearch;
    public EditText mEditTextSearch;
    public Button mButtonSearch;
    public LoadMoreUtil loadMoreUtil;
    public ImageView mImageClear;
    public ViewGroup mainView;
    public LinearLayout mLinearLayout;
    // public TextView mHintMessageTv;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = ViewUtils.getFragmentView(container, R.layout.swipe_twitter_swipe_toload_recyclerview);
//        mLinearLayout =ViewUtils.findViewById(mainView,R.id.swipe_twitter_swipe_toload_recyclerview_bg_linearlayout);
        mLinearLayoutSearch = ViewUtils.findViewById(mainView, R.id.swipe_twitter_swipe_toload_recyclerview_search_linearlayout);//搜索框
        //  mHintMessageTv = ViewUtils.findViewById(mainView, R.id.hint_message);
        mEditTextSearch = ViewUtils.findViewById(mainView, R.id.swipe_twitter_swipe_toload_recyclerview_edit);
        mButtonSearch = ViewUtils.findViewById(mainView, R.id.swipe_twitter_swipe_toload_recyclerview_search_btn);
        mImageClear = ViewUtils.findViewById(mainView, R.id.swipe_twitter_swipe_toload_recyclerview_clear_img);
        swipeToLoadLayout = ViewUtils.findViewById(mainView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        recycler_targetView = ViewUtils.findViewById(mainView, R.id.swipe_target);
        loadMoreUtil = new LoadMoreUtil(this, recycler_targetView);
        bottom_view = ViewUtils.findViewById(mainView, R.id.bottom_view);
        down_view = ViewUtils.findViewById(mainView, R.id.down_view);
        top_view = ViewUtils.findViewById(mainView, R.id.top_view);
        initView(mainView);
        return mainView;
    }

    public void initView(View mainView) {

    }


    public void update() {
        swipeToLoadLayout.setRefreshing(true);
    }


    @Override
    public void onLoadMore() {
        getData(pageNo + 1);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        getData(1);
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        stop();
    }


    public void stop() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }
}
