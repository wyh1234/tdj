package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

public class SimplePackingCashProgressAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.handle_info, "handle_info");
                putRelation(R.id.create_time, "created_at");
               // putRelation(R.id.handle_operator, "handle_operator");

                putRelation(R.id.item_view, "check");
            }

        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_packing_cash_progress);
    }


}
