package cn.com.taodaji.viewModel.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stylingandroid.prism.viewpager.ColorProvider;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.common.Constants;
import tools.fragment.DataBaseFragment;


import com.base.utils.UIUtils;

public class ManageActivityPagerAdapter extends FragmentPagerAdapter implements ColorProvider {
    List<? extends DataBaseFragment> fmList;
    FragmentManager fm;

    private Map<String, Integer> map = new HashMap<>();


    public ManageActivityPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public int getColor(int position) {
        if (position == fmList.size() - 1) {
            if (PublicCache.login_mode.equals(Constants.SUPPLIER)) {
                return UIUtils.getColor(R.color.blue_2898eb);
            }
        }
        return UIUtils.getColor(R.color.orange_yellow_ff7d01);
    }

    @Override
    public int getCount() {
        return fmList == null ? 0 : fmList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fmList.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= fmList.size()) return fmList.get(0);
//        return ColourFragment.newInstance(context, getPageTitle(position), colour.getColour());
        return fmList.get(position);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        if (map.get(object.getClass().getSimpleName()) != null) {
            map.remove(object.getClass().getSimpleName());
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void setFragments(@NonNull List<? extends DataBaseFragment> fragments) {
        List list = fm.getFragments();
        if (fragments.size() == list.size()) {
            map.clear();
            for (int i = 0; i < fragments.size(); i++) {
                if (fragments.get(i) != list.get(i)) {
                    map.put(list.get(i).getClass().getSimpleName(), i);
                }
            }
        }
        this.fmList = fragments;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return fmList.get(position).hashCode();
    }
}