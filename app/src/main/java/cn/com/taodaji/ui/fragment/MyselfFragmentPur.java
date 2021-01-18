package cn.com.taodaji.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.activity.StatusBarBaseActivity;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.ColorUtil;
import com.base.utils.JavaMethod;
import com.base.utils.MySpecialStyle;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.BuyIntegral;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.DuiBaRegisterUrl;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.OrderStatusCount;
import cn.com.taodaji.model.entity.WaitCountResultBean;
import cn.com.taodaji.model.event.FavoriteEvent;
import cn.com.taodaji.model.event.Login_out;
import cn.com.taodaji.model.event.NotificationCountEvent;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.model.event.UserVisibleEvent;
import cn.com.taodaji.model.event.WaitAddEvent;
import cn.com.taodaji.model.event.WaitCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.cashCoupon.CashCouponActivity;
import cn.com.taodaji.ui.activity.evaluate.EvaluateManageActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.integral.IntegralShopMainActivity;
import cn.com.taodaji.ui.activity.integral.ShopVipActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.popuwindow.CheckSuccessPopupWindow;
import cn.com.taodaji.ui.activity.integral.tools.CurrentItem;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import cn.com.taodaji.ui.activity.myself.FavoriteActivity;
import cn.com.taodaji.ui.activity.myself.GoodsNeedActivity;
import cn.com.taodaji.ui.activity.myself.LeagueActivity;
import cn.com.taodaji.ui.activity.myself.MeaningPostActivity;
import cn.com.taodaji.ui.activity.myself.NoticeWaitManageActivity;
import cn.com.taodaji.ui.activity.myself.SettingPurchaserActivity;
import cn.com.taodaji.ui.activity.myself.ShareActivity;
import cn.com.taodaji.ui.activity.myself.SypplyNameCardActivity;
import cn.com.taodaji.ui.activity.openTicket.TicketStatusActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesListActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.ui.activity.packingCash.PackingCashCurrentActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseMoneyListActivity;
import cn.com.taodaji.ui.activity.shopManagement.AddressManagementActivity;
import cn.com.taodaji.ui.activity.shopManagement.StoreImageActivity;
import cn.com.taodaji.ui.activity.shopManagement.StoresManagementActivity;
import cn.com.taodaji.ui.activity.tdjc.CommissionerPoupWindow;
import cn.com.taodaji.ui.activity.tdjc.MyPickupCodeActivity;
import cn.com.taodaji.ui.activity.tdjc.PickUpOrderActivity;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyActivity;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import cn.com.taodaji.viewModel.adapter.SimplePPWAdapter;
import tools.extend.PhoneUtils;
import tools.fragment.DataBaseFragment;

/**
 * Created by yangkuo on 2018/12/18.
 */
public class MyselfFragmentPur extends DataBaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_recyclerView;


    private SimplePPWAdapter adapter;
//    private RelativeLayout fab;

    private boolean isFirst=false;
    private boolean isGetData=false;

    private CheckSuccessPopupWindow successPopuoWindow;
    private int  wait_total;
    private String commissionerTelephone,commissionerName;
    private CommissionerPoupWindow commissionerPoupWindow;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_myself_pur, container, false);
        swipeRefreshLayout = mainView.findViewById(R.id.swipeRefreshLayout);
//        fab=mainView.findViewById(R.id.fab);
//        fab.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_recyclerView = mainView.findViewById(R.id.rv_recyclerView);
        rv_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SimplePPWAdapter();
        rv_recyclerView.setAdapter(adapter);


        return mainView;
    }
    //可见
    @Subscribe
    public void firstVisible(UserVisibleEvent event) {

        isFirst=true;

        if (wait_total>0&&isFirst&&isGetData) {
            initNoticeDialog(wait_total);
        }
    }
    @Override
    public void initData() {
        super.initData();

        initTitleView();
        initCheckView();
        iniEnshrineView();

        initOrderView();
        initToolsView();
        initHelpServiceView();
        if (checkedLoginState()) onRefresh();
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        //初始化数据
        if (checkedLoginState()) onRefresh();
        else {
            initTitleView();
            initCheckView();
            iniEnshrineView();
        }
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();

        //初始化数据
        if (checkedLoginState()) {
            onRefresh();
        } else {
            initTitleView();
            initCheckView();
            iniEnshrineView();
        }
    }

    //显示数量变化通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NotificationCountEvent event) {
        onRefresh();
    }


