package cn.com.taodaji.ui.activity.integral.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.CartQuantitys;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.ui.activity.integral.BuyIntegralActivity;
import cn.com.taodaji.ui.activity.integral.IntegralShopMainActivity;
import cn.com.taodaji.ui.activity.integral.SubmitOrderActivity;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopCartAdapter;
import cn.com.taodaji.ui.activity.integral.popuwindow.BuyIntegralPopupWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.DelPopuWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.DelShopPopuWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.InputKeysetPopupWindow;
import cn.com.taodaji.ui.activity.integral.tools.BigDecimalUtils;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.fragment.DataBaseFragment;

public class IntegralShopCartFragment extends DataBaseFragment implements IntegralShopCartAdapter.OnItemClickListener,
        InputKeysetPopupWindow.InputKeysetPopuWindowListener, DelShopPopuWindow.DelPopuWindowListener , BuyIntegralPopupWindow.BuyIntegralPopupWindowListener {
    private View mainView;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.cart_list)
    RecyclerView cart_list;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_num)
    TextView tv_num;
    private BuyIntegralPopupWindow buyIntegralPopupWindow;
    private IntegralShopMainActivity activity;
    private boolean flag;
    private DelShopPopuWindow delPopuWindow;
    private int total;//商品总数量
//    BigDecimal money= BigDecimal.ZERO;
    private double money;
    private int intergral=0;
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @OnClick({R.id.btn,R.id.iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn:
                StringBuilder builder=new StringBuilder();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).isB()){
                        builder.append(list.get(i).getGoodsId()+",");
                    }

                }
                if (builder.toString().length()>0){
                    builder.deleteCharAt(builder.length() - 1);
                    verifyUserIntegral(builder.toString());
                }else {
                    UIUtils.showToastSafe("请选择商品");
                }

                break;
            case R.id.iv:
                if (flag){
                    iv.setSelected(false);
                    flag=false;
                }else {
                    iv.setSelected(true);
                    flag=true;
                }
