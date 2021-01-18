package cn.com.taodaji.viewModel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.HomepageGridDatas;
import cn.com.taodaji.ui.activity.homepage.CommendActivity;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;

import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class GroupHomePageAdapter extends GroupRecyclerAdapter<HomepageGridDatas.ListBean> {

    @Override
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj.getClass() == HomepageGridDatas.ListBean.class) return TYPE_GROUP;
        else return super.concludeItemViewType(obj);
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GROUP:
                view = ViewUtils.getFragmentView(parent, R.layout.item_homepage_grid_group);
                break;
            default:
                view = ViewUtils.getFragmentView(parent, R.layout.item_homepage_grid_child);
                view.setPadding(20, 0, 20, 0);
                int w = UIUtils.getScreenWidthPixels();
                int image_w = (w - 120) / 3;
                ImageView imageView = ViewUtils.findViewById(view, R.id.goods_image);
                UIUtils.setViewSize(imageView, image_w, image_w);
                break;
        }
        return view;
    }

    private HomepageGridDatas datas;

    public void setDatas(HomepageGridDatas datas) {
        this.datas = datas;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_GROUP, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.goods_image, "recommendImage");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.goods_image);
            }
        });
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.goods_image, "image");
                putRelation(R.id.goods_name, "name");
                putRelation(R.id.goods_unit, "unit");
                putRelation(R.id.goods_price, "minPrice");
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.goods_image);
            }
        });
    }

    @Override
    public List getChildList(HomepageGridDatas.ListBean gBean) {
        return gBean.getProducts().getItems();
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {

        if (onclickType == 0) {
            if (getItemViewType(position) == TYPE_GROUP) {
                if (datas == null || datas.getList() == null) return true;
                datas.setIsShowIndex(getGroupIndex(position));
                CommendActivity.startActivity(view.getContext(), datas);
            } else
                //9宫格子view点击事件
                GoodsDetailActivity.startActivity(view.getContext(), (int) JavaMethod.getValueFromBean(bean, "entityId"));
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }
}
