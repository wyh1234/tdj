package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.viewModel.adapter.SimpleEvaluateManageAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.model.event.EvaluateListSucEvent;
import cn.com.taodaji.model.event.EvaluateReplyEvent;
import cn.com.taodaji.model.event.EvaluateWaitCountEvent;

import com.base.retrofit.RequestCallback;
import com.base.utils.JavaMethod;

import tools.fragment.LoadMoreRecyclerViewFragment;


public class EvaluateManageFragment extends LoadMoreRecyclerViewFragment {

    private SimpleEvaluateManageAdapter evaluateManageAdapter;
    private int status_code;

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        evaluateManageAdapter = new SimpleEvaluateManageAdapter();
        recycler_targetView.setAdapter(evaluateManageAdapter);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }


    //接收评价回复成功事件
    @Subscribe
    public void onEvent(EvaluateReplyEvent event) {
        int evaluateId = event.getEvaluateId();
        List<EvaluationList.DataBean.ItemsBean> lit = evaluateManageAdapter.getList();
        int position = -1;
        for (int i = 0; i < lit.size(); i++) {
            if (lit.get(i) == null) continue;
            if (lit.get(i).getEntityId() == evaluateId) {
                position = i;
                break;
            }
        }
        if (position > -1) {
            loadMoreUtil.refreshData(position, 5);
        }
    }

    //接收评价发布更改成功事件
    @Subscribe
    public void onEvent(EvaluateListSucEvent event) {


        if (event.getEvaluationBean() != null) {
            List<EvaluationList.DataBean.ItemsBean> lit = evaluateManageAdapter.getList();
            int position = -1;
            for (int i = 0; i < lit.size(); i++) {
                if (lit.get(i) == null) continue;
                if (lit.get(i).getEntityId() == event.getEvaluationBean().getEntityId()) {
                    position = i;
                    JavaMethod.copyValue(lit.get(i), event.getEvaluationBean());
                    break;
                }
            }
            evaluateManageAdapter.notifyItemChanged(position);

        } else if (event.isNew()) {
            getData(1);
        }

    }

    @Override
    public void onUserVisible() {
        if (evaluateManageAdapter != null && evaluateManageAdapter.getRealCount() == 0)
            super.onUserVisible();
    }

    @Override
    public void getData(int pn) {
        int useType;
        if (PublicCache.loginPurchase != null) useType = 0;
        else if (PublicCache.loginSupplier != null) useType = 1;
        else return;

        Map<String, Object> map = new HashMap<>();
        map.put("userType", useType);
        map.put("pn", pn);
        map.put("ps", 5);

        if (status_code == 0) {
            map.put("creditLevel", -1);
        } else if (status_code == 1) {
            map.put("hasImg", 1);
        } else if (status_code == 2) {
            map.put("creditLevel", 3);
        } else if (status_code == 3) {
            map.put("creditLevel", 3);
        } else if (status_code == 4) {
            map.put("isReply", 0);
        }
        //useType, pn, 5/ps
        getRequestPresenter().evaluation_pageList(map, new RequestCallback<EvaluationList>() {
            @Override
            public void onSuc(EvaluationList body) {
                if (status_code == 4 && body.getData() != null) {
                    EventBus.getDefault().post(new EvaluateWaitCountEvent(body.getData().getTotal()));
                }
                loadMoreUtil.setData(body.getData().getItems(), body.getData().getPn(), body.getData().getPs());
                stop();
            }

            @Override
            public void onFailed(int evaluationList, String msg) {
                stop();
            }
        });
    }
}
