package cn.com.taodaji.ui.ppw;


import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FreightParticulars;
import cn.com.taodaji.model.entity.FreightParticularsNew;
import cn.com.taodaji.ui.activity.cashCoupon.DistributionFeeStatementActivity;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.utils.ViewUtils;


public class FreightParticularsPopupWindow extends BasePopupWindow implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SingleRecyclerViewAdapter adapter;
    private FreightParticulars.DataBean dataBean;
    private List<FreightParticularsNew.DataBean> listBean = new ArrayList<>();

    public FreightParticularsPopupWindow(View mainView, List<FreightParticularsNew.DataBean> listBean) {
        super(mainView);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        this.listBean = listBean;
        adapter.setList(listBean);
    }

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_freight);
        ViewUtils.findViewById(view, R.id.tv_i_know).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.tv_see_details).setOnClickListener(this);

        mRecyclerView = ViewUtils.findViewById(view, R.id.rv_freight_particulars_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        adapter = new SingleRecyclerViewAdapter() {

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_freight_particulars_list);
            }

            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.tv_shpsf_text, "title");
                        putRelation(R.id.tv_shpsf, "fee");
                    }
                });
            }

        };
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_i_know:
                dismiss();
                break;
            case R.id.tv_see_details:
                Intent intent =new Intent(v.getContext(), DistributionFeeStatementActivity.class);
                v.getContext().startActivity(intent);
                break;
        }
    }


}