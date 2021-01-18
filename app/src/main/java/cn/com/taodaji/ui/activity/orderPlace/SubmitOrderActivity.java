package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.CommunityAddress;
import cn.com.taodaji.model.entity.CouponsChooseCouponList;
import cn.com.taodaji.model.entity.DeliverFee;
import cn.com.taodaji.model.entity.DeliverTime;
import cn.com.taodaji.model.entity.FreightParticulars;
import cn.com.taodaji.model.entity.FreightParticularsNew;
import cn.com.taodaji.model.entity.GoodsReceiptAddress;
import cn.com.taodaji.model.entity.GoodsReceiptAddressBean;
import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;
import cn.com.taodaji.model.entity.OrderConfirm;
import cn.com.taodaji.model.entity.OrderPlaceBack;
import cn.com.taodaji.model.entity.PayerListResultBean;
import cn.com.taodaji.model.entity.TzServiceFee;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.CashCouponListEvent;
import cn.com.taodaji.model.event.CashCouponTabCountEvent;
import cn.com.taodaji.model.event.CashCouponUseEvent;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.event.UpdateAddressEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.cashCoupon.CashCouponActivity;
import cn.com.taodaji.ui.activity.cashCoupon.NewCashCouponActivity;
import cn.com.taodaji.ui.activity.employeeManagement.PopupBottomActivity;
import cn.com.taodaji.ui.activity.myself.GoodsReceiptAddressActivity;
import cn.com.taodaji.ui.activity.shopManagement.AddressManagementActivity;
import cn.com.taodaji.ui.activity.shopManagement.EditAddressManagementActivity;
import cn.com.taodaji.ui.activity.tdjc.MyPickupAddressActivity;
import cn.com.taodaji.ui.activity.tdjc.MyPickupCodeActivity;
import cn.com.taodaji.ui.activity.tdjc.SelectPickUpTimePoupWindow;
import cn.com.taodaji.ui.activity.tdjc.SelectPickUpWayPoupWindow;
import cn.com.taodaji.ui.activity.tdjc.ServiceMoneyPopuWindow;
import cn.com.taodaji.ui.activity.tdjc.SubmitOrderErrPoupWindow;
import cn.com.taodaji.ui.pay.PurchaserOrderConfirmaActivity;
import cn.com.taodaji.ui.ppw.FreightParticularsPopupWindow;
import cn.com.taodaji.viewModel.adapter.SubmitOrderPlaceAdapter;
import cn.com.taodaji.viewModel.vm.ReceiptAddressVM;
import tools.activity.SimpleToolbarActivity;
import tools.animation.PlaceOrderPopuWindow;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by yangkuo on 2018-08-07.
 */
public class SubmitOrderActivity extends SimpleToolbarActivity implements View.OnClickListener, PlaceOrderPopuWindow.PlaceOrderPopuWindowListener,SelectPickUpWayPoupWindow.SelectPickUpWayPoupWindowListener,SelectPickUpTimePoupWindow.SelectPickUpTimePoupWindowListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("提交订单");
    }

    private View mainView;
    private TextView tv_address, cart_price_bottom, place_order;
    private RelativeLayout address_detail, address_default,address_detail_c;
    private TextView count_image, second_text,hotelName,consignee,cityName,tv_address_phone_name;
    //代金券
    private LinearLayout cash_coupon, cash_coupon_pt,linearLayout_ticket, ll_cash_pledge,ll_select_payer,ll_select_way,linearLayout_service_money_c;
    private TextView tv_ticket_tips, tv_ticket_pay,tv_c_time;
    private TextView tv_cash_coupons_used_count, tv_cash_coupons_used_count_pt,tv_cash_coupon_money,tv_cash_coupon_money_pt, tv_cash_coupon_used_money, tv_cash_coupon_used_money_pt,tv_cash_pledge_sum,tv_service_money_c,tv_address_c;
    private TextView tv_cash_coupon_used_mon, tv_goods_money, cart_price,tv_current_payer,tv_current_way,c_phone;

    private BaseViewHolder viewHolder;//收货地址的holder
    private SubmitOrderPlaceAdapter simpleCartAdapter;
    private RecyclerView cart_item;
    private ImageView im_shipping_eetails,ic_time,ic_show_address,iv_address_c;
    private String[] payerArray;
    private List<PayerListResultBean.DataBean.ListBean>  payerList=new ArrayList<>();
    private int paymentCustomerId;//（付款人ID）

    private String paymentCustomerName="";
    private String  paymentCustomerRole="";
    private PlaceOrderPopuWindow placeOrderPopuWindow;
    private LinearLayout send_time_group;
    private SelectPickUpWayPoupWindow selectPickUpPoupWindow;
    private SelectPickUpTimePoupWindow selectPickUpTimePoupWindow;
    private ServiceMoneyPopuWindow serviceMoneyPopuWindow;
    private SubmitOrderErrPoupWindow submitOrderErrPoupWindow;
    private DeliverFee mbody;
    private int deliveryType,timeEntityId;
    private BigDecimal tzServiceFee=BigDecimal.ZERO;
    private  ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> shopList = new ArrayList<>();//所以的店券
    private ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> pList = new ArrayList<>();//所以的平台券
    private  Map<Integer ,NewCouponsChooseCouponList.DataBean.ItemBean> shopmap=new HashMap<>();

    private  ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean>allmap=new ArrayList<>();//选择的总券
    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_submit_order);
        body_other.addView(mainView);

        //选购的商品
        simpleCartAdapter = new SubmitOrderPlaceAdapter(this);
