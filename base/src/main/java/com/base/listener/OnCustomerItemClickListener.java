package com.base.listener;

import android.view.View;


public interface OnCustomerItemClickListener<T> {
    void onCustomerItemClick(View view, int position, T bean);
}
