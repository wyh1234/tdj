package cn.com.taodaji.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.activity.StatusBarBaseActivity;
import com.base.event.LoginLoseEfficacyEvent;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.utils.ADInfo;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DateUtils;
import com.base.utils.DialogUtils;
import com.base.utils.IOUtils;
import com.base.utils.JavaMethod;
import com.base.utils.MD5AndSHA;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.MyRecyclerView;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj.zxinglibrary.common.Constant;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.CommunityAddress;
import cn.com.taodaji.model.entity.DuiBaRegisterUrl;
import cn.com.taodaji.model.entity.FindByActivitiesID;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.HomePageFuncationButton;
import cn.com.taodaji.model.entity.HomeStore;
import cn.com.taodaji.model.entity.HomepageGridDatas;
import cn.com.taodaji.model.entity.Inforeaded;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.entity.SpecialActivities;
import cn.com.taodaji.model.entity.UpdateCommunityRef;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.CityChangeEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.cashCoupon.CashCouponActivity;
import cn.com.taodaji.ui.activity.homepage.AppletWebActivity;
import cn.com.taodaji.ui.activity.homepage.HomeOftenBuyActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.OftenBuyActivity;
import cn.com.taodaji.ui.activity.homepage.PickFoodActivity;
import cn.com.taodaji.ui.activity.homepage.PovertyAlleviationActivity;
import cn.com.taodaji.ui.activity.homepage.RichScanHintActivity;
import cn.com.taodaji.ui.activity.homepage.SearchNewActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialOfferActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.CityActivity;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import cn.com.taodaji.ui.activity.login.RegisterPurchaserSecondActivity;
import cn.com.taodaji.ui.activity.login.XiaoQuAddressActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestOrderActivity;
import cn.com.taodaji.ui.activity.tdjc.SelectPickUpActivity;
import cn.com.taodaji.viewModel.adapter.BannerHolderView;
import cn.com.taodaji.viewModel.adapter.GroupHomePageAdapter;
import cn.com.taodaji.viewModel.adapter.HomePageSimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.adapter.HomeStoreAdapter;
import cn.com.taodaji.viewModel.adapter.NewGroupHomePageAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import io.reactivex.functions.Consumer;
import tools.animation.ScrollLinearLayoutManager;
import tools.animation.WelcomeEnterPopuWindow;
import tools.extend.MyRecyclerViews;
import tools.fragment.DataBaseFragment;
import tools.jni.JniMethod;
import tools.location.LocationUtils;


public class HomepagesFragment extends DataBaseFragment implements View.OnClickListener, OnRefreshListener,  OnItemClickListener, OnLoadmoreListener {
    private View mainView;
    ConvenientBanner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.city_localhost)
    LinearLayout city_localhost;
    @BindView(R.id.city_text)
    TextView city_text;
    @BindView(R.id.search_text)
    TextView search_text;
    @BindView(R.id.message_icon)
    GlideImageView message_icon;
   /* @BindView(R.id.scroll)
    NestedScrollView scroll;*/
    @BindView(R.id.tv_sao)
    TextView tv_sao;
    LinearLayout ll_bxs,ll_bxs_one;
    LinearLayout hy_ll;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    RecyclerView function_buttons;
    MyRecyclerViews swipe_target;
    @BindView(R.id.xiala_iv)
    GlideImageView xiala_iv;
    @BindView(R.id.iv_shopping_cart)
    GlideImageView iv_shopping_cart;

    @BindView(R.id.tv_shopping_count)
    TextView tv_shopping_count;
    TextView inforeadedcount_tv;


    TextView tv_name_type;


    private boolean isNeedUpdate = false;
    @BindView(R.id.refreshLayout)
    RefreshLayout  swipeRefreshLayout;
    private ManageActivity manageActivity;
    private WelcomeEnterPopuWindow welcomeEnterPopuWindow;
    private int inforeadedCount;//行业信息数量
    private SimpleMyselfFunctionAdapter myRecyclerViewAdapter_4;
    private NewGroupHomePageAdapter grid_adapter;
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private List<ADInfo> listBanner;
    private DialogUtils dialogUtils = null;
    public List<ADInfo> getListBanner() {
        return listBanner;
    }
    private HomePageSimpleGoodsInformationAdapter homePageSimpleGoodsInformationAdapter;
    private int totalDy = 0;
    private int pn=1;
    public void setListBanner(List<ADInfo> listBanner) {
        this.listBanner = listBanner;
    }
    private  List<GoodsInformation> goodsInformationList=new ArrayList<>();
    private LinearLayout ll_bg;
    private ImageView image_one,image_two,image_three,image_four;
    private RxPermissions rxPermissions;
    private HomeStoreAdapter homeStoreAdapter;
    private RecyclerView store_list;
    private String address;
    private String distance;
    private int communityId;

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        cityInit();
//        getSpecialShops();
        //5个功能按扭
        getFunctionButton();
        getHomeStore();
        //行业信息
        industryInformation();
        //今日特价
        getHomePageData();
        //促销活动
        getHomePageData1(1);

        //活动专题请求，广告页
        getSpecialActivities();
    }




    //功能按扭数扭请求
    private void getFunctionButton() {
        getRequestPresenter().commendCategory(new RequestCallback<HomePageFuncationButton>() {
            @Override
            protected void onSuc(HomePageFuncationButton body) {
                LogUtils.i(body);
                stop();
                List<ADInfo> arraylist = new ArrayList<>();
                for (HomePageFuncationButton.DataBean dataBean : body.getData()) {
                    ADInfo adInfo = new ADInfo();
                    adInfo.setImageObject(dataBean.getImageUrl());
                    adInfo.setImageName(dataBean.getName());
                    adInfo.setEntity_id(dataBean.getCategoryId());//跳转去挑菜的id
                    adInfo.setImageGoodsType(dataBean.getType());
                    //行业信息数量
//                    if (dataBean.getType() == 1) adInfo.setGoodsCount(inforeadedCount);

                    adInfo.setImageUrl(dataBean.getUrl());
                    arraylist.add(adInfo);
                }
                myRecyclerViewAdapter_4.notifyDataSetChanged(arraylist);
            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
            }
        });
    }
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            /* 工具栏的点击事件*/
            case R.id.city_localhost:
