package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.HomeStore;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;

public class HomeStoreAdapter extends   RecyclerView.Adapter<HomeStoreAdapter.HomeStoreViewHolder>{
    private Context context;
    private  List<HomeStore.DataBean.ItemsBean> items;;

    public HomeStoreAdapter(Context context){
        this.context=context;

    }

    public List<HomeStore.DataBean.ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<HomeStore.DataBean.ItemsBean> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HomeStoreAdapter.HomeStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_homepage_store, parent, false);

        root.setPadding(10, 0, 10, 0);
        int w = UIUtils.getScreenWidthPixels();
        int image_w = (w - 120) / 4;
        ImageView imageView = ViewUtils.findViewById(root, R.id.goods_image);
        UIUtils.setViewSize(imageView, image_w, image_w);
        HomeStoreAdapter.HomeStoreViewHolder newGroupHomePageViewHolder=new  HomeStoreAdapter.HomeStoreViewHolder(root);
        return newGroupHomePageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull   HomeStoreAdapter.HomeStoreViewHolder homeStoreViewHolder, int i) {
        ImageLoaderUtils.loadImage(homeStoreViewHolder.goods_image,getItems().get(i).getImageUrl());
        homeStoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItems().get(i).getType()==1){
                    GoodsDetailActivity.startActivity(context, Integer.parseInt(getItems().get(i).getInfo()));

                }else {
                    ShopDetailInformationActivity.startActivity(context, Integer.parseInt(getItems().get(i).getInfo()));

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public void clear() {
        if (items!=null){
            if (items.size()>0){
                items.clear();
                notifyDataSetChanged();
            }

        }


    }

    class HomeStoreViewHolder extends RecyclerView.ViewHolder {
        GlideImageView goods_image;

        public HomeStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            goods_image=itemView.findViewById(R.id.goods_image);
        }
    }
}
