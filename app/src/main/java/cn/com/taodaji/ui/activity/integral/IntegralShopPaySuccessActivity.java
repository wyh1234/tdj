package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AgainOrder;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.ui.activity.integral.tools.CurrentItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class IntegralShopPaySuccessActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_no)
    TextView tv_no;
    @BindView(R.id.tv_money)
    TextView tv_money;

    @OnClick({R.id.btn,R.id.btn_back})
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.btn:
//                Intent intent=new Intent(this,BuyIntegralActivity.class);
//                startActivity(intent);
                order_again();
                break;
            case R.id.btn_back:
                //已付款
                Intent intent=new Intent(this, WebViewActivity.class);
                intent.putExtra("url",  PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?" +
                        "userId="+(PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=1");
                startActivity(intent);
                finish();
                break;
        }
    }

    public void order_again(){
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", getIntent().getStringExtra("orderId"));
        map.put("userId", PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().order_again(map, new RequestCallback<AgainOrder>(this) {
            @Override
            public void onSuc(AgainOrder body) {
                if (!ListUtils.isNullOrZeroLenght(body.getData())){
                    UIUtils.showToastSafe(body.getData());
                }
                ActivityManage.setTopActivity(IntegralShopMainActivity.class);
                 EventBus.getDefault().post(new IntegralShopCart());
                EventBus.getDefault().post(new CurrentItem());

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                UIUtils.showToastSafe(msg);

            }
        });
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.integral_shop_pay_success_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        tv_title.setText("订单完成");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);




    }

    @Override
    protected void initData() {
        super.initData();

        tv_no.setText("订单号:"+getIntent().getStringExtra("orderId"));
        if (getIntent().getStringExtra("integral")!=null){
//            btn.setVisibility(View.GONE);
            tv_money.setText(getIntent().getStringExtra("integral")+"积分");
        }else {
//            btn.setVisibility(View.VISIBLE);
            tv_money.setText("￥"+getIntent().getStringExtra("total_amount"));
        }
    }
}
