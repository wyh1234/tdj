package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.ui.activity.evaluate.EvaluateSupplierReplyActivity;
import cn.com.taodaji.ui.activity.evaluate.EvaluateUpdatePurchaseActivity;
import cn.com.taodaji.viewModel.vm.EvaluateManageVM;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.utils.BitmapUtil;
import com.base.utils.CustomerData;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;


public class SimpleEvaluateManageAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new EvaluateManageVM());
    }

    // EvaluationList.DataBean.ItemsBean
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_evaluate_manage);
    }

    private CustomerData<Integer, String, Integer> customerData = PublicCache.getEvaluateInformation();

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        EvaluationList.DataBean.ItemsBean bean = (EvaluationList.DataBean.ItemsBean) getListBean(position);
        if (bean == null) return;

        //如果是采购商隐藏下面回复
        if (PublicCache.loginPurchase != null) {
            holder.findViewById(R.id.purchaser).setVisibility(View.GONE);

            TextView txt_go_update_evaluate = holder.findViewById(R.id.txt_go_update_evaluate);


            if (bean.getModifyCount() > 0) {
                txt_go_update_evaluate.setVisibility(View.VISIBLE);
                txt_go_update_evaluate.setBackground(BitmapUtil.getDrawable(R.drawable.r_round_rect_solid_white));
                txt_go_update_evaluate.setTextColor(UIUtils.getColor(R.color.gray_69));
                txt_go_update_evaluate.setText("已更改过");
            } else {
                if (bean.getCreditLevel() == 3) {
                    txt_go_update_evaluate.setVisibility(View.VISIBLE);
                    txt_go_update_evaluate.setBackground(BitmapUtil.getDrawable(R.drawable.r_round_rect_orage_ff7d01));
                    txt_go_update_evaluate.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                    txt_go_update_evaluate.setText("更改评价");
                } else {
                    txt_go_update_evaluate.setVisibility(View.GONE);
                }
            }


        }
        //如果是供应商
        else if (PublicCache.loginSupplier != null) {
            holder.findViewById(R.id.purchaser).setVisibility(View.VISIBLE);
            holder.findViewById(R.id.txt_go_update_evaluate).setVisibility(View.GONE);
        }

        if (bean.getIsReply() == 0) {
            holder.findViewById(R.id.reply).setVisibility(View.VISIBLE);
            //供应商回复内容
            holder.findViewById(R.id.supplier_reply).setVisibility(View.GONE);
        } else if (bean.getIsReply() == 1){
            holder.findViewById(R.id.reply).setVisibility(View.GONE);
            holder.findViewById(R.id.supplier_reply).setVisibility(View.VISIBLE);
        }



        ImageView evaluate_logo = holder.findViewById(R.id.evaluate_logo);
        TextView evaluate_name = holder.findViewById(R.id.evaluate_name);
        evaluate_logo.setSelected(true);
        evaluate_logo.setImageResource(customerData.getKeyOfId(bean.getCreditLevel()));
        evaluate_name.setText(customerData.getValueOfId(bean.getCreditLevel()));

//        View textView21 = holder.findViewById(R.id.textView_21);
//        View textView22 = holder.findViewById(R.id.textView_22);
//        if (TextUtils.isEmpty(bean.getNickName())) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }

        holder.setVisibility(R.id.textView1, View.GONE);
        holder.setVisibility(R.id.level2Value, View.GONE);
        holder.setVisibility(R.id.level2Unit, View.GONE);
        holder.setVisibility(R.id.specification_split, View.GONE);
        holder.setVisibility(R.id.level3Value, View.GONE);
        holder.setVisibility(R.id.level3Unit, View.GONE);
        holder.setVisibility(R.id.textView2, View.GONE);
        holder.setVisibility(R.id.line_total_price, View.VISIBLE);


        holder.setText(R.id.goods_unit2, bean.getUnit());

        RecyclerView recyclerView = holder.findViewById(R.id.recyclerView);
        SimpleAddPicturesAdapter addPicturesAdapter = (SimpleAddPicturesAdapter) recyclerView.getAdapter();
        if (addPicturesAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), OrientationHelper.HORIZONTAL, false));
            recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5), R.color.white));
            addPicturesAdapter = new SimpleAddPicturesAdapter();
            recyclerView.setAdapter(addPicturesAdapter);
            addPicturesAdapter.setShowAdd(false);
            addPicturesAdapter.setShowDelete(false);
            addPicturesAdapter.setImageStrs(bean.getCreditImgs());
        } else addPicturesAdapter.setImageStrs(bean.getCreditImgs());


    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (!super.onItemClick(view, onclickType, position, bean)) {
            if (onclickType == 0) {
                if (PublicCache.loginPurchase!=null) {
                    if (PublicCache.loginPurchase.getEmpRole() == 2 || PublicCache.loginPurchase.getEmpRole() == 5) {
                        UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole()) + "没有该操作权限");
                        return true;
                    }
                }
                switch (view.getId()) {
                    case R.id.reply: {
                        Intent intent = new Intent(view.getContext(), EvaluateSupplierReplyActivity.class);
                        intent.putExtra("data", (EvaluationList.DataBean.ItemsBean) bean);
                        view.getContext().startActivity(intent);
                        return true;
                    }

                    case R.id.txt_go_update_evaluate: {
                        EvaluationList.DataBean.ItemsBean itembean = (EvaluationList.DataBean.ItemsBean) bean;
                        if (itembean.getModifyCount() > 0) {
                            UIUtils.showToastSafesShort("评价只能修改一次");
                            return true;
                        }
                        if (PublicCache.loginPurchase != null) {
                            EventBus.getDefault().postSticky(bean);
                            Intent intent = new Intent(view.getContext(), EvaluateUpdatePurchaseActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                }


            }
        }
        return false;
    }
}
