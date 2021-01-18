package cn.com.taodaji.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.ui.activity.homepage.OftenBuyActivity;
import cn.com.taodaji.ui.activity.homepage.PickFoodActivity;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.SimpleCartAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CartNet;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.activity.cashCoupon.CashCouponsGetActivity;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;

import cn.com.taodaji.ui.activity.orderPlace.SubmitOrderActivity;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DateUtils;

import cn.com.taodaji.model.LoginMethod;
import tools.fragment.DataBaseFragment;

import com.base.utils.JavaMethod;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.orm.SugarRecord;

public class CartFragment extends DataBaseFragment implements View.OnClickListener {

    private SimpleCartAdapter simpleCartAdapter;
    private LinearLayout bottom_body;
    private View bottom_delete, bottom_pay;
    private TextView right_text;
    private TextView cart_price;
    private TextView count_image;
    private CartModel cartModel;
    private CheckBox checkBox_de;
    private CheckBox checkBox_pay;
    private RecyclerView cart_recyclerView;
    //当前状态是不是购物车，false为编辑状态
    private boolean isCart = true;

    private View goods_delete;
    private TextView cart_help;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        TextView simple_title = ViewUtils.findViewById(view, R.id.simple_title);
        simple_title.setText("购物车");
        right_text = ViewUtils.findViewById(view, R.id.right_text);
        cart_help = ViewUtils.findViewById(view, R.id.cart_help);


        // right_text.setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.title_right).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.cash_coupons).setOnClickListener(this);
        right_text.setText("编辑");
        right_text.setTextColor(UIUtils.getColor(R.color.white));

        cart_recyclerView = ViewUtils.findViewById(view, R.id.cart_recyclerView);
        cart_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        simpleCartAdapter = new SimpleCartAdapter(getContext());
        cart_recyclerView.setAdapter(simpleCartAdapter);

        bottom_body = ViewUtils.findViewById(view, R.id.bottom_body);

        bottom_delete = ViewUtils.getLayoutView(getContext(), R.layout.item_cart_bottom_delete);
        bottom_pay = ViewUtils.getLayoutView(getContext(), R.layout.item_cart_bottom_pay);
        bottom_body.addView(bottom_pay);

        cartModel = CartModel.getInstance();
        cart_price = ViewUtils.findViewById(bottom_pay, R.id.cart_price);
        count_image = ViewUtils.findViewById(bottom_pay, R.id.count_image);

        ViewUtils.findViewById(bottom_pay, R.id.cart_pay).setOnClickListener(this);
        checkBox_pay = ViewUtils.findViewById(bottom_pay, R.id.cart_checked);
        checkBox_pay.setOnClickListener(this);

        simpleCartAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object data) {
                if (onclickType == 0) {
                    switch (view.getId()) {
                        case R.id.item_select:
                            CartGoodsBean bean = (CartGoodsBean) data;
                            if (bean.getStatus() != 1 || bean.getStoreStatus() != 0) return true;
                            bean.setSelected(!bean.getSelected());

                            if (isCart) {
                                CartGoodsBean crtm = cartModel.getDataBean(bean.getSpecId());
                                crtm.setSelected(bean.getSelected());
                                crtm.update();
                            }

                            selectUpdate(position, bean.getSelected());
                            updateBottom();
                            return true;
                    }
                }
                return false;
            }
        });

        ViewUtils.findViewById(bottom_delete, R.id.cart_delete).setOnClickListener(this);
        checkBox_de = ViewUtils.findViewById(bottom_delete, R.id.cart_checked);
        checkBox_de.setOnClickListener(this);

        updateCart();
        return view;
    }


    private void initCartHelp() {
        if (PublicCache.findByIsActive != null && PublicCache.findByIsActive.getList() != null && cart_help != null) {
            for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
                if (listBean.getId() == PublicCache.site_login) {
                    if (listBean.getAddition4().equals("1")||listBean.getCustomerPurchaseTimeControl().equals("1")) {//
                        cart_help.setVisibility(View.VISIBLE);
                        if (PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()==1){
                            cart_help.setText(listBean.getCustomerPurchaseTimeHint());
                        }else {
                            cart_help.setText(listBean.getAddition1());
                        }

                    } else cart_help.setVisibility(View.GONE);

                    break;
                }
            }
        }
    }


    @Override
    public void initData() {
        super.initData();
        initCartHelp();
        getValueNet();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (!isCart) cartEdit(true);
//         updateCart();
        initCartHelp();
        getValueNet();
    }

    @Override
    public void onPauseRevert() {
        super.onPauseRevert();
        initCartHelp();
        getValueNet();
        if (!isCart) cartEdit(true);
    }

    //接收商品数量变化事件
    @Subscribe(priority = 10)
    public void onMessageEvent(CartEvent event) {
        if (event.isUpdate) {
            simpleCartAdapter.setList(getList());
        } else if (event.getData() != null) {
            int count = event.getData().getProductQty();
            if (count == 0 && event.position != -1) {
                simpleCartAdapter.remove(event.position);
            } else {
                int pos = getPosition(event.getData().getSpecId());
                if (pos > -1) {
                    if (count == 0) {
                        simpleCartAdapter.remove(pos);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("productQty", count);
                        map.put("selected", event.getData().getSelected());
                        simpleCartAdapter.update(pos, map);
                    }
                }
            }
        }

        //如果数量对不上则同步
        if (simpleCartAdapter.getRealCount() != cartModel.getCartList().size()) {
            simpleCartAdapter.setList(getList());
        }
        updateBottom();
    }


    public int getPosition(int specId) {
        List list = simpleCartAdapter.getList();
        int i = 0;
        for (Object o : list) {
            if (o instanceof CartGoodsBean) {
                CartGoodsBean bean = (CartGoodsBean) o;
                if (specId == bean.getSpecId()) return i;
            }
            i++;
        }

        return -1;
    }

    private void getValueNet() {

        List<CartGoodsBean> list = cartModel.getCartList();
        if (list.isEmpty()) {
            //如果存在视图，则移除
            if (simpleCartAdapter.getItemCount() > 0) {
                simpleCartAdapter.clearAll();
            }
            if (count_image != null)
                count_image.setText(String.valueOf(cartModel.getSelectCount()));
            if (cart_price != null)
                cart_price.setText(String.valueOf(cartModel.getSelectPriceSum()));

            checkBox_pay.setChecked(false);
            return;
        }


        String ss = "";
        for (CartGoodsBean goodsBean : list) {
            ss += goodsBean.getSpecId() + ",";
        }



        if (TextUtils.isEmpty(ss)) return;
        ss = ss.substring(0, ss.length() - 1);

        int entityId;

        if (PublicCache.loginPurchase != null) entityId = PublicCache.loginPurchase.getEntityId();
        else if (PublicCache.loginSupplier != null)
            entityId = PublicCache.loginSupplier.getEntityId();
        else return;
        ShowLoadingDialog.close();
        ShowLoadingDialog.showLoadingDialog(getBaseActivity());
        getRequestPresenter().product_findOneBase(ss, entityId, new RequestCallback<CartNet>() {
            @Override
            public void onSuc(CartNet body) {

                boolean isInvild = false;//是否有下架商品
                //更新下架商品
                String deleteSpecIds = body.getData().getDeleteSpecIds();
                if (!TextUtils.isEmpty(deleteSpecIds)) {
                    String specIds[] = deleteSpecIds.split(",");
                    for (String specId : specIds) {
                        CartGoodsBean bean = cartModel.getDataBean(Integer.valueOf(specId));
                        if (bean != null) {
                            isInvild = true;
                            bean.setStatus(2);
                            bean.update();
                        }
                    }
                }

                //更新数据
                for (CartNet.DataBean.ItemsBean dataBean : body.getData().getItems()) {
                    CartGoodsBean bean = cartModel.getDataBean(dataBean.getSpecId());
                    if (bean == null) continue;
                    if (dataBean.getStatus() != 1 || dataBean.getStoreStatus() != 0) {
                        bean.setSelected(false);
                        isInvild = true;
                    }
                    JavaMethod.copyValue(bean, dataBean);
                    if (dataBean.getProductType() == 0) bean.setCountXg(dataBean.getStock());
                    else if (bean.getTypeXg() == 1) bean.setCountXg(dataBean.getAllowPurchase() - dataBean.getAlreadyPurchase());

                    bean.setProductName(dataBean.getName());
                    bean.setProductImage(dataBean.getImage());
                    bean.setTypeXg(dataBean.getProductType());
                    bean.setProductUnit(dataBean.getLevel1Unit());
                    bean.setProductPrice(dataBean.getPrice());
                    bean.setProductCriteria(dataBean.getProductCriteria());
                    bean.setPackageStatus(-1);
                    bean.update();
                }

                if (goods_delete == null) {
                    goods_delete = ViewUtils.getFragmentView(cart_recyclerView, R.layout.item_cart_delete_goods);
                    goods_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<CartGoodsBean> list1 = cartModel.getCartList();
                            for (int i = list1.size() - 1; i >= 0; i--) {
                                CartGoodsBean bean = list.get(i);
                                if (bean != null && (bean.getStatus() != 1 || bean.getStoreStatus() != 0)) {
                                    bean.setProductQty(0);
                                    //通知购物车，购物数量变化
                                    CartEvent cartEvent = new CartEvent(bean);
                                    cartEvent.position = i;
                                    EventBus.getDefault().post(cartEvent);
                                }
                            }
                            //如果存在尾部视图，则移除
                            simpleCartAdapter.removeFooter(goods_delete);
                        }
                    });
                }

                //如果存在dataBean.getStatus() != 1 || dataBean.getStoreStatus() != 0  的商品 则添加尾部视图
                if (isInvild) simpleCartAdapter.addFooterView(goods_delete);
                    //如果不存在,则移除尾部
                else simpleCartAdapter.removeFooter(goods_delete);

                //更新商品图片或价格等数据
                simpleCartAdapter.setList(getList());
                updateBottom();
                ShowLoadingDialog.close();
            }

            @Override
            public void onFailed(int cartNet, String msg) {
                // UIUtils.showToastSafesShort(msg);
                ShowLoadingDialog.close();
            }
        });
    }


    //获取新的数据源
    public List<CartGoodsBean> getList() {
        List<CartGoodsBean> list = new ArrayList<>();
        for (CartGoodsBean goodsBean : cartModel.getCartList()) {
            CartGoodsBean cartGoodsBean = new CartGoodsBean();
            JavaMethod.copyValue(cartGoodsBean, goodsBean);
            list.add(cartGoodsBean);
        }
        return list;
    }


    public void updateCart() {
        simpleCartAdapter.setList(getList(), true);
        //检查初始化数据是否已准备
        if (PublicCache.initializtionData == null) {
            ManageActivity manageActivity = (ManageActivity) getActivity();
            if (manageActivity != null && !manageActivity.isFinishing()) {
                manageActivity.initializtionData();
            }
        }
        updateBottom();
    }

    private void updateBottom() {
        if (cartModel.getSelectCount() == cartModel.getCount() && cartModel.getSelectCount() > 0) {
            if (checkBox_pay != null) checkBox_pay.setChecked(true);
        } else {
            if (checkBox_pay != null) checkBox_pay.setChecked(false);
        }
        if (count_image != null) count_image.setText(String.valueOf(cartModel.getSelectCount()));
        if (cart_price != null) cart_price.setText(String.valueOf(cartModel.getSelectPriceSum()));
    }

    private void setAllChecked(boolean isChecked, boolean isSaved) {

        if (isSaved) {
            List<CartGoodsBean> list = cartModel.getCartList();
            for (CartGoodsBean bean : list) {
                bean.setSelected(isChecked);
                bean.update();
            }
            simpleCartAdapter.setList(getList());
        } else {
            int count = simpleCartAdapter.getItemCount();
            for (int i = 0; i < count; i++) {
                if (simpleCartAdapter.getListBean(i) instanceof CartGoodsBean) {
                    selectUpdate(i, isChecked);
                }
            }
        }
    }

    private void selectUpdate(int position, boolean selected) {
        simpleCartAdapter.update(position, "selected", selected);
    }

    private void cartEdit(boolean toCart) {
        this.isCart = toCart;
        if (toCart) {
            right_text.setText("编辑");
            bottom_body.removeAllViews();
            bottom_body.addView(bottom_pay);
            checkBox_pay.setChecked(false);
            updateCart();
        } else {
            right_text.setText("完成");
            bottom_body.removeAllViews();
            bottom_body.addView(bottom_delete);
            checkBox_de.setChecked(false);
            setAllChecked(false, false);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_right:
                if (isCart) {
                    AlertDialog.Builder builder = ViewUtils.showDialog(getContext(), "提示", "您要编辑购物车吗？");
                    builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                    builder.setPositiveButton("确定", (dialog, which) -> cartEdit(false));
                    builder.create().show();

                } else {
                    AlertDialog.Builder builder = ViewUtils.showDialog(getContext(), "提示", "编辑已完成");
                    builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                    builder.setPositiveButton("确定", (dialog, which) -> cartEdit(true));
                    builder.create().show();
                }

                break;
            case R.id.cart_checked:
                if (isCart) {
                    if (checkBox_pay.isChecked()) {
                        setAllChecked(true, true);
                    } else {
                        setAllChecked(false, true);
                    }
                    updateBottom();
                } else {
                    if (checkBox_de.isChecked()) {
                        setAllChecked(true, false);
                    } else {
                        setAllChecked(false, false);
                    }
                }
                break;
            case R.id.cart_delete:
                //删除按钮
                AlertDialog.Builder builder = ViewUtils.showDialog(getContext(), "提示", "是否删除订单");
                builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                builder.setPositiveButton("确定", (dialog, which) -> {
//                    List<CartGoodsBean> list = simpleCartAdapter.getList();

                    int count = simpleCartAdapter.getItemCount();
                    for (int i = count - 1; i >= 0; i--) {
                        if (simpleCartAdapter.getListBean(i) instanceof CartGoodsBean) {
                            CartGoodsBean bean = (CartGoodsBean) simpleCartAdapter.getListBean(i);
                            if (bean.getSelected() && bean.getStoreStatus() == 0 && bean.getStatus() == 1) {
                                CartGoodsBean cartGoodsBean = cartModel.getDataBean(bean.getSpecId());
                                cartGoodsBean.setProductQty(0);
                                //通知购物车，购物数量变化
                                CartEvent cartEvent = new CartEvent(cartGoodsBean);
                                cartEvent.position = i;
                                EventBus.getDefault().post(cartEvent);
                            }
                        }
                    }
                });
                builder.create().show();

                break;
            case R.id.cart_pay:
                if (!ClickCheckedUtil.onClickChecked(2000)) return;

                if (PublicCache.loginSupplier != null) {
                    UIUtils.showToastSafesShort("当前为销售商账号，不能进行结算");
                    return;
                }
                if (PublicCache.loginPurchase == null) {
                    UIUtils.showToastSafesShort("您还未登录，请先登录账号");
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                        }
                    }, 500);

                    return;
                }
                if (PublicCache.loginPurchase.getAuthStatus() != 1) {
                    UIUtils.showToastSafesShort("请您在审核通过后进行结算");
                    return;
                }
                if (PublicCache.loginPurchase.getEmpRole() == 2||PublicCache.loginPurchase.getEmpRole() == 5) {
                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole()) + "没有结算权限");
                    return;
                }

                List<CartGoodsBean> lil = cartModel.getCartList();
                if (lil.isEmpty()) {
                    UIUtils.showToastSafesShort("购物车中无任何商品");
                    return;
                }

                List<CartGoodsBean> list = new ArrayList<>();//需要提交的商品
                for (int i = 0; i < lil.size(); i++) {
                    CartGoodsBean cartGoodsBean = lil.get(i);
                    if (cartGoodsBean.getSelected() && cartGoodsBean.getStatus() == 1 && cartGoodsBean.getStoreStatus() == 0) {
                        list.add(cartGoodsBean);
                        if (cartGoodsBean.getStock() < cartGoodsBean.getProductQty()) {
                            UIUtils.showToastSafesShort("购物车有商品超出库存");
                            simpleCartAdapter.moveToPosition(i);
                            return;
                        }
                    }
                }

                if (list.size() == 0) {
                    UIUtils.showToastSafesShort("请选择需要提交的商品");
                    return;
                }

