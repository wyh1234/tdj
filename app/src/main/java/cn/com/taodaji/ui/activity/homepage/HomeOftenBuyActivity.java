package cn.com.taodaji.ui.activity.homepage;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.listener.DrawableRightListener;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.MyRecyclerView;
import com.base.widget.my_edittext.UserNameEditText;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryListNext;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.entity.PickFoodGoodsList;
import cn.com.taodaji.ui.ppw.PickFoodSortPopupWindow;
import cn.com.taodaji.ui.ppw.PopupResultInterface;
import cn.com.taodaji.ui.ppw.SearchToolBarPopupWindow;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.vm.PickFoodViewModel;
import tools.activity.SimpleToolbarActivity;
import tools.animation.ClassifyPopuWindow;

public class HomeOftenBuyActivity extends SimpleToolbarActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnGetDataListener, DrawerLayout.DrawerListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setStatusBarColor(R.color.orange_yellow_ff7d01);

        if (getIntent().getStringExtra("type").equals("1")) {
            setTitle("通菜");
        } else if (getIntent().getStringExtra("type").equals("3")) {
            setTitle("整件批");
        } else if (getIntent().getStringExtra("type").equals("4")) {
            setTitle("精品菜");
        }

    }

    private View mainView;
    public SwipeToLoadLayout swipeToLoadLayout;
    public RecyclerView recycler_targetView;
    public LoadMoreUtil loadMoreUtil;
    public SimpleGoodsInformationAdapter simpleGoodsInformationAdapter;
    private TextView categoryName;
    private View iv_shopping_cart;
    private TextView tv_shopping_count;
    private TextView search_heard_tv;
    private TextView right_text;
    private EditText search_edit;
    private RelativeLayout search_heard;
    private RelativeLayout search_edit_group;
    private LinearLayout sort, filter, ll;
    private SearchToolBarPopupWindow stp;
    public String searchText = "商品";
    public String searchResult = "";
    public String type;
    private String sortString = "{popularity:1}";//默认按销量排序  {qty:1}
    private PickFoodSortPopupWindow pickFoodSortPopupWindow;
    private DrawerLayout drawerLayout;
    private MyRecyclerView drawer_recy;
    private GroupRecyclerAdapter<GoodsCategoryList.ChildrenBean> drawer_right_adapter;
    private static List<GoodsCategoryList> list_second = new ArrayList<>();
    private String goodsName = "";
    private int categoryId = 10;//-1为二级分类所有
    private LinearLayout ll_one,ll_two,ll_zero;
    private ImageView img_open;
    private CheckedTextView ctv_jin,ctv_ordinary;
    private int isCriteria=-1;
    private boolean b=true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }


    @Override
    protected void initMainView() {
        View heard = ViewUtils.getLayoutViewMatch(this, R.layout.home_often_buy_layout);
        body_other.addView(heard);
         LinearLayout canScrolView= heard.findViewById(R.id.canScrolView);
        View shopping_floating_button = findViewById(R.id.shopping_floating_button);

        shopping_floating_button.setVisibility(View.VISIBLE);
        View iv_shopping_cart = findViewById(R.id.iv_shopping_cart);
        tv_shopping_count = findViewById(R.id.tv_shopping_count);

        CartModel cartModel = CartModel.getInstance();
        tv_shopping_count.setText(String.valueOf(cartModel.getCount()));


//        simpleGoodsInformationAdapter.addHeaderView(heard);

        swipeToLoadLayout = heard.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        recycler_targetView = heard.findViewById(R.id.swipe_target);
        loadMoreUtil = new LoadMoreUtil(this, recycler_targetView);


        recycler_targetView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        simpleGoodsInformationAdapter = new SimpleGoodsInformationAdapter();
        simpleGoodsInformationAdapter.setCountImage(tv_shopping_count);

        simpleGoodsInformationAdapter.setmMainLayout(canScrolView);

        simpleGoodsInformationAdapter.setmShoppingCart(iv_shopping_cart);
        recycler_targetView.setAdapter(simpleGoodsInformationAdapter);


        ctv_jin=heard.findViewById(R.id.ctv_jin);
        ctv_ordinary=heard.findViewById(R.id.ctv_ordinary);
        ctv_ordinary.setChecked(true);
        ctv_jin.setChecked(true);
        ctv_ordinary.setOnClickListener(this);
        ctv_jin.setOnClickListener(this);


        img_open= heard.findViewById(R.id.img_open);
        ll_one= heard.findViewById(R.id.ll_one);
        ll_two= heard.findViewById(R.id.ll_two);
        ll_zero= heard.findViewById(R.id.ll_zero);
        ll = heard.findViewById(R.id.ll);
        sort = heard.findViewById(R.id.sort);
        filter = heard.findViewById(R.id.filter);
        drawerLayout = heard.findViewById(R.id.drawer_layout);
        search_heard_tv = heard.findViewById(R.id.search_heard_tv);
        search_heard = heard.findViewById(R.id.search_heard);
        search_edit = heard.findViewById(R.id.search_edit);
        right_text = heard.findViewById(R.id.right_text);

        search_heard.setOnClickListener(this);
        sort.setOnClickListener(this);
        img_open.setOnClickListener(this);
        right_text.setOnClickListener(this);
        filter.setOnClickListener(this);
        drawerLayout.addDrawerListener(this);
        drawer_recy = heard.findViewById(R.id.right_drawer);
        drawer_recy.setLayoutManager(new GridLayoutManager(this, 3));
        drawer_recy.closeDefaultAnimator();

        if (getIntent().getStringExtra("type").equals("1")) {
            ll_one.setVisibility(View.GONE);
            ll_zero.setVisibility(View.VISIBLE);
            img_open.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("type").equals("3")) {
            ll_one.setVisibility(View.VISIBLE);
            img_open.setVisibility(View.VISIBLE);
            ll_zero.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("type").equals("4")) {
            ll_one.setVisibility(View.GONE);
            img_open.setVisibility(View.GONE);
            ll_zero.setVisibility(View.VISIBLE);
        }
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                   goodsName=editable.toString();
                }else {
                    goodsName="";
                }
            }
        });

    }

    @Override
    protected void initData() {
        onStartLoading();
        initRightDrawerView();
        getGoodsCategoryList();
        onRefresh();
    }

    private void initRightDrawerView() {
        if (drawer_right_adapter != null) return;

        View drawer_header = ViewUtils.getLayoutView(this, R.layout.fragment_pickfood_drawer_header);
        categoryName = ViewUtils.findViewById(drawer_header, R.id.categoryName);
        drawer_right_adapter = new GroupRecyclerAdapter<GoodsCategoryList.ChildrenBean>() {
            @Override
            public List getChildList(GoodsCategoryList.ChildrenBean gBean) {
                return gBean.getChildren();
            }

            @Override
            public int concludeItemViewType(Object obj) {
                if (obj == null) return TYPE_CHILD;
                if (obj.getClass() == GoodsCategoryList.ChildrenBean.class) return TYPE_GROUP;
                else return super.concludeItemViewType(obj);
            }

            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_GROUP, new PickFoodViewModel());
                putBaseVM(TYPE_CHILD, new PickFoodViewModel());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {


                switch (viewType) {
                    case TYPE_GROUP:
                        View view = ViewUtils.getFragmentView(parent, R.layout.item_myself_help_center_group);
                        View split_line = ViewUtils.findViewById(view, R.id.split_line);
                        View split_1 = ViewUtils.findViewById(view, R.id.split_1);
                        split_1.setVisibility(View.VISIBLE);
                        View split_2 = ViewUtils.findViewById(view, R.id.split_2);
                        split_2.setVisibility(View.GONE);
                        split_line.setVisibility(View.GONE);
                        return view;
                    case TYPE_CHILD:
                        View viewC = ViewUtils.getFragmentView(parent, R.layout.item_search_recyclerview);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(DensityUtil.dp2px(5), 0, 0, DensityUtil.dp2px(5));
                        viewC.setLayoutParams(layoutParams1);

                        TextView textView = ViewUtils.findViewById(viewC, R.id.image_name);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, DensityUtil.dp2px(15), 0, DensityUtil.dp2px(15));
                        textView.setLayoutParams(layoutParams);
                        textView.setTextColor(UIUtils.getColor(R.color.black_63));
                        return viewC;
                }

                return null;
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                int itemType = concludeItemViewType(bean);
                if (onclickType == 0 && itemType == TYPE_CHILD) {
                    drawerLayout.closeDrawer(drawer_recy);
                    b=false;
                    categoryId = JavaMethod.getFieldValue(bean, "categoryId");
                    goodsName = JavaMethod.getFieldValue(bean, "name");
//                    updateChildFragment();// //更新内容
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        swipeToLoadLayout.setRefreshing(true);
                    }
                    return true;
                } else if (onclickType == 2 && itemType == TYPE_GROUP) {
                    setFoldChanged(position);
                    return true;
                }

                return false;
            }
        };
        drawer_recy.setAdapter(drawer_right_adapter);
        drawer_right_adapter.addHeaderView(drawer_header);
    }

    private void getGoodsCategoryList() {
        getRequestPresenter().findCategoryList(PublicCache.site,PublicCache.refreshId, new RequestCallback<GoodsCategoryList_Resu>(this) {
            @Override
            public void onSuc(GoodsCategoryList_Resu body) {

                //                loadingDimss();
                list_second.clear();
                // gcr = body;
                if (body.getData() == null) return;
                if (!body.getData().isEmpty()) {

                    for (GoodsCategoryList gcl : body.getData()) {

                        for (GoodsCategoryList.ChildrenBean gc : gcl.getChildren()) {
                            GoodsCategoryListNext ggg = new GoodsCategoryListNext();
                            ggg.setName("全部");
                            ggg.setCategoryId(gc.getCategoryId());//二级分类
                            gc.getChildren().add(0, ggg);
                        }

                        list_second.add(gcl);
                    }
                }


            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_heard:
                if (stp == null) {
                    stp = new SearchToolBarPopupWindow(getBaseActivity(), v.getWidth());
                    stp.setResultInterface(new PopupResultInterface() {
                        @Override
                        public void setResult(Object object) {
                            setSearchText(object.toString());
                            search_heard_tv.setText(getSearchText());

                        }
                    });
                }
                if (!stp.isShowing()) stp.showAsDropDown(v);
                break;
            case R.id.right_text:

                String ss = search_edit.getText().toString();
                LogUtils.i(ss);
                    goodsName = ss;
                    if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                        swipeToLoadLayout.setRefreshing(true);
                    }


                break;
            case R.id.sort:
                pickFoodSortPopupWindow = new PickFoodSortPopupWindow(ViewUtils.findViewById(v, R.id.sort_class)) {
                    @Override
                    public void setResult(Object object) {
                        sortString = object.toString();
                        if (swipeToLoadLayout != null && !swipeToLoadLayout.isRefreshing()) {
                            swipeToLoadLayout.setRefreshing(true);
                        }
                    }
                };
                pickFoodSortPopupWindow.showAsDropDown(v, 0, UIUtils.dip2px(1));
                break;
            case R.id.filter:

                drawerLayout.openDrawer(drawer_recy);
                break;
            case R.id.img_open:
                if (ll_two.getVisibility()==View.VISIBLE) {
                    ll_two.setVisibility(View.GONE);
                }else{
                    ll_two.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.ctv_ordinary:
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
                break;

            case R.id.ctv_jin:
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
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData(1);
    }

    @Override
    public void getData(int pn) {


        Map<String, Object> map = new HashMap<>();
        map.put("pn", pn);
        map.put("ps", 6);
        map.put("status", 1);
        if (getIntent().getStringExtra("type").equals("1")) {
            map.put("productCriteria", "1");
            map.put("isP", "0");
        } else if (getIntent().getStringExtra("type").equals("3")) {
              if (isCriteria == -1) {
                  map.put("productCriteria", "");
              }else {
                  map.put("productCriteria",isCriteria+"");
              }

            map.put("isP", "1");
        } else if (getIntent().getStringExtra("type").equals("4")) {
            map.put("productCriteria", "2");
            map.put("isP", "0");
        }
        if (b) {
            map.put("includeSubCategory", 1);
        }


        //排序
        map.put("sort", sortString);
        if ("全部".equals(goodsName)) {
            map.put("categoryId", categoryId);
        } else {

            map.put("categoryId", categoryId);
        }

        if (searchText.equals("商品")) {

            if ("全部".equals(goodsName)) {
                map.put("name", "");

            } else {
                map.put("name", goodsName);
            }
            map.put("storeName", "");

        } else {
            if ("全部".equals(goodsName)) {
                map.put("storeName", "");

            } else {
                map.put("storeName", goodsName);
            }

            map.put("name", "");
        }

//            }

        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        map.put("userType", userType);
        getRequestPresenter().findPageList(map, new ResultInfoCallback<PickFoodGoodsList>() {
            @Override
            public void onSuccess(PickFoodGoodsList body) {
                loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                stop();
            }

            @Override
            public void onFailed(int pickFoodGoodsListResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
                stop();
            }
        });
    }

    public void stop() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        /**
         * 当一个抽屉被完全打开的时候被调用
         */
        categoryName.setText("新鲜蔬菜");
        drawer_right_adapter.setGroupList(list_second.get(0).getChildren());
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
