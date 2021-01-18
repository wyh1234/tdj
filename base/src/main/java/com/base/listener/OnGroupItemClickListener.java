package com.base.listener;

import android.view.View;

/**
 * Created by aspsine on 16/8/9.
 */

public interface OnGroupItemClickListener<G> {
    void onGroupItemClick(View view, int groupIndex, G bean);
}
