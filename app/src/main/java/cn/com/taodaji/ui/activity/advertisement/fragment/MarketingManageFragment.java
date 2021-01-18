package cn.com.taodaji.ui.activity.advertisement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MarketingManage;
import cn.com.taodaji.ui.activity.advertisement.CreateTfAdvManageActivity;
import cn.com.taodaji.ui.activity.advertisement.adapter.MarketingManageAdapter;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.fragment.DataBaseFragment;

public class MarketingManageFragment extends DataBaseFragment implements MarketingManageAdapter.OnItemClickListener,OnRefreshListener {
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    View mainView;
    private int index;
    private MarketingManageAdapter marketingManageAdapter;
    private List<MarketingManage.DataBean> marketingManageList=new ArrayList<>();


    public static MarketingManageFragment newInstance(int str) {
        Bundle args = new Bundle();
        args.putInt("intent", str);
        MarketingManageFragment f = new MarketingManageFragment();
        f.setArguments(args);
        return f;
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.punish_list_fragment_layout, container, false);

        ButterKnife.bind(this, mainView);


        setView(mainView);
        return mainView;
    }

    private void setView(View mainView) {
        refreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        marketingManageAdapter=new MarketingManageAdapter(getContext(),marketingManageList);
        marketingManageAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(marketingManageAdapter);
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
        Intent intent;
        if (view.getId()==R.id.tv_desc){
            intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("title","广告说明");
            intent.putExtra("url", "http://m.51taodj.com/tdjh5/advertisement/info?entityId="+marketingManageList.get(position).getEntity_id()+"&site="+ PublicCache.site);
        }else {
             intent=new Intent(getContext(), CreateTfAdvManageActivity.class);
        }
        intent.putExtra("DataBean",marketingManageList.get(position));
        intent.putExtra("stage_type",marketingManageList.get(position).getStage_type()+"");
        intent.putExtra("entity_id",marketingManageList.get(position).getEntity_id());
        intent.putExtra("name",marketingManageList.get(position).getName());
        intent.putExtra("advertisementPackageId",marketingManageList.get(position).getPackageList().get(0).getEntity_id());
        intent.putExtra("advPackagePice",marketingManageList.get(position).getPackageList().get(0).getPrice()+"");
        intent.putExtra("limit_days",marketingManageList.get(position).getPackageList().get(0).getLimit_days());
        startActivity(intent);
    }

    public void getData(){
        if (!ListUtils.isEmpty(marketingManageList)){
            marketingManageList.clear();
            marketingManageAdapter.notifyDataSetChanged();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("combination",index);
        getRequestPresenter().advfindList(map, new RequestCallback<MarketingManage>() {
            @Override
            protected void onSuc(MarketingManage body) {
                stop();
                if (!ListUtils.isEmpty(body.getData())){
                    marketingManageList.addAll(body.getData());
                    marketingManageAdapter.notifyDataSetChanged();
                    ll_empty.setVisibility(View.GONE);
                }else {
                    ll_empty.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
                ll_empty.setVisibility(View.VISIBLE);
            }
        });
    }

    public void  stop(){
        if (refreshLayout.isRefreshing()){
            refreshLayout.finishRefresh();
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }


}