//    //登录
//    @Subscribe
//    public void update(Login_in login_in) {
//        Log.e("Login_in", getClass().getSimpleName());
//        ((ManageActivity) getActivity()).initializtionData();
//        initTitleView();
//        initCheckView();
//        iniEnshrineView();
//        onRefresh();
//    }


    public void login_in() {
        ((ManageActivity) getActivity()).initializtionData();
        initTitleView();
        initCheckView();
        iniEnshrineView();
        onRefresh();
    }

    public void login_out() {
        initTitleView();
        initCheckView();
        iniEnshrineView();
        orderStatusCount(new OrderStatusCount());
        onRefresh();
    }

//    //退出登录
    @Subscribe
    public void onEvent(Login_out login_out) {
        initTitleView();
        initCheckView();
        iniEnshrineView();
        orderStatusCount(new OrderStatusCount());
    }

    /**
     * 初始化标题
     */
    private GlideImageView giv_shop_logo;
    private View iv_setting, iv_backlog;
    private TextView tv_unread_news, tv_shop_name, tv_person, tv_shop_manage,tv_check_everyday,tv_vip;
    private View title_view;
    private ImageView iv_commissioner;

    private void initTitleView() {
        if (rv_recyclerView == null) {
            return;
        }
        if (title_view == null) {
            title_view = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_title);
            adapter.addHeaderView(title_view);

            giv_shop_logo = title_view.findViewById(R.id.giv_shop_logo);
            giv_shop_logo.setOnClickListener(this);

            tv_check_everyday = title_view.findViewById(R.id.tv_check_everyday);
            tv_check_everyday.setOnClickListener(this);

            iv_commissioner= title_view.findViewById(R.id.iv_commissioner);
            iv_commissioner.setOnClickListener(this);

            tv_vip = title_view.findViewById(R.id.tv_vip);
            tv_vip.setOnClickListener(this);

            iv_setting = title_view.findViewById(R.id.iv_setting);
            iv_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PublicCache.loginPurchase==null) {
                        LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                    }else {
                        Intent intent = new Intent(getActivity(), SettingPurchaserActivity.class);
                        startActivity(intent);
                    }

//                startActivity(new Intent(getBaseActivity(), InvoiceTitleActivity.class));
                }
            });
            iv_backlog = title_view.findViewById(R.id.iv_backlog);
            iv_backlog.setOnClickListener(this);

            tv_unread_news = title_view.findViewById(R.id.tv_unread_news);
            tv_shop_name = title_view.findViewById(R.id.tv_shop_name);
            tv_shop_name.setOnClickListener(this);
            tv_person = title_view.findViewById(R.id.tv_person);
            tv_shop_manage = title_view.findViewById(R.id.tv_shop_manage);
            tv_shop_manage.setOnClickListener(this);



            //修改的布局
            tv_balance = title_view.findViewById(R.id.tv_balance);
            tv_cash_coupon_count = title_view.findViewById(R.id.tv_cash_coupon_count);
            tv_integral = title_view.findViewById(R.id.tv_integral);
            tv_good_enshrine_count = title_view.findViewById(R.id.tv_good_enshrine_count);
            title_view.findViewById(R.id.ll_good_enshrine).setOnClickListener(this);
            title_view.findViewById(R.id.ll_balance).setOnClickListener(this);
            title_view.findViewById(R.id.ll_cash_coupon_count).setOnClickListener(this);
            title_view.findViewById(R.id.ll_integral).setOnClickListener(this);
            if (PublicCache.loginPurchase == null) {
                tv_balance.setText("--");
                tv_cash_coupon_count.setText("--");
                tv_integral.setText("--");
                tv_good_enshrine_count.setText("--");
                LogUtils.e("--");
             }
        }


        if (getActivity() != null) {
            ((StatusBarBaseActivity) getActivity()).setStatusBarDrawableRes(R.drawable.bg_test);
        }
        if (swipeRefreshLayout != null) {
            if (PublicCache.loginPurchase != null) {
                swipeRefreshLayout.setColorSchemeResources(R.color.orange_yellow_ff7d01);
            } else if (PublicCache.loginSupplier != null) {
                swipeRefreshLayout.setColorSchemeResources(R.color.blue_2898eb);
            } else
                swipeRefreshLayout.setColorSchemeResources(R.color.orange_yellow_ff7d01, R.color.blue_2898eb);
        }

        giv_shop_logo.setImageBitmap(BitmapUtil.toRoundBitmap(R.drawable.head_portrait));
       // giv_shop_logo.setOnClickListener(null);
        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getImgCheckStatus()==1) {
                giv_shop_logo.loadImage(PublicCache.loginPurchase.getImageUrl());
            }


            tv_shop_manage.setVisibility(View.VISIBLE);
            if (PublicCache.loginPurchase.getIsC()==1){
//                tv_person.setText("(家用)");
                tv_person.setText("");
                tv_shop_name.setText(PublicCache.loginPurchase.getRealname().trim());
                tv_shop_manage.setText("收货地址管理");
            }else {
                tv_shop_name.setText(PublicCache.loginPurchase.getHotelName().trim());
                tv_person.setText("(" +PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())  + ")");

            }


        } else {
            tv_unread_news.setVisibility(View.GONE);
            tv_shop_name.setText("登录 / 注册");
            tv_person.setText("");
            tv_shop_manage.setVisibility(View.GONE);
            tv_vip.setVisibility(View.GONE);
            tv_check_everyday.setVisibility(View.GONE);
        }


    }

    /**
     * 审核是否通过
     */
    private View checkView;//审核是否通过
