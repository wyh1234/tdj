package cn.com.taodaji.ui.pay.alipay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.PaySuccessEvent;
import tools.jni.JniMethod;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;

public class Alipay {
    Activity activity;
    String subject; // 商品名称
    String body;// 商品详情
    String price;// 商品金额
    String order_sn;// 商户网站唯一订单号
    String call_back;// 服务器异步通知页面路径
    String RSA_PRIVATE;
    String payId;
    boolean IsHb;

    public Alipay(Activity act,String subject,String body,String price, String order_sn,String call_back){
        activity = act;
        this.subject = subject;
        this.body = body;// 商品详情
        this.price = price;// 商品金额
        this.order_sn = order_sn;// 商户网站唯一订单号
        this.call_back = call_back;// 服务器异步通知页面路径
        if (PublicCache.site_login==3){
            this.RSA_PRIVATE = JniMethod.alipay_PRI();
            this.payId = JniMethod.alipayId();
        }else {
            this.RSA_PRIVATE = JniMethod.alipay_PRI_xy();
            this.payId = JniMethod.alipayId_xy();
        }
    }
    public Alipay(Activity act,String subject,String body,String price, String order_sn,String call_back,boolean IsHb){
        activity = act;
        this.subject = subject;
        this.body = body;// 商品详情
        this.price = price;// 商品金额
        this.order_sn = order_sn;// 商户网站唯一订单号
        this.call_back = call_back;// 服务器异步通知页面路径
        if (PublicCache.site_login==3){
            this.RSA_PRIVATE = JniMethod.alipay_PRI();
            this.payId = JniMethod.alipayId();
        }else {
            this.RSA_PRIVATE = JniMethod.alipay_PRI_xy();
            this.payId = JniMethod.alipayId_xy();
        }
        this.IsHb = IsHb;// 是否支持花呗
    }

    public void newPay() {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(JniMethod.alipayId(), subject, body, price, order_sn, call_back, true, "5m");

      /*  String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, true);
        final String orderInfo = orderParam + "&" + sign;*/

        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(order_sn, true);

                PayResult payResult = new PayResult(result);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                LogUtils.e(resultStatus);
                // TODO 自动生成的方法存根
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    UIUtils.showToastSafe("支付成功");
                    //通知提交页面支付成功
                    EventBus.getDefault().post(new PaySuccessEvent());
                    // activity.finish();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        UIUtils.showToastSafe("支付结果确认中");
                        //   activity.finish();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        UIUtils.showToastSafe("支付失败");
                    }
                }
            }
        });
    }

    public void pay() {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(payId, subject, body, price, order_sn, call_back, true, "5m");

        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, true);
        final String orderInfo = orderParam + "&" + sign;

        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                PayResult payResult = new PayResult(result);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // TODO 自动生成的方法存根
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    UIUtils.showToastSafe("支付成功");
                    //通知提交页面支付成功
                    EventBus.getDefault().post(new PaySuccessEvent());
                    // activity.finish();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        UIUtils.showToastSafe("支付结果确认中");
                        //   activity.finish();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        UIUtils.showToastSafe("支付失败");
                    }
                }
            }
        });

    }

    public void payIsHb() {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(payId, subject, body, price, order_sn, call_back, true, "5m",IsHb);

        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, true);
        final String orderInfo = orderParam + "&" + sign;

        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                PayResult payResult = new PayResult(result);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // TODO 自动生成的方法存根
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    UIUtils.showToastSafe("支付成功");
                    //通知提交页面支付成功
                    EventBus.getDefault().post(new PaySuccessEvent());
                    // activity.finish();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        UIUtils.showToastSafe("支付结果确认中");
                        //   activity.finish();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        UIUtils.showToastSafe("支付失败");
                    }
                }
            }
        });

    }

    /**
     * get the cn version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        UIUtils.showToastSafesShort(version);
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content, String RSA_PRIVATE) {
        return SignUtils.sign(content, RSA_PRIVATE, true);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA2\"";
    }
}
