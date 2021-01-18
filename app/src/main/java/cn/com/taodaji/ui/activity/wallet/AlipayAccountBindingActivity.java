package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;

import com.base.entity.ResultInfo;

import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class AlipayAccountBindingActivity extends SimpleToolbarActivity implements View.OnClickListener {

    EditText identificationNo;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("绑定支付宝");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_alipay_binding);
        body_other.addView(mainView);
        identificationNo = ViewUtils.findViewById(mainView, R.id.identification_no);
        Button identification_OK = ViewUtils.findViewById(mainView, R.id.identification_OK);
        identification_OK.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    public void onClick(View view) {
        String ali_account = identificationNo.getText().toString();
        if (ali_account.length() == 0) {
            UIUtils.showToastSafesShort("请输入支付宝账户");
            return;
        }

        if (PublicCache.loginPurchase != null) {
            Map<String, Object> map = new HashMap<>();
            loading();
            map.put("provinceId", PublicCache.loginPurchase.getProvinceId());
            map.put("cityId", PublicCache.loginPurchase.getCityId());
            map.put("customerId", PublicCache.loginPurchase.getEntityId());
            map.put("hotelName", PublicCache.loginPurchase.getHotelName());
            map.put("isDefault", 1);
            map.put("bankName", "支付宝");
            map.put("accountNo", ali_account);
            map.put("ownerName", PublicCache.loginPurchase.getRealname());
            //map.put("bankAddress", et_bankCard_name2.getText().toString());
            map.put("bankType", 1);

            getRequestPresenter().customerFinance_bankAccount(map, new RequestCallback<ResultInfo>() {
                @Override
                public void onSuc(ResultInfo body) {
                    loadingDimss();
                    finish();
                }

                @Override
                public void onFailed(int resultInfo, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AlipayAccountBindingActivity.class);
        context.startActivity(intent);
    }
}
