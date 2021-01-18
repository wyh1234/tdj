package cn.com.taodaji.ui.activity.tdjc;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderList;
import cn.com.taodaji.model.entity.PickUpOrder;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.SimpleToolbarActivity;

public class PickUpOrderActivity extends SimpleToolbarActivity   implements OnRefreshListener, OnLoadmoreListener {
    View mainView;
    RecyclerView pick_up_order_list;
    RelativeLayout rl_;
    private List<PickUpOrder.DataObject.ItemsBean> items=new ArrayList<>();
    PickUpOrderAdapter adapter;
    RefreshLayout refreshLayout;
    public int pageNo = 1;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("未提货商品");
    }
    @Override
    public void initData() {
        super.initData();
        refreshLayout.autoRefresh();
    }
    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.pick_up_order_activity);
        body_other.addView(mainView);
        pick_up_order_list=mainView.findViewById(R.id.pick_up_order_list);
        rl_=mainView.findViewById(R.id.rl_);
        refreshLayout=mainView.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);

        pick_up_order_list.setLayoutManager(new LinearLayoutManager(this));
        pick_up_order_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new PickUpOrderAdapter(this, items);
        pick_up_order_list.setAdapter(adapter);

    }


    public void getData(int pn){
        Map<String, Object> params=new HashMap<>();
        params.put("customerId", PublicCache.loginPurchase.getEntityId());
        params.put("userType",0);
        params.put("deliveryStatus",1);
        params.put("pn",pn);
        params.put("ps",10);
        params.put("isC",1);

        getRequestPresenter().pageListByVisitor(params, new RequestCallback<PickUpOrder>() {
            @Override
            protected void onSuc(PickUpOrder body) {
                stop();
                if (ListUtils.isEmpty(items)) {
                    if (ListUtils.isEmpty(body.getData().getItems())) {
                        //获取不到数据,显示空布局
                        rl_.setVisibility(View.VISIBLE);
//                        mStateView.showEmpty();
                        return;
                    }
                    rl_.setVisibility(View.GONE);
//                    mStateView.showContent();//显示内容
                }
                if (pn==1){
                    if (!ListUtils.isEmpty(body.getData().getItems())) {
                        items.clear();
                    }
                }

                if (ListUtils.isEmpty(body.getData().getItems())) {
                    //已经获取数据
                    if (pn!=1){
                        UIUtils.showToastSafe("数据加载完毕");
                        return;
                    }else {
                        UIUtils.showToastSafe("暂无数据");
                    }

                }

                items.addAll(body.getData().getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {

                if (pn==1) {
                    if (!ListUtils.isEmpty(items)) {
                        items.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
                stop();
            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo=1;
        getData(pageNo);
    }

    public void stop() {
        LogUtils.i(refreshLayout.isRefreshing());
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }

}
