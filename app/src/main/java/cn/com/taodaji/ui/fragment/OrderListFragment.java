package cn.com.taodaji.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DateUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.BaseRecyclerViewAdapter;
import com.base.viewModel.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.OrderConfirm;
import cn.com.taodaji.model.entity.OrderList;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.AfterSalesCreateEvent;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.EvaluateWaitCountEvent;
import cn.com.taodaji.model.event.OrderDeleteEvent;
import cn.com.taodaji.model.event.OrderDetailEvent;
import cn.com.taodaji.model.event.OrderListSucEvent;
import cn.com.taodaji.model.event.OrderPlaceCountEvent;
import cn.com.taodaji.model.event.OrderStatusEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.evaluate.EvaluatePurchaseActivity;
import cn.com.taodaji.ui.activity.evaluate.EvaluateSupplierActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesCreateActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderDetailActivity;
import cn.com.taodaji.ui.pay.PurchaserOrderConfirmaActivity;
import cn.com.taodaji.viewModel.adapter.SimpleOrderListAdapter;
import tools.activity.DataBaseActivity;
import tools.activity.MenuToolbarActivity;
import tools.extend.PhoneUtils;
import tools.fragment.LoadMoreRecyclerViewFragment;

import static com.base.viewModel.adapter.GroupRecyclerAdapter.TYPE_GROUP;

/**
 * 订单列表Fragment
 **/
public class OrderListFragment extends LoadMoreRecyclerViewFragment implements OnItemClickListener {

    //    private SimpleOrderListAdapter simpleOrderListAdapter;
    private SimpleOrderListAdapter simpleOrderListAdapter;
    private String status;
    private int status_code;
    public static boolean isCreateAfterSale = false;
    private boolean f;

    @Override
    public void initData() {
//        simpleOrderListAdapter = new SimpleOrderListAdapter();
        simpleOrderListAdapter = new SimpleOrderListAdapter();
        simpleOrderListAdapter.setChildDefaultCount(2);
        simpleOrderListAdapter.setFold(true);
        simpleOrderListAdapter.setItemClickListener(this);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.setBackgroundColor(UIUtils.getColor(R.color.gray_f2));
        recycler_targetView.setAdapter(simpleOrderListAdapter);
        recycler_targetView.closeDefaultAnimator();

        long now_12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(0, "yyyy-MM-dd") + " 12:00:00");
        long timer_12 = now_12 - System.currentTimeMillis();//距当天中午12点的时间还剩下多少秒

        if (timer_12 > 0)
            recycler_targetView.postDelayed(() -> simpleOrderListAdapter.notifyDataSetChanged(), timer_12 + 1000);


        status = status_code == -1 ? "" : PublicCache.getOrderState().getKeyOfId(status_code);
        if (status_code == 4) {
            if (PublicCache.loginPurchase != null)
                status += "," + "wait_seller_confirm_goods";//PublicCache.getOrderState().getKeyOfId(3);
        } else if (status_code == 2 || status_code == 5) {
            status += "," + PublicCache.getOrderState().getKeyOfId(6);
        }

        if (PublicCache.loginPurchase != null) {
            loadMoreUtil.setZeroDataView(R.mipmap.no_order, "您还没有相关的订单");
        } else if (PublicCache.loginSupplier != null) {
            loadMoreUtil.setZeroDataView(R.mipmap.no_order_supplier, "您还没有相关的订单");
        }


