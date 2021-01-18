package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class RecyclerViewPopupWindow extends PopupWindow {


    private Context mContext;
    private SingleRecyclerViewAdapter adapter;
    private OnItemClickListener onClickListener;

    public void setOnCustomerItemClickListener(OnItemClickListener onCustomerItemClickListener) {
        this.onClickListener = onCustomerItemClickListener;
    }

    public RecyclerViewPopupWindow(Context context, int width) {
        mContext = context;
        RecyclerView recyclerView = ViewUtils.getLayoutView(context, R.layout.t_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setContentView(recyclerView);
        setWidth(width);
        setHeight(UIUtils.dip2px(300));
        setFocusable(true);
        setBackgroundDrawable(BitmapUtil.getColorDrawable(R.color.white));

        adapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.imageView, "imageObject");
                        putRelation(R.id.bankCard_name, "imageName");
                    }

                    @Override
                    protected void addOnclick() {
                        putViewOnClick(R.id.item_view);
                    }
                });
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.popup_window_recycler_view_item);
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    onClickListener.onItemClick(view, onclickType, position, bean);
                    dismiss();
                    return true;
                }
                return super.onItemClick(view, onclickType, position, bean);
            }
        };

        recyclerView.setAdapter(adapter);

        CustomerData<Integer, String, String> bank = PublicCache.getBank();
        List<ADInfo> list = new ArrayList<>();
        for (int i = 1; i < bank.size(); i++) {
            ADInfo ad = new ADInfo();
            ad.setImageGoodsType(i);
            ad.setImageObject(bank.getKeyOfId(i));
            ad.setImageName(bank.getValueOfId(i));
            list.add(ad);
        }
        adapter.setList(list);


    }
}
