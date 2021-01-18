package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.retrofit.ResultInfoCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FavoriteProducts;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.MarketShopList;
import cn.com.taodaji.model.entity.ShopInformation;
import cn.com.taodaji.model.event.FavoriteRefreshEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleShopListAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

/**
 * Created by yangkuo on 2018/12/5.
 */
public class FavoriteFragment extends LoadMoreRecyclerViewFragment {


    private int type;


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void initView(View mainView) {
        super.initView(mainView);
        if (mainView != null) {
            recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));

            if (type == 1) {
                recycler_targetView.setAdapter(new SimpleShopListAdapter() {
                    @Override
                    public List<GoodsInformation> getGoodsList(int position) {
                        return ((ShopInformation) getListBean(position)).getItems();
                    }
                });
            } else if (type == 2) {
                recycler_targetView.setAdapter(new SimpleGoodsInformationAdapter());
            }

        }
    }

    @Override
    public void initData() {
        super.initData();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onUserVisible() {
        if (loadMoreUtil.getRealCount() == 0) super.onUserVisible();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Subscribe
    public void onEvent(FavoriteRefreshEvent event) {
        if (event.type == type) {
            View view = recycler_targetView.getChildAt(0);
            if (view != null) {
                int position = recycler_targetView.getChildAdapterPosition(view);
                loadMoreUtil.refreshData(position, 8);
            }
        }
    }


    @Override
    public void getData(int pn) {

        int personId = -1;
        int userType = 0;
        if (PublicCache.loginPurchase != null) {
            userType = 0;
            personId = PublicCache.loginPurchase.getEntityId();
        } else if (PublicCache.loginSupplier != null) {
            userType = 1;
            personId = PublicCache.loginSupplier.getEntityId();
        }
        if (personId == -1) {
            stop();
            return;
        }


        if (type == 1) {
            addRequest(ModelRequest.getInstance().favorite_findFavoriteStores(userType, personId, type, PublicCache.site_login, pn, 8), new ResultInfoCallback<MarketShopList>() {
                @Override
                public void onSuccess(MarketShopList body) {
                    stop();
                    loadMoreUtil.setData(body.getList(), body.getPn(), body.getPs());
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    stop();
                }
            });
        } else if (type == 2) {
            addRequest(ModelRequest.getInstance().favorite_findFavoriteProducts(userType, personId, type, PublicCache.site_login, pn, 8), new ResultInfoCallback<FavoriteProducts>() {
                @Override
                public void onSuccess(FavoriteProducts body) {
                    stop();
                    loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    stop();
                }
            });

        }

    }


}
