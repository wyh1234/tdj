package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.retrofit.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.WaitCountEvent;
import cn.com.taodaji.model.event.WaitEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.WaitListAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

public class WaitFragment extends LoadMoreRecyclerViewFragment {
    private  WaitListAdapter adapter;
    private int total;

    @Override
    public void initView(View mainView) {
        super.initView(mainView);

        if (mainView != null) {
            adapter=new WaitListAdapter();
            recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));

            recycler_targetView.setAdapter(adapter);
        }
    }

    @Override
    public void initData() {
        super.initData();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void getData(int pn) {
        addRequest(ModelRequest.getInstance(1).getWaitList(PublicCache.loginPurchase.getEmpRole(),PublicCache.loginPurchase.getLoginUserId(), PublicCache.site_login,  pn, 10), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                stop();
                loadMoreUtil.setData(body.getData().getItems(), pn, 10);
                if (body.getData() != null) {
                 total=body.getData().getTotal();
                }

                EventBus.getDefault().postSticky(new WaitCountEvent(total));
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
                stop();
            }
        });
    }
    @Override
    public void onUserVisible() {
        if (loadMoreUtil.getRealCount() == 0) super.onUserVisible();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }
    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Subscribe
    public void onEvent(WaitEvent event) {
        if (event.getPosition()>=0) {
            adapter.remove(event.getPosition());
            total=total-1;
            EventBus.getDefault().postSticky(new WaitCountEvent(total));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cancelAllTimers();
        }
    }
}
