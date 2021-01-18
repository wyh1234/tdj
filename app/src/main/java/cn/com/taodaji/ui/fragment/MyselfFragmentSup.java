package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.StatusBarBaseActivity;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.DialogUtils;
import com.base.utils.JavaMethod;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.MyselftUpdateS;
import cn.com.taodaji.model.entity.OffToday;
import cn.com.taodaji.model.entity.OrderStatusCount;
import cn.com.taodaji.model.entity.ProductStatus;
import cn.com.taodaji.model.event.FavoriteEvent;
import cn.com.taodaji.model.event.NotificationCountEvent;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.model.event.OrderPlaceCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.cashCoupon.CashCouponActivity;
import cn.com.taodaji.ui.activity.evaluate.EvaluateManageActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.myself.DataModificationActivity;
import cn.com.taodaji.ui.activity.myself.FavoriteActivity;
import cn.com.taodaji.ui.activity.myself.GoodsNeedActivity;
import cn.com.taodaji.ui.activity.myself.ImageUploadActivity;
import cn.com.taodaji.ui.activity.myself.LeagueActivity;
import cn.com.taodaji.ui.activity.myself.MeaningPostActivity;
import cn.com.taodaji.ui.activity.myself.MyEquitiesActivity;
import cn.com.taodaji.ui.activity.myself.MyNameCardActivity;
import cn.com.taodaji.ui.activity.myself.MySelfSupYearMoney;
import cn.com.taodaji.ui.activity.myself.NewsListActivity;
import cn.com.taodaji.ui.activity.myself.PayManageActivity;
import cn.com.taodaji.ui.activity.myself.PickupServiceActivity;
import cn.com.taodaji.ui.activity.myself.PunishRuleIntroduceActivity;
import cn.com.taodaji.ui.activity.myself.SelectDeliveryWarehouseActivity;
import cn.com.taodaji.ui.activity.myself.SettingActivity;
import cn.com.taodaji.ui.activity.myself.ShareActivity;
import cn.com.taodaji.ui.activity.myself.SubAccountActivity;
import cn.com.taodaji.ui.activity.myself.SypplyNameCardActivity;
import cn.com.taodaji.ui.activity.myself.TodayDeliverGoodsOrderManageActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesListActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.ui.activity.packingCash.PackingCashCurrentActivity;
import cn.com.taodaji.ui.activity.penalty.PunishListActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseMoneyListActivity;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivity;
import cn.com.taodaji.ui.activity.shopManagement.ShopManagementActivity;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyActivity;
import cn.com.taodaji.ui.ppw.ShopStateRemarkPopupWindow;
import cn.com.taodaji.viewModel.adapter.CommomItemAdapter;
import cn.com.taodaji.viewModel.adapter.InviteTypeListAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import tools.extend.PhoneUtils;
import tools.fragment.DataBaseFragment;
import tools.statusbar.Eyes;

