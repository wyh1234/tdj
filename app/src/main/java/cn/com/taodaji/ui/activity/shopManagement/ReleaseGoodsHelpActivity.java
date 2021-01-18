package cn.com.taodaji.ui.activity.shopManagement;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.activity.ActivityManage;
import com.base.utils.ViewUtils;

import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;


public class ReleaseGoodsHelpActivity extends SimpleToolbarActivity implements View.OnClickListener {
    TextView buttonLeft;
    TextView buttonRight;

    @Override
    protected void initData() {

    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setTitle("发布商品");
        setStatusBarColor(R.color.blue_2898eb);
        setToolBarColor(R.color.blue_2898eb);

    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_release_goods_help);
        body_other.addView(mainView);
        buttonLeft = ViewUtils.findViewById(mainView, R.id.button_left);
        buttonRight = ViewUtils.findViewById(mainView, R.id.button_right);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                MenuToolbarActivity.goToPage(1);
                break;
            case R.id.button_right:
/*                Intent intent = new Intent(this,CommodityCategoryActivity.class);
                startActivity(intent);
                finish();*/
                ActivityManage.setTopActivity(CommodityCategoryActivity.class);
                break;
        }
    }
}
