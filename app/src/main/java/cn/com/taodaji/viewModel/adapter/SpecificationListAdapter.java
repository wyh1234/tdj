package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.viewModel.vm.SpecificationVM;


/**
 * Created by Administrator on 2018/1/19.
 */

public class SpecificationListAdapter extends SingleRecyclerViewAdapter {


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new SpecificationVM());
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        GoodsSpecification bean = (GoodsSpecification) getListBean(position);
        if (bean == null) return;
        holder.setText(R.id.level1Unit_, bean.getLevel1Unit());
        if (bean.getLevelType() == 1) {
            holder.setVisibility(R.id.middle, View.GONE);
        } else holder.setVisibility(R.id.middle, View.VISIBLE);
    }


    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_specification);
    }
}
