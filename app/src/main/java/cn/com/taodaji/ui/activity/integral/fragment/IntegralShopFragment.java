package cn.com.taodaji.ui.activity.integral.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.HttpRetrofit;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.entity.IntegralShop;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestService;
import cn.com.taodaji.ui.activity.integral.IntegralShopMainActivity;
import cn.com.taodaji.ui.activity.integral.ShopFiltrateActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.fragment.DataBaseFragment;

public class IntegralShopFragment extends DataBaseFragment implements OnRefreshListener, OnLoadmoreListener, IntegralShopAdapter.OnItemClickListener {
    private View mainView;
    @BindView(R.id.recyclerView_list)
     RecyclerView recyclerView_list;
 /*   @BindView(R.id.rl_filtrate)
    RelativeLayout rl_filtrate;*/
    @BindView(R.id.rl_zh)
    RelativeLayout rl_zh;//综合
    @BindView(R.id.rl_xl)
    RelativeLayout rl_xl;//销量
    @BindView(R.id.rl_jg)
    RelativeLayout rl_jg;//价格

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv_seach)
    TextView tv_seach;
    @BindView(R.id.ed_name)
    EditText ed_name;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    public int pageNo = 1;//翻页计数器
    private List<IntegralShop.DataBean.ObjBean> data=new ArrayList<>();
    private IntegralShopAdapter shopAdapter;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    private IntegralShopMainActivity activity;
    private String sort="";
    private int price_click_coun = 1;//价格排序的点击次数
    private int sellNum_click_coun = 1;//销量排序的点击次数
    private LoadingDialog loadingDialog;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    //sort_gray

    @OnClick({R.id.btn_back,R.id.rl_zh,R.id.rl_xl,R.id.rl_jg,R.id.tv_seach})
    public void onClick(View view){
        switch (view.getId()){
         /*   case R.id.rl_filtrate:
                Intent intent=new Intent(getContext(), ShopFiltrateActivity.class);
                startActivity(intent);
                break;*/
            case R.id.btn_back:
               activity.finish();
                break;
            case R.id.rl_zh:
                setSort("");
                ed_name.setText(null);
                iv.setImageResource(R.mipmap.hongse_bottom);
                tv.setTextColor(getResources().getColor(R.color.red_f0));
                tv1.setTextColor(getResources().getColor(R.color.gray_66));
                tv2.setTextColor(getResources().getColor(R.color.gray_66));
                iv1.setImageResource(R.mipmap.icon_price_unselected);
                iv2.setImageResource(R.mipmap.icon_price_unselected);
                price_click_coun = 1;
                sellNum_click_coun=1;
                refreshLayout.autoRefresh();
                break;
            case R.id.rl_xl:
                ed_name.setText(null);
                iv.setImageResource(R.mipmap.sort_gray);
                tv.setTextColor(getResources().getColor(R.color.gray_66));
                tv1.setTextColor(getResources().getColor(R.color.red_f0));
                tv2.setTextColor(getResources().getColor(R.color.gray_66));
                iv2.setImageResource(R.mipmap.icon_price_unselected);
                sellNum_click_coun++;
                price_click_coun = 1;
                if (sellNum_click_coun % 2 == 1) {
                    setSort("{sellNum:1}");
                    iv1.setImageResource(R.mipmap.quanhui_bottom);
                }else {
                    setSort("{sellNum:0}");
                    iv1.setImageResource(R.mipmap.quanhui_top);
                }
                refreshLayout.autoRefresh();
                break;
            case R.id.rl_jg:
                sellNum_click_coun=1;
                iv.setImageResource(R.mipmap.sort_gray);
                tv.setTextColor(getResources().getColor(R.color.gray_66));
                tv1.setTextColor(getResources().getColor(R.color.gray_66));
                tv2.setTextColor(getResources().getColor(R.color.red_f0));
                iv1.setImageResource(R.mipmap.icon_price_unselected);
                price_click_coun++;
                if (price_click_coun % 2 == 1) {
                    setSort("{price:1}");
                    iv2.setImageResource(R.mipmap.quanhui_bottom);
                }else {
                    setSort("{price:0}");
                    iv2.setImageResource(R.mipmap.quanhui_top);
                }
                refreshLayout.autoRefresh();
                break;
            case R.id.tv_seach:
                setSort("");
                iv.setImageResource(R.mipmap.hongse_bottom);
                tv.setTextColor(getResources().getColor(R.color.red_f0));
                iv1.setImageResource(R.mipmap.icon_price_unselected);
                iv2.setImageResource(R.mipmap.icon_price_unselected);
                price_click_coun = 1;
                sellNum_click_coun=1;
                refreshLayout.autoRefresh();
                    break;
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (IntegralShopMainActivity) context;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragments_integral_shop, container, false);
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
         shopAdapter=new IntegralShopAdapter(getContext(),data);
        shopAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(shopAdapter);
        btn_back.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        super.initData();
        refreshLayout.autoRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo=1;
        getData(1);
    }

    protected  void getData(int pn){
        LogUtils.i(getSort());
        Map<String,Object> map=new HashMap<>();
        map.put("lim",10);
        map.put("offs",pn);
        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
//        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getSubUserId());
        map.put("name",ListUtils.isNullOrZeroLenght(ed_name.getText().toString())?"":ed_name.getText().toString());
        map.put("sort",sort);
        map.put("status","1");
        getIntegralRequestPresenter().commodity_queryList(map, new RequestCallback<IntegralShop>(this) {
            @Override
            public void onSuc(IntegralShop body) {
                LogUtils.i(pn);
                stop();
                LogUtils.i(body);
                //如果是第一次获取数据
                if (ListUtils.isEmpty(data)) {
                    if (ListUtils.isEmpty(body.getData().getObj())) {
                        //获取不到数据,显示空布局
                        ll_empty.setVisibility(View.VISIBLE);
//                        mStateView.showEmpty();
                        return;
                    }
                    ll_empty.setVisibility(View.GONE);
//                    mStateView.showContent();//显示内容
                }
                if (pn==1){
                    if (!ListUtils.isEmpty(data)) {
                        data.clear();
                    }
                }

                if (ListUtils.isEmpty(body.getData().getObj())) {
                    //已经获取数据
                    if (pn!=1){
                        UIUtils.showToastSafe("数据加载完毕");
                        return;
                    }else {
                        UIUtils.showToastSafe("暂无数据");
                    }

                }

                data.addAll(body.getData().getObj());
                shopAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                if (pn==1) {
                    if (!ListUtils.isEmpty(data)) {
                        data.clear();
                        shopAdapter.notifyDataSetChanged();
                    }
                }
                stop();
            }
        });
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

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("url",  PublicCache.getROOT_URL().get(2)+"tdj-store/store/commodity/queryOne?id="
                +data.get(position).getId()+"&userId="+(PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId()));
        startActivity(intent);


    }
}
