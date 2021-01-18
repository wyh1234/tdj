package cn.com.taodaji.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.retrofit.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PunishmentMessage;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.viewModel.adapter.NoticeListAdapter;
import cn.com.taodaji.viewModel.adapter.PunishmentListAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PunishmentFragment extends LoadMoreRecyclerViewFragment {

    @Override
    public void initView(View mainView) {
        super.initView(mainView);
        if (mainView != null) {
            recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));

            recycler_targetView.setAdapter(new PunishmentListAdapter());
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
        getRequestPresenter().getInstance().getPunishmentList(entityId,1,pn,10, new RequestCallback<PunishmentMessage>() {
            @Override
            protected void onSuc(PunishmentMessage body) {
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

}
