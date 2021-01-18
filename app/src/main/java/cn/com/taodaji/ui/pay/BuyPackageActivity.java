package cn.com.taodaji.ui.pay;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.BuyPackageFee;
import cn.com.taodaji.model.entity.PickupPackage;
import cn.com.taodaji.model.entity.PickupPackageItem;
import cn.com.taodaji.model.entity.WXPay;
import cn.com.taodaji.model.event.BuyPackageEvent;
import cn.com.taodaji.model.event.PaySuccessEvent;
import cn.com.taodaji.model.entity.RefreshPickupFee;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.pay.alipay.Alipay;
import cn.com.taodaji.ui.pay.wxapppay.Wxpay;
import cn.com.taodaji.viewModel.adapter.PickupPackageAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.jni.JniMethod;

public class BuyPackageActivity extends SimpleToolbarActivity {

    private View mainView;
    private RecyclerView recyclerView;
    private List<PickupPackageItem> itemList = new ArrayList<>();
    private PickupPackageAdapter adapter;
    private CheckBox auto,aliPay,wechatPay,restPay;
    private TextView restFee,totalPrice,confirm;
    private int type ,itemPosition=0;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("充值淘钱包");
    }

    protected void initMainView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_buy_package);
        body_other.addView(mainView);

        auto = mainView.findViewById(R.id.cb_auto_renew);
        if (PublicCache.loginSupplier.getIsAutomaticRenewal()==1){
            auto.setChecked(true);
        }else {
            auto.setChecked(false);
        }
        aliPay = mainView.findViewById(R.id.cb_ali_pay);
        wechatPay = mainView.findViewById(R.id.cb_wechat_pay);
        restPay = mainView.findViewById(R.id.cb_balance_pay);
        restFee = mainView.findViewById(R.id.tv_rest_fee);
        restFee.setText("可用余额  "+PublicCache.loginSupplier.getWithdrawalMoney()+"元");
        aliPay.setChecked(true);

        totalPrice = mainView.findViewById(R.id.tv_total_price);
        confirm = mainView.findViewById(R.id.tv_confirm_pay);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aliPay.isChecked()){
                    type = 1;
                }else if (wechatPay.isChecked()){
                    type =2;
                }else if (restPay.isChecked()){
                    type=3;
                }
                confirmPay(type);
            }
        });

        recyclerView = mainView.findViewById(R.id.rv_pickup_package);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        adapter = new PickupPackageAdapter(itemList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                adapter.setSelection(position);
                EventBus.getDefault().post(new BuyPackageEvent(itemList.get(position).getDiscountAmount()));
                itemPosition = position;
            }
        });
        recyclerView.setAdapter(adapter);

        aliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wechatPay.setChecked(false);
                restPay.setChecked(false);
                if (b){
                    aliPay.setChecked(true);
                }
            }
        });

        wechatPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                aliPay.setChecked(false);
                restPay.setChecked(false);
                if (b){
                    wechatPay.setChecked(true);
                }
            }
        });

        restPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wechatPay.setChecked(false);
                aliPay.setChecked(false);
                if (b){
                    restPay.setChecked(true);
                }
            }
        });
    }

    @Override
    protected void initData() {
        itemList.clear();
        RequestPresenter2.getInstance().getPickupPackageList(new RequestCallback<PickupPackage>() {
            @Override
            protected void onSuc(PickupPackage body) {
                if (body.getData().getItems().size()!=0){
                    for (PickupPackage.DataBean.ItemsBean bean : body.getData().getItems()){
                        PickupPackageItem item = new PickupPackageItem();
                        item.setCreateTime(bean.getCreateTime());
                        item.setDiscountAmount(bean.getDiscountAmount());
                        item.setEntityId(bean.getEntityId());
                        item.setPackageTitle(bean.getPackageTitle());
                        item.setRechargeAmount(bean.getRechargeAmount());
                        item.setRemarks(bean.getRemarks());
                        item.setSite(bean.getWebsiteId());
                        item.setUpdateTime(bean.getUpdateTime());
                        itemList.add(item);
                    }
                    adapter.setSelection(0);
                    totalPrice.setText("总计￥ "+itemList.get(0).getDiscountAmount()+"元");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void confirmPay(int type){
        PickupPackageItem item = itemList.get(itemPosition);
        RequestPresenter.getInstance().saveAndPay(item.getEntityId(), PublicCache.loginSupplier.getStore(), type, item.getDiscountAmount(), item.getRechargeAmount(), auto.isChecked() ? 1 : 0, PublicCache.loginSupplier.getAutomaticRenewalFee(), PublicCache.loginSupplier.getStoreName(), PublicCache.loginSupplier.getPhoneNumber(), new RequestCallback<BuyPackageFee>() {
            @Override
            protected void onSuc(BuyPackageFee body) {
                switch (type){
                    case 1://支付宝
                        new Alipay(BuyPackageActivity.this, "淘大集供应链", "淘大集供应链", body.getData().getPayMoney(),body.getData().getOutTradeNo(), PublicCache.getROOT_URL().get(0)+body.getData().getNotify_url()).pay();
                        break;
                    case 2://微信
                        WXPay wxpay=new WXPay();
                        wxpay.setAppid(body.getData().getAppid());
                        wxpay.setNoncestr(body.getData().getNoncestr());
                        wxpay.setPackageX(body.getData().getPackageX());
                        wxpay.setPartnerid(body.getData().getPartnerid());
                        wxpay.setPrepayid(body.getData().getPrepayid());
                        wxpay.setSign(body.getData().getSign());
                        wxpay.setTimestamp(body.getData().getTimestamp());
                        new Wxpay(BuyPackageActivity.this).pay(wxpay);
                        break;
                    case 3://余额
                        UIUtils.showToastSafe("支付成功");
                        finish();
                        break;
                    default:break;
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    //接收支付成功通知
    @Subscribe
    public void onEvent(PaySuccessEvent event) {
        finish();
    }

    @Subscribe
    public void onEvent(BuyPackageEvent event) {
        totalPrice.setText("总计￥ "+event.getPrice()+"元");
    }
}
