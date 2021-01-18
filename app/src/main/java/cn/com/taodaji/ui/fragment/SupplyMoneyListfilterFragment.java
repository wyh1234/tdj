package cn.com.taodaji.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.retrofit.RequestCallback;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupSupplyMoneyDetailNewAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplyMoneyListBean;
import tools.fragment.LoadMoreRecyclerViewFragment;


public class SupplyMoneyListfilterFragment extends LoadMoreRecyclerViewFragment {


    private GroupSupplyMoneyDetailNewAdapter adapter;
    private View headerView;//筛选明细的头部
    private TextView tv_title_1, tv_title_2;//头部的文字


    public List<Object> initData(SupplyMoneyListBean.DataBean.PageBeanBean event) {
        //上一个的月份
        int last = -1;
        List<Object> list = new ArrayList<>();
        int now = DateUtils.getMonth(0);
        SupplyMoneyListBean.DataBean.PageBeanBean lsc = null;
        for (SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean bean : event.getRecordList()) {

            int m = DateUtils.getMonth(bean.getCreateTime(), "yyyy-MM-dd HH:mm");

            String mon = m == now ? "本月" : JavaMethod.intParse(m + 1) + "月";

            if (m != last) {
                lsc = new SupplyMoneyListBean.DataBean.PageBeanBean();
                list.add(lsc);
                last = m;
            } else lsc = (SupplyMoneyListBean.DataBean.PageBeanBean) list.get(list.size() - 1);

            if (lsc != null) {
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
/*      swipeToLoadLayout.setLoadMoreEnabled(true);
        swipeToLoadLayout.setOnLoadMoreListener(this);*/
        swipeToLoadLayout.setRefreshing(true);
//        mHintMessageTv.setVisibility(View.VISIBLE);

        headerView = ViewUtils.getFragmentView(recycler_targetView, R.layout.item_purchase_filtrate_header);
        tv_title_1 = ViewUtils.findViewById(headerView, R.id.tv_title_1);
        tv_title_2 = ViewUtils.findViewById(headerView, R.id.tv_title_2);

        if (!adapter.isHasHeader(headerView)) {

            tv_title_1.setText("已收款：+" + 0 + "        售后退款：-" + 0 + "        提现：-" + 0);
            tv_title_2.setText("冻结款：+" + 0 + "");
            adapter.addHeaderView(headerView);
        }
        return view;
    }

    @Override
    public void getData(int pn) {

        Bundle bundle = getArguments();
        String startTime = bundle.getString("startTime", "");
        String endTime = bundle.getString("endTime", "");
        String orderFreezeStatus = bundle.getString("orderFreezeStatus", "");
        int type = bundle.getInt("type", 0);

        getSupplyMoneyListFilterDatas(pn, type, startTime, endTime, orderFreezeStatus);

    }

    private void getSupplyMoneyListFilterDatas(int pn, int type, String startTime, String endTime, String orderFreezeStatus) {
        //PublicCache.loginSupplier.getStore(),pn, 10
        Map<String, Object> map = new HashMap<>();
        //int storeId,int pn, int ps
        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("pn", pn);
        map.put("ps", 10);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        if (type > 0) {
            map.put("type", type);//1、订单 2、提现 3、售后
        }
        if (!TextUtils.isEmpty(orderFreezeStatus)) {
            map.put("orderFreezeStatus", orderFreezeStatus);//可选项 FREEZE:结状态 UNFREEZE:解冻状态
        }

        getRequestPresenter().getSupplyMoneyListFilter(map, new RequestCallback<SupplyMoneyListBean>() {

            @Override
            protected void onSuc(SupplyMoneyListBean event) {
                stop();
                if (event.getErr() == 0 && event.getData() != null) {
                    if (event.getData().getPageBean() != null) {
                        initHeader(event);
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

    private void initHeader(SupplyMoneyListBean bean) {
        if (bean.getData() == null || headerView == null) return;
        BigDecimal cash = bean.getData().getOrderFreezeMoney().add(bean.getData().getOrderUNFreezeMoney());
        BigDecimal result = cash.subtract(bean.getData().getOrderCanceledMoney());
        String tee = "";
        if (result.compareTo(BigDecimal.ZERO) > 0) {
            tee = "+";
        }
        tv_title_1.setText("已收款：" + tee + result.toString() + "        售后退款：-" + bean.getData().getAfterSaleMoney().toString() + "        提现：-" + bean.getData().getWithdrawalMoney().toString());
        tv_title_2.setText("冻结款：+" + bean.getData().getOrderFreezeMoney().toString() + "");
        if (!adapter.isHasHeader(headerView)) {
            adapter.addHeaderView(headerView);
        }
    }
}
