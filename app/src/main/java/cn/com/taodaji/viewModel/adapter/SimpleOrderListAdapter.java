package cn.com.taodaji.viewModel.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.GroupSplitRecyclerAdapter;
import com.base.utils.CustomerData;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderList;
import cn.com.taodaji.model.entity.OrderListTop;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsVM;

/**
 * Created by yangkuo on 2018-09-21.
 */
public class SimpleOrderListAdapter extends GroupSplitRecyclerAdapter<OrderList.ItemsBean, OrderListTop> {

    private CustomerData<String, String, String> order_state = PublicCache.getOrderState();


    @Override
    public int concludeItemViewType(Object obj) {
        if (obj instanceof OrderListTop) return TYPE_GROUP_SPLIT;
        else if (obj instanceof OrderList.ItemsBean) return TYPE_GROUP;
        else if (obj instanceof CartGoodsBean) return TYPE_CHILD;
        return super.concludeItemViewType(obj);
    }

    @Override
    public OrderListTop getSplitGroupBean(OrderList.ItemsBean gBean) {
        OrderListTop orderListTop = new OrderListTop();
        JavaMethod.copyValue(orderListTop, gBean);
        return orderListTop;
    }

    @Override
    public int getSplitGroupOrientation() {
        return SPLITGROUPS_ORIENTATION_TOP;
    }

