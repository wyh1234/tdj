package com.base.viewModel.adapter;

import android.view.View;

public interface OnItemClickListener
{
	//onclickType 0 点击事件，1 长按事件 ， 2，触摸事件 按下去就触发
	boolean onItemClick(View view, int onclickType, int position, Object bean);
}
