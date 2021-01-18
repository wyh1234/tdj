package cn.com.taodaji.ui.activity.integral.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.viewModel.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.GetPointsItem;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.IntegralShopMy;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.integral.BuyIntegralActivity;
import cn.com.taodaji.ui.activity.integral.IntegralShopMainActivity;
import cn.com.taodaji.ui.activity.integral.PointsDetailActivity;
import cn.com.taodaji.ui.activity.integral.PointsShopRulesActivity;
import cn.com.taodaji.ui.activity.integral.ShopVipActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.popuwindow.CheckSuccessPopupWindow;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.myself.ShareActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesListActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.viewModel.adapter.GetPointsAdapter;
import cn.com.taodaji.viewModel.adapter.ShopAddressAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import cn.com.taodaji.viewModel.adapter.TimeLineAdapter;
import tools.fragment.DataBaseFragment;

public class IntegralShopMyFragment extends DataBaseFragment implements View.OnClickListener , ItemClickListener {
    private View mainView;
    private Unbinder unbinder;
    private List<IntegralShopMy.DataBean.ApproachsBean> itemList = new ArrayList<>();
    private GetPointsAdapter pointsAdapter;
    private SimpleMyselfFunctionAdapter myRecyclerViewAdapter_6;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.rv_timeline)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_get_points)
    RecyclerView pointsRecyclerView;
    @BindView(R.id.giv_user_icon)
    GlideImageView userIcon;
    @BindView(R.id.tv_vip)
    TextView vip;
    @BindView(R.id.tv_points_detail)
    TextView pointsDetail;
    @BindView(R.id.ll_checked_days)
    RelativeLayout checkedDays;
    @BindView(R.id.btn_go_check)
    Button goCheck;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone_num)
    TextView tv_phone_num;
    @BindView(R.id.tv_my_points)
    TextView tv_my_points;
    @BindView(R.id.tv_check_days)
    TextView tv_check_days;

    private CheckSuccessPopupWindow successPopuoWindow;
    private   TimeLineAdapter adapter;
    private  List<ADInfo> list;
    private IntegralShopMy body;
    private boolean falg;
    private IntegralShopMainActivity activity;

    public IntegralShopMy getBody() {
        return body;
    }

    public void setBody(IntegralShopMy body) {
        this.body = body;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_integral_mysellf, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        vip.setOnClickListener(this);
        pointsDetail.setOnClickListener(this);
        checkedDays.setOnClickListener(this);
        goCheck.setOnClickListener(this);
        userIcon.setImageBitmap(BitmapUtil.toRoundBitmap(R.drawable.head_portrait));
        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getImgCheckStatus() == 1) {
                userIcon.loadImage(PublicCache.loginPurchase.getImageUrl());
            }
        }
        title.setText("我的");
        initTimeLine();
        initOrderView();
        initGetPoints();
        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(IntegralShopMainActivity) context;
    }

    @Override
    public void initData() {
        super.initData();
        getUserAndApproach();

    }
    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (!ListUtils.isEmpty(itemList)){
            itemList.clear();
        }
        ShowLoadingDialog.close();

        getUserAndApproach();

    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        if (falg){
            if (!ListUtils.isEmpty(itemList)){
                itemList.clear();
            }
            getUserAndApproach();
            falg=false;
        }


    }

    public void getUserAndApproach(){
        ShowLoadingDialog.showLoadingDialog(getActivity());
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getUserAndApproach(map, new RequestCallback<IntegralShopMy>(this) {
            @Override
            public void onSuc(IntegralShopMy body) {

                setBody(body);
                ImageLoaderUtils.loadImage(userIcon,body.getData().getHeadUrl());
                tv_name.setText(body.getData().getNickName());
                tv_phone_num.setText(body.getData().getMobile());
                if (body.getData().getLevel()==0){
                    vip.setText("铜牌会员");
                }else if (body.getData().getLevel()==1){
                    vip.setText("银牌会员");
                }else if (body.getData().getLevel()==2){
                    vip.setText("金牌会员");
                }else if (body.getData().getLevel()==3){
                    vip.setText("钻石会员");
                }else if (body.getData().getLevel()==4){
                    vip.setText("至尊会员");
                }
                tv_my_points.setText(body.getData().getIntegral()+"");
                tv_check_days.setText(body.getData().getSignCount()+"");
                adapter.setCurrentNode(body.getData().getSignCount());
                goCheck.setSelected(body.getData().getIsSign()==0?true:false);
                goCheck.setText(body.getData().getIsSign()==0?"签到":"已签到");
                if (!ListUtils.isEmpty(list)){
                    list.get(1).setGoodsCount(body.getData().getMyWaiePayment());
                    myRecyclerViewAdapter_6.notifyDataSetChanged(list);
                }
                itemList.addAll(body.getData().getApproachs());
                pointsAdapter.notifyDataSetChanged();

                ShowLoadingDialog.close();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_vip:
                Intent intent = new Intent(getContext(), ShopVipActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_points_detail:
                Intent intent2 = new Intent(getContext(), PointsDetailActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_checked_days:
                Intent intent1 = new Intent(getContext(), WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/signRulePage");
                startActivity(intent1);
                break;
            case R.id.btn_go_check:
                if (goCheck.isSelected()){
                    if (ClickCheckedUtil.onClickChecked(2000)){
                        LogUtils.i(goCheck);
                        integral_signIn();
                    }


                }

                break;
                default:break;
        }
    }

    public void integral_signIn(){
        ShowLoadingDialog.showLoadingDialog(getActivity());
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().integral_signIn(map, new RequestCallback<CartQuantity>(this) {
            @Override
            public void onSuc(CartQuantity body) {
                ShowLoadingDialog.close();
                goCheck.setSelected(false);
                goCheck.setText("已签到");

               adapter.setCurrentNode(body.getData().getSignCount());
                tv_check_days.setText(body.getData().getSignCount()+"");
                tv_my_points.setText(body.getData().getTotalIntegral()+"");
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

    private void initTimeLine() {
         adapter = new TimeLineAdapter(getActivity(), 7);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    private void initOrderView() {
        RecyclerView rv_recyclerView_order = mainView.findViewById(R.id.rv_recyclerView_order);
        myRecyclerViewAdapter_6 = new SimpleMyselfFunctionAdapter();
        rv_recyclerView_order.setAdapter(myRecyclerViewAdapter_6);
        rv_recyclerView_order.setLayoutManager(new GridLayoutManager(getContext(), 3));
        myRecyclerViewAdapter_6.setViewSize(UIUtils.dip2px(33), UIUtils.dip2px(33));
        myRecyclerViewAdapter_6.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                String url = null;
                if (onclickType == 0) {
                    if (!checkedLoginState()) {
                        LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
                        return true;
                    }
                    Intent intent=new Intent(getContext(), WebViewActivity.class);
                    if (position==0){
                         url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());

                    }else if (position==1){
                        falg=true;
                        url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+
                                ( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=0";

                    }else if (position==2){
                        url=PublicCache.getROOT_URL().get(2)+"tdj-store/store/order/query/list/view?userId="+
                                ( PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId())+"&orderStatus=1";
                    }
                    intent.putExtra("url",url);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        String[] titles = new String[]{"全部", "待付款", "已付款"};
        int[] imageID = new int[]{R.mipmap.suoyou_bg, R.mipmap.daifukuan_bg, R.mipmap.yifukuan_bg};

        list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(String.valueOf(i));
            adInfo.setImageObject(imageID[i]);
            adInfo.setImageName(titles[i]);
            list.add(adInfo);
        }
        myRecyclerViewAdapter_6.setList(list);
    }

    public void initGetPoints(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        pointsRecyclerView.setLayoutManager(layoutManager);
        pointsRecyclerView.setNestedScrollingEnabled(false);
        pointsAdapter = new GetPointsAdapter(itemList,getActivity());
        pointsAdapter.setItemClickListener(this);
        pointsRecyclerView.setAdapter(pointsAdapter);

    }

    private boolean checkedLoginState() {
        //在页面显示时验证是否登录
        if (PublicCache.loginPurchase == null ) {
            // LoginMethod.getInstance(getBaseActivity()).toChooseLoginActivity();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View v, int position) {
        if (position==4){
            Intent intent=new Intent(getContext(), BuyIntegralActivity.class);
            startActivity(intent);

        }else if (position==0||position==1){
            ActivityManage.setTopActivity(ManageActivity.class);
            ManageActivity manageActivity = ActivityManage.getActivity(ManageActivity.class);
            if (manageActivity != null && manageActivity.mViewPager != null)
                manageActivity.mViewPager.setCurrentItem(0, false);
//            activity.mViewPager.setCurrentItem(0, false);
        }else if (position==2){
            Intent intent=new Intent(getContext(), ShareActivity.class);
            startActivity(intent);
        }else if (position==3){
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            EventBus.getDefault().postSticky(new OrderEvent(position + 1));
            startActivity(intent);
        }


    }
}
