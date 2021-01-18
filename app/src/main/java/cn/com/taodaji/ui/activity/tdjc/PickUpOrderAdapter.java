package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.model.entity.OrderList;
import cn.com.taodaji.model.entity.PickUpOrder;
import cn.com.taodaji.model.event.OrderDetailEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.orderPlace.OrderDetailActivity;
import tools.extend.SimpleStringUtils;

public class PickUpOrderAdapter  extends RecyclerView.Adapter<PickUpOrderAdapter.PickUpOrderViewHolder>{
   private Context context;
   private List<PickUpOrder.DataObject.ItemsBean> items;
    public PickUpOrderAdapter(Context context, List<PickUpOrder.DataObject.ItemsBean> items){
        this.context=context;
        this.items=items;

    }
    @NonNull
    @Override
    public PickUpOrderAdapter.PickUpOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.pick_up_order_item, parent, false);
        PickUpOrderAdapter.PickUpOrderViewHolder pickUpOrderViewHolder = new PickUpOrderAdapter.PickUpOrderViewHolder(root);
        return pickUpOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickUpOrderAdapter.PickUpOrderViewHolder holder, int position) {

        holder.order_num.setText(items.get(position).getCommunityShortName());
        holder.count_image.setText(items.get(position).getItemCount()+"");
        holder.cart_price_sum.setText(items.get(position).getTotalCost()+"");
        holder.order_time.setText(items.get(position).getCreateTime());
        if (items.get(position).getDeliveryType()==1){
            holder.order_state.setText("客户自提");
        }else {
            holder.order_state.setText("送货上门");
        }

        if (items.get(position).getExtraField().size()>2){
            holder.order_fold.setVisibility(View.VISIBLE);
        }else {
            holder.order_fold.setVisibility(View.GONE);
        }
        if (items.get(position).isFold()){
            holder.order_fold.setText("收起更多");
            setgoods(items.get(position).getExtraField(),items.get(position).getExtraField().size(),holder.ll_goods,position);


        }else {
            holder.order_fold.setText("展开更多");
            if (items.get(position).getExtraField().size()>2){
                setgoods(items.get(position).getExtraField(),2,holder.ll_goods,position);

            } else {
                setgoods(items.get(position).getExtraField(),items.get(position).getExtraField().size(),holder.ll_goods,position);
            }

        }

        holder.order_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.get(position).isFold()){
                    items.get(position).setFold(false);
                }else {
                    items.get(position).setFold(true);
                }
                notifyDataSetChanged();

            }
        });



    }
    private void setgoods(List<CartGoodsBean>goods, int count, LinearLayout mLlContain,int pos) {
        mLlContain.removeAllViews();
        LayoutInflater from = LayoutInflater.from(context);
        for (int i = 0; i < count; i++) {
            View view = from.inflate(R.layout.pick_up_order_goods, null, false);
            mLlContain.addView(view);
            TextView tv_cash_pledge_name=view.findViewById(R.id.tv_cash_pledge_name);
            TextView tv_cash_pledge_price=view.findViewById(R.id.tv_cash_pledge_price);
            TextView tv_textView1=view.findViewById(R.id.tv_textView1);
            TextView tv_cash_pledge_count=view.findViewById(R.id.tv_cash_pledge_count);
            RelativeLayout ll_cash_pledge=view.findViewById(R.id.ll_cash_pledge);
            TextView tv_cash_pledge_sum=view.findViewById(R.id.tv_cash_pledge_sum);
            GlideImageView goods_image=view.findViewById(R.id.goods_image);
            GlideImageView special_offer=view.findViewById(R.id.special_offer);
            GlideImageView img_hot_sale=view.findViewById(R.id.img_hot_sale);
            GlideImageView jinpin=view.findViewById(R.id.jinpin);
            TextView  tv_isP=view.findViewById(R.id.tv_isP);
            TextView goods_price=view.findViewById(R.id.goods_price);
            TextView goods_name_txt = view.findViewById(R.id.goods_name);
            TextView goods_unit = view.findViewById(R.id.goods_unit);
            TextView goods_unit2=view.findViewById(R.id.goods_unit2);
            TextView cart_price=view.findViewById(R.id.cart_price);
            TextView level3Value=view.findViewById(R.id.level3Value);
            TextView specification_split=view.findViewById(R.id.specification_split);
            TextView level2Unit=view.findViewById(R.id.level2Unit);
            TextView level2Value=view.findViewById(R.id.level2Value);
            TextView level3Unit=view.findViewById(R.id.level3Unit);
            TextView goods_count=view.findViewById(R.id.goods_count);

            TextView textView1=view.findViewById(R.id.textView1);
            TextView textView2=view.findViewById(R.id.textView2);
            if (goods.get(i).getIsForegift()==0){
                ll_cash_pledge.setVisibility(View.GONE);
            }else {
                ll_cash_pledge.setVisibility(View.VISIBLE);
            }
            tv_cash_pledge_name.setText(goods.get(i).getProductName());

            tv_cash_pledge_price.setText(goods.get(i).getForegift()+"");
            if (goods.get(i).getPackageStatus()==-1){
                tv_textView1.setText("订购");
            }else if (goods.get(i).getPackageStatus()==0){
                tv_textView1.setText("未退");
            }else {
                tv_textView1.setText("已退完");
            }

            tv_cash_pledge_count.setText(goods.get(i).getPackageNum()+"");
            tv_cash_pledge_sum.setText(goods.get(i).getPackageFee()+"");
            ImageLoaderUtils.loadImage(goods_image,goods.get(i).getProductImage());
            //促销
            if (goods.get(i).getProductType() == 1) {
                special_offer.setVisibility(View.VISIBLE);
            } else {
                special_offer.setVisibility( View.GONE);
            }
            img_hot_sale.setVisibility( View.GONE);
            jinpin.setVisibility( View.GONE);
            tv_isP.setVisibility( View.GONE);

            SpecialStringBuilder sb = SimpleStringUtils.getTitleName(goods.get(i).getProductName(), goods.get(i).getNickName(), goods.get(i).getProductType(),
                    goods.get(i).getProductCriteria(),goods.get(i).getIsP());
            goods_name_txt.setText(sb.getCharSequence());

            goods_price.setText(goods.get(i).getProductPrice()+"");
            goods_unit.setText(goods.get(i).getProductUnit());
            goods_unit2.setText(goods.get(i).getAvgUnit());
            cart_price.setText(goods.get(i).getTotalPrice()+"");

            if (goods.get(i).getLevelType()==3){
                level3Value.setVisibility(View.VISIBLE);
                specification_split.setVisibility( View.VISIBLE);
                level3Unit.setVisibility( View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                level2Value.setVisibility( View.VISIBLE);
                level2Unit.setVisibility(View.VISIBLE);

            }else if (goods.get(i).getLevelType()==2){
                level3Value.setVisibility(View.GONE);
                specification_split.setVisibility( View.GONE);
                level3Unit.setVisibility( View.GONE);

                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                level2Value.setVisibility( View.VISIBLE);
                level2Unit.setVisibility(View.VISIBLE);
            }else {
                level3Value.setVisibility(View.GONE);
                specification_split.setVisibility( View.GONE);
                level3Unit.setVisibility( View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                level2Value.setVisibility( View.GONE);
                level2Unit.setVisibility(View.GONE);
            }
            level3Value.setText(goods.get(i).getLevel3Value()+"");
            level2Value.setText(goods.get(i).getLevel2Value()+"");
            level2Unit.setText(goods.get(i).getLevel2Unit()+"");
            level3Unit.setText(goods.get(i).getLevel3Unit()+"");

            if (Constants.specification_unit_base.contains(goods.get(i).getAvgUnit())) {
                if (goods.get(i).getLevelType() == 1) {
                    goods_count.setText(goods.get(i).getProductQty()+"");
                } else if (goods.get(i).getLevelType() == 2) {
                    goods_count.setText(goods.get(i).getLevel2Value().multiply(BigDecimal.valueOf(goods.get(i).getProductQty()))+"");
                } else if (goods.get(i).getLevelType() == 3) {
                    goods_count.setText( goods.get(i).getLevel3Value().multiply(goods.get(i).getLevel2Value().multiply(BigDecimal.valueOf(goods.get(i).getProductQty())))+"");
                }
            }
            //均价单位是二级单位
            else {
                if (goods.get(i).getLevelType() == 2) {
                    goods_count.setText( goods.get(i).getProductQty()+"");
                } else if (goods.get(i).getLevelType() == 3) {
                    goods_count.setText( goods.get(i).getLevel2Value().multiply(BigDecimal.valueOf(goods.get(i).getProductQty()))+"");
                }
            }
            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailActivity.startActivity(view.getContext(), new OrderDetailEvent( items.get(pos).getOrderId(), items.get(pos).getOrderNo(),  items.get(pos).getCouponAmount()));

                }
            });
        }


    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class  PickUpOrderViewHolder extends RecyclerView.ViewHolder{
        TextView order_fold;
        LinearLayout ll_goods;
        TextView order_num;
        TextView count_image;
        TextView cart_price_sum;
        TextView order_time;
        TextView order_state;




        public PickUpOrderViewHolder(View itemView) {
            super(itemView);

            order_fold=itemView.findViewById(R.id.order_fold);
            ll_goods=itemView.findViewById(R.id.ll_goods);
            order_num=itemView.findViewById(R.id.order_num);
            count_image=itemView.findViewById(R.id.count_image);
            cart_price_sum=itemView.findViewById(R.id.cart_price_sum);
            order_time=itemView.findViewById(R.id.order_time);
            order_state=itemView.findViewById(R.id.order_state);

        }
    }
}
