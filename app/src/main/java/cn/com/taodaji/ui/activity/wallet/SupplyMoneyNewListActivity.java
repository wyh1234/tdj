package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.purchaseBill.FiltrateTransactionActivity;
import cn.com.taodaji.ui.fragment.SupplyMoneyListNewFragment;
import tools.activity.SimpleToolbarActivity;

/**
 * 新版供应商资金明细列表
 */
public class SupplyMoneyNewListActivity extends SimpleToolbarActivity implements View.OnClickListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("资金明细");
        right_textView.setText("筛选");
        // title_right.setVisibility(View.GONE);
        //   right_textView_new.setText("提成说明");
        right_textView.setTextSize(14);
        // right_textView_new.setTextSize(14);
        right_textView.setOnClickListener(this);
        //   right_textView_new.setOnClickListener(this);

      /*  View tipsView = LayoutInflater.from(this).inflate(R.layout.top_tips_button, null);
        TextView txt_look_tips = com.base.utils.ViewUtils.findViewById(tipsView, R.id.txt_look_tips);
        TextView txt_go_look = com.base.utils.ViewUtils.findViewById(tipsView, R.id.txt_go_look);
        txt_look_tips.setText(UIUtils.getString(R.string.replace_to_old));
        txt_go_look.setText("切换到旧版");
        txt_go_look.setBackgroundResource(R.drawable.gray_roundness_button_stroke);
        txt_go_look.setTextColor(UIUtils.getColor(R.color.gray_66));
        txt_go_look.setOnClickListener(this);
        line_top_tips.setVisibility(View.VISIBLE);
        line_top_tips.addView(tipsView);*/
    }

    private SupplyMoneyListNewFragment fragment = null;

    @Override

    protected void initMainView() {
        if (fragment == null) {
            fragment = new SupplyMoneyListNewFragment();
//            Bundle bundle=new Bundle();
//            bundle.putString("title","资金明细");
//            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SupplyMoneyNewListActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_go_look:
                SupplyMoneyListActivity.startActivity(this);
                finish();
                break;
            case R.id.right_text:
//                Intent intent = new Intent();
//                intent.setClass(this, DetailFiltrateActivity.class);
//                startActivity(intent);
                FiltrateTransactionActivity.startActivity(getBaseActivity(), 5);
                break;
        }
    }
}
