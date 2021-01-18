package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GetNews;
import cn.com.taodaji.model.entity.PunishmentMessage;
import cn.com.taodaji.ui.activity.myself.NewsContentActivity;

public class PunishmentListAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                //putRelation(R.id.tv_message_title, "title");
                putRelation(R.id.tv_message_date, "showTime");
                putRelation(R.id.tv_message_content, "content");
                //putRelation(R.id.iv_is_new, "isRead");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.ll_message_card);
            }
        });
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        PunishmentMessage.DataBean.ItemsBean itemBean = (PunishmentMessage.DataBean.ItemsBean)getListBean(position);
        if (itemBean.getReaded()==1){
            holder.setVisibility(R.id.iv_is_new, View.INVISIBLE);
        }else {
            holder.setVisibility(R.id.iv_is_new,View.VISIBLE);
        }

    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            PunishmentMessage.DataBean.ItemsBean itemBean = (PunishmentMessage.DataBean.ItemsBean) bean;
            Intent intent = new Intent(view.getContext(), NewsContentActivity.class);
            intent.putExtra("flag",1);
            intent.putExtra("data", itemBean);
            view.getContext().startActivity(intent);
            update(position,"readed",1);
            return true;
        }

        return super.onItemClick(view, onclickType, position, bean);
    }


    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_punishment_list);
    }
}
