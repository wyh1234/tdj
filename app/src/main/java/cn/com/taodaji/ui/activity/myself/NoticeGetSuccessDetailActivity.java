package cn.com.taodaji.ui.activity.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.viewModel.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.NoticeGetCashResultBean;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.vm.NoticeDetailVM;
import tools.activity.SimpleToolbarActivity;

public class NoticeGetSuccessDetailActivity extends SimpleToolbarActivity {
    private View mainView;
    private WaitNoticeResultBean.DataBean.ItemsBean itemsBean;
    private BaseViewHolder viewHolder;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();

        simple_title.setText("详情");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_notice_get_success_detail);
        body_scroll.addView(mainView);

        itemsBean=(WaitNoticeResultBean.DataBean.ItemsBean)getIntent().getSerializableExtra("data");
        viewHolder = new BaseViewHolder(mainView, new NoticeDetailVM(), null);


    }

    @Override
    protected void initData() {
        super.initData();
        if (itemsBean == null) {
            return;
        }
        //updataUnReadNotice();
        onStartLoading();
        addRequest(ModelRequest.getInstance().getCashResultDetail(itemsBean.getEntityId(), PublicCache.site_login), new RequestCallback<NoticeGetCashResultBean>(this) {
            @Override
            protected void onSuc(NoticeGetCashResultBean body) {
                if ( body.getData()!=null) {

                    if (itemsBean != null) {
                        itemsBean.setHandleCreateTime(body.getData().getCreate_time());
                        int originatorRole = itemsBean.getOriginatorRole();
                        String start_role= PublicCache.getRoleType().getValueOfKey(originatorRole);
                        itemsBean.setStartNameAndRole(itemsBean.getCustomerName()+"("+start_role+")");

                        viewHolder.setValues(itemsBean);
                    }


                }

            }
        });


    }

   /* private void updataUnReadNotice(){
        addRequest(ModelRequest.getInstance(1).updateNoticeUnReadCount(itemsBean.getEntityId(), PublicCache.site_login), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                if (body.getErr()==0) {
                    EventBus.getDefault().postSticky(new NoticeCountEvent());
                }
            }
        });

    }*/

}