//                if ((PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()!=1)||PublicCache.loginSupplier!=null){
//                    intent = new Intent(getContext(), CityActivity.class);
//                    startActivityForResult(intent, 100);
//                }else {
//                    location();
//                }

                intent = new Intent(getContext(), CityActivity.class);
                startActivityForResult(intent, 100);

                break;
            case R.id.search_text:
                intent = new Intent(getContext(), SearchNewActivity.class);
                startActivity(intent);
                break;
            case R.id.message_icon:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                    UIUtils.showToastSafesShort("请登录后再扫码");
                    return;
                }

                if (PublicCache.loginSupplier != null) {
                    UIUtils.showToastSafesShort("暂不支持供应商扫码");
                    return;
                }

                if (!IOUtils.isWeixinAvilible(getContext())) {
                    UIUtils.showToastSafesShort("请安装微信后，再进行扫码！");
                    return;
                }

                String appId = JniMethod.wxpayId(); // 填应用AppId
                PublicCache.WX_APP_ID = appId;
                IWXAPI api = WXAPIFactory.createWXAPI(getContext(), appId);

                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//                req.userName = "wx1df6758888470ab4"; // 填小程序原始id
                req.userName = "gh_9f73a062410b";
//              req.path="page/index?key1=xxx&key2=yyy";//类似http的url方法来传递参数 //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                req.path = "pages/scan/scan";
//                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;// 可选打开 开发版，体验版和正式版
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                break;
            case R.id.ll_bxs:
                //扶贫
                Intent intent1 = new Intent(getBaseActivity(), PovertyAlleviationActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_bxs_one:
                onClick(1);//县域特色

                break;

            case R.id.hy_ll:
                String url_="http://msg.taodaji.com.cn/m/index.php?mod=applogin&mobile=$MOBILE&action=auto&cityid=$CITYID&date=$DATE&unique=$UNIQUE&sign=$SIGN";

                String phone;
                if (PublicCache.loginPurchase != null)
                    phone = PublicCache.loginPurchase.getPhoneNumber();
                else if (PublicCache.loginSupplier != null)
                    phone = PublicCache.loginSupplier.getPhoneNumber();
                else phone = "-1";


                if (PublicCache.findByIsActive!= null){

                String cityId = "146";
                for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
                    if (listBean.getName().equals(PublicCache.site_name)) {
                        cityId = listBean.getInitials();
                        break;
                    }
                }

                String date = DateUtils.parseDate("yyyy-MM-dd");
                String unique = SystemUtils.getAndroidId();
                String sign = MD5AndSHA.md5Encode("applogin" + phone + "auto" + cityId + date + unique);


                String url = url_.replace("$MOBILE", phone);
                url = url.replace("$CITYID", cityId);
                url = url.replace("$DATE", date);
                url = url.replace("$UNIQUE", unique);
                url = url.replace("$SIGN", sign);
               LogUtils.e(url);
                AppletWebActivity.startActivity(getContext(), url);
                }
                break;
            case R.id.iv_shopping_cart :
                if (!checkedLoginState()) {
                    LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                    return;
                }
                CashCouponActivity.startActivity(getContext(), 0);
//                manageActivity.mViewPager.setCurrentItem(3, true);
                break;
            case R.id.image_one://2429 3652
                image_onClick(102);
                break;

            case R.id.image_two://3495
//                image_onClick(3495);
                image_onClick(3652);
                break;
            case R.id.image_three://3524
