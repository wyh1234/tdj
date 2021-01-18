package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;

import cn.com.taodaji.model.entity.ShopDetail_Goods;
import cn.com.taodaji.model.entity.StoreRecommend;
import cn.com.taodaji.model.CartModel;

import com.base.retrofit.ResultInfoCallback;

import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class StoreRecommendDetailActivity extends SimpleToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onEvent(StoreRecommend.ListBean bean) {
        EventBus.getDefault().removeStickyEvent(bean);
        ImageLoaderUtils.loadImage(imageView,  bean.getBannerImgUrl());
        simple_title.setText(bean.getName());
        store = bean.getStore();
    }

    private GlideImageView imageView;
    private TextView count_image;
    private CartModel cartModel;
    private SimpleGoodsInformationAdapter goodsView_adapter;
    private int store;

    @Override
    protected void initMainView() {
        //商家图片
        imageView = ViewUtils.getImageView(this);
        UIUtils.setViewSize(imageView, UIUtils.getScreenWidthPixels(), UIUtils.dip2px(150));
        title.addView(imageView);

        //底部购物车
        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        View mShoppingCart = findViewById(R.id.iv_shopping_cart);
        count_image = findViewById(R.id.tv_shopping_count);

        cartModel = CartModel.getInstance();

        count_image.setText(String.valueOf(cartModel.getCount()));
        // 商品列表
        RecyclerView goodsView_recyclerView = getLayoutView(R.layout.t_recyclerview);
        body_other.addView(goodsView_recyclerView);
        goodsView_adapter = new SimpleGoodsInformationAdapter();
        goodsView_adapter.setCountImage(count_image);

        goodsView_recyclerView.setAdapter(goodsView_adapter);

        goodsView_adapter.setmMainLayout(mainLayout);
        goodsView_adapter.setmShoppingCart(mShoppingCart);

        //去挑菜
        View foot_view = ViewUtils.getFragmentView(goodsView_recyclerView, R.layout.item_commend_footer);
        goodsView_adapter.addFooterView(foot_view);
        foot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuToolbarActivity.goToPage(2);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        getRequestPresenter().getShop_goods_detail(store, 1, 10, new ResultInfoCallback<ShopDetail_Goods>() {
            @Override
            public void onSuccess(ShopDetail_Goods body) {
                goodsView_adapter.setList(body.getItems());
            }

            @Override
            public void onFailed(int shopDetail_goodsResultInfo, String msg) {

            }
        });
    }

    public static void startActivity(Context context, StoreRecommend.ListBean bean) {
        EventBus.getDefault().postSticky(bean);
        context.startActivity(new Intent(context, StoreRecommendDetailActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_cart:
                MenuToolbarActivity.goToPage(3);
                break;
        }
    }
}
