package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.AfterSalesListFragment;
import tools.activity.SimpleToolbarActivity;

public class AfterSalesListActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("退货/售后");
    }

    private AfterSalesListFragment afterSalesListFragment;

    @Override
    protected void initMainView() {
        if (afterSalesListFragment == null) {
            afterSalesListFragment = new AfterSalesListFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, afterSalesListFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AfterSalesListActivity.class));
    }

}
