package cn.com.taodaji.ui.activity.orderPlace;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.CustomerData;
import com.base.utils.DateUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.FreightParticularsNew;
import cn.com.taodaji.model.entity.OrderConfirm;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.OrderList;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.AfterSalesCreateEvent;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.OrderDeleteEvent;
import cn.com.taodaji.model.event.OrderDetailEvent;
import cn.com.taodaji.model.event.OrderListSucEvent;
import cn.com.taodaji.model.event.OrderPlaceCountEvent;
import cn.com.taodaji.model.event.OrderStatusEvent;
import cn.com.taodaji.model.event.PrintComplete;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.evaluate.EvaluatePurchaseActivity;
import cn.com.taodaji.ui.activity.evaluate.EvaluateSupplierActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.tdjc.MyPickupAddressActivity;
import cn.com.taodaji.ui.activity.tdjc.ServiceMoneyPopuWindow;
import cn.com.taodaji.ui.pay.PurchaserOrderConfirmaActivity;
import cn.com.taodaji.ui.ppw.FreightParticularsPopupWindow;
import cn.com.taodaji.viewModel.adapter.SimpleOPDetailAdapter;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;
import tools.gprint.PrintUtils;


/**
 * 订单详情页面
 * 提交订单入口
 * <p>
 * 订单详情入口
 * {@link #onEvent(OrderDetailEvent)}
 * <p>
 * 详情订单明细
 * {@link #order_detail(OrderDetail, int)}
 */

