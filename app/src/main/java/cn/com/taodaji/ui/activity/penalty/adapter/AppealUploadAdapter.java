package cn.com.taodaji.ui.activity.penalty.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.base.glide.ImageLoaderUtils;

import java.util.ArrayList;

import cn.com.taodaji.R;

public class AppealUploadAdapter extends RecyclerView.Adapter<AppealUploadAdapter.AppealUploadViewHolder> {
    private ArrayList<String> mURL=new ArrayList<>();

    private Context context;

    public ArrayList<String> getmURL() {
        return mURL;
    }

    public void setmURL(ArrayList<String> mURL) {
        this.mURL = mURL;
    }


    public AppealUploadAdapter(Context context){
        this.context=context;

    }
    public void setData(String url) {
        mURL.add(url);
        notifyDataSetChanged();
    }


    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemViewClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppealUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View picView = View.inflate(parent.getContext(), R.layout.recycler_pic_item, null);
        return new AppealUploadViewHolder(picView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppealUploadViewHolder holder, final int position) {
            if (position <mURL.size()) {
                holder.back_pic.setVisibility(View.VISIBLE);
                ImageLoaderUtils.loadImage(holder.item_pay_pic,mURL.get(position));
            }else {
                holder.item_pay_pic.setImageResource(R.mipmap.upload_image);
                holder.back_pic.setVisibility(View.GONE);
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.OnItemViewClick(holder.itemView, holder.getLayoutPosition());

            }
        });
        holder.back_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mURL.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        //含有9张图片，直接展示，不需要“加号”图片
        if (mURL!=null){
            if (mURL.size() == 3) {
                return 3;
            }
            //小于9张需要“加号”图片
            return mURL.size() + 1;
        }else {
            return 1;
        }

    }


    class AppealUploadViewHolder extends RecyclerView.ViewHolder{
        ImageView item_pay_pic,back_pic;
        public AppealUploadViewHolder(View itemView) {
            super(itemView);
            item_pay_pic = itemView.findViewById(R.id.item_pay_pic);
            back_pic = itemView.findViewById(R.id.back_pic);
        }
    }
}
