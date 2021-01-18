package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.base.glide.ImageLoaderUtils;
import com.base.utils.ADInfo;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bigkoo.convenientbanner.holder.Holder;

import cn.com.taodaji.R;

/**
 * Created by Administrator on 2018/4/17.
 *
 */

public class BannerHolderView implements Holder<ADInfo> {
    ImageView bannerImg;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);

        bannerImg= view.findViewById(R.id.banner_img);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, ADInfo data) {
        ImageLoaderUtils.loadImage(bannerImg,data.getImageObject());
    }
}
