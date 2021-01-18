package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.utils.BitmapUtil;
import com.base.utils.DensityUtil;
import com.base.utils.ViewUtils;

public class RecyclerViewPPW extends PopupWindow {

    public RecyclerViewPPW(Context context, View onclickView, SingleRecyclerViewAdapter customerSimpleRecyclerViewAdapter, int... hightDip) {
        View view = ViewUtils.getLayoutView(context, R.layout.ppw_recycler_view);
        setContentView(view);
        setWidth(onclickView.getMeasuredWidth());
        setHeight(hightDip.length == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : DensityUtil.dp2px(hightDip[0]));

        setFocusable(true);
        setOutsideTouchable(true);
        //防止虚拟软键盘被弹出菜单遮住
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setBackgroundDrawable(BitmapUtil.getColorDrawable(R.color.gray_db));
        RecyclerView recyclerView = ViewUtils.findViewById(view, R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(5, R.color.gray_f2));
        recyclerView.setAdapter(customerSimpleRecyclerViewAdapter);
    }


}
