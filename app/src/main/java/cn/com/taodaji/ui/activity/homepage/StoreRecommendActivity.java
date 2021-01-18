package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.StoreRecommendListFragment;
import tools.activity.MenuToolbarActivity;
import com.base.utils.UIUtils;

public class StoreRecommendActivity extends MenuToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private int type;
    private StoreRecommendListFragment storeRecommendListFragment;

    @Override
    protected void initMainView() {
        if (storeRecommendListFragment == null) {
            body_other.setBackgroundColor(UIUtils.getColor(R.color.white));
            storeRecommendListFragment = new StoreRecommendListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, storeRecommendListFragment).commitAllowingStateLoss();
        }
        EventBus.getDefault().register(this);
        storeRecommendListFragment.setType(type);
    }

    @Override
    protected void initData() {

        if (type == 1) simple_title.setText("优秀店铺");
        else simple_title.setText("实力商家");
    }


    @Subscribe(sticky = true)
    public void onEvent(Integer type) {
        EventBus.getDefault().removeStickyEvent(type);
        this.type = type;
    }

    public static void startActivity(Context context, int type) {
        EventBus.getDefault().postSticky(type);
        context.startActivity(new Intent(context, StoreRecommendActivity.class));
    }
}
