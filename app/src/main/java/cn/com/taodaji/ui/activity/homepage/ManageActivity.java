package cn.com.taodaji.ui.activity.homepage;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.activity.StatusBarBaseActivity;
import com.base.common.Config;
import com.base.common.PublicStaticValue;
import com.base.event.LoginLoseEfficacyEvent;
import com.base.event.SystemMaintenanceEvent;
import com.base.event.VersionEvent;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DialogUtils;
import com.base.utils.IOUtils;
import com.base.utils.JavaMethod;
import com.base.utils.MD5AndSHA;
import com.base.utils.NotificationsUtils;


import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;


import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.AndroidUpdate;
import cn.com.taodaji.model.entity.DictFindAll;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.PushMessageCustomerToken;
import cn.com.taodaji.model.entity.SpecialMerchants;
import cn.com.taodaji.model.event.APKInstallEvent;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.CityChangeEvent;
import cn.com.taodaji.model.event.HomepageEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.NotificationStartActivityEvent;
import cn.com.taodaji.model.event.UmengTokenEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.cashCoupon.CashCouponActivity;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import cn.com.taodaji.ui.activity.login.SystemFixActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.fragment.CartFragment;
import cn.com.taodaji.ui.fragment.HomepageFragment;
import cn.com.taodaji.ui.fragment.HomepagesFragment;
import cn.com.taodaji.ui.fragment.MyselfFragment;
import cn.com.taodaji.ui.fragment.MyselfFragmentPur;
import cn.com.taodaji.ui.view.BadgeView;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import me.leolin.shortcutbadger.ShortcutBadger;

import tools.activity.DataBaseActivity;
import cn.com.taodaji.R;
import cn.com.taodaji.ui.fragment.MarketFragment;
import cn.com.taodaji.ui.fragment.PickFoodFragment;
import tools.animation.ExpiredPopuWindow;
import tools.animation.ServStatusCountDownPopuwindow;
import tools.animation.WelcomeEnterPopuWindow;
import tools.fragment.DataBaseFragment;
import tools.loadingDownFile.ApkSaveActivity;
import tools.location.LocationManagerUtils;
import tools.statusbar.Eyes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static cn.com.taodaji.common.PublicCache.isLocationSucc;

public class ManageActivity extends DataBaseActivity implements View.OnClickListener {


