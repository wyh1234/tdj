package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import cn.com.taodaji.R;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;

public class SimplePPWAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.image_name, "imageName");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.group_item);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_myself_debug);
    }

}
