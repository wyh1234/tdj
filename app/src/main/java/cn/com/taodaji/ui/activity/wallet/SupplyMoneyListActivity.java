package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.SupplyMoneyListFragment;
import tools.activity.SimpleToolbarActivity;

public class SupplyMoneyListActivity extends SimpleToolbarActivity implements View.OnClickListener{

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("货款明细");

        View tipsView= LayoutInflater.from(this).inflate(R.layout.top_tips_button,null);
        TextView txt_look_tips = com.base.utils.ViewUtils.findViewById(tipsView, R.id.txt_look_tips);
        TextView txt_go_look = com.base.utils.ViewUtils.findViewById(tipsView, R.id.txt_go_look);
        txt_look_tips.setText(UIUtils.getString(R.string.replace_to_new));
        txt_go_look.setText("切换到新版");
        txt_go_look.setTextColor(UIUtils.getColor(R.color.blue_2898eb));
        txt_go_look.setBackgroundResource(R.drawable.blue_roundness_button_stroke);
        txt_go_look.setOnClickListener(this);
        line_top_tips.setVisibility(View.VISIBLE);
        line_top_tips.addView(tipsView);

    }

    private SupplyMoneyListFragment fragment = null;

    @Override

    protected void initMainView() {
        if (fragment == null) {
            fragment = new SupplyMoneyListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SupplyMoneyListActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_go_look:
                SupplyMoneyNewListActivity.startActivity(this);
                finish();
                break;

        }
    }
}
