package cn.com.taodaji.ui.activity.linkPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.R;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.DuiBaRegisterUrl;
import cn.com.taodaji.model.entity.FindByIsActive;

import com.apkfuns.logutils.LogUtils;
import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.model.entity.SpecialMerchants;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;

import com.base.utils.BitmapUtil;
import com.base.utils.JavaMethod;
import com.base.utils.MD5AndSHA;
import com.base.utils.NotificationsUtils;
import com.base.utils.SystemUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.orm.SugarContext;
import com.orm.SugarRecord;

import cn.com.taodaji.common.Constants;


import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialOfferActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.tdjc.ServiceMoneyPopuWindow;
import tools.activity.DataBaseActivity;

import com.base.utils.UIUtils;

import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能：使用ViewPager实现初次进入应用时的引导页
 * <p/>
 * (1)判断是否是首次加载应用--采取读取SharedPreferences的方法 (2)是，则进入引导activity；否，则进入MainActivity
 * (3)2s后执行(2)操作
 */
@SuppressLint("HandlerLeak")
public class StartActivity extends DataBaseActivity implements Constants, AppAgreementPopuWindow.AppAgreementPopuWindowListener {


    private SharedPreferences sp;


//    private boolean isLogin = true;//是否登录

    private SpecialMerchants.ListBean listBean;

