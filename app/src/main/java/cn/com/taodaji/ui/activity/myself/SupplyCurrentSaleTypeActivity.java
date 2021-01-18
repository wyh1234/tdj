package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.retrofit.RequestCallback;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplySaleTypeBean;
import cn.com.taodaji.model.event.SupplyAskNewSaleTypeEvent;
import cn.com.taodaji.model.event.TodayOrderTimeEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SupplySaleTypeAdapter;
import tools.activity.SimpleToolbarActivity;

public class SupplyCurrentSaleTypeActivity extends SimpleToolbarActivity {
    private View mainView;
    private RecyclerView grid_sale_on,grid_sale_agreeing,grid_sale_disagree;
    private SupplySaleTypeAdapter grid_sale_on_adapter,grid_sale_agreeing_adapter,grid_sale_disagree_adapter;
    //private List<SupplySaleTypeBean> list0,list1,list2;
    private LinearLayout line_sale_on,line_sale_agreeing,line_sale_disagree;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("当前出售的分类");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_supply_current_sale_type);
        body_scroll.addView(mainView);


        line_sale_on = ViewUtils.findViewById(mainView, R.id.line_sale_on);
        line_sale_agreeing = ViewUtils.findViewById(mainView, R.id.line_sale_agreeing);
        line_sale_disagree = ViewUtils.findViewById(mainView, R.id.line_sale_disagree);

        grid_sale_on = ViewUtils.findViewById(mainView, R.id.grid_sale_on);
        grid_sale_agreeing = ViewUtils.findViewById(mainView, R.id.grid_sale_agreeing);
        grid_sale_disagree = ViewUtils.findViewById(mainView, R.id.grid_sale_disagree);

       Button btn_apply_new_type = ViewUtils.findViewById(mainView, R.id.btn_apply_new_type);
       btn_apply_new_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseActivity(), SupplyAskNewSaleTypeActivity.class);
                startActivity(intent1);
            }
        });

        grid_sale_on.setLayoutManager(new GridLayoutManager(getBaseActivity(), 4));
        grid_sale_agreeing.setLayoutManager(new GridLayoutManager(getBaseActivity(), 4));
        grid_sale_disagree.setLayoutManager(new GridLayoutManager(getBaseActivity(), 4));
//        home_page_grid.addItemDecoration(new DividerGridItemDecoration(getContext()));
        grid_sale_on_adapter = new SupplySaleTypeAdapter();
        grid_sale_on_adapter.setSaleStutas(0);
//          grid_adapter.setTypeFixed(TYPE_GROUP);
        grid_sale_on.setAdapter(grid_sale_on_adapter);

        grid_sale_agreeing_adapter = new SupplySaleTypeAdapter();
        grid_sale_agreeing_adapter.setSaleStutas(1);
//          grid_adapter.setTypeFixed(TYPE_GROUP);
        grid_sale_agreeing.setAdapter(grid_sale_agreeing_adapter);

        grid_sale_disagree_adapter = new SupplySaleTypeAdapter();
        grid_sale_disagree_adapter.setSaleStutas(2);
//          grid_adapter.setTypeFixed(TYPE_GROUP);
        grid_sale_disagree.setAdapter(grid_sale_disagree_adapter);


    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        getDatas();

    }
    @Subscribe
    public void onEvent(SupplyAskNewSaleTypeEvent event) {
        if (event.isSucess()) {
            getDatas();
        }
    }
    private void getDatas(){
        onStartLoading();
        Map<String, String> map = new HashMap();
        map.put("site",PublicCache.site_login +"");
        map.put("storeId",PublicCache.loginSupplier.getStore() +"");
        map.put("checkStatus","-1");//-1-查询所有状态,0-待审核,1-审核通过,2-审核驳回
        addRequest(ModelRequest.getInstance().currentStoreCategoryList(map), new RequestCallback<SupplySaleTypeBean>(this) {
            @Override
            protected void onSuc(SupplySaleTypeBean body) {
                if (body.getData() != null&&body.getData().getStoreCategoryList() != null&&body.getData().getStoreCategoryList().size()>0) {
                    List<SupplySaleTypeBean.DataBean.StoreCategoryListBean>   list0=new ArrayList<>();
                    List<SupplySaleTypeBean.DataBean.StoreCategoryListBean>   list1=new ArrayList<>();
                    List<SupplySaleTypeBean.DataBean.StoreCategoryListBean>   list2=new ArrayList<>();

                    List<SupplySaleTypeBean.DataBean.StoreCategoryListBean> storeCategoryList= body.getData().getStoreCategoryList();
                    for (int i = 0; i < storeCategoryList.size(); i++) {
                        SupplySaleTypeBean.DataBean.StoreCategoryListBean  bean=storeCategoryList.get(i);
                        switch(bean.getCheckStatus()){
                            case 0 :
                                list0.add(bean);
                            break;
                            case 1 :
                                list1.add(bean);
                                break;
                            case 2:
                                list2.add(bean);
                            break;
                        }
                    }
                    if (list0.size() >0) {
                        line_sale_agreeing.setVisibility(View.VISIBLE);
                        grid_sale_agreeing_adapter.setList(list0);
                    }else {
                        line_sale_agreeing.setVisibility(View.GONE);
                    }
                    if (list1.size() >0) {
                        line_sale_on.setVisibility(View.VISIBLE);
                        grid_sale_on_adapter.setList(list1);
                    }else {
                        line_sale_on.setVisibility(View.GONE);
                    }
                    if (list2.size() >0) {
                        line_sale_disagree.setVisibility(View.VISIBLE);
                        grid_sale_disagree_adapter.setList(list2);
                    }else {
                        line_sale_disagree.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
