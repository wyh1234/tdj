package cn.com.taodaji.ui.pay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.event.HomepageEvent;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.PovertyAlleviationActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import tools.activity.SimpleToolbarActivity;

public class PayCompleteActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private Button viewOrder,backHomepage;
    private ImageView actionAds;
    private TextView orderPrice;
    private FindByIsActive.ListBean bean = new FindByIsActive.ListBean();
    private LinearLayout layout;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("支付成功");
        right_textView.setText("完成");
        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.color.orange_yellow_ff7d01);
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutView(this, R.layout.activity_pay_complete);
        body_scroll.addView(mainView);

        viewOrder = mainView.findViewById(R.id.btn_view_order);
        backHomepage = mainView.findViewById(R.id.btn_back_homepage);
        viewOrder.setOnClickListener(this);
        backHomepage.setOnClickListener(this);

        orderPrice = mainView.findViewById(R.id.tv_order_price);
        orderPrice.setText(getIntent().getStringExtra("money")+"元");
        layout = mainView.findViewById(R.id.ll_pay_ads);

        actionAds = mainView.findViewById(R.id.iv_action_ads);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_view_order:
                Intent intent = new Intent(PayCompleteActivity.this, OrderListActivity.class);
                EventBus.getDefault().postSticky(new OrderEvent(2));
                startActivity(intent);
                finish();
                break;
            case R.id.btn_back_homepage:
                Intent intent1 = new Intent(PayCompleteActivity.this, ManageActivity.class);
                EventBus.getDefault().postSticky(new HomepageEvent(0));
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_action_ads:
                if (PublicCache.loginPurchase==null){
                    return;
                }
                Intent intent2 = new Intent(PayCompleteActivity.this, SpecialActivitiesActivity.class);
                intent2.putExtra("url", bean.getH5Url()+"&customerId="+PublicCache.loginPurchase.getLoginUserId()+"&subCustomerId="+PublicCache.loginPurchase.getSubUserId());
                startActivity(intent2);
                finish();
                break;
                default:break;
        }
    }

    @Override
    protected void initData() {
        for (int i=0;i<PublicCache.findByIsActive.getList().size();i++){
            if (PublicCache.findByIsActive.getList().get(i).getId()==PublicCache.site_login){
                bean = PublicCache.findByIsActive.getList().get(i);
            }
        }
        if (bean.getH5Open()==1){
            layout.setVisibility(View.VISIBLE);
            actionAds.setOnClickListener(this);
            Glide.with(actionAds).load(bean.getH5Img()).into(actionAds);
        }
    }
}
