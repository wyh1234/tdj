package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;

public class FiltrateTransactionAdapter extends SingleRecyclerViewAdapter {

    private int type;//1 type  2,time

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.text, "text");
                putRelation(R.id.item_view, "selected");

                //点击事件
                putViewOnClick(R.id.item_view);
            }

        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        if (type == 1) {
            return ViewUtils.getFragmentView(parent, R.layout.item_filtrate_transaction_select_type);
        } else
            return ViewUtils.getFragmentView(parent, R.layout.item_filtrate_transaction_select_time);
    }

}
