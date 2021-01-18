package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.SupplyMoneyListNewFragment;
import cn.com.taodaji.ui.fragment.SupplyMoneyListfilterFragment;
import tools.activity.SimpleToolbarActivity;

/**
 * 新版供应商资金明细列表
 */
public class SupplyMoneyFilterListActivity extends SimpleToolbarActivity {
    private SupplyMoneyListfilterFragment fragment = null;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("明细筛选");
    }


    @Override

    protected void initMainView() {
        if (fragment == null) {
            fragment = new SupplyMoneyListfilterFragment();
            Bundle bundle = new Bundle();
            bundle.putString("startTime", getIntent().getStringExtra("startTime"));
            bundle.putString("endTime", getIntent().getStringExtra("endTime"));
            int type = getIntent().getIntExtra("type", 0);
            if (type == 100) {
                bundle.putString("orderFreezeStatus", "FREEZE");
                bundle.putInt("type", 1);
            } else bundle.putInt("type", type);


            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, int type, String startTime, String endTime) {
        Intent intent = new Intent(context, SupplyMoneyFilterListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
//        intent.putExtra("orderFreezeStatus", orderFreezeStatus);
        context.startActivity(intent);
    }


}
