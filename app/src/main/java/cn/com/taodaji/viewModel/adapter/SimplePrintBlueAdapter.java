package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

public class SimplePrintBlueAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                //  putRelation(R.id.print_name, "name");

                putViewOnClick(R.id.item_view);
            }
        });

    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
//        Object obj = JavaMethod.getMethodValue(getListBean(position), "getAliasName");
        Object obj = JavaMethod.getMethodValue(getListBean(position), "getName");
        String madress = JavaMethod.getFieldValue(getListBean(position), "mAddress");
        if (obj != null) {
            holder.setText(R.id.print_name, obj.toString() + "  " + madress);
        }
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_print_system_seting);
    }


}
