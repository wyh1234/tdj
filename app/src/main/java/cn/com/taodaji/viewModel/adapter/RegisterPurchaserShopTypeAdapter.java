package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopTypeBean;

public class RegisterPurchaserShopTypeAdapter extends SingleRecyclerViewAdapter {
    private int type;

    public void setType(int type) {

        this.type = type;
    }
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("name",R.id.text_name);
                //putRelation("selected",R.id.item_view);
            }

            @Override
            protected void addOnclick() {

                putViewOnClick(R.id.item_view);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_register_purchaser_shop_type);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);


      /*  TextView text_name=holder.findViewById(R.id.text_name);
        LinearLayout line_type_name=holder.findViewById(R.id.line_type_name);
        ImageView img_select=holder.findViewById(R.id.img_select);

        switch(type){
            case 1 :
                img_select.setVisibility(View.VISIBLE);
                text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_ff8207));
                img_select.setImageResource(R.drawable.s_orange_arrow_right_purchaser);
                line_type_name.setBackgroundResource(R.drawable.r_gray_f3_f7);

                break;
            case 2 :
                img_select.setVisibility(View.VISIBLE);
                text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_ff8207));
                img_select.setImageResource(R.drawable.s_orange_arrow_right_purchaser);
                line_type_name.setBackgroundResource(R.drawable.r_gray_white_f3);
                break;
            case 3 :
                text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_fb7646));
                img_select.setVisibility(View.GONE);
                line_type_name.setBackgroundResource(R.drawable.r_gray_white_ffb976);
                break;
        }*/
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        ShopTypeBean bean=(ShopTypeBean)getListBean(position);

        TextView text_name=holder.findViewById(R.id.text_name);
        LinearLayout line_type_name=holder.findViewById(R.id.line_type_name);
        ImageView img_select=holder.findViewById(R.id.img_select);

        if (bean.isAdd()) {
            text_name.setTextColor(UIUtils.getColor(R.color.fb7646));//fb7646
            img_select.setVisibility(View.GONE);
            line_type_name.setBackgroundColor(UIUtils.getColor(R.color.fff2e9));//fff2e9
        }else{
            switch(type){
                case 1 :
                    if (bean.isCheck() ) {
                        img_select.setVisibility(View.VISIBLE);
                        text_name.setTextColor(UIUtils.getColor(R.color.ff8207));
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.gray_f7));
                    }else {
                        img_select.setVisibility(View.GONE);
                        text_name.setTextColor(UIUtils.getColor(R.color.gray_69));
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.gray_f3));
                    }
                  /*  img_select.setVisibility(View.VISIBLE);
                    text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_ff8207));
                    img_select.setImageResource(R.drawable.s_orange_arrow_right_purchaser);
                    line_type_name.setBackgroundResource(R.drawable.r_gray_f3_f7);*/

                    break;
                case 2 :
                    if (bean.isCheck() ) {
                        img_select.setVisibility(View.VISIBLE);
                        text_name.setTextColor(UIUtils.getColor(R.color.ff8207));
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.white));
                    }else {
                        img_select.setVisibility(View.GONE);
                        text_name.setTextColor(UIUtils.getColor(R.color.gray_69));
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.gray_f7));
                    }
                   /* img_select.setVisibility(View.VISIBLE);
                    text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_ff8207));
                    img_select.setImageResource(R.drawable.s_orange_arrow_right_purchaser);
                    line_type_name.setBackgroundResource(R.drawable.r_gray_white_f3);*/
                    break;
                case 3 :
                    if (bean.isCheck() ) {
                        text_name.setTextColor(UIUtils.getColor(R.color.fb7646));
                        img_select.setVisibility(View.GONE);
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.fff2e9));
                    }else {
                        img_select.setVisibility(View.GONE);
                        text_name.setTextColor(UIUtils.getColor(R.color.gray_69));
                        line_type_name.setBackgroundColor(UIUtils.getColor(R.color.white));
                    }
                  /*  text_name.setTextColor(UIUtils.getColorStateList(R.color.s_gray_66_fb7646));
                    img_select.setVisibility(View.GONE);
                    line_type_name.setBackgroundResource(R.drawable.r_gray_white_ffb976);*/
                    break;
            }
        }


    }
}
