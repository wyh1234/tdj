package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.viewModel.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.FavoriteRefreshEvent;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.myself.NoticePayDetailActivity;
import cn.com.taodaji.viewModel.adapter.NoticeListAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

public class NoticeFragment extends LoadMoreRecyclerViewFragment {
    @Override
    public void initView(View mainView) {
        super.initView(mainView);
        if (mainView != null) {
            recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));

            recycler_targetView.setAdapter(new NoticeListAdapter());
        }
    }

    @Override
    public void initData() {
        super.initData();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void getData(int pn) {
        addRequest(ModelRequest.getInstance(1).getNoticeList(PublicCache.loginPurchase.getEmpRole(),PublicCache.loginPurchase.getLoginUserId(), PublicCache.site_login,  pn, 10), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                EventBus.getDefault().postSticky(new NoticeCountEvent());
                stop();
                loadMoreUtil.setData(body.getData().getItems(), pn, 10);
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

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }
    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }
*/



}
