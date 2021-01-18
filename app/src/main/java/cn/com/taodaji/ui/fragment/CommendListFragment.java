package cn.com.taodaji.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.model.entity.FindCommendProduct;
import cn.com.taodaji.model.entity.HomepageGridDatas;

import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.activity.homepage.CommendActivity;
import tools.activity.MenuToolbarActivity;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.glide.GlideImageView;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class CommendListFragment extends LoadMoreRecyclerViewFragment {

    private SimpleGoodsInformationAdapter goodsInformationAdapter;
    private int index;
    private int next_index;
    private HomepageGridDatas ben;
    private View mShoppingCart;     //购物车

    public void setmShoppingCart(View mShoppingCart) {
        this.mShoppingCart = mShoppingCart;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);

        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.setBackgroundResource(R.color.gray_f2);
        //  recycler_targetView.addItemDecoration(new SpacesItemDecoration(UIUtils.dip2px(5)));
        goodsInformationAdapter = new SimpleGoodsInformationAdapter();
        recycler_targetView.setAdapter(goodsInformationAdapter);

        View head_view = ViewUtils.getFragmentView(recycler_targetView, R.layout.item_commend_header);
        final GlideImageView head_image = ViewUtils.findViewById(head_view, R.id.commend_bg);

        View foot_view1 = ViewUtils.getFragmentView(recycler_targetView, R.layout.item_commend_header);
        final ImageView foot_image = ViewUtils.findViewById(foot_view1, R.id.commend_bg);
        final TextView foot_text = ViewUtils.findViewById(foot_view1, R.id.commend_text);

        View foot_view2 = ViewUtils.getFragmentView(recycler_targetView, R.layout.item_commend_footer);


        goodsInformationAdapter.setmMainLayout(mainView);
        goodsInformationAdapter.setmShoppingCart(mShoppingCart);


        goodsInformationAdapter.addHeaderView(head_view);
        goodsInformationAdapter.addFooterView(foot_view1);
        goodsInformationAdapter.addFooterView(foot_view2);

        if (ben == null) return view;

        next_index = index == ben.getList().size() - 1 ? 0 : index + 1;
        //头部显示图片
        head_image.loadImage(ben.getList().get(index).getRecommendImage());
        //脚显示文字
        foot_text.setText("去" + ben.getList().get(next_index).getName() + "区看看");
        foot_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = next_index;
                next_index = index == ben.getList().size() - 1 ? 0 : index + 1;
                foot_text.setText("去" + ben.getList().get(next_index).getName() + "区看看");

                head_image.loadImage(ben.getList().get(index).getRecommendImage());
                // EventBus.getDefault().post(ben.getList().get(index).getName());
                goodsInformationAdapter.clear();
                swipeToLoadLayout.setRefreshing(true);
                if (getActivity() instanceof CommendActivity) {
                    CommendActivity activity = (CommendActivity) getActivity();
                    activity.simple_title.setText("店长推荐" + ben.getList().get(index).getName() + "专区");
                }
            }
        });
        foot_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuToolbarActivity.goToPage(2);
            }
        });
        swipeToLoadLayout.setRefreshing(true);
        return view;
    }


    public void setData(HomepageGridDatas ben, int index) {
        this.index = index;
        this.ben = ben;
    }

    @Override
    public void getData(int pn) {
        if (ben != null && ben.getList() != null)
            getViewData(ben.getList().get(index).getCategoryId(), pn);
    }

    //type 14  20  23  48
    private void getViewData(int categoryId, int pn) {
        getRequestPresenter().findCommendProduct(categoryId, pn, 9, new ResultInfoCallback<FindCommendProduct>() {
            @Override
            public void onSuccess(FindCommendProduct body) {
                loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                stop();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
                stop();
            }
        });
    }
}
