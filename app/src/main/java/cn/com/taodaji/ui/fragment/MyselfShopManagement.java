package cn.com.taodaji.ui.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.HaltSaleProduct;
import cn.com.taodaji.model.entity.ModifyInventory;
import cn.com.taodaji.model.entity.ProductStatus;
import cn.com.taodaji.model.event.CityChangeEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.ShopManagementTabLayoutTextEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.activity.integral.fragment.ModifyPriceActivity;
import cn.com.taodaji.ui.activity.shopManagement.ReleaseGoodsActivity;
import cn.com.taodaji.ui.activity.shopManagement.SpecificationActivity;
import cn.com.taodaji.ui.activity.shopManagement.SpecificationAddActivity;
import cn.com.taodaji.viewModel.adapter.SimpleShopManagementAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsDelete;
import cn.com.taodaji.model.entity.GoodsInformation;

import com.base.utils.DialogUtils;
import com.base.viewModel.adapter.OnItemClickListener;

import cn.com.taodaji.model.entity.TakeDown;
import cn.com.taodaji.model.entity.TakeUp;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivity;
import cn.com.taodaji.ui.activity.shopManagement.ReleaseCommodityGoodsCreateActivity;
import cn.com.taodaji.model.entity.HomepageGridData;
import tools.fragment.LoadMoreRecyclerViewFragment;

import java.util.HashMap;
import java.util.Map;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//商品管理fragment
public class MyselfShopManagement extends LoadMoreRecyclerViewFragment implements View.OnClickListener {

    public static GoodsInformation goodsInformation;

    private SimpleShopManagementAdapter customerSimpleRecyclerViewAdapter;
    private int status;//
    private int pos;
    private String searchStr;
    private View footerView;

    private boolean isNeedUpdate = false;//是否需要更新
    private ProductStatus.DataBean.MapBean productStatusBean;//橱窗限制


