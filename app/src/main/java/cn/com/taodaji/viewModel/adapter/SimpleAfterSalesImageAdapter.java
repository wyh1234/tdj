package cn.com.taodaji.viewModel.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import com.base.viewModel.BaseVM;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SimpleAfterSalesImageAdapter extends SingleRecyclerViewAdapter {
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.image, "imageObject");
                putViewOnClick(R.id.image);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = ViewUtils.getFragmentView(parent, R.layout.t_imageview);
        int w = parent.getMeasuredWidth();
        int h = (w - DensityUtil.dp2px(20)) / 3;
        UIUtils.setViewSize(view, h, h);
        return view;
    }


    public void setData(String images) {
        if (TextUtils.isEmpty(images)) return;
        String[] str = images.split(",");
        List<ADInfo> list = new ArrayList<>();
        for (String ss : str) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageObject(ss);
            list.add(adInfo);
        }
        setList(list);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
//            ZoomImageActivity.startActivity(view.getContext(), String.valueOf(JavaMethod.getFieldValue(bean, "imageObject")));
            ImagesActivity.startActivity(getRecyclerView(),R.id.image,"imageObject",position);
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
