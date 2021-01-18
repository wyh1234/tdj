package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SelGoods;

import static com.base.utils.UIUtils.getResources;

public class SelGoodsAdapter extends RecyclerView.Adapter<SelGoodsAdapter.SelGoodsViewHolder> {
    private Context context;
    private List<SelGoods.DataBean.ItemsBean> selGoods;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SelGoodsAdapter(Context context, List<SelGoods.DataBean.ItemsBean> selGoods) {
        this.context = context;
        this.selGoods = selGoods;

    }

    @NonNull
    @Override
    public SelGoodsAdapter.SelGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.sel_goods_list_item, parent, false);
        SelGoodsViewHolder selGoodsViewHolder = new SelGoodsViewHolder(root);
        return selGoodsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelGoodsAdapter.SelGoodsViewHolder holder, int position) {
        ImageLoaderUtils.loadImage(holder.goods_image,selGoods.get(position).getImage());
        holder.tv_name.setText(selGoods.get(position).getName());
        holder.tv_title.setText("("+selGoods.get(position).getNickName()+")");
        if ((selGoods.get(position).getMaxPrice()).compareTo(new BigDecimal(-1)) == 0 && selGoods.get(position).getSpecs().size() == 1) {
            SelGoods.DataBean.ItemsBean.SpecsBean gsf = selGoods.get(position).getSpecs().get(0);
            if (gsf == null) return;
            if (gsf.getLevelType() == 1) {
                holder.tv_money.setText(selGoods.get(position).getMinPrice() + "元/" + selGoods.get(position).getUnit());
            } else {
                if (gsf.getLevelType() == 3) {
                    holder.tv_money.setText(selGoods.get(position).getMinPrice() + "元/" + selGoods.get(position).getUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + "*" + gsf.getLevel3Value() + gsf.getLevel3Unit() + "" + ")");
                } else {
                    holder.tv_money.setText(selGoods.get(position).getMinPrice() + "元/" + selGoods.get(position).getUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + ")");
                }
            }
        } else {
            holder.tv_money.setText(selGoods.get(position).getMinPrice() + "元/" + selGoods.get(position).getUnit());
        }
        if (selGoods.get(position).getProductCriteria() == 2) {
            holder.iv_type.setImageResource(R.mipmap.icon_jin_red);
        } else if (selGoods.get(position).getProductCriteria() == 1) {
            holder.iv_type.setImageResource(R.mipmap.icon_tong_blue);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
        holder.iv_sel.setSelected(selGoods.get(position).isFlag());

    }

    @Override
    public int getItemCount() {
        return selGoods.size();
    }

    class SelGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_type,goods_image,iv_sel;
        TextView tv_money,tv_name,tv_title;

        public SelGoodsViewHolder(View itemView) {
            super(itemView);
            iv_type = itemView.findViewById(R.id.iv_type);
            tv_money = itemView.findViewById(R.id.tv_money);
            goods_image = itemView.findViewById(R.id.goods_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_sel = itemView.findViewById(R.id.iv_sel);

        }
    }
}
