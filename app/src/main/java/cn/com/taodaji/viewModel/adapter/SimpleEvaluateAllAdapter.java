package cn.com.taodaji.viewModel.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.utils.DensityUtil;
import com.base.utils.ViewUtils;

public class SimpleEvaluateAllAdapter extends SingleRecyclerViewAdapter {


    private int state;//0为商品详情的评价    1为全部评价

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
//                putRelation(R.id.customerLogo, "customerLogo");
//
//                putRelation(R.id.customerName, "customerName");
                putRelation(R.id.evaluate_time, "createTime");
                putRelation(R.id.evaluate_text, "creditContent");
                putRelation(R.id.evaluate_response_time, "replyTime");
                putRelation(R.id.evaluate_response_text, "replyContent");
            }

            @Override
            public void setValues(@NonNull View view, Object value) {
                if (view == null) return;
                if (view.getId() == R.id.evaluate_time) {
                    String ss = value.toString();
                    if (ss.length() >= 10) {
                        value = ss.substring(0, 10);
                    }
                }
                super.setValues(view, value);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_evaluate_all);
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        EvaluationList.DataBean.ItemsBean bean = (EvaluationList.DataBean.ItemsBean) getListBean(position);

        boolean isHide = true;
        /** isVirtual 0匿名1正常*/
        if (bean != null) {
            if (bean.getIsVirtual() == 1) {
                isHide = false;
            } else {
                isHide = true;
            }
            if (PublicCache.loginPurchase != null && bean.getCustomerId() == PublicCache.loginPurchase.getEntityId()) {//采购商的登录数据
                isHide = false;
            }
            if (PublicCache.loginSupplier != null && bean.getCustomerId() == PublicCache.loginSupplier.getEntityId()) {//供应商的登录数据
                isHide = false;
            }
        }
        if (isHide) {
            holder.setImageRes(R.id.customerLogo, R.mipmap.hide_portrait);
            holder.setText(R.id.customerName, "匿名用户");
        } else {
            holder.setImageRes(R.id.customerLogo, bean.getCustomerLogo());
            holder.setText(R.id.customerName, bean.getCustomerName());
        }


        if (state == 0) {
            holder.findViewById(R.id.supplier_reply).setVisibility(View.GONE);
        } else if (state == 1) {

            if (bean.getIsReply() == 1)
                holder.findViewById(R.id.supplier_reply).setVisibility(View.VISIBLE);
            else holder.findViewById(R.id.supplier_reply).setVisibility(View.GONE);

            RecyclerView recyclerView = holder.findViewById(R.id.recyclerView);
            SimpleAddPicturesAdapter addPicturesAdapter;
            if (recyclerView.getAdapter() == null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), OrientationHelper.HORIZONTAL, false));
                recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5), R.color.white));
                addPicturesAdapter = new SimpleAddPicturesAdapter();
                addPicturesAdapter.setShowAdd(false);
                addPicturesAdapter.setShowDelete(false);
                recyclerView.setAdapter(addPicturesAdapter);
            } else {
                addPicturesAdapter = (SimpleAddPicturesAdapter) recyclerView.getAdapter();
            }
            addPicturesAdapter.setImageStrs(bean.getCreditImgs());
        }
    }

}