//        freightFree = ViewUtils.findViewById(mainView, R.id.freightFree);
        cart_item = ViewUtils.findViewById(mainView, R.id.cart_item);
        cart_item.setLayoutManager(new LinearLayoutManager(this));
        cart_item.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(3), R.color.white));
        cart_item.setAdapter(simpleCartAdapter);
        //头部选择地址
        View heard_view = ViewUtils.getFragmentView(cart_item, R.layout.activity_submit_order_heard);
        simpleCartAdapter.addHeaderView(heard_view);
        address_detail = heard_view.findViewById(R.id.address_detail);
        c_phone= heard_view.findViewById(R.id.c_phone);
        send_time_group = heard_view.findViewById(R.id.send_time_group);
        address_default = heard_view.findViewById(R.id.address_default);
        second_text = heard_view.findViewById(R.id.second_text);
        tv_c_time = heard_view.findViewById(R.id.tv_c_time);
        address_detail_c = heard_view.findViewById(R.id.address_detail_c);
        tv_address_c= heard_view.findViewById(R.id.tv_address_c);
        iv_address_c= heard_view.findViewById(R.id.iv_address_c);
        tv_address_phone_name= heard_view.findViewById(R.id.tv_address_phone_name);
        ic_time=heard_view.findViewById(R.id.ic_time);
        ic_show_address=heard_view.findViewById(R.id.ic_show_address);

        hotelName=heard_view.findViewById(R.id.hotelName);
        c_phone=heard_view.findViewById(R.id.c_phone);
        consignee=heard_view.findViewById(R.id.consignee);
        cityName=heard_view.findViewById(R.id.cityName);

        address_detail_c.setOnClickListener(this);

        ll_select_payer = heard_view.findViewById(R.id.ll_select_payer);

        ll_select_way = heard_view.findViewById(R.id.ll_select_way);
        ll_select_way.setOnClickListener(this);
        ll_select_payer.setOnClickListener(this);
        send_time_group.setOnClickListener(this);

        tv_current_payer = heard_view.findViewById(R.id.tv_current_payer);
        tv_current_way = heard_view.findViewById(R.id.tv_current_way);

        viewHolder = new BaseViewHolder(address_detail, new ReceiptAddressVM(), new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    if (PublicCache.loginPurchase.getIsC()==1){
                        Intent intent=new Intent(getBaseActivity(), MyPickupAddressActivity.class);//传提货点经纬度，地址
                        intent.putExtra("lat",mbody.getData().getPickupInfo().getLat()+"");
                        intent.putExtra("lon",mbody.getData().getPickupInfo().getLon()+"");
                        intent.putExtra("communityShortName",mbody.getData().getPickupInfo().getShortPickupPoint());
                        intent.putExtra("address",mbody.getData().getPickupInfo().getPickupAddress());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getBaseActivity(), GoodsReceiptAddressActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("flg", true);
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            }
        });

        //尾部
        View foot_view = ViewUtils.getFragmentView(cart_item, R.layout.activity_submit_order_footer);
        simpleCartAdapter.addFooterView(foot_view);

        linearLayout_ticket = foot_view.findViewById(R.id.linearLayout_ticket);
        tv_ticket_tips = foot_view.findViewById(R.id.tv_ticket_tips);
        tv_ticket_pay = foot_view.findViewById(R.id.tv_ticket_pay);
        cart_price = foot_view.findViewById(R.id.cart_price);
        linearLayout_service_money_c= foot_view.findViewById(R.id.linearLayout_service_money_c);
        tv_service_money_c= foot_view.findViewById(R.id.tv_service_money_c);
        linearLayout_service_money_c.setOnClickListener(this);

        count_image = foot_view.findViewById(R.id.count_image);
        ll_cash_pledge = foot_view.findViewById(R.id.ll_cash_pledge);
        tv_cash_coupon_money = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupon_money);//代金券金额
        tv_cash_coupon_money_pt = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupon_money_pt);//平台代金券金额
        tv_cash_coupon_used_money = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupon_used_money);//代金券使用金额
        tv_cash_coupon_used_money_pt = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupon_used_money_pt);//代金券使用金额
        tv_cash_coupons_used_count = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupons_used_count);//代金券使用数量
        tv_cash_coupons_used_count_pt = ViewUtils.findViewById(foot_view, R.id.tv_cash_coupons_used_count_pt);//平台代金券使用数量
        tv_cash_coupon_used_mon = foot_view.findViewById(R.id.tv_cash_coupon_used_mon);//运费
        tv_cash_pledge_sum = foot_view.findViewById(R.id.tv_cash_pledge_sum);//押金
        tv_goods_money = foot_view.findViewById(R.id.tv_goods_money);
        cash_coupon = ViewUtils.findViewById(foot_view, R.id.cash_coupon);
        cash_coupon.setOnClickListener(this);
        cash_coupon_pt = ViewUtils.findViewById(foot_view, R.id.cash_coupon_pt);
        cash_coupon_pt.setOnClickListener(this);

        im_shipping_eetails = foot_view.findViewById(R.id.im_shipping_eetails);
        im_shipping_eetails.setOnClickListener(this);


        //尾部提交订单
        View bottom_view = ViewUtils.getLayoutView(this, R.layout.activity_order_place_bottom);
        body_bottom.removeAllViews();
        body_bottom.addView(bottom_view);
        body_bottom.setVisibility(View.VISIBLE);

        //收货地址
        tv_address = ViewUtils.findViewById(bottom_view, R.id.tv_address);
        tv_address.setOnClickListener(this);
        tv_address.setVisibility(View.VISIBLE);
        //实付款
        cart_price_bottom = ViewUtils.findViewById(bottom_view, R.id.cart_price_bottom);
        //提交订单
        place_order = ViewUtils.findViewById(bottom_view, R.id.place_order);
        place_order.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

    }


    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private int addressId = -1;
    private BigDecimal freight_money = BigDecimal.ZERO;  //运费
    private BigDecimal coupon_money = BigDecimal.ZERO;     //优惠券
    private String productInfo = "";//提交时的参数
    private String freightItemInfo;//提交时运费的参数
    private String couponItemInfo;//提交时代金券参数
    private String paymentCode = "online_payment";//提交时支付方式

    private String productInfo1 = "";//获取代金券时需要的参数
    private String productInfo2 = "";//获取配送费时需要的参数
    private List<FreightParticularsNew.DataBean> freightParticularsNewList = new ArrayList<>();

    private List<CartGoodsBean> list_bean = new ArrayList<>();//购物车中选中的商品

    private List<CartGoodsBean> list_remak_bean = new ArrayList<>();//有备注的商品

    private int productCount = 0;//选中商品的数量

    private BigDecimal price_sum = BigDecimal.ZERO;//商品的实付金额
    private BigDecimal price_sum_real = BigDecimal.ZERO;//商品的总金额不减运费
    private BigDecimal pay_invoice = BigDecimal.ZERO;//税费
    private Map<String, Object> shippingAddressInfo_map = new HashMap<>();

    private BigDecimal pay_cash = BigDecimal.ZERO;//押金
    private BigDecimal coupon_money_pt = BigDecimal.ZERO;     //平台优惠券
    @Override
    protected void initData() {
        super.initData();
        onStartLoading();
        ThreadManager.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                //初始化列表
                List<CartGoodsBean> list = CartModel.getInstance().getCartList();
                price_sum = BigDecimal.ZERO;
                price_sum_real = BigDecimal.ZERO;
                pay_cash = BigDecimal.ZERO;//押金
//                productInfo = "[";
                productInfo1 = "[";
                productInfo2 = "[";
                list_bean.clear();
                for (CartGoodsBean cgb : list) {
                    if (cgb.getSelected() && cgb.getStatus() == 1 && cgb.getStoreStatus() == 0) {
                        list_bean.add(cgb);
                        price_sum_real = price_sum_real.add(cgb.getProductPrice().multiply(BigDecimal.valueOf(cgb.getProductQty())));
//                        productInfo += "{productId:" + cgb.getProductId() + ",categoryId:" + cgb.getCategoryId() + ",commodityId:" +
//                                cgb.getCommodityId() + ",isP:" + cgb.getIsP() +
//                                ",qty:" + cgb.getProductQty() + ",specId:" + cgb.getSpecId() +",remark:" + cgb.getRemark() +  "},";
                        productInfo1 += "{\"productId\":" + cgb.getProductId() + ",\"price\":" + cgb.getProductPrice().toString() + ",\"qty\":" + cgb.getProductQty() + "},";
                        productInfo2 += "{\"productId\":" + cgb.getProductId() + ",\"price\":" + cgb.getProductPrice().toString() + ",\"qty\":" + cgb.getProductQty() + "},";

                        if (!TextUtils.isEmpty(cgb.getPackageName())) {
                            cgb.setPackageFee(cgb.getForegift().multiply(BigDecimal.valueOf(cgb.getProductQty())));
                            cgb.setIsForegift(1);
                            cgb.setPackageNum(cgb.getProductQty());
                            pay_cash = pay_cash.add(cgb.getPackageFee());
                        } else {
                            cgb.setIsForegift(0);
                        }

                    }
                }
                price_sum = price_sum.add(price_sum_real);
                price_sum = price_sum.add(pay_cash);

//                productInfo = productInfo.substring(0, productInfo.length() - 1) + "]";
                productInfo1 = productInfo1.substring(0, productInfo1.length() - 1) + "]";
                productInfo2 = productInfo2.substring(0, productInfo2.length() - 1) + "]";
                productCount = list_bean.size();


                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pay_cash.compareTo(BigDecimal.ZERO) > 0) {
                            ll_cash_pledge.setVisibility(View.VISIBLE);
                            tv_cash_pledge_sum.setText("+" + String.valueOf(pay_cash) + "元");
                        } else ll_cash_pledge.setVisibility(View.GONE);

                        cart_price.setText(String.valueOf(price_sum_real));
                        cart_price_bottom.setText(String.valueOf(price_sum));//总计
                        tv_goods_money.setText(String.valueOf(price_sum_real) + "元");//商品金额
                        count_image.setText(String.valueOf(list_bean.size()));
                        simpleCartAdapter.notifyDataSetChanged(list_bean);
                        //获取运费
                        getYF();
                        //获取可用代金券数量
                        getCashCouponData2();
                    }
                });
            }
        });
        SharedPreferences sp = UIUtils.getSharedPreferences("payment");
        paymentCustomerId=sp.getInt("paymentCustomerId",0);
        paymentCustomerName=sp.getString("paymentCustomerName","");
        paymentCustomerRole=sp.getString("paymentCustomerRole","");

        tv_current_payer.setText(paymentCustomerName+paymentCustomerRole);

        //初始化收货地址
        initAdress();


        //初始化付款人
