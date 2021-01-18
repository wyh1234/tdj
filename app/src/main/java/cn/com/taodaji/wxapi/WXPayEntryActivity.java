package cn.com.taodaji.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.PaySuccessEvent;
import com.base.utils.UIUtils;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_currency);
        api = WXAPIFactory.createWXAPI(this, PublicCache.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                UIUtils.showToastSafe("支付成功!");
                //通知提交页面支付成功
                EventBus.getDefault().post(new PaySuccessEvent());
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                UIUtils.showToastSafe("用户已取消支付！");
                finish();
                break;
            case BaseResp.ErrCode.ERR_COMM:
                // 签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                UIUtils.showToastSafe("签名错误或未注册APPID");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                // 用户拒绝授权
                UIUtils.showToastSafe("用户拒绝授权");
                finish();
                break;
            default:
                UIUtils.showToastSafe("发生了未知错误，不能支付，请您更换支付方式!");
                break;
        }
    }

}