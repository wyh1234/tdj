package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;
import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.goods.GoodsSimpleVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SimpleGoodsAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new GoodsSimpleVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = ViewUtils.getFragmentView(parent, R.layout.item_homepage_grid_child);
        view.setPadding(20, 0, 20, 0);
        int w = UIUtils.getScreenWidthPixels();
        int image_w = (w - 120) / 3;
        View goods_image = ViewUtils.findViewById(view, R.id.goods_image);
        UIUtils.setViewSize(goods_image, image_w, image_w);
        return view;
    }
}
