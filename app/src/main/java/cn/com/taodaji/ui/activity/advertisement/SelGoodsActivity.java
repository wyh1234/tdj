package cn.com.taodaji.ui.activity.advertisement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CreateTfAdvManage;
import cn.com.taodaji.model.entity.SelGoods;
import cn.com.taodaji.ui.activity.advertisement.adapter.SelGoodsAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class SelGoodsActivity extends DataBaseActivity implements OnRefreshListener, OnLoadmoreListener, SelGoodsAdapter.OnItemClickListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.ed_seach)
    EditText ed_seach;
    @BindView(R.id.tv_seach)
    TextView tv_seach;
    @BindView(R.id.ll_empty)
    RelativeLayout ll_empty;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_sel)
    TextView tv_sel;
    @BindView(R.id.tv_ok)
    TextView tv_ok;

    private SelGoodsAdapter adapter;
    private int page=1;
    private List<SelGoods.DataBean.ItemsBean> selGoods=new ArrayList<>();
    private ArrayList<SelGoods.DataBean.ItemsBean> goodsLis=new ArrayList<>();
    @OnClick({R.id.btn_back,R.id.tv_seach,R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_seach:
                refreshLayout.autoRefresh();
                break;
            case R.id.tv_ok:
                SelGoods.DataBean dataBean=new SelGoods.DataBean();
                dataBean.setItems(goodsLis);
                EventBus.getDefault().post(dataBean);
                finish();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.sel_goods_layout);
    }

    @Override
    protected void initData() {
        super.initData();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("选择商品");
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        adapter=new SelGoodsAdapter(this,selGoods);
        adapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(adapter);
        if (getIntent().getStringExtra("selGoods")!=null){
            tv_num.setText("0/10");
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        getData(page);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (getIntent().getStringExtra("selGoods")!=null){
            if (goodsLis.size()<
                    10){
                if (!selGoods.get(position).isFlag()){
                    selGoods.get(position).setFlag(true);
                    goodsLis.add(selGoods.get(position));
                    adapter.notifyItemChanged(position);

                }else {
                    selGoods.get(position).setFlag(false);
                    goodsLis.remove(selGoods.get(position));
                    adapter.notifyItemChanged(position);
                }
            }else {
                if (selGoods.get(position).isFlag()){
                    selGoods.get(position).setFlag(false);
                    goodsLis.remove(selGoods.get(position));
                    adapter.notifyItemChanged(position);
                }
            }


        }
        tv_num.setText(goodsLis.size()+"/10");
        tv_sel.setText("已勾选"+goodsLis.size()+"个");



    }

    public void getData(int pn){
        getRequestPresenter().products(PublicCache.loginSupplier.getStore(), UIUtils.isNullOrZeroLenght(ed_seach.getText().toString())
                ?"":ed_seach.getText().toString(), pn, 10, new RequestCallback<SelGoods>() {
            @Override
            protected void onSuc(SelGoods body) {
                stop();
                LogUtils.i(body);
                if (ListUtils.isEmpty(selGoods)) {
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

                selGoods.addAll(body.getData().getItems());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
                if (pn==1) {
                    ll_empty.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++page);
    }

    public void stop(){
        LogUtils.i(refreshLayout.isRefreshing());
        if (refreshLayout.isRefreshing()) {
            if (!ListUtils.isEmpty(selGoods)){
                selGoods.clear();
                adapter.notifyDataSetChanged();
            }
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }
}