    @Override
    public List getChildList(OrderList.ItemsBean gBean) {
        return gBean.getExtraField();
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        int itemType = getItemViewType(position);
        //子项目
        if (itemType == TYPE_CHILD) {
            CartGoodsBean bean = (CartGoodsBean) getListBean(position);


            //是否是热销
       /*     if (bean.getProductType() == 3) holder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
            else holder.setVisibility(R.id.img_hot_sale, View.GONE);

                //是否是热销
                if (bean.getProductType() == 3) holder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
                else holder.setVisibility(R.id.img_hot_sale, View.GONE);

            holder.setVisibility(R.id.jinpin, View.VISIBLE);

            //商品标准“1”：通货商品 “2”：精品商品 '
            if (bean.getProductCriteria() == 2) {
                holder.setImageRes(R.id.jinpin, R.mipmap.icon_jin_red);
            } else {
                holder.setImageRes(R.id.jinpin, R.mipmap.icon_tong_blue);
            }


                //是否为促销商品
                if (bean.getProductType() == 1) {
                    holder.setVisibility(R.id.special_offer, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.special_offer, View.GONE);
                }*/

            if (bean.getItemStatus() == 6 || bean.getItemStatus() == 9) {
                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                holder.setText(R.id.after_sales, Constants.Order_itemStatus.get(bean.getItemStatus()));
            } else {
                if (PublicCache.loginPurchase != null) {
/*                orderState.put(0, "wait_buyer_pay", "待付款", "待付款");
                orderState.put(1, "wait_buyer_confirm_goods", "待收货", "待收货");
                orderState.put(2, "wait_buyer_evaluate", "待评价", "待评价");

                orderState.put(3, "wait_seller_confirm_goods", "待确认", "待确认");
                orderState.put(4, "wait_seller_send_goods", "待发货", "待发货");
                orderState.put(5, "wait_seller_evaluate", "待评价", "待评价");

                orderState.put(6, "trade_success", "待评价", "待评价");
                orderState.put(7, "trade_finished", "交易成功", "交易成功");
                orderState.put(8, "trade_closed", "交易关闭", "交易关闭");
                orderState.put(9, "trade_canceled", "交易取消", "交易取消");*/
                    int groupPosition = getGroupPosition(position);
                    if (groupPosition == -1) return;
                    OrderList.ItemsBean itemsBean = (OrderList.ItemsBean) getListBean(groupPosition);
                    int state = order_state.idOfKey(itemsBean.getStatusCode());
                    switch (state) {
                        case 1:
                        case 2:
                        case 5:
                        case 6:
                        case 7:

                            //送达时期的中午12点
                            long expectDeliveredDate_s12 = DateUtils.dateStringToLong(itemsBean.getExpectDeliveredDate() + " 21:00:00");

                            if (System.currentTimeMillis() < expectDeliveredDate_s12) {
                                holder.setVisibility(R.id.after_sales, View.VISIBLE);
                                holder.setText(R.id.after_sales, "申请售后");
                            }
                            else holder.findViewById(R.id.after_sales).setVisibility(View.GONE);

                            break;
                        default:
                            holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
//                            holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                            break;
                    }


                } else {
                    int groupPosition = getGroupPosition(position);
                    if (groupPosition == -1) return;
                    OrderList.ItemsBean itemsBean = (OrderList.ItemsBean) getListBean(groupPosition);
                    int state = order_state.idOfKey(itemsBean.getStatusCode());
                    holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
                    if (state == 3 || state == 4) {


                        switch (bean.getItemStatus()) {
                            case 3://已入库
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "已入库");
                                break;
                            case 7://卖家自送
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "卖家自送");
                                break;
                            case 8://卖家无货
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "卖家无货");
                                break;
                            case 11://补单
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "补单");
                                break;
                            default:
                                holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
                                break;
                        }
                    }
                    if (state == 11 || state == 1) {
                        switch (bean.getItemStatus()) {
                            case 8://卖家无货
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "卖家无货");
                                break;
                            case 7://卖家自送
                                holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                                holder.setText(R.id.after_sales, "卖家自送");
                                break;
                            default:
                                holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
                                break;
                        }
                    }
                }
            }


        }

        //分组
        else if (itemType == TYPE_GROUP) {
            OrderList.ItemsBean bean = (OrderList.ItemsBean) getListBean(position);
            LogUtils.e(bean.getFullRegionAndShippingLineCode());
            int itemCount = bean.getItemCount();
            if (itemCount <= childDefaultCount) holder.setVisibility(R.id.order_fold, View.GONE);
            else holder.setVisibility(R.id.order_fold, View.VISIBLE);

            //再一来一单
            holder.findViewById(R.id.order_oneMore).setVisibility(View.GONE);
            int state = order_state.idOfKey(bean.getStatusCode());

            switch (state) {
                case 0://等待买家付款
                    if (PublicCache.loginPurchase != null) {

                        if (PublicCache.loginPurchase.getEmpRole() != 2||PublicCache.loginPurchase.getEmpRole() != 5) {
                            holder.findViewById(R.id.order_oneMore).setVisibility(View.VISIBLE);
                        } else holder.findViewById(R.id.order_oneMore).setVisibility(View.GONE);

                        holder.findViewById(R.id.order_ok).setVisibility(View.VISIBLE);
                        holder.setText(R.id.order_ok, "立即付款");
                        holder.setText(R.id.order_oneMore, "编辑订单");


                    } else holder.findViewById(R.id.order_ok).setVisibility(View.GONE);

                    break;
                case 1://等待买家收货
                    if (PublicCache.loginSupplier != null) {
                        holder.setVisibility(R.id.order_ok, View.GONE);
                    } else {
                        holder.findViewById(R.id.order_ok).setVisibility(View.VISIBLE);
                        holder.setText(R.id.order_ok, "确认收货");
                        holder.findViewById(R.id.order_oneMore).setVisibility(View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    }

                    break;
                case 2://待买家评价
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                        holder.setText(R.id.order_ok, "评价");
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    } else {
                        holder.setVisibility(R.id.order_ok, View.GONE);
                    }

                    break;
                case 3://等待卖家确认
                    if (PublicCache.loginPurchase != null) {
                        // simpleCartAdapter.setAfterSale(true);
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                        holder.setText(R.id.order_ok, "取消订单");
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");

                    } else {
                        holder.setText(R.id.order_ok, "我要发货");
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                    }

                    break;
                case 4://等待卖家发货
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                        holder.setText(R.id.order_ok, "取消订单");
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    } else {
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                        holder.setText(R.id.order_ok, "我要发货");
                    }
                    break;
                case 5://等待卖家评价
                    if (PublicCache.loginSupplier != null) {
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                        holder.setText(R.id.order_ok, "评价");
                    } else {
                        holder.setVisibility(R.id.order_ok, View.GONE);
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    }

                    break;
                case 6://trade_success
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    }
                    holder.setVisibility(R.id.order_ok, View.VISIBLE);
                    holder.setText(R.id.order_ok, "评价");
                    break;
                case 7://交易完成
                    holder.setVisibility(R.id.order_ok, View.GONE);
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    }
                    break;
                case 8://交易关闭
                    holder.setVisibility(R.id.order_ok, View.GONE);
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                        holder.setText(R.id.order_ok, "删除订单");
                        holder.setVisibility(R.id.order_ok, View.VISIBLE);
                    }
                    break;
                case 9://交易取消
                    holder.setVisibility(R.id.order_ok, View.GONE);
                    if (PublicCache.loginPurchase != null) {
                        holder.setVisibility(R.id.order_oneMore, View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
//                    holder.setText(R.id.order_ok, "删除订单");
                        holder.setVisibility(R.id.order_ok, View.GONE);
                    }else {
                        holder.setText(R.id.tv_1,"取消时间：");
                        holder.setVisibility(R.id.tv_2, View.GONE);
                        holder.setVisibility(R.id.buyNumber, View.GONE);

                        holder.setText(R.id.expectDeliveredDate,bean.getCanceledTime());
                        holder.setVisibility(R.id.expectDeliveredDate_time, View.GONE);
                    }
                    break;
                case 11:
                    if (PublicCache.loginSupplier != null) {
                        holder.setVisibility(R.id.order_ok, View.GONE);
                    } else {
                        holder.findViewById(R.id.order_ok).setVisibility(View.VISIBLE);
                        holder.setText(R.id.order_ok, "确认收货");
                        holder.findViewById(R.id.order_oneMore).setVisibility(View.VISIBLE);
                        holder.setText(R.id.order_oneMore, "再来一单");
                    }
                    break;

            }


            long createTime = DateUtils.dateStringToLong(bean.getCreateTime());
            long now = DateUtils.dateStringToLong("2018-04-18");
            if (createTime < now) {
                holder.findViewById(R.id.order_oneMore).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GROUP_SPLIT:
                view = ViewUtils.getFragmentView(parent, R.layout.item_order_place_group_top);
                if (PublicCache.loginSupplier != null) {
                    ViewUtils.findViewById(view, R.id.purchaser).setVisibility(View.GONE);
                    ViewUtils.findViewById(view, R.id.ll_c).setVisibility(View.GONE);
                    ViewUtils.findViewById(view, R.id.supplier).setVisibility(View.VISIBLE);
//                    ViewUtils.findViewById(view, R.id.supplier_time).setVisibility(View.VISIBLE);
                }else if (PublicCache.loginPurchase != null){
                    if (PublicCache.loginPurchase.getIsC()==1){
                        ViewUtils.findViewById(view, R.id.purchaser).setVisibility(View.GONE);
                        ViewUtils.findViewById(view, R.id.ll_c).setVisibility(View.VISIBLE);
                    }else {
                        ViewUtils.findViewById(view, R.id.purchaser).setVisibility(View.VISIBLE);
                        ViewUtils.findViewById(view, R.id.ll_c).setVisibility(View.GONE);
                    }
                }


                break;
            case TYPE_GROUP:
                view = ViewUtils.getFragmentView(parent, R.layout.item_order_place_group_bottom);
                if (PublicCache.loginSupplier != null) {
                    ViewUtils.findViewById(view, R.id.supplier_time).setVisibility(View.VISIBLE);
                    if (PublicCache.initializtionData != null) {
                        TextView expectDeliveredDate_time = view.findViewById(R.id.expectDeliveredDate_time);
                        expectDeliveredDate_time.setText(PublicCache.initializtionData.getSupplier_delivery_time());
                    }
                }
                break;
            case TYPE_CHILD:
                view = ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
                break;
        }

        return view;
    }

    @Override
    public void initBaseVM() {
        //设置折叠选中的字段
        setSelectFieldName("isFold");

        putBaseVM(TYPE_GROUP_SPLIT, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.order_num, "lastName");
                putRelation(R.id.customerLogo, "customerLogo");
                putRelation(R.id.tv_shipping_line_code, "fullRegionAndShippingLineCode");

                putRelation(R.id.cName, "communityShortName");

                putRelation(R.id.customerName, "lastName");
                putRelation(R.id.order_state, "statusCode");
            }


            @Override
            public void setValue(@NonNull TextView textView, @NonNull Object value) {
                if (textView.getId() == R.id.order_state) {
                    int state = order_state.idOfKey(String.valueOf(value));

                    if (state == 2 && PublicCache.loginSupplier != null) {
                        textView.setText("交易成功");
                    } else if (state == 5 && PublicCache.loginPurchase != null) {
                        textView.setText("交易成功");
                    } else if (state == 1 && PublicCache.loginSupplier != null) {
                        textView.setText("待签收");
                    } else {
                        textView.setText(order_state.getDesc(state));
                        if (PublicCache.loginPurchase != null) {
                            if (state == 3) textView.setText("待发货");
                        }
                    }
                } else super.setValue(textView, value);
            }

        });

        putBaseVM(TYPE_GROUP, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.order_time, "createTime");

                putRelation(R.id.count_image, "itemCount");
                putRelation(R.id.cart_price_sum, "actualTotalCost");
                putRelation(R.id.expectDeliveredDate, "expectDeliveredDate");
                putRelation(R.id.buyNumber, "buyNumber");
                putRelation(R.id.order_fold, "isFold");

                putViewOnClick(R.id.order_fold);
                putViewOnClick(R.id.order_oneMore);
                putViewOnClick(R.id.order_ok);
            }

            @Override
            public void setValues(@NonNull View view, Object value) {
                if (view.getId() == R.id.order_fold) {
                    TextView textView = (TextView) view;
                    boolean bb = value != null && (boolean) value;
                    if (bb) textView.setText("展开更多");
                    else textView.setText("收起订单");
                }
                super.setValues(view, value);
            }

            @Override
            public void setValue(@NonNull TextView textView, @NonNull Object value) {
                switch (textView.getId()) {
                    case R.id.buyNumber:
                        int number = JavaMethod.transformClass(int.class, value);
                        if (number == 0) textView.setText("新用户");
                        else if (number >=10) textView.setText("老用户");
                        else textView.setText("共 " + number + " 次购买");
                        break;
                    default:
                        super.setValue(textView, value);
                        break;

                }


            }
        });


        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsVM());

    }


}