public class OrderDetailActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private TextView priceSum;

    private TextView order1;

    private TextView order2;

    private SimpleOPDetailAdapter simpleOPDetailAdapter;
    private PrintUtils printUtils;
    private String expectDeliveredDate,orderNo,driverTel,extOrderId;
    private int addressId,driverId;

    private SparseArrayCompat<String> list_qr = new SparseArrayCompat<>();

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        if (PublicCache.loginPurchase != null) {
            setToolBackgroundResource(R.drawable.bg_test);
            setStatusBarDrawableRes(R.drawable.bg_test);
        } else {
            setToolBarColor(R.color.blue_2898eb);
        }
    }

    private CustomerData<String, String, String> order_state = PublicCache.getOrderState();

    private View mainView;
    private View order_info;

    private TextView count_image, cart_price, tv_goods_money, second_text,tv_service_money_c,tv_pickup_way;
    private RecyclerView cart_item;
    private OrderDetail orderDetail;
    private TextView tv_address;
    private RelativeLayout address_detail_c;

    private ImageView im_shipping_eetails;
    private LinearLayout mLinearLayoutYC, linearLayout_ticket, ll_cash_pledge,linearLayout_service_money_c;

    private TextView mTextViewFuKuan, tv_ticket_pay,tv_address_c;
    private TextView tv_cash_coupon_used_money;
    private TextView tv_cash_coupon_used_mon;
    private List<FreightParticularsNew.DataBean> freightParticularsNewList = new ArrayList<>();
    private TextView txt_ticket_pay;
    private TextView tv_cash_pledge_sum;

    //司机信息（采购商才有）
    private LinearLayout line_driver_info,ll_ps_info;
    private TextView txt_driver_name,txt_driver_phone;
    private ImageView img_driver_phone;
    private ServiceMoneyPopuWindow serviceMoneyPopuWindow;
    @Override
    protected void initMainView() {

        mainView = getLayoutView(R.layout.activity_order_detail);
        body_scroll.addView(mainView);

        count_image = ViewUtils.findViewById(mainView, R.id.count_image);
        cart_price = ViewUtils.findViewById(mainView, R.id.cart_price);

        order_info = ViewUtils.findViewById(mainView, R.id.order_info);


        second_text = ViewUtils.findViewById(mainView, R.id.second_text);
        im_shipping_eetails = ViewUtils.findViewById(mainView, R.id.im_shipping_eetails);

        tv_cash_pledge_sum = mainView.findViewById(R.id.tv_cash_pledge_sum);
        tv_cash_coupon_used_money = ViewUtils.findViewById(mainView, R.id.tv_cash_coupon_used_money);//代金券使用金额
        tv_goods_money = mainView.findViewById(R.id.tv_goods_money);
        //c端相关
        linearLayout_service_money_c=mainView.findViewById(R.id.linearLayout_service_money_c);
        tv_service_money_c=mainView.findViewById(R.id.tv_service_money_c);
        tv_pickup_way=mainView.findViewById(R.id.tv_pickup_way);
        tv_address_c=mainView.findViewById(R.id.tv_address_c);

        address_detail_c=mainView.findViewById(R.id.address_detail_c);
        linearLayout_service_money_c.setOnClickListener(this);

        txt_ticket_pay = ViewUtils.findViewById(mainView, R.id.txt_ticket_pay);//专票税费

        tv_cash_coupon_used_mon = ViewUtils.findViewById(mainView, R.id.tv_cash_coupon_used_mon);//运费
        mTextViewFuKuan = ViewUtils.findViewById(mainView, R.id.tv_fukuan);
        mLinearLayoutYC = ViewUtils.findViewById(mainView, R.id.linearLayout_yc);// 要隐藏的linearlayout
        linearLayout_ticket = mainView.findViewById(R.id.linearLayout_ticket);
        tv_ticket_pay = mainView.findViewById(R.id.tv_ticket_pay);

//      freightFree = ViewUtils.findViewById(mainView, R.id.freightFree);
        cart_item = ViewUtils.findViewById(mainView, R.id.cart_item);
        cart_item.setLayoutManager(new LinearLayoutManager(this));
        cart_item.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(3), R.color.white));

        im_shipping_eetails.setOnClickListener(this);
        ll_ps_info=ViewUtils.findViewById(mainView, R.id.ll_ps_info);
        line_driver_info=ViewUtils.findViewById(mainView, R.id.line_driver_info);
        txt_driver_name=ViewUtils.findViewById(mainView, R.id.txt_driver_name);
        txt_driver_phone=ViewUtils.findViewById(mainView, R.id.txt_driver_phone);
        img_driver_phone=ViewUtils.findViewById(mainView, R.id.img_driver_phone);
        img_driver_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(6))||orderDetail.getStatusCode().equals(order_state.getKeyOfId(1))){
                    Intent intent = new Intent(OrderDetailActivity.this,DirverMapActivity.class);
                    intent.putExtra("orderDetail",orderDetail);
                    startActivity(intent);
                }else {
                    if (UserNameUtill.isPhoneNO(txt_driver_phone.getText().toString().trim())){
                        SystemUtils.callPhone(OrderDetailActivity.this,txt_driver_phone.getText().toString().trim());
                    }else {
                        UIUtils.showToastSafe("该电话无效，请去意见反馈");
                    }
                }
            }
        });

        if (PublicCache.loginSupplier != null) {
            mLinearLayoutYC.setVisibility(View.GONE);
            line_driver_info.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (printUtils != null) {
            printUtils.recycler();
        }
        super.onDestroy();
    }

    private TextView order_pay_method, tv_cash_unit, order_pay_time, order_create_time, order_no;


    //    private BigDecimal price_sum = BigDecimal.ZERO;//商品的总金额
    private BigDecimal coupon_money = BigDecimal.ZERO;     //优惠券

    @Subscribe(sticky = true)//订单详情
    public void onEvent(OrderDetailEvent event) {
        onStartLoading();
        EventBus.getDefault().removeStickyEvent(event);
        simple_title.setText("订单详情");


        //订单信息
        order_info.setVisibility(View.VISIBLE);
        order_pay_method = ViewUtils.findViewById(mainView, R.id.order_pay_method);
        order_pay_time = ViewUtils.findViewById(mainView, R.id.order_pay_time);
        order_create_time = ViewUtils.findViewById(mainView, R.id.order_create_time);
        order_no = ViewUtils.findViewById(mainView, R.id.order_no);

        View all_print = ViewUtils.getFragmentView(body_bottom, R.layout.activity_order_detail_bottom);
        body_bottom.addView(all_print);

        priceSum = ViewUtils.findViewById(all_print, R.id.price_sum);
        tv_address = ViewUtils.findViewById(all_print, R.id.tv_address);
        mTextViewFuKuan = ViewUtils.findViewById(all_print, R.id.tv_fukuan);
        tv_cash_unit = all_print.findViewById(R.id.tv_cash_unit);

        mTextViewFuKuan.setText("总金额:");
        order1 = ViewUtils.findViewById(all_print, R.id.order_1);
        order1.setOnClickListener(this);
        order2 = ViewUtils.findViewById(all_print, R.id.order_2);
        order2.setOnClickListener(this);
        title_right.setVisibility(View.GONE);




        ImageView iv_title_line = mainView.findViewById(R.id.iv_title_line);
        ImageView iv_title_line_order = mainView.findViewById(R.id.iv_title_line_order);

        View ll_cart_price_pur = mainView.findViewById(R.id.ll_cart_price_pur);
        View ll_cart_price_sup = mainView.findViewById(R.id.ll_cart_price_sup);
        TextView tv_cash_pledge_help = mainView.findViewById(R.id.tv_cash_pledge_help);
        //配送,支付方式
        if (PublicCache.loginSupplier != null) {
            iv_title_line.setImageResource(R.mipmap.ic_title_line_blue);
            iv_title_line_order.setImageResource(R.mipmap.ic_title_line_blue);

            ll_cart_price_pur.setVisibility(View.GONE);
            ll_cart_price_sup.setVisibility(View.VISIBLE);
            tv_cash_pledge_help.setVisibility(View.VISIBLE);

            tv_cash_unit.setTextColor(UIUtils.getColor(R.color.red_dark));
            priceSum.setTextColor(UIUtils.getColor(R.color.red_dark));


            ViewUtils.findViewById(mainView, R.id.send_time_group).setVisibility(View.GONE);

            ViewUtils.findViewById(mainView, R.id.pay_send_method).setVisibility(View.GONE);
            mainView.findViewById(R.id.linearLayout_zffs).setVisibility(View.GONE);

            //订单编号
            order_no.setVisibility(View.GONE);
            ViewUtils.findViewById(mainView, R.id.textView_0).setVisibility(View.GONE);

//            ViewUtils.findViewById(mainView, R.id.textView).setVisibility(View.VISIBLE);
//            ViewUtils.findViewById(mainView, R.id.hotelName).setVisibility(View.VISIBLE);

            TextView recyclerView_title = ViewUtils.findViewById(mainView, R.id.recyclerView_title);
            recyclerView_title.setText("订单明细");


        } else {
            iv_title_line.setImageResource(R.mipmap.ic_title_line);
            iv_title_line_order.setImageResource(R.mipmap.ic_title_line);

            ll_cart_price_pur.setVisibility(View.VISIBLE);
            ll_cart_price_sup.setVisibility(View.GONE);
            tv_cash_pledge_help.setVisibility(View.GONE);

            mTextViewFuKuan.setText("实付款:");

            //支付方式
            ViewUtils.findViewById(mainView, R.id.textView_4).setVisibility(View.GONE);
            mainView.findViewById(R.id.order_pay_method).setVisibility(View.GONE);

            //付款时间
            mainView.findViewById(R.id.textView_3).setVisibility(View.GONE);
            mainView.findViewById(R.id.order_pay_time).setVisibility(View.GONE);

        }

//        loading();
        //订单明细
        getRequestPresenter().order_findOne(event.getOrderId(), event.getOrderNO(), new ResultInfoCallback<OrderDetail>(this) {
            @Override
            public void onSuccess(OrderDetail body) {

                order_detail(body, event.getCouponAmount());

                if (String.valueOf(tv_cash_coupon_used_mon) != null) {
//                    cart_price_bottom.setText(body.getActualTotalCost().toString());
                    tv_cash_coupon_used_mon.setText("+" + body.getTotalFreight().toString() + "元");
                }

                if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(6))||orderDetail.getStatusCode().equals(order_state.getKeyOfId(1))){
                    img_driver_phone.setImageResource(R.mipmap.order_next);
                }

                if (PublicCache.loginPurchase != null) {
                    if (body.getTaxCost().compareTo(BigDecimal.ZERO) > 0) {
                        linearLayout_ticket.setVisibility(View.VISIBLE);
                        txt_ticket_pay.setText("+" + body.getTaxCost().toString() + "元");//要判断是否开启税票
                    } else {
                        linearLayout_ticket.setVisibility(View.GONE);
                    }
                }




            }

            @Override
            public void onFailed(int orderDetailResultInfo, String msg) {
//                loadingDimss();
            }
        });


    }

    public void getYF(String orderNo) {
        RequestPresenter.getInstance().freight_particulars_new(orderNo, new RequestCallback<FreightParticularsNew>(this) {
            @Override
            protected void onSuc(FreightParticularsNew body) {
                freightParticularsNewList.clear();
                freightParticularsNewList.addAll(body.getData());

            }

            @Override
            public void onFailed(int freightParticularsNew, String msg) {
                Log.i("1111111", msg.toString());
            }
        });
    }


    //订单明细
    public void order_detail(OrderDetail orderDetail, int couponAmount) {
        this.orderDetail = orderDetail;
        //如果是供应购商，是等待卖家发货状态，则显示蓝牙打印
        if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(4)) && PublicCache.loginSupplier != null) {
            title_right.setVisibility(View.VISIBLE);
            right_icon.setImageResource(R.mipmap.ic_print_white);
            right_textView.setTextColor(UIUtils.getColor(R.color.white));
            printUtils = new PrintUtils(this, right_textView);
            printUtils.setIsC_name(orderDetail.getReceiveAddr().getName());
        }

        //获取配送费
        getYF(orderDetail.getOrderNo());

        TextView tv_hotel_name = mainView.findViewById(R.id.tv_hotel_name);
        TextView tv_real_name = mainView.findViewById(R.id.tv_real_name);
        TextView tv_telephone = mainView.findViewById(R.id.tv_telephone);
        TextView tv_address = mainView.findViewById(R.id.tv_address);

        LinearLayout ll_b= mainView.findViewById(R.id.ll_b);
        LinearLayout ll_c= mainView.findViewById(R.id.ll_c);
        ll_c.setOnClickListener(this);
        TextView tv_address_cc = mainView.findViewById(R.id.tv_address_cc);
        TextView tv_telephone_c = mainView.findViewById(R.id.tv_telephone_c);
        TextView tv_hotel_name_c = mainView.findViewById(R.id.tv_hotel_name_c);
        TextView tv_address_phone_name = mainView.findViewById(R.id.tv_address_phone_name);


        tv_real_name.setText(orderDetail.getReceiveAddr().getName());
        tv_telephone.setText(orderDetail.getReceiveAddr().getTelephone());
        tv_address.setText(orderDetail.getReceiveAddr().getAddress());

        tv_address_cc.setText(orderDetail.getAddress());
        tv_telephone_c.setText("团长电话："+orderDetail.getPhone());


