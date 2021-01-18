package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

public class ShareShopListAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("enterpriseCode", R.id.text_shop_name);
                putRelation("nickName",R.id.text_person_name);
                putRelation("account",R.id.text_shop_phone);
            }

            @Override
            protected void addOnclick() {

              //  putViewOnClick(R.id.item_view);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_share_shop_list);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

       // img_green_point
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
