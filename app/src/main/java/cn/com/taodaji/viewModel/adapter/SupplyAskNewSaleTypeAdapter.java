package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.RegisterSaleTypeBean;

public class SupplyAskNewSaleTypeAdapter extends SingleRecyclerViewAdapter {

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
           return ViewUtils.getFragmentView(parent, R.layout.item_ask_supply_sale_type);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        RegisterSaleTypeBean.DataBean.ListBean bean=  (RegisterSaleTypeBean.DataBean.ListBean)getListBean(position);

        CheckedTextView tv =holder.findViewById(R.id.tv_type);
        tv.setText(bean.getCategoryName());

      /*  tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.isChecked()) {
                    bean.setSelect(false);
                    tv.setChecked(false);
                }else{
                    bean.setSelect(true);
                    tv.setChecked(true);
                }
            }
        });*/

        //0-可申请，1-不可申请
        if (bean.getIsApply()==0) {
            tv.setClickable(true);
            tv.setBackgroundResource(R.drawable.s_whole_blue_white);
            if (bean.isSelect()) {
                tv.setTextColor(UIUtils.getColor(R.color.white));
                tv.setChecked(true);
            }else{
                tv.setTextColor(UIUtils.getColor(R.color.blue_2898eb));
                tv.setChecked(false);
            }
        } else {
            tv.setClickable(false);
            tv.setTextColor(UIUtils.getColor(R.color.gray_c2c2c2));
            //UIUtils.getContext().getResources().getDrawable(R.drawable.z_round_rect_blue_2898eb,null)
            tv.setBackgroundResource(R.drawable.z_rect_gray_light);
        }

    }

    @Override
    public void initBaseVM() {
            putBaseVM(TYPE_CHILD, new BaseVM() {
                @Override
                protected void dataBinding() {
                   // putRelation("categoryName", R.id.tv_type);
                }

                @Override
                protected void addOnclick() {
                    putViewOnClick(R.id.tv_type);
                }
            });
    }
}
