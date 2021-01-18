package cn.com.taodaji.ui.activity.cashCoupon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import com.base.retrofit.ResultInfoCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.widget.VerifyCodeView;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class CashCouponCardPwdActivity extends SimpleToolbarActivity implements View.OnClickListener {
    EditText edCardPwd;
    EditText edVerify;
    VerifyCodeView vcvVerify;
    TextView btCardPwdGet;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setTitle("领取卡密券");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_cash_coupon_cardpwd);
        body_other.addView(mainView);
        edCardPwd = ViewUtils.findViewById(mainView, R.id.ed_cardPwd);
        edVerify = ViewUtils.findViewById(mainView, R.id.ed_verify);
        vcvVerify = ViewUtils.findViewById(mainView, R.id.vcv_verify);
        btCardPwdGet = ViewUtils.findViewById(mainView, R.id.bt_cardPwd_get);
        btCardPwdGet.setOnClickListener(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CashCouponCardPwdActivity.class);
        context.startActivity(intent);
    }

    public void onClick(View view) {

        String text = edCardPwd.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            UIUtils.showToastSafesShort("请输入卡密");
            edCardPwd.requestFocus();
            return;
        }

        String ss = edVerify.getText().toString().trim();
        if (ss.length() == 0) {
            UIUtils.showToastSafesShort("输入验证码");
            return;
        }

        if (!vcvVerify.isEqualsIgnoreCase(ss)) {
            UIUtils.showToastSafesShort("验证码输入错误");
            vcvVerify.refresh();
            return;
        }
        if (PublicCache.loginPurchase == null) return;
        loading();
        getRequestPresenter().coupons_getCouponByCode(PublicCache.loginPurchase.getEntityId(),PublicCache.loginPurchase .getIsC(), text, new ResultInfoCallback(this) {
            @Override
            public void onSuccess(Object body) {
                loadingDimss();
                UIUtils.showToastSafesShort("领取成功");
                edCardPwd.setText("");
                edVerify.setText("");
                vcvVerify.refresh();
            }

            @Override
            public void onFailed(int o, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });


    }
}
