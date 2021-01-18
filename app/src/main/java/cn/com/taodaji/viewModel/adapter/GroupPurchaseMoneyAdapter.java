package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.GroupRecyclerAdapter;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PurchaseFinanceRecord;
import cn.com.taodaji.ui.activity.purchaseBill.DepositRefundDetailActivity;
import cn.com.taodaji.ui.activity.purchaseBill.MonthlyBillActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseAfterSaleActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseOrderFormDetailActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseRechargeDetailActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseWithDepositeDetailActivity;


public class GroupPurchaseMoneyAdapter extends GroupRecyclerAdapter<PurchaseFinanceRecord> {

    private int state;//页面状态 1电子账单 2为余额明细  3交易筛选明细,4余额筛选明细

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public List getChildList(PurchaseFinanceRecord gBean) {
        return gBean.getItemList();
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.weekday, "weekDay");
                putRelation(R.id.type, "paySymbol");
                putRelation(R.id.price, "paymentAmount");
                putRelation(R.id.name, "description");
                putRelation(R.id.date, "yearMonth");
                putRelation(R.id.logo, "image");

                putViewOnClick(R.id.item_view);

                //筛选明细的余额
                putRelation(R.id.balance, "balance");

            }
        });
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
    }

    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj instanceof PurchaseFinanceRecord) return TYPE_GROUP;
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
                if (state % 2 == 0)
                    view = ViewUtils.getFragmentView(parent, R.layout.item_balance_detail);
                else view = ViewUtils.getFragmentView(parent, R.layout.item_electronic_bill_detail);
                break;
        }
        return view;
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        if (PublicCache.loginPurchase != null) {
            holder.setTextColor(R.id.textView_1, R.color.ff8207);
            holder.setImageRes(R.id.image_view, R.mipmap.arrow_right_orange);
        }else   if (PublicCache.loginSupplier != null) {
            holder.setTextColor(R.id.textView_1, R.color.gray_6a);
            holder.setImageRes(R.id.image_view, R.mipmap.right_arrow_gary);
        }
        if (getItemViewType(position) == 0) {
            PurchaseFinanceRecord.ItemBean bean = (PurchaseFinanceRecord.ItemBean) getListBean(position);
            if (bean != null) {
                if (state % 2 == 0) {
                    if (bean.getStatus() == 3) holder.setVisibility(R.id.state, View.VISIBLE);
                    else holder.setVisibility(R.id.state, View.GONE);
                } else {
                    //status	num	表示数据类型： (1) 充值、(2)支付、(3)退款、(4)提现  5、取消订单 6、退押金
                    switch (bean.getStatus()) {
                        case 1:
                            holder.setVisibility(R.id.pay_logo1, View.GONE);
                            holder.setVisibility(R.id.pay_logo2, View.GONE);
                            holder.setVisibility(R.id.state, View.VISIBLE);
                            holder.setText(R.id.state, "充值");
                            holder.setTextColor(R.id.state, R.color.gray_66);
                            break;
                        case 5:
                        case 2:
                            holder.setVisibility(R.id.state, View.GONE);
                            //paymentMethod	num	0、退款  1、支付宝 2、微信支付 3、余额支付 4、微信+余额支付 5、支付宝+余额支付 6、提现
                            switch (bean.getPaymentMethod()) {
                                case 0:
                                    holder.setVisibility(R.id.pay_logo1, View.GONE);
                                    holder.setVisibility(R.id.pay_logo2, View.VISIBLE);
                                    holder.setImageRes(R.id.pay_logo2, R.mipmap.icon_balance);
                                    break;
                                case 1:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.GONE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.icon_alpay);
                                    break;
                                case 7:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.GONE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.huabei);
                                    break;
                                case 2:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.GONE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.icon_wxapp);
                                    break;
                                case 3:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.GONE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.icon_balance);
                                    break;
                                case 4:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.VISIBLE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.icon_wxapp);
                                    holder.setImageRes(R.id.pay_logo2, R.mipmap.icon_balance);
                                    break;
                                case 8:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.VISIBLE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.huabei);
                                    holder.setImageRes(R.id.pay_logo2, R.mipmap.icon_balance);
                                    break;
                                case 5:
                                    holder.setVisibility(R.id.pay_logo1, View.VISIBLE);
                                    holder.setVisibility(R.id.pay_logo2, View.VISIBLE);
                                    holder.setImageRes(R.id.pay_logo1, R.mipmap.icon_alpay);
                                    holder.setImageRes(R.id.pay_logo2, R.mipmap.icon_balance);
                                    break;
                            }
                            break;
                        case 3:
                            holder.setVisibility(R.id.pay_logo1, View.GONE);
                            holder.setVisibility(R.id.pay_logo2, View.GONE);
                            holder.setVisibility(R.id.state, View.VISIBLE);
                            holder.setTextColor(R.id.state, R.color.red_dark);
                            holder.setText(R.id.state, "售后退款");
                            break;
                        case 4:
                            holder.setVisibility(R.id.pay_logo1, View.GONE);
                            holder.setVisibility(R.id.pay_logo2, View.GONE);
                            holder.setVisibility(R.id.state, View.VISIBLE);
                            holder.setTextColor(R.id.state, R.color.gray_66);
                            holder.setText(R.id.state, "提现");
                            break;
                        case 6:
                            holder.setVisibility(R.id.state, View.GONE);
                            holder.setVisibility(R.id.pay_logo1, View.GONE);
                            holder.setVisibility(R.id.pay_logo2, View.GONE);
                            break;

                    }
                }
            }
        } else if (getItemViewType(position) == TYPE_GROUP) {
            if (state > 1) {
                holder.setVisibility(R.id.textView_1, View.GONE);
                holder.setVisibility(R.id.image_view, View.GONE);
            }
        }
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {

        if (onclickType == 0) {
            if (getItemViewType(position) == TYPE_GROUP) {
                if (state == 1)
                    MonthlyBillActivity.startActivity(view.getContext(), JavaMethod.getFieldValue(bean, "month"));
            } else if (getItemViewType(position) == TYPE_CHILD) {
                PurchaseFinanceRecord.ItemBean pbean = (PurchaseFinanceRecord.ItemBean) bean;
                if (pbean == null) return true;
                //status	num	表示数据类型： (1) 充值、(2)支付、(3)退款、(4)提现  5、取消订单
                switch (pbean.getStatus()) {
                    case 1:
                        PurchaseRechargeDetailActivity.startActivity(view.getContext(), pbean.getEntityId());
                        break;
                    case 2:
                    case 5:
                        String text;
                        if (state % 2 == 1) {
                            text = "已支付(元)";
                        } else text = "余额支付(元)";
                        if (pbean.getStatus() == 5) text = "取消订单";
                        PurchaseOrderFormDetailActivity.startActivity(view.getContext(), pbean.getEntityId(), text);
                        break;
                    case 3:
                        PurchaseAfterSaleActivity.startActivity(view.getContext(), pbean.getEntityId());
                        break;
                    case 4:
                        PurchaseWithDepositeDetailActivity.startActivity(view.getContext(), pbean.getEntityId());
                        break;
                    case 6:
                        DepositRefundDetailActivity.startActivity(view.getContext(), pbean.getEntityId());
                        break;
                }
            }
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