//    private View pickupView;

    private void initCheckView() {
        if (rv_recyclerView == null) {
            return;
        }
        if (checkView == null) {
            checkView = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_check);
        }

//        if (pickupView == null) {
//            pickupView = ViewUtils.getFragmentView(rv_recyclerView, R.layout.myself_pur_pickup_item);
//        }
        RelativeLayout rl_thm=checkView.findViewById(R.id.rl_thm);
        RelativeLayout rl_wth=checkView.findViewById(R.id.rl_wth);
        LinearLayout ll_qh=checkView.findViewById(R.id.ll_qh);
        LinearLayout ll=checkView.findViewById(R.id.ll);
        rl_thm.setOnClickListener(this);
        rl_wth.setOnClickListener(this);

  /*      if (PublicCache.loginPurchase!=null){

        }
*/
        TextView tv_hint_message = checkView.findViewById(R.id.tv_hint_message);
        TextView tv_hint_status = checkView.findViewById(R.id.tv_hint_status);

//        checkView.findViewById(R.id.tv_hint_message).setOnClickListener(this);
        checkView.findViewById(R.id.bt_call_phone).setOnClickListener(this);
        if (PublicCache.loginPurchase != null) {

//            PublicCache.loginPurchase.setAuthStatus(2);

            //authStaus   -1,0 ： 审核中    1：审核成功  2：审核失败
            // "authStatus": "0 已经提交（待审核）,1 已认证（审核通过），2 驳回  （xxxxx?）",
            if (PublicCache.loginPurchase.getAuthStatus() == 1) {
//                adapter.removeHeard(checkView);
                adapter.addHeaderView(checkView);
                ll.setVisibility(View.GONE);
            } else {
                ll.setVisibility(View.VISIBLE);
                adapter.addHeaderView(checkView);
//                adapter.addHeaderView(checkView,1);
                switch (PublicCache.loginPurchase.getAuthStatus()) {
                    case -1:
                    case 0:
                        tv_hint_status.setText("正在审核中");
                        //tv_hint_message.setText(PublicCache.loginPurchase.getVerifyInfo());
                          tv_hint_message.setText("提示：等待平台审核，审核通过方可下单采购。");
                        break;
                    case 2:
                        tv_hint_status.setText("驳回审核");
                        tv_hint_message.setText("驳回审核原因："+PublicCache.loginPurchase.getVerifyInfo());
                       // tv_hint_message.setText("请重新上传相关资料，再次审核。");
                        break;
                }
            }

            if (PublicCache.loginPurchase.getIsC()==1 ){
                ll_qh.setVisibility(View.VISIBLE);
//                ll_qh.setVisibility(View.GONE);
            }else {
                ll_qh.setVisibility(View.GONE);
            }
        } else {
            //                adapter.removeHeard(checkView);
            adapter.addHeaderView(checkView);
            ll.setVisibility(View.GONE);
        }


    }

    private void getNoticeDialogData() {

        addRequest(ModelRequest.getInstance(1).getWaitCount(PublicCache.loginPurchase.getEmpRole(),PublicCache.site_login, PublicCache.loginPurchase.getLoginUserId()), new RequestCallback<WaitCountResultBean>() {
            @Override
            protected void onSuc(WaitCountResultBean body) {
                if (body.getData()!=null) {
                    int total=body.getData().getTotal();
                    isGetData=true;

                    EventBus.getDefault().postSticky(new WaitCountEvent(total));

                    if (tv_unread_news != null){
                        if (total == 0) {
                            tv_unread_news.setVisibility(View.INVISIBLE);
                        } else {
                            tv_unread_news.setVisibility(View.VISIBLE);
                            tv_unread_news.setText(String.valueOf(total));
                        }
                    }

                    if (total>0&&total != wait_total) {
                        wait_total=total;
                        if (wait_total > 0) {
                            isGetData=true;
                        }
                    }else {
                        isGetData=false;
                    }
                }
            }
        });


    }

    private void initNoticeDialog(int count){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        View view = View.inflate(getBaseActivity(), R.layout.dialog_wait_notice, null);

        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(com.base.R.color.transparent);

        MySpecialStyle style = new MySpecialStyle();
        SpecialStringBuilder sb = new SpecialStringBuilder();

        style.setColor(ColorUtil.getColor(R.color.black_63));
        sb.append("您有", style);

        style.setColor(ColorUtil.getColor(com.base.R.color.orange_yellow_ff7d01));
        sb.append(count + "条", style);

        style.setColor(ColorUtil.getColor(R.color.black_63));
        sb.append("新的待办申请需要处理", style);

        TextView text_tips=view.findViewById(R.id.text_tips);
        text_tips.setText(sb.getCharSequence());

        ImageView img_look=view.findViewById(R.id.img_look);
        img_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), NoticeWaitManageActivity.class);
                startActivity(intent);
            }
        });
        ImageView img_close=view.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        isFirst=false;
        isGetData=false;
    }
    /**
     * 收藏和关注
     *
     * @return
     */
    private TextView tv_balance, tv_cash_coupon_count, tv_integral, tv_good_enshrine_count, tv_attention_shop_count;
    private View enshrineView;
    private ImageView iv_bg_title;

    private void iniEnshrineView() {


//
//        if (enshrineView == null) {
//            if (rv_recyclerView == null) {
//                return;
//            }
//            enshrineView = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_enshrine);
//            adapter.addHeaderView(enshrineView);
//
//            iv_bg_title = enshrineView.findViewById(R.id.iv_bg_title);
//
//            tv_balance = enshrineView.findViewById(R.id.tv_balance);
//            tv_cash_coupon_count = enshrineView.findViewById(R.id.tv_cash_coupon_count);
//            tv_integral = enshrineView.findViewById(R.id.tv_integral);
//            tv_good_enshrine_count = enshrineView.findViewById(R.id.tv_good_enshrine_count);
//            tv_attention_shop_count = enshrineView.findViewById(R.id.tv_attention_shop_count);
//
//            enshrineView.findViewById(R.id.ll_good_enshrine).setOnClickListener(this);
//            enshrineView.findViewById(R.id.ll_attention_shop).setOnClickListener(this);
//
//
//            enshrineView.findViewById(R.id.ll_balance).setOnClickListener(this);
//            enshrineView.findViewById(R.id.ll_cash_coupon_count).setOnClickListener(this);
//            enshrineView.findViewById(R.id.ll_integral).setOnClickListener(this);
//        }
//
//        if (adapter.isHasHeader(checkView)) {
//            iv_bg_title.setVisibility(View.GONE);
//        } else iv_bg_title.setVisibility(View.VISIBLE);
//
//
//        if (PublicCache.loginPurchase != null) {
//            tv_balance.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
//            tv_cash_coupon_count.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
//            tv_integral.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
//
//            tv_good_enshrine_count.setTextColor(UIUtils.getColor(R.color.red_dark));
//            tv_attention_shop_count.setTextColor(UIUtils.getColor(R.color.red_dark));
//
//        } else {
//            tv_balance.setTextColor(UIUtils.getColor(R.color.black_63));
//            tv_balance.setText("--");
//
//            tv_cash_coupon_count.setTextColor(UIUtils.getColor(R.color.black_63));
//            tv_cash_coupon_count.setText("--");
//
//            tv_integral.setTextColor(UIUtils.getColor(R.color.black_63));
//            tv_integral.setText("--");
//
//            tv_good_enshrine_count.setTextColor(UIUtils.getColor(R.color.black_63));
//            tv_good_enshrine_count.setText("         --");
//
//            tv_attention_shop_count.setTextColor(UIUtils.getColor(R.color.black_63));
//            tv_attention_shop_count.setText("         --");
//        }


    }

    /**
     * 我的订单
     *
     * @return
     */
    private SimpleMyselfFunctionAdapter myRecyclerViewAdapter_6;

    private void initOrderView() {
        View view = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_order);

        adapter.addHeaderView(view);
        view.findViewById(R.id.ll_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                EventBus.getDefault().postSticky(new OrderEvent(0));
                startActivity(intent);
            }
        });


        view.findViewById(R.id.iv_title_line).setVisibility(View.GONE);

        RecyclerView rv_recyclerView_order = view.findViewById(R.id.rv_recyclerView_order);
        myRecyclerViewAdapter_6 = new SimpleMyselfFunctionAdapter();
        rv_recyclerView_order.setAdapter(myRecyclerViewAdapter_6);
        rv_recyclerView_order.setLayoutManager(new GridLayoutManager(getContext(), 5));
        myRecyclerViewAdapter_6.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        myRecyclerViewAdapter_6.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    if (!checkedLoginState()) {
                        LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                        return true;
                    }
                    if (position == 4) {
                        AfterSalesListActivity.startActivity(getBaseActivity());
                    }
                  else {
                        Intent intent = new Intent(getActivity(), OrderListActivity.class);
                        EventBus.getDefault().postSticky(new OrderEvent(position + 1));
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        String[] titles = new String[]{"待付款", "待发货", "待收货", "待评价", "退换/售后"};
        int[] imageID = new int[]{R.mipmap.ic_pay_wait, R.mipmap.ic_shipments_wait, R.mipmap.ic_receive_goods_wait, R.mipmap.ic_evaluate_wait, R.mipmap.ic_sales_return};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        myRecyclerViewAdapter_6.setList(list);



    }

    /**
     * 常用工具
     *
     * @return
     */
    private int wait_evaluate;

    private void initToolsView() {
        View view = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_order);
        adapter.addHeaderView(view);
        view.findViewById(R.id.ll_look_more).setVisibility(View.GONE);
        TextView tv_title_text = view.findViewById(R.id.tv_title_text);
        tv_title_text.setText("常用工具");

        RecyclerView rv_recyclerView_order = view.findViewById(R.id.rv_recyclerView_order);
        SimpleMyselfFunctionAdapter myRecyclerViewAdapter_6 = new SimpleMyselfFunctionAdapter();
        rv_recyclerView_order.setAdapter(myRecyclerViewAdapter_6);
        rv_recyclerView_order.setLayoutManager(new GridLayoutManager(getContext(), 4));
        myRecyclerViewAdapter_6.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        myRecyclerViewAdapter_6.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    if (!checkedLoginState()) {
                        LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                        return true;
                    }
                    Intent intent;
                    switch (position) {
                        case 0:
                            PurchaseMoneyListActivity.startActivity(getContext(), 1);
                            break;
                        case 1:
                            intent = new Intent(getContext(), EvaluateManageActivity.class);
                            intent.putExtra("wait_evaluate", wait_evaluate);
                            startActivity(intent);
                            break;
                        case 2:
                            /**
                             * 采购商的身份
                             * role: 0 表示主账号、1表示子账号厨师  2表示子账号财务 3表示子账号管理员
                             */
                            if (PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getEmpRole()==5) {
                                UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                                return true;
                            }
                          /*  if (PublicCache.loginPurchase != null && (PublicCache.loginPurchase.getRole() == 0 || PublicCache.loginPurchase.getRole() == 3)) {
                                *//** 子账号管理*//*
                                intent = new Intent(getActivity(), SubAccountActivity.class);
                                startActivity(intent);
                            } else {
                                UIUtils.showToastSafesShort("没有子账号管理权限");
                            }*/
                            if (!checkedLoginState()) {
                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                                return true;
                            }
                            intent = new Intent(getBaseActivity(), TicketStatusActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(getActivity(), PackingCashCurrentActivity.class);
                            startActivity(intent);
                            break;
                    }

                    return true;
                }
                return false;
            }
        });

        String[] titles = new String[]{"对账单", "我的评价", "开具专票", "退押金"};
        int[] imageID = new int[]{R.mipmap.ic_order_list, R.mipmap.ic_evaluate, R.mipmap.ic_invoice, R.mipmap.ic_return_deposit};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        myRecyclerViewAdapter_6.setList(list);
    }

    /**
     * 帮助服务
     *
     * @return
     */

    private void initHelpServiceView() {
        View view = ViewUtils.getFragmentView(rv_recyclerView, R.layout.fragment_myself_pur_item_order);
        adapter.addHeaderView(view);
        view.findViewById(R.id.ll_look_more).setVisibility(View.GONE);
        TextView tv_title_text = view.findViewById(R.id.tv_title_text);
        tv_title_text.setText("帮助服务");

        RecyclerView rv_recyclerView_order = view.findViewById(R.id.rv_recyclerView_order);
        SimpleMyselfFunctionAdapter myRecyclerViewAdapter_6 = new SimpleMyselfFunctionAdapter();
        rv_recyclerView_order.setAdapter(myRecyclerViewAdapter_6);
        rv_recyclerView_order.setLayoutManager(new GridLayoutManager(getContext(), 4));
        myRecyclerViewAdapter_6.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        myRecyclerViewAdapter_6.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {


                    switch (position) {
                        case 0:
                           /* intent = new Intent(getBaseActivity(), HelpCenterActivity.class);
                            startActivity(intent);*/
                            LeagueActivity.startActivity(getContext(),1);
                            break;
                        case 1:
                            ShareActivity.startActivity(getContext());
                            break;
                        case 2:
                            LeagueActivity.startActivity(getContext(),0);
                            break;
                        case 3:
//                            if (!checkedLoginState()) {
//                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
//                                return true;
//                            }
                            /** 拨打电话*/
                            SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                            break;
                        case 4:
                            if (!checkedLoginState()) {
                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                                return true;
                            }
                            Intent intent = new Intent(getBaseActivity(), MeaningPostActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            if (!checkedLoginState()) {
                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                                return true;
                            }
                            Intent   intent1 = new Intent(getBaseActivity(), GoodsNeedActivity.class);
                            startActivity(intent1);
                            break;
                        case 6:
                            if (!checkedLoginState()) {
                                LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                                return true;
                            }

                            getPartnerUser();

                            break;
                        case 7:
                            Intent intent2=new Intent(getContext(), LeagueActivity.class);
                            intent2.putExtra("urlType",3);
                            startActivity(intent2);

                            break;
                    }
                    return true;
                }
                return false;
            }
        });

        String[] titles = new String[]{"帮助中心", "邀请有礼", "我要合作", "客服电话","意见反馈","新品需求","申请创客","申请团长"};
        int[] imageID = new int[]{R.mipmap.ic_help_center, R.mipmap.ic_share, R.mipmap.ic_cooperation, R.mipmap.ic_cs, R.mipmap.ic_mean,R.mipmap.xinping_bg,R.mipmap.hezuo_bg,R.mipmap.tuanzhang};

        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        myRecyclerViewAdapter_6.setList(list);
    }



    public void orderStatusCount(OrderStatusCount event) {
        if (myRecyclerViewAdapter_6 == null || myRecyclerViewAdapter_6.getRealCount() == 0) return;
        SparseArrayCompat<Map<String, Object>> sparry = new SparseArrayCompat<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("goodsCount", event.getWait_buyer_pay());
        sparry.put(0, map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("goodsCount", event.getWait_seller_send_goods() + event.getWait_seller_confirm_goods());
        sparry.put(1, map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("goodsCount", event.getWait_buyer_confirm_goods());
        sparry.put(2, map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("goodsCount", event.getTrade_success() + event.getWait_buyer_evaluate());
        sparry.put(3, map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("goodsCount", event.getAfterSalesCount());
        sparry.put(4, map5);


        wait_evaluate = event.getTrade_success() + event.getWait_buyer_evaluate();


        for (int i = 0; i < sparry.size(); i++) {
            myRecyclerViewAdapter_6.update(sparry.keyAt(i), sparry.valueAt(i));
        }

    }

    private boolean checkedLoginState() {
        //在页面显示时验证是否登录
        if (PublicCache.loginPurchase == null ) {
            // LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return false;
        }
        return true;
    }


    private int messCount;//通知数量

    @Override
    public void onRefresh() {
        if (PublicCache.loginPurchase != null) {
            int en;
          /*  if (PublicCache.loginPurchase.getRole() == 0)
                en = PublicCache.loginPurchase.getEntityId();
            else en = PublicCache.loginPurchase.getSubUserId();*/

            en = PublicCache.loginPurchase.getEntityId();// flag //0-刷新门店信息 1-获取当前登陆用户信息

            getRequestPresenter().customer_refreshInfo(en,0,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<MyselftUpdateP>(getBaseActivity()) {
                @Override
                public void onSuc(MyselftUpdateP event) {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (PublicCache.loginPurchase.getIsC()==0){


                        if (UIUtils.isNullOrZeroLenght(event.getData().getCommissionerTelephone())){
                            iv_commissioner.setVisibility(View.GONE);
                        }else {
                            iv_commissioner.setVisibility(View.VISIBLE);
                        }

                        commissionerTelephone=event.getData().getCommissionerTelephone();
                        commissionerName=event.getData().getCommissionerName();
                    }

                    if (tv_good_enshrine_count != null)
                        tv_good_enshrine_count.setText((event.getData().getFavoriteProdCount()+event.getData().getFavoriteStoreCount()) + "");
                    if (tv_attention_shop_count != null)
                        tv_attention_shop_count.setText(event.getData().getFavoriteStoreCount() + ">>");
                    if (tv_cash_coupon_count != null)
                        tv_cash_coupon_count.setText(event.getData().getCouponItemsCount() + "");

                    PublicCache.isInvoice = event.getData().getIsInvoice();
                    if (PublicCache.loginPurchase == null) return;
                    JavaMethod.copyValue(PublicCache.loginPurchase, event.getData());
                    PublicCache.loginPurchase.update();

                    orderStatusCount(event.getData().getOrderNumber());
                    PublicCache.notifycationCount -= messCount;
                    messCount = setIconCount(event.getData().getOrderNumber(), event.getData().getPushMessageNotReadCount());
                    PublicCache.notifycationCount += messCount;
                    if (PublicCache.loginPurchase.getAuthStatus() == 1){
//                        fab.setVisibility(View.VISIBLE);
                        tv_vip.setVisibility(View.VISIBLE);
                        tv_check_everyday.setVisibility(View.VISIBLE);
                    }else {
//                        fab.setVisibility(View.GONE);
                        tv_vip.setVisibility(View.GONE);
                        tv_check_everyday.setVisibility(View.GONE);
                    }
                    if (tv_balance != null)
                        tv_balance.setText(String.valueOf(event.getData().getMoney()));

                    //设置积分；
                    tv_integral.setText(event.getData().getIntegral()+"");
                    //设置等级；
                    if (event.getData().getLevel()==0){
                        tv_vip.setText("铜牌会员");
                    }else if (event.getData().getLevel()==1){
                        tv_vip.setText("银牌会员");
                    }else if (event.getData().getLevel()==2){
                        tv_vip.setText("金牌会员");
                    }else if (event.getData().getLevel()==3){
                        tv_vip.setText("钻石会员");
                    }else if (event.getData().getLevel()==4){
                        tv_vip.setText("至尊会员");
                    }

                    //签到；
                        tv_check_everyday.setText(event.getData().getIsSign()==1?"已签到":"去签到");

                    getNoticeDialogData();
                    //初始化数据
                    initTitleView();
                    initCheckView();
                    iniEnshrineView();

                    rv_recyclerView.scrollToPosition(0);

                }

                @Override
                public void onFailed(int myselftUpdateP, String msg) {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            if (PublicCache.loginPurchase == null) {
                tv_balance.setText("--");
                tv_cash_coupon_count.setText("--");
                tv_integral.setText("--");
                tv_good_enshrine_count.setText("--");
                LogUtils.e("--");
            }
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public int setIconCount(OrderStatusCount orderStatusCount, int pushMessageNotReadCount) {
        int count = 0;
        count += orderStatusCount.getWait_buyer_pay();
        count += orderStatusCount.getWait_seller_send_goods() + orderStatusCount.getWait_seller_confirm_goods();
        count += orderStatusCount.getWait_buyer_confirm_goods();
        count += orderStatusCount.getTrade_success() + orderStatusCount.getWait_buyer_evaluate();
        count += pushMessageNotReadCount;
        return count;
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

    @Subscribe(sticky = true)
    public void onEvent(WaitCountEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        int count=event.getCount();
        if (count >0) {
            tv_unread_news.setVisibility(View.VISIBLE);
            tv_unread_news.setText(String.valueOf(count));
        }else {
            tv_unread_news.setVisibility(View.INVISIBLE);
        }


    }
    //头像上传完毕后返回
    @Subscribe
    public void onEvent(ImageUploadOk ok) {
        if (ok.getData() instanceof Boolean) return;
        if (PublicCache.loginPurchase != null)
            ImageLoaderUtils.loadImage(giv_shop_logo, PublicCache.loginPurchase.getImageUrl());
    }
    //接收支付成功通知
    @Subscribe
    public void onEvent(WaitAddEvent event) {
        //通知订单列表和详情支付成功
        getNoticeDialogData();
    }
    @Override
    public void onClick(View v) {
        if (!checkedLoginState()) {
            LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return;
        }
        Intent intent;
        switch (v.getId()) {

            case R.id.iv_backlog:
                /** 消息*/
                intent = new Intent(getActivity(), NoticeWaitManageActivity.class);//NewsListActivity
                startActivity(intent);
                break;
            case R.id.giv_shop_logo:
                if (PublicCache.loginPurchase != null) {
                    if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3||PublicCache.loginPurchase.getEmpRole()==4) {
                        intent = new Intent(getActivity(), StoreImageActivity.class);
                        intent.putExtra("url", PublicCache.loginPurchase.getImageUrl());
                        intent.putExtra("imgStatus",PublicCache.loginPurchase.getImgCheckStatus()) ;
                        startActivity(intent);//
                    }

                }

                break;
            case R.id.tv_shop_manage:
                /** 我的名片*/
                if (PublicCache.loginPurchase != null) {
                    if (PublicCache.loginPurchase.getIsC()==1){

                   intent = new Intent(getBaseActivity(), AddressManagementActivity.class);
                   intent.putExtra("id",PublicCache.loginPurchase.getEntityId());
                    startActivity(intent);
                    }else {
                        intent = new Intent(getBaseActivity(), StoresManagementActivity.class);
                        startActivity(intent);
                    }

                } else if (PublicCache.loginSupplier != null) {
                    intent = new Intent(getBaseActivity(), SypplyNameCardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bt_call_phone:
                /** 拨打电话*/
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
            case R.id.ll_good_enshrine:
                EventBus.getDefault().postSticky(new FavoriteEvent(2));
                startActivity(new Intent(getContext(), FavoriteActivity.class));
                break;
            case R.id.ll_attention_shop:
                EventBus.getDefault().postSticky(new FavoriteEvent(1));
                startActivity(new Intent(getContext(), FavoriteActivity.class));
                break;
            case R.id.ll_balance:
                SupplyMoneyActivity.startActivity(getContext());
                break;
            case R.id.ll_cash_coupon_count:
                CashCouponActivity.startActivity(getContext(), 0);
                break;
            case R.id.ll_integral:
                Intent intent1=new Intent(getContext(), IntegralShopMainActivity.class);
                intent1.putExtra("current","2");
                startActivity(intent1);

                break;
            case R.id.tv_vip:
                Intent intentVip = new Intent(getContext(), ShopVipActivity.class);
                startActivity(intentVip);
                break;

      /*      case R.id.fab:
                if (ClickCheckedUtil.onClickChecked(2000)){
                    Intent intent2=new Intent(getContext(), IntegralShopMainActivity.class);
                    startActivity(intent2);
                }

                break;*/
            case R.id.tv_check_everyday:
                if (tv_check_everyday.getText().toString().equals("去签到")){
                    if (ClickCheckedUtil.onClickChecked(2000)){
                        integral_signIn();
                    }
                }
                break;

            case R.id.rl_thm:
                Intent intent_code = new Intent(getContext(), MyPickupCodeActivity.class);
                startActivity(intent_code);

                break;
            case R.id.rl_wth:
                Intent intent_Order = new Intent(getContext(), PickUpOrderActivity.class);
                startActivity(intent_Order);

                break;
            case R.id.iv_commissioner:
                if (commissionerPoupWindow != null) {
                    if (commissionerPoupWindow.isShowing()) {
                        return;
                    }

                }
                commissionerPoupWindow = new CommissionerPoupWindow(  commissionerName,commissionerTelephone, getBaseActivity());
                commissionerPoupWindow.setPopupWindowFullScreen(true);//铺满
                commissionerPoupWindow.setDismissWhenTouchOutside(false);
                commissionerPoupWindow.setInterceptTouchEvent(false);
                commissionerPoupWindow.setBackPressEnable(false);
                commissionerPoupWindow.showPopupWindow();
                break;
        }

    }
    public void getPartnerUser(){
        ShowLoadingDialog.showLoadingDialog(getContext());
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getPartnerUser(map, new RequestCallback<BuyIntegral>(this) {
            @Override
            public void onSuc(BuyIntegral body) {
                ShowLoadingDialog.close();
                if (body.getData().equals("0")){
                    Intent intent2=new Intent(getContext(), WebViewActivity.class);
                    intent2.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-partner/partner/userApply/partnerPage?userId="+PublicCache.loginPurchase.getEntityId());
                    startActivity(intent2);
                }else {
                    UIUtils.showToastSafe(body.getMsg());
                }


            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });


    }


    public void integral_signIn(){
        ShowLoadingDialog.showLoadingDialog(getContext());
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().integral_signIn(map, new RequestCallback<CartQuantity>(this) {
            @Override
            public void onSuc(CartQuantity body) {
                ShowLoadingDialog.close();
                tv_check_everyday.setText("已签到");
                tv_integral.setText(body.getData().getTotalIntegral()+"");
                if (successPopuoWindow!=null){
                    if (successPopuoWindow.isShowing()){
                        return;
                    }
                }
                successPopuoWindow= new CheckSuccessPopupWindow(getActivity(),body.getMsg());
                successPopuoWindow.setDismissWhenTouchOutside(false);
                successPopuoWindow.setInterceptTouchEvent(false);
                successPopuoWindow.setPopupWindowFullScreen(true);//铺满
                successPopuoWindow.showPopupWindow();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });


    }
}
