package cn.com.taodaji.ui.activity.wallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CustomerCash;
import cn.com.taodaji.model.entity.CustomerFinanceDefaultAccount;
import cn.com.taodaji.model.entity.DefaultAccount;

import cn.com.taodaji.model.entity.SupplierCash;
import cn.com.taodaji.model.entity.WithdrawFeeRule;
import cn.com.taodaji.model.event.WithDrawalsEvent;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.activity.integral.popuwindow.FriendlyPopuWindow;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationActivity;
import cn.com.taodaji.ui.ppw.WithDrawalsPopuWindow;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class WithDrawalsActivity extends SimpleToolbarActivity implements View.OnClickListener, WithDrawalsPopuWindow.WithDrawalsPopuWindowListener {

    TextView bankCardNo;
    EditText amountMoney;
    TextView amountMoneyEnable;
    Button txOk;
    TextView titleHelp;
    TextView textView3;
    TextView txHelp;
    WithDrawalsPopuWindow withDrawalsPopuWindow;
    WithdrawFeeRule mbody;


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("提现");
    }

    private BigDecimal money;
    private DefaultAccount defaultAccount;
    private CustomerFinanceDefaultAccount.DataBean defaultPurAccount;
    private int type;

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_with_drawals);
        body_scroll.addView(mainView);

        bankCardNo = ViewUtils.findViewById(mainView, R.id.bankCard_no);
        amountMoney = ViewUtils.findViewById(mainView, R.id.amount_money);
        amountMoneyEnable = ViewUtils.findViewById(mainView, R.id.amount_money_enable);
        txOk = ViewUtils.findViewById(mainView, R.id.tx_Ok);
        txOk.setOnClickListener(this);
        titleHelp = ViewUtils.findViewById(mainView, R.id.title_help);
        textView3 = ViewUtils.findViewById(mainView, R.id.textView_3);
        txHelp = ViewUtils.findViewById(mainView, R.id.tx_help);

        ViewUtils.findViewById(mainView, R.id.tx_count).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.all_pay).setOnClickListener(this);


        setViewBackColor(txOk);
        txOk.setEnabled(false);
        if (PublicCache.loginPurchase != null) titleHelp.setVisibility(View.VISIBLE);
        amountMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(".")) return;
                if (s.toString().equals("") || s.toString().equals("0")) txOk.setEnabled(false);
                else if (new BigDecimal(s.toString()).compareTo(money) > 0) {
                    amountMoney.setError("超出可提现金额");
                    txOk.setEnabled(false);
                } else txOk.setEnabled(true);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        if (PublicCache.loginSupplier != null) {
            //获取默认提现银行卡
            getRequestPresenter().getDefaultAccount(PublicCache.loginSupplier.getStore(), new ResultInfoCallback<DefaultAccount>(this) {
                @Override
                public void onSuccess(DefaultAccount event) {
                    if (event != null && !TextUtils.isEmpty(event.getAccountNo())) {

                        String ccn = event.getAccountNo().replaceAll(" ", "");
                        bankCardNo.setText(event.getBankName() + "(" + ccn.substring(ccn.length() - 4, ccn.length()) + ")");
                        defaultAccount = event;
                    } else bankCardNo.setText("点击设置提现账户");
                }

                @Override
                public void onFailed(int defaultAccountResultInfo, String msg) {
                    //   UIUtils.showToastSafesShort(msg);
                    if (bankCardNo != null) bankCardNo.setText("点击设置提现账户");
                }
            });
            getRequestPresenter().getWithdrawFeeRule(new RequestCallback<WithdrawFeeRule>() {
                @Override
                protected void onSuc(WithdrawFeeRule body) {
                    if (body.getData() != null) {
                        mbody=body;
                        txHelp.setText("提示：1、提现金额<"+body.getData().getStandard_amount()+"元，收取"+body.getData().getCollection_rate()+"%代收手续费和"+
                                body.getData().getTransfer_amount()+"元转账手续费;"+"\n"+"2、提现金额>="+body.getData().getStandard_amount()+"元，收取"+body.getData().getCollection_rate()
                                +"%代收手续费");
                    }

                }

            });
        } else if (PublicCache.loginPurchase != null) {
            getRequestPresenter().customerFinance_getDefaultAccount(PublicCache.loginPurchase.getEntityId(), new RequestCallback<CustomerFinanceDefaultAccount>(this) {
                @Override
                public void onSuc(CustomerFinanceDefaultAccount body) {
                    if (body.getData() != null) {
                        String ccn = body.getData().getAccountNo().replaceAll(" ", "");
                        if (body.getData().getBankType() == 1) {
                            bankCardNo.setText(body.getData().getBankName() + "(" + ccn + ")");
                        } else
                            bankCardNo.setText(body.getData().getBankName() + "(" + ccn.substring(ccn.length() - 4, ccn.length()) + ")");
                        defaultPurAccount = body.getData();
                    } else bankCardNo.setText("点击设置提现账户");
                }

                @Override
                public void onFailed(int customerFinanceDefaultAccount, String msg) {
                    if (bankCardNo != null) bankCardNo.setText("点击设置提现账户");
                }
            });



        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //入口参数
    @Subscribe(sticky = true)
    public void onEvent(WithDrawalsEvent event) {
        money = event.getMoney();
        type = event.getType();
        EventBus.getDefault().removeStickyEvent(money);
        amountMoneyEnable.setText(money + ",");

        //采购商提现入口已关闭
        //0.采购商充值提现
        if (event.getType() == 2) {
            textView3.setText("充值余额￥");
            txHelp.setText("提示：提现收取千分之8手续费");
            txOk.setText("7个工作日内到账，确认提现");

            //0.采购商余额提现
        } else if (event.getType() == 1) {
            textView3.setText("售后余额￥");
            txHelp.setText("提示：少于1000元，收取2元手续费。");
            txOk.setText("2个工作日内到账，确认提现");
        }
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, WithDrawalsEvent event) {
        EventBus.getDefault().postSticky(event);
        context.startActivity(new Intent(context, WithDrawalsActivity.class));
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_count:
                if (PublicCache.loginSupplier != null) {
                    if (PublicCache.loginSupplier.getIsAuth().equals("0")) {
                        UIUtils.showToastSafe("请先实名认证");
                        startActivity(new Intent(this, RealNameAuthenticationActivity.class));
                    } else {
                        MyBankCardListActivity.startActivity(this, true);
                    }
                }

                break;
            case R.id.all_pay:
                if (money.compareTo(BigDecimal.ZERO) > 0f) {
                    amountMoney.setText(money.toString());
                    txOk.setEnabled(true);
                }
                break;
            case R.id.tx_Ok:

                if (PublicCache.loginSupplier != null && PublicCache.loginSupplier.getIsAuth().equals("0")) {
                    UIUtils.showToastSafe("请先实名认证");
                    startActivity(new Intent(this, RealNameAuthenticationActivity.class));
                    return;
                }

                if (defaultAccount == null && defaultPurAccount == null) {
                    UIUtils.showToastSafesShort("请选择提现账户");
                    return;
                }

                String m = amountMoney.getText().toString();
                if (m.length() == 0) {
                    UIUtils.showToastSafesShort("请输入提现金额");
                    return;
                }
                final BigDecimal money = new BigDecimal(m);
                BigDecimal money1 = new BigDecimal("0");
                BigDecimal money2 = new BigDecimal("0");
                String messae = "";
                switch (type) {
                    case -1:
                        money1 = money.compareTo(new BigDecimal("1000")) >= 0 ? BigDecimal.ZERO : new BigDecimal("2");
                        money2 = money.subtract(money1);
                        break;
                    case 2:
                        money1 = money.multiply(new BigDecimal("0.008")).setScale(2, BigDecimal.ROUND_HALF_UP);
                        money2 = money.subtract(money1);
                        break;
                    case 1:
                        money1 = money.compareTo(new BigDecimal("1000")) >= 0 ? BigDecimal.ZERO : new BigDecimal("2");
                        money2 = money.subtract(money1);
                        break;
                }

                final BigDecimal cashMoney = money2.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : money2;


                messae = "您申请提现金额为：" + money + "元,扣除提现手续费用：" + money1 + "元，实际提现金额为：" + (cashMoney.toString()) + "元。如果您已开通接货仓服务，建议留一部分钱用于接货仓续费使用。";

                if (money.compareTo(mbody.getData().getStandard_amount())==-1){
                    if (money.subtract(money.multiply(mbody.getData().getCollection_rate()).divide(new BigDecimal(100))).
                            subtract(mbody.getData().getTransfer_amount()).setScale(2, BigDecimal.ROUND_HALF_UP)
                            .compareTo(BigDecimal.ZERO)<=0){
                        UIUtils.showToastSafesShort("可提现余额不足");
                        return;

                    }
                }


                if (withDrawalsPopuWindow!=null){
                    if (withDrawalsPopuWindow.isShowing()){
                        return;
                    }

                }
                withDrawalsPopuWindow = new WithDrawalsPopuWindow(this,mbody,money);
                withDrawalsPopuWindow.setWithDrawalsPopuWindowListener(this);
                withDrawalsPopuWindow.setDismissWhenTouchOutside(false);
                withDrawalsPopuWindow.setInterceptTouchEvent(false);
                withDrawalsPopuWindow.setPopupWindowFullScreen(true);//铺满
                withDrawalsPopuWindow.showPopupWindow();
/*                AlertDialog.Builder builder = ViewUtils.showDialog(this, "提现", messae);
                builder.setPositiveButton("继续提现", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        if (money.compareTo(BigDecimal.valueOf(2)) < 0) {
                            UIUtils.showToastSafesShort("提现失败，提现金额不可小于2元");
                            return;
                        }


                        *//**
                         * //申请提现
                         * 参数名	类型	必须(1是/0否)	说明
                         * supplierId	int	1	供应商ID
                         * provinceName	Str	1	省名称
                         * cityName	Str	1	城市名称
                         * bankName	Str	1	银行名称
                         * bankId	Str	1	银行卡号
                         * bankAddress	Str	1	开户行地址
                         * capitalWithdrawal	double	1	提现金额
                         *//*

                        Map<String, Object> map = new HashMap<>();
                        loading();
                        if (PublicCache.loginSupplier != null) {
                            map.put("supplierId", PublicCache.loginSupplier.getEntityId());
                            map.put("provinceName", defaultAccount.getProvinceName());
                            map.put("cityName", defaultAccount.getCityName());
                            map.put("bankName", defaultAccount.getBankName());
                            map.put("bankId", defaultAccount.getEntityId());
                            map.put("bankAddress", defaultAccount.getBankAddress());
                            map.put("capitalWithdrawal", money.toString());

                            getRequestPresenter().getSupplierCashWithdrwalApplication(map, new ResultInfoCallback<SupplierCash>(getBaseActivity()) {
                                @Override
                                public void onSuccess(SupplierCash body) {
                                    dialog.dismiss();
                                    loadingDimss();
                                    UIUtils.showToastSafesShort("申请已提交");
                                    finish();
                                }

                                @Override
                                public void onFailed(int supplierCashResultInfo, String msg) {
//                                    UIUtils.showToastSafesShort(msg);
                                    showErrDialog(msg);
                                    loadingDimss();
                                }
                            });
                        } else {
                            map.put("customerId", PublicCache.loginPurchase.getEntityId());
                            map.put("provinceName", defaultPurAccount.getProvinceName());
                            map.put("cityName", defaultPurAccount.getCityName());
                            map.put("bankName", defaultPurAccount.getBankName());
                            map.put("accountNo", defaultPurAccount.getAccountNo());
                            map.put("bankAddress", defaultPurAccount.getBankName().equals("支付宝") ? "支付宝" : defaultPurAccount.getBankAddress());
                            map.put("capitalWithdrawal", money.toString());
                            map.put("moneyType", type);
                            getRequestPresenter().getCustomerCashWithdrwalApplication(map, new ResultInfoCallback<CustomerCash>(getBaseActivity()) {
                                @Override
                                public void onSuccess(CustomerCash body) {
                                    dialog.dismiss();
                                    loadingDimss();
                                    UIUtils.showToastSafesShort("申请已提交");
                                    finish();
                                }

                                @Override
                                public void onFailed(int supplierCashResultInfo, String msg) {
                                    UIUtils.showToastSafesShort(msg);
                                    loadingDimss();
                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();*/

                break;
        }
    }

    public void  showErrDialog(String messae){

        AlertDialog.Builder builder = ViewUtils.showDialog(WithDrawalsActivity.this, "提示", messae);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.dismiss();
            }


        });     builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void button(BigDecimal money) {

        if (money.compareTo(BigDecimal.valueOf(2)) < 0) {
            UIUtils.showToastSafesShort("提现失败，提现金额不可小于2元");
            return;
        }


        /**
         * //申请提现
         * 参数名	类型	必须(1是/0否)	说明
         * supplierId	int	1	供应商ID
         * provinceName	Str	1	省名称
         * cityName	Str	1	城市名称
         * bankName	Str	1	银行名称
         * bankId	Str	1	银行卡号
         * bankAddress	Str	1	开户行地址
         * capitalWithdrawal	double	1	提现金额
         */

        Map<String, Object> map = new HashMap<>();
        loading();
        if (PublicCache.loginSupplier != null) {
            map.put("supplierId", PublicCache.loginSupplier.getEntityId());
            map.put("provinceName", defaultAccount.getProvinceName());
            map.put("cityName", defaultAccount.getCityName());
            map.put("bankName", defaultAccount.getBankName());
            map.put("bankId", defaultAccount.getEntityId());
            map.put("bankAddress", defaultAccount.getBankAddress());
            map.put("capitalWithdrawal", money.toString());

            getRequestPresenter().getSupplierCashWithdrwalApplication(map, new ResultInfoCallback<SupplierCash>(getBaseActivity()) {
                @Override
                public void onSuccess(SupplierCash body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("申请已提交");
                    finish();
                }

                @Override
                public void onFailed(int supplierCashResultInfo, String msg) {
//                                    UIUtils.showToastSafesShort(msg);
                    showErrDialog(msg);
                    loadingDimss();
                }
            });
        } else {
            map.put("customerId", PublicCache.loginPurchase.getEntityId());
            map.put("provinceName", defaultPurAccount.getProvinceName());
            map.put("cityName", defaultPurAccount.getCityName());
            map.put("bankName", defaultPurAccount.getBankName());
            map.put("accountNo", defaultPurAccount.getAccountNo());
            map.put("bankAddress", defaultPurAccount.getBankName().equals("支付宝") ? "支付宝" : defaultPurAccount.getBankAddress());
            map.put("capitalWithdrawal", money.toString());
            map.put("moneyType", type);
            getRequestPresenter().getCustomerCashWithdrwalApplication(map, new ResultInfoCallback<CustomerCash>(getBaseActivity()) {
                @Override
                public void onSuccess(CustomerCash body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("申请已提交");
                    finish();
                }

                @Override
                public void onFailed(int supplierCashResultInfo, String msg) {
                    UIUtils.showToastSafesShort(msg);
                    loadingDimss();
                }
            });
        }
    }
}