//                FindByIsActive.ListBean bean = null;
//                if (PublicCache.findByIsActive != null && PublicCache.findByIsActive.getList() != null) {
//                    for (FindByIsActive.ListBean listBean : PublicCache.findByIsActive.getList()) {
//                        if (listBean.getId() == PublicCache.site_login) {
//                            if (listBean.getAddition4().equals("1")) {
//                                bean = listBean;
//                                break;
//                            }
//                            break;
//                        }
//                    }
//                }
//
//
//                if (bean != null) {
//                    int time_now = Integer.valueOf(DateUtils.parseTime("HH"));
//                    if (!TextUtils.isEmpty(bean.getAddition2()) && !TextUtils.isEmpty(bean.getAddition3()) && bean.getAddition2().contains(":") && bean.getAddition3().contains(":")) {
//
//                        int start = Integer.valueOf(bean.getAddition3().substring(0, bean.getAddition3().indexOf(":")));//开始
//                        int end = Integer.valueOf(bean.getAddition2().substring(0, bean.getAddition2().indexOf(":")));//结束
//
//                        if (start < end) {
//                            if (time_now >= end || time_now < start) {
//                                UIUtils.showToastSafe(bean.getAddition1());
//                                return;
//                            }
//                        }
//                        //如果end>start 则结束时间是凌晨
//                        else if (time_now >= end && time_now < start) {
//                            UIUtils.showToastSafe(bean.getAddition1());
//                            return;
//                        }
//                    }
//                }

                startActivity(new Intent(getBaseActivity(), SubmitOrderActivity.class));

                break;
            case R.id.cash_coupons:

                if (PublicCache.loginPurchase == null && PublicCache.loginSupplier == null) {
                    LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
                } else if (PublicCache.loginPurchase != null){
                    Intent intent2 = new Intent(getContext(), OftenBuyActivity.class);
                    intent2.putExtra("type","2");
                    startActivity(intent2);
                }

//                if (PublicCache.loginPurchase == null && PublicCache.loginSupplier == null) {
//                    LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
//                } else if (PublicCache.loginPurchase != null)
//                    CashCouponsGetActivity.startActivity(getContext());
                break;
        }
    }

}
