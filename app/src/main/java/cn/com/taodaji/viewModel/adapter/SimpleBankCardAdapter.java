package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.BankCardVM;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

public class SimpleBankCardAdapter extends SingleRecyclerViewAdapter {


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BankCardVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_myself_my_bank_card);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        int type = JavaMethod.getFieldValue(getListBean(position), "bankType");
        if (type == 0) holder.setText(R.id.textView_1, "账号：");
        else holder.setText(R.id.textView_1, "卡号：");
    }

}
