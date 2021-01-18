package cn.com.taodaji.ui.pay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplierAnnalFeeInfo;
import cn.com.taodaji.model.entity.SupplierAnnalFeePayInfo;
import cn.com.taodaji.model.entity.SupplyMoney;
import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.myself.MyEquitiesActivity;
import cn.com.taodaji.ui.activity.myself.MySelfSupYearMoney;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearPayWayAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.jni.JniMethod;

public class YearMoneyPayActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private RelativeLayout zhifubao_rl,weixin_rl,y_e_rl;
    private ImageView sel_iv1,sel_iv,sel_iv_y_e;
    private Button btn;
    private int payType;
    private TextView tv_money,tv_y_e;
    private BigDecimal withdrawalMoney;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.statusBarColor);
        setToolBarColor(R.color.statusBarColor);
        simple_title.setText("支付收银台");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.year_money_pay_layout);
        body_other.addView(mainView);
        zhifubao_rl=ViewUtils.findViewById(mainView,R.id.zhifubao_rl);
        zhifubao_rl.setOnClickListener(this);
        weixin_rl=ViewUtils.findViewById(mainView,R.id.weixin_rl);
        weixin_rl.setOnClickListener(this);
        sel_iv1=ViewUtils.findViewById(mainView,R.id.sel_iv1);
        sel_iv=ViewUtils.findViewById(mainView,R.id.sel_iv);
        btn=ViewUtils.findViewById(mainView,R.id.btn);
        tv_money=ViewUtils.findViewById(mainView, R.id.tv_money);
        y_e_rl=ViewUtils.findViewById(mainView, R.id.y_e_rl);
        sel_iv_y_e=ViewUtils.findViewById(mainView, R.id.sel_iv_y_e);
        tv_y_e=ViewUtils.findViewById(mainView, R.id.tv_y_e);
        btn.setOnClickListener(this);
        y_e_rl.setOnClickListener(this);

        tv_money.setText("￥"+getIntent().getStringExtra("payCount"));
    }

    //设置监听事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = View.inflate(this, R.layout.year_money_pay_dialog, null);

                builder.setView(view);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();
                view.findViewById(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                view.findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        getSupplyMoney();
    }

    public void getSupplyMoney(){
        getRequestPresenter().getSupplyMoney(PublicCache.loginSupplier.getEntityId(), new ResultInfoCallback<SupplyMoney>(this) {
            @Override
            public void onSuccess(SupplyMoney event) {
                tv_y_e.setText("余额抵扣（可用余额:"+event.getWithdrawalMoney().toString() + "元)");
                withdrawalMoney=event.getWithdrawalMoney();
            }

            @Override
            public void onFailed(int supplyMoneyResultInfo, String msg) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        LogUtils.i("event");
        UIUtils.showToastSafe("支付成功");
        Intent intent=new Intent(this, MyEquitiesActivity.class);
        intent.putExtra("type",getIntent().getStringExtra("type"));
        startActivity(intent);
//        setResult(RESULT_OK);
        finish();
    }
    private void getSupplierAnnalFeePay(Map<String,Object> map) {
        ShowLoadingDialog.showLoadingDialog(this);
        getRequestPresenter().adv_fee_pay(map,new RequestCallback<SupplierAnnalFeePayInfo>() {
            @Override
            public void onSuc(SupplierAnnalFeePayInfo body) {
                ShowLoadingDialog.close();
                if (getPayType()==2){
                    LogUtils.i(getPayType());
                    WXPay wxpay=new WXPay();
                    wxpay.setAppid(body.getData().getAppid());
                    wxpay.setNoncestr(body.getData().getNoncestr());
                    wxpay.setPackageX(body.getData().getPackageX());
                    wxpay.setPartnerid(body.getData().getPartnerid());
                    wxpay.setPrepayid(body.getData().getPrepayid());
                    wxpay.setSign(body.getData().getSign());
                    wxpay.setTimestamp(body.getData().getTimestamp());
                    LogUtils.i(wxpay);
                    new Wxpay(YearMoneyPayActivity.this).pay(wxpay);
                }else if (sel_iv1.isSelected()){
                    LogUtils.i(getIntent().getStringExtra("payCount"));
                    new Alipay(getBaseActivity(), "年费-淘大集供应链", "年费",
                            getIntent().getStringExtra("payCount"), body.getData().getOutTradeNo(), PublicCache.getROOT_URL().get(0) + JniMethod.supplierAnnalFee_alipay()).newPay();

                }else {
                    UIUtils.showToastSafe("余额支付成功");
                    Intent intent=new Intent(YearMoneyPayActivity.this, MyEquitiesActivity.class);
                    intent.putExtra("type",getIntent().getStringExtra("type"));
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zhifubao_rl:
                sel_iv1.setSelected(true);
                sel_iv.setSelected(false);
                sel_iv_y_e.setSelected(false);
                setPayType(1);

                break;
            case R.id.weixin_rl:
                sel_iv1.setSelected(false);
                sel_iv.setSelected(true);
                sel_iv_y_e.setSelected(false);
                setPayType(2);
                break;
            case  R.id.btn:
                String type=getIntent().getStringExtra("type");
                LogUtils.i(type);
                Map<String,Object> map=new HashMap<>();
                if (getPayType()==0){
                    Toast.makeText(this,"请选择支付方式",Toast.LENGTH_LONG).show();
                    return;
                }
                if (sel_iv_y_e.isSelected()){
                    if (withdrawalMoney!=null){
                        if (withdrawalMoney.compareTo(new BigDecimal(getIntent().getStringExtra("payCount")))==-1){
                            Toast.makeText(this,"账户余额不足",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                }


                map.put("storeType",((type.equals("ZM"))?"2":"1"));
                LogUtils.i(getPayType());
                map.put("type",11);
                map.put("payType",getPayType());

                map.put("platform","0");
                map.put("payCount",getIntent().getStringExtra("payCount"));
                map.put("storeId",getIntent().getStringExtra("storeId"));
                map.put("openCycle",getIntent().getStringExtra("openCycle"));
                getSupplierAnnalFeePay(map);
                break;
            case R.id.y_e_rl:
                sel_iv1.setSelected(false);
                sel_iv.setSelected(false);
                sel_iv_y_e.setSelected(true);
                setPayType(3);
                break;
        }

    }
}
