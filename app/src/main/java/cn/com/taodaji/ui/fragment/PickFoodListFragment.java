package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.activity.homepage.PickFoodActivity;
import cn.com.taodaji.viewModel.adapter.SimpleGoodsInformationAdapter;
import cn.com.taodaji.model.entity.PickFoodGoodsList;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;
import cn.com.taodaji.model.CartModel;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.ResultInfoCallback;

import tools.activity.MenuToolbarActivity;
import tools.fragment.LoadMoreRecyclerViewFragment;

import com.base.listener.UploadPicturesDoneListener;
import com.base.utils.UIUtils;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.listener.PickFoodListener;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//有问题
public class PickFoodListFragment extends LoadMoreRecyclerViewFragment {

    private SimpleGoodsInformationAdapter simpleGoodsInformationAdapter;
    private PickFoodListener pickFoodListener;
    private int categoryId;//一级分类
    private TextView count_image;
    private View mShoppingCart;//购物车


    private int isP = -1;//0零售，1事件批，-1全部
    private int isCRITERIA = -1;//0零售，1事件批，-1全部

    public void setmShoppingCart(View mShoppingCart) {
        this.mShoppingCart = mShoppingCart;

        //主要是fragment复用时
        if (simpleGoodsInformationAdapter != null)
            simpleGoodsInformationAdapter.setmShoppingCart(mShoppingCart);
    }

    @Override
    public void initData() {
        super.initData();
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(getContext()));
        simpleGoodsInformationAdapter = new SimpleGoodsInformationAdapter();

        simpleGoodsInformationAdapter.setmMainLayout(mainView);
        simpleGoodsInformationAdapter.setmShoppingCart(mShoppingCart);

        recycler_targetView.setAdapter(simpleGoodsInformationAdapter);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshing(true);
        bottom_view.setVisibility(View.GONE);
    }

    @Override
    public void getData(final int pn) {
        if (pickFoodListener == null) return;


        Map<String, Object> map = new HashMap<>();
        map.put("pn", pn);
        map.put("ps", 6);
        map.put("status", 1);

        isP = pickFoodListener.getIsP();
        isCRITERIA=pickFoodListener.getIsCRITERIA();
        map.put("isP", isP);
        if (isCRITERIA == -1) {
            map.put("productCriteria", "");
        }else{
            map.put("productCriteria",isCRITERIA +"");
        }

        //排序
        map.put("sort", pickFoodListener.sortString());


        //类别
        //categoryId代表一级分类
        //categoryId == -1  代表全部，不需要categoryId ,includeSubCategory字段
        if (categoryId != -1) {
            //pickFoodListener.categoryId() 代表二级分类
            //pickFoodListener.categoryId() == -1 代表二级分类的全部 ，需要includeSubCategory 字段
            if (pickFoodListener.categoryId() == -1) {
                map.put("includeSubCategory", 1);
                map.put("categoryId", categoryId);
            } else {
                if ("全部".equals(pickFoodListener.goodsName())) {
                    map.put("categoryId", pickFoodListener.categoryId());
                } else {
                    map.put("name", pickFoodListener.goodsName());
                    map.put("categoryId", pickFoodListener.categoryId());
                }
            }

        } else {
            stop();
            return;
        }
        map.put("isCanteen", pickFoodListener.isCanteen());
  /*      if (pickFoodListener.isCanteen()==1){
            if (!"全部".equals(pickFoodListener.goodsName())) {
                map.put("name", pickFoodListener.goodsName());
            }


        }*/
        int userType = 0;
        if (PublicCache.loginSupplier != null) userType = 1;
        map.put("userType", userType);
        map.put("fenceGid", PublicCache.refreshId);
        getRequestPresenter().findPageList(map, new ResultInfoCallback<PickFoodGoodsList>() {
            @Override
            public void onSuccess(PickFoodGoodsList body) {
                loadMoreUtil.setData(body.getItems(), body.getPn(), body.getPs());
                stop();
            }

            @Override
            public void onFailed(int pickFoodGoodsListResultInfo, String msg) {
                UIUtils.showToastSafe(msg);
                stop();
            }
        });
    }


    @Override
    public void update() {
        if (swipeToLoadLayout == null) return;
        if (loadMoreUtil != null) loadMoreUtil.clearAll();

        swipeToLoadLayout.setRefreshing(true);
    }

    public boolean isStatusDefault() {
        if (swipeToLoadLayout == null) return false;
        return swipeToLoadLayout.isStatusDefault();
    }

    @Override
    public void onUserVisible() {
        if (isP != pickFoodListener.getIsP()||isCRITERIA!=pickFoodListener.getIsCRITERIA()) {
            if (loadMoreUtil != null) {
                loadMoreUtil.clearAll();
            }
            super.onUserVisible();
            return;
        }
        if (simpleGoodsInformationAdapter != null && simpleGoodsInformationAdapter.getRealCount() == 0) {
            super.onUserVisible();
        }
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        onUserVisible();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }


    //登录后刷新
    @Subscribe
    public void update(Login_in login_in) {
        if (loadMoreUtil != null) {
            loadMoreUtil.clearAll();
        }
    }

    //退出后刷新
    @Subscribe
    public void update(Login_out login_out) {
        if (loadMoreUtil != null) {
            loadMoreUtil.clearAll();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (context instanceof UploadPicturesDoneListener) {
            pickFoodListener = (PickFoodListener) context;
        } else if (context instanceof PickFoodActivity){
                pickFoodListener = (PickFoodListener) context;
            }else {
                Fragment fragment = getParentFragment();
                if (fragment == null)
                    throw new IllegalStateException(context.getClass() + " 类，必须实现 PickFoodListener 接口");
                else pickFoodListener = (PickFoodListener) fragment;

        }

    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDetach();
    }
}
