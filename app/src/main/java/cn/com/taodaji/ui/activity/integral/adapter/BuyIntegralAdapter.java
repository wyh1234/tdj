package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.BuyIntegral;
import cn.com.taodaji.model.entity.FiltrateService;
import cn.com.taodaji.model.entity.QueryIntergral;

public class BuyIntegralAdapter extends BaseRecyclerViewAdapter<QueryIntergral.QueryIntergralData> {
    private Context context;
    public  BuyIntegralAdapter(Context context, List<QueryIntergral.QueryIntergralData> data) {
        super(context, data, R.layout.buy_itegral_list_item);
        this.context=context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, QueryIntergral.QueryIntergralData bean, int position) {
        holder.getView(R.id.rl_image_bg).setSelected(bean.isB());
        if (bean.isB()) {
            ((TextView) holder.getView(R.id.tv_num)).setTextColor(context.getResources().getColor(R.color.white));
            ((TextView) holder.getView(R.id.tv_money)).setTextColor(context.getResources().getColor(R.color.white));
            ((TextView) holder.getView(R.id.tv1)).setTextColor(context.getResources().getColor(R.color.white));
            ((TextView) holder.getView(R.id.tv_jf)).setTextColor(context.getResources().getColor(R.color.white));
        } else {
            ((TextView) holder.getView(R.id.tv1)).setTextColor(context.getResources().getColor(R.color.buy_list_bg));
            ((TextView) holder.getView(R.id.tv_jf)).setTextColor(context.getResources().getColor(R.color.buy_list_bg));
            ((TextView) holder.getView(R.id.tv_num)).setTextColor(context.getResources().getColor(R.color.buy_list_bg));
            ((TextView) holder.getView(R.id.tv_money)).setTextColor(context.getResources().getColor(R.color.buy_list_bg));
        }
        ((TextView) holder.getView(R.id.tv_num)).setText(bean.getGotAmount());
        ((TextView) holder.getView(R.id.tv_money)).setText(bean.getPrice()+"");


    }
}
