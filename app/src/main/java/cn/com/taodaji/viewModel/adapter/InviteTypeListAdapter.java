package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.InviteTypeBean;

public class InviteTypeListAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("type", R.id.txt_type);
//                putRelation("nickName",R.id.text_person_name);
//                putRelation("account",R.id.text_shop_phone);
            }

            @Override
            protected void addOnclick() {

               putViewOnClick(R.id.txt_type);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_invite_type);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        InviteTypeBean bean=(InviteTypeBean)getListBean(position);

        if (position==getLastPosition()) {
            holder.setVisibility(R.id.line_type, View.GONE);
        }else {
            holder.setVisibility(R.id.line_type, View.VISIBLE);
        }

    }
}
