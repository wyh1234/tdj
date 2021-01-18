package cn.com.taodaji.viewModel.adapter;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.application.MyApplication;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.myself.PickupServiceActivity;
import cn.com.taodaji.ui.fragment.PickFoodListFragment;
import cn.com.taodaji.viewModel.ProblemAdapter;
import cn.com.taodaji.viewModel.vm.goods.GoodsInformationVM;

import com.alibaba.fastjson.JSON;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;

import tools.shopping_anim.ShoppingButtonNew;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static cn.com.taodaji.common.Constants.goodsNameLength;

public class SimpleGoodsInformationAdapter extends SingleRecyclerViewAdapter {

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
        putBaseVM(TYPE_CHILD, new GoodsInformationVM());
    }

    public SimpleGoodsInformationAdapter() {
        super();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_goods_information_list);
    }

    public void setCountImage(TextView count_image) {
        this.count_image = count_image;
    }

    public void setCartPrice(TextView cart_price) {
        this.cart_price = cart_price;
    }

    public void setContrast_price(boolean contrast_price) {
        this.contrast_price = contrast_price;
    }


    @Override
    public void onBindViewHolderBind(BaseViewHolder holder, int position) {
        super.onBindViewHolderBind(holder, position);
        holder.setText(R.id.tv_spec_help, "多规格");

        GoodsInformation bean = (GoodsInformation) getListBean(position);
        if (bean == null) return;
        ImageView tv_store= holder.findViewById(R.id.tv_store);
        if (bean.getStoreType()==-1){
            tv_store.setVisibility(View.GONE);
        }else if (bean.getStoreType()==1){
            tv_store.setVisibility(View.VISIBLE);
            tv_store.setImageResource(R.mipmap.qijian_home);
        }else if (bean.getStoreType()==2){
//            holder.findViewById(R.id.tv_store).setVisibility(View.VISIBLE);
            tv_store.setImageResource(R.mipmap.zhuanmai_home);
        }
        if (ListUtils.isNullOrZeroLenght(bean.getEavluationLevelOneNum())){
            holder.setText(R.id.tv_eavluationLevelOneNum,"0条评价");

        }else {
            holder.setText(R.id.tv_eavluationLevelOneNum,bean.getEavluationLevelOneNum()+"条评价");
        }
        holder.setText(R.id.tv_eavluationRatioNum,"好评"+bean.getEavluationRatioNum()+"%");
        RecyclerView recyclerView = holder.findViewById(R.id.specificationRecyclerView);
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

        GoodsSpecificationAdapter specificationAdapter = (GoodsSpecificationAdapter) recyclerView.getAdapter();
        if (specificationAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            specificationAdapter = new GoodsSpecificationAdapter();
            specificationAdapter.setmMainLayout(mMainLayout);
            specificationAdapter.setmShoppingCart(mShoppingCart);
            specificationAdapter.setRecyclerView(getRecyclerView());
            recyclerView.setAdapter(specificationAdapter);
        } else {
            if (holder.getText(R.id.tv_spec_help).equals("收起") && bean.getSpecs().size() > 1)
                specificationAdapter.setList(bean.getSpecs(), false);
            else specificationAdapter.clear();
        }
    }


    //下拉刷新会用到
    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        GoodsInformation bean = (GoodsInformation) getListBean(position);
        if (bean == null) return;

        //名称太长处理
//        View textView21 = holder.findViewById(R.id.textView_21);
//        View textView22 = holder.findViewById(R.id.textView_22);
//
//        int nameL = bean.getName().length();
//        String ss = bean.getNickName();
//        int niceName = ss.length();
//
//        if (nameL >= goodsNameLength) ss = "";
//        else if (nameL + niceName > goodsNameLength) {
//            ss = ss.substring(0, goodsNameLength - nameL) + "..";
//        }
//
//        holder.setText(R.id.goods_nickName, ss);
//
//        if (TextUtils.isEmpty(ss)) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }


        //比价
        if (contrast_price) holder.findViewById(R.id.contrast_price).setVisibility(View.VISIBLE);
        else holder.findViewById(R.id.contrast_price).setVisibility(View.GONE);


        // holder.setText(R.id.goods_saleCount,String.valueOf(gsf.getSalesNumber()));

        //特价活动，限购数量
        View unit = holder.findViewById(R.id.unit);
        TextView purchase_restrictions = holder.findViewById(R.id.purchase_restrictions);

/*
        //是否是精品蔬菜
        if (bean.getProductType() == 3) holder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
        else holder.setVisibility(R.id.img_hot_sale, View.GONE);

       holder.setVisibility(R.id.jinpin, View.VISIBLE);

        //商品标准“1”：通货商品 “2”：精品商品 '
        if (bean.getProductCriteria()==2 ) {
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
        }else{
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
        }*/

        //是否为促销商品
        if (bean.getProductType() == 1||bean.getProductType() == 4) {
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
                    if (bean.getProductType() == 1){
                        purchase_restrictions.setText(String.valueOf(bean.getAllowPurchase() - bean.getAlreadyPurchase()));//
                    }else {
                        purchase_restrictions.setText(String.valueOf(bean.getAllowPurchase()));//

                    }
                }
            }


        } else {
            // holder.setVisibility(R.id.special_offer, View.GONE);
            holder.setVisibility(R.id.textView_1, View.GONE);
            if (unit != null) unit.setVisibility(View.GONE);
            if (purchase_restrictions != null) {
                purchase_restrictions.setVisibility(View.GONE);
            }
        }


        //多规格
        BigDecimal maxPrice = bean.getMaxPrice();
        if (maxPrice == null) return;
        if (bean.getSpecs() == null) return;
        holder.setText(R.id.goods_max_price, String.valueOf(bean.getMaxPrice()));

        RecyclerView recyclerView = holder.findViewById(R.id.specificationRecyclerView);
        GoodsSpecificationAdapter specificationAdapter = (GoodsSpecificationAdapter) recyclerView.getAdapter();
        if (specificationAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            specificationAdapter = new GoodsSpecificationAdapter();
            specificationAdapter.setmMainLayout(mMainLayout);
            specificationAdapter.setmShoppingCart(mShoppingCart);
            specificationAdapter.setRecyclerView(getRecyclerView());
            recyclerView.setAdapter(specificationAdapter);
        } else {
            if (holder.getText(R.id.tv_spec_help).equals("收起") && bean.getSpecs().size() > 1)
                specificationAdapter.setList(bean.getSpecs(), false);
            else specificationAdapter.clear();
        }


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

            specificationAdapter.setGoodsInformation(bean);

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

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object abean) {
        if (onclickType == 0) {
            if (view.getId() == R.id.tv_spec_help) {
                TextView textView = (TextView) view;
                GoodsInformation bean = (GoodsInformation) abean;
                if (bean == null || bean.getSpecs() == null) return true;
                BaseViewHolder holder = getViewHolder(position);
                if (holder != null) {
                    RecyclerView recyclerView = holder.findViewById(R.id.specificationRecyclerView);
                    GoodsSpecificationAdapter specificationAdapter = (GoodsSpecificationAdapter) recyclerView.getAdapter();
                    if (specificationAdapter != null) {
                        if (textView.getText().toString().equals("收起")) {
                            textView.setText("多规格");
                            specificationAdapter.clear();
                        } else {
                            textView.setText("收起");
                            specificationAdapter.setList(bean.getSpecs(), false);
                        }
                    }
                }


                return true;
            }
        }
        return false;
    }
}
