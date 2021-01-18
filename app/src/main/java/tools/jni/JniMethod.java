package tools.jni;

/**
 * Created by yangkuo on 2018-08-30.
 */
public class JniMethod {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    /**
     *
     */
    public static native String alipay_order();
    /**
     *花呗
     */
    public static native String alipay_orderhb();


    /**
     * 支付宝充值成功回调地址
     */
    public static native String supplierAnnalFee_alipay();
    /**
     *
     */
    public static native String alipay_recharge();

    /**
     *
     */
    public static native String alipay_rechargehb();

    /**
     * alipayId
     */
    public static native String alipayId();

    /**
     * alipayId  xiangyang
     */
    public static native String alipayId_xy();

    /**
     *alipayPRI
     */
    public static native String alipay_PRI();

    /**
     *alipayPRI  xiangyang
     */
    public static native String alipay_PRI_xy();

    /**
     * wxpayId
     */
    public static native String wxpayId();

}
