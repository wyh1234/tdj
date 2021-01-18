package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;

import com.base.utils.UIUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FiltrateService;
public class FiltrateCategoryAdapter extends BaseRecyclerViewAdapter<FiltrateService> {


   public FiltrateCategoryAdapter(Context context, List<FiltrateService> data) {
        super(context, data, R.layout.filtrate_service_item);
        }

     @Override
   protected void onBindData(RecyclerViewHolder holder, FiltrateService bean, int position) {
     /*   holder.itemView.setPadding(15, 10, 15, 10);
        int w = UIUtils.getScreenWidthPixels();
        int rl = (w - 130) / 4;
        UIUtils.setViewSize(holder.getView(R.id.rl), rl, rl/2);*/
        }
        }