//                 money= BigDecimal.ZERO;
                money=0;
                 intergral=0;
                for (int i=0;i<list.size();i++){
                    if (iv.isSelected()){
                        list.get(i).setB(true);
                        money= BigDecimalUtils.add(money,BigDecimalUtils.mul(list.get(i).getSalesPrice().doubleValue(),list.get(i).getQuantity()));
//                        money=money.add(list.get(i).getSalesPrice());
                        intergral=intergral+list.get(i).getSalesIntegral()*list.get(i).getQuantity();
                    }else {
                        list.get(i).setB(false);
                    }

                }
                total_money();
                  integralShopCartAdapter.notifyDataSetChanged();
                    if (iv.isSelected()){
                        tv_num.setText("共"+list.size()+"件商品不含配送费");
                        setTotal(list.size());

                    }else {
                        tv_num.setText("共"+0+"件商品不含配送费");
                        setTotal(0);
                    }

                break;
        }
    }



    private List<IntegralShopCart.DataBean> list=new ArrayList();
    private IntegralShopCartAdapter integralShopCartAdapter;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragments_integral_cart, container, false);

        ButterKnife.bind(this, mainView);


        setView(mainView);
        return mainView;
    }

    private void setView(View mainView) {
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        tv_title.setText("购物车");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.GONE);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        cart_list.setLayoutManager(layout);
        integralShopCartAdapter=new IntegralShopCartAdapter(getContext(),list);
        integralShopCartAdapter.setOnItemClickListener(this);
        cart_list.setAdapter(integralShopCartAdapter);
        btn_back.setVisibility(View.VISIBLE);


        buyIntegralPopupWindow = new BuyIntegralPopupWindow(getContext());
        buyIntegralPopupWindow.setDismissWhenTouchOutside(false);
        buyIntegralPopupWindow.setBuyIntegralPopupWindowListener(this);
        buyIntegralPopupWindow.setInterceptTouchEvent(false);
        buyIntegralPopupWindow.setPopupWindowFullScreen(true);//铺满

    }
    @Override
    public void onItemSelClick(View v, int position) {
        flag=true;
        if (list.get(position).isB()){
            list.get(position).setB(false);
//            money=money.subtract(list.get(position).getSalesPrice());
            money= BigDecimalUtils.sub(money,BigDecimalUtils.mul(list.get(position).getSalesPrice().doubleValue(),list.get(position).getQuantity()));
            intergral=intergral-list.get(position).getSalesIntegral()*list.get(position).getQuantity();
            if (total>0){
                setTotal(--total);
            }
        }else {
            list.get(position).setB(true);
//            money= money.add(list.get(position).getSalesPrice());
            money= BigDecimalUtils.add(money,BigDecimalUtils.mul(list.get(position).getSalesPrice().doubleValue(),list.get(position).getQuantity()));
            intergral=intergral+list.get(position).getSalesIntegral()*list.get(position).getQuantity();
            setTotal(++total);
        }
        integralShopCartAdapter.notifyItemChanged(position);
        for (int i=0;i<list.size();i++){
            if (!list.get(i).isB()){
                flag=false;

            }
        }
        total_money();

        iv.setSelected(flag);
        tv_num.setText("共"+getTotal()+"件商品不含配送费");

    }
    public void isdel(String type,int pos){

        if (delPopuWindow!=null){
            if (delPopuWindow.isShowing()){
                return;
            }

        }
        delPopuWindow = new DelShopPopuWindow(getContext(),type,pos);
        delPopuWindow.setDelShopPopuWindowListener(this);
        delPopuWindow.setDismissWhenTouchOutside(false);
        delPopuWindow.setInterceptTouchEvent(false);
        delPopuWindow.setPopupWindowFullScreen(true);//铺满
        delPopuWindow.showPopupWindow();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        reset();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get_cart_queryList(IntegralShopCart event) {

        reset();
    }
    public void reset(){
        money=0;
        intergral=0;
        flag=false;
        iv.setSelected(false);
        setTotal(0);
        total_money();
        tv_num.setText("共"+0+"件商品不含配送费");
        ShowLoadingDialog.close();
        cart_queryList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    public void cart_queryList(){
        ShowLoadingDialog.showLoadingDialog(getActivity());
        Map<String,Object> map=new HashMap<>();
//        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().cart_queryList(map, new RequestCallback<IntegralShopCart>(this) {
            @Override
            public void onSuc(IntegralShopCart body) {

                if (!ListUtils.isEmpty(list)){
                    list.clear();

                }
                LogUtils.i(body);
                if (!ListUtils.isEmpty(body.getData())){
                    list.addAll(body.getData());
                    integralShopCartAdapter.notifyDataSetChanged();
                    ll_empty.setVisibility(View.GONE);
                }else {
                    ll_empty.setVisibility(View.VISIBLE);
                }
                ShowLoadingDialog.close();

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    public void  total_money(){
        if (money>0&&intergral>0){
            tv_money.setText(money+"元+"+intergral+"积分");
        }else if (money==0&&intergral==0){

            tv_money.setText(0+"元+"+0+"积分");
        }else {
            if (money>0){
                tv_money.setText(money+"元+"+0+"积分");
            }else if (intergral>0){
                tv_money.setText(0+"元+"+intergral+"积分");
            }else {

            }
        }
    }

    @Override
    public void onItemNumClick(View v, int position) {
        InputKeysetPopupWindow inputKeysetPopupWindow = new InputKeysetPopupWindow(v.getContext(), list.get(position).getQuantity(),position);
        inputKeysetPopupWindow.setInputKeysetPopuWindowListener(this);
        inputKeysetPopupWindow.showAtLocation(v.getRootView(), Gravity.BOTTOM, 0, 0);


    }

    @Override
    public void onItemAddClick(View v, int position) {
        if (list.get(position).getRepertory()==list.get(position).getQuantity()){
            UIUtils.showToastSafe("商品库存不足");
            return;
        }

        update_quantity(position,"add");

    }

    @Override
    public void onItemSubClick(View v, int position) {
        if (list.get(position).getQuantity()- 1==0){
            isdel("sub",position);

        }else {
            update_quantity(position,"sub");
        }


    }

    @Override
    public void button_1(int v,int pos) {
        if (v>list.get(pos).getRepertory()){
            UIUtils.showToastSafe("商品库存不足");
            return;
        }
        if (v==0){
            isdel(""+v,pos);

        }else {
            update_quantity(pos,""+v);
        }


        LogUtils.i(v);


    }

    @Override
    public void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        cart_queryList();
    }

    public void update_quantity(int pos,String type){
        ShowLoadingDialog.showLoadingDialog(getActivity());
        Map<String,Object> map=new HashMap<>();
        map.put("guid", list.get(pos).getGuid());
        if ( type.equals("add")){
            map.put("goodsQuantity", list.get(pos).getQuantity()+1);
        }else if (type.equals("sub")){
            map.put("goodsQuantity", list.get(pos).getQuantity()-1);
        }else {
            map.put("goodsQuantity", Integer.parseInt(type));
        }
        map.put("goodsQuantity", type.equals("add")?list.get(pos).getQuantity() + 1:list.get(pos).getQuantity() - 1);
        getIntegralRequestPresenter().update_quantity(map, new RequestCallback<CartQuantitys>(this) {
            @Override
            public void onSuc(CartQuantitys body) {

                if ( type.equals("add")){
                    list.get(pos).setQuantity(list.get(pos).getQuantity() + 1);
                    if (list.get(pos).isB()){
                        money= BigDecimalUtils.add(money,list.get(pos).getSalesPrice().doubleValue());
//                        money=money.add(list.get(i).getSalesPrice());
                        intergral=intergral+list.get(pos).getSalesIntegral();
                    }
                }else if (type.equals("sub")){
                    list.get(pos).setQuantity(list.get(pos).getQuantity() - 1);
                    if (list.get(pos).isB()){
                        money= BigDecimalUtils.sub(money,list.get(pos).getSalesPrice().doubleValue());
//                        money=money.add(list.get(i).getSalesPrice());
                        intergral=intergral-list.get(pos).getSalesIntegral();
                    }
                }else {
                    if (list.get(pos).isB()){
                        if (Integer.parseInt(type)>  list.get(pos).getQuantity()){
                            money= BigDecimalUtils.add(money,BigDecimalUtils.mul(list.get(pos).getSalesPrice().doubleValue(),(Integer.parseInt(type)-list.get(pos).getQuantity())));
                            intergral=intergral+list.get(pos).getSalesIntegral()*(Integer.parseInt(type)-list.get(pos).getQuantity());
                        }else {
                            money= BigDecimalUtils.sub(money,BigDecimalUtils.mul(list.get(pos).getSalesPrice().doubleValue(),(list.get(pos).getQuantity()-Integer.parseInt(type))));
                            intergral=intergral-list.get(pos).getSalesIntegral()*(list.get(pos).getQuantity()-Integer.parseInt(type));
                        }
                    }
                    list.get(pos).setQuantity(Integer.parseInt(type));

                }
                integralShopCartAdapter.notifyItemChanged(pos);

                total_money();
                ShowLoadingDialog.close();
            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    public void delete(int pos,String type){
        Map<String,Object> map=new HashMap<>();
        map.put("guid", list.get(pos).getGuid());
        getIntegralRequestPresenter().delete_cart(map, new RequestCallback<CartQuantitys>(this) {
            @Override
            public void onSuc(CartQuantitys body) {
                if (type.equals("sub")){
                    if (list.get(pos).isB()){
                        money= BigDecimalUtils.sub(money,list.get(pos).getSalesPrice().doubleValue());
                        intergral=intergral-list.get(pos).getSalesIntegral();
                    }
                }else {
                    if (list.get(pos).isB()){
                            money= BigDecimalUtils.sub(money,BigDecimalUtils.mul(list.get(pos).getSalesPrice().doubleValue(),list.get(pos).getQuantity()));
                            intergral=intergral-list.get(pos).getSalesIntegral()*list.get(pos).getQuantity();

                    }
                }

                list.remove(pos);
                integralShopCartAdapter.notifyDataSetChanged();
                total_money();
                if (ListUtils.isEmpty(list)){
                    iv.setSelected(false);
                    flag=false;
                    tv_num.setText("共"+0+"件商品不含配送费");
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
            }
        });


    }
    public void verifyUserIntegral( String listid){
        ShowLoadingDialog.showLoadingDialog(getActivity());
        LogUtils.i(listid);
        Map<String,Object> map=new HashMap<>();
        map.put("goodsIdList",  listid);
//        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().verifyUserIntegral(map, new RequestCallback<CartQuantity>(this) {
            @Override
            public void onSuc(CartQuantity body) {
                LogUtils.i(body);
                ShowLoadingDialog.close();
                List<IntegralShopCart.DataBean> newlist=new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).isB()){
                        newlist.add(list.get(i));
                    }
                }
                    Intent intent=new Intent(getContext(), SubmitOrderActivity.class);
                    intent.putExtra("item", (Serializable) newlist);
                    intent.putExtra("money", money+"");
                    intent.putExtra("intergral", intergral+"");
                    startActivity(intent);
//                }


            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                LogUtils.i(msg+"goodsCategoryList_resu"+goodsCategoryList_resu);
                if (goodsCategoryList_resu==902){
                        if (buyIntegralPopupWindow.isShowing()){
                            return;
                        }


                    buyIntegralPopupWindow.showPopupWindow();
                }else {
                    UIUtils.showToastSafe(msg);
                }

            }
        });
    }
    @Override
    public void del_shop(String type, int pos) {
        delete(pos,type);
    }

    @Override
    public void buy_integra() {
        Intent intent=new Intent(getContext(), BuyIntegralActivity.class);
        startActivity(intent);

    }
}
