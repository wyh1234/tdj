package cn.com.taodaji.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.CashCouponAdapter;
import cn.com.taodaji.model.entity.CouponsChooseCouponList;
import cn.com.taodaji.model.event.CashCouponUseEvent;
import tools.fragment.DataBaseFragment;


import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import com.base.utils.DensityUtil;
import com.base.utils.ViewUtils;


public class CashCouponUsedFragment extends DataBaseFragment implements OnItemClickListener {

    //status：4,结算-可用代金券 5,结算-不可用代金券
    private int status;
    private CashCouponAdapter couponAdapter;
    private List<CouponsChooseCouponList.DataBean.ItemBean> listData;

    private TextView tv_cash_coupons_help;

    private int cash_count;

    private BigDecimal cash_money = BigDecimal.ZERO;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setListData(List<CouponsChooseCouponList.DataBean.ItemBean> listData) {
        if (listData != null && listData.size() > 0) {
            Map<String, CouponsChooseCouponList.DataBean.ItemBean> map = new HashMap<>();
            for (int size = listData.size() - 1; size >= 0; size--) {
                map.put(listData.get(size).getCategoryId(), listData.get(size));
            }

            if (map.size() > 0) {
                for (Map.Entry<String, CouponsChooseCouponList.DataBean.ItemBean> stringItemBeanEntry : map.entrySet()) {
                    stringItemBeanEntry.getValue().setSelected(true);
                    cash_count += 1;
                    cash_money = cash_money.add(stringItemBeanEntry.getValue().getAmount());
                }
            }
        }

        this.listData = listData;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = ViewUtils.getFragmentView(container, R.layout.fragment_cash_coupon_used);
        tv_cash_coupons_help = ViewUtils.findViewById(view, R.id.tv_cash_coupons_help);
        RecyclerView recyclerView = ViewUtils.findViewById(view, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setPadding(DensityUtil.dp2px(7.5f), 0, DensityUtil.dp2px(10), 0);
        recyclerView.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(10), R.color.transparent));
        recyclerView.setBackgroundResource(R.color.gray_f2);
        couponAdapter = new CashCouponAdapter();
        couponAdapter.setItemClickListener(this);
        couponAdapter.setStatus(status);
        recyclerView.setAdapter(couponAdapter);
        return view;
    }

    @Override
    public void initData() {
        if (listData != null) {
            couponAdapter.setList(listData);
            if (tv_cash_coupons_help.getVisibility() == View.GONE&&status==4)
                tv_cash_coupons_help.setVisibility(View.VISIBLE);
            tv_cash_coupons_help.setText("已选中代金券" + cash_count + "张，共抵用￥" + cash_money + "元");
        }
    }

    public CashCouponUseEvent getCouponItemInfo() {
        CashCouponUseEvent event = new CashCouponUseEvent();
        event.setCash_coupon_count(cash_count);
        event.setCash_coupon_money(cash_money);
        event.setCash_coupon_used_money(cash_money);
        String productInfo = "[";
        int count = couponAdapter.getRealCount();
        for (int i = couponAdapter.getHeaderCount(); i < count; i++) {
            CouponsChooseCouponList.DataBean.ItemBean item = (CouponsChooseCouponList.DataBean.ItemBean) couponAdapter.getListBean(i);
            if (item.getSelected()) {
                productInfo += "{\"entityId\":" + item.getEntityId() + ",\"couponId\":" + item.getCouponId() + ",\"productIds\":\"" + item.getProductIds() + "\",\"amount\":" + item.getAmount() + "},";
            }
        }
        productInfo = productInfo.substring(0, productInfo.length() - 1) + "]";
        event.setCouponItemInfo(productInfo);
        return event;
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            CouponsChooseCouponList.DataBean.ItemBean itemBean = (CouponsChooseCouponList.DataBean.ItemBean) bean;


            //如果点击的是选中的则取消选择中
            if (itemBean.getSelected()) {
                couponAdapter.setSelectedCancel(position);
                cash_count -= 1;
//                cash_money -= itemBean.getAmount();
                cash_money = cash_money.subtract(itemBean.getAmount());
                tv_cash_coupons_help.setText("已选中代金券" + cash_count + "张，共抵用￥" + cash_money + "元");
                return true;
            }
            //点击项选择中
            couponAdapter.setSelected(position);
            cash_count += 1;
//            cash_money += itemBean.getAmount();
            cash_money = cash_money.add(itemBean.getAmount());

            if (tv_cash_coupons_help.getVisibility() == View.GONE)
                tv_cash_coupons_help.setVisibility(View.VISIBLE);

            int count = couponAdapter.getRealCount();
            //不限制
            if (itemBean.getCategoryId().equals("-1")) {
                //如果是不限制分类的代金券，除了当前点击外，所有的不限制分类的代金券都取消选中
                for (int i = couponAdapter.getHeaderCount(); i < count; i++) {
                    if (i == position) continue;
                    CouponsChooseCouponList.DataBean.ItemBean item = (CouponsChooseCouponList.DataBean.ItemBean) couponAdapter.getListBean(i);
                    if (item.getSelected() && item.getCategoryId().equals("-1")) {
                        couponAdapter.setSelectedCancel(i);
                        cash_count -= 1;
//                        cash_money -= item.getAmount();
                        cash_money = cash_money.subtract(item.getAmount());
                    }
                }
            }
            //每个分类限选择一个，如果有相同的则取消掉前面选择
            else {
                for (int i = couponAdapter.getHeaderCount(); i < count; i++) {
                    if (i == position) continue;
                    CouponsChooseCouponList.DataBean.ItemBean item = (CouponsChooseCouponList.DataBean.ItemBean) couponAdapter.getListBean(i);
                    if (itemBean.getCategoryId().equals(item.getCategoryId()) && item.getSelected()) {
                        couponAdapter.setSelectedCancel(i);
                        cash_count -= 1;
//                        cash_money -= item.getAmount();
                        cash_money = cash_money.subtract(item.getAmount());
                    }
                }
            }
            tv_cash_coupons_help.setText("已选中代金券" + cash_count + "张，共抵用￥" + cash_money + "元");
            return true;
        }
        return false;
    }
}
