package cn.com.taodaji.ui.activity.market;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DensityUtil;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.widget.MyRadioGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;
import cn.com.taodaji.model.entity.FavoriteCount;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.entity.ShopDetail;
import cn.com.taodaji.model.entity.ShopDetail_Goods;
import cn.com.taodaji.model.event.FavoriteRefreshEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.SearchNewActivity;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.vm.shop.ShopDetailViewModel;
import tools.activity.SimpleToolbarActivity;
import tools.animation.ClassifyPopuWindow;
import tools.animation.MorePopuPWindow;

public class ShopDetailInformationSeachActivity extends SimpleToolbarActivity implements OnGetDataListener,
        SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,
        ClassifyPopuWindow.ClassifyPopuWindowListener, MorePopuPWindow.MorePopuPWindowListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
//        simple_title.setText("店铺简介");
        simple_title.setVisibility(View.GONE);
        right_textView_new.setVisibility(View.GONE);
        right_textView.setVisibility(View.GONE);
        right_onclick_line.setVisibility(View.GONE);
        rl_fl.setVisibility(View.VISIBLE);
        rl_more.setVisibility(View.VISIBLE);
        search_text.setVisibility(View.VISIBLE);
        search_text.setOnClickListener(this);
        rl_fl.setOnClickListener(this);
        rl_more.setOnClickListener(this);
    }


    private View mainView;
    public SwipeToLoadLayout swipeToLoadLayout;
    public RecyclerView recycler_targetView;
    public LoadMoreUtil loadMoreUtil;
    private SimpleGoodsInformationAdapter simpleGoodsInformationAdapter;
    private TextView count_image;
    private BaseViewHolder baseViewHolder;
    private View shop_message;
    private String qqNumber;
    private int store;
    private ShopDetail shopDetail;
    private TextView evaluate, tv_enshrine_count, tv_total;
    private CheckedTextView ctv_retail,ctv_isP,ctv_jin,ctv_ordinary;
    private MyRadioGroup radioGroup;
    private RadioButton price_jl,price_xl,radio0,radio3;
    private ShopDetail body;
    private ClassifyPopuWindow classifyPopuWindow;
    private MorePopuPWindow morePopuPWindow;
    private RelativeLayout rl;
    private View views;

    private int price_click_coun = 1;//价格排序的点击次数

    private int price_click_couns = 1;//销量排序的点击次数


    private String sort="num";//默认为月销量；

    private int isAsc=1;//默认为月销量；

    private int isP = -1;//0零售，1事件批，-1全部
    private int isCriteria=-1;//商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria
    public String name;
    private String categoryId;//分类id;
    private String goodsName;
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopDetail getBody() {
        return body;
    }


    private ImageView iv_add_attention,price_icon,xl_icon;
    private static List<ClassifyPopuWindowInfo> list_second = new ArrayList<>();

    @Override
    protected void initMainView() {

        setName(getIntent().getStringExtra("data"));
        setStore(Integer.parseInt(getIntent().getStringExtra("id")));

//        ctv_retail.setChecked(true);


        mainView = ViewUtils.getLayoutViewMatch(this, com.base.R.layout.swipe_twitter_swipe_toload_recyclerview);


        swipeToLoadLayout = ViewUtils.findViewById(mainView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        body_other.addView(mainView);


        recycler_targetView = ViewUtils.findViewById(mainView, R.id.swipe_target);
        loadMoreUtil = new LoadMoreUtil(this, recycler_targetView);

        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        View iv_shopping_cart = findViewById(R.id.iv_shopping_cart);
        count_image = findViewById(R.id.tv_shopping_count);

        CartModel cartModel = CartModel.getInstance();
        count_image.setText(String.valueOf(cartModel.getCount()));

        recycler_targetView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        simpleGoodsInformationAdapter = new SimpleGoodsInformationAdapter();
        simpleGoodsInformationAdapter.setCountImage(count_image);

        simpleGoodsInformationAdapter.setmMainLayout((ViewGroup) mainView);

        simpleGoodsInformationAdapter.setmShoppingCart(iv_shopping_cart);
        recycler_targetView.setAdapter(simpleGoodsInformationAdapter);


        View heard = ViewUtils.getFragmentView(recycler_targetView, R.layout.activity_shop_detail_information);
        shop_message = ViewUtils.findViewById(heard, R.id.shop_message);
        simpleGoodsInformationAdapter.addHeaderView(heard);

        evaluate = ViewUtils.findViewById(heard, R.id.shop_evaluate_value);
        evaluate.setTextColor(UIUtils.getColor(R.color.gray_66));
        tv_enshrine_count = heard.findViewById(R.id.tv_enshrine_count);

        tv_total = heard.findViewById(R.id.tv_total);
        iv_add_attention = heard.findViewById(R.id.iv_add_attention);

        //筛选：
        rl=heard.findViewById(R.id.rl);
        views=heard.findViewById(R.id.views);
        rl.setVisibility(View.GONE);
        views.setVisibility(View.GONE);
        radioGroup=heard.findViewById(R.id.radioGroup);

        radio0=heard.findViewById(R.id.radio0);//销量
        radio3=heard.findViewById(R.id.radio3);//销量

        price_jl=heard.findViewById(R.id.radio2);//價格
        price_icon=heard.findViewById(R.id.price_icon);
        price_xl=heard.findViewById(R.id.radio1);//销量

        xl_icon=heard.findViewById(R.id.xl_icon);

        radioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if (checkedId != R.id.radio2 && price_click_coun != 0) {
                    price_icon.setImageResource(R.mipmap.icon_price_unselected);

                    price_click_coun = 0;

                }
                if (checkedId != R.id.radio1 && price_click_couns != 0){
                    xl_icon.setImageResource(R.mipmap.xl_ys);
                    price_click_couns = 0;
                }

                switch (checkedId) {
                    case R.id.radio0:
                        sort="evaluation";
                        isAsc=1;
                        break;
                    case R.id.radio1:
                        sort="num";
                        isAsc = price_click_coun % 2;
                        break;
                    case R.id.radio2:
                        sort="price";
                        isAsc = price_click_coun % 2;
                        break;
                    case R.id.radio3:
                        sort="new";
                        isAsc=1;
                        break;

                }

//                loadMoreUtil.clearAll();
                if (checkedId != R.id.radio2&&checkedId != R.id.radio1) {
//                    onRefresh();
                    swipeToLoadLayout.setRefreshing(true);
                }
            }
        });
        price_jl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                    price_click_coun++;
                    LogUtils.d(price_click_coun);
                    LogUtils.d(price_click_coun % 2);
                    if (price_click_coun % 2 == 1) {
                        price_icon.setImageResource(R.mipmap.icon_price_up);
                        isAsc = 0;
                    } else {
                        isAsc = 1;
                        price_icon.setImageResource(R.mipmap.icon_price_down);
                    }
