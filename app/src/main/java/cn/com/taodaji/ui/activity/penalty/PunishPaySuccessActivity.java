package cn.com.taodaji.ui.activity.penalty;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.activity.ActivityManage;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class PunishPaySuccessActivity extends DataBaseActivity {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_orderNo)
    TextView tv_orderNo;
    @BindView(R.id.post_ok)
    Button post_ok;
    @OnClick({R.id.btn_back,R.id.post_ok})
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                starPage();
                break;
            case R.id.post_ok:
                starPage();
                break;
        }
    }

    public void starPage(){
        ActivityManage.setTopActivity(PunishListActivity.class);
        PunishListActivity punishListActivity = ActivityManage.getActivity(PunishListActivity.class);
        if (punishListActivity != null && punishListActivity.viewPager != null)
            punishListActivity.viewPager.setCurrentItem(1, false);
        finish();
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.punish_pay_success_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this,this.getResources().getColor(R.color.blue_2898eb));
        tv_money.setText("收款金额："+getIntent().getStringExtra("payCount")+"元");
        tv_orderNo.setText("收款编号："+getIntent().getStringExtra("orderId"));
    }
}
