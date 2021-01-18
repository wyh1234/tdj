package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.base.utils.ADInfo;
import com.base.utils.ADInfoGroup;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupHelpCenterSupplierAdapter;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

public class HelpCenterSupplierActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("帮助中心");
    }

    private RecyclerView view;
    private String[] group = null;
    private String[] type = null;
    private String[] service = null;
    // private String[] child = null;

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

        group = UIUtils.getStringArray(R.array.help_center_group_sup);
        service = UIUtils.getStringArray(R.array.help_center_group_service);
        type = UIUtils.getStringArray(R.array.help_center_group_type);

        //   child = UIUtils.getStringArray(R.array.help_center_child_sup);

        List<ADInfoGroup> list = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            ADInfoGroup adInfo = new ADInfoGroup();
            adInfo.setImageName(type[i]);

            if (i == 0) {
                List<ADInfo> adInfoList = new ArrayList<>();
                for (int j = 0; j < group.length; j++) {
                    ADInfo aa = new ADInfo();
                    aa.setImageGoodsType(i);
                    aa.setEntity_id(j);
                    aa.setImageContent(group[j]);
                    aa.setImageName(j + 1 + "." + group[j]);
                    adInfoList.add(aa);
                }
                adInfo.setListAdinfo(adInfoList);
                list.add(adInfo);
            }
            if (i == 1 && PublicCache.loginSupplier.getAuthStatus() == 1) {
                List<ADInfo> adInfoList = new ArrayList<>();
                for (int j = 0; j < service.length; j++) {
                    ADInfo aa = new ADInfo();
                    aa.setImageGoodsType(i);
                    aa.setEntity_id(j);
                    aa.setImageContent(service[j]);
                    aa.setImageName(j + 1 + "." + service[j]);
                    adInfoList.add(aa);
                }

                adInfo.setListAdinfo(adInfoList);
                list.add(adInfo);
            }


        }

        GroupHelpCenterSupplierAdapter groupHelpCenterAdapter = new GroupHelpCenterSupplierAdapter();
        //groupHelpCenterAdapter.setFold(true);
        view.setAdapter(groupHelpCenterAdapter);
        groupHelpCenterAdapter.setGroupList(list);


    }
}