//                    loadMoreUtil.clearAll();
                    swipeToLoadLayout.setRefreshing(true);
//                onRefresh();
                }
            }
        });

        price_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                    price_click_couns++;
                    if (price_click_couns % 2 == 1) {
                        isAsc = 1;
                        xl_icon.setImageResource(R.mipmap.xl_right);
                    } else {
                        isAsc = 0;
                        xl_icon.setImageResource(R.mipmap.xl_left);
                    }
//                    loadMoreUtil.clearAll();
                    swipeToLoadLayout.setRefreshing(true);
//                onRefresh();
                }
            }
        });


        rl=heard.findViewById(R.id.rl);

        ctv_retail=heard.findViewById(R.id.ctv_retail);
        ctv_isP=heard.findViewById(R.id.ctv_isP);
        ctv_jin=heard.findViewById(R.id.ctv_jin);
        ctv_ordinary=heard.findViewById(R.id.ctv_ordinary);

        ctv_ordinary.setChecked(true);
        ctv_isP.setChecked(true);
        ctv_jin.setChecked(true);
        ctv_retail.setChecked(true);

        ctv_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickCheckedUtil.onClickChecked(1000)) {
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        if (ctv_isP.isChecked() && ctv_retail.isChecked()) {
                            ctv_retail.setChecked(false);
                            isP = 1;
                            swipeToLoadLayout.setRefreshing(true);
                        } else {
                            if (!ctv_retail.isChecked() && ctv_isP.isChecked()) {
                                ctv_retail.setChecked(true);
                                isP = -1;
                                swipeToLoadLayout.setRefreshing(true);
                            }

                        }

//                onRefresh();
                    }
                }
            }
        });

        ctv_isP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickCheckedUtil.onClickChecked(1000)) {
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        if (ctv_isP.isChecked() && ctv_retail.isChecked()) {
                            ctv_isP.setChecked(false);
                            isP = 0;
                            swipeToLoadLayout.setRefreshing(true);
                        } else {
                            if (!ctv_isP.isChecked() && ctv_retail.isChecked()) {
                                ctv_isP.setChecked(true);
                                isP = -1;
                                swipeToLoadLayout.setRefreshing(true);
                            }

                        }

//                onRefresh();
                    }
                }
            }
        });

        ctv_jin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickCheckedUtil.onClickChecked(1000)) {
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        if (ctv_ordinary.isChecked() && ctv_jin.isChecked()) {
                            ctv_jin.setChecked(false);
                            isCriteria = 1;
                            swipeToLoadLayout.setRefreshing(true);
                        } else {
                            if (!ctv_jin.isChecked() && ctv_ordinary.isChecked()) {
                                ctv_jin.setChecked(true);
                                isCriteria = -1;
                                swipeToLoadLayout.setRefreshing(true);
                            }

                        }

//                onRefresh();
                    }
                }
            }
        });
        ctv_ordinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickCheckedUtil.onClickChecked(1000)) {
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        if (ctv_ordinary.isChecked() && ctv_jin.isChecked()) {
                            ctv_ordinary.setChecked(false);
                            isCriteria = 2;
                            swipeToLoadLayout.setRefreshing(true);
                        } else {
                            if (!ctv_ordinary.isChecked() && ctv_jin.isChecked()) {
                                ctv_ordinary.setChecked(true);
                                isCriteria = -1;
                                swipeToLoadLayout.setRefreshing(true);
                            }

                        }

//                onRefresh();
                    }
                }
            }
        });




        iv_add_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userType = 0;
                int personId = -1;
                if (PublicCache.loginPurchase != null) {
                    userType = 0;
                    personId = PublicCache.loginPurchase.getEntityId();
                } else if (PublicCache.loginSupplier != null) {
                    userType = 1;
                    personId = PublicCache.loginSupplier.getEntityId();
                } else LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();

                if (store == -1 || personId == -1) {
                    return;
                }

                boolean isFavorite = v.isSelected();

                if (isFavorite) {
                    //取消关注
                    loading();
                    ModelRequest.getInstance().favorite_delFavorite(userType, store, personId, 1, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {
                        @Override
                        public void onSuccess(FavoriteCount body) {
                            v.setSelected(false);
                            baseViewHolder.setText(R.id.tv_enshrine_count, body.getFavoriteCount());
                            EventBus.getDefault().post(new FavoriteRefreshEvent(1));
                            UIUtils.showToastSafesShort("取消关注成功");
                            loadingDimss();
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            super.onFailed(errCode, msg);
                            UIUtils.showToastSafesShort(msg);
                            loadingDimss();
                        }
                    });
                } else {
                    //关注
                    loading();
                    ModelRequest.getInstance().favorite_addFavorite(userType, store, personId, 1, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {
                        @Override
                        public void onSuccess(FavoriteCount body) {
                            baseViewHolder.setText(R.id.tv_enshrine_count, body.getFavoriteCount());
                            v.setSelected(true);
                            EventBus.getDefault().post(new FavoriteRefreshEvent(1));
                            UIUtils.showToastSafesShort("关注成功");
                            loadingDimss();
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            super.onFailed(errCode, msg);
                            UIUtils.showToastSafesShort(msg);
                            loadingDimss();
                        }
                    });

                }


            }
        });
        //视图模型初始化
        baseViewHolder = new BaseViewHolder(heard, new ShopDetailViewModel(), null);


        floating.setVisibility(View.GONE);
        floating.setMarginBottom(DensityUtil.dp2px(60));
        floating.setImageResource(R.mipmap.ic_qq);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qqNumber = getQqNumber();
                if (SystemUtils.isQQClientAvailable(getBaseActivity())) {
                    if (!TextUtils.isEmpty(qqNumber)) SystemUtils.callQQ(qqNumber);
                } else UIUtils.showToastSafesShort("手机未安装QQ");

            }
        });
        getdata();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (floating.getVisibility() == View.VISIBLE) floating.clearFocus();
    }

