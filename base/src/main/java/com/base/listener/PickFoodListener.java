package com.base.listener;

/**
 * Created by Administrator on 2017/2/5 0005.
 */
public interface PickFoodListener {

    String sortString();

    String goodsName();

    int categoryId();

    int getIsP();//0零售，1事件批，-1全部

    int getIsCRITERIA();//商品标准“1”：通货商品 “2”：精品商品 '，-1全部


    int isCanteen();
}
