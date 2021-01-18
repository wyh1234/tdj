package cn.com.taodaji.ui.activity.homepage;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.ui.activity.myself.GoodsNeedActivity;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;

import com.base.utils.UIUtils;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;

import cn.com.taodaji.model.entity.SearchGoods3;
import cn.com.taodaji.model.CartModel;

import com.base.retrofit.ResultInfoCallback;

import tools.activity.MenuToolbarActivity;

import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DensityUtil;
import com.base.widget.MyRadioGroup;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;

public class SearchGoodsActivity extends SearchBaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnGetDataListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        super.titleSetting(toolbar);
        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_edit.setBackgroundResource(R.drawable.z_round_rect_gray_6a);
        search_edit.removeTextChangedListener(search_edit);
        search_edit.removeDrawableRightListener(search_edit);
        search_edit.setCompoundDrawables(null, null, null, null);
        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput(search_edit.getWindowToken());
                finish();
                //   String ss = search_edit.getText().toString();
                //     if (ss.length() == 0) return;
                //     onClicked(ss);
            }
        });
    }


    private int price_click_coun = 0;//价格排序的点击次数
    private int isASC = 0;//默认升序
    private String sort = "complex";//排序关键字 默认价格

    private int isP = -1;//0零售，1事件批，-1全部
    private int isCriteria=-1;//商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria

    private LinearLayout goods_view;//商品的view
    private RecyclerView search_recyclerView;//搜索结果的列表
    private SimpleGoodsInformationAdapter goodsView_adapter;
    //   private TextView cart_price;
    //  private TextView count_image;
    private CartModel cartModel;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ImageView price_icon;//价格排序的图标
    private LoadMoreUtil loadMoreUtils;
    private TextView tv_shopping_count, count_image;

    private LinearLayout line_tips_search_goods;

    private  ImageView img_open;
    private TextView tv_reloading;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_search_goods);
        body_other.addView(view);

        line_tips_search_goods = ViewUtils.findViewById(view, R.id.line_tips_search_goods);
        tv_reloading= ViewUtils.findViewById(view, R.id.tv_reloading);
        goods_view = ViewUtils.findViewById(view, R.id.goods_view);
        tv_reloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(SearchGoodsActivity.this).toChooseLoginActivity();
                    return;
                }
                Intent   intent = new Intent(getBaseActivity(), GoodsNeedActivity.class);
                if (!TextUtils.isEmpty(name)) {
                    intent.putExtra("name",name);
                }
                startActivity(intent);
            }
        });
        search_recyclerView = ViewUtils.findViewById(view, R.id.search_recyclerView);
        search_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        //搜索结果显示的view\
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpacesItemDecoration sp = new SpacesItemDecoration(15);
        search_recyclerView.addItemDecoration(sp);
        search_recyclerView.setAdapter(simpleSearchResultAdapter);

        simpleSearchResultAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    Object obj = JavaMethod.getValueFromBean(bean, "name");
                    if (obj != null) {
                        search_edit.removeTextChangedListener(SearchGoodsActivity.this);
                        search_edit.setText(obj.toString());
                        search_edit.setSelection(obj.toString().length());
                        search_edit.addTextChangedListener(SearchGoodsActivity.this);
                        onClicked(obj.toString());
                    }
                    return true;
                }
                return false;
            }
        });

        //商品显示的view
        swipeToLoadLayout = ViewUtils.findViewById(goods_view, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        RecyclerView goods_recycle = ViewUtils.findViewById(swipeToLoadLayout, R.id.swipe_target);
//        swipeToLoadLayout.setPadding(0, 0, 0, DensityUtil.dp2px(55));
        loadMoreUtils = new LoadMoreUtil(this, goods_recycle);
        goods_recycle.setLayoutManager(new LinearLayoutManager(this));
        goodsView_adapter = new SimpleGoodsInformationAdapter();
        goods_recycle.setAdapter(goodsView_adapter);


        //底部购物车
        cartModel = CartModel.getInstance();

        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        shopping_floating_button.setVisibility(View.VISIBLE);
        View mShoppingCart = findViewById(R.id.iv_shopping_cart);
        count_image = findViewById(R.id.tv_shopping_count);

        goodsView_adapter.setmMainLayout(mainLayout);
        goodsView_adapter.setmShoppingCart(mShoppingCart);
        count_image.setText(String.valueOf(cartModel.getCount()));
        goodsView_adapter.setCountImage(count_image);


        MyRadioGroup radioGroup = ViewUtils.findViewById(goods_view, R.id.radioGroup);
        price_icon = ViewUtils.findViewById(radioGroup, R.id.price_icon);
        RadioButton price_sort = ViewUtils.findViewById(goods_view, R.id.radio2);
        radioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if (checkedId != R.id.radio2 && price_click_coun != 0) {
                    price_icon.setImageResource(R.mipmap.icon_price_unselected);
                    price_click_coun = 0;
                }

                switch (checkedId) {
                    case R.id.radio0:
                        isASC = 1;
                        sort = "complex";
                        break;
                    case R.id.radio1:
                        isASC = 1;
                        sort = "qty";
                        break;
                    case R.id.radio2:
                        isASC = price_click_coun % 2;
                        sort = "price";
                        break;
                    case R.id.radio3:
                        isASC = 1;
                        sort = "createTime";
                        break;

                }

                loadMoreUtils.clearAll();
                if (checkedId != R.id.radio2) {
//                    onRefresh();
                    swipeToLoadLayout.setRefreshing(true);
                }

            }
        });
        price_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                    price_click_coun++;
                    if (price_click_coun % 2 == 1) {
                        price_icon.setImageResource(R.mipmap.icon_price_up);
                        isASC = 0;
                    } else {
                        price_icon.setImageResource(R.mipmap.icon_price_down);
                        isASC = 1;
                    }
                    loadMoreUtils.clearAll();
                    swipeToLoadLayout.setRefreshing(true);
