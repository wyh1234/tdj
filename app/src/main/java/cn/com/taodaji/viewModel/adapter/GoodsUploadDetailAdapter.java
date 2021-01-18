package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FindProductDetail;

/**
 * Created by yangkuo on 2018-03-06.
 */

public class GoodsUploadDetailAdapter extends SingleRecyclerViewAdapter {


    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (FindProductDetail.DataBean.class == obj.getClass()) {
            FindProductDetail.DataBean bean = (FindProductDetail.DataBean) obj;
            return bean.getType();//type  1为图片，2为文字
        } else return super.concludeItemViewType(obj);


    }

    @Override
    public void initBaseVM() {

        putBaseVM(1, new BaseVM() {
             boolean isTrue = false;
            @Override
            protected void dataBinding() {
                putRelation(R.id.iv_image, "description");
                putViewOnClick(R.id.tv_delete);
                putViewOnClick(R.id.tv_update);

            }


            @Override
            public <T> void setValues(BaseViewHolder viewHolder, T bean) {
                super.setValues(viewHolder, bean);
                if (isTrue = false) {
                    viewHolder.setVisibility(R.id.tv_delete, View.GONE);
                    viewHolder.setVisibility(R.id.tv_update, View.GONE);
                }


            }
        });
        putBaseVM(2, new BaseVM() {
            boolean isTrue = false;
            @Override
            protected void dataBinding() {
                putRelation(R.id.et_description, "description");
                putViewOnClick(R.id.tv_delete);
                putViewOnClick(R.id.et_description);

            }

            @Override
            public <T> void setValues(BaseViewHolder viewHolder, T bean) {
                super.setValues(viewHolder, bean);
                if (isTrue = true) {
                    //viewHolder.setVisibility(R.id.tv_delete, View.GONE);
                    viewHolder.findViewById(R.id.et_description).setBackgroundColor(UIUtils.getColor(R.color.white));
                }

            }
        });

    }

    public void dataDispose() {
        for (int i = getFirstPosition(); i <= getLastPosition(); i++) {
            BaseViewHolder holder = getViewHolder(i);
            if (holder != null) initItem(holder);
        }
    }


    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = ViewUtils.getFragmentView(parent, R.layout.item_goods_upload_detail_image);
            ImageView iv_image = ViewUtils.findViewById(view, R.id.iv_image);
            int width = DensityUtil.getScreenWidthPixels(parent.getContext());
            int w = width - DensityUtil.dp2px(20);
            UIUtils.setViewSize(iv_image, w, w);
            return view;
        } else view = ViewUtils.getFragmentView(parent, R.layout.item_goods_upload_detail_text);
        return view;
    }

    private void initItem(BaseViewHolder holder) {
        TextView editText = holder.findViewById(R.id.et_description);
        if (editText != null) {
            String str = editText.getText().toString();
            str = str.replaceAll("#", "");
            JavaMethod.setFieldValue(getListBean(holder.getAdapterPosition()), "description", str);
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        initItem(holder);
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        return false;
    }
}
