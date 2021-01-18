package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PunishmentMessage;
import cn.com.taodaji.model.entity.PunishmentReaded;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.viewModel.adapter.NewsAdapter;
import cn.com.taodaji.model.entity.GetNews;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.viewModel.adapter.PunishmentListAdapter;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by Administrator on 2018/1/23.
 * 通知详情页
 */

public class NewsContentActivity extends SimpleToolbarActivity {
    int entityId;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("通知");
    }


    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_news_content);
        body_other.addView(mainView);
        if (getIntent().getIntExtra("flag",-1)==0) {
            GetNews.ItemsBean itemsBean = (GetNews.ItemsBean) getIntent().getSerializableExtra("data");
            entityId = itemsBean.getEntityId();
            BaseViewHolder viewHolder = new BaseViewHolder(mainView, new NewsAdapter().getVM(0), null);
            viewHolder.setValues(itemsBean);
            //点击查看消息，已读传递到服务器
            isRead();
        }else {
           PunishmentMessage.DataBean.ItemsBean itemBean = (PunishmentMessage.DataBean.ItemsBean) getIntent().getSerializableExtra("data");
            entityId = itemBean.getEntityId();
            BaseViewHolder viewHolder = new BaseViewHolder(mainView, new PunishmentListAdapter().getVM(0), null);
            viewHolder.setValues(itemBean);
            //点击查看消息，已读传递到服务器
            isReadPunishment();
        }
    }

    private void isRead() {
        getRequestPresenter().readNews(entityId, 1, new ResultInfoCallback() {
            @Override
            public void onSuccess(Object body) {

            }

            @Override
            public void onFailed(int o, String msg) {

            }

        });
    }

    private void isReadPunishment(){
        getRequestPresenter().getInstance().updatePunishmentStatus(PublicCache.loginSupplier.getEntityId(),entityId, new RequestCallback<PunishmentReaded>() {
            @Override
            protected void onSuc(PunishmentReaded body) {

            }

            @Override
            public void onFailed(int errCode, String msg) {

            }
        });
    }
}
