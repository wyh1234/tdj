package com.base.activity;

import android.annotation.SuppressLint;
import android.app.backup.BackupAgent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.base.R;
import com.base.listener.OnPermissionListener;
import com.base.retrofit.HttpRequestManage;
import com.base.utils.JavaMethod;
import com.base.utils.PermissionAuthorityUtils;
import com.base.utils.StatusBarUtil;
import com.base.utils.SystemUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.DataBaseViewModel;
import com.base.viewModel.DataLoadingListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 类说明
 * 兼容碎片的基类，大多类都继承于该类，
 * 实现了Constant这个常量 可以在类中直接使用这些常量
 * 收集了一些获取添加到该类的碎片的列表方法
 */

public abstract class BaseActivity extends SharedElementActivity implements DataLoadingListener {

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    protected abstract View getContentLayout();

    /**
     * 初始化视图
     */
    protected abstract void initView();


    /**
     * 初始化数据
     */
    protected void initData() {
        if (viewModel != null) viewModel.initData();
    }

    private AlertDialog alertDialog;
        private AlertDialog alertDialogUnable;//不能手动取消

    //申请处理权限的工具类
    private PermissionAuthorityUtils permissionAuthorityUtils;
    public DataBaseViewModel viewModel;//用于交互和请求数据的vm

    protected DataBaseViewModel setViewModel() {
        return new DataBaseViewModel(this);
    }

    public boolean isScreenVertical = true;//是否竖屏

    private LinearLayout base_body, ll_base_loading;
    public LinearLayout title;//toolbar
    private View base_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.addActivity(this, getClass());
       // initWindow();

//        SystemUtils.setScreenVertical(this);

        //设为竖屏
        try {
            if (isScreenVertical) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (IllegalStateException e) {
// Only fullscreen activities can request orientation
            e.printStackTrace();
        }


       // ImmersionBar.with(this).init();//  .hideBar(BarHide.FLAG_HIDE_BAR)
        setContentView(R.layout.activity_base);
            title = findViewById(R.id.title);//toolbar
        base_body = findViewById(R.id.base_body);//显示的正文
        ll_base_loading = findViewById(R.id.ll_base_loading);
        //添加正文
        base_content = getContentLayout();
        base_body.addView(base_content);
        viewModel = setViewModel();

        initView();//初始化视图
        initData();//初始化数据

    }


    private void initWindow(){
    /*    boolean g=NavigationBarUtil.hasNavigationBar(this);
        Log.e("tempStutas",g+"");
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
*/
        //隐藏虚拟按键
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions =View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  /* | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;*/
 /*           View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
           // View.SYSTEM_UI_FLAG_IMMERSIVE
                  //  |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                  //  | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR*/
            ;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    @Override
    protected void onDestroy() {
        if (viewModel != null) viewModel.onDestroy();
        viewModel = null;
        ActivityManage.removeActivity(this);
        loadingDimss();
        loadingDimssUnable();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewModel != null) viewModel.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 正文
     *
     * @return
     */
    public View getBaseContentView() {
        return base_content;
    }

    public LinearLayout ll_base_loading() {
        return ll_base_loading;
    }

    /**
     * 单个请求返回时回调该方法
     */
    public void requestReturnUniform() {
        if (viewModel != null) viewModel.requestReturnUniform();
    }

    /**
     * 判断是否正在加载数据
     *
     * @return
     */
    public boolean isLoading() {
        if (viewModel != null) return viewModel.isLoading();
        return false;
    }


    /**
     * Activity正在加载数据
     */
    public void onStartLoading() {
        if (viewModel != null) viewModel.onStartLoading();
    }

    /**
     * 清楚所有的请求列表
     */
    public void loadingClear() {
        HttpRequestManage.clear(this.hashCode());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (viewModel != null) {
            if (viewModel.onKeyDown(keyCode, event)) return true;
            else {
                BaseFragment baseFragment = isFragmentIsLoading();
                if (baseFragment != null) {
                    if (baseFragment.onKeyDown(keyCode, event)) return true;
                }
            }
        }


        return super.onKeyDown(keyCode, event);
    }


    /**
     *
     */
    private BaseFragment isFragmentIsLoading() {
        List<Fragment> list = getFirstVisibleFragments();
        if (!list.isEmpty()) {
            for (Fragment fragment : list) {
                if (fragment instanceof BaseFragment) {
                    BaseFragment baseFragment = (BaseFragment) fragment;
                    if (baseFragment.isLoading()) {
                        Log.e("loading", this.getClass().getSimpleName() + "   " + baseFragment.getClass().getSimpleName() + "  isLoading true");
                        return baseFragment;
                    }
                }
            }
        }
        return null;
    }

    public View getRootView() {
        return getWindow().getDecorView();
    }

    public BaseActivity getBaseActivity() {
        return this;
    }

    public void loading(String... message) {

        if (isFinishing() || isDestroyed()) return;

        TextView help_message = null;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
//            builder.setOnKeyListener(keylistener);
            View view = ViewUtils.getLayoutView(this, R.layout.popup_window_loading);
            help_message = ViewUtils.findViewById(view, R.id.help_message);
            builder.setView(view);
            alertDialog = builder.create();
            if (alertDialog.getWindow() != null) alertDialog.getWindow().setDimAmount(0);//设置昏暗度为0
        }
        if (message.length > 0 && help_message != null) {
            help_message.setText(message[0]);
        }
        if (isFinishing() || isDestroyed()) return;
        if (!alertDialog.isShowing()) alertDialog.show();
    }

