package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.pay.PurchaserOrderConfirmaActivity;
import tools.activity.SimpleToolbarActivity;

public class SubmitOrderConfirmationActivity extends SimpleToolbarActivity implements View.OnClickListener{
    View  mainView;
    Button btn_pay,btn_order;
    TextView tv_orderNo,tv_money;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setTitle("提交订单确认");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutView(this, R.layout.submit_order_confirm_layout);
        body_scroll.addView(mainView);
        btn_pay=ViewUtils.findViewById(mainView,R.id.btn_pay);
        btn_order=ViewUtils.findViewById(mainView,R.id.btn_order);
        tv_orderNo=ViewUtils.findViewById(mainView,R.id.tv_orderNo);
        tv_money=ViewUtils.findViewById(mainView,R.id.tv_money);
        btn_pay.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        tv_orderNo.setText("订单编号："+getIntent().getStringExtra("Order_no"));
        tv_money.setText(""+getIntent().getStringExtra("TotalPrice"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pay:
                Intent intent1 = new Intent(this, PurchaserOrderConfirmaActivity.class);
                intent1.putExtra("CreateTime", getIntent().getLongExtra("CreateTime",-1000));
                intent1.putExtra("OrderIds", getIntent().getStringExtra("OrderIds"));
                intent1.putExtra("OutTradeNo", getIntent().getStringExtra("OutTradeNo"));
                intent1.putExtra("Order_no",  getIntent().getStringExtra("Order_no"));
                intent1.putExtra("TotalPrice", getIntent().getStringExtra("TotalPrice"));
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_order:
                Intent intent = new Intent(this, OrderListActivity.class);
                EventBus.getDefault().postSticky(new OrderEvent(1));
                startActivity(intent);
                finish();
                break;
        }

    }
}
