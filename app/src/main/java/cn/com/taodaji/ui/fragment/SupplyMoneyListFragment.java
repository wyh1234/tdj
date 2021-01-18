package cn.com.taodaji.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupSupplyMoneyDetailAdapter;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import cn.com.taodaji.model.entity.LogSupplierCapitalFlow;
import com.base.retrofit.ResultInfoCallback;
import tools.fragment.LoadMoreRecyclerViewFragment;
import com.base.utils.DateUtils;
import com.base.utils.UIUtils;


public class SupplyMoneyListFragment extends LoadMoreRecyclerViewFragment {


    private GroupSupplyMoneyDetailAdapter adapter;


    public List<Object> initData(LogSupplierCapitalFlow event) {
        //上一个的月份
        int last = -1;
        List<Object> list = new ArrayList<>();
        int now = DateUtils.getMonth(0);
        LogSupplierCapitalFlow lsc = null;
        for (LogSupplierCapitalFlow.ItemsBean bean : event.getItems()) {

            int m = DateUtils.getMonth(bean.getCreate_order_time(), "yyyy-MM-dd HH:mm");

//            String mon = m == now ? "本月" : JavaMethod.intParse(m + 1) + "月";

            if (m != last) {
                lsc = new LogSupplierCapitalFlow();
                list.add(lsc);
                last = m;
            } else lsc = (LogSupplierCapitalFlow) list.get(list.size() - 1);

            if (lsc != null) {
                String mon = DateUtils.dateStringToString(bean.getCreate_order_time(), "yyyy-MM-dd HH:mm", "yyyy年MM月");
                lsc.setMonth(mon);
                if (lsc.getItems() == null)
                    lsc.setItems(new ArrayList<>());
                lsc.getItems().add(bean);
            }
        }
        return list;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);

        adapter = new GroupSupplyMoneyDetailAdapter();
        adapter.setTypeFixed(GroupRecyclerAdapter.TYPE_GROUP);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));

        recycler_targetView.setAdapter(adapter);
//        loadMoreUtils = new LoadMoreUtil(this, recycler_targetView);
/*      swipeToLoadLayout.setLoadMoreEnabled(true);
        swipeToLoadLayout.setOnLoadMoreListener(this);*/
        swipeToLoadLayout.setRefreshing(true);
//        mHintMessageTv.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void getData(int pn) {
        getRequestPresenter().logSupplierCapitalFlow(pn, 10, new ResultInfoCallback<LogSupplierCapitalFlow>() {
            @Override
            public void onSuccess(LogSupplierCapitalFlow event) {
                stop();
                List<Object> list = initData(event);
                loadMoreUtil.setData(list, event.getPn(), event.getPs());
            }


            @Override
            public void onFailed(int logSupplierCapitalFlowResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
                stop();
            }
        });
    }
}
