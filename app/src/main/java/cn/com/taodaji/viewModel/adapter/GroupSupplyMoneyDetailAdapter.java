package cn.com.taodaji.viewModel.adapter;


import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.LogSupplierCapitalFlow;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyDetailActivity;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;

public class GroupSupplyMoneyDetailAdapter extends GroupRecyclerAdapter<LogSupplierCapitalFlow> {


    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj.getClass() == LogSupplierCapitalFlow.class) return TYPE_GROUP;
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
                view = ViewUtils.getFragmentView(parent, R.layout.item_balance_detail);
                LinearLayout ll = view.findViewById(R.id.ll_balance);
                ll.setVisibility(View.GONE);
//                view = ViewUtils.getFragmentView(parent, R.layout.item_supply_money_detail);
                break;
        }
        return view;
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_GROUP) {
            holder.setVisibility(R.id.textView_1, View.GONE);
            holder.setVisibility(R.id.image_view, View.GONE);
        } else {

            LogSupplierCapitalFlow.ItemsBean bean = (LogSupplierCapitalFlow.ItemsBean) getListBean(position);
            if (bean != null) {
                //   type: 1、已收款（+） 2、售后退款（-） 3、提现金额  4、提现驳回  5、资金充值  6、取消订单
                if (bean.getType() == 1) {
                    holder.setVisibility(R.id.state, View.GONE);
                } else {
                    holder.setVisibility(R.id.state, View.VISIBLE);
                }


                String value = "";
                switch (bean.getType()) {
                    case 1:
                        value = "+";
                        holder.setImageRes(R.id.logo, R.mipmap.icon_order_form);
                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
                        break;
                    case 2:
                        value = "-";
                        holder.setImageRes(R.id.logo, bean.getProduct_img());
                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
                        break;
                    case 3:
                        value = "-";
                        holder.setImageRes(R.id.logo, R.mipmap.icon_withdraw_deposit);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
                        break;
                    case 4:
                        value = "+";
                        holder.setImageRes(R.id.logo, R.mipmap.icon_withdraw_deposit);
                        holder.setVisibility(R.id.name, View.GONE);
                        holder.setVisibility(R.id.tv_description, View.GONE);
                        break;
                    case 5:
                        value = "+";
                        break;
                    case 6:
                        value = "-";
                        holder.setImageRes(R.id.logo, R.mipmap.icon_order_cancel);
                        holder.setVisibility(R.id.name, View.VISIBLE);
                        holder.setVisibility(R.id.tv_description, View.VISIBLE);
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
                putRelation(R.id.weekday, "create_order_time");
//                putRelation(R.id.type, "type");
                putRelation(R.id.price, "total_price");
                putRelation(R.id.name, "product_name");
                putRelation(R.id.state, "capital_change_reason");
                putRelation(R.id.date, "create_order_time");
//                putRelation(R.id.logo, "product_img");
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

                        if (value != null && value.toString().length() > 5)
                            value = value.toString().substring(5, 10);
                        break;
                    case R.id.weekday:
                        value = DateUtils.getWeekDay(value.toString(), "yyyy-MM-dd HH:mm", "周");
                        break;
                }
                super.setValues(view, value);
            }
        });
    }


    @Override
    public List getChildList(LogSupplierCapitalFlow gBean) {
        return gBean.getItems();
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (getItemViewType(position)) {
                case TYPE_GROUP:
                    //Intent intent = new Intent(view.getContext(), MonthlyBillActivity.class);
                    //  view.getContext().startActivity(intent);
                    break;
                default:
                    if (bean instanceof LogSupplierCapitalFlow.ItemsBean) {
                        LogSupplierCapitalFlow.ItemsBean itemsBean = (LogSupplierCapitalFlow.ItemsBean) bean;
                        if (itemsBean.getType() == 3 || itemsBean.getType() == 4) return true;
                        SupplyMoneyDetailActivity.startActivity(view.getContext(), itemsBean);
                    }

            }
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
