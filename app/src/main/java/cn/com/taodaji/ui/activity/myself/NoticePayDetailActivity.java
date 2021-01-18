package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FreightParticularsNew;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.event.WaitCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesCreateActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.ui.ppw.FreightParticularsPopupWindow;
import cn.com.taodaji.viewModel.adapter.SimpleOPDetailAdapter;
import cn.com.taodaji.viewModel.vm.NoticeDetailVM;
import cn.com.taodaji.viewModel.vm.SupplierOrderDetailVM;
import tools.activity.SimpleToolbarActivity;
import tools.gprint.PrintUtils;

public class NoticePayDetailActivity extends SimpleToolbarActivity implements View.OnClickListener{
    private View mainView;
    private RecyclerView cart_item;
    private SimpleOPDetailAdapter simpleOPDetailAdapter;
    private BaseViewHolder viewHolder;

    private   WaitNoticeResultBean.DataBean.ItemsBean itemsBean;

    private List<FreightParticularsNew.DataBean> freightParticularsNewList = new ArrayList<>();

    private TextView count_image, tv_goods_money,order_no;//, cart_price, second_text;

    private LinearLayout linearLayout_ticket;// mLinearLayoutYC, linearLayout_ticket, ll_cash_pledge;

    private TextView tv_cash_coupon_used_money;
    private TextView tv_cash_coupon_used_mon;
    private TextView txt_ticket_pay;
    private TextView tv_cash_pledge_sum;
    private TextView priceSum;

    private ImageView im_shipping_eetails;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();

