package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.utils.ADInfoGroup;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class GroupQualificationsLookAdapter extends GroupRecyclerAdapter<ADInfoGroup> {


    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj.getClass() == ADInfoGroup.class) return TYPE_GROUP;
        else return super.concludeItemViewType(obj);
    }

    @Override
    public List getChildList(ADInfoGroup gBean) {
        return gBean.getListAdinfo();
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_GROUP, new ADInfoViewModel());
        putBaseVM(TYPE_CHILD, new ADInfoViewModel());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GROUP:
                view = ViewUtils.getFragmentView(parent, R.layout.item_upload_qualifications_group);
                break;
            case TYPE_CHILD:
                view = ViewUtils.getFragmentView(parent, R.layout.t_imageview);
                int w = parent.getMeasuredWidth();
                int spac = parent.getPaddingEnd() + parent.getPaddingStart() + 5 * 6;
                UIUtils.setViewSize(view, (w - spac) / 3, (w - spac) / 3);
                break;
        }
        return view;
    }

}
