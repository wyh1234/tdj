package cn.com.taodaji.ui.activity.scanner;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ScannerFeeList;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class ScannerFeeListActivity extends DataBaseActivity implements OnRefreshListener, OnLoadmoreListener, ScannerFeeListAdapter.OnItemClickListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    public int pageNo = 1;//翻页计数器
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    private ScannerFeeListAdapter scannerFeeListAdapter;
    private List<ScannerFeeList.DataBean.ItemsBean> listBeans=new ArrayList<>();
    @OnClick({R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.scannaer_fee_list_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("扫码明细");

        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        scannerFeeListAdapter=new ScannerFeeListAdapter(this,listBeans);
        scannerFeeListAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(scannerFeeListAdapter);
        refreshLayout.autoRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }

    private void getData(int pn) {
        Map<String,Object> map=new HashMap<>();
        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("scannerType", getIntent().getStringExtra("scannerType"));
        map.put("startTime", getIntent().getStringExtra("star_date"));
        map.put("endTime",  getIntent().getStringExtra("end_date"));
        map.put("ps", 20);
        map.put("pn", pn);
        getRequestPresenter().storeDiyFeeList(map, new RequestCallback<ScannerFeeList>() {
            @Override
            protected void onSuc(ScannerFeeList body) {
                stop();
                if (ListUtils.isEmpty(listBeans)) {
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

                listBeans.addAll(body.getData().getItems());
                scannerFeeListAdapter.notifyDataSetChanged();


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
            if (!ListUtils.isEmpty(listBeans)){
                listBeans.clear();
                scannerFeeListAdapter.notifyDataSetChanged();

            }
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo=1;
        getData(pageNo);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(this,ScannerFeeListDetailsActivity.class);
        intent.putExtra("entityId",listBeans.get(position).getEntityId());
        startActivity(intent);

    }
}
