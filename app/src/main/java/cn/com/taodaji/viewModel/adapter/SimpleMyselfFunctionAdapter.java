package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;
import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;

public class SimpleMyselfFunctionAdapter extends SingleRecyclerViewAdapter {
    int w = 0;
    int h = 0;
    boolean isRed=false;

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new ADInfoViewModel(){
            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.item_view);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view1 = ViewUtils.getFragmentView(parent, R.layout.item_myselft_funcation_64);
        View image = ViewUtils.findViewById(view1, R.id.image);
        UIUtils.setViewSize(image, w, h);
        View image_ani = ViewUtils.findViewById(view1, R.id.image_ani);
        UIUtils.setViewSize(image_ani, w, h);
        return view1;
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        Object obj = JavaMethod.getFieldValue(getListBean(position), "goodsCount");
        if (obj == null) return;
        if (Integer.valueOf(obj.toString()) == 0)
            holder.setVisibility(R.id.count_image, View.GONE);
        else{
            TextView imageCount=holder.findViewById(R.id.count_image);
            imageCount.setVisibility(View.VISIBLE);

            imageCount.setBackgroundResource(R.drawable.bg_count_red);
            imageCount.setTextColor(UIUtils.getColor(R.color.white));

          /*  if (isRed) {
                imageCount.setBackgroundResource(R.drawable.bg_count_red);
                imageCount.setTextColor(UIUtils.getColor(R.color.white));
            }else {
                imageCount.setBackgroundResource(R.mipmap.icon_circle_orange);
                imageCount.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
            }*/
          }
        ImageView img_month_menu_gif=holder.findViewById(R.id.img_month_menu_gif);
        Object obj1 = JavaMethod.getFieldValue(getListBean(position), "imageName");
        if (obj1 == null) return;
        if (obj1.toString().equals("对账单")&&DateUtils.getToDay()==2) {
            img_month_menu_gif.setVisibility(View.VISIBLE);
            ImageLoaderUtils.loadImage(img_month_menu_gif,R.drawable.month_menu);
        }else {
            img_month_menu_gif.setVisibility(View.GONE);
        }
    }

    public void setViewSize(int wpx, int hpx) {
        w = wpx;
        h = hpx;
    }


}
