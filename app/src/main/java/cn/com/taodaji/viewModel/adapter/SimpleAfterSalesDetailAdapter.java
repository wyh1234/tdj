package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;
import cn.com.taodaji.R;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;

public class SimpleAfterSalesDetailAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.handle_info, "handle_info");
                putRelation(R.id.create_time, "create_time");
                putRelation(R.id.handle_operator, "handle_operator");

                putRelation(R.id.item_view, foldName);
            }

        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_refund_detail);
    }


}
