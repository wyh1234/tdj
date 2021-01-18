package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryListNext;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;

import cn.com.taodaji.model.CartModel;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.MyRecyclerView;
import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.activity.homepage.SearchNewActivity;
import cn.com.taodaji.ui.ppw.PickFoodSortPopupWindow;
import cn.com.taodaji.viewModel.vm.PickFoodViewModel;
import tools.activity.MenuToolbarActivity;
import tools.extend.PhoneUtils;
import tools.fragment.DataBaseFragment;


import com.base.listener.PickFoodListener;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class PickFoodFragment extends DataBaseFragment implements View.OnClickListener, PickFoodListener, ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {

    private ViewPager mViewPager;
    private DrawerLayout drawerLayout;
    private MyRecyclerView drawer_recy;
    private TextView categoryName;

    private TextView countImageTv;
    private ImageView imageViewCart;


    private GroupRecyclerAdapter<GoodsCategoryList.ChildrenBean> drawer_right_adapter;


    private static List<GoodsCategoryList> list_second = new ArrayList<>();
    private List<PickFoodListFragment> fragments = new ArrayList<>();
    // private List<Integer> listId = new ArrayList<>();//分类id集合
    private String sortString = "{popularity:1}";//默认按销量排序  {qty:1}
    private String goodsName = "全部";
    private int categoryId = 0;//-1为二级分类所有
    // private GoodsCategoryList_Resu gcr;
    private boolean isCurrent = false;//是否翻页
    private int currentItem = 0;
    private boolean isNeedUpdate = false;

    private View title_view;
    private LinearLayout canScrolView;
    View shopping_floating_button;
    private ManageActivityPagerAdapter mAdapter;

    private int isP = -1;//0零售，1事件批，-1全部
    private int isCriteria=-1;//商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria

    private CheckedTextView ctv_isP, ctv_retail;

    private CheckedTextView ctv_jin, ctv_ordinary;
    private ImageView img_open;
    private LinearLayout  line_type;

    @Override
    protected View initToolbar() {
        View title = ViewUtils.getLayoutView(getContext(), R.layout.toolbar_customer_simple_title);
        Toolbar toolbar = ViewUtils.findViewById(title, R.id.toolbar);
        toolbar.setBackgroundColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
        TextView search_text = ViewUtils.findViewById(title, R.id.search_text);
        search_text.setVisibility(View.VISIBLE);
        search_text.setOnClickListener(this);
        ImageView right_icon = ViewUtils.findViewById(title, R.id.right_icon);
        right_icon.setImageResource(R.mipmap.ic_customer_service_white);
        UIUtils.setViewSize(right_icon, DensityUtil.dp2px(30), DensityUtil.dp2px(30));
        right_icon.setOnClickListener(this);
        return title;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickfood, container, false);
        title_view = ViewUtils.findViewById(view, R.id.item_view);

        canScrolView = ViewUtils.findViewById(view, R.id.canScrolView);

        countImageTv = ViewUtils.findViewById(view, R.id.tv_shopping_count);
        imageViewCart = ViewUtils.findViewById(view, R.id.iv_shopping_cart);
        shopping_floating_button = ViewUtils.findViewById(view, R.id.shopping_floating_button);
        imageViewCart.setOnClickListener(this);

        TabLayout tabLayout = ViewUtils.findViewById(view, R.id.tabLayout);
        mViewPager = ViewUtils.findViewById(view, R.id.tabLayout_viewpager);
        mViewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(mViewPager);

        mAdapter = new ManageActivityPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);


        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));

        tabLayout.setSelectedTabIndicatorHeight(5);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        LinearLayout tabLayout_header = ViewUtils.findViewById(view, R.id.tabLayout_header);
        View sort_view = ViewUtils.getLayoutView(getContext(), R.layout.fragment_pickfood_sort_header);
        tabLayout_header.addView(sort_view);

        ctv_isP = sort_view.findViewById(R.id.ctv_isP);
        ctv_retail = sort_view.findViewById(R.id.ctv_retail);

        ctv_jin = sort_view.findViewById(R.id.ctv_jin);
        ctv_ordinary = sort_view.findViewById(R.id.ctv_ordinary);

        img_open = sort_view.findViewById(R.id.img_open);

        line_type = sort_view.findViewById(R.id.line_type);

        ctv_isP.setOnClickListener(this);
        ctv_retail.setOnClickListener(this);
        ctv_jin.setOnClickListener(this);
        ctv_ordinary.setOnClickListener(this);
        img_open.setOnClickListener(this);



        ViewUtils.findViewById(view, R.id.sort).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.bargain_price).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.filter).setOnClickListener(this);


        drawerLayout = ViewUtils.findViewById(view, R.id.drawer_layout);
        drawerLayout.addDrawerListener(this);
        drawer_recy = ViewUtils.findViewById(drawerLayout, R.id.right_drawer);
        drawer_recy.setLayoutManager(new GridLayoutManager(getContext(), 3));
        drawer_recy.closeDefaultAnimator();
        return view;
    }


    //接收商品数量变化，改变悬浮按扭的数值
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        countImageTv.setText(String.valueOf(CartModel.getInstance().getCount()));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    //首次进入   先 setCurrentItem  执行
    @Override
    public void initData() {
        super.initData();

/*        FloatingDragViewUtils floatingDragViewUtils = new FloatingDragViewUtils(shopping_floating_button);
        floatingDragViewUtils.init();*/
        CartModel cartModel = CartModel.getInstance();
        countImageTv.setText(String.valueOf(cartModel.getCount()));
        initRightDrawerView();
        isNeedUpdate = true;
        getGoodsCategoryList();
        onPauseRevert();
    }

    //翻到某一页
    public void setCurrentItem(int categoryId, boolean smoothScroll) {
        if (mViewPager == null) return;
        if (isNeedUpdate) {
            currentItem = categoryId;
            isCurrent = true;
        } else {
            mViewPager.setCurrentItem(getIndex(categoryId), smoothScroll);
        }

    }

    public void set_ctv_isP_Checked() {
        isP = 1;
        if (ctv_retail != null) ctv_retail.setChecked(false);
        if (ctv_isP != null) ctv_isP.setChecked(true);

        if (!isNeedUpdate) {
            if (fragments.size() > 0) {
                List<Fragment> fragts = getFirstVisibleFragments();
                if (fragts.size() > 0) {
                    if (fragts.get(0) instanceof PickFoodListFragment) {
                        PickFoodListFragment fn = (PickFoodListFragment) fragts.get(0);
                        fn.update();
                    }
                }
            }
        }
    }

    //除了第一次的加载的显示   先 setCurrentItem  执行
    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (fragments.size() == 0) getGoodsCategoryList();
        else {
            if (isNeedUpdate) getGoodsCategoryList();
        }

        if (countImageTv != null)
            countImageTv.setText(String.valueOf(CartModel.getInstance().getCount()));


        onPauseRevert();
    }

    @Override
    public void onPauseRevert() {

        switch (isP) {
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
        List<Fragment> list = getFirstVisibleFragments();
        for (Fragment fragment : list) {
            if (fragment.isVisible()) {
                if (fragment instanceof PickFoodListFragment) {
                    PickFoodListFragment pickFoodListFragment = (PickFoodListFragment) fragment;
                    pickFoodListFragment.onPauseRevert();
                }
            }
        }

    }

    private void initRightDrawerView() {

        if (drawer_right_adapter != null) return;

        View drawer_header = ViewUtils.getLayoutView(getContext(), R.layout.fragment_pickfood_drawer_header);
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
                    categoryId = JavaMethod.getFieldValue(bean, "categoryId");
                    goodsName = JavaMethod.getFieldValue(bean, "name");
                    updateChildFragment();
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

    private void initDrawerData(int index) {
        if (fragments != null) {
            if (index >= fragments.size()) return;
            categoryName.setText(fragments.get(index).getTitle());
            drawer_right_adapter.setGroupList(list_second.get(index).getChildren());
        }
    }

    public int getIndex(int categoryId) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getCategoryId() == categoryId) return i;
        }
        //如果不存在则到首页
        return 0;
    }

    private void initFragments(List<GoodsCategoryList> body) {
        if (body.isEmpty()) {
            fragments.clear();
            return;
        }
        int coun = fragments.size();

        //如果之前的比现在的数量多，则移除
        if (coun > body.size()) {
            for (int i = coun - 1; i > body.size() - 1; i--) {
                fragments.remove(i);
            }
        }
        //如果之前的比现在的数量少，则添加
        else if (coun < body.size()) {
            for (int i = coun; i < body.size(); i++) {
                fragments.add(new PickFoodListFragment());
            }
        }

        //数量相等后更新默认数据
        for (int i = 0; i < body.size(); i++) {
            fragments.get(i).setTitle(body.get(i).getCategoryName());
            fragments.get(i).setCategoryId(body.get(i).getCategoryId());//一级分类
            fragments.get(i).setmShoppingCart(imageViewCart);
        }
    }

    //更新内容
    private void updateChildFragment() {
        List<Fragment> fragments = getFirstVisibleFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof PickFoodListFragment) {
                PickFoodListFragment fn = (PickFoodListFragment) fragment;
                if (fn.isAdded()) {
                    fn.update();
                }
            }
        }
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        if (drawer_recy != null && drawer_recy.isAttachedToWindow()) {
            drawerLayout.closeDrawer(drawer_recy);
        }
    }

    private void getGoodsCategoryList() {
        onStartLoading();
        loadingClear();
//        loadingShow();
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

                    initFragments(body.getData());
                    mAdapter.setFragments(fragments);
                    int pos = mViewPager.getCurrentItem();

                    if (isCurrent) {
                        isCurrent = false;
                        int toIndex = getIndex(currentItem);
                        if (toIndex == pos) updateChildFragment();
                        else mViewPager.setCurrentItem(toIndex);
                    } else {
                        if (pos > 0) mViewPager.setCurrentItem(0);
//                        if (pos == 0) updateChildFragment();
                    }
                    isNeedUpdate = false;


                }
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
//                loadingDimss();
                UIUtils.showToastSafe(msg);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //排序
            case R.id.sort:
                PickFoodSortPopupWindow pickFoodSortPopupWindow = new PickFoodSortPopupWindow(ViewUtils.findViewById(view, R.id.sort_class)) {
                    @Override
                    public void setResult(Object object) {
                        sortString = object.toString();
                        updateChildFragment();
                    }
                };
                pickFoodSortPopupWindow.showAsDropDown(view, 0, UIUtils.dip2px(1));
                break;
            //只看特价
            case R.id.bargain_price:

                break;
            //筛选
            case R.id.filter:
                drawerLayout.openDrawer(drawer_recy);
                break;
            case R.id.right_icon:
                /** 拨打电话*/
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
            case R.id.search_text:
                Intent intent = new Intent(getContext(), SearchNewActivity.class);
                startActivity(intent);
                break;
            //跳转购物车
            case R.id.iv_shopping_cart:
                MenuToolbarActivity.goToPage(3);
                break;
            case R.id.ctv_isP: {
                List<Fragment> fragments = getFirstVisibleFragments();
                if (fragments.size() > 0) {
                    if (fragments.get(0) instanceof PickFoodListFragment) {
                        PickFoodListFragment fn = (PickFoodListFragment) fragments.get(0);
                        if (fn.isAdded() && fn.isStatusDefault()) {
                            if (ctv_isP.isChecked() && ctv_retail.isChecked()) {
                                ctv_isP.setChecked(false);
                                isP = 0;
                                fn.update();
                            } else {
                                if (!ctv_isP.isChecked() && ctv_retail.isChecked()) {
                                    ctv_isP.setChecked(true);
                                    isP = -1;
                                    fn.update();
                                }
                            }

                        }
                    }
                }
                break;
            }
            case R.id.ctv_retail: {
                List<Fragment> fragments = getFirstVisibleFragments();
                if (fragments.size() > 0) {
                    if (fragments.get(0) instanceof PickFoodListFragment) {
                        PickFoodListFragment fn = (PickFoodListFragment) fragments.get(0);
                        if (fn.isAdded() && fn.isStatusDefault()) {
                            if (ctv_isP.isChecked() && ctv_retail.isChecked()) {
                                ctv_retail.setChecked(false);
                                isP = 1;
                                fn.update();
                            } else {
                                if (!ctv_retail.isChecked() && ctv_isP.isChecked()) {
                                    ctv_retail.setChecked(true);
                                    isP = -1;
                                    fn.update();
                                }

                            }

                        }
                    }
                }
                break;
            }
            case R.id.ctv_jin: {
                //商品标准“1”：通货商品 “2”：精品商品 '，-1全部 productCriteria
                List<Fragment> fragments = getFirstVisibleFragments();
                if (fragments.size() > 0) {
                    if (fragments.get(0) instanceof PickFoodListFragment) {
                        PickFoodListFragment fn = (PickFoodListFragment) fragments.get(0);
                        if (fn.isAdded() && fn.isStatusDefault()) {
                            if (ctv_jin.isChecked() && ctv_ordinary.isChecked()) {
                                ctv_jin.setChecked(false);
                                isCriteria = 1;
                                fn.update();
                            } else {
                                if (!ctv_jin.isChecked() && ctv_ordinary.isChecked()) {
                                    ctv_jin.setChecked(true);
                                    isCriteria = -1;
                                    fn.update();
                                }

                            }

                        }
                    }
                }
                break;
            }
            case R.id.ctv_ordinary: {
                List<Fragment> fragments = getFirstVisibleFragments();
                if (fragments.size() > 0) {
                    if (fragments.get(0) instanceof PickFoodListFragment) {
                        PickFoodListFragment fn = (PickFoodListFragment) fragments.get(0);
                        if (fn.isAdded() && fn.isStatusDefault()) {
                            if (ctv_jin.isChecked() && ctv_ordinary.isChecked()) {
                                ctv_ordinary.setChecked(false);
                                isCriteria = 2;
                                fn.update();
                            } else {
                                if (!ctv_ordinary.isChecked() && ctv_jin.isChecked()) {
                                    ctv_ordinary.setChecked(true);
                                    isCriteria = -1;
                                    fn.update();
                                }
                            }

                        }
                    }
                }
                break;
            }
            case R.id.img_open: {
                if (line_type.getVisibility()==View.VISIBLE) {
                    line_type.setVisibility(View.GONE);
                }else{
                    line_type.setVisibility(View.VISIBLE);
                }

                break;
            }
        }
    }

    /**
     * 排序或筛选监听
     */
    @Override
    public String sortString() {
        return sortString;
    }

    @Override
    public String goodsName() {
        return goodsName;
    }

    @Override
    public int categoryId() {
        return categoryId;
    }

    @Override
    public int getIsP() {
        return isP;
    }

    @Override
    public int getIsCRITERIA() {
        return isCriteria;
    }

    @Override
    public int isCanteen() {
        return 0;
    }


    /**
     * 抽屉监听
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        /**
         * 当抽屉被滑动的时候调用此方法
         * slideOffset 表示 滑动的幅度（0-1）
         */
 /*       if (mViewPager.getCurrentItem() == 0) {
            drawerLayout.closeDrawer(drawerView);
        }*/
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        /**
         * 当一个抽屉被完全打开的时候被调用
         */
        initDrawerData(mViewPager.getCurrentItem());
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        /**
         * 当一个抽屉完全关闭的时候调用此方法
         */

    }


    @Override
    public void onDrawerStateChanged(int newState) {
        /**
         * 当抽屉滑动状态改变的时候被调用
         * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
         * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
         */
    }

    //登录后刷新
    @Subscribe
    public void update(Login_in login_in) {
        isNeedUpdate = true;
    }

    //退出后刷新
    @Subscribe
    public void update(Login_out login_out) {
        isNeedUpdate = true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        categoryId = -1;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
