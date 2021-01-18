package cn.com.taodaji.ui.activity.packingCash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PackingCashBean;
import cn.com.taodaji.model.event.PackingCashCancleEvent;
import cn.com.taodaji.model.event.PackingCashReturnEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.adapter.PackingCashAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;

public class PackingCashFragment extends LoadMoreRecyclerViewFragment implements OnItemClickListener {

    private int type;
    private PackingCashAdapter adapter;
    private String[] title = {"处理中押金", "未申请退押","已支付押金", "已退押金"};
    private TabLayout tabLayout;
    private TextView txt_look_tips;

    public void setType(int type) {
        this.type = type;
    }

    public void setTablayoutView(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void setTextTips(TextView txt_look_tips) {
        this.txt_look_tips = txt_look_tips;
    }
    private void setCountTitle(int total) {
        //title = {"处理中押金", "未退押金","已支付押金", "已退押金"};  1 2 3 4
        if (tabLayout != null) {
            String titleTab=title[type - 1] + "（" + total + "）";
            TabLayout.Tab tab=null;
            if (type == 1) {
                tab= tabLayout.getTabAt(1);
            }else  if (type == 2) {
               tab=  tabLayout.getTabAt(0);
            }
            if (!tab.getText().toString().equals(titleTab)) {
                tab.setText(titleTab);
            }
        }
    }
    @Override
    public void initData() {
        super.initData();
        recycler_targetView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_targetView.addItemDecoration(new DividerItemDecoration(getContext()));
        recycler_targetView.setBackgroundColor(UIUtils.getColor(R.color.gray_f2));


        if (type == 1||type == 2) {
            setCountTitle(0);
            if (type == 2) {
                getTips(BigDecimal.ZERO,BigDecimal.ZERO);
            }


            View bottowView=LayoutInflater.from(getActivity()).inflate(R.layout.top_tips_stroke,null);
            TextView txt_go_other = ViewUtils.findViewById(bottowView, R.id.txt_go_other);
            down_view.setVisibility(View.VISIBLE);
            down_view.addView(bottowView);
            txt_go_other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PackingCashHistoryActivity.class);//
                    startActivity(intent);
                }
            });

        }

        adapter=new PackingCashAdapter();
        adapter.setType(type);
        adapter.setItemClickListener(this);
        recycler_targetView.setAdapter(adapter);

        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void getData(int pn) {


        Map<String, Object> map = new HashMap<>();
        map.put("pn", pn);
        map.put("ps", 6);
        //  //title = {"处理中押金", "未退押金","已支付押金", "已退押金"};  1 2 3 4
        switch(type){
            case 1 :
                map.put("status", 1);
            break;
            case 2 :
                map.put("status", 0);
                break;
            case 3 :
                map.put("status", 3);
                break;
            case 4 :
                map.put("status", 2);
                break;
        }
       // map.put("status", 1);//0-未退（30天内未退，拒绝，撤销的押金），1-处理中，2-已退，3-已支付（超过30天的未退，撤销和拒绝的押金）




        int userType=0 ;
        if (PublicCache.loginSupplier != null){
            map.put("storeId",PublicCache.loginSupplier.getStore());
            map.put("customerId", 0);
            userType = 1;
        }else  if (PublicCache.loginPurchase != null){
            map.put("customerId", PublicCache.loginPurchase.getEntityId());
            map.put("storeId",0);
            userType = 0;
        }
        map.put("userType", userType);


        getRequestPresenter().getPackageForegiftList(map, new RequestCallback<PackingCashBean>() {
            @Override
            protected void onSuc(PackingCashBean body) {


                if (body.getData()!=null) {
                    if (type == 1||type == 2) {
                        setCountTitle(body.getData().getTotal());
                        if (type == 2) {
                            getTips(body.getData().getUnReturnMoney(),body.getData().getApplyMoney());
                        }

                    }
                    if (body.getData().getPackList()!=null) {
                        for (CartGoodsBean cartGoodsBean : body.getData().getPackList()) {
                            cartGoodsBean.setType(type);
                        }
                        loadMoreUtil.setData(body.getData().getPackList(), body.getData().getPn(), body.getData().getPs());
                    }
                }

                stop();

            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
            }


        });
    }

    private void  getTips(BigDecimal unBackMoney, BigDecimal backingMoney){
        if (txt_look_tips == null) {
            return;
        }

//            String str1="当前押金：未退：";
//            String str2="元";
//            String str3="     处理中：";
//
//            String str=str1+unBackMoney+str2+str3+backingMoney+str2;  //以后启用

        String str1 = "当前处理中押金：";
        String str2="元";
        String str=str1+backingMoney+str2;

            if (!txt_look_tips.getText().toString().equals(str)) {
                SpannableString spannableString = new SpannableString(str);
                ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#EE0000"));
                //ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#EE0000"));
                spannableString.setSpan(colorSpan1, str1.length(), (str1+backingMoney).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //spannableString.setSpan(colorSpan2, (str1+unBackMoney+str2+str3).length(), (str1+unBackMoney+str2+str3+backingMoney).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txt_look_tips.setText(spannableString);
            }
    }
    @Override
    public void onUserVisible() {

        if (adapter != null && adapter.getRealCount() == 0) {
            super.onUserVisible();
        }
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        onUserVisible();
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            if (bean == null) {
                return true;
            }
            switch(view.getId()){
                case R.id.tv_go_back_money :
                    TextView textView = (TextView) view;
                    String state = textView.getText().toString();
                    switch (state) {
                        case "进度查询": {
                            PackingCashProgressActivity.startActivity(getContext(),(CartGoodsBean) bean);
                            break;
                        }
                        case "退押金": {

                            if (bean!=null&&bean instanceof CartGoodsBean) {
                                    CartGoodsBean goodsBean = (CartGoodsBean) bean;
                                //订单数字状态  0 不可退押，1-可点击退押
                                if (goodsBean.getOrderStatusNum() == 1) {
                                    PackingCashReturnActivity.startActivity(getContext(),goodsBean);
                                }
                            }

                            break;
                        }
                    }
                break;
                default:
                break;
            }
        }
        return false;
    }
    //接收退押金取消事件
    @Subscribe
    public void onEvent(PackingCashCancleEvent event) {
        long afterSaleId = event.getAfterSaleId();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getListBean(i) instanceof CartGoodsBean) {
                CartGoodsBean bean = (CartGoodsBean) adapter.getListBean(i);
                if (bean.getAfterSaleId() == afterSaleId) {
                    loadMoreUtil.refreshData(i,6);
                    break;
                }
            }
        }
    }

    //接收退押金成功事件
    @Subscribe
    public void onEvent(PackingCashReturnEvent event) {
        long afterSaleId = event.getAfterSaleId();
        //title = {"处理中押金", "未退押金","已支付押金", "已退押金"};  1 2 3 4
        if (type == 2) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (adapter.getListBean(i) instanceof CartGoodsBean) {
                    CartGoodsBean bean = (CartGoodsBean) adapter.getListBean(i);
                    if (bean.getAfterSaleId() == afterSaleId) {
                        loadMoreUtil.refreshData(i,6);
                        break;
                    }
                }
            }
        }else{
            onRefresh();
          }

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
}
