package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.base.utils.DialogUtils;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;

import cn.com.taodaji.model.entity.GetNews;
import cn.com.taodaji.ui.activity.myself.NewsContentActivity;


/**
 * Created by Administrator on 2018/1/19.
 */

public class NewsAdapter extends SingleRecyclerViewAdapter {


    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.tv_message_title, "title");
                putRelation(R.id.tv_message_date, "createTime");
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
        GetNews.ItemsBean itemsBean = (GetNews.ItemsBean)getListBean(position);
        if (itemsBean.getIsRead()==1){
            holder.setVisibility(R.id.iv_is_new,View.INVISIBLE);
        }else {
            holder.setVisibility(R.id.iv_is_new,View.VISIBLE);
        }

    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            GetNews.ItemsBean itemsBean = (GetNews.ItemsBean) bean;
            Intent intent = new Intent(view.getContext(), NewsContentActivity.class);
            intent.putExtra("flag",0);
            intent.putExtra("data", itemsBean);
            view.getContext().startActivity(intent);
            update(position,"isRead",1);
            return true;
        }

        return super.onItemClick(view, onclickType, position, bean);
    }


    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_news_list);
    }
}
