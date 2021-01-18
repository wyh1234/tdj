package com.base.listener;

import android.view.View;

public interface OnChildItemClickListener<C> {
    void onChildItemClick(View view, int groupIndex, int childIndex, C bean);
}