    public ViewPager mViewPager = null;
    private TabLayout tabLayout;
    private ManageActivityPagerAdapter mAdapter;
    private List<DataBaseFragment> fragments = new ArrayList<>();
    private ServStatusCountDownPopuwindow servStatusCountDownPopuwindow;
    private ExpiredPopuWindow expiredPopuWindow;
    public FeeTips feeTips;
    private WelcomeEnterPopuWindow welcomeEnterPopuWindow;
    public FeeTips getFeeTips() {
        return feeTips;
    }
    private List<BadgeView> mBadgeViews;
    public void setFeeTips(FeeTips feeTips) {
        this.feeTips = feeTips;
    }
    private List<Integer> mBadgeCountList;
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_main_manage);
    }


    /**
     * 版本过低需要升级
     *
     * @param event
     */
    @Subscribe
    public void onEvent(VersionEvent event) {
        checkUpdate(event.isTips());
    }

    /**
     * - 检测软件更新
     */
    public void checkUpdate(boolean isTips) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        RequestPresenter.getInstance().androidUpdate(new RequestCallback<AndroidUpdate>() {
            @Override
            public void onSuc(AndroidUpdate body) {
                AndroidUpdate.DataBean bean = body.getData();
                // 版本的更新信息
                String version_info = body.getData().getContent();
                String mVersion_name = SystemUtils.getVersionName();// 当前的版本号
                String nVersion_code = body.getData().getVersioin();
                // 显示提示对话
                String particular = version_info.replace("\\n", "\n");
                bean.setContent(particular);

                if (mVersion_name.compareTo(nVersion_code) < 0) {
                    Intent intent = new Intent(getBaseActivity(), ApkSaveActivity.class);
                    intent.putExtra("data", bean);
                    startActivity(intent);

                    //检查本地是否有该版本的安装包
                    String absPath = Config.APKSaveDir + "/taodaji.apk";

                    final File file = new File(absPath);
                    if (IOUtils.fileIsExists(file) && nVersion_code.compareTo(SystemUtils.getApkVersionName(absPath, getBaseActivity())) == 0) {
                        //发送程序可以安装事件  ApkSaveActivity接收
                        EventBus.getDefault().postSticky(new APKInstallEvent(file, particular + "\n\n 已下载完毕,请安装"));
                    } else {
                        //发送程序可以安装事件  ApkSaveActivity接收
                        EventBus.getDefault().postSticky(new APKInstallEvent(null, particular));
                    }

                } else {
                    if (isTips) {
                        UIUtils.showToastSafesShort("已是最新版本");
                    }

                }


            }

            @Override
            public void onFailed(int androidUpdate, String msg) {

            }
        });
    }


    //通知的点击事件
    @Subscribe(sticky = true)
    public void onNotificationEvent(NotificationStartActivityEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (ClickCheckedUtil.onClickChecked(1000))
            ActivityManage.setTopActivity(event.activityClass);
    }

    //登陆失效事件
    @Subscribe
    public void onEvent(LoginLoseEfficacyEvent event) {
        if (!ActivityManage.isActivityExist(LoginActivity.class) && !ActivityManage.isActivityExist(LoginPurchaserActivity.class)) {
            BaseActivity baseActivity = ActivityManage.getTopActivity();
            if (baseActivity == null) return;
            LoginMethod.getInstance(baseActivity).toLoginActivity();
        }
    }

    /**
     * 系统维护事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(SystemMaintenanceEvent event) {
        if (!ActivityManage.isActivityExist(SystemFixActivity.class)) {
            BaseActivity baseActivity = ActivityManage.getTopActivity();
            if (baseActivity == null) return;
            Intent intent = new Intent();
            intent.setClass(baseActivity, SystemFixActivity.class);
            baseActivity.startActivity(intent);//这里的Activity是弹出系统维护的
            baseActivity.finish();
            ActivityManage.removeAllActivity(baseActivity);
        } else {
            ActivityManage.setTopActivity(SystemFixActivity.class);
        }
    }

    /**
     * 友盟推送绑定
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UmengTokenEvent event) {
        Log.e("PublicCache",""+PublicCache.deviceToken_umeng);
        RequestPresenter.getInstance().pushMessageCustomer(PublicCache.deviceToken_umeng, new RequestCallback<PushMessageCustomerToken>() {
            @Override
            protected void onSuc(PushMessageCustomerToken body) {
//                Log.d("token-customer ", "suc " + PublicCache.deviceToken_umeng);
            }

            @Override
            public void onFailed(int errCode, String msg) {
//                Log.d("token-customer ", "err " + PublicCache.deviceToken_umeng);
            }
        });

        RequestPresenter.getInstance().pushMessageSupplier(PublicCache.deviceToken_umeng, new RequestCallback<PushMessageCustomerToken>() {
            @Override
            protected void onSuc(PushMessageCustomerToken body) {
//                Log.d("token-supplier ", "suc " + PublicCache.deviceToken_umeng);
            }

            @Override
            public void onFailed(int pushMessageCustomerToken, String msg) {
//                Log.d("token-supplier ", "err " + PublicCache.deviceToken_umeng);
            }
        });
    }


    private void openNotification() {
        SharedPreferences sp = UIUtils.getSharedPreferences("Notification");
        String ss = sp.getString("isOpen", null);
        if (ss == null) {
            boolean bb = NotificationsUtils.isNotificationOpen(getBaseActivity());
            if (!bb) {
                NotificationsUtils.startNotificationSetting(getBaseActivity());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (PublicCache.loginPurchase == null && PublicCache.loginSupplier == null) {
            PublicCache.login_mode = PURCHASER;
        } else if (PublicCache.loginSupplier != null) {//调显示弹框的按钮
            getRequestPresenter().getSupplierAnnalFeeTips(PublicCache.site_login, PublicCache.loginSupplier.getStore() + "", new RequestCallback<FeeTips>() {
                @Override
                public void onSuc(FeeTips body) {
                    LogUtils.i("1111111111111111111" + body.getData().getInfo().toString());
                    setFeeTips(body);
                    if (body.getData().getInfo().getReminrType()!=0){


                    if (body.getData().getInfo().getReminrType() == 3) {//倒计时
                        if (servStatusCountDownPopuwindow != null) {
                            if (servStatusCountDownPopuwindow.isShowing()) {
                                return;
                            }
                        }
                        servStatusCountDownPopuwindow = new ServStatusCountDownPopuwindow(ManageActivity.this, body.getData().getInfo());
                        servStatusCountDownPopuwindow.setPopupWindowFullScreen(true);//铺满
                        servStatusCountDownPopuwindow.showPopupWindow();
                    }
                    if (body.getData().getInfo().getServStatus() == 3) {//服务过期
                        if (expiredPopuWindow != null) {
                            if (expiredPopuWindow.isShowing()) {
                                return;
                            }
                        }
                        expiredPopuWindow = new ExpiredPopuWindow(ManageActivity.this, body.getData().getInfo());
                        expiredPopuWindow.setPopupWindowFullScreen(true);//铺满
                        expiredPopuWindow.showPopupWindow();
                    }


                }
                    if (getFeeTips().getData().getInfo().getReminrType()==1||getFeeTips().getData().getInfo().getReminrType()==2){
                        if (welcomeEnterPopuWindow!=null){
                            if (welcomeEnterPopuWindow.isShowing()){
                                return;
                            }

                        }
                        welcomeEnterPopuWindow = new WelcomeEnterPopuWindow(ManageActivity.this,getFeeTips().getData().getInfo());
//            welcomeEnterPopuWindow.setBackPressEnable(false);//物理返回不生效
                        welcomeEnterPopuWindow.setPopupWindowFullScreen(true);//铺满
                        welcomeEnterPopuWindow.showPopupWindow();
                    }

                }

                public void onFailed(int goodsCategoryList_resu, String msg) {
                    loadingDimss();
                }
            });


        }
    }

    private LocationManagerUtils locationManagerUtils;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (locationManagerUtils != null)
            locationManagerUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(HomepageEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        mViewPager.setCurrentItem(event.getPosition(), false);
    }

    protected void initView() {
        super.initView();
//        setStatusBarColor(R.color.orange_yellow_ff7d01);
        Eyes.translucentStatusBar(getBaseActivity(),true);

        mViewPager = findViewById(R.id.tabLayout_viewpager);
        //定位
        if (locationManagerUtils == null) {
            locationManagerUtils = new LocationManagerUtils(this) {
                @Override
                public void showLocation(AMapLocation aMapLocation) {
                    if (PublicCache.findByIsActive == null) return;
                    int site = DEFAULT_site;
                    String city=aMapLocation.getCity();
                    for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
                        if (listBean.getName().equals(aMapLocation.getCity())) {
                            site = listBean.getId();
                            break;
                        }
                    }

//                    site = 3;
//                    city = "武汉市";
                    PublicCache.isLocationSucc = true;

                    if (site == DEFAULT_site) {
                        city = DEFAULT_siteName;
                        PublicCache.longtitude = XIANGYANG_LAT.longitude;
                        PublicCache.latitude = XIANGYANG_LAT.latitude;
                        PublicCache.location_default = city;
                        Log.d("gps", city);
                    } else {
                        PublicCache.longtitude = aMapLocation.getLongitude();
                        PublicCache.latitude =aMapLocation.getLatitude() ;
                        PublicCache.location_default = city;

                        if (LoginMethod.notLoginChecked()) {
                            final int finalSite = site;
                            DialogUtils.getInstance(getBaseActivity()).getSimpleDialog("定位到您所在的城市 【" + PublicCache.location_default + "】 已开启，是否切换？", "城市切换").setPositiveButton("切换城市", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    EventBus.getDefault().post(new CityChangeEvent(finalSite, PublicCache.location_default));
                                    EventBus.getDefault().post(new CityChangeEvent(finalSite, PublicCache.location_default,true));
                                }

                            }, R.color.orange_yellow_ff7d01).show();

                        }
                    }


                }
            };
            locationManagerUtils.beginLocatioon();


        }


        //检查是否有读取手机状态和身份权限
        //permissionRequest(101, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show();
        }

        tabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.tabLayout_viewpager);
        mViewPager.setOffscreenPageLimit(4);
        if (tabLayout == null || mViewPager == null) return;
        initFragments();
        mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        mAdapter.setFragments(fragments);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);

//        Trigger trigger = ViewPagerTrigger.newInstance(mViewPager, mAdapter);
//        Filter tint = new TintFilter(0f);
//        prism = Prism.Builder.newInstance().add(trigger).background(getWindow(), tint).background(mViewPager, tint).build();
//        hookPrism();
        initBadgeViews();
        initTabView();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
                if (tab.getPosition() == 4) {
                    Eyes.translucentStatusBar(getBaseActivity(),true);
                    switch (PublicCache.login_mode)
                    {
                        case Constants.SUPPLIER:
//                            Eyes.setStatusBarColor(getBaseActivity(), getResources().getColor(R.color.blue_2898eb));
                            break;
                        case Constants.PURCHASER:
//                            Eyes.setStatusBarColor(getBaseActivity(), getResources().getColor(R.color.orange_yellow_ff7d01));
                            break;
                            default:
//                                Eyes.setStatusBarColor(getBaseActivity(), getResources().getColor(R.color.orange_yellow_ff7d01));
                                break;
                    }
                } else if (tab.getPosition() == 0){
                    Eyes.translucentStatusBar(getBaseActivity(),true);
//                    Eyes.translucentStatusBar(ManageActivity.this,true);
//                    setStatusBarDrawableRes(R.color.orange_yellow_ff7d01);
                }else {
//                    setStatusBarDrawableRes(R.color.orange_yellow_ff7d01);
                    Eyes.setStatusBarColor(getBaseActivity(), getResources().getColor(R.color.orange_yellow_ff7d01));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initStartActivityData(0);

        initializtionData();
//        ShortcutBadger.applyCount(UIUtils.getContext(), 0);
        openNotification();
        //绑定token  Umeng
        onEvent(new UmengTokenEvent(null));

        initStartActivityData(1);


        EasyFloat.with(this).setLayout(R.layout.float_test, new OnInvokeView() {
            @Override
            public void invoke(View view) {
               GlideImageView iv_shopping_cart =view.findViewById(R.id.iv_shopping_cart);
                ImageLoaderUtils.loadImage(iv_shopping_cart,R.drawable.go_cashcoupon);
                iv_shopping_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!checkedLoginState()) {
                            LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                            return;
                        }
                        CashCouponActivity.startActivity(ManageActivity.this, 0);
                    }
                });

            }
        })  .setSidePattern(SidePattern.RESULT_HORIZONTAL)  . setGravity(Gravity.END |Gravity.BOTTOM, 0, -400).setDragEnable(true).show();
    }

    private boolean checkedLoginState() {
        //在页面显示时验证是否登录
        if (PublicCache.loginPurchase == null && PublicCache.loginSupplier == null) {
            // LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return false;
        }
        return true;
    }

    private void initTabView() {
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            // 更新Badge前,先remove原来的customView,否则Badge无法更新
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
            }

            // 更新CustomView
            tab.setCustomView(getTabView(i));
//            if (tab != null) {
//                tab.setCustomView(getTabView(i));
//                tab.setIcon(MAIN_HOMEPAGE_BOTTOM_ICON[i]);
//                tab.setText(MAIN_HOMEPAGE_BOTTOM_TAG.get(i));
//            }
        }
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().setSelected(true);
    }
    private void initBadgeViews() {
        mBadgeCountList
                = new ArrayList<Integer>();
        if (mBadgeViews == null) {
            mBadgeViews = new ArrayList<BadgeView>();
            for (int i = 0; i < fragments.size(); i++) {
                BadgeView tmp = new BadgeView(this);
                tmp.setBadgeMargin(0, 6, 10, 0);
                tmp.setTextSize(10);
                mBadgeViews.add(tmp);
                mBadgeCountList.add(0);
            }
        }
        mBadgeCountList.set(3,CartModel.getInstance().getCount());
    }
    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        buglyInit();
    }
    @Subscribe
    public void onMessageEvent(CartEvent event) {
        mBadgeCountList.set(3,CartModel.getInstance().getCount());
        initTabView();
    }
    private void buglyInit() {
        /**
         *  设置自定义升级对话框UI布局
         *  注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         *  标题：beta_title，如：android:tag="beta_title"
         *  升级信息：beta_upgrade_info  如： android:tag="beta_upgrade_info"
         *  更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
         *  取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         *  确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         *  详见layout/upgrade_dialog.xml
         */
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.canShowUpgradeActs.add(getClass());

        //         设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
