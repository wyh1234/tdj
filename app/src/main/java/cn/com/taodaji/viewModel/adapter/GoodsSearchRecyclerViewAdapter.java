package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
//import com.google.gson.Gson;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsClassifySearchBean;
import cn.com.taodaji.ui.activity.shopManagement.ReleaseCommodityGoodsCreateActivity;
import cn.com.taodaji.ui.activity.shopManagement.ReleaseGoodsActivity;

import com.base.utils.JavaMethod;
import com.base.utils.ListDataSave;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-10-19.
 */

public class GoodsSearchRecyclerViewAdapter extends SingleRecyclerViewAdapter {
    ListDataSave listDataSave;

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.goods_search_text_one, "parentCategoryName");
                putRelation(R.id.goods_search_text_two, "categoryName");
                putRelation(R.id.goods_search_text_three, "name");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.item_goods_search_linearlayout);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_goods_search_recycler_view);
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            GoodsClassifySearchBean.DataBean databean = (GoodsClassifySearchBean.DataBean) getListBean(position);
//            Gson gs = new Gson();
//            String objectStr = gs.toJson(databean);
            String objectStr = JavaMethod.transBean2Json(databean);
            //如果存放的集合大小 大于5 移除第一个，如果添加的元素不重复 那么添加这个新元素,
            listDataSave = new ListDataSave(view.getContext(), "BEAN");
            List<String> listStr = listDataSave.getDataList("GOODS_SEARCH_BEAN");
            if (listStr.size() < 5) {
                if (!listStr.contains(objectStr)) {
                    listStr.add(0, objectStr);
                }
            } else {
                //如果已经包含了这个数据，那么不添加为历史记录
                if (!listStr.contains(listStr)) {
                    listStr.remove(listStr.size() - 1);
                    listStr.add(0, objectStr);
                }
            }
            listDataSave.setDataList("GOODS_SEARCH_BEAN", listStr);

            //跳转发布商铺
            if (databean.getIsDrainageArea()==1){
                Intent intent = new Intent(view.getContext(), ReleaseGoodsActivity.class);
                intent.putExtra("commodityId", databean.getEntityId());
                intent.putExtra("categoryId", databean.getCategoryId());
                intent.putExtra("typeId", databean.getTypeId());
                intent.putExtra("name", databean.getName());
                intent.putExtra("goodsEditState", 0);//发布商品
                intent.putExtra("parentCategoryId",databean.getParentCategoryId());
                view.getContext().startActivity(intent);
            }else {
                Intent intent = new Intent(view.getContext(), ReleaseCommodityGoodsCreateActivity.class);
                intent.putExtra("typeId", databean.getTypeId());
                intent.putExtra("name", databean.getName());
                intent.putExtra("commodityId", databean.getEntityId());
                intent.putExtra("categoryId", databean.getCategoryId());
                intent.putExtra("goodsEditState", 0);//发布商品
                intent.putExtra("parentCategoryId",databean.getParentCategoryId());
                intent.putExtra("isForceTemplate",databean.getIsForceTemplate());

                view.getContext().startActivity(intent);
            }


            return true;
        }
        return false;
    }
}
