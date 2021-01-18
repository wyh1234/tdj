package com.base.viewModel.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.R;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreUtil extends RecyclerView.OnScrollListener {
    private OnGetDataListener onGetDataListener;

    private BaseRecyclerViewAdapter adapter;

    private int pageNo = 1;//翻页计数器
    private int ps = 1;

    public boolean isUpdate = false;//是否中间局部更新
    private boolean hasLoadMore = true;//是否启用加载更多
    private boolean loadingState = true;//是否正处在加载状态

    private Context mContext;
    private View footerView;//加载更多
    private View zeroDataView;//零数据时显示的
    private View progressbar, ivSuccess;
    private TextView tvLoadMore;
    private RecyclerView recyclerView;

    private boolean isPreload = false;//返回的数据是否是预加载数据

    private List list_preload = new ArrayList<>();//缓存预加载的数据

    public LoadMoreUtil(@NonNull OnGetDataListener onGetDataListener, @NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mContext = recyclerView.getContext();
        this.onGetDataListener = onGetDataListener;
    }

    //加载方式,触底加载,预加载
    private void loadMoreType() {
        this.recyclerView.addOnScrollListener(this);
        getLoadMoreView();
        loadMoreView();
        adapter.addFooterView(footerView);
    }

    private void removeLoadMore() {
        this.recyclerView.removeOnScrollListener(this);
        adapter.removeFooter(footerView);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //如果不能滑动时
            if (!recyclerView.canScrollVertically(1)) {
                //如果加载完成，则显示
                if (!loadingState) {
                    showData(list_preload, pageNo + 1, ps);
                }
                //没有缓存数据，为预加载数据还没有返回，故返回的数据不是预加载的数据，需要直接显示
                else isPreload = false;
            }
        }
    }

    //预加载
    private void preload(int pn) {
        if (onGetDataListener != null && hasLoadMore) {

            isPreload = true;//返回的数据是预加载的数据
            loadingState = true;//正在加载数据

            //延迟1秒，再加载，防止加载数据时，开子线程网络请求，大量消耗内存，造成显示的卡顿
            if (recyclerView != null) {
                if (pn == 1) {
                    recyclerView.postDelayed(() -> onGetDataListener.getData(pn + 1), 1200);
                } else onGetDataListener.getData(pn + 1);

            }
        }
    }

    //显示数据
    private void showData(List list, int ppn, int ps) {
        if (list.isEmpty()) {

            //无任何数据
            if (ppn == 1) {
                if (footerView != null) adapter.removeFooter(footerView);
                adapter.clear();
                addZeroDataView();
            }
            //加载完毕
            else {
                //如果是局部更新
                if (isUpdate) {
                    isUpdate = false;
                    int startPosition = adapter.getPageStartPos(ppn, ps);
                    adapter.rangeUpdate(list, false, startPosition);
                }
                loadDoneView();
            }

        } else {


            //如果是第一页，且不为空 则移除
            if (ppn == 1) removeZeroDataView();


            //如果需要加载更多，且没有添加，则添加
            if (hasLoadMore && !adapter.isHasFooter(footerView)) {
                loadMoreType();
            }
            //如果不需要加载更多，且已拥有加载更多，则移除
            else if (!hasLoadMore && adapter.isHasFooter(footerView)) {
                removeLoadMore();
            }

            if (ppn == 1 && list.size() < ps) {
                loadDoneView();
            } else loadMoreView();

            //如果是局部更新
            if (isUpdate) {
                isUpdate = false;
                if (ppn == 1) {
                    adapter.rangeUpdate(list, false);
                } else {
                    //对比找到更新位置
                    Object one = list.get(0);
                    int start = -1;
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        if (JavaMethod.beanEquals_OnlyField(adapter.getListBean(i), one)) {
                            start = i;
                            break;
                        }
                    }
                    if (start == -1) {
                        adapter.rangeUpdate(list, false, adapter.getFirstPosition());
                    } else {
                        adapter.rangeUpdate(list, false, start);
                    }
                }

            } else if (ppn == 1) {
                pageNo = ppn;
                adapter.setList(list, true);
            } else {
                pageNo = ppn;
                adapter.loadMore(list);
            }


            //如果显示的是预加载数据，则清空
            if (list_preload == list) {
                list_preload.clear();
            }
            //显示完成后，自动预加载
            preload(pageNo);
        }
    }

    public void setData(List list, int ppn, int ps) {
        if (adapter == null) {
            this.ps = ps;
            adapter = (BaseRecyclerViewAdapter) this.recyclerView.getAdapter();
            if (adapter == null) return;
        }
        if (list == null) return;

        //如果ppn=1则可能是刷新的数据，不为预加载数据
        if (ppn == 1) {
            list_preload.clear();
            isPreload = false;
        }
        loadingState = false;//加载完成

        //是预加载的数据，缓存不更新UI
        if (isPreload) {
            isPreload = false;
            list_preload = list;
        } else showData(list, ppn, ps);

    }

    private void loadDoneView() {
        if (progressbar != null) progressbar.setVisibility(View.GONE);
        if (ivSuccess != null) ivSuccess.setVisibility(View.VISIBLE);
        if (tvLoadMore != null) tvLoadMore.setText("已加载完毕");
    }

    private void loadMoreView() {
        if (progressbar != null) progressbar.setVisibility(View.VISIBLE);
        if (ivSuccess != null) ivSuccess.setVisibility(View.GONE);
        if (tvLoadMore != null) tvLoadMore.setText("正在加载数据");
    }

    //中间刷新
    public void refreshData(int position, int ps) {
        if (onGetDataListener != null) {
            int pagNo = adapter.getPageNO(position, ps);
            isUpdate = true;
            isPreload = false;
            onGetDataListener.getData(pagNo);
        }
    }

    private void getLoadMoreView() {
        if (footerView != null) return;
        footerView = ViewUtils.getFragmentView(recyclerView, R.layout.swipe_twitter_footer);
        progressbar = ViewUtils.findViewById(footerView, R.id.progressbar);
        ivSuccess = ViewUtils.findViewById(footerView, R.id.ivSuccess);
        tvLoadMore = ViewUtils.findViewById(footerView, R.id.tvLoadMore);
    }

    private void addZeroDataView() {
        if (zeroDataView == null) getZeroDataView();
        if (adapter != null && !adapter.isHasFooter(zeroDataView))
            adapter.addFooterView(zeroDataView);
    }

    private void removeZeroDataView() {
        if (adapter != null && zeroDataView != null) {
            if (adapter.isHasFooter(zeroDataView)) adapter.removeFooter(zeroDataView);
        }
    }

    private View getZeroDataView() {
        if (zeroDataView == null)
            zeroDataView = ViewUtils.getFragmentView(recyclerView, R.layout.zero_data_view);

        return zeroDataView;
    }

    public void setZeroDataView(@DrawableRes int resId, String text) {
        if (zeroDataView != null) return;
        zeroDataView = ViewUtils.getFragmentView(recyclerView, R.layout.zero_data_view);
        ImageView image = ViewUtils.findViewById(zeroDataView, R.id.iv_imageView);
        image.setImageResource(resId);
        TextView tv_text = ViewUtils.findViewById(zeroDataView, R.id.tv_textView);
        tv_text.setText(text);
    }


    public boolean getHasLoadMore() {
        return hasLoadMore;
    }

    public void setHasLoadMore(boolean hasLoadMore) {
        this.hasLoadMore = hasLoadMore;
    }

    public void clear() {
        list_preload.clear();
        if (adapter != null) adapter.clear();
    }

    public void clearAll() {
        list_preload.clear();
        if (adapter != null) adapter.clearAll();
    }

    public int getRealCount() {
        if (adapter != null) {
            return adapter.getRealCount();
        }
        return 0;
    }
}
