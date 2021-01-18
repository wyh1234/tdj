package cn.com.taodaji.ui.activity.penalty.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cn.com.taodaji.ui.activity.penalty.fragment.PunishListFragment;

public class PunishListFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public PunishListFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return PunishListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
