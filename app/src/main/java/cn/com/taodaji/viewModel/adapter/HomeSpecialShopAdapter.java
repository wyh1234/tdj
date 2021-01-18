package cn.com.taodaji.viewModel.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.glide.GlideImageView;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;


public class HomeSpecialShopAdapter extends SingleRecyclerViewAdapter {
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {

            @Override
            protected void dataBinding() {
                putRelation(R.id.img_special_shop, "image_url");
            }


            @Override
            public void setValues(@NonNull View view, Object value) {
                if (view instanceof GlideImageView) {
                    GlideImageView imageView = (GlideImageView) view;
                    imageView.loadImage(value, false);
                } else super.setValues(view, value);
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.item_view);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view1 = ViewUtils.getFragmentView(parent, R.layout.item_home_special_shop);
        return view1;
    }
}
