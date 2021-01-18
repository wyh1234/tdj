package cn.com.taodaji.ui.activity.myself;

import android.graphics.Color;
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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PaymentList;
import cn.com.taodaji.ui.activity.advertisement.adapter.PaymentListAdapter;
import cn.com.taodaji.ui.activity.advertisement.adapter.SelGoodsAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.DataBaseActivity;
import tools.animation.PaymentCalssifyPouwindow;
import tools.statusbar.Eyes;

public class PaymentListActivity extends DataBaseActivity implements OnRefreshListener, OnLoadmoreListener, PaymentCalssifyPouwindow.PaymentCalssifyPouwindowListener
, PaymentListAdapter.OnItemClickListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_classify)
    TextView tv_classify;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    public int pageNo = 1;//翻页计数器
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    private TimePickerView pvTime;
    private PaymentCalssifyPouwindow paymentCalssifyPouwindow;
    private List<PaymentList.DataBean.ListBean> listBeans=new ArrayList<>();
    private PaymentListAdapter paymentListAdapter;
    private int type;
    private String paymentTime;

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @OnClick({R.id.btn_back,R.id.tv_classify,R.id.tv_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_classify:
                if (paymentCalssifyPouwindow!=null){
                    if (paymentCalssifyPouwindow.isShowing()){
                        return;
                    }
                    paymentCalssifyPouwindow.showPopupWindow();
                }else {

                    paymentCalssifyPouwindow = new PaymentCalssifyPouwindow(this);
                    paymentCalssifyPouwindow.setPopupWindowFullScreen(true);//铺满
                    paymentCalssifyPouwindow.setDismissWhenTouchOutside(false);
                    paymentCalssifyPouwindow.setPaymentCalssifyPouwindowListener(this);
                    paymentCalssifyPouwindow.setInterceptTouchEvent(false);
                    paymentCalssifyPouwindow.showPopupWindow();
                }
                break;
            case R.id.tv_time:
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        setPaymentTime(setTimes(date));;
                        tv_time.setText(getTimes(date));
                        refreshLayout.autoRefresh();
                    }
                }) //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .setLabel("年", "月","", "", "", "")
                        .isCenterLabel(true)
                        .setContentTextSize(16)
                        .setLineSpacingMultiplier(1.8f)
                        .setDividerColor(Color.DKGRAY)
                        .setDate(Calendar.getInstance())
                        .setDecorView(null)
                        .build();
                pvTime.show();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.pay_ment_list_layout);
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
        tv_title.setText("缴费清单");
        tv_time.setText(getTimes(new Date(
                System.currentTimeMillis())));

        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        paymentListAdapter=new PaymentListAdapter(this,listBeans);
        paymentListAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(paymentListAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        setPaymentTime(getCurrDay());;
        tv_time.setText(setCurrDay());
        refreshLayout.autoRefresh();
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

    private void getData(int pageNo) {
        Map<String,Object> map=new HashMap<>();
        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("type", getType());
        map.put("paymentTime", getPaymentTime());
        map.put("ps", 10);

        map.put("pn", pageNo);
        getRequestPresenter().findPaymentList(map, new RequestCallback<PaymentList>() {
            @Override
            protected void onSuc(PaymentList body) {
                tv_money.setText("支出￥"+body.getData().getTotalMoney());
                stop();
                if (ListUtils.isEmpty(listBeans)) {
                    if (ListUtils.isEmpty(body.getData().getList())) {
                        //获取不到数据,显示空布局
                        ll_empty.setVisibility(View.VISIBLE);
                        return;
                    }
                    ll_empty.setVisibility(View.GONE);
                }


                if (ListUtils.isEmpty(body.getData().getList())) {
                    //已经获取数据
                    if (pageNo!=1){
                        UIUtils.showToastSafe("数据加载完毕");
                        return;
                    }

                }

                listBeans.addAll(body.getData().getList());
                paymentListAdapter.notifyDataSetChanged();


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

    @Override
    public void onCancel() {
        paymentCalssifyPouwindow.dismiss();
    }

    @Override
    public void onSel(int type) {
        setType(type);
        refreshLayout.autoRefresh();
        paymentCalssifyPouwindow.dismiss();
    }


    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }
    public static String setTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
    public static String getCurrDay() {//
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
    public static String setCurrDay() {//
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }
    public void stop(){
        LogUtils.i(refreshLayout.isRefreshing());
        if (refreshLayout.isRefreshing()) {
            if (!ListUtils.isEmpty(listBeans)){
                listBeans.clear();
                paymentListAdapter.notifyDataSetChanged();

            }
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
