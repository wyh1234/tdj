package cn.com.taodaji.viewModel.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

/**
 * Created by yangkuo on 2018/11/24.
 */
public class SimpleShopWindowDeclareAdapter extends SingleRecyclerViewAdapter {


    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return getFragmentView(parent, R.layout.activity_shop_window_declare_item);
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation("sales_number", R.id.tv_sales_number, R.id.ll_goods_limit);
                putRelation("category_name", R.id.tv_category_name);
                putRelation("total_number", R.id.tv_total_number);
            }

            @Override
            public void setValues(@NonNull View view, Object value) {
                if (view.getId() == R.id.ll_goods_limit) {
                    int sales_number = (int) value;
                    if (sales_number == 0) view.setVisibility(View.GONE);
                    else view.setVisibility(View.VISIBLE);
                } else super.setValues(view, value);
            }
        });
    }

}
