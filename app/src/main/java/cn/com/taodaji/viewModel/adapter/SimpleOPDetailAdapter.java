package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;

import com.base.utils.UIUtils;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;

public class SimpleOPDetailAdapter extends SingleRecyclerViewAdapter {

    private String statusCode;
    private long now_12;//中午12点的时间
    private long now_12_delayed = 0;//缓存的时间

    private boolean print=true;//是否需要打印

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public void setReceiveTime(long receiveTime) {
        now_12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(receiveTime, "yyyy-MM-dd") + " 12:00:00");
    }

    public void setExpectDeliveredDate(String expectDeliveredDate) {
        now_12 = DateUtils.dateStringToLong(expectDeliveredDate + " 12:00:00");
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {

        OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) getListBean(position);

      /*  //是否是热销
        if (bean.getProductType() == 3) holder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
        else holder.setVisibility(R.id.img_hot_sale, View.GONE);

        holder.setVisibility(R.id.jinpin, View.VISIBLE);

        //商品标准“1”：通货商品 “2”：精品商品 '
        if (bean.getProductCriteria()==2 ) {
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
        }else{
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
        }


        //是否为促销商品
        if (bean.getProductType() == 1) {
            holder.setVisibility(R.id.special_offer, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.special_offer, View.GONE);
        }*/
        RelativeLayout nz_rl = holder.findViewById(R.id.nz_rl);
        TextView tv_remark = holder.findViewById(R.id.tv_remark);
        if (UIUtils.isNullOrZeroLenght(bean.getRemark())){
            nz_rl.setVisibility(View.GONE);
        }else {
            nz_rl.setVisibility(View.VISIBLE);
            tv_remark.setText(bean.getRemark());
        }

        if (bean.getStatus() == 6 || bean.getStatus() == 9) {
            holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
            holder.setText(R.id.after_sales, Constants.Order_itemStatus.get(bean.getStatus()));

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
                int order_state = PublicCache.getOrderState().idOfKey(statusCode);

                switch (order_state) {
                    case 1:
                    case 2:
                    case 5:
                    case 6:
                    case 7:
                        if (System.currentTimeMillis() < now_12) {
                            holder.setVisibility(R.id.after_sales, View.VISIBLE);
                            holder.setText(R.id.after_sales, "申请售后");
                            //如果缓存的时间和中午的时间不相等则重新
                            if (now_12_delayed != now_12) {
                                now_12_delayed = now_12;
                                holder.findViewById(R.id.after_sales).removeCallbacks(runnable);
                                holder.findViewById(R.id.after_sales).postDelayed(runnable, now_12 - System.currentTimeMillis() + 10L);
                            }
                        } else holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
                        break;
                    default:
                        holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
                        break;
                }
            } else {
                int order_state = PublicCache.getOrderState().idOfKey(statusCode);
                if (order_state ==3||order_state==4) {


                switch (bean.getStatus()) {
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
                if (order_state ==11||order_state==1) {
                    switch (bean.getStatus()) {
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

        holder.setVisibility(R.id.order_print, View.VISIBLE);
        if (PublicCache.loginSupplier != null) {
            if (PublicCache.getOrderState().idOfKey(statusCode) == 3 || PublicCache.getOrderState().idOfKey(statusCode) == 4) {
                //待发货状态下，商品状态是售后中或售后结束的时侯，不能打印发货，要显示给供应商 售后状态
                if (bean.getStatus() == 6 || bean.getStatus() == 9 || bean.getStatus() == 3 || bean.getStatus() == 11) {
                    //售后中和售后结束不能发货
                    holder.findViewById(R.id.order_print_goods).setVisibility(View.GONE);
                    //显示给供应商 售后状态
                    holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
                } else {
                    if (isPrint()) {
                        holder.findViewById(R.id.order_print_goods).setVisibility(View.VISIBLE);
                    }else {
                        holder.findViewById(R.id.order_print_goods).setVisibility(View.GONE);
                    }

                }


            }



        } else if (PublicCache.loginPurchase != null) {
            holder.findViewById(R.id.order_print).setVisibility(View.GONE);
        }
    }

    private Runnable runnable = this::notifyDataSetChanged;

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsDetailVM() );
    }


}
