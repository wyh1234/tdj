package cn.com.taodaji.ui.activity.myself;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ColorUtil;
import com.base.utils.MySpecialStyle;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ShareShopListResult;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.ppw.InviteTypePopupWindow;
import cn.com.taodaji.viewModel.adapter.ShareShopListAdapter;
import tools.activity.SimpleToolbarActivity;

public class ShareShopListActivity extends SimpleToolbarActivity implements View.OnClickListener, OnGetDataListener, SwipeRefreshLayout.OnRefreshListener {
    private List<ShareShopListResult.DataBean.ListBean> list = new ArrayList<>();
    private SwipeToLoadLayout swipeToLoadLayout;
    private LoadMoreUtil loadMoreUt;

    private   InviteTypePopupWindow   inviteTypePopupWindow;
    private String inviteStr[]={"全部","已下单","未下单","待审核","审核失败"};
    //(0-全部，1-已下单，2-未下单，3-待审核，4-审核失败)

    private TextView txt_date,txt_select,txt_total;
    private TimePickerView pvTime;
    private int type = 0;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的邀请");
    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_share_shop_list);
        body_other.addView(view);
        swipeToLoadLayout = ViewUtils.findViewById(view, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshEnabled(false);
        RecyclerView recyclerView = ViewUtils.findViewById(swipeToLoadLayout, R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        ShareShopListAdapter adapter=new ShareShopListAdapter();
        recyclerView.setAdapter(adapter);
        loadMoreUt = new LoadMoreUtil(this, recyclerView);
        swipeToLoadLayout.setRefreshing(true);

        txt_date = ViewUtils.findViewById(view, R.id.txt_date);
        txt_date.setOnClickListener(this);
        txt_select = ViewUtils.findViewById(view, R.id.txt_select);
        txt_select.setOnClickListener(this);
        txt_total = ViewUtils.findViewById(view, R.id.txt_total);
        setTotalShop(0,0,0);

        txt_select.setText(inviteStr[0]);
        initTimePickView();
        onRefresh();
    }

//    @Override
//    protected void initData() {
//
//        if (PublicCache.loginPurchase==null) {
//              return;
//        }
//        onStartLoading();
//        //用户邀请码
//        list.clear();
//        addRequest(ModelRequest.getInstance().getShareShopList(PublicCache.loginPurchase.getVerifyCode(), PublicCache.site_login,txt_date.getText().toString(), type,1,10), new RequestCallback<ShareShopListResult>(this) {
//            @Override
//            protected void onSuc(ShareShopListResult body) {
//
//                if (body.getData()!=null) {
//                    list=body.getData().getList();
//                    setTotalShop(body.getData().getApplyCount(),body.getData().getOrderCount(),body.getData().getNoOrderCount());
//                    if (list != null) {
//                        adapter.setList(list);
//                    }
//                }
//            }
//        });
//    }

    private void setTotalShop(int register,int order,int unSucess){


        MySpecialStyle style=new MySpecialStyle();
        SpecialStringBuilder sb=new SpecialStringBuilder();

        style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
        sb.append("邀请成功注册 "+register+" 家（",style);

        style.setColor(ColorUtil.getColor(R.color.f120a539));
        sb.append("已下单 "+order+" 家",style);

        style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
        sb.append(" / 未下单 "+unSucess+" 家）",style);

        txt_total.setText(sb.getCharSequence());
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ShareShopListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txt_select :
                if (inviteTypePopupWindow == null) {
                    inviteTypePopupWindow =new InviteTypePopupWindow(v,getBaseActivity(),inviteStr) {
                        @Override
                        public void setResult(Object object) {
                            txt_select.setText(inviteStr[(int)object]);
                            type = (int) object;
                            onRefresh();
                        }
                    };
                   // inviteTypePopupWindow.showAsDropDown(v);
                    inviteTypePopupWindow.showAtLocation(v,Gravity.TOP,0,0);
                }else {
                    if (!inviteTypePopupWindow.isShowing()) {
                       // inviteTypePopupWindow.showAsDropDown(v);
                        inviteTypePopupWindow.showAtLocation(v,Gravity.TOP,0,0);
                    }
                }
                break;
            case R.id.txt_date :
                if (pvTime == null) {
                    initTimePickView();
                    pvTime.show();
                }else {
                    if (!pvTime.isShowing()) {
                        pvTime.show();
                    }
                }
            break;
        }
    }

    @Override
    public void getData(int pn) {
        if (PublicCache.loginPurchase == null) return;
        addRequest(ModelRequest.getInstance().getShareShopList(PublicCache.loginPurchase.getVerifyCode(), PublicCache.site_login,txt_date.getText().toString(), type,pn,10), new RequestCallback<ShareShopListResult>(this) {
            @Override
            protected void onSuc(ShareShopListResult body) {

                if (body.getData()!=null) {
                    list=body.getData().getList();
                    setTotalShop(body.getData().getTotal(),body.getData().getOrderCount(),body.getData().getNoOrderCount());
                    if (list != null) {
                        if (swipeToLoadLayout.isLoadingMore())swipeToLoadLayout.setLoadingMore(false);
                        loadMoreUt.setData(body.getData().getList(), body.getData().getPn(), body.getData().getPs());
                    }
                }
            }

            @Override
            public void onFailed(int couponsFindreciveList, String msg) {
                if (swipeToLoadLayout.isLoadingMore()) swipeToLoadLayout.setLoadingMore(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        getData(1);
    }

    private void initTimePickView(){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013,0,1);
        endDate.set(2020,11,31);

         txt_date.setText(getTime(selectedDate.getTime()));
         pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                txt_date.setText(getTime(date));
                onRefresh();
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                //.setTitleText("Title")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
               // .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(UIUtils.getColor(R.color.gray_66))//确定按钮文字颜色
                .setCancelColor(UIUtils.getColor(R.color.gray_66))//取消按钮文字颜色
                .setTitleBgColor(UIUtils.getColor(R.color.gray_df))//标题背景颜色 Night mode
                .setBgColor(UIUtils.getColor(R.color.white))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
