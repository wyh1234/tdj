package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.R;

public class ADInfoViewModel extends BaseVM {

    @Override
    protected void dataBinding() {
        putRelation(R.id.image_name, "imageName");
        putRelation(R.id.image, "imageObject");
        putRelation(R.id.count_image, "goodsCount");
        putRelation(R.id.img_special_shop, "imageResource");
    }

    @Override
    public void setValue(@NonNull TextView textView, @NonNull Object value) {

        if (textView.getId() == R.id.count_image) {
            int count = Integer.valueOf(value.toString());
            if (count > 99) {
                textView.setText("99+");
                return;
            }
        }
        super.setValue(textView, value);
    }


    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.item_view);
        putViewOnClick(R.id.image_name);
        putViewOnClick(R.id.image);
        putViewOnClick(R.id.delete_image);
    }


}
