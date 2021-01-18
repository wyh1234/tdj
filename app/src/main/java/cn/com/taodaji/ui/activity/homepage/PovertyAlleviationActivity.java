package cn.com.taodaji.ui.activity.homepage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.DialogUtils;
import com.base.utils.MD5AndSHA;
import com.base.utils.UIUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.Poverty;
import cn.com.taodaji.model.entity.PovertyAlleviationRecommend;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.viewModel.adapter.BannerHolderView;
import cn.com.taodaji.viewModel.adapter.HomePageSimpleGoodsInformationAdapter;
import cn.com.taodaji.viewModel.adapter.PovertyAlleviationAdapter;
import cn.com.taodaji.viewModel.adapter.RecommendListAdapter;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;

public class PovertyAlleviationActivity extends SimpleToolbarActivity implements OnItemClickListener {

    private ConvenientBanner banner;
    private RecyclerView povertyAlleviation, hotRecycler, recommendRecycler;
    private  List<ADInfo> bannerList = new ArrayList<>();
    private List<ADInfo> adInfoList = new ArrayList<>();
    private List<cn.com.taodaji.model.entity.GoodsInformation> hotList = new ArrayList<>();
    private List<cn.com.taodaji.model.entity.GoodsInformation> recommendList = new ArrayList<>();
    private PovertyAlleviationAdapter alleviationAdapter;
    private RecommendListAdapter recommendListAdapter;
    private RecommendListAdapter hotListAdapter;
    private DialogUtils dialogUtils = null;
    private ImageView iv_shopping_cart;
    private TextView tv_shopping_count;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("助力精准扶贫");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_poverty_alleviation);
        body_other.addView(mainView);

        banner = mainView.findViewById(R.id.banner);
        banner.setOnItemClickListener(this);

        iv_shopping_cart = mainView.findViewById(R.id.iv_shopping_cart);
        iv_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                MenuToolbarActivity.goToPage(3);
            }
        });
        tv_shopping_count = mainView.findViewById(R.id.tv_shopping_count);

        povertyAlleviation = mainView.findViewById(R.id.rv_poverty_alleviation);
        povertyAlleviation.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        povertyAlleviation.setNestedScrollingEnabled(false);
        povertyAlleviation.setHasFixedSize(true);
        alleviationAdapter = new PovertyAlleviationAdapter(adInfoList,this);
        povertyAlleviation.setAdapter(alleviationAdapter);
        alleviationAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (adInfoList.get(position).getImageGoodsType()==1) {
                   String url = adInfoList.get(position).getImageLinkHttpUrl();
                   Intent intent1 = new Intent(PovertyAlleviationActivity.this, SpecialActivitiesActivity.class);
                   intent1.putExtra("url", url);
                   startActivity(intent1);
                }
            }
        });

        hotRecycler = mainView.findViewById(R.id.rv_hot_list);
        hotRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        hotRecycler.setNestedScrollingEnabled(false);
        hotListAdapter = new RecommendListAdapter();
        hotListAdapter.setmMainLayout((ViewGroup)mainView);
        hotListAdapter.setmShoppingCart(iv_shopping_cart);
        hotRecycler.setAdapter(hotListAdapter);

        recommendRecycler = mainView.findViewById(R.id.rv_recommended_list);
        recommendRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recommendRecycler.setNestedScrollingEnabled(false);
        recommendListAdapter = new RecommendListAdapter();
        recommendListAdapter.setmMainLayout((ViewGroup)mainView);
        recommendListAdapter.setmShoppingCart(iv_shopping_cart);
        recommendRecycler.setAdapter(recommendListAdapter);

        initBanner();
        initAd();
        initHotList();
        initRecommendList();

        CartModel cartModel = CartModel.getInstance();
        tv_shopping_count.setText(String.valueOf(cartModel.getCount()));

        EventBus.getDefault().register(this);
    }

    public void initBanner(){
        bannerList.clear();
        getRequestPresenter().findPoverty(0, new RequestCallback<Poverty>() {
            @Override
            protected void onSuc(Poverty body) {
                if (body.getErr()==0){
                    for (Poverty.DataBean.ItemsBean bean : body.getData().getItems()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageLinkHttpUrl(bean.getUrl());
                        adInfo.setImageObject(bean.getImage());
                        adInfo.setEntity_id(bean.getEntityId());
                        adInfo.setImageContent(bean.getCommentary());
                        adInfo.setImageName(bean.getTitle());
                        adInfo.setImageGoodsType(bean.getType());
                        bannerList.add(adInfo);
                    }
                    //初始化商品图片轮播
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new BannerHolderView();
                        }
                    }, bannerList);
                    banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.mipmap.hongdian_home});
                    banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                    banner.startTurning(5000);
                    banner.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void initAd(){
        adInfoList.clear();
        getRequestPresenter().findPoverty(1, new RequestCallback<Poverty>() {
            @Override
            protected void onSuc(Poverty body) {
                if (body.getErr()==0){
                    for (Poverty.DataBean.ItemsBean bean : body.getData().getItems()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setEntity_id(bean.getEntityId());
                        adInfo.setImageName(bean.getTitle());
                        adInfo.setImageContent(bean.getCommentary());
                        adInfo.setImageUrl(bean.getImage());
                        adInfo.setImageLinkHttpUrl(bean.getUrl());
                        adInfo.setImageGoodsType(bean.getType());
                        adInfoList.add(adInfo);
                    }
                    alleviationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void initHotList(){
        hotList.clear();
        getRequestPresenter().findRecommend(2, new RequestCallback<PovertyAlleviationRecommend>() {
            @Override
            protected void onSuc(PovertyAlleviationRecommend body) {
                if (body.getErr()==0){
                    hotListAdapter.setList(body.getData().getItems());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg
                );
            }
        });
    }

    public void initRecommendList(){
        recommendList.clear();
        getRequestPresenter().findRecommend(1, new RequestCallback<PovertyAlleviationRecommend>() {
            @Override
            protected void onSuc(PovertyAlleviationRecommend body) {
                if (body.getErr()==0){
                   recommendListAdapter.setList(body.getData().getItems());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg
                );
            }
        });
    }

    @Override
    public void onItemClick(int position) {
            switch (bannerList.get(position).getImageGoodsType()) {
                case 0:    //商品
                    break;
                case 1:  //h5专题
                    String url = "";
                    if (!TextUtils.isEmpty(bannerList.get(position).getImageLinkHttpUrl()) && bannerList.get(position).getImageLinkHttpUrl().contains("islogin=true")) {
                        if (LoginMethod.notLoginChecked()) {
                            LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                            return;
                        }
                        if (PublicCache.loginPurchase != null) {

                            String sstr = MD5AndSHA.md5Encode("tdj" + PublicCache.loginPurchase.getEntityId());

                            if (PublicCache.loginPurchase.getFlag() == 1) {//主账号
                                url = bannerList.get(position).getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getEntityId();
                            } else {//子账号
                                url = bannerList.get(position).getImageLinkHttpUrl() + "&customerId=" + PublicCache.loginPurchase.getEntityId() + "&subCustomerId=" + PublicCache.loginPurchase.getSubUserId();
                            }
                            url += "&sign=" + sstr;
                        } else if (bannerList.get(position).getImageLinkHttpUrl().contains("activityfor=customer") && PublicCache.loginSupplier != null) {
                            if (dialogUtils == null) {
                                dialogUtils = DialogUtils.getInstance(getBaseActivity()).getSingleDialog(R.layout.dialog_activities_message, UIUtils.getString(R.string.dialog_recharge_tips)).setPositiveButton("", null);
                            }
                            dialogUtils.show();
                            return;
                        }
                    } else url = bannerList.get(position).getImageLinkHttpUrl();

                    Intent intent1 = new Intent(this, SpecialActivitiesActivity.class);
                    intent1.putExtra("url", url);
                    startActivity(intent1);
                    break;
            }
    }

    //接收商品数量变化，改变悬浮按扭的数值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CartEvent event) {
        tv_shopping_count.setText(String.valueOf(CartModel.getInstance().getCount()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
