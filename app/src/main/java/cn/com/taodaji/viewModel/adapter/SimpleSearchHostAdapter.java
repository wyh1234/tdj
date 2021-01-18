package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.utils.ViewUtils;

public class SimpleSearchHostAdapter extends SingleRecyclerViewAdapter {
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new ADInfoViewModel());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_search_recyclerview);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (itemClickListener != null) {
            return itemClickListener.onItemClick(view, onclickType, position, bean);
        }
        return super.onItemClick(view, onclickType, position, bean);
    }

}
