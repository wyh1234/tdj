package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;


import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;


public class SupplierOrderItemAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsDetailVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        holder.setVisibility(R.id.order_print, View.GONE);
        holder.setVisibility(R.id.ll_cash_pledge, View.GONE);
    }
}