    private int goodsCount;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", getTitle());
        outState.putInt("status", status);
        outState.putInt("goodsCount", goodsCount);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            status = savedInstanceState.getInt("status", 1);
            setTitle(savedInstanceState.getString("title"));
            goodsCount = savedInstanceState.getInt("goodsCount", 0);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        if (isNeedUpdate) {
            isNeedUpdate = false;
            customerSimpleRecyclerViewAdapter.notifyItemChanged(pos);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }
    @Subscribe
    public void onEvent(GoodsInformation event) {
        getData(1);
    }
    @Override
    public void initData() {

        mLinearLayoutSearch.setVisibility(View.VISIBLE);            //如果是在商品管理页面才显示这个搜索框
        mButtonSearch.setOnClickListener(this);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        customerSimpleRecyclerViewAdapter = new SimpleShopManagementAdapter();
        customerSimpleRecyclerViewAdapter.setStatus(status);
        customerSimpleRecyclerViewAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, final Object data) {
                if (onclickType == 0) {
                    pos = position;
                    final GoodsInformation bean = (GoodsInformation) data;
                    if (bean == null) return true;
                    Intent intent;
                    switch (view.getId()) {
                        case R.id.shelves_up:

                            if (status == 1) {
                                if (bean.getProductType()!=4) {
                                    loadingShow();
                                    getRequestPresenter().takeDown(bean.getStore(), bean.getEntityId(), new RequestCallback<TakeDown>() {
                                        @Override
                                        public void onSuc(TakeDown body) {
                                            loadingDimss();
                                            customerSimpleRecyclerViewAdapter.remove(pos);
                                            EventBus.getDefault().post(new ShopManagementTabLayoutTextEvent(--goodsCount, getTitle(), status - 1));
                                            //通知下架中有商品下架
                                            EventBus.getDefault().post(body);
                                        }

                                        @Override
                                        public void onFailed(int takeDown, String msg) {
                                            loadingDimss();
                                            UIUtils.showToastSafesShort(msg);
                                        }
                                    });
                                }else {
                                    UIUtils.showToastSafe("每日爆品无法下架");
                                }
                            } else {
                                if (bean.getProductType()==4) {
                                    UIUtils.showToastSafe("每日爆品无法上架");
                                    return true;
                                }
                                if (bean.getSpecs() == null || bean.getSpecs().isEmpty()) {
                                    loadingDimss();
                                    UIUtils.showToastSafesShort("因功能改版，请重新编辑商品，添加新规格。");
                                    return true;
                                }

                                if (productStatusBean == null) {
                                    UIUtils.showToastSafesShort("数据丢失，请刷新！");
                                    return true;
                                } else {
                                    if (productStatusBean.getLimitSalesNum() > 0 && productStatusBean.getPutOn() >= productStatusBean.getLimitSalesNum()) {
                                        DialogUtils.getInstance(getContext()).getSingleDialog(R.layout.dialog_goods_release_limit, "当前已达出售商品橱窗限量" + productStatusBean.getLimitSalesNum() + "个,请下架出售中的商品，再上架其它商品。", "注意").setNegativeButton("确定", null).show();
                                        return true;
                                    }
                                }

                                loadingShow();
                                getRequestPresenter().takeUp(bean.getStore(), bean.getEntityId(), new RequestCallback<TakeUp>() {
                                    @Override
                                    public void onSuc(TakeUp body) {
                                        loadingDimss();
                                        customerSimpleRecyclerViewAdapter.remove(pos);
                                        productStatusBean.setPutOn(productStatusBean.getPutOn() + 1);
                                        EventBus.getDefault().post(new ShopManagementTabLayoutTextEvent(--goodsCount, getTitle(), status - 1));
                                        //通知出售中有商品上架
                                        EventBus.getDefault().post(body);
                                    }

                                    @Override
                                    public void onFailed(int takeUp, String msg) {
                                        loadingDimss();
                                        UIUtils.showToastSafesShort(msg);
                                    }
                                });
                            }

                            return true;
                        case R.id.goods_edit:
                            if ((status!=1||status!=2)&&bean.getProductType()!=4) {
                                if (bean.getIsDrainageArea()==1){
                                    intent = new Intent(getContext(), ReleaseGoodsActivity.class);
                                    intent.putExtra("id",bean.getEntityId());
                                    intent.putExtra("commodityId",bean.getCommodityId());
                                    intent.putExtra("varietyEntityId",bean.getVarietyEntityId());
                                }else {
                                    intent = new Intent(getContext(), ReleaseCommodityGoodsCreateActivity.class);
                                    intent.putExtra("isCanteen", bean.getIsCanteen());//重新编辑
                                    intent.putExtra("isForceTemplate", bean.getIsForceTemplate());//重新编辑

                                }
                                if (status == 3) {
                                    intent.putExtra("goodsEditState", 2);//重新编辑
                                } else {
                                    intent.putExtra("goodsEditState", 1);//编辑
                                }
                                intent.putExtra("custmerExhibitionImage", bean.getCustomerExhibitionImage());//编辑
                                isNeedUpdate = true;
                                goodsInformation = bean;
                                startActivity(intent);

                            }else {
                                UIUtils.showToastSafe("每日爆品无法编辑");
                            }
                            return true;
                        case R.id.tv_inventory_edit:
                            if ((status!=1||status!=2)&&bean.getProductType()!=4) {
                                intent = new Intent(getContext(), ModifyInventoryActivity.class);
                                intent.putExtra("id",bean.getEntityId());
                                startActivity(intent);
                            }else {
                                UIUtils.showToastSafe("每日爆品无法修改库存");
                            }
                            break;
                        case R.id.tv_price_edit:
                            if (bean.getPriceEditable()==1){
                                intent = new Intent(getContext(), ModifyPriceActivity.class);
                                intent.putExtra("id",bean.getEntityId());
                                startActivity(intent);
                            }else {
                                UIUtils.showToastSafe("该商品价格不可编辑");
                            }

                            break;
                        case R.id.view_reason:
                            RequestPresenter.getInstance().getHaltSaleReason(bean.getEntityId(), 0, new RequestCallback<HaltSaleProduct>() {
                                @Override
                                protected void onSuc(HaltSaleProduct body) {
                                    String s1 = "商品名称："+body.getData().getList().get(0).getProductName()+"\n";
                                    String s2 = "违反条款："+body.getData().getList().get(0).getRulerName()+"\n";
                                    String s3 = "因最近"+body.getData().getList().get(0).getSaleDay()+"天，责任售后率达到"+body.getData().getList().get(0).getAfterSaleRate()+"%,于"+body.getData().getList().get(0).getCreateTime()
                                            +"停售"+body.getData().getList().get(0).getHaltDay()+"天";
                                    String[] mess_content ={s1,s2,s3};
                                    int[] ints = new int[2];
                                    DialogUtils.getInstance(getActivity()).getTipsWithButtonDialog("停售原因",mess_content,ints).show();
                                }
                            });
                            break;
                        case R.id.goods_delete:
                            if (status!=2||bean.getProductType()!=4){
                                AlertDialog.Builder builder = ViewUtils.showDialog(getBaseActivity(), "提示", "确认删除该商品？");
                                builder.setCancelable(false);
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("entityId", bean.getEntityId());
                                        loadingShow();
                                        getRequestPresenter().goodsDelete(map, new RequestCallback<GoodsDelete>() {
                                            @Override
                                            public void onSuc(GoodsDelete body) {
                                                loadingDimss();
                                                customerSimpleRecyclerViewAdapter.remove(pos);
                                                EventBus.getDefault().post(new ShopManagementTabLayoutTextEvent(--goodsCount, getTitle(), status - 1));
                                            }

                                            @Override
                                            public void onFailed(int goodsDelete, String msg) {
                                                loadingDimss();
                                                UIUtils.showToastSafesShort(msg);
                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }else {
                                UIUtils.showToastSafe("每日爆品无法删除");
                            }



                            return true;
/*                        case R.id.goods_edit_price:
                            GoodsPriceChangePopupwindow popupwindow = new GoodsPriceChangePopupwindow(swipeToLoadLayout, bean) {
                                @Override
                                public void setResult(Object object) {
                                    Map map = (Map) object;
                                    customerSimpleRecyclerViewAdapter.update(pos, map);
                                }
                            };
                            popupwindow.showAtLocation(swipeToLoadLayout, Gravity.CENTER, 0, 0);
                            return true;*/
                    }
                }
                return false;
            }
        });
        recycler_targetView.setAdapter(customerSimpleRecyclerViewAdapter);
        if (status == 1) {
            footerView = ViewUtils.getFragmentView(recycler_targetView, R.layout.item_shop_management_footer_view);
            footerView.setOnClickListener(this);
            customerSimpleRecyclerViewAdapter.addFooterView(footerView);
        }

        loadMoreUtil.setZeroDataView(R.mipmap.no_order, "您还没有相关的商品");

        initEditextLinstener();
        swipeToLoadLayout.setRefreshing(true);


    }


