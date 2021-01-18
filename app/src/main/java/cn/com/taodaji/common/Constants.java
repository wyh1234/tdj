package cn.com.taodaji.common;

import com.amap.api.maps2d.model.LatLng;

import cn.com.taodaji.R;

import com.base.common.Config;
import com.base.utils.IOUtils;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Constants {

//    String PHONE_400 = "";

    //    String PHONE_0710 = UIUtils.getString(R.string.phone_headquarters);
//    String PHONE_after = UIUtils.getString(R.string.phone_after_sale);
    //主页面底部图标
    int[] MAIN_HOMEPAGE_BOTTOM_ICON = {R.drawable.s_main_homepage, R.drawable.s_main_market, R.drawable.s_main_pickfood, R.drawable.s_main_cart, R.drawable.s_main_myself};//底部图片标识

    //主页面底部文字
    List<String> MAIN_HOMEPAGE_BOTTOM_TAG = new ArrayList<>(Arrays.asList("首页", "逛市场", "去挑菜", "购物车", "我")); //底部文字标识

    //积分商城主页面底部图标
    int[] Integral_MAIN_BOTTOM_ICON = {R.drawable.i_main_homepage, R.drawable.i_main_cart, R.drawable.i_main_my};//底部图片标识

    //积分商城主页面底部文字
    List<String> Integral_MAIN_BOTTOM_TAG = new ArrayList<>(Arrays.asList("首页","购物车", "我的")); //底部文字标识

    /**
     * 店铺状态
     * "storeStatus": "店铺状态 0营业中，1今日休息，2歇业一周,3歇业一月,4关闭商铺【int型】",
     */
    List<String> STORE_STATUS = new ArrayList<>(Arrays.asList("正常营业", "暂停营业", "歇业一周", "歇业一月", "歇业中"));

    //店铺管理的列表
    List<String> SHOP_MANAGEMENT_LIST = new ArrayList<>(Arrays.asList("出售中", "已下架", "审核中"));

    //今日待入库订单的列表
    List<String> WAIT_ORDER_MANAGEMENT_LIST = new ArrayList<>(Arrays.asList("全部", "未打印","已打印"));

    //订单商品的当前状态
    /*        code	说明
        0	待确认
        1	待打印发货
        2	打印发货
        3	已入库
        4	已出库
        5	买家确认收货
        6	售后中
        7	自己送达
        8	卖家没货
        9	售后结束*/ List<String> Order_itemStatus = new ArrayList<>(Arrays.asList("待确认", "待打印发货", "打印发货", "已入库", "已出库", "买家确认收货", "售后中", "自己送达", "卖家没货", "售后结束"));
    /**
     * 采购商的身份
     * role: 0 表示主账号、1表示子账号厨师  2表示子账号财务 3表示子账号管理员
     */
//    List<String> ROLE_STATUS = new ArrayList<>(Arrays.asList("主账号", "厨师", "财务", "管理员","店长","员工"));


    String DEFAULT_siteName = "襄阳市";
    int DEFAULT_site = 2;

    LatLng XIANGYANG_LAT = new LatLng(32.070431, 112.147349);
    LatLng WUHAN_LAT = new LatLng(30.52,114.31);

    String FLAG = "flag";//标识
    //String PURCHASER = "purchaser";   // 采购商用户customer
    String PURCHASER = "customer";   // 采购商用户customer
    String SUPPLIER = "supplier";    // 供应商用户
    String VER_CODE_LOGIN = "1";//验证码登录
    String PASSWORD_LOGIN = "0";//密码登录

    String specification_unit_base = "个双卷斤张天小时";//和斤一样处理的单位

    int goodsNameLength = 15;
    /**
     * 图片上传关键字，根据这个字段确定调用哪个上传接口
     */
    String HEAD_PORTRAIT_UPLOAD = "HeadPortraitUpload";//采购商头像
    String CARD_ID_UPLOAD = "CardIdUpload";//身份证验证
    String FOOD_QUALITY_UPLOAD = "FoodQualityUpload";//食品资格证
    String LICENCE_UPLOAD = "LicenceUpload";//营业执照
    String CREDENTIALS_IMAGE = "credentialsImage";//菜的资质

}
