package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.com.taodaji.R;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;

public class PunishScoreRecordAdapter extends SingleRecyclerViewAdapter {


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.punish_type, "punish_type");
                putRelation(R.id.punish_reason, "remark");
                putRelation(R.id.punish_score, "punishScore");
                putRelation(R.id.punish_time, "create_datetime");
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_shop_rule);
    }

}
