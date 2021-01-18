package com.base.viewModel.adapter;


import java.util.List;

/**
 * Created by yangkuo on 2018-08-24.
 */
public abstract class SingleRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    //初始化
    public void setList(List lst, boolean... toTop) {
        boolean top = toTop.length > 0 && toTop[0];

        if (lst == null || lst.size() == 0) {
            clear();
        } else if (getRealCount() == 0) {
            notifyDataSetChanged(lst);
            if (top) setTopPosition(0);
        } else {
            rangeUpdate(lst, false);
            if (top) setTopPosition(0);
        }
    }

    public int getPageNO(int position, int ps) {
        if (ps == 0) ps = 1;
        int posr = position - getHeaderCount();
        return posr / ps + 1;
    }

    public int getPageStartPos(int ppn, int ps) {
        return (ppn - 1) * ps;
    }

    public void notifyDataSetChanged(List lst) {
        if (lst == null || lst.size() == 0) {
            remove(getFirstPosition(), getLastPosition());
        } else {
            list.subList(getFirstPosition(), getLastPosition() + 1).clear();
            list.addAll(getFirstPosition(), lst);
            notifyDataSetChanged();
        }
    }
}