    public void loadingUnable(String... message) {

        if (isFinishing() || isDestroyed()) return;

        TextView help_message = null;
        if (alertDialogUnable == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
//            builder.setOnKeyListener(keylistener);
            View view = ViewUtils.getLayoutView(this, R.layout.popup_window_loading);
            help_message = ViewUtils.findViewById(view, R.id.help_message);
            builder.setView(view);
            alertDialogUnable = builder.create();
            if (alertDialogUnable.getWindow() != null) alertDialogUnable.getWindow().setDimAmount(0);//设置昏暗度为0
        }
        if (message.length > 0 && help_message != null) {
            help_message.setText(message[0]);
        }
        if (isFinishing() || isDestroyed()) return;
        if (!alertDialogUnable.isShowing()) alertDialogUnable.show();
    }
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //当进度条显示时
                if (dialog != null && alertDialog.isShowing()) {
                    //取消还未返回的网络请求
//                    requestCancel();
//                    alertDialog.dismiss();
                    return true;
                }
            }

            return false;
        }
    };

    public void setLoadingCancelable(boolean cancelable) {
        if (alertDialog != null) {
            alertDialog.setCancelable(cancelable);
        }
    }

    public void loadingDimss() {
//        if (isFinishing() || isDestroyed()) return;
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
    }

    public void loadingDimssUnable() {
//        if (isFinishing() || isDestroyed()) return;
        if (alertDialogUnable != null && alertDialogUnable.isShowing()) alertDialogUnable.dismiss();
    }

    // 空出statusBar的高度
    public void setImmersiveStatusBarToolbar(@NonNull Toolbar toolbar) {
        ViewGroup.MarginLayoutParams toolLayoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        toolLayoutParams.height = StatusBarUtil.getStatusBarHeight() + StatusBarUtil.getActionBarSize(this);
        toolbar.setLayoutParams(toolLayoutParams);
        toolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(), 0, 0);
        toolbar.requestLayout();

    }


    // 空出NavigationBar的高度
    public void setImmersiveNavigationBar() {
        getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, StatusBarUtil.getNavigationBarHeight(this));
    }

    // 动态添加布局(xml方式)
    public <T extends View> T getLayoutView(@LayoutRes int layoutResId) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        T view = (T) LayoutInflater.from(this).inflate(layoutResId, null);
        view.setLayoutParams(lp);
        return view;
    }


    // 隐藏软键盘
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    // 获取点击事件  点击空白隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean showInputMethod(View focus) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {

            Object mService = JavaMethod.getFieldValue(imm, "mService");
            Object mClient = JavaMethod.getFieldValue(imm, "mClient");
            Class<?> clientClass = ClassLoader.getSystemClassLoader().loadClass("com.android.internal.view.IInputMethodClient");
            Method showSoftInput = JavaMethod.getMethod(mService, "showSoftInput", clientClass, int.class, ResultReceiver.class);
            return (Boolean) showSoftInput.invoke(mService, mClient, 0, null);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (focus == null) {
            focus = getWindow().getCurrentFocus();
        }
        return imm.showSoftInput(focus, 0);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    //获取当前显示的fragment  ps:被覆盖的也会显示
    public List<Fragment> getVisibleFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        List<Fragment> list = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                getAllChildFrament(list, fragment);
            }
        }
        return list;
    }

    //递归调用找出所有的Fragment
    public void getAllChildFrament(List<Fragment> list, Fragment fragment) {
        list.add(fragment);
        @SuppressLint("RestrictedApi") List<Fragment> frags = fragment.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) {
                    getAllChildFrament(list, f);
                }
            }
        }
    }

    //获取当前显示在第一位的fragment ps:被覆盖的不会显示
    public List<Fragment> getFirstVisibleFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) return null;
        List<Fragment> list = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible() && fragment.getUserVisibleHint()) {
                getAllChildFrament(list, fragment);
            }
        }
        return list;
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();

        //config.setToDefaults();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 添加请求权限监听
     */
    public void addPermissionListen(int requestCode, OnPermissionListener onPermissionListener) {
        if (permissionAuthorityUtils == null)
            permissionAuthorityUtils = new PermissionAuthorityUtils(this);
        permissionAuthorityUtils.addPermissionListen(requestCode, onPermissionListener);
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void permissionRequest(int requestCode, String... permissions) {
        if (permissionAuthorityUtils == null)
            permissionAuthorityUtils = new PermissionAuthorityUtils(this);
        permissionAuthorityUtils.permissionRequest(requestCode, permissions);
    }

    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionAuthorityUtils != null)
            permissionAuthorityUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 启动当前应用设置页面
     */
    public void startAppSettings() {
        SystemUtils.startManageApplicationInfo(this);
    }
}
