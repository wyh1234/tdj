package cn.com.taodaji.model;

import android.content.Intent;

import android.util.Log;
import android.view.Gravity;

import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.PurchaseBean;
import cn.com.taodaji.model.entity.PushMessageCustomerToken;
import cn.com.taodaji.model.entity.SupplierBean;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.login.ChooseLoginActivity;
import cn.com.taodaji.ui.activity.login.ForgetPasswordActivity;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;

import java.util.Map;

import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import cn.com.taodaji.ui.ppw.PopupResultInterface;
import cn.com.taodaji.ui.ppw.SubAccountPopuWindow;

import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.retrofit.cookie.PersistentCookieStore;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;

import com.base.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class LoginMethod implements Constants {

    private BaseActivity activity;

    private LoginMethod(BaseActivity act) {
        activity = act;
    }

    public static LoginMethod getInstance(BaseActivity act) {
        return new LoginMethod(act);
    }

    //  login_type 0密码登录  1验证码登录
    public void login(Map<String, Object> map_login, String login_mode) {
        map_login.put("uniqueId", SystemUtils.getAndroidId());
        map_login.put("sourceType", "android");
        map_login.put("isShowC", 0);
        switch (login_mode) {
            case PURCHASER:
                RequestPresenter.getInstance().loginData_purchase(map_login, new RequestCallback<PurchaseBean>(activity) {
                    @Override
                    public void onSuc(PurchaseBean body) {
                        if (activity != null && !activity.isFinishing()) {
                            activity.loadingDimss();
                        }

                        PublicCache.site = body.getData().getSite();
                        PublicCache.site_name = body.getData().getSiteName();
                        PublicCache.site_login = PublicCache.site;
                        PublicCache.site_name_login = PublicCache.site_name;


                        int en;
                        if (body.getData().getFlag() == 1) en = body.getData().getEntityId();
                        else en = body.getData().getSubUserId();
                        PublicCache.userNameId = en;


                       /* if (body.getData().getFlag() == 2 && body.getData().getHasVerfify() == 0) {
                            SubAccountPopuWindow popuWindow = new SubAccountPopuWindow(activity, body.getData());
                            popuWindow.setResultInterface(new PopupResultInterface() {
                                @Override
                                public void setResult(Object object) {
                                    LoginSupplierBean.deleteAll(LoginSupplierBean.class);
                                    LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
                                    PublicCache.loginPurchase = body.getData();
                                    PublicCache.loginPurchase.save();
                                    PublicCache.login_mode = PURCHASER;
                                    sendLoginBroadcast();
                                }
                            });
                            popuWindow.showAtLocation(activity.getCurrentFocus(), Gravity.CENTER, 0, 0);
                        }
                        //初始化完成，发送登录广播
                        else {
                            LoginSupplierBean.deleteAll(LoginSupplierBean.class);
                            LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
                            PublicCache.loginPurchase = body.getData();
                            PublicCache.loginPurchase.save();
                            PublicCache.login_mode = PURCHASER;

                            sendLoginBroadcast();
                        }*/


                        LoginSupplierBean.deleteAll(LoginSupplierBean.class);
                        LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
                        PublicCache.loginPurchase = body.getData();
                        PublicCache.loginPurchase.save();
                        PublicCache.login_mode = PURCHASER;

                        sendLoginBroadcast();
                    }

                    @Override
                    public void onFailed(int purchaseBean, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        if (activity != null && !activity.isFinishing()) {
                            activity.loadingDimss();
                        }
                    }
                });
                break;
            case SUPPLIER:
                RequestPresenter.getInstance().loginData_supplier(map_login, new RequestCallback<SupplierBean>(activity) {
                    @Override
                    public void onSuc(SupplierBean body) {
                        if (activity != null && !activity.isFinishing()) {
                            activity.loadingDimss();
                        }

                        PublicCache.site = body.getData().getSite();
                        PublicCache.site_name = body.getData().getSiteName();
                        PublicCache.site_login = PublicCache.site;
                        PublicCache.site_name_login = PublicCache.site_name;
                        PublicCache.userNameId = body.getData().getEntityId();

                        LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
                        LoginSupplierBean.deleteAll(LoginSupplierBean.class);
                        body.getData().save();
                        PublicCache.loginSupplier = body.getData();
                        PublicCache.login_mode = SUPPLIER;
                        PublicCache.refreshId="";
                        //初始化完成，
                        sendLoginBroadcast();
                    }

                    @Override
                    public void onFailed(int supplierBean, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        if (activity != null && !activity.isFinishing()) {
                            activity.loadingDimss();
                        }
                    }
                });
                break;
        }
    }

    public void sendLoginBroadcast() {

        if (activity != null && !activity.isFinishing()) {
            activity.loadingDimss();
        }

        RequestPresenter.getInstance().pushMessageCustomer(PublicCache.deviceToken_umeng, new RequestCallback<PushMessageCustomerToken>() {
            @Override
            protected void onSuc(PushMessageCustomerToken body) {
                Log.d("token-customer ", "suc " + PublicCache.deviceToken_umeng);
            }

            @Override
            public void onFailed(int pushMessageCustomerToken, String msg) {
                Log.d("token-customer ", "err " + PublicCache.deviceToken_umeng);
            }
        });

        RequestPresenter.getInstance().pushMessageSupplier(PublicCache.deviceToken_umeng, new RequestCallback<PushMessageCustomerToken>() {
            @Override
            protected void onSuc(PushMessageCustomerToken body) {
                Log.d("token-supplier ", "suc " + PublicCache.deviceToken_umeng);
            }

            @Override
            public void onFailed(int pushMessageCustomerToken, String msg) {
                Log.d("token-supplier ", "err " + PublicCache.deviceToken_umeng);
            }
        });

        //初始化的数据
        ManageActivity activity = ActivityManage.getActivity(ManageActivity.class);
        if (activity != null) {
            activity.initializtionData();
        }

        EventBus.getDefault().post(new Login_in());
        //验证主页是否已存在于栈中  如果存在则是因退出登录或登录失效后弹出，
        if (ActivityManage.isActivityExist(ManageActivity.class)) {
            ActivityManage.setTopActivity(ManageActivity.class);
//            if (activity != null) activity.finish();//这里的Activity是登录页面的
        } else toMainActivity();
    }

    private void toMainActivity() {
        if (ActivityManage.isActivityExist(ManageActivity.class)) {
            activity.finish();//这里的Activity是登录页面的
            return;
        }
        //跳转到主页
        Intent intent = new Intent();
        intent.setClass(activity, ManageActivity.class);
        activity.startActivity(intent);
        activity.finish();//这里的Activity是登录页面的
    }


    public void toChooseLoginActivity() {
        loginOut();
        //跳转到选择登录页面
        Intent intent = new Intent();
        intent.setClass(activity, ChooseLoginActivity.class);
        activity.startActivity(intent);//这里的Activity是弹出登录的
    }

    //退出登录
    public static void loginOut() {
        /**  退出登录
         * 发送退出登录事件
         * 清空当前的用户表
         * 清空缓存的登录数据
         * 登录模式改为采购商登录,这样主题会变成采购商的橙色
         * 弹出选择登录选择页面
         * */


        //退出时城市切换为定位所在的城市，如果已开通则为当地，否则为襄阳
        PublicCache.site = DEFAULT_site;
        PublicCache.refreshId="";
        if (PublicCache.findByIsActive != null) {
            for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
                if (listBean.getName().equals(PublicCache.location_default)) {
                    PublicCache.site = listBean.getId();
                    break;
                }
            }
        }

        if (PublicCache.site != DEFAULT_site) PublicCache.site_name = PublicCache.location_default;
        else PublicCache.site_name = DEFAULT_siteName;


        PublicCache.site_login = PublicCache.site;
        PublicCache.site_name_login = PublicCache.site_name;

        new PersistentCookieStore(UIUtils.getContext()).removeAll();//清空cookies
        //清空初始化数据
        PublicCache.initializtionData = null;
        //初始化的数据
        ManageActivity activity = ActivityManage.getActivity(ManageActivity.class);
        if (activity != null) {
            activity.initializtionData();
        }

        RequestPresenter.getInstance().pushMessageLogout(new ResultInfoCallback<Object>(activity) {
            @Override
            public void onFailed(int objectResultInfo, String msg) {
                Log.d("token ", "out err ");
            }

            @Override
            public void onSuccess(Object body) {
                Log.d("token ", "out suc ");
            }
        });


        if (PublicCache.loginPurchase != null) {
            LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
            PublicCache.loginPurchase = null;
        } else if (PublicCache.loginSupplier != null) {
            LoginSupplierBean.deleteAll(LoginSupplierBean.class);
            PublicCache.loginSupplier = null;
        }

        EventBus.getDefault().post(new Login_out());
    }

    public void toLoginActivity() {
        loginOut();//先退出

        //置顶
        ActivityManage.setTopActivity(ManageActivity.class);

        //跳转到选择登录页面

        switch (PublicCache.login_mode) {
            case PURCHASER:{
                Intent intent = new Intent();
                intent.setClass(activity, LoginPurchaserActivity.class);
                intent.putExtra(FLAG, PublicCache.login_mode);
                activity.startActivity(intent);//这里的Activity是弹出登录的
                break;}
            case SUPPLIER:{
                Intent intent = new Intent();
                intent.setClass(activity, LoginActivity.class);
                intent.putExtra(FLAG, PublicCache.login_mode);
                activity.startActivity(intent);//这里的Activity是弹出登录的
                break;}
        }



    }


    public static boolean notLoginChecked() {
        return PublicCache.loginPurchase == null && PublicCache.loginSupplier == null;
    }

}
