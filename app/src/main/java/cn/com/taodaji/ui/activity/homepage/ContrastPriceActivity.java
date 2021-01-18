package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.model.entity.GoodsInformation;

import com.base.utils.UIUtils;
import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.model.entity.SearchGoods3;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.CartModel;

import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.viewModel.vm.goods.GoodsInformationVM;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.ViewUtils;

public class ContrastPriceActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("比价");
    }

    SimpleGoodsInformationAdapter adapter;


    private GoodsInformation goodsInformation;
    private RecyclerView recyclerView;
    private TextView goods_count;

    public TextView count_image;
    public CartModel cartModel;
    public View mShoppingCart;

    @Override
    protected void initMainView() {
        recyclerView = ViewUtils.getLayoutView(this, R.layout.t_recyclerview);
        body_other.addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleGoodsInformationAdapter();

        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        mShoppingCart = findViewById(R.id.iv_shopping_cart);
        count_image = findViewById(R.id.tv_shopping_count);

        cartModel = CartModel.getInstance();
        count_image.setText(String.valueOf(cartModel.getCount()));
        adapter.setCountImage(count_image);

        adapter.setmMainLayout(mainLayout);
        adapter.setmShoppingCart(mShoppingCart);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        if (goodsInformation == null) return;
        View heard_view1 = ViewUtils.getFragmentView(recyclerView, R.layout.item_goods_information_list);
        View heard_view2 = ViewUtils.getFragmentView(recyclerView, R.layout.activity_contrast_price_splite);
        goods_count = ViewUtils.findViewById(heard_view1, R.id.goods_count);

        adapter.addHeaderView(heard_view1);
        adapter.addHeaderView(heard_view2);

        BaseViewHolder viewHolder = new BaseViewHolder(heard_view1, new GoodsInformationVM(), null);
        viewHolder.setValues(goodsInformation);


        heard_view1.findViewById(R.id.contrast_price).setVisibility(View.GONE);

        if (TextUtils.isEmpty(goodsInformation.getCredentialsImage())) {
            heard_view1.findViewById(R.id.look_document).setVisibility(View.GONE);
        }
        if (goodsInformation.getProductType() == 1) {
            heard_view1.findViewById(R.id.special_offer).setVisibility(View.VISIBLE);
            heard_view1.findViewById(R.id.textView_1).setVisibility(View.VISIBLE);
            TextView purchase_restrictions = (TextView) heard_view1.findViewById(R.id.purchase_restrictions);
            purchase_restrictions.setVisibility(View.VISIBLE);
            purchase_restrictions.setText(String.valueOf(goodsInformation.getAllowPurchase() - goodsInformation.getAlreadyPurchase()));
            heard_view1.findViewById(R.id.unit).setVisibility(View.VISIBLE);
            //  heard_view1.findViewById(R.id.contrast_price).setVisibility(View.VISIBLE);

        } else if (goodsInformation.getProductType() == 0) {
            heard_view1.findViewById(R.id.special_offer).setVisibility(View.GONE);
            heard_view1.findViewById(R.id.textView_1).setVisibility(View.GONE);
            heard_view1.findViewById(R.id.purchase_restrictions).setVisibility(View.GONE);
            heard_view1.findViewById(R.id.unit).setVisibility(View.GONE);
            //  heard_view1.findViewById(R.id.contrast_price).setVisibility(View.GONE);
        }
        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        getRequestPresenter().getSearchGood(userType, goodsInformation.getName(), "price", 0, -1,"", 1, 999,0, new ResultInfoCallback<SearchGoods3>() {
            @Override
            public void onFailed(int errCode, String msg) {
                // loadingDimss();
                UIUtils.showToastSafe(msg);
            }

            @Override
            public void onSuccess(SearchGoods3 body) {
                List<GoodsInformation> list = new ArrayList<>();
                for (GoodsInformation information : body.getItems()) {
                    if (information.getEntityId() != goodsInformation.getEntityId()) {
                        list.add(information);
                    }
                }
                adapter.setList(list);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //接收商品数量变化事件
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        goods_count.setText(String.valueOf(event.getData().getProductQty()));
        if (count_image != null) count_image.setText(String.valueOf(cartModel.getCount()));
    }

    @Subscribe(sticky = true)
    public void onEvent(GoodsInformation goodsInformation) {
        this.goodsInformation = goodsInformation;
        EventBus.getDefault().removeStickyEvent(goodsInformation);
    }

    public static void startActivity(Context context, GoodsInformation goodsInformation) {
        EventBus.getDefault().postSticky(goodsInformation);
        context.startActivity(new Intent(context, ContrastPriceActivity.class));
    }

}