//        Bugly.setIsDevelopmentDevice(getApplication(), true);
//         这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
//         调试时，将第三个参数改为true
        Bugly.init(getApplication(), "d431099864", false);

//        UIUtils.showToastSafesShort("测试补丁已启用");

        //检查是否有更新
//        Beta.checkUpgrade(false, false);
        Beta.checkUpgrade();

    }


    private View getTabView(int position) {
        View v = ViewUtils.getFragmentView(tabLayout, R.layout.tablayout_item_bottom_view);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView titleText = v.findViewById(R.id.tab_text);
        ImageView titleImg = v.findViewById(R.id.tab_image);
        titleImg.setImageResource(MAIN_HOMEPAGE_BOTTOM_ICON[position]);
        titleText.setText(MAIN_HOMEPAGE_BOTTOM_TAG.get(position));

        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(titleImg);
        badgeView.setBadgeMargin(12, 0, 0, 8);
        badgeView.setTextSize(10);
        LogUtils.e(mBadgeCountList.get(position));
        badgeView.setText(formatBadgeNumber(mBadgeCountList.get(position)));

        return v;
    }
    public static String formatBadgeNumber(int value) {
        if (value <= 0) {
            return null;
        }

        if (value < 100) {
            // equivalent to String#valueOf(int);
            return Integer.toString(value);
        }

        // my own policy
        return "99+";
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {

        }
    }

