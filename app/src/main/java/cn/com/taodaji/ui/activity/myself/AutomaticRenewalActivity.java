package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressUpdate;
import cn.com.taodaji.model.event.EidtPickupFeeEvent;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.ppw.EditPickupFeePopupWindow;
import tools.activity.SimpleToolbarActivity;

public class AutomaticRenewalActivity extends SimpleToolbarActivity {

    private View mainView;
    private Switch aSwitch;
    private RelativeLayout rlEditFee;
    private TextView restFee,tips;
    private Button btnEditFee;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("自动续费淘钱包");
    }

    protected void initMainView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_automatic_renewal);
        body_other.addView(mainView);
        aSwitch = mainView.findViewById(R.id.sw_auto_renew);
        rlEditFee = mainView.findViewById(R.id.rl_edit_pickup_fee);
        restFee = mainView.findViewById(R.id.tv_rest_fee);
        btnEditFee = mainView.findViewById(R.id.btn_edit_pickup_fee);
        tips = mainView.findViewById(R.id.tips);
        restFee.setText(getIntent().getStringExtra("fee"));
        if (PublicCache.loginSupplier.getIsAutomaticRenewal()==1){
            aSwitch.setChecked(true);
            rlEditFee.setVisibility(View.VISIBLE);
            tips.setText("当《淘钱包》余额小于200元时，自动从《可提现余额》中续费，续费金额可再转出提现。");
        }else {
            aSwitch.setChecked(false);
            rlEditFee.setVisibility(View.GONE);
            tips.setText("开通自动续费后，当“淘钱包余额”小于200时，将自动从“可用余额”中续费200元,当扣费失败，服务将自动关闭。");
        }
        btnEditFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPickupFeePopupWindow popupWindow = new EditPickupFeePopupWindow(AutomaticRenewalActivity.this);
                popupWindow.setRestFee(new BigDecimal(PublicCache.loginSupplier.getWithdrawalMoney()));
                popupWindow.setDismissWhenTouchOutside(false);
                popupWindow.setInterceptTouchEvent(false);
                popupWindow.setPopupWindowFullScreen(true);//铺满
                popupWindow.showPopupWindow();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    loading("请稍后...");
                    RequestPresenter2.getInstance().setAutomaticRenewal(PublicCache.loginSupplier.getStore(), 1, new RequestCallback<AddressUpdate>() {
                        @Override
                        protected void onSuc(AddressUpdate body) {
                            rlEditFee.setVisibility(View.VISIBLE);
                            loadingDimss();
                            PublicCache.loginSupplier.setIsAutomaticRenewal(1);
                            PublicCache.loginSupplier.update();
                            tips.setText("当《淘钱包》余额小于200元时，自动从《可提现余额》中续费，续费金额可再转出提现。");
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            loadingDimss();
                            UIUtils.showToastSafe(msg);
                        }
                    });
                }else {
                    loading("请稍后...");
                    RequestPresenter2.getInstance().setAutomaticRenewal(PublicCache.loginSupplier.getStore(), 0, new RequestCallback<AddressUpdate>() {
                        @Override
                        protected void onSuc(AddressUpdate body) {
                            rlEditFee.setVisibility(View.GONE);
                            PublicCache.loginSupplier.setIsAutomaticRenewal(0);
                            PublicCache.loginSupplier.update();
                            loadingDimss();
                            tips.setText("开通自动续费后，当“淘钱包余额”小于200时，将自动从“可用余额”中续费200元,当扣费失败，服务将自动关闭。");
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            loadingDimss();
                            UIUtils.showToastSafe(msg);
                        }
                    });
                }
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

    //接收编辑续费金额
    @Subscribe
    public void onEvent(EidtPickupFeeEvent event) {
        RequestPresenter2.getInstance().setAutomaticRenewalFee(PublicCache.loginSupplier.getStore(), event.getFee(), new RequestCallback<AddressUpdate>() {
            @Override
            protected void onSuc(AddressUpdate body) {
                UIUtils.showToastSafe("编辑成功");
                finish();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }
}
