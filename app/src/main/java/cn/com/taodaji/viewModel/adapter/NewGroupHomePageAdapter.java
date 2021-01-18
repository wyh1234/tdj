package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FindByActivitiesID;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;

public class NewGroupHomePageAdapter extends   RecyclerView.Adapter<NewGroupHomePageAdapter.NewGroupHomePageViewHolder>{
    private Context context;
    private List<GoodsInformation> items;
//    private OnItemClickListener listener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    public interface OnItemClickListener {
//        void OnItemClick1(View view, int position);
//    }
    public NewGroupHomePageAdapter(Context context){
        this.context=context;

    }

    public List<GoodsInformation> getItems() {
        return items;
    }

    public void setItems(List<GoodsInformation> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NewGroupHomePageAdapter.NewGroupHomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_homepage_grid_child_one, parent, false);

        root.setPadding(10, 0, 10, 0);
        int w = UIUtils.getScreenWidthPixels();
        int image_w = (w - 120) / 3;
        ImageView imageView = ViewUtils.findViewById(root, R.id.goods_image);
        UIUtils.setViewSize(imageView, image_w, image_w);
        NewGroupHomePageAdapter.NewGroupHomePageViewHolder newGroupHomePageViewHolder=new NewGroupHomePageAdapter.NewGroupHomePageViewHolder(root);
        return newGroupHomePageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewGroupHomePageAdapter.NewGroupHomePageViewHolder newGroupHomePageViewHolder, int i) {
        newGroupHomePageViewHolder.goods_name.setText(getItems().get(i).getName());
        newGroupHomePageViewHolder.goods_unit.setText(getItems().get(i).getSpecs().get(0).getLevel1Unit());
        newGroupHomePageViewHolder.goods_price.setText(String.valueOf(getItems().get(i).getSpecs().get(0).getPrice()));
        ImageLoaderUtils.loadImage(newGroupHomePageViewHolder.goods_image,getItems().get(i).getImage());
            newGroupHomePageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.startActivity(context,getItems().get(i).getEntityId());


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

    class NewGroupHomePageViewHolder extends RecyclerView.ViewHolder {
        TextView goods_name,goods_price,goods_unit;
        GlideImageView goods_image;

        public NewGroupHomePageViewHolder(@NonNull View itemView) {
            super(itemView);
            goods_name=itemView.findViewById(R.id.goods_name);
            goods_price=itemView.findViewById(R.id.goods_price);
            goods_unit=itemView.findViewById(R.id.goods_unit);
            goods_image=itemView.findViewById(R.id.goods_image);
        }
    }
}