//    //登录
    @Subscribe()
    public void update(Login_in login_in) {
        if (PublicCache.loginSupplier != null){
            EasyFloat.hide();
        }
    }
//
//    //退出登录
//    @Subscribe(priority = 999)
//    public void onEvent(Login_out login_out) {
//        checkMyselfFragment();
//    }

//    private void checkMyselfFragment() {
//        if (fragments.size() > 4) {
//            if (PublicCache.loginSupplier != null) {
//                if (fragments.get(4) instanceof MyselfFragmentPur) {
//
//                    MyselfFragmentPur fragment = (MyselfFragmentPur) fragments.get(4);
//
//                    MyselfFragmentSup myselfFragmentSup = new MyselfFragmentSup();
//                    myselfFragmentSup.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(4));
//                    fragments.set(4, myselfFragmentSup);
//                    fragment.isDestroy = true;
//                    fragment.onDetach();
//                    fragment = null;
//                    mAdapter.setFragments(fragments);
//                    initTabView();
//                }
//            } else {
//                if (fragments.get(4) instanceof MyselfFragmentSup) {
//                    MyselfFragmentSup fragment = (MyselfFragmentSup) fragments.get(4);
//                    MyselfFragmentPur myselfFragmentPur = new MyselfFragmentPur();
//                    myselfFragmentPur.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(4));
//                    fragments.set(4, myselfFragmentPur);
//                    fragment.isDestroy = true;
//                    fragment.onDetach();
//                    fragment = null;
//                    mAdapter.setFragments(fragments);
//                    initTabView();
//                }
//            }
//        }
//    }


    private List<DataBaseFragment> initFragments() {
        fragments.clear();

        HomepagesFragment homepage = new HomepagesFragment();
        homepage.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(0));
        fragments.add(homepage);

        MarketFragment marketFragment = new MarketFragment();
        marketFragment.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(1));
        fragments.add(marketFragment);

        PickFoodFragment pickFoodFragment = new PickFoodFragment();
        pickFoodFragment.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(2));
        fragments.add(pickFoodFragment);

        CartFragment cartFragment = new CartFragment();
        cartFragment.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(3));
        fragments.add(cartFragment);

        MyselfFragment myselfFragment = new MyselfFragment();
        myselfFragment.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(4));
        fragments.add(myselfFragment);


