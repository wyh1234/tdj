package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.BaseActivity;
import com.base.activity.StatusBarBaseActivity;
import com.base.cycleViewPager.CycleViewUtil;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.utils.ADInfo;
import com.base.utils.DateUtils;
import com.base.utils.DialogUtils;
import com.base.utils.IOUtils;
import com.base.utils.JavaMethod;
import com.base.utils.MD5AndSHA;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
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

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FindBusiness;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.HomePageFuncationButton;
import cn.com.taodaji.model.entity.HomepageGridDatas;
import cn.com.taodaji.model.entity.Inforeaded;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.entity.SpecialActivities;
import cn.com.taodaji.model.entity.StoreRecommend;
import cn.com.taodaji.model.event.CityChangeEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.homepage.AppletWebActivity;
import cn.com.taodaji.ui.activity.homepage.AttractInvestmentActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.OftenBuyActivity;
import cn.com.taodaji.ui.activity.homepage.RichScanHintActivity;
import cn.com.taodaji.ui.activity.homepage.SearchNewActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialOfferActivity;
import cn.com.taodaji.ui.activity.login.CityActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesRequestOrderActivity;
import cn.com.taodaji.viewModel.adapter.GroupHomePageAdapter;
import cn.com.taodaji.viewModel.adapter.HomeSpecialShopAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import tools.animation.WelcomeEnterPopuWindow;
import tools.fragment.DataBaseFragment;
import tools.jni.JniMethod;

