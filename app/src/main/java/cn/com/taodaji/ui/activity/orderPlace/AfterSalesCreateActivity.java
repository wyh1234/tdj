package cn.com.taodaji.ui.activity.orderPlace;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ADInfo;
import com.base.utils.CustomerData;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AfterSales;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.event.AfterSalesCreateEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.packingCash.PackingCashReturnActivity;
import cn.com.taodaji.ui.ppw.SimpleButtonBottomPPW;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsVM;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.AddedPicturesFragment;

public class AfterSalesCreateActivity extends SimpleToolbarActivity implements UploadPicturesDoneListener, View.OnClickListener {

    EditText count;
    EditText description;
    Button ok;
    TextView after_sales_type;
    ImageView goods_image;
    TextView problem_type;
    TextView type_name, tv_after_sales, goods_name, goods_unit, goods_price, goods_unit2, cart_price, tv_coupons, goods_nickName;
    private CartGoodsBean cartGoodsBean;
    private OrderDetail.ItemsBean orderBean;
    private BigDecimal goodscount = BigDecimal.ZERO;
    private AddedPicturesFragment addedPicturesFragment;
    // private String typeString = "换货";
    //private int isUserCoupon = 0;

    private BigDecimal cou = BigDecimal.ZERO;//可以退换的总数
    private BigDecimal priceSum = BigDecimal.ZERO; //可以退款总数

    private Switch sw_switch;//是否退押金
    private View ll_withdraw_cash_pledge;

    private int isForegift;// 是否收取押金 0-不收，1-收
    private int packageStatus = -1;//押金状态  -1 订购，0 - 未退, 1-已退完

