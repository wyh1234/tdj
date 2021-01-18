package cn.com.taodaji.ui.activity.integral;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GetPointsItem;
import cn.com.taodaji.model.entity.IntegralItem;
import cn.com.taodaji.model.entity.IntegralShopMy;
import cn.com.taodaji.ui.activity.integral.popuwindow.CheckSuccessPopupWindow;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.PointsDetailAdapter;
import tools.activity.DataBaseActivity;
import tools.activity.SimpleToolbarActivity;
import tools.statusbar.Eyes;

public class PointsDetailActivity  extends DataBaseActivity {

    private PointsDetailAdapter adapter;
    private List<IntegralItem.DataBean> itemList = new ArrayList<>();
    @BindView(R.id.rv_points_detail)
     RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(UIUtils.getContext(), R.color.red_f0));
        title.setText("积分明细");
        btn_back.setVisibility(View.VISIBLE);
        adapter = new PointsDetailAdapter(itemList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().integral_item(map, new RequestCallback<IntegralItem>(this) {
            @Override
            public void onSuc(IntegralItem body) {
                ShowLoadingDialog.close();
                if (!ListUtils.isEmpty(body.getData())){
                    itemList.addAll(body.getData());

                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });

    }


    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_points_detail);
    }
}
