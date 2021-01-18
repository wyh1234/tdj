package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AccountByCustomerId;
import cn.com.taodaji.model.entity.AccountByStoreId;
import cn.com.taodaji.model.entity.BankUnbundling;
import com.base.retrofit.RequestCallback;
import cn.com.taodaji.viewModel.vm.BankCardVM;
import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.BaseViewHolder;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class MyBankCardDetailActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("银行卡详情");
    }

    private View mainView;
    private AccountByStoreId accountByStoreId = null;
    private AccountByCustomerId.DataBean dataBean;
    private TextView unbind, name;
    BaseViewHolder holder;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_my_bank_card_deatil);
        body_other.addView(mainView);

        unbind = ViewUtils.findViewById(mainView, R.id.unbind);
        name = ViewUtils.findViewById(mainView, R.id.name);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accountByStoreId != null) {
                    loading();
                    getRequestPresenter().bankUnbundling(accountByStoreId.getEntityId(), accountByStoreId.getStoreId(), new RequestCallback<BankUnbundling>() {
                        @Override
                        public void onSuc(BankUnbundling body) {
                            loadingDimss();
                            UIUtils.showToastSafesShort("解绑成功");
                            finish();
                        }

                        @Override
                        public void onFailed(int bankUnbundling, String msg) {
                            loadingDimss();
                            UIUtils.showToastSafesShort(msg);
                        }
                    });
                } else {
                    UIUtils.showToastSafesShort("暂不支持解绑，请联系客服");
                }
            }
        });

        holder = new BaseViewHolder(mainView, new BankCardVM(), null);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onEvent(AccountByStoreId event) {
         accountByStoreId = event;
        EventBus.getDefault().removeStickyEvent(event);
        holder.setValues(event);
        name.setText(PublicCache.loginSupplier.getRealname());
    }

    @Subscribe(sticky = true)
    public void onEvent(AccountByCustomerId.DataBean event) {
        dataBean = event;
        EventBus.getDefault().removeStickyEvent(event);
        BankCardVM bankCardVM = new BankCardVM();
        if (dataBean.getBankType() == 0) {
            holder.setText(R.id.textView, "姓名：");
            holder.setText(R.id.textView_1, "账号：");
        }
        holder.setValues(event);
        name.setText(PublicCache.loginPurchase.getRealname());
        // unbind.setEnabled(false);
    }

    public static void startActivity(Context context, AccountByStoreId abs) {
        EventBus.getDefault().postSticky(abs);
        Intent intent = new Intent(context, MyBankCardDetailActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, AccountByCustomerId.DataBean abs) {
        EventBus.getDefault().postSticky(abs);
        Intent intent = new Intent(context, MyBankCardDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {

    }
}
