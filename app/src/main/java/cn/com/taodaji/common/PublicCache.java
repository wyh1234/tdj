package cn.com.taodaji.common;

import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DictFindAll;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;

import static cn.com.taodaji.common.Constants.DEFAULT_site;
import static cn.com.taodaji.common.Constants.DEFAULT_siteName;


public class PublicCache {
    //域名
    public static final String http_type = "real";
//      public static final String http_type = "test";
//    public static final String http_type = "model";
//    public static final String http_type = "ningQian";
//    public static final String http_type = "liJie";
//    public static final String http_type = "liKun";
//    public static final String http_type = "songXiaoYu";
//    public static final String http_type = "new";
//    public static final String http_type ="zhangran";
//    public static String http_type = ENVIRONMENT;
//    public static final String http_type = "node2";

    private static Map<String, List<String>> ROOT_HTTP_URL;

    public static List<String> getROOT_URL() {
        if (ROOT_HTTP_URL == null) {
            ROOT_HTTP_URL = new HashMap<>();
            //真实环境
            if (ROOT_HTTP_URL.get("real") == null) ROOT_HTTP_URL.put("real", new ArrayList<>());
            ROOT_HTTP_URL.get("real").add("http://www.51taodj.com:3001/");//http://node2.51taodj.com:8001/
            ROOT_HTTP_URL.get("real").add("http://finance.51taodj.com/");
            ROOT_HTTP_URL.get("real").add("http://gateway.51taodj.com:9000/");
            //模拟真实环境
            if (ROOT_HTTP_URL.get("model") == null) ROOT_HTTP_URL.put("model", new ArrayList<>());
            ROOT_HTTP_URL.get("model").add("http://47.97.250.138:8001/");//http://node2.51taodj.com:8001/       http://121.196.199.8:8001/
            ROOT_HTTP_URL.get("model").add("http://finance.51taodj.com/");
            ROOT_HTTP_URL.get("model").add("http://gateway.51taodj.com:9000/");
            //测试
            if (ROOT_HTTP_URL.get("test") == null) ROOT_HTTP_URL.put("test", new ArrayList<>());

//            ROOT_HTTP_URL.get("test").add("http://192.168.10.94:3001/");
            ROOT_HTTP_URL.get("test").add("http://114.55.253.161:8001/");
            ROOT_HTTP_URL.get("test").add("http://test-finance2.51taodj.com:8080/");
            ROOT_HTTP_URL.get("test").add("http://47.111.22.162:9000/");
            //宁倩
            if (ROOT_HTTP_URL.get("ningQian") == null)
                ROOT_HTTP_URL.put("ningQian", new ArrayList<>());
            ROOT_HTTP_URL.get("ningQian").add("http://192.168.5.122:3001/");
            ROOT_HTTP_URL.get("ningQian").add("http://test-finance2.51taodj.com:8080/");

            //李杰
            if (ROOT_HTTP_URL.get("liJie") == null) ROOT_HTTP_URL.put("liJie", new ArrayList<>());
            ROOT_HTTP_URL.get("liJie").add("http://192.168.5.128:3001/");
            ROOT_HTTP_URL.get("liJie").add("http://test-finance2.51taodj.com:8080/");

            //李坤
            if (ROOT_HTTP_URL.get("liKun") == null) ROOT_HTTP_URL.put("liKun", new ArrayList<>());
            ROOT_HTTP_URL.get("liKun").add("http://192.168.5.126:3001/");
            ROOT_HTTP_URL.get("liKun").add("http://test-finance2.51taodj.com:8080/");

            //宋晓宇
            if (ROOT_HTTP_URL.get("songXiaoYu") == null)
                ROOT_HTTP_URL.put("songXiaoYu", new ArrayList<>());
            ROOT_HTTP_URL.get("songXiaoYu").add("http://10.254.10.88:3001/");
            ROOT_HTTP_URL.get("songXiaoYu").add("http://test-finance2.51taodj.com:8080/");

            //new
            if (ROOT_HTTP_URL.get("new") == null) ROOT_HTTP_URL.put("new", new ArrayList<>());
            ROOT_HTTP_URL.get("new").add("http://47.98.43.145:8001/");
            ROOT_HTTP_URL.get("new").add("http://finance.51taodj.com/");

            //张然
            if (ROOT_HTTP_URL.get("zhangran") == null) ROOT_HTTP_URL.put("zhangran", new ArrayList<>());
            ROOT_HTTP_URL.get("zhangran").add("http://192.168.5.117:3001/");
            ROOT_HTTP_URL.get("zhangran").add("http://test-finance2.51taodj.com:8080/");

            //node2
            if (ROOT_HTTP_URL.get("node2") == null) ROOT_HTTP_URL.put("node2", new ArrayList<>());
            ROOT_HTTP_URL.get("node2").add("http://node2.51taodj.com:8001/");
            ROOT_HTTP_URL.get("node2").add("http://finance.51taodj.com/");
            ROOT_HTTP_URL.get("node2").add("http://47.111.22.162:9000/");
        }

        return ROOT_HTTP_URL.get(http_type);
    }

