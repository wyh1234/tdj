package cn.com.taodaji.ui.activity.market;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.base.activity.ActivityManage;
import com.base.cycleViewPager.CycleViewUtil;
import com.base.listener.OnCustomerItemClickListener;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.model.entity.EvaluationStatics;
import cn.com.taodaji.model.entity.FindProductDetail;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.ShopDetail;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.GoodsDetailEvent;
import cn.com.taodaji.ui.activity.evaluate.EvaluateAllActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.fragment.CartBottomToCartView;
import cn.com.taodaji.viewModel.adapter.GoodsSpecificationAdapter;
import cn.com.taodaji.viewModel.adapter.GoodsUploadDetailAdapter2;
import cn.com.taodaji.viewModel.adapter.ProblemListAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleEvaluateAllAdapter;
import cn.com.taodaji.viewModel.vm.goods.GoodsInformationVM;
import cn.com.taodaji.viewModel.vm.shop.ShopDetailViewModel;
import tools.activity.CollapseToolbarActivity;
import tools.extend.FluidLayout;
import tools.share.ShareUtils;
import tools.shopping_anim.ShoppingButtonNew;

public class ShopGoodsDetailActivity extends CollapseToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(android.R.color.transparent);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        collapsingToolbarLayout.setExpandedTitleColor(UIUtils.getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white));
    }

    private View mainView;
    private int entityId;
    private BaseViewHolder baseViewHolder;
    private BaseViewHolder goodsViewHolder;
    private CycleViewUtil cycleViewUtil;
    private TextView stock;
    private TextView count_image,textView_1,purchase_restrictions,unit;
    private GoodsInformation goodsInformationSimple;
    private CartModel cartModel;
    private View shop_logo, stroll_shop;
    private TextView evaluate_all, evaluate_count, evaluate_value, tv_no_des,tv_isShow;
    private SimpleEvaluateAllAdapter simpleEvaluateAllAdapter;
    private String qqNumber;
    private FluidLayout fluidLayout;
    private GoodsUploadDetailAdapter2 goodsUploadDetailAdapter2;

    private List<ProblemItem> itemList = new ArrayList<>();
    private View mShoppingCart;
    private BaseViewHolder speciViewHolder;
    private ShoppingButtonNew bt_shopping;
    private LinearLayout offShelf;
    private List<GoodsSpecification> goodsSpecificationsList;
    private RecyclerView recyclerView;
    private ProblemListAdapter adapter;

    private TextView evaluate, tv_enshrine_count;
    CartBottomToCartView cartBottomToCartView;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_goods_information_deatil);
        collapse_content.addView(mainView);

        cartModel = CartModel.getInstance();
        floating.setVisibility(View.VISIBLE);
        floating.setImageResource(R.mipmap.ic_qq);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemUtils.isQQClientAvailable(getBaseActivity())) {
                    if (!TextUtils.isEmpty(qqNumber)) SystemUtils.callQQ(qqNumber);
                } else UIUtils.showToastSafesShort("手机未安装QQ");

            }
        });


        cartBottomToCartView = new CartBottomToCartView(this);
        cartBottomToCartView.setBaseActivity(this);
        collapse_bottom.setVisibility(View.VISIBLE);
        collapse_bottom.addView(cartBottomToCartView.getMainView());
        mShoppingCart = cartBottomToCartView.getShopping_cart();

        count_image = cartBottomToCartView.getCount_image();
        count_image.setText(String.valueOf(cartModel.getCount()));

        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_goods_property);
        adapter = new ProblemListAdapter(itemList, ShopGoodsDetailActivity.this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        tv_isShow = ViewUtils.findViewById(mainView, R.id.tv_isShow);
        tv_no_des = ViewUtils.findViewById(mainView, R.id.tv_no_des);
        evaluate_all = ViewUtils.findViewById(mainView, R.id.evaluate_all);
        evaluate_all.setOnClickListener(this);
        evaluate_count = ViewUtils.findViewById(mainView, R.id.evaluate_count);
        evaluate_value = ViewUtils.findViewById(mainView, R.id.evaluate_value);
        ViewUtils.findViewById(mainView, R.id.evaluate_value_group).setOnClickListener(this);

        shop_logo = ViewUtils.findViewById(mainView, R.id.shop_logo);
        stroll_shop = ViewUtils.findViewById(mainView, R.id.stroll_shop);
        stock = ViewUtils.findViewById(mainView, R.id.stock);

        textView_1 = ViewUtils.findViewById(mainView, R.id.textView_1);
        purchase_restrictions = ViewUtils.findViewById(mainView, R.id.purchase_restrictions);
        unit= ViewUtils.findViewById(mainView, R.id.unit);
        collapse_content.setPadding(0, 0, 0, DensityUtil.dp2px(55));

        evaluate = ViewUtils.findViewById(mainView, R.id.shop_evaluate_value);
        evaluate.setTextColor(UIUtils.getColor(R.color.gray_66));
        tv_enshrine_count = mainView.findViewById(R.id.tv_enshrine_count);

        if (cycleViewUtil == null) {
            cycleViewUtil = new CycleViewUtil(this);
            cycleViewUtil.setHightstate(1.0f);
            View view = cycleViewUtil.initView();

            cycleViewUtil.setRight_icon(R.mipmap.icon_special_offer);
            cycleViewUtil.setOnCustomerItemClickListener(new OnCustomerItemClickListener<ADInfo>() {
                @Override
                public void onCustomerItemClick(View view, int position, ADInfo bean) {
                    //  ZoomImageActivity.startActivity(view.getContext(), bean.getImageObject());
                }
            });
            title_collapse_view.addView(view);


            TextView tv_share = view.findViewById(R.id.tv_share);
            tv_share.setVisibility(View.VISIBLE);
            tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodsInformationSimple != null && goodsInformationSimple.getSpecs() != null && goodsInformationSimple.getSpecs().size() > 0) {
                        int enty = -1;
                        int userType = -1;
                        if (PublicCache.loginPurchase != null) {
                            enty = PublicCache.loginPurchase.getEntityId();
                            userType = 0;
                        } else if (PublicCache.loginSupplier != null) {
                            enty = PublicCache.loginSupplier.getEntityId();
                            userType = 1;
                        }
                        GoodsSpecification gs = goodsInformationSimple.getSpecs().get(0);
                        String text =  gs.getPrice() + "元/" + gs.getLevel1Unit();
                        if (gs.getLevelType() == 2) {
                            text += "(" + gs.getLevel2Value() + gs.getLevel2Unit() + ")";
                        } else if (gs.getLevelType() == 3) {
                            text += "(" + gs.getLevel2Value() + gs.getLevel2Unit() + "*" + gs.getLevel3Value() + gs.getLevel3Unit() + ")";
                        }

                        String nickName = "";
                        if (!TextUtils.isEmpty(goodsInformationSimple.getNickName())) {
                            nickName = "(" + goodsInformationSimple.getNickName() + ")";
                        }
                        String shareTitle = goodsInformationSimple.getName() + nickName + " 价格:" + text + "《" + goodsInformationSimple.getStoreName() + "》";
                        String shareDetail = "价格:" + text + "不早起，批发价买所有食材！";
                        new ShareUtils(getBaseActivity()).shareWeb(goodsInformationSimple.getGallery().get(0), PublicCache.getROOT_URL().get(1) + "fund/product/findProductDetail/" + goodsInformationSimple.getEntityId() + "/" + enty + "/" + userType, shareTitle, shareDetail);
                    }
                }
            });
        }


        //评价列表
        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleEvaluateAllAdapter = new SimpleEvaluateAllAdapter();
        simpleEvaluateAllAdapter.setState(0);
        recyclerView.setAdapter(simpleEvaluateAllAdapter);

        baseViewHolder = new BaseViewHolder(mainView, new ShopDetailViewModel(), null);
        goodsViewHolder = new BaseViewHolder(mainView, new GoodsInformationVM(), null);

        //图文详情
        RecyclerView recycleView_con = ViewUtils.findViewById(mainView, R.id.recycleView_con);
        recycleView_con.setLayoutManager(new LinearLayoutManager(this));
        goodsUploadDetailAdapter2 = new GoodsUploadDetailAdapter2();
        recycleView_con.setAdapter(goodsUploadDetailAdapter2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setTransitionName(UIUtils.getString(R.string.transitional_image));
        }
        //规格
        View specView = ViewUtils.findViewById(mainView, R.id.in_specification);
        fluidLayout = ViewUtils.findViewById(mainView, R.id.fluidLayout);
        fluidLayout.setChecked(true);
        fluidLayout.setRadio(true);
        bt_shopping = ViewUtils.findViewById(mainView, R.id.bt_shopping);

        bt_shopping.setmMainLayout(coordinatorLayout);
        bt_shopping.setmShoppingCart(mShoppingCart);

        speciViewHolder = new BaseViewHolder(specView, new GoodsSpecificationAdapter().getVM(0), null);


        fluidLayout.setOnCustomerItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    GoodsSpecification goodsSpecification = (GoodsSpecification) bean;
                    bt_shopping.setGoodsCount(cartModel.getCount(goodsSpecification.getSpecId()));
                    stock.setText(String.valueOf(goodsSpecification.getStock()));
                    speciViewHolder.setValues(goodsSpecification);
                    return true;
                }
                return false;
            }
        });


        if (!EventBus.getDefault().isRegistered(this)) {      //加上判断
            EventBus.getDefault().register(this);
        }


    }


    private void initDatas() {
        onStartLoading();
//        loading();
        //请求商品数据
        getGoodsDetail(entityId);
        //请求评分信息
        getEvaluateValue(entityId);
        //请求评价数据
        getEvaluateData(entityId);
        // evaluate_all.setVisibility(View.GONE);
        //请求图文详情
        getFindProductDetail(entityId);
    }


    @Override
    protected void onResume() {
        super.onResume();
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }

        if (bt_shopping != null && goodsSpecificationsList != null && fluidLayout != null) {
            int pos = fluidLayout.getCheckedPosition();
            if (pos > -1) {
                bt_shopping.setGoodsCount(cartModel.getCount(goodsSpecificationsList.get(pos).getSpecId()));
            }
        }


    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private GoodsSpecification getSpecBean(int specId) {
        if (goodsSpecificationsList == null) return null;
        for (GoodsSpecification goodsSpecification : goodsSpecificationsList) {
            if (goodsSpecification.getSpecId() == specId) return goodsSpecification;
        }
        return null;
    }

    //购买数量变化，事件通知
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        if (event != null) {
            //购物车已购数量
            count_image.setText(String.valueOf(cartModel.getCount()));
            goodsInformationSimple.setProductQty(event.getData().getProductQty());
            if (bt_shopping != null) bt_shopping.setGoodsCount(event.getData().getProductQty());
        }
    }


    //private CartGoodsBean goodsBean = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_pickfood:
                goToPage(2);
                break;
            case R.id.action_cart:
                goToPage(3);
                break;

            case R.id.evaluate_all:
                if (goodsInformationSimple == null) return;
                Intent intent = new Intent(this, EvaluateAllActivity.class);
                intent.putExtra("productId", goodsInformationSimple.getEntityId());
                startActivity(intent);
                break;
            case R.id.evaluate_value_group:
                evaluate_all.callOnClick();
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(GoodsDetailEvent event) {

        EventBus.getDefault().removeStickyEvent(event);
        entityId = event.getCode();

        count_image.setText(String.valueOf(cartModel.getCount()));
        cartBottomToCartView.setStoreOrProdId(entityId);


        initDatas();
//        supportStartPostponedEnterTransition();
    }

    //请求商品数据
    private void getGoodsDetail(int productId) {
        int userType = 0;
        int personId = 0;

        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getFlag() == 1)
                personId = PublicCache.loginPurchase.getEntityId();
            else personId = PublicCache.loginPurchase.getSubUserId();
        } else if (PublicCache.loginSupplier != null) {
            userType = 1;
            personId = PublicCache.loginSupplier.getEntityId();
        }

        getRequestPresenter().showGoodsInformation(productId, userType, personId, new ResultInfoCallback<GoodsInformation>(this) {
            @Override
            public void onSuccess(final GoodsInformation event) {
//                loadingDimss();
//                supportStartPostponedEnterTransition();
//                collapsingToolbarLayout.setTitle(event.getName());
                if (detail_title != null) detail_title.setText(event.getName());
                goodsInformationSimple = event;
                if (event.getSpecs() == null || event.getSpecs().size() == 0) return;
                goodsSpecificationsList = event.getSpecs();
                List<NameValue> parseArray = JSON.parseArray(event.getProductProperty(), NameValue.class);
                if (parseArray!=null){
                    for (NameValue value : parseArray){
                        ProblemItem item = new ProblemItem();
                        item.setText(value.getPName()+":"+value.getPValue());
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }

              /*  if (TextUtils.isEmpty(event.getNickName())) {
                    mainView.findViewById(R.id.textView_21).setVisibility(View.GONE);
                    mainView.findViewById(R.id.textView_22).setVisibility(View.GONE);
                } else {
                    mainView.findViewById(R.id.textView_21).setVisibility(View.VISIBLE);
                    mainView.findViewById(R.id.textView_22).setVisibility(View.VISIBLE);
                }*/

                if (bt_shopping != null) {
                    goodsInformationSimple.setProductQty(cartModel.getCount(goodsSpecificationsList.get(0).getSpecId()));
                    bt_shopping.setCartGoodsBean(goodsInformationSimple);
                }
                if (cartBottomToCartView != null) cartBottomToCartView.setFavorite(event.isFavor());
              /*  //是否是精品蔬菜
                if (event.getProductType() == 3)
                    goodsViewHolder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
                else goodsViewHolder.setVisibility(R.id.img_hot_sale, View.GONE);

                goodsViewHolder.setVisibility(R.id.jinpin, View.VISIBLE);

                //商品标准“1”：通货商品 “2”：精品商品 '
                if (event.getProductCriteria() == 2) {
                    goodsViewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_jin_red);
                } else {
                    goodsViewHolder.setImageRes(R.id.jinpin, R.mipmap.icon_tong_blue);
                }
*/
                //是否为促销商品
                if (event.getProductType() == 1) {
                    cycleViewUtil.setShowRight(true);
                } else {
                    cycleViewUtil.setShowRight(false);
                }
                //是否为促销商品
                if (event.getProductType() == 4) {
                    // holder.setVisibility(R.id.special_offer, View.VISIBLE);
                    if (event.getAllowPurchase() > 100) {
                        textView_1.setVisibility(View.GONE);
                        if (unit != null) unit.setVisibility(View.GONE);
                        if (purchase_restrictions != null) {
                            purchase_restrictions.setVisibility(View.GONE);
                        }
                    } else {
                        textView_1.setVisibility( View.VISIBLE);
                        if (unit != null) unit.setVisibility(View.VISIBLE);
                        if (purchase_restrictions != null) {
                            purchase_restrictions.setVisibility(View.VISIBLE);
                            purchase_restrictions.setText(String.valueOf(event.getAllowPurchase() ));//
                        }
                    }


                } else {
                    textView_1.setVisibility( View.GONE);
                    if (unit != null) unit.setVisibility(View.GONE);
                    if (purchase_restrictions != null) {
                        purchase_restrictions.setVisibility(View.GONE);
                    }
                }
               /* //是否整件批
                if (event.getIsP() == 1) {
                    mainView.findViewById(R.id.tv_isP).setVisibility(View.VISIBLE);
                } else {
                    mainView.findViewById(R.id.tv_isP).setVisibility(View.GONE);
                }*/
                cycleViewUtil.setData(event.getGallery());
                goodsViewHolder.setValues(event);

                //  salesNumber.setText(String.valueOf(event.getTotalSellNum()));
                //规格
                if (goodsInformationSimple.getStatus() == 1)
                    bt_shopping.setVisibility(View.VISIBLE);
                else bt_shopping.setVisibility(View.GONE);
                //  Collections.reverse(goodsSpecificationsList);
                fluidLayout.setData(goodsSpecificationsList, R.layout.item_specification_fluid_layout, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.tv_spec, "text");
                        putViewOnClick(R.id.item_view);
                    }

                    @Override
                    public <T> void setValues(BaseViewHolder viewHolder, T bean) {
                        GoodsSpecification gbean = (GoodsSpecification) bean;
                        String text = "每" + gbean.getLevel1Unit();
                        if (gbean.getLevelType() == 1) {
                            text = String.valueOf(gbean.getPrice()) + "元/" + gbean.getLevel1Unit();
                        } else if (gbean.getLevelType() == 2) {
                            text += "(" + gbean.getLevel2Value() + gbean.getLevel2Unit() + ")";
                        } else {
                            text += "(" + gbean.getLevel2Value() + gbean.getLevel2Unit() + "*" + gbean.getLevel3Value() + gbean.getLevel3Unit() + ")";
                        }
                        gbean.setText(text);

                        super.setValues(viewHolder, gbean);
                    }
                });
                if (ListUtils.isNullOrZeroLenght(event.getEavluationLevelOneNum())){
                    evaluate_count.setText("0");
                }else {
                    evaluate_count.setText(event.getEavluationLevelOneNum());
                }

                evaluate_value.setText(event.getEavluationRatioNum()+"%");
                //请求店铺数据
                getStoreData(event.getStore());

            }

            @Override
            public void onFailed(int goodsInformationResultInfo, String msg) {
//                loadingDimss();
            }
        });
    }


    //请求图文详情
    private void getFindProductDetail(int productId) {
        getRequestPresenter().product_findProductDetail(productId, new RequestCallback<FindProductDetail>(this) {
            @Override
            protected void onSuc(FindProductDetail body) {
                if (body.getData().size() == 0) {
                    tv_no_des.setVisibility(View.VISIBLE);
                } else {
                    goodsUploadDetailAdapter2.setList(body.getData());
                }
            }

            @Override
            public void onFailed(int findProductDetail, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    //请求评分信息
    private void getEvaluateValue(int productId) {
        getRequestPresenter().evaluation_statics(productId, new RequestCallback<EvaluationStatics>(this) {
            @Override
            public void onSuc(EvaluationStatics body) {
                if (body.getData().getAllEvaluateCount() == 0) {
//                    evaluate_count.setText("0");
//                    evaluate_value.setText("暂无评");
                } else {
//                    evaluate_count.setText(body.getData().getAllEvaluateCount() + "");
//                    evaluate_value.setText(body.getData().getAvgScore().toString());
                }

            }

            @Override
            public void onFailed(int evaluationStatics, String msg) {
//                evaluate_count.setText("0");
//                evaluate_value.setText("暂无评");
            }
        });
    }


    //请求评价数据
    private void getEvaluateData(int productId) {
        getRequestPresenter().evaluation_pPageList(-1, -1, productId, 1, 2, new RequestCallback<EvaluationList>(this) {
            @Override
            public void onSuc(EvaluationList body) {
                if (simpleEvaluateAllAdapter == null) return;
                if (body.getData().getTotal() == 0) {
                    if (evaluate_all != null) evaluate_all.setVisibility(View.GONE);
                    simpleEvaluateAllAdapter.clear();
                } else {
                    simpleEvaluateAllAdapter.notifyDataSetChanged(body.getData().getItems());
                }
            }

            @Override
            public void onFailed(int evaluationList, String msg) {
                if (evaluate_all != null) evaluate_all.setVisibility(View.GONE);
            }
        });
    }


    //请求店铺数据
    private void getStoreData(int store) {
        int userType = 0;
        int personId = 0;

        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getFlag() == 1)
                personId = PublicCache.loginPurchase.getEntityId();
            else personId = PublicCache.loginPurchase.getSubUserId();
        } else if (PublicCache.loginSupplier != null) {
            personId = PublicCache.loginSupplier.getEntityId();
            userType = 1;
        }
        getRequestPresenter().getShop_detail(store, userType, personId, new ResultInfoCallback<ShopDetail>(this) {
            @Override
            public void onSuccess(final ShopDetail body) {
                if (body.getStoreType()==1){
                    tv_isShow.setVisibility(View.VISIBLE);
                }else {
                    tv_isShow.setVisibility(View.GONE);
                }

                if (body.getStoreStatus() != 0 || body.getIsActive() == 0) {
                    shop_logo.setEnabled(false);
                    stroll_shop.setEnabled(false);
                }
                baseViewHolder.setValues(body);

                if (floating != null) {
                    if (!TextUtils.isEmpty(body.getQqNumber()))
                        floating.setVisibility(View.VISIBLE);
                    else floating.setVisibility(View.GONE);
                }
                qqNumber = body.getQqNumber();

                BigDecimal storeScore = body.getStoreScore();
                if (evaluate != null)
                    evaluate.setText(String.valueOf(storeScore == null ? "" : storeScore));

            }

            @Override
            public void onFailed(int shopDetailResultInfo, String msg) {

            }
        });
    }


    public static void startActivity(Context context, int productId, View... shareView) {

            Intent intent = new Intent(context, ShopGoodsDetailActivity.class);
            if (shareView.length > 0) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, new Pair<>(shareView[0], UIUtils.getString(R.string.transitional_image)));
                ActivityCompat.startActivity(context, intent, compat.toBundle());
            } else context.startActivity(intent);

//            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        EventBus.getDefault().postSticky(new GoodsDetailEvent(productId));
    }


}