//                onRefresh();
                }
            }
        });
        CheckedTextView ctv_retail = goods_view.findViewById(R.id.ctv_retail);
        CheckedTextView ctv_isP = goods_view.findViewById(R.id.ctv_isP);


        ctv_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        CheckedTextView ctv_jin = goods_view.findViewById(R.id.ctv_jin);
        CheckedTextView ctv_ordinary = goods_view.findViewById(R.id.ctv_ordinary);

       //商品标准“1”：通货商品 “2”：精品商品 '，-1全部
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

        switch (isCriteria) {
            case -1:
                if (ctv_jin != null) ctv_jin.setChecked(true);
                if (ctv_ordinary != null) ctv_ordinary.setChecked(true);
                break;
            case 1:
                if (ctv_jin != null) ctv_jin.setChecked(false);
                if (ctv_ordinary != null) ctv_ordinary.setChecked(true);
                break;
            case 2:
                if (ctv_jin != null) ctv_jin.setChecked(true);
                if (ctv_ordinary != null) ctv_ordinary.setChecked(false);
                break;
        }

        switch (isP ) {
            case -1:
                if (ctv_retail != null) ctv_retail.setChecked(true);
                if (ctv_isP != null) ctv_isP.setChecked(true);
                break;
            case 0:
                if (ctv_retail != null) ctv_retail.setChecked(true);
                if (ctv_isP != null) ctv_isP.setChecked(false);
                break;
            case 1:
                if (ctv_retail != null) ctv_retail.setChecked(false);
                if (ctv_isP != null) ctv_isP.setChecked(true);
                break;
        }
    }

    private void onClicked(String str) {
        if (TextUtils.isEmpty(str)) return;
        goods_view.setVisibility(View.VISIBLE);
        search_recyclerView.setVisibility(View.GONE);
        simpleSearchResultAdapter.clear();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    protected void initData() {
        setSearchText("商品");
        String ss = getIntent().getStringExtra("data");
        if (ss == null) ss = "";
        search_edit.removeTextChangedListener(this);
        search_edit.setText(ss);
        search_edit.setSelection(ss.length());
        search_edit.addTextChangedListener(this);
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String ss = editable.toString();
        if (ss.length() == 0) {
            goods_view.setVisibility(View.VISIBLE);
            search_recyclerView.setVisibility(View.GONE);
            simpleSearchResultAdapter.clear();
        } else {
            goods_view.setVisibility(View.GONE);
            search_recyclerView.setVisibility(View.VISIBLE);
            getSearchResult(ss,getIntent().getIntExtra("isCanteen",0));
        }
    }

    @Override
    public void onRefresh() {
        // goodsView_adapter.clear();
        loading();
        getData(1);
    }


    //获取，商品的数据
    private void getGoodsviewData(String name, String sort, int isAsc, int isP,String  productCriteria, int pn) {
        if (TextUtils.isEmpty(name)) {
            loadingDimss();
            stop();
            return;
        }
        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        getRequestPresenter().getSearchGood(userType, name, sort, isAsc, isP,productCriteria, pn, 9,getIntent().getIntExtra("isCanteen",0) ,new ResultInfoCallback<SearchGoods3>() {
            @Override
            public void onFailed(int errCode, String msg) {
                stop();
                UIUtils.showToastSafe(msg);
                search_edit.postDelayed(() -> loadingDimss(), 800);
            }

            @Override
            public void onSuccess(SearchGoods3 body) {
                loadMoreUtils.setData(body.getItems(), body.getPn(), body.getPs());
                stop();
                search_edit.postDelayed(() -> loadingDimss(), 800);

                if (body.getTotal() == 0) {
                    line_tips_search_goods.setVisibility(View.VISIBLE);
                } else {
                    line_tips_search_goods.setVisibility(View.GONE);
                }
            }
        });

    }

    public void stop() {
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        } else if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    @Override
    public void getData(int pn) {
        String ss = search_edit.getText().toString().trim();
       // int isCRITERIA=PublicCache.isCRITERIA_DEFAULT;
        String productCriteria;
        if (isCriteria == -1) {
            productCriteria="";
        }else{
            productCriteria=isCriteria+"";
        }
        setName(ss);
        getGoodsviewData(ss, sort, isASC,isP,productCriteria , pn);
    }
}