    public static String refreshId="";//刷新首页数
    public static int site = DEFAULT_site;//当前站点  默认为2
    public static String site_name = DEFAULT_siteName;//当前站点名称

    public static int site_login = DEFAULT_site;//登录后的site  默认为2
    public static String site_name_login = DEFAULT_siteName;//登录后站点名称 默认为襄阳市

    public static String location_default = DEFAULT_siteName;//定位站点   默认为襄阳市
    public static boolean isLocationSucc = false;//是否定位成功

    public static double longtitude = 0d;//经度

    public static double latitude = 0d;//纬度
    public static int userNameId;//登录的账号id

//    public static int isP_DEFAULT = 0;//0零售，1事件批，-1全部
//    public static int isCRITERIA_DEFAULT = -1;//商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria

    public static FindByIsActive findByIsActive;//已开启的城市

    public static int notifycationCount;//通知数量

    public static String deviceToken_umeng;//友盟推送的token

    public static LoginPurchaseBean loginPurchase = null;//采购商的登录数据
    public static LoginSupplierBean loginSupplier = null;//供应商的登录数据

    public static int isInvoice = 0;//isInvoice 0-无发票 1-启用 2-关闭

    public static String login_mode = Constants.PURCHASER;//默认的登录方式


    public static String WX_APP_ID;
    /**
     * customer_withdrawal_fee	采购商提现费用比例
     * count_down_time	倒计时间
     * is_start_limited_time	是否启用限制下单   0不限制  1限制
     * order_start_time	当天开始下单时间 6:00
     * order_end_time	当天截止下单时间24:00
     * customer_delivery_time	采购商送达时间
     * supplier_delivery_time	供应商送达时间
     */
    public static DictFindAll.DataBean initializtionData = null;//初始化需要用到的数据


    //订单状态
    private static CustomerData<String, String, String> orderState;

    public static CustomerData<String, String, String> getOrderState() {
        if (orderState == null) {
            orderState = new CustomerData<>();
            orderState.put(0, "wait_buyer_pay", "待付款", "待付款");
            orderState.put(1, "wait_buyer_confirm_goods", "待收货", "待收货");
            orderState.put(2, "wait_buyer_evaluate", "待评价", "待评价");

            orderState.put(3, "wait_seller_send_goods", "待入库", "待入库");//供 应商  wait_seller_confirm_goods
            orderState.put(4, "wait_seller_send_goods", "待发货", "待发货");//采购商
            orderState.put(5, "wait_seller_evaluate", "待评价", "待评价");

            orderState.put(6, "trade_success", "待评价", "待评价");
            orderState.put(7, "trade_finished", "交易成功", "交易成功");
            orderState.put(8, "trade_closed", "交易关闭", "交易关闭");
            orderState.put(9, "trade_canceled", "已取消", "订单已取消");

            //orderState.put(10, "wait_seller_send_goods", "待入库", "待入库");
            orderState.put(11, "wait_buyer_confirm_goods", "待签收", "待签收");
        }
        return orderState;
    }

    //售后类别
    private static CustomerData<Integer, String, String> afterSaleType;

    public static CustomerData<Integer, String, String> getAfterSaleType() {
        if (afterSaleType == null) {
            afterSaleType = new CustomerData<>();
            afterSaleType.put(0, 1, "退款", "");
            afterSaleType.put(1, 3, "退款退货", "");
            afterSaleType.put(2, 2, "换货", "");
            afterSaleType.put(3, 4, "补货", "");
        }
        return afterSaleType;
    }

    //问题类型       ..
//    private static CustomerData<Integer, String, String> afterSaleProblem_1;//申请类别:1表示申请退款、
//    private static CustomerData<Integer, String, String> afterSaleProblem_2;//:2表示申请换货
//    private static CustomerData<Integer, String, String> afterSaleProblem_3;//3退款退货

