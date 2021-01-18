package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.listener.PickFoodListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.adapter.MyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryListNext;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.ui.fragment.PickFoodListFragment;
import cn.com.taodaji.ui.ppw.PickFoodSortPopupWindow;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.viewModel.vm.PickFoodViewModel;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

public class PickFoodActivity extends SimpleToolbarActivity implements View.OnClickListener, PickFoodListener, ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {

    private ViewPager mViewPager;
    private DrawerLayout drawerLayout;
    private MyRecyclerView drawer_recy;
    private TextView categoryName,right_text;

    private TextView countImageTv;
    private ImageView imageViewCart,right_icons;
    private View view;


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
    private TextView search_edit;

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
    private String seachName;


    @Override
    protected void initMainView() {
         view = ViewUtils.getLayoutView(this, R.layout.activity_pickfood);
        body_other.addView(view);
        canScrolView = ViewUtils.findViewById(view, R.id.canScrolView);
        right_text= ViewUtils.findViewById(view, R.id.right_text);
        right_icons= ViewUtils.findViewById(view, R.id.right_icons);
        right_icons.setImageResource(R.mipmap.ic_customer_service_gray);
        right_icons.setOnClickListener(this);
        search_edit= ViewUtils.findViewById(view, R.id.search_edit);
        search_edit.setOnClickListener(this);
        countImageTv = ViewUtils.findViewById(view, R.id.tv_shopping_count);
        imageViewCart = ViewUtils.findViewById(view, R.id.iv_shopping_cart);
        shopping_floating_button = ViewUtils.findViewById(view, R.id.shopping_floating_button);
        imageViewCart.setOnClickListener(this);

        TabLayout tabLayout = ViewUtils.findViewById(view, R.id.tabLayout);
        mViewPager = ViewUtils.findViewById(view, R.id.tabLayout_viewpager);
        mViewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(mViewPager);

        mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);


        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_gray66_orage_yellow_ff7d01));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));

        tabLayout.setSelectedTabIndicatorHeight(5);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        LinearLayout tabLayout_header = ViewUtils.findViewById(view, R.id.tabLayout_header);
        View sort_view = ViewUtils.getLayoutView(this, R.layout.fragment_pickfood_sort_header);
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
        drawer_recy.setLayoutManager(new GridLayoutManager(this, 3));
        drawer_recy.closeDefaultAnimator();


    }







    @Override
    protected void titleSetting(Toolbar toolbar) {
        setTitle("食堂菜");
        setStatusBarColor();
        setToolBarColor();
    }
    //接收商品数量变化，改变悬浮按扭的数值
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        countImageTv.setText(String.valueOf(CartModel.getInstance().getCount()));
    }



    @Override
    public void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        CartModel cartModel = CartModel.getInstance();
        countImageTv.setText(String.valueOf(cartModel.getCount()));
        initRightDrawerView();


        if (fragments.size() == 0) getGoodsCategoryList();

        if (countImageTv != null)
            countImageTv.setText(String.valueOf(CartModel.getInstance().getCount()));


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

    /*    search_edit.addTextChangedListener(new TextWatcher() {
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
                    goodsName="全部";
                }
                updateChildFragment();

            }
        });*/
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
    protected void onDestroy() {
        super.onDestroy();
        drawerLayout.closeDrawer(drawer_recy);
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
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
            case R.id.right_icons:
                /** 拨打电话*/
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
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
            case R.id.search_edit:
                Intent intent = new Intent(this, SearchNewActivity.class);
                intent.putExtra("isCanteen",1);
                startActivity(intent);

                break;
            //筛选
            case R.id.filter:
                drawerLayout.openDrawer(drawer_recy);
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
        return 1;
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