//                image_onClick(3524);
                image_onClick(3495);
                break;
            case R.id.image_four://3705
                image_onClick(3705);
                break;
        }

    }
    public void image_onClick(int id){
        if (!TextUtils.isEmpty(city_text.getText().toString())){
            if (PublicCache.site==2){
                ShopDetailInformationActivity.startActivity(getActivity(), id);
            }

        }

    }
    public void onClick(int type){



        int store = 0;
//        String str="襄阳市";
//        String cityStr=city_text.getText().toString();
            if (PublicCache.site==2){
                if (type==2){
                    store=2429;
                }else {
//                    store=2848;
                    store=4106;
                }

            }else {
                if (type==2){
                    store=2449;
                }else {
                    store=3935;//2849
                }
            }
        LogUtils.i(TextUtils.isEmpty(city_text.getText().toString()));
        LogUtils.i(city_text.getText().toString());
        ShopDetailInformationActivity.startActivity(getActivity(), store);
    }
    public void setIcon(){
            if (PublicCache.site==2){
                tv_name_type.setText("襄农优品");
                image_one.setBackgroundResource(R.mipmap.baoxiansu);
                image_two.setBackgroundResource(R.mipmap.xiaobaixiang);
                image_three.setBackgroundResource(R.mipmap.yuyingfang);
                image_four.setBackgroundResource(R.mipmap.niuroumain);

            }else {
                tv_name_type.setText("基地直采");
                image_one.setBackgroundResource(R.mipmap.syfs);
                image_two.setBackgroundResource(R.mipmap.sysg);
                image_three.setBackgroundResource(R.mipmap.symy);
                image_four.setBackgroundResource(R.mipmap.syhw);
            }
    }

    private void cityInit() {
        if (city_text != null) {
//            if (PublicCache.loginSupplier!=null||(PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()==0)){
                city_text.setText(PublicCache.site_name);
//
//            }else {
//                currentCommunityName();
//            }

            setIcon();
        }

//        if (PublicCache.loginSupplier != null){
//            iv_shopping_cart.setVisibility(View.GONE);
//        }else {
//            iv_shopping_cart.setVisibility(View.VISIBLE);
//            ImageLoaderUtils.loadImage(iv_shopping_cart,R.drawable.go_cashcoupon);
//        }
    }

    public void currentCommunityName(){
        Map<String,Object> map=new HashMap<>();
        map.put("page",0);
        map.put("ps",20);
        map.put("pn",1);
        map.put("lon",PublicCache.longtitude);
        map.put("lat",PublicCache.latitude);
        map.put("communityName","");
        if (PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()==1){
            if (communityId>0){
                map.put("communityId",communityId);
            }else {
                map.put("communityId",PublicCache.loginPurchase.getCommunityId());
            }


        }else {
            if (communityId>0){
                map.put("communityId",communityId);
            }

        }

        LogUtils.e(map);
        getRequestPresenter().customerCommunity_find( PublicCache.site,map, new RequestCallback<XiaoQuAddressItem>() {
            @Override
            protected void onSuc(XiaoQuAddressItem body) {
                    city_text.setText(body.getData().getDataList().getItems().get(0).getCommunityName());
                    address=body.getData().getDataList().getItems().get(0).getCity()+body.getData().getDataList().getItems().get(0).getAddress()+body.getData().getDataList().getItems().get(0).getAddress();
                distance=body.getData().getDataList().getItems().get(0).getDistance()+"";
                if (PublicCache.loginPurchase==null||PublicCache.loginSupplier==null) {
                    PublicCache.refreshId = body.getData().getDataList().getItems().get(0).getFenceGid();
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
            }
        });
//        Map<String,Object> map=new HashMap<>();
//        map.put("customerId",PublicCache.loginPurchase.getEntityId());
//        LogUtils.e(map);
//        getRequestPresenter().customerCommunity_find_addresss(map, new RequestCallback<CommunityAddress>() {
//            @Override
//            protected void onSuc(CommunityAddress body) {
//                if (body.getData()!=null){
//                    city_text.setText(body.getData().getCommunityName());
//                    address=body.getData().getAddress();
//                    communityId= body.getData().getCommunityId();
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailed(int errCode, String msg) {
//            }
//        });
    }
    @Subscribe
    public void updaterCommunityName(XiaoQuAddressItem.DataBean.DataListBean.ItemsBean itemsBean) {//切换提货点刷新数据
        LogUtils.e(itemsBean);
            city_text.setText(itemsBean.getCommunityName());
            address=itemsBean.getCity()+itemsBean.getArea()+itemsBean.getAddress();
        distance=itemsBean.getDistance()+"";
        communityId=itemsBean.getId();
        //给refreshId赋值，然后刷新数据
        if (PublicCache.loginPurchase==null||PublicCache.loginSupplier==null){
            PublicCache.refreshId=itemsBean.getFenceGid();
            clearData();
            totalDy=0;
            getFunctionButton();
            getHomeStore();
            //行业信息
            industryInformation();
            //今日特价
            getHomePageData();
            //促销活动
            getHomePageData1(1);

            //活动专题请求，广告页
            getSpecialActivities();
        }




    }
    @Override
    public void initData() {
        super.initData();

        CartModel cartModel = CartModel.getInstance();
        tv_shopping_count.setText(String.valueOf(cartModel.getCount()));
        if (getActivity() instanceof ManageActivity) {
            manageActivity = (ManageActivity) getActivity();
        }


        onRefresh(swipeRefreshLayout);
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragments_homepage, container, false);

        ButterKnife.bind(this, mainView);
        rxPermissions = new RxPermissions(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadmoreListener(this);

        toolbar.setContentInsetsRelative(0, 0);
        BaseActivity aac = ((BaseActivity) getActivity());
        aac.setSupportActionBar(toolbar);
        city_localhost.setOnClickListener(this);
//        scroll.setOnScrollChangeListener(this);
        search_text.setOnClickListener(this);
        iv_shopping_cart.setOnClickListener(this);
        message_icon.setOnClickListener(this);

        setView();

        return mainView;
    }

    private void setView() {


        headerHeight = 300;
        minHeaderHeight = 60;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalDy-=dy;

                //调用方法
                //Y轴偏移量
                float scrollYs = totalDy;

                //变化率
                float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
                float offset =  Math.max((headerBarOffsetY - scrollYs) / headerBarOffsetY, 0f)-1;
                LogUtils.i(""+offset);
                LogUtils.i(offset);
                if (offset<1){
                    city_text.setTextColor(getResources().getColor(R.color.white));
                    tv_sao.setTextColor(getResources().getColor(R.color.white));
                    message_icon.setImageResource(R.mipmap.ic_rich_scan);
                    xiala_iv.setImageResource(R.mipmap.pulldown_triangle_white);
                    search_text.setBackgroundResource(R.drawable.z_round_rect_solid_white_transparent_one);
                    toolbar.setBackgroundColor(Color.argb((int) (0), 255, 255, 255));
                }else {
                    city_text.setTextColor(getResources().getColor(R.color.black));
                    tv_sao.setTextColor(getResources().getColor(R.color.black));
                    message_icon.setImageResource(R.mipmap.huise_sao);
                    xiala_iv.setImageResource(R.mipmap.xiala_bg);
                    search_text.setBackgroundResource(R.drawable.z_round_rect_solid_white_transparent_two);
                    //Toolbar背景色透明度
                    toolbar.setBackgroundColor(Color.argb((int) (255), 255, 255, 255));

                }




            }
        });
        //促销活动

        ScrollLinearLayoutManager layoutManager1=   new ScrollLinearLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager1);
