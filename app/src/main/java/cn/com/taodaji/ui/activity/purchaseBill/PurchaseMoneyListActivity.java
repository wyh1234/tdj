package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupPurchaseMoneyAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CustomerFinanceCondition;
import cn.com.taodaji.model.entity.CustomerFinanceItem;
import cn.com.taodaji.model.entity.CustomerFinanceRecord;
import cn.com.taodaji.model.entity.PurchaseFinanceRecord;

import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.DateUtils;
import com.base.utils.SerializableMap;
import com.base.utils.ViewUtils;

import static com.base.viewModel.adapter.GroupRecyclerAdapter.TYPE_GROUP;


public class PurchaseMoneyListActivity extends SimpleToolbarActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnGetDataListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
    }

    private SwipeToLoadLayout swipeToLoadLayout;
    private LoadMoreUtil loadMoreUt;

    //paymentMethod	num	0、退款  1、支付宝 2、微信支付 3、余额支付 4、微信+余额支付 5、支付宝+余额支付 6、提现
    private String[] payment = {"退款", "支付宝", "微信支付", "余额支付", "微信+余额支付", "支付宝+余额支付", "提现","花呗支付","","线下缴费"};

    private GroupPurchaseMoneyAdapter moneyAdapter;


    private int state;//页面状态 1电子账单 2为余额明细  3交易筛选明细,4余额筛选明细

    private Map<String, Object> parmasMap;//筛选明细需要的参数


    private View headerView;//筛选明细的头部
    private TextView tv_title_1, tv_title_2;//头部的文字

    @Override
    protected void initData() {
        super.initData();

        //页面状态 1电子账单 2为余额明细  3交易筛选明细,4余额筛选明细
        state = getIntent().getIntExtra("data", 1);
        SerializableMap smap = (SerializableMap) getIntent().getSerializableExtra("map");
        if (smap != null) {
            parmasMap = smap.getMap();
        }
        switch (state) {
            case 1:
                right_textView.setText("筛选");
                setTitle("电子账单");
                title_right.setOnClickListener(this);
                break;
            case 2:
                right_textView.setText("筛选");
                setTitle("余额明细");
                title_right.setOnClickListener(this);
                break;
            case 3:
                setTitle("交易筛选明细");
                break;
            case 4:
                setTitle("余额筛选明细");
                break;
        }
        moneyAdapter.setState(state);
        swipeToLoadLayout.setRefreshing(true);
        // getData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right:
                FiltrateTransactionActivity.startActivity(getBaseActivity(), state + 2);
                break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.swipe_twitter_swipe_toload_recyclerview);
        // View mainView = getLayoutView(R.layout.t_recyclerview);
        body_other.addView(mainView);
        swipeToLoadLayout = ViewUtils.findViewById(mainView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.swipe_target);
        recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moneyAdapter = new GroupPurchaseMoneyAdapter();
        moneyAdapter.setTypeFixed(TYPE_GROUP);
        recyclerView.setAdapter(moneyAdapter);


        headerView = ViewUtils.getFragmentView(recyclerView, R.layout.item_purchase_filtrate_header);
        tv_title_1 = ViewUtils.findViewById(headerView, R.id.tv_title_1);
        tv_title_2 = ViewUtils.findViewById(headerView, R.id.tv_title_2);
        loadMoreUt = new LoadMoreUtil(this, recyclerView);

    }

    @Override
    public void onRefresh() {
        getData(1);
    }


    private List<Object> initList(List<CustomerFinanceItem> lst) {
        //上一个的月份
        int last = -1;
        List<Object> list = new ArrayList<>();
        if (lst == null) return list;

        int now = DateUtils.getMonth(0);
        PurchaseFinanceRecord lsc = null;
        for (CustomerFinanceItem bean : lst) {

            int m = DateUtils.getMonth(bean.getCreateTime(), "yyyy-MM-dd HH:mm");

            // String mon = m == now ? "本月" : JavaMethod.intParse(m + 1) + "月";
            String mon = DateUtils.dateStringToString(bean.getCreateTime(), "yyyy-MM-dd HH:mm", "yyyy年MM月");

            if (m != last) {
                lsc = new PurchaseFinanceRecord();
                list.add(lsc);
                last = m;
            } else lsc = (PurchaseFinanceRecord) list.get(list.size() - 1);


            if (lsc != null) {
                lsc.setMonth(mon);
                if (lsc.getItemList() == null)
                    lsc.setItemList(new ArrayList<PurchaseFinanceRecord.ItemBean>());

                PurchaseFinanceRecord.ItemBean itemBean = new PurchaseFinanceRecord.ItemBean();

                String weekday = bean.getCreateTime();
                String yearMonth = bean.getCreateTime();
                if (!TextUtils.isEmpty(weekday)) {
                    weekday = DateUtils.getWeekDay(weekday, "yyyy-MM-dd HH:mm", "周");
                    if (yearMonth.length() > 5) yearMonth = yearMonth.substring(5, 10);
                }
                itemBean.setEntityId(bean.getEntityId());
                itemBean.setWeekDay(weekday);
                itemBean.setYearMonth(yearMonth);
                itemBean.setPaymentAmount(bean.getPaymentAmount() == null ? "0" : bean.getPaymentAmount().toString());
                if (bean.getMoneyAmount() != null) itemBean.setBalance(bean.getMoneyAmount());
                else itemBean.setBalance(BigDecimal.ZERO);

                itemBean.setStatus(bean.getStatus());
                itemBean.setPaymentMethod(bean.getPaymentMethod());

/*                if (bean.getWithdrawalRechargeAmount() != null)
                    itemBean.setBalance(bean.getWithdrawalRechargeAmount().add(bean.getWithdrawalAfterSaleAmount()));
                else itemBean.setBalance(BigDecimal.ZERO);*/


                //status	num	表示数据类型： (1) 充值、(2)支付、(3)退款、(4)提现  5、取消订单
                switch (bean.getStatus()) {
                    case 1:
                        itemBean.setPaySymbol("+");
                        itemBean.setImage(R.mipmap.icon_recharge);
                        itemBean.setDescription(bean.getRole() + bean.getCustomerName() + payment[bean.getPaymentMethod()] + "充值");
                        break;
                    case 2:
                        itemBean.setPaySymbol("-");
                        itemBean.setImage(R.mipmap.icon_order_form);
                        itemBean.setDescription("编号：" + bean.getExtOrderId());
                        if (state % 2 == 0) {
                            itemBean.setPaymentAmount(bean.getAccountPaymentAmount() == null ? "0" : bean.getAccountPaymentAmount().toString());
                        }
                        break;
                    case 3:
                        itemBean.setPaySymbol("+");
                        itemBean.setImage(bean.getProductImg());
                        String name = bean.getProductName();
                        int nameL = name == null ? 0 : name.length();
                        String ss = bean.getProductNickName();
                        if (ss == null) ss = "";
                        int niceName = ss.length();

                        if (nameL >= goodsNameLength) ss = "";
                        else if (nameL + niceName > goodsNameLength) {
                            ss = ss.substring(0, goodsNameLength - nameL) + "..";
                        }
                        if (niceName > 0) {
                            ss = "(" + ss + ")";
                        }
                        itemBean.setDescription(name + ss);
                        itemBean.setPaymentAmount(bean.getRefundTotalMoney() == null ? "0" : bean.getRefundTotalMoney().toString());
                        break;
                    case 4:
                        itemBean.setPaySymbol("-");
                        itemBean.setImage(R.mipmap.icon_withdraw_deposit);
                        itemBean.setDescription("致电平台申请提现");
       /*                 if (state % 2 == 0) {
                            itemBean.setPaymentAmount(bean.getRefundTotalMoney() == null ? "0" : bean.getRefundTotalMoney().toString());
                        }*/
                        break;
                    case 5:
                        itemBean.setPaySymbol("+");
                        itemBean.setImage(R.mipmap.icon_order_cancel);
                        itemBean.setDescription("编号：" + bean.getExtOrderId());
                        break;
                    case 6:
                        itemBean.setPaySymbol("+");
                        itemBean.setImage(R.mipmap.ic_deposit_refund);

                        String name1 = bean.getProductName();
                        int nameL1 = name1 == null ? 0 : name1.length();
                        String ss1 = bean.getProductNickName();
                        if (ss1 == null) ss1 = "";
                        int niceName1 = ss1.length();

                        if (nameL1 >= goodsNameLength) ss1 = "";
                        else if (nameL1 + niceName1 > goodsNameLength) {
                            ss1 = ss1.substring(0, goodsNameLength - nameL1) + "..";
                        }
                        if (niceName1 > 0) {
                            ss1 = "(" + ss1 + ")";
                        }
                        itemBean.setDescription(name1 + ss1);
                        break;
                }
                lsc.getItemList().add(itemBean);
            }
        }

        return list;
    }


    public static void startActivity(Context context, int state, Map<String, Object>... map) {
        Intent intent = new Intent(context, PurchaseMoneyListActivity.class);
        intent.putExtra("data", state);
        if (map.length > 0) intent.putExtra("map", new SerializableMap(map[0]));
        context.startActivity(intent);
    }


    @Override
    public void getData(int pn) {
        switch (state) {
            case 1:
                customerFinanceRecord(pn);
                break;
            case 2:
                customerFinanceRecord_balance(pn);
                break;
            case 3:
                customerFinanceCondition(pn);
                break;
            case 4:
                customerFinanceCondition_balance(pn);
                break;
        }
    }

    //电子账单
    private void customerFinanceRecord(int pn) {
        if (PublicCache.loginPurchase != null)
            getRequestPresenter().customerFinanceRecord(PublicCache.loginPurchase.getEntityId(), pn, 19, new RequestCallback<CustomerFinanceRecord>() {
                @Override
                public void onSuc(CustomerFinanceRecord body) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                    if (body.getData() != null)
                        loadMoreUt.setData(initList(body.getData().getItems()), body.getData().getPn(), body.getData().getPs());
                }

                @Override
                public void onFailed(int customerFinanceRecord, String msg) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                }
            });
    }

    //余额明细

    private void customerFinanceRecord_balance(int pn) {
        if (PublicCache.loginPurchase != null)
            getRequestPresenter().customerFinanceRecord_balance(PublicCache.loginPurchase.getEntityId(), pn, 19, new RequestCallback<CustomerFinanceRecord>() {
                @Override
                public void onSuc(CustomerFinanceRecord body) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                    if (body.getData() != null)
                        loadMoreUt.setData(initList(body.getData().getItems()), body.getData().getPn(), body.getData().getPs());
                }

                @Override
                public void onFailed(int customerFinanceRecord, String msg) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                }
            });
    }

    //筛选后的电子明细
    private void customerFinanceCondition(int pn) {
        if (parmasMap != null)
            getRequestPresenter().customerFinanceCondition(parmasMap, pn, 19, new RequestCallback<CustomerFinanceCondition>() {
                @Override
                public void onSuc(CustomerFinanceCondition body) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                    if (body.getData() != null) {
                        initHeader(body.getData().getCount());
                        loadMoreUt.setData(initList(body.getData().getItems()), body.getData().getPn(), body.getData().getPs());
                    }
                }

                @Override
                public void onFailed(int customerFinanceRecord, String msg) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                }
            });
    }

    //筛选后的余额明细
    private void customerFinanceCondition_balance(int pn) {
        if (parmasMap != null)
            getRequestPresenter().customerFinanceCondition_balance(parmasMap, pn, 19, new RequestCallback<CustomerFinanceCondition>() {
                @Override
                public void onSuc(CustomerFinanceCondition body) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                    if (body.getData() != null) {
                        initHeader(body.getData().getCount());
                        loadMoreUt.setData(initList(body.getData().getItems()), body.getData().getPn(), body.getData().getPs());
                    }

                }

                @Override
                public void onFailed(int customerFinanceRecord, String msg) {
                    if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                }
            });
    }

    private void initHeader(CustomerFinanceCondition.DataBean.CountBean bean) {
        if (bean == null || headerView == null) return;
        if (!moneyAdapter.isHasHeader(headerView)) {
            tv_title_1.setText("已支付：" + bean.getPaymentConsumeMoney().toString() + "        售后退款：" + bean.getPaymentAfterSaleMoney().toString() + "        充值：" + bean.getPaymentRechargeMoney().toString());
            tv_title_2.setText("提现：" + bean.getPaymentWithdrawalMoney().toString() + "   取消订单：" + bean.getPaymentCancelOrderMoney());
            moneyAdapter.addHeaderView(headerView);
        }
    }


}