//            tv_address.setText(orderDetail.getAddress());
            if (PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()==1){
                line_driver_info.setVisibility(View.GONE);
                ll_ps_info.setVisibility(View.GONE);
                ll_b.setVisibility(View.GONE);
                tv_pickup_way.setVisibility(View.VISIBLE);
                linearLayout_service_money_c.setVisibility(View.VISIBLE);
                tv_service_money_c.setText("+"+orderDetail.getTzServiceAmount()+"元");
                if (orderDetail.getDeliveryType()==1){
                    address_detail_c.setVisibility(View.GONE);
                    ll_c.setVisibility(View.VISIBLE);
                    tv_pickup_way.setText("收货方式：客户自提");
                    tv_hotel_name_c.setText("自提点："+orderDetail.getCommunityShortName());
                }else {
                    address_detail_c.setVisibility(View.VISIBLE);
                    ll_c.setVisibility(View.VISIBLE);
                    tv_address_c.setText("收货地址："+orderDetail.getDeliverAddress()+"("+orderDetail.getStreetNumber()+")");
                    tv_pickup_way.setText("收货方式：送货上门");
                    tv_hotel_name_c.setText("配送店："+orderDetail.getCommunityShortName());
                    tv_address_phone_name.setText("收货人："+orderDetail.getReceiveAddr().getName()+orderDetail.getReceiveAddr().getTelephone());
                }


        }else {
            linearLayout_service_money_c.setVisibility(View.GONE);
            tv_hotel_name.setText(orderDetail.getReceiveAddr().getHotelName());
            tv_pickup_way.setVisibility(View.GONE);
            ll_c.setVisibility(View.GONE);
            ll_b.setVisibility(View.VISIBLE);
            address_detail_c.setVisibility(View.GONE);
        }


        initTitle(orderDetail);

        second_text.setText(orderDetail.getExpectDeliveredDate() + " " + orderDetail.getExpectDeliveredEarliestTime() + "至" + orderDetail.getExpectDeliveredLatestTime());
        order_no.setText(orderDetail.getOrderNo());
        order_create_time.setText(orderDetail.getCreateTime());
        //order_pay_method.setText(orderDetail.getPaymentMethod().getLabel());

        String order_pay_time_s = getOrderPayTime(orderDetail);
        if (TextUtils.isEmpty(order_pay_time_s)) {
            ViewUtils.findViewById(mainView, R.id.textView_3).setVisibility(View.GONE);
            order_pay_time.setVisibility(View.GONE);
        } else order_pay_time.setText(order_pay_time_s);


        simpleOPDetailAdapter = new SimpleOPDetailAdapter();
        simpleOPDetailAdapter.setStatusCode(orderDetail.getStatusCode());
        simpleOPDetailAdapter.setExpectDeliveredDate(orderDetail.getExpectDeliveredDate());
        //确认收货时间
