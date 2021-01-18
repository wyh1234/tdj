package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

public class SearchShopTypeListAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("name", R.id.text_type_name);
//                putRelation("person_name",R.id.text_person_name);
//                putRelation("phone",R.id.text_shop_phone);
            }

            @Override
            protected void addOnclick() {

                putViewOnClick(R.id.item_view);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_search_shop_type_list);
    }
}
