package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.IntegralShop;
import cn.com.taodaji.model.entity.IntegralShopCart;

public class IntegralShopCartAdapter extends BaseRecyclerViewAdapter<IntegralShopCart.DataBean> {
    private Context context;
    private SparseArray<String> mTextCache = new SparseArray<>();
    private TextWatcher textWatcher;
    private List<IntegralShopCart.DataBean> data;
     private OnItemClickListener mListener;
    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
//        void onItemClick( View v, int position);
        void onItemSelClick( View v, int position);
        void onItemNumClick( View v, int position);
        void onItemAddClick( View v, int position);
        void onItemSubClick( View v, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    public IntegralShopCartAdapter(Context context, List<IntegralShopCart.DataBean> data) {
        super(context, data, R.layout.integral_cart_list_item);
        this.context = context;
        this.data=data;
    }


    @Override
    protected void onBindData(RecyclerViewHolder holder, IntegralShopCart.DataBean bean, int position) {
        ((TextView)holder.getView(R.id.shop_name)).setText(bean.getName());
        //1.现金，2.积分，3，积分加现金
        if (bean.getSellType()==1){
            ((TextView)holder.getView(R.id.intergral_num_tv)).setText(bean.getSalesPrice()+"元");

        }else if (bean.getSellType()==2){
            ((TextView)holder.getView(R.id.intergral_num_tv)).setText(bean.getSalesIntegral()+"积分");
        }else {
            ((TextView)holder.getView(R.id.intergral_num_tv)).setText(bean.getSalesPrice()+"元+"+bean.getSalesIntegral()+"积分");
        }

        ((TextView)holder.getView(R.id.tv_yj)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        if (data.get(position).getMoneyPrice().doubleValue()>0&&data.get(position).getSalesPrice().doubleValue()>0){
            if (data.get(position).getMoneyPrice()==data.get(position).getSalesPrice()){
                ((TextView)holder.getView(R.id.tv_yj)).setVisibility(View.GONE);
            }else {
                ((TextView)holder.getView(R.id.tv_yj)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_yj)).setText("原价:"+data.get(position).getMoneyPrice()+"元");
            }
        }


        ((TextView)holder.getView(R.id.tv_kc)).setText("库存"+bean.getRepertory());
        ((TextView)holder.getView(R.id.ed_num)).setText(""+bean.getQuantity());
        ((ImageView)holder.getView(R.id.iv)).setSelected(bean.isB());
        ImageLoaderUtils.loadImage(((ImageView)holder.getView(R.id.image)),bean.getPicUrl());
    /*    holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,  position);
            }
        });*/

        ((TextView)holder.getView(R.id.ed_num)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemNumClick( view, position);

            }
        });
        ((ImageView)holder.getView(R.id.iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemSelClick( view, position);

            }
        });
        ((ImageView)holder.getView(R.id.add_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemAddClick( view, position);

            }
        });

        ((ImageView)holder.getView(R.id.sub_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemSubClick( view, position);

            }
        });



/*        //购物车数量
        ((EditText)holder.getView(R.id.ed_num)).setSelection(((EditText) holder.getView(R.id.ed_num)).getText().toString().length());
        if ( ((EditText)holder.getView(R.id.ed_num)).getTag() != null && (boolean) ( ((EditText)holder.getView(R.id.ed_num)).getTag())) {
            return;
        }

        ((EditText)holder.getView(R.id.ed_num)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 如果填入数据与缓存数据相同返回
                if (TextUtils.equals(mTextCache.get(holder.getAdapterPosition()), editable.toString())) {
                    return;
                }
                mTextCache.put(holder.getAdapterPosition(), editable.toString());
//                data.get(holder.getLayoutPosition()).setFinal_value(editable.toString());

            }
        });

        ((EditText)holder.getView(R.id.ed_num)).setTag(true);
        ((EditText)holder.getView(R.id.ed_num)).setTag(R.id.ed_num, textWatcher);*/
    }
}