public class HomepageFragment extends DataBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private LinearLayout city_localhost;
    private TextView city_text;
    private TextView search_text;
    private ImageView message_icon;
    private View mainView;
    private ManageActivity manageActivity;

    private GroupHomePageAdapter grid_adapter;
    // private CycleViewFragment cycleViewFragment;
    private CycleViewUtil cycleViewUtil;

    private SimpleMyselfFunctionAdapter myRecyclerViewAdapter_4;
    private HomeSpecialShopAdapter mHomeSpecialShopAdapter;

    private SwipeToLoadLayout swipeToLoadLayout;

    private boolean isNeedUpdate = false;

    private int inforeadedCount;//行业信息数量
    private DialogUtils dialogUtils = null;
    private WelcomeEnterPopuWindow welcomeEnterPopuWindow;


    @Override
    public void onRefresh() {
        cityInit();
        getSpecialShops();
        //10个功能按扭
        getFunctionButton();
        industryInformation();
        //9宫格数据请求
        getHomePageData();
        //活动专题请求
        getSpecialActivities();
        showDialog();
        LogUtils.i("showDialog");


    }
    private void showDialog() {
        if (manageActivity.getFeeTips()!=null){
            if (manageActivity.getFeeTips().getData().getInfo().getReminrType()!=0)
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


    @Override
    public View initView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_homepage, container, false);

        swipeToLoadLayout = ViewUtils.findViewById(mainView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);

        //toolbar
        initToolbar(mainView);

        //九宫格
        RecyclerView home_page_grid = ViewUtils.findViewById(mainView, R.id.swipe_target);
        home_page_grid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        home_page_grid.addItemDecoration(new DividerGridItemDecoration(getContext()));
        grid_adapter = new GroupHomePageAdapter();
//          grid_adapter.setTypeFixed(TYPE_GROUP);
        home_page_grid.setAdapter(grid_adapter);


        //轮播窗口
        cycleViewUtil = new CycleViewUtil(getContext());
        cycleViewUtil.setHightstate(3.2f);
        View cycleyView = cycleViewUtil.initView();
        grid_adapter.addHeaderView(cycleyView);

        cycleViewUtil.setOnCustomerItemClickListener((view, position, bean) -> {
            switch (bean.getImageGoodsType()) {
                case 1:    //商品
                    GoodsDetailActivity.startActivity(getContext(), Integer.valueOf(bean.getImageId()));
                    break;
                case 2: //店铺
                    ShopDetailInformationActivity.startActivity(getContext(), Integer.valueOf(bean.getImageId()));
                    break;
                case 3:  //活动专题  促销活动
                    Intent intent = new Intent(getActivity(), SpecialOfferActivity.class);
                    intent.putExtra("entity_id", bean.getEntity_id());
                    intent.putExtra("type", 3);
                    startActivity(intent);
                    break;
                case 4:  //h5专题

                    String url = "";
                    if (!TextUtils.isEmpty(bean.getImageLinkHttpUrl()) && bean.getImageLinkHttpUrl().contains("islogin=true")) {

                        if (LoginMethod.notLoginChecked()) {
                            LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                            return;
                        }

                        if (PublicCache.loginPurchase != null) {

                            String sstr = MD5AndSHA.md5Encode("tdj" + PublicCache.loginPurchase.getEntityId());

                            if (PublicCache.loginPurchase.getFlag() == 1) {//主账号
                                url = bean.getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getEntityId();
                            } else {//子账号
                                url = bean.getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getSubUserId();
                            }
                            url += "&sign=" + sstr;
                        } else if (bean.getImageLinkHttpUrl().contains("activityfor=customer") && PublicCache.loginSupplier != null) {
                            if (dialogUtils == null) {
                                dialogUtils = DialogUtils.getInstance(getBaseActivity()).getSingleDialog(R.layout.dialog_activities_message, UIUtils.getString(R.string.dialog_recharge_tips)).setPositiveButton("", null);
                            }
                            dialogUtils.show();
                            return;
                        }

                    } else url = bean.getImageLinkHttpUrl();


                    Intent intent1 = new Intent(getActivity(), SpecialActivitiesActivity.class);
                    intent1.putExtra("url", url);
                    startActivity(intent1);

                    break;
            }
        });

        View hearView = ViewUtils.getFragmentView(home_page_grid, R.layout.fragment_homepage_new_item);
        grid_adapter.addHeaderView(hearView);

        //招商入驻，功能按钮线面的布局
        RecyclerView special_shop_gridView = ViewUtils.findViewById(hearView, R.id.special_shop);
        special_shop_gridView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // special_shop_gridView.addItemDecoration(new DividerItemDecoration(getContext(), UIUtils.dip2px(8.55f), R.layout.t_split_line));
        mHomeSpecialShopAdapter = new HomeSpecialShopAdapter();
        mHomeSpecialShopAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    FindBusiness.DataBean.ItemsBean itemsBean = (FindBusiness.DataBean.ItemsBean) bean;
                    switch (itemsBean.getType()) {
                        //精品蔬菜
                        case 7:
                            Intent in = new Intent(getActivity(), SpecialOfferActivity.class);
                            in.putExtra("type", 7);
                            startActivity(in);
                            return true;
                        //促销活动
                        case 8:
                            Intent in1 = new Intent(getActivity(), SpecialOfferActivity.class);
                            in1.putExtra("type", 8);
                            startActivity(in1);
                            return true;
                        default:
                            if (TextUtils.isEmpty(itemsBean.getInner_image().trim())) {
                                Intent intent2 = new Intent(getActivity(), AttractInvestmentActivity.class);
                                intent2.putExtra("name", itemsBean.getAdsInfo());
                                //intent2.putExtra("phone", "18007274067");
                                startActivity(intent2);
                            } else {
                                //保鲜疏
                                ShopDetailInformationActivity.startActivity(getActivity(), itemsBean.getStoreId());
                            }

                            return true;
                    }
                }
                return false;
            }
        });
        special_shop_gridView.setAdapter(mHomeSpecialShopAdapter);

        //功能按扭列表
        RecyclerView gridView = ViewUtils.findViewById(hearView, R.id.function_buttons);
        gridView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        myRecyclerViewAdapter_4 = new SimpleMyselfFunctionAdapter();
        myRecyclerViewAdapter_4.setViewSize(UIUtils.dip2px(50), UIUtils.dip2px(50));
        myRecyclerViewAdapter_4.setRed(true);
        myRecyclerViewAdapter_4.setItemClickListener((view, onclickType, position, bean) -> {
            if (onclickType == 0) {
                ADInfo adInfo = (ADInfo) bean;
                switch (adInfo.getImageGoodsType()) {
/*                        case R.mipmap.main_grab_discount:
                        //抢特价
                        UIUtils.showToastSafesShort("活动已结束");
//                             Intent in = new Intent(getBaseActivity(), SpecialOfferActivity.class);
//                             startActivity(in);
                        return true;*/

                    case 0:
                        manageActivity.mViewPager.setCurrentItem(2, true);
                        PickFoodFragment fragment = (PickFoodFragment) manageActivity.getViewPagerFragment(2);
                        fragment.setCurrentItem(adInfo.getEntity_id(), true);
                        return true;
                    case 1:
                        //http://msg.taodaji.com.cn/m/index.php?mod=applogin&mobile=$TEL&action=auto&cityid=$CITYID

                        String phone;
                        if (PublicCache.loginPurchase != null)
                            phone = PublicCache.loginPurchase.getPhoneNumber();
                        else if (PublicCache.loginSupplier != null)
                            phone = PublicCache.loginSupplier.getPhoneNumber();
                        else phone = "-1";


                        if (PublicCache.findByIsActive == null) return true;
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


                        String url = adInfo.getImageUrl().replace("$MOBILE", phone);
                        url = url.replace("$CITYID", cityId);
                        url = url.replace("$DATE", date);
                        url = url.replace("$UNIQUE", unique);
                        url = url.replace("$SIGN", sign);
                        AppletWebActivity.startActivity(getContext(), url);
                        return true;
                    case 2:
                        //经常买
                        Intent intent = new Intent(getContext(), OftenBuyActivity.class);
                        startActivity(intent);
                        return true;
                    case 3:
                        goToWholeUnit();
                        break;
                }
            }
            return false;
        });
        gridView.setAdapter(myRecyclerViewAdapter_4);


        //  swipeToLoadLayout.setRefreshing(true);
        return mainView;
    }


    private void goToWholeUnit() {
        PickFoodFragment fragment = (PickFoodFragment) manageActivity.getViewPagerFragment(2);
        fragment.set_ctv_isP_Checked();
        manageActivity.mViewPager.setCurrentItem(2, true);
    }


    //功能按扭数扭请求
    private void getFunctionButton() {
        getRequestPresenter().commendCategory(new RequestCallback<HomePageFuncationButton>() {
            @Override
            protected void onSuc(HomePageFuncationButton body) {
                stop();
                List<ADInfo> arraylist = new ArrayList<>();
                for (HomePageFuncationButton.DataBean dataBean : body.getData()) {
                    ADInfo adInfo = new ADInfo();
                    adInfo.setImageObject(dataBean.getImageUrl());
                    adInfo.setImageName(dataBean.getName());
                    adInfo.setEntity_id(dataBean.getCategoryId());//跳转去挑菜的id
                    adInfo.setImageGoodsType(dataBean.getType());
                    //行业信息数量
                    if (dataBean.getType() == 1) adInfo.setGoodsCount(inforeadedCount);

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

    //9宫格数据请求
    private void getHomePageData() {
        getRequestPresenter().commendForHomePage(1, 9, new ResultInfoCallback<HomepageGridDatas>() {
            @Override
            public void onSuccess(HomepageGridDatas body) {
                stop();
                grid_adapter.setDatas(body);
                grid_adapter.setGroupList(body.getList());
            }

            @Override
            public void onFailed(int homepageGridDatasResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
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
                stop();
                cycleViewUtil.setDatas(list);
            }

            @Override
            public void onFailed(int specialActivitiesResultInfo, String msg) {
                stop();
            }
        });
    }

    //优秀店铺推荐请求
    private void getStoreRecommend() {
        getRequestPresenter().store_recommend(1, 1, 3, new ResultInfoCallback<StoreRecommend>() {
            @Override
            public void onSuccess(StoreRecommend body) {
                List<StoreRecommend.ListBean> listBeen = new ArrayList<>();
                int size = body.getList().size() > 3 ? 3 : body.getList().size();
                for (int i = 0; i < size; i++) {
                    listBeen.add(body.getList().get(i));
                }
                // storeRecommendAdapter.setList(listBeen);
            }

            @Override
            public void onFailed(int storeRecommendResultInfo, String msg) {

            }
        });
    }

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

                if (myRecyclerViewAdapter_4.getItemCount() > 0) {
                    for (int i = myRecyclerViewAdapter_4.getLastPosition(); i >= 0; i--) {
                        ADInfo adInfo = (ADInfo) myRecyclerViewAdapter_4.getListBean(i);
                        if (adInfo.getImageGoodsType() == 1) {
                            myRecyclerViewAdapter_4.update(i, "goodsCount", inforeadedCount);
                            break;
                        }
                    }
                }
//                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });


    }




    @Override
    public void initData() {
        super.initData();
        if (getActivity() instanceof ManageActivity) {
            manageActivity = (ManageActivity) getActivity();
        }


        onRefresh();
    }

    private void getSpecialShops() {
        Map<String, Object> map = new HashMap<>();
        getRequestPresenter().find_business(map, new RequestCallback<FindBusiness>() {
            @Override
            protected void onSuc(FindBusiness body) {
                List<FindBusiness.DataBean.ItemsBean> arraylist = body.getData().getItems();
                if (arraylist != null && arraylist.size() > 0) {
                    if (arraylist.size() % 2 != 0) {
                        FindBusiness.DataBean.ItemsBean item = new FindBusiness.DataBean.ItemsBean();
                        item.setEntity_id(0);
                        arraylist.add(item);
                    }

                    for (int i = 0; i < arraylist.size(); i++) {
                        FindBusiness.DataBean.ItemsBean itemsBean = arraylist.get(i);
                        if (itemsBean.getType() == 0 && !TextUtils.isEmpty(itemsBean.getInner_image().trim())) {
                            itemsBean.setImage_url(itemsBean.getInner_image());
                        } else {
                            itemsBean.setImage_url(itemsBean.getImage());
                        }
                    }
                    stop();
                    mHomeSpecialShopAdapter.notifyDataSetChanged(arraylist);
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
                UIUtils.showToastSafesShort(msg);
            }
        });

    }

    private void cityInit() {
        if (city_text != null) {
            city_text.setText(PublicCache.site_name);
        }
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        showDialog();

        if (isNeedUpdate) onRefresh();
        isNeedUpdate = false;
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        if (isNeedUpdate) onRefresh();
        else industryInformation();
        isNeedUpdate = false;

    }

    protected void initToolbar(View view) {
        Toolbar toolbar = ViewUtils.findViewById(view, R.id.toolbar);
//        toolbar.setBackgroundColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));

        toolbar.setContentInsetsRelative(0, 0);
        BaseActivity aac = ((BaseActivity) getActivity());
        aac.setSupportActionBar(toolbar);
        city_localhost = ViewUtils.findViewById(toolbar, R.id.city_localhost);
        city_localhost.setOnClickListener(this);
        city_text = ViewUtils.findViewById(toolbar, R.id.city_text);
        search_text = ViewUtils.findViewById(toolbar, R.id.search_text);
        search_text.setOnClickListener(this);
        message_icon = ViewUtils.findViewById(toolbar, R.id.message_icon);
        message_icon.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {

            /* 工具栏的点击事件*/
            case R.id.city_localhost:
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


       /*         addPermissionListen(10233, new OnPermissionListener() {
                    @Override
                    public void permissionSuccess(int requestCode) {
                        Intent intent = new Intent(getBaseActivity(), CaptureActivity.class);
                        *//*ZxingConfig是配置类
             *可以设置是否显示底部布局，闪光灯，相册，
             * 是否播放提示音  震动
             * 设置扫描框颜色等
             * 也可以不传这个参数
             * *//*
                        ZxingConfig config = new ZxingConfig();
                        config.setPlayBeep(false);//是否播放扫描声音 默认为true
                        config.setShake(true);//是否震动  默认为true
                        config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                        config.setReactColor(R.color.orange_yellow_ff7d01);//设置扫描框四个角的颜色 默认为淡蓝色
//                        config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
                        config.setFullScreenScan(true);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                        config.setShowAlbum(false);
                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                        startActivityForResult(intent, 102);


                        //生成二维码
//                        try {
//                            bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);
//
//                        } catch (WriterException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void permissionFail(int requestCode) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                        Toast.makeText(getBaseActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                    }
                });
                permissionRequest(10233, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);*/

                break;

            /* 实力商家点击事件*/
/*            case R.id.storeRecommend:
                StoreRecommendActivity.startActivity(view.getContext(), 2);
                break;
            case R.id.guaranteeOnline:
                startActivity(new Intent(getContext(), MainGuaranteeOnline.class));
                break;*/
        }
    }

    private void stop() {
        if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
    }

    //登录后刷新
    @Subscribe
    public void update(Login_in login_in) {
        isNeedUpdate = true;
        if (myRecyclerViewAdapter_4 != null) {
            myRecyclerViewAdapter_4.clearAll();
        }
        if (grid_adapter != null) {
            grid_adapter.clear();
        }
        if (mHomeSpecialShopAdapter != null) {
            mHomeSpecialShopAdapter.clear();
        }
        if (cycleViewUtil != null) {
            cycleViewUtil.setDatas(null);
        }
    }

/*    //退出后刷新
    @Subscribe
    public void update(Login_out login_out) {
        isNeedUpdate = true;
        if (myRecyclerViewAdapter_4 != null) {
            myRecyclerViewAdapter_4.clearAll();
        }
        if (mHomeSpecialShopAdapter != null) {
            mHomeSpecialShopAdapter.clear();
        }
        if (cycleViewUtil != null) {
            cycleViewUtil.setDatas(null);
        }
        if (grid_adapter != null) {
            grid_adapter.clear();
        }

    }*/

    //定位成功事件
    @Subscribe
    public void onEvent(CityChangeEvent event) {
        PublicCache.site = event.getId();
        PublicCache.site_name = event.getName();
        city_text.setText(PublicCache.site_name);
        EventBus.getDefault().post(new Login_in());
        ((StatusBarBaseActivity) getActivity()).setStatusBarColor(R.color.orange_yellow_ff7d01);
        isNeedUpdate = false;
        onRefresh();
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
}
