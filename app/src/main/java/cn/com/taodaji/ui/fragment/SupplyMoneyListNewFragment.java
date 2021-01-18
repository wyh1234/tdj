package cn.com.taodaji.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.retrofit.RequestCallback;
import com.base.utils.DateUtils;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupSupplyMoneyDetailNewAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplyMoneyListBean;
import tools.fragment.LoadMoreRecyclerViewFragment;

public class SupplyMoneyListNewFragment extends LoadMoreRecyclerViewFragment {


    private GroupSupplyMoneyDetailNewAdapter adapter;


    public List<Object> initData(SupplyMoneyListBean.DataBean.PageBeanBean event) {
        //上一个的月份
        int last = -1;
        List<Object> list = new ArrayList<>();
        int now = DateUtils.getMonth(0);
        SupplyMoneyListBean.DataBean.PageBeanBean lsc = null;
        for (SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean bean : event.getRecordList()) {

            int m = DateUtils.getMonth(bean.getCreateTime(), "yyyy-MM-dd HH:mm");

//            String mon = m == now ? "本月" : JavaMethod.intParse(m + 1) + "月";

            if (m != last) {
                lsc = new SupplyMoneyListBean.DataBean.PageBeanBean();
                list.add(lsc);
                last = m;
            } else lsc = (SupplyMoneyListBean.DataBean.PageBeanBean) list.get(list.size() - 1);

            if (lsc != null) {
                String mon = DateUtils.dateStringToString(bean.getCreateTime(), "yyyy-MM-dd HH:mm", "yyyy年MM月");
                lsc.setMonth(mon);
                if (lsc.getRecordList() == null) lsc.setRecordList(new ArrayList<>());
                lsc.getRecordList().add(bean);
            }
        }
        return list;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);

        adapter = new GroupSupplyMoneyDetailNewAdapter();
        adapter.setTypeFixed(GroupRecyclerAdapter.TYPE_GROUP);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));

        recycler_targetView.setAdapter(adapter);
//        loadMoreUtils = new LoadMoreUtil(this, recycler_targetView);
        swipeToLoadLayout.setLoadMoreEnabled(false);
/*      swipeToLoadLayout.setLoadMoreEnabled(true);
        swipeToLoadLayout.setOnLoadMoreListener(this);*/
        swipeToLoadLayout.setRefreshing(true);
//        mHintMessageTv.setVisibility(View.VISIBLE);


        return view;
    }

    @Override
    public void getData(int pn) {
//
//        Bundle bundle=getArguments();
//        String title=bundle.getString("title","");

        getSupplyMoneyListNewDatas(pn);

    }

    private void getSupplyMoneyListNewDatas(int pn) {
        if (PublicCache.loginSupplier == null) return;
        getRequestPresenter().getSupplyMoneyListNew(PublicCache.loginSupplier.getStore(), pn, 10, new RequestCallback<SupplyMoneyListBean>() {
            @Override
            protected void onSuc(SupplyMoneyListBean event) {
                stop();
                if (event.getData() != null) {
                    if (event.getData().getPageBean() != null) {
                        List<Object> list = initData(event.getData().getPageBean());
                        loadMoreUtil.setData(list, event.getData().getPageBean().getPageNo(), event.getData().getPageBean().getLength());
                    }
                }
            }

            @Override
            public void onFailed(int logSupplierCapitalFlowResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
                stop();
            }
        });


    }


}
