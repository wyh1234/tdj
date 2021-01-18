package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;

public class SimpleSearchResultAdapter extends SingleRecyclerViewAdapter {

    private int itemViewType;//0为商品，1,为店铺

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(0, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.image_name, "name");
                putRelation(R.id.text_count, "number");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.group_item);
            }
        });
        putBaseVM(1, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.image_name, "name");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.group_item);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (itemViewType == 0 || itemViewType == 1) return itemViewType;
        else return super.getItemViewType(position);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return ViewUtils.getFragmentView(parent, R.layout.item_search_goods_view);
            case 1:
                return ViewUtils.getFragmentView(parent, R.layout.item_search_shop_view);
        }
        return null;
    }

}
