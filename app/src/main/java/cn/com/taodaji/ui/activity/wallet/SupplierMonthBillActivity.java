package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimplePPWAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.Float_String;
import cn.com.taodaji.model.entity.SupplyMonthlyBillBean;
import cn.com.taodaji.ui.ppw.RecyclerViewPPW;
import cn.com.taodaji.ui.view.MonthlyBillLineView;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-12-08.
 * 供应商月账单页面
 */

public class SupplierMonthBillActivity extends SimpleToolbarActivity {
    private MonthlyBillLineView monthlyBillLineView;
    private MonthlyBillLineView supplier_monthlyBillLineView_after_sales;
    //折线图
    private List<Float_String> list1 = new ArrayList<>();
    //消费情况
    private List<Float_String> list2 = new ArrayList<>();

    private TextView tv_balance_money, tv_after_sale_money, tv_date;
    private LinearLayout date_par, date_group;
    private RecyclerViewPPW debug_listView;
    private SimplePPWAdapter adapter;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("月账单");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_supplier_month_bill);
        body_scroll.addView(mainView);
        monthlyBillLineView = ViewUtils.findViewById(mainView, R.id.supplier_monthlyBillLineView);
        supplier_monthlyBillLineView_after_sales = ViewUtils.findViewById(mainView, R.id.supplier_monthlyBillLineView_after_sales);
        tv_balance_money = mainView.findViewById(R.id.tv_balance_money);
        tv_after_sale_money = mainView.findViewById(R.id.tv_after_sale_money);

        date_par = mainView.findViewById(R.id.date_par);
        tv_date = mainView.findViewById(R.id.tv_date);

        date_group = mainView.findViewById(R.id.date_group);
        date_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dates = tv_date.getText().toString();
                if (adapter == null) {
                    adapter = new SimplePPWAdapter();
                    adapter.setItemClickListener(new OnItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                            if (onclickType == 0) {
                                String sstr = JavaMethod.getFieldValue(bean, "imageName");
                                tv_date.setText(sstr);
                                String year = DateUtils.dateStringToString(sstr, "yyyy年MM月", "yyyy");
                                String month = DateUtils.dateStringToString(sstr, "yyyy年MM月", "MM");
                                getData(Integer.valueOf(year), Integer.valueOf(month));
                                debug_listView.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                    debug_listView = new RecyclerViewPPW(getBaseActivity(), date_par, adapter);
                }


                List<ADInfo> list = new ArrayList<>();
                ADInfo adInfo = new ADInfo();
                adInfo.setImageName(dates);
                list.add(adInfo);

                long now = DateUtils.dateStringToLong(dates, "yyyy年MM月");
                for (int i = 1; i <= 4; i++) {
                    String str1 = DateUtils.getMonth("yyyy年MM月", -i, now);
                    ADInfo adInfo1 = new ADInfo();
                    adInfo1.setImageName(str1);
                    list.add(adInfo1);
                }
                adapter.notifyDataSetChanged(list);

                if (!debug_listView.isShowing()) debug_listView.showAsDropDown(date_par);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        if (month < 10) {
            tv_date.setText(year + "年0" + month + "月");
        } else tv_date.setText(year + "年" + month + "月");
        getData(year, month);
    }


    public void getData(int year, int month) {
        if (PublicCache.loginSupplier == null) return;
        getRequestPresenter().findPageMonthlyBill(PublicCache.loginSupplier.getStore(), year, month, new RequestCallback<SupplyMonthlyBillBean>() {
            @Override
            protected void onSuc(SupplyMonthlyBillBean body) {
                if (body.getData() != null) {

                    tv_after_sale_money.setText(String.valueOf(body.getData().getCurrentMonthAfterSaleMoney()));
                    tv_balance_money.setText(String.valueOf(body.getData().getCurrentMonthFreezeMoney()));
                    list1.clear();
                    list2.clear();
                    if (body.getData().getReceiveMoneyList() != null) {
                        for (SupplyMonthlyBillBean.DataBean.ReceiveMoneyListBean receiveMoneyListBean : body.getData().getReceiveMoneyList()) {
                            Float_String floatS = new Float_String();
                            floatS.setValue(receiveMoneyListBean.getFreezeMoney().floatValue());
                            floatS.setDescription(receiveMoneyListBean.getMonth() + "月");
                            list1.add(floatS);
                        }
                        //折线图  需要倒转顺序
                        Collections.reverse(list1);
                        monthlyBillLineView.setValues(list1);
                        monthlyBillLineView.invalidate();
                    }

                    if (body.getData().getAfterSalersMoneyList() != null) {
                        for (SupplyMonthlyBillBean.DataBean.AfterSalersMoneyListBean afterSalersMoneyListBean : body.getData().getAfterSalersMoneyList()) {
                            Float_String floatS = new Float_String();
                            floatS.setValue(afterSalersMoneyListBean.getAfterSaleMoney().floatValue());
                            floatS.setDescription(afterSalersMoneyListBean.getMonth() + "月");
                            list2.add(floatS);
                        }
                        Collections.reverse(list2);
                        supplier_monthlyBillLineView_after_sales.setValues(list2);
                        supplier_monthlyBillLineView_after_sales.invalidate();
                    }


                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


    public static void startActivity(Context context, int year, int month) {
        Intent intent = new Intent(context, SupplierMonthBillActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        context.startActivity(intent);
    }
}