    int isUserCoupon = 0;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("申请售后");
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_after_sales);
        body_scroll.addView(mainView);
        View goods_information = ViewUtils.findViewById(mainView, R.id.goods_information);
        goods_information.setBackgroundColor(UIUtils.getColor(R.color.white));
        goods_information.setVisibility(View.VISIBLE);
        count = ViewUtils.findViewById(mainView, R.id.count);
        sw_switch = mainView.findViewById(R.id.sw_switch);
        sw_switch.setChecked(false);
        ll_withdraw_cash_pledge = mainView.findViewById(R.id.ll_withdraw_cash_pledge);
        if (cartGoodsBean != null) {
            //列表
            BaseViewHolder holder = new BaseViewHolder(goods_information, new OrderPlaceGoodsVM(), null);
            holder.setValues(cartGoodsBean);
            isForegift = cartGoodsBean.getIsForegift();
            packageStatus = cartGoodsBean.getPackageStatus();
        } else if (orderBean != null) {
            //详情
            BaseViewHolder holder = new BaseViewHolder(goods_information, new OrderPlaceGoodsDetailVM(), null);
            holder.setValues(orderBean);
            isForegift = orderBean.getIsForegift();
            packageStatus = orderBean.getPackageStatus();
        }

        if (isForegift == 0 || packageStatus == 1) ll_withdraw_cash_pledge.setVisibility(View.GONE);

        GoodsSpecification gsf = new GoodsSpecification();


        String unit = "";

        if (orderBean != null) {
            cou = orderBean.getAmount();
            priceSum = orderBean.getTotalPrice();
            JavaMethod.copyValue(gsf, orderBean);
            unit = orderBean.getAvgUnit();
        } else if (cartGoodsBean != null) {
            cou = BigDecimal.valueOf(cartGoodsBean.getProductQty());
            priceSum = cartGoodsBean.getPriceSum();
            unit = cartGoodsBean.getAvgUnit();
            JavaMethod.copyValue(gsf, cartGoodsBean);
        }

        if (gsf.getLevelType() == 2) {
            if (specification_unit_base.contains(unit) && gsf.getLevel2Unit().equals(unit))
                cou = cou.multiply(gsf.getLevel2Value());
        } else if (gsf.getLevelType() == 3) {
            if (specification_unit_base.contains(unit) && gsf.getLevel3Unit().equals(unit))
                cou = cou.multiply(gsf.getLevel2Value()).multiply(gsf.getLevel3Value());
            else cou = cou.multiply(gsf.getLevel2Value());
        }


        count.setHint("最多可退 " + String.valueOf(cou.stripTrailingZeros().toPlainString()) + " " + unit);


        tv_after_sales = ViewUtils.findViewById(mainView, R.id.tv_after_sales);
        goods_image = ViewUtils.findViewById(mainView, R.id.goods_image);
        goods_name = ViewUtils.findViewById(mainView, R.id.goods_name);
        goods_nickName = ViewUtils.findViewById(mainView, R.id.goods_nickName);
        goods_price = ViewUtils.findViewById(mainView, R.id.goods_price);
        goods_unit = ViewUtils.findViewById(mainView, R.id.goods_unit);
        cart_price = ViewUtils.findViewById(mainView, R.id.cart_price);
        tv_coupons = ViewUtils.findViewById(mainView, R.id.tv_coupons);   //是否使用代金劵


        if (cartGoodsBean != null) {
            isUserCoupon = cartGoodsBean.getIsUseCoupon();
        } else if (orderBean != null) {
            isUserCoupon = orderBean.getIsUseCoupon();
        }
        if (isUserCoupon > 0) {
            tv_coupons.setVisibility(View.VISIBLE);
        } else {
            tv_coupons.setVisibility(View.GONE);
        }


        cart_price = ViewUtils.findViewById(mainView, R.id.cart_price);
        description = ViewUtils.findViewById(mainView, R.id.description);
        ok = ViewUtils.findViewById(mainView, R.id.ok);
        ok.setOnClickListener(this);
        after_sales_type = ViewUtils.findViewById(mainView, R.id.after_sales_type);
        after_sales_type.setHint("请选择售后类型");
        after_sales_type.setOnClickListener(this);
        problem_type = ViewUtils.findViewById(mainView, R.id.problem_type);
        problem_type.setOnClickListener(this);
        type_name = ViewUtils.findViewById(mainView, R.id.type_name);

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.length() == 0 ? "0" : s.toString();
                // if (str.startsWith(".")) str = "0" + str;
                if (s.length() == 0) {
                    tv_after_sales.setText("0");
                    return;
                }


                if ((goodscount = new BigDecimal(str)).compareTo(cou) > 0) {
                    count.setError("您没有这么多商品需要退换");
                    goodscount = BigDecimal.ZERO;
                    return;
                }
                LogUtils.i(priceSum);
                if (gsf.getAvgPrice().multiply(goodscount).compareTo(priceSum) > 0) {
                    tv_after_sales.setText(String.valueOf(priceSum));
                } else {
                    if (goodscount.compareTo(cou) == 0)
                        tv_after_sales.setText(String.valueOf(priceSum));
                    else
                        tv_after_sales.setText(String.valueOf(priceSum.divide(cou,2, BigDecimal.ROUND_HALF_UP).multiply(goodscount).setScale(2, BigDecimal.ROUND_HALF_UP)));
//                        tv_after_sales.setText(String.valueOf(gsf.getAvgPrice().multiply(goodscount).setScale(2, BigDecimal.ROUND_HALF_UP)));
                }
            }
        });

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
//        addedPicturesFragment.setBackgroundColor(R.color.gray_f2);
        addedPicturesFragment.setMaxSelect(3);
    }

    @Override
    protected void initData() {
        initAfter_sales_ppw();
        initProblem_type_ppw();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //列表入口
    @Subscribe(sticky = true)
    public void onEvent(CartGoodsBean event) {
        cartGoodsBean = event;
        EventBus.getDefault().removeStickyEvent(event);
    }

    //详情入口
    @Subscribe(sticky = true)
    public void onEvent(OrderDetail.ItemsBean event) {
        orderBean = event;
        EventBus.getDefault().removeStickyEvent(event);
    }

    private SimpleButtonBottomPPW after_sales_ppw;
    private SingleRecyclerViewAdapter after_sales_adapter;

    private SimpleButtonBottomPPW problem_type_ppw;
    private SingleRecyclerViewAdapter problem_type_adapter;


    private void initAfter_sales_ppw() {
        if (after_sales_ppw == null) {
            View view = ViewUtils.getLayoutView(this, R.layout.ppw_goods_type_selector);
            view.findViewById(R.id.v_line).setVisibility(View.GONE);
            view.findViewById(R.id.bt_cancel).setVisibility(View.GONE);
            after_sales_ppw = new SimpleButtonBottomPPW(view);
            after_sales_adapter = new SingleRecyclerViewAdapter() {

                @Override
                public View onCreateHolderView(ViewGroup parent, int viewType) {
                    return ViewUtils.getFragmentView(parent, R.layout.ppw_goods_type_item);
                }

                @Override
                public void initBaseVM() {
                    putBaseVM(TYPE_CHILD, new BaseVM() {
                        @Override
                        protected void dataBinding() {
                            putRelation("imageName", R.id.tv_name);
                            putRelation("imageUrl", R.id.tv_description);

                            putViewOnClick(R.id.item_view);
                        }

                        @Override
                        public void setValue(@NonNull TextView textView, @NonNull Object value) {
                            if (textView.getId() == R.id.tv_description) {
                                if (value.toString().equals("")) {
                                    textView.setVisibility(View.GONE);
                                } else textView.setVisibility(View.VISIBLE);
                            }
                            super.setValue(textView, value);
                        }
                    });

                }

                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                    if (onclickType == 0) {
                        after_sales_ppw.dismiss();
                        ADInfo adInfo = (ADInfo) bean;
                        after_sales_type.setText(adInfo.getImageName());
                        after_sales_type.setTextColor(UIUtils.getColor(R.color.black_63));

                        //换货  补货
                        if (adInfo.getGoodsCount() == 2 || adInfo.getGoodsCount() == 4)
                            ll_withdraw_cash_pledge.setVisibility(View.GONE);
                        else {
                            if (isForegift == 0 || packageStatus == 1)
                                ll_withdraw_cash_pledge.setVisibility(View.GONE);

                            else ll_withdraw_cash_pledge.setVisibility(View.VISIBLE);
                        }
                        sw_switch.setChecked(false);
                        return true;
                    }
                    return false;
                }
            };

            RecyclerView recyclerView = view.findViewById(R.id.rv_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            recyclerView.setAdapter(after_sales_adapter);
            after_sales_adapter.setList(getList(PublicCache.getAfterSaleType()));
//            after_sales_type.setText(JavaMethod.getFieldValue(after_sales_adapter.getListBean(0), "imageName"));
        }
    }

    private int onclickPosition;

    private void initProblem_type_ppw() {
        if (problem_type_ppw == null) {
            View view = ViewUtils.getLayoutView(this, R.layout.ppw_after_sale_problem);
            problem_type_ppw = new SimpleButtonBottomPPW(view);
            problem_type_ppw.setPostButton(R.id.bt_ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    problem_type.setText(JavaMethod.getFieldValue(problem_type_adapter.getListBean(onclickPosition), "imageName"));
                    problem_type.setTextColor(UIUtils.getColor(R.color.black_63));
                    problem_type_ppw.dismiss();
                }
            });
            problem_type_adapter = new SingleRecyclerViewAdapter() {

                @Override
                public View onCreateHolderView(ViewGroup parent, int viewType) {

                    return ViewUtils.getFragmentView(parent, R.layout.ppw_after_sale_problem_item);
                }

                @Override
                public void initBaseVM() {
                    putBaseVM(TYPE_CHILD, new BaseVM() {
                        @Override
                        protected void dataBinding() {
                            putRelation("imageName", R.id.tv_name);
                            putViewOnClick(R.id.item_view);

                            putRelation("selected", R.id.item_view);
                        }
                    });

                }

                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                    if (onclickType == 0) {
                        onclickPosition = position;
                        problem_type_adapter.setSelected(position);
                        return true;
                    }
                    return false;
                }
            };
            problem_type_adapter.setRadio(true);
            RecyclerView recyclerView = view.findViewById(R.id.rv_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dp2px(10)));
            recyclerView.setAdapter(problem_type_adapter);
            problem_type_adapter.setList(getList(PublicCache.getAfterSaleProblem()));
           /* onclickPosition = 0;
            problem_type_adapter.setSelected(onclickPosition);
            problem_type.setText(JavaMethod.getFieldValue(problem_type_adapter.getListBean(onclickPosition), "imageName"));*/
        }
    }

    public void onClick(View view) {
        int applyIndex = PublicCache.getAfterSaleType().idOfValue(after_sales_type.getText().toString());

        switch (view.getId()) {
            case R.id.after_sales_type:
                initAfter_sales_ppw();
                if (!after_sales_ppw.isShowing()) {
                    after_sales_ppw.showAtLocation(view, Gravity.CENTER, 0, 0);
                }

                break;
            case R.id.problem_type:
                initProblem_type_ppw();
                if (!problem_type_ppw.isShowing()) {
                    problem_type_ppw.showAtLocation(view, Gravity.CENTER, 0, 0);
                }

                break;
            case R.id.ok:
                if (goodscount.compareTo(BigDecimal.ZERO) == 0) {
                    UIUtils.showToastSafesShort("请输入需要退换的数量");
                    return;
                }
                if (count.getText().toString().equals("")) {
                    UIUtils.showToastSafesShort("请输入需要退换的数量");
                    tv_after_sales.setText("0.00");
                    return;
                }

                if (description.getText().length() == 0) {
                    UIUtils.showToastSafesShort("请输入具体的问题描述");
                    return;
                }
                if (applyIndex < 0) {
                    UIUtils.showToastSafesShort("请选择售后类型");
                    return;
                }
                int problemIndex = PublicCache.getAfterSaleProblem().idOfValue(problem_type.getText().toString());
                if (problemIndex < 0) {
                    UIUtils.showToastSafesShort("请选择售后原因");
                    return;
                }
                if (PublicCache.loginPurchase.getIsC()==1){
                    if (ListUtils.isNullOrZeroLenght(addedPicturesFragment.getImageStr())){
                        ToastUtils.showShortToast(this,"请上传售后凭证照片");
                        return;
                    }
                }

                ok.setEnabled(false);
                loading();
                if (!addedPicturesFragment.isUploadDone()) {
                    addedPicturesFragment.setCallBack(true);
                } else uploadPicturesIsDone(null);
                break;
        }
    }

    private List<ADInfo> getList(CustomerData<Integer, String, String> customerData) {
        List<ADInfo> list = new ArrayList<>();
        for (int i = 0; i < customerData.size(); i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageName(customerData.getValueAtIndex(i));
            adInfo.setGoodsCount(customerData.getKeyAtIndex(i));
            list.add(adInfo);
        }
        return list;
    }

    public static void startActivity(Context context, CartGoodsBean unit) {
        EventBus.getDefault().postSticky(unit);
        context.startActivity(new Intent(context, AfterSalesCreateActivity.class));
    }

    public static void startActivity(Context context, OrderDetail.ItemsBean unit) {
        EventBus.getDefault().postSticky(unit);
        context.startActivity(new Intent(context, AfterSalesCreateActivity.class));
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;

        /**
         * 申请售后退款或者退货
         * 请求参数
         * <p/>
         * 参数名	类型	必须(1是/0否)	说明
         * customerId	int	1	采购商的ID
         * storeId	int	1	供应商店铺ID
         * orderId	int	1	订单ID
         * orderItemId	int	1	订单条目ID
         * productImg  	str	1	产品图片
         * sku       	str	1	商品SKU编号
         * unit       	str	1	商品单位
         * name      	str	1	商品名称
         * nickName  	str	1	商品昵称名称
         * price     	double	1	商品价格
         * amount    	int	1	退货的数量
         * problemDescription	str	1	问题描述
         * certificatePhotos 	str	1	上传的凭证照片
         * status            	int	1	售后处理：status: 2退款中 3换货中 4拒绝退款 5拒绝换货 6完成售后操作
         * applyType         	int	1	申请类别:1表示申请退款、:2表示申请退货
         * customerName      	str	1	采购商姓名
         *
         */

        Map<String, Object> map = new HashMap<>();
        map.put("customerId", PublicCache.loginPurchase.getEntityId());
        if (cartGoodsBean != null) {
            map.put("storeId", cartGoodsBean.getStoreId());
            map.put("orderId", cartGoodsBean.getOrderId());
            map.put("orderItemId", cartGoodsBean.getItemId());
            map.put("productImg", cartGoodsBean.getProductImage());
            map.put("sku", cartGoodsBean.getSku());
            map.put("unit", cartGoodsBean.getProductUnit());
            map.put("name", cartGoodsBean.getProductName());
            map.put("nickName", cartGoodsBean.getNickName());
            map.put("price", cartGoodsBean.getProductPrice());
        } else if (orderBean != null) {
            map.put("storeId", orderBean.getStoreId());
            map.put("orderId", orderBean.getOrderId());
            map.put("orderItemId", orderBean.getItemId());
            map.put("productImg", orderBean.getImage());
            map.put("sku", orderBean.getSku());
            map.put("unit", orderBean.getUnit());
            map.put("name", orderBean.getName());
            map.put("nickName", orderBean.getNickName());
            map.put("price", orderBean.getPrice());
        }


        map.put("amount", goodscount);
        map.put("problemDescription", description.getText().toString());
        map.put("certificatePhotos", addedPicturesFragment.getImageStr());
        map.put("status", 1);
        int applyType = PublicCache.getAfterSaleType().keyOfValue(after_sales_type.getText().toString());
        int problemType = PublicCache.getAfterSaleProblem().keyOfValue(problem_type.getText().toString());
        map.put("applyType", applyType);
        map.put("problemType", problemType);

        map.put("customerName", PublicCache.loginPurchase.getRealname());

        getRequestPresenter().afterSalesApplication(map, new ResultInfoCallback<AfterSales>() {

            @Override
            public void onSuccess(AfterSales body) {
                loadingDimss();
                if (sw_switch.isChecked()) {
                    UIUtils.showToastSafesShort("售后申请已提交");//,请选择退押金数量！
                    if (cartGoodsBean != null) {
                        if (cartGoodsBean.getPackageStatus() != 1) {
                            PackingCashReturnActivity.startActivity(getBaseActivity(), cartGoodsBean);
                        }
                    } else if (orderBean != null) {
                        if (orderBean.getPackageStatus() != 1) {
                            PackingCashReturnActivity.startActivity(getBaseActivity(), orderBean);
                        }
                    }
                } else {
                    UIUtils.showToastSafesShort("售后申请已提交");
                }
                EventBus.getDefault().post(new AfterSalesCreateEvent(body.getOrderItemId()));
                finish();
            }

            @Override
            public void onFailed(int afterSalesResultInfo, String msg) {
                ok.setEnabled(true);
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


}
