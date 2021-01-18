package cn.com.taodaji.ui.activity.penalty.fragment;

import android.content.Context;
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
import com.base.utils.UIUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OffToday;
import cn.com.taodaji.model.entity.PunishData;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.myself.PaymentListActivity;
import cn.com.taodaji.ui.activity.penalty.AppealActivity;
import cn.com.taodaji.ui.activity.penalty.AppealDetailsActivity;
import cn.com.taodaji.ui.activity.penalty.PunishListActivity;
import cn.com.taodaji.ui.activity.penalty.PunishPayActivity;
import cn.com.taodaji.ui.activity.penalty.adapter.PunishListAdapter;
import tools.fragment.DataBaseFragment;

public class PunishListFragment extends DataBaseFragment  implements OnRefreshListener, OnLoadmoreListener, PunishListAdapter.OnItemClickListener {
    private int index;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    private List<PunishData.DataBean.ListBean> data=new ArrayList<>();
    private PunishListAdapter punishListAdapter;
    private PunishListActivity activity;
    private int appealStatus=0;
    private int pn=1;

    public static PunishListFragment newInstance(int str) {
        Bundle args = new Bundle();
        args.putInt("intent", str);
        PunishListFragment f = new PunishListFragment();
        f.setArguments(args);
        return f;
    }
    View mainView;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.punish_list_fragment_layout, container, false);

        ButterKnife.bind(this, mainView);


        setView(mainView);
        return mainView;
    }

    private void setView(View mainView) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        punishListAdapter=new PunishListAdapter(getContext(),data);
        punishListAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(punishListAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(PunishListActivity)context;
//        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

 /*   @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
    //申诉状态刷新；
    @Subscribe
    public void refresh(PunishData punishData) {
      *//*  if (activity.tv_tab.isSelected()){
            appealStatus=3;
        }else if (activity.tv_tab1.isSelected()){
            appealStatus=2;
        }else if (activity.tv_tab2.isSelected()){
            appealStatus=1;
        }*//*
        if (!ListUtils.isEmpty(data)){
            data.clear();
            punishListAdapter.notifyDataSetChanged();

        }
        refreshLayout.autoRefresh();

    }*/

    @Override
    public void initData() {//第一次可见，不会加载onUserVisible()
        super.initData();
        index=getArguments().getInt("intent");
        LogUtils.e(index);

    /*    if (index==1){
            activity.ll_item.setVisibility(View.VISIBLE);
        }else {
            activity.ll_item.setVisibility(View.GONE);
        }*/

        refreshLayout.autoRefresh();

    }

    public void getData(int pn){
        Map<String,Object> map=new HashMap<>();
        map.put("storeId",PublicCache.loginSupplier.getStore());
        if (index==1){
            map.put("status",index+2);
        }else if (index==2){
            map.put("status",index+2);
        }else {
            map.put("status",index+1);
        }

         map.put("appealStatus",appealStatus);
        map.put("ps",10);
        map.put("pn",pn);
        getRequestPresenter().findFineOrderList(map, new RequestCallback<PunishData>() {
            @Override
            public void onSuc(PunishData body) {
                stop();
                if (index==0){
                    activity.tv_total.setText("待处理缴费"+body.getData().getTotal()+"条");

                }
                if (!ListUtils.isEmpty(body.getData().getList())){
                    data.addAll(body.getData().getList());
                    punishListAdapter.notifyDataSetChanged();
                }else {
                    if (refreshLayout.isRefreshing()){
                        ll_empty.setVisibility(View.VISIBLE);
                    }

                }


            }

            @Override
            public void onFailed(int offToday, String msg) {
                UIUtils.showToastSafesShort(msg);
                stop();
                ll_empty.setVisibility(View.VISIBLE);
            }
        });

    }
    public void stop() {
        if (refreshLayout.isRefreshing()) {
            if (!ListUtils.isEmpty(data)){
                data.clear();
                punishListAdapter.notifyDataSetChanged();

            }
            refreshLayout.finishRefresh();
        }
        refreshLayout.finishLoadmore();
    }
    @Override
    public void onUserVisible() {//再次可见，不会加载initData
        super.onUserVisible();
        index=getArguments().getInt("intent");
        LogUtils.e(index);
        refreshLayout.autoRefresh();
  /*      if (index==1){
            activity.ll_item.setVisibility(View.VISIBLE);
        }else {
            activity.ll_item.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (!ListUtils.isEmpty(data)){
            data.clear();
            punishListAdapter.notifyDataSetChanged();

        }
        pn=1;
        getData(pn);

    }

    @Override
    public void onItemClick(View view, int position) {
      if (view.getId()==R.id.tv_go_pay){
            Intent intent=new Intent(getContext(), PunishPayActivity.class);
            intent.putExtra("orderId",data.get(position).getOrder_no());
          intent.putExtra("Id",data.get(position).getId()+"");
           intent.putExtra("payCount",data.get(position).getMoney().toString());
          intent.putExtra("item",data.get(position).getItem());
            startActivity(intent);

        }else {
        }



    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pn);
    }
}
