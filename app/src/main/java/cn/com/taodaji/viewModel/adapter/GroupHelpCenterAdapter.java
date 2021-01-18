package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ADInfoGroup;
import com.base.utils.ViewUtils;

public class GroupHelpCenterAdapter extends GroupRecyclerAdapter<ADInfoGroup> {


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.image_name, "imageName");
            }
        });
        putBaseVM(TYPE_GROUP, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.group_text, "imageName");
                putRelation(R.id.group_icon, "selected");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.group_item);
            }
        });
    }

    @Override
    public List getChildList(ADInfoGroup gBean) {
        return gBean.getListAdinfo();
    }

    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj.getClass() == ADInfoGroup.class) return TYPE_GROUP;
        else return super.concludeItemViewType(obj);
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        if (position == 0) {
            holder.setVisibility(R.id.split_line, View.GONE);
        } else holder.setVisibility(R.id.split_line, View.VISIBLE);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GROUP:
                view = ViewUtils.getFragmentView(parent, R.layout.item_myself_help_center_group);
                break;
            default:
                view = ViewUtils.getLayoutView(parent.getContext(), R.layout.item_myself_help_center_groupitem);
                break;
        }
        return view;
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            if (concludeItemViewType(bean) == TYPE_GROUP) {
                if (isFold(position)) setSpread(position);
                else setFold(position);
            }
        }
        return false;
    }
}
