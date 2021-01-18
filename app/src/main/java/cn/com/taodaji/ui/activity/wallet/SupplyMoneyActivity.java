package cn.com.taodaji.ui.activity.wallet;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.RefreshPickupFee;
import cn.com.taodaji.model.entity.SupplyMoney;
import cn.com.taodaji.model.event.WithDrawalsEvent;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseMoneyListActivity;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SupplyMoneyActivity extends SimpleToolbarActivity implements View.OnClickListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("供货款");
        right_textView.setText("货款明细");
        title_right.setOnClickListener(this);
    }

    private TextView withdrawalMoney, freezeMoney, supplier_money,tqb;
    private SupplyMoney supplyMoney;
    private MyselftUpdateP.DataBean purMoney;
    private View my_bankCard, title;

    TextView titleHelp;
    TextView moneyText;
    LinearLayout purchaser_hide;
    LinearLayout tx_Ok,ll_balance;
    TextView tx_text;
    TextView withdrawalMoneyText;
    TextView freezeMoneyText;

    Button btn_add_cash;

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_myself_supply_money);
        body_other.addView(mainView);


        titleHelp = ViewUtils.findViewById(mainView, R.id.title_help);
        moneyText = ViewUtils.findViewById(mainView, R.id.money_text);
        purchaser_hide = ViewUtils.findViewById(mainView, R.id.purchaser_hide);
        tx_Ok = ViewUtils.findViewById(mainView, R.id.tx_Ok);
        tx_text = ViewUtils.findViewById(mainView, R.id.tx_text);
        btn_add_cash = ViewUtils.findViewById(mainView, R.id.btn_add_cash);
        withdrawalMoneyText = ViewUtils.findViewById(mainView, R.id.withdrawalMoney_text);
        freezeMoneyText = ViewUtils.findViewById(mainView, R.id.freezeMoney_text);
        tqb = ViewUtils.findViewById(mainView, R.id.tx_tqb);
        ll_balance = ViewUtils.findViewById(mainView,R.id.tx_balance);


        my_bankCard = ViewUtils.findViewById(mainView, R.id.my_bankCard);
        my_bankCard.setOnClickListener(this);
        title = ViewUtils.findViewById(mainView, R.id.title);

        ViewUtils.findViewById(mainView, R.id.tx_Ok).setOnClickListener(this);
        // ViewUtils.findViewById(mainView, R.id.btn_txcz).setOnClickListener(this);
        // ViewUtils.findViewById(mainView, R.id.btn_txye).setOnClickListener(this);
        title_right.setOnClickListener(this);

        btn_add_cash.setOnClickListener(this);

        withdrawalMoney = ViewUtils.findViewById(mainView, R.id.withdrawalMoney);
        freezeMoney = ViewUtils.findViewById(mainView, R.id.freezeMoney);
        supplier_money = ViewUtils.findViewById(mainView, R.id.supplier_money);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PublicCache.loginPurchase != null) {
            titleHelp.setVisibility(View.GONE);
            my_bankCard.setVisibility(View.GONE);
            btn_add_cash.setVisibility(View.VISIBLE);
            ll_balance.setVisibility(View.GONE);
            title.setBackgroundColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
            setTitle("我的钱包");
            right_textView.setText("余额明细");
            moneyText.setText("我的钱包总额（元）");
            withdrawalMoneyText.setText("充值余额");
            freezeMoneyText.setText("售后余额");
            //tx_text.setText("充值");
            tx_Ok.setVisibility(View.GONE);


            supplier_money.setText(String.valueOf(PublicCache.loginPurchase.getMoney()));
                 //flag //0-刷新门店信息 1-获取当前登陆用户信息
            getRequestPresenter().customer_refreshInfo(PublicCache.loginPurchase.getEntityId(),0,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<MyselftUpdateP>(this) {

                @Override
                public void onSuc(MyselftUpdateP body) {
                    purMoney = body.getData();
                    freezeMoney.setText(purMoney.getRefundMoney().toString() + "元");
                    withdrawalMoney.setText(purMoney.getRechargeMoney().toString() + "元");
                    supplier_money.setText(purMoney.getMoney().toString());
                }

                @Override
                public void onFailed(int myselftUpdateP, String msg) {

                }
            });

        } else if (PublicCache.loginSupplier != null) {
            getRequestPresenter().getSupplyMoney(PublicCache.loginSupplier.getEntityId(), new ResultInfoCallback<SupplyMoney>(this) {
                @Override
                public void onSuccess(SupplyMoney event) {
                    supplyMoney = event;
                    freezeMoney.setText(event.getFreezeMoney().toString() + "元");
                    withdrawalMoney.setText(event.getWithdrawalMoney().toString() + "元");
                    supplier_money.setText(event.getWithdrawalMoney().add(event.getFreezeMoney()).toString());
                    tqb.setText(PublicCache.loginSupplier.getReceiveMoney()+"");
                }

                @Override
                public void onFailed(int supplyMoneyResultInfo, String msg) {

                }
            });


            RequestPresenter2.getInstance().refreshPickupFee(PublicCache.loginSupplier.getStore(), new RequestCallback<RefreshPickupFee>() {
                @Override
                protected void onSuc(RefreshPickupFee body) {
                    tqb.setText(body.getData().getReceive_money() + "");
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    UIUtils.showToastSafe(msg);
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SupplyMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_bankCard:
                // if (PublicCache.loginSupplier != null)
                MyBankCardListActivity.startActivity(this, false);
                break;
            case R.id.title_right:

                if (PublicCache.loginPurchase != null) {
                    //余额明细
                    PurchaseMoneyListActivity.startActivity(this, 2);
                } else if (PublicCache.loginSupplier != null)
                    //SupplyMoneyListActivity.startActivity(this);
                    SupplyMoneyNewListActivity.startActivity(this);

                break;
            case R.id.tx_Ok:
                if (supplyMoney != null && PublicCache.loginSupplier != null)
                    //-1,供应商提现
                    WithDrawalsActivity.startActivity(this, new WithDrawalsEvent(-1, supplyMoney.getWithdrawalMoney()));
                    //采购商为充值

                break;


            case R.id.btn_add_cash:
                if (PublicCache.loginPurchase != null) {
                    RechargeMoneyActivity.startActivity(this);
                }
                break;
/*            case R.id.btn_txcz:
                //0.采购商充值提现
                if (purMoney != null)
                    WithDrawalsActivity.startActivity(this, new WithDrawalsEvent(2, purMoney.getRechargeMoney()));
                break;
            case R.id.btn_txye:
                //0.采购商余额提现
                if (purMoney != null)
                    WithDrawalsActivity.startActivity(this, new WithDrawalsEvent(1, purMoney.getRefundMoney()));
                break;*/
        }

    }

}
