package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import cn.com.taodaji.R;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.viewModel.adapter.GroupHelpCenterAdapter;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.ADInfo;
import com.base.utils.ADInfoGroup;
import com.base.utils.UIUtils;

public class HelpCenterActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("帮助中心");
    }

    private RecyclerView view;
    private String[] group = null;
    private String[] child = null;

    @Override
    protected void initMainView() {

        view = getLayoutView(R.layout.t_recyclerview);
        view.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        // view.addItemDecoration(new DividerItemDecoration(this, UIUtils.dip2px(10), R.layout.t_split_line));
        body_other.addView(view);
        body_other.setBackgroundColor(UIUtils.getColor(R.color.gray_f2));
    }

    @Override
    protected void initData() {

        if (PublicCache.login_mode.equals(PURCHASER)) {
            group = UIUtils.getStringArray(R.array.help_center_group_pus);
            child = UIUtils.getStringArray(R.array.help_center_child_pus);
        } else if (PublicCache.login_mode.equals(SUPPLIER)) {
            group = UIUtils.getStringArray(R.array.help_center_group_sup);
            child = UIUtils.getStringArray(R.array.help_center_child_sup);
        }

        List<ADInfoGroup> list = new ArrayList<>();
        for (int i = 0; i < group.length; i++) {
            ADInfoGroup adInfo = new ADInfoGroup();
            adInfo.setImageName(group[i]);
            List<ADInfo> adInfoList = new ArrayList<>();
            ADInfo aa = new ADInfo();
            aa.setImageName(child[i]);
            adInfoList.add(aa);
            adInfo.setListAdinfo(adInfoList);
            list.add(adInfo);
        }

        GroupHelpCenterAdapter groupHelpCenterAdapter = new GroupHelpCenterAdapter();
        groupHelpCenterAdapter.setFold(true);
        view.setAdapter(groupHelpCenterAdapter);
        groupHelpCenterAdapter.setGroupList(list);


    }
}