    private TextView tv_skip;
    private GlideImageView giv_image;
    private int time = 3;
    private ImageView iv;
    private boolean onClick = false;//就否执行了点击事件
    private AppAgreementPopuWindow appAgreementPopuWindow;
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_start);
    }

    @Override
    protected void initView() {
        setStatusBarForeColorColor(false);
        //数据库初始化
        SugarContext.init(UIUtils.getContext());

        tv_skip = findViewById(R.id.tv_skip);
        giv_image = findViewById(R.id.giv_image);
        iv = findViewById(R.id.iv);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean mFirst = isFirstEnter(getBaseActivity(), getClass().getName());
                if (mFirst) tokeNext();
                else toMainActivity();
                onClick = true;
            }
        });
        String str = UIUtils.getSharedPreferences("StartActivity").getString("start", "");
        if (!TextUtils.isEmpty(str)) {
            listBean = JavaMethod.getJsonBean(str, SpecialMerchants.ListBean.class);
        }

        if (listBean != null) {
//            giv_image.loadImage(listBean.getImage(), false);
            iv.setVisibility(View.GONE);
            Glide.with(UIUtils.getContext()).asBitmap().load(listBean.getImage()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                    getBaseContentView().setBackground(BitmapUtil.getDrawable(resource));
                }
            });
            giv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listBean.getType() != 5) {
                        onClick = true;
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
            });

            tv_skip.postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    tv_skip.setText("跳过(" + time + ")");
                    if (time == 0) {
                        toMainActivity();
                    } else tv_skip.postDelayed(this, 1000);
                }
            }, 1000);
        } else {
            iv.setVisibility(View.VISIBLE);
            tv_skip.setVisibility(View.GONE);
            tv_skip.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean mFirst = isFirstEnter(getBaseActivity(), getClass().getName());
                    if (mFirst) tokeNext();
                    else toMainActivity();
                }
            }, 3000);
        }
    }

    @Override
    protected void initData() {
        //获取已开启城市信息
        dataInit();
        getCitysite();
        intiMessage();
        SystemUtils.clearApk("taodaji.apk");
    }

    //获取已开启城市信息
    public void getCitysite() {
        RequestPresenter.getInstance().findByIsActive(3, new ResultInfoCallback<FindByIsActive>() {
            @Override
            public void onSuccess(FindByIsActive body) {
                PublicCache.findByIsActive = body;
            }

            @Override
            public void onFailed(int findByIsActiveResultInfo, String msg) {
                Log.e("msg", msg);
            }
        });
    }


    public void intiMessage() {
        sp = getSharedPreferences("is_open_news", 0);
        boolean bb = NotificationsUtils.isNotificationOpen(getBaseActivity());

        // boolean order_message = sp.getBoolean("order_message", true);
        //boolean message = sp.getBoolean("message", true);

        PushAgent mPushAgent = PushAgent.getInstance(this);


        if (bb) {
            mPushAgent.enable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("message", true);
                    //提交edit
                    edit.apply();
                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        } else {
            //点击开关取消推送   注销回调：IUmengCallback；，则需要调用以下代码（请在Activity内调用）：
            mPushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("message", false);
                    //提交edit
                    edit.apply();
                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        }


    }


    // ****************************************************************
    // 判断应用是否初次加载，读取SharedPreferences中的guide_activity字段
    // ****************************************************************
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
//    private ApkUpadteAutoUitl autoUitl;

    @SuppressLint("WorldReadableFiles")
    private boolean isFirstEnter(Context context, String className) {
        if (context == null || className == null || "".equalsIgnoreCase(className)) return false;
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).getString(KEY_GUIDE_ACTIVITY, "");
        return !mResultStr.equalsIgnoreCase("false");
    }

    //数据初始化
    public void dataInit() {
        LoginPurchaseBean loginPurchase = SugarRecord.first(LoginPurchaseBean.class);
        LoginSupplierBean loginSupplier = LoginSupplierBean.first(LoginSupplierBean.class);
        if (loginPurchase != null) {
            //已登录  初始化数据
            PublicCache.loginPurchase = loginPurchase;
            PublicCache.login_mode = PURCHASER;

            PublicCache.site = PublicCache.loginPurchase.getSite();
            PublicCache.site_name = PublicCache.loginPurchase.getSiteName();
            PublicCache.site_login = PublicCache.site;
            PublicCache.site_name_login = PublicCache.site_name;
            int en;
            if (loginPurchase.getFlag() == 1) en = loginPurchase.getEntityId();
            else en = loginPurchase.getSubUserId();
            PublicCache.userNameId = en;

        } else if (loginSupplier != null) {
            //已登录  初始化数据
            PublicCache.loginSupplier = loginSupplier;
            PublicCache.login_mode = SUPPLIER;

            PublicCache.site = PublicCache.loginSupplier.getSite();
            PublicCache.site_name = PublicCache.loginSupplier.getSiteName();
            PublicCache.site_login = PublicCache.site;
            PublicCache.site_name_login = PublicCache.site_name;
            PublicCache.userNameId = loginSupplier.getEntityId();

        } else {
            PublicCache.login_mode = PURCHASER;
            PublicCache.site_name = DEFAULT_siteName;
            PublicCache.site = DEFAULT_site;
            PublicCache.site_login = PublicCache.site;
            PublicCache.site_name_login = PublicCache.site_name;
        }
        PublicCache.notifycationCount = 0;

    }


    private void toMainActivity() {
        if (onClick) {
            finish();
        } else {
            if (getIntent().getData()!=null){
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                    return;
                }
                Map<String ,Object> haspmap=new HashMap<>();
                haspmap.put("dbredirect",getIntent().getData().getQueryParameter("redirect_uri"));
                haspmap.put("customerId",PublicCache.loginPurchase.getEntityId());

                getIntegralRequestPresenter().getShareDuibaRegisterUrl(haspmap, new RequestCallback<DuiBaRegisterUrl>() {
                    @Override
                    public void onSuc(DuiBaRegisterUrl body) {
                        Intent intent1 = new Intent(StartActivity.this, SpecialActivitiesActivity.class);
                        intent1.putExtra("url",  body.getData());
                        startActivity(intent1);
                        finish();
                    }


                    @Override
                    public void onFailed(int findByIsActiveResultInfo, String msg) {
                        Log.e("msg", msg);
                    }
                });

            }else {
                Intent mIntent = new Intent();
                mIntent.setClass(this, ManageActivity.class);
                startActivity(mIntent);
                finish();
            }

        }

    }


    private void tokeNext() {
        //接口已返回，等待时间结束，不需要更新
        if (onClick) {
            finish();
        } else {
            if (appAgreementPopuWindow!=null){
                if (appAgreementPopuWindow.isShowing()){
                    return;
                }

            }
            appAgreementPopuWindow = new AppAgreementPopuWindow(this);
            appAgreementPopuWindow.setPopupWindowFullScreen(true);//铺满
            appAgreementPopuWindow.setAppAgreementPopuWindowListener(this);
            appAgreementPopuWindow.showPopupWindow();

        }
    }


    //两次返回键退出程序
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                UIUtils.showToastSafesShort("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void ok() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, GuideActivity.class);
        StartActivity.this.startActivity(mIntent);
        StartActivity.this.finish();
    }

    @Override
    public void cancel() {
        finish();
    }
}
