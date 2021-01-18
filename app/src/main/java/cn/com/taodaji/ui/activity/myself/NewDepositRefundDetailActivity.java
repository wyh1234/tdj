package cn.com.taodaji.ui.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AdvMoneyDetails;
import cn.com.taodaji.model.entity.FindByCustomerRecordId;
import cn.com.taodaji.model.entity.PackageForegift;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.purchaseBill.DepositRefundDetailActivity;
import tools.activity.SimpleToolbarActivity;

public class NewDepositRefundDetailActivity extends SimpleToolbarActivity {
    private TextView tv_payContext,tv_time,tv_bottom,payContext,tv_date,order_num,tv_desc,paymentAmount;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("明细详情");
    }

    private View view;

    @Override
    protected void initMainView() {
        view = getLayoutView(R.layout.activity_new_deposit_refund_detail);
        body_other.addView(view);
        tv_payContext=view.findViewById(R.id.tv_payContext);
        tv_bottom=view.findViewById(R.id.tv_bottom);
        tv_time=view.findViewById(R.id.tv_time);
        payContext=view.findViewById(R.id.payContext);
        tv_date=view.findViewById(R.id.tv_date);
        order_num=view.findViewById(R.id.order_num);
        tv_desc=view.findViewById(R.id.tv_desc);
        paymentAmount=view.findViewById(R.id.paymentAmount);
        if (getIntent().getStringExtra("transactionNo").startsWith("gg_tk_")){
            tv_time.setText("退还时间：");
            tv_bottom.setText("购买时间：");

        }else if (getIntent().getStringExtra("transactionNo").startsWith("gg_")){
            tv_time.setText("投放时间：");
            tv_bottom.setText("付款时间：");
        }else {
            tv_time.setText("罚款时间：");
            tv_bottom.setText("罚款事由：");
        }

    }

    @Override
    protected void initData() {
        super.initData();

        Map<String, Object> map = new HashMap<>();
        map.put("transactionNo", getIntent().getStringExtra("transactionNo"));
        map.put("orderId", getIntent().getIntExtra("orderId",0));
        ShowLoadingDialog.showLoadingDialog(this);
        getRequestPresenter().advertisementOrFine(map, new RequestCallback<AdvMoneyDetails>() {
            @Override
            protected void onSuc(AdvMoneyDetails body) {
                ShowLoadingDialog.close();
                if (getIntent().getStringExtra("transactionNo").startsWith("gg_tk_")){
                    paymentAmount.setText("+"+body.getData().getMoney()+"");
                    payContext.setText("退还广告费("+"后台操作"+")");
                    tv_payContext.setText("退还广告费");
                    tv_date.setText(getIntent().getStringExtra("ctime"));
                    order_num.setText(body.getData().getAdvertisementPlanCode());
                    tv_desc.setText(body.getData().getCreateTime());
                }else if (getIntent().getStringExtra("transactionNo").startsWith("gg_")){


                    paymentAmount.setText("-"+body.getData().getPayCount()+"");
                    payContext.setText("广告费("+body.getData().getAdvertisementTypeName()+")");
                    tv_payContext.setText("缴纳广告费");
                    tv_date.setText(body.getData().getPutTime());
                    order_num.setText(body.getData().getOrderCode());
                    tv_desc.setText(body.getData().getCreateTime());
                }else {
                    paymentAmount.setText("-"+body.getData().getInfo().getMoney()+"");
                    payContext.setText("罚款("+body.getData().getInfo().getItem()+")");
                    tv_payContext.setText("缴纳罚款");
                    tv_date.setText(body.getData().getInfo().getCreateTime());
                    order_num.setText(body.getData().getInfo().getOrderNo());
                    tv_desc.setText(body.getData().getInfo().getItem());
                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });





    }

}
