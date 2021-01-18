package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.utils.ViewUtils;

public class PurchaseOrderItemAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsDetailVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
    }

}
