package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.application.MyApplication;
import cn.com.taodaji.model.entity.PickUpDetail;
import cn.com.taodaji.model.entity.PickUpGoods;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;

public class PickupOrderDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = PickupOrderDetailAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PickupOrderDetailAdapter(List<MultiItemEntity> data,Context context) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_pickup_detail);
        addItemType(TYPE_PERSON, R.layout.item_pickup_goods);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final PickUpDetail detail = (PickUpDetail) item;
                holder.setText(R.id.tv_goods_name,detail.getName())
                        .setText(R.id.tv_goods_shortname,"（"+detail.getShortName()+"）")
                        .setText(R.id.tv_receive_fee,detail.getReceiveFee()+"")
                        .setText(R.id.tv_pickup_fee,detail.getPickupFee()+"")
                        .setText(R.id.tv_order_count,"共"+detail.getOrderCount()+"个订单")
                        .setText(R.id.tv_order_total,detail.getTotal()+"");
                ImageView icon = holder.getView(R.id.iv_item_icon);
                Glide.with(mContext).load(detail.getUrl()).into(icon);
                holder.setOnClickListener(R.id.order_fold, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        if (detail.isExpanded()) {
                            collapse(pos, true);
                            holder.setText(R.id.order_fold,"收起");
                        } else {
                            expand(pos, true);
                            holder.setText(R.id.order_fold,"展开更多");
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final PickUpGoods goods = (PickUpGoods) item;
                holder.setText(R.id.tv_shop_name, goods.getShopName())
                        .setText(R.id.tv_order_number, "商品单号："+goods.getOrderNo())
                        .setText(R.id.tv_goods_unit,goods.getPriceUnit())
                        .setText(R.id.tv_goods_count,"x"+goods.getGoodsCount())
                        .setText(R.id.tv_goods_fee,goods.getPickupFee()+"");
                break;
            default:
                break;
        }
    }
}
