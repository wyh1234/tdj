package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import com.base.event.IntegralLogin;
import com.base.viewModel.adapter.OnItemClickListener;

import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.DuiBaRegisterUrl;
import cn.com.taodaji.model.entity.IntegralShopMy;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.integral.fragment.IntegralShopCartFragment;
import cn.com.taodaji.ui.activity.integral.fragment.IntegralShopFragment;
import cn.com.taodaji.ui.activity.integral.fragment.IntegralShopMyFragment;
import cn.com.taodaji.ui.activity.integral.popuwindow.CheckSuccessPopupWindow;
import cn.com.taodaji.ui.activity.integral.tools.CurrentItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import cn.com.taodaji.ui.activity.myself.ShareActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.viewModel.adapter.GetPointsAdapter;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import cn.com.taodaji.viewModel.adapter.TimeLineAdapter;
import tools.activity.DataBaseActivity;
import tools.fragment.DataBaseFragment;
import tools.statusbar.Eyes;

public class IntegralShopMainActivity extends DataBaseActivity implements View.OnClickListener, ItemClickListener {
    private List<IntegralShopMy.DataBean.ApproachsBean> itemList = new ArrayList<>();
    private GetPointsAdapter pointsAdapter;
    private SimpleMyselfFunctionAdapter myRecyclerViewAdapter_6;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.rv_timeline)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_get_points)
    RecyclerView pointsRecyclerView;
    @BindView(R.id.giv_user_icon)
    GlideImageView userIcon;
    @BindView(R.id.tv_vip)
    TextView vip;
    @BindView(R.id.tv_points_detail)
    TextView pointsDetail;
    @BindView(R.id.ll_checked_days)
    RelativeLayout checkedDays;
    @BindView(R.id.btn_go_check)
    Button goCheck;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone_num)
    TextView tv_phone_num;
    @BindView(R.id.tv_my_points)
    TextView tv_my_points;
    @BindView(R.id.tv_check_days)
    TextView tv_check_days;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.rv_recyclerView_order)
    RecyclerView rv_recyclerView_order;
    @BindView(R.id.tv_integral)
    ImageView tv_integral;

    private CheckSuccessPopupWindow successPopuoWindow;
    private TimeLineAdapter adapter;
    private  List<ADInfo> list;
    private IntegralShopMy body;
    private boolean falg;
    private IntegralShopMainActivity activity;

    public IntegralShopMy getBody() {
        return body;
    }

    public void setBody(IntegralShopMy body) {
        this.body = body;
    }


    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.fragment_integral_mysellf);
    }

    @Override
    protected void initView() {
        super.initView();
        Eyes.setStatusBarColor(IntegralShopMainActivity.this, UIUtils.getColor(IntegralShopMainActivity.this, R.color.red_f0));
        ButterKnife.bind(this);
        vip.setOnClickListener(this);
        pointsDetail.setOnClickListener(this);
        checkedDays.setOnClickListener(this);
        goCheck.setOnClickListener(this);
        userIcon.setImageBitmap(BitmapUtil.toRoundBitmap(R.drawable.head_portrait));
        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getImgCheckStatus() == 1) {
                userIcon.loadImage(PublicCache.loginPurchase.getImageUrl());
            }
        }
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(this);
        title.setText("我的");
        initTimeLine();
        initOrderView();
        initGetPoints();

    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getUserAndApproach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (falg){
            if (!ListUtils.isEmpty(itemList)){
                itemList.clear();
            }
            getUserAndApproach();
            falg=false;
        }

    }
    public void getUserAndApproach(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getUserAndApproach(map, new RequestCallback<IntegralShopMy>(this) {
            @Override
            public void onSuc(IntegralShopMy body) {

                setBody(body);
                ImageLoaderUtils.loadImage(userIcon,body.getData().getHeadUrl());
                tv_name.setText(body.getData().getNickName());
                tv_phone_num.setText(body.getData().getMobile());
                if (body.getData().getLevel()==0){
                    vip.setText("铜牌会员");
                }else if (body.getData().getLevel()==1){
                    vip.setText("银牌会员");
                }else if (body.getData().getLevel()==2){
                    vip.setText("金牌会员");
                }else if (body.getData().getLevel()==3){
                    vip.setText("钻石会员");
                }else if (body.getData().getLevel()==4){
                    vip.setText("至尊会员");
                }
                tv_my_points.setText(body.getData().getIntegral()+"");
                tv_check_days.setText(body.getData().getSignCount()+"");
                adapter.setCurrentNode(body.getData().getSignCount());
                goCheck.setSelected(body.getData().getIsSign()==0?true:false);
                goCheck.setText(body.getData().getIsSign()==0?"签到":"已签到");
                if (!ListUtils.isEmpty(list)){
                    list.get(1).setGoodsCount(body.getData().getMyWaiePayment());
                    myRecyclerViewAdapter_6.notifyDataSetChanged(list);
                }
                itemList.addAll(body.getData().getApproachs());
                pointsAdapter.notifyDataSetChanged();

                ShowLoadingDialog.close();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }
    private void initTimeLine() {
        adapter = new TimeLineAdapter(this, 7);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
    }
    private void initOrderView() {
        tv_integral.setOnClickListener(this);
        myRecyclerViewAdapter_6 = new SimpleMyselfFunctionAdapter();
        rv_recyclerView_order.setAdapter(myRecyclerViewAdapter_6);
        rv_recyclerView_order.setLayoutManager(new GridLayoutManager(this, 3));
        myRecyclerViewAdapter_6.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        myRecyclerViewAdapter_6.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                String url = null;
                if (onclickType == 0) {
                    if (!checkedLoginState()) {
                        LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                        return true;
                    }
                    Intent intent=new Intent(IntegralShopMainActivity.this, WebViewActivity.class);
                    if (position==0){
                        url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());

                    }else if (position==1){
                        falg=true;
                        url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+
                                ( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=0";

                    }else if (position==2){
                        url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+
                                ( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=1";
                    }
                    intent.putExtra("url",url);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        String[] titles = new String[]{"全部", "待付款", "已付款"};
        int[] imageID = new int[]{R.mipmap.suoyou_bg, R.mipmap.daifukuan_bg, R.mipmap.yifukuan_bg};

        list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        myRecyclerViewAdapter_6.setList(list);
    }

    public void initGetPoints(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        pointsRecyclerView.setLayoutManager(layoutManager);
        pointsRecyclerView.setNestedScrollingEnabled(false);
        pointsAdapter = new GetPointsAdapter(itemList,this);
        pointsAdapter.setItemClickListener(this);
        pointsRecyclerView.setAdapter(pointsAdapter);

    }

    private boolean checkedLoginState() {
        //在页面显示时验证是否登录
        if (PublicCache.loginPurchase == null ) {
            // LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_vip:
                Intent intent = new Intent(this, ShopVipActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_points_detail:
                Intent intent2 = new Intent(this, PointsDetailActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_checked_days:
                Intent intent1 = new Intent(this, WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/signRulePage");
                startActivity(intent1);
                break;
            case R.id.btn_go_check:
                if (goCheck.isSelected()){
                    if (ClickCheckedUtil.onClickChecked(2000)){
                        LogUtils.i(goCheck);
                        integral_signIn();
                    }


                }

                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_integral:
                getDuiBaurl();
                break;
        }

    }

    private void getDuiBaurl() {
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getDuiBaRegisterUrl( map, new RequestCallback<DuiBaRegisterUrl>() {
            @Override
            public void onSuc(DuiBaRegisterUrl body) {
                ShowLoadingDialog.close();
                Intent intent1 = new Intent(IntegralShopMainActivity.this, SpecialActivitiesActivity.class);
                intent1.putExtra("url", body.getData());
                intent1.putExtra("duiba", "duiba");
                startActivity(intent1);
            }

            @Override
            public void onFailed(int findByActivitiesID, String msg) {
                ShowLoadingDialog.close();
                if (findByActivitiesID==901){
                    if (!ActivityManage.isActivityExist(LoginActivity.class) && !ActivityManage.isActivityExist(LoginPurchaserActivity.class)) {
                        BaseActivity baseActivity = ActivityManage.getTopActivity();
                        if (baseActivity == null) return;
                        LoginMethod.getInstance(baseActivity).toLoginActivity();
                    }
                }
            }
        });
    }

    public void integral_signIn(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().integral_signIn(map, new RequestCallback<CartQuantity>(this) {
            @Override
            public void onSuc(CartQuantity body) {
                ShowLoadingDialog.close();
                goCheck.setSelected(false);
                goCheck.setText("已签到");

                adapter.setCurrentNode(body.getData().getSignCount());
                tv_check_days.setText(body.getData().getSignCount()+"");
                tv_my_points.setText(body.getData().getTotalIntegral()+"");
                if (successPopuoWindow!=null){
                    if (successPopuoWindow.isShowing()){
                        return;
                    }
                }
                successPopuoWindow= new CheckSuccessPopupWindow(IntegralShopMainActivity.this,body.getMsg());
                successPopuoWindow.setDismissWhenTouchOutside(false);
                successPopuoWindow.setInterceptTouchEvent(false);
                successPopuoWindow.setPopupWindowFullScreen(true);//铺满
                successPopuoWindow.showPopupWindow();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });


    }
    @Override
    public void onItemClick(View v, int position) {
        if (position==4){
            Intent intent=new Intent(this, BuyIntegralActivity.class);
            startActivity(intent);

        }else if (position==0||position==1){
            ActivityManage.setTopActivity(ManageActivity.class);
            ManageActivity manageActivity = ActivityManage.getActivity(ManageActivity.class);
            if (manageActivity != null && manageActivity.mViewPager != null)
                manageActivity.mViewPager.setCurrentItem(0, false);
//            activity.mViewPager.setCurrentItem(0, false);
        }else if (position==2){
            Intent intent=new Intent(this, ShareActivity.class);
            startActivity(intent);
        }else if (position==3){
            Intent intent = new Intent(this, OrderListActivity.class);
            EventBus.getDefault().postSticky(new OrderEvent(position + 1));
            startActivity(intent);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    //登陆失效事件
    @Subscribe
    public void onEvent(IntegralLogin event) {
        if (!ActivityManage.isActivityExist(LoginActivity.class) && !ActivityManage.isActivityExist(LoginPurchaserActivity.class)) {
            BaseActivity baseActivity = ActivityManage.getTopActivity();
            if (baseActivity == null) return;
            LoginMethod.getInstance(baseActivity).toLoginActivity();
        }
    }
    /*   private TabLayout tabLayout;
    public ViewPager mViewPager ;
    private ManageActivityPagerAdapter mAdapter;
    private List<DataBaseFragment> fragments = new ArrayList<>();
    @Override
    public void onClick(View view) {

    }
    protected void initView() {
        super.initView();
        Eyes.setStatusBarColor(IntegralShopMainActivity.this, UIUtils.getColor(this, R.color.red_f0));
//        Eyes.setLightStatusBar(this,true);//设置状态栏字体颜色

        mViewPager = findViewById(R.id.tabLayout_viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.tabLayout_viewpager);
        mViewPager.setOffscreenPageLimit(2);
        if (tabLayout == null || mViewPager == null) return;
        initFragments();
        mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        mAdapter.setFragments(fragments);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        initTabView();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
                if (tab.getPosition() == 0||tab.getPosition() == 2) {
                    Eyes.setStatusBarColor(IntegralShopMainActivity.this, UIUtils.getColor(IntegralShopMainActivity.this, R.color.red_f0));
                } else if (tab.getPosition() == 1){
                    Eyes.setStatusBarColor(IntegralShopMainActivity.this, UIUtils.getColor(IntegralShopMainActivity.this, R.color.white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (getIntent().getStringExtra("current")!=null){
            mViewPager.setCurrentItem(2, false);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCurrentItem(CurrentItem event) {
        if (event.getPos()==2){
            mViewPager.setCurrentItem(event.getPos(), false);
        }else {
            mViewPager.setCurrentItem(1, false);
        }

    }

      //登陆失效事件
    @Subscribe
    public void onEvent(IntegralLogin event) {
        if (!ActivityManage.isActivityExist(LoginActivity.class) && !ActivityManage.isActivityExist(LoginPurchaserActivity.class)) {
            BaseActivity baseActivity = ActivityManage.getTopActivity();
            if (baseActivity == null) return;
            LoginMethod.getInstance(baseActivity).toLoginActivity();
        }
    }

    private List<DataBaseFragment> initFragments() {
        fragments.clear();

        IntegralShopFragment integralShopFragment = new IntegralShopFragment();
        integralShopFragment.setTitle(Integral_MAIN_BOTTOM_TAG.get(0));
        fragments.add(integralShopFragment);

        IntegralShopCartFragment integralShopCartFragment = new IntegralShopCartFragment();
        integralShopCartFragment.setTitle(Integral_MAIN_BOTTOM_TAG.get(1));
        fragments.add(integralShopCartFragment);

        IntegralShopMyFragment integralShopMyFragment = new IntegralShopMyFragment();
        integralShopMyFragment.setTitle(Integral_MAIN_BOTTOM_TAG.get(2));
        fragments.add(integralShopMyFragment);



        return fragments;
    }
    public DataBaseFragment getViewPagerFragment(int item) {
        return fragments.get(item);
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.integral_shop_main_layout);
    }

    private void initTabView() {
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }
    }
    private View getTabView(int position) {
        View v = ViewUtils.getFragmentView(tabLayout, R.layout.tablayout_item_bottom_view);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView titleText = v.findViewById(R.id.tab_text);
        ImageView titleImg = v.findViewById(R.id.tab_image);
        titleImg.setImageResource(Integral_MAIN_BOTTOM_ICON[position]);
        titleText.setText(Integral_MAIN_BOTTOM_TAG.get(position));
        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }*/
}