//      myrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        homePageSimpleGoodsInformationAdapter = new HomePageSimpleGoodsInformationAdapter();

        homePageSimpleGoodsInformationAdapter.setmMainLayout((ViewGroup) mainView);
//        homePageSimpleGoodsInformationAdapter.setmShoppingCart(iv_shopping_cart);

        recyclerView.setAdapter(homePageSimpleGoodsInformationAdapter);

        View hearView = ViewUtils.getFragmentView(recyclerView, R.layout.fragment_homepages_new_item);
        banner=ViewUtils.findViewById(hearView,R.id.banner);
        ll_bg=ViewUtils.findViewById(hearView,R.id.ll_bg);
        image_one=ViewUtils.findViewById(hearView,R.id.image_one);
        image_two=ViewUtils.findViewById(hearView,R.id.image_two);
        image_three=ViewUtils.findViewById(hearView,R.id.image_three);
        image_four=ViewUtils.findViewById(hearView,R.id.image_four);
        store_list=ViewUtils.findViewById(hearView,R.id.store_list);
        tv_name_type=ViewUtils.findViewById(hearView,R.id.tv_name_type);
        image_one.setOnClickListener(this);
        image_two.setOnClickListener(this);
        image_three.setOnClickListener(this);
        image_four.setOnClickListener(this);

        swipe_target=ViewUtils.findViewById(hearView,R.id.swipe_target);
        function_buttons=ViewUtils.findViewById(hearView,R.id.function_buttons);
        inforeadedcount_tv=ViewUtils.findViewById(hearView,R.id.inforeadedcount_tv);
        ll_bxs=ViewUtils.findViewById(hearView,R.id.ll_bxs);
        ll_bxs_one=ViewUtils.findViewById(hearView,R.id.ll_bxs_one);
        hy_ll=ViewUtils.findViewById(hearView,R.id.hy_ll);
        ll_bxs.setOnClickListener(this);
        ll_bxs_one.setOnClickListener(this);
        hy_ll.setOnClickListener(this);
        banner.setOnItemClickListener(this);
        homePageSimpleGoodsInformationAdapter.addHeaderView(hearView);

        //今日特价
        ScrollLinearLayoutManager layoutManager=   new ScrollLinearLayoutManager(getActivity(), 3);

        swipe_target.setLayoutManager(layoutManager);
        layoutManager.setScrollEnable(false);
        grid_adapter = new NewGroupHomePageAdapter(getContext());
        //店铺
        ScrollLinearLayoutManager layoutManager2=   new ScrollLinearLayoutManager(getActivity(), 4);

        store_list.setLayoutManager(layoutManager2);
        layoutManager.setScrollEnable(false);
        homeStoreAdapter = new HomeStoreAdapter(getContext());

        //功能按钮
        function_buttons.setLayoutManager(new GridLayoutManager(getContext(), 5));
        myRecyclerViewAdapter_4 = new SimpleMyselfFunctionAdapter();
        myRecyclerViewAdapter_4.setViewSize(UIUtils.dip2px(50), UIUtils.dip2px(50));
        myRecyclerViewAdapter_4.setRed(true);
        myRecyclerViewAdapter_4.setItemClickListener((view, onclickType, position, bean) -> {


                ADInfo adInfo = (ADInfo) bean;
                switch (adInfo.getImageGoodsType()) {
                    case 0:
                        if (ClickCheckedUtil.onClickChecked(2000)){
                            if (!checkedLoginState()) {
                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                                return true;
                            }
//                            if (PublicCache.loginPurchase!=null){
                                getDuiBaurl();

                           /* }else if (PublicCache.loginSupplier!=null){
                                UIUtils.showToastSafe("该功能待开放");
                            }*/



                        }

                     /*   manageActivity.mViewPager.setCurrentItem(2, true);
                        PickFoodFragment fragment = (PickFoodFragment) manageActivity.getViewPagerFragment(2);
                        fragment.setCurrentItem(adInfo.getEntity_id(), true);*/
                        return true;
                    case 1:
                        //通菜
                        Intent intent1 = new Intent(getContext(), HomeOftenBuyActivity.class);
                        intent1.putExtra("type","1");
                        startActivity(intent1);
                        return true;
                    case 2:
                        //经常买
                        Intent intent2 = new Intent(getContext(), PickFoodActivity.class);
//                        intent2.putExtra("type","2");
                        startActivity(intent2);
                        return true;
                    case 3:
                        //整件批
                        Intent intent3 = new Intent(getContext(), HomeOftenBuyActivity.class);
                        intent3.putExtra("type","3");
                        startActivity(intent3);
                        return true;
                    case 4:
                        //精品菜
                        Intent intent4 = new Intent(getContext(), HomeOftenBuyActivity.class);
                        intent4.putExtra("type","4");
                        startActivity(intent4);
                        return true;
                }

            return false;
        });
        function_buttons.setAdapter(myRecyclerViewAdapter_4);



    }
    private void goToWholeUnit() {
        PickFoodFragment fragment = (PickFoodFragment) manageActivity.getViewPagerFragment(2);
        fragment.set_ctv_isP_Checked();
        manageActivity.mViewPager.setCurrentItem(2, true);
    }
    //店铺请求：
    private void getHomeStore() {
        Map<String,Object> map=new HashMap<>();
        map.put("num",4);

        getRequestPresenter().specialMerchants(map, new RequestCallback<HomeStore>() {
            @Override
            public void onSuc(HomeStore body) {
                stop();
                if (body.getData().getItems()!=null){
                    homeStoreAdapter.setItems(body.getData().getItems());
                    store_list.setAdapter(homeStoreAdapter);
                }
            }

            @Override
            public void onFailed(int findByActivitiesID, String msg) {
                stop();
            }
        });


    }

    //活动专题请求
    private void getSpecialActivities() {

        getRequestPresenter().specialActivities_findAll(0, new ResultInfoCallback<SpecialActivities>() {
            @Override
            public void onSuccess(SpecialActivities body) {

                List<ADInfo> list = new ArrayList<>();
                for (SpecialActivities.ItemsBean bean : body.getItems()) {
                    ADInfo adInfo = new ADInfo();
                    adInfo.setImageGoodsType(bean.getType());
                    adInfo.setImageObject(bean.getImage());
                    switch (bean.getType()) {
                        case 1:    //商品
                            adInfo.setImageId(String.valueOf(bean.getProductId()));
                            break;
                        case 2: //店铺
                            adInfo.setImageId(String.valueOf(bean.getStoreId()));
                            break;
                        case 3:  //活动专题  促销活动
                            adInfo.setImageLinkHttpUrl(bean.getInner_image());
                            adInfo.setEntity_id(bean.getEntity_id());
                            break;
                        case 4:  //h5专题
                            adInfo.setImageLinkHttpUrl(bean.getUrl());
                            break;
                    }
                    list.add(adInfo);
                }


                setListBanner(list);
                stop();

                //初始化商品图片轮播
                banner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new BannerHolderView();
                    }
                }, getListBanner());
                banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.mipmap.hongdian_home});
                banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                banner.startTurning(5000);
            }

            @Override
            public void onFailed(int specialActivitiesResultInfo, String msg) {
                stop();
            }
        });
    }
    //今日特价
    private void getHomePageData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pn", 1);
        map.put("pageable",0);
        map.put("fenceGid",PublicCache.refreshId);
        map.put("ps", 9);
        getRequestPresenter().findByActivitiesID(4, map, new RequestCallback<FindByActivitiesID>() {
            @Override
            public void onSuc(FindByActivitiesID body) {
                stop();
                if (body.getData().getItems()!=null)
                grid_adapter.setItems(body.getData().getItems());
                swipe_target.setAdapter(grid_adapter);

            }

            @Override
            public void onFailed(int findByActivitiesID, String msg) {
                stop();
            }
        });
    }
    //促销活动
    private void getHomePageData1(int pn) {
        if (pn==1){
            if (!ListUtils.isEmpty(goodsInformationList)){
                goodsInformationList.clear();
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("pn", pn);
        map.put("pageable",1);
        map.put("fenceGid",PublicCache.refreshId);
        map.put("ps", 5);
        getRequestPresenter().findByActivitiesID(1, map, new RequestCallback<FindByActivitiesID>() {
            @Override
            public void onSuc(FindByActivitiesID body) {
                LogUtils.i(body);
                stop();
                if (body.getData().getItems()!=null)
                    goodsInformationList.addAll(body.getData().getItems());
                homePageSimpleGoodsInformationAdapter.setList(goodsInformationList);

            }

            @Override
            public void onFailed(int findByActivitiesID, String msg) {
                stop();
            }
        });
    }




    private void stop() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.finishRefresh();
            pn=1;
        }
        if (swipeRefreshLayout.isEnableLoadmore()) {
            swipeRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (tv_shopping_count != null){
            tv_shopping_count.setText(String.valueOf(CartModel.getInstance().getCount()));
        }

        if (isNeedUpdate) onRefresh(swipeRefreshLayout);
        isNeedUpdate = false;
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        if (isNeedUpdate) onRefresh(swipeRefreshLayout);
        else industryInformation();
        isNeedUpdate = false;

    }


    //登录后刷新
    @Subscribe
    public void update(Login_in login_in) {
//        if (PublicCache.loginSupplier != null){
//            iv_shopping_cart.setVisibility(View.GONE);
//        }else {
//            iv_shopping_cart.setVisibility(View.VISIBLE);
//            ImageLoaderUtils.loadImage(iv_shopping_cart,R.drawable.go_cashcoupon);
//        }
        isNeedUpdate = true;
        clearData();
        totalDy=0;
        cityInit();

    }

    //登录后刷新
    @Subscribe
    public void update(Login_out login_out) {
        communityId=0;

    }
    public void clearData(){
        if (myRecyclerViewAdapter_4 != null) {
            myRecyclerViewAdapter_4.clearAll();
        }

        if (grid_adapter != null) {
            grid_adapter.clear();
        }
        if (homePageSimpleGoodsInformationAdapter!=null){
            homePageSimpleGoodsInformationAdapter.clear();
        }
        if (homeStoreAdapter!=null){
            homeStoreAdapter.clear();
        }
        if (getListBanner() != null) {
            if (getListBanner().size()>0){
                getListBanner().clear();
                banner.notifyDataSetChanged();
            }
        }
    }

    //定位成功事件
    @Subscribe
    public void onEvent(CityChangeEvent event) {
        totalDy=0;
        PublicCache.site = event.getId();
        PublicCache.site_name = event.getName();
        city_text.setText(PublicCache.site_name);
        LogUtils.i(event);
        EventBus.getDefault().post(new Login_in());
//        ((StatusBarBaseActivity) getActivity()).setStatusBarColor(R.color.orange_yellow_ff7d01);
        isNeedUpdate = false;
        if (event.isF()){
            onRefresh(swipeRefreshLayout);
        }
//        onRefresh(swipeRefreshLayout);
//        cityInit();
//        setIcon();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == -1) {
            //当地的市场刷新
            if (data != null) {
                String name = data.getStringExtra("name");
                int site = data.getIntExtra("id", 2);
//                EventBus.getDefault().post(new CityChangeEvent(site, name));
                EventBus.getDefault().post(new CityChangeEvent(site, name,false));
            }
        }
        //扫码返回页面
        else if (requestCode == 102 && resultCode == -1) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                UIUtils.showToastSafesShort(content);

                if (!TextUtils.isEmpty(content)) {
                    //如果是条码
                    if (JavaMethod.isNumeric(content) && content.length() == 13) {
                        //售后订单
                        if (PublicCache.loginPurchase != null) {

                            loadingShow();
                            Map<String, String> map = new HashMap<>();
                            map.put("c", String.valueOf(PublicCache.loginPurchase.getEntityId()));
                            map.put("qrCodeId", content);
                            ModelRequest.getInstance().scanQRCode(map).enqueue(new RequestCallback<ScanQRCode>() {
                                @Override
                                protected void onSuc(ScanQRCode body) {
                                    loadingDimss();
                                    if (body.getData() != null && body.getData().getItems() != null && body.getData().getItems().size() > 0) {
                                        for (OrderDetail.ItemsBean itemsBean : body.getData().getItems()) {
                                            itemsBean.setPackageNum(itemsBean.getAmount().intValue());
                                            itemsBean.setPackageFee(itemsBean.getAmount().multiply(itemsBean.getForegift()));
                                        }
                                        Intent intent = new Intent(getContext(), AfterSalesRequestActivity.class);
                                        intent.putExtra("data", body.getData());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailed(int errCode, String msg) {
                                    loadingDimss();
                                    Intent intent = new Intent(getBaseActivity(), RichScanHintActivity.class);
                                    if (errCode == 1) {
                                        intent.putExtra("data", msg);
                                    }
                                    startActivity(intent);
//                                    UIUtils.showToastSafesShort(msg);
                                }
                            });
                        }
                    }
                    //是售后二维码
                    else if (content.startsWith("tdj{")) {
                        int start = content.indexOf("{");
                        int end = content.indexOf("}");
                        if (end > start) {
                            Map<String, String> map = new HashMap<>();
                            String str = content.substring(start + 1, end);
                            if (str.contains(",")) {
                                String[] ss = str.split(",");
                                if (ss.length > 0) {
                                    for (String s : ss) {
                                        if (s.contains(":")) {
                                            String[] params = s.split(":");
                                            if (params.length > 1) {
                                                map.put(params[0], params[1]);
                                            }
                                        }
                                    }
                                }

                            }
                            if (map.size() > 0) {
                                String enId;
                                if ((enId = map.get("c")) != null) {
                                    //售后订单
                                    if (!JavaMethod.isNumeric(enId)) {
                                        UIUtils.showToastSafesShort("数据解析失败 c:" + enId);
                                        return;
                                    }

                                    if (PublicCache.loginPurchase != null && PublicCache.loginPurchase.getEntityId() == Integer.valueOf(enId)) {

                                        loadingShow();
                                        ModelRequest.getInstance().scanQRCode(map).enqueue(new RequestCallback<ScanQRCode>() {
                                            @Override
                                            protected void onSuc(ScanQRCode body) {
                                                loadingDimss();
                                                if (body.getData() != null && body.getData().getItems() != null && body.getData().getItems().size() > 0) {


                                                    for (OrderDetail.ItemsBean itemsBean : body.getData().getItems()) {
                                                        itemsBean.setPackageNum(itemsBean.getAmount().intValue());
                                                        itemsBean.setPackageFee(itemsBean.getAmount().multiply(itemsBean.getForegift()));
                                                    }
                                                    Intent intent = new Intent(getContext(), AfterSalesRequestOrderActivity.class);
                                                    intent.putExtra("data", body.getData());
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onFailed(int errCode, String msg) {
                                                loadingDimss();
                                                UIUtils.showToastSafesShort(msg);
                                            }
                                        });


                                    }
                                    //不是自已的订单
                                    else {
                                        startActivity(new Intent(getBaseActivity(), RichScanHintActivity.class));
                                    }
                                }
                            }
                        }
                    }
                    //非淘大集订单
                    else {
                        Intent intent = new Intent(getBaseActivity(), RichScanHintActivity.class);
                        intent.putExtra("data", "该订单非淘大集订单无法识别。");
                        startActivity(intent);
                    }

                }
            }
        }

    }

   /* @Override
    public void onScrolled(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
       //Y轴偏移量
        float scrollYs = v.getScrollY();

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollYs) / headerBarOffsetY, 0f);
        LogUtils.i(offset);
        if (offset<1){
            city_text.setTextColor(getResources().getColor(R.color.white));
            tv_sao.setTextColor(getResources().getColor(R.color.white));
            message_icon.setImageResource(R.mipmap.ic_rich_scan);
            xiala_iv.setImageResource(R.mipmap.pulldown_triangle_white);
            search_text.setBackgroundResource(R.drawable.z_round_rect_solid_white_transparent_one);
        }else {
            city_text.setTextColor(getResources().getColor(R.color.black));
            tv_sao.setTextColor(getResources().getColor(R.color.black));
            message_icon.setImageResource(R.mipmap.huise_sao);
            xiala_iv.setImageResource(R.mipmap.xiala_bg);
            search_text.setBackgroundResource(R.drawable.z_round_rect_solid_white_transparent_two);

        }

        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset*255), 255, 255, 255));
    }*/


    //行业信息数量
    public void industryInformation() {
        String phone = null;
        if (PublicCache.loginSupplier != null) phone = PublicCache.loginSupplier.getPhoneNumber();
        else if (PublicCache.loginPurchase != null)
            phone = PublicCache.loginPurchase.getPhoneNumber();
//        else {
//            //没有登陆或退出登陆，则需要减掉之前添加的数量
//            PublicCache.notifycationCount -= inforeadedCount;
//            inforeadedCount = 0;
//            return;
//        }
        if (PublicCache.findByIsActive == null) return;

        String cityId = "146";

        for (FindByIsActive.ListBean bean : PublicCache.findByIsActive.getList()) {
            if (bean.getName().equals(PublicCache.site_name)) {
                cityId = bean.getInitials();
                break;
            }
        }
        ModelRequest.getInstance().inforeaded(phone, cityId).enqueue(new RequestCallback<Inforeaded>() {
            @Override
            protected void onSuc(Inforeaded body) {
//                if (body.getCode() == 1) {
                if (TextUtils.isEmpty(body.getData())) return;
                //先减掉之前的添加的数量，如果是第一次，inforeadedCount的数量为0
                PublicCache.notifycationCount -= inforeadedCount;

                inforeadedCount = Integer.valueOf(body.getData());
                //添加到当前的数量当中
                PublicCache.notifycationCount += inforeadedCount;

          /*      if (myRecyclerViewAdapter_4.getItemCount() > 0) {
                    for (int i = myRecyclerViewAdapter_4.getLastPosition(); i >= 0; i--) {
                        ADInfo adInfo = (ADInfo) myRecyclerViewAdapter_4.getListBean(i);
                        if (adInfo.getImageGoodsType() == 1) {
                            myRecyclerViewAdapter_4.update(i, "goodsCount", inforeadedCount);
                            break;
                        }
                    }
                }*/
                //添加数字角标；
                if (inforeadedCount > 99) {
                    inforeadedcount_tv.setVisibility(View.VISIBLE);
                    inforeadedcount_tv.setText("99+");
                }else if (inforeadedCount<=0){
                    inforeadedcount_tv.setVisibility(View.GONE);
                }else {
                    inforeadedcount_tv.setVisibility(View.VISIBLE);
                    inforeadedcount_tv.setText(inforeadedCount+"");
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });


    }

    @Override
    public void onItemClick(int position) {
        switch (getListBanner().get(position).getImageGoodsType()) {
            case 1:    //商品
                GoodsDetailActivity.startActivity(getContext(), Integer.valueOf(getListBanner().get(position).getImageId()));
                break;
            case 2: //店铺
                ShopDetailInformationActivity.startActivity(getContext(), Integer.valueOf(getListBanner().get(position).getImageId()));
                break;
            case 3:  //活动专题  促销活动
                Intent intent = new Intent(getActivity(), SpecialOfferActivity.class);
                intent.putExtra("entity_id", getListBanner().get(position).getEntity_id());
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case 4:  //h5专题

                String url = "";
                if (!TextUtils.isEmpty(getListBanner().get(position).getImageLinkHttpUrl()) && getListBanner().get(position).getImageLinkHttpUrl().contains("islogin=true")) {

                    if (LoginMethod.notLoginChecked()) {
                        LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                        return;
                    }

                    if (PublicCache.loginPurchase != null) {

                        String sstr = MD5AndSHA.md5Encode("tdj" + PublicCache.loginPurchase.getEntityId());

                        if (PublicCache.loginPurchase.getFlag() == 1) {//主账号
                            url = getListBanner().get(position).getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getEntityId();
                        } else {//子账号
                            url = getListBanner().get(position).getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getSubUserId();
                        }
                        url += "&sign=" + sstr;
                    } else if (getListBanner().get(position).getImageLinkHttpUrl().contains("activityfor=customer") && PublicCache.loginSupplier != null) {
                        if (dialogUtils == null) {
                            dialogUtils = DialogUtils.getInstance(getBaseActivity()).getSingleDialog(R.layout.dialog_activities_message, UIUtils.getString(R.string.dialog_recharge_tips)).setPositiveButton("", null);
                        }
                        dialogUtils.show();
                        return;
                    }

                } else url = getListBanner().get(position).getImageLinkHttpUrl();

                LogUtils.e(url);
                Intent intent1 = new Intent(getActivity(), SpecialActivitiesActivity.class);
                intent1.putExtra("url", url);
                startActivity(intent1);

                break;
        }
    }
//          GoodsDetailActivity.startActivity(view.getContext(), bean.getEntityId());


    //接收商品数量变化，改变悬浮按扭的数值
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        tv_shopping_count.setText(String.valueOf(CartModel.getInstance().getCount()));
    }

    private void showDialog() {
        if (manageActivity.getFeeTips()!=null){
            if (manageActivity.getFeeTips().getData().getInfo().getReminrType()==1||manageActivity.getFeeTips().getData().getInfo().getReminrType()==2){
                if (welcomeEnterPopuWindow!=null){
                    if (welcomeEnterPopuWindow.isShowing()){
                        return;
                    }

                }
                welcomeEnterPopuWindow = new WelcomeEnterPopuWindow(getContext(),manageActivity.getFeeTips().getData().getInfo());
//            welcomeEnterPopuWindow.setBackPressEnable(false);//物理返回不生效
                welcomeEnterPopuWindow.setPopupWindowFullScreen(true);//铺满
                welcomeEnterPopuWindow.showPopupWindow();
            }

        }


    }

    private void getDuiBaurl() {
        ShowLoadingDialog.showLoadingDialog(getActivity());
        Map<String, Object> map = new HashMap<>();
        if (PublicCache.loginPurchase!=null){
            map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        }else {
            map.put("userId", PublicCache.loginSupplier.getEntityId());

        }
        getIntegralRequestPresenter().getDuiBaRegisterUrl( map, new RequestCallback<DuiBaRegisterUrl>() {
            @Override
            public void onSuc(DuiBaRegisterUrl body) {
                ShowLoadingDialog.close();
                Intent intent1 = new Intent(getActivity(), SpecialActivitiesActivity.class);
                intent1.putExtra("url", body.getData());
                intent1.putExtra("duiba", "duiba");
                startActivity(intent1);
           /*     Intent intent=new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",  body.getData());
                startActivity(intent);*/


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

    private boolean checkedLoginState() {
        //在页面显示时验证是否登录
        if (PublicCache.loginPurchase == null && PublicCache.loginSupplier == null) {
            // LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return false;
        }
        return true;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getHomePageData1(++pn);
    }



    public void  location(){
        rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                if (b){
                    LocationUtils.getInstance().startLocalService("");
                    Intent intent2 = new Intent(getContext(), SelectPickUpActivity.class);
                        intent2.putExtra("currentCommunityName",city_text.getText().toString());
                        intent2.putExtra("currentCommunityaddress",address);
                    intent2.putExtra("distance",distance);

                    startActivity(intent2);
                }

            }
        });
    }
}
