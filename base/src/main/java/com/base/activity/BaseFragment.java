package com.base.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.base.R;
import com.base.listener.OnPermissionListener;
import com.base.retrofit.HttpRequestManage;
import com.base.utils.ViewUtils;
import com.base.viewModel.DataBaseViewModel;
import com.base.viewModel.DataLoadingListener;


/**
 * initView() 实现了复用,不用每次都重新加载视图
 */
public abstract class BaseFragment extends Fragment implements DataLoadingListener {



    /*   onAttach方法：Fragment和Activity建立关联的时候调用。
    onCreate 方法 创建该fragment类似于Activity.onCreate，可以在其中初始化除了view之外的东西
    onCreateView方法：为Fragment加载布局时调用。
    onActivityCreated方法：当Activity中的onCreate方法执行完后调用。
    onDestroyView方法：Fragment中的布局被移除时调用。
    onDetach方法：Fragment和Activity解除关联的时候调用。*/

    /**
     * 复用rootView,不用每次都重新加载视图
     * FragmentTabHost切换Fragment时避免重复加载UI
     */
    protected View mRootView;
    private String title;

    private LinearLayout base_body, ll_base_loading;
    public LinearLayout ll_title;//toolbar

    public DataBaseViewModel viewModel;//用于交互和请求数据的vm
    private View base_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//在fragment中使用toolbar的menu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.activity_base, container, false);

            ll_title = mRootView.findViewById(R.id.title);//toolbar
            View toolbar = initToolbar();
            if (toolbar != null) {
                ll_title.addView(toolbar);
                BaseActivity aac = ((BaseActivity) getActivity());

                if (aac != null) {
                    Toolbar toolbars = ViewUtils.findViewById(toolbar, R.id.toolbar);
                    aac.setSupportActionBar(toolbars);
                    if (aac.getSupportActionBar() != null)
                        aac.getSupportActionBar().setDisplayShowTitleEnabled(false);
                }

                titleSetting(toolbar);
            }

            base_body = mRootView.findViewById(R.id.base_body);//显示的正文

            ll_base_loading = mRootView.findViewById(R.id.ll_base_loading);
            //添加正文
            base_content = initView(inflater, container, savedInstanceState);
            base_body.addView(base_content);
            viewModel = setViewModel();
            initView(base_content);
        } else {
            // 防止重复加载，出现闪退
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    /**
     * 初始化工具栏
     */
    protected View initToolbar() {
        return null;
    }

    protected void titleSetting(View toolbar) {

    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void initView(View mainView) {

    }

    protected DataBaseViewModel setViewModel() {
        return new DataBaseViewModel(this);
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
     * 是否正在加载数据
     *
     * @return
     */
    public boolean isLoading() {
        if (viewModel == null) return false;
        return viewModel.isLoading();
    }

    /**
     * 开始加载数据
     */
    public void onStartLoading() {
        if (viewModel != null) viewModel.onStartLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModel != null) viewModel.onDestroy();
        viewModel = null;
    }

    /**
     * 清楚所有的请求列表
     */
    public void loadingClear() {
        HttpRequestManage.clear(this.hashCode());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (viewModel != null && viewModel.onKeyDown(keyCode, event)) {
            return true;
        }
        return false;
    }


    /**
     * 根view
     *
     * @return
     */
    public View getRootView() {
        return mRootView;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void loadingShow(String... message) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity bta = (BaseActivity) getActivity();
            bta.loading(message);
        }
    }

    public void loadingDimss() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity bta = (BaseActivity) getActivity();
            bta.loadingDimss();
        }
    }



    /**
     * 得到根Fragment
     *
     * @
     */
    public Fragment getRootFragment(Fragment fragment) {

        Fragment frag = fragment.getParentFragment();
        if (frag == null) return fragment;
        else return getRootFragment(frag);
    }

    //判断fragment是否显示在了用户面前
    public boolean isVisibleToUser(Fragment fragment) {
        Fragment frag = fragment.getParentFragment();
        if (fragment.getUserVisibleHint() && isVisible() && frag != null) {
            return frag.getUserVisibleHint() && frag.isVisible() && isVisibleToUser(frag);
        } else {
            return fragment.getUserVisibleHint() && fragment.isVisible();
        }
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> frags = getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) f.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    //判断是否是RootFragment
    private boolean isRootFragment() {
        Fragment fragment = getParentFragment();
        return fragment == null;
    }

    //获取当前显示的fragment  ps:被覆盖的也会显示
    public List<Fragment> getVisibleFragments() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
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
        List<Fragment> frags = fragment.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) {
                    getAllChildFrament(list, f);
                }
            }
        }
    }

    public List<Fragment> getChildFrament() {
        return getChildFragmentManager().getFragments();
    }

    //获取当前显示在第一位的fragment ps:被覆盖的不会显示
    public List<Fragment> getFirstVisibleFragments() {
        List<Fragment> list = new ArrayList<>();
        if (!isAdded()) return list;
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) return list;
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible() && fragment.getUserVisibleHint()) {
                getAllChildFrament(list, fragment);
            }
        }
        return list;
    }


    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void permissionRequest(int requestCode, String... permissions) {
        getBaseActivity().permissionRequest(requestCode, permissions);
    }

    /**
     * 添加请求权限监听
     */
    public void addPermissionListen(int requestCode, OnPermissionListener onPermissionListener) {
        getBaseActivity().addPermissionListen(requestCode, onPermissionListener);
    }
}
