package cn.com.taodaji.ui.ppw;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;

import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.presenter.RequestPresenter;
import com.base.retrofit.ResultInfoCallback;
import com.base.activity.BaseActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SubAccountPopuWindow extends PopupWindow implements View.OnClickListener {

    TextView helpText;

    EditText verifyCode;

    private View mainView;
    private Context context;

    private PopupResultInterface resultInterface;

    private LoginPurchaseBean loginPurchase;


    public void setResultInterface(PopupResultInterface resultInterface) {
        this.resultInterface = resultInterface;
    }

    public SubAccountPopuWindow(Context context, LoginPurchaseBean loginPurchase) {
        this.context = context;
        this.loginPurchase = loginPurchase;
        mainView = ViewUtils.getLayoutView(context, R.layout.popup_window_subaccount_jihuo);

        setContentView(mainView);
        setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        helpText = ViewUtils.findViewById(mainView, R.id.help_text);
        verifyCode = ViewUtils.findViewById(mainView, R.id.verify_code);
        ViewUtils.findViewById(mainView, R.id.cancel).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.ok).setOnClickListener(this);

        helpText.setText(loginPurchase.getHotelName() + "，位于" + loginPurchase.getHotelAddress() + "，邀请您在《淘大集》平台为该酒店担任" +PublicCache.getRoleType().getValueOfKey (loginPurchase.getEmpRole()) + "一职");


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cancel:

                RequestPresenter.getInstance().subuser_refuse(loginPurchase.getSubUserId(), new ResultInfoCallback<Object>() {
                    @Override
                    public void onSuccess(Object body) {
                        //  if (finalActivity != null) finalActivity.loadingDimss();
                        UIUtils.showToastSafesShort("已拒绝邀请");
                        dismiss();
                    }

                    @Override
                    public void onFailed(int objectResultInfo, String msg) {
                        //    if (finalActivity != null) finalActivity.loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });

                break;
            case R.id.ok:
                String v_code = verifyCode.getText().toString().trim();
                if (v_code.length() == 0) {
                    UIUtils.showToastSafesShort("请输入邀请码");
                    return;
                }

                //  if (finalActivity != null) finalActivity.loading(mainView, null);
                RequestPresenter.getInstance().activation(loginPurchase.getSubUserId(), v_code, new ResultInfoCallback<Object>() {
                    @Override
                    public void onSuccess(Object body) {
                        BaseActivity activity = (BaseActivity) context;
                        activity.hideSoftInput(verifyCode.getWindowToken());
                        PublicCache.loginPurchase = loginPurchase;
                        resultInterface.setResult(body);
                        UIUtils.showToastSafesShort("已同意邀请");
                        dismiss();
                    }

                    @Override
                    public void onFailed(int objectResultInfo, String msg) {
                        //    if (finalActivity != null) finalActivity.loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
        }
    }
}