        simple_title.setText("详情");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_notice_pay_detail);
        body_scroll.addView(mainView);
        mainLayout.setBackgroundColor(UIUtils.getColor(R.color.bg_color));

        viewHolder = new BaseViewHolder(mainView, new NoticeDetailVM(), null);

        count_image = ViewUtils.findViewById(mainView, R.id.count_image);
        tv_goods_money = mainView.findViewById(R.id.tv_goods_money);
        tv_cash_coupon_used_money = ViewUtils.findViewById(mainView, R.id.tv_cash_coupon_used_money);//代金券使用金额
        tv_cash_coupon_used_mon = ViewUtils.findViewById(mainView, R.id.tv_cash_coupon_used_mon);//运费
        txt_ticket_pay = ViewUtils.findViewById(mainView, R.id.txt_ticket_pay);//专票税费
        tv_cash_pledge_sum = mainView.findViewById(R.id.tv_cash_pledge_sum);
        priceSum = ViewUtils.findViewById(mainView, R.id.price_sum);
        order_no = ViewUtils.findViewById(mainView, R.id.order_no);

        linearLayout_ticket = mainView.findViewById(R.id.linearLayout_ticket);
        im_shipping_eetails = ViewUtils.findViewById(mainView, R.id.im_shipping_eetails);
        im_shipping_eetails.setOnClickListener(this);

        cart_item = ViewUtils.findViewById(mainView, R.id.cart_item);
        cart_item.setLayoutManager(new LinearLayoutManager(this));
        cart_item.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(3), R.color.white));


         itemsBean=(WaitNoticeResultBean.DataBean.ItemsBean)getIntent().getSerializableExtra("data");
        if (itemsBean != null) {

            int originatorRole = itemsBean.getOriginatorRole();
            String start_role= PublicCache.getRoleType().getValueOfKey(originatorRole);

            int paymentCustomerRole =itemsBean.getPaymentCustomerRole();
            String pay_role= PublicCache.getRoleType().getValueOfKey(paymentCustomerRole);

            itemsBean.setStartNameAndRole(itemsBean.getCustomerName()+"("+start_role+")");
            itemsBean.setPayNameAndRole(itemsBean.getPaymentCustomerName()+"("+pay_role+")");

            viewHolder.setValues(itemsBean);

            //1.订单/付款成功
            //2.订单/付款超时订单关闭
            if (itemsBean.getMessageType()==1) {
                viewHolder.setText(R.id.text_pay_title,"付款时间：");
                viewHolder.setText(R.id.text_pay_time,itemsBean.getPaymentSuccessTime());
            }else  if (itemsBean.getMessageType()==2) {
                viewHolder.setText(R.id.text_pay_title,"关闭时间：");
                viewHolder.setText(R.id.text_pay_time,itemsBean.getOrderClosedTime());
            }

        }

    }

    @Override
    protected void initData() {
        super.initData();
        onStartLoading();
        if (itemsBean == null) {
            return;
        }
        //updataUnReadNotice();
        //订单明细
        getRequestPresenter().order_findOne(0, itemsBean.getExtOrderId(), new ResultInfoCallback<OrderDetail>(this) {
            @Override
            public void onSuccess(OrderDetail body) {

                order_detail(body, Integer.parseInt(body.getCouponAmount()+""));


                if (String.valueOf(tv_cash_coupon_used_mon) != null) {
//                    cart_price_bottom.setText(body.getActualTotalCost().toString());
                    tv_cash_coupon_used_mon.setText("+" + body.getTotalFreight().toString() + "元");
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

/*
    private void updataUnReadNotice(){
        addRequest(ModelRequest.getInstance(1).updateNoticeUnReadCount(itemsBean.getEntityId(), PublicCache.site_login), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                if (body.getErr()==0) {
                    EventBus.getDefault().postSticky(new NoticeCountEvent());
                }
            }
        });

    }*/


    //订单明细
    public void order_detail(OrderDetail orderDetail, int couponAmount) {
       // this.orderDetail = orderDetail;

        //获取配送费
        getYF(orderDetail.getOrderNo());

        order_no.setText(orderDetail.getOrderNo());

        simpleOPDetailAdapter = new SimpleOPDetailAdapter();
        simpleOPDetailAdapter.setPrint(false);
        simpleOPDetailAdapter.setStatusCode(orderDetail.getStatusCode());
        simpleOPDetailAdapter.setExpectDeliveredDate(orderDetail.getExpectDeliveredDate());

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
/*        simpleOPDetailAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    OrderDetail.ItemsBean itemsBean = (OrderDetail.ItemsBean) bean;
                    switch (view.getId()) {
                        case R.id.item_view:
                            GoodsDetailActivity.startActivity(view.getContext(), itemsBean.getProductId());
                            break;
                        case R.id.order_print_goods:

                            break;
                        case R.id.after_sales:
//                            long receiveTime = simpleOPDetailAdapter.getReceiveTime();
//                            if (PublicCache.getOrderState().idOfKey(orderDetail.getStatusCode()) == 1 || (PublicCache.getOrderState().idOfKey(orderDetail.getStatusCode()) == 6 && System.currentTimeMillis() - receiveTime < 60 * 60 * 3 * 1000)) {
                            if (itemsBean.getStatus() != 6 && itemsBean.getStatus() != 9) {
                                TextView after_sales = ViewUtils.findViewById(view, R.id.after_sales);
                                if (after_sales.getText().toString().equals("申请售后")) {
                                    AfterSalesCreateActivity.startActivity(view.getContext(), itemsBean);
                                }

                            } else if (itemsBean.getStatus() == 6 || itemsBean.getStatus() == 9) {
                                AfterSalesDetailActivity.startActivity(view.getContext(), -1, itemsBean.getItemId());
                            }
//                            } else UIUtils.showToastSafesShort("已超过售后申请时间");
                            break;
                    }
                    return true;
                }
                return false;
            }
        });*/

        //商品总价，数量
        count_image.setText(String.valueOf(orderDetail.getItemCount()));

        //采购商的订单信息
        tv_cash_pledge_sum.setText("+" + String.valueOf(orderDetail.getOrderForegift()) + "元");


        if (PublicCache.loginPurchase != null)
            priceSum.setText(String.valueOf(orderDetail.getActualTotalCost()));
        else if (PublicCache.loginSupplier != null) {
            priceSum.setText(String.valueOf(orderDetail.getSubtotalCost().add(orderDetail.getOrderForegift())));
        }

        tv_goods_money.setText(orderDetail.getSubtotalCost().toString() + "元");
        tv_cash_coupon_used_money.setText("-" + String.valueOf(orderDetail.getCouponAmount()) + "元");


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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.im_shipping_eetails:
                if (freightParticularsNewList == null) return;
                FreightParticularsPopupWindow freightParticularsPopupWindow = new FreightParticularsPopupWindow(im_shipping_eetails, freightParticularsNewList);
                freightParticularsPopupWindow.showAtLocation(im_shipping_eetails, Gravity.CENTER, 0, 0);
                break;
        }
    }
}
