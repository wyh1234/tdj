package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.PunishScoreRecordAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PunishScoreRecord;
import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.ViewUtils;



public class PunishRuleIntroduceActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("店铺评分规则");
    }

    private PunishScoreRecordAdapter adapter;

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_shop_score_rule);
        body_scroll.addView(view);

        RecyclerView recyclerView = ViewUtils.findViewById(view, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PunishScoreRecordAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        if (PublicCache.loginSupplier != null)
            getRequestPresenter().getPunishScored(PublicCache.loginSupplier.getStore(), new RequestCallback<PunishScoreRecord>() {
                @Override
                public void onSuc(PunishScoreRecord body) {
                    adapter.setList(body.getData().getList());
                }

                @Override
                public void onFailed(int punishScoreRecord, String msg) {

                }
            });

    }
}
