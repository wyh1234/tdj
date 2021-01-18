package cn.com.taodaji.ui.activity.advertisement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AdvPackage;
import cn.com.taodaji.model.entity.MarketingManage;
import cn.com.taodaji.ui.activity.advertisement.adapter.AdvPackageSelAdapter;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class AdvPackageSelActivity extends DataBaseActivity implements AdvPackageSelAdapter.OnItemClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView_list;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    private AdvPackageSelAdapter advPackageSelAdapter;
    private MarketingManage.DataBean dataBean;
    private List<MarketingManage.DataBean.PackageListBean> advPackageList=new ArrayList<>();
    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.adv_package_sel_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("选择投放套餐");
         dataBean= (MarketingManage.DataBean) getIntent().getSerializableExtra("DataBean");
        advPackageList.addAll(dataBean.getPackageList());
        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        advPackageSelAdapter=new AdvPackageSelAdapter(this,advPackageList);
        advPackageSelAdapter.setOnItemClickListener(this);
        recyclerView_list.setAdapter(advPackageSelAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
            for (int i=0;i<dataBean.getPackageList().size();i++){
                dataBean.getPackageList().get(i).setFlag(false);
            }

             dataBean.getPackageList().get(position).setFlag(true);
            advPackageSelAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(dataBean.getPackageList().get(position));
        finish();

    }
}
