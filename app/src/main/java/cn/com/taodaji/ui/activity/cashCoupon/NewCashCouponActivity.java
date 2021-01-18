package cn.com.taodaji.ui.activity.cashCoupon;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;
import cn.com.taodaji.model.event.CashCouponListEvent;
import cn.com.taodaji.model.event.CashCouponTabCountEvent;
import cn.com.taodaji.model.event.CashCouponUseEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.adapter.NewCashCouponAdapter;
import tools.activity.SimpleToolbarActivity;


public class NewCashCouponActivity extends SimpleToolbarActivity implements ItemClickListener {
    private int cash_count;
    private int state;
    private BigDecimal cash_money = BigDecimal.ZERO;
    private NewCashCouponAdapter newCashCouponAdapter;
    private ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> listData=new ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean>();
    private  RecyclerView recyclerView;
    private Map<Integer,NewCouponsChooseCouponList.DataBean.ItemBean> map=new HashMap<>();//店券
    private Map<String,NewCouponsChooseCouponList.DataBean.ItemBean> pmap=new HashMap<>();//平台券

    private Map<String,BigDecimal> bmap=new HashMap<>();//多品券总金额


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
    }
   private  List<CartGoodsBean> list;

    private BigDecimal pice= BigDecimal.ZERO;//平台券总金额
    private int count;
    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutViewMatch(this, R.layout.new_tablayout_tabs_top);
        body_other.addView(view);

         recyclerView = ViewUtils.findViewById(view, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setPadding(DensityUtil.dp2px(7.5f), 0, DensityUtil.dp2px(10), 0);
        recyclerView.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(10), R.color.transparent));
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

    }


    @Override
    protected void initData() {
        super.initData();

        setTitle("代金券列表");
        state= getIntent().getIntExtra("data", -1);
        right_textView.setVisibility(View.GONE);
//        listData = (ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean>) getIntent().getSerializableExtra("shopList");
       CashCouponListEvent cashCouponListEvent= (CashCouponListEvent) getIntent().getSerializableExtra("cashCouponListEvent");
        listData = cashCouponListEvent.getList() ;
        newCashCouponAdapter = new NewCashCouponAdapter(listData,this);
        newCashCouponAdapter.setItemClickListener(this);
        recyclerView.setAdapter(newCashCouponAdapter);
        newCashCouponAdapter.setStase(state);

       list = CartModel.getInstance().getCartList();

        if (state==2){
            for (CartGoodsBean cartGoodsBean:list){
                pice=pice.add(cartGoodsBean.getProductPrice().multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty())));//平台总金额
            }


        }else {
            for (CartGoodsBean cartGoodsBean:list){
                if (bmap.containsKey(String.valueOf(cartGoodsBean.getStoreId()))){
                    BigDecimal  shop_price_sum=  bmap.get(String.valueOf(cartGoodsBean.getStoreId()));
                    bmap.put(String.valueOf(cartGoodsBean.getStoreId()),shop_price_sum.add(cartGoodsBean.getProductPrice().multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty()))));
                }else {
                    bmap.put(String.valueOf(cartGoodsBean.getStoreId()),cartGoodsBean.getProductPrice().multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty())));//店铺满减券总金额

                }
                if (bmap.containsKey(String.valueOf("c"+cartGoodsBean.getCategoryId()))){
                    BigDecimal  shop_category_price_sum=  bmap.get(String.valueOf("c"+cartGoodsBean.getCategoryId()));
                    bmap.put(String.valueOf("c"+cartGoodsBean.getCategoryId()),shop_category_price_sum.add(cartGoodsBean.getProductPrice()
                            .multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty()))));//店铺满减券总金额
                }else {
                    bmap.put(String.valueOf("c"+cartGoodsBean.getCategoryId()),cartGoodsBean.getProductPrice().multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty())));//店铺满减券总金额

                }
                bmap.put(String.valueOf("p"+cartGoodsBean.getProductId()),cartGoodsBean.getProductPrice().multiply(BigDecimal.valueOf(cartGoodsBean.getProductQty())));//多分类券总金额
                LogUtils.e(cartGoodsBean.getStoreId());
            }
        }
        LogUtils.e(bmap+"----------------"+list);

        for (NewCouponsChooseCouponList.DataBean.ItemBean itemBean:listData){
            if (itemBean.getSelected()){
                if (state==2){
                    pmap.put(itemBean.getCategoryId(),itemBean);
                }else {
                    map.put(itemBean.getStoreId(),itemBean);
                    Set<Map.Entry<String, BigDecimal>> set=bmap.entrySet();
                    Iterator<Map.Entry<String, BigDecimal>> iterator=set.iterator();
                    while (iterator.hasNext()) {
                        String key=iterator.next().getKey();
                        if (itemBean.getType()==2){
                            if (getProductId(itemBean.getProductIds()).contains(key)){
                                bmap.put(key,bmap.get(key).subtract(itemBean.getAmount()));
                            }
                        }else {
                            if (itemBean.getCategoryId().equals("-1")){
                                if (String.valueOf(itemBean.getStoreId()).contains(key)){
                                    bmap.put(key,bmap.get(key).subtract(itemBean.getAmount()));
                                }
                            }else {
                                if (getCategoryId(itemBean.getCategoryId()).contains(key)){
                                    bmap.put(key,bmap.get(key).subtract(itemBean.getAmount()));
                                }
                            }

                        }




                    }

                }

            }
        }
        LogUtils.e(bmap);

    }

    public String getCategoryId(String id){
        if (id.indexOf(",") >=0 ){
            return "c"+id.replace(",",",c");
        }else {
            return "c"+id;
        }




    }
    public String getProductId(String id){
        if (id.indexOf(",") >=0 ){
            return "p"+id.replace(",",",p");
        }else {
            return "p"+id;
        }




    }


    public CashCouponUseEvent getCouponItemInfo() {
        CashCouponUseEvent event = new CashCouponUseEvent();
        for (NewCouponsChooseCouponList.DataBean.ItemBean itemBean:listData){
            if (itemBean.getSelected()){
                count++;
                cash_money=cash_money.add(itemBean.getAmount());

            }

        }
        event.setCash_coupon_count(count);
        event.setCash_coupon_money(cash_money);
        event.setList( listData);
        event.setCash_coupon_used_money(cash_money);
        event.setStaste(state);
        return event;
    }


    @Subscribe
    public void onEvent(CashCouponListEvent event) {
//        listData = event.getList();
        LogUtils.e("111111111");
//        newCashCouponAdapter.notifyDataSetChanged();

    }

    /**
     * 将选中的代金券，发送到{@link cn.com.taodaji.ui.activity.orderPlace.SubmitOrderActivity#onEvent(CashCouponUseEvent)}
     */
    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(getCouponItemInfo());
        super.onDestroy();
    }



    public static void startActivity(Context context, int state, ArrayList<NewCouponsChooseCouponList.DataBean.ItemBean> list,BigDecimal pice) {
        //state 0 代金券  1使用代金券
        Intent intent = new Intent(context, NewCashCouponActivity.class);
        intent.putExtra("data", state);
            intent.putExtra("shopList",list);
        intent.putExtra("price_sum",pice.toString());
        context.startActivity(intent);
    }
    public static void startActivity(Context context, int state, CashCouponListEvent cashCouponListEvent,BigDecimal pice) {
        //state 0 代金券  1使用代金券
        Intent intent = new Intent(context, NewCashCouponActivity.class);
        intent.putExtra("data", state);
        intent.putExtra("cashCouponListEvent",cashCouponListEvent);
        intent.putExtra("price_sum",pice.toString());
        context.startActivity(intent);
    }




    @Override
    public void onItemClick(View v, int position) {
        LogUtils.e(bmap);
        if (state==1){//店券

            BigDecimal  amounts=listData.get(position).getAmount();
            if (listData.get(position).getSelected()){
                listData.get(position).setSelected(false);
                for (Map.Entry<String, BigDecimal> entry : bmap.entrySet()) {
                    String key=entry.getKey();

                    if (listData.get(position).getType()==2){
                        if (getProductId(listData.get(position).getProductIds()).indexOf(key) >=0){
                            bmap.put(key,bmap.get(key).add(listData.get(position).getAmount()));
                        }
                    }else {
                        if (listData.get(position).getCategoryId().equals("-1")){
                            if (String.valueOf(listData.get(position).getStoreId()).indexOf(key) >=0){
                                bmap.put(key,bmap.get(key).add(listData.get(position).getAmount()));
                            }
                        }else {
                            String str=getCategoryId(listData.get(position).getCategoryId());
                            if (str.indexOf(key) >=0){
                                bmap.put(key,bmap.get(key).add(listData.get(position).getAmount()));
                            }
                        }

                    }
                }

                if (map.size()>0){//判断该商品id有没有在map中出现过
                    Set<Map.Entry<Integer, NewCouponsChooseCouponList.DataBean.ItemBean>> set=map.entrySet();
                    Iterator<Map.Entry<Integer, NewCouponsChooseCouponList.DataBean.ItemBean>> iterator=set.iterator();
                    while (iterator.hasNext()) {
                        int key=iterator.next().getKey();
                        if (key==  listData.get(position).getStoreId()){
                            iterator.remove();
                            break;
                        }

                    }

                }
            }else {
                for (Map.Entry<Integer, NewCouponsChooseCouponList.DataBean.ItemBean> entry : map.entrySet()) {
                    if (entry.getKey()==  listData.get(position).getStoreId()){
                        UIUtils.showToastSafe("该店铺已经使用一张代金券");
                        return;
                    }

                }

                for (Map.Entry<String, BigDecimal> entry : bmap.entrySet()) {
                    String key=entry.getKey();


                    if (listData.get(position).getType()==2){
                        if (getProductId(listData.get(position).getProductIds()).indexOf(key) >=0){
                            if (!(bmap.get(key).compareTo(amounts)>=0)){
                                UIUtils.showToastSafe("代金券不满足条件");
                                return;
                            }
                            bmap.put(key,bmap.get(key).subtract(listData.get(position).getAmount()));
                            listData.get(position).setSelected(true);
                            map.put(listData.get(position).getStoreId(),listData.get(position));
                            break;
                        }
                    }else {
                        if (listData.get(position).getCategoryId().equals("-1")){
                            if (String.valueOf(listData.get(position).getStoreId()).indexOf(key) >=0){
                                if (!(bmap.get(key).compareTo(amounts)>=0)){
                                    UIUtils.showToastSafe("代金券不满足条件");
                                    return;
                                }
                                bmap.put(key,bmap.get(key).subtract(listData.get(position).getAmount()));
                                listData.get(position).setSelected(true);
                                map.put(listData.get(position).getStoreId(),listData.get(position));
                                break;
                            }
                        }else {
                            String str=getCategoryId(listData.get(position).getCategoryId());
                            if (str.indexOf(key) >=0){
                                if (!(bmap.get(key).compareTo(amounts)>=0)){
                                    UIUtils.showToastSafe("代金券不满足条件");
                                    return;
                                }
                                bmap.put(key,bmap.get(key).subtract(listData.get(position).getAmount()));
                                listData.get(position).setSelected(true);
                                map.put(listData.get(position).getStoreId(),listData.get(position));

                                break;
                            }
                        }

                    }




                }


                    }

            newCashCouponAdapter.notifyDataSetChanged();

        }else {
            LogUtils.e(pmap.size());
           BigDecimal amount=listData.get(position).getAmount();
            if (listData.get(position).getSelected()) {
                pice= pice.subtract(amount);
                listData.get(position).setSelected(false);
                if (pmap.size()>0) {//判断该商品id有没有在map中出现过
                    pice = pice.add(amount);
                    pmap.clear();
                }
            }else {
                LogUtils.e(pice);
                if (pmap.size()==1){
                    UIUtils.showToastSafe("平台代金券只能使用一张");
                }else {
                    if (pice.compareTo(amount)>=0){
                        pice=  pice.subtract(amount);
                        listData.get(position).setSelected(true);
                        pmap.put(listData.get(position).getCategoryId(),listData.get(position));
                    }
                }


              }

            newCashCouponAdapter.notifyDataSetChanged();
        }

    }
}