//        initPayMentCustomer();
        if (PublicCache.loginPurchase.getIsC()==1){
            initDeliverFee(true);
            ll_select_way.setVisibility(View.VISIBLE);
            linearLayout_service_money_c.setVisibility(View.VISIBLE);
            ic_time.setVisibility(View.VISIBLE);
            ll_select_payer.setVisibility(View.GONE);
            ic_show_address.setVisibility(View.VISIBLE);
            tv_c_time.setText("提货时间：");

        }else {

            initPayMentCustomer();
            address_detail_c.setVisibility(View.GONE);
            ll_select_way.setVisibility(View.GONE);
            linearLayout_service_money_c.setVisibility(View.GONE);
            ic_time.setVisibility(View.GONE);
            ll_select_payer.setVisibility(View.VISIBLE);
            ic_show_address.setVisibility(View.GONE);
            tv_c_time.setText("要求送达时间：");
        }

    }
    private void initDeliverFee(boolean updaterAddress){
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",PublicCache.loginPurchase.getEntityId());
        getRequestPresenter().getDeliverFee(map, new RequestCallback<DeliverFee>() {
            @Override
            protected void onSuc(DeliverFee body) {
                mbody=body;
                if (updaterAddress){
                    int day;
                    int time_now = Integer.valueOf(DateUtils.parseTime("HH"));
                    //这里不用考虑下单时间，只判定4点前付款当天送达，4点后第二天送达
                    if (time_now < 4) day = 0;
                    else day = 1;
                    second_text.setText(DateUtils.getDay("MM-dd", day) + "("+(day>0?"明天":"今天")+")" + body.getData().getDefaultTime().getStartTime() + "-" + body.getData().getDefaultTime().getEndTime());
                    tv_current_way.setText(body.getData().getServiceFee().get(0).getServiceName());
                    deliveryType=body.getData().getServiceFee().get(0).getDeliveryType();
                    timeEntityId=body.getData().getDefaultTime().getTimeEntityId();

                    hotelName.setText("自提点："+body.getData().getPickupInfo().getPickupPoint());

                    hotelName.setTextColor(getResources().getColor(R.color.orange_yellow_ff7d01));
                    cityName.setTextColor(getResources().getColor(R.color.orange_yellow_ff7d01));
                    cityName.setText("团长电话"+body.getData().getPickupInfo().getMasterPhone());
                    consignee.setText(body.getData().getPickupInfo().getPickupAddress());
                    initTzServiceFee(body.getData().getServiceFee().get(0).getDeliveryType(),body.getData().getDefaultTime().getTimeEntityId());
                }else {
                    initTzServiceFee(deliveryType,timeEntityId);
                }


            }


        });

    }
    public void initTzServiceFee(int deliveryType,int timeEntityId){
        Map<String,Object> map=new HashMap<>();
        map.put("deliveryType",deliveryType);
        map.put("timeEntityId",timeEntityId);
        map.put("customerId",PublicCache.loginPurchase.getEntityId());

        getRequestPresenter().getTzServiceFee(map, new RequestCallback<TzServiceFee>() {
            @Override
            protected void onSuc(TzServiceFee body) {
                tv_service_money_c.setText(body.getData().getTzServiceFee()+"元");
                price_sum=  price_sum.subtract(tzServiceFee);
                tzServiceFee=new BigDecimal(body.getData().getTzServiceFee());
                price_sum= price_sum.add(tzServiceFee);
                cart_price_bottom.setText(String.valueOf(price_sum));//总计
                if (deliveryType==1){
                    address_detail.setVisibility(View.VISIBLE);
                    address_detail_c.setVisibility(View.GONE);
                    tv_address.setText(mbody.getData().getPickupInfo().getPickupAddress());
                }else {
                    address_detail_c.setVisibility(View.VISIBLE);
                    address_detail.setVisibility(View.GONE);
                    tv_address.setText(mbody.getData().getDeliverInfo().getDeliverAddress()+"("+mbody.getData().getDeliverInfo().getStreetNumber()+")");
                    if (mbody.getData().getDeliverInfo().getIsCreateAddress()==0){
                        tv_address_phone_name.setText("收货人："+mbody.getData().getDeliverInfo().getCustomerName()+" "+mbody.getData().getDeliverInfo().getCustomerTel());
                        tv_address_c.setText("收货地址："+mbody.getData().getDeliverInfo().getDeliverAddress()+"("+mbody.getData().getDeliverInfo().getStreetNumber()+")");
//                        iv_address_c.setVisibility(View.GONE);
                        iv_address_c.setVisibility(View.VISIBLE);
                    }else {
                        tv_address_c.setText("请选择收货地址");
                        iv_address_c.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


    }

    //初始化付款人
    private void initPayMentCustomer(){
        addRequest(ModelRequest.getInstance().getPayerList(PublicCache.site_login,PublicCache.loginPurchase.getEntityId(),PublicCache.loginPurchase.getLoginUserId()), new RequestCallback<PayerListResultBean>() {

            @Override
            protected void onSuc(PayerListResultBean body) {

                payerList.clear();
                if (body.getData() != null) {
                    List<PayerListResultBean.DataBean.ListBean>  tempList=(List<PayerListResultBean.DataBean.ListBean>)body.getData().getList();
                    if (tempList != null) {
                        payerList.addAll(tempList) ;
                        payerArray=new String[payerList.size()];

                        boolean old_pay=false;
//                        boolean new_pay=false;
//                        int index=0;

                        for (int i = 0; i <payerList.size() ; i++) {
                            payerArray[i]=payerList.get(i).getNickName()+ "("+PublicCache.getRoleType().getValueOfKey(payerList.get(i).getRole())+")"+payerList.get(i).getPhone();
                            if (paymentCustomerId==payerList.get(i).getCustomerEntityId()) {
                                old_pay=true;
                            }
                        }
                        /*if (paymentCustomerId==PublicCache.loginPurchase.getEntityId()) {
                            old_pay=true;
                        }*/
                        if (!old_pay){
                         /*   if (PublicCache.loginPurchase.getEmpRole()==1||PublicCache.loginPurchase.getEmpRole()==5) {
                                if (payerList.size()>0) {
                                    paymentCustomerId=payerList.get(0).getCustomerEntityId();
                                    paymentCustomerName=payerList.get(0).getNickName();
                                    paymentCustomerRole="("+PublicCache.getRoleType().getValueOfKey(payerList.get(0).getRole())+")";
                                }

                            }else {
                                paymentCustomerId=PublicCache.loginPurchase.getEntityId();
                                paymentCustomerName=PublicCache.loginPurchase.getRealname();
                                paymentCustomerRole="("+PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+")";
                            }*/
                            if (payerList.size()>0) {
                                paymentCustomerId=payerList.get(0).getCustomerEntityId();
                                paymentCustomerName=payerList.get(0).getNickName();
                                paymentCustomerRole="("+PublicCache.getRoleType().getValueOfKey(payerList.get(0).getRole())+")";
                                tv_current_payer.setText(paymentCustomerName+paymentCustomerRole);
                            }else {
                                if (PublicCache.loginPurchase.getEmpRole()!=2&&PublicCache.loginPurchase.getEmpRole()!=5) {
                                    paymentCustomerId=PublicCache.loginPurchase.getEntityId();
                                    paymentCustomerName=PublicCache.loginPurchase.getRealname();
                                    paymentCustomerRole="("+PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+")";
                                    tv_current_payer.setText(paymentCustomerName+paymentCustomerRole);
                                }

                            }

                        }

                    }else {
                        if (PublicCache.loginPurchase.getEmpRole()!=2&&PublicCache.loginPurchase.getEmpRole()!=5) {
                            paymentCustomerId=PublicCache.loginPurchase.getEntityId();
                            paymentCustomerName=PublicCache.loginPurchase.getRealname();
                            paymentCustomerRole="("+PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+")";
                            tv_current_payer.setText(paymentCustomerName+paymentCustomerRole);
                        }


                    }
                }

            }
        });

    }
    //初始化送达时间
    private void initShippingAddressInfo(GoodsReceiptAddressBean bean) {
        shippingAddressInfo_map.put("shippingMethodCode", "system_default");
        int day;
        int time_now = Integer.valueOf(DateUtils.parseTime("HH"));
        //这里不用考虑下单时间，只判定4点前付款当天送达，4点后第二天送达
        if (time_now < 4) day = 0;
        else day = 1;
        if (bean == null) {
            return;
        }
       // String str = PublicCache.initializtionData.getCustomer_delivery_time();
        //int index = str.indexOf("-");
//        if (index > -1) {
//            String start = str.substring(0, index);
//            String end = str.substring(index + 1, str.length());
//
//        }

        second_text.setText(DateUtils.getDay("MM月dd日", day) + "上午" + bean.getDeliveredTime() + "至" + bean.getDeliveredTimeEnd());
        shippingAddressInfo_map.put("expectDeliveredETime", bean.getDeliveredTime());
        shippingAddressInfo_map.put("expectDeliveredLTime", bean.getDeliveredTimeEnd());
        shippingAddressInfo_map.put("expectDeliveredDate", DateUtils.getDay("yyyy-MM-dd", day));
    }

    //初始化收货地址
    private void initAdress() {
        getRequestPresenter().getAddressList(1, 10,PublicCache.loginPurchase.getEntityId(),PublicCache.loginPurchase.getLoginUserId(), new ResultInfoCallback<GoodsReceiptAddress>(this) {
            @Override
            public void onSuccess(GoodsReceiptAddress body) {
                if (PublicCache.loginPurchase.getIsC()==1){
                    if (body.getItems().size() == 1) {
                        addressId=  body.getItems().get(0).getAddressId();
                    }
                    return;
                }

                //如果收货地址有1个，那么默认显示这个收货地址，如果没有或者多个，那么显示添加默认收货地址
                if (body.getItems().size() == 1) {
                    GoodsReceiptAddressBean bean = body.getItems().get(0);
//                    c_phone.setText("(团长："+bean.getPhoneNumber()+")");
                    viewHolder.setValues(bean);
                    viewHolder.setText(R.id.street, bean.getStreet()+"  "+bean.getStreet_number());
                    if (tv_address != null && PublicCache.loginPurchase != null){
                        if(PublicCache.loginPurchase.getIsC()==1){
                            tv_address.setText(String.valueOf(bean.getStreet() +"  "+ bean.getHotelName()));

                        }else {
                            tv_address.setText(String.valueOf(bean.getCityName() + bean.getStreet()+"  "+bean.getStreet_number() +"  "+ bean.getHotelName()));

                        }

                    }

                    addressId = bean.getAddressId();
                    address_default.setVisibility(View.VISIBLE);

                    //初始化送达时间
                    initShippingAddressInfo(bean);
                } else if (body.getItems().size() == 0) {
                    address_default.setVisibility(View.GONE);
                    addressId = -1;
                    if (tv_address != null) {
                        tv_address.setText("请添加收货地址");
                    }
                } else {
                    address_default.setVisibility(View.GONE);
                    addressId = -1;
                    if (tv_address != null) {
                        tv_address.setText("请选择收货地址");
                    }
                }
            }

            @Override
            public void onFailed(int goodsReceiptAddressResultInfo, String msg) {
                address_default.setVisibility(View.GONE);
                addressId = -1;
            }
        });
    }

    // 接收选择的  收货地址
    @Subscribe
    public void onEvent(UpdateAddressEvent updateAddressEvent) {
        GoodsReceiptAddressBean bean = updateAddressEvent.getBean();
        viewHolder.setValues(bean);
        if (tv_address != null)
            tv_address.setText(String.valueOf(bean.getCityName() + bean.getStreet() + bean.getHotelName()));
        addressId = bean.getAddressId();
        address_default.setVisibility(View.VISIBLE);

        //初始化送达时间
        initShippingAddressInfo(bean);
    }


    /**
     * 获取配送费
     */
    public void getYF() {
        RequestPresenter.getInstance().freight_particulars(productInfo2, new RequestCallback<FreightParticulars>(this) {
            @Override
            protected void onSuc(FreightParticulars body) {
                if (body.getData() == null) return;


                freight_money = body.getData().getTotalFreight();
                price_sum = price_sum.add(freight_money);
                tv_cash_coupon_used_mon.setText("+" + body.getData().getTotalFreight().setScale(2, ROUND_HALF_UP).toString() + "元");


                if (body.getData().getIsInvoice() == 1) {
                    linearLayout_ticket.setVisibility(View.VISIBLE);
                    tv_ticket_tips.setVisibility(View.VISIBLE);
                    if (PublicCache.initializtionData != null && !TextUtils.isEmpty(PublicCache.initializtionData.getTax_fee_rate())) {
                        tv_ticket_tips.setText("(商品金额的" + (new BigDecimal(PublicCache.initializtionData.getTax_fee_rate()).multiply(BigDecimal.valueOf(100)).intValue()) + "%)");
                        pay_invoice = price_sum_real.multiply(new BigDecimal(PublicCache.initializtionData.getTax_fee_rate())).setScale(2, ROUND_HALF_UP);

                        price_sum = price_sum.add(pay_invoice);
                        tv_ticket_pay.setText("+" + pay_invoice.toString() + "元");
                    }
                } else {
                    linearLayout_ticket.setVisibility(View.GONE);
                    tv_ticket_tips.setVisibility(View.GONE);
                }

                cart_price_bottom.setText(String.valueOf(price_sum));

//                if (body.getData().getItem() == null) return;
//                freightItemInfo = "[";
//                for (FreightParticulars.DataBean.ItemBean a : body.getData().getItem()) {
//                    freightItemInfo += "{\"entityId\":" + a.getEntityId() + ",\"fee\":" + a.getFee().toString() + ",\"platform\":" + a.getPlatform() + ",\"website\":" + a.getWebsite().toString() + ",\"supplier\":" + a.getSupplier().toString() + ",\"title\":\"" + a.getTitle() + "\"},";
//
//                    FreightParticularsNew.DataBean bean = new FreightParticularsNew.DataBean();
//                    bean.setTitle(a.getTitle());
//                    bean.setFee(a.getFee());
//                    freightParticularsNewList.add(bean);
//                }
//                if (freightItemInfo.length() == 1) {
//                    freightItemInfo += "]";
//                } else
//                    freightItemInfo = freightItemInfo.substring(0, freightItemInfo.length() - 1) + "]";
//
//
//                if (PublicCache.loginPurchase.getIsC()==1){
                    freightItemInfo = "["+"{\"fee\":" +'"'+freight_money +'"'+","+"\"title\":" + '"'+"运费"+'"'+"}]";
//                }
            }


            @Override
            public void onFailed(int freightParticulars, String msg) {

            }
        });
    }

    //代金券数量
    private void getCashCouponData() {
        if (PublicCache.loginPurchase == null) return;
        getRequestPresenter().coupons_chooseCouponList(PublicCache.loginPurchase.getEntityId(), productInfo1, new RequestCallback<CouponsChooseCouponList>(this) {
            @Override
            protected void onSuc(CouponsChooseCouponList body) {

                if (body.getData() != null) {
                    //加上上次使用的，减去本次使用的
                    price_sum = price_sum.add(coupon_money);

                    coupon_money = BigDecimal.ZERO;
                    Map<String, CouponsChooseCouponList.DataBean.ItemBean> map = new HashMap<>();

                    if (body.getData().getUnlimit() != null && body.getData().getUnlimit().size() > 0) {
                        map.put(body.getData().getUnlimit().get(0).getCategoryId(), body.getData().getUnlimit().get(0));
                    }
                    if (body.getData().getLimit() != null && body.getData().getLimit().size() > 0) {
                        Collections.reverse(body.getData().getLimit());
                        for (CouponsChooseCouponList.DataBean.ItemBean itemBean : body.getData().getLimit()) {
                            map.put(itemBean.getCategoryId(), itemBean);
                        }
                    }

                    int count = map.size();
                    couponItemInfo = "[";
                    if (count > 0) {
                        for (String s : map.keySet()) {
                            CouponsChooseCouponList.DataBean.ItemBean item = map.get(s);
                            coupon_money = coupon_money.add(item.getAmount());
                            couponItemInfo += "{\"entityId\":" + item.getEntityId() + ",\"couponId\":" + item.getCouponId() + ",\"productIds\":\"" + item.getProductIds() + "\",\"amount\":" + item.getAmount() + "},";
                        }
                        couponItemInfo = couponItemInfo.substring(0, couponItemInfo.length() - 1) + "]";
                    } else {
                        couponItemInfo += "]";
                    }

                    price_sum = price_sum.subtract(coupon_money);
                    if (count == 0) {
                        if (tv_cash_coupon_money != null) tv_cash_coupon_money.setText("0张可用");
                    } else {

                        if (tv_cash_coupon_money != null)
                            tv_cash_coupon_money.setText(String.valueOf(coupon_money) + "元");
                        if (tv_cash_coupons_used_count != null) {
                            tv_cash_coupons_used_count.setVisibility(View.VISIBLE);
                            tv_cash_coupons_used_count.setText("已选" + count + "张");
                            tv_cash_coupon_used_money.setText("-" + String.valueOf(coupon_money) + "元");
                            cart_price_bottom.setText(String.valueOf(price_sum));


                        }
                    }

                }

            }

            @Override
            public void onFailed(int couponsFindReceiveList, String msg) {
                tv_cash_coupon_money.setText("0张可用");
            }
        });
    }

    //代金券数量
    private void getCashCouponData2() {
        if (PublicCache.loginPurchase == null) return;
        getRequestPresenter().coupons_chooseNewCouponList(PublicCache.loginPurchase.getEntityId(), productInfo1, new RequestCallback<NewCouponsChooseCouponList>(this) {
            @Override
            protected void onSuc(NewCouponsChooseCouponList body) {
                if (body.getData() == null) return;
                //加上上次使用的，减去本次使用的
                price_sum = price_sum.add(coupon_money);

                coupon_money = BigDecimal.ZERO;
                shopList.addAll(body.getData().getShop().getLimit());
                shopList.addAll(body.getData().getShop().getUnlimit());
                pList.addAll(body.getData().getPlatform().getUnlimit());
                pList.addAll(body.getData().getPlatform().getLimit());
                for(NewCouponsChooseCouponList.DataBean.ItemBean shopitemBean:shopList){
                    shopmap.put(shopitemBean.getStoreId(),shopitemBean);

                }


                int count = shopmap.size();
                couponItemInfo = "[";
                if (count > 0) {
                    for (Map.Entry<Integer, NewCouponsChooseCouponList.DataBean.ItemBean> entry : shopmap.entrySet()) {
                        coupon_money = coupon_money.add(entry.getValue().getAmount());
                        couponItemInfo += "{\"entityId\":" + entry.getValue().getEntityId() + ",\"couponId\":" + entry.getValue().getCouponId() +  ",\"storeId\":" +
                                entry.getValue().getStoreId()+ ",\"productIds\":\"" + entry.getValue().getProductIds() + "\",\"amount\":" + entry.getValue().getAmount() + "},";

                    }
                    couponItemInfo = couponItemInfo.substring(0, couponItemInfo.length() - 1) + "]";
                } else {
                    couponItemInfo += "]";
                }
                if (pList.size()>0){
                    tv_cash_coupon_money_pt.setText("去使用");
                }else {
                    tv_cash_coupon_money_pt.setText("0张可用");
                }

                price_sum = price_sum.subtract(coupon_money);
                if (count == 0) {
                    if (tv_cash_coupon_money != null)
                        tv_cash_coupon_money.setText("0张可用");
                } else {

                    if (tv_cash_coupon_money != null)
                        tv_cash_coupon_money.setText(String.valueOf(coupon_money) + "元");
                    if (tv_cash_coupons_used_count != null) {
                        tv_cash_coupons_used_count.setVisibility(View.VISIBLE);
                        tv_cash_coupons_used_count.setText("已选" + count + "张");
                        tv_cash_coupon_used_money.setText("-" + String.valueOf(coupon_money) + "元");
                        cart_price_bottom.setText(String.valueOf(price_sum));


                    }
                    for (Map.Entry<Integer, NewCouponsChooseCouponList.DataBean.ItemBean> entry : shopmap.entrySet()) {
                        for ( NewCouponsChooseCouponList.DataBean.ItemBean  itemBean:shopList){
                            if (entry.getValue().getEntityId()==itemBean.getEntityId()){
                                itemBean.setSelected(true);
                            }

                        }
                    }
                }

            }

            @Override
            public void onFailed(int couponsFindReceiveList, String msg) {
                tv_cash_coupon_money.setText("0张可用");
                tv_cash_coupon_money_pt.setText("0张可用");
            }
        });
    }


    /**
     * 代金券选中后返回{@link CashCouponActivity#}
     */

    @Subscribe
    public void onEvent(CashCouponUseEvent event) {
        //加上上次使用的，减去本次使用的
//        price_sum = price_sum.add(coupon_money);
//        coupon_money = event.getCash_coupon_used_money();
//        price_sum = price_sum.subtract(coupon_money);
//
//        if (cart_price_bottom == null || tv_cash_coupon_used_money == null) return;
//        tv_cash_coupons_used_count.setVisibility(View.VISIBLE);
//
//        cart_price_bottom.setText(String.valueOf(price_sum));
//        tv_cash_coupons_used_count.setText("已选" + String.valueOf(event.getCash_coupon_count()) + "张");
//        tv_cash_coupon_money.setText(String.valueOf(event.getCash_coupon_money() + "元"));
//        tv_cash_coupon_used_money.setText("-" + String.valueOf(coupon_money) + "元");
//        couponItemInfo = event.getCouponItemInfo();

        if (event.getStaste()==1){
             price_sum = price_sum.add(coupon_money);
            coupon_money=BigDecimal.ZERO;
             coupon_money = event.getCash_coupon_used_money();
             price_sum = price_sum.subtract(coupon_money);

             LogUtils.e(coupon_money);
//            coupon_money_pt=BigDecimal.ZERO;
              if (cart_price_bottom == null || tv_cash_coupon_used_money == null) return;
               tv_cash_coupons_used_count.setVisibility(View.VISIBLE);
             cart_price_bottom.setText(String.valueOf(price_sum));
             tv_cash_coupons_used_count.setText("已选" + String.valueOf(event.getCash_coupon_count()) + "张");
//             tv_cash_coupons_used_count_pt.setText("已选" + 0 + "张");
             tv_cash_coupon_money.setText(String.valueOf(event.getCash_coupon_money() + "元"));
//             tv_cash_coupon_money_pt.setText(0+ "元");
             tv_cash_coupon_used_money.setText("-" + String.valueOf(coupon_money) + "元");
//             tv_cash_coupon_used_money_pt.setText("-" + 0+ "元");
//            for ( NewCouponsChooseCouponList.DataBean.ItemBean itemBean:pList){
//                itemBean.setSelected(false);
//            }

            shopList.clear();
            allmap.clear();
            shopList.addAll(event.getList());
            allmap.addAll(event.getList());
            allmap.addAll(pList);
        }else {
            price_sum = price_sum.add(coupon_money_pt);
            coupon_money_pt=BigDecimal.ZERO;
            coupon_money_pt= event.getCash_coupon_used_money();
            price_sum = price_sum.subtract(coupon_money_pt);
            if (cart_price_bottom == null || tv_cash_coupon_used_money_pt == null) return;
            tv_cash_coupons_used_count_pt.setVisibility(View.VISIBLE);
            cart_price_bottom.setText(String.valueOf(price_sum));
            tv_cash_coupons_used_count_pt.setText("已选" + String.valueOf(event.getCash_coupon_count()) + "张");
            tv_cash_coupon_money_pt.setText(String.valueOf(event.getCash_coupon_money() + "元"));
            tv_cash_coupon_used_money_pt.setText("-" + String.valueOf(coupon_money_pt) + "元");
            pList.clear();
            allmap.clear();
            pList.addAll(event.getList());

            allmap.addAll(pList);
            allmap.addAll(shopList);


        }
        LogUtils.e(allmap.size());
        couponItemInfo = "[";
        if (allmap.size() > 0) {
            for (NewCouponsChooseCouponList.DataBean.ItemBean entry : allmap) {
                if (entry.getSelected()){
                    couponItemInfo += "{\"entityId\":" +
                            entry.getEntityId() + ",\"couponId\":" + entry.getCouponId() + ",\"productIds\":\"" +
                            entry.getProductIds() + "\",\"storeId\":\"" +
                            entry.getStoreId()+ "\",\"amount\":" + entry.getAmount() + "},";
                }


            }
            couponItemInfo = couponItemInfo.substring(0, couponItemInfo.length() - 1) + "]";
        } else {
            couponItemInfo += "]";
        }
    }

    //选择收款人
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectShopOrPositionEvent event) {
            tv_current_payer.setText(event.getCurrentSelected());
            paymentCustomerId=payerList.get(event.getPosition()).getCustomerEntityId();
            paymentCustomerName=payerList.get(event.getPosition()).getNickName();
            paymentCustomerRole= "("+PublicCache.getRoleType().getValueOfKey(payerList.get(event.getPosition()).getRole())+")";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address:
//                simpleCartAdapter.moveToPosition(0);
                if (cart_item != null) cart_item.smoothScrollToPosition(0);
                break;
            case R.id.place_order://提交订单
                LogUtils.e(couponItemInfo);
                if (list_remak_bean.size()>0){
                    list_remak_bean.clear();
                }
                productInfo = "[";
                List<CartGoodsBean> list = CartModel.getInstance().getCartList();
                for (CartGoodsBean cgb : list) {
                    if (cgb.getSelected() && cgb.getStatus() == 1 && cgb.getStoreStatus() == 0 && !UIUtils.isNullOrZeroLenght(cgb.getRemark())) {
                        list_remak_bean.add(cgb);

                    }
                    if (cgb.getSelected() && cgb.getStatus() == 1 && cgb.getStoreStatus() == 0){
                        productInfo += "{productId:" + cgb.getProductId() + ",categoryId:" + cgb.getCategoryId() + ",commodityId:" +
                                cgb.getCommodityId() + ",isP:" + cgb.getIsP() +
                                ",qty:" + cgb.getProductQty() + ",specId:" + cgb.getSpecId() +",remark:" +(UIUtils.isNullOrZeroLenght(cgb.getRemark())?'"'+""+'"': '"'+cgb.getRemark()+'"') +  "},";
                    }
                }
                  productInfo = productInfo.substring(0, productInfo.length() - 1) + "]";
                if (list_remak_bean.size()>0){
                    placeOrderPopuWindow = new PlaceOrderPopuWindow(this,list_remak_bean,v);
                    placeOrderPopuWindow.setDismissWhenTouchOutside(false);
                    placeOrderPopuWindow.setInterceptTouchEvent(false);
                    placeOrderPopuWindow.showPopupWindow();
                    placeOrderPopuWindow.setPlaceOrderPopuWindowListener(this);
                }else {
                    LogUtils.d(productInfo);
                    button_1(v);
                }



                break;
            case R.id.cash_coupon:
//                CashCouponActivity.startActivity(this, 1);
//                EventBus.getDefault().post(new CashCouponListEvent());
                CashCouponListEvent cashCouponListEvent=new CashCouponListEvent(shopList);
                NewCashCouponActivity.startActivity(this, 1,cashCouponListEvent,new BigDecimal(cart_price_bottom.getText().toString()));
//                NewCashCouponActivity.startActivity(this, 1,new BigDecimal(cart_price_bottom.getText().toString()));
                break;
            case R.id.cash_coupon_pt:
//                CashCouponActivity.startActivity(this, 1);
//
                CashCouponListEvent cashCouponListEventp=new CashCouponListEvent(pList);
//                EventBus.getDefault().postSticky(cashCouponListEventp);//可以用粘性事件
                NewCashCouponActivity.startActivity(this, 2,cashCouponListEventp,new BigDecimal(cart_price_bottom.getText().toString()));
//                NewCashCouponActivity.startActivity(this, 2,new BigDecimal(cart_price_bottom.getText().toString()));

                break;
            case R.id.im_shipping_eetails:
                if (freightParticularsNewList == null) return;
                FreightParticularsPopupWindow freightParticularsPopupWindow = new FreightParticularsPopupWindow(im_shipping_eetails, freightParticularsNewList);
                freightParticularsPopupWindow.showAtLocation(im_shipping_eetails, Gravity.CENTER, 0, 0);
                break;
                case R.id.ll_select_payer:
                    if (payerList.size()>0) {
                        Intent intent = new Intent(SubmitOrderActivity.this, PopupBottomActivity.class);
                        intent.putExtra("title","选择付款人");
                        intent.putExtra("itemList",payerArray);
                        intent.putExtra("flag",true);
                        startActivity(intent);
                    }

                    break;

            case R.id.ll_select_way:
                if (selectPickUpPoupWindow!=null){
                    if (selectPickUpPoupWindow.isShowing()){
                        return;
                    }

                }
                selectPickUpPoupWindow = new SelectPickUpWayPoupWindow(SubmitOrderActivity.this,mbody);
                selectPickUpPoupWindow.setSelectPickUpWayPoupWindowListener(this);
                selectPickUpPoupWindow.setPopupWindowFullScreen(true);//铺满
                selectPickUpPoupWindow.showPopupWindow();

                break;
            case R.id.send_time_group:
                if (PublicCache.loginPurchase.getIsC()==1){
                    initDeliverTime();
                }





                break;
            case R.id.linearLayout_service_money_c:
                if (serviceMoneyPopuWindow!=null){
                    if (serviceMoneyPopuWindow.isShowing()){
                        return;
                    }

                }
                serviceMoneyPopuWindow = new ServiceMoneyPopuWindow(mbody,SubmitOrderActivity.this);
                serviceMoneyPopuWindow.setPopupWindowFullScreen(true);//铺满
                serviceMoneyPopuWindow.showPopupWindow();

                break;
            case R.id.address_detail_c:
                if (mbody.getData().getIsUpdateAddress()==0){
                    UIUtils.showToastSafe("当日下单前可更换一次收货地址，您今天已提交过订单，所以无法更换");
                    return;
                }

//                if (mbody.getData().getDeliverInfo().getIsCreateAddress()==1){
                    Intent intent1 = new Intent(this, EditAddressManagementActivity.class);
                intent1.putExtra("EditAddress","EditAddress");
                intent1.putExtra("addressId",addressId);
                    intent1.putExtra("streetNumber",mbody.getData().getDeliverInfo().getStreetNumber());
                    intent1.putExtra("address",mbody.getData().getDeliverInfo().getDeliverAddress());
                    intent1.putExtra("customerName",mbody.getData().getDeliverInfo().getCustomerName());
                    intent1.putExtra("deliverScope",mbody.getData().getDeliverScope());
                    intent1.putExtra("customerTel",mbody.getData().getDeliverInfo().getCustomerTel());
                startActivity(intent1);
//                    startActivityForResult(intent1,2);
//                }

                break;

    }
    }


    @Override
    public void button_1(View v) {

        if (PublicCache.loginPurchase == null) return;
        if (PublicCache.loginPurchase.getIsC()==1&&deliveryType==2){
            if (UIUtils.isNullOrZeroLenght(mbody.getData().getDeliverInfo().getDeliverAddress())){
                UIUtils.showToastSafesShort("请选择收货地址");
                return;
            }
        }
        if (PublicCache.loginPurchase.getEmpRole() == 2||PublicCache.loginPurchase.getEmpRole() == 5) {
            UIUtils.showToastSafesShort("您当前的身份无法提交");
            return;
        }
        if (addressId == -1&&PublicCache.loginPurchase.getIsC()!=1) {
            UIUtils.showToastSafesShort("请选择收货地址");
            return;
        }

        if (TextUtils.isEmpty(freightItemInfo)) return;

        v.setEnabled(false);
        if (couponItemInfo == null || couponItemInfo.length() < 5) couponItemInfo = "[]";

        if (cart_price_bottom == null) return;
        String pay_money = cart_price_bottom.getText().toString();
        if (new BigDecimal(pay_money).compareTo(BigDecimal.ZERO) <= 0) {
            UIUtils.showToastSafesShort("实际支付金额不能小于零");
            return;
        }
        LogUtils.d(productInfo);
        LogUtils.e(timeEntityId);
        if (PublicCache.loginPurchase.getIsC()!=1){
            toOrder(v);  //企业
        }else {
            toPersonOrder(v);
        }

    }
    public void toPersonOrder(View v){//个人
        LogUtils.i(freightItemInfo);
        if (deliveryType==2){
            if ((UIUtils.getDistance(mbody.getData().getPickupInfo().getLon(),mbody.getData().getPickupInfo().getLat(),PublicCache.longtitude,PublicCache.latitude)*1000)>mbody.getData().getDeliverScope()){
                UIUtils.showToastSafesShort("您的地址超出了对应提货点的配送范围了哦");
                LogUtils.i((UIUtils.getDistance(mbody.getData().getPickupInfo().getLon(),mbody.getData().getPickupInfo().getLat(),PublicCache.longtitude,PublicCache.latitude)*1000));
                LogUtils.i(""+PublicCache.longtitude,PublicCache.latitude);
                return;
            }
        }

        loading();
        getRequestPresenter().toPersonalOrder(PublicCache.loginPurchase.getLoginUserId(),paymentCustomerId,PublicCache.loginPurchase.getEntityId(),
               addressId, paymentCode, JavaMethod.transMap2Json(shippingAddressInfo_map), productInfo,
                couponItemInfo, productCount, freightItemInfo,timeEntityId,deliveryType,mbody.getData().getPickupInfo().getCommunityId(), new ResultInfoCallback<OrderPlaceBack>(this) {
                    @Override
                    public void onSuccess(OrderPlaceBack body) {
                        loadingDimss();
                        UIUtils.showToastSafesShort("订单提交成功");

                        //EventBus.getDefault().post(new WaitAddEvent());

                        SharedPreferences.Editor sp = UIUtils.getSharedPreferences("payment").edit();
                        sp.putInt("paymentCustomerId", paymentCustomerId);
                        sp.putString("paymentCustomerName",paymentCustomerName);
                        sp.putString("paymentCustomerRole",paymentCustomerRole);
                        sp.apply();

                        for (CartGoodsBean goodsBean : list_bean) {
                            goodsBean.setProductQty(0);
                            //通知购物车，购物数量变化
                            EventBus.getDefault().post(new CartEvent(goodsBean));
                        }


                        body.setCreateTime(System.currentTimeMillis());

                        OrderConfirm orderConfirm = new OrderConfirm();
                        orderConfirm.setCreateTime(body.getCreateTime());
                        orderConfirm.setOrderIds(body.getOrderIds());
                        orderConfirm.setOutTradeNo(body.getOutTradeNo());
                        orderConfirm.setOrder_no(body.getExtOrderId());
                        orderConfirm.setTotalPrice(body.getActualTotalPrice());

                        Intent intent1 = new Intent(v.getContext(), PurchaserOrderConfirmaActivity.class);
                        intent1.putExtra("CreateTime", orderConfirm.getCreateTime());
                        intent1.putExtra("OrderIds", orderConfirm.getOrderIds());
                        intent1.putExtra("OutTradeNo", orderConfirm.getOutTradeNo());
                        intent1.putExtra("Order_no", orderConfirm.getOrder_no());
                        intent1.putExtra("TotalPrice", orderConfirm.getTotalPrice());
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailed(int err, String msg) {
                        v.setEnabled(true);

                        if (err==600002){
                            if (submitOrderErrPoupWindow!=null){
                                if (submitOrderErrPoupWindow.isShowing()){
                                    return;
                                }

                            }
                            submitOrderErrPoupWindow = new SubmitOrderErrPoupWindow(msg,SubmitOrderActivity.this);
                            submitOrderErrPoupWindow.setPopupWindowFullScreen(true);//铺满
                            submitOrderErrPoupWindow.showPopupWindow();
                        }else {
                            UIUtils.showToastSafesShort(msg);
                        }
                        loadingDimss();
                    }
                });
    }
    public void toOrder(View v){
        loading();
        getRequestPresenter().toOrder(PublicCache.loginPurchase.getLoginUserId(),paymentCustomerId,PublicCache.loginPurchase.getEntityId(),
                addressId, paymentCode, JavaMethod.transMap2Json(shippingAddressInfo_map), productInfo,
                couponItemInfo, productCount, freightItemInfo ,new ResultInfoCallback<OrderPlaceBack>(this) {
                    @Override
                    public void onSuccess(OrderPlaceBack body) {
                        loadingDimss();
                        UIUtils.showToastSafesShort("订单提交成功");

                        //EventBus.getDefault().post(new WaitAddEvent());

                        SharedPreferences.Editor sp = UIUtils.getSharedPreferences("payment").edit();
                        sp.putInt("paymentCustomerId", paymentCustomerId);
                        sp.putString("paymentCustomerName",paymentCustomerName);
                        sp.putString("paymentCustomerRole",paymentCustomerRole);
                        sp.apply();

                        for (CartGoodsBean goodsBean : list_bean) {
                            goodsBean.setProductQty(0);
                            //通知购物车，购物数量变化
                            EventBus.getDefault().post(new CartEvent(goodsBean));
                        }


                        body.setCreateTime(System.currentTimeMillis());

                        OrderConfirm orderConfirm = new OrderConfirm();
                        orderConfirm.setCreateTime(body.getCreateTime());
                        orderConfirm.setOrderIds(body.getOrderIds());
                        orderConfirm.setOutTradeNo(body.getOutTradeNo());
                        orderConfirm.setOrder_no(body.getExtOrderId());
                        orderConfirm.setTotalPrice(body.getActualTotalPrice());

                        Intent intent1 = new Intent(v.getContext(), PurchaserOrderConfirmaActivity.class);
                        intent1.putExtra("CreateTime", orderConfirm.getCreateTime());
                        intent1.putExtra("OrderIds", orderConfirm.getOrderIds());
                        intent1.putExtra("OutTradeNo", orderConfirm.getOutTradeNo());
                        intent1.putExtra("Order_no", orderConfirm.getOrder_no());
                        intent1.putExtra("TotalPrice", orderConfirm.getTotalPrice());
                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailed(int orderPlaceBackResultInfo, String msg) {
                        v.setEnabled(true);
                        UIUtils.showToastSafesShort(msg);
                        loadingDimss();
                    }
                });
    }

    @Override
    public void select_way(int mdeliveryType,String delivery ) {
        tv_current_way.setText(delivery);
        deliveryType=mdeliveryType;
        initTzServiceFee(mdeliveryType,timeEntityId);
    }

    @Override
    public void select_time(int mtimeEntityId,String time) {
        int day;
        int time_now = Integer.valueOf(DateUtils.parseTime("HH"));
        //这里不用考虑下单时间，只判定4点前付款当天送达，4点后第二天送达
        if (time_now < 4) day = 0;
        else day = 1;
        second_text.setText(DateUtils.getDay("MM-dd", day) + (day>0?"明天":"今天") +time );
        timeEntityId=mtimeEntityId;
        initTzServiceFee(deliveryType,mtimeEntityId);


    }
    public void initDeliverTime(){
        getRequestPresenter().getDeliverTime(new RequestCallback<DeliverTime>() {
            @Override
            protected void onSuc(DeliverTime body) {

                if (selectPickUpTimePoupWindow!=null){
                    if (selectPickUpTimePoupWindow.isShowing()){
                        return;
                    }

                }
                selectPickUpTimePoupWindow = new SelectPickUpTimePoupWindow(SubmitOrderActivity.this, body);
                selectPickUpTimePoupWindow.setSelectPickUpTimePoupWindowListener(SubmitOrderActivity.this);
                selectPickUpTimePoupWindow.setPopupWindowFullScreen(true);//铺满
                selectPickUpTimePoupWindow.showPopupWindow();

            }
        });

        }

    @Subscribe
    public void updaterAddress(CommunityAddress itemsBean) {//编辑地址后的最新地址
        initDeliverFee(false);
    }


}
