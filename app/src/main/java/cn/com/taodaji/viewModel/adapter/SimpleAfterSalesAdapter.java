package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.RefundDetail;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.viewModel.vm.AfterSalesVM;

import com.base.utils.UIUtils;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;

public class SimpleAfterSalesAdapter extends SingleRecyclerViewAdapter {
    private boolean goDetail=true;

    public boolean isGoDetail() {
        return goDetail;
    }

    public void setGoDetail(boolean goDetail) {
        this.goDetail = goDetail;
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_refund_list);
    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new AfterSalesVM());
    }

    public SimpleAfterSalesAdapter() {
        super();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);


        if (PublicCache.loginSupplier == null) holder.setVisibility(R.id.supplier, View.GONE);
        else holder.setVisibility(R.id.purchaser, View.GONE);

        holder.setVisibility(R.id.ll_bottom, View.GONE);


        RefundDetail bean = (RefundDetail) getListBean(position);

        RelativeLayout nz_rl = holder.findViewById(R.id.nz_rl);
        TextView tv_remark = holder.findViewById(R.id.tv_remark);
        if (UIUtils.isNullOrZeroLenght(bean.getPro_remark())){
            nz_rl.setVisibility(View.GONE);
        }else {
            nz_rl.setVisibility(View.VISIBLE);
            tv_remark.setText(bean.getPro_remark());
        }

//        int nameL = bean.getName().length();
//        String ss = bean.getNick_name();
//        int niceName = ss.length();
//
//        if (nameL >= 10) ss = "";
//        else if (nameL + niceName > 10) {
//            ss = ss.substring(0, 10 - nameL) + "..";
//        }
//        TextView textView = holder.findViewById(R.id.goods_nickName);
//        textView.setText(ss);
//
//        View textView21 = holder.findViewById(R.id.textView_21);
//        View textView22 = holder.findViewById(R.id.textView_22);
//        if (TextUtils.isEmpty(ss)) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }

        //  holder.setText(R.id.goods_unit2,bean.getUnit());



        if (isGoDetail()) {
            String statss = PublicCache.getAfterSaleType().getValueOfKey(bean.getApply_type());
            switch (bean.getStatus()) {
                case 1:
                    statss += "申请";
                    break;
                case 6:
                    statss += "完成";
                    break;
                case 2:
                case 3:
                case 7:
                    statss += "中";
                    break;
                case 4:
                case 5:
                case 8:
                    statss = "拒绝" + statss;
                    break;
            }
            TextView order_state = holder.findViewById(R.id.order_state);
            order_state.setText(statss);
        }else {
            holder.setVisibility(R.id.line_item_top, View.GONE);
            holder.setVisibility(R.id.line_item_bottom1, View.GONE);
            holder.setVisibility(R.id.line_item_bottom2, View.GONE);
        }



    }

    //接收售后取消事件
    @Subscribe
    public void onEvent(AfterSalesCancleEvent event) {
        if (!isGoDetail()) {
            return;
        }
        int orderItemId = event.getOrderItemId();
        for (int i = 0; i < getItemCount(); i++) {
            RefundDetail bean = (RefundDetail) getListBean(i);
            if (bean == null) continue;
            if (bean.getOrder_item_id() == orderItemId) {
                Map<String, Object> map = new HashMap<>();
                map.put("status", 8);
                update(i, map);
                break;
            }
        }
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0&&isGoDetail()) {
            AfterSalesDetailActivity.startActivity(view.getContext(), ((RefundDetail) bean).getEntity_id(), -1);
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }

}
