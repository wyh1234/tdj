package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.BannerSalesWebViewActivity;
import cn.com.taodaji.viewModel.adapter.SimpleSearchHostAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.RechargeMoneyPayEvent;
import cn.com.taodaji.ui.pay.RechargeMoneyPayActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class RechargeMoneyActivity extends SimpleToolbarActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    EditText editMoney;
    Button btnNext;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("充值");
    }


    private SimpleSearchHostAdapter adapter;

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_recharge_money);
        body_other.addView(mainView);
        recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        editMoney = ViewUtils.findViewById(mainView, R.id.edit_money);
        btnNext = ViewUtils.findViewById(mainView, R.id.btn_next);
        btnNext.setOnClickListener(this);
        editMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(".")) {
                    int index = s.toString().indexOf(".");
                    if (s.length() - index - 1 > 2) {
                        editMoney.setText(s.toString().substring(0, index + 3));
                        editMoney.setSelection(index + 3);
                    }
                }
            }
        });
        if (PublicCache.initializtionData != null) {
            editMoney.setHint("最少充值金额" + PublicCache.initializtionData.getRecharge_min_amount() + "元");
        } else initializtionData();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacesItemDecoration(UIUtils.dip2px(5)));
        adapter = new SimpleSearchHostAdapter() {
            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                View view = ViewUtils.getFragmentView(parent, R.layout.item_search_recyclerview);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextView textView = ViewUtils.findViewById(view, R.id.image_name);
                textView.setPadding(0, DensityUtil.dp2px(10), 0, DensityUtil.dp2px(10));
                textView.setTextColor(UIUtils.getColor(R.color.black_63));
                view.setBackgroundResource(R.drawable.s_rect_white_grayf2_stroke_graydb);
                return view;
            }
        };
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    String ss = JavaMethod.getFieldValue(bean, "imageName");
                    ss = ss.replace("元", "");
                    post(Float.valueOf(ss));
                    return true;
                }
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        String[] str = {"500元", "1000元", "1500元", "2000元", "3000元", "5000元"};

        List<ADInfo> list = new ArrayList<>();
        for (String s : str) {
            ADInfo adin = new ADInfo();
            adin.setImageName(s);
            list.add(adin);
        }
        adapter.setList(list);
    }


    private void post(float money) {
        Intent intent = new Intent(this, RechargeMoneyPayActivity.class);
        startActivityForResult(intent, 110);
        EventBus.getDefault().postSticky(new RechargeMoneyPayEvent(money));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == RESULT_OK) {
            finish();
        }
    }


    public void onClick(View view) {
        String text = editMoney.getText().toString();
        if (TextUtils.isEmpty(text)) {
            UIUtils.showToastSafe("请输入充值金额");
            return;
        }


        if (PublicCache.initializtionData != null) {
            if (new BigDecimal(text).compareTo(new BigDecimal(PublicCache.initializtionData.getRecharge_min_amount())) < 0) {
                UIUtils.showToastSafe("充值金额不可少于" + PublicCache.initializtionData.getRecharge_min_amount() + "元");
                return;
            }
            post(Float.valueOf(text));
        } else initializtionData();


    }

    public static void startActivity(Context cont) {
        cont.startActivity(new Intent(cont, RechargeMoneyActivity.class));
    }

    public static void startActivity(Context context, BigDecimal money) {
        Intent intent = new Intent(context, RechargeMoneyActivity.class);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }
}
