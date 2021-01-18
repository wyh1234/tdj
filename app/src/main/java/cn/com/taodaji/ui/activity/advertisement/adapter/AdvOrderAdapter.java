package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.utils.UIUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CreateTfAdvManage;
import cn.com.taodaji.model.entity.SelGoods;

public class AdvOrderAdapter extends RecyclerView.Adapter<AdvOrderAdapter.AdvOrderViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    public  List<CreateTfAdvManage> data;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdvOrderAdapter(Context context, List<CreateTfAdvManage> data){
        this.context=context;
        this.data=data;

    }
    @NonNull
    @Override
    public AdvOrderAdapter.AdvOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.adv_order_commit_item, parent, false);
        AdvOrderViewHolder advOrderViewHolder = new AdvOrderViewHolder(root);
        return advOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvOrderAdapter.AdvOrderViewHolder holder, int position) {
        setOtherMoney(data.get(position).getGoods(), position,holder.mLlContain);
        if (data.get(position).isF()){
            holder.mLlContain.setVisibility(View.GONE);
        }else {
            holder.mLlContain.setVisibility(View.VISIBLE);
        }
        holder.iv_right.setSelected(!data.get(position).isB());
        holder.tv_num.setText("广告计划"+(position+1));


        holder.rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
        if (data.get(position).isB()){
           BigDecimal decimal= data.get(position).getAdvPackagePice().multiply(new BigDecimal(data.get(position).getGoods().size()*Integer.parseInt(data.get(position).getDay())));
            holder.tv_money.setText("￥"+decimal);
        }else {
            BigDecimal decimal= data.get(position).getAdvPackagePice().multiply(new BigDecimal(data.get(position).getGoods().size()));
            holder.tv_money.setText("￥"+decimal);
        }


    }

    private void setOtherMoney(List<SelGoods.DataBean.ItemsBean> goods, int position, LinearLayout mLlContain) {
        mLlContain.removeAllViews();
        LayoutInflater from = LayoutInflater.from(context);
        for (int i = 0; i < goods.size(); i++) {
            View view = from.inflate(R.layout.include_order_goods_item, null, false);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_name_tag = (TextView) view.findViewById(R.id.tv_name_tag);
            TextView tv_adv_package = (TextView) view.findViewById(R.id.tv_adv_package);
            tv_name.setText(goods.get(i).getName());
            tv_name_tag.setText("("+goods.get(i).getNickName()+")");
            if (data.get(position).isB()){
                tv_adv_package.setText(data.get(position).getDay()+"天");
            }else {
                if (!UIUtils.isNullOrZeroLenght(data.get(position).getAdvPackageName())){
                    if (!UIUtils.isNullOrZeroLenght(data.get(position).getJsDay())){
                        String strMsg = data.get(position).getAdvPackageName()+"<font color=\"#FD0404\">"+data.get(position).getJsDay()+"</font>"+data.get(position).getAdvPackagePice()+"元";
                        tv_adv_package.setText(Html.fromHtml(strMsg));

                    }else {
                        tv_adv_package.setText(data.get(position).getAdvPackageName()+data.get(position).getAdvPackagePice()+"元");
                    }
                }
            }



            mLlContain.addView(view);



        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class AdvOrderViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLlContain;
        ImageView iv_right;
        TextView tv_num,tv_money;
        RelativeLayout rl_right;

        public AdvOrderViewHolder(View itemView) {
            super(itemView);
            mLlContain=itemView.findViewById(R.id.ll_goods);
            iv_right=itemView.findViewById(R.id.iv_right);
            tv_num=itemView.findViewById(R.id.tv_num);
            rl_right=itemView.findViewById(R.id.rl_right);
            tv_money=itemView.findViewById(R.id.tv_money);
        }
    }
}
