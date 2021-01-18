package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.OftenBuyActivity;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PickFoodGoodsList;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.UIUtils;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import cn.com.taodaji.model.CartModel;

import com.base.retrofit.ResultInfoCallback;

import tools.activity.MenuToolbarActivity;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.utils.DensityUtil;
import com.base.utils.ViewUtils;


public class OffenBuyFragment extends LoadMoreRecyclerViewFragment {

    private int catalogId;
    private SimpleGoodsInformationAdapter simpleGoodsInformationAdapter;
    private int entityId, entityType;

    public void setCatalogId(int categoryId) {
        this.catalogId = categoryId;
    }

    private View iv_shopping_cart;
    private TextView tv_shopping_count;

    public void setIv_shopping_cart(View iv_shopping_cart) {
        this.iv_shopping_cart = iv_shopping_cart;
    }

    public void setTv_shopping_count(TextView tv_shopping_count) {
        this.tv_shopping_count = tv_shopping_count;
    }

    @Override
    public void initData() {
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(getContext()));
        simpleGoodsInformationAdapter = new SimpleGoodsInformationAdapter();


        CartModel cartModel = CartModel.getInstance();
        tv_shopping_count.setText(String.valueOf(cartModel.getCount()));

        simpleGoodsInformationAdapter.setCountImage(tv_shopping_count);
        simpleGoodsInformationAdapter.setContrast_price(false);

        simpleGoodsInformationAdapter.setmMainLayout(mainView);
        simpleGoodsInformationAdapter.setmShoppingCart(iv_shopping_cart);
        recycler_targetView.setAdapter(simpleGoodsInformationAdapter);
//        swipeToLoadLayout.setPadding(0, 0, 0, DensityUtil.dp2px(55));
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshing(true);
        loadMoreUtil.setHasLoadMore(false);
    }

    @Override
    public void onUserVisible() {
        //  if (simpleGoodsInformationAdapter.getRealItemCount() == 0)
        super.onUserVisible();
        //    else simpleGoodsInformationAdapter.moveToPosition(simpleGoodsInformationAdapter.getHeadersCount());
    }

    @Override
    public void getData(int pn) {
            if (PublicCache.loginPurchase != null) {
                entityId = PublicCache.loginPurchase.getEntityId();
                entityType = 0;
            } else if (PublicCache.loginSupplier != null) {
                entityId = PublicCache.loginSupplier.getEntityId();
                entityType = 1;
            } else {
                entityId = -1;
                entityType = -1;
            }

            getRequestPresenter().oftenBuy(catalogId, entityId, entityType, pn, 9, new ResultInfoCallback<PickFoodGoodsList>() {
                @Override
                public void onSuccess(PickFoodGoodsList body) {
                    loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                    stop();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    stop();
                }
            });
        }

    }

//}