public class MyselfFragmentSup extends DataBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View mainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ManageActivity activity;
    private RecyclerView order,tool,help;
    private SimpleMyselfFunctionAdapter orderAdapter,toolAdapter,helpAdapter;
    private LinearLayout setting,status,rights,service,lookMore,llshopEvaluate,llshopCollect,llshopFollow,llauditing,llmyOrder;
    private RelativeLayout message;
    private GlideImageView headportrait;
    private TextView shopName,shopInfo,shopStatus,shopEvaluate,shopCollect,shopFollow,myRights,pickupService,auditingMessage,helpMessage,unread_news,kucun;
    private ImageView statusIcon;
    private Button todayOrder,callPhone;
    private String imageUrl = "";
    private int state,storeId,code = 0,wait_evaluate,flag=0;//code--Event传值;
    private ProductStatus.DataBean.MapBean productStatusBean;//橱窗限制


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_myself_sup, container, false);
        swipeRefreshLayout = mainView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //初始化
        initOtherView(mainView);
        initOrder(mainView);
        initTool(mainView);
        initHelp(mainView);
        onRefresh();
        return mainView;
    }

    public void initOtherView(View mainView){
        unread_news = ViewUtils.findViewById(mainView,R.id.unread_news);
        message = ViewUtils.findViewById(mainView,R.id.ll_news);
        message.setOnClickListener(this);
        setting = ViewUtils.findViewById(mainView,R.id.ll_setting);
        setting.setOnClickListener(this);

        headportrait = ViewUtils.findViewById(mainView,R.id.myself_headportrait);
        headportrait.setOnClickListener(this);
        shopName = ViewUtils.findViewById(mainView,R.id.tv_shop_name);
        shopInfo = ViewUtils.findViewById(mainView,R.id.tv_shop_info);
        shopInfo.setOnClickListener(this);
        status = ViewUtils.findViewById(mainView,R.id.ll_shop_status);
        shopStatus = ViewUtils.findViewById(mainView,R.id.tv_shop_status);
        statusIcon = ViewUtils.findViewById(mainView,R.id.iv_store_status);

        llshopEvaluate = ViewUtils.findViewById(mainView,R.id.ll_shop_evaluate);
        llshopEvaluate.setOnClickListener(this);
        llshopCollect = ViewUtils.findViewById(mainView,R.id.ll_shop_collect);
        llshopCollect.setOnClickListener(this);
        llshopFollow = ViewUtils.findViewById(mainView,R.id.ll_shop_follow);
        llshopFollow.setOnClickListener(this);
        shopEvaluate = ViewUtils.findViewById(mainView,R.id.tv_shop_evaluate);
        shopCollect = ViewUtils.findViewById(mainView,R.id.tv_store_collect);
        shopFollow = ViewUtils.findViewById(mainView,R.id.tv_store_follow);

        rights = ViewUtils.findViewById(mainView,R.id.ll_my_rights);
        service = ViewUtils.findViewById(mainView,R.id.ll_pick_up_service);
        rights.setOnClickListener(this);
        service.setOnClickListener(this);
        myRights = ViewUtils.findViewById(mainView,R.id.tv_my_rights);
        pickupService = ViewUtils.findViewById(mainView,R.id.tv_pick_up_service);

        llauditing = ViewUtils.findViewById(mainView,R.id.supplier_auditing);
        auditingMessage = ViewUtils.findViewById(mainView,R.id.auditing_message);
        helpMessage = ViewUtils.findViewById(mainView,R.id.help_message);
        callPhone = ViewUtils.findViewById(mainView,R.id.call_phone);
        callPhone.setOnClickListener(this);

        kucun = ViewUtils.findViewById(mainView,R.id.tv_kucun);
    }

    public void initOrder(View mainView){
        llmyOrder = ViewUtils.findViewById(mainView,R.id.ll_my_order);
        lookMore = ViewUtils.findViewById(mainView,R.id.ll_look_more);
        lookMore.setOnClickListener(this);
        todayOrder = ViewUtils.findViewById(mainView,R.id.btn_today_order);
        todayOrder.setOnClickListener(this);
        order = ViewUtils.findViewById(mainView,R.id.rv_order_recyclerView);

        orderAdapter = new SimpleMyselfFunctionAdapter();
        orderAdapter.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        order.setAdapter(orderAdapter);
        orderAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType==0){
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), OrderListActivity.class);
                            code = 1;
                            break;
                        case 1:
                            intent = new Intent(getActivity(), OrderListActivity.class);
                            code = 2;
                            break;
                        case 2:
                            intent = new Intent(getActivity(), OrderListActivity.class);
                            code = 3;
                            break;
                        case 3: if (PublicCache.loginSupplier != null)
                            AfterSalesListActivity.startActivity(getContext());
                            break;
                    }
                    if (intent != null) {
                        EventBus.getDefault().postSticky(new OrderEvent(code));
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
        if (order == null) return;
        order.setLayoutManager(new GridLayoutManager(getContext(), 4));
        String[] titles = new String[]{"待入库", "待签收", "待评价", "退换/售后"};
        int[] imageID = new int[]{R.mipmap.pending_confirmation3x, R.mipmap.pending_deliver_goods3x, R.mipmap.pending_evaluate3x, R.mipmap.refund3x};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        if (orderAdapter != null) orderAdapter.setList(list);
    }

    public void initTool(View mainView){
        tool = mainView.findViewById(R.id.rv_tool_recyclerView);
        toolAdapter = new SimpleMyselfFunctionAdapter();
        toolAdapter.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        tool.setAdapter(toolAdapter);
        tool.setLayoutManager(new GridLayoutManager(getContext(), 4));
        toolAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType==0){
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            if (productStatusBean != null) {
                                if (productStatusBean.getLimitTotalNum() == 0 || productStatusBean.getLimitTotalNum() > productStatusBean.getTotal()) {
                                    intent = new Intent(getActivity(), CommodityCategoryActivity.class);
                                } else {
                                    DialogUtils.getInstance(getContext()).getSingleDialog(R.layout.dialog_goods_release_limit, "当前已达商品橱窗限量" + productStatusBean.getLimitTotalNum() + "个,请删除下架中或审核中商品，再发布其它商品。", "注意").setNegativeButton("确定", null).show();
                                }
                            } else {
                                UIUtils.showToastSafesShort("数据丢失，请刷新！");
                            }
                            break;
                        case 1:
                            intent = new Intent(getActivity(), ShopManagementActivity.class);
                            break;
                        case 2:
                            intent = new Intent(getActivity(), OrderListActivity.class);
                            code = 0;
                            EventBus.getDefault().postSticky(new OrderEvent(code));
                            break;
                        case 3:
                            ShopDetailInformationActivity.startActivity(getContext(), PublicCache.loginSupplier.getStore());
                            break;
                        case 4:
                            intent = new Intent(getContext(), EvaluateManageActivity.class);
                            intent.putExtra("wait_evaluate", wait_evaluate);
                            break;
                        case 5:
                            intent = new Intent(getContext(), PackingCashCurrentActivity.class);
                            break;
                        case 6:
                            intent = new Intent(getContext(), SupplyMoneyActivity.class);
                            break;
                        case 7://平台年费按钮
                            if (activity.getFeeTips().getData().getInfo().getServStatus() == 2) {//服务中
                                intent = new Intent(getContext(), MyEquitiesActivity.class);
                            } else if (activity.getFeeTips().getData().getInfo().getServStatus() == 3) {

                            } else {
                                intent = new Intent(getContext(), MySelfSupYearMoney.class);
                                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
                            }
                            if (activity.getFeeTips().getData().getInfo().getIsSelected() == 1) {
                                intent = new Intent(getContext(), MySelfSupYearMoney.class);
                                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
                            } else if (activity.getFeeTips().getData().getInfo().getIsSelected() == 0) {
                                intent = new Intent(getContext(), MySelfSupYearMoney.class);
                                intent.putExtra("IsSelected", "0");
                                intent.putExtra("type", activity.getFeeTips().getData().getInfo().getStoreType() == 2 ? "ZM" : "QJ");
                                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
                            }
                            break;
                    }
                    if (intent != null) startActivity(intent);
                }
                return false;
            }
        });
        if (tool == null) return;
        String[] titles = new String[]{"发布商品", "商品管理", "订单管理", "店铺预览", "评价管理", "押金管理", "账户提现" ,"平台年费"};
        int[] imageID = new int[]{R.mipmap.tool1, R.mipmap.tool2, R.mipmap.tool3, R.mipmap.tool4,R.mipmap.tool5,R.mipmap.tool6,R.mipmap.tool7,R.mipmap.tool8};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        if (toolAdapter != null) toolAdapter.setList(list);
    }

    public void initHelp(View mainView){
        help = mainView.findViewById(R.id.rv_help_recyclerView);
        if (help == null) return;
        help.setLayoutManager(new GridLayoutManager(getContext(), 4));
        helpAdapter = new SimpleMyselfFunctionAdapter();
        helpAdapter.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        help.setAdapter(helpAdapter);
        helpAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType==0){
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            LeagueActivity.startActivity(getContext(), 2);
                            break;
                        case 1:
                            ShareActivity.startActivity(getContext());
                            break;
                        case 2:
                            LeagueActivity.startActivity(getContext(), 0);
                            break;

                        case 3:
                            SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                            break;
                        case 4:
                            intent = new Intent(getBaseActivity(), MeaningPostActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(getBaseActivity(), GoodsNeedActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
                return false;
            }
        });
        String[] titles = new String[]{"帮助中心", "分享推荐", "合作加盟","客服电话","意见反馈","新品提报"};
        int[] imageID = new int[]{R.mipmap.help1, R.mipmap.help2, R.mipmap.help3, R.mipmap.help4,R.mipmap.help5,R.mipmap.help6};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        if (helpAdapter != null) helpAdapter.setList(list);
    }

    public void initViewData(){
        imageUrl = PublicCache.loginSupplier.getStorePics();
        if ( headportrait!= null) {
            ImageLoaderUtils.loadImage(headportrait, imageUrl);
        }
        shopName.setText(PublicCache.loginSupplier.getStoreName());
        switch (PublicCache.loginSupplier.getAuthStatus()) {
            case -1://发生异常
                llauditing.setVisibility(View.GONE);
                llmyOrder.setVisibility(View.GONE);
                break;
            case 0://审核中
                llauditing.setVisibility(View.VISIBLE);
                llmyOrder.setVisibility(View.GONE);
                auditingMessage.setText("等待平台审核，审核通过方可下单采购。");
                helpMessage.setText("24小时内我们将上门审核完毕，请耐心等待。");
                shopInfo.setText("修改资料");
                break;
            case 1://审核通过
                llauditing.setVisibility(View.GONE);
                llmyOrder.setVisibility(View.VISIBLE);
                shopInfo.setText("店铺资料");
                break;
            case 2://审核驳回
                llauditing.setVisibility(View.VISIBLE);
                llmyOrder.setVisibility(View.GONE);
                auditingMessage.setText(PublicCache.loginSupplier.getAuthStatusRemark());
                helpMessage.setText("请重新上传相关资料，再次审核。");
                shopInfo.setText("修改资料");
                break;
        }
        shopState();
    }

    public void login_in() {
        onRefresh();
    }

    //头像上传完毕后返回
    @Subscribe
    public void onEvent(ImageUploadOk ok) {
        if (ok.getData() instanceof Boolean) return;
        if (PublicCache.loginPurchase != null) {
            imageUrl = PublicCache.loginPurchase.getImageUrl();
        } else if (PublicCache.loginSupplier != null) {
            imageUrl = PublicCache.loginSupplier.getStorePics();
        }
        ImageLoaderUtils.loadImage(headportrait, imageUrl);
    }

    public void orderStatusCount(OrderStatusCount event) {
        if (orderAdapter == null || orderAdapter.getRealCount() == 0) return;
        SparseArrayCompat<Map<String, Object>> sparry = new SparseArrayCompat<>();
        if (PublicCache.loginSupplier != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("goodsCount", event.getWait_seller_send_goods());
            sparry.put(0, map);

            Map<String, Object> map1 = new HashMap<>();
            map1.put("goodsCount", event.getWait_buyer_confirm_goods());
            sparry.put(1, map1);

            Map<String, Object> map2 = new HashMap<>();
            map2.put("goodsCount", event.getTrade_success() + event.getWait_seller_evaluate());
            sparry.put(2, map2);

            Map<String, Object> map3 = new HashMap<>();
            map3.put("goodsCount", event.getAfterSalesCount());
            sparry.put(3, map3);

            wait_evaluate = event.getTrade_success() + event.getWait_buyer_evaluate();
        }

        for (int i = 0; i < sparry.size(); i++) {
            orderAdapter.update(sparry.keyAt(i), sparry.valueAt(i));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ll_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_news:
                intent = new Intent(getActivity(), NewsListActivity.class);
                startActivity(intent);
                break;
            case R.id.myself_headportrait:
                /** 头像设置*/
                String imageParamsKey = "";
                if (PublicCache.loginPurchase != null) {
                    imageParamsKey = "imageUrl";
                    imageUrl = PublicCache.loginPurchase.getImageUrl();
                } else if (PublicCache.loginSupplier != null) {
                    imageParamsKey = "storePics";
                    imageUrl = PublicCache.loginSupplier.getStorePics();
                } else return;
                intent = new Intent(getActivity(), ImageUploadActivity.class);
                intent.putExtra("title", "头像上传");
                intent.putExtra("isCut", true);//默认为false
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("imageDescription", HEAD_PORTRAIT_UPLOAD);
                intent.putExtra("imageParamsKey", imageParamsKey);
                startActivity(intent);
                break;
            case R.id.tv_shop_info:
                intent = new Intent(getBaseActivity(), SypplyNameCardActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_shop_status:
                if (shopStatus.getText().equals("正常营业") || shopStatus.getText().equals("暂停营业")) {
                    ShopStateRemarkPopupWindow shopStateRemarkPopupWindow = new ShopStateRemarkPopupWindow(view) {
                        @Override
                        public void leftOnclick() {
                            /** 状态设置*/
                            if (PublicCache.loginSupplier == null) return;
                            state = 1;
                            Map<String, Object> map = new HashMap<>();
                            map.put("storeId", PublicCache.loginSupplier.getStore());
                            map.put("storeStatus", state);
                            loadingShow("正在切换");
                            stateUpdate(map);
                        }

                        @Override
                        public void rightOnclick() {
                            /** 状态设置*/
                            if (PublicCache.loginSupplier == null) return;
                            state = 0;
                            Map<String, Object> map = new HashMap<>();
                            map.put("storeId", PublicCache.loginSupplier.getStore());
                            map.put("storeStatus", state);
                            loadingShow("正在切换");
                            stateUpdate(map);
                        }
                    };
                    shopStateRemarkPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.ll_shop_evaluate:
                intent = new Intent(getContext(), PunishRuleIntroduceActivity.class);
                startActivity(intent);
            case R.id.ll_shop_collect:
                EventBus.getDefault().postSticky(new FavoriteEvent(2));
                startActivity(new Intent(getContext(), FavoriteActivity.class));
                break;
            case R.id.ll_shop_follow:
                EventBus.getDefault().postSticky(new FavoriteEvent(1));
                startActivity(new Intent(getContext(), FavoriteActivity.class));
                break;
            case R.id.ll_look_more:
                intent = new Intent(getActivity(), OrderListActivity.class);
                EventBus.getDefault().postSticky(new OrderEvent(0));
                startActivity(intent);
                break;
            case R.id.btn_today_order:
                Intent intent1 = new Intent(getContext(), SelectDeliveryWarehouseActivity.class);
                intent1.putExtra("orderId1", storeId);
                startActivity(intent1);
                break;
            case R.id.call_phone:
                /** 拨打电话*/
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
            case R.id.ll_my_rights:
                if (flag==1){
                    return;
                }
                intent = new Intent(getBaseActivity(),MyEquitiesActivity.class);
                intent.putExtra("IsSelected",activity.getFeeTips().getData().getInfo().getIsSelected());
                startActivity(intent);
                break;
            case R.id.ll_pick_up_service:
                intent = new Intent(getBaseActivity(), PickupServiceActivity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(ManageActivity)context;
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        addRequest(ModelRequest.getInstance().product_status(PublicCache.site_login, PublicCache.loginSupplier.getStore()), new RequestCallback<ProductStatus>() {
            @Override
            protected void onSuc(ProductStatus body) {
                productStatusBean = body.getData().getMap();
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailed(int errCode, String msg) {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        int entyId = PublicCache.loginSupplier.getEntityId();
        getRequestPresenter().supplier_refreshInfo(entyId, new RequestCallback<MyselftUpdateS>() {
            @Override
            public void onSuc(MyselftUpdateS event) {


                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (event.getErr()==4){
                    UIUtils.showToastSafesShort("用户登录信息失效,请重新登陆");
                    PublicCache.login_mode = PURCHASER;
                    LoginMethod.loginOut();
                    return;
                }

                if (event.getData().getIsWarning()>0){
                    kucun.setVisibility(View.VISIBLE);
                }


                if (shopCollect != null)
                    shopCollect.setText(event.getData().getFavoriteProdCount() + "");
                if (shopFollow != null)
                    shopFollow.setText(event.getData().getFavoriteStoreCount() + "");

                storeId = event.getData().getStore();
                if (PublicCache.loginSupplier == null) return;
                JavaMethod.copyValue(PublicCache.loginSupplier, event.getData());
                PublicCache.loginSupplier.update();

                if (event.getData().getAuthStatus() != PublicCache.loginSupplier.getAuthStatus()) {
                    initViewData();
                }
                state = event.getData().getStoreStatus();
                shopState();
                if (event.getData().getIsAllOpen()==1){
                    pickupService.setText("立即查看");
                }

                if (event.getData().getStoreScore() != null) shopEvaluate.setText(event.getData().getStoreScore().toString());

                orderStatusCount(event.getData().getOrderNumber());

                if (event.getData().getPushMessageNotReadCount() == 0) {
                    unread_news.setVisibility(View.INVISIBLE);
                } else {
                    unread_news.setVisibility(View.VISIBLE);
                    unread_news.setText(String.valueOf(event.getData().getPushMessageNotReadCount()));
                }
                //初始化数据
                initViewData();
            }

            @Override
            public void onFailed(int myselftUpdateS, String msg) {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);}
                if (myselftUpdateS==4){
                    UIUtils.showToastSafesShort("用户登录信息失效,请重新登陆");
                    PublicCache.login_mode = PURCHASER;
                    LoginMethod.loginOut();
                    return;
                }
            }
        });
    }


    private void shopState() {
        if (statusIcon != null) {
            if (PublicCache.loginSupplier.getAuthStatus() == 1) {//审核通过
                if (state == 0) {
                    statusIcon.setBackgroundResource(R.mipmap.normal3x);
                    shopStatus.setText(STORE_STATUS.get(state));
                    status.setOnClickListener(this);
                } else if (state == 1) {
                    statusIcon.setBackgroundResource(R.mipmap.stop_business);
                    shopStatus.setText(STORE_STATUS.get(state));
                    status.setOnClickListener(this);
                } else {
                    statusIcon.setBackgroundResource(R.mipmap.shutdown);
                    shopStatus.setText(STORE_STATUS.get(state));
                }

                if (activity.getFeeTips() != null) {//如果是新用户,并且审核通过，在ManageActivity可以获取相关字段判断，显示年费相关按钮，否则隐藏,
                    if (activity.getFeeTips().getData().getInfo().getId() == 0) {
                        myRights.setText("未开通");
                        flag = 1;
                    } else {
                        if (activity.getFeeTips().getData().getInfo().getServStatus() == 2) {//服务中
                            switch (activity.getFeeTips().getData().getInfo().getStoreType()) {
                                case 1:
                                    myRights.setText("旗舰店");
                                    break;
                                case 2:
                                    myRights.setText("专卖店");
                                    break;
                                case 3:
                                    myRights.setText("未缴费");
                                    break;
                            }
                        } else {
                            myRights.setText("试营业");
                        }
                    }
                }
            } else if (PublicCache.loginSupplier.getAuthStatus() == 2) {//审核驳回
                statusIcon.setBackgroundResource(R.mipmap.dismissed3x);
                shopStatus.setText("审核驳回");
            } else {//审核中
                statusIcon.setBackgroundResource(R.mipmap.authing3x);
                shopStatus.setText("正在审核");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    public void stateUpdate(Map<String, Object> map) {
        getRequestPresenter().offToday(map, new RequestCallback<OffToday>() {
            @Override
            public void onSuc(OffToday body) {
                loadingDimss();
                shopState();
                if (PublicCache.loginSupplier == null) return;
                PublicCache.loginSupplier.setStoreStatus(state);
                PublicCache.loginSupplier.update();
            }

            @Override
            public void onFailed(int offToday, String msg) {
                UIUtils.showToastSafesShort(msg);
                loadingDimss();
            }
        });
    }
}