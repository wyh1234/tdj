package cn.com.taodaji.viewModel.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.ShopInformation;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.vm.shop.ShopListViewModel;

import java.math.BigDecimal;
import java.util.List;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public abstract class SimpleShopListAdapter extends SingleRecyclerViewAdapter {
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_search_shop_list);
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new ShopListViewModel());
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        //   super.onBindViewHolder(holder, position);

       TextView tv_isShow  = holder.findViewById(R.id.tv_isShow);
        int itemType = getItemViewType(position);
        //子项目
        if (itemType == TYPE_CHILD) {
            if (getListBean(position) instanceof ShopInformation){
                ShopInformation bean = (ShopInformation) getListBean(position);
                if (bean.getStoreType() == 1) {
                    tv_isShow.setVisibility(View.VISIBLE);
                } else {
                    tv_isShow.setVisibility(View.GONE);
                }
            }

        }
        RecyclerView recyclerView1 = holder.findViewById(R.id.shop_goods_list);
        SimpleGoodsAdapter searchShopViewItemAdapter = (SimpleGoodsAdapter) recyclerView1.getAdapter();

        if (searchShopViewItemAdapter == null) {
            recyclerView1.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            searchShopViewItemAdapter = new SimpleGoodsAdapter();
            recyclerView1.setAdapter(searchShopViewItemAdapter);
        }
        if (getGoodsList(position) != null && getGoodsList(position).size() > 0) {
            if (searchShopViewItemAdapter.isHasFooter()) {
                searchShopViewItemAdapter.removeAllFooter();
            }
            searchShopViewItemAdapter.setList(getGoodsList(position));
        } else {
            View view = ViewUtils.getFragmentView(recyclerView1, R.layout.t_textview);
            TextView textView = ViewUtils.findViewById(view, R.id.textView);
            int w = UIUtils.getScreenWidthPixels();
            UIUtils.setViewSize(view, w, w / 3);
            textView.setText("暂无上架商品，敬请期待!");
            searchShopViewItemAdapter.clear();
            searchShopViewItemAdapter.addFooterView(view);
        }

    }

    public abstract List<GoodsInformation> getGoodsList(int position);

}