//        if (PublicCache.loginSupplier != null) {
//            MyselfFragmentSup myselfFragmentSup = new MyselfFragmentSup();
//            myselfFragmentSup.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(4));
//            fragments.add(myselfFragmentSup);
//        } else {
//            MyselfFragmentPur myselfFragmentPur = new MyselfFragmentPur();
//            myselfFragmentPur.setTitle(MAIN_HOMEPAGE_BOTTOM_TAG.get(4));
//            fragments.add(myselfFragmentPur);
//        }

        return fragments;
    }

    public DataBaseFragment getViewPagerFragment(int item) {
        return fragments.get(item);
    }


    //两次返回键退出程序
    private long mExitTime;


    @Override
    public void onBackPressed() {
//        super.onBackPressed();


        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            UIUtils.showToastSafesShort("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            if (PublicStaticValue.isPatch) {
                //1,杀死自己进程的方法
                android.os.Process.killProcess(android.os.Process.myPid());  //获取PID}
            } else {
                finish();
            }


            //2, System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出


/*                4：杀死别人进程的方法（不能杀死自己）

                ActivityManager.killBackgroundProcesses
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                activityManager.killBackgroundProcesses($packageName);
                需要加入权限：

<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>

                        5：杀死别人进程的方法（不能杀死自己）
                ActivityManager.restartPackage
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                activityManager.restartPackage($packageName);
                需要加入权限:

                        <uses-permission android:name="android.permission.RESTART_PACKAGES"/>*/
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //获取重要通知初始化的数据
    public void tipsData() {

        if (PublicCache.findByIsActive == null) return;

        FindByIsActive.ListBean tempBean = null;
        for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
            if (listBean.getId() == PublicCache.site) {
                tempBean = listBean;
            }
        }
        if (tempBean != null && tempBean.getAnnouncementSwitch() == 1 && !TextUtils.isEmpty(tempBean.getAnnouncementContent())) {
            DialogUtils.getInstance(ManageActivity.this).getSingleDialog(R.layout.dialog_importance_message, "    " + tempBean.getAnnouncementContent()).setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }



      /*  getRequestPresenter().dictFindAll(new RequestCallback<DictFindAll>() {
            @Override
            public void onSuc(DictFindAll body) {

                if (body.getData().getExt_prohibit_order() == 1) {
                    DialogUtils.getInstance(ManageActivity.this).getSingleDialog(R.layout.dialog_importance_message, "    " + body.getData().getExt_prohibit_content()).setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }

            @Override
            public void onFailed(int dictFindAll, String msg) {
                //UIUtils.showToastSafesShort(msg);
            }
        });*/
    }

    //启动页广告
    public void initStartActivityData(int flag) {
        final int final_flag = flag;

        addRequest(ModelRequest.getInstance().specialMerchants_findAllAds(PublicCache.site_login, final_flag), new ResultInfoCallback<SpecialMerchants>() {
            @Override
            public void onSuccess(SpecialMerchants body) {
                if (body.getList() == null || body.getList().size() == 0) return;


                if (final_flag == 0) {

                    SpecialMerchants.ListBean listBean = null;
                    String str = UIUtils.getSharedPreferences("StartActivity").getString("start", "");
                    if (!TextUtils.isEmpty(str)) {
                        listBean = JavaMethod.getJsonBean(str, SpecialMerchants.ListBean.class);
                    }

                    SpecialMerchants.ListBean bean = null;
                    if (listBean == null) {
                        bean = body.getList().get(0);
                    } else {
                        int count = body.getList().size();
                        for (int i = 0; i < count; i++) {
                            if (i == count - 1) {
                                bean = body.getList().get(0);
                            } else if (listBean.getEntityId() == body.getList().get(i).getEntityId()) {
                                bean = body.getList().get(i + 1);
                                break;
                            }
                        }
                    }


                    if (bean != null) {
                        SpecialMerchants.ListBean finalBean = bean;
                        Glide.with(UIUtils.getContext()).asBitmap().load(finalBean.getImage()).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                                SharedPreferences.Editor sp_start = UIUtils.getSharedPreferences("StartActivity").edit();
                                sp_start.putString("start", JavaMethod.transBean2Json(finalBean));
                                sp_start.apply();
                            }
                        });
                    }


                } else {
                    SpecialMerchants.ListBean listBean;
                    if (body.getList().size()>1){
                        Random rand = new Random();
                        listBean = body.getList().get(rand.nextInt(body.getList().size()));
                        LogUtils.e(rand.nextInt(body.getList().size())+"");
                    }else {
                        listBean = body.getList().get(0);
                    }


                    DialogUtils dialogUtils=    DialogUtils.getInstance(getBaseActivity());
                    dialogUtils.getSingleDialog(R.layout.dialog_image).setViewValues(R.id.giv_image, listBean.getImage()).setViewOnClick(R.id.giv_image, new DialogUtils.DialogUtilsListener() {
                       @Override
                       public void viewsListener() {


                            if (listBean.getType() != 5) {
                                switch (listBean.getType()) {
                                    case 1:    //商品
                                        GoodsDetailActivity.startActivity(getBaseActivity(), Integer.valueOf(listBean.getAdsInfo()));
                                        break;
                                    case 2: //店铺
                                        ShopDetailInformationActivity.startActivity(getBaseActivity(), Integer.valueOf(listBean.getAdsInfo()));
                                        break;
                                    case 3:  //活动专题  促销活动
                                        Intent intent = new Intent(getBaseActivity(), SpecialOfferActivity.class);
                                        intent.putExtra("entity_id", Integer.valueOf(listBean.getAdsInfo()));
                                        intent.putExtra("type", 3);
                                        startActivity(intent);
                                        break;
                                    case 4:  //h5专题

                                        String url = "";
                                        if (!TextUtils.isEmpty(listBean.getAdsInfo()) && listBean.getAdsInfo().contains("islogin=true")) {

                                            if (LoginMethod.notLoginChecked()) {
                                                LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                                                return;
                                            }

                                            if (PublicCache.loginPurchase != null) {

                                                String sstr = MD5AndSHA.md5Encode("tdj" + PublicCache.loginPurchase.getEntityId());

                                                if (PublicCache.loginPurchase.getFlag() == 1) {//主账号
                                                    url = listBean.getAdsInfo() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getEntityId();
                                                } else {//子账号
                                                    url = listBean.getAdsInfo() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getSubUserId();
                                                }
                                                url += "&sign=" + sstr;
                                            } else if (listBean.getAdsInfo().contains("activityfor=customer") && PublicCache.loginSupplier != null) {
                                                return;
                                            }

                                        } else url = listBean.getAdsInfo();

                                        Intent intent1 = new Intent(getBaseActivity(), SpecialActivitiesActivity.class);
                                        intent1.putExtra("url", url);
                                        startActivity(intent1);

                                        break;
                                }
                            }
                        }
                    }).setNegativeButton("", null).show();
                    tipsData();
                }

            }
        });
    }


    @Override
    protected void onDestroy() {

        ShortcutBadger.applyCount(UIUtils.getContext(), PublicCache.notifycationCount);

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();

//      android.os.Process.killProcess(android.os.Process.myPid());  //获取PID
    }


}


