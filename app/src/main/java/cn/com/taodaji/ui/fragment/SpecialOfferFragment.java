package cn.com.taodaji.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.DensityUtil;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.model.entity.FindByActivitiesID;
import cn.com.taodaji.model.CartModel;

import com.base.retrofit.RequestCallback;

import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class SpecialOfferFragment extends LoadMoreRecyclerViewFragment {

    private SimpleGoodsInformationAdapter adapter;


    private GlideImageView imageView;


    private int entity_id;


    private int type;//7为宝鲜蔬  8为促销活动

    private View iv_shopping_cart;
    private TextView tv_shopping_count;

    public void setIv_shopping_cart(View iv_shopping_cart) {
        this.iv_shopping_cart = iv_shopping_cart;
    }

    public void setTv_shopping_count(TextView tv_shopping_count) {
        this.tv_shopping_count = tv_shopping_count;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);

        type = getActivity().getIntent().getIntExtra("type", 8);


        if (type == 3) {
            entity_id = getActivity().getIntent().getIntExtra("entity_id", 0);
        }

        if (type == 7) {
            SimpleToolbarActivity activity = (SimpleToolbarActivity) getActivity();
            activity.setTitle("宝鲜蔬 ");
        }


        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(getContext()));

        adapter = new SimpleGoodsInformationAdapter();
        imageView = ViewUtils.getImageView(getActivity());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        recycler_targetView.setAdapter(adapter);
        loadMoreUtil.setHasLoadMore(false);


        CartModel cartModel = CartModel.getInstance();
        if (tv_shopping_count != null)
            tv_shopping_count.setText(String.valueOf(cartModel.getCount()));
        adapter.setCountImage(tv_shopping_count);
        adapter.setmMainLayout(mainView);
        adapter.setmShoppingCart(iv_shopping_cart);
        swipeToLoadLayout.setRefreshing(true);
        return view;
    }


    @Override
    public void getData(int pn) {
        Map<String, Object> map = new HashMap<>();
        if (entity_id != 0) {
            map.put("flag", 0);
            type = entity_id;
        }
        map.put("pn", pn);
        map.put("ps", 9);
        getRequestPresenter().findByActivitiesIDs(type, map, new RequestCallback<FindByActivitiesID>() {
            @Override
            public void onSuc(FindByActivitiesID body) {


                if (TextUtils.isEmpty(body.getData().getInnerImage().trim())) {
                    if (adapter.isHasHeader(imageView)) adapter.removeHeard(imageView);
                } else {
                    if (!adapter.isHasHeader(imageView)) adapter.addHeaderView(imageView);
                }

                ImageLoaderUtils.loadImage(imageView, body.getData().getInnerImage(), false);
                stop();
                if (body.getData() != null && body.getData().getItems() != null && loadMoreUtil != null)
                    loadMoreUtil.setData(body.getData().getItems(), 1, body.getData().getPs());

            }

            @Override
            public void onFailed(int findByActivitiesID, String msg) {
                stop();
            }
        });
    }
}
