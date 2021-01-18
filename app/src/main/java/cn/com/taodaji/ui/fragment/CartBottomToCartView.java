package cn.com.taodaji.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.base.activity.BaseActivity;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FavoriteCount;
import cn.com.taodaji.model.event.FavoriteRefreshEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import tools.activity.MenuToolbarActivity;
import tools.extend.PhoneUtils;


/**
 * Created by yangkuo on 2018/11/29.
 */
public class CartBottomToCartView implements View.OnClickListener {


    private View shopping_cart, action_cart, fl_enshrine, fl_customer_service;
    private TextView count_image;
    private BaseActivity activity;
    private View mainView;
    private int storeOrProdId = -1;
    private BaseActivity baseActivity;

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void setStoreOrProdId(int storeOrProdId) {
        this.storeOrProdId = storeOrProdId;
    }

    public CartBottomToCartView(BaseActivity activity) {
        this.activity = activity;
        mainView = ViewUtils.getLayoutView(activity, R.layout.item_cart_bottom_tocart);
        shopping_cart = mainView.findViewById(R.id.shopping_cart);
        count_image = mainView.findViewById(R.id.count_image);
        fl_enshrine = mainView.findViewById(R.id.fl_enshrine);
        fl_enshrine.setOnClickListener(this);
        fl_customer_service = mainView.findViewById(R.id.fl_customer_service);
        fl_customer_service.setOnClickListener(this);
        action_cart = mainView.findViewById(R.id.action_cart);
        action_cart.setOnClickListener(this);
    }

    public void setFavorite(boolean favorite) {
        if (fl_enshrine != null) {
            fl_enshrine.setSelected(favorite);
        }
    }


    public void setFl_enshrine_show(boolean isShow) {
        if (isShow) fl_enshrine.setVisibility(View.VISIBLE);
        else fl_enshrine.setVisibility(View.GONE);
    }

    public View getMainView() {
        return mainView;
    }

    public View getShopping_cart() {
        return shopping_cart;
    }

    public TextView getCount_image() {
        return count_image;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_customer_service:
                SystemUtils.callPhone(activity, PhoneUtils.getPhoneService());
                break;
            case R.id.fl_enshrine://收藏
                int personId = -1;
                int userType = 0;
                if (PublicCache.loginPurchase != null) {
                    personId = PublicCache.loginPurchase.getEntityId();
                    userType = 0;
                } else if (PublicCache.loginSupplier != null) {
                    personId = PublicCache.loginSupplier.getEntityId();
                    userType = 1;
                } else LoginMethod.getInstance(baseActivity).toChooseLoginActivity();

                if (storeOrProdId == -1 || personId == -1) {
                    return;
                }

                boolean isFavorite = v.isSelected();

                if (isFavorite) {
                    //取消关注
                    if (baseActivity != null) baseActivity.loading();
                    ModelRequest.getInstance().favorite_delFavorite(userType, storeOrProdId, personId, 2, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {

                        @Override
                        public void onSuccess(FavoriteCount body) {
                            v.setSelected(false);
                            EventBus.getDefault().post(new FavoriteRefreshEvent(2));
                            if (baseActivity != null) baseActivity.loadingDimss();
                            UIUtils.showToastSafesShort("取消收藏成功");
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            super.onFailed(errCode, msg);
                            UIUtils.showToastSafesShort(msg);
                            if (baseActivity != null) baseActivity.loadingDimss();
                        }
                    });
                } else {
                    //关注
                    if (baseActivity != null) baseActivity.loading();
                    ModelRequest.getInstance().favorite_addFavorite(userType, storeOrProdId, personId, 2, PublicCache.site_login).enqueue(new ResultInfoCallback<FavoriteCount>() {
                        @Override
                        public void onSuccess(FavoriteCount body) {
                            EventBus.getDefault().post(new FavoriteRefreshEvent(2));
                            v.setSelected(true);
                            UIUtils.showToastSafesShort("收藏成功");
                            if (baseActivity != null) baseActivity.loadingDimss();
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            super.onFailed(errCode, msg);
                            UIUtils.showToastSafesShort(msg);
                            if (baseActivity != null) baseActivity.loadingDimss();
                        }
                    });

                }


                break;
            case R.id.action_cart:
                MenuToolbarActivity.goToPage(3);
                break;
        }
    }
}
