package cn.com.taodaji.viewModel.adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SupplyMoneyListBean;
import cn.com.taodaji.ui.activity.myself.NewDepositRefundDetailActivity;
import cn.com.taodaji.ui.activity.purchaseBill.DepositRefundDetailActivity;
import cn.com.taodaji.ui.activity.purchaseBill.SupplierOrderAfterSaleActivity;
import cn.com.taodaji.ui.activity.purchaseBill.SupplierOrderGetCashActivity;
import cn.com.taodaji.ui.activity.purchaseBill.SupplierOrderPaymentActivity;
import cn.com.taodaji.ui.activity.wallet.SupplierMonthBillActivity;

public class GroupSupplyMoneyDetailNewAdapter extends GroupRecyclerAdapter<SupplyMoneyListBean.DataBean.PageBeanBean> {


    @Override
    public int concludeItemViewType(Object obj) {
        if (obj instanceof SupplyMoneyListBean.DataBean.PageBeanBean) return TYPE_GROUP;
        else return super.concludeItemViewType(obj);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GROUP:
                view = ViewUtils.getFragmentView(parent, R.layout.item_supply_money_detail_group);
                break;
            default:
                view = ViewUtils.getFragmentView(parent, R.layout.item_supply_money_detail);
                break;
        }
        return view;
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_GROUP) {
//            holder.setVisibility(R.id.textView_1, View.GONE);
//            holder.setVisibility(R.id.image_view, View.GONE);
        } else {

            SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean bean = (SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean) getListBean(position);
            if (bean != null) {

                String value = "";
                //order_freeze_status  订单冻结类型(FREEZE:结状态 UNFREEZE:解冻状态）
                // order_status订单的状态:trade_finished|trade_canceled|trade_closed
                //type 数据类型：1、订单 2、提现 3、售后 4、提现撤销 5、提现驳回 6、押金超时支付

                //withdrawal_status供应商提现状态：0：处理中|1：处理完成|2：拒绝提现
                switch (bean.getType()) {
                    case 1:


                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
                        holder.setVisibility(R.id.giv_lock, View.GONE);

                        BigDecimal realPrice = bean.getCapitalChangeAmount().subtract(bean.getOrderCommissionAmount()).subtract(bean.getShopCouponAmount());

                        holder.setText(R.id.price, realPrice.toString());

                        //  名称和原因
                        //putRelation(R.id.name, "capitalChangeReason");//productName productNickName//extOrderId
                        holder.setText(R.id.name, "编号：" + bean.getExtOrderId());
                        //holder.setText(R.id.tv_description, "收："+bean.getExtOrderId());

                        //BigDecimal totalPrice=bean.getCapitalChangeAmount().add(bean.getOrderCommissionAmount());

                        if ("trade_canceled".equals(bean.getOrderStatus())) {

                            holder.setImageRes(R.id.logo, R.mipmap.icon_order_cancel);
                            value = "-";
                            holder.setVisibility(R.id.state, View.VISIBLE);
                            holder.setText(R.id.state, "订单取消");
                            if (bean.getShopCouponAmount().compareTo(BigDecimal.ZERO)>0){
                                holder.setText(R.id.tv_description, "收：-" + bean.getCapitalChangeAmount() + "  交易费：+" + bean.getOrderCommissionAmount()+ "  代金券：+" + bean.getShopCouponAmount());

                            }else {
                                holder.setText(R.id.tv_description, "收：-" + bean.getCapitalChangeAmount() + "  交易费：+" + bean.getOrderCommissionAmount());

                            }

                        } else {
                            holder.setImageRes(R.id.logo, R.mipmap.icon_order_form);
                            value = "+";
                            if (bean.getShopCouponAmount().compareTo(BigDecimal.ZERO)>0){
                                holder.setText(R.id.tv_description, "收：+" + bean.getCapitalChangeAmount() + "  交易费：-" + bean.getOrderCommissionAmount() +"  代金券：-" + bean.getShopCouponAmount());

                            }else {
                                holder.setText(R.id.tv_description, "收：+" + bean.getCapitalChangeAmount() + "  交易费：-" + bean.getOrderCommissionAmount());

                            }

                        }


                        if ("FREEZE".equals(bean.getOrderFreezeStatus())) {
                            holder.setVisibility(R.id.giv_lock, View.VISIBLE);
                        }

                        break;
                    case 2: {
                        holder.setText(R.id.price, bean.getWithdrawalTotalAmount() + "");

                        holder.setImageRes(R.id.logo, R.mipmap.icon_withdraw_deposit);

                        holder.setVisibility(R.id.state, View.VISIBLE);
                        holder.setVisibility(R.id.tv_description, View.GONE);
                        holder.setVisibility(R.id.giv_lock, View.GONE);


                        String addOrPlus = "-";
                        switch (bean.getWithdrawalStatus()) {
                            //0：处理中|1：处理完成|2：拒绝提现3:处理中驳回
                            case 0:
                                value = "-";
                                addOrPlus = "-";
                                holder.setText(R.id.state, "处理中");
                                break;
                            case 1:
                                value = "-";
                                addOrPlus = "-";
                                holder.setText(R.id.state, "提现成功");
                                break;
                            case 2:
                                value = "+";
                                addOrPlus = "+";
                                holder.setText(R.id.state, "驳回提现");
                                break;
                            case 3:
                                value = "-";
                                addOrPlus = "-";
                                holder.setText(R.id.state, "处理中(已驳回)");
                                break;
                        }

//                        if (bean.getWithdrawalStatus() == 2 || bean.getWithdrawalTotalAmount().compareTo(new BigDecimal(1000)) >= 0) {
                        if (bean.getWithdrawalStatus() == 2 ) {
                            holder.setVisibility(R.id.name, View.GONE);
                        } else {
                            holder.setVisibility(R.id.name, View.VISIBLE);
                            String reason = "收：" + addOrPlus + bean.getWithdrawalFeeAmount() + "  实到：" + addOrPlus + bean.getWithdrawalActuralAmount();
                            holder.setText(R.id.name, reason);
                        }


                        break;
                    }
                    case 3:
                        BigDecimal realPrice_after = bean.getCapitalChangeAmount().subtract(bean.getOrderCommissionAmount());
                        holder.setText(R.id.price, realPrice_after.toString());

                        holder.setImageRes(R.id.logo, bean.getProductImg());
                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setVisibility(R.id.state, View.VISIBLE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
                        holder.setVisibility(R.id.giv_lock, View.GONE);

                        //  名称和原因
                        //putRelation(R.id.name, "capitalChangeReason");//productName productNickName//extOrderId

                        value = "-";

                        String name = bean.getProductName();
                        if (!TextUtils.isEmpty(bean.getProductNickName())) {
                            name = name + "(" + bean.getProductNickName() + ")";
                        }
                        holder.setText(R.id.name, name);

                        holder.setText(R.id.state, "售后退款");

                        if ("FREEZE".equals(bean.getOrderFreezeStatus())) {
                            holder.setVisibility(R.id.giv_lock, View.VISIBLE);
                        }
                        BigDecimal totalPrice1 = bean.getCapitalChangeAmount();
                        holder.setText(R.id.tv_description, "售：-" + totalPrice1 + "  交易费：+" + bean.getOrderCommissionAmount());
                        break;
                    case 6:
                        value = "+";
                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setImageRes(R.id.logo, R.mipmap.ic_deposit_refund);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.tv_description, View.GONE);
                        holder.setVisibility(R.id.giv_lock, View.GONE);

                        holder.setText(R.id.price, bean.getCapitalChangeAmount());
                        String name1 = bean.getProductName();
                        if (!TextUtils.isEmpty(bean.getProductNickName())) {
                            name1 = name1 + "(" + bean.getProductNickName() + ")";
                        }
                        holder.setText(R.id.name, name1);

                        break;
                    case 7:
                        value = "-";
                        holder.setText(R.id.price, bean.getCapitalChangeAmount() + "");
                        holder.setImageRes(R.id.logo, R.mipmap.turn_out3x);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setText(R.id.tv_description, bean.getCapitalChangeReason());
                        holder.setVisibility(R.id.giv_lock, View.GONE);
                        break;
                    case 8:
                        value = "+";
                        holder.setText(R.id.price, bean.getCapitalChangeAmount() + "");
                        holder.setImageRes(R.id.logo, R.mipmap.turn_in3x);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setText(R.id.tv_description, bean.getCapitalChangeReason());
                        holder.setVisibility(R.id.giv_lock, View.GONE);
                        break;
                    case 9:
                        value = "-";
                        holder.setText(R.id.price, bean.getCapitalChangeAmount() + "");
                        holder.setImageRes(R.id.logo, R.mipmap.buy_package3x);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setText(R.id.tv_description, bean.getCapitalChangeReason());
                        holder.setVisibility(R.id.giv_lock, View.GONE);
                        break;
                    case 12:
                        value = "+";
                        holder.setText(R.id.price, bean.getCapitalChangeAmount() + "");
                        holder.setImageRes(R.id.logo, R.mipmap.tui_money);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setText(R.id.tv_description, bean.getCapitalChangeReason());
                        holder.setVisibility(R.id.giv_lock, View.GONE);
                        break;
                    case 11:
                        value = "-";
                        holder.setText(R.id.price, bean.getCapitalChangeAmount() + "");
                        holder.setImageRes(R.id.logo, R.mipmap.pay_money);
                        holder.setVisibility(R.id.state, View.GONE);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setText(R.id.tv_description, bean.getCapitalChangeReason());
                        holder.setVisibility(R.id.giv_lock, View.GONE);
                        break;

                }

                holder.setText(R.id.type, value);

            }


        }

    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_GROUP, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.month, "month");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.group_item);
            }
        });
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                //putRelation(R.id.weekday, "createTime");
                //     putRelation(R.id.type, "type");
                //putRelation(R.id.price, "capitalChangeAmount");
                //   putRelation(R.id.state, "capitalChangeReason");
                putRelation(R.id.date, "createTime");//type  withdrawal_status

                putRelation(R.id.tv_balance, "supplierTotalAmount");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.item_view);
            }


            @Override
            public void setValues(@NonNull View view, Object value) {
                if (view == null) return;

                switch (view.getId()) {
                    case R.id.date:
                        value = DateUtils.dateStringToString(value.toString(), "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm");
                        break;
                    case R.id.weekday:
                        //value = DateUtils.getWeekDay(value.toString(), "yyyy-MM-dd HH:mm", "周");
                        break;
                }
                super.setValues(view, value);
            }
        });
    }


    @Override
    public List getChildList(SupplyMoneyListBean.DataBean.PageBeanBean gBean) {
        return gBean.getRecordList();
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (getItemViewType(position)) {
                case TYPE_GROUP:
                    String date = JavaMethod.getFieldValue(bean, "month");
                    if (!TextUtils.isEmpty(date)) {
                        String year = DateUtils.dateStringToString(date, "yyyy年MM月", "yyyy");
                        String month = DateUtils.dateStringToString(date, "yyyy年MM月", "MM");
                        if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(month)) {
                            SupplierMonthBillActivity.startActivity(view.getContext(), Integer.valueOf(year), Integer.valueOf(month));
                        }
                    }
                    return true;
                case TYPE_CHILD:
                    if (bean instanceof SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean) {
                        SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean itemsBean = (SupplyMoneyListBean.DataBean.PageBeanBean.RecordListBean) bean;

                        switch (itemsBean.getType()) {
                            //type 数据类型：1、订单 2、提现 3、售后 4、提现撤销 5、提现驳回 6、押金超时支付
                            case 1:
                                SupplierOrderPaymentActivity.startActivity(view.getContext(), itemsBean.getEntityId(), itemsBean.getExtOrderId(), itemsBean.getOrderStatus(), itemsBean.getStoreId(),
                                        itemsBean.getShopCouponAmount().toString());
                                break;
                            case 2:
                                SupplierOrderGetCashActivity.startActivity(view.getContext(), itemsBean.getRemarks(), itemsBean.getWithdrawalStatus());
                                break;
                            case 3:
                                SupplierOrderAfterSaleActivity.startActivity(view.getContext(), itemsBean.getRemarks());
                                break;
                            case 6:
                                DepositRefundDetailActivity.startActivity(view.getContext(), itemsBean.getPackOrderId(), itemsBean.getCreateTime());
                                break;
                            case 11:
                                if (!itemsBean.getTransactionNo().startsWith("annal_")){
                                    Intent intent=new Intent(view.getContext(), NewDepositRefundDetailActivity.class);
                                    intent.putExtra("orderId",itemsBean.getOrderId());
                                    intent.putExtra("transactionNo",itemsBean.getTransactionNo());
                                    view.getContext().startActivity(intent);

                                }
                                break;
                            case 12:
                                    Intent intent=new Intent(view.getContext(), NewDepositRefundDetailActivity.class);
                                    intent.putExtra("orderId",itemsBean.getOrderId());
                                    intent.putExtra("transactionNo",itemsBean.getTransactionNo());
                                 intent.putExtra("ctime",itemsBean.getCreateTime());
                                    view.getContext().startActivity(intent);
                                break;

                        }
                    }
                    return true;
            }
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
