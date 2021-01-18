package cn.com.taodaji.ui.pay.wxapppay;

import android.app.Activity;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WXPay;
import com.base.utils.UIUtils;

public class Wxpay {
    private IWXAPI api;
    Activity activity;

    public Wxpay(Activity activity) {
        this.activity = activity;

    }

    public void pay(WXPay wxPay) {
        try {
            api = WXAPIFactory.createWXAPI(activity, null);
            if(api != null) {
                api.registerApp(wxPay.getAppid());
                // 检查威信是否支持支付
                if (!checkedAPP()) {
                    UIUtils.showToastSafe("您的微信版本不支持支付！");
                    return;
                }
                // 将该app注册到微信
                if (!registerApp(wxPay.getAppid())) {
                    UIUtils.showToastSafe("淘大集注册到微信失败！");
                    return;
                }
                PublicCache.WX_APP_ID = wxPay.getAppid();
                PayReq req = new PayReq();
                req.appId = wxPay.getAppid();
                req.prepayId = wxPay.getPrepayid();
                req.partnerId = wxPay.getPartnerid();
                req.nonceStr = wxPay.getNoncestr();
                req.timeStamp = wxPay.getTimestamp();
                req.packageValue = wxPay.getPackageX();
                req.sign = wxPay.getSign();

                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        } catch (Exception e) {
            UIUtils.showToastSafe("异常：" + e.getMessage());
        }
    }

    // 检查威信是否支持支付
    public boolean checkedAPP() {
        return api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    // 将该app注册到微信
    public boolean registerApp(String appId) {
        return api.registerApp(appId);
    }

}
