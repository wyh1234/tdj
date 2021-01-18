package cn.com.taodaji.viewModel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.viewModel.vm.SpecificationVM;
import tools.shopping_anim.ShoppingButtonNew;

/**
 * Created by Administrator on 2018-02-26.
 */

public class GoodsSpecificationAdapter extends SingleRecyclerViewAdapter {

    private View mShoppingCart;//购物车
    private ViewGroup mMainLayout;//toolbar下面的主窗体
    private RecyclerView recyclerView;//主RecyclerView
    private GoodsInformation goodinfo;//商品

    public void setGoodsInformation(GoodsInformation goodsInformation) {
        this.goodinfo = goodsInformation;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setmShoppingCart(View mShoppingCart) {
        this.mShoppingCart = mShoppingCart;
    }

    public void setmMainLayout(ViewGroup mMainLayout) {
        this.mMainLayout = mMainLayout;
    }


    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    public GoodsSpecificationAdapter() {
        super();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new SpecificationVM());
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        GoodsSpecification gsf = (GoodsSpecification) getListBean(position);
        if (gsf != null) {
            ShoppingButtonNew shoppingButton = holder.findViewById(R.id.bt_shopping);
            if (shoppingButton != null) {
                shoppingButton.setGoodsCount(CartModel.getInstance().getCount(gsf.getSpecId()));
            }
        }
    }

    @Override
    public void onBindViewHolderBind(BaseViewHolder holder, int position) {
        super.onBindViewHolderBind(holder, position);

        GoodsSpecification bean = (GoodsSpecification) getListBean(position);
        if (bean == null) return;

        ShoppingButtonNew shoppingButton = holder.findViewById(R.id.bt_shopping);
        if (shoppingButton != null) {
            shoppingButton.setmShoppingCart(mShoppingCart);
            shoppingButton.setmMainLayout(mMainLayout);
        }
    }

    //接收商品数量变化事件
    @Subscribe(priority = 100)
    public void onMessageEvent(CartEvent event) {
        int pos = getPosition(event.getData().getSpecId());
        if (pos > -1) {
            update(pos, "productQty", event.getData().getProductQty());
        }
    }

    private int getPosition(int specId) {
        for (int i = getFirstPosition(); i <= getLastPosition(); i++) {
            GoodsSpecification bean = (GoodsSpecification) getListBean(i);
            if (bean != null) {
                if (bean.getSpecId() == specId) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_goods_specification_list);
    }

}