        swipeToLoadLayout.setRefreshing(true);
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void getData(int pn) {
        LogUtils.e(f);

        getRequestPresenter().order_pageList(status, pn, 5, new ResultInfoCallback<OrderList>() {
            @Override
            public void onSuccess(OrderList body) {
                stop();
                if (status_code == 2) {
                    EventBus.getDefault().post(new EvaluateWaitCountEvent(body.getTotal()));
                }

                if (loadMoreUtil.isUpdate) loadingDimss();
                if (ListUtils.isEmpty(body.getItems())){
                    return;
                }
                loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());

            }

            @Override
            public void onFailed(int orderListResultInfo, String msg) {
                stop();
            }
        });
    }

    @Override
    public void onUserVisible() {
        if (simpleOrderListAdapter != null && simpleOrderListAdapter.getRealCount() == 0)
            super.onUserVisible();
        //初始化数据
        if (PublicCache.initializtionData == null) {
            DataBaseActivity activity = (DataBaseActivity) getActivity();
            if (activity != null) {
                activity.initializtionData();
            }
        }
    }

    //接收订单操作成功事件
    @Subscribe
    public void onEvent(OrderListSucEvent event) {
        String outTradeNo = event.getOutTradeNo();

        if (isVisible() && getUserVisibleHint()) {
            if (simpleOrderListAdapter == null) {
                return;
            }
            int position = -1;
            for (int i = 0; i < simpleOrderListAdapter.getItemCount(); i++) {
                if (simpleOrderListAdapter.getListBean(i) instanceof OrderList.ItemsBean) {
                    OrderList.ItemsBean bean = (OrderList.ItemsBean) simpleOrderListAdapter.getListBean(i);
                    if (bean.getOutTradeNo().compareTo(outTradeNo) == 0) {
                        position = i;
                        break;
                    }
                }
            }
            if (position > -1) {
                loadMoreUtil.refreshData(position, 5);
            }
        }

    }

    //接收售后成功事件
    @Subscribe
    public void onEvent(AfterSalesCreateEvent event) {
        int orderItemId = event.getOrderItemId();
        for (int i = simpleOrderListAdapter.getFirstPosition(); i <= simpleOrderListAdapter.getLastPosition(); i++) {
            if (simpleOrderListAdapter.getListBean(i) instanceof CartGoodsBean) {
                CartGoodsBean bean = (CartGoodsBean) simpleOrderListAdapter.getListBean(i);
                if (bean.getItemId() == orderItemId) {
                    simpleOrderListAdapter.update(i, "itemStatus", 6);
                    break;
                }
            }
        }
    }
    //接收售后取消事件
    @Subscribe
    public void onEvent(AfterSalesCancleEvent event) {
        int orderItemId = event.getOrderItemId();
        for (int i = 0; i < simpleOrderListAdapter.getItemCount(); i++) {
            if (simpleOrderListAdapter.getListBean(i) instanceof CartGoodsBean) {
                CartGoodsBean bean = (CartGoodsBean) simpleOrderListAdapter.getListBean(i);
                if (bean.getItemId() == orderItemId) {
                    simpleOrderListAdapter.update(i, "itemStatus", 4);
                    break;
                }
            }
        }
    }
    @Override
    public boolean onItemClick(View view, int onclickType, final int position, final Object abean) {

        if (onclickType == 0) {
            if (abean == null) return true;

            int itemType = simpleOrderListAdapter.concludeItemViewType(abean);

            if (itemType == BaseRecyclerViewAdapter.TYPE_CHILD) {
                CartGoodsBean cBean = (CartGoodsBean) abean;
                int groupPosition = simpleOrderListAdapter.getGroupPosition(position);
                if (groupPosition == -1) return true;
                OrderList.ItemsBean itemsBean = (OrderList.ItemsBean) simpleOrderListAdapter.getListBean(groupPosition);


                switch (view.getId()) {
                    case R.id.item_view:
                        if (view.getContext() instanceof OrderDetailActivity) return true;
                        if (TextUtils.isEmpty(itemsBean.getOrderNo())) return true;
                        if (ClickCheckedUtil.onClickChecked(1000))
                            OrderDetailActivity.startActivity(view.getContext(), new OrderDetailEvent(itemsBean.getOrderId(), itemsBean.getOrderNo(), itemsBean.getCouponAmount()));
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

                        if (goAfter) {
                            int state = cBean.getItemStatus();
                            //trade_success
                            if (state == 6 || state == 9) {
                                AfterSalesDetailActivity.startActivity(view.getContext(), -1, cBean.getItemId());
                                return true;
                            } else {
//                            if (((order_state == 6 || order_state == 2 || order_state == 5 || order_state == 7) && System.currentTimeMillis() - receiveTime < 60 * 60 * 3 * 1000) || order_state == 1) {
                                TextView after_sales = ViewUtils.findViewById(view, R.id.after_sales);
                                if (after_sales.getText().toString().equals("申请售后")) {
                                    cBean.setIsUseCoupon(itemsBean.getCouponAmount());
                                    AfterSalesCreateActivity.startActivity(view.getContext(), cBean);
                                }

                            }
                        }

//                        else UIUtils.showToastSafesShort("已超过售后申请时间");
                        break;
                    case R.id.address_detail:
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        break;
                }

            } else if (itemType == TYPE_GROUP) {
                final OrderList.ItemsBean bean = (OrderList.ItemsBean) abean;

                TextView textView = (TextView) view;
                String state = textView.getText().toString();
                switch (view.getId()) {
                    case R.id.order_fold:
                       /* if (PublicCache.loginPurchase==null)return true;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return true;
                        }
*/

                        if (simpleOrderListAdapter.isFold(position)){
                            f=true;
                        }else {
                            f=false;
                        }
                        LogUtils.e(f);
                        simpleOrderListAdapter.setFoldChanged(position);
                        break;
                    case R.id.order_ok:
                        switch (state) {
                            case "立即付款":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
                                OrderConfirm orderPlaceBack = new OrderConfirm();
                                orderPlaceBack.setCreateTime(DateUtils.dateStringToLong(bean.getCreateTime(), "yyyy-MM-dd HH:mm"));
                                orderPlaceBack.setOrderIds(bean.getOrderIds());
                                orderPlaceBack.setOrder_no(bean.getOrderNo());
                                orderPlaceBack.setOutTradeNo(bean.getOutTradeNo());
                                orderPlaceBack.setTotalPrice(String.valueOf(bean.getActualTotalCost()));
//                        OrderConfirmActivity.startActivity(view.getContext(), orderPlaceBack);
//                        PurchaserOrderConfirmaActivity.startActivity(view.getContext(), orderPlaceBack);
                                Intent intent1 = new Intent(view.getContext(), PurchaserOrderConfirmaActivity.class);
                                intent1.putExtra("CreateTime", orderPlaceBack.getCreateTime());
                                intent1.putExtra("OrderIds", bean.getOrderIds());
                                intent1.putExtra("OutTradeNo", bean.getOutTradeNo());
                                intent1.putExtra("Order_no", bean.getOrderNo());
                                intent1.putExtra("TotalPrice", bean.getActualTotalCost() + "");
                                startActivity(intent1);
                                return true;
                            case "确认收货":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
                                loadingShow();
                                getRequestPresenter().modifyStatus("trade_success", bean.getOrderIds(), bean.getOrderNo(), new RequestCallback<OrderStatusEvent>() {
                                    @Override
                                    public void onSuc(OrderStatusEvent body) {
//                                        loadingDimss();
                                        //通知个人中心更新数量变化
                                        EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                        loadMoreUtil.refreshData(position, 5);
                                    }

                                    @Override
                                    public void onFailed(int event, String msg) {
                                        loadingDimss();
                                        UIUtils.showToastSafesShort(msg);
                                    }
                                });
                                return true;
                            case "确认订单":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
                                if (bean == null || bean.getOrderIds() == null || bean.getOrderNo() == null)
                                    return true;
                                loadingShow();
                                getRequestPresenter().modifyStatus("wait_seller_send_goods", bean.getOrderIds(), bean.getOrderNo(), new RequestCallback<OrderStatusEvent>() {
                                    @Override
                                    public void onSuc(OrderStatusEvent body) {
//                                        loadingDimss();
                                        //通知个人中心更新数量变化
                                        EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                        loadMoreUtil.refreshData(position, 5);
                                    }

                                    @Override
                                    public void onFailed(int event, String msg) {
                                        loadingDimss();
                                        UIUtils.showToastSafesShort(msg);
                                    }
                                });
                                return true;
                            case "我要发货":
                                if (TextUtils.isEmpty(bean.getOrderNo())) return true;
                                OrderDetailActivity.startActivity(view.getContext(), new OrderDetailEvent(bean.getOrderId(), bean.getOrderNo(), bean.getCouponAmount()));
                                return true;
                            case "删除订单":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
                                loadingShow();
                                getRequestPresenter().order_delete(bean.getOrderIds(), new RequestCallback<OrderDeleteEvent>() {
                                    @Override
                                    public void onSuc(OrderDeleteEvent body) {
                                        loadingDimss();
                                        //通知个人中心更新数量变化
                                        EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                        simpleOrderListAdapter.removeGroup(position);
                                    }

                                    @Override
                                    public void onFailed(int orderDeleteEvent, String msg) {
                                        loadingDimss();
                                        UIUtils.showToastSafesShort(msg);
                                    }
                                });
                                return true;
                            case "评价":
                                //  UIUtils.showToastSafesShort("暂未开放，敬请期待");

                                if (PublicCache.loginPurchase != null) {
                                    if (PublicCache.loginPurchase==null)return true;
                                    if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                        UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                        return true;
                                    }
                                    EventBus.getDefault().postSticky(bean);
                                    Intent intent = new Intent(getContext(), EvaluatePurchaseActivity.class);
                                    startActivity(intent);
                                } else if (PublicCache.loginSupplier != null) {
                                    Intent intent = new Intent(getContext(), EvaluateSupplierActivity.class);
                                    intent.putExtra("data", bean);
                                    startActivity(intent);
                                }

                                return true;
                            case "取消订单":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
//                            startActivity(new Intent(getContext(), OderCancelActivity.class));
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                builder.setCancelable(false);
                                View view1 = ViewUtils.getLayoutView(getContext(), R.layout.activity_order_cancel);
                                TextView phone=view1.findViewById(R.id.tv_phone);
                                phone.setText(PhoneUtils.getPhoneAfter());
                                builder.setView(view1);
                                AlertDialog alertDialog = builder.create();
                                view1.findViewById(R.id.bt_ok).setOnClickListener(v -> alertDialog.dismiss());
                                view1.findViewById(R.id.tv_phone).setOnClickListener(v -> SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneAfter()));
                                alertDialog.show();

                                break;
                        }
                        return true;
                    case R.id.order_oneMore:
                        if (PublicCache.loginPurchase==null)return true;
                        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                            return true;
                        }
                        switch (state) {
                            case "编辑订单":
                                /*if (PublicCache.loginPurchase != null&&PublicCache.loginPurchase.getLoginUserId()!=bean.getOriginalorCustomerId()) {
                                    UIUtils.showToastSafesShort("您不是订单的创建人");
                                   return true;
                                }*/
                                AlertDialog.Builder builder = ViewUtils.showDialog(getContext(), "编辑订单", "您确定要撤销订单到购物车重新编辑？");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        loadingShow();
                                        getRequestPresenter().order_deleteReally(bean.getOrderIds(), new RequestCallback<ResultInfo>() {
                                            @Override
                                            public void onSuc(ResultInfo body) {
                                                List<CartGoodsBean> list = bean.getExtraField();
                                                if (list.size() > 0) {
                                                    CartModel cartModel = CartModel.getInstance();
                                                    for (CartGoodsBean goodsBean : list) {
                                                        CartGoodsBean cartGoodsBean = cartModel.getDataBean(goodsBean.getSpecId());
                                                        if (cartGoodsBean == null) {
                                                            cartGoodsBean = goodsBean;
                                                            cartGoodsBean.setCountXg(goodsBean.getStock());
                                                            cartGoodsBean.setSelected(true);
                                                        }
                                                        cartGoodsBean.setProductQty(goodsBean.getProductQty());
                                                        //这里是模拟的数据，会被替换掉
                                                        cartGoodsBean.setItemStatus(0);
                                                        cartGoodsBean.setStatus(1);
                                                        // cartGoodsBean.setStock(999);
                                                        cartGoodsBean.setStoreStatus(0);
                                                        cartGoodsBean.setIsP(goodsBean.getIsP());
                                                        cartGoodsBean.setCategoryId(goodsBean.getCategoryId());
                                                        cartGoodsBean.setCommodityId(goodsBean.getCommodityId());
                                                        //通知购物车，购物数量变化
                                                        EventBus.getDefault().post(new CartEvent(cartGoodsBean));
                                                    }

                                                    UIUtils.showToastSafe("已撤销订单到购物车");
                                                    //通知个人中心订单数量变化
                                                    EventBus.getDefault().postSticky(new OrderPlaceCountEvent());
                                                    //跳转到购物车
                                                    MenuToolbarActivity.goToPage(3);
                                                }

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
                                return true;
                            case "再来一单":
                                if (PublicCache.loginPurchase==null)return true;
                                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                    return true;
                                }
                                AlertDialog.Builder builder1 = ViewUtils.showDialog(getContext(), "再来一单", "您确定要将该订单添加到购物车？");
                                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<CartGoodsBean> list = bean.getExtraField();
                                        if (list.size() > 0) {
                                            CartModel cartModel = CartModel.getInstance();

                                            for (CartGoodsBean goodsBean : list) {
                                                CartGoodsBean cartGoodsBean = cartModel.getDataBean(goodsBean.getSpecId());
                                                if (cartGoodsBean == null) {
                                                    cartGoodsBean = goodsBean;
                                                    cartGoodsBean.setCountXg(goodsBean.getStock());
                                                }
                                                cartGoodsBean.setSelected(true);
                                                //这里是模拟的数据，会被替换掉
                                                cartGoodsBean.setItemStatus(0);
                                                cartGoodsBean.setStatus(1);
                                                //  cartGoodsBean.setStock(999);
                                                cartGoodsBean.setStoreStatus(0);
                                                cartGoodsBean.setProductQty(goodsBean.getProductQty());

                                                cartGoodsBean.setIsP(goodsBean.getIsP());
                                                cartGoodsBean.setCategoryId(goodsBean.getCategoryId());
                                                cartGoodsBean.setCommodityId(goodsBean.getCommodityId());
                                                //通知购物车，购物数量变化
                                                EventBus.getDefault().post(new CartEvent(cartGoodsBean));
                                            }

                                            UIUtils.showToastSafe("已添加订单到购物车");
                                            MenuToolbarActivity.goToPage(3);
                                        }

                                    }
                                });
                                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder1.show();
                                return true;
                        }
                        return true;
                }
            }

        }
        return false;
    }
}
