package cn.com.taodaji.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.model.entity.GetNews;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.EvaluateListSucEvent;
import cn.com.taodaji.model.event.EvaluateReplyEvent;
import cn.com.taodaji.model.event.EvaluateWaitCountEvent;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.NewsAdapter;
import cn.com.taodaji.viewModel.adapter.NoticeListAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleEvaluateManageAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends LoadMoreRecyclerViewFragment {
    @Override
    public void initView(View mainView) {
        super.initView(mainView);
        if (mainView != null) {
            recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));

            recycler_targetView.setAdapter(new NewsAdapter());
        }
    }

    @Override
    public void initData() {
        super.initData();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void getData(int pn) {
        int entityId;
        if (PublicCache.loginPurchase != null) entityId = PublicCache.loginPurchase.getEntityId();
        else if (PublicCache.loginSupplier != null)
            entityId = PublicCache.loginSupplier.getEntityId();
        else return;
        getRequestPresenter().getNews(entityId, pn, 10, new ResultInfoCallback<GetNews>() {
            @Override
            public void onSuccess(GetNews body) {
                EventBus.getDefault().postSticky(new NoticeCountEvent());
                stop();
                loadMoreUtil.setData(body.getItems(), pn, 10);
            }

            @Override
            public void onFailed(int getNewsResultInfo, String msg) {
                super.onFailed(getNewsResultInfo, msg);
                stop();
            }
        });
    }
    @Override
    public void onUserVisible() {
        if (loadMoreUtil.getRealCount() == 0) super.onUserVisible();
    }
}
