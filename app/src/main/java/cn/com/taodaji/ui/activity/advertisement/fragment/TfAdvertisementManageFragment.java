package cn.com.taodaji.ui.activity.advertisement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anet.channel.util.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MarketingManage;
import cn.com.taodaji.model.entity.TfAdvertisement;
import cn.com.taodaji.ui.activity.advertisement.TfAdvCheckDetailsActivity;
import cn.com.taodaji.ui.activity.advertisement.adapter.TfAdvertisementManageListAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.fragment.DataBaseFragment;

public class TfAdvertisementManageFragment extends DataBaseFragment implements TfAdvertisementManageListAdapter.OnItemClickListener, OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.ll_empty)
    RelativeLayout ll_empty;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private int index;
    private int page=1;
    View mainView;
    private TfAdvertisementManageListAdapter tfAdvertisementManageListAdapter;
    private List<TfAdvertisement.DataBean.ItemsBean> marketingManageList=new ArrayList<>();
    public static TfAdvertisementManageFragment newInstance(int str) {
        Bundle args = new Bundle();
        args.putInt("intent", str);
        TfAdvertisementManageFragment f = new TfAdvertisementManageFragment();
        f.setArguments(args);
        return f;
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.tf_adv_manage_list_fragment_layout, container, false);

        ButterKnife.bind(this, mainView);


        setView(mainView);
        return mainView;
    }

    private void setView(View mainView) {
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        tfAdvertisementManageListAdapter=new TfAdvertisementManageListAdapter(getContext(),marketingManageList);
        tfAdvertisementManageListAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(tfAdvertisementManageListAdapter);
    }
    @Override
    public void initData() {//第一次可见，不会加载onUserVisible()
        super.initData();
        index=getArguments().getInt("intent");
        LogUtils.e(index);
        refreshLayout.autoRefresh();


    }
    @Override
    public void onUserVisible() {//再次可见，不会加载initData
        super.onUserVisible();
        index=getArguments().getInt("intent");
        LogUtils.e(index);
    }

    @Override
    public void onItemClick(View view, int position) {
        LogUtils.e(position);
        LogUtils.e(marketingManageList.get(position).getEntityId());
        Intent intent=new Intent(getContext(), TfAdvCheckDetailsActivity.class);
        intent.putExtra("entityId",marketingManageList.get(position).getEntityId()+"");
        startActivity(intent);
    }

    public void getData(int pn){
        Map<String,Object> map=new HashMap<>();
        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("status", index+1);
        map.put("page", 1);
        map.put("pn", pn);
        map.put("ps", 10);
        LogUtils.e(map);
        getRequestPresenter().myAdvertisementList(map, new RequestCallback<TfAdvertisement>() {
            @Override
            protected void onSuc(TfAdvertisement body) {
                stop();
                if (ListUtils.isEmpty(marketingManageList)) {
                    if (ListUtils.isEmpty(body.getData().getItems())) {
                        //获取不到数据,显示空布局
                        ll_empty.setVisibility(View.VISIBLE);
                        return;
                    }
                    ll_empty.setVisibility(View.GONE);
                }


                if (ListUtils.isEmpty(body.getData().getItems())) {
                    //已经获取数据
                    if (pn!=1){
                        UIUtils.showToastSafe("数据加载完毕");
                        return;
                    }

                }

                marketingManageList.addAll(body.getData().getItems());
                tfAdvertisementManageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                if (refreshLayout.isRefreshing()) {
                    ll_empty.setVisibility(View.VISIBLE);
                }
                stop();


            }
        });
    }
    public void stop(){
        LogUtils.i(refreshLayout.isRefreshing());
        if (refreshLayout.isRefreshing()) {
            if (!ListUtils.isEmpty(marketingManageList)){
                marketingManageList.clear();
                tfAdvertisementManageListAdapter.notifyDataSetChanged();

            }
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }
    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++page);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        getData(page);
    }
}
