package cn.com.taodaji.ui.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ADInfo;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AccountByStoreId;
import cn.com.taodaji.ui.activity.wallet.MyBankCardDetailActivity;
import tools.activity.SimpleToolbarActivity;

public class HelpCenterQuestionDetailSupplierActivity extends SimpleToolbarActivity {
    private View mainView;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("常见问题");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_help_center_question_detail_supplier);
        body_scroll.addView(mainView);

        EventBus.getDefault().register(this);
    }

    public static void startActivity(Context context, ADInfo abs) {
        EventBus.getDefault().postSticky(abs);
        Intent intent = new Intent(context, HelpCenterQuestionDetailSupplierActivity.class);
        context.startActivity(intent);
    }
    @Subscribe(sticky = true)
    public void onEvent(ADInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        String[]  child = UIUtils.getStringArray(R.array.help_center_child_sup);

        TextView title= findViewById(R.id.txt_title);
        title.setText(event.getImageContent());
        TextView content= findViewById(R.id.txt_content);
        content.setText(child[event.getEntity_id()]);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
