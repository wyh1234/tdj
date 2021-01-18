package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import tools.activity.MenuToolbarActivity;

import com.base.glide.ImageLoaderUtils;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;


public class CashCouponAdapter extends SingleRecyclerViewAdapter {

    //status：0未使用，1已使用，2已过期，  3,领取代金券,4,结算-可用代金券 5,结算-不可用代金券
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.tv_cash_coupon_money, "amount");
                putRelation(R.id.tv_cash_coupon_use_condition, "purchaseAmount");
                putRelation(R.id.tv_cash_coupon_use_range, "couponDesc");
                putRelation(R.id.validity_stat, "startTime");
                putRelation(R.id.validity_end, "endTime");
                //领取代金券才有
                putRelation(R.id.cash_coupon_get_num, "canCollect");
                putViewOnClick(R.id.bt_cash_coupon_used);
                putRelation(R.id.imageview, "logoImageUrl");
                putRelation(R.id.order_num, "storeName");


                //选择使用代金券
                putRelation(R.id.iv_cash_coupon_new_select, "selected");
                putViewOnClick(R.id.item_view);
            }

            @Override
            public void setValues(BaseViewHolder viewHolder, String pamstr, Object value) {
                if (pamstr.contains("Time") && value != null) {
                    String ss = value.toString();
                    ss = ss.replace("-", ".");
                    if (ss.length() > 10) {
                        value = ss.substring(0, 10);
                    }
                } else if (pamstr.equals("purchaseAmount") && value != null) {
                    if (value.toString().equals("0")) value = "无金额限制";
                    else value = "满" + value.toString() + "可用";
                }
                super.setValues(viewHolder, pamstr, value);
            }
        });
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        Object storeId = JavaMethod.getFieldValue(getListBean(position), "storeId");
        if (status == 0) {
            Object receiveTime = JavaMethod.getFieldValue(getListBean(position), "receiveTime");
            if (receiveTime != null) {
                String ss = DateUtils.dateStringToString(receiveTime.toString(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
                String now = DateUtils.dateLongToString(0, "yyyy-MM-dd");
                if (DateUtils.getSubtractDay(ss, now, "yyyy-MM-dd") == 0) {
                    holder.setVisibility(R.id.iv_cash_coupon_new, View.VISIBLE);
                } else holder.setVisibility(R.id.iv_cash_coupon_new, View.GONE);
            }

        } else if (status == 1) {
            holder.setImageRes(R.id.iv_cash_coupon_new, R.drawable.bg_cash_coupon_used);
        } else if (status == 2){
            holder.setImageRes(R.id.iv_cash_coupon_new, R.drawable.bg_cash_coupon_expired);
        }else if (status==-1) {
            Object canCollect = JavaMethod.getFieldValue(getListBean(position), "canCollect");
            Object startTime = JavaMethod.getFieldValue(getListBean(position), "startTime");
            Object expireTime = JavaMethod.getFieldValue(getListBean(position), "expireTime");
            Object expiryTimeUnit = JavaMethod.getFieldValue(getListBean(position), "expiryTimeUnit");
            if ((Integer) canCollect == 0) {
                holder.setVisibility(R.id.bt_cash_coupon_used, View.GONE);
                holder.setVisibility(R.id.bt_cash_coupon_used_none, View.VISIBLE);
                holder.setBg(R.id.left, R.drawable.bg_cash_coupon_gray);
                holder.setVisibility(R.id.tv, View.VISIBLE);
                holder.setVisibility(R.id.tv_type, View.GONE);
            } else {
                holder.setBg(R.id.left, R.drawable.bg_cash_coupon_red);
                holder.setVisibility(R.id.bt_cash_coupon_used, View.VISIBLE);
                holder.setVisibility(R.id.bt_cash_coupon_used_none, View.GONE);
                holder.setVisibility(R.id.tv, View.GONE);
                holder.setVisibility(R.id.tv_type, View.VISIBLE);
            }
            if (!ListUtils.isNullOrZeroLenght(startTime.toString())) {
                holder.setVisibility(R.id.ll_bottom, View.VISIBLE);
                holder.setVisibility(R.id.ll_bottom_one, View.GONE);
            } else {
                holder.setVisibility(R.id.ll_bottom, View.GONE);
                holder.setVisibility(R.id.ll_bottom_one, View.VISIBLE);
                if (expiryTimeUnit.toString().equals("d")) {
                    holder.setText(R.id.tv_time, "有效期：" + expireTime + "天");
                } else if (expiryTimeUnit.toString().equals("m"))
                    holder.setText(R.id.tv_time, "有效期：" + expireTime + "月");
            }

        }


        if ((Integer)storeId>0){
            holder.setText(R.id.tv_none,"店券");
            holder.setText(R.id.textView11,"店券");
              holder.setText(R.id.tv_type,"店券");
            holder.setText(R.id.tv,"店券");

        }else {
            holder.setText(R.id.tv_type,"淘券");
            holder.setText(R.id.tv_none,"淘券");
            holder.setText(R.id.textView11,"淘券");
            holder.setText(R.id.tv,"淘券");
        }


    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        //status：0未使用，1已使用，2已过期，  3,领取代金券,4,结算-可用代金券 5,结算-不可用代金券
        if (status == 0){
            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_unused);
        }
        else if (status == 1 || status == 2){
            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_lose_efficacy);

        }
        else if ( status==-1){

            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_get_none);

        }
        else if (status == 3){
            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_get);

        }
        else if (status == 4){
            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_usable);

        }
        else{
            return ViewUtils.getFragmentView(parent, R.layout.item_cash_coupon_unable);
        }
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            //立即使用
            if (status == 0) {
                MenuToolbarActivity.goToPage(0);
                return true;
            }
            //立即领取
            else if (status == 3||status==-1) return false;
            else return false;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
