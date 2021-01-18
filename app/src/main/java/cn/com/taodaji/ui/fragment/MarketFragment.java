package cn.com.taodaji.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.activity.market.MarketShopMapActivity;
import cn.com.taodaji.ui.activity.market.MarketStrollShopListActivity;
import cn.com.taodaji.viewModel.vm.MarketListViewModel;
import cn.com.taodaji.model.entity.MarketLocal;
import tools.fragment.DataBaseFragment;


import com.base.activity.BaseActivity;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;
import com.base.listener.OnPermissionListener;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MarketFragment extends DataBaseFragment implements View.OnClickListener, OnPermissionListener {
    private SingleRecyclerViewAdapter customerSimpleRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private boolean isNeedUpdate = false;

    @Override
    protected View initToolbar() {
        View title = ViewUtils.getLayoutView(getContext(), R.layout.toolbar_customer_simple_title);
        Toolbar toolbar = ViewUtils.findViewById(title, R.id.toolbar);
        toolbar.setContentInsetsRelative(UIUtils.dip2px(50), UIUtils.dip2px(5));
        toolbar.setBackgroundColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
        TextView simple_title = ViewUtils.findViewById(title, R.id.simple_title);
        simple_title.setText("逛市场");
        TextView right_text = ViewUtils.findViewById(title, R.id.right_text);
        right_text.setText("地图");
        right_text.setTextColor(UIUtils.getColor(R.color.white));
        right_text.setOnClickListener(this);
        return title;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        recyclerView = ViewUtils.findViewById(view, R.id.market_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        customerSimpleRecyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new MarketListViewModel());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                View view = ViewUtils.getFragmentView(parent, R.layout.item_market_list);
                float x = 280f / 200f;
                int w = (UIUtils.getScreenWidthPixels() - 80) / 2;
                int h = (int) (w / x);
                UIUtils.setViewSize(ViewUtils.findViewById(view, R.id.market_image), w, h);
                return view;
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    Intent intent = new Intent(getActivity(), MarketStrollShopListActivity.class);
                    intent.putExtra("marketId", JavaMethod.getFieldValue(bean, "entityId", int.class));
                    startActivity(intent);
                    return true;
                } else return super.onItemClick(view, onclickType, position, bean);
            }
        };
        recyclerView.setAdapter(customerSimpleRecyclerViewAdapter);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        getRequestPresenter().getMarket_ListAll(new RequestCallback<MarketLocal>() {
            @Override
            public void onSuc(MarketLocal body) {
                customerSimpleRecyclerViewAdapter.setList(body.getData());
            }

            @Override
            public void onFailed(int marketLocal, String msg) {

            }
        });
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (isNeedUpdate || (customerSimpleRecyclerViewAdapter != null && customerSimpleRecyclerViewAdapter.getRealCount() == 0))
            initData();
        isNeedUpdate = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_text:
                addPermissionListen(10015, this);
                permissionRequest(10015, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                break;
        }
    }

    //定位权限请求成功
    @Override
    public void permissionSuccess(int requestCode) {
        if (requestCode == 10015) {
            Intent intent = new Intent(getContext(), MarketShopMapActivity.class);
            startActivity(intent);
        }

    }

    //定位权限请求失败
    @Override
    public void permissionFail(int requestCode) {

    }

    //登录后刷新
    @Subscribe
    public void update(Login_in login_in) {
        isNeedUpdate = true;
        if (customerSimpleRecyclerViewAdapter != null) customerSimpleRecyclerViewAdapter.clear();
    }

/*    //登录后刷新
    @Subscribe
    public void update(Login_out login_out) {
        isNeedUpdate = true;
        if (customerSimpleRecyclerViewAdapter != null) customerSimpleRecyclerViewAdapter.clear();
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }


}