/*        for (OrderDetail.HistoryBean bean : orderDetail.getHistory()) {
            if (bean.getStatus().equals(PublicCache.getOrderState().getKeyOfId(6))) {
                simpleOPDetailAdapter.setExpectDeliveredDate(DateUtils.dateStringToLong(bean.getCreatedAt(), "yyyy-MM-dd HH:mm"));
                break;
            }
        }*/

        cart_item.setAdapter(simpleOPDetailAdapter);
        List<OrderDetail.ItemsBean> items = orderDetail.getItems();
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                OrderDetail.ItemsBean itemBean = items.get(i);
                itemBean.setIsUseCoupon(couponAmount);
                itemBean.setReceiveAddr(orderDetail.getReceiveAddr());
                itemBean.setCustomerLineCode(orderDetail.getCustomerLineCode());
            }
        }
        simpleOPDetailAdapter.notifyDataSetChanged(orderDetail.getItems());
        simpleOPDetailAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {

                if (onclickType == 0) {
                    OrderDetail.ItemsBean itemsBean = (OrderDetail.ItemsBean) bean;
                    switch (view.getId()) {
                        case R.id.item_view:
                            GoodsDetailActivity.startActivity(view.getContext(), itemsBean.getProductId());
                            break;
                        case R.id.order_print_goods:
                           /* if (PublicCache.loginPurchase==null)return true;
                            if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                return true;
                            }*/
//                            if (itemsBean.getPrintState() == 1) return true;

                            if (printUtils.isPrintDone()) printUtils.printOne(itemsBean);
                            updatePrintStatus(itemsBean.getItemId()+"");
                            break;
                        case R.id.after_sales:
                            boolean goAfter=false;
                            if (PublicCache.loginPurchase != null) {
                                goAfter=true;
                                if (PublicCache.loginPurchase.getEmpRole() == 2 || PublicCache.loginPurchase.getEmpRole() == 5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole()) + "没有该操作权限");
                                    goAfter=false;
                                }
                            }else if (PublicCache.loginSupplier != null) {
                                goAfter=true;
                            }
//                            long receiveTime = simpleOPDetailAdapter.getReceiveTime();
//                            if (PublicCache.getOrderState().idOfKey(orderDetail.getStatusCode()) == 1 || (PublicCache.getOrderState().idOfKey(orderDetail.getStatusCode()) == 6 && System.currentTimeMillis() - receiveTime < 60 * 60 * 3 * 1000)) {
                            if (goAfter) {
                                if (itemsBean.getStatus() != 6 && itemsBean.getStatus() != 9) {
                                    TextView after_sales = ViewUtils.findViewById(view, R.id.after_sales);
                                    if (after_sales.getText().toString().equals("申请售后")) {
                                        AfterSalesCreateActivity.startActivity(view.getContext(), itemsBean);
                                    }

                                } else if (itemsBean.getStatus() == 6 || itemsBean.getStatus() == 9) {
                                    AfterSalesDetailActivity.startActivity(view.getContext(), -1, itemsBean.getItemId());
                                }
                            }


//                            } else UIUtils.showToastSafesShort("已超过售后申请时间");
                            break;
                    }
                    return true;
                }
                return false;
            }
        });

        //商品总价，数量
        count_image.setText(String.valueOf(orderDetail.getItemCount()));
        cart_price.setText(orderDetail.getSubtotalCost().toString());
        txt_driver_name.setText(orderDetail.getItems().get(0).getDriverName());
        txt_driver_phone.setText(orderDetail.getItems().get(0).getDriverTel());

        orderNo = orderDetail.getOrderNo();
        driverTel = orderDetail.getItems().get(0).getDriverTel();
        addressId = orderDetail.getCustomerAddrId();
        driverId = orderDetail.getItems().get(0).getDriverId();
        extOrderId = orderDetail.getExtOrderId();
        //供应商的商品和押金
        TextView tv_goods_price_sum = mainView.findViewById(R.id.tv_goods_price_sum);
        TextView tv_cash_pledge_price_sum = mainView.findViewById(R.id.tv_cash_pledge_price_sum);
        tv_goods_price_sum.setText(String.valueOf(orderDetail.getSubtotalCost()));
        tv_cash_pledge_price_sum.setText(String.valueOf(orderDetail.getOrderForegift()));


        //采购商的订单信息
        tv_cash_pledge_sum.setText("+" + String.valueOf(orderDetail.getOrderForegift()) + "元");

        if (PublicCache.loginSupplier != null && orderDetail.getOrderForegift().compareTo(BigDecimal.ZERO) > 0)
            tv_cash_unit.setText("元（含押金）");

        if (PublicCache.loginPurchase != null)
            priceSum.setText(String.valueOf(orderDetail.getActualTotalCost()));
        else if (PublicCache.loginSupplier != null) {
            priceSum.setText(String.valueOf(orderDetail.getSubtotalCost().add(orderDetail.getOrderForegift())));
        }

        coupon_money = orderDetail.getCouponAmount();

        tv_goods_money.setText(orderDetail.getSubtotalCost().toString() + "元");

        tv_cash_coupon_used_money.setText("-" + String.valueOf(orderDetail.getCouponAmount()) + "元");

        //采购待付款
        if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(0))) {
            //如果是采购商，且不是财务，是待付款状态，则显示编辑订单
            if (PublicCache.loginPurchase != null) {

                if (PublicCache.loginPurchase.getEmpRole() != 2||PublicCache.loginPurchase.getEmpRole() != 5) {
                    order1.setVisibility(View.VISIBLE);
                    order1.setText("编辑订单");
                } else order1.setVisibility(View.GONE);

                body_bottom.setVisibility(View.VISIBLE);
                order2.setText("立即付款");
                if (tv_address != null) {
                    tv_address.setVisibility(View.VISIBLE);
                    OrderDetail.ReceiveAddrBean body = orderDetail.getReceiveAddr();
                    tv_address.setText(body.getAddress() + body.getHotelName());
                }

            } else {
                // body_bottom.setVisibility(View.GONE);
                order1.setVisibility(View.GONE);
                order2.setVisibility(View.GONE);
            }

            //采购待收货
        } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(1))) {
            body_bottom.setVisibility(View.VISIBLE);
            order1.setText("再来一单");
            order2.setText("确认收货");
            order1.setVisibility(View.GONE);
            if (PublicCache.loginSupplier != null) {
                //body_bottom.setVisibility(View.INVISIBLE);
                order1.setVisibility(View.GONE);
                order2.setVisibility(View.GONE);
            }


        }
        //等待买家评价
        else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(2))) {
            body_bottom.setVisibility(View.VISIBLE);
            if (PublicCache.loginPurchase != null) {
                order1.setText("再来一单");
                order2.setText("评价");
                //order2.setVisibility(View.GONE);
            } else {
                // body_bottom.setVisibility(View.GONE);
                order1.setVisibility(View.GONE);
                order2.setVisibility(View.GONE);
            }
        }
        //待卖家确认
        else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(3)) && PublicCache.loginPurchase != null) {
            body_bottom.setVisibility(View.VISIBLE);

            if (PublicCache.loginPurchase == null) {
                order1.setVisibility(View.GONE);
                order2.setText("批量发货");
                order2.setBackgroundColor(UIUtils.getColor(R.color.blue_2898eb));
            } else {
                order1.setText("再来一单");
//                order2.setText("取消订单");
                order2.setVisibility(View.GONE);
            }


        } //等待卖家发货
        else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(4)) && PublicCache.loginSupplier != null) {
            body_bottom.setVisibility(View.VISIBLE);

            if (PublicCache.loginPurchase == null) {
                order1.setVisibility(View.GONE);
                order2.setText("批量发货");
            } else {
                order1.setText("再来一单");
//                order2.setText("取消订单");

            }

        } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(5))) {
            body_bottom.setVisibility(View.VISIBLE);
            if (PublicCache.loginPurchase != null) {
                order1.setText("再来一单");
//                order2.setText("评价");
                order2.setVisibility(View.GONE);
            } else {
                order1.setVisibility(View.GONE);
                order2.setText("评价");
                //  order2.setVisibility(View.GONE);
            }

        } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(6))) {
            body_bottom.setVisibility(View.VISIBLE);
            order2.setText("评价");
            if (PublicCache.loginPurchase != null) {
                order1.setText("再来一单");
                //order2.setVisibility(View.GONE);
            } else {
                order1.setVisibility(View.GONE);
                // order2.setVisibility(View.GONE);
            }
        }

        //交易完成
        else {
            if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(7))) {
                body_bottom.setVisibility(View.VISIBLE);
                if (PublicCache.loginPurchase != null) {
                    order1.setText("再来一单");
                    order2.setVisibility(View.GONE);
                } else {
                    order1.setVisibility(View.GONE);
                    order2.setVisibility(View.GONE);
                }
                //交易关闭
            } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(8))) {
                body_bottom.setVisibility(View.VISIBLE);
                if (PublicCache.loginPurchase != null) {
                    order1.setText("再来一单");
                    order2.setText("删除订单");
                } else {
                    order1.setVisibility(View.GONE);
                    order2.setVisibility(View.GONE);
                }
                //交易取消
            } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(9))) {
                body_bottom.setVisibility(View.VISIBLE);
                if (PublicCache.loginPurchase != null) {
                    order1.setText("再来一单");
                    order2.setVisibility(View.GONE);
                } else {
                    order1.setVisibility(View.GONE);
                    order2.setVisibility(View.GONE);
                }
            } else if (orderDetail.getStatusCode().equals(order_state.getKeyOfId(11))) {
                body_bottom.setVisibility(View.VISIBLE);
                order1.setText("再来一单");
                order2.setText("确认收货");
                order1.setVisibility(View.GONE);
                if (PublicCache.loginSupplier != null) {
                    //body_bottom.setVisibility(View.INVISIBLE);
                    order1.setVisibility(View.GONE);
                    order2.setVisibility(View.GONE);
                }


            }

            if (order1.getText().toString().equals("再来一单") || order1.getText().toString().equals("编辑订单")) {
                long createTime = DateUtils.dateStringToLong(orderDetail.getCreateTime(), "yyyy-MM-dd HH:mm");
                long now = DateUtils.dateStringToLong("2018-04-18", "yyyy-MM-dd");
                if (createTime < now) {
                    order1.setVisibility(View.GONE);
                }
            }


        }

    }


    @Subscribe
    public void onEvent(AfterSalesCreateEvent event) {
        if (event != null) {
            int orderItemId = event.getOrderItemId();
            for (int i = 0; i < simpleOPDetailAdapter.getItemCount(); i++) {
                OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) simpleOPDetailAdapter.getListBean(i);
                if (bean == null) continue;
                if (bean.getItemId() == orderItemId) {
                    simpleOPDetailAdapter.update(i, "status", 6);
                    break;
                }
            }
        }
    }

    //接收售后取消事件
    @Subscribe
    public void onEvent(AfterSalesCancleEvent event) {
        int orderItemId = event.getOrderItemId();
        for (int i = 0; i < simpleOPDetailAdapter.getItemCount(); i++) {
            if (simpleOPDetailAdapter.getListBean(i) instanceof OrderDetail.ItemsBean) {
                OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) simpleOPDetailAdapter.getListBean(i);
                if (bean.getItemId() == orderItemId) {
                    simpleOPDetailAdapter.update(i, "status", 4);
                    break;
                }
            }
        }
    }

    private String getOrderPayTime(OrderDetail orderDetail) {
        for (OrderDetail.HistoryBean bean : orderDetail.getHistory()) {
            if (bean.getStatus().equals(PublicCache.getOrderState().getKeyOfId(3))) {
                return bean.getCreatedAt();
            }
        }
        return "";
    }

    private void initTitle(OrderDetail orderDetail) {

        View ll_order_title_pur = ViewUtils.findViewById(mainView, R.id.ll_order_title_pur);
        View ll_order_title_sup = ViewUtils.findViewById(mainView, R.id.ll_order_title_sup);
        int state = order_state.idOfKey(orderDetail.getStatusCode());
        if (PublicCache.loginSupplier != null) {
            ll_order_title_pur.setVisibility(View.GONE);
            ll_order_title_sup.setVisibility(View.VISIBLE);

            String value;
            if (PublicCache.initializtionData != null) {
                value = PublicCache.initializtionData.getSupplier_delivery_time();
            } else value = "";

            TextView tv_supply_send_time = ll_order_title_sup.findViewById(R.id.tv_supply_send_time);
            tv_supply_send_time.setText("请于" + orderDetail.getExpectDeliveredDate() + "  " + value + "之前送达配送中心");
            TextView tv_buy_count = ll_order_title_sup.findViewById(R.id.tv_buy_count);
            if (orderDetail.getBuyNumber()==0)  tv_buy_count.setText("新用户");
            else if (orderDetail.getBuyNumber()>=10)  tv_buy_count.setText("老用户");
            else tv_buy_count.setText("第" + orderDetail.getBuyNumber() + "次购买");

        } else {

            ll_order_title_pur.setVisibility(View.VISIBLE);
            ll_order_title_sup.setVisibility(View.GONE);

            TextView tv_status = ll_order_title_pur.findViewById(R.id.tv_status);
            TextView tv_info = ll_order_title_pur.findViewById(R.id.tv_info);
            ImageView iv_order_status = ll_order_title_pur.findViewById(R.id.iv_order_status);


            if (state == 3) tv_status.setText("订单状态：待发货");
            else if (state == 5) {
                tv_status.setText("订单状态：交易成功");
            } else tv_status.setText("订单状态：" + order_state.getDesc(orderDetail.getStatusCode()));


            if (state == 0) {

                if (PublicCache.initializtionData != null) {
                    //  BigDecimal limited_hour = new BigDecimal(PublicCache.initializtionData.getCount_down_time());
                    int minute = PublicCache.initializtionData.getCount_down_time();
                    String ss = DateUtils.getMinute(DateUtils.dateStringToLong(orderDetail.getCreateTime(), "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm", minute);
                    tv_info.setText(ss + " 前未付款，订单将自动关闭");
                }


            } else if (state == 1) {
                tv_info.setText("配送小哥正在向你奔来.");
            } else if (state == 4 || state == 3) {
                tv_info.setText("卖家正在精心挑选备货中");
            } else {
                tv_info.setText("淘大集：期待再次为您服务!");
            }

            switch (state) {
                case 0:
                    iv_order_status.setImageResource(R.mipmap.ic_pay_wait_pur);
                    break;
                case 1:
                    iv_order_status.setImageResource(R.mipmap.ic_receive_wait_pur);
                    break;
                case 2:
                    iv_order_status.setImageResource(R.mipmap.ic_deal_succ_pur);
                    break;
                case 3:
                    iv_order_status.setImageResource(R.mipmap.ic_send_goods_wait_pur);
                    break;
                case 4:
                    iv_order_status.setImageResource(R.mipmap.ic_send_goods_wait_pur);
                    break;
                case 5:
                case 6:
                case 7:
                    iv_order_status.setImageResource(R.mipmap.ic_deal_succ_pur);
                    break;
                case 8:
                    iv_order_status.setImageResource(R.mipmap.ic_order_close_pur);
                    break;
                case 9:
                    iv_order_status.setImageResource(R.mipmap.ic_order_cancel_pur);
                    break;
                case 10:
                    iv_order_status.setImageResource(R.mipmap.ic_send_goods_wait_pur);
                    break;
                case 11:
                    iv_order_status.setImageResource(R.mipmap.ic_receive_wait_pur);
                    break;
            }


        }
    }


    @Override
    protected void initData() {
        //是否初始化成功
        if (PublicCache.initializtionData == null) {
            // setDataCallBackListener(this);
            initializtionData();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onClick(final View view) {

        switch (view.getId()) {
            case R.id.linearLayout_service_money_c:
                if (serviceMoneyPopuWindow!=null){
                    if (serviceMoneyPopuWindow.isShowing()){
                        return;
                    }

                }
                serviceMoneyPopuWindow = new ServiceMoneyPopuWindow(null,this);
                serviceMoneyPopuWindow.setPopupWindowFullScreen(true);//铺满
                serviceMoneyPopuWindow.showPopupWindow();
                break;
            case R.id.send_time:
/*                SendTimePopupWindow sendTimePopupWindow = new SendTimePopupWindow(view) {
                    @Override
                    public void setResult(Object object) {
                        time_state = (int) object;
                        initShippingAddressInfo(time_state);
                    }
                };
                sendTimePopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                sendTimePopupWindow.setTimeState(time_state);*/
                break;

            case R.id.place_order:

                break;

            case R.id.order_1:
                if (PublicCache.loginPurchase==null)return ;
                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                    return ;
                }
                //采购待付款  取消订单
                String ss = order1.getText().toString();
                switch (ss) {
                    case "编辑订单":
                        if (PublicCache.loginPurchase != null&&PublicCache.loginPurchase.getLoginUserId()!=orderDetail.getOriginalorCustomerId()) {
                            UIUtils.showToastSafesShort("您不是订单的创建人");
                            return ;
                        }
                        AlertDialog.Builder builder = ViewUtils.showDialog(this, "编辑订单", "您确定要撤销订单到购物车重新编辑？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loading();
                                getRequestPresenter().order_deleteReally(orderDetail.getOrderIds(), new RequestCallback<ResultInfo>() {
                                    @Override
                                    public void onSuc(ResultInfo body) {
                                        List<OrderDetail.ItemsBean> list = orderDetail.getItems();
                                        CartModel cartModel = CartModel.getInstance();
                                        int count = 0;
                                        for (OrderDetail.ItemsBean goodsBean : list) {
                                            CartGoodsBean cartGoodsBean = cartModel.getDataBean(goodsBean.getSpecId());
                                            if (cartGoodsBean == null) {
                                                cartGoodsBean = new CartGoodsBean();
                                                cartGoodsBean.setSelected(true);
                                                cartGoodsBean.setSpecId(goodsBean.getSpecId());
                                                cartGoodsBean.setProductId(goodsBean.getProductId());
                                                cartGoodsBean.setProductName(goodsBean.getName());
                                                cartGoodsBean.setProductImage(goodsBean.getImage());
                                                cartGoodsBean.setNickName(goodsBean.getNickName());
                                                //这里是模拟的数据，会被替换掉
                                                cartGoodsBean.setItemStatus(0);
                                                cartGoodsBean.setStatus(1);
                                                cartGoodsBean.setStock(999);
                                                cartGoodsBean.setStoreStatus(0);
                                            }
                                            cartGoodsBean.setProductQty(goodsBean.getAmount().intValue());
                                            cartGoodsBean.setProductPrice(goodsBean.getPrice());

                                            cartGoodsBean.setIsP(goodsBean.getIsP());
                                            cartGoodsBean.setCategoryId(goodsBean.getCategoryId());
                                            cartGoodsBean.setCommodityId(goodsBean.getCommodityId());
                                            //通知购物车，购物数量变化
                                            EventBus.getDefault().post(new CartEvent(cartGoodsBean).setUpdate(true));
                                        }
                                        UIUtils.showToastSafe("已撤销订单到购物车");
                                        //通知个人中心订单数量变化
                                        EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                        MenuToolbarActivity.goToPage(3);
                                        loadingDimss();
                                    }

                                    @Override
                                    public void onFailed(int resultInfo, String msg) {
                                        UIUtils.showToastSafe(msg);
                                        loadingDimss();
                                    }
                                });
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        break;
                    case "再来一单":
                        AlertDialog.Builder builder1 = ViewUtils.showDialog(this, "再来一单", "您确定要将该订单添加到购物车？");
                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                List<OrderDetail.ItemsBean> list = orderDetail.getItems();
                                CartModel cartModel = CartModel.getInstance();

                                for (OrderDetail.ItemsBean goodsBean : list) {
                                    CartGoodsBean cartGoodsBean = cartModel.getDataBean(goodsBean.getSpecId());
                                    if (cartGoodsBean == null) {
                                        cartGoodsBean = new CartGoodsBean();
                                        cartGoodsBean.setSelected(true);
                                        cartGoodsBean.setSpecId(goodsBean.getSpecId());
                                        cartGoodsBean.setProductId(goodsBean.getProductId());
                                        cartGoodsBean.setProductName(goodsBean.getName());
                                        cartGoodsBean.setProductImage(goodsBean.getImage());
                                        cartGoodsBean.setNickName(goodsBean.getNickName());
                                        //这里是模拟的数据，会被替换掉
                                        cartGoodsBean.setItemStatus(0);
                                        cartGoodsBean.setStatus(1);
                                        cartGoodsBean.setStock(999);
                                        cartGoodsBean.setStoreStatus(0);
                                        cartGoodsBean.setIsP(goodsBean.getIsP());
                                        cartGoodsBean.setCategoryId(goodsBean.getCategoryId());
                                        cartGoodsBean.setCommodityId(goodsBean.getCommodityId());
                                    }
                                    cartGoodsBean.setProductQty(goodsBean.getAmount().intValue());
                                    cartGoodsBean.setProductPrice(goodsBean.getPrice());

                                    //通知购物车，购物数量变化
                                    EventBus.getDefault().post(new CartEvent(cartGoodsBean).setUpdate(true));
                                }
                                UIUtils.showToastSafe("已添加订单到购物车");
                                MenuToolbarActivity.goToPage(3);

                            }
                        });
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.show();
                        break;
                    case "取消订单":
                        loading();
                        getRequestPresenter().modifyStatus("trade_canceled", orderDetail.getOrderIds(), orderDetail.getOrderNo(), new RequestCallback<OrderStatusEvent>() {
                            @Override
                            public void onSuc(OrderStatusEvent body) {
                                loadingDimss();
                                //通知个人中心更新数量变化
                                EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                //通知订单列表，操作成功
                                EventBus.getDefault().post(new OrderListSucEvent(orderDetail.getOutTradeNo()));
                                finish();
                            }

                            @Override
                            public void onFailed(int event, String msg) {
                                UIUtils.showToastSafesShort(msg);
                                loadingDimss();
                            }
                        });
                        break;
                    case "确认订单":
                        loading();
                        getRequestPresenter().modifyStatus("wait_seller_send_goods", orderDetail.getOrderIds(), orderDetail.getOrderNo(), new RequestCallback<OrderStatusEvent>() {
                            @Override
                            public void onSuc(OrderStatusEvent body) {
                                loadingDimss();
                                //通知个人中心更新数量变化
                                EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                //通知订单列表，操作成功
                                EventBus.getDefault().post(new OrderListSucEvent(orderDetail.getOutTradeNo()));
                                finish();
                            }

                            @Override
                            public void onFailed(int event, String msg) {
                                UIUtils.showToastSafesShort(msg);
                                loadingDimss();
                            }
                        });
                        break;
                }
                break;
            case R.id.order_2:
                String sstr = order2.getText().toString();
                switch (sstr) {
                    case "立即付款":
                        if (PublicCache.loginPurchase==null)return ;
                        if (PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return ;
                        }
                        OrderConfirm orderPlaceBack = new OrderConfirm();
                        orderPlaceBack.setOrderIds(orderDetail.getOrderIds());
                        orderPlaceBack.setOutTradeNo(orderDetail.getOutTradeNo());
                        orderPlaceBack.setOrder_no(orderDetail.getOrderNo());
                        orderPlaceBack.setTotalPrice(String.valueOf(orderDetail.getActualTotalCost()));
                        orderPlaceBack.setCreateTime(DateUtils.dateStringToLong(orderDetail.getCreateTime(), "yyyy-MM-dd HH:mm"));
//                        OrderConfirmActivity.startActivity(this, orderPlaceBack);
//                        PurchaserOrderConfirmaActivity.startActivity(view.getContext(), orderPlaceBack);
                        EventBus.getDefault().post(orderPlaceBack);
                        Intent intent2 = new Intent(view.getContext(), PurchaserOrderConfirmaActivity.class);
                        intent2.putExtra("CreateTime", orderPlaceBack.getCreateTime());
                        intent2.putExtra("OrderIds", orderPlaceBack.getOrderIds());
                        intent2.putExtra("OutTradeNo", orderPlaceBack.getOutTradeNo());
                        intent2.putExtra("Order_no", orderPlaceBack.getOrder_no());
                        intent2.putExtra("TotalPrice", orderPlaceBack.getTotalPrice());
                        startActivity(intent2);
                        finish();
                        break;
                    case "确认收货":
                        if (PublicCache.loginPurchase==null)return ;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return ;
                        }
                        loading();
                        getRequestPresenter().modifyStatus("trade_success", orderDetail.getOrderIds(), orderDetail.getOrderNo(), new RequestCallback<OrderStatusEvent>() {
                            @Override
                            public void onSuc(OrderStatusEvent body) {
                                loadingDimss();
                                //通知个人中心更新数量变化
                                EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                //通知订单列表，操作成功
                                EventBus.getDefault().post(new OrderListSucEvent(orderDetail.getOutTradeNo()));
                                finish();
                            }

                            @Override
                            public void onFailed(int event, String msg) {
                                UIUtils.showToastSafesShort(msg);
                                loadingDimss();
                            }
                        });
                        break;
                    case "批量发货":
                      /*  if (PublicCache.loginPurchase==null)return ;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return ;
                        }*/
                      StringBuffer sb = new StringBuffer();
                        List<OrderDetail.ItemsBean> list11 = orderDetail.getItems();
                        List<OrderDetail.ItemsBean> listPrint = new ArrayList<>();
                        for (OrderDetail.ItemsBean itemsBean : list11) {
                            if (itemsBean.getStatus() != 6 && itemsBean.getStatus() != 9 && itemsBean.getStatus() != 11) {
//                                if (itemsBean.getPrintState() != 2)
                                listPrint.add(itemsBean);
                                sb.append(itemsBean.getItemId()+",");
                            }
                        }
                        if (0 == listPrint.size()) {
                            UIUtils.showToastSafesShort("没有需要发货的商品");
                            return;
                        }
                        if (ClickCheckedUtil.onClickChecked(1000)) {
                            printUtils.printList(listPrint);
                            updatePrintStatus(sb.substring(0,sb.length()-1));
                        }
                        LogUtils.e(listPrint.get(0).getCustomerMobile());
                        break;

                    case "删除订单":
                        if (PublicCache.loginPurchase==null)return ;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return ;
                        }
                        loading();
                        getRequestPresenter().order_delete(orderDetail.getOrderIds(), new RequestCallback<OrderDeleteEvent>() {
                            @Override
                            public void onSuc(OrderDeleteEvent body) {
                                loadingDimss();
                                //通知个人中心更新数量变化
                                EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                //通知订单列表，操作成功
                                EventBus.getDefault().post(new OrderListSucEvent(orderDetail.getOutTradeNo()));
                                finish();
                            }

                            @Override
                            public void onFailed(int orderDeleteEvent, String msg) {
                                loadingDimss();
                                UIUtils.showToastSafesShort(msg);
                            }
                        });
                        break;

                    case "评价":
                        if (PublicCache.loginPurchase==null)return ;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return ;
                        }
                        OrderList.ItemsBean bean = new OrderList.ItemsBean();
                        bean.setOutTradeNo(orderDetail.getOutTradeNo());
                        bean.setOrderNo(orderDetail.getOrderNo());
                        if (PublicCache.loginPurchase != null) {
                            List<CartGoodsBean> list = new ArrayList<>();
                            for (OrderDetail.ItemsBean itemsBean : orderDetail.getItems()) {
                                CartGoodsBean cbean = new CartGoodsBean();
                                cbean.setProductId(itemsBean.getProductId());
                                cbean.setProductImage(itemsBean.getImage());
                                cbean.setStoreId(itemsBean.getStoreId());
                                cbean.setItemId(itemsBean.getItemId());
                                cbean.setOrderId(itemsBean.getOrderId());
                                list.add(cbean);
                            }
                            bean.setExtraField(list);
                            EventBus.getDefault().postSticky(bean);
                            Intent intent1 = new Intent(this, EvaluatePurchaseActivity.class);
                            startActivity(intent1);
                        } else if (PublicCache.loginSupplier != null) {
                            bean.setCustomerLogo(orderDetail.getCustomerLogo());
                            bean.setCustomerName(orderDetail.getCustomerName());
                            bean.setBuyNumber(orderDetail.getBuyNumber());

                            bean.setOrderId(orderDetail.getOrderId());
                            Intent intent1 = new Intent(this, EvaluateSupplierActivity.class);
                            intent1.putExtra("data", bean);
                            startActivity(intent1);
                        }
                        break;
                }
                break;
            case R.id.im_shipping_eetails:
                if (freightParticularsNewList == null) return;
                FreightParticularsPopupWindow freightParticularsPopupWindow = new FreightParticularsPopupWindow(im_shipping_eetails, freightParticularsNewList);
                freightParticularsPopupWindow.showAtLocation(im_shipping_eetails, Gravity.CENTER, 0, 0);
                break;
            case R.id.ll_c:
                Intent intent=new Intent(getBaseActivity(), MyPickupAddressActivity.class);//传提货点经纬度，地址
                intent.putExtra("lat",orderDetail.getCcLat());
                intent.putExtra("lon",orderDetail.getCcLon());
                intent.putExtra("communityShortName",orderDetail.getCommunityShortName());
                intent.putExtra("address",orderDetail.getDeliverAddress());
                startActivity(intent);
                break;
        }

    }

    public static <T> void startActivity(Context context, T event) {
        EventBus.getDefault().postSticky(event);
        ActivityManage.setTopActivity(OrderDetailActivity.class);

/*        Intent intent = new Intent(context, OrderDetailActivity.class);
        context.startActivity(intent);*/
    }

    public void updatePrintStatus(String itemIds){
        RequestPresenter.getInstance().updatePrintStatus(itemIds, new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {

            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

}
