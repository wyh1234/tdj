package cn.com.taodaji.viewModel.vm;

import android.view.View;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.JavaMethod;

import java.util.Map;

import cn.com.taodaji.R;

/**
 * Created by yangkuo on 2018-03-07.
 */

public class SpecificationVM extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.price, "price");

        putRelation(R.id.level2Value, "level2Value");
        putRelation(R.id.level2Unit, "level2Unit");
        putRelation(R.id.level3Value, "level3Value");
        putRelation(R.id.level3Unit, "level3Unit");
        putRelation(R.id.avgPrice, "avgPrice");
        putRelation(R.id.avgUnit, "avgUnit");
        putRelation(R.id.salesNumber, "salesNumber");

        //规格发布
        putRelation(R.id.stock, "stock");

        putRelation("level1Unit", R.id.level1Unit, R.id.tv_ll_text, R.id.level1Unit_);

        putRelation("levelType", R.id.specification_split);
        putViewOnClick(R.id.bt_delete);
        putViewOnClick(R.id.bt_update);

    }

    @Override
    public <T> void setValues(BaseViewHolder viewHolder, Map<String, Object> map, T bean) {
        super.setValues(viewHolder, map, bean);
        int pos = viewHolder.getAdapterPosition();
        String sst = JavaMethod.intParse(pos + 1);
        viewHolder.setText(R.id.tv_position, sst + "：");
    }

    @Override
    public void setValues(BaseViewHolder viewHolder, String pamstr, Object value) {
        if (pamstr.equals("levelType")) {
            int levelType = (int) value;
            change(viewHolder, levelType);
        } else super.setValues(viewHolder, pamstr, value);
    }

    private void change(BaseViewHolder holder, int levelType) {
        if (levelType == 1) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.level2Value, View.GONE);
            holder.setVisibility(R.id.level2Unit, View.GONE);

            holder.setVisibility(R.id.textView1, View.GONE);
            holder.setVisibility(R.id.textView2, View.GONE);
        } else if (levelType == 2) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);

            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);


        } else {
            holder.setVisibility(R.id.level3Value, View.VISIBLE);
            holder.setVisibility(R.id.specification_split, View.VISIBLE);
            holder.setVisibility(R.id.level3Unit, View.VISIBLE);

            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);
        }
    }

}
