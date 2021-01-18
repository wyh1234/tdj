package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;
import tools.fragment.DataBaseFragment;

/**
 * Created by yangkuo on 2019/1/11.
 */
public class MyselfFragment extends DataBaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }


    @Override
    public void initData() {
        super.initData();
        if (PublicCache.loginSupplier != null){
            if (myselfFragmentSup.messagePopuWindow!=null){
                myselfFragmentSup.messagePopuWindow.showPopupWindow();
            }

        }
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (PublicCache.loginSupplier != null){
            if (myselfFragmentSup.messagePopuWindow!=null){
                myselfFragmentSup.messagePopuWindow.showPopupWindow();
            }

        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ViewUtils.getFragmentView(container, R.layout.fragment_myself);
    }

    @Override
    public void initView(View mainView) {
        super.initView(mainView);
        checkMyselfFragment();
    }

    private MyselfFragmentNewSup myselfFragmentSup;
    private MyselfFragmentPur myselfFragmentPur;

    private void checkMyselfFragment() {
        FragmentTransaction fa = getChildFragmentManager().beginTransaction();


        List<Fragment> list = getChildFrament();

        if (PublicCache.loginSupplier != null) {
            if (list.size() == 0) {
                if (myselfFragmentSup == null) myselfFragmentSup = new MyselfFragmentNewSup();
                fa.add(R.id.fm_fragment, myselfFragmentSup, SUPPLIER);
                fa.commit();
            } else {
                if (list.get(0) instanceof MyselfFragmentPur) {
                    if (myselfFragmentSup == null) myselfFragmentSup = new MyselfFragmentNewSup();
//                    fa.detach(list.get(0));
//                    myselfFragmentSup.login_in();
                    fa.replace(R.id.fm_fragment, myselfFragmentSup, SUPPLIER);
                    fa.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fa.commitNowAllowingStateLoss();
                } else {
                    MyselfFragmentNewSup myselfFragmentSup = (MyselfFragmentNewSup) list.get(0);
                    if (myselfFragmentSup != null) {
                        myselfFragmentSup.login_in();
                    }
                }
            }
        } else {
            if (list.size() == 0) {
                if (myselfFragmentPur == null) myselfFragmentPur = new MyselfFragmentPur();
                fa.add(R.id.fm_fragment, myselfFragmentPur, PURCHASER);
                fa.commit();
            } else {
                if (list.get(0) instanceof MyselfFragmentNewSup) {
                    if (myselfFragmentPur == null) myselfFragmentPur = new MyselfFragmentPur();
//                    fa.detach(list.get(0));
//                    myselfFragmentPur.login_in();
                    fa.replace(R.id.fm_fragment, myselfFragmentPur, PURCHASER);
                    fa.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fa.commitNowAllowingStateLoss();
                } else {
                    MyselfFragmentPur myselfFragmentPur = (MyselfFragmentPur) list.get(0);
                    if (myselfFragmentPur != null) {
                        if (PublicCache.loginPurchase != null) {
                            myselfFragmentPur.login_in();
                        } else {
                            myselfFragmentPur.login_out();
                        }
                    }
                }

            }
        }

    }

    //登录
    @Subscribe
    public void update(Login_in login_in) {
        checkMyselfFragment();
    }

    //退出登录
    @Subscribe
    public void onEvent(Login_out login_out) {
        checkMyselfFragment();
    }


}
