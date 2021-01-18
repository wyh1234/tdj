package cn.com.taodaji.ui.activity.integral;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.IntegralAliPay;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.IntegralWXPay;
import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.ui.activity.integral.popuwindow.DelShopPopuWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.PaySuccessPopuWindow;
import cn.com.taodaji.ui.activity.integral.tools.CurrentItem;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.myself.MyEquitiesActivity;
import cn.com.taodaji.ui.pay.YearMoneyPayActivity;
import cn.com.taodaji.ui.pay.alipay.PayResult;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class IntegralShopPayActivity extends DataBaseActivity implements PaySuccessPopuWindow.PaySuccessPopuWindowListene {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.zhifubao_rl)
    RelativeLayout zhifubao_rl;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixin_rl;
    @BindView(R.id.sel_iv1)
    ImageView sel_iv1;
    @BindView(R.id.sel_iv)
    ImageView sel_iv;
    @BindView(R.id.tv_money)
    TextView tv_money;
    private IntegralOrder.IntegralOrderData data;
    private PaySuccessPopuWindow paySuccessPopuWindow;

   @OnClick({R.id.btn,R.id.zhifubao_rl,R.id.weixin_rl,R.id.btn_back})
   public void onClick(View view){
       switch (view.getId()){
           case R.id.btn:
               if (!sel_iv1.isSelected()&&!sel_iv.isSelected()){
                   UIUtils.showToastSafe("请选择支付方式");
                   return;
               }
               if (data!=null)
               if (sel_iv.isSelected()){
                   order_wx_pay(data.getOrderId());
               }else if (sel_iv1.isSelected()){
                   order_ali_pay(data.getOrderId());
               }


               break;

           case R.id.weixin_rl:
               sel_iv1.setSelected(false);
               sel_iv.setSelected(true);
               btn.setText("微信支付￥"+data.getTotalFee());
               break;

           case R.id.zhifubao_rl:
               btn.setText("支付宝支付￥"+data.getTotalFee());
               sel_iv1.setSelected(true);
               sel_iv.setSelected(false);
               break;
           case R.id.btn_back:
               //待付款
               if (getIntent().getStringExtra("BuyIntegral")!=null){
                   Intent intent=new Intent(this, BuyIntegralActivity.class);
                   startActivity(intent);
               }else {
                   Intent intent=new Intent(this, WebViewActivity.class);
                   intent.putExtra("url",  PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?" +
                           "userId="+(PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=0");
                   startActivity(intent);
               }

               finish();
               break;
       }
   }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.i(payResult.getResultStatus());
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new PaySuccessEvent());

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        UIUtils.showToastSafe("支付失败");
//                        showPaySuccess();
                    }
                    break;
                }

            }
        }
    };

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.integral_shop_pay_layout);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        if (getIntent().getStringExtra("BuyIntegral")!=null){
            tv_title.setText("积分购买");
        }else {
            tv_title.setText("在线付款");
        }

        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        data = (IntegralOrder.IntegralOrderData) getIntent().getSerializableExtra("IntegralOrder");
        tv_money.setText(data.getTotalFee());
        btn.setText("支付￥"+data.getTotalFee());
    }

    public void order_wx_pay(String orderId){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", orderId);
        getIntegralRequestPresenter().order_wx_pay(map, new RequestCallback<IntegralWXPay>(this) {
            @Override
            public void onSuc(IntegralWXPay body) {
                ShowLoadingDialog.close();
                WXPay wxpay=new WXPay();
                wxpay.setAppid(body.getData().getAppid());
                wxpay.setNoncestr(body.getData().getNoncestr());
                wxpay.setPackageX("Sign=WXPay");
                wxpay.setPartnerid(body.getData().getPartnerid());
                wxpay.setPrepayid(body.getData().getPrepayid());
                wxpay.setSign(body.getData().getSign());
                wxpay.setTimestamp(body.getData().getTimestamp());
                LogUtils.i(wxpay);
                new Wxpay(IntegralShopPayActivity.this).pay(wxpay);
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });

    }

    public void order_ali_pay(String orderId){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", orderId);
        getIntegralRequestPresenter().order_ali_pay(map, new RequestCallback<IntegralAliPay>(this) {
            @Override
            public void onSuc(IntegralAliPay body) {
                ShowLoadingDialog.close();
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(IntegralShopPayActivity.this);
                        Map<String, String> result = alipay.payV2(body.getData(), true);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

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
        if (sel_iv1.isSelected()){
            ali_orderQuery();

        }else if (sel_iv.isSelected()){
            wx_orderQuery();
        }

    }

    public void  wx_orderQuery(){
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", data.getOrderId());
        getIntegralRequestPresenter().orderQuery(map, new RequestCallback<IntegralOrder>(this) {
            @Override
            public void onSuc(IntegralOrder body) {
                if (body.getData().getTrade_status().equals("SUCCESS")){
                    if (getIntent().getStringExtra("BuyIntegral")!=null){
                        data.setPayWay("微信付款");
                        showPaySuccess();
                    }else {
                        Intent intent=new Intent(IntegralShopPayActivity.this,IntegralShopPaySuccessActivity.class);
                        intent.putExtra("orderId",data.getOrderId());
                        intent.putExtra("total_amount",data.getTotalFee());
                        startActivity(intent);
                        finish();
                    }

                }else {
                    UIUtils.showToastSafe(body.getData().getSub_msg());
                }




            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                UIUtils.showToastSafe(msg);

            }
        });
    }


    public void  ali_orderQuery(){
        Map<String,Object> map=new HashMap<>();
        map.put("orderId", data.getOrderId());
        getIntegralRequestPresenter().aliorderQuery(map, new RequestCallback<IntegralOrder>(this) {
            @Override
            public void onSuc(IntegralOrder body) {
                if (body.getData().getTrade_status().equals("TRADE_SUCCESS")){
                    if (getIntent().getStringExtra("BuyIntegral")!=null){
                        data.setPayWay("支付宝付款");
                        showPaySuccess();

                    }else {
                        Intent intent=new Intent(IntegralShopPayActivity.this,IntegralShopPaySuccessActivity.class);
                        intent.putExtra("orderId",data.getOrderId());
                        intent.putExtra("total_amount",data.getTotalFee());
                        startActivity(intent);
                        finish();
                    }

                }else {
                    UIUtils.showToastSafe(body.getData().getTrade_state_desc());
                }




            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                UIUtils.showToastSafe(msg);

            }
        });
    }

    public void  showPaySuccess(){

        if (paySuccessPopuWindow!=null){
            if (paySuccessPopuWindow.isShowing()){
                return;
            }

        }
        paySuccessPopuWindow = new PaySuccessPopuWindow(this,data);
        paySuccessPopuWindow.setPaySuccessPopuWindowListene(this);
        paySuccessPopuWindow.setDismissWhenTouchOutside(false);
        paySuccessPopuWindow.setInterceptTouchEvent(false);
        paySuccessPopuWindow.setPopupWindowFullScreen(true);//铺满
        paySuccessPopuWindow.showPopupWindow();
    }


    @Override
    public void back_my() {
        ActivityManage.setTopActivity(IntegralShopMainActivity.class);
        CurrentItem currentItem  = new CurrentItem();
        currentItem.setPos(2);
        EventBus.getDefault().post(currentItem);
        paySuccessPopuWindow.dismiss();
        finish();
    }

    @Override
    public void back_cart() {
        ActivityManage.setTopActivity(IntegralShopMainActivity.class);
        EventBus.getDefault().post(new CurrentItem());
        paySuccessPopuWindow.dismiss();
        finish();
    }
}
