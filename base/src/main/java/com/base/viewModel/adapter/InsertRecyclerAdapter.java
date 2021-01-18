package com.base.viewModel.adapter;


import android.support.v4.util.SparseArrayCompat;
import android.view.View;

/**
 * Created by yangkuo on 2018-03-10.
 */

public abstract class InsertRecyclerAdapter extends SingleRecyclerViewAdapter {

    private static final int TYPE_FOOTER = 300000;//中间插入的项

    //决定插入的位置
    public abstract int concludeInsertPosition(int index);

    //存储插入的View
    protected SparseArrayCompat<View> mInsertViews = new SparseArrayCompat<>();


    @Override
    protected boolean isFullSpanType(int type) {
        return isInsertType(type) || super.isFullSpanType(type);
    }


    private boolean isInsertType(int viewType) {
        return mInsertViews.indexOfKey(viewType) > -1;
    }

    public static class Insert {
        public int type;

        public Insert(int type) {
            this.type = type;
        }
    }


}
