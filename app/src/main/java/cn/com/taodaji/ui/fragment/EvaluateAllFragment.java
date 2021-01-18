package cn.com.taodaji.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleEvaluateAllAdapter;
import cn.com.taodaji.model.entity.EvaluationList;
import com.base.retrofit.RequestCallback;
import tools.fragment.LoadMoreRecyclerViewFragment;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.utils.UIUtils;


public class EvaluateAllFragment extends LoadMoreRecyclerViewFragment {


    private SimpleEvaluateAllAdapter simpleEvaluateAllAdapter;

    private LoadMoreUtil loadMoreUtil;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.initView(inflater, container, savedInstanceState);
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadMoreUtil = new LoadMoreUtil(this, recycler_targetView);
        recycler_targetView.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(8), R.color.gray_f2));
        simpleEvaluateAllAdapter = new SimpleEvaluateAllAdapter();
        simpleEvaluateAllAdapter.setState(1);
        recycler_targetView.setAdapter(simpleEvaluateAllAdapter);
        swipeToLoadLayout.setRefreshing(true);
        return view;
    }

    /**
     * creditLevel	int	0		评价等级1好评2中评3差评 -1或空 全部
     * hasImg	int	0		是否有图1晒图 -1或空 全部
     * productId	int	0		商品id，-1或空全部
     */
    private int productId;
    private int hasImg;
    private int creditLevel = -1;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public void setCreditLevel(int creditLevel) {
        this.creditLevel = creditLevel;
    }

    @Override
    public void getData(int pn) {
        getRequestPresenter().evaluation_pPageList(creditLevel, hasImg, productId, pn, 9, new RequestCallback<EvaluationList>() {
            @Override
            public void onSuc(EvaluationList body) {
                loadMoreUtil.setData(body.getData().getItems(), body.getData().getPn(), body.getData().getPs());
                stop();
            }

            @Override
            public void onFailed(int evaluationList, String msg) {
                stop();
            }
        });
    }

}
