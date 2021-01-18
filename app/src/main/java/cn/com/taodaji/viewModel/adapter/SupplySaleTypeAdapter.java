package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

public class SupplySaleTypeAdapter extends SingleRecyclerViewAdapter {
    private int saleStutas;



    public void setSaleStutas(int saleStutas) {
        this.saleStutas = saleStutas;
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
           return ViewUtils.getFragmentView(parent, R.layout.item_supply_sale_type);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

       // SupplySaleTypeBean.DataBean.StoreCategoryListBean bean=  (SupplySaleTypeBean.DataBean.StoreCategoryListBean)getListBean(position);

        TextView tv =holder.findViewById(R.id.tv_type);
        //tv.setText(bean.getCategoryName());
       //holder.setValues(R.id.tv_type,bean.getCategoryName());

        switch(saleStutas){
            case 0 :
                tv.setTextColor(UIUtils.getColor(R.color.blue_2898ec));
                //UIUtils.getContext().getResources().getDrawable(R.drawable.z_round_rect_blue_2898eb,null)
                tv.setBackgroundResource(R.drawable.z_rect_blue_light);
            break;
            case 1 :
                tv.setTextColor(UIUtils.getColor(R.color.red_fa0504));
                //UIUtils.getContext().getResources().getDrawable(R.drawable.z_round_rect_blue_2898eb,null)
                tv.setBackgroundResource(R.drawable.z_rect_red_light);
                break;
            case 2 :
                tv.setTextColor(UIUtils.getColor(R.color.gray_c2c2c2));
                //UIUtils.getContext().getResources().getDrawable(R.drawable.z_round_rect_blue_2898eb,null)
                tv.setBackgroundResource(R.drawable.z_rect_gray_light);
                break;

            default:
            break;
        }

    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation("categoryName", R.id.tv_type);
            }

        });
    }
}
