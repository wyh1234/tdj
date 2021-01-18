package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.StoreRecommend;
import cn.com.taodaji.ui.activity.homepage.StoreRecommendDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SimpleStoreRecommendAdapter extends SingleRecyclerViewAdapter {
    private final static int LINEARN = 100;//优秀店铺展示
    private final static int GRID = 101;//优秀店铺展示   首页三个小的
    private final static int LIN = 102;//实力商家
    private int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                if (type == LIN) putRelation(R.id.market_image, "bannerImgUrl");
                else putRelation(R.id.market_image, "logoImageUrl");
                putRelation(R.id.market_name, "name");
                putRelation(R.id.mainCommodity, "mainCommodity");
                putRelation(R.id.market_address, "marketName");
                putRelation(R.id.market_NO, "marketNo");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.item_view);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (type > 0) return type;
        else return super.getItemViewType(position);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        int w = UIUtils.getScreenWidthPixels();
        View view = null;

        switch (viewType) {
            case LINEARN:
                view = ViewUtils.getFragmentView(parent, R.layout.item_store_recommend);
                break;
            case GRID:
                view = ViewUtils.getFragmentView(parent, R.layout.item_market_list);
                int h = (w - UIUtils.dip2px(5) * 6) / 3;
                View market_image = ViewUtils.findViewById(view, R.id.market_image);
                UIUtils.setViewSize(market_image, h, h);
                break;
            case LIN:
                view = ViewUtils.getFragmentView(parent, R.layout.item_market_list);
                view.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10));
                View market_image1 = ViewUtils.findViewById(view, R.id.market_image);
                UIUtils.setViewSize(market_image1, w, UIUtils.dip2px(150));
                break;
        }
        return view;
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (!super.onItemClick(view, onclickType, position, bean)) {
            StoreRecommend.ListBean data = (StoreRecommend.ListBean) bean;
            if (type == 103) StoreRecommendDetailActivity.startActivity(view.getContext(), data);
            else ShopDetailInformationActivity.startActivity(view.getContext(), data.getStore());
            return true;
        }
        return false;
    }

}
