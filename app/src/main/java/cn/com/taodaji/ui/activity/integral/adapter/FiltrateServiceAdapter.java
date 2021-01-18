package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FiltrateService;
public class FiltrateServiceAdapter extends BaseRecyclerViewAdapter<FiltrateService> {


    public FiltrateServiceAdapter(Context context, List<FiltrateService> data) {
        super(context, data, R.layout.filtrate_service_item);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, FiltrateService bean, int position) {
     /*   holder.itemView.setPadding(15, 10, 15, 10);
        int w = UIUtils.getScreenWidthPixels();
        int rl = (w - 130) / 3;
        UIUtils.setViewSize(holder.getView(R.id.rl), rl, rl/3);*/
    }
}
