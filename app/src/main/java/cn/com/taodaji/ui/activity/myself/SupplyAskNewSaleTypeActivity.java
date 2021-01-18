package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.RegisterSaleTypeBean;
import cn.com.taodaji.model.event.SupplyAskNewSaleTypeEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SupplyAskNewSaleTypeAdapter;
import tools.activity.SimpleToolbarActivity;

public class SupplyAskNewSaleTypeActivity extends SimpleToolbarActivity {
    private View mainView;
    private RecyclerView grid_sale_apply;
    private SupplyAskNewSaleTypeAdapter grid_sale_apply_adapter;
    private List<RegisterSaleTypeBean.DataBean.ListBean> list=new ArrayList();
    private Button btn_apply_new_submit;
    private LinearLayout line_tips_search_goods;
    private TextView tips_txt;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("申请新分类出售");
    }


    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_supply_ask_new_sale_type);
        line_tips_search_goods = ViewUtils.findViewById(mainView, R.id.line_tips_search_goods);
        tips_txt = ViewUtils.findViewById(mainView, R.id.tips_txt);
        body_scroll.addView(mainView);
        grid_sale_apply = ViewUtils.findViewById(mainView, R.id.grid_sale_apply);
        grid_sale_apply_adapter=new SupplyAskNewSaleTypeAdapter();

        grid_sale_apply.setLayoutManager(new GridLayoutManager(getBaseActivity(), 2));
        //grid_sale_apply.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(20), R.color.transparent));

        grid_sale_apply.setAdapter(grid_sale_apply_adapter);


        grid_sale_apply_adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    switch (view.getId()) {
                        case R.id.tv_type:
                            RegisterSaleTypeBean.DataBean.ListBean tempBean=  (RegisterSaleTypeBean.DataBean.ListBean)bean;
                            //0-可申请，1-不可申请
                            if (tempBean.getIsApply()!=0)  return true;
                            if (tempBean.isSelect()) {
                                list.get(position).setSelect(false);
                            }else{
                                for (int i = 0; i <list.size() ; i++) {
                                    list.get(i).setSelect(false);
                                }
                                list.get(position).setSelect(true);
                            }
                            grid_sale_apply_adapter.notifyDataSetChanged(list);
                           // selectUpdate(position);
                            return true;

                    }
                }
                return false;
            }
        });


        btn_apply_new_submit = ViewUtils.findViewById(mainView, R.id.btn_apply_new_submit);
        btn_apply_new_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<RegisterSaleTypeBean.DataBean.ListBean> tempList=new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        tempList.add(list.get(i));
                    }
                }
                if (tempList.size()>0) {
                    applySubmit( tempList.get(0).getCategoryId(), tempList.get(0).getCategoryName());
                }else {
                    UIUtils.showToastSafe("请选择要申请的新分类");
                }
//                Intent intent1 = new Intent(getBaseActivity(), SupplyAskNewSaleTypeActivity.class);
//                startActivity(intent1);
            }
        });
    }

    @Override
    protected void initData() {
        onStartLoading();
        list.clear();

        Map<String, String> map = new HashMap();
        map.put("site",PublicCache.site_login +"");
        map.put("storeId",PublicCache.loginSupplier.getStore() +"");

        addRequest(ModelRequest.getInstance().applyStoreCategoryList(map), new RequestCallback<RegisterSaleTypeBean>(this) {
            @Override
            protected void onSuc(RegisterSaleTypeBean body) {
                if (body.getData() != null&&body.getData().getList() != null) {
                    List<RegisterSaleTypeBean.DataBean.ListBean> tempList= body.getData().getList();
                    if (tempList != null) {
                        list.addAll(tempList) ;
                        grid_sale_apply.setVisibility(View.VISIBLE);
                        btn_apply_new_submit.setVisibility(View.VISIBLE);
                        line_tips_search_goods.setVisibility(View.GONE);

                        grid_sale_apply_adapter.setList(list);
                    }

                }else{
                    grid_sale_apply.setVisibility(View.GONE);
                    btn_apply_new_submit.setVisibility(View.GONE);

                    tips_txt.setText("暂无数据");
                    line_tips_search_goods.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
             UIUtils.showToastSafe(msg);
            }
        });


    }

   private void applySubmit(int categoryId,String categoryName){
       loading();
       Map<String, Object> map = new HashMap();
       map.put("site",PublicCache.site_login);
       map.put("storeId",PublicCache.loginSupplier.getStore() );
       map.put("categoryId",categoryId);
       map.put("categoryName",categoryName);


       addRequest(ModelRequest.getInstance().applyStoreCategory(map), new RequestCallback<ResultInfo>() {
           @Override
           protected void onSuc(ResultInfo body) {
               loadingDimss();
               UIUtils.showToastSafe("提交成功");
               EventBus.getDefault().post(new SupplyAskNewSaleTypeEvent(true));
               finish();

           }

           @Override
           public void onFailed(int errCode, String msg) {
               loadingDimss();
               UIUtils.showToastSafe(msg);
           }
       });
   }


}