    public void setTextStr(String str) {
        if (str != null && mEditTextSearch != null) {
            mEditTextSearch.setText(str);
        }
    }

    public void getData(final int pn) {
        searchStr = mEditTextSearch.getText().toString().trim();
        if (PublicCache.loginSupplier != null) {
            getRequestPresenter().getShopManagementListSearch(PublicCache.loginSupplier.getStore(), status, pn, 9, searchStr, new ResultInfoCallback<HomepageGridData>() {
                @Override
                public void onFailed(int homepageGridDataResultInfo, String msg) {
                    stop();
                }

                @Override
                public void onSuccess(HomepageGridData body) {
                    if (status == 1 && footerView != null && body.getPn() == 1 && body.getItems() != null && body.getItems().size() > 0) {
                        customerSimpleRecyclerViewAdapter.addFooterView(footerView);
                    }
                    goodsCount = body.getTotal();
                    EventBus.getDefault().post(new ShopManagementTabLayoutTextEvent(goodsCount, getTitle(), status - 1));
                    loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                    stop();
                }
            });
            addRequest(ModelRequest.getInstance().product_status(PublicCache.site_login, PublicCache.loginSupplier.getStore()), new RequestCallback<ProductStatus>() {
                @Override
                protected void onSuc(ProductStatus body) {
                    productStatusBean = body.getData().getMap();
                }
            });

        }
    }


    private void initEditextLinstener() {
        mImageClear.setOnClickListener(v -> {
            mEditTextSearch.setText("");
            getData(1);
        });

        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchStr = s.toString().trim();
                if (searchStr.length() >= 1) {
                    mImageClear.setVisibility(View.VISIBLE);
                } else {
                    mImageClear.setVisibility(View.GONE);
                }
//                getData(1);
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.swipe_twitter_swipe_toload_recyclerview_search_btn) {
            //搜索按钮点击事件
            getData(1);
        } else if (view.getId() == R.id.swipe_twitter_swipe_toload_recyclerview_clear_img) {
            mEditTextSearch.setText("");
            customerSimpleRecyclerViewAdapter.clear();
            getData(1);
        } else {
            Intent intent = new Intent(getContext(), CommodityCategoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onUserVisible() {
        if (customerSimpleRecyclerViewAdapter != null && customerSimpleRecyclerViewAdapter.getRealCount() == 0)
            super.onUserVisible();
    }

}
