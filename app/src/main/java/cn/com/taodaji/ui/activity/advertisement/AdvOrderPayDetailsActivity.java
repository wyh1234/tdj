package cn.com.taodaji.ui.activity.advertisement;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AvdOrder;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class AdvOrderPayDetailsActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn_left)
    Button btn_left;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_orderNo)
    TextView tv_orderNo;
    @OnClick({R.id.btn_back,R.id.btn_left,R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                startPage();
                break;
            case R.id.btn_left:
                startPage();
                break;
            case R.id.btn_right:
                ActivityManage.setTopActivity(ManageActivity.class);
                ManageActivity manageActivity = ActivityManage.getActivity(ManageActivity.class);
                if (manageActivity != null && manageActivity.mViewPager != null)
                    manageActivity.mViewPager.setCurrentItem(4, false);
                finish();
                break;
        }
    }

    public void startPage(){
        Intent intent=new Intent(this,TfAdvertisementManageActivity.class);
        startActivity(intent);
        CreateTfAdvManageActivity createTfAdvManageActivity = ActivityManage.getActivity(CreateTfAdvManageActivity.class);
        if (createTfAdvManageActivity != null){
            createTfAdvManageActivity.finish();
        }
        finish();
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.adv_order_pay_details_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("支付详情");
        AvdOrder   avdOrder= (AvdOrder) getIntent().getSerializableExtra("avdOrder");
        tv_money.setText(avdOrder.getData().getPayCount()+"元");
        tv_orderNo.setText(avdOrder.getData().getOrderCode());
    }

}