    public static CustomerData<Integer, String, String> getAfterSaleProblem() {
        CustomerData<Integer, String, String> afterSaleProblem = new CustomerData<>();
        afterSaleProblem.put(0, 1, "未收到货", "");
        afterSaleProblem.put(1, 2, "差称", "");


        afterSaleProblem.put(2, 9, "有烂坏", "");
        afterSaleProblem.put(3, 10, "有异味", "");
        afterSaleProblem.put(4, 11, "有发霉", "");
        afterSaleProblem.put(5, 12, "有虫眼", "");


        //        afterSaleProblem.put(6, 3, "质量问题", "");
        afterSaleProblem.put(7, 5, "品种不符", "");
        afterSaleProblem.put(8, 6, "发错货", "");
        afterSaleProblem.put(9, 7, "下错单", "");


       // afterSaleProblem.put(10, 8, "取消商品", "");
        afterSaleProblem.put(15, 4, "其它", "");

        afterSaleProblem.put(11, 13, "压坏", "");
        afterSaleProblem.put(12, 14, "捂坏", "");
        afterSaleProblem.put(13, 15, "化冻", "");
        afterSaleProblem.put(14, 16, "闷死", "");


      /*  switch (afterSaleType) {
            case 1:

                if (afterSaleProblem_1 == null) {
                    afterSaleProblem_1 = new CustomerData<>();
                    afterSaleProblem_1.put(0, 1, "未收到货", "");
                    afterSaleProblem_1.put(1, 2, "差称", "");
                    afterSaleProblem_1.put(2, 3, "质量问题", "");
                    afterSaleProblem_1.put(3, 4, "其它", "");
                    afterSaleProblem_2.put(4, 5, "品种不符", "");
                    afterSaleProblem_2.put(5, 6, "发错货", "");
                    afterSaleProblem_3.put(6, 7, "下错单", "");
                    afterSaleProblem_3.put(7, 8, "取消商品", "");
                }
                return afterSaleProblem_1;
            case 2:
                if (afterSaleProblem_2 == null) {
                    afterSaleProblem_2 = new CustomerData<>();
                    afterSaleProblem_2.put(0, 3, "质量问题", "");
                    afterSaleProblem_2.put(1, 5, "品种不符", "");
                    afterSaleProblem_2.put(2, 6, "发错货", "");
                    afterSaleProblem_2.put(3, 4, "其它", "");
                }
                return afterSaleProblem_2;
            default://3
                if (afterSaleProblem_3 == null) {
                    afterSaleProblem_3 = new CustomerData<>();
                    afterSaleProblem_3.put(0, 3, "质量问题", "");
                    afterSaleProblem_3.put(1, 5, "品种不符", "");
                    afterSaleProblem_3.put(2, 6, "发错货", "");
                    afterSaleProblem_3.put(3, 7, "下错单", "");
                    afterSaleProblem_3.put(4, 4, "其它", "");
                }
        }*/
        return afterSaleProblem;
    }


    public static CustomerData<Integer, String, String> getBank() {
        CustomerData<Integer, String, String> customerData = new CustomerData<>();
        String[] sstr = UIUtils.getStringArray(R.array.bank_type);
        Integer[] banktype = {R.mipmap.alipay, R.mipmap.cbc, R.mipmap.icbc, R.mipmap.abc, R.mipmap.bc, R.mipmap.bcm, R.mipmap.cmb, R.mipmap.psbc, R.mipmap.ceb, R.mipmap.cmbc,
                R.mipmap.spdb, R.mipmap.ccb, R.mipmap.cib, R.mipmap.hxb,R.mipmap.hubei};

        for (int i = 0; i < banktype.length; i++) {
            customerData.put(i, banktype[i], sstr[i], "");
        }
        return customerData;
    }

    public static CustomerData<Integer, String, Integer> getEvaluateInformation() {
        CustomerData<Integer, String, Integer> customerData = new CustomerData<>();
        customerData.put(1, R.drawable.s_evaluate_good, "好评", 5);
        customerData.put(2, R.drawable.s_evaluate, "中评", 3);
        customerData.put(3, R.drawable.s_evaluate_bad, "差评", 1);
        return customerData;
    }


    //评论状态
    private static CustomerData<Integer, String, String> evaluateType;

    public static CustomerData<Integer, String, String> getEvaluateType() {
        if (evaluateType == null) {
            evaluateType = new CustomerData<>();
            evaluateType.put(0, -1, "所有评价", "");
            evaluateType.put(1, 1, "晒图评价", "");
            evaluateType.put(2, 3, "给出的差评", "");

            evaluateType.put(3, 3, "差评", "");
            evaluateType.put(4, 0, "未回复", "");

        }
        return evaluateType;
    }

    //角色类别
    private static CustomerData<Integer, String, String> roleType;

    public static CustomerData<Integer, String, String> getRoleType() {
        if (roleType == null) {
            roleType = new CustomerData<>();
            roleType.put(0, 0, "主账号", "主账号角色");
            roleType.put(1, 1, "厨师", "子账号厨师");
            roleType.put(2, 2, "财务", "子账号财务");
            roleType.put(3, 3, "管理员", "子账号管理员");
            roleType.put(4, 4, "店长", "店长");
            roleType.put(5, 5, "员工", "员工");
        }
        return roleType;
    }

}
