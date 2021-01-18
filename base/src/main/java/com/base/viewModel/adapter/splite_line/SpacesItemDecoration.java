package com.base.viewModel.adapter.splite_line;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.viewModel.adapter.BaseRecyclerViewAdapter;


public class SpacesItemDecoration extends FixedItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        super();
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        boolean isFixedHeader = false;
        if (parent.getAdapter() instanceof BaseRecyclerViewAdapter) {
            BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) parent.getAdapter();
            isFixedHeader = adapter.isFixedHeader();
        }
        outRect.right = space;
        outRect.bottom = space;
        if (isHorizontal(parent)) {
            if (isFixedHeader) outRect.left = 0;
            else outRect.left = space;
            outRect.top = space;
        } else {
            if (isFixedHeader) outRect.top = 0;
            else outRect.top = space;

            outRect.left = space;
        }

    }
}
