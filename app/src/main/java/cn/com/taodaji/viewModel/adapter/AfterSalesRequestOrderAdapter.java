package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;

/**
 * Created by yangkuo on 2018/10/25.
 */
public class AfterSalesRequestOrderAdapter extends SingleRecyclerViewAdapter {
    private long expectDeliveredDate;

    public long getExpectDeliveredDate() {
        return expectDeliveredDate;
    }

    public void setExpectDeliveredDate(long expectDeliveredDate) {
        this.expectDeliveredDate = expectDeliveredDate;
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsDetailVM());
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) getListBean(position);

        holder.setVisibility(R.id.order_print, View.GONE);

        if (bean.getStatus() == 6 || bean.getStatus() == 9) {
            holder.findViewById(R.id.after_sales).setVisibility(View.VISIBLE);
            holder.setText(R.id.after_sales, Constants.Order_itemStatus.get(bean.getStatus()));
        } else {
            if (PublicCache.loginPurchase != null) {
                //送达时期的中午12点
                long expectDeliveredDate_s12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(getExpectDeliveredDate(), "yyyy-MM-dd") + " 12:00:00");

                if (System.currentTimeMillis() < expectDeliveredDate_s12) {
                    holder.setVisibility(R.id.after_sales, View.VISIBLE);
                    holder.setText(R.id.after_sales, "申请售后");
                } else holder.findViewById(R.id.after_sales).setVisibility(View.GONE);

            } else holder.findViewById(R.id.after_sales).setVisibility(View.GONE);
        }


    }
}
