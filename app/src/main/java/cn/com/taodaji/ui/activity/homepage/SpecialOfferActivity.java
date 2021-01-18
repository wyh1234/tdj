package cn.com.taodaji.ui.activity.homepage;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.SpecialOfferFragment;
import tools.activity.SimpleToolbarActivity;

public class SpecialOfferActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("活动");
    }

    private SpecialOfferFragment fragment;

    private View iv_shopping_cart;
    private TextView tv_shopping_count;
    @Override
    protected void initMainView() {


        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        iv_shopping_cart = findViewById(R.id.iv_shopping_cart);
        tv_shopping_count = findViewById(R.id.tv_shopping_count);



        if (fragment == null) {
            fragment = new SpecialOfferFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.other_body, fragment).commitAllowingStateLoss();
        }
        fragment.setIv_shopping_cart(iv_shopping_cart);
        fragment.setTv_shopping_count(tv_shopping_count);

    }

    @Override
    protected void initData() {

    }
}
