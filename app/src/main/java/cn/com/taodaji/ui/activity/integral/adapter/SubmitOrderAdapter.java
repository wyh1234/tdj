package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.IntegralShopCart;
public class SubmitOrderAdapter  extends BaseRecyclerViewAdapter<IntegralShopCart.DataBean> {
    private Context context;
    private SparseArray<String> mTextCache = new SparseArray<>();
    private TextWatcher textWatcher;
    public SubmitOrderAdapter(Context context, List<IntegralShopCart.DataBean> data) {
        super(context, data, R.layout.integral_sub_order_list_item);
        this.context = context;
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
        ((TextView)holder.getView(R.id.tv_kc)).setText("库存"+bean.getRepertory());
        ((TextView)holder.getView(R.id.ed_num)).setText(""+bean.getQuantity());
        ((ImageView)holder.getView(R.id.iv)).setSelected(true);
        ((TextView)holder.getView(R.id.tv_yj)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        if (bean.getMoneyPrice().doubleValue()>0&&bean.getSalesPrice().doubleValue()>0){
            if (bean.getMoneyPrice()==bean.getSalesPrice()){
                ((TextView)holder.getView(R.id.tv_yj)).setVisibility(View.GONE);
            }else {
                ((TextView)holder.getView(R.id.tv_yj)).setVisibility(View.VISIBLE);
                ((TextView)holder.getView(R.id.tv_yj)).setText("原价:"+bean.getMoneyPrice()+"元");
            }
        }

        ImageLoaderUtils.loadImage(((ImageView)holder.getView(R.id.image)),bean.getPicUrl());

    }
}