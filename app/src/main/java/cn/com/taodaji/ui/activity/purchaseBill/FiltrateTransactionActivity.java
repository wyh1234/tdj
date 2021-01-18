package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyFilterListActivity;
import cn.com.taodaji.viewModel.adapter.FiltrateTransactionAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FiltrateTransaction;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;

import com.base.widget.DatePickDialog;
import com.base.utils.DateUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-08-18.
 */

public class FiltrateTransactionActivity extends SimpleToolbarActivity implements View.OnClickListener {
    RecyclerView selectTypeRecyclerView;
    RecyclerView selectTimeRecyclerView;
    TextView dateStart;
    TextView dateEnd;
    TextView ok;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();

    }

    private int state;//页面状态 1电子账单 2为余额明细  3交易筛选明细,4余额筛选明细   5,供货商筛选明细
    private FiltrateTransactionAdapter transactionAdapter_type;

    private FiltrateTransactionAdapter transactionAdapter_time;

    private int type;//查询类别

    private String start_time;
    private String end_time;

    @Override
    protected void initData() {
        super.initData();
        state = getIntent().getIntExtra("data", 1);

        String[] time = {"今日", "近1周", "近1月", "近3月", "近6月"};

        String[] type;
        int[] typeI;
        if (state == 3) {
            setTitle("账单明细筛选");
            // 表示数据类型： (0)全部、(1) 充值、(2)支付、(3)退款、(4)提现、 5、取消订单 6、退押金
            type = new String[]{"全部", "已支付", "售后退款", "提现", "充值", "取消订单", "退押金"};
            typeI = new int[]{0, 2, 3, 4, 1, 5, 6};
        } else if (state == 4) {
            setTitle("余额明细筛选");
            // 表示数据类型： (0)全部、(2)支付、(3)退款、(4)提现、(1) 充值、 5、取消订单 6、退押金
            type = new String[]{"全部", "已支付", "售后退款", "提现", "充值", "取消订单", "退押金"};
            typeI = new int[]{0, 2, 3, 4, 1, 5, 6};
        } else if (state == 5) {
            setTitle("明细筛选");
            // 表示数据类型：数据类型：1、订单 2、提现 3、售后 4、提现撤销 5、提现驳回 6、押金超时支付      100、冻结款（自定义的，后面会折分 1 和 FREEZE）
            type = new String[]{"全部", "已支付", "售后退款", "提现", "冻结款", " 支付押金","支出","余额退款"};
            typeI = new int[]{0, 1, 3, 2, 100, 6,11,12};
        } else return;


        List<FiltrateTransaction> list_type = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            FiltrateTransaction bean = new FiltrateTransaction();
            bean.setType(typeI[i]);
            bean.setText(type[i]);
            list_type.add(bean);
        }
        List<FiltrateTransaction> list_time = new ArrayList<>();
        for (int i = 0; i < time.length; i++) {
            FiltrateTransaction bean = new FiltrateTransaction();
            bean.setText(time[i]);
            list_time.add(bean);
        }


        //初始化类别
        list_type.get(0).setSelected(true);
        transactionAdapter_type.setList(list_type);
        this.type = 0;


        //初始化起止时间
        list_time.get(0).setSelected(true);
        transactionAdapter_time.setList(list_time);

        String nowDate = DateUtils.parseTime("yyyy-MM-dd");
        start_time = nowDate;
        end_time = nowDate;
        dateStart.setText(start_time);
        dateEnd.setText(end_time);

    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_filtrate_transaction);
        body_other.addView(mainView);
        selectTypeRecyclerView = ViewUtils.findViewById(mainView, R.id.select_type_recyclerView);
        selectTimeRecyclerView = ViewUtils.findViewById(mainView, R.id.select_time_recyclerView);
        dateStart = ViewUtils.findViewById(mainView, R.id.date_start);
        dateStart.setOnClickListener(this);
        dateEnd = ViewUtils.findViewById(mainView, R.id.date_end);
        dateEnd.setOnClickListener(this);
        ok = ViewUtils.findViewById(mainView, R.id.ok);
        ok.setOnClickListener(this);

        selectTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        selectTypeRecyclerView.addItemDecoration(new DividerGridItemDecoration(UIUtils.dip2px(5)));

        selectTimeRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        selectTimeRecyclerView.addItemDecoration(new DividerGridItemDecoration(UIUtils.dip2px(5)));

        transactionAdapter_type = new FiltrateTransactionAdapter();
        transactionAdapter_type.setType(1);
        transactionAdapter_type.setRadio(true);
        transactionAdapter_type.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    FiltrateTransaction data = (FiltrateTransaction) bean;
                    type = data.getType();
                    transactionAdapter_type.setSelected(position);
                    return true;
                }
                return false;
            }
        });
        transactionAdapter_time = new FiltrateTransactionAdapter();
        transactionAdapter_time.setType(2);
        transactionAdapter_time.setRadio(true);
        transactionAdapter_time.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    transactionAdapter_time.setSelected(position);

                    end_time = DateUtils.parseTime("yyyy-MM-dd");

                    switch (position) {
                        case 0://今日
                            start_time = end_time;
                            break;
                        case 1://近一周
                            start_time = DateUtils.getDay("yyyy-MM-dd", -7);
                            break;
                        case 2://近一月
                            start_time = DateUtils.getMonth("yyyy-MM-dd", -1);
                            break;
                        case 3://近三月
                            start_time = DateUtils.getMonth("yyyy-MM-dd", -3);
                            break;
                        case 4://近6月
                            start_time = DateUtils.getMonth("yyyy-MM-dd", -6);
                            break;
                    }
                    dateStart.setText(start_time);
                    dateEnd.setText(end_time);

                    return true;
                }
                return false;
            }
        });
        selectTimeRecyclerView.setAdapter(transactionAdapter_time);
        selectTypeRecyclerView.setAdapter(transactionAdapter_type);


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_start:
                new DatePickDialog(this, start_time, dateStart) {
                    @Override
                    public void setOnclick(DialogInterface dialog, int whichButton, String dateStr) {
                        start_time = dateStr;
                        if (end_time.compareTo(start_time) < 0) {
                            ok.setEnabled(false);
                        } else ok.setEnabled(true);
                    }
                };
                break;
            case R.id.date_end:
                new DatePickDialog(this, end_time, dateEnd) {
                    @Override
                    public void setOnclick(DialogInterface dialog, int whichButton, String dateStr) {
                        end_time = dateStr;
                        if (end_time.compareTo(start_time) < 0) {
                            ok.setEnabled(false);
                        } else ok.setEnabled(true);
                    }
                };
                break;
            case R.id.ok:
                if (PublicCache.loginPurchase != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", PublicCache.loginPurchase.getEntityId());
                    map.put("type", type);
                    map.put("startTime", start_time);
                    map.put("endTime", end_time);
                    PurchaseMoneyListActivity.startActivity(this, state, map);
                } else if (PublicCache.loginSupplier != null) {
                    SupplyMoneyFilterListActivity.startActivity(this, type, start_time, end_time);
                }

                break;
        }
    }

    public static void startActivity(Context context, int state) {
        Intent intent = new Intent(context, FiltrateTransactionActivity.class);
        intent.putExtra("data", state);
        context.startActivity(intent);
    }
}
