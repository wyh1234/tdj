package cn.com.taodaji.viewModel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.glide.ImageLoaderUtils;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.List;

import cn.com.taodaji.R;

public class ReleaseGoodsImageAdapter extends RecyclerView.Adapter<ReleaseGoodsImageAdapter.ViewHolder>{

    private List<String> mItemList;
    private Context mContext;

    public ReleaseGoodsImageAdapter(List<String> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ReleaseGoodsImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = ViewUtils.getFragmentView(parent, R.layout.customer_added_pictures_item);


        int w = parent.getMeasuredWidth();

        int spac = parent.getPaddingEnd() + parent.getPaddingStart() + DensityUtil.dp2px(6) * 3;


        UIUtils.setViewSize(view1, (w - spac) / 4 - 1, (w - spac) / 4);

        ReleaseGoodsImageAdapter.ViewHolder viewHolder=new ReleaseGoodsImageAdapter.ViewHolder(view1);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseGoodsImageAdapter.ViewHolder holder, int position) {
        ImageLoaderUtils.loadImage(holder.icon,mItemList.get(position));
        holder.delete_image.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagesActivity.startActivity(holder.icon, mItemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon,delete_image;
        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.image);
            delete_image = view.findViewById(R.id.delete_image);


        }
    }
}
