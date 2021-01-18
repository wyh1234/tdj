package cn.com.taodaji.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.weixin.callback.WXCallbackActivity;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.homepage.RichScanHintActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestOrderActivity;

//import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        api = WXAPIFactory.createWXAPI(this, PublicCache.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            String content = launchMiniProResp.extMsg; // 对应JsApi navigateBackApplication中的extraData字段数据


            if (!TextUtils.isEmpty(content)) {
                //如果是条码
                if (JavaMethod.isNumeric(content) && content.length() == 13) {
                    //售后订单
                    if (PublicCache.loginPurchase != null) {

                        loading();
                        Map<String, String> map = new HashMap<>();
                        map.put("c", String.valueOf(PublicCache.loginPurchase.getEntityId()));
                        map.put("qrCodeId", content);
                        ModelRequest.getInstance().scanQRCode(map).enqueue(new RequestCallback<ScanQRCode>() {
                            @Override
                            protected void onSuc(ScanQRCode body) {
                                loadingDimss();
                                if (body.getData() != null && body.getData().getItems() != null && body.getData().getItems().size() > 0) {
                                    for (OrderDetail.ItemsBean itemsBean : body.getData().getItems()) {
                                        itemsBean.setPackageNum(itemsBean.getAmount().intValue());
                                        itemsBean.setPackageFee(itemsBean.getAmount().multiply(itemsBean.getForegift()));
                                    }
                                    Intent intent = new Intent(getBaseContext(), AfterSalesRequestActivity.class);
                                    intent.putExtra("data", body.getData());
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailed(int errCode, String msg) {
                                loadingDimss();
                                Intent intent = new Intent(getBaseContext(), RichScanHintActivity.class);
                                if (errCode == 1) {
                                    intent.putExtra("data", msg);
                                }
                                startActivity(intent);
                                finish();
//                                    UIUtils.showToastSafesShort(msg);
                            }
                        });
                    }
                }
                //是售后二维码
                else if (content.startsWith("tdj{")) {
                    int start = content.indexOf("{");
                    int end = content.indexOf("}");
                    if (end > start) {
                        Map<String, String> map = new HashMap<>();
                        String str = content.substring(start + 1, end);
                        if (str.contains(",")) {
                            String[] ss = str.split(",");
                            if (ss.length > 0) {
                                for (String s : ss) {
                                    if (s.contains(":")) {
                                        String[] params = s.split(":");
                                        if (params.length > 1) {
                                            map.put(params[0], params[1]);
                                        }
                                    }
                                }
                            }

                        }
                        if (map.size() > 0) {
                            String enId;
                            if ((enId = map.get("c")) != null) {
                                //售后订单
                                if (!JavaMethod.isNumeric(enId)) {
                                    UIUtils.showToastSafesShort("数据解析失败 c:" + enId);
                                    return;
                                }

                                if (PublicCache.loginPurchase != null && PublicCache.loginPurchase.getEntityId() == Integer.valueOf(enId)) {

                                    loading();
                                    ModelRequest.getInstance().scanQRCode(map).enqueue(new RequestCallback<ScanQRCode>() {
                                        @Override
                                        protected void onSuc(ScanQRCode body) {
                                            loadingDimss();
                                            if (body.getData() != null && body.getData().getItems() != null && body.getData().getItems().size() > 0) {
                                                for (OrderDetail.ItemsBean itemsBean : body.getData().getItems()) {
                                                    itemsBean.setPackageNum(itemsBean.getAmount().intValue());
                                                    itemsBean.setPackageFee(itemsBean.getAmount().multiply(itemsBean.getForegift()));
                                                }

                                                Intent intent = new Intent(getBaseContext(), AfterSalesRequestOrderActivity.class);
                                                intent.putExtra("data", body.getData());
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailed(int errCode, String msg) {
                                            loadingDimss();
                                            UIUtils.showToastSafesShort(msg);
                                        }
                                    });


                                }
                                //不是自已的订单
                                else {
                                    startActivity(new Intent(getBaseContext(), RichScanHintActivity.class));
                                    finish();
                                }
                            }
                        }
                    }
                }
                //非淘大集订单
                else {
                    Intent intent = new Intent(getBaseContext(), RichScanHintActivity.class);
                    intent.putExtra("data", "该订单非淘大集订单无法识别。");
                    startActivity(intent);
                    finish();
                }

            }
        }
    }

    private AlertDialog alertDialog;

    public void loading(String... message) {

        if (isFinishing() || isDestroyed()) return;

        TextView help_message = null;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
//            builder.setOnKeyListener(keylistener);
            View view = ViewUtils.getLayoutView(this, com.base.R.layout.popup_window_loading);
            help_message = ViewUtils.findViewById(view, com.base.R.id.help_message);
            builder.setView(view);
            alertDialog = builder.create();
            if (alertDialog.getWindow() != null) alertDialog.getWindow().setDimAmount(0);//设置昏暗度为0
        }
        if (message.length > 0 && help_message != null) {
            help_message.setText(message[0]);
        }
        if (isFinishing() || isDestroyed()) return;
        if (!alertDialog.isShowing()) alertDialog.show();
    }

    public void loadingDimss() {
//        if (isFinishing() || isDestroyed()) return;
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
    }

}
