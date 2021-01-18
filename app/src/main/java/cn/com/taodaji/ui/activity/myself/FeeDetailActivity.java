package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class FeeDetailActivity extends SimpleToolbarActivity {

    TextView fee,price,payType,receiveDate,pickupDate,address;
    LinearLayout next;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("接货详情");
    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_fee_detial);
        body_other.addView(view);

        fee = view.findViewById(R.id.tv_item_fee);
        price = view.findViewById(R.id.tv_item_price);
        payType = view.findViewById(R.id.tv_pay_type);
        receiveDate = view.findViewById(R.id.tv_receive_date);
        pickupDate = view.findViewById(R.id.tv_pickup_date);
        address = view.findViewById(R.id.tv_pickup_address);
        next = view.findViewById(R.id.ll_order_detail);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeeDetailActivity.this,PickupOrderDetailActivity.class);
                intent.putExtra("receiveType",getIntent().getIntExtra("receiveType",0));
                intent.putExtra("feePercent", getIntent().getStringExtra("feePercent"));
                intent.putExtra("expectDeliveredDate",getIntent().getStringExtra("expectDeliveredDate"));
                intent.putExtra("receiveStationId",getIntent().getIntExtra("receiveStationId",0));
                intent.putExtra("payMoney",getIntent().getStringExtra("payMoney"));
                intent.putExtra("orderTotalFee",getIntent().getStringExtra("orderTotalFee"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });

        fee.setText("-"+getIntent().getStringExtra("payMoney"));
        price.setText(getIntent().getStringExtra("orderTotalFee")+"元");
        if (getIntent().getIntExtra("receiveType",2)==1){
            payType.setText("收费方式：按接货金额的"+getIntent().getStringExtra("feePercent"));
        }else {
            payType.setText("收费方式：按每日"+getIntent().getStringExtra("payMoney")+"元");
        }
        receiveDate.setText("送达时间："+getIntent().getStringExtra("expectDeliveredDate"));
        pickupDate.setText("接货时间："+getIntent().getStringExtra("receiveTime"));
        address.setText("接货仓："+getIntent().getStringExtra("address"));
    }
}
