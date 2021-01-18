package cn.com.taodaji.viewModel.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;

public class UsuallyProblemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public UsuallyProblemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_problem_titlle);
        addItemType(TYPE_PERSON, R.layout.item_problem_content);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final ShopEmployeeList lv1 = (ShopEmployeeList) item;
                holder.setText(R.id.tv_item_title, lv1.title)
                        .setText(R.id.tv_item_num,lv1.getEmployeesNum()+"、")
                        .setImageResource(R.id.iv_item_expand, lv1.isExpanded() ? R.mipmap.expand : R.mipmap.unexpand);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });

                break;
            case TYPE_PERSON:
                final ShopEmployeeItem person = (ShopEmployeeItem) item;
                StringBuffer sb = new StringBuffer();
                holder.setText(R.id.tv_item_content, person.getName());
                break;
            default:
                break;
        }
    }
}

