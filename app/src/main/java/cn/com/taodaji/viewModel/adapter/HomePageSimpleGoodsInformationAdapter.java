package cn.com.taodaji.viewModel.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.application.MyApplication;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.viewModel.ProblemAdapter;
import cn.com.taodaji.viewModel.vm.goods.GoodsInformationVM;
import tools.shopping_anim.ShoppingButtonNew;

public class HomePageSimpleGoodsInformationAdapter extends SingleRecyclerViewAdapter {

    public TextView count_image;
    public TextView cart_price;
    private CartModel cartModel = CartModel.getInstance();

    private boolean contrast_price = false;
    private View mShoppingCart;//购物车
    private ViewGroup mMainLayout;//toolbar下面的主窗体

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

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new GoodsInformationVM(true));
    }

    public HomePageSimpleGoodsInformationAdapter() {
        super();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_home_goods_information_list);
    }



    @Override
    public void onBindViewHolderBind(BaseViewHolder holder, int position) {
        super.onBindViewHolderBind(holder, position);
    }


    //下拉刷新会用到
    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        GoodsInformation bean = (GoodsInformation) getListBean(position);
        if (bean == null) return;
        //比价
       ImageView tv_store= holder.findViewById(R.id.tv_store);
        RelativeLayout rl_djq= holder.findViewById(R.id.rl_djq);
        if (bean.getStoreType()==-1){
            tv_store.setVisibility(View.GONE);
        }else if (bean.getStoreType()==1){
            tv_store.setVisibility(View.VISIBLE);
            tv_store.setImageResource(R.mipmap.qijian_home);
        }else if (bean.getStoreType()==2){
            holder.findViewById(R.id.tv_store).setVisibility(View.VISIBLE);
            tv_store.setImageResource(R.mipmap.zhuanmai_home);
        }
        if (bean.getShopCouponsCount()>0){
            rl_djq.setVisibility(View.VISIBLE);
        }else {
            rl_djq.setVisibility(View.GONE);
        }
        if (!ListUtils.isNullOrZeroLenght(bean.getEavluationLevelOneNum())){
            holder.setText(R.id.tv_eavluationLevelOneNum,bean.getEavluationLevelOneNum()+"条评价");

        }
        holder.setText(R.id.tv_eavluationRatioNum,"好评"+bean.getEavluationRatioNum()+"%");

        if (contrast_price) holder.findViewById(R.id.contrast_price).setVisibility(View.VISIBLE);
        else holder.findViewById(R.id.contrast_price).setVisibility(View.GONE);

        //特价活动，限购数量
        View unit = holder.findViewById(R.id.unit);
        TextView purchase_restrictions = holder.findViewById(R.id.purchase_restrictions);

        //是否为促销商品
        if (bean.getProductType() == 1) {
            // holder.setVisibility(R.id.special_offer, View.VISIBLE);
            if (bean.getAllowPurchase() > 100) {
                holder.setVisibility(R.id.textView_1, View.GONE);
                if (unit != null) unit.setVisibility(View.GONE);
                if (purchase_restrictions != null) {
                    purchase_restrictions.setVisibility(View.GONE);
                }
            } else {
                holder.setVisibility(R.id.textView_1, View.VISIBLE);
                if (unit != null) unit.setVisibility(View.VISIBLE);
                if (purchase_restrictions != null) {
                    purchase_restrictions.setVisibility(View.VISIBLE);
                    purchase_restrictions.setText(String.valueOf(bean.getAllowPurchase() - bean.getAlreadyPurchase()));//
                }
            }

        } else {
            holder.setVisibility(R.id.textView_1, View.GONE);
            if (unit != null) unit.setVisibility(View.GONE);
            if (purchase_restrictions != null) {
                purchase_restrictions.setVisibility(View.GONE);
            }
        }

        RecyclerView recyclerView1 = holder.findViewById(R.id.rv_goods_property);
        List<ProblemItem> itemList = new ArrayList<>();
        ProblemAdapter adapter = new ProblemAdapter(itemList, MyApplication.getApplication());
        List<NameValue> parseArray = JSON.parseArray(bean.getProductProperty(), NameValue.class);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        if (parseArray!=null){
            if (parseArray.size()>10) {
                recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
            }
        }
        recyclerView1.setAdapter(adapter);

        if (parseArray!=null){
            for (NameValue value : parseArray){
                ProblemItem item = new ProblemItem();
                item.setText(value.getPValue());
                itemList.add(item);
            }
            adapter.notifyDataSetChanged();
        }


        //多规格
        BigDecimal maxPrice = bean.getMaxPrice();
        if (maxPrice == null) return;
        if (bean.getSpecs() == null) return;
        holder.setText(R.id.goods_max_price, String.valueOf(bean.getMaxPrice()));
        //如果只有一个规格
        if (maxPrice.compareTo(new BigDecimal(-1)) == 0 && bean.getSpecs().size() == 1) {

            GoodsSpecification gsf = bean.getSpecs().get(0);
            if (gsf == null) return;

            holder.setText(R.id.avgPrice, String.valueOf(gsf.getAvgPrice()));
            holder.setText(R.id.avgUnit, String.valueOf(gsf.getAvgUnit()));

            if (gsf.getLevelType() == 1) {
                holder.setVisibility(R.id.goods_spec_more, View.GONE);
                holder.setVisibility(R.id.goods_spec_1, View.GONE);
            } else {
                holder.setVisibility(R.id.goods_spec_more, View.GONE);
                holder.setVisibility(R.id.goods_spec_1, View.VISIBLE);

                holder.setText(R.id.level2Value, gsf.getLevel2Value() + "");
                holder.setText(R.id.level2Unit, gsf.getLevel2Unit());
                if (gsf.getLevelType() == 3) {
                    holder.setVisibility(R.id.specification_split, View.VISIBLE);
                    holder.setText(R.id.level3Value, gsf.getLevel3Value() + "");
                    holder.setText(R.id.level3Unit, gsf.getLevel3Unit());
                } else {
                    holder.setVisibility(R.id.specification_split, View.GONE);
                    holder.setText(R.id.level3Value, "");
                    holder.setText(R.id.level3Unit, "");
                }
            }

            holder.setVisibility(R.id.avg_group, View.VISIBLE);
            holder.setVisibility(R.id.bt_shopping, View.VISIBLE);
            holder.setVisibility(R.id.tv_spec_help, View.GONE);

            ShoppingButtonNew shoppingButton = holder.findViewById(R.id.bt_shopping);
            shoppingButton.setmShoppingCart(mShoppingCart);
            shoppingButton.setmMainLayout(mMainLayout);
            shoppingButton.setCartGoodsBean(bean);
            bean.setProductQty(cartModel.getCount(gsf.getSpecId()));
            shoppingButton.setGoodsCount(bean.getProductQty());


        } else {

            holder.setVisibility(R.id.avg_group, View.GONE);
            holder.setVisibility(R.id.bt_shopping, View.GONE);
            holder.setVisibility(R.id.tv_spec_help, View.VISIBLE);

            holder.setVisibility(R.id.goods_spec_more, View.VISIBLE);
            holder.setVisibility(R.id.goods_spec_1, View.GONE);
            holder.setText(R.id.goods_max_price, String.valueOf(bean.getMaxPrice()));

        }

    }

    //接收商品数量变化事件
    @Subscribe(priority = 100)
    public void onMessageEvent(CartEvent event) {

        if (count_image != null) count_image.setText(String.valueOf(cartModel.getCount()));
        if (cart_price != null) cart_price.setText(String.valueOf(cartModel.getPriceSum()));

        int pos = getPosition(event.getData().getSpecId());
        if (pos > -1) {
            update(pos, "productQty", event.getData().getProductQty());
        }

    }

    private int getPosition(int specId) {
        for (int i = getFirstPosition(); i <= getLastPosition(); i++) {
            GoodsInformation bean = (GoodsInformation) getListBean(i);
            if (bean != null && bean.getSpecs() != null && bean.getSpecs().size() > 0) {
                if (bean.getSpecs().get(0).getSpecId() == specId) {
                    if (bean.getSpecs().size() > 1) return -1;
                    else return i;
                }
            }
        }
        return -1;
    }

}