//    @Subscribe(sticky = true)
    public void getdata() {

        onRefresh();
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
            public void onSuccess(ShopDetail body) {
                setShopDetail(body);
                LogUtils.d(body);

                if (floating != null) {
                    if (!TextUtils.isEmpty(body.getQqNumber())) {
                        floating.setVisibility(View.VISIBLE);


                    } else floating.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(int shopDetailResultInfo, String msg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {
        onStartLoading();
     /*   if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }*/
     if (list_second.size()>0){
         list_second.clear();
     }

        getGoodsCategoryList();
    }

    private void getGoodsCategoryList() {
        getRequestPresenter().newfindCategoryList(PublicCache.site,store, new RequestCallback<GoodsCategoryList_Resu>(this) {
            @Override
            public void onSuc(GoodsCategoryList_Resu body) {

                if (body.getData() == null) return;

                for (GoodsCategoryList gcl : body.getData()) {
                    list_second.add(new ClassifyPopuWindowInfo(gcl.getCategoryName(),gcl.getCategoryId()+"",1));
                    for (GoodsCategoryList.ChildrenBean gc : gcl.getChildren()) {
                        list_second.add(new ClassifyPopuWindowInfo(gc.getCategoryName(),gc.getCategoryId()+"",2));
                    }

                }
                list_second.add(0,new ClassifyPopuWindowInfo("全部商品","",1));



            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
            }
        });
    }


    public void setShopDetail(ShopDetail body) {
        this.body = body;
//        swipeToLoadLayout.setRefreshing(true);

        shopDetail = body;
        baseViewHolder.setValues(shopDetail);
        if (iv_add_attention != null) iv_add_attention.setSelected(body.isFavor());
        String realname;
        if ((realname = shopDetail.getRealName()) != null && !realname.equals(""))
            baseViewHolder.setText(R.id.realname, realname.substring(0, 1) + "**");
        String phone = shopDetail.getContactPhone();
        if (phone.length() > 0)
            baseViewHolder.setText(R.id.phone_no, phone.substring(0, 4) + "****" + phone.substring(8));
        evaluate.setText(String.valueOf(body.getStoreScore()));

        setQqNumber(body.getQqNumber());

    }


    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    @Override
    public void getData(int pn) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        if (!UIUtils.isNullOrZeroLenght(getCategoryId())){
                map.put("categoryId", getCategoryId());
        }else {
            if (!UIUtils.isNullOrZeroLenght(getGoodsName())&&getGoodsName().equals("全部商品")){
                sort="num";//默认为月销量；
                isAsc=1;//默认为月销量；
                isP = -1;//0零售，1事件批，-1全部
                isCriteria=-1;//商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria
                price_click_coun = 1;//价格排序的点击次数
                price_click_couns = 1;//销量排序的点击次数
                radio0.setChecked(false);
                price_xl.setChecked(true);
                price_jl.setChecked(false);
                radio3.setChecked(false);
                xl_icon.setImageResource(R.mipmap.xl_right);
                price_icon.setImageResource(R.mipmap.icon_price_unselected);
                ctv_ordinary.setChecked(true);
                ctv_isP.setChecked(true);
                ctv_jin.setChecked(true);
                ctv_retail.setChecked(true);
                setCategoryId(null);
            }

        }
        map.put("productName", UIUtils.isNullOrZeroLenght(getName())?"":getName());
        map.put("isP", isP);
        String productCriteria;
        if (isCriteria == -1) {
            productCriteria="";
            map.put("productCriteria", "");
        }else{
            productCriteria=isCriteria+"";
            map.put("productCriteria",productCriteria);
        }



        //排序
        map.put("sort", sort);
        map.put("isAsc", isAsc);
        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        map.put("userType", userType);
        LogUtils.d(map);

        getRequestPresenter().getnewShop_goods_detail(map,store, pn, 10, new ResultInfoCallback<ShopDetail_Goods>() {
            @Override
            public void onSuccess(ShopDetail_Goods body) {
                if (tv_total != null) tv_total.setText(String.valueOf(body.getTotal()));
                loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                stop();
            }

            @Override
            public void onFailed(int shopDetail_goodsResultInfo, String msg) {
                stop();
            }
        });
    }

    public void stop() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    public void setStore(int store) {
        this.store = store;
    }


    @Override
    public void onRefresh() {
        getData(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_more:
                if (classifyPopuWindow!=null){
                    if (classifyPopuWindow.isShowing()) {
                        classifyPopuWindow.dismiss();
                    }
                }
                if (morePopuPWindow != null) {
                    if (morePopuPWindow.isShowing()) {
                        morePopuPWindow.dismiss();
                    } else {
                        morePopuPWindow = new MorePopuPWindow(this);
                        morePopuPWindow.setDismissWhenTouchOutside(false);
                        morePopuPWindow.setInterceptTouchEvent(false);
                        morePopuPWindow.showPopupWindow();
                        morePopuPWindow.setMorePopuPWindow(this);
                    }
                } else {
                    morePopuPWindow = new MorePopuPWindow(this);
                    morePopuPWindow.setDismissWhenTouchOutside(false);
                    morePopuPWindow.setInterceptTouchEvent(false);
                    morePopuPWindow.showPopupWindow();
                    morePopuPWindow.setMorePopuPWindow(this);
                }

                break;
            case R.id.rl_fl:
                if (morePopuPWindow!=null){
                    if (morePopuPWindow.isShowing()) {
                        morePopuPWindow.dismiss();
                    }
                }
                if (classifyPopuWindow != null) {
                    if (classifyPopuWindow.isShowing()) {
                        classifyPopuWindow.dismiss();
                    } else {
                        classifyPopuWindow = new ClassifyPopuWindow(this,getBody(),list_second);
                        classifyPopuWindow.setDismissWhenTouchOutside(false);
                        classifyPopuWindow.setInterceptTouchEvent(false);
                        classifyPopuWindow.showPopupWindow(toolbar);
                        classifyPopuWindow.setClassifyPopuWindowListener(this);
                    }
                } else {
                    classifyPopuWindow = new ClassifyPopuWindow(this,getBody(),list_second);
                    classifyPopuWindow.setDismissWhenTouchOutside(false);
                    classifyPopuWindow.setInterceptTouchEvent(false);
                    classifyPopuWindow.showPopupWindow(toolbar);
                    classifyPopuWindow.setClassifyPopuWindowListener(this);
                }

                break;
            case R.id.search_text:
                Intent intent = new Intent(this, SearchNewActivity.class);
                intent.putExtra("searchhost", getIntent().getSerializableExtra("searchhost"));
                intent.putExtra("id",store+"");
                startActivity(intent);
                finish();
                break;
        }


    }

    @Override
    public void add_attention(ImageView v,TextView tv_enshrine_count) {

        int userType = 0;
        int personId = -1;
        if (PublicCache.loginPurchase != null) {
            userType = 0;
            personId = PublicCache.loginPurchase.getEntityId();
        } else if (PublicCache.loginSupplier != null) {
            userType = 1;
            personId = PublicCache.loginSupplier.getEntityId();
        } else LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();

        if (store == -1 || personId == -1) {
            return;
        }

        boolean isFavorite = v.isSelected();

        if (isFavorite) {
            //取消关注
            loading();
            ModelRequest.getInstance().favorite_delFavorite(userType, store, personId, 1, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {
                @Override
                public void onSuccess(FavoriteCount body) {
                    v.setSelected(false);
                    baseViewHolder.setText(R.id.tv_enshrine_count, body.getFavoriteCount());
                    EventBus.getDefault().post(new FavoriteRefreshEvent(1));
                    UIUtils.showToastSafesShort("取消关注成功");
                    int cont=Integer.parseInt(tv_enshrine_count.getText().toString());
                    if (cont>0){
                        tv_enshrine_count.setText((cont--)+"");
                    }
                    loadingDimss();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    UIUtils.showToastSafesShort(msg);
                    loadingDimss();
                }
            });
        } else {
            //关注
            loading();
            ModelRequest.getInstance().favorite_addFavorite(userType, store, personId, 1, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {
                @Override
                public void onSuccess(FavoriteCount body) {
                    baseViewHolder.setText(R.id.tv_enshrine_count, body.getFavoriteCount());
                    v.setSelected(true);
                    EventBus.getDefault().post(new FavoriteRefreshEvent(1));
                    UIUtils.showToastSafesShort("关注成功");
                    int cont=Integer.parseInt(tv_enshrine_count.getText().toString());
                    tv_enshrine_count.setText((cont--)+"");
                    loadingDimss();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    UIUtils.showToastSafesShort(msg);
                    loadingDimss();
                }
            });

        }


    }

    @Override
    public void onItemOneClickListener(View view, int position) {
        //一级分类
        setCategoryId(list_second.get(position).getCategoryId());
        setGoodsName(list_second.get(position).getCategoryName());
        if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(true);
        }
        classifyPopuWindow.dismiss();
    }

    @Override
    public void button_1() {
        String qqNumber = getQqNumber();
        if (SystemUtils.isQQClientAvailable(getBaseActivity())) {
            if (!TextUtils.isEmpty(qqNumber)) SystemUtils.callQQ(qqNumber);
        } else UIUtils.showToastSafesShort("手机未安装QQ");
        morePopuPWindow.dismiss();
    }

    @Override
    public void button_2() {
        Intent intent =new Intent(this, ShopDetailActivity.class);
        intent.putExtra("body",getBody());
        startActivity(intent);
        morePopuPWindow.dismiss();
    }

    @Override
    public void button_3() {
        Intent intent =new Intent(this, ManageActivity.class);
        startActivity(intent);
        morePopuPWindow.dismiss();

    }
